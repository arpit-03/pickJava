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
/*     */ public class SimpleVectorValueChecker
/*     */   extends AbstractConvergenceChecker<PointVectorValuePair>
/*     */ {
/*     */   private static final int ITERATION_CHECK_DISABLED = -1;
/*     */   private final int maxIterationCount;
/*     */   
/*     */   @Deprecated
/*     */   public SimpleVectorValueChecker() {
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
/*     */   
/*     */   public SimpleVectorValueChecker(double relativeThreshold, double absoluteThreshold) {
/*  77 */     super(relativeThreshold, absoluteThreshold);
/*  78 */     this.maxIterationCount = -1;
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
/*     */   public SimpleVectorValueChecker(double relativeThreshold, double absoluteThreshold, int maxIter) {
/*  99 */     super(relativeThreshold, absoluteThreshold);
/*     */     
/* 101 */     if (maxIter <= 0) {
/* 102 */       throw new NotStrictlyPositiveException(Integer.valueOf(maxIter));
/*     */     }
/* 104 */     this.maxIterationCount = maxIter;
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
/*     */   public boolean converged(int iteration, PointVectorValuePair previous, PointVectorValuePair current) {
/* 127 */     if (this.maxIterationCount != -1 && iteration >= this.maxIterationCount) {
/* 128 */       return true;
/*     */     }
/*     */     
/* 131 */     double[] p = previous.getValueRef();
/* 132 */     double[] c = current.getValueRef();
/* 133 */     for (int i = 0; i < p.length; i++) {
/* 134 */       double pi = p[i];
/* 135 */       double ci = c[i];
/* 136 */       double difference = FastMath.abs(pi - ci);
/* 137 */       double size = FastMath.max(FastMath.abs(pi), FastMath.abs(ci));
/* 138 */       if (difference > size * getRelativeThreshold() && difference > getAbsoluteThreshold())
/*     */       {
/* 140 */         return false;
/*     */       }
/*     */     } 
/* 143 */     return true;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optimization/SimpleVectorValueChecker.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */