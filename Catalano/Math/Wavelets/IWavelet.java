package Catalano.Math.Wavelets;

public interface IWavelet {
  void Forward(double[] paramArrayOfdouble);
  
  void Forward(double[][] paramArrayOfdouble);
  
  void Backward(double[] paramArrayOfdouble);
  
  void Backward(double[][] paramArrayOfdouble);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Math/Wavelets/IWavelet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */