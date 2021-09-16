package org.apache.commons.math3.optimization;

@Deprecated
public interface BaseMultivariateVectorOptimizer<FUNC extends org.apache.commons.math3.analysis.MultivariateVectorFunction> extends BaseOptimizer<PointVectorValuePair> {
  @Deprecated
  PointVectorValuePair optimize(int paramInt, FUNC paramFUNC, double[] paramArrayOfdouble1, double[] paramArrayOfdouble2, double[] paramArrayOfdouble3);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optimization/BaseMultivariateVectorOptimizer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */