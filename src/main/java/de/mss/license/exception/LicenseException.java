package de.mss.license.exception;

import de.mss.utils.exception.Error;
import de.mss.utils.exception.MssException;


public class LicenseException extends MssException {

   private static final long serialVersionUID = 8239343090752874906L;


   public LicenseException(Error ec) {
      super(ec);
   }


   public LicenseException(Error ec, Throwable t) {
      super(ec, t);
   }


   public LicenseException(Error ec, Throwable t, String msg) {
      super(ec, t, msg);
   }


   public LicenseException(Error ec, String msg) {
      super(ec, msg);
   }


   public LicenseException(int code) {
      super(code);
   }


   public LicenseException(Throwable t) {
      super(t);
   }


   public LicenseException(Throwable t, String msg) {
      super(t, msg);
   }


   public LicenseException(int code, String msg) {
      super(code, msg);
   }

}
