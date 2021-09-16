/*    */ package edu.mines.jtk.bench;
/*    */ 
/*    */ import edu.mines.jtk.util.MathPlus;
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
/*    */ public class SamplingBench
/*    */ {
/*    */   public static void main(String[] args) {
/* 27 */     float f = 0.0F;
/* 28 */     float d = 0.33333334F;
/* 29 */     int n = 10000;
/* 30 */     double errorMaximum = 0.0D;
/* 31 */     float x = f;
/* 32 */     for (int i = 0; i < n; i++, x += d) {
/* 33 */       double xd = (f + i * d);
/* 34 */       errorMaximum = MathPlus.max(errorMaximum, MathPlus.abs(xd - x));
/*    */     } 
/* 36 */     double errorEstimate = (1.1920929E-7F * n * (MathPlus.abs(f) + MathPlus.abs(f + (n - 1) * d))) / 2.0D;
/* 37 */     System.out.println("error estimate =" + (errorEstimate / d));
/* 38 */     System.out.println("error maximum  =" + (errorMaximum / d));
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/bench/SamplingBench.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */