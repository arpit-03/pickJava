package org.apache.http.impl.client.cache;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.cache.HttpCacheEntry;
import org.apache.http.client.methods.CloseableHttpResponse;

interface HttpCache {
  void flushCacheEntriesFor(HttpHost paramHttpHost, HttpRequest paramHttpRequest) throws IOException;
  
  void flushInvalidatedCacheEntriesFor(HttpHost paramHttpHost, HttpRequest paramHttpRequest) throws IOException;
  
  void flushInvalidatedCacheEntriesFor(HttpHost paramHttpHost, HttpRequest paramHttpRequest, HttpResponse paramHttpResponse);
  
  HttpCacheEntry getCacheEntry(HttpHost paramHttpHost, HttpRequest paramHttpRequest) throws IOException;
  
  Map<String, Variant> getVariantCacheEntriesWithEtags(HttpHost paramHttpHost, HttpRequest paramHttpRequest) throws IOException;
  
  HttpResponse cacheAndReturnResponse(HttpHost paramHttpHost, HttpRequest paramHttpRequest, HttpResponse paramHttpResponse, Date paramDate1, Date paramDate2) throws IOException;
  
  CloseableHttpResponse cacheAndReturnResponse(HttpHost paramHttpHost, HttpRequest paramHttpRequest, CloseableHttpResponse paramCloseableHttpResponse, Date paramDate1, Date paramDate2) throws IOException;
  
  HttpCacheEntry updateCacheEntry(HttpHost paramHttpHost, HttpRequest paramHttpRequest, HttpCacheEntry paramHttpCacheEntry, HttpResponse paramHttpResponse, Date paramDate1, Date paramDate2) throws IOException;
  
  HttpCacheEntry updateVariantCacheEntry(HttpHost paramHttpHost, HttpRequest paramHttpRequest, HttpCacheEntry paramHttpCacheEntry, HttpResponse paramHttpResponse, Date paramDate1, Date paramDate2, String paramString) throws IOException;
  
  void reuseVariantEntryFor(HttpHost paramHttpHost, HttpRequest paramHttpRequest, Variant paramVariant) throws IOException;
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/impl/client/cache/HttpCache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */