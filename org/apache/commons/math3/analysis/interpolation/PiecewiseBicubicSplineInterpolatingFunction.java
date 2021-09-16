/*     */ package org.apache.commons.math3.analysis.interpolation;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import org.apache.commons.math3.analysis.BivariateFunction;
/*     */ import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.InsufficientDataException;
/*     */ import org.apache.commons.math3.exception.NoDataException;
/*     */ import org.apache.commons.math3.exception.NonMonotonicSequenceException;
/*     */ import org.apache.commons.math3.exception.NullArgumentException;
/*     */ import org.apache.commons.math3.exception.OutOfRangeException;
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
/*     */ public class PiecewiseBicubicSplineInterpolatingFunction
/*     */   implements BivariateFunction
/*     */ {
/*     */   private static final int MIN_NUM_POINTS = 5;
/*     */   private final double[] xval;
/*     */   private final double[] yval;
/*     */   private final double[][] fval;
/*     */   
/*     */   public PiecewiseBicubicSplineInterpolatingFunction(double[] x, double[] y, double[][] f) throws DimensionMismatchException, NullArgumentException, NoDataException, NonMonotonicSequenceException {
/*  71 */     if (x == null || y == null || f == null || f[0] == null)
/*     */     {
/*     */ 
/*     */       
/*  75 */       throw new NullArgumentException();
/*     */     }
/*     */     
/*  78 */     int xLen = x.length;
/*  79 */     int yLen = y.length;
/*     */     
/*  81 */     if (xLen == 0 || yLen == 0 || f.length == 0 || (f[0]).length == 0)
/*     */     {
/*     */ 
/*     */       
/*  85 */       throw new NoDataException();
/*     */     }
/*     */     
/*  88 */     if (xLen < 5 || yLen < 5 || f.length < 5 || (f[0]).length < 5)
/*     */     {
/*     */ 
/*     */       
/*  92 */       throw new InsufficientDataException();
/*     */     }
/*     */     
/*  95 */     if (xLen != f.length) {
/*  96 */       throw new DimensionMismatchException(xLen, f.length);
/*     */     }
/*     */     
/*  99 */     if (yLen != (f[0]).length) {
/* 100 */       throw new DimensionMismatchException(yLen, (f[0]).length);
/*     */     }
/*     */     
/* 103 */     MathArrays.checkOrder(x);
/* 104 */     MathArrays.checkOrder(y);
/*     */     
/* 106 */     this.xval = (double[])x.clone();
/* 107 */     this.yval = (double[])y.clone();
/* 108 */     this.fval = (double[][])f.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double value(double x, double y) throws OutOfRangeException {
/* 117 */     AkimaSplineInterpolator interpolator = new AkimaSplineInterpolator();
/* 118 */     int offset = 2;
/* 119 */     int count = 5;
/* 120 */     int i = searchIndex(x, this.xval, 2, 5);
/* 121 */     int j = searchIndex(y, this.yval, 2, 5);
/*     */     
/* 123 */     double[] xArray = new double[5];
/* 124 */     double[] yArray = new double[5];
/* 125 */     double[] zArray = new double[5];
/* 126 */     double[] interpArray = new double[5];
/*     */     
/* 128 */     for (int index = 0; index < 5; index++) {
/* 129 */       xArray[index] = this.xval[i + index];
/* 130 */       yArray[index] = this.yval[j + index];
/*     */     } 
/*     */     
/* 133 */     for (int zIndex = 0; zIndex < 5; zIndex++) {
/* 134 */       for (int k = 0; k < 5; k++) {
/* 135 */         zArray[k] = this.fval[i + k][j + zIndex];
/*     */       }
/* 137 */       PolynomialSplineFunction polynomialSplineFunction = interpolator.interpolate(xArray, zArray);
/* 138 */       interpArray[zIndex] = polynomialSplineFunction.value(x);
/*     */     } 
/*     */     
/* 141 */     PolynomialSplineFunction spline = interpolator.interpolate(yArray, interpArray);
/*     */     
/* 143 */     double returnValue = spline.value(y);
/*     */     
/* 145 */     return returnValue;
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
/*     */   public boolean isValidPoint(double x, double y) {
/* 158 */     if (x < this.xval[0] || x > this.xval[this.xval.length - 1] || y < this.yval[0] || y > this.yval[this.yval.length - 1])
/*     */     {
/*     */ 
/*     */       
/* 162 */       return false;
/*     */     }
/* 164 */     return true;
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
/*     */   private int searchIndex(double c, double[] val, int offset, int count) {
/* 183 */     int r = Arrays.binarySearch(val, c);
/*     */     
/* 185 */     if (r == -1 || r == -val.length - 1) {
/* 186 */       throw new OutOfRangeException(Double.valueOf(c), Double.valueOf(val[0]), Double.valueOf(val[val.length - 1]));
/*     */     }
/*     */     
/* 189 */     if (r < 0) {
/*     */ 
/*     */ 
/*     */       
/* 193 */       r = -r - offset - 1;
/*     */     } else {
/* 195 */       r -= offset;
/*     */     } 
/*     */     
/* 198 */     if (r < 0) {
/* 199 */       r = 0;
/*     */     }
/*     */     
/* 202 */     if (r + count >= val.length)
/*     */     {
/*     */       
/* 205 */       r = val.length - count;
/*     */     }
/*     */     
/* 208 */     return r;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/analysis/interpolation/PiecewiseBicubicSplineInterpolatingFunction.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */