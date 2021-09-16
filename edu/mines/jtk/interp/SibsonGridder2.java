/*    */ package edu.mines.jtk.interp;
/*    */ 
/*    */ import edu.mines.jtk.dsp.Sampling;
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
/*    */ public class SibsonGridder2
/*    */   extends SibsonInterpolator2
/*    */   implements Gridder2
/*    */ {
/*    */   public SibsonGridder2(float[] f, float[] x1, float[] x2) {
/* 36 */     super(f, x1, x2);
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setSmooth(boolean smooth) {
/* 54 */     if (smooth) {
/* 55 */       setGradientPower(1.0D);
/*    */     } else {
/* 57 */       setGradientPower(0.0D);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setScattered(float[] f, float[] x1, float[] x2) {
/* 68 */     setSamples(f, x1, x2);
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
/*    */   public float[][] grid(Sampling s1, Sampling s2) {
/* 81 */     setBounds(s1, s2);
/* 82 */     return interpolate(s1, s2);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/interp/SibsonGridder2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */