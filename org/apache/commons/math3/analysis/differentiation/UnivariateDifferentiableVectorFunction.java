package org.apache.commons.math3.analysis.differentiation;

import org.apache.commons.math3.analysis.UnivariateVectorFunction;
import org.apache.commons.math3.exception.MathIllegalArgumentException;

public interface UnivariateDifferentiableVectorFunction extends UnivariateVectorFunction {
  DerivativeStructure[] value(DerivativeStructure paramDerivativeStructure) throws MathIllegalArgumentException;
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/analysis/differentiation/UnivariateDifferentiableVectorFunction.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */