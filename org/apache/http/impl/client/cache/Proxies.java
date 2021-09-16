/*    */ package org.apache.http.impl.client.cache;
/*    */ 
/*    */ import java.lang.reflect.Proxy;
/*    */ import org.apache.http.HttpResponse;
/*    */ import org.apache.http.client.methods.CloseableHttpResponse;
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
/*    */ class Proxies
/*    */ {
/*    */   public static CloseableHttpResponse enhanceResponse(HttpResponse original) {
/* 43 */     Args.notNull(original, "HTTP response");
/* 44 */     if (original instanceof CloseableHttpResponse) {
/* 45 */       return (CloseableHttpResponse)original;
/*    */     }
/* 47 */     return (CloseableHttpResponse)Proxy.newProxyInstance(ResponseProxyHandler.class.getClassLoader(), new Class[] { CloseableHttpResponse.class }, new ResponseProxyHandler(original));
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/impl/client/cache/Proxies.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */