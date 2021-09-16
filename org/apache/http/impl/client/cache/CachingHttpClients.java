/*    */ package org.apache.http.impl.client.cache;
/*    */ 
/*    */ import java.io.File;
/*    */ import org.apache.http.impl.client.CloseableHttpClient;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CachingHttpClients
/*    */ {
/*    */   public static CachingHttpClientBuilder custom() {
/* 51 */     return CachingHttpClientBuilder.create();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static CloseableHttpClient createMemoryBound() {
/* 59 */     return CachingHttpClientBuilder.create().build();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static CloseableHttpClient createFileBound(File cacheDir) {
/* 69 */     return CachingHttpClientBuilder.create().setCacheDir(cacheDir).build();
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/impl/client/cache/CachingHttpClients.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */