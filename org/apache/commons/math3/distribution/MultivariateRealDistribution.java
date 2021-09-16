package org.apache.commons.math3.distribution;

import org.apache.commons.math3.exception.NotStrictlyPositiveException;

public interface MultivariateRealDistribution {
  double density(double[] paramArrayOfdouble);
  
  void reseedRandomGenerator(long paramLong);
  
  int getDimension();
  
  double[] sample();
  
  double[][] sample(int paramInt) throws NotStrictlyPositiveException;
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/distribution/MultivariateRealDistribution.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */