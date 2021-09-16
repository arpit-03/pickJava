/*     */ package org.apache.http.impl.client.cache;
/*     */ 
/*     */ import java.io.Closeable;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import org.apache.http.client.cache.HttpCacheInvalidator;
/*     */ import org.apache.http.client.cache.HttpCacheStorage;
/*     */ import org.apache.http.client.cache.ResourceFactory;
/*     */ import org.apache.http.impl.client.HttpClientBuilder;
/*     */ import org.apache.http.impl.execchain.ClientExecChain;
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
/*     */ public class CachingHttpClientBuilder
/*     */   extends HttpClientBuilder
/*     */ {
/*     */   private ResourceFactory resourceFactory;
/*     */   private HttpCacheStorage storage;
/*     */   private File cacheDir;
/*     */   private CacheConfig cacheConfig;
/*     */   private SchedulingStrategy schedulingStrategy;
/*     */   private HttpCacheInvalidator httpCacheInvalidator;
/*     */   private boolean deleteCache;
/*     */   
/*     */   public static CachingHttpClientBuilder create() {
/*  56 */     return new CachingHttpClientBuilder();
/*     */   }
/*     */ 
/*     */   
/*     */   protected CachingHttpClientBuilder() {
/*  61 */     this.deleteCache = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public final CachingHttpClientBuilder setResourceFactory(ResourceFactory resourceFactory) {
/*  66 */     this.resourceFactory = resourceFactory;
/*  67 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public final CachingHttpClientBuilder setHttpCacheStorage(HttpCacheStorage storage) {
/*  72 */     this.storage = storage;
/*  73 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public final CachingHttpClientBuilder setCacheDir(File cacheDir) {
/*  78 */     this.cacheDir = cacheDir;
/*  79 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public final CachingHttpClientBuilder setCacheConfig(CacheConfig cacheConfig) {
/*  84 */     this.cacheConfig = cacheConfig;
/*  85 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public final CachingHttpClientBuilder setSchedulingStrategy(SchedulingStrategy schedulingStrategy) {
/*  90 */     this.schedulingStrategy = schedulingStrategy;
/*  91 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public final CachingHttpClientBuilder setHttpCacheInvalidator(HttpCacheInvalidator cacheInvalidator) {
/*  96 */     this.httpCacheInvalidator = cacheInvalidator;
/*  97 */     return this;
/*     */   }
/*     */   
/*     */   public CachingHttpClientBuilder setDeleteCache(boolean deleteCache) {
/* 101 */     this.deleteCache = deleteCache;
/* 102 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected ClientExecChain decorateMainExec(ClientExecChain mainExec) {
/* 107 */     CacheConfig config = (this.cacheConfig != null) ? this.cacheConfig : CacheConfig.DEFAULT;
/*     */     
/* 109 */     ResourceFactory resourceFactoryCopy = this.resourceFactory;
/* 110 */     if (resourceFactoryCopy == null) {
/* 111 */       if (this.cacheDir == null) {
/* 112 */         resourceFactoryCopy = new HeapResourceFactory();
/*     */       } else {
/* 114 */         resourceFactoryCopy = new FileResourceFactory(this.cacheDir);
/*     */       } 
/*     */     }
/* 117 */     HttpCacheStorage storageCopy = this.storage;
/* 118 */     if (storageCopy == null) {
/* 119 */       if (this.cacheDir == null) {
/* 120 */         storageCopy = new BasicHttpCacheStorage(config);
/*     */       } else {
/* 122 */         final ManagedHttpCacheStorage managedStorage = new ManagedHttpCacheStorage(config);
/* 123 */         if (this.deleteCache) {
/* 124 */           addCloseable(new Closeable()
/*     */               {
/*     */                 public void close() throws IOException
/*     */                 {
/* 128 */                   managedStorage.shutdown();
/*     */                 }
/*     */               });
/*     */         } else {
/*     */           
/* 133 */           addCloseable(managedStorage);
/*     */         } 
/* 135 */         storageCopy = managedStorage;
/*     */       } 
/*     */     }
/* 138 */     AsynchronousValidator revalidator = createAsynchronousRevalidator(config);
/* 139 */     CacheKeyGenerator uriExtractor = new CacheKeyGenerator();
/*     */     
/* 141 */     HttpCacheInvalidator cacheInvalidator = this.httpCacheInvalidator;
/* 142 */     if (cacheInvalidator == null) {
/* 143 */       cacheInvalidator = new CacheInvalidator(uriExtractor, storageCopy);
/*     */     }
/*     */     
/* 146 */     return new CachingExec(mainExec, new BasicHttpCache(resourceFactoryCopy, storageCopy, config, uriExtractor, cacheInvalidator), config, revalidator);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private AsynchronousValidator createAsynchronousRevalidator(CacheConfig config) {
/* 155 */     if (config.getAsynchronousWorkersMax() > 0) {
/* 156 */       SchedulingStrategy configuredSchedulingStrategy = createSchedulingStrategy(config);
/* 157 */       AsynchronousValidator revalidator = new AsynchronousValidator(configuredSchedulingStrategy);
/*     */       
/* 159 */       addCloseable(revalidator);
/* 160 */       return revalidator;
/*     */     } 
/* 162 */     return null;
/*     */   }
/*     */   
/*     */   private SchedulingStrategy createSchedulingStrategy(CacheConfig config) {
/* 166 */     return (this.schedulingStrategy != null) ? this.schedulingStrategy : new ImmediateSchedulingStrategy(config);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/impl/client/cache/CachingHttpClientBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */