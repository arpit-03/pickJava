package us.hebi.matlab.mat.types;

public interface Sparse extends Matrix {
  @Deprecated
  int getNzMax();
  
  int getNumNonZero();
  
  double getDefaultValue();
  
  void setDefaultValue(double paramDouble);
  
  void forEach(SparseConsumer paramSparseConsumer);
  
  public static interface SparseConsumer {
    void accept(int param1Int1, int param1Int2, double param1Double1, double param1Double2);
  }
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/us/hebi/matlab/mat/types/Sparse.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */