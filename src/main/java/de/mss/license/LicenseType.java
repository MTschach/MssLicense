package de.mss.license;

import de.mss.license.exception.ErrorCodes;
import de.mss.license.exception.LicenseException;

public enum LicenseType {
   //@formatter:off
   //                       name                level    UUID-Value
   FREE_LICENSE            ("free",             0,       0x7f),
   PERSONAL_LICENSE        ("personal",         100,     0xf3),
   PROFESSIONAL_LICENSE    ("professional",     200,     0x41),
   ENTERPRISE_LICENSE      ("enterprise",       300,     0xac)
   ;
   //@formatter:on
   
   
   private String type     = null;
   private int level       = 0;
   private int    uuidValue = 0;
   

   private LicenseType(String t, int l, int u) {
      this.type = t;
      this.level = l;
      this.uuidValue = u;
   }
   
   
   public String getLicenseType() {
      return this.type;
   }
   
   
   public int getLevel() {
      return this.level;
   }
   
   
   public int getUuidValue() {
      return this.uuidValue;
   }


   public static LicenseType getByUuidValue(int u) throws LicenseException {
      for (LicenseType l : LicenseType.values())
         if (u == l.getUuidValue())
            return l;

      throw new LicenseException(ErrorCodes.ERROR_UNKNOWN_LICENSE_TYPE, "no license type for the given UUID-Value found");
   }


   public static LicenseType getByType(String t) throws LicenseException {
      
      for (LicenseType l : LicenseType.values())
         if (l.getLicenseType().equalsIgnoreCase(t))
            return l;
      
      throw new LicenseException(ErrorCodes.ERROR_UNKNOWN_LICENSE_TYPE, "Type '" + t + "' is not a valid license type");
   }
}
