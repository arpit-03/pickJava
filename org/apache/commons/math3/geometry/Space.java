package org.apache.commons.math3.geometry;

import java.io.Serializable;
import org.apache.commons.math3.exception.MathUnsupportedOperationException;

public interface Space extends Serializable {
  int getDimension();
  
  Space getSubSpace() throws MathUnsupportedOperationException;
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/geometry/Space.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */