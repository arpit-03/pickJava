package org.la4j.inversion;

import java.io.Serializable;
import org.la4j.Matrix;

public interface MatrixInverter extends Serializable {
  Matrix inverse();
  
  Matrix self();
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/la4j/inversion/MatrixInverter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */