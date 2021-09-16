/*    */ package org.apache.http.client.cache;
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
/*    */ public class InputLimit
/*    */ {
/*    */   private final long value;
/*    */   private boolean reached;
/*    */   
/*    */   public InputLimit(long value) {
/* 47 */     this.value = value;
/* 48 */     this.reached = false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long getValue() {
/* 56 */     return this.value;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void reached() {
/* 63 */     this.reached = true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isReached() {
/* 70 */     return this.reached;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/client/cache/InputLimit.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */