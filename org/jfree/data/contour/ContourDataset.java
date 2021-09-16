package org.jfree.data.contour;

import org.jfree.data.Range;
import org.jfree.data.xy.XYZDataset;

public interface ContourDataset extends XYZDataset {
  double getMinZValue();
  
  double getMaxZValue();
  
  Number[] getXValues();
  
  Number[] getYValues();
  
  Number[] getZValues();
  
  int[] indexX();
  
  int[] getXIndices();
  
  Range getZValueRange(Range paramRange1, Range paramRange2);
  
  boolean isDateAxis(int paramInt);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/contour/ContourDataset.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */