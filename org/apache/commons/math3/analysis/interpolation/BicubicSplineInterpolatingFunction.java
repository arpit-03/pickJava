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
/*     */ 
/*     */ 
/*     */ 
/*     */ @Deprecated
/*     */ public class BicubicSplineInterpolatingFunction
/*     */   implements BivariateFunction
/*     */ {
/*     */   private static final int NUM_COEFF = 16;
/*  46 */   private static final double[][] AINV = new double[][] { { 1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D }, { 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D }, { -3.0D, 3.0D, 0.0D, 0.0D, -2.0D, -1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D }, { 2.0D, -2.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D }, { 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D }, { 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D, 0.0D, 0.0D }, { 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, -3.0D, 3.0D, 0.0D, 0.0D, -2.0D, -1.0D, 0.0D, 0.0D }, { 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 2.0D, -2.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.0D, 0.0D }, { -3.0D, 0.0D, 3.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, -2.0D, 0.0D, -1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D }, { 0.0D, 0.0D, 0.0D, 0.0D, -3.0D, 0.0D, 3.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, -2.0D, 0.0D, -1.0D, 0.0D }, { 9.0D, -9.0D, -9.0D, 9.0D, 6.0D, 3.0D, -6.0D, -3.0D, 6.0D, -6.0D, 3.0D, -3.0D, 4.0D, 2.0D, 2.0D, 1.0D }, { -6.0D, 6.0D, 6.0D, -6.0D, -3.0D, -3.0D, 3.0D, 3.0D, -4.0D, 4.0D, -2.0D, 2.0D, -2.0D, -2.0D, -1.0D, -1.0D }, { 2.0D, 0.0D, -2.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D, 1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D }, { 0.0D, 0.0D, 0.0D, 0.0D, 2.0D, 0.0D, -2.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D, 1.0D, 0.0D }, { -6.0D, 6.0D, 6.0D, -6.0D, -4.0D, -2.0D, 4.0D, 2.0D, -3.0D, 3.0D, -3.0D, 3.0D, -2.0D, -1.0D, -2.0D, -1.0D }, { 4.0D, -4.0D, -4.0D, 4.0D, 2.0D, 2.0D, -2.0D, -2.0D, 2.0D, -2.0D, 2.0D, -2.0D, 1.0D, 1.0D, 1.0D, 1.0D } };
/*     */ 
/*     */ 
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
/*     */   private final BicubicSplineFunction[][] splines;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final BivariateFunction[][][] partialDerivatives;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BicubicSplineInterpolatingFunction(double[] x, double[] y, double[][] f, double[][] dFdX, double[][] dFdY, double[][] d2FdXdY) throws DimensionMismatchException, NoDataException, NonMonotonicSequenceException {
/* 107 */     this(x, y, f, dFdX, dFdY, d2FdXdY, false);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BicubicSplineInterpolatingFunction(double[] x, double[] y, double[][] f, double[][] dFdX, double[][] dFdY, double[][] d2FdXdY, boolean initializeDerivatives) throws DimensionMismatchException, NoDataException, NonMonotonicSequenceException {
/* 145 */     int xLen = x.length;
/* 146 */     int yLen = y.length;
/*     */     
/* 148 */     if (xLen == 0 || yLen == 0 || f.length == 0 || (f[0]).length == 0) {
/* 149 */       throw new NoDataException();
/*     */     }
/* 151 */     if (xLen != f.length) {
/* 152 */       throw new DimensionMismatchException(xLen, f.length);
/*     */     }
/* 154 */     if (xLen != dFdX.length) {
/* 155 */       throw new DimensionMismatchException(xLen, dFdX.length);
/*     */     }
/* 157 */     if (xLen != dFdY.length) {
/* 158 */       throw new DimensionMismatchException(xLen, dFdY.length);
/*     */     }
/* 160 */     if (xLen != d2FdXdY.length) {
/* 161 */       throw new DimensionMismatchException(xLen, d2FdXdY.length);
/*     */     }
/*     */     
/* 164 */     MathArrays.checkOrder(x);
/* 165 */     MathArrays.checkOrder(y);
/*     */     
/* 167 */     this.xval = (double[])x.clone();
/* 168 */     this.yval = (double[])y.clone();
/*     */     
/* 170 */     int lastI = xLen - 1;
/* 171 */     int lastJ = yLen - 1;
/* 172 */     this.splines = new BicubicSplineFunction[lastI][lastJ];
/*     */     int i;
/* 174 */     for (i = 0; i < lastI; i++) {
/* 175 */       if ((f[i]).length != yLen) {
/* 176 */         throw new DimensionMismatchException((f[i]).length, yLen);
/*     */       }
/* 178 */       if ((dFdX[i]).length != yLen) {
/* 179 */         throw new DimensionMismatchException((dFdX[i]).length, yLen);
/*     */       }
/* 181 */       if ((dFdY[i]).length != yLen) {
/* 182 */         throw new DimensionMismatchException((dFdY[i]).length, yLen);
/*     */       }
/* 184 */       if ((d2FdXdY[i]).length != yLen) {
/* 185 */         throw new DimensionMismatchException((d2FdXdY[i]).length, yLen);
/*     */       }
/* 187 */       int ip1 = i + 1;
/* 188 */       for (int j = 0; j < lastJ; j++) {
/* 189 */         int jp1 = j + 1;
/* 190 */         double[] beta = { f[i][j], f[ip1][j], f[i][jp1], f[ip1][jp1], dFdX[i][j], dFdX[ip1][j], dFdX[i][jp1], dFdX[ip1][jp1], dFdY[i][j], dFdY[ip1][j], dFdY[i][jp1], dFdY[ip1][jp1], d2FdXdY[i][j], d2FdXdY[ip1][j], d2FdXdY[i][jp1], d2FdXdY[ip1][jp1] };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 197 */         this.splines[i][j] = new BicubicSplineFunction(computeSplineCoefficients(beta), initializeDerivatives);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 202 */     if (initializeDerivatives) {
/*     */       
/* 204 */       this.partialDerivatives = new BivariateFunction[5][lastI][lastJ];
/*     */       
/* 206 */       for (i = 0; i < lastI; i++) {
/* 207 */         for (int j = 0; j < lastJ; j++) {
/* 208 */           BicubicSplineFunction bcs = this.splines[i][j];
/* 209 */           this.partialDerivatives[0][i][j] = bcs.partialDerivativeX();
/* 210 */           this.partialDerivatives[1][i][j] = bcs.partialDerivativeY();
/* 211 */           this.partialDerivatives[2][i][j] = bcs.partialDerivativeXX();
/* 212 */           this.partialDerivatives[3][i][j] = bcs.partialDerivativeYY();
/* 213 */           this.partialDerivatives[4][i][j] = bcs.partialDerivativeXY();
/*     */         } 
/*     */       } 
/*     */     } else {
/*     */       
/* 218 */       this.partialDerivatives = (BivariateFunction[][][])null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double value(double x, double y) throws OutOfRangeException {
/* 227 */     int i = searchIndex(x, this.xval);
/* 228 */     int j = searchIndex(y, this.yval);
/*     */     
/* 230 */     double xN = (x - this.xval[i]) / (this.xval[i + 1] - this.xval[i]);
/* 231 */     double yN = (y - this.yval[j]) / (this.yval[j + 1] - this.yval[j]);
/*     */     
/* 233 */     return this.splines[i][j].value(xN, yN);
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
/*     */   public boolean isValidPoint(double x, double y) {
/* 245 */     if (x < this.xval[0] || x > this.xval[this.xval.length - 1] || y < this.yval[0] || y > this.yval[this.yval.length - 1])
/*     */     {
/*     */ 
/*     */       
/* 249 */       return false;
/*     */     }
/* 251 */     return true;
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
/*     */   public double partialDerivativeX(double x, double y) throws OutOfRangeException {
/* 269 */     return partialDerivative(0, x, y);
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
/*     */   public double partialDerivativeY(double x, double y) throws OutOfRangeException {
/* 285 */     return partialDerivative(1, x, y);
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
/*     */   public double partialDerivativeXX(double x, double y) throws OutOfRangeException {
/* 301 */     return partialDerivative(2, x, y);
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
/*     */   public double partialDerivativeYY(double x, double y) throws OutOfRangeException {
/* 317 */     return partialDerivative(3, x, y);
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
/*     */   public double partialDerivativeXY(double x, double y) throws OutOfRangeException {
/* 332 */     return partialDerivative(4, x, y);
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
/*     */   private double partialDerivative(int which, double x, double y) throws OutOfRangeException {
/* 349 */     int i = searchIndex(x, this.xval);
/* 350 */     int j = searchIndex(y, this.yval);
/*     */     
/* 352 */     double xN = (x - this.xval[i]) / (this.xval[i + 1] - this.xval[i]);
/* 353 */     double yN = (y - this.yval[j]) / (this.yval[j + 1] - this.yval[j]);
/*     */     
/* 355 */     return this.partialDerivatives[which][i][j].value(xN, yN);
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
/*     */   private int searchIndex(double c, double[] val) {
/* 367 */     int r = Arrays.binarySearch(val, c);
/*     */     
/* 369 */     if (r == -1 || r == -val.length - 1)
/*     */     {
/* 371 */       throw new OutOfRangeException(Double.valueOf(c), Double.valueOf(val[0]), Double.valueOf(val[val.length - 1]));
/*     */     }
/*     */     
/* 374 */     if (r < 0)
/*     */     {
/*     */       
/* 377 */       return -r - 2;
/*     */     }
/* 379 */     int last = val.length - 1;
/* 380 */     if (r == last)
/*     */     {
/*     */       
/* 383 */       return last - 1;
/*     */     }
/*     */ 
/*     */     
/* 387 */     return r;
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
/* 420 */     double[] a = new double[16];
/*     */     
/* 422 */     for (int i = 0; i < 16; i++) {
/* 423 */       double result = 0.0D;
/* 424 */       double[] row = AINV[i];
/* 425 */       for (int j = 0; j < 16; j++) {
/* 426 */         result += row[j] * beta[j];
/*     */       }
/* 428 */       a[i] = result;
/*     */     } 
/*     */     
/* 431 */     return a;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/analysis/interpolation/BicubicSplineInterpolatingFunction.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */