/*     */ package org.apache.http.client.fluent;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import org.apache.http.entity.AbstractHttpEntity;
/*     */ import org.apache.http.entity.ContentType;
/*     */ import org.apache.http.util.Args;
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
/*     */ class InternalByteArrayEntity
/*     */   extends AbstractHttpEntity
/*     */   implements Cloneable
/*     */ {
/*     */   private final byte[] b;
/*     */   private final int off;
/*     */   private final int len;
/*     */   
/*     */   public InternalByteArrayEntity(byte[] b, ContentType contentType) {
/*  46 */     Args.notNull(b, "Source byte array");
/*  47 */     this.b = b;
/*  48 */     this.off = 0;
/*  49 */     this.len = this.b.length;
/*  50 */     if (contentType != null) {
/*  51 */       setContentType(contentType.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public InternalByteArrayEntity(byte[] b, int off, int len, ContentType contentType) {
/*  57 */     Args.notNull(b, "Source byte array");
/*  58 */     if (off < 0 || off > b.length || len < 0 || off + len < 0 || off + len > b.length)
/*     */     {
/*  60 */       throw new IndexOutOfBoundsException("off: " + off + " len: " + len + " b.length: " + b.length);
/*     */     }
/*  62 */     this.b = b;
/*  63 */     this.off = off;
/*  64 */     this.len = len;
/*  65 */     if (contentType != null) {
/*  66 */       setContentType(contentType.toString());
/*     */     }
/*     */   }
/*     */   
/*     */   public InternalByteArrayEntity(byte[] b) {
/*  71 */     this(b, (ContentType)null);
/*     */   }
/*     */   
/*     */   public InternalByteArrayEntity(byte[] b, int off, int len) {
/*  75 */     this(b, off, len, (ContentType)null);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isRepeatable() {
/*  80 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public long getContentLength() {
/*  85 */     return this.len;
/*     */   }
/*     */ 
/*     */   
/*     */   public InputStream getContent() {
/*  90 */     return new ByteArrayInputStream(this.b, this.off, this.len);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeTo(OutputStream outStream) throws IOException {
/*  95 */     Args.notNull(outStream, "Output stream");
/*  96 */     outStream.write(this.b, this.off, this.len);
/*  97 */     outStream.flush();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isStreaming() {
/* 102 */     return false;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/client/fluent/InternalByteArrayEntity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */