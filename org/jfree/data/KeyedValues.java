package org.jfree.data;

import java.util.List;

public interface KeyedValues extends Values {
  Comparable getKey(int paramInt);
  
  int getIndex(Comparable paramComparable);
  
  List getKeys();
  
  Number getValue(Comparable paramComparable);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/KeyedValues.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */