package org.jfree.chart.annotations;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.XYPlot;

public interface XYAnnotation extends Annotation {
  void draw(Graphics2D paramGraphics2D, XYPlot paramXYPlot, Rectangle2D paramRectangle2D, ValueAxis paramValueAxis1, ValueAxis paramValueAxis2, int paramInt, PlotRenderingInfo paramPlotRenderingInfo);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/annotations/XYAnnotation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */