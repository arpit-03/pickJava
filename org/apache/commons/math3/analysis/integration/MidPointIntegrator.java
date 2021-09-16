/*     */ package org.apache.commons.math3.analysis.integration;
/*     */ 
/*     */ import org.apache.commons.math3.exception.MathIllegalArgumentException;
/*     */ import org.apache.commons.math3.exception.MaxCountExceededException;
/*     */ import org.apache.commons.math3.exception.NotStrictlyPositiveException;
/*     */ import org.apache.commons.math3.exception.NumberIsTooLargeException;
/*     */ import org.apache.commons.math3.exception.NumberIsTooSmallException;
/*     */ import org.apache.commons.math3.exception.TooManyEvaluationsException;
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
/*     */ public class MidPointIntegrator
/*     */   extends BaseAbstractUnivariateIntegrator
/*     */ {
/*     */   public static final int MIDPOINT_MAX_ITERATIONS_COUNT = 64;
/*     */   
/*     */   public MidPointIntegrator(double relativeAccuracy, double absoluteAccuracy, int minimalIterationCount, int maximalIterationCount) throws NotStrictlyPositiveException, NumberIsTooSmallException, NumberIsTooLargeException {
/*  61 */     super(relativeAccuracy, absoluteAccuracy, minimalIterationCount, maximalIterationCount);
/*  62 */     if (maximalIterationCount > 64) {
/*  63 */       throw new NumberIsTooLargeException(Integer.valueOf(maximalIterationCount), Integer.valueOf(64), false);
/*     */     }
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
/*     */   public MidPointIntegrator(int minimalIterationCount, int maximalIterationCount) throws NotStrictlyPositiveException, NumberIsTooSmallException, NumberIsTooLargeException {
/*  83 */     super(minimalIterationCount, maximalIterationCount);
/*  84 */     if (maximalIterationCount > 64) {
/*  85 */       throw new NumberIsTooLargeException(Integer.valueOf(maximalIterationCount), Integer.valueOf(64), false);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MidPointIntegrator() {
/*  95 */     super(3, 64);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private double stage(int n, double previousStageResult, double min, double diffMaxMin) throws TooManyEvaluationsException {
/* 124 */     long np = 1L << n - 1;
/* 125 */     double sum = 0.0D;
/*     */ 
/*     */     
/* 128 */     double spacing = diffMaxMin / np;
/*     */ 
/*     */     
/* 131 */     double x = min + 0.5D * spacing; long i;
/* 132 */     for (i = 0L; i < np; i++) {
/* 133 */       sum += computeObjectiveValue(x);
/* 134 */       x += spacing;
/*     */     } 
/*     */     
/* 137 */     return 0.5D * (previousStageResult + sum * spacing);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected double doIntegrate() throws MathIllegalArgumentException, TooManyEvaluationsException, MaxCountExceededException {
/* 146 */     double min = getMin();
/* 147 */     double diff = getMax() - min;
/* 148 */     double midPoint = min + 0.5D * diff;
/*     */     
/* 150 */     double oldt = diff * computeObjectiveValue(midPoint);
/*     */     
/*     */     while (true) {
/* 153 */       incrementCount();
/* 154 */       int i = getIterations();
/* 155 */       double t = stage(i, oldt, min, diff);
/* 156 */       if (i >= getMinimalIterationCount()) {
/* 157 */         double delta = FastMath.abs(t - oldt);
/* 158 */         double rLimit = getRelativeAccuracy() * (FastMath.abs(oldt) + FastMath.abs(t)) * 0.5D;
/*     */         
/* 160 */         if (delta <= rLimit || delta <= getAbsoluteAccuracy()) {
/* 161 */           return t;
/*     */         }
/*     */       } 
/* 164 */       oldt = t;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/analysis/integration/MidPointIntegrator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */