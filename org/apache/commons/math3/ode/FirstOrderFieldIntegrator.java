package org.apache.commons.math3.ode;

import java.util.Collection;
import org.apache.commons.math3.analysis.solvers.BracketedRealFieldUnivariateSolver;
import org.apache.commons.math3.exception.MaxCountExceededException;
import org.apache.commons.math3.exception.NoBracketingException;
import org.apache.commons.math3.exception.NumberIsTooSmallException;
import org.apache.commons.math3.ode.events.FieldEventHandler;
import org.apache.commons.math3.ode.sampling.FieldStepHandler;

public interface FirstOrderFieldIntegrator<T extends org.apache.commons.math3.RealFieldElement<T>> {
  String getName();
  
  void addStepHandler(FieldStepHandler<T> paramFieldStepHandler);
  
  Collection<FieldStepHandler<T>> getStepHandlers();
  
  void clearStepHandlers();
  
  void addEventHandler(FieldEventHandler<T> paramFieldEventHandler, double paramDouble1, double paramDouble2, int paramInt);
  
  void addEventHandler(FieldEventHandler<T> paramFieldEventHandler, double paramDouble1, double paramDouble2, int paramInt, BracketedRealFieldUnivariateSolver<T> paramBracketedRealFieldUnivariateSolver);
  
  Collection<FieldEventHandler<T>> getEventHandlers();
  
  void clearEventHandlers();
  
  FieldODEStateAndDerivative<T> getCurrentStepStart();
  
  T getCurrentSignedStepsize();
  
  void setMaxEvaluations(int paramInt);
  
  int getMaxEvaluations();
  
  int getEvaluations();
  
  FieldODEStateAndDerivative<T> integrate(FieldExpandableODE<T> paramFieldExpandableODE, FieldODEState<T> paramFieldODEState, T paramT) throws NumberIsTooSmallException, MaxCountExceededException, NoBracketingException;
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ode/FirstOrderFieldIntegrator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */