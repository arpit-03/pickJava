package edu.mines.jtk.opt;

public interface Quadratic {
  void multiplyHessian(Vect paramVect);
  
  void inverseHessian(Vect paramVect);
  
  Vect getB();
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/opt/Quadratic.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */