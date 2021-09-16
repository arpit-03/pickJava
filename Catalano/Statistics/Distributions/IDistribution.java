package Catalano.Statistics.Distributions;

public interface IDistribution {
  double Mean();
  
  double Variance();
  
  double Entropy();
  
  double DistributionFunction(double paramDouble);
  
  double ProbabilityDensityFunction(double paramDouble);
  
  double LogProbabilityDensityFunction(double paramDouble);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Statistics/Distributions/IDistribution.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */