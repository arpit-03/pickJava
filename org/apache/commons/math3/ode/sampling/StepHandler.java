package org.apache.commons.math3.ode.sampling;

import org.apache.commons.math3.exception.MaxCountExceededException;

public interface StepHandler {
  void init(double paramDouble1, double[] paramArrayOfdouble, double paramDouble2);
  
  void handleStep(StepInterpolator paramStepInterpolator, boolean paramBoolean) throws MaxCountExceededException;
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ode/sampling/StepHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */