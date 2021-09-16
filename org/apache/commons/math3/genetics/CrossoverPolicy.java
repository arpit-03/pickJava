package org.apache.commons.math3.genetics;

import org.apache.commons.math3.exception.MathIllegalArgumentException;

public interface CrossoverPolicy {
  ChromosomePair crossover(Chromosome paramChromosome1, Chromosome paramChromosome2) throws MathIllegalArgumentException;
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/genetics/CrossoverPolicy.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */