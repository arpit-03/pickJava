package org.apache.http.impl.conn.tsccm;

import java.util.concurrent.TimeUnit;
import org.apache.http.conn.ConnectionPoolTimeoutException;

@Deprecated
public interface PoolEntryRequest {
  BasicPoolEntry getPoolEntry(long paramLong, TimeUnit paramTimeUnit) throws InterruptedException, ConnectionPoolTimeoutException;
  
  void abortRequest();
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/impl/conn/tsccm/PoolEntryRequest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */