package org.apache.commons.math3.ode.sampling;

public interface FixedStepHandler {
  void init(double paramDouble1, double[] paramArrayOfdouble, double paramDouble2);
  
  void handleStep(double paramDouble, double[] paramArrayOfdouble1, double[] paramArrayOfdouble2, boolean paramBoolean);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ode/sampling/FixedStepHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */