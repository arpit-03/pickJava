package org.apache.http.nio;

import java.io.IOException;

public interface IOControl {
  void requestInput();
  
  void suspendInput();
  
  void requestOutput();
  
  void suspendOutput();
  
  void shutdown() throws IOException;
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/nio/IOControl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */