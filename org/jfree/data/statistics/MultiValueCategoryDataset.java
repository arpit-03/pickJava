package org.jfree.data.statistics;

import java.util.List;
import org.jfree.data.category.CategoryDataset;

public interface MultiValueCategoryDataset extends CategoryDataset {
  List getValues(int paramInt1, int paramInt2);
  
  List getValues(Comparable paramComparable1, Comparable paramComparable2);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/statistics/MultiValueCategoryDataset.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */