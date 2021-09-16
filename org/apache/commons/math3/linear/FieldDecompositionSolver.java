package org.apache.commons.math3.linear;

public interface FieldDecompositionSolver<T extends org.apache.commons.math3.FieldElement<T>> {
  FieldVector<T> solve(FieldVector<T> paramFieldVector);
  
  FieldMatrix<T> solve(FieldMatrix<T> paramFieldMatrix);
  
  boolean isNonSingular();
  
  FieldMatrix<T> getInverse();
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/linear/FieldDecompositionSolver.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */