package org.jfree.data.general;

public interface Dataset {
  void addChangeListener(DatasetChangeListener paramDatasetChangeListener);
  
  void removeChangeListener(DatasetChangeListener paramDatasetChangeListener);
  
  DatasetGroup getGroup();
  
  void setGroup(DatasetGroup paramDatasetGroup);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/general/Dataset.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */