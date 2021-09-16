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
/*    */ 
/*    */ public class SibsonGridder3
/*    */   extends SibsonInterpolator3
/*    */   implements Gridder3
/*    */ {
/*    */   public SibsonGridder3(float[] f, float[] x1, float[] x2, float[] x3) {
/* 37 */     super(f, x1, x2, x3);
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
/* 55 */     if (smooth) {
/* 56 */       setGradientPower(1.0D);
/*    */     } else {
/* 58 */       setGradientPower(0.0D);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setScattered(float[] f, float[] x1, float[] x2, float[] x3) {
/* 70 */     setSamples(f, x1, x2, x3);
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
/*    */   public float[][][] grid(Sampling s1, Sampling s2, Sampling s3) {
/* 84 */     setBounds(s1, s2, s3);
/* 85 */     return interpolate(s1, s2, s3);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/interp/SibsonGridder3.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */