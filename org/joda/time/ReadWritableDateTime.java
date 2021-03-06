package org.joda.time;

public interface ReadWritableDateTime extends ReadableDateTime, ReadWritableInstant {
  void setYear(int paramInt);
  
  void addYears(int paramInt);
  
  void setWeekyear(int paramInt);
  
  void addWeekyears(int paramInt);
  
  void setMonthOfYear(int paramInt);
  
  void addMonths(int paramInt);
  
  void setWeekOfWeekyear(int paramInt);
  
  void addWeeks(int paramInt);
  
  void setDayOfYear(int paramInt);
  
  void setDayOfMonth(int paramInt);
  
  void setDayOfWeek(int paramInt);
  
  void addDays(int paramInt);
  
  void setHourOfDay(int paramInt);
  
  void addHours(int paramInt);
  
  void setMinuteOfDay(int paramInt);
  
  void setMinuteOfHour(int paramInt);
  
  void addMinutes(int paramInt);
  
  void setSecondOfDay(int paramInt);
  
  void setSecondOfMinute(int paramInt);
  
  void addSeconds(int paramInt);
  
  void setMillisOfDay(int paramInt);
  
  void setMillisOfSecond(int paramInt);
  
  void addMillis(int paramInt);
  
  void setDate(int paramInt1, int paramInt2, int paramInt3);
  
  void setTime(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
  
  void setDateTime(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/ReadWritableDateTime.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */