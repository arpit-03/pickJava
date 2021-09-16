package org.apache.commons.math3.analysis.differentiation;

import org.apache.commons.math3.analysis.MultivariateFunction;
import org.apache.commons.math3.exception.MathIllegalArgumentException;

public interface MultivariateDifferentiableFunction extends MultivariateFunction {
  DerivativeStructure value(DerivativeStructure[] paramArrayOfDerivativeStructure) throws MathIllegalArgumentException;
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/analysis/differentiation/MultivariateDifferentiableFunction.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */