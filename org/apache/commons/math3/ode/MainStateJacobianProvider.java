package org.apache.commons.math3.ode;

import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.MaxCountExceededException;

public interface MainStateJacobianProvider extends FirstOrderDifferentialEquations {
  void computeMainStateJacobian(double paramDouble, double[] paramArrayOfdouble1, double[] paramArrayOfdouble2, double[][] paramArrayOfdouble) throws MaxCountExceededException, DimensionMismatchException;
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ode/MainStateJacobianProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */