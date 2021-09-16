package org.joda.time;

public interface ReadableInterval {
  Chronology getChronology();
  
  long getStartMillis();
  
  DateTime getStart();
  
  long getEndMillis();
  
  DateTime getEnd();
  
  boolean contains(ReadableInstant paramReadableInstant);
  
  boolean contains(ReadableInterval paramReadableInterval);
  
  boolean overlaps(ReadableInterval paramReadableInterval);
  
  boolean isAfter(ReadableInstant paramReadableInstant);
  
  boolean isAfter(ReadableInterval paramReadableInterval);
  
  boolean isBefore(ReadableInstant paramReadableInstant);
  
  boolean isBefore(ReadableInterval paramReadableInterval);
  
  Interval toInterval();
  
  MutableInterval toMutableInterval();
  
  Duration toDuration();
  
  long toDurationMillis();
  
  Period toPeriod();
  
  Period toPeriod(PeriodType paramPeriodType);
  
  boolean equals(Object paramObject);
  
  int hashCode();
  
  String toString();
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/ReadableInterval.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */