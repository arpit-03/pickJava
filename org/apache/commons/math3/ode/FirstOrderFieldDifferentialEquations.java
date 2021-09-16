package org.apache.commons.math3.ode;

public interface FirstOrderFieldDifferentialEquations<T extends org.apache.commons.math3.RealFieldElement<T>> {
  int getDimension();
  
  void init(T paramT1, T[] paramArrayOfT, T paramT2);
  
  T[] computeDerivatives(T paramT, T[] paramArrayOfT);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ode/FirstOrderFieldDifferentialEquations.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */