package org.apache.http.nio.reactor;

import java.io.IOException;
import java.net.SocketAddress;

public interface SessionRequest {
  SocketAddress getRemoteAddress();
  
  SocketAddress getLocalAddress();
  
  Object getAttachment();
  
  boolean isCompleted();
  
  IOSession getSession();
  
  IOException getException();
  
  void waitFor() throws InterruptedException;
  
  void setConnectTimeout(int paramInt);
  
  int getConnectTimeout();
  
  void cancel();
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/nio/reactor/SessionRequest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */