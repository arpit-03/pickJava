/*    */ package org.apache.http.client.fluent;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.FileInputStream;
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
/*    */ class InternalFileEntity
/*    */   extends AbstractHttpEntity
/*    */   implements Cloneable
/*    */ {
/*    */   private final File file;
/*    */   
/*    */   public InternalFileEntity(File file, ContentType contentType) {
/* 46 */     this.file = (File)Args.notNull(file, "File");
/* 47 */     if (contentType != null) {
/* 48 */       setContentType(contentType.toString());
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isRepeatable() {
/* 54 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public long getContentLength() {
/* 59 */     return this.file.length();
/*    */   }
/*    */ 
/*    */   
/*    */   public InputStream getContent() throws IOException {
/* 64 */     return new FileInputStream(this.file);
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeTo(OutputStream outStream) throws IOException {
/* 69 */     Args.notNull(outStream, "Output stream");
/* 70 */     InputStream inStream = new FileInputStream(this.file);
/*    */     try {
/* 72 */       byte[] tmp = new byte[4096];
/*    */       int l;
/* 74 */       while ((l = inStream.read(tmp)) != -1) {
/* 75 */         outStream.write(tmp, 0, l);
/*    */       }
/* 77 */       outStream.flush();
/*    */     } finally {
/* 79 */       inStream.close();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isStreaming() {
/* 85 */     return false;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/client/fluent/InternalFileEntity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */