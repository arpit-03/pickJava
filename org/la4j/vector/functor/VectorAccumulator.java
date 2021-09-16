package org.la4j.vector.functor;

public interface VectorAccumulator {
  void update(int paramInt, double paramDouble);
  
  double accumulate();
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/la4j/vector/functor/VectorAccumulator.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */