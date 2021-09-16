package org.apache.commons.math3.genetics;

import org.apache.commons.math3.exception.MathIllegalArgumentException;

public interface MutationPolicy {
  Chromosome mutate(Chromosome paramChromosome) throws MathIllegalArgumentException;
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/genetics/MutationPolicy.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */