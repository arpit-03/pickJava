package org.apache.commons.math3.stat.clustering;

import java.util.Collection;

@Deprecated
public interface Clusterable<T> {
  double distanceFrom(T paramT);
  
  T centroidOf(Collection<T> paramCollection);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/stat/clustering/Clusterable.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */