/*    */ package us.hebi.matlab.mat.util;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.concurrent.Callable;
/*    */ import java.util.concurrent.Future;
/*    */ import java.util.concurrent.TimeUnit;
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
/*    */ public class Tasks
/*    */ {
/*    */   public static <T> Future<T> wrapAsFuture(T result) {
/* 34 */     return new PrecomputedFuture<>(result, null);
/*    */   }
/*    */   
/*    */   public static interface IoTask<V> extends Callable<V> {
/*    */     V call() throws IOException;
/*    */   }
/*    */   
/*    */   private static class PrecomputedFuture<T> implements Future<T> {
/*    */     final T result;
/*    */     
/*    */     private PrecomputedFuture(T result) {
/* 45 */       this.result = result;
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     public boolean cancel(boolean mayInterruptIfRunning) {
/* 52 */       return false;
/*    */     }
/*    */ 
/*    */     
/*    */     public boolean isCancelled() {
/* 57 */       return false;
/*    */     }
/*    */ 
/*    */     
/*    */     public boolean isDone() {
/* 62 */       return true;
/*    */     }
/*    */ 
/*    */     
/*    */     public T get() {
/* 67 */       return this.result;
/*    */     }
/*    */ 
/*    */     
/*    */     public T get(long timeout, TimeUnit unit) {
/* 72 */       return this.result;
/*    */     }
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/us/hebi/matlab/mat/util/Tasks.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */