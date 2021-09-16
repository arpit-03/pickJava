package org.apache.commons.math3.fitting.leastsquares;

import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

public interface ValueAndJacobianFunction extends MultivariateJacobianFunction {
  RealVector computeValue(double[] paramArrayOfdouble);
  
  RealMatrix computeJacobian(double[] paramArrayOfdouble);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/fitting/leastsquares/ValueAndJacobianFunction.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */