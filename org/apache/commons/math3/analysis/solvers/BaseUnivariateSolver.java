package org.apache.commons.math3.analysis.solvers;

import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.exception.TooManyEvaluationsException;

public interface BaseUnivariateSolver<FUNC extends org.apache.commons.math3.analysis.UnivariateFunction> {
  int getMaxEvaluations();
  
  int getEvaluations();
  
  double getAbsoluteAccuracy();
  
  double getRelativeAccuracy();
  
  double getFunctionValueAccuracy();
  
  double solve(int paramInt, FUNC paramFUNC, double paramDouble1, double paramDouble2) throws MathIllegalArgumentException, TooManyEvaluationsException;
  
  double solve(int paramInt, FUNC paramFUNC, double paramDouble1, double paramDouble2, double paramDouble3) throws MathIllegalArgumentException, TooManyEvaluationsException;
  
  double solve(int paramInt, FUNC paramFUNC, double paramDouble);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/analysis/solvers/BaseUnivariateSolver.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */