/*    */ package org.apache.http.impl.client.cache.memcached;
/*    */ 
/*    */ import java.io.IOException;
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
/*    */ class MemcachedOperationTimeoutException
/*    */   extends IOException
/*    */ {
/*    */   private static final long serialVersionUID = 1608334789051537010L;
/*    */   
/*    */   public MemcachedOperationTimeoutException(Throwable cause) {
/* 39 */     super(cause.getMessage());
/* 40 */     initCause(cause);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/impl/client/cache/memcached/MemcachedOperationTimeoutException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */