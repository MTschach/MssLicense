package de.mss.license;

import de.mss.license.exception.ErrorCodes;
import de.mss.license.exception.LicenseException;
import de.mss.utils.CRC16CCITT;
import de.mss.utils.Tools;

public class License extends LicenseBase {

   private String     appId          = null;
   private String     customerNumber = null;
   private LicenseKey licenseKey     = null;


   public License(String a, String c, String k) throws LicenseException {
      this.appId = a;
      this.customerNumber = c;
      this.licenseKey = new LicenseKey(k);

      checkLicense();
   }


   private void checkLicense() throws LicenseException {
      if (!Tools.isSet(this.appId))
         throw new LicenseException(de.mss.utils.exception.ErrorCodes.ERROR_INVALID_PARAM, "no license given");

      if (!Tools.isSet(this.customerNumber))
         throw new LicenseException(de.mss.utils.exception.ErrorCodes.ERROR_INVALID_PARAM, "no customer number given");

      String flavor = flavorAppId(this.appId, this.customerNumber, this.licenseKey.getLicenseVersion());
      de.mss.utils.CRC16CCITT c = new CRC16CCITT();
      c.update(flavor.toUpperCase().getBytes());
      c.update(this.licenseKey.getLicenseKey().substring(0, this.licenseKey.getLicenseKey().length() - 5).toUpperCase().getBytes());
      if (this.licenseKey.getControlSum() != c.getValue())
         throw new LicenseException(ErrorCodes.ERROR_WRONG_LICENSE_KEY, "The license key is wrong");
   }
}
