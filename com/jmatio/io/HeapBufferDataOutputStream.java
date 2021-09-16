/*    */ package com.jmatio.io;
/*    */ 
/*    */ import java.io.ByteArrayOutputStream;
/*    */ import java.io.IOException;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ public class HeapBufferDataOutputStream
/*    */   extends ByteArrayOutputStream implements DataOutputStream {
/*  9 */   private final int BUFFER_SIZE = 1024;
/*    */ 
/*    */   
/*    */   public ByteBuffer getByteBuffer() throws IOException {
/* 13 */     return ByteBuffer.wrap(this.buf);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void write(ByteBuffer byteBuffer) throws IOException {
/* 19 */     byte[] tmp = new byte[1024];
/*    */     
/* 21 */     while (byteBuffer.hasRemaining()) {
/*    */       
/* 23 */       int length = Math.min(byteBuffer.remaining(), tmp.length);
/* 24 */       byteBuffer.get(tmp, 0, length);
/* 25 */       write(tmp, 0, length);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/jmatio/io/HeapBufferDataOutputStream.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */