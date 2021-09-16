package org.jfree.data.statistics;

import java.util.List;
import org.jfree.data.xy.XYDataset;

public interface BoxAndWhiskerXYDataset extends XYDataset {
  Number getMeanValue(int paramInt1, int paramInt2);
  
  Number getMedianValue(int paramInt1, int paramInt2);
  
  Number getQ1Value(int paramInt1, int paramInt2);
  
  Number getQ3Value(int paramInt1, int paramInt2);
  
  Number getMinRegularValue(int paramInt1, int paramInt2);
  
  Number getMaxRegularValue(int paramInt1, int paramInt2);
  
  Number getMinOutlier(int paramInt1, int paramInt2);
  
  Number getMaxOutlier(int paramInt1, int paramInt2);
  
  List getOutliers(int paramInt1, int paramInt2);
  
  double getOutlierCoefficient();
  
  double getFaroutCoefficient();
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/statistics/BoxAndWhiskerXYDataset.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */