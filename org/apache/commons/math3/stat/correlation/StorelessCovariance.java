/*     */ package org.apache.commons.math3.stat.correlation;
/*     */ 
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.MathUnsupportedOperationException;
/*     */ import org.apache.commons.math3.exception.NumberIsTooSmallException;
/*     */ import org.apache.commons.math3.linear.MatrixUtils;
/*     */ import org.apache.commons.math3.linear.RealMatrix;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StorelessCovariance
/*     */   extends Covariance
/*     */ {
/*     */   private StorelessBivariateCovariance[] covMatrix;
/*     */   private int dimension;
/*     */   
/*     */   public StorelessCovariance(int dim) {
/*  56 */     this(dim, true);
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
/*     */   public StorelessCovariance(int dim, boolean biasCorrected) {
/*  69 */     this.dimension = dim;
/*  70 */     this.covMatrix = new StorelessBivariateCovariance[this.dimension * (this.dimension + 1) / 2];
/*  71 */     initializeMatrix(biasCorrected);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initializeMatrix(boolean biasCorrected) {
/*  81 */     for (int i = 0; i < this.dimension; i++) {
/*  82 */       for (int j = 0; j < this.dimension; j++) {
/*  83 */         setElement(i, j, new StorelessBivariateCovariance(biasCorrected));
/*     */       }
/*     */     } 
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
/*     */   private int indexOf(int i, int j) {
/*  98 */     return (j < i) ? (i * (i + 1) / 2 + j) : (j * (j + 1) / 2 + i);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private StorelessBivariateCovariance getElement(int i, int j) {
/* 108 */     return this.covMatrix[indexOf(i, j)];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setElement(int i, int j, StorelessBivariateCovariance cov) {
/* 119 */     this.covMatrix[indexOf(i, j)] = cov;
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
/*     */   public double getCovariance(int xIndex, int yIndex) throws NumberIsTooSmallException {
/* 135 */     return getElement(xIndex, yIndex).getResult();
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
/*     */   public void increment(double[] data) throws DimensionMismatchException {
/* 149 */     int length = data.length;
/* 150 */     if (length != this.dimension) {
/* 151 */       throw new DimensionMismatchException(length, this.dimension);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 156 */     for (int i = 0; i < length; i++) {
/* 157 */       for (int j = i; j < length; j++) {
/* 158 */         getElement(i, j).increment(data[i], data[j]);
/*     */       }
/*     */     } 
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
/*     */   public void append(StorelessCovariance sc) throws DimensionMismatchException {
/* 175 */     if (sc.dimension != this.dimension) {
/* 176 */       throw new DimensionMismatchException(sc.dimension, this.dimension);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 181 */     for (int i = 0; i < this.dimension; i++) {
/* 182 */       for (int j = i; j < this.dimension; j++) {
/* 183 */         getElement(i, j).append(sc.getElement(i, j));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RealMatrix getCovarianceMatrix() throws NumberIsTooSmallException {
/* 195 */     return MatrixUtils.createRealMatrix(getData());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[][] getData() throws NumberIsTooSmallException {
/* 206 */     double[][] data = new double[this.dimension][this.dimension];
/* 207 */     for (int i = 0; i < this.dimension; i++) {
/* 208 */       for (int j = 0; j < this.dimension; j++) {
/* 209 */         data[i][j] = getElement(i, j).getResult();
/*     */       }
/*     */     } 
/* 212 */     return data;
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
/*     */   public int getN() throws MathUnsupportedOperationException {
/* 227 */     throw new MathUnsupportedOperationException();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/stat/correlation/StorelessCovariance.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */