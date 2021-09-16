package Catalano.Math.Random;

public interface IRandomNumberGenerator {
  void setSeed(long paramLong);
  
  int nextBits(int paramInt);
  
  int nextInt();
  
  int nextInt(int paramInt);
  
  long nextLong();
  
  double nextDouble();
  
  void nextDoubles(double[] paramArrayOfdouble);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Math/Random/IRandomNumberGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */