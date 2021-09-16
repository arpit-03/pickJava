/*     */ package org.apache.commons.math3.optim;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SimplePointChecker<PAIR extends Pair<double[], ? extends Object>>
/*     */   extends AbstractConvergenceChecker<PAIR>
/*     */ {
/*     */   private static final int ITERATION_CHECK_DISABLED = -1;
/*     */   private final int maxIterationCount;
/*     */   
/*     */   public SimplePointChecker(double relativeThreshold, double absoluteThreshold) {
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
/*     */   public SimplePointChecker(double relativeThreshold, double absoluteThreshold, int maxIter) {
/*  87 */     super(relativeThreshold, absoluteThreshold);
/*     */     
/*  89 */     if (maxIter <= 0) {
/*  90 */       throw new NotStrictlyPositiveException(Integer.valueOf(maxIter));
/*     */     }
/*  92 */     this.maxIterationCount = maxIter;
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
/* 115 */     if (this.maxIterationCount != -1 && iteration >= this.maxIterationCount) {
/* 116 */       return true;
/*     */     }
/*     */     
/* 119 */     double[] p = (double[])previous.getKey();
/* 120 */     double[] c = (double[])current.getKey();
/* 121 */     for (int i = 0; i < p.length; i++) {
/* 122 */       double pi = p[i];
/* 123 */       double ci = c[i];
/* 124 */       double difference = FastMath.abs(pi - ci);
/* 125 */       double size = FastMath.max(FastMath.abs(pi), FastMath.abs(ci));
/* 126 */       if (difference > size * getRelativeThreshold() && difference > getAbsoluteThreshold())
/*     */       {
/* 128 */         return false;
/*     */       }
/*     */     } 
/* 131 */     return true;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optim/SimplePointChecker.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */