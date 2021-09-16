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
/*     */ 
/*     */ public class SimpleVectorValueChecker
/*     */   extends AbstractConvergenceChecker<PointVectorValuePair>
/*     */ {
/*     */   private static final int ITERATION_CHECK_DISABLED = -1;
/*     */   private final int maxIterationCount;
/*     */   
/*     */   public SimpleVectorValueChecker(double relativeThreshold, double absoluteThreshold) {
/*  66 */     super(relativeThreshold, absoluteThreshold);
/*  67 */     this.maxIterationCount = -1;
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
/*     */   public boolean converged(int iteration, PointVectorValuePair previous, PointVectorValuePair current) {
/* 116 */     if (this.maxIterationCount != -1 && iteration >= this.maxIterationCount) {
/* 117 */       return true;
/*     */     }
/*     */     
/* 120 */     double[] p = previous.getValueRef();
/* 121 */     double[] c = current.getValueRef();
/* 122 */     for (int i = 0; i < p.length; i++) {
/* 123 */       double pi = p[i];
/* 124 */       double ci = c[i];
/* 125 */       double difference = FastMath.abs(pi - ci);
/* 126 */       double size = FastMath.max(FastMath.abs(pi), FastMath.abs(ci));
/* 127 */       if (difference > size * getRelativeThreshold() && difference > getAbsoluteThreshold())
/*     */       {
/* 129 */         return false;
/*     */       }
/*     */     } 
/* 132 */     return true;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optim/SimpleVectorValueChecker.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */