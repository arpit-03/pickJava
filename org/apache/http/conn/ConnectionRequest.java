package org.apache.http.conn;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import org.apache.http.HttpClientConnection;
import org.apache.http.concurrent.Cancellable;

public interface ConnectionRequest extends Cancellable {
  HttpClientConnection get(long paramLong, TimeUnit paramTimeUnit) throws InterruptedException, ExecutionException, ConnectionPoolTimeoutException;
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/conn/ConnectionRequest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */