/*     */ package org.apache.commons.math3.analysis.interpolation;
/*     */ 
/*     */ import org.apache.commons.math3.analysis.UnivariateFunction;
/*     */ import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
/*     */ import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.MathIllegalArgumentException;
/*     */ import org.apache.commons.math3.exception.NonMonotonicSequenceException;
/*     */ import org.apache.commons.math3.exception.NumberIsTooSmallException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.util.MathArrays;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SplineInterpolator
/*     */   implements UnivariateInterpolator
/*     */ {
/*     */   public PolynomialSplineFunction interpolate(double[] x, double[] y) throws DimensionMismatchException, NumberIsTooSmallException, NonMonotonicSequenceException {
/*  69 */     if (x.length != y.length) {
/*  70 */       throw new DimensionMismatchException(x.length, y.length);
/*     */     }
/*     */     
/*  73 */     if (x.length < 3) {
/*  74 */       throw new NumberIsTooSmallException(LocalizedFormats.NUMBER_OF_POINTS, Integer.valueOf(x.length), Integer.valueOf(3), true);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  79 */     int n = x.length - 1;
/*     */     
/*  81 */     MathArrays.checkOrder(x);
/*     */ 
/*     */     
/*  84 */     double[] h = new double[n];
/*  85 */     for (int i = 0; i < n; i++) {
/*  86 */       h[i] = x[i + 1] - x[i];
/*     */     }
/*     */     
/*  89 */     double[] mu = new double[n];
/*  90 */     double[] z = new double[n + 1];
/*  91 */     mu[0] = 0.0D;
/*  92 */     z[0] = 0.0D;
/*  93 */     double g = 0.0D;
/*  94 */     for (int k = 1; k < n; k++) {
/*  95 */       g = 2.0D * (x[k + 1] - x[k - 1]) - h[k - 1] * mu[k - 1];
/*  96 */       mu[k] = h[k] / g;
/*  97 */       z[k] = (3.0D * (y[k + 1] * h[k - 1] - y[k] * (x[k + 1] - x[k - 1]) + y[k - 1] * h[k]) / h[k - 1] * h[k] - h[k - 1] * z[k - 1]) / g;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 102 */     double[] b = new double[n];
/* 103 */     double[] c = new double[n + 1];
/* 104 */     double[] d = new double[n];
/*     */     
/* 106 */     z[n] = 0.0D;
/* 107 */     c[n] = 0.0D;
/*     */     
/* 109 */     for (int j = n - 1; j >= 0; j--) {
/* 110 */       c[j] = z[j] - mu[j] * c[j + 1];
/* 111 */       b[j] = (y[j + 1] - y[j]) / h[j] - h[j] * (c[j + 1] + 2.0D * c[j]) / 3.0D;
/* 112 */       d[j] = (c[j + 1] - c[j]) / 3.0D * h[j];
/*     */     } 
/*     */     
/* 115 */     PolynomialFunction[] polynomials = new PolynomialFunction[n];
/* 116 */     double[] coefficients = new double[4];
/* 117 */     for (int m = 0; m < n; m++) {
/* 118 */       coefficients[0] = y[m];
/* 119 */       coefficients[1] = b[m];
/* 120 */       coefficients[2] = c[m];
/* 121 */       coefficients[3] = d[m];
/* 122 */       polynomials[m] = new PolynomialFunction(coefficients);
/*     */     } 
/*     */     
/* 125 */     return new PolynomialSplineFunction(x, polynomials);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/analysis/interpolation/SplineInterpolator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */