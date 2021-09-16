/*     */ package org.apache.commons.math3.optimization;
/*     */ 
/*     */ import org.apache.commons.math3.exception.NotStrictlyPositiveException;
/*     */ import org.apache.commons.math3.util.FastMath;
/*     */ import org.apache.commons.math3.util.Pair;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public class SimplePointChecker<PAIR extends Pair<double[], ? extends Object>>
/*     */   extends AbstractConvergenceChecker<PAIR>
/*     */ {
/*     */   private static final int ITERATION_CHECK_DISABLED = -1;
/*     */   private final int maxIterationCount;
/*     */   
/*     */   @Deprecated
/*     */   public SimplePointChecker() {
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
/*     */   public SimplePointChecker(double relativeThreshold, double absoluteThreshold) {
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
/*     */   public SimplePointChecker(double relativeThreshold, double absoluteThreshold, int maxIter) {
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
/*     */   public boolean converged(int iteration, PAIR previous, PAIR current) {
/* 127 */     if (this.maxIterationCount != -1 && iteration >= this.maxIterationCount) {
/* 128 */       return true;
/*     */     }
/*     */     
/* 131 */     double[] p = (double[])previous.getKey();
/* 132 */     double[] c = (double[])current.getKey();
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


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optimization/SimplePointChecker.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */