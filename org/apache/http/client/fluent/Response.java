/*     */ package org.apache.http.client.fluent;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import org.apache.http.HttpEntity;
/*     */ import org.apache.http.HttpResponse;
/*     */ import org.apache.http.StatusLine;
/*     */ import org.apache.http.client.ClientProtocolException;
/*     */ import org.apache.http.client.HttpResponseException;
/*     */ import org.apache.http.client.ResponseHandler;
/*     */ import org.apache.http.entity.ByteArrayEntity;
/*     */ import org.apache.http.entity.ContentType;
/*     */ import org.apache.http.util.EntityUtils;
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
/*     */ public class Response
/*     */ {
/*     */   private final HttpResponse response;
/*     */   private boolean consumed;
/*     */   
/*     */   Response(HttpResponse response) {
/*  51 */     this.response = response;
/*     */   }
/*     */   
/*     */   private void assertNotConsumed() {
/*  55 */     if (this.consumed) {
/*  56 */       throw new IllegalStateException("Response content has been already consumed");
/*     */     }
/*     */   }
/*     */   
/*     */   private void dispose() {
/*  61 */     if (this.consumed) {
/*     */       return;
/*     */     }
/*     */     
/*  65 */     try { HttpEntity entity = this.response.getEntity();
/*  66 */       InputStream content = entity.getContent();
/*  67 */       if (content != null) {
/*  68 */         content.close();
/*     */       } }
/*  70 */     catch (Exception ignore) {  }
/*     */     finally
/*  72 */     { this.consumed = true; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void discardContent() {
/*  80 */     dispose();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T handleResponse(ResponseHandler<T> handler) throws ClientProtocolException, IOException {
/*  88 */     assertNotConsumed();
/*     */     try {
/*  90 */       return (T)handler.handleResponse(this.response);
/*     */     } finally {
/*  92 */       dispose();
/*     */     } 
/*     */   }
/*     */   
/*     */   public Content returnContent() throws ClientProtocolException, IOException {
/*  97 */     return handleResponse((ResponseHandler<Content>)new ContentResponseHandler());
/*     */   }
/*     */   
/*     */   public HttpResponse returnResponse() throws IOException {
/* 101 */     assertNotConsumed();
/*     */     try {
/* 103 */       HttpEntity entity = this.response.getEntity();
/* 104 */       if (entity != null) {
/* 105 */         ByteArrayEntity byteArrayEntity = new ByteArrayEntity(EntityUtils.toByteArray(entity));
/*     */         
/* 107 */         ContentType contentType = ContentType.getOrDefault(entity);
/* 108 */         byteArrayEntity.setContentType(contentType.toString());
/* 109 */         this.response.setEntity((HttpEntity)byteArrayEntity);
/*     */       } 
/* 111 */       return this.response;
/*     */     } finally {
/* 113 */       this.consumed = true;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void saveContent(File file) throws IOException {
/* 118 */     assertNotConsumed();
/* 119 */     StatusLine statusLine = this.response.getStatusLine();
/* 120 */     if (statusLine.getStatusCode() >= 300) {
/* 121 */       throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
/*     */     }
/*     */     
/* 124 */     FileOutputStream out = new FileOutputStream(file);
/*     */     try {
/* 126 */       HttpEntity entity = this.response.getEntity();
/* 127 */       if (entity != null) {
/* 128 */         entity.writeTo(out);
/*     */       }
/*     */     } finally {
/* 131 */       this.consumed = true;
/* 132 */       out.close();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/client/fluent/Response.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */