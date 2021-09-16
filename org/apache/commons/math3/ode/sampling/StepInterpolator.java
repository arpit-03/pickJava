package org.apache.commons.math3.ode.sampling;

import java.io.Externalizable;
import org.apache.commons.math3.exception.MaxCountExceededException;

public interface StepInterpolator extends Externalizable {
  double getPreviousTime();
  
  double getCurrentTime();
  
  double getInterpolatedTime();
  
  void setInterpolatedTime(double paramDouble);
  
  double[] getInterpolatedState() throws MaxCountExceededException;
  
  double[] getInterpolatedDerivatives() throws MaxCountExceededException;
  
  double[] getInterpolatedSecondaryState(int paramInt) throws MaxCountExceededException;
  
  double[] getInterpolatedSecondaryDerivatives(int paramInt) throws MaxCountExceededException;
  
  boolean isForward();
  
  StepInterpolator copy() throws MaxCountExceededException;
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ode/sampling/StepInterpolator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */