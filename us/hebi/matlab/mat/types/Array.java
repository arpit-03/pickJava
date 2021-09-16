package us.hebi.matlab.mat.types;

import java.io.Closeable;

public interface Array extends Closeable {
  MatlabType getType();
  
  int[] getDimensions();
  
  int getNumDimensions();
  
  int getNumRows();
  
  int getNumCols();
  
  int getNumElements();
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/us/hebi/matlab/mat/types/Array.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */