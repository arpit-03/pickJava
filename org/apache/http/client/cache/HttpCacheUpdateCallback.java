package org.apache.http.client.cache;

import java.io.IOException;

public interface HttpCacheUpdateCallback {
  HttpCacheEntry update(HttpCacheEntry paramHttpCacheEntry) throws IOException;
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/client/cache/HttpCacheUpdateCallback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */