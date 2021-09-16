package org.apache.commons.math3.optim.nonlinear.scalar.gradient;

public interface Preconditioner {
  double[] precondition(double[] paramArrayOfdouble1, double[] paramArrayOfdouble2);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optim/nonlinear/scalar/gradient/Preconditioner.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */