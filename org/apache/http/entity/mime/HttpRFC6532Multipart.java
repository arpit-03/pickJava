/*    */ package org.apache.http.entity.mime;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.OutputStream;
/*    */ import java.nio.charset.Charset;
/*    */ import java.util.List;
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
/*    */ class HttpRFC6532Multipart
/*    */   extends AbstractMultipartForm
/*    */ {
/*    */   private final List<FormBodyPart> parts;
/*    */   
/*    */   public HttpRFC6532Multipart(Charset charset, String boundary, List<FormBodyPart> parts) {
/* 50 */     super(charset, boundary);
/* 51 */     this.parts = parts;
/*    */   }
/*    */ 
/*    */   
/*    */   public List<FormBodyPart> getBodyParts() {
/* 56 */     return this.parts;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void formatMultipartHeader(FormBodyPart part, OutputStream out) throws IOException {
/* 65 */     Header header = part.getHeader();
/* 66 */     for (MinimalField field : header)
/* 67 */       writeField(field, MIME.UTF8_CHARSET, out); 
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/entity/mime/HttpRFC6532Multipart.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */