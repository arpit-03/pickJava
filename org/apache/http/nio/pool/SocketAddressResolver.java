package org.apache.http.nio.pool;

import java.io.IOException;
import java.net.SocketAddress;

public interface SocketAddressResolver<T> {
  SocketAddress resolveLocalAddress(T paramT) throws IOException;
  
  SocketAddress resolveRemoteAddress(T paramT) throws IOException;
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/nio/pool/SocketAddressResolver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */