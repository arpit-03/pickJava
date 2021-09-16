package org.joda.time.convert;

import org.joda.time.Chronology;
import org.joda.time.PeriodType;
import org.joda.time.ReadWritablePeriod;

public interface PeriodConverter extends Converter {
  void setInto(ReadWritablePeriod paramReadWritablePeriod, Object paramObject, Chronology paramChronology);
  
  PeriodType getPeriodType(Object paramObject);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/convert/PeriodConverter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */