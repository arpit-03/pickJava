/*    */ package org.apache.http.client.fluent;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import org.apache.http.entity.AbstractHttpEntity;
/*    */ import org.apache.http.entity.ContentType;
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
/*    */ class InternalInputStreamEntity
/*    */   extends AbstractHttpEntity
/*    */ {
/*    */   private final InputStream content;
/*    */   private final long length;
/*    */   
/*    */   public InternalInputStreamEntity(InputStream inputStream, long length, ContentType contentType) {
/* 45 */     this.content = (InputStream)Args.notNull(inputStream, "Source input stream");
/* 46 */     this.length = length;
/* 47 */     if (contentType != null) {
/* 48 */       setContentType(contentType.toString());
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isRepeatable() {
/* 54 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public long getContentLength() {
/* 59 */     return this.length;
/*    */   }
/*    */ 
/*    */   
/*    */   public InputStream getContent() throws IOException {
/* 64 */     return this.content;
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeTo(OutputStream outStream) throws IOException {
/* 69 */     Args.notNull(outStream, "Output stream");
/* 70 */     InputStream inStream = this.content;
/*    */     try {
/* 72 */       byte[] buffer = new byte[4096];
/*    */       
/* 74 */       if (this.length < 0L) {
/*    */         int readLen;
/* 76 */         while ((readLen = inStream.read(buffer)) != -1) {
/* 77 */           outStream.write(buffer, 0, readLen);
/*    */         }
/*    */       } else {
/*    */         
/* 81 */         long remaining = this.length;
/* 82 */         while (remaining > 0L) {
/* 83 */           int readLen = inStream.read(buffer, 0, (int)Math.min(4096L, remaining));
/* 84 */           if (readLen == -1) {
/*    */             break;
/*    */           }
/* 87 */           outStream.write(buffer, 0, readLen);
/* 88 */           remaining -= readLen;
/*    */         } 
/*    */       } 
/*    */     } finally {
/* 92 */       inStream.close();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isStreaming() {
/* 98 */     return true;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/client/fluent/InternalInputStreamEntity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */