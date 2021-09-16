/*    */ package edu.mines.jtk.util;
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
/*    */ public class Threads
/*    */ {
/*    */   public static int getAvailableProcessors() {
/* 29 */     return Runtime.getRuntime().availableProcessors();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static Thread[] makeArray() {
/* 40 */     return makeArray(1.0D);
/*    */   }
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
/*    */   public static Thread[] makeArray(double multiple) {
/* 53 */     int nprocessors = getAvailableProcessors();
/* 54 */     int nthread = Math.max(1, (int)(multiple * nprocessors));
/* 55 */     return new Thread[nthread];
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void startAndJoin(Thread[] threads) {
/* 64 */     for (Thread thread : threads)
/* 65 */       thread.start(); 
/*    */     try {
/* 67 */       for (Thread thread : threads)
/* 68 */         thread.join(); 
/* 69 */     } catch (InterruptedException ie) {
/* 70 */       throw new RuntimeException(ie);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/util/Threads.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */