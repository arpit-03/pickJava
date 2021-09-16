/*     */ package org.apache.http.impl.client.cache;
/*     */ 
/*     */ import java.io.FilterInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.SequenceInputStream;
/*     */ import org.apache.http.client.cache.Resource;
/*     */ import org.apache.http.entity.AbstractHttpEntity;
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
/*     */ 
/*     */ class CombinedEntity
/*     */   extends AbstractHttpEntity
/*     */ {
/*     */   private final Resource resource;
/*     */   private final InputStream combinedStream;
/*     */   
/*     */   CombinedEntity(Resource resource, InputStream inStream) throws IOException {
/*  46 */     this.resource = resource;
/*  47 */     this.combinedStream = new SequenceInputStream(new ResourceStream(resource.getInputStream()), inStream);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public long getContentLength() {
/*  53 */     return -1L;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isRepeatable() {
/*  58 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isStreaming() {
/*  63 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public InputStream getContent() throws IOException, IllegalStateException {
/*  68 */     return this.combinedStream;
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeTo(OutputStream outStream) throws IOException {
/*  73 */     Args.notNull(outStream, "Output stream");
/*  74 */     InputStream inStream = getContent();
/*     */     
/*     */     try {
/*  77 */       byte[] tmp = new byte[2048]; int l;
/*  78 */       while ((l = inStream.read(tmp)) != -1) {
/*  79 */         outStream.write(tmp, 0, l);
/*     */       }
/*     */     } finally {
/*  82 */       inStream.close();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void dispose() {
/*  87 */     this.resource.dispose();
/*     */   }
/*     */   
/*     */   class ResourceStream
/*     */     extends FilterInputStream {
/*     */     protected ResourceStream(InputStream in) {
/*  93 */       super(in);
/*     */     }
/*     */ 
/*     */     
/*     */     public void close() throws IOException {
/*     */       try {
/*  99 */         super.close();
/*     */       } finally {
/* 101 */         CombinedEntity.this.dispose();
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/impl/client/cache/CombinedEntity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */