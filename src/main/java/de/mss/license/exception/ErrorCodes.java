package de.mss.license.exception;

import de.mss.utils.exception.Error;
import de.mss.utils.exception.MssException;

public class ErrorCodes {

   private static final int  ERROR_CODE_BASE                   = 3000;
   public static final Error ERROR_INVALID_LICENSE_KEY         = new Error(ERROR_CODE_BASE + 0, "invalid license key");
   public static final Error ERROR_UNKNOWN_LICENSE_TYPE        = new Error(ERROR_CODE_BASE + 1, "unknown license type");
   public static final Error ERROR_UNSUPPORTED_LICENSE_VERSION = new Error(ERROR_CODE_BASE + 2, "unsupported license version");
   public static final Error ERROR_WRONG_LICENSE_KEY           = new Error(ERROR_CODE_BASE + 3, "License key wrong");

   public ErrorCodes() throws MssException {
      throw new MssException(
            new Error(
                  de.mss.utils.exception.ErrorCodes.ERROR_NOT_INSTANCABLE.getErrorCode(),
                  de.mss.utils.exception.ErrorCodes.ERROR_NOT_INSTANCABLE.getErrorText() + " (" + getClass().getName() + ")"));
   }
}
