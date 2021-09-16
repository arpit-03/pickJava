package org.apache.commons.math3.analysis;

@Deprecated
public interface DifferentiableMultivariateFunction extends MultivariateFunction {
  MultivariateFunction partialDerivative(int paramInt);
  
  MultivariateVectorFunction gradient();
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/analysis/DifferentiableMultivariateFunction.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */