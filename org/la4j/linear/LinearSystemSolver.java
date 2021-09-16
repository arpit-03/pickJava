package org.la4j.linear;

import java.io.Serializable;
import org.la4j.Matrix;
import org.la4j.Vector;

public interface LinearSystemSolver extends Serializable {
  Vector solve(Vector paramVector);
  
  Matrix self();
  
  int unknowns();
  
  int equations();
  
  boolean applicableTo(Matrix paramMatrix);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/la4j/linear/LinearSystemSolver.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */