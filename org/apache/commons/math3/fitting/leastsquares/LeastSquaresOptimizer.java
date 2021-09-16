package org.apache.commons.math3.fitting.leastsquares;

public interface LeastSquaresOptimizer {
  Optimum optimize(LeastSquaresProblem paramLeastSquaresProblem);
  
  public static interface Optimum extends LeastSquaresProblem.Evaluation {
    int getEvaluations();
    
    int getIterations();
  }
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/fitting/leastsquares/LeastSquaresOptimizer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */