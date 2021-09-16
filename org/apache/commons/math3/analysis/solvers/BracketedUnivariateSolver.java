package org.apache.commons.math3.analysis.solvers;

public interface BracketedUnivariateSolver<FUNC extends org.apache.commons.math3.analysis.UnivariateFunction> extends BaseUnivariateSolver<FUNC> {
  double solve(int paramInt, FUNC paramFUNC, double paramDouble1, double paramDouble2, AllowedSolution paramAllowedSolution);
  
  double solve(int paramInt, FUNC paramFUNC, double paramDouble1, double paramDouble2, double paramDouble3, AllowedSolution paramAllowedSolution);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/analysis/solvers/BracketedUnivariateSolver.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */