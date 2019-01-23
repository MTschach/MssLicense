package de.mss.license;

import de.mss.license.exception.LicenseException;

public class LicenseBaseForTest extends LicenseBase {

   public LicenseBaseForTest() {}


   public String flavorAppIdForTest(String applId, String custNo, int version) throws LicenseException {
      return flavorAppId(applId, custNo, version);
   }


   public int checkAndGetControlByteForTest(String licenseKey, int licenseVersion, boolean checkOnly) throws LicenseException {
      return checkAndGetControlByte(licenseKey, licenseVersion, checkOnly);
   }
}
