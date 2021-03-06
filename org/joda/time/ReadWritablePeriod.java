package org.joda.time;

public interface ReadWritablePeriod extends ReadablePeriod {
  void clear();
  
  void setValue(int paramInt1, int paramInt2);
  
  void set(DurationFieldType paramDurationFieldType, int paramInt);
  
  void setPeriod(ReadablePeriod paramReadablePeriod);
  
  void setPeriod(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8);
  
  void setPeriod(ReadableInterval paramReadableInterval);
  
  void add(DurationFieldType paramDurationFieldType, int paramInt);
  
  void add(ReadablePeriod paramReadablePeriod);
  
  void add(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8);
  
  void add(ReadableInterval paramReadableInterval);
  
  void setYears(int paramInt);
  
  void addYears(int paramInt);
  
  void setMonths(int paramInt);
  
  void addMonths(int paramInt);
  
  void setWeeks(int paramInt);
  
  void addWeeks(int paramInt);
  
  void setDays(int paramInt);
  
  void addDays(int paramInt);
  
  void setHours(int paramInt);
  
  void addHours(int paramInt);
  
  void setMinutes(int paramInt);
  
  void addMinutes(int paramInt);
  
  void setSeconds(int paramInt);
  
  void addSeconds(int paramInt);
  
  void setMillis(int paramInt);
  
  void addMillis(int paramInt);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/ReadWritablePeriod.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */