package org.jfree.chart.plot;

import java.awt.geom.Point2D;

public interface Pannable {
  PlotOrientation getOrientation();
  
  boolean isDomainPannable();
  
  boolean isRangePannable();
  
  void panDomainAxes(double paramDouble, PlotRenderingInfo paramPlotRenderingInfo, Point2D paramPoint2D);
  
  void panRangeAxes(double paramDouble, PlotRenderingInfo paramPlotRenderingInfo, Point2D paramPoint2D);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/plot/Pannable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */