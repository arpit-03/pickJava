package org.apache.commons.math3.geometry.enclosing;

public interface Encloser<S extends org.apache.commons.math3.geometry.Space, P extends org.apache.commons.math3.geometry.Point<S>> {
  EnclosingBall<S, P> enclose(Iterable<P> paramIterable);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/geometry/enclosing/Encloser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */