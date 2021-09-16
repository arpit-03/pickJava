/*     */ package org.apache.commons.math3.random;
/*     */ 
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.linear.RealMatrix;
/*     */ import org.apache.commons.math3.linear.RectangularCholeskyDecomposition;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CorrelatedRandomVectorGenerator
/*     */   implements RandomVectorGenerator
/*     */ {
/*     */   private final double[] mean;
/*     */   private final NormalizedRandomGenerator generator;
/*     */   private final double[] normalized;
/*     */   private final RealMatrix root;
/*     */   
/*     */   public CorrelatedRandomVectorGenerator(double[] mean, RealMatrix covariance, double small, NormalizedRandomGenerator generator) {
/*  89 */     int order = covariance.getRowDimension();
/*  90 */     if (mean.length != order) {
/*  91 */       throw new DimensionMismatchException(mean.length, order);
/*     */     }
/*  93 */     this.mean = (double[])mean.clone();
/*     */     
/*  95 */     RectangularCholeskyDecomposition decomposition = new RectangularCholeskyDecomposition(covariance, small);
/*     */     
/*  97 */     this.root = decomposition.getRootMatrix();
/*     */     
/*  99 */     this.generator = generator;
/* 100 */     this.normalized = new double[decomposition.getRank()];
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
/*     */   public CorrelatedRandomVectorGenerator(RealMatrix covariance, double small, NormalizedRandomGenerator generator) {
/* 118 */     int order = covariance.getRowDimension();
/* 119 */     this.mean = new double[order];
/* 120 */     for (int i = 0; i < order; i++) {
/* 121 */       this.mean[i] = 0.0D;
/*     */     }
/*     */     
/* 124 */     RectangularCholeskyDecomposition decomposition = new RectangularCholeskyDecomposition(covariance, small);
/*     */     
/* 126 */     this.root = decomposition.getRootMatrix();
/*     */     
/* 128 */     this.generator = generator;
/* 129 */     this.normalized = new double[decomposition.getRank()];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NormalizedRandomGenerator getGenerator() {
/* 137 */     return this.generator;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRank() {
/* 147 */     return this.normalized.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RealMatrix getRootMatrix() {
/* 157 */     return this.root;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] nextVector() {
/* 167 */     for (int i = 0; i < this.normalized.length; i++) {
/* 168 */       this.normalized[i] = this.generator.nextNormalizedDouble();
/*     */     }
/*     */ 
/*     */     
/* 172 */     double[] correlated = new double[this.mean.length];
/* 173 */     for (int j = 0; j < correlated.length; j++) {
/* 174 */       correlated[j] = this.mean[j];
/* 175 */       for (int k = 0; k < this.root.getColumnDimension(); k++) {
/* 176 */         correlated[j] = correlated[j] + this.root.getEntry(j, k) * this.normalized[k];
/*     */       }
/*     */     } 
/*     */     
/* 180 */     return correlated;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/random/CorrelatedRandomVectorGenerator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */