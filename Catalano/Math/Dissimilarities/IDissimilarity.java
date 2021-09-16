package Catalano.Math.Dissimilarities;

import Catalano.Math.Distances.IDivergence;

public interface IDissimilarity<T> extends IDivergence<T> {
  double Compute(T paramT1, T paramT2);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Math/Dissimilarities/IDissimilarity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */