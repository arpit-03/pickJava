package org.apache.commons.math3.transform;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.exception.NonMonotonicSequenceException;
import org.apache.commons.math3.exception.NotStrictlyPositiveException;

public interface RealTransformer {
  double[] transform(double[] paramArrayOfdouble, TransformType paramTransformType) throws MathIllegalArgumentException;
  
  double[] transform(UnivariateFunction paramUnivariateFunction, double paramDouble1, double paramDouble2, int paramInt, TransformType paramTransformType) throws NonMonotonicSequenceException, NotStrictlyPositiveException, MathIllegalArgumentException;
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/transform/RealTransformer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */