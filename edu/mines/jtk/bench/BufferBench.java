/*    */ package edu.mines.jtk.bench;
/*    */ 
/*    */ import java.nio.ByteBuffer;
/*    */ import java.nio.ByteOrder;
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
/*    */ public class BufferBench
/*    */ {
/*    */   public static void main(String[] args) {
/* 28 */     testOutOfMemory();
/*    */   }
/*    */   
/*    */   public static void testOutOfMemory() {
/* 32 */     int capacity = 100000000;
/* 33 */     int nbuf = 100;
/* 34 */     byte data = 31;
/* 35 */     for (int ibuf = 0; ibuf < nbuf; ibuf++) {
/* 36 */       ByteBuffer bb = newByteBuffer(capacity);
/* 37 */       bb.put(capacity / 2, data);
/* 38 */       bb.put(capacity - 1, data);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private static ByteBuffer newByteBuffer(int capacity) {
/* 45 */     ByteBuffer bb = null;
/*    */     try {
/* 47 */       System.out.println("allocating " + capacity + " bytes");
/* 48 */       bb = ByteBuffer.allocateDirect(capacity).order(ByteOrder.nativeOrder());
/* 49 */     } catch (OutOfMemoryError e1) {
/* 50 */       System.gc();
/* 51 */       System.out.println("attempted gc after exception: " + e1);
/* 52 */       System.out.println("now attempting to allocate again");
/*    */       try {
/* 54 */         bb = ByteBuffer.allocateDirect(capacity);
/* 55 */       } catch (OutOfMemoryError e2) {
/* 56 */         System.out.println("failed allocate after gc" + e2.getMessage());
/*    */       } 
/*    */     } 
/* 59 */     return bb;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/bench/BufferBench.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */