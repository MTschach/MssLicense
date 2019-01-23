package de.mss.license.exception;

import org.junit.Test;

import de.mss.utils.exception.MssException;
import junit.framework.TestCase;

public class ErrorCodesTest extends TestCase {

   @SuppressWarnings("unused")
   @Test
   public void testOk() {
      try {
         new ErrorCodes();
      }
      catch (MssException e) {
         assertEquals("ErrorCode", de.mss.utils.exception.ErrorCodes.ERROR_NOT_INSTANCABLE.getErrorCode(), e.getError().getErrorCode());
      }
   }
}
