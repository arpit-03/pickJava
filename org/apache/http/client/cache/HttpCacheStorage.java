package org.apache.http.client.cache;

import java.io.IOException;

public interface HttpCacheStorage {
  void putEntry(String paramString, HttpCacheEntry paramHttpCacheEntry) throws IOException;
  
  HttpCacheEntry getEntry(String paramString) throws IOException;
  
  void removeEntry(String paramString) throws IOException;
  
  void updateEntry(String paramString, HttpCacheUpdateCallback paramHttpCacheUpdateCallback) throws IOException, HttpCacheUpdateException;
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/client/cache/HttpCacheStorage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */