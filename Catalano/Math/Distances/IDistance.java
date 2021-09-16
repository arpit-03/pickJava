package Catalano.Math.Distances;

import java.io.Serializable;

public interface IDistance<T> extends IDivergence<T>, Serializable {
  double Compute(T paramT1, T paramT2);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Math/Distances/IDistance.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */