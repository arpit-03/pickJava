package org.apache.http.client.cache;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface HttpCacheEntrySerializer {
  void writeTo(HttpCacheEntry paramHttpCacheEntry, OutputStream paramOutputStream) throws IOException;
  
  HttpCacheEntry readFrom(InputStream paramInputStream) throws IOException;
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/client/cache/HttpCacheEntrySerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */