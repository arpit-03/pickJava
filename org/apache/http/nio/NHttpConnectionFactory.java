package org.apache.http.nio;

import org.apache.http.nio.reactor.IOSession;

public interface NHttpConnectionFactory<T extends NHttpConnection> {
  T createConnection(IOSession paramIOSession);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/nio/NHttpConnectionFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */