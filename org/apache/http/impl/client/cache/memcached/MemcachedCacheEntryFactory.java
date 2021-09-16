package org.apache.http.impl.client.cache.memcached;

import org.apache.http.client.cache.HttpCacheEntry;

public interface MemcachedCacheEntryFactory {
  MemcachedCacheEntry getMemcachedCacheEntry(String paramString, HttpCacheEntry paramHttpCacheEntry);
  
  MemcachedCacheEntry getUnsetCacheEntry();
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/impl/client/cache/memcached/MemcachedCacheEntryFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */