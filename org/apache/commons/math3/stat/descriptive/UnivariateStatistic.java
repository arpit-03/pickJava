package org.apache.commons.math3.stat.descriptive;

import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.util.MathArrays;

public interface UnivariateStatistic extends MathArrays.Function {
  double evaluate(double[] paramArrayOfdouble) throws MathIllegalArgumentException;
  
  double evaluate(double[] paramArrayOfdouble, int paramInt1, int paramInt2) throws MathIllegalArgumentException;
  
  UnivariateStatistic copy();
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/stat/descriptive/UnivariateStatistic.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */