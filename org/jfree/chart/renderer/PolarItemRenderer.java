package org.jfree.chart.renderer;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.List;
import org.jfree.chart.LegendItem;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.event.RendererChangeListener;
import org.jfree.chart.labels.XYToolTipGenerator;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.PolarPlot;
import org.jfree.chart.urls.XYURLGenerator;
import org.jfree.data.xy.XYDataset;

public interface PolarItemRenderer {
  void drawSeries(Graphics2D paramGraphics2D, Rectangle2D paramRectangle2D, PlotRenderingInfo paramPlotRenderingInfo, PolarPlot paramPolarPlot, XYDataset paramXYDataset, int paramInt);
  
  void drawAngularGridLines(Graphics2D paramGraphics2D, PolarPlot paramPolarPlot, List paramList, Rectangle2D paramRectangle2D);
  
  void drawRadialGridLines(Graphics2D paramGraphics2D, PolarPlot paramPolarPlot, ValueAxis paramValueAxis, List paramList, Rectangle2D paramRectangle2D);
  
  LegendItem getLegendItem(int paramInt);
  
  PolarPlot getPlot();
  
  void setPlot(PolarPlot paramPolarPlot);
  
  void addChangeListener(RendererChangeListener paramRendererChangeListener);
  
  void removeChangeListener(RendererChangeListener paramRendererChangeListener);
  
  XYToolTipGenerator getToolTipGenerator(int paramInt1, int paramInt2);
  
  XYToolTipGenerator getSeriesToolTipGenerator(int paramInt);
  
  void setSeriesToolTipGenerator(int paramInt, XYToolTipGenerator paramXYToolTipGenerator);
  
  XYToolTipGenerator getBaseToolTipGenerator();
  
  void setBaseToolTipGenerator(XYToolTipGenerator paramXYToolTipGenerator);
  
  XYURLGenerator getURLGenerator();
  
  void setURLGenerator(XYURLGenerator paramXYURLGenerator);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/renderer/PolarItemRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */