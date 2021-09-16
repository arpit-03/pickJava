package org.apache.commons.math3.analysis.differentiation;

import org.apache.commons.math3.analysis.MultivariateVectorFunction;
import org.apache.commons.math3.exception.MathIllegalArgumentException;

public interface MultivariateDifferentiableVectorFunction extends MultivariateVectorFunction {
  DerivativeStructure[] value(DerivativeStructure[] paramArrayOfDerivativeStructure) throws MathIllegalArgumentException;
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/analysis/differentiation/MultivariateDifferentiableVectorFunction.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */