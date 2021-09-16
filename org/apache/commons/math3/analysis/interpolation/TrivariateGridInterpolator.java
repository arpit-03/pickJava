package org.apache.commons.math3.analysis.interpolation;

import org.apache.commons.math3.analysis.TrivariateFunction;
import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.NoDataException;
import org.apache.commons.math3.exception.NonMonotonicSequenceException;
import org.apache.commons.math3.exception.NumberIsTooSmallException;

public interface TrivariateGridInterpolator {
  TrivariateFunction interpolate(double[] paramArrayOfdouble1, double[] paramArrayOfdouble2, double[] paramArrayOfdouble3, double[][][] paramArrayOfdouble) throws NoDataException, NumberIsTooSmallException, DimensionMismatchException, NonMonotonicSequenceException;
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/analysis/interpolation/TrivariateGridInterpolator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */