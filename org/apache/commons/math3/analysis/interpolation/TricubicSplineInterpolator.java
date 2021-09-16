/*     */ package org.apache.commons.math3.analysis.interpolation;
/*     */ 
/*     */ import org.apache.commons.math3.analysis.TrivariateFunction;
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
/*     */ public class TricubicSplineInterpolator
/*     */   implements TrivariateGridInterpolator
/*     */ {
/*     */   public TricubicSplineInterpolatingFunction interpolate(double[] xval, double[] yval, double[] zval, double[][][] fval) throws NoDataException, NumberIsTooSmallException, DimensionMismatchException, NonMonotonicSequenceException {
/*  43 */     if (xval.length == 0 || yval.length == 0 || zval.length == 0 || fval.length == 0) {
/*  44 */       throw new NoDataException();
/*     */     }
/*  46 */     if (xval.length != fval.length) {
/*  47 */       throw new DimensionMismatchException(xval.length, fval.length);
/*     */     }
/*     */     
/*  50 */     MathArrays.checkOrder(xval);
/*  51 */     MathArrays.checkOrder(yval);
/*  52 */     MathArrays.checkOrder(zval);
/*     */     
/*  54 */     int xLen = xval.length;
/*  55 */     int yLen = yval.length;
/*  56 */     int zLen = zval.length;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  61 */     double[][][] fvalXY = new double[zLen][xLen][yLen];
/*  62 */     double[][][] fvalZX = new double[yLen][zLen][xLen];
/*  63 */     for (int i = 0; i < xLen; i++) {
/*  64 */       if ((fval[i]).length != yLen) {
/*  65 */         throw new DimensionMismatchException((fval[i]).length, yLen);
/*     */       }
/*     */       
/*  68 */       for (int i4 = 0; i4 < yLen; i4++) {
/*  69 */         if ((fval[i][i4]).length != zLen) {
/*  70 */           throw new DimensionMismatchException((fval[i][i4]).length, zLen);
/*     */         }
/*     */         
/*  73 */         for (int i5 = 0; i5 < zLen; i5++) {
/*  74 */           double v = fval[i][i4][i5];
/*  75 */           fvalXY[i5][i][i4] = v;
/*  76 */           fvalZX[i4][i5][i] = v;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  81 */     BicubicSplineInterpolator bsi = new BicubicSplineInterpolator(true);
/*     */ 
/*     */     
/*  84 */     BicubicSplineInterpolatingFunction[] xSplineYZ = new BicubicSplineInterpolatingFunction[xLen];
/*     */     
/*  86 */     for (int m = 0; m < xLen; m++) {
/*  87 */       xSplineYZ[m] = bsi.interpolate(yval, zval, fval[m]);
/*     */     }
/*     */ 
/*     */     
/*  91 */     BicubicSplineInterpolatingFunction[] ySplineZX = new BicubicSplineInterpolatingFunction[yLen];
/*     */     
/*  93 */     for (int j = 0; j < yLen; j++) {
/*  94 */       ySplineZX[j] = bsi.interpolate(zval, xval, fvalZX[j]);
/*     */     }
/*     */ 
/*     */     
/*  98 */     BicubicSplineInterpolatingFunction[] zSplineXY = new BicubicSplineInterpolatingFunction[zLen];
/*     */     
/* 100 */     for (int k = 0; k < zLen; k++) {
/* 101 */       zSplineXY[k] = bsi.interpolate(xval, yval, fvalXY[k]);
/*     */     }
/*     */ 
/*     */     
/* 105 */     double[][][] dFdX = new double[xLen][yLen][zLen];
/* 106 */     double[][][] dFdY = new double[xLen][yLen][zLen];
/* 107 */     double[][][] d2FdXdY = new double[xLen][yLen][zLen];
/* 108 */     for (int n = 0; n < zLen; n++) {
/* 109 */       BicubicSplineInterpolatingFunction f = zSplineXY[n];
/* 110 */       for (int i4 = 0; i4 < xLen; i4++) {
/* 111 */         double x = xval[i4];
/* 112 */         for (int i5 = 0; i5 < yLen; i5++) {
/* 113 */           double y = yval[i5];
/* 114 */           dFdX[i4][i5][n] = f.partialDerivativeX(x, y);
/* 115 */           dFdY[i4][i5][n] = f.partialDerivativeY(x, y);
/* 116 */           d2FdXdY[i4][i5][n] = f.partialDerivativeXY(x, y);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 122 */     double[][][] dFdZ = new double[xLen][yLen][zLen];
/* 123 */     double[][][] d2FdYdZ = new double[xLen][yLen][zLen];
/* 124 */     for (int i1 = 0; i1 < xLen; i1++) {
/* 125 */       BicubicSplineInterpolatingFunction f = xSplineYZ[i1];
/* 126 */       for (int i4 = 0; i4 < yLen; i4++) {
/* 127 */         double y = yval[i4];
/* 128 */         for (int i5 = 0; i5 < zLen; i5++) {
/* 129 */           double z = zval[i5];
/* 130 */           dFdZ[i1][i4][i5] = f.partialDerivativeY(y, z);
/* 131 */           d2FdYdZ[i1][i4][i5] = f.partialDerivativeXY(y, z);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 137 */     double[][][] d2FdZdX = new double[xLen][yLen][zLen];
/* 138 */     for (int i2 = 0; i2 < yLen; i2++) {
/* 139 */       BicubicSplineInterpolatingFunction f = ySplineZX[i2];
/* 140 */       for (int i4 = 0; i4 < zLen; i4++) {
/* 141 */         double z = zval[i4];
/* 142 */         for (int i5 = 0; i5 < xLen; i5++) {
/* 143 */           double x = xval[i5];
/* 144 */           d2FdZdX[i5][i2][i4] = f.partialDerivativeXY(z, x);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 150 */     double[][][] d3FdXdYdZ = new double[xLen][yLen][zLen];
/* 151 */     for (int i3 = 0; i3 < xLen; i3++) {
/* 152 */       int nI = nextIndex(i3, xLen);
/* 153 */       int pI = previousIndex(i3);
/* 154 */       for (int i4 = 0; i4 < yLen; i4++) {
/* 155 */         int nJ = nextIndex(i4, yLen);
/* 156 */         int pJ = previousIndex(i4);
/* 157 */         for (int i5 = 0; i5 < zLen; i5++) {
/* 158 */           int nK = nextIndex(i5, zLen);
/* 159 */           int pK = previousIndex(i5);
/*     */ 
/*     */           
/* 162 */           d3FdXdYdZ[i3][i4][i5] = (fval[nI][nJ][nK] - fval[nI][pJ][nK] - fval[pI][nJ][nK] + fval[pI][pJ][nK] - fval[nI][nJ][pK] + fval[nI][pJ][pK] + fval[pI][nJ][pK] - fval[pI][pJ][pK]) / (xval[nI] - xval[pI]) * (yval[nJ] - yval[pJ]) * (zval[nK] - zval[pK]);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 172 */     return new TricubicSplineInterpolatingFunction(xval, yval, zval, fval, dFdX, dFdY, dFdZ, d2FdXdY, d2FdZdX, d2FdYdZ, d3FdXdYdZ);
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
/*     */   private int nextIndex(int i, int max) {
/* 187 */     int index = i + 1;
/* 188 */     return (index < max) ? index : (index - 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int previousIndex(int i) {
/* 198 */     int index = i - 1;
/* 199 */     return (index >= 0) ? index : 0;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/analysis/interpolation/TricubicSplineInterpolator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */