package org.apache.commons.math3.stat.descriptive;

import org.apache.commons.math3.exception.MathIllegalArgumentException;

public interface StorelessUnivariateStatistic extends UnivariateStatistic {
  void increment(double paramDouble);
  
  void incrementAll(double[] paramArrayOfdouble) throws MathIllegalArgumentException;
  
  void incrementAll(double[] paramArrayOfdouble, int paramInt1, int paramInt2) throws MathIllegalArgumentException;
  
  double getResult();
  
  long getN();
  
  void clear();
  
  StorelessUnivariateStatistic copy();
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/stat/descriptive/StorelessUnivariateStatistic.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */