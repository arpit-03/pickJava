package org.apache.commons.math3.ode.sampling;

import org.apache.commons.math3.ode.FieldODEStateAndDerivative;

public interface FieldStepInterpolator<T extends org.apache.commons.math3.RealFieldElement<T>> {
  FieldODEStateAndDerivative<T> getPreviousState();
  
  FieldODEStateAndDerivative<T> getCurrentState();
  
  FieldODEStateAndDerivative<T> getInterpolatedState(T paramT);
  
  boolean isForward();
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ode/sampling/FieldStepInterpolator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */