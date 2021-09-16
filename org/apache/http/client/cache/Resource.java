package org.apache.http.client.cache;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

public interface Resource extends Serializable {
  InputStream getInputStream() throws IOException;
  
  long length();
  
  void dispose();
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/client/cache/Resource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */