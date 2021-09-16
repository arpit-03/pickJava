package org.joda.time.convert;

import org.joda.time.Chronology;
import org.joda.time.DateTimeZone;
import org.joda.time.ReadablePartial;
import org.joda.time.format.DateTimeFormatter;

public interface PartialConverter extends Converter {
  Chronology getChronology(Object paramObject, DateTimeZone paramDateTimeZone);
  
  Chronology getChronology(Object paramObject, Chronology paramChronology);
  
  int[] getPartialValues(ReadablePartial paramReadablePartial, Object paramObject, Chronology paramChronology);
  
  int[] getPartialValues(ReadablePartial paramReadablePartial, Object paramObject, Chronology paramChronology, DateTimeFormatter paramDateTimeFormatter);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/convert/PartialConverter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */