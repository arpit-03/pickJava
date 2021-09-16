/*    */ package com.jmatio.io;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class ByteBufferInputStream
/*    */   extends InputStream
/*    */ {
/*    */   private ByteBuffer buf;
/*    */   private int limit;
/*    */   
/*    */   public ByteBufferInputStream(ByteBuffer buf, int limit) {
/* 18 */     this.buf = buf;
/* 19 */     this.limit = limit;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public synchronized int read() throws IOException {
/* 25 */     if (this.limit <= 0)
/*    */     {
/* 27 */       return -1;
/*    */     }
/* 29 */     this.limit--;
/* 30 */     return this.buf.get() & 0xFF;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public synchronized int read(byte[] bytes, int off, int len) throws IOException {
/* 37 */     if (this.limit <= 0)
/*    */     {
/* 39 */       return -1;
/*    */     }
/* 41 */     len = Math.min(len, this.limit);
/*    */     
/* 43 */     this.buf.get(bytes, off, len);
/* 44 */     this.limit -= len;
/* 45 */     return len;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/jmatio/io/ByteBufferInputStream.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */