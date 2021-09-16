package org.apache.commons.math3.analysis.differentiation;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.exception.DimensionMismatchException;

public interface UnivariateDifferentiableFunction extends UnivariateFunction {
  DerivativeStructure value(DerivativeStructure paramDerivativeStructure) throws DimensionMismatchException;
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/analysis/differentiation/UnivariateDifferentiableFunction.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */