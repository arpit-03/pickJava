package org.apache.http.impl.client.cache.memcached;

import org.apache.http.client.cache.HttpCacheEntry;

public interface MemcachedCacheEntry {
  byte[] toByteArray();
  
  String getStorageKey();
  
  HttpCacheEntry getHttpCacheEntry();
  
  void set(byte[] paramArrayOfbyte);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/impl/client/cache/memcached/MemcachedCacheEntry.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */