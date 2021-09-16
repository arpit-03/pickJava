/*     */ package org.apache.commons.math3.analysis.integration;
/*     */ 
/*     */ import org.apache.commons.math3.analysis.UnivariateFunction;
/*     */ import org.apache.commons.math3.analysis.integration.gauss.GaussIntegrator;
/*     */ import org.apache.commons.math3.analysis.integration.gauss.GaussIntegratorFactory;
/*     */ import org.apache.commons.math3.exception.MathIllegalArgumentException;
/*     */ import org.apache.commons.math3.exception.MaxCountExceededException;
/*     */ import org.apache.commons.math3.exception.NotStrictlyPositiveException;
/*     */ import org.apache.commons.math3.exception.NumberIsTooSmallException;
/*     */ import org.apache.commons.math3.exception.TooManyEvaluationsException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
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
/*     */ public class IterativeLegendreGaussIntegrator
/*     */   extends BaseAbstractUnivariateIntegrator
/*     */ {
/*  50 */   private static final GaussIntegratorFactory FACTORY = new GaussIntegratorFactory();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final int numberOfPoints;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IterativeLegendreGaussIntegrator(int n, double relativeAccuracy, double absoluteAccuracy, int minimalIterationCount, int maximalIterationCount) throws NotStrictlyPositiveException, NumberIsTooSmallException {
/*  74 */     super(relativeAccuracy, absoluteAccuracy, minimalIterationCount, maximalIterationCount);
/*  75 */     if (n <= 0) {
/*  76 */       throw new NotStrictlyPositiveException(LocalizedFormats.NUMBER_OF_POINTS, Integer.valueOf(n));
/*     */     }
/*  78 */     this.numberOfPoints = n;
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
/*     */   public IterativeLegendreGaussIntegrator(int n, double relativeAccuracy, double absoluteAccuracy) throws NotStrictlyPositiveException {
/*  93 */     this(n, relativeAccuracy, absoluteAccuracy, 3, 2147483647);
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
/*     */   public IterativeLegendreGaussIntegrator(int n, int minimalIterationCount, int maximalIterationCount) throws NotStrictlyPositiveException, NumberIsTooSmallException {
/* 113 */     this(n, 1.0E-6D, 1.0E-15D, minimalIterationCount, maximalIterationCount);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected double doIntegrate() throws MathIllegalArgumentException, TooManyEvaluationsException, MaxCountExceededException {
/* 122 */     double oldt = stage(1);
/*     */     
/* 124 */     int n = 2;
/*     */     
/*     */     while (true) {
/* 127 */       double t = stage(n);
/*     */ 
/*     */       
/* 130 */       double delta = FastMath.abs(t - oldt);
/* 131 */       double limit = FastMath.max(getAbsoluteAccuracy(), getRelativeAccuracy() * (FastMath.abs(oldt) + FastMath.abs(t)) * 0.5D);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 136 */       if (getIterations() + 1 >= getMinimalIterationCount() && delta <= limit)
/*     */       {
/* 138 */         return t;
/*     */       }
/*     */ 
/*     */       
/* 142 */       double ratio = FastMath.min(4.0D, FastMath.pow(delta / limit, 0.5D / this.numberOfPoints));
/* 143 */       n = FastMath.max((int)(ratio * n), n + 1);
/* 144 */       oldt = t;
/* 145 */       incrementCount();
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
/*     */   private double stage(int n) throws TooManyEvaluationsException {
/* 160 */     UnivariateFunction f = new UnivariateFunction()
/*     */       {
/*     */         public double value(double x) throws MathIllegalArgumentException, TooManyEvaluationsException
/*     */         {
/* 164 */           return IterativeLegendreGaussIntegrator.this.computeObjectiveValue(x);
/*     */         }
/*     */       };
/*     */     
/* 168 */     double min = getMin();
/* 169 */     double max = getMax();
/* 170 */     double step = (max - min) / n;
/*     */     
/* 172 */     double sum = 0.0D;
/* 173 */     for (int i = 0; i < n; i++) {
/*     */       
/* 175 */       double a = min + i * step;
/* 176 */       double b = a + step;
/* 177 */       GaussIntegrator g = FACTORY.legendreHighPrecision(this.numberOfPoints, a, b);
/* 178 */       sum += g.integrate(f);
/*     */     } 
/*     */     
/* 181 */     return sum;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/analysis/integration/IterativeLegendreGaussIntegrator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */