/*    */ package org.apache.http.client.fluent;
/*    */ 
/*    */ import java.net.URI;
/*    */ import org.apache.http.Header;
/*    */ import org.apache.http.HttpEntity;
/*    */ import org.apache.http.HttpEntityEnclosingRequest;
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
/*    */ class InternalEntityEnclosingHttpRequest
/*    */   extends InternalHttpRequest
/*    */   implements HttpEntityEnclosingRequest
/*    */ {
/*    */   private HttpEntity entity;
/*    */   
/*    */   public InternalEntityEnclosingHttpRequest(String method, URI requestURI) {
/* 42 */     super(method, requestURI);
/*    */   }
/*    */ 
/*    */   
/*    */   public HttpEntity getEntity() {
/* 47 */     return this.entity;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setEntity(HttpEntity entity) {
/* 52 */     this.entity = entity;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean expectContinue() {
/* 57 */     Header expect = getFirstHeader("Expect");
/* 58 */     return (expect != null && "100-continue".equalsIgnoreCase(expect.getValue()));
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/client/fluent/InternalEntityEnclosingHttpRequest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */