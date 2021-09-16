package org.joda.time;

public interface ReadWritableInterval extends ReadableInterval {
  void setInterval(long paramLong1, long paramLong2);
  
  void setInterval(ReadableInterval paramReadableInterval);
  
  void setInterval(ReadableInstant paramReadableInstant1, ReadableInstant paramReadableInstant2);
  
  void setChronology(Chronology paramChronology);
  
  void setStartMillis(long paramLong);
  
  void setStart(ReadableInstant paramReadableInstant);
  
  void setEndMillis(long paramLong);
  
  void setEnd(ReadableInstant paramReadableInstant);
  
  void setDurationAfterStart(ReadableDuration paramReadableDuration);
  
  void setDurationBeforeEnd(ReadableDuration paramReadableDuration);
  
  void setPeriodAfterStart(ReadablePeriod paramReadablePeriod);
  
  void setPeriodBeforeEnd(ReadablePeriod paramReadablePeriod);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/ReadWritableInterval.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */