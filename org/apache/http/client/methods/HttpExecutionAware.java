package org.apache.http.client.methods;

import org.apache.http.concurrent.Cancellable;

public interface HttpExecutionAware {
  boolean isAborted();
  
  void setCancellable(Cancellable paramCancellable);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/client/methods/HttpExecutionAware.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */