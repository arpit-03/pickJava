/*     */ package org.apache.commons.math3.analysis.interpolation;
/*     */ 
/*     */ import org.apache.commons.math3.analysis.UnivariateFunction;
/*     */ import org.apache.commons.math3.exception.MathIllegalArgumentException;
/*     */ import org.apache.commons.math3.exception.NonMonotonicSequenceException;
/*     */ import org.apache.commons.math3.exception.NumberIsTooSmallException;
/*     */ import org.apache.commons.math3.util.MathArrays;
/*     */ import org.apache.commons.math3.util.MathUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class UnivariatePeriodicInterpolator
/*     */   implements UnivariateInterpolator
/*     */ {
/*     */   public static final int DEFAULT_EXTEND = 5;
/*     */   private final UnivariateInterpolator interpolator;
/*     */   private final double period;
/*     */   private final int extend;
/*     */   
/*     */   public UnivariatePeriodicInterpolator(UnivariateInterpolator interpolator, double period, int extend) {
/*  60 */     this.interpolator = interpolator;
/*  61 */     this.period = period;
/*  62 */     this.extend = extend;
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
/*     */   public UnivariatePeriodicInterpolator(UnivariateInterpolator interpolator, double period) {
/*  75 */     this(interpolator, period, 5);
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
/*     */   public UnivariateFunction interpolate(double[] xval, double[] yval) throws NumberIsTooSmallException, NonMonotonicSequenceException {
/*  87 */     if (xval.length < this.extend) {
/*  88 */       throw new NumberIsTooSmallException(Integer.valueOf(xval.length), Integer.valueOf(this.extend), true);
/*     */     }
/*     */     
/*  91 */     MathArrays.checkOrder(xval);
/*  92 */     final double offset = xval[0];
/*     */     
/*  94 */     int len = xval.length + this.extend * 2;
/*  95 */     double[] x = new double[len];
/*  96 */     double[] y = new double[len]; int i;
/*  97 */     for (i = 0; i < xval.length; i++) {
/*  98 */       int index = i + this.extend;
/*  99 */       x[index] = MathUtils.reduce(xval[i], this.period, offset);
/* 100 */       y[index] = yval[i];
/*     */     } 
/*     */ 
/*     */     
/* 104 */     for (i = 0; i < this.extend; i++) {
/* 105 */       int index = xval.length - this.extend + i;
/* 106 */       x[i] = MathUtils.reduce(xval[index], this.period, offset) - this.period;
/* 107 */       y[i] = yval[index];
/*     */       
/* 109 */       index = len - this.extend + i;
/* 110 */       x[index] = MathUtils.reduce(xval[i], this.period, offset) + this.period;
/* 111 */       y[index] = yval[i];
/*     */     } 
/*     */     
/* 114 */     MathArrays.sortInPlace(x, new double[][] { y });
/*     */     
/* 116 */     final UnivariateFunction f = this.interpolator.interpolate(x, y);
/* 117 */     return new UnivariateFunction()
/*     */       {
/*     */         public double value(double x) throws MathIllegalArgumentException {
/* 120 */           return f.value(MathUtils.reduce(x, UnivariatePeriodicInterpolator.this.period, offset));
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/analysis/interpolation/UnivariatePeriodicInterpolator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */