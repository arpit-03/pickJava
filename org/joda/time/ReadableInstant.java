package org.joda.time;

public interface ReadableInstant extends Comparable<ReadableInstant> {
  long getMillis();
  
  Chronology getChronology();
  
  DateTimeZone getZone();
  
  int get(DateTimeFieldType paramDateTimeFieldType);
  
  boolean isSupported(DateTimeFieldType paramDateTimeFieldType);
  
  Instant toInstant();
  
  boolean isEqual(ReadableInstant paramReadableInstant);
  
  boolean isAfter(ReadableInstant paramReadableInstant);
  
  boolean isBefore(ReadableInstant paramReadableInstant);
  
  boolean equals(Object paramObject);
  
  int hashCode();
  
  String toString();
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/ReadableInstant.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */