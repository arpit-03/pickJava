package org.apache.commons.math3.geometry.partitioning;

import org.apache.commons.math3.geometry.Point;

public interface Transform<S extends org.apache.commons.math3.geometry.Space, T extends org.apache.commons.math3.geometry.Space> {
  Point<S> apply(Point<S> paramPoint);
  
  Hyperplane<S> apply(Hyperplane<S> paramHyperplane);
  
  SubHyperplane<T> apply(SubHyperplane<T> paramSubHyperplane, Hyperplane<S> paramHyperplane1, Hyperplane<S> paramHyperplane2);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/geometry/partitioning/Transform.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */