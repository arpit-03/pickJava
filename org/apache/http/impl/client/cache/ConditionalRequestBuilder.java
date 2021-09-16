/*     */ package org.apache.http.impl.client.cache;
/*     */ 
/*     */ import java.util.Map;
/*     */ import org.apache.http.Header;
/*     */ import org.apache.http.HeaderElement;
/*     */ import org.apache.http.ProtocolException;
/*     */ import org.apache.http.annotation.Contract;
/*     */ import org.apache.http.annotation.ThreadingBehavior;
/*     */ import org.apache.http.client.cache.HttpCacheEntry;
/*     */ import org.apache.http.client.methods.HttpRequestWrapper;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Contract(threading = ThreadingBehavior.IMMUTABLE)
/*     */ class ConditionalRequestBuilder
/*     */ {
/*     */   public HttpRequestWrapper buildConditionalRequest(HttpRequestWrapper request, HttpCacheEntry cacheEntry) throws ProtocolException {
/*  59 */     HttpRequestWrapper newRequest = HttpRequestWrapper.wrap(request.getOriginal());
/*  60 */     newRequest.setHeaders(request.getAllHeaders());
/*  61 */     Header eTag = cacheEntry.getFirstHeader("ETag");
/*  62 */     if (eTag != null) {
/*  63 */       newRequest.setHeader("If-None-Match", eTag.getValue());
/*     */     }
/*  65 */     Header lastModified = cacheEntry.getFirstHeader("Last-Modified");
/*  66 */     if (lastModified != null) {
/*  67 */       newRequest.setHeader("If-Modified-Since", lastModified.getValue());
/*     */     }
/*  69 */     boolean mustRevalidate = false;
/*  70 */     for (Header h : cacheEntry.getHeaders("Cache-Control")) {
/*  71 */       for (HeaderElement elt : h.getElements()) {
/*  72 */         if ("must-revalidate".equalsIgnoreCase(elt.getName()) || "proxy-revalidate".equalsIgnoreCase(elt.getName())) {
/*     */           
/*  74 */           mustRevalidate = true;
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/*  79 */     if (mustRevalidate) {
/*  80 */       newRequest.addHeader("Cache-Control", "max-age=0");
/*     */     }
/*  82 */     return newRequest;
/*     */   }
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
/*     */   public HttpRequestWrapper buildConditionalRequestFromVariants(HttpRequestWrapper request, Map<String, Variant> variants) {
/*  99 */     HttpRequestWrapper newRequest = HttpRequestWrapper.wrap(request.getOriginal());
/* 100 */     newRequest.setHeaders(request.getAllHeaders());
/*     */ 
/*     */     
/* 103 */     StringBuilder etags = new StringBuilder();
/* 104 */     boolean first = true;
/* 105 */     for (String etag : variants.keySet()) {
/* 106 */       if (!first) {
/* 107 */         etags.append(",");
/*     */       }
/* 109 */       first = false;
/* 110 */       etags.append(etag);
/*     */     } 
/*     */     
/* 113 */     newRequest.setHeader("If-None-Match", etags.toString());
/* 114 */     return newRequest;
/*     */   }
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
/*     */   public HttpRequestWrapper buildUnconditionalRequest(HttpRequestWrapper request, HttpCacheEntry entry) {
/* 129 */     HttpRequestWrapper newRequest = HttpRequestWrapper.wrap(request.getOriginal());
/* 130 */     newRequest.setHeaders(request.getAllHeaders());
/* 131 */     newRequest.addHeader("Cache-Control", "no-cache");
/* 132 */     newRequest.addHeader("Pragma", "no-cache");
/* 133 */     newRequest.removeHeaders("If-Range");
/* 134 */     newRequest.removeHeaders("If-Match");
/* 135 */     newRequest.removeHeaders("If-None-Match");
/* 136 */     newRequest.removeHeaders("If-Unmodified-Since");
/* 137 */     newRequest.removeHeaders("If-Modified-Since");
/* 138 */     return newRequest;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/impl/client/cache/ConditionalRequestBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */