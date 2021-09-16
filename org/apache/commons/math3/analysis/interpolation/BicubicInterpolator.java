/*     */ package org.apache.commons.math3.analysis.interpolation;
/*     */ 
/*     */ import org.apache.commons.math3.analysis.BivariateFunction;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BicubicInterpolator
/*     */   implements BivariateGridInterpolator
/*     */ {
/*     */   public BicubicInterpolatingFunction interpolate(final double[] xval, final double[] yval, double[][] fval) throws NoDataException, DimensionMismatchException, NonMonotonicSequenceException, NumberIsTooSmallException {
/*  52 */     if (xval.length == 0 || yval.length == 0 || fval.length == 0) {
/*  53 */       throw new NoDataException();
/*     */     }
/*  55 */     if (xval.length != fval.length) {
/*  56 */       throw new DimensionMismatchException(xval.length, fval.length);
/*     */     }
/*     */     
/*  59 */     MathArrays.checkOrder(xval);
/*  60 */     MathArrays.checkOrder(yval);
/*     */     
/*  62 */     int xLen = xval.length;
/*  63 */     int yLen = yval.length;
/*     */ 
/*     */     
/*  66 */     double[][] dFdX = new double[xLen][yLen];
/*  67 */     double[][] dFdY = new double[xLen][yLen];
/*  68 */     double[][] d2FdXdY = new double[xLen][yLen];
/*  69 */     for (int i = 1; i < xLen - 1; i++) {
/*  70 */       int nI = i + 1;
/*  71 */       int pI = i - 1;
/*     */       
/*  73 */       double nX = xval[nI];
/*  74 */       double pX = xval[pI];
/*     */       
/*  76 */       double deltaX = nX - pX;
/*     */       
/*  78 */       for (int j = 1; j < yLen - 1; j++) {
/*  79 */         int nJ = j + 1;
/*  80 */         int pJ = j - 1;
/*     */         
/*  82 */         double nY = yval[nJ];
/*  83 */         double pY = yval[pJ];
/*     */         
/*  85 */         double deltaY = nY - pY;
/*     */         
/*  87 */         dFdX[i][j] = (fval[nI][j] - fval[pI][j]) / deltaX;
/*  88 */         dFdY[i][j] = (fval[i][nJ] - fval[i][pJ]) / deltaY;
/*     */         
/*  90 */         double deltaXY = deltaX * deltaY;
/*     */         
/*  92 */         d2FdXdY[i][j] = (fval[nI][nJ] - fval[nI][pJ] - fval[pI][nJ] + fval[pI][pJ]) / deltaXY;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*  97 */     return new BicubicInterpolatingFunction(xval, yval, fval, dFdX, dFdY, d2FdXdY)
/*     */       {
/*     */         
/*     */         public boolean isValidPoint(double x, double y)
/*     */         {
/* 102 */           if (x < xval[1] || x > xval[xval.length - 2] || y < yval[1] || y > yval[yval.length - 2])
/*     */           {
/*     */ 
/*     */             
/* 106 */             return false;
/*     */           }
/* 108 */           return true;
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/analysis/interpolation/BicubicInterpolator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */