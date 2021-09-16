/*    */ package edu.mines.jtk.bench;
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
/*    */ public class MaxHeap
/*    */ {
/*    */   public static void main(String[] args) {
/* 25 */     int m = 1000000;
/* 26 */     for (int n = 100; n < 2000000000; n = (int)(1.1D * n)) {
/* 27 */       byte[][] a = new byte[n][m];
/* 28 */       n = a.length;
/* 29 */       System.out.println("allocated n=" + n + " MB");
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/bench/MaxHeap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */