package org.apache.commons.math3.linear;

import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.NoDataException;
import org.apache.commons.math3.exception.NotPositiveException;
import org.apache.commons.math3.exception.NotStrictlyPositiveException;
import org.apache.commons.math3.exception.NullArgumentException;
import org.apache.commons.math3.exception.NumberIsTooSmallException;
import org.apache.commons.math3.exception.OutOfRangeException;

public interface RealMatrix extends AnyMatrix {
  RealMatrix createMatrix(int paramInt1, int paramInt2) throws NotStrictlyPositiveException;
  
  RealMatrix copy();
  
  RealMatrix add(RealMatrix paramRealMatrix) throws MatrixDimensionMismatchException;
  
  RealMatrix subtract(RealMatrix paramRealMatrix) throws MatrixDimensionMismatchException;
  
  RealMatrix scalarAdd(double paramDouble);
  
  RealMatrix scalarMultiply(double paramDouble);
  
  RealMatrix multiply(RealMatrix paramRealMatrix) throws DimensionMismatchException;
  
  RealMatrix preMultiply(RealMatrix paramRealMatrix) throws DimensionMismatchException;
  
  RealMatrix power(int paramInt) throws NotPositiveException, NonSquareMatrixException;
  
  double[][] getData();
  
  double getNorm();
  
  double getFrobeniusNorm();
  
  RealMatrix getSubMatrix(int paramInt1, int paramInt2, int paramInt3, int paramInt4) throws OutOfRangeException, NumberIsTooSmallException;
  
  RealMatrix getSubMatrix(int[] paramArrayOfint1, int[] paramArrayOfint2) throws NullArgumentException, NoDataException, OutOfRangeException;
  
  void copySubMatrix(int paramInt1, int paramInt2, int paramInt3, int paramInt4, double[][] paramArrayOfdouble) throws OutOfRangeException, NumberIsTooSmallException, MatrixDimensionMismatchException;
  
  void copySubMatrix(int[] paramArrayOfint1, int[] paramArrayOfint2, double[][] paramArrayOfdouble) throws OutOfRangeException, NullArgumentException, NoDataException, MatrixDimensionMismatchException;
  
  void setSubMatrix(double[][] paramArrayOfdouble, int paramInt1, int paramInt2) throws NoDataException, OutOfRangeException, DimensionMismatchException, NullArgumentException;
  
  RealMatrix getRowMatrix(int paramInt) throws OutOfRangeException;
  
  void setRowMatrix(int paramInt, RealMatrix paramRealMatrix) throws OutOfRangeException, MatrixDimensionMismatchException;
  
  RealMatrix getColumnMatrix(int paramInt) throws OutOfRangeException;
  
  void setColumnMatrix(int paramInt, RealMatrix paramRealMatrix) throws OutOfRangeException, MatrixDimensionMismatchException;
  
  RealVector getRowVector(int paramInt) throws OutOfRangeException;
  
  void setRowVector(int paramInt, RealVector paramRealVector) throws OutOfRangeException, MatrixDimensionMismatchException;
  
  RealVector getColumnVector(int paramInt) throws OutOfRangeException;
  
  void setColumnVector(int paramInt, RealVector paramRealVector) throws OutOfRangeException, MatrixDimensionMismatchException;
  
  double[] getRow(int paramInt) throws OutOfRangeException;
  
  void setRow(int paramInt, double[] paramArrayOfdouble) throws OutOfRangeException, MatrixDimensionMismatchException;
  
  double[] getColumn(int paramInt) throws OutOfRangeException;
  
  void setColumn(int paramInt, double[] paramArrayOfdouble) throws OutOfRangeException, MatrixDimensionMismatchException;
  
  double getEntry(int paramInt1, int paramInt2) throws OutOfRangeException;
  
  void setEntry(int paramInt1, int paramInt2, double paramDouble) throws OutOfRangeException;
  
  void addToEntry(int paramInt1, int paramInt2, double paramDouble) throws OutOfRangeException;
  
  void multiplyEntry(int paramInt1, int paramInt2, double paramDouble) throws OutOfRangeException;
  
  RealMatrix transpose();
  
  double getTrace() throws NonSquareMatrixException;
  
  double[] operate(double[] paramArrayOfdouble) throws DimensionMismatchException;
  
  RealVector operate(RealVector paramRealVector) throws DimensionMismatchException;
  
  double[] preMultiply(double[] paramArrayOfdouble) throws DimensionMismatchException;
  
  RealVector preMultiply(RealVector paramRealVector) throws DimensionMismatchException;
  
  double walkInRowOrder(RealMatrixChangingVisitor paramRealMatrixChangingVisitor);
  
  double walkInRowOrder(RealMatrixPreservingVisitor paramRealMatrixPreservingVisitor);
  
  double walkInRowOrder(RealMatrixChangingVisitor paramRealMatrixChangingVisitor, int paramInt1, int paramInt2, int paramInt3, int paramInt4) throws OutOfRangeException, NumberIsTooSmallException;
  
  double walkInRowOrder(RealMatrixPreservingVisitor paramRealMatrixPreservingVisitor, int paramInt1, int paramInt2, int paramInt3, int paramInt4) throws OutOfRangeException, NumberIsTooSmallException;
  
  double walkInColumnOrder(RealMatrixChangingVisitor paramRealMatrixChangingVisitor);
  
  double walkInColumnOrder(RealMatrixPreservingVisitor paramRealMatrixPreservingVisitor);
  
  double walkInColumnOrder(RealMatrixChangingVisitor paramRealMatrixChangingVisitor, int paramInt1, int paramInt2, int paramInt3, int paramInt4) throws OutOfRangeException, NumberIsTooSmallException;
  
  double walkInColumnOrder(RealMatrixPreservingVisitor paramRealMatrixPreservingVisitor, int paramInt1, int paramInt2, int paramInt3, int paramInt4) throws OutOfRangeException, NumberIsTooSmallException;
  
  double walkInOptimizedOrder(RealMatrixChangingVisitor paramRealMatrixChangingVisitor);
  
  double walkInOptimizedOrder(RealMatrixPreservingVisitor paramRealMatrixPreservingVisitor);
  
  double walkInOptimizedOrder(RealMatrixChangingVisitor paramRealMatrixChangingVisitor, int paramInt1, int paramInt2, int paramInt3, int paramInt4) throws OutOfRangeException, NumberIsTooSmallException;
  
  double walkInOptimizedOrder(RealMatrixPreservingVisitor paramRealMatrixPreservingVisitor, int paramInt1, int paramInt2, int paramInt3, int paramInt4) throws OutOfRangeException, NumberIsTooSmallException;
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/linear/RealMatrix.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */