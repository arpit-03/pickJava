package edu.mines.jtk.opt;

public interface LinearTransform {
  void forward(Vect paramVect, VectConst paramVectConst);
  
  void addTranspose(VectConst paramVectConst, Vect paramVect);
  
  void inverseHessian(Vect paramVect);
  
  void adjustRobustErrors(Vect paramVect);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/opt/LinearTransform.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */