/*     */ package org.apache.commons.math3.stat.correlation;
/*     */ 
/*     */ import org.apache.commons.math3.exception.MathIllegalArgumentException;
/*     */ import org.apache.commons.math3.exception.NotStrictlyPositiveException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.linear.BlockRealMatrix;
/*     */ import org.apache.commons.math3.linear.RealMatrix;
/*     */ import org.apache.commons.math3.stat.descriptive.moment.Mean;
/*     */ import org.apache.commons.math3.stat.descriptive.moment.Variance;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Covariance
/*     */ {
/*     */   private final RealMatrix covarianceMatrix;
/*     */   private final int n;
/*     */   
/*     */   public Covariance() {
/*  62 */     this.covarianceMatrix = null;
/*  63 */     this.n = 0;
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
/*     */   public Covariance(double[][] data, boolean biasCorrected) throws MathIllegalArgumentException, NotStrictlyPositiveException {
/*  85 */     this((RealMatrix)new BlockRealMatrix(data), biasCorrected);
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
/*     */   public Covariance(double[][] data) throws MathIllegalArgumentException, NotStrictlyPositiveException {
/* 103 */     this(data, true);
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
/*     */   public Covariance(RealMatrix matrix, boolean biasCorrected) throws MathIllegalArgumentException {
/* 122 */     checkSufficientData(matrix);
/* 123 */     this.n = matrix.getRowDimension();
/* 124 */     this.covarianceMatrix = computeCovarianceMatrix(matrix, biasCorrected);
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
/*     */   public Covariance(RealMatrix matrix) throws MathIllegalArgumentException {
/* 138 */     this(matrix, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RealMatrix getCovarianceMatrix() {
/* 147 */     return this.covarianceMatrix;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getN() {
/* 156 */     return this.n;
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
/*     */   protected RealMatrix computeCovarianceMatrix(RealMatrix matrix, boolean biasCorrected) throws MathIllegalArgumentException {
/* 169 */     int dimension = matrix.getColumnDimension();
/* 170 */     Variance variance = new Variance(biasCorrected);
/* 171 */     BlockRealMatrix blockRealMatrix = new BlockRealMatrix(dimension, dimension);
/* 172 */     for (int i = 0; i < dimension; i++) {
/* 173 */       for (int j = 0; j < i; j++) {
/* 174 */         double cov = covariance(matrix.getColumn(i), matrix.getColumn(j), biasCorrected);
/* 175 */         blockRealMatrix.setEntry(i, j, cov);
/* 176 */         blockRealMatrix.setEntry(j, i, cov);
/*     */       } 
/* 178 */       blockRealMatrix.setEntry(i, i, variance.evaluate(matrix.getColumn(i)));
/*     */     } 
/* 180 */     return (RealMatrix)blockRealMatrix;
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
/*     */   protected RealMatrix computeCovarianceMatrix(RealMatrix matrix) throws MathIllegalArgumentException {
/* 193 */     return computeCovarianceMatrix(matrix, true);
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
/*     */   protected RealMatrix computeCovarianceMatrix(double[][] data, boolean biasCorrected) throws MathIllegalArgumentException, NotStrictlyPositiveException {
/* 209 */     return computeCovarianceMatrix((RealMatrix)new BlockRealMatrix(data), biasCorrected);
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
/*     */   protected RealMatrix computeCovarianceMatrix(double[][] data) throws MathIllegalArgumentException, NotStrictlyPositiveException {
/* 224 */     return computeCovarianceMatrix(data, true);
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
/*     */   public double covariance(double[] xArray, double[] yArray, boolean biasCorrected) throws MathIllegalArgumentException {
/* 241 */     Mean mean = new Mean();
/* 242 */     double result = 0.0D;
/* 243 */     int length = xArray.length;
/* 244 */     if (length != yArray.length) {
/* 245 */       throw new MathIllegalArgumentException(LocalizedFormats.DIMENSIONS_MISMATCH_SIMPLE, new Object[] { Integer.valueOf(length), Integer.valueOf(yArray.length) });
/*     */     }
/* 247 */     if (length < 2) {
/* 248 */       throw new MathIllegalArgumentException(LocalizedFormats.INSUFFICIENT_OBSERVED_POINTS_IN_SAMPLE, new Object[] { Integer.valueOf(length), Integer.valueOf(2) });
/*     */     }
/*     */     
/* 251 */     double xMean = mean.evaluate(xArray);
/* 252 */     double yMean = mean.evaluate(yArray);
/* 253 */     for (int i = 0; i < length; i++) {
/* 254 */       double xDev = xArray[i] - xMean;
/* 255 */       double yDev = yArray[i] - yMean;
/* 256 */       result += (xDev * yDev - result) / (i + 1);
/*     */     } 
/*     */     
/* 259 */     return biasCorrected ? (result * length / (length - 1)) : result;
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
/*     */   public double covariance(double[] xArray, double[] yArray) throws MathIllegalArgumentException {
/* 276 */     return covariance(xArray, yArray, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkSufficientData(RealMatrix matrix) throws MathIllegalArgumentException {
/* 287 */     int nRows = matrix.getRowDimension();
/* 288 */     int nCols = matrix.getColumnDimension();
/* 289 */     if (nRows < 2 || nCols < 1)
/* 290 */       throw new MathIllegalArgumentException(LocalizedFormats.INSUFFICIENT_ROWS_AND_COLUMNS, new Object[] { Integer.valueOf(nRows), Integer.valueOf(nCols) }); 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/stat/correlation/Covariance.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */