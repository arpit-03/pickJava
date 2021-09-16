/*    */ package org.apache.commons.math3.optimization;
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
/*    */ @Deprecated
/*    */ public class InitialGuess
/*    */   implements OptimizationData
/*    */ {
/*    */   private final double[] init;
/*    */   
/*    */   public InitialGuess(double[] startPoint) {
/* 37 */     this.init = (double[])startPoint.clone();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double[] getInitialGuess() {
/* 46 */     return (double[])this.init.clone();
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optimization/InitialGuess.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */