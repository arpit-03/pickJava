package edu.mines.jtk.opt;

public interface Vect extends VectConst {
  void add(double paramDouble1, double paramDouble2, VectConst paramVectConst);
  
  void project(double paramDouble1, double paramDouble2, VectConst paramVectConst);
  
  void dispose();
  
  void multiplyInverseCovariance();
  
  void constrain();
  
  void postCondition();
  
  Vect clone();
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/opt/Vect.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */