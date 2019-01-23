package de.mss.license;

import de.mss.license.exception.ErrorCodes;
import de.mss.license.exception.LicenseException;
import de.mss.utils.Tools;

public abstract class LicenseBase {

   public native String flavorAppIdV2(String appId, String customerNumber);


   protected String flavorAppId(String applId, String custNo, int version) throws LicenseException {
      switch (version) {
         case 1:
            return flavorAppIdV1(applId, custNo);
         default:
            throw new LicenseException(
                  ErrorCodes.ERROR_UNSUPPORTED_LICENSE_VERSION,
                  "The license version " + version + " is not supported");
      }
   }


   private String flavorAppIdV1(String applId, String custNo) {
      return (applId + custNo);
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
