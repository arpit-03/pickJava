/*     */ package org.apache.commons.math3.analysis.interpolation;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import org.apache.commons.math3.analysis.BivariateFunction;
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.NoDataException;
/*     */ import org.apache.commons.math3.exception.NonMonotonicSequenceException;
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
/*     */ public class BicubicInterpolatingFunction
/*     */   implements BivariateFunction
/*     */ {
/*     */   private static final int NUM_COEFF = 16;
/*  42 */   private static final double[][] AINV = new double[][] { { 1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D }, { 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D }, { -3.0D, 3.0D, 0.0D, 0.0D, -2.0D, -1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D }, { 2.0D, -2.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D }, { 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D }, { 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D, 0.0D, 0.0D }, { 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, -3.0D, 3.0D, 0.0D, 0.0D, -2.0D, -1.0D, 0.0D, 0.0D }, { 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 2.0D, -2.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.0D, 0.0D }, { -3.0D, 0.0D, 3.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, -2.0D, 0.0D, -1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D }, { 0.0D, 0.0D, 0.0D, 0.0D, -3.0D, 0.0D, 3.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, -2.0D, 0.0D, -1.0D, 0.0D }, { 9.0D, -9.0D, -9.0D, 9.0D, 6.0D, 3.0D, -6.0D, -3.0D, 6.0D, -6.0D, 3.0D, -3.0D, 4.0D, 2.0D, 2.0D, 1.0D }, { -6.0D, 6.0D, 6.0D, -6.0D, -3.0D, -3.0D, 3.0D, 3.0D, -4.0D, 4.0D, -2.0D, 2.0D, -2.0D, -2.0D, -1.0D, -1.0D }, { 2.0D, 0.0D, -2.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D, 1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D }, { 0.0D, 0.0D, 0.0D, 0.0D, 2.0D, 0.0D, -2.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D, 1.0D, 0.0D }, { -6.0D, 6.0D, 6.0D, -6.0D, -4.0D, -2.0D, 4.0D, 2.0D, -3.0D, 3.0D, -3.0D, 3.0D, -2.0D, -1.0D, -2.0D, -1.0D }, { 4.0D, -4.0D, -4.0D, 4.0D, 2.0D, 2.0D, -2.0D, -2.0D, 2.0D, -2.0D, 2.0D, -2.0D, 1.0D, 1.0D, 1.0D, 1.0D } };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final double[] xval;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final double[] yval;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final BicubicFunction[][] splines;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BicubicInterpolatingFunction(double[] x, double[] y, double[][] f, double[][] dFdX, double[][] dFdY, double[][] d2FdXdY) throws DimensionMismatchException, NoDataException, NonMonotonicSequenceException {
/*  93 */     int xLen = x.length;
/*  94 */     int yLen = y.length;
/*     */     
/*  96 */     if (xLen == 0 || yLen == 0 || f.length == 0 || (f[0]).length == 0) {
/*  97 */       throw new NoDataException();
/*     */     }
/*  99 */     if (xLen != f.length) {
/* 100 */       throw new DimensionMismatchException(xLen, f.length);
/*     */     }
/* 102 */     if (xLen != dFdX.length) {
/* 103 */       throw new DimensionMismatchException(xLen, dFdX.length);
/*     */     }
/* 105 */     if (xLen != dFdY.length) {
/* 106 */       throw new DimensionMismatchException(xLen, dFdY.length);
/*     */     }
/* 108 */     if (xLen != d2FdXdY.length) {
/* 109 */       throw new DimensionMismatchException(xLen, d2FdXdY.length);
/*     */     }
/*     */     
/* 112 */     MathArrays.checkOrder(x);
/* 113 */     MathArrays.checkOrder(y);
/*     */     
/* 115 */     this.xval = (double[])x.clone();
/* 116 */     this.yval = (double[])y.clone();
/*     */     
/* 118 */     int lastI = xLen - 1;
/* 119 */     int lastJ = yLen - 1;
/* 120 */     this.splines = new BicubicFunction[lastI][lastJ];
/*     */     
/* 122 */     for (int i = 0; i < lastI; i++) {
/* 123 */       if ((f[i]).length != yLen) {
/* 124 */         throw new DimensionMismatchException((f[i]).length, yLen);
/*     */       }
/* 126 */       if ((dFdX[i]).length != yLen) {
/* 127 */         throw new DimensionMismatchException((dFdX[i]).length, yLen);
/*     */       }
/* 129 */       if ((dFdY[i]).length != yLen) {
/* 130 */         throw new DimensionMismatchException((dFdY[i]).length, yLen);
/*     */       }
/* 132 */       if ((d2FdXdY[i]).length != yLen) {
/* 133 */         throw new DimensionMismatchException((d2FdXdY[i]).length, yLen);
/*     */       }
/* 135 */       int ip1 = i + 1;
/* 136 */       double xR = this.xval[ip1] - this.xval[i];
/* 137 */       for (int j = 0; j < lastJ; j++) {
/* 138 */         int jp1 = j + 1;
/* 139 */         double yR = this.yval[jp1] - this.yval[j];
/* 140 */         double xRyR = xR * yR;
/* 141 */         double[] beta = { f[i][j], f[ip1][j], f[i][jp1], f[ip1][jp1], dFdX[i][j] * xR, dFdX[ip1][j] * xR, dFdX[i][jp1] * xR, dFdX[ip1][jp1] * xR, dFdY[i][j] * yR, dFdY[ip1][j] * yR, dFdY[i][jp1] * yR, dFdY[ip1][jp1] * yR, d2FdXdY[i][j] * xRyR, d2FdXdY[ip1][j] * xRyR, d2FdXdY[i][jp1] * xRyR, d2FdXdY[ip1][jp1] * xRyR };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 148 */         this.splines[i][j] = new BicubicFunction(computeSplineCoefficients(beta));
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double value(double x, double y) throws OutOfRangeException {
/* 158 */     int i = searchIndex(x, this.xval);
/* 159 */     int j = searchIndex(y, this.yval);
/*     */     
/* 161 */     double xN = (x - this.xval[i]) / (this.xval[i + 1] - this.xval[i]);
/* 162 */     double yN = (y - this.yval[j]) / (this.yval[j + 1] - this.yval[j]);
/*     */     
/* 164 */     return this.splines[i][j].value(xN, yN);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isValidPoint(double x, double y) {
/* 175 */     if (x < this.xval[0] || x > this.xval[this.xval.length - 1] || y < this.yval[0] || y > this.yval[this.yval.length - 1])
/*     */     {
/*     */ 
/*     */       
/* 179 */       return false;
/*     */     }
/* 181 */     return true;
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
/*     */   private int searchIndex(double c, double[] val) {
/* 194 */     int r = Arrays.binarySearch(val, c);
/*     */     
/* 196 */     if (r == -1 || r == -val.length - 1)
/*     */     {
/* 198 */       throw new OutOfRangeException(Double.valueOf(c), Double.valueOf(val[0]), Double.valueOf(val[val.length - 1]));
/*     */     }
/*     */     
/* 201 */     if (r < 0)
/*     */     {
/*     */       
/* 204 */       return -r - 2;
/*     */     }
/* 206 */     int last = val.length - 1;
/* 207 */     if (r == last)
/*     */     {
/*     */       
/* 210 */       return last - 1;
/*     */     }
/*     */ 
/*     */     
/* 214 */     return r;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private double[] computeSplineCoefficients(double[] beta) {
/* 247 */     double[] a = new double[16];
/*     */     
/* 249 */     for (int i = 0; i < 16; i++) {
/* 250 */       double result = 0.0D;
/* 251 */       double[] row = AINV[i];
/* 252 */       for (int j = 0; j < 16; j++) {
/* 253 */         result += row[j] * beta[j];
/*     */       }
/* 255 */       a[i] = result;
/*     */     } 
/*     */     
/* 258 */     return a;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/analysis/interpolation/BicubicInterpolatingFunction.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */