/*     */ package org.apache.commons.math3.analysis.interpolation;
/*     */ 
/*     */ import org.apache.commons.math3.analysis.BivariateFunction;
/*     */ import org.apache.commons.math3.analysis.UnivariateFunction;
/*     */ import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.NoDataException;
/*     */ import org.apache.commons.math3.exception.NonMonotonicSequenceException;
/*     */ import org.apache.commons.math3.exception.NumberIsTooSmallException;
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
/*     */ @Deprecated
/*     */ public class BicubicSplineInterpolator
/*     */   implements BivariateGridInterpolator
/*     */ {
/*     */   private final boolean initializeDerivatives;
/*     */   
/*     */   public BicubicSplineInterpolator() {
/*  47 */     this(false);
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
/*     */   public BicubicSplineInterpolator(boolean initializeDerivatives) {
/*  59 */     this.initializeDerivatives = initializeDerivatives;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BicubicSplineInterpolatingFunction interpolate(double[] xval, double[] yval, double[][] fval) throws NoDataException, DimensionMismatchException, NonMonotonicSequenceException, NumberIsTooSmallException {
/*  70 */     if (xval.length == 0 || yval.length == 0 || fval.length == 0) {
/*  71 */       throw new NoDataException();
/*     */     }
/*  73 */     if (xval.length != fval.length) {
/*  74 */       throw new DimensionMismatchException(xval.length, fval.length);
/*     */     }
/*     */     
/*  77 */     MathArrays.checkOrder(xval);
/*  78 */     MathArrays.checkOrder(yval);
/*     */     
/*  80 */     int xLen = xval.length;
/*  81 */     int yLen = yval.length;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  87 */     double[][] fX = new double[yLen][xLen];
/*  88 */     for (int i = 0; i < xLen; i++) {
/*  89 */       if ((fval[i]).length != yLen) {
/*  90 */         throw new DimensionMismatchException((fval[i]).length, yLen);
/*     */       }
/*     */       
/*  93 */       for (int i2 = 0; i2 < yLen; i2++) {
/*  94 */         fX[i2][i] = fval[i][i2];
/*     */       }
/*     */     } 
/*     */     
/*  98 */     SplineInterpolator spInterpolator = new SplineInterpolator();
/*     */ 
/*     */ 
/*     */     
/* 102 */     PolynomialSplineFunction[] ySplineX = new PolynomialSplineFunction[yLen];
/* 103 */     for (int j = 0; j < yLen; j++) {
/* 104 */       ySplineX[j] = spInterpolator.interpolate(xval, fX[j]);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 109 */     PolynomialSplineFunction[] xSplineY = new PolynomialSplineFunction[xLen];
/* 110 */     for (int k = 0; k < xLen; k++) {
/* 111 */       xSplineY[k] = spInterpolator.interpolate(yval, fval[k]);
/*     */     }
/*     */ 
/*     */     
/* 115 */     double[][] dFdX = new double[xLen][yLen];
/* 116 */     for (int m = 0; m < yLen; m++) {
/* 117 */       UnivariateFunction f = ySplineX[m].derivative();
/* 118 */       for (int i2 = 0; i2 < xLen; i2++) {
/* 119 */         dFdX[i2][m] = f.value(xval[i2]);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 124 */     double[][] dFdY = new double[xLen][yLen];
/* 125 */     for (int n = 0; n < xLen; n++) {
/* 126 */       UnivariateFunction f = xSplineY[n].derivative();
/* 127 */       for (int i2 = 0; i2 < yLen; i2++) {
/* 128 */         dFdY[n][i2] = f.value(yval[i2]);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 133 */     double[][] d2FdXdY = new double[xLen][yLen];
/* 134 */     for (int i1 = 0; i1 < xLen; i1++) {
/* 135 */       int nI = nextIndex(i1, xLen);
/* 136 */       int pI = previousIndex(i1);
/* 137 */       for (int i2 = 0; i2 < yLen; i2++) {
/* 138 */         int nJ = nextIndex(i2, yLen);
/* 139 */         int pJ = previousIndex(i2);
/* 140 */         d2FdXdY[i1][i2] = (fval[nI][nJ] - fval[nI][pJ] - fval[pI][nJ] + fval[pI][pJ]) / (xval[nI] - xval[pI]) * (yval[nJ] - yval[pJ]);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 147 */     return new BicubicSplineInterpolatingFunction(xval, yval, fval, dFdX, dFdY, d2FdXdY, this.initializeDerivatives);
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
/*     */   private int nextIndex(int i, int max) {
/* 161 */     int index = i + 1;
/* 162 */     return (index < max) ? index : (index - 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int previousIndex(int i) {
/* 173 */     int index = i - 1;
/* 174 */     return (index >= 0) ? index : 0;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/analysis/interpolation/BicubicSplineInterpolator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */