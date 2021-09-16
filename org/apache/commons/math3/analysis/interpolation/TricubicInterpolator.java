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
/*     */ public class TricubicInterpolator
/*     */   implements TrivariateGridInterpolator
/*     */ {
/*     */   public TricubicInterpolatingFunction interpolate(final double[] xval, final double[] yval, final double[] zval, double[][][] fval) throws NoDataException, NumberIsTooSmallException, DimensionMismatchException, NonMonotonicSequenceException {
/*  41 */     if (xval.length == 0 || yval.length == 0 || zval.length == 0 || fval.length == 0) {
/*  42 */       throw new NoDataException();
/*     */     }
/*  44 */     if (xval.length != fval.length) {
/*  45 */       throw new DimensionMismatchException(xval.length, fval.length);
/*     */     }
/*     */     
/*  48 */     MathArrays.checkOrder(xval);
/*  49 */     MathArrays.checkOrder(yval);
/*  50 */     MathArrays.checkOrder(zval);
/*     */     
/*  52 */     int xLen = xval.length;
/*  53 */     int yLen = yval.length;
/*  54 */     int zLen = zval.length;
/*     */ 
/*     */     
/*  57 */     double[][][] dFdX = new double[xLen][yLen][zLen];
/*  58 */     double[][][] dFdY = new double[xLen][yLen][zLen];
/*  59 */     double[][][] dFdZ = new double[xLen][yLen][zLen];
/*  60 */     double[][][] d2FdXdY = new double[xLen][yLen][zLen];
/*  61 */     double[][][] d2FdXdZ = new double[xLen][yLen][zLen];
/*  62 */     double[][][] d2FdYdZ = new double[xLen][yLen][zLen];
/*  63 */     double[][][] d3FdXdYdZ = new double[xLen][yLen][zLen];
/*     */     
/*  65 */     for (int i = 1; i < xLen - 1; i++) {
/*  66 */       if (yval.length != (fval[i]).length) {
/*  67 */         throw new DimensionMismatchException(yval.length, (fval[i]).length);
/*     */       }
/*     */       
/*  70 */       int nI = i + 1;
/*  71 */       int pI = i - 1;
/*     */       
/*  73 */       double nX = xval[nI];
/*  74 */       double pX = xval[pI];
/*     */       
/*  76 */       double deltaX = nX - pX;
/*     */       
/*  78 */       for (int j = 1; j < yLen - 1; j++) {
/*  79 */         if (zval.length != (fval[i][j]).length) {
/*  80 */           throw new DimensionMismatchException(zval.length, (fval[i][j]).length);
/*     */         }
/*     */         
/*  83 */         int nJ = j + 1;
/*  84 */         int pJ = j - 1;
/*     */         
/*  86 */         double nY = yval[nJ];
/*  87 */         double pY = yval[pJ];
/*     */         
/*  89 */         double deltaY = nY - pY;
/*  90 */         double deltaXY = deltaX * deltaY;
/*     */         
/*  92 */         for (int k = 1; k < zLen - 1; k++) {
/*  93 */           int nK = k + 1;
/*  94 */           int pK = k - 1;
/*     */           
/*  96 */           double nZ = zval[nK];
/*  97 */           double pZ = zval[pK];
/*     */           
/*  99 */           double deltaZ = nZ - pZ;
/*     */           
/* 101 */           dFdX[i][j][k] = (fval[nI][j][k] - fval[pI][j][k]) / deltaX;
/* 102 */           dFdY[i][j][k] = (fval[i][nJ][k] - fval[i][pJ][k]) / deltaY;
/* 103 */           dFdZ[i][j][k] = (fval[i][j][nK] - fval[i][j][pK]) / deltaZ;
/*     */           
/* 105 */           double deltaXZ = deltaX * deltaZ;
/* 106 */           double deltaYZ = deltaY * deltaZ;
/*     */           
/* 108 */           d2FdXdY[i][j][k] = (fval[nI][nJ][k] - fval[nI][pJ][k] - fval[pI][nJ][k] + fval[pI][pJ][k]) / deltaXY;
/* 109 */           d2FdXdZ[i][j][k] = (fval[nI][j][nK] - fval[nI][j][pK] - fval[pI][j][nK] + fval[pI][j][pK]) / deltaXZ;
/* 110 */           d2FdYdZ[i][j][k] = (fval[i][nJ][nK] - fval[i][nJ][pK] - fval[i][pJ][nK] + fval[i][pJ][pK]) / deltaYZ;
/*     */           
/* 112 */           double deltaXYZ = deltaXY * deltaZ;
/*     */           
/* 114 */           d3FdXdYdZ[i][j][k] = (fval[nI][nJ][nK] - fval[nI][pJ][nK] - fval[pI][nJ][nK] + fval[pI][pJ][nK] - fval[nI][nJ][pK] + fval[nI][pJ][pK] + fval[pI][nJ][pK] - fval[pI][pJ][pK]) / deltaXYZ;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 123 */     return new TricubicInterpolatingFunction(xval, yval, zval, fval, dFdX, dFdY, dFdZ, d2FdXdY, d2FdXdZ, d2FdYdZ, d3FdXdYdZ)
/*     */       {
/*     */ 
/*     */ 
/*     */         
/*     */         public boolean isValidPoint(double x, double y, double z)
/*     */         {
/* 130 */           if (x < xval[1] || x > xval[xval.length - 2] || y < yval[1] || y > yval[yval.length - 2] || z < zval[1] || z > zval[zval.length - 2])
/*     */           {
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 136 */             return false;
/*     */           }
/* 138 */           return true;
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/analysis/interpolation/TricubicInterpolator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */