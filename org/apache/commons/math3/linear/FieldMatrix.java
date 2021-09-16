package org.apache.commons.math3.linear;

import org.apache.commons.math3.Field;
import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.NoDataException;
import org.apache.commons.math3.exception.NotPositiveException;
import org.apache.commons.math3.exception.NotStrictlyPositiveException;
import org.apache.commons.math3.exception.NullArgumentException;
import org.apache.commons.math3.exception.NumberIsTooSmallException;
import org.apache.commons.math3.exception.OutOfRangeException;

public interface FieldMatrix<T extends org.apache.commons.math3.FieldElement<T>> extends AnyMatrix {
  Field<T> getField();
  
  FieldMatrix<T> createMatrix(int paramInt1, int paramInt2) throws NotStrictlyPositiveException;
  
  FieldMatrix<T> copy();
  
  FieldMatrix<T> add(FieldMatrix<T> paramFieldMatrix) throws MatrixDimensionMismatchException;
  
  FieldMatrix<T> subtract(FieldMatrix<T> paramFieldMatrix) throws MatrixDimensionMismatchException;
  
  FieldMatrix<T> scalarAdd(T paramT);
  
  FieldMatrix<T> scalarMultiply(T paramT);
  
  FieldMatrix<T> multiply(FieldMatrix<T> paramFieldMatrix) throws DimensionMismatchException;
  
  FieldMatrix<T> preMultiply(FieldMatrix<T> paramFieldMatrix) throws DimensionMismatchException;
  
  FieldMatrix<T> power(int paramInt) throws NonSquareMatrixException, NotPositiveException;
  
  T[][] getData();
  
  FieldMatrix<T> getSubMatrix(int paramInt1, int paramInt2, int paramInt3, int paramInt4) throws NumberIsTooSmallException, OutOfRangeException;
  
  FieldMatrix<T> getSubMatrix(int[] paramArrayOfint1, int[] paramArrayOfint2) throws NoDataException, NullArgumentException, OutOfRangeException;
  
  void copySubMatrix(int paramInt1, int paramInt2, int paramInt3, int paramInt4, T[][] paramArrayOfT) throws MatrixDimensionMismatchException, NumberIsTooSmallException, OutOfRangeException;
  
  void copySubMatrix(int[] paramArrayOfint1, int[] paramArrayOfint2, T[][] paramArrayOfT) throws MatrixDimensionMismatchException, NoDataException, NullArgumentException, OutOfRangeException;
  
  void setSubMatrix(T[][] paramArrayOfT, int paramInt1, int paramInt2) throws DimensionMismatchException, OutOfRangeException, NoDataException, NullArgumentException;
  
  FieldMatrix<T> getRowMatrix(int paramInt) throws OutOfRangeException;
  
  void setRowMatrix(int paramInt, FieldMatrix<T> paramFieldMatrix) throws MatrixDimensionMismatchException, OutOfRangeException;
  
  FieldMatrix<T> getColumnMatrix(int paramInt) throws OutOfRangeException;
  
  void setColumnMatrix(int paramInt, FieldMatrix<T> paramFieldMatrix) throws MatrixDimensionMismatchException, OutOfRangeException;
  
  FieldVector<T> getRowVector(int paramInt) throws OutOfRangeException;
  
  void setRowVector(int paramInt, FieldVector<T> paramFieldVector) throws MatrixDimensionMismatchException, OutOfRangeException;
  
  FieldVector<T> getColumnVector(int paramInt) throws OutOfRangeException;
  
  void setColumnVector(int paramInt, FieldVector<T> paramFieldVector) throws MatrixDimensionMismatchException, OutOfRangeException;
  
  T[] getRow(int paramInt) throws OutOfRangeException;
  
  void setRow(int paramInt, T[] paramArrayOfT) throws MatrixDimensionMismatchException, OutOfRangeException;
  
  T[] getColumn(int paramInt) throws OutOfRangeException;
  
  void setColumn(int paramInt, T[] paramArrayOfT) throws MatrixDimensionMismatchException, OutOfRangeException;
  
  T getEntry(int paramInt1, int paramInt2) throws OutOfRangeException;
  
  void setEntry(int paramInt1, int paramInt2, T paramT) throws OutOfRangeException;
  
  void addToEntry(int paramInt1, int paramInt2, T paramT) throws OutOfRangeException;
  
  void multiplyEntry(int paramInt1, int paramInt2, T paramT) throws OutOfRangeException;
  
  FieldMatrix<T> transpose();
  
  T getTrace() throws NonSquareMatrixException;
  
  T[] operate(T[] paramArrayOfT) throws DimensionMismatchException;
  
  FieldVector<T> operate(FieldVector<T> paramFieldVector) throws DimensionMismatchException;
  
  T[] preMultiply(T[] paramArrayOfT) throws DimensionMismatchException;
  
  FieldVector<T> preMultiply(FieldVector<T> paramFieldVector) throws DimensionMismatchException;
  
  T walkInRowOrder(FieldMatrixChangingVisitor<T> paramFieldMatrixChangingVisitor);
  
  T walkInRowOrder(FieldMatrixPreservingVisitor<T> paramFieldMatrixPreservingVisitor);
  
  T walkInRowOrder(FieldMatrixChangingVisitor<T> paramFieldMatrixChangingVisitor, int paramInt1, int paramInt2, int paramInt3, int paramInt4) throws OutOfRangeException, NumberIsTooSmallException;
  
  T walkInRowOrder(FieldMatrixPreservingVisitor<T> paramFieldMatrixPreservingVisitor, int paramInt1, int paramInt2, int paramInt3, int paramInt4) throws OutOfRangeException, NumberIsTooSmallException;
  
  T walkInColumnOrder(FieldMatrixChangingVisitor<T> paramFieldMatrixChangingVisitor);
  
  T walkInColumnOrder(FieldMatrixPreservingVisitor<T> paramFieldMatrixPreservingVisitor);
  
  T walkInColumnOrder(FieldMatrixChangingVisitor<T> paramFieldMatrixChangingVisitor, int paramInt1, int paramInt2, int paramInt3, int paramInt4) throws NumberIsTooSmallException, OutOfRangeException;
  
  T walkInColumnOrder(FieldMatrixPreservingVisitor<T> paramFieldMatrixPreservingVisitor, int paramInt1, int paramInt2, int paramInt3, int paramInt4) throws NumberIsTooSmallException, OutOfRangeException;
  
  T walkInOptimizedOrder(FieldMatrixChangingVisitor<T> paramFieldMatrixChangingVisitor);
  
  T walkInOptimizedOrder(FieldMatrixPreservingVisitor<T> paramFieldMatrixPreservingVisitor);
  
  T walkInOptimizedOrder(FieldMatrixChangingVisitor<T> paramFieldMatrixChangingVisitor, int paramInt1, int paramInt2, int paramInt3, int paramInt4) throws NumberIsTooSmallException, OutOfRangeException;
  
  T walkInOptimizedOrder(FieldMatrixPreservingVisitor<T> paramFieldMatrixPreservingVisitor, int paramInt1, int paramInt2, int paramInt3, int paramInt4) throws NumberIsTooSmallException, OutOfRangeException;
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/linear/FieldMatrix.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */