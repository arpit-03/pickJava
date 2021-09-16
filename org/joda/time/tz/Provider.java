package org.joda.time.tz;

import java.util.Set;
import org.joda.time.DateTimeZone;

public interface Provider {
  DateTimeZone getZone(String paramString);
  
  Set<String> getAvailableIDs();
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/tz/Provider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */