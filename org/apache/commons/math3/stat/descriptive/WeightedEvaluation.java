package org.apache.commons.math3.stat.descriptive;

import org.apache.commons.math3.exception.MathIllegalArgumentException;

public interface WeightedEvaluation {
  double evaluate(double[] paramArrayOfdouble1, double[] paramArrayOfdouble2) throws MathIllegalArgumentException;
  
  double evaluate(double[] paramArrayOfdouble1, double[] paramArrayOfdouble2, int paramInt1, int paramInt2) throws MathIllegalArgumentException;
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/stat/descriptive/WeightedEvaluation.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */