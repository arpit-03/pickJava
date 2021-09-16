package org.jfree.data;

import java.util.List;

public interface KeyedValues2D extends Values2D {
  Comparable getRowKey(int paramInt);
  
  int getRowIndex(Comparable paramComparable);
  
  List getRowKeys();
  
  Comparable getColumnKey(int paramInt);
  
  int getColumnIndex(Comparable paramComparable);
  
  List getColumnKeys();
  
  Number getValue(Comparable paramComparable1, Comparable paramComparable2);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/KeyedValues2D.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */