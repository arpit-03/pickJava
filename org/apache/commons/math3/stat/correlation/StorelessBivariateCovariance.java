/*     */ package org.apache.commons.math3.stat.correlation;
/*     */ 
/*     */ import org.apache.commons.math3.exception.NumberIsTooSmallException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class StorelessBivariateCovariance
/*     */ {
/*     */   private double meanX;
/*     */   private double meanY;
/*     */   private double n;
/*     */   private double covarianceNumerator;
/*     */   private boolean biasCorrected;
/*     */   
/*     */   StorelessBivariateCovariance() {
/*  60 */     this(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   StorelessBivariateCovariance(boolean biasCorrection) {
/*  71 */     this.meanX = this.meanY = 0.0D;
/*  72 */     this.n = 0.0D;
/*  73 */     this.covarianceNumerator = 0.0D;
/*  74 */     this.biasCorrected = biasCorrection;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void increment(double x, double y) {
/*  84 */     this.n++;
/*  85 */     double deltaX = x - this.meanX;
/*  86 */     double deltaY = y - this.meanY;
/*  87 */     this.meanX += deltaX / this.n;
/*  88 */     this.meanY += deltaY / this.n;
/*  89 */     this.covarianceNumerator += (this.n - 1.0D) / this.n * deltaX * deltaY;
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
/*     */   public void append(StorelessBivariateCovariance cov) {
/* 101 */     double oldN = this.n;
/* 102 */     this.n += cov.n;
/* 103 */     double deltaX = cov.meanX - this.meanX;
/* 104 */     double deltaY = cov.meanY - this.meanY;
/* 105 */     this.meanX += deltaX * cov.n / this.n;
/* 106 */     this.meanY += deltaY * cov.n / this.n;
/* 107 */     this.covarianceNumerator += cov.covarianceNumerator + oldN * cov.n / this.n * deltaX * deltaY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getN() {
/* 116 */     return this.n;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getResult() throws NumberIsTooSmallException {
/* 127 */     if (this.n < 2.0D) {
/* 128 */       throw new NumberIsTooSmallException(LocalizedFormats.INSUFFICIENT_DIMENSION, Double.valueOf(this.n), Integer.valueOf(2), true);
/*     */     }
/*     */     
/* 131 */     if (this.biasCorrected) {
/* 132 */       return this.covarianceNumerator / (this.n - 1.0D);
/*     */     }
/* 134 */     return this.covarianceNumerator / this.n;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/stat/correlation/StorelessBivariateCovariance.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */