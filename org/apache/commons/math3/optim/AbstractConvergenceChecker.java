/*    */ package org.apache.commons.math3.optim;
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
/*    */ public abstract class AbstractConvergenceChecker<PAIR>
/*    */   implements ConvergenceChecker<PAIR>
/*    */ {
/*    */   private final double relativeThreshold;
/*    */   private final double absoluteThreshold;
/*    */   
/*    */   public AbstractConvergenceChecker(double relativeThreshold, double absoluteThreshold) {
/* 45 */     this.relativeThreshold = relativeThreshold;
/* 46 */     this.absoluteThreshold = absoluteThreshold;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double getRelativeThreshold() {
/* 53 */     return this.relativeThreshold;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double getAbsoluteThreshold() {
/* 60 */     return this.absoluteThreshold;
/*    */   }
/*    */   
/*    */   public abstract boolean converged(int paramInt, PAIR paramPAIR1, PAIR paramPAIR2);
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optim/AbstractConvergenceChecker.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */