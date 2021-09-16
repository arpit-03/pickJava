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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RadialGridder2
/*    */   extends RadialInterpolator2
/*    */   implements Gridder2
/*    */ {
/*    */   public RadialGridder2(RadialInterpolator2.Basis basis, float[] f, float[] x1, float[] x2) {
/* 40 */     super(basis, f, x1, x2);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setScattered(float[] f, float[] x1, float[] x2) {
/* 50 */     setSamples(f, x1, x2);
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
/* 63 */     return interpolate(s1, s2);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/interp/RadialGridder2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */