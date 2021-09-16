/*     */ package org.apache.http.impl.client.cache;
/*     */ 
/*     */ import java.io.Closeable;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.RandomAccessFile;
/*     */ import java.nio.channels.FileChannel;
/*     */ import org.apache.http.HttpEntity;
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
/*     */ class IOUtils
/*     */ {
/*     */   static void consume(HttpEntity entity) throws IOException {
/*  42 */     if (entity == null) {
/*     */       return;
/*     */     }
/*  45 */     if (entity.isStreaming()) {
/*  46 */       InputStream inStream = entity.getContent();
/*  47 */       if (inStream != null) {
/*  48 */         inStream.close();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   static void copy(InputStream in, OutputStream out) throws IOException {
/*  54 */     byte[] buf = new byte[2048];
/*     */     int len;
/*  56 */     while ((len = in.read(buf)) != -1) {
/*  57 */       out.write(buf, 0, len);
/*     */     }
/*     */   }
/*     */   
/*     */   static void closeSilently(Closeable closable) {
/*     */     try {
/*  63 */       closable.close();
/*  64 */     } catch (IOException ignore) {}
/*     */   }
/*     */ 
/*     */   
/*     */   static void copyAndClose(InputStream in, OutputStream out) throws IOException {
/*     */     try {
/*  70 */       copy(in, out);
/*  71 */       in.close();
/*  72 */       out.close();
/*  73 */     } catch (IOException ex) {
/*  74 */       closeSilently(in);
/*  75 */       closeSilently(out);
/*     */       
/*  77 */       throw ex;
/*     */     } 
/*     */   }
/*     */   
/*     */   static void copyFile(File in, File out) throws IOException {
/*  82 */     RandomAccessFile f1 = new RandomAccessFile(in, "r");
/*  83 */     RandomAccessFile f2 = new RandomAccessFile(out, "rw");
/*     */     try {
/*  85 */       FileChannel c1 = f1.getChannel();
/*  86 */       FileChannel c2 = f2.getChannel();
/*     */       try {
/*  88 */         c1.transferTo(0L, f1.length(), c2);
/*  89 */         c1.close();
/*  90 */         c2.close();
/*  91 */       } catch (IOException ex) {
/*  92 */         closeSilently(c1);
/*  93 */         closeSilently(c2);
/*     */         
/*  95 */         throw ex;
/*     */       } 
/*  97 */       f1.close();
/*  98 */       f2.close();
/*  99 */     } catch (IOException ex) {
/* 100 */       closeSilently(f1);
/* 101 */       closeSilently(f2);
/*     */       
/* 103 */       throw ex;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/impl/client/cache/IOUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */