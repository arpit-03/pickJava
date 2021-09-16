package org.apache.commons.math3.optim;

public interface ConvergenceChecker<PAIR> {
  boolean converged(int paramInt, PAIR paramPAIR1, PAIR paramPAIR2);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optim/ConvergenceChecker.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */