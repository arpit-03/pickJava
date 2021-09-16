package org.apache.http.impl.client.cache;

import java.io.Closeable;

public interface SchedulingStrategy extends Closeable {
  void schedule(AsynchronousValidationRequest paramAsynchronousValidationRequest);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/impl/client/cache/SchedulingStrategy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */