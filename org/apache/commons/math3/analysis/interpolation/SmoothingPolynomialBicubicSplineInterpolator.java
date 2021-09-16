/*     */ package org.apache.commons.math3.analysis.interpolation;
/*     */ 
/*     */ import org.apache.commons.math3.analysis.BivariateFunction;
/*     */ import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.NoDataException;
/*     */ import org.apache.commons.math3.exception.NonMonotonicSequenceException;
/*     */ import org.apache.commons.math3.exception.NotPositiveException;
/*     */ import org.apache.commons.math3.exception.NullArgumentException;
/*     */ import org.apache.commons.math3.exception.NumberIsTooSmallException;
/*     */ import org.apache.commons.math3.fitting.PolynomialFitter;
/*     */ import org.apache.commons.math3.optim.ConvergenceChecker;
/*     */ import org.apache.commons.math3.optim.SimpleVectorValueChecker;
/*     */ import org.apache.commons.math3.optim.nonlinear.vector.MultivariateVectorOptimizer;
/*     */ import org.apache.commons.math3.optim.nonlinear.vector.jacobian.GaussNewtonOptimizer;
/*     */ import org.apache.commons.math3.util.MathArrays;
/*     */ import org.apache.commons.math3.util.Precision;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public class SmoothingPolynomialBicubicSplineInterpolator
/*     */   extends BicubicSplineInterpolator
/*     */ {
/*     */   private final PolynomialFitter xFitter;
/*     */   private final int xDegree;
/*     */   private final PolynomialFitter yFitter;
/*     */   private final int yDegree;
/*     */   
/*     */   public SmoothingPolynomialBicubicSplineInterpolator() {
/*  55 */     this(3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SmoothingPolynomialBicubicSplineInterpolator(int degree) throws NotPositiveException {
/*  64 */     this(degree, degree);
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
/*     */   public SmoothingPolynomialBicubicSplineInterpolator(int xDegree, int yDegree) throws NotPositiveException {
/*  76 */     if (xDegree < 0) {
/*  77 */       throw new NotPositiveException(Integer.valueOf(xDegree));
/*     */     }
/*  79 */     if (yDegree < 0) {
/*  80 */       throw new NotPositiveException(Integer.valueOf(yDegree));
/*     */     }
/*  82 */     this.xDegree = xDegree;
/*  83 */     this.yDegree = yDegree;
/*     */     
/*  85 */     double safeFactor = 100.0D;
/*  86 */     SimpleVectorValueChecker checker = new SimpleVectorValueChecker(100.0D * Precision.EPSILON, 100.0D * Precision.SAFE_MIN);
/*     */ 
/*     */     
/*  89 */     this.xFitter = new PolynomialFitter((MultivariateVectorOptimizer)new GaussNewtonOptimizer(false, (ConvergenceChecker)checker));
/*  90 */     this.yFitter = new PolynomialFitter((MultivariateVectorOptimizer)new GaussNewtonOptimizer(false, (ConvergenceChecker)checker));
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
/*     */   public BicubicSplineInterpolatingFunction interpolate(double[] xval, double[] yval, double[][] fval) throws NoDataException, NullArgumentException, DimensionMismatchException, NonMonotonicSequenceException {
/* 102 */     if (xval.length == 0 || yval.length == 0 || fval.length == 0) {
/* 103 */       throw new NoDataException();
/*     */     }
/* 105 */     if (xval.length != fval.length) {
/* 106 */       throw new DimensionMismatchException(xval.length, fval.length);
/*     */     }
/*     */     
/* 109 */     int xLen = xval.length;
/* 110 */     int yLen = yval.length;
/*     */     
/* 112 */     for (int i = 0; i < xLen; i++) {
/* 113 */       if ((fval[i]).length != yLen) {
/* 114 */         throw new DimensionMismatchException((fval[i]).length, yLen);
/*     */       }
/*     */     } 
/*     */     
/* 118 */     MathArrays.checkOrder(xval);
/* 119 */     MathArrays.checkOrder(yval);
/*     */ 
/*     */ 
/*     */     
/* 123 */     PolynomialFunction[] yPolyX = new PolynomialFunction[yLen];
/* 124 */     for (int j = 0; j < yLen; j++) {
/* 125 */       this.xFitter.clearObservations();
/* 126 */       for (int i1 = 0; i1 < xLen; i1++) {
/* 127 */         this.xFitter.addObservedPoint(1.0D, xval[i1], fval[i1][j]);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 132 */       yPolyX[j] = new PolynomialFunction(this.xFitter.fit(new double[this.xDegree + 1]));
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 137 */     double[][] fval_1 = new double[xLen][yLen];
/* 138 */     for (int k = 0; k < yLen; k++) {
/* 139 */       PolynomialFunction f = yPolyX[k];
/* 140 */       for (int i1 = 0; i1 < xLen; i1++) {
/* 141 */         fval_1[i1][k] = f.value(xval[i1]);
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 147 */     PolynomialFunction[] xPolyY = new PolynomialFunction[xLen];
/* 148 */     for (int m = 0; m < xLen; m++) {
/* 149 */       this.yFitter.clearObservations();
/* 150 */       for (int i1 = 0; i1 < yLen; i1++) {
/* 151 */         this.yFitter.addObservedPoint(1.0D, yval[i1], fval_1[m][i1]);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 156 */       xPolyY[m] = new PolynomialFunction(this.yFitter.fit(new double[this.yDegree + 1]));
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 161 */     double[][] fval_2 = new double[xLen][yLen];
/* 162 */     for (int n = 0; n < xLen; n++) {
/* 163 */       PolynomialFunction f = xPolyY[n];
/* 164 */       for (int i1 = 0; i1 < yLen; i1++) {
/* 165 */         fval_2[n][i1] = f.value(yval[i1]);
/*     */       }
/*     */     } 
/*     */     
/* 169 */     return super.interpolate(xval, yval, fval_2);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/analysis/interpolation/SmoothingPolynomialBicubicSplineInterpolator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */