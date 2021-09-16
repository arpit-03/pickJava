/*     */ package org.apache.http.impl.client.cache;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Date;
/*     */ import org.apache.http.Header;
/*     */ import org.apache.http.HeaderIterator;
/*     */ import org.apache.http.HttpResponse;
/*     */ import org.apache.http.annotation.Contract;
/*     */ import org.apache.http.annotation.ThreadingBehavior;
/*     */ import org.apache.http.client.cache.HttpCacheEntry;
/*     */ import org.apache.http.client.cache.Resource;
/*     */ import org.apache.http.client.cache.ResourceFactory;
/*     */ import org.apache.http.client.utils.DateUtils;
/*     */ import org.apache.http.message.HeaderGroup;
/*     */ import org.apache.http.util.Args;
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
/*     */ @Contract(threading = ThreadingBehavior.IMMUTABLE_CONDITIONAL)
/*     */ class CacheEntryUpdater
/*     */ {
/*     */   private final ResourceFactory resourceFactory;
/*     */   
/*     */   CacheEntryUpdater() {
/*  60 */     this(new HeapResourceFactory());
/*     */   }
/*     */ 
/*     */   
/*     */   CacheEntryUpdater(ResourceFactory resourceFactory) {
/*  65 */     this.resourceFactory = resourceFactory;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HttpCacheEntry updateCacheEntry(String requestId, HttpCacheEntry entry, Date requestDate, Date responseDate, HttpResponse response) throws IOException {
/*  86 */     Args.check((response.getStatusLine().getStatusCode() == 304), "Response must have 304 status code");
/*     */     
/*  88 */     Header[] mergedHeaders = mergeHeaders(entry, response);
/*  89 */     Resource resource = null;
/*  90 */     if (entry.getResource() != null) {
/*  91 */       resource = this.resourceFactory.copy(requestId, entry.getResource());
/*     */     }
/*  93 */     return new HttpCacheEntry(requestDate, responseDate, entry.getStatusLine(), mergedHeaders, resource, entry.getRequestMethod());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Header[] mergeHeaders(HttpCacheEntry entry, HttpResponse response) {
/* 103 */     if (entryAndResponseHaveDateHeader(entry, response) && entryDateHeaderNewerThenResponse(entry, response))
/*     */     {
/*     */       
/* 106 */       return entry.getAllHeaders();
/*     */     }
/*     */     
/* 109 */     HeaderGroup headerGroup = new HeaderGroup();
/* 110 */     headerGroup.setHeaders(entry.getAllHeaders());
/*     */     HeaderIterator it;
/* 112 */     for (it = response.headerIterator(); it.hasNext(); ) {
/* 113 */       Header responseHeader = it.nextHeader();
/*     */       
/* 115 */       if ("Content-Encoding".equals(responseHeader.getName())) {
/*     */         continue;
/*     */       }
/* 118 */       Header[] matchingHeaders = headerGroup.getHeaders(responseHeader.getName());
/* 119 */       for (Header matchingHeader : matchingHeaders) {
/* 120 */         headerGroup.removeHeader(matchingHeader);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 125 */     for (it = headerGroup.iterator(); it.hasNext(); ) {
/* 126 */       Header cacheHeader = it.nextHeader();
/* 127 */       if ("Warning".equalsIgnoreCase(cacheHeader.getName())) {
/* 128 */         String warningValue = cacheHeader.getValue();
/* 129 */         if (warningValue != null && warningValue.startsWith("1")) {
/* 130 */           it.remove();
/*     */         }
/*     */       } 
/*     */     } 
/* 134 */     for (it = response.headerIterator(); it.hasNext(); ) {
/* 135 */       Header responseHeader = it.nextHeader();
/*     */       
/* 137 */       if ("Content-Encoding".equals(responseHeader.getName())) {
/*     */         continue;
/*     */       }
/* 140 */       headerGroup.addHeader(responseHeader);
/*     */     } 
/* 142 */     return headerGroup.getAllHeaders();
/*     */   }
/*     */   
/*     */   private boolean entryDateHeaderNewerThenResponse(HttpCacheEntry entry, HttpResponse response) {
/* 146 */     Date entryDate = DateUtils.parseDate(entry.getFirstHeader("Date").getValue());
/*     */     
/* 148 */     Date responseDate = DateUtils.parseDate(response.getFirstHeader("Date").getValue());
/*     */     
/* 150 */     return (entryDate != null && responseDate != null && entryDate.after(responseDate));
/*     */   }
/*     */   
/*     */   private boolean entryAndResponseHaveDateHeader(HttpCacheEntry entry, HttpResponse response) {
/* 154 */     return (entry.getFirstHeader("Date") != null && response.getFirstHeader("Date") != null);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/impl/client/cache/CacheEntryUpdater.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */