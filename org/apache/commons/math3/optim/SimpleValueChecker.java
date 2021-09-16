/*     */ package org.apache.commons.math3.optim;
/*     */ 
/*     */ import org.apache.commons.math3.exception.NotStrictlyPositiveException;
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
/*     */ public class SimpleValueChecker
/*     */   extends AbstractConvergenceChecker<PointValuePair>
/*     */ {
/*     */   private static final int ITERATION_CHECK_DISABLED = -1;
/*     */   private final int maxIterationCount;
/*     */   
/*     */   public SimpleValueChecker(double relativeThreshold, double absoluteThreshold) {
/*  65 */     super(relativeThreshold, absoluteThreshold);
/*  66 */     this.maxIterationCount = -1;
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
/*     */   public SimpleValueChecker(double relativeThreshold, double absoluteThreshold, int maxIter) {
/*  86 */     super(relativeThreshold, absoluteThreshold);
/*     */     
/*  88 */     if (maxIter <= 0) {
/*  89 */       throw new NotStrictlyPositiveException(Integer.valueOf(maxIter));
/*     */     }
/*  91 */     this.maxIterationCount = maxIter;
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
/*     */   public boolean converged(int iteration, PointValuePair previous, PointValuePair current) {
/* 114 */     if (this.maxIterationCount != -1 && iteration >= this.maxIterationCount) {
/* 115 */       return true;
/*     */     }
/*     */     
/* 118 */     double p = ((Double)previous.getValue()).doubleValue();
/* 119 */     double c = ((Double)current.getValue()).doubleValue();
/* 120 */     double difference = FastMath.abs(p - c);
/* 121 */     double size = FastMath.max(FastMath.abs(p), FastMath.abs(c));
/* 122 */     return (difference <= size * getRelativeThreshold() || difference <= getAbsoluteThreshold());
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optim/SimpleValueChecker.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */