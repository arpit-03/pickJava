package org.apache.commons.math3.geometry.partitioning;

import org.apache.commons.math3.geometry.Point;

public interface Hyperplane<S extends org.apache.commons.math3.geometry.Space> {
  Hyperplane<S> copySelf();
  
  double getOffset(Point<S> paramPoint);
  
  Point<S> project(Point<S> paramPoint);
  
  double getTolerance();
  
  boolean sameOrientationAs(Hyperplane<S> paramHyperplane);
  
  SubHyperplane<S> wholeHyperplane();
  
  Region<S> wholeSpace();
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/geometry/partitioning/Hyperplane.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */