package org.joda.time;

public interface ReadableDuration extends Comparable<ReadableDuration> {
  long getMillis();
  
  Duration toDuration();
  
  Period toPeriod();
  
  boolean isEqual(ReadableDuration paramReadableDuration);
  
  boolean isLongerThan(ReadableDuration paramReadableDuration);
  
  boolean isShorterThan(ReadableDuration paramReadableDuration);
  
  boolean equals(Object paramObject);
  
  int hashCode();
  
  String toString();
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/ReadableDuration.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */