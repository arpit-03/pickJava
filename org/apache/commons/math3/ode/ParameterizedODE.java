package org.apache.commons.math3.ode;

public interface ParameterizedODE extends Parameterizable {
  double getParameter(String paramString) throws UnknownParameterException;
  
  void setParameter(String paramString, double paramDouble) throws UnknownParameterException;
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ode/ParameterizedODE.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */