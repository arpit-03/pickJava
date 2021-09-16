/*     */ package org.apache.http.impl.client.cache;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.Serializable;
/*     */ import org.apache.http.Header;
/*     */ import org.apache.http.HttpEntity;
/*     */ import org.apache.http.annotation.Contract;
/*     */ import org.apache.http.annotation.ThreadingBehavior;
/*     */ import org.apache.http.client.cache.HttpCacheEntry;
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
/*     */ 
/*     */ 
/*     */ @Contract(threading = ThreadingBehavior.IMMUTABLE)
/*     */ class CacheEntity
/*     */   implements HttpEntity, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -3467082284120936233L;
/*     */   private final HttpCacheEntry cacheEntry;
/*     */   
/*     */   public CacheEntity(HttpCacheEntry cacheEntry) {
/*  51 */     this.cacheEntry = cacheEntry;
/*     */   }
/*     */ 
/*     */   
/*     */   public Header getContentType() {
/*  56 */     return this.cacheEntry.getFirstHeader("Content-Type");
/*     */   }
/*     */ 
/*     */   
/*     */   public Header getContentEncoding() {
/*  61 */     return this.cacheEntry.getFirstHeader("Content-Encoding");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isChunked() {
/*  66 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isRepeatable() {
/*  71 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public long getContentLength() {
/*  76 */     return this.cacheEntry.getResource().length();
/*     */   }
/*     */ 
/*     */   
/*     */   public InputStream getContent() throws IOException {
/*  81 */     return this.cacheEntry.getResource().getInputStream();
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeTo(OutputStream outStream) throws IOException {
/*  86 */     Args.notNull(outStream, "Output stream");
/*  87 */     InputStream inStream = this.cacheEntry.getResource().getInputStream();
/*     */     try {
/*  89 */       IOUtils.copy(inStream, outStream);
/*     */     } finally {
/*  91 */       inStream.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isStreaming() {
/*  97 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void consumeContent() throws IOException {}
/*     */ 
/*     */   
/*     */   public Object clone() throws CloneNotSupportedException {
/* 106 */     return super.clone();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/impl/client/cache/CacheEntity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */