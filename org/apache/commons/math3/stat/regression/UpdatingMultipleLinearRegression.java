package org.apache.commons.math3.stat.regression;

import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.exception.NoDataException;

public interface UpdatingMultipleLinearRegression {
  boolean hasIntercept();
  
  long getN();
  
  void addObservation(double[] paramArrayOfdouble, double paramDouble) throws ModelSpecificationException;
  
  void addObservations(double[][] paramArrayOfdouble, double[] paramArrayOfdouble1) throws ModelSpecificationException;
  
  void clear();
  
  RegressionResults regress() throws ModelSpecificationException, NoDataException;
  
  RegressionResults regress(int[] paramArrayOfint) throws ModelSpecificationException, MathIllegalArgumentException;
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/stat/regression/UpdatingMultipleLinearRegression.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */