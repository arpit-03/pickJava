/*    */ package org.apache.commons.math3.ode.nonstiff;
/*    */ 
/*    */ import org.apache.commons.math3.util.FastMath;
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
/*    */ public class LutherIntegrator
/*    */   extends RungeKuttaIntegrator
/*    */ {
/* 59 */   private static final double Q = FastMath.sqrt(21.0D);
/*    */ 
/*    */   
/* 62 */   private static final double[] STATIC_C = new double[] { 1.0D, 0.5D, 0.6666666666666666D, (7.0D - Q) / 14.0D, (7.0D + Q) / 14.0D, 1.0D };
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 67 */   private static final double[][] STATIC_A = new double[][] { { 1.0D }, { 0.375D, 0.125D }, { 0.2962962962962963D, 0.07407407407407407D, 0.2962962962962963D }, { (-21.0D + 9.0D * Q) / 392.0D, (-56.0D + 8.0D * Q) / 392.0D, (336.0D - 48.0D * Q) / 392.0D, (-63.0D + 3.0D * Q) / 392.0D }, { (-1155.0D - 255.0D * Q) / 1960.0D, (-280.0D - 40.0D * Q) / 1960.0D, (0.0D - 320.0D * Q) / 1960.0D, (63.0D + 363.0D * Q) / 1960.0D, (2352.0D + 392.0D * Q) / 1960.0D }, { (330.0D + 105.0D * Q) / 180.0D, (120.0D + 0.0D * Q) / 180.0D, (-200.0D + 280.0D * Q) / 180.0D, (126.0D - 189.0D * Q) / 180.0D, (-686.0D - 126.0D * Q) / 180.0D, (490.0D - 70.0D * Q) / 180.0D } };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 77 */   private static final double[] STATIC_B = new double[] { 0.05D, 0.0D, 0.35555555555555557D, 0.0D, 0.2722222222222222D, 0.2722222222222222D, 0.05D };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public LutherIntegrator(double step) {
/* 86 */     super("Luther", STATIC_C, STATIC_A, STATIC_B, new LutherStepInterpolator(), step);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ode/nonstiff/LutherIntegrator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */