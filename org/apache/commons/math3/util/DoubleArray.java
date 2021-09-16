package org.apache.commons.math3.util;

public interface DoubleArray {
  int getNumElements();
  
  double getElement(int paramInt);
  
  void setElement(int paramInt, double paramDouble);
  
  void addElement(double paramDouble);
  
  void addElements(double[] paramArrayOfdouble);
  
  double addElementRolling(double paramDouble);
  
  double[] getElements();
  
  void clear();
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/util/DoubleArray.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */