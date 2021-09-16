package org.jfree.chart.plot;

import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;

public interface DrawingSupplier {
  Paint getNextPaint();
  
  Paint getNextOutlinePaint();
  
  Paint getNextFillPaint();
  
  Stroke getNextStroke();
  
  Stroke getNextOutlineStroke();
  
  Shape getNextShape();
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/plot/DrawingSupplier.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */