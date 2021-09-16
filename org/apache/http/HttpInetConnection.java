package org.apache.http;

import java.net.InetAddress;

public interface HttpInetConnection extends HttpConnection {
  InetAddress getLocalAddress();
  
  int getLocalPort();
  
  InetAddress getRemoteAddress();
  
  int getRemotePort();
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/HttpInetConnection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */