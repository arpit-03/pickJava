package Catalano.Statistics.Distributions;

public interface IDiscreteDistribution {
  double Mean();
  
  double Variance();
  
  double Entropy();
  
  double DistributionFunction(int paramInt);
  
  double ProbabilityMassFunction(int paramInt);
  
  double LogProbabilityMassFunction(int paramInt);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Statistics/Distributions/IDiscreteDistribution.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */