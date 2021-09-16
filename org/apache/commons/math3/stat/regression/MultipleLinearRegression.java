package org.apache.commons.math3.stat.regression;

public interface MultipleLinearRegression {
  double[] estimateRegressionParameters();
  
  double[][] estimateRegressionParametersVariance();
  
  double[] estimateResiduals();
  
  double estimateRegressandVariance();
  
  double[] estimateRegressionParametersStandardErrors();
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/stat/regression/MultipleLinearRegression.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */