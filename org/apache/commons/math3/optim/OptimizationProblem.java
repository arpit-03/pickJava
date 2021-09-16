package org.apache.commons.math3.optim;

import org.apache.commons.math3.util.Incrementor;

public interface OptimizationProblem<PAIR> {
  Incrementor getEvaluationCounter();
  
  Incrementor getIterationCounter();
  
  ConvergenceChecker<PAIR> getConvergenceChecker();
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optim/OptimizationProblem.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */