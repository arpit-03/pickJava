/*     */ package org.apache.commons.math3.optimization.univariate;
/*     */ 
/*     */ import org.apache.commons.math3.exception.NotStrictlyPositiveException;
/*     */ import org.apache.commons.math3.optimization.AbstractConvergenceChecker;
/*     */ import org.apache.commons.math3.util.FastMath;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Deprecated
/*     */ public class SimpleUnivariateValueChecker
/*     */   extends AbstractConvergenceChecker<UnivariatePointValuePair>
/*     */ {
/*     */   private static final int ITERATION_CHECK_DISABLED = -1;
/*     */   private final int maxIterationCount;
/*     */   
/*     */   @Deprecated
/*     */   public SimpleUnivariateValueChecker() {
/*  65 */     this.maxIterationCount = -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SimpleUnivariateValueChecker(double relativeThreshold, double absoluteThreshold) {
/*  79 */     super(relativeThreshold, absoluteThreshold);
/*  80 */     this.maxIterationCount = -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SimpleUnivariateValueChecker(double relativeThreshold, double absoluteThreshold, int maxIter) {
/* 100 */     super(relativeThreshold, absoluteThreshold);
/*     */     
/* 102 */     if (maxIter <= 0) {
/* 103 */       throw new NotStrictlyPositiveException(Integer.valueOf(maxIter));
/*     */     }
/* 105 */     this.maxIterationCount = maxIter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean converged(int iteration, UnivariatePointValuePair previous, UnivariatePointValuePair current) {
/* 128 */     if (this.maxIterationCount != -1 && iteration >= this.maxIterationCount) {
/* 129 */       return true;
/*     */     }
/*     */     
/* 132 */     double p = previous.getValue();
/* 133 */     double c = current.getValue();
/* 134 */     double difference = FastMath.abs(p - c);
/* 135 */     double size = FastMath.max(FastMath.abs(p), FastMath.abs(c));
/* 136 */     return (difference <= size * getRelativeThreshold() || difference <= getAbsoluteThreshold());
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optimization/univariate/SimpleUnivariateValueChecker.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */