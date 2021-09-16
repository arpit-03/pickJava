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
/*     */ 
/*     */ public class RombergIntegrator
/*     */   extends BaseAbstractUnivariateIntegrator
/*     */ {
/*     */   public static final int ROMBERG_MAX_ITERATIONS_COUNT = 32;
/*     */   
/*     */   public RombergIntegrator(double relativeAccuracy, double absoluteAccuracy, int minimalIterationCount, int maximalIterationCount) throws NotStrictlyPositiveException, NumberIsTooSmallException, NumberIsTooLargeException {
/*  62 */     super(relativeAccuracy, absoluteAccuracy, minimalIterationCount, maximalIterationCount);
/*  63 */     if (maximalIterationCount > 32) {
/*  64 */       throw new NumberIsTooLargeException(Integer.valueOf(maximalIterationCount), Integer.valueOf(32), false);
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
/*     */   public RombergIntegrator(int minimalIterationCount, int maximalIterationCount) throws NotStrictlyPositiveException, NumberIsTooSmallException, NumberIsTooLargeException {
/*  84 */     super(minimalIterationCount, maximalIterationCount);
/*  85 */     if (maximalIterationCount > 32) {
/*  86 */       throw new NumberIsTooLargeException(Integer.valueOf(maximalIterationCount), Integer.valueOf(32), false);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RombergIntegrator() {
/*  96 */     super(3, 32);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected double doIntegrate() throws TooManyEvaluationsException, MaxCountExceededException {
/* 104 */     int m = getMaximalIterationCount() + 1;
/* 105 */     double[] previousRow = new double[m];
/* 106 */     double[] currentRow = new double[m];
/*     */     
/* 108 */     TrapezoidIntegrator qtrap = new TrapezoidIntegrator();
/* 109 */     currentRow[0] = qtrap.stage(this, 0);
/* 110 */     incrementCount();
/* 111 */     double olds = currentRow[0];
/*     */     
/*     */     while (true) {
/* 114 */       int i = getIterations();
/*     */ 
/*     */       
/* 117 */       double[] tmpRow = previousRow;
/* 118 */       previousRow = currentRow;
/* 119 */       currentRow = tmpRow;
/*     */       
/* 121 */       currentRow[0] = qtrap.stage(this, i);
/* 122 */       incrementCount();
/* 123 */       for (int j = 1; j <= i; j++) {
/*     */         
/* 125 */         double r = ((1L << 2 * j) - 1L);
/* 126 */         double tIJm1 = currentRow[j - 1];
/* 127 */         currentRow[j] = tIJm1 + (tIJm1 - previousRow[j - 1]) / r;
/*     */       } 
/* 129 */       double s = currentRow[i];
/* 130 */       if (i >= getMinimalIterationCount()) {
/* 131 */         double delta = FastMath.abs(s - olds);
/* 132 */         double rLimit = getRelativeAccuracy() * (FastMath.abs(olds) + FastMath.abs(s)) * 0.5D;
/* 133 */         if (delta <= rLimit || delta <= getAbsoluteAccuracy()) {
/* 134 */           return s;
/*     */         }
/*     */       } 
/* 137 */       olds = s;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/analysis/integration/RombergIntegrator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */