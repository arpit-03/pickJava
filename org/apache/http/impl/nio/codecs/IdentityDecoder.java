/*     */ package org.apache.http.impl.nio.codecs;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.channels.FileChannel;
/*     */ import java.nio.channels.ReadableByteChannel;
/*     */ import org.apache.http.impl.io.HttpTransportMetricsImpl;
/*     */ import org.apache.http.nio.FileContentDecoder;
/*     */ import org.apache.http.nio.reactor.SessionInputBuffer;
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
/*     */ public class IdentityDecoder
/*     */   extends AbstractContentDecoder
/*     */   implements FileContentDecoder
/*     */ {
/*     */   public IdentityDecoder(ReadableByteChannel channel, SessionInputBuffer buffer, HttpTransportMetricsImpl metrics) {
/*  59 */     super(channel, buffer, metrics);
/*     */   }
/*     */   
/*     */   public int read(ByteBuffer dst) throws IOException {
/*     */     int bytesRead;
/*  64 */     Args.notNull(dst, "Byte buffer");
/*  65 */     if (isCompleted()) {
/*  66 */       return -1;
/*     */     }
/*     */ 
/*     */     
/*  70 */     if (this.buffer.hasData()) {
/*  71 */       bytesRead = this.buffer.read(dst);
/*     */     } else {
/*  73 */       bytesRead = readFromChannel(dst);
/*     */     } 
/*  75 */     if (bytesRead == -1) {
/*  76 */       setCompleted();
/*     */     }
/*  78 */     return bytesRead;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long transfer(FileChannel dst, long position, long count) throws IOException {
/*     */     long bytesRead;
/*  87 */     if (dst == null) {
/*  88 */       return 0L;
/*     */     }
/*  90 */     if (isCompleted()) {
/*  91 */       return 0L;
/*     */     }
/*     */ 
/*     */     
/*  95 */     if (this.buffer.hasData()) {
/*  96 */       int maxLen = this.buffer.length();
/*  97 */       dst.position(position);
/*  98 */       bytesRead = this.buffer.read(dst, (count < maxLen) ? (int)count : maxLen);
/*     */     } else {
/* 100 */       if (this.channel.isOpen()) {
/* 101 */         if (position > dst.size()) {
/* 102 */           throw new IOException(String.format("Position past end of file [%,d > %,d]", new Object[] { Long.valueOf(position), Long.valueOf(dst.size()) }));
/*     */         }
/*     */         
/* 105 */         bytesRead = dst.transferFrom(this.channel, position, count);
/* 106 */         if (count > 0L && bytesRead == 0L) {
/* 107 */           bytesRead = this.buffer.fill(this.channel);
/*     */         }
/*     */       } else {
/* 110 */         bytesRead = -1L;
/*     */       } 
/* 112 */       if (bytesRead > 0L) {
/* 113 */         this.metrics.incrementBytesTransferred(bytesRead);
/*     */       }
/*     */     } 
/* 116 */     if (bytesRead == -1L) {
/* 117 */       setCompleted();
/*     */     }
/* 119 */     return bytesRead;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 124 */     StringBuilder sb = new StringBuilder();
/* 125 */     sb.append("[identity; completed: ");
/* 126 */     sb.append(this.completed);
/* 127 */     sb.append("]");
/* 128 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/impl/nio/codecs/IdentityDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */