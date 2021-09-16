package org.jfree.chart.axis;

public interface TickUnitSource {
  TickUnit getLargerTickUnit(TickUnit paramTickUnit);
  
  TickUnit getCeilingTickUnit(TickUnit paramTickUnit);
  
  TickUnit getCeilingTickUnit(double paramDouble);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/axis/TickUnitSource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */