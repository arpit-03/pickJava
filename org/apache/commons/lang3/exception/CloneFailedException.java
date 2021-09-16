/*    */ package org.apache.commons.lang3.exception;
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
/*    */ public class CloneFailedException
/*    */   extends RuntimeException
/*    */ {
/*    */   private static final long serialVersionUID = 20091223L;
/*    */   
/*    */   public CloneFailedException(String message) {
/* 39 */     super(message);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public CloneFailedException(Throwable cause) {
/* 49 */     super(cause);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public CloneFailedException(String message, Throwable cause) {
/* 60 */     super(message, cause);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/lang3/exception/CloneFailedException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */