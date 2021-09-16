package org.apache.commons.math3.analysis.solvers;

import org.apache.commons.math3.analysis.RealFieldUnivariateFunction;

public interface BracketedRealFieldUnivariateSolver<T extends org.apache.commons.math3.RealFieldElement<T>> {
  int getMaxEvaluations();
  
  int getEvaluations();
  
  T getAbsoluteAccuracy();
  
  T getRelativeAccuracy();
  
  T getFunctionValueAccuracy();
  
  T solve(int paramInt, RealFieldUnivariateFunction<T> paramRealFieldUnivariateFunction, T paramT1, T paramT2, AllowedSolution paramAllowedSolution);
  
  T solve(int paramInt, RealFieldUnivariateFunction<T> paramRealFieldUnivariateFunction, T paramT1, T paramT2, T paramT3, AllowedSolution paramAllowedSolution);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/analysis/solvers/BracketedRealFieldUnivariateSolver.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */