package org.joda.time.tz;

import java.util.Locale;

public interface NameProvider {
  String getShortName(Locale paramLocale, String paramString1, String paramString2);
  
  String getName(Locale paramLocale, String paramString1, String paramString2);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/tz/NameProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */