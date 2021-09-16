package org.apache.commons.math3.ml.distance;

import java.io.Serializable;
import org.apache.commons.math3.exception.DimensionMismatchException;

public interface DistanceMeasure extends Serializable {
  double compute(double[] paramArrayOfdouble1, double[] paramArrayOfdouble2) throws DimensionMismatchException;
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ml/distance/DistanceMeasure.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */