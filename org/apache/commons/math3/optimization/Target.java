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
/*    */ @Deprecated
/*    */ public class Target
/*    */   implements OptimizationData
/*    */ {
/*    */   private final double[] target;
/*    */   
/*    */   public Target(double[] observations) {
/* 39 */     this.target = (double[])observations.clone();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double[] getTarget() {
/* 48 */     return (double[])this.target.clone();
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optimization/Target.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */