package org.apache.commons.math3.analysis.interpolation;

import org.apache.commons.math3.analysis.BivariateFunction;
import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.NoDataException;
import org.apache.commons.math3.exception.NonMonotonicSequenceException;
import org.apache.commons.math3.exception.NumberIsTooSmallException;

public interface BivariateGridInterpolator {
  BivariateFunction interpolate(double[] paramArrayOfdouble1, double[] paramArrayOfdouble2, double[][] paramArrayOfdouble) throws NoDataException, DimensionMismatchException, NonMonotonicSequenceException, NumberIsTooSmallException;
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/analysis/interpolation/BivariateGridInterpolator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */