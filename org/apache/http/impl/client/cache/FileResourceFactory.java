/*     */ package org.apache.http.impl.client.cache;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import org.apache.http.annotation.Contract;
/*     */ import org.apache.http.annotation.ThreadingBehavior;
/*     */ import org.apache.http.client.cache.InputLimit;
/*     */ import org.apache.http.client.cache.Resource;
/*     */ import org.apache.http.client.cache.ResourceFactory;
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
/*     */ @Contract(threading = ThreadingBehavior.IMMUTABLE)
/*     */ public class FileResourceFactory
/*     */   implements ResourceFactory
/*     */ {
/*     */   private final File cacheDir;
/*     */   private final BasicIdGenerator idgen;
/*     */   
/*     */   public FileResourceFactory(File cacheDir) {
/*  53 */     this.cacheDir = cacheDir;
/*  54 */     this.idgen = new BasicIdGenerator();
/*     */   }
/*     */   
/*     */   private File generateUniqueCacheFile(String requestId) {
/*  58 */     StringBuilder buffer = new StringBuilder();
/*  59 */     this.idgen.generate(buffer);
/*  60 */     buffer.append('.');
/*  61 */     int len = Math.min(requestId.length(), 100);
/*  62 */     for (int i = 0; i < len; i++) {
/*  63 */       char ch = requestId.charAt(i);
/*  64 */       if (Character.isLetterOrDigit(ch) || ch == '.') {
/*  65 */         buffer.append(ch);
/*     */       } else {
/*  67 */         buffer.append('-');
/*     */       } 
/*     */     } 
/*  70 */     return new File(this.cacheDir, buffer.toString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Resource generate(String requestId, InputStream inStream, InputLimit limit) throws IOException {
/*  78 */     File file = generateUniqueCacheFile(requestId);
/*  79 */     FileOutputStream outStream = new FileOutputStream(file);
/*     */     try {
/*  81 */       byte[] buf = new byte[2048];
/*  82 */       long total = 0L;
/*     */       int l;
/*  84 */       while ((l = inStream.read(buf)) != -1) {
/*  85 */         outStream.write(buf, 0, l);
/*  86 */         total += l;
/*  87 */         if (limit != null && total > limit.getValue()) {
/*  88 */           limit.reached();
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } finally {
/*  93 */       outStream.close();
/*     */     } 
/*  95 */     return new FileResource(file);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Resource copy(String requestId, Resource resource) throws IOException {
/* 102 */     File file = generateUniqueCacheFile(requestId);
/*     */     
/* 104 */     if (resource instanceof FileResource) {
/* 105 */       File src = ((FileResource)resource).getFile();
/* 106 */       IOUtils.copyFile(src, file);
/*     */     } else {
/* 108 */       FileOutputStream out = new FileOutputStream(file);
/* 109 */       IOUtils.copyAndClose(resource.getInputStream(), out);
/*     */     } 
/* 111 */     return new FileResource(file);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/impl/client/cache/FileResourceFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */