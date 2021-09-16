/*     */ package org.apache.http.impl.client.cache;
/*     */ 
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
/*     */ public class CacheConfig
/*     */   implements Cloneable
/*     */ {
/*     */   public static final int DEFAULT_MAX_OBJECT_SIZE_BYTES = 8192;
/*     */   public static final int DEFAULT_MAX_CACHE_ENTRIES = 1000;
/*     */   public static final int DEFAULT_MAX_UPDATE_RETRIES = 1;
/*     */   public static final boolean DEFAULT_303_CACHING_ENABLED = false;
/*     */   public static final boolean DEFAULT_WEAK_ETAG_ON_PUTDELETE_ALLOWED = false;
/*     */   public static final boolean DEFAULT_HEURISTIC_CACHING_ENABLED = false;
/*     */   public static final float DEFAULT_HEURISTIC_COEFFICIENT = 0.1F;
/*     */   public static final long DEFAULT_HEURISTIC_LIFETIME = 0L;
/*     */   public static final int DEFAULT_ASYNCHRONOUS_WORKERS_MAX = 1;
/*     */   public static final int DEFAULT_ASYNCHRONOUS_WORKERS_CORE = 1;
/*     */   public static final int DEFAULT_ASYNCHRONOUS_WORKER_IDLE_LIFETIME_SECS = 60;
/*     */   public static final int DEFAULT_REVALIDATION_QUEUE_SIZE = 100;
/* 161 */   public static final CacheConfig DEFAULT = (new Builder()).build();
/*     */   
/*     */   private long maxObjectSize;
/*     */   
/*     */   private int maxCacheEntries;
/*     */   
/*     */   private int maxUpdateRetries;
/*     */   
/*     */   private final boolean allow303Caching;
/*     */   
/*     */   private final boolean weakETagOnPutDeleteAllowed;
/*     */   
/*     */   private boolean heuristicCachingEnabled;
/*     */   private float heuristicCoefficient;
/*     */   private long heuristicDefaultLifetime;
/*     */   private boolean isSharedCache;
/*     */   private int asynchronousWorkersMax;
/*     */   private int asynchronousWorkersCore;
/*     */   private int asynchronousWorkerIdleLifetimeSecs;
/*     */   private int revalidationQueueSize;
/*     */   private boolean neverCacheHTTP10ResponsesWithQuery;
/*     */   
/*     */   @Deprecated
/*     */   public CacheConfig() {
/* 185 */     this.maxObjectSize = 8192L;
/* 186 */     this.maxCacheEntries = 1000;
/* 187 */     this.maxUpdateRetries = 1;
/* 188 */     this.allow303Caching = false;
/* 189 */     this.weakETagOnPutDeleteAllowed = false;
/* 190 */     this.heuristicCachingEnabled = false;
/* 191 */     this.heuristicCoefficient = 0.1F;
/* 192 */     this.heuristicDefaultLifetime = 0L;
/* 193 */     this.isSharedCache = true;
/* 194 */     this.asynchronousWorkersMax = 1;
/* 195 */     this.asynchronousWorkersCore = 1;
/* 196 */     this.asynchronousWorkerIdleLifetimeSecs = 60;
/* 197 */     this.revalidationQueueSize = 100;
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
/*     */   CacheConfig(long maxObjectSize, int maxCacheEntries, int maxUpdateRetries, boolean allow303Caching, boolean weakETagOnPutDeleteAllowed, boolean heuristicCachingEnabled, float heuristicCoefficient, long heuristicDefaultLifetime, boolean isSharedCache, int asynchronousWorkersMax, int asynchronousWorkersCore, int asynchronousWorkerIdleLifetimeSecs, int revalidationQueueSize, boolean neverCacheHTTP10ResponsesWithQuery) {
/* 216 */     this.maxObjectSize = maxObjectSize;
/* 217 */     this.maxCacheEntries = maxCacheEntries;
/* 218 */     this.maxUpdateRetries = maxUpdateRetries;
/* 219 */     this.allow303Caching = allow303Caching;
/* 220 */     this.weakETagOnPutDeleteAllowed = weakETagOnPutDeleteAllowed;
/* 221 */     this.heuristicCachingEnabled = heuristicCachingEnabled;
/* 222 */     this.heuristicCoefficient = heuristicCoefficient;
/* 223 */     this.heuristicDefaultLifetime = heuristicDefaultLifetime;
/* 224 */     this.isSharedCache = isSharedCache;
/* 225 */     this.asynchronousWorkersMax = asynchronousWorkersMax;
/* 226 */     this.asynchronousWorkersCore = asynchronousWorkersCore;
/* 227 */     this.asynchronousWorkerIdleLifetimeSecs = asynchronousWorkerIdleLifetimeSecs;
/* 228 */     this.revalidationQueueSize = revalidationQueueSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public int getMaxObjectSizeBytes() {
/* 239 */     return (this.maxObjectSize > 2147483647L) ? Integer.MAX_VALUE : (int)this.maxObjectSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void setMaxObjectSizeBytes(int maxObjectSizeBytes) {
/* 250 */     if (maxObjectSizeBytes > Integer.MAX_VALUE) {
/* 251 */       this.maxObjectSize = 2147483647L;
/*     */     } else {
/* 253 */       this.maxObjectSize = maxObjectSizeBytes;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getMaxObjectSize() {
/* 264 */     return this.maxObjectSize;
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
/*     */   @Deprecated
/*     */   public void setMaxObjectSize(long maxObjectSize) {
/* 277 */     this.maxObjectSize = maxObjectSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isNeverCacheHTTP10ResponsesWithQuery() {
/* 286 */     return this.neverCacheHTTP10ResponsesWithQuery;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxCacheEntries() {
/* 293 */     return this.maxCacheEntries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void setMaxCacheEntries(int maxCacheEntries) {
/* 303 */     this.maxCacheEntries = maxCacheEntries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxUpdateRetries() {
/* 310 */     return this.maxUpdateRetries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void setMaxUpdateRetries(int maxUpdateRetries) {
/* 320 */     this.maxUpdateRetries = maxUpdateRetries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean is303CachingEnabled() {
/* 328 */     return this.allow303Caching;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isWeakETagOnPutDeleteAllowed() {
/* 336 */     return this.weakETagOnPutDeleteAllowed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isHeuristicCachingEnabled() {
/* 344 */     return this.heuristicCachingEnabled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void setHeuristicCachingEnabled(boolean heuristicCachingEnabled) {
/* 356 */     this.heuristicCachingEnabled = heuristicCachingEnabled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getHeuristicCoefficient() {
/* 363 */     return this.heuristicCoefficient;
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
/*     */   @Deprecated
/*     */   public void setHeuristicCoefficient(float heuristicCoefficient) {
/* 378 */     this.heuristicCoefficient = heuristicCoefficient;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getHeuristicDefaultLifetime() {
/* 386 */     return this.heuristicDefaultLifetime;
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
/*     */   @Deprecated
/*     */   public void setHeuristicDefaultLifetime(long heuristicDefaultLifetimeSecs) {
/* 404 */     this.heuristicDefaultLifetime = heuristicDefaultLifetimeSecs;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSharedCache() {
/* 413 */     return this.isSharedCache;
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
/*     */   @Deprecated
/*     */   public void setSharedCache(boolean isSharedCache) {
/* 426 */     this.isSharedCache = isSharedCache;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getAsynchronousWorkersMax() {
/* 435 */     return this.asynchronousWorkersMax;
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
/*     */   @Deprecated
/*     */   public void setAsynchronousWorkersMax(int max) {
/* 448 */     this.asynchronousWorkersMax = max;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getAsynchronousWorkersCore() {
/* 456 */     return this.asynchronousWorkersCore;
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
/*     */   @Deprecated
/*     */   public void setAsynchronousWorkersCore(int min) {
/* 469 */     this.asynchronousWorkersCore = min;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getAsynchronousWorkerIdleLifetimeSecs() {
/* 479 */     return this.asynchronousWorkerIdleLifetimeSecs;
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
/*     */   @Deprecated
/*     */   public void setAsynchronousWorkerIdleLifetimeSecs(int secs) {
/* 493 */     this.asynchronousWorkerIdleLifetimeSecs = secs;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRevalidationQueueSize() {
/* 500 */     return this.revalidationQueueSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void setRevalidationQueueSize(int size) {
/* 510 */     this.revalidationQueueSize = size;
/*     */   }
/*     */ 
/*     */   
/*     */   protected CacheConfig clone() throws CloneNotSupportedException {
/* 515 */     return (CacheConfig)super.clone();
/*     */   }
/*     */   
/*     */   public static Builder custom() {
/* 519 */     return new Builder();
/*     */   }
/*     */   
/*     */   public static Builder copy(CacheConfig config) {
/* 523 */     Args.notNull(config, "Cache config");
/* 524 */     return (new Builder()).setMaxObjectSize(config.getMaxObjectSize()).setMaxCacheEntries(config.getMaxCacheEntries()).setMaxUpdateRetries(config.getMaxUpdateRetries()).setHeuristicCachingEnabled(config.isHeuristicCachingEnabled()).setHeuristicCoefficient(config.getHeuristicCoefficient()).setHeuristicDefaultLifetime(config.getHeuristicDefaultLifetime()).setSharedCache(config.isSharedCache()).setAsynchronousWorkersMax(config.getAsynchronousWorkersMax()).setAsynchronousWorkersCore(config.getAsynchronousWorkersCore()).setAsynchronousWorkerIdleLifetimeSecs(config.getAsynchronousWorkerIdleLifetimeSecs()).setRevalidationQueueSize(config.getRevalidationQueueSize()).setNeverCacheHTTP10ResponsesWithQueryString(config.isNeverCacheHTTP10ResponsesWithQuery());
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
/*     */   public static class Builder
/*     */   {
/* 558 */     private long maxObjectSize = 8192L;
/* 559 */     private int maxCacheEntries = 1000;
/* 560 */     private int maxUpdateRetries = 1;
/*     */     private boolean allow303Caching = false;
/*     */     private boolean weakETagOnPutDeleteAllowed = false;
/*     */     private boolean heuristicCachingEnabled = false;
/* 564 */     private float heuristicCoefficient = 0.1F;
/* 565 */     private long heuristicDefaultLifetime = 0L;
/*     */     private boolean isSharedCache = true;
/* 567 */     private int asynchronousWorkersMax = 1;
/* 568 */     private int asynchronousWorkersCore = 1;
/* 569 */     private int asynchronousWorkerIdleLifetimeSecs = 60;
/* 570 */     private int revalidationQueueSize = 100;
/*     */ 
/*     */     
/*     */     private boolean neverCacheHTTP10ResponsesWithQuery;
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setMaxObjectSize(long maxObjectSize) {
/* 578 */       this.maxObjectSize = maxObjectSize;
/* 579 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setMaxCacheEntries(int maxCacheEntries) {
/* 586 */       this.maxCacheEntries = maxCacheEntries;
/* 587 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setMaxUpdateRetries(int maxUpdateRetries) {
/* 594 */       this.maxUpdateRetries = maxUpdateRetries;
/* 595 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setAllow303Caching(boolean allow303Caching) {
/* 604 */       this.allow303Caching = allow303Caching;
/* 605 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setWeakETagOnPutDeleteAllowed(boolean weakETagOnPutDeleteAllowed) {
/* 614 */       this.weakETagOnPutDeleteAllowed = weakETagOnPutDeleteAllowed;
/* 615 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setHeuristicCachingEnabled(boolean heuristicCachingEnabled) {
/* 624 */       this.heuristicCachingEnabled = heuristicCachingEnabled;
/* 625 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setHeuristicCoefficient(float heuristicCoefficient) {
/* 637 */       this.heuristicCoefficient = heuristicCoefficient;
/* 638 */       return this;
/*     */     }
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
/*     */     public Builder setHeuristicDefaultLifetime(long heuristicDefaultLifetime) {
/* 653 */       this.heuristicDefaultLifetime = heuristicDefaultLifetime;
/* 654 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setSharedCache(boolean isSharedCache) {
/* 664 */       this.isSharedCache = isSharedCache;
/* 665 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setAsynchronousWorkersMax(int asynchronousWorkersMax) {
/* 675 */       this.asynchronousWorkersMax = asynchronousWorkersMax;
/* 676 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setAsynchronousWorkersCore(int asynchronousWorkersCore) {
/* 686 */       this.asynchronousWorkersCore = asynchronousWorkersCore;
/* 687 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setAsynchronousWorkerIdleLifetimeSecs(int asynchronousWorkerIdleLifetimeSecs) {
/* 698 */       this.asynchronousWorkerIdleLifetimeSecs = asynchronousWorkerIdleLifetimeSecs;
/* 699 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setRevalidationQueueSize(int revalidationQueueSize) {
/* 706 */       this.revalidationQueueSize = revalidationQueueSize;
/* 707 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setNeverCacheHTTP10ResponsesWithQueryString(boolean neverCacheHTTP10ResponsesWithQuery) {
/* 719 */       this.neverCacheHTTP10ResponsesWithQuery = neverCacheHTTP10ResponsesWithQuery;
/* 720 */       return this;
/*     */     }
/*     */     
/*     */     public CacheConfig build() {
/* 724 */       return new CacheConfig(this.maxObjectSize, this.maxCacheEntries, this.maxUpdateRetries, this.allow303Caching, this.weakETagOnPutDeleteAllowed, this.heuristicCachingEnabled, this.heuristicCoefficient, this.heuristicDefaultLifetime, this.isSharedCache, this.asynchronousWorkersMax, this.asynchronousWorkersCore, this.asynchronousWorkerIdleLifetimeSecs, this.revalidationQueueSize, this.neverCacheHTTP10ResponsesWithQuery);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 745 */     StringBuilder builder = new StringBuilder();
/* 746 */     builder.append("[maxObjectSize=").append(this.maxObjectSize).append(", maxCacheEntries=").append(this.maxCacheEntries).append(", maxUpdateRetries=").append(this.maxUpdateRetries).append(", 303CachingEnabled=").append(this.allow303Caching).append(", weakETagOnPutDeleteAllowed=").append(this.weakETagOnPutDeleteAllowed).append(", heuristicCachingEnabled=").append(this.heuristicCachingEnabled).append(", heuristicCoefficient=").append(this.heuristicCoefficient).append(", heuristicDefaultLifetime=").append(this.heuristicDefaultLifetime).append(", isSharedCache=").append(this.isSharedCache).append(", asynchronousWorkersMax=").append(this.asynchronousWorkersMax).append(", asynchronousWorkersCore=").append(this.asynchronousWorkersCore).append(", asynchronousWorkerIdleLifetimeSecs=").append(this.asynchronousWorkerIdleLifetimeSecs).append(", revalidationQueueSize=").append(this.revalidationQueueSize).append(", neverCacheHTTP10ResponsesWithQuery=").append(this.neverCacheHTTP10ResponsesWithQuery).append("]");
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
/* 761 */     return builder.toString();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/impl/client/cache/CacheConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */