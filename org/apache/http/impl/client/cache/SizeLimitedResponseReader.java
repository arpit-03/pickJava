/*     */ package org.apache.http.impl.client.cache;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.lang.reflect.Proxy;
/*     */ import org.apache.http.HttpEntity;
/*     */ import org.apache.http.HttpRequest;
/*     */ import org.apache.http.HttpResponse;
/*     */ import org.apache.http.client.cache.InputLimit;
/*     */ import org.apache.http.client.cache.Resource;
/*     */ import org.apache.http.client.cache.ResourceFactory;
/*     */ import org.apache.http.client.methods.CloseableHttpResponse;
/*     */ import org.apache.http.message.BasicHttpResponse;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class SizeLimitedResponseReader
/*     */ {
/*     */   private final ResourceFactory resourceFactory;
/*     */   private final long maxResponseSizeBytes;
/*     */   private final HttpRequest request;
/*     */   private final CloseableHttpResponse response;
/*     */   private InputStream inStream;
/*     */   private InputLimit limit;
/*     */   private Resource resource;
/*     */   private boolean consumed;
/*     */   
/*     */   public SizeLimitedResponseReader(ResourceFactory resourceFactory, long maxResponseSizeBytes, HttpRequest request, CloseableHttpResponse response) {
/*  67 */     this.resourceFactory = resourceFactory;
/*  68 */     this.maxResponseSizeBytes = maxResponseSizeBytes;
/*  69 */     this.request = request;
/*  70 */     this.response = response;
/*     */   }
/*     */   
/*     */   protected void readResponse() throws IOException {
/*  74 */     if (!this.consumed) {
/*  75 */       doConsume();
/*     */     }
/*     */   }
/*     */   
/*     */   private void ensureNotConsumed() {
/*  80 */     if (this.consumed) {
/*  81 */       throw new IllegalStateException("Response has already been consumed");
/*     */     }
/*     */   }
/*     */   
/*     */   private void ensureConsumed() {
/*  86 */     if (!this.consumed) {
/*  87 */       throw new IllegalStateException("Response has not been consumed");
/*     */     }
/*     */   }
/*     */   
/*     */   private void doConsume() throws IOException {
/*  92 */     ensureNotConsumed();
/*  93 */     this.consumed = true;
/*     */     
/*  95 */     this.limit = new InputLimit(this.maxResponseSizeBytes);
/*     */     
/*  97 */     HttpEntity entity = this.response.getEntity();
/*  98 */     if (entity == null) {
/*     */       return;
/*     */     }
/* 101 */     String uri = this.request.getRequestLine().getUri();
/* 102 */     this.inStream = entity.getContent();
/*     */     try {
/* 104 */       this.resource = this.resourceFactory.generate(uri, this.inStream, this.limit);
/*     */     } finally {
/* 106 */       if (!this.limit.isReached()) {
/* 107 */         this.inStream.close();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   boolean isLimitReached() {
/* 113 */     ensureConsumed();
/* 114 */     return this.limit.isReached();
/*     */   }
/*     */   
/*     */   Resource getResource() {
/* 118 */     ensureConsumed();
/* 119 */     return this.resource;
/*     */   }
/*     */   
/*     */   CloseableHttpResponse getReconstructedResponse() throws IOException {
/* 123 */     ensureConsumed();
/* 124 */     BasicHttpResponse basicHttpResponse = new BasicHttpResponse(this.response.getStatusLine());
/* 125 */     basicHttpResponse.setHeaders(this.response.getAllHeaders());
/*     */     
/* 127 */     CombinedEntity combinedEntity = new CombinedEntity(this.resource, this.inStream);
/* 128 */     HttpEntity entity = this.response.getEntity();
/* 129 */     if (entity != null) {
/* 130 */       combinedEntity.setContentType(entity.getContentType());
/* 131 */       combinedEntity.setContentEncoding(entity.getContentEncoding());
/* 132 */       combinedEntity.setChunked(entity.isChunked());
/*     */     } 
/* 134 */     basicHttpResponse.setEntity((HttpEntity)combinedEntity);
/* 135 */     return (CloseableHttpResponse)Proxy.newProxyInstance(ResponseProxyHandler.class.getClassLoader(), new Class[] { CloseableHttpResponse.class }, new ResponseProxyHandler((HttpResponse)basicHttpResponse)
/*     */         {
/*     */ 
/*     */ 
/*     */           
/*     */           public void close() throws IOException
/*     */           {
/* 142 */             SizeLimitedResponseReader.this.response.close();
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/impl/client/cache/SizeLimitedResponseReader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */