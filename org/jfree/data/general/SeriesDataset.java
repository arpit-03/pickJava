package org.jfree.data.general;

public interface SeriesDataset extends Dataset {
  int getSeriesCount();
  
  Comparable getSeriesKey(int paramInt);
  
  int indexOf(Comparable paramComparable);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/general/SeriesDataset.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */