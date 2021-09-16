package org.apache.http.client.entity;

import java.io.IOException;
import java.io.InputStream;

public interface InputStreamFactory {
  InputStream create(InputStream paramInputStream) throws IOException;
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/client/entity/InputStreamFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */