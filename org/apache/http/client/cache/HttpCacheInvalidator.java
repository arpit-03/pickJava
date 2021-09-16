package org.apache.http.client.cache;

import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;

public interface HttpCacheInvalidator {
  void flushInvalidatedCacheEntries(HttpHost paramHttpHost, HttpRequest paramHttpRequest);
  
  void flushInvalidatedCacheEntries(HttpHost paramHttpHost, HttpRequest paramHttpRequest, HttpResponse paramHttpResponse);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/client/cache/HttpCacheInvalidator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */