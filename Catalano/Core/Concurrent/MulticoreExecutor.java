/*     */ package Catalano.Core.Concurrent;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.concurrent.Executors;
/*     */ import java.util.concurrent.Future;
/*     */ import java.util.concurrent.ThreadPoolExecutor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MulticoreExecutor
/*     */ {
/*     */   private static final int nprocs;
/*  49 */   private static ThreadPoolExecutor threads = null;
/*     */   
/*     */   static {
/*  52 */     nprocs = Runtime.getRuntime().availableProcessors();
/*     */     
/*  54 */     if (nprocs > 1) {
/*  55 */       threads = (ThreadPoolExecutor)Executors.newFixedThreadPool(nprocs, new SimpleDeamonThreadFactory());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getThreadPoolSize() {
/*  64 */     return nprocs;
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
/*     */   public static <T> List<T> run(Collection<? extends Callable<T>> tasks) throws Exception {
/*  78 */     List<T> results = new ArrayList<>();
/*  79 */     if (threads == null) {
/*  80 */       for (Callable<T> task : tasks) {
/*  81 */         results.add(task.call());
/*     */       }
/*     */     }
/*  84 */     else if (threads.getActiveCount() < nprocs) {
/*  85 */       List<Future<T>> futures = threads.invokeAll(tasks);
/*  86 */       for (Future<T> future : futures) {
/*  87 */         results.add(future.get());
/*     */       }
/*     */     } else {
/*     */       
/*  91 */       for (Callable<T> task : tasks) {
/*  92 */         results.add(task.call());
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/*  97 */     return results;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void shutdown() {
/* 104 */     if (threads != null)
/* 105 */       threads.shutdown(); 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Core/Concurrent/MulticoreExecutor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */