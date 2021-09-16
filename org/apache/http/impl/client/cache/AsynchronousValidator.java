/*     */ package org.apache.http.impl.client.cache;
/*     */ 
/*     */ import java.io.Closeable;
/*     */ import java.io.IOException;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.RejectedExecutionException;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.apache.http.HttpRequest;
/*     */ import org.apache.http.client.cache.HttpCacheEntry;
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
/*     */ class AsynchronousValidator
/*     */   implements Closeable
/*     */ {
/*     */   private final SchedulingStrategy schedulingStrategy;
/*     */   private final Set<String> queued;
/*     */   private final CacheKeyGenerator cacheKeyGenerator;
/*     */   private final FailureCache failureCache;
/*  54 */   private final Log log = LogFactory.getLog(getClass());
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
/*     */   public AsynchronousValidator(CacheConfig config) {
/*  67 */     this(new ImmediateSchedulingStrategy(config));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   AsynchronousValidator(SchedulingStrategy schedulingStrategy) {
/*  78 */     this.schedulingStrategy = schedulingStrategy;
/*  79 */     this.queued = new HashSet<String>();
/*  80 */     this.cacheKeyGenerator = new CacheKeyGenerator();
/*  81 */     this.failureCache = new DefaultFailureCache();
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/*  86 */     this.schedulingStrategy.close();
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
/*     */   public synchronized void revalidateCacheEntry(CachingExec cachingExec, HttpRoute route, HttpRequestWrapper request, HttpClientContext context, HttpExecutionAware execAware, HttpCacheEntry entry) {
/* 100 */     String uri = this.cacheKeyGenerator.getVariantURI(context.getTargetHost(), (HttpRequest)request, entry);
/*     */     
/* 102 */     if (!this.queued.contains(uri)) {
/* 103 */       int consecutiveFailedAttempts = this.failureCache.getErrorCount(uri);
/* 104 */       AsynchronousValidationRequest revalidationRequest = new AsynchronousValidationRequest(this, cachingExec, route, request, context, execAware, entry, uri, consecutiveFailedAttempts);
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 109 */         this.schedulingStrategy.schedule(revalidationRequest);
/* 110 */         this.queued.add(uri);
/* 111 */       } catch (RejectedExecutionException ree) {
/* 112 */         this.log.debug("Revalidation for [" + uri + "] not scheduled: " + ree);
/*     */       } 
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
/*     */   synchronized void markComplete(String identifier) {
/* 125 */     this.queued.remove(identifier);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void jobSuccessful(String identifier) {
/* 135 */     this.failureCache.resetErrorCount(identifier);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void jobFailed(String identifier) {
/* 145 */     this.failureCache.increaseErrorCount(identifier);
/*     */   }
/*     */   
/*     */   Set<String> getScheduledIdentifiers() {
/* 149 */     return Collections.unmodifiableSet(this.queued);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/impl/client/cache/AsynchronousValidator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */