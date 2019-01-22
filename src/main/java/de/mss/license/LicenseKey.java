package de.mss.license;

import java.util.UUID;

import de.mss.license.exception.ErrorCodes;
import de.mss.license.exception.LicenseException;

public class LicenseKey extends LicenseBase{

   private String      licenseKey     = null;
   private LicenseType licenseType    = LicenseType.FREE_LICENSE;
   private int         licenseVersion = 0;
   private int         controlSum     = 0;


   public LicenseKey(String l) throws LicenseException {
      initLicenseKey(l);
   }


   private void initLicenseKey(String l) throws LicenseException {
      if (l == null)
         throw new LicenseException(de.mss.utils.exception.ErrorCodes.ERROR_INVALID_PARAM, "no LicenseKey given");
      try {
         UUID.fromString(l);
      }
      catch (IllegalArgumentException e) {
         throw new LicenseException(ErrorCodes.ERROR_INVALID_LICENSE_KEY, e, "wrong license key format");
      }

      this.licenseKey = l;
      String[] keys = l.split("-");
      int version = Integer.parseInt(keys[1], 16);
      this.licenseVersion = (version >> 8) & 0xff;
      this.licenseType = LicenseType.getByUuidValue(version & 0xff);

      this.controlSum = Integer.parseInt(keys[4].substring(keys[4].length() - 5, keys[4].length() - 1), 16);
      int c = getControlSumV0(keys[0], 0);
      c = getControlSumV0(keys[1], c);
      c = getControlSumV0(keys[2], c);
      c = getControlSumV0(keys[3], c);
      c = getControlSumV0(keys[4], c);

      if (c != 0)
         throw new LicenseException(ErrorCodes.ERROR_INVALID_LICENSE_KEY, "wrong license key");
   }


   public String getLicenseKey() {
      return this.licenseKey;
   }


   public LicenseType getLicenseType() {
      return this.licenseType;
   }


   public int getLicenseVersion() {
      return this.licenseVersion;
   }


   public int getControlSum() {
      return this.controlSum;
   }
}
