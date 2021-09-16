package org.la4j.decomposition;

import java.io.Serializable;
import org.la4j.Matrix;

public interface MatrixDecompositor extends Serializable {
  Matrix[] decompose();
  
  Matrix self();
  
  boolean applicableTo(Matrix paramMatrix);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/la4j/decomposition/MatrixDecompositor.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */