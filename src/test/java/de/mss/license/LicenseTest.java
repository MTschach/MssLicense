package de.mss.license;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import de.mss.license.exception.LicenseException;

public class LicenseTest {

   private final String LICENSE_KEY_OK = "35753951-017f-4976-ba5b-958021ce025a";

   @SuppressWarnings("unused")
   @Test
   public void testOk() throws LicenseException {
      new License("applId", "12345678", "35753951-017f-4976-ba5b-958021ce025a");
   }


   @SuppressWarnings("unused")
   @Test
   public void noApplId() {
      try {
         new License(null, "12345678", "35753951-017f-4976-ba5b-958021ce025a");
         fail();
      }
      catch (LicenseException e) {
         assertEquals("ErrorCode", 2, e.getError().getErrorCode());
      }
   }


   @SuppressWarnings("unused")
   @Test
   public void noCustomerNumber() {
      try {
         new License("applId", null, "35753951-017f-4976-ba5b-958021ce025a");
         fail();
      }
      catch (LicenseException e) {
         assertEquals("ErrorCode", 2, e.getError().getErrorCode());
      }
   }


   @SuppressWarnings("unused")
   @Test
   public void noLicenseKey() {
      try {
         new License("applId", "12345678", null);
         fail();
      }
      catch (LicenseException e) {
         assertEquals("ErrorCode", 2, e.getError().getErrorCode());
      }
   }


   @SuppressWarnings("unused")
   @Test
   public void noWrongLicenseKey() {
      try {
         new License("applId", "12345678", "35753951-017f-4976-ba5b-958021ce0269");
         fail();
      }
      catch (LicenseException e) {
         assertEquals("ErrorCode", 3003, e.getError().getErrorCode());
      }
   }


   @Test
   public void flavorWithWrongVersion() {
      try {
         new LicenseBaseForTest().flavorAppIdForTest("applId", "12345678", -1);
         fail();
      }
      catch (LicenseException e) {
         assertEquals("ErrorCode", 3002, e.getError().getErrorCode());
      }
   }


   @Test
   public void checkControlByteWithWrongVersion() {
      try {
         new LicenseBaseForTest().checkAndGetControlByteForTest(LICENSE_KEY_OK, -1, true);
         fail();
      }
      catch (LicenseException e) {
         assertEquals("ErrorCode", 3002, e.getError().getErrorCode());
      }
   }


   @Test
   public void checkControlByteNullLicenseKey() {
      try {
         new LicenseBaseForTest().checkAndGetControlByteForTest(null, 1, true);
         fail();
      }
      catch (LicenseException e) {
         assertEquals("ErrorCode", 2, e.getError().getErrorCode());
      }
   }


   @Test
   public void checkControlByteEmptyLicenseKey() {
      try {
         new LicenseBaseForTest().checkAndGetControlByteForTest("", 1, true);
         fail();
      }
      catch (LicenseException e) {
         assertEquals("ErrorCode", 2, e.getError().getErrorCode());
      }
   }


   @Test
   public void getControlByte() throws LicenseException {
      int checkByte = new LicenseBaseForTest().checkAndGetControlByteForTest(LICENSE_KEY_OK.substring(0, LICENSE_KEY_OK.length() - 1), 1, false);

      assertEquals("CheckByte", 6, checkByte);
   }
}
