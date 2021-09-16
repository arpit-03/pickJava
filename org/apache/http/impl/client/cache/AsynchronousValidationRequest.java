/*     */ package org.apache.http.impl.client.cache;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.apache.http.Header;
/*     */ import org.apache.http.HttpException;
/*     */ import org.apache.http.HttpResponse;
/*     */ import org.apache.http.client.cache.HttpCacheEntry;
/*     */ import org.apache.http.client.methods.CloseableHttpResponse;
/*     */ import org.apache.http.client.methods.HttpExecutionAware;
/*     */ import org.apache.http.client.methods.HttpRequestWrapper;
/*     */ import org.apache.http.client.protocol.HttpClientContext;
/*     */ import org.apache.http.conn.routing.HttpRoute;
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
/*     */ public class AsynchronousValidationRequest
/*     */   implements Runnable
/*     */ {
/*     */   private final AsynchronousValidator parent;
/*     */   private final CachingExec cachingExec;
/*     */   private final HttpRoute route;
/*     */   private final HttpRequestWrapper request;
/*     */   private final HttpClientContext context;
/*     */   private final HttpExecutionAware execAware;
/*     */   private final HttpCacheEntry cacheEntry;
/*     */   private final String identifier;
/*     */   private final int consecutiveFailedAttempts;
/*  59 */   private final Log log = LogFactory.getLog(getClass());
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
/*     */   AsynchronousValidationRequest(AsynchronousValidator parent, CachingExec cachingExec, HttpRoute route, HttpRequestWrapper request, HttpClientContext context, HttpExecutionAware execAware, HttpCacheEntry cacheEntry, String identifier, int consecutiveFailedAttempts) {
/*  80 */     this.parent = parent;
/*  81 */     this.cachingExec = cachingExec;
/*  82 */     this.route = route;
/*  83 */     this.request = request;
/*  84 */     this.context = context;
/*  85 */     this.execAware = execAware;
/*  86 */     this.cacheEntry = cacheEntry;
/*  87 */     this.identifier = identifier;
/*  88 */     this.consecutiveFailedAttempts = consecutiveFailedAttempts;
/*     */   }
/*     */ 
/*     */   
/*     */   public void run() {
/*     */     try {
/*  94 */       if (revalidateCacheEntry()) {
/*  95 */         this.parent.jobSuccessful(this.identifier);
/*     */       } else {
/*  97 */         this.parent.jobFailed(this.identifier);
/*     */       } 
/*     */     } finally {
/* 100 */       this.parent.markComplete(this.identifier);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean revalidateCacheEntry() {
/*     */     try {
/* 113 */       CloseableHttpResponse httpResponse = this.cachingExec.revalidateCacheEntry(this.route, this.request, this.context, this.execAware, this.cacheEntry);
/*     */       try {
/* 115 */         int statusCode = httpResponse.getStatusLine().getStatusCode();
/* 116 */         return (isNotServerError(statusCode) && isNotStale((HttpResponse)httpResponse));
/*     */       } finally {
/* 118 */         httpResponse.close();
/*     */       } 
/* 120 */     } catch (IOException ioe) {
/* 121 */       this.log.debug("Asynchronous revalidation failed due to I/O error", ioe);
/* 122 */       return false;
/* 123 */     } catch (HttpException pe) {
/* 124 */       this.log.error("HTTP protocol exception during asynchronous revalidation", (Throwable)pe);
/* 125 */       return false;
/* 126 */     } catch (RuntimeException re) {
/* 127 */       this.log.error("RuntimeException thrown during asynchronous revalidation: " + re);
/* 128 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isNotServerError(int statusCode) {
/* 138 */     return (statusCode < 500);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isNotStale(HttpResponse httpResponse) {
/* 147 */     Header[] warnings = httpResponse.getHeaders("Warning");
/* 148 */     if (warnings != null)
/*     */     {
/* 150 */       for (Header warning : warnings) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 157 */         String warningValue = warning.getValue();
/* 158 */         if (warningValue.startsWith("110") || warningValue.startsWith("111"))
/*     */         {
/* 160 */           return false;
/*     */         }
/*     */       } 
/*     */     }
/* 164 */     return true;
/*     */   }
/*     */   
/*     */   public String getIdentifier() {
/* 168 */     return this.identifier;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getConsecutiveFailedAttempts() {
/* 176 */     return this.consecutiveFailedAttempts;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/impl/client/cache/AsynchronousValidationRequest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */