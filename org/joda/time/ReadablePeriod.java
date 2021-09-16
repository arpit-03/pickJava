package org.joda.time;

public interface ReadablePeriod {
  PeriodType getPeriodType();
  
  int size();
  
  DurationFieldType getFieldType(int paramInt);
  
  int getValue(int paramInt);
  
  int get(DurationFieldType paramDurationFieldType);
  
  boolean isSupported(DurationFieldType paramDurationFieldType);
  
  Period toPeriod();
  
  MutablePeriod toMutablePeriod();
  
  boolean equals(Object paramObject);
  
  int hashCode();
  
  String toString();
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/ReadablePeriod.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */