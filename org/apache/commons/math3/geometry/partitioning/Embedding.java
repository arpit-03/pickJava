package org.apache.commons.math3.geometry.partitioning;

import org.apache.commons.math3.geometry.Point;

public interface Embedding<S extends org.apache.commons.math3.geometry.Space, T extends org.apache.commons.math3.geometry.Space> {
  Point<T> toSubSpace(Point<S> paramPoint);
  
  Point<S> toSpace(Point<T> paramPoint);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/geometry/partitioning/Embedding.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */