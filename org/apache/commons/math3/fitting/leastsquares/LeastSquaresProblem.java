package org.apache.commons.math3.fitting.leastsquares;

import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.apache.commons.math3.optim.OptimizationProblem;

public interface LeastSquaresProblem extends OptimizationProblem<LeastSquaresProblem.Evaluation> {
  RealVector getStart();
  
  int getObservationSize();
  
  int getParameterSize();
  
  Evaluation evaluate(RealVector paramRealVector);
  
  public static interface Evaluation {
    RealMatrix getCovariances(double param1Double);
    
    RealVector getSigma(double param1Double);
    
    double getRMS();
    
    RealMatrix getJacobian();
    
    double getCost();
    
    RealVector getResiduals();
    
    RealVector getPoint();
  }
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/fitting/leastsquares/LeastSquaresProblem.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */