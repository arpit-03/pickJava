package org.apache.http.nio.reactor;

import java.io.IOException;
import java.net.SocketAddress;

public interface ListenerEndpoint {
  SocketAddress getAddress();
  
  IOException getException();
  
  void waitFor() throws InterruptedException;
  
  boolean isClosed();
  
  void close();
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/nio/reactor/ListenerEndpoint.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */