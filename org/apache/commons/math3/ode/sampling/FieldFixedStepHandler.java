package org.apache.commons.math3.ode.sampling;

import org.apache.commons.math3.ode.FieldODEStateAndDerivative;

public interface FieldFixedStepHandler<T extends org.apache.commons.math3.RealFieldElement<T>> {
  void init(FieldODEStateAndDerivative<T> paramFieldODEStateAndDerivative, T paramT);
  
  void handleStep(FieldODEStateAndDerivative<T> paramFieldODEStateAndDerivative, boolean paramBoolean);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ode/sampling/FieldFixedStepHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */