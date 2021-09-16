package org.joda.time.convert;

import org.joda.time.Chronology;
import org.joda.time.ReadWritableInterval;

public interface IntervalConverter extends Converter {
  boolean isReadableInterval(Object paramObject, Chronology paramChronology);
  
  void setInto(ReadWritableInterval paramReadWritableInterval, Object paramObject, Chronology paramChronology);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/convert/IntervalConverter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */