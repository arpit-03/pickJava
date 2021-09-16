package org.la4j.matrix.functor;

public interface MatrixAccumulator {
  void update(int paramInt1, int paramInt2, double paramDouble);
  
  double accumulate();
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/la4j/matrix/functor/MatrixAccumulator.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */