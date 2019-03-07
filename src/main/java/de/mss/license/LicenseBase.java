package de.mss.license;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import de.mss.license.exception.ErrorCodes;
import de.mss.license.exception.LicenseException;
import de.mss.utils.Tools;

public abstract class LicenseBase {

   static {
      String libName = System.mapLibraryName("msslicense");
      if (!loadFromResource(libName) && !loadFromFileSystem(libName)) {
         System.err.println("Could not load library " + libName);
         System.exit(1);
      }
      File lib = new File(System.getProperty("java.io.tmpdir") + libName);
      System.load(lib.getAbsolutePath());
   }


   private static boolean loadFromResource(String libName) {
      try (InputStream inStream = LicenseBase.class.getResourceAsStream(libName)) {
         return copyFile(libName, inStream);
      }
      catch (IOException e) {}
      return false;
   }


   private static boolean loadFromFileSystem(String libName) {
      try (FileInputStream inStream = new FileInputStream("lib/" + libName)) {
         return copyFile(libName, inStream);
      }
      catch (IOException e) {
         e.printStackTrace();
      }
      return false;
   }


   private static boolean copyFile(String libName, InputStream inStream) {
      if (inStream == null)
         return false;

      String outName = System.getProperty("java.io.tmpdir") + libName;
      byte[] buffer = new byte[10240];
      int ret = 0;
      try (FileOutputStream outStream = new FileOutputStream(outName)) {
         while ((ret = inStream.read(buffer)) > 0)
            outStream.write(buffer, 0, ret);

         outStream.flush();
         outStream.close();

         return true;
      }
      catch (IOException e) {

      }

      return false;
   }


   public native String flavorAppIdV0(String appId, String customerNumber);
   public native String flavorAppIdV1(String appId, String customerNumber);
   public native String flavorAppIdV2(String appId, String customerNumber);


   protected String flavorAppId(String applId, String custNo, int version) throws LicenseException {
      switch (version) {
         case 0:
            return flavorAppIdV0(applId, custNo);
         case 1:
            return flavorAppIdV1(applId, custNo);
         case 2:
            return flavorAppIdV2(applId, custNo);
         default:
            throw new LicenseException(
                  ErrorCodes.ERROR_UNSUPPORTED_LICENSE_VERSION,
                  "The license version " + version + " is not supported");
      }
   }



   protected int checkAndGetControlByte(String licenseKey, int licenseVersion) throws LicenseException {
      return checkAndGetControlByte(licenseKey, licenseVersion, true);
   }


   protected int checkAndGetControlByte(String licenseKey, int licenseVersion, boolean checkOnly) throws LicenseException {
      switch (licenseVersion) {
         case 1:
            return checkAndGetControlByteV1(licenseKey, checkOnly);

         default:
            throw new LicenseException(ErrorCodes.ERROR_UNSUPPORTED_LICENSE_VERSION, "license version " + licenseVersion + " is not supported");
      }
   }


   private int checkAndGetControlByteV1(String licenseKey, boolean checkOnly) throws LicenseException {
      if (!Tools.isSet(licenseKey))
         throw new LicenseException(de.mss.utils.exception.ErrorCodes.ERROR_INVALID_PARAM, "the licenseKey is not set");

      String[] subKeys = licenseKey.split("-");
      int checkByte = 0;
      for (int i = 0; i < subKeys.length; i++ )
         checkByte = calcCheckByteV1(subKeys[i], checkByte);

      if (!checkOnly)
         return checkByte;

      if (checkByte != 0)
         throw new LicenseException(ErrorCodes.ERROR_INVALID_LICENSE_KEY, "wrong license key");

      return 0;
   }


   private int calcCheckByteV1(String subKey, int checkByte) {
      int ret = checkByte;
      long l = Long.parseLong(subKey, 16);
      int offset = 60;
      while (offset >= 0) {
         ret += (l >> offset) & 0xf;
         offset -= 4;
      }

      return ret & 0xf;
   }

}
