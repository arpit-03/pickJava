/*     */ package org.apache.commons.math3.optimization;
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
/*     */ @Deprecated
/*     */ public class SimpleValueChecker
/*     */   extends AbstractConvergenceChecker<PointValuePair>
/*     */ {
/*     */   private static final int ITERATION_CHECK_DISABLED = -1;
/*     */   private final int maxIterationCount;
/*     */   
/*     */   @Deprecated
/*     */   public SimpleValueChecker() {
/*  62 */     this.maxIterationCount = -1;
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
/*     */   public SimpleValueChecker(double relativeThreshold, double absoluteThreshold) {
/*  76 */     super(relativeThreshold, absoluteThreshold);
/*  77 */     this.maxIterationCount = -1;
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
/*  97 */     super(relativeThreshold, absoluteThreshold);
/*     */     
/*  99 */     if (maxIter <= 0) {
/* 100 */       throw new NotStrictlyPositiveException(Integer.valueOf(maxIter));
/*     */     }
/* 102 */     this.maxIterationCount = maxIter;
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
/* 125 */     if (this.maxIterationCount != -1 && iteration >= this.maxIterationCount) {
/* 126 */       return true;
/*     */     }
/*     */     
/* 129 */     double p = ((Double)previous.getValue()).doubleValue();
/* 130 */     double c = ((Double)current.getValue()).doubleValue();
/* 131 */     double difference = FastMath.abs(p - c);
/* 132 */     double size = FastMath.max(FastMath.abs(p), FastMath.abs(c));
/* 133 */     return (difference <= size * getRelativeThreshold() || difference <= getAbsoluteThreshold());
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optimization/SimpleValueChecker.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */