package org.apache.http.pool;

import java.io.IOException;

public interface ConnFactory<T, C> {
  C create(T paramT) throws IOException;
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/pool/ConnFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */