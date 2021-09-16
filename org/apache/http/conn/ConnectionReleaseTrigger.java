package org.apache.http.conn;

import java.io.IOException;

public interface ConnectionReleaseTrigger {
  void releaseConnection() throws IOException;
  
  void abortConnection() throws IOException;
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/conn/ConnectionReleaseTrigger.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */