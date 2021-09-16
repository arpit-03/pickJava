package org.apache.commons.math3.ode;

import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.MaxCountExceededException;

public interface FirstOrderDifferentialEquations {
  int getDimension();
  
  void computeDerivatives(double paramDouble, double[] paramArrayOfdouble1, double[] paramArrayOfdouble2) throws MaxCountExceededException, DimensionMismatchException;
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ode/FirstOrderDifferentialEquations.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */