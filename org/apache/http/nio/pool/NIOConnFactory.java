package org.apache.http.nio.pool;

import java.io.IOException;
import org.apache.http.nio.reactor.IOSession;

public interface NIOConnFactory<T, C> {
  C create(T paramT, IOSession paramIOSession) throws IOException;
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/nio/pool/NIOConnFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */