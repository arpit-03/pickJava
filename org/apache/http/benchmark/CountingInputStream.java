/*    */ package org.apache.http.benchmark;
/*    */ 
/*    */ import java.io.FilterInputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
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
/*    */ class CountingInputStream
/*    */   extends FilterInputStream
/*    */ {
/*    */   private final Stats stats;
/*    */   
/*    */   CountingInputStream(InputStream inStream, Stats stats) {
/* 38 */     super(inStream);
/* 39 */     this.stats = stats;
/*    */   }
/*    */ 
/*    */   
/*    */   public int read() throws IOException {
/* 44 */     int b = this.in.read();
/* 45 */     if (b != -1) {
/* 46 */       this.stats.incTotalBytesRecv(1L);
/*    */     }
/* 48 */     return b;
/*    */   }
/*    */ 
/*    */   
/*    */   public int read(byte[] b) throws IOException {
/* 53 */     int bytesRead = this.in.read(b);
/* 54 */     if (bytesRead > 0) {
/* 55 */       this.stats.incTotalBytesRecv(bytesRead);
/*    */     }
/* 57 */     return bytesRead;
/*    */   }
/*    */ 
/*    */   
/*    */   public int read(byte[] b, int off, int len) throws IOException {
/* 62 */     int bytesRead = this.in.read(b, off, len);
/* 63 */     if (bytesRead > 0) {
/* 64 */       this.stats.incTotalBytesRecv(bytesRead);
/*    */     }
/* 66 */     return bytesRead;
/*    */   }
/*    */ 
/*    */   
/*    */   public long skip(long n) throws IOException {
/* 71 */     long bytesRead = this.in.skip(n);
/* 72 */     if (bytesRead > 0L) {
/* 73 */       this.stats.incTotalBytesRecv(bytesRead);
/*    */     }
/* 75 */     return bytesRead;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/benchmark/CountingInputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */