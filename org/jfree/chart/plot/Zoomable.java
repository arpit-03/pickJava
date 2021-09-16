package org.jfree.chart.plot;

import java.awt.geom.Point2D;

public interface Zoomable {
  boolean isDomainZoomable();
  
  boolean isRangeZoomable();
  
  PlotOrientation getOrientation();
  
  void zoomDomainAxes(double paramDouble, PlotRenderingInfo paramPlotRenderingInfo, Point2D paramPoint2D);
  
  void zoomDomainAxes(double paramDouble, PlotRenderingInfo paramPlotRenderingInfo, Point2D paramPoint2D, boolean paramBoolean);
  
  void zoomDomainAxes(double paramDouble1, double paramDouble2, PlotRenderingInfo paramPlotRenderingInfo, Point2D paramPoint2D);
  
  void zoomRangeAxes(double paramDouble, PlotRenderingInfo paramPlotRenderingInfo, Point2D paramPoint2D);
  
  void zoomRangeAxes(double paramDouble, PlotRenderingInfo paramPlotRenderingInfo, Point2D paramPoint2D, boolean paramBoolean);
  
  void zoomRangeAxes(double paramDouble1, double paramDouble2, PlotRenderingInfo paramPlotRenderingInfo, Point2D paramPoint2D);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/plot/Zoomable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */