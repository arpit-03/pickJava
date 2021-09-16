/*     */ package org.apache.commons.math3.analysis.integration;
/*     */ 
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
/*     */ 
/*     */ public class SimpsonIntegrator
/*     */   extends BaseAbstractUnivariateIntegrator
/*     */ {
/*     */   public static final int SIMPSON_MAX_ITERATIONS_COUNT = 64;
/*     */   
/*     */   public SimpsonIntegrator(double relativeAccuracy, double absoluteAccuracy, int minimalIterationCount, int maximalIterationCount) throws NotStrictlyPositiveException, NumberIsTooSmallException, NumberIsTooLargeException {
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
/*     */   public SimpsonIntegrator(int minimalIterationCount, int maximalIterationCount) throws NotStrictlyPositiveException, NumberIsTooSmallException, NumberIsTooLargeException {
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
/*     */   public SimpsonIntegrator() {
/*  95 */     super(3, 64);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected double doIntegrate() throws TooManyEvaluationsException, MaxCountExceededException {
/* 103 */     TrapezoidIntegrator qtrap = new TrapezoidIntegrator();
/* 104 */     if (getMinimalIterationCount() == 1) {
/* 105 */       return (4.0D * qtrap.stage(this, 1) - qtrap.stage(this, 0)) / 3.0D;
/*     */     }
/*     */ 
/*     */     
/* 109 */     double olds = 0.0D;
/* 110 */     double oldt = qtrap.stage(this, 0);
/*     */     while (true) {
/* 112 */       double t = qtrap.stage(this, getIterations());
/* 113 */       incrementCount();
/* 114 */       double s = (4.0D * t - oldt) / 3.0D;
/* 115 */       if (getIterations() >= getMinimalIterationCount()) {
/* 116 */         double delta = FastMath.abs(s - olds);
/* 117 */         double rLimit = getRelativeAccuracy() * (FastMath.abs(olds) + FastMath.abs(s)) * 0.5D;
/*     */         
/* 119 */         if (delta <= rLimit || delta <= getAbsoluteAccuracy()) {
/* 120 */           return s;
/*     */         }
/*     */       } 
/* 123 */       olds = s;
/* 124 */       oldt = t;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/analysis/integration/SimpsonIntegrator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */