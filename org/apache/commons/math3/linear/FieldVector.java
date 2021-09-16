package org.apache.commons.math3.linear;

import org.apache.commons.math3.Field;
import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.MathArithmeticException;
import org.apache.commons.math3.exception.NotPositiveException;
import org.apache.commons.math3.exception.NullArgumentException;
import org.apache.commons.math3.exception.OutOfRangeException;

public interface FieldVector<T extends org.apache.commons.math3.FieldElement<T>> {
  Field<T> getField();
  
  FieldVector<T> copy();
  
  FieldVector<T> add(FieldVector<T> paramFieldVector) throws DimensionMismatchException;
  
  FieldVector<T> subtract(FieldVector<T> paramFieldVector) throws DimensionMismatchException;
  
  FieldVector<T> mapAdd(T paramT) throws NullArgumentException;
  
  FieldVector<T> mapAddToSelf(T paramT) throws NullArgumentException;
  
  FieldVector<T> mapSubtract(T paramT) throws NullArgumentException;
  
  FieldVector<T> mapSubtractToSelf(T paramT) throws NullArgumentException;
  
  FieldVector<T> mapMultiply(T paramT) throws NullArgumentException;
  
  FieldVector<T> mapMultiplyToSelf(T paramT) throws NullArgumentException;
  
  FieldVector<T> mapDivide(T paramT) throws NullArgumentException, MathArithmeticException;
  
  FieldVector<T> mapDivideToSelf(T paramT) throws NullArgumentException, MathArithmeticException;
  
  FieldVector<T> mapInv() throws MathArithmeticException;
  
  FieldVector<T> mapInvToSelf() throws MathArithmeticException;
  
  FieldVector<T> ebeMultiply(FieldVector<T> paramFieldVector) throws DimensionMismatchException;
  
  FieldVector<T> ebeDivide(FieldVector<T> paramFieldVector) throws DimensionMismatchException, MathArithmeticException;
  
  @Deprecated
  T[] getData();
  
  T dotProduct(FieldVector<T> paramFieldVector) throws DimensionMismatchException;
  
  FieldVector<T> projection(FieldVector<T> paramFieldVector) throws DimensionMismatchException, MathArithmeticException;
  
  FieldMatrix<T> outerProduct(FieldVector<T> paramFieldVector);
  
  T getEntry(int paramInt) throws OutOfRangeException;
  
  void setEntry(int paramInt, T paramT) throws OutOfRangeException;
  
  int getDimension();
  
  FieldVector<T> append(FieldVector<T> paramFieldVector);
  
  FieldVector<T> append(T paramT);
  
  FieldVector<T> getSubVector(int paramInt1, int paramInt2) throws OutOfRangeException, NotPositiveException;
  
  void setSubVector(int paramInt, FieldVector<T> paramFieldVector) throws OutOfRangeException;
  
  void set(T paramT);
  
  T[] toArray();
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/linear/FieldVector.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */