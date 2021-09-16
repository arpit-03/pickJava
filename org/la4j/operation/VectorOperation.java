package org.la4j.operation;

import org.la4j.Vector;
import org.la4j.vector.DenseVector;
import org.la4j.vector.SparseVector;

public abstract class VectorOperation<R> {
  public abstract R apply(SparseVector paramSparseVector);
  
  public abstract R apply(DenseVector paramDenseVector);
  
  public void ensureApplicableTo(Vector a) {}
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/la4j/operation/VectorOperation.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */