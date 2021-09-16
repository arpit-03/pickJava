package org.jfree.data.xy;

public interface OHLCDataset extends XYDataset {
  Number getHigh(int paramInt1, int paramInt2);
  
  double getHighValue(int paramInt1, int paramInt2);
  
  Number getLow(int paramInt1, int paramInt2);
  
  double getLowValue(int paramInt1, int paramInt2);
  
  Number getOpen(int paramInt1, int paramInt2);
  
  double getOpenValue(int paramInt1, int paramInt2);
  
  Number getClose(int paramInt1, int paramInt2);
  
  double getCloseValue(int paramInt1, int paramInt2);
  
  Number getVolume(int paramInt1, int paramInt2);
  
  double getVolumeValue(int paramInt1, int paramInt2);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/xy/OHLCDataset.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */