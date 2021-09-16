package org.apache.http.client;

import org.apache.http.conn.routing.HttpRoute;

public interface BackoffManager {
  void backOff(HttpRoute paramHttpRoute);
  
  void probe(HttpRoute paramHttpRoute);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/client/BackoffManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */