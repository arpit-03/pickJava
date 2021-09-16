package edu.mines.jtk.opt;

import java.io.Serializable;

public interface VectConst extends Cloneable, Serializable {
  double dot(VectConst paramVectConst);
  
  double magnitude();
  
  Vect clone();
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/opt/VectConst.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */