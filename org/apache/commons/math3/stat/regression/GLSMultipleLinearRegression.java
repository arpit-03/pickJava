/*     */ package org.apache.commons.math3.stat.regression;
/*     */ 
/*     */ import org.apache.commons.math3.linear.Array2DRowRealMatrix;
/*     */ import org.apache.commons.math3.linear.LUDecomposition;
/*     */ import org.apache.commons.math3.linear.RealMatrix;
/*     */ import org.apache.commons.math3.linear.RealVector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GLSMultipleLinearRegression
/*     */   extends AbstractMultipleLinearRegression
/*     */ {
/*     */   private RealMatrix Omega;
/*     */   private RealMatrix OmegaInverse;
/*     */   
/*     */   public void newSampleData(double[] y, double[][] x, double[][] covariance) {
/*  56 */     validateSampleData(x, y);
/*  57 */     newYSampleData(y);
/*  58 */     newXSampleData(x);
/*  59 */     validateCovarianceData(x, covariance);
/*  60 */     newCovarianceData(covariance);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void newCovarianceData(double[][] omega) {
/*  69 */     this.Omega = (RealMatrix)new Array2DRowRealMatrix(omega);
/*  70 */     this.OmegaInverse = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected RealMatrix getOmegaInverse() {
/*  79 */     if (this.OmegaInverse == null) {
/*  80 */       this.OmegaInverse = (new LUDecomposition(this.Omega)).getSolver().getInverse();
/*     */     }
/*  82 */     return this.OmegaInverse;
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
/*     */   protected RealVector calculateBeta() {
/*  94 */     RealMatrix OI = getOmegaInverse();
/*  95 */     RealMatrix XT = getX().transpose();
/*  96 */     RealMatrix XTOIX = XT.multiply(OI).multiply(getX());
/*  97 */     RealMatrix inverse = (new LUDecomposition(XTOIX)).getSolver().getInverse();
/*  98 */     return inverse.multiply(XT).multiply(OI).operate(getY());
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
/*     */   protected RealMatrix calculateBetaVariance() {
/* 110 */     RealMatrix OI = getOmegaInverse();
/* 111 */     RealMatrix XTOIX = getX().transpose().multiply(OI).multiply(getX());
/* 112 */     return (new LUDecomposition(XTOIX)).getSolver().getInverse();
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
/*     */   protected double calculateErrorVariance() {
/* 129 */     RealVector residuals = calculateResiduals();
/* 130 */     double t = residuals.dotProduct(getOmegaInverse().operate(residuals));
/* 131 */     return t / (getX().getRowDimension() - getX().getColumnDimension());
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/stat/regression/GLSMultipleLinearRegression.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */