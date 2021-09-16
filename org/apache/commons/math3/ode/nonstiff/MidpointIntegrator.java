/*    */ package org.apache.commons.math3.ode.nonstiff;
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
/*    */ public class MidpointIntegrator
/*    */   extends RungeKuttaIntegrator
/*    */ {
/* 47 */   private static final double[] STATIC_C = new double[] { 0.5D };
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 52 */   private static final double[][] STATIC_A = new double[][] { { 0.5D } };
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 57 */   private static final double[] STATIC_B = new double[] { 0.0D, 1.0D };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public MidpointIntegrator(double step) {
/* 66 */     super("midpoint", STATIC_C, STATIC_A, STATIC_B, new MidpointStepInterpolator(), step);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ode/nonstiff/MidpointIntegrator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */