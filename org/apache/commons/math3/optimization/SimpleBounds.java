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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Deprecated
/*    */ public class SimpleBounds
/*    */   implements OptimizationData
/*    */ {
/*    */   private final double[] lower;
/*    */   private final double[] upper;
/*    */   
/*    */   public SimpleBounds(double[] lB, double[] uB) {
/* 43 */     this.lower = (double[])lB.clone();
/* 44 */     this.upper = (double[])uB.clone();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double[] getLower() {
/* 53 */     return (double[])this.lower.clone();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double[] getUpper() {
/* 61 */     return (double[])this.upper.clone();
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optimization/SimpleBounds.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */