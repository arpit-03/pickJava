package org.joda.time.convert;

import org.joda.time.Chronology;
import org.joda.time.DateTimeZone;

public interface InstantConverter extends Converter {
  Chronology getChronology(Object paramObject, DateTimeZone paramDateTimeZone);
  
  Chronology getChronology(Object paramObject, Chronology paramChronology);
  
  long getInstantMillis(Object paramObject, Chronology paramChronology);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/convert/InstantConverter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */