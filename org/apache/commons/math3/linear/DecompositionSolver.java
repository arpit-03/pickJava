package org.apache.commons.math3.linear;

public interface DecompositionSolver {
  RealVector solve(RealVector paramRealVector) throws SingularMatrixException;
  
  RealMatrix solve(RealMatrix paramRealMatrix) throws SingularMatrixException;
  
  boolean isNonSingular();
  
  RealMatrix getInverse() throws SingularMatrixException;
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/linear/DecompositionSolver.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */