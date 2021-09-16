/*     */ package org.apache.http.impl.client.cache;
/*     */ 
/*     */ import java.util.concurrent.ScheduledExecutorService;
/*     */ import java.util.concurrent.ScheduledThreadPoolExecutor;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import org.apache.http.annotation.Contract;
/*     */ import org.apache.http.annotation.ThreadingBehavior;
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
/*     */ @Contract(threading = ThreadingBehavior.SAFE)
/*     */ public class ExponentialBackOffSchedulingStrategy
/*     */   implements SchedulingStrategy
/*     */ {
/*     */   public static final long DEFAULT_BACK_OFF_RATE = 10L;
/*  64 */   public static final long DEFAULT_INITIAL_EXPIRY_IN_MILLIS = TimeUnit.SECONDS.toMillis(6L);
/*  65 */   public static final long DEFAULT_MAX_EXPIRY_IN_MILLIS = TimeUnit.SECONDS.toMillis(86400L);
/*     */ 
/*     */   
/*     */   private final long backOffRate;
/*     */ 
/*     */   
/*     */   private final long initialExpiryInMillis;
/*     */ 
/*     */   
/*     */   private final long maxExpiryInMillis;
/*     */ 
/*     */   
/*     */   private final ScheduledExecutorService executor;
/*     */ 
/*     */ 
/*     */   
/*     */   public ExponentialBackOffSchedulingStrategy(CacheConfig cacheConfig) {
/*  82 */     this(cacheConfig, 10L, DEFAULT_INITIAL_EXPIRY_IN_MILLIS, DEFAULT_MAX_EXPIRY_IN_MILLIS);
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
/*     */   public ExponentialBackOffSchedulingStrategy(CacheConfig cacheConfig, long backOffRate, long initialExpiryInMillis, long maxExpiryInMillis) {
/* 104 */     this(createThreadPoolFromCacheConfig(cacheConfig), backOffRate, initialExpiryInMillis, maxExpiryInMillis);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static ScheduledThreadPoolExecutor createThreadPoolFromCacheConfig(CacheConfig cacheConfig) {
/* 112 */     ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(cacheConfig.getAsynchronousWorkersMax());
/*     */     
/* 114 */     scheduledThreadPoolExecutor.setExecuteExistingDelayedTasksAfterShutdownPolicy(false);
/* 115 */     return scheduledThreadPoolExecutor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ExponentialBackOffSchedulingStrategy(ScheduledExecutorService executor, long backOffRate, long initialExpiryInMillis, long maxExpiryInMillis) {
/* 123 */     this.executor = (ScheduledExecutorService)Args.notNull(executor, "Executor");
/* 124 */     this.backOffRate = Args.notNegative(backOffRate, "BackOffRate");
/* 125 */     this.initialExpiryInMillis = Args.notNegative(initialExpiryInMillis, "InitialExpiryInMillis");
/* 126 */     this.maxExpiryInMillis = Args.notNegative(maxExpiryInMillis, "MaxExpiryInMillis");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void schedule(AsynchronousValidationRequest revalidationRequest) {
/* 132 */     Args.notNull(revalidationRequest, "RevalidationRequest");
/* 133 */     int consecutiveFailedAttempts = revalidationRequest.getConsecutiveFailedAttempts();
/* 134 */     long delayInMillis = calculateDelayInMillis(consecutiveFailedAttempts);
/* 135 */     this.executor.schedule(revalidationRequest, delayInMillis, TimeUnit.MILLISECONDS);
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() {
/* 140 */     this.executor.shutdown();
/*     */   }
/*     */   
/*     */   public long getBackOffRate() {
/* 144 */     return this.backOffRate;
/*     */   }
/*     */   
/*     */   public long getInitialExpiryInMillis() {
/* 148 */     return this.initialExpiryInMillis;
/*     */   }
/*     */   
/*     */   public long getMaxExpiryInMillis() {
/* 152 */     return this.maxExpiryInMillis;
/*     */   }
/*     */   
/*     */   protected long calculateDelayInMillis(int consecutiveFailedAttempts) {
/* 156 */     if (consecutiveFailedAttempts > 0) {
/* 157 */       long delayInSeconds = (long)(this.initialExpiryInMillis * Math.pow(this.backOffRate, (consecutiveFailedAttempts - 1)));
/*     */       
/* 159 */       return Math.min(delayInSeconds, this.maxExpiryInMillis);
/*     */     } 
/*     */     
/* 162 */     return 0L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected static <T> T checkNotNull(String parameterName, T value) {
/* 171 */     if (value == null) {
/* 172 */       throw new IllegalArgumentException(parameterName + " may not be null");
/*     */     }
/* 174 */     return value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected static long checkNotNegative(String parameterName, long value) {
/* 182 */     if (value < 0L) {
/* 183 */       throw new IllegalArgumentException(parameterName + " may not be negative");
/*     */     }
/* 185 */     return value;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/impl/client/cache/ExponentialBackOffSchedulingStrategy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */