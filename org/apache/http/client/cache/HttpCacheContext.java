/*    */ package org.apache.http.client.cache;
/*    */ 
/*    */ import org.apache.http.client.protocol.HttpClientContext;
/*    */ import org.apache.http.protocol.BasicHttpContext;
/*    */ import org.apache.http.protocol.HttpContext;
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
/*    */ public class HttpCacheContext
/*    */   extends HttpClientContext
/*    */ {
/*    */   public static final String CACHE_RESPONSE_STATUS = "http.cache.response.status";
/*    */   
/*    */   public static HttpCacheContext adapt(HttpContext context) {
/* 46 */     if (context instanceof HttpCacheContext) {
/* 47 */       return (HttpCacheContext)context;
/*    */     }
/* 49 */     return new HttpCacheContext(context);
/*    */   }
/*    */ 
/*    */   
/*    */   public static HttpCacheContext create() {
/* 54 */     return new HttpCacheContext((HttpContext)new BasicHttpContext());
/*    */   }
/*    */   
/*    */   public HttpCacheContext(HttpContext context) {
/* 58 */     super(context);
/*    */   }
/*    */ 
/*    */   
/*    */   public HttpCacheContext() {}
/*    */ 
/*    */   
/*    */   public CacheResponseStatus getCacheResponseStatus() {
/* 66 */     return (CacheResponseStatus)getAttribute("http.cache.response.status", CacheResponseStatus.class);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/client/cache/HttpCacheContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */