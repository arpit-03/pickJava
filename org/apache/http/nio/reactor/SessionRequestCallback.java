package org.apache.http.nio.reactor;

public interface SessionRequestCallback {
  void completed(SessionRequest paramSessionRequest);
  
  void failed(SessionRequest paramSessionRequest);
  
  void timeout(SessionRequest paramSessionRequest);
  
  void cancelled(SessionRequest paramSessionRequest);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/nio/reactor/SessionRequestCallback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */