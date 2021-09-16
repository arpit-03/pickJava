/*    */ package org.apache.http.benchmark;
/*    */ 
/*    */ import java.io.FilterOutputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.OutputStream;
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
/*    */ class CountingOutputStream
/*    */   extends FilterOutputStream
/*    */ {
/*    */   private final Stats stats;
/*    */   
/*    */   CountingOutputStream(OutputStream outStream, Stats stats) {
/* 38 */     super(outStream);
/* 39 */     this.stats = stats;
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(int b) throws IOException {
/* 44 */     this.out.write(b);
/* 45 */     this.stats.incTotalBytesSent(1L);
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(byte[] b) throws IOException {
/* 50 */     this.out.write(b);
/* 51 */     this.stats.incTotalBytesSent(b.length);
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(byte[] b, int off, int len) throws IOException {
/* 56 */     this.out.write(b, off, len);
/* 57 */     this.stats.incTotalBytesSent(len);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/benchmark/CountingOutputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */