package org.apache.commons.math3.genetics;

import org.apache.commons.math3.exception.NumberIsTooLargeException;

public interface Population extends Iterable<Chromosome> {
  int getPopulationSize();
  
  int getPopulationLimit();
  
  Population nextGeneration();
  
  void addChromosome(Chromosome paramChromosome) throws NumberIsTooLargeException;
  
  Chromosome getFittestChromosome();
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/genetics/Population.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */