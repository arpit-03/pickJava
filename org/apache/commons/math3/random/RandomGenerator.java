package org.apache.commons.math3.random;

public interface RandomGenerator {
  void setSeed(int paramInt);
  
  void setSeed(int[] paramArrayOfint);
  
  void setSeed(long paramLong);
  
  void nextBytes(byte[] paramArrayOfbyte);
  
  int nextInt();
  
  int nextInt(int paramInt);
  
  long nextLong();
  
  boolean nextBoolean();
  
  float nextFloat();
  
  double nextDouble();
  
  double nextGaussian();
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/random/RandomGenerator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */