package de.mss.license;

import de.mss.license.exception.ErrorCodes;
import de.mss.license.exception.LicenseException;

public abstract class LicenseBase {

   //   public native String flavorAppIdV0(String appId, String customerNumber);


   protected String flavorAppId(String applId, String custNo, int version) throws LicenseException {
      switch (version) {
         case 0:
            return flavorAppIdV0(applId, custNo);
         default:
            throw new LicenseException(
                  ErrorCodes.ERROR_UNSUPPORTED_LICENSE_VERSION,
                  "The license version " + version + " is not supported");
      }
   }


   private String flavorAppIdV0(String applId, String custNo) {
      return applId + custNo;
   }


   protected int getControlSumV0(String s, int c) {
      int ret = c;

      long l = Long.parseLong(s, 16);
      int offset = 60;
      while (offset >= 0) {
         ret += (l >> offset) & 0xf;
         offset -= 4;
      }

      return ret & 0xf;
   }


}
