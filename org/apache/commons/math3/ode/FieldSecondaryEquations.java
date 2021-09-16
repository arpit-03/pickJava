package org.apache.commons.math3.ode;

import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.MaxCountExceededException;

public interface FieldSecondaryEquations<T extends org.apache.commons.math3.RealFieldElement<T>> {
  int getDimension();
  
  void init(T paramT1, T[] paramArrayOfT1, T[] paramArrayOfT2, T paramT2);
  
  T[] computeDerivatives(T paramT, T[] paramArrayOfT1, T[] paramArrayOfT2, T[] paramArrayOfT3) throws MaxCountExceededException, DimensionMismatchException;
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ode/FieldSecondaryEquations.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */