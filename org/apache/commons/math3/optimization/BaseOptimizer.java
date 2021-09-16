package org.apache.commons.math3.optimization;

@Deprecated
public interface BaseOptimizer<PAIR> {
  int getMaxEvaluations();
  
  int getEvaluations();
  
  ConvergenceChecker<PAIR> getConvergenceChecker();
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optimization/BaseOptimizer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */