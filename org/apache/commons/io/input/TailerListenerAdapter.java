package org.apache.commons.io.input;

public class TailerListenerAdapter implements TailerListener {
  public void init(Tailer tailer) {}
  
  public void fileNotFound() {}
  
  public void fileRotated() {}
  
  public void handle(String line) {}
  
  public void handle(Exception ex) {}
  
  public void endOfFileReached() {}
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/io/input/TailerListenerAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */