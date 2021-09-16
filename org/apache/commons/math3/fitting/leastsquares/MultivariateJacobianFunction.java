package org.apache.commons.math3.fitting.leastsquares;

import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.apache.commons.math3.util.Pair;

public interface MultivariateJacobianFunction {
  Pair<RealVector, RealMatrix> value(RealVector paramRealVector);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/fitting/leastsquares/MultivariateJacobianFunction.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */