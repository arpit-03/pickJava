/*    */ package org.apache.http.benchmark;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import org.apache.http.impl.DefaultBHttpClientConnection;
/*    */ import org.apache.http.io.SessionInputBuffer;
/*    */ import org.apache.http.io.SessionOutputBuffer;
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
/*    */ class BenchmarkConnection
/*    */   extends DefaultBHttpClientConnection
/*    */ {
/*    */   private final Stats stats;
/*    */   
/*    */   BenchmarkConnection(int bufsize, Stats stats) {
/* 41 */     super(bufsize);
/* 42 */     this.stats = stats;
/*    */   }
/*    */ 
/*    */   
/*    */   protected OutputStream createOutputStream(long len, SessionOutputBuffer outbuffer) {
/* 47 */     return new CountingOutputStream(super.createOutputStream(len, outbuffer), this.stats);
/*    */   }
/*    */ 
/*    */   
/*    */   protected InputStream createInputStream(long len, SessionInputBuffer inBuffer) {
/* 52 */     return new CountingInputStream(super.createInputStream(len, inBuffer), this.stats);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/benchmark/BenchmarkConnection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */