package edu.mines.jtk.util;

public interface Float3 {
  int getN1();
  
  int getN2();
  
  int getN3();
  
  void get1(int paramInt1, int paramInt2, int paramInt3, int paramInt4, float[] paramArrayOffloat);
  
  void get2(int paramInt1, int paramInt2, int paramInt3, int paramInt4, float[] paramArrayOffloat);
  
  void get3(int paramInt1, int paramInt2, int paramInt3, int paramInt4, float[] paramArrayOffloat);
  
  void get12(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, float[][] paramArrayOffloat);
  
  void get13(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, float[][] paramArrayOffloat);
  
  void get23(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, float[][] paramArrayOffloat);
  
  void get123(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, float[][][] paramArrayOffloat);
  
  void get123(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, float[] paramArrayOffloat);
  
  void set1(int paramInt1, int paramInt2, int paramInt3, int paramInt4, float[] paramArrayOffloat);
  
  void set2(int paramInt1, int paramInt2, int paramInt3, int paramInt4, float[] paramArrayOffloat);
  
  void set3(int paramInt1, int paramInt2, int paramInt3, int paramInt4, float[] paramArrayOffloat);
  
  void set12(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, float[][] paramArrayOffloat);
  
  void set13(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, float[][] paramArrayOffloat);
  
  void set23(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, float[][] paramArrayOffloat);
  
  void set123(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, float[][][] paramArrayOffloat);
  
  void set123(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, float[] paramArrayOffloat);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/util/Float3.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */