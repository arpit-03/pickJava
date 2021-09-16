package us.hebi.matlab.mat.format;

import java.io.Closeable;
import java.io.IOException;
import us.hebi.matlab.mat.types.Sink;

interface NumberStore extends Closeable {
  int getNumElements();
  
  long getLong(int paramInt);
  
  void setLong(int paramInt, long paramLong);
  
  double getDouble(int paramInt);
  
  void setDouble(int paramInt, double paramDouble);
  
  int getMat5Size();
  
  void writeMat5(Sink paramSink) throws IOException;
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/us/hebi/matlab/mat/format/NumberStore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */