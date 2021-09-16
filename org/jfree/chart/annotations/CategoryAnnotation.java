package org.jfree.chart.annotations;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;

public interface CategoryAnnotation extends Annotation {
  void draw(Graphics2D paramGraphics2D, CategoryPlot paramCategoryPlot, Rectangle2D paramRectangle2D, CategoryAxis paramCategoryAxis, ValueAxis paramValueAxis);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/annotations/CategoryAnnotation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */