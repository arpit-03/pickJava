/*    */ package org.apache.http.client.cache;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HttpCacheEntrySerializationException
/*    */   extends IOException
/*    */ {
/*    */   private static final long serialVersionUID = 9219188365878433519L;
/*    */   
/*    */   public HttpCacheEntrySerializationException(String message) {}
/*    */   
/*    */   public HttpCacheEntrySerializationException(String message, Throwable cause) {
/* 44 */     super(message);
/* 45 */     initCause(cause);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/client/cache/HttpCacheEntrySerializationException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */