package edu.mines.jtk.opt;

public interface VectContainer extends Vect {
  void put(int paramInt, Vect paramVect);
  
  Vect get(int paramInt);
  
  int size();
  
  boolean containsKey(int paramInt);
  
  int[] getKeys();
  
  VectContainer clone();
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/opt/VectContainer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */