/*    */ package org.apache.http.impl.client.cache;
/*    */ 
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.commons.logging.LogFactory;
/*    */ import org.apache.http.Header;
/*    */ import org.apache.http.HeaderElement;
/*    */ import org.apache.http.HttpRequest;
/*    */ import org.apache.http.HttpVersion;
/*    */ import org.apache.http.ProtocolVersion;
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
/*    */ 
/*    */ @Contract(threading = ThreadingBehavior.IMMUTABLE)
/*    */ class CacheableRequestPolicy
/*    */ {
/* 48 */   private final Log log = LogFactory.getLog(getClass());
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isServableFromCache(HttpRequest request) {
/* 58 */     String method = request.getRequestLine().getMethod();
/*    */     
/* 60 */     ProtocolVersion pv = request.getRequestLine().getProtocolVersion();
/* 61 */     if (HttpVersion.HTTP_1_1.compareToVersion(pv) != 0) {
/* 62 */       this.log.trace("non-HTTP/1.1 request was not serveable from cache");
/* 63 */       return false;
/*    */     } 
/*    */     
/* 66 */     if (!method.equals("GET") && !method.equals("HEAD")) {
/*    */       
/* 68 */       this.log.trace("non-GET or non-HEAD request was not serveable from cache");
/* 69 */       return false;
/*    */     } 
/*    */     
/* 72 */     if ((request.getHeaders("Pragma")).length > 0) {
/* 73 */       this.log.trace("request with Pragma header was not serveable from cache");
/* 74 */       return false;
/*    */     } 
/*    */     
/* 77 */     Header[] cacheControlHeaders = request.getHeaders("Cache-Control");
/* 78 */     for (Header cacheControl : cacheControlHeaders) {
/* 79 */       for (HeaderElement cacheControlElement : cacheControl.getElements()) {
/* 80 */         if ("no-store".equalsIgnoreCase(cacheControlElement.getName())) {
/*    */           
/* 82 */           this.log.trace("Request with no-store was not serveable from cache");
/* 83 */           return false;
/*    */         } 
/*    */         
/* 86 */         if ("no-cache".equalsIgnoreCase(cacheControlElement.getName())) {
/*    */           
/* 88 */           this.log.trace("Request with no-cache was not serveable from cache");
/* 89 */           return false;
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 94 */     this.log.trace("Request was serveable from cache");
/* 95 */     return true;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/impl/client/cache/CacheableRequestPolicy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */