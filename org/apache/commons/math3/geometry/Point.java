package org.apache.commons.math3.geometry;

import java.io.Serializable;

public interface Point<S extends Space> extends Serializable {
  Space getSpace();
  
  boolean isNaN();
  
  double distance(Point<S> paramPoint);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/geometry/Point.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */