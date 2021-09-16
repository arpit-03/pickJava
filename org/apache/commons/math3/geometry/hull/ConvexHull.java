package org.apache.commons.math3.geometry.hull;

import java.io.Serializable;
import org.apache.commons.math3.exception.InsufficientDataException;
import org.apache.commons.math3.geometry.partitioning.Region;

public interface ConvexHull<S extends org.apache.commons.math3.geometry.Space, P extends org.apache.commons.math3.geometry.Point<S>> extends Serializable {
  P[] getVertices();
  
  Region<S> createRegion() throws InsufficientDataException;
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/geometry/hull/ConvexHull.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */