package edu.mines.jtk.opt;

public interface Transform {
  void forwardNonlinear(Vect paramVect, VectConst paramVectConst);
  
  void forwardLinearized(Vect paramVect, VectConst paramVectConst1, VectConst paramVectConst2);
  
  void addTranspose(VectConst paramVectConst1, Vect paramVect, VectConst paramVectConst2);
  
  void inverseHessian(Vect paramVect, VectConst paramVectConst);
  
  void adjustRobustErrors(Vect paramVect);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/opt/Transform.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */