package org.la4j.operation;

import org.la4j.Matrix;
import org.la4j.matrix.ColumnMajorSparseMatrix;
import org.la4j.matrix.DenseMatrix;
import org.la4j.matrix.RowMajorSparseMatrix;

public abstract class MatrixOperation<R> {
  public abstract R apply(DenseMatrix paramDenseMatrix);
  
  public abstract R apply(RowMajorSparseMatrix paramRowMajorSparseMatrix);
  
  public abstract R apply(ColumnMajorSparseMatrix paramColumnMajorSparseMatrix);
  
  public void ensureApplicableTo(Matrix a) {}
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/la4j/operation/MatrixOperation.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */