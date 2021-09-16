/*     */ package org.apache.http.impl.client.cache;
/*     */ 
/*     */ import java.util.Date;
/*     */ import org.apache.http.Header;
/*     */ import org.apache.http.HttpEntity;
/*     */ import org.apache.http.HttpResponse;
/*     */ import org.apache.http.HttpVersion;
/*     */ import org.apache.http.ProtocolVersion;
/*     */ import org.apache.http.annotation.Contract;
/*     */ import org.apache.http.annotation.ThreadingBehavior;
/*     */ import org.apache.http.client.cache.HttpCacheEntry;
/*     */ import org.apache.http.client.methods.CloseableHttpResponse;
/*     */ import org.apache.http.client.methods.HttpRequestWrapper;
/*     */ import org.apache.http.client.utils.DateUtils;
/*     */ import org.apache.http.message.BasicHeader;
/*     */ import org.apache.http.message.BasicHttpResponse;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Contract(threading = ThreadingBehavior.IMMUTABLE_CONDITIONAL)
/*     */ class CachedHttpResponseGenerator
/*     */ {
/*     */   private final CacheValidityPolicy validityStrategy;
/*     */   
/*     */   CachedHttpResponseGenerator(CacheValidityPolicy validityStrategy) {
/*  59 */     this.validityStrategy = validityStrategy;
/*     */   }
/*     */   
/*     */   CachedHttpResponseGenerator() {
/*  63 */     this(new CacheValidityPolicy());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   CloseableHttpResponse generateResponse(HttpRequestWrapper request, HttpCacheEntry entry) {
/*  74 */     Date now = new Date();
/*  75 */     BasicHttpResponse basicHttpResponse = new BasicHttpResponse((ProtocolVersion)HttpVersion.HTTP_1_1, entry.getStatusCode(), entry.getReasonPhrase());
/*     */ 
/*     */     
/*  78 */     basicHttpResponse.setHeaders(entry.getAllHeaders());
/*     */     
/*  80 */     if (responseShouldContainEntity(request, entry)) {
/*  81 */       HttpEntity entity = new CacheEntity(entry);
/*  82 */       addMissingContentLengthHeader((HttpResponse)basicHttpResponse, entity);
/*  83 */       basicHttpResponse.setEntity(entity);
/*     */     } 
/*     */     
/*  86 */     long age = this.validityStrategy.getCurrentAgeSecs(entry, now);
/*  87 */     if (age > 0L) {
/*  88 */       if (age >= 2147483647L) {
/*  89 */         basicHttpResponse.setHeader("Age", "2147483648");
/*     */       } else {
/*  91 */         basicHttpResponse.setHeader("Age", "" + (int)age);
/*     */       } 
/*     */     }
/*     */     
/*  95 */     return Proxies.enhanceResponse((HttpResponse)basicHttpResponse);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   CloseableHttpResponse generateNotModifiedResponse(HttpCacheEntry entry) {
/*     */     BasicHeader basicHeader;
/* 104 */     BasicHttpResponse basicHttpResponse = new BasicHttpResponse((ProtocolVersion)HttpVersion.HTTP_1_1, 304, "Not Modified");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 111 */     Header dateHeader = entry.getFirstHeader("Date");
/* 112 */     if (dateHeader == null) {
/* 113 */       basicHeader = new BasicHeader("Date", DateUtils.formatDate(new Date()));
/*     */     }
/* 115 */     basicHttpResponse.addHeader((Header)basicHeader);
/*     */ 
/*     */ 
/*     */     
/* 119 */     Header etagHeader = entry.getFirstHeader("ETag");
/* 120 */     if (etagHeader != null) {
/* 121 */       basicHttpResponse.addHeader(etagHeader);
/*     */     }
/*     */     
/* 124 */     Header contentLocationHeader = entry.getFirstHeader("Content-Location");
/* 125 */     if (contentLocationHeader != null) {
/* 126 */       basicHttpResponse.addHeader(contentLocationHeader);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 132 */     Header expiresHeader = entry.getFirstHeader("Expires");
/* 133 */     if (expiresHeader != null) {
/* 134 */       basicHttpResponse.addHeader(expiresHeader);
/*     */     }
/*     */     
/* 137 */     Header cacheControlHeader = entry.getFirstHeader("Cache-Control");
/* 138 */     if (cacheControlHeader != null) {
/* 139 */       basicHttpResponse.addHeader(cacheControlHeader);
/*     */     }
/*     */     
/* 142 */     Header varyHeader = entry.getFirstHeader("Vary");
/* 143 */     if (varyHeader != null) {
/* 144 */       basicHttpResponse.addHeader(varyHeader);
/*     */     }
/*     */     
/* 147 */     return Proxies.enhanceResponse((HttpResponse)basicHttpResponse);
/*     */   }
/*     */   
/*     */   private void addMissingContentLengthHeader(HttpResponse response, HttpEntity entity) {
/* 151 */     if (transferEncodingIsPresent(response)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 156 */     BasicHeader basicHeader = new BasicHeader("Content-Length", Long.toString(entity.getContentLength()));
/* 157 */     response.setHeader((Header)basicHeader);
/*     */   }
/*     */   
/*     */   private boolean transferEncodingIsPresent(HttpResponse response) {
/* 161 */     Header hdr = response.getFirstHeader("Transfer-Encoding");
/* 162 */     return (hdr != null);
/*     */   }
/*     */   
/*     */   private boolean responseShouldContainEntity(HttpRequestWrapper request, HttpCacheEntry cacheEntry) {
/* 166 */     return (request.getRequestLine().getMethod().equals("GET") && cacheEntry.getResource() != null);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/impl/client/cache/CachedHttpResponseGenerator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */