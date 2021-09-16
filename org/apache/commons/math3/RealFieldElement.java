package org.apache.commons.math3;

import org.apache.commons.math3.exception.DimensionMismatchException;

public interface RealFieldElement<T> extends FieldElement<T> {
  double getReal();
  
  T add(double paramDouble);
  
  T subtract(double paramDouble);
  
  T multiply(double paramDouble);
  
  T divide(double paramDouble);
  
  T remainder(double paramDouble);
  
  T remainder(T paramT) throws DimensionMismatchException;
  
  T abs();
  
  T ceil();
  
  T floor();
  
  T rint();
  
  long round();
  
  T signum();
  
  T copySign(T paramT);
  
  T copySign(double paramDouble);
  
  T scalb(int paramInt);
  
  T hypot(T paramT) throws DimensionMismatchException;
  
  T reciprocal();
  
  T sqrt();
  
  T cbrt();
  
  T rootN(int paramInt);
  
  T pow(double paramDouble);
  
  T pow(int paramInt);
  
  T pow(T paramT) throws DimensionMismatchException;
  
  T exp();
  
  T expm1();
  
  T log();
  
  T log1p();
  
  T cos();
  
  T sin();
  
  T tan();
  
  T acos();
  
  T asin();
  
  T atan();
  
  T atan2(T paramT) throws DimensionMismatchException;
  
  T cosh();
  
  T sinh();
  
  T tanh();
  
  T acosh();
  
  T asinh();
  
  T atanh();
  
  T linearCombination(T[] paramArrayOfT1, T[] paramArrayOfT2) throws DimensionMismatchException;
  
  T linearCombination(double[] paramArrayOfdouble, T[] paramArrayOfT) throws DimensionMismatchException;
  
  T linearCombination(T paramT1, T paramT2, T paramT3, T paramT4);
  
  T linearCombination(double paramDouble1, T paramT1, double paramDouble2, T paramT2);
  
  T linearCombination(T paramT1, T paramT2, T paramT3, T paramT4, T paramT5, T paramT6);
  
  T linearCombination(double paramDouble1, T paramT1, double paramDouble2, T paramT2, double paramDouble3, T paramT3);
  
  T linearCombination(T paramT1, T paramT2, T paramT3, T paramT4, T paramT5, T paramT6, T paramT7, T paramT8);
  
  T linearCombination(double paramDouble1, T paramT1, double paramDouble2, T paramT2, double paramDouble3, T paramT3, double paramDouble4, T paramT4);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/RealFieldElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */