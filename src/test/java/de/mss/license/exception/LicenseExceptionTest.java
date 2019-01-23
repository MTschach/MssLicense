package de.mss.license.exception;

import org.junit.Test;

import junit.framework.TestCase;

public class LicenseExceptionTest extends TestCase {

   @Test
   public void testErrorCode() {
      LicenseException e = new LicenseException(
            new de.mss.utils.exception.Error(
                  de.mss.utils.exception.ErrorCodes.ERROR_NOT_INSTANCABLE.getErrorCode(),
                  de.mss.utils.exception.ErrorCodes.ERROR_NOT_INSTANCABLE.getErrorText()));
      checkException(e, de.mss.utils.exception.ErrorCodes.ERROR_NOT_INSTANCABLE, false, null, 0);
   }


   @Test
   public void testErrorCodeThrowable() {
      LicenseException e = new LicenseException(
            new de.mss.utils.exception.Error(
                  de.mss.utils.exception.ErrorCodes.ERROR_NOT_INSTANCABLE.getErrorCode(),
                  de.mss.utils.exception.ErrorCodes.ERROR_NOT_INSTANCABLE.getErrorText()),
            new Throwable());
      checkException(e, de.mss.utils.exception.ErrorCodes.ERROR_NOT_INSTANCABLE, true, null, 0);
   }


   @Test
   public void testErrorCodeThrowableMsg() {
      LicenseException e = new LicenseException(
            new de.mss.utils.exception.Error(
                  de.mss.utils.exception.ErrorCodes.ERROR_NOT_INSTANCABLE.getErrorCode(),
                  de.mss.utils.exception.ErrorCodes.ERROR_NOT_INSTANCABLE.getErrorText()),
            new Throwable(),
            "own errortext");
      checkException(e, de.mss.utils.exception.ErrorCodes.ERROR_NOT_INSTANCABLE, true, "own errortext", 0);
   }


   @Test
   public void testErrorCodeMessage() {
      LicenseException e = new LicenseException(
            new de.mss.utils.exception.Error(
                  de.mss.utils.exception.ErrorCodes.ERROR_NOT_INSTANCABLE.getErrorCode(),
                  de.mss.utils.exception.ErrorCodes.ERROR_NOT_INSTANCABLE.getErrorText()),
            "own errortext");
      checkException(e, de.mss.utils.exception.ErrorCodes.ERROR_NOT_INSTANCABLE, false, "own errortext", 0);
   }


   @Test
   public void testAltErrorCode() {
      LicenseException e = new LicenseException(4);
      checkException(e, de.mss.utils.exception.ErrorCodes.NO_ERROR, false, null, 4);
   }


   @Test
   public void testThrowable() {
      LicenseException e = new LicenseException(new Throwable());
      checkException(e, de.mss.utils.exception.ErrorCodes.NO_ERROR, true, null, 0);
   }


   @Test
   public void testThrowableMsg() {
      LicenseException e = new LicenseException(new Throwable(), "own errortext");
      checkException(e, de.mss.utils.exception.ErrorCodes.NO_ERROR, true, "own errortext", 0);
   }


   @Test
   public void testCodeText() {
      LicenseException e = new LicenseException(5, "an error");
      checkException(e, de.mss.utils.exception.ErrorCodes.NO_ERROR, false, "an error", 5);
   }


   protected
         void
         checkException(LicenseException e, de.mss.utils.exception.Error expError, boolean throwable, String expErrorText, int expErrorCode) {
      assertEquals("ErrorCode", expError.getErrorCode(), e.getError().getErrorCode());
      assertEquals("ErrorText", expError.getErrorText(), e.getError().getErrorText());

      assertEquals("Alt ErrorCode", expErrorCode, e.getAltErrorCode());

      if (expErrorText != null)
         assertEquals("Alt Error text", expErrorText, e.getAltErrorText());
      else
         assertNull("Alt ErrorText", e.getAltErrorText());

      if (throwable)
         assertNotNull("Cause not null", e.getCause());
      else
         assertNull("Cause is null", e.getCause());

      StringBuilder expMsg = new StringBuilder("Error : ");
      if (expError != de.mss.utils.exception.ErrorCodes.NO_ERROR) {
         expMsg.append(expError.getErrorCode() + "(" + (expErrorText == null ? expError.getErrorText() : expErrorText) + ")");
      }
      else {
         expMsg.append(expErrorCode + "(" + expErrorText + ")");
      }

      String msg = e.toString();

      assertTrue("toString", msg.startsWith(expMsg.toString()));

   }
}
