/*    */ package org.apache.commons.math3.optimization;
/*    */ 
/*    */ import org.apache.commons.math3.util.Precision;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
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
/*    */ public abstract class AbstractConvergenceChecker<PAIR>
/*    */   implements ConvergenceChecker<PAIR>
/*    */ {
/*    */   @Deprecated
/* 39 */   private static final double DEFAULT_RELATIVE_THRESHOLD = 100.0D * Precision.EPSILON;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/* 46 */   private static final double DEFAULT_ABSOLUTE_THRESHOLD = 100.0D * Precision.SAFE_MIN;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private final double relativeThreshold;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private final double absoluteThreshold;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public AbstractConvergenceChecker() {
/* 66 */     this.relativeThreshold = DEFAULT_RELATIVE_THRESHOLD;
/* 67 */     this.absoluteThreshold = DEFAULT_ABSOLUTE_THRESHOLD;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public AbstractConvergenceChecker(double relativeThreshold, double absoluteThreshold) {
/* 78 */     this.relativeThreshold = relativeThreshold;
/* 79 */     this.absoluteThreshold = absoluteThreshold;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double getRelativeThreshold() {
/* 86 */     return this.relativeThreshold;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double getAbsoluteThreshold() {
/* 93 */     return this.absoluteThreshold;
/*    */   }
/*    */   
/*    */   public abstract boolean converged(int paramInt, PAIR paramPAIR1, PAIR paramPAIR2);
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optimization/AbstractConvergenceChecker.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */