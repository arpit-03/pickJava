package Catalano.Statistics.Regression;

public interface ISimpleRegression {
  double Regression(double paramDouble);
  
  double[] Regression(double[] paramArrayOfdouble);
  
  double CoefficientOfDetermination();
  
  String toString();
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Statistics/Regression/ISimpleRegression.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */