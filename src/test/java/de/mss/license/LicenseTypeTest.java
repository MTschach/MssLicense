package de.mss.license;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Test;

import de.mss.license.exception.LicenseException;

public class LicenseTypeTest {

   @Test
   public void getByType() throws LicenseException {
      LicenseType type = null;

      try {
         type = LicenseType.getByType(null);
         fail();
      }
      catch (LicenseException e) {
         assertEquals("ErrorCode", 3001, e.getError().getErrorCode());
      }

      try {
         type = LicenseType.getByType("");
         fail();
      }
      catch (LicenseException e) {
         assertEquals("ErrorCode", 3001, e.getError().getErrorCode());
      }

      try {
         type = LicenseType.getByType("blie");
         fail();
      }
      catch (LicenseException e) {
         assertEquals("ErrorCode", 3001, e.getError().getErrorCode());
      }

      type = LicenseType.getByType("free");
      checkLicenseType(LicenseType.FREE_LICENSE, type);

      type = LicenseType.getByType("PERSONAL");
      checkLicenseType(LicenseType.PERSONAL_LICENSE, type);

      type = LicenseType.getByType("Professional");
      checkLicenseType(LicenseType.PROFESSIONAL_LICENSE, type);

      type = LicenseType.getByType("EnTeRpRiSe");
      checkLicenseType(LicenseType.ENTERPRISE_LICENSE, type);
   }


   @Test
   public void getByUuid() throws LicenseException {
      LicenseType type = null;

      try {
         type = LicenseType.getByUuidValue(0);
         fail();
      }
      catch (LicenseException e) {
         assertEquals("ErrorCode", 3001, e.getError().getErrorCode());
      }

      type = LicenseType.getByUuidValue(0x7f);
      checkLicenseType(LicenseType.FREE_LICENSE, type);

      type = LicenseType.getByUuidValue(0xf3);
      checkLicenseType(LicenseType.PERSONAL_LICENSE, type);

      type = LicenseType.getByUuidValue(0x41);
      checkLicenseType(LicenseType.PROFESSIONAL_LICENSE, type);

      type = LicenseType.getByUuidValue(0xac);
      checkLicenseType(LicenseType.ENTERPRISE_LICENSE, type);
   }


   private void checkLicenseType(LicenseType expected, LicenseType licenseType) {
      assertNotNull("Expected is not null", expected);
      assertNotNull("License type is not null", licenseType);

      assertEquals("License Type", expected.getLicenseType(), licenseType.getLicenseType());
      assertEquals("License level", expected.getLevel(), licenseType.getLevel());
      assertEquals("UUID Value", expected.getUuidValue(), licenseType.getUuidValue());
   }
}
