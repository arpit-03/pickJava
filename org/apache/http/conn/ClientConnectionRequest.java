package org.apache.http.conn;

import java.util.concurrent.TimeUnit;

@Deprecated
public interface ClientConnectionRequest {
  ManagedClientConnection getConnection(long paramLong, TimeUnit paramTimeUnit) throws InterruptedException, ConnectionPoolTimeoutException;
  
  void abortRequest();
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/conn/ClientConnectionRequest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */