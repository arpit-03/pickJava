package org.apache.commons.math3.ode.sampling;

import org.apache.commons.math3.exception.MaxCountExceededException;
import org.apache.commons.math3.ode.FieldODEStateAndDerivative;

public interface FieldStepHandler<T extends org.apache.commons.math3.RealFieldElement<T>> {
  void init(FieldODEStateAndDerivative<T> paramFieldODEStateAndDerivative, T paramT);
  
  void handleStep(FieldStepInterpolator<T> paramFieldStepInterpolator, boolean paramBoolean) throws MaxCountExceededException;
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ode/sampling/FieldStepHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */