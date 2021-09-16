package org.joda.time;

public interface ReadWritableInstant extends ReadableInstant {
  void setMillis(long paramLong);
  
  void setMillis(ReadableInstant paramReadableInstant);
  
  void setChronology(Chronology paramChronology);
  
  void setZone(DateTimeZone paramDateTimeZone);
  
  void setZoneRetainFields(DateTimeZone paramDateTimeZone);
  
  void add(long paramLong);
  
  void add(ReadableDuration paramReadableDuration);
  
  void add(ReadableDuration paramReadableDuration, int paramInt);
  
  void add(ReadablePeriod paramReadablePeriod);
  
  void add(ReadablePeriod paramReadablePeriod, int paramInt);
  
  void set(DateTimeFieldType paramDateTimeFieldType, int paramInt);
  
  void add(DurationFieldType paramDurationFieldType, int paramInt);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/ReadWritableInstant.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */