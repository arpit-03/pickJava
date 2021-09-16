/*     */ package org.apache.commons.math3.optim.univariate;
/*     */ 
/*     */ import org.apache.commons.math3.exception.NotStrictlyPositiveException;
/*     */ import org.apache.commons.math3.optim.AbstractConvergenceChecker;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SimpleUnivariateValueChecker
/*     */   extends AbstractConvergenceChecker<UnivariatePointValuePair>
/*     */ {
/*     */   private static final int ITERATION_CHECK_DISABLED = -1;
/*     */   private final int maxIterationCount;
/*     */   
/*     */   public SimpleUnivariateValueChecker(double relativeThreshold, double absoluteThreshold) {
/*  67 */     super(relativeThreshold, absoluteThreshold);
/*  68 */     this.maxIterationCount = -1;
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
/*  88 */     super(relativeThreshold, absoluteThreshold);
/*     */     
/*  90 */     if (maxIter <= 0) {
/*  91 */       throw new NotStrictlyPositiveException(Integer.valueOf(maxIter));
/*     */     }
/*  93 */     this.maxIterationCount = maxIter;
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
/* 116 */     if (this.maxIterationCount != -1 && iteration >= this.maxIterationCount) {
/* 117 */       return true;
/*     */     }
/*     */     
/* 120 */     double p = previous.getValue();
/* 121 */     double c = current.getValue();
/* 122 */     double difference = FastMath.abs(p - c);
/* 123 */     double size = FastMath.max(FastMath.abs(p), FastMath.abs(c));
/* 124 */     return (difference <= size * getRelativeThreshold() || difference <= getAbsoluteThreshold());
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optim/univariate/SimpleUnivariateValueChecker.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */