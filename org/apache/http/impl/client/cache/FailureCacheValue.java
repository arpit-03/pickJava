/*    */ package org.apache.http.impl.client.cache;
/*    */ 
/*    */ import org.apache.http.annotation.Contract;
/*    */ import org.apache.http.annotation.ThreadingBehavior;
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
/*    */ @Contract(threading = ThreadingBehavior.IMMUTABLE)
/*    */ public class FailureCacheValue
/*    */ {
/*    */   private final long creationTimeInNanos;
/*    */   private final String key;
/*    */   private final int errorCount;
/*    */   
/*    */   public FailureCacheValue(String key, int errorCount) {
/* 45 */     this.creationTimeInNanos = System.nanoTime();
/* 46 */     this.key = key;
/* 47 */     this.errorCount = errorCount;
/*    */   }
/*    */   
/*    */   public long getCreationTimeInNanos() {
/* 51 */     return this.creationTimeInNanos;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getKey() {
/* 56 */     return this.key;
/*    */   }
/*    */   
/*    */   public int getErrorCount() {
/* 60 */     return this.errorCount;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 65 */     return "[entry creationTimeInNanos=" + this.creationTimeInNanos + "; " + "key=" + this.key + "; errorCount=" + this.errorCount + ']';
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/impl/client/cache/FailureCacheValue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */