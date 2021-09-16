/*     */ package org.apache.commons.math3.stat.regression;
/*     */ 
/*     */ import org.apache.commons.math3.exception.MathIllegalArgumentException;
/*     */ import org.apache.commons.math3.linear.Array2DRowRealMatrix;
/*     */ import org.apache.commons.math3.linear.LUDecomposition;
/*     */ import org.apache.commons.math3.linear.QRDecomposition;
/*     */ import org.apache.commons.math3.linear.RealMatrix;
/*     */ import org.apache.commons.math3.linear.RealVector;
/*     */ import org.apache.commons.math3.stat.StatUtils;
/*     */ import org.apache.commons.math3.stat.descriptive.moment.SecondMoment;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class OLSMultipleLinearRegression
/*     */   extends AbstractMultipleLinearRegression
/*     */ {
/*  57 */   private QRDecomposition qr = null;
/*     */ 
/*     */ 
/*     */   
/*     */   private final double threshold;
/*     */ 
/*     */ 
/*     */   
/*     */   public OLSMultipleLinearRegression() {
/*  66 */     this(0.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OLSMultipleLinearRegression(double threshold) {
/*  77 */     this.threshold = threshold;
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
/*     */   public void newSampleData(double[] y, double[][] x) throws MathIllegalArgumentException {
/*  90 */     validateSampleData(x, y);
/*  91 */     newYSampleData(y);
/*  92 */     newXSampleData(x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void newSampleData(double[] data, int nobs, int nvars) {
/* 101 */     super.newSampleData(data, nobs, nvars);
/* 102 */     this.qr = new QRDecomposition(getX(), this.threshold);
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
/*     */   public RealMatrix calculateHat() {
/* 128 */     RealMatrix Q = this.qr.getQ();
/* 129 */     int p = this.qr.getR().getColumnDimension();
/* 130 */     int n = Q.getColumnDimension();
/*     */     
/* 132 */     Array2DRowRealMatrix augI = new Array2DRowRealMatrix(n, n);
/* 133 */     double[][] augIData = augI.getDataRef();
/* 134 */     for (int i = 0; i < n; i++) {
/* 135 */       for (int j = 0; j < n; j++) {
/* 136 */         if (i == j && i < p) {
/* 137 */           augIData[i][j] = 1.0D;
/*     */         } else {
/* 139 */           augIData[i][j] = 0.0D;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 146 */     return Q.multiply((RealMatrix)augI).multiply(Q.transpose());
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
/*     */   public double calculateTotalSumOfSquares() {
/* 164 */     if (isNoIntercept()) {
/* 165 */       return StatUtils.sumSq(getY().toArray());
/*     */     }
/* 167 */     return (new SecondMoment()).evaluate(getY().toArray());
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
/*     */   public double calculateResidualSumOfSquares() {
/* 180 */     RealVector residuals = calculateResiduals();
/*     */     
/* 182 */     return residuals.dotProduct(residuals);
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
/*     */   public double calculateRSquared() {
/* 200 */     return 1.0D - calculateResidualSumOfSquares() / calculateTotalSumOfSquares();
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
/*     */   public double calculateAdjustedRSquared() {
/* 224 */     double n = getX().getRowDimension();
/* 225 */     if (isNoIntercept()) {
/* 226 */       return 1.0D - (1.0D - calculateRSquared()) * n / (n - getX().getColumnDimension());
/*     */     }
/* 228 */     return 1.0D - calculateResidualSumOfSquares() * (n - 1.0D) / calculateTotalSumOfSquares() * (n - getX().getColumnDimension());
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
/*     */   protected void newXSampleData(double[][] x) {
/* 240 */     super.newXSampleData(x);
/* 241 */     this.qr = new QRDecomposition(getX(), this.threshold);
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
/*     */   protected RealVector calculateBeta() {
/* 257 */     return this.qr.getSolver().solve(getY());
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
/*     */   protected RealMatrix calculateBetaVariance() {
/* 279 */     int p = getX().getColumnDimension();
/* 280 */     RealMatrix Raug = this.qr.getR().getSubMatrix(0, p - 1, 0, p - 1);
/* 281 */     RealMatrix Rinv = (new LUDecomposition(Raug)).getSolver().getInverse();
/* 282 */     return Rinv.multiply(Rinv.transpose());
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/stat/regression/OLSMultipleLinearRegression.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */