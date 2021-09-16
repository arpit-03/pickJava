package org.jfree.data.xy;

public interface IntervalXYDataset extends XYDataset {
  Number getStartX(int paramInt1, int paramInt2);
  
  double getStartXValue(int paramInt1, int paramInt2);
  
  Number getEndX(int paramInt1, int paramInt2);
  
  double getEndXValue(int paramInt1, int paramInt2);
  
  Number getStartY(int paramInt1, int paramInt2);
  
  double getStartYValue(int paramInt1, int paramInt2);
  
  Number getEndY(int paramInt1, int paramInt2);
  
  double getEndYValue(int paramInt1, int paramInt2);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/xy/IntervalXYDataset.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */