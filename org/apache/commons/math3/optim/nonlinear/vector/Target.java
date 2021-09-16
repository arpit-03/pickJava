/*    */ package org.apache.commons.math3.optim.nonlinear.vector;
/*    */ 
/*    */ import org.apache.commons.math3.optim.OptimizationData;
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
/*    */ public class Target
/*    */   implements OptimizationData
/*    */ {
/*    */   private final double[] target;
/*    */   
/*    */   public Target(double[] observations) {
/* 43 */     this.target = (double[])observations.clone();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double[] getTarget() {
/* 52 */     return (double[])this.target.clone();
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optim/nonlinear/vector/Target.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */