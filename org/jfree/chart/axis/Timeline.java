package org.jfree.chart.axis;

import java.util.Date;

public interface Timeline {
  long toTimelineValue(long paramLong);
  
  long toTimelineValue(Date paramDate);
  
  long toMillisecond(long paramLong);
  
  boolean containsDomainValue(long paramLong);
  
  boolean containsDomainValue(Date paramDate);
  
  boolean containsDomainRange(long paramLong1, long paramLong2);
  
  boolean containsDomainRange(Date paramDate1, Date paramDate2);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/axis/Timeline.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */