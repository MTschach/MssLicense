package de.mss.license;
import org.junit.Test;

import de.mss.license.exception.LicenseException;

public class LicenseTest {

   @Test
   public void testOk() throws LicenseException {
      License l = new License("applId", "12345678", "a65b5ccf-007f-4dfe-adbb-e8b37968299d");
   }
}
