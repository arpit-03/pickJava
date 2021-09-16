package org.jfree.chart.plot.dial;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.EventListener;

public interface DialLayer {
  boolean isVisible();
  
  void addChangeListener(DialLayerChangeListener paramDialLayerChangeListener);
  
  void removeChangeListener(DialLayerChangeListener paramDialLayerChangeListener);
  
  boolean hasListener(EventListener paramEventListener);
  
  boolean isClippedToWindow();
  
  void draw(Graphics2D paramGraphics2D, DialPlot paramDialPlot, Rectangle2D paramRectangle2D1, Rectangle2D paramRectangle2D2);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/plot/dial/DialLayer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */