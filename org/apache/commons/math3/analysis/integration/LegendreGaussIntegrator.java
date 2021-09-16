/*     */ package org.apache.commons.math3.analysis.integration;
/*     */ 
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
/*     */ 
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
/*     */ public class LegendreGaussIntegrator
/*     */   extends BaseAbstractUnivariateIntegrator
/*     */ {
/*  58 */   private static final double[] ABSCISSAS_2 = new double[] { -1.0D / FastMath.sqrt(3.0D), 1.0D / FastMath.sqrt(3.0D) };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  64 */   private static final double[] WEIGHTS_2 = new double[] { 1.0D, 1.0D };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  70 */   private static final double[] ABSCISSAS_3 = new double[] { -FastMath.sqrt(0.6D), 0.0D, FastMath.sqrt(0.6D) };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  77 */   private static final double[] WEIGHTS_3 = new double[] { 0.5555555555555556D, 0.8888888888888888D, 0.5555555555555556D };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  84 */   private static final double[] ABSCISSAS_4 = new double[] { -FastMath.sqrt((15.0D + 2.0D * FastMath.sqrt(30.0D)) / 35.0D), -FastMath.sqrt((15.0D - 2.0D * FastMath.sqrt(30.0D)) / 35.0D), FastMath.sqrt((15.0D - 2.0D * FastMath.sqrt(30.0D)) / 35.0D), FastMath.sqrt((15.0D + 2.0D * FastMath.sqrt(30.0D)) / 35.0D) };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  92 */   private static final double[] WEIGHTS_4 = new double[] { (90.0D - 5.0D * FastMath.sqrt(30.0D)) / 180.0D, (90.0D + 5.0D * FastMath.sqrt(30.0D)) / 180.0D, (90.0D + 5.0D * FastMath.sqrt(30.0D)) / 180.0D, (90.0D - 5.0D * FastMath.sqrt(30.0D)) / 180.0D };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 100 */   private static final double[] ABSCISSAS_5 = new double[] { -FastMath.sqrt((35.0D + 2.0D * FastMath.sqrt(70.0D)) / 63.0D), -FastMath.sqrt((35.0D - 2.0D * FastMath.sqrt(70.0D)) / 63.0D), 0.0D, FastMath.sqrt((35.0D - 2.0D * FastMath.sqrt(70.0D)) / 63.0D), FastMath.sqrt((35.0D + 2.0D * FastMath.sqrt(70.0D)) / 63.0D) };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 109 */   private static final double[] WEIGHTS_5 = new double[] { (322.0D - 13.0D * FastMath.sqrt(70.0D)) / 900.0D, (322.0D + 13.0D * FastMath.sqrt(70.0D)) / 900.0D, 0.5688888888888889D, (322.0D + 13.0D * FastMath.sqrt(70.0D)) / 900.0D, (322.0D - 13.0D * FastMath.sqrt(70.0D)) / 900.0D };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final double[] abscissas;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final double[] weights;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LegendreGaussIntegrator(int n, double relativeAccuracy, double absoluteAccuracy, int minimalIterationCount, int maximalIterationCount) throws MathIllegalArgumentException, NotStrictlyPositiveException, NumberIsTooSmallException {
/* 142 */     super(relativeAccuracy, absoluteAccuracy, minimalIterationCount, maximalIterationCount);
/* 143 */     switch (n) {
/*     */       case 2:
/* 145 */         this.abscissas = ABSCISSAS_2;
/* 146 */         this.weights = WEIGHTS_2;
/*     */         return;
/*     */       case 3:
/* 149 */         this.abscissas = ABSCISSAS_3;
/* 150 */         this.weights = WEIGHTS_3;
/*     */         return;
/*     */       case 4:
/* 153 */         this.abscissas = ABSCISSAS_4;
/* 154 */         this.weights = WEIGHTS_4;
/*     */         return;
/*     */       case 5:
/* 157 */         this.abscissas = ABSCISSAS_5;
/* 158 */         this.weights = WEIGHTS_5;
/*     */         return;
/*     */     } 
/* 161 */     throw new MathIllegalArgumentException(LocalizedFormats.N_POINTS_GAUSS_LEGENDRE_INTEGRATOR_NOT_SUPPORTED, new Object[] { Integer.valueOf(n), Integer.valueOf(2), Integer.valueOf(5) });
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
/*     */   public LegendreGaussIntegrator(int n, double relativeAccuracy, double absoluteAccuracy) throws MathIllegalArgumentException {
/* 179 */     this(n, relativeAccuracy, absoluteAccuracy, 3, 2147483647);
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
/*     */   public LegendreGaussIntegrator(int n, int minimalIterationCount, int maximalIterationCount) throws MathIllegalArgumentException {
/* 198 */     this(n, 1.0E-6D, 1.0E-15D, minimalIterationCount, maximalIterationCount);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected double doIntegrate() throws MathIllegalArgumentException, TooManyEvaluationsException, MaxCountExceededException {
/* 208 */     double oldt = stage(1);
/*     */     
/* 210 */     int n = 2;
/*     */ 
/*     */     
/*     */     while (true) {
/* 214 */       double t = stage(n);
/*     */ 
/*     */       
/* 217 */       double delta = FastMath.abs(t - oldt);
/* 218 */       double limit = FastMath.max(getAbsoluteAccuracy(), getRelativeAccuracy() * (FastMath.abs(oldt) + FastMath.abs(t)) * 0.5D);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 223 */       if (getIterations() + 1 >= getMinimalIterationCount() && delta <= limit) {
/* 224 */         return t;
/*     */       }
/*     */ 
/*     */       
/* 228 */       double ratio = FastMath.min(4.0D, FastMath.pow(delta / limit, 0.5D / this.abscissas.length));
/* 229 */       n = FastMath.max((int)(ratio * n), n + 1);
/* 230 */       oldt = t;
/* 231 */       incrementCount();
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
/*     */   private double stage(int n) throws TooManyEvaluationsException {
/* 248 */     double step = (getMax() - getMin()) / n;
/* 249 */     double halfStep = step / 2.0D;
/*     */ 
/*     */     
/* 252 */     double midPoint = getMin() + halfStep;
/* 253 */     double sum = 0.0D;
/* 254 */     for (int i = 0; i < n; i++) {
/* 255 */       for (int j = 0; j < this.abscissas.length; j++) {
/* 256 */         sum += this.weights[j] * computeObjectiveValue(midPoint + halfStep * this.abscissas[j]);
/*     */       }
/* 258 */       midPoint += step;
/*     */     } 
/*     */     
/* 261 */     return halfStep * sum;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/analysis/integration/LegendreGaussIntegrator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */