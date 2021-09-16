package org.apache.commons.math3.ode.events;

import org.apache.commons.math3.ode.FieldODEState;
import org.apache.commons.math3.ode.FieldODEStateAndDerivative;

public interface FieldEventHandler<T extends org.apache.commons.math3.RealFieldElement<T>> {
  void init(FieldODEStateAndDerivative<T> paramFieldODEStateAndDerivative, T paramT);
  
  T g(FieldODEStateAndDerivative<T> paramFieldODEStateAndDerivative);
  
  Action eventOccurred(FieldODEStateAndDerivative<T> paramFieldODEStateAndDerivative, boolean paramBoolean);
  
  FieldODEState<T> resetState(FieldODEStateAndDerivative<T> paramFieldODEStateAndDerivative);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ode/events/FieldEventHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */