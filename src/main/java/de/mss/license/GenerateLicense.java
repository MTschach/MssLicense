package de.mss.license;

import java.util.UUID;

import de.mss.license.exception.LicenseException;
import de.mss.utils.CRC16CCITT;

public class GenerateLicense extends LicenseBase {

   public GenerateLicense() {}


   public String generateLicense(String applId, String custNo, int version, LicenseType licenseType) throws LicenseException {
      String flavor = flavorAppId(applId, custNo, version);
      UUID lk = UUID.randomUUID();
      String[] subkeys = lk.toString().split("-");
      subkeys[1] = String.format("%04x", Integer.valueOf((version << 8) + licenseType.getUuidValue()));
      subkeys[4] = subkeys[4].substring(0, subkeys[4].length() - 5);
      CRC16CCITT c = new CRC16CCITT();
      c.update(flavor.toUpperCase().getBytes());
      c.update(subkeys[0].toUpperCase().getBytes());
      for (int i = 1; i <= 4; i++ )
         c.update(("-" + subkeys[i].toUpperCase()).getBytes());

      subkeys[4] += String.format("%04x", Integer.valueOf(c.getValue()));

      int s = getControlSum(subkeys[0], 0);
      s = getControlSum(subkeys[1], s);
      s = getControlSum(subkeys[2], s);
      s = getControlSum(subkeys[3], s);
      s = 16 - getControlSum(subkeys[4], s);

      String key = subkeys[0] + "-" + subkeys[1] + "-" + subkeys[2] + "-" + subkeys[3] + "-" + subkeys[4] + String.format("%1x", Integer.valueOf(s));
      return key;
   }


   private int getControlSum(String s, int c) {
      int ret = c;

      long l = Long.parseLong(s, 16);
      int offset = 60;
      while (offset >= 0) {
         ret += (l >> offset) & 0xf;
         offset -= 4;
      }

      return ret & 0xf;
   }


   public static void main(String[] args) throws LicenseException {
      String key = new GenerateLicense().generateLicense("applId", "12345678", 0, LicenseType.FREE_LICENSE);
      System.out.println(key);
      try {
      LicenseKey lk = new LicenseKey(key);
      }
      catch (Exception e) {
         e.printStackTrace();
      }
   }
}
