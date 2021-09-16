package org.apache.http.impl.client.cache;

public interface FailureCache {
  int getErrorCount(String paramString);
  
  void resetErrorCount(String paramString);
  
  void increaseErrorCount(String paramString);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/impl/client/cache/FailureCache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */