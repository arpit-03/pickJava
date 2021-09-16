/*    */ package org.apache.http.impl.client.cache;
/*    */ 
/*    */ import java.util.concurrent.ArrayBlockingQueue;
/*    */ import java.util.concurrent.ExecutorService;
/*    */ import java.util.concurrent.ThreadPoolExecutor;
/*    */ import java.util.concurrent.TimeUnit;
/*    */ import org.apache.http.annotation.Contract;
/*    */ import org.apache.http.annotation.ThreadingBehavior;
/*    */ import org.apache.http.util.Args;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Contract(threading = ThreadingBehavior.SAFE)
/*    */ public class ImmediateSchedulingStrategy
/*    */   implements SchedulingStrategy
/*    */ {
/*    */   private final ExecutorService executor;
/*    */   
/*    */   public ImmediateSchedulingStrategy(CacheConfig cacheConfig) {
/* 59 */     this(new ThreadPoolExecutor(cacheConfig.getAsynchronousWorkersCore(), cacheConfig.getAsynchronousWorkersMax(), cacheConfig.getAsynchronousWorkerIdleLifetimeSecs(), TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(cacheConfig.getRevalidationQueueSize())));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   ImmediateSchedulingStrategy(ExecutorService executor) {
/* 69 */     this.executor = executor;
/*    */   }
/*    */ 
/*    */   
/*    */   public void schedule(AsynchronousValidationRequest revalidationRequest) {
/* 74 */     Args.notNull(revalidationRequest, "AsynchronousValidationRequest");
/* 75 */     this.executor.execute(revalidationRequest);
/*    */   }
/*    */ 
/*    */   
/*    */   public void close() {
/* 80 */     this.executor.shutdown();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   void awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
/* 87 */     this.executor.awaitTermination(timeout, unit);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/impl/client/cache/ImmediateSchedulingStrategy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */