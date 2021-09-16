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
/*    */ public class HttpCacheUpdateException
/*    */   extends Exception
/*    */ {
/*    */   private static final long serialVersionUID = 823573584868632876L;
/*    */   
/*    */   public HttpCacheUpdateException(String message) {
/* 40 */     super(message);
/*    */   }
/*    */   
/*    */   public HttpCacheUpdateException(String message, Throwable cause) {
/* 44 */     super(message);
/* 45 */     initCause(cause);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/client/cache/HttpCacheUpdateException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */