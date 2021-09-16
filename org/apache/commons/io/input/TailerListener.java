package org.apache.commons.io.input;

public interface TailerListener {
  void init(Tailer paramTailer);
  
  void fileNotFound();
  
  void fileRotated();
  
  void handle(String paramString);
  
  void handle(Exception paramException);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/io/input/TailerListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */