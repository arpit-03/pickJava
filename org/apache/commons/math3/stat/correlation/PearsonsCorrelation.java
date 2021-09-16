/*     */ package org.apache.commons.math3.stat.correlation;
/*     */ 
/*     */ import org.apache.commons.math3.distribution.TDistribution;
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.MathIllegalArgumentException;
/*     */ import org.apache.commons.math3.exception.NullArgumentException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.linear.BlockRealMatrix;
/*     */ import org.apache.commons.math3.linear.RealMatrix;
/*     */ import org.apache.commons.math3.stat.regression.SimpleRegression;
/*     */ import org.apache.commons.math3.util.FastMath;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PearsonsCorrelation
/*     */ {
/*     */   private final RealMatrix correlationMatrix;
/*     */   private final int nObs;
/*     */   
/*     */   public PearsonsCorrelation() {
/*  64 */     this.correlationMatrix = null;
/*  65 */     this.nObs = 0;
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
/*     */   public PearsonsCorrelation(double[][] data) {
/*  82 */     this((RealMatrix)new BlockRealMatrix(data));
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
/*     */   public PearsonsCorrelation(RealMatrix matrix) {
/*  98 */     this.nObs = matrix.getRowDimension();
/*  99 */     this.correlationMatrix = computeCorrelationMatrix(matrix);
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
/*     */   public PearsonsCorrelation(Covariance covariance) {
/* 111 */     RealMatrix covarianceMatrix = covariance.getCovarianceMatrix();
/* 112 */     if (covarianceMatrix == null) {
/* 113 */       throw new NullArgumentException(LocalizedFormats.COVARIANCE_MATRIX, new Object[0]);
/*     */     }
/* 115 */     this.nObs = covariance.getN();
/* 116 */     this.correlationMatrix = covarianceToCorrelation(covarianceMatrix);
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
/*     */   public PearsonsCorrelation(RealMatrix covarianceMatrix, int numberOfObservations) {
/* 128 */     this.nObs = numberOfObservations;
/* 129 */     this.correlationMatrix = covarianceToCorrelation(covarianceMatrix);
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
/*     */   public RealMatrix getCorrelationMatrix() {
/* 142 */     return this.correlationMatrix;
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
/*     */   public RealMatrix getCorrelationStandardErrors() {
/* 163 */     int nVars = this.correlationMatrix.getColumnDimension();
/* 164 */     double[][] out = new double[nVars][nVars];
/* 165 */     for (int i = 0; i < nVars; i++) {
/* 166 */       for (int j = 0; j < nVars; j++) {
/* 167 */         double r = this.correlationMatrix.getEntry(i, j);
/* 168 */         out[i][j] = FastMath.sqrt((1.0D - r * r) / (this.nObs - 2));
/*     */       } 
/*     */     } 
/* 171 */     return (RealMatrix)new BlockRealMatrix(out);
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
/*     */   public RealMatrix getCorrelationPValues() {
/* 195 */     TDistribution tDistribution = new TDistribution((this.nObs - 2));
/* 196 */     int nVars = this.correlationMatrix.getColumnDimension();
/* 197 */     double[][] out = new double[nVars][nVars];
/* 198 */     for (int i = 0; i < nVars; i++) {
/* 199 */       for (int j = 0; j < nVars; j++) {
/* 200 */         if (i == j) {
/* 201 */           out[i][j] = 0.0D;
/*     */         } else {
/* 203 */           double r = this.correlationMatrix.getEntry(i, j);
/* 204 */           double t = FastMath.abs(r * FastMath.sqrt((this.nObs - 2) / (1.0D - r * r)));
/* 205 */           out[i][j] = 2.0D * tDistribution.cumulativeProbability(-t);
/*     */         } 
/*     */       } 
/*     */     } 
/* 209 */     return (RealMatrix)new BlockRealMatrix(out);
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
/*     */   public RealMatrix computeCorrelationMatrix(RealMatrix matrix) {
/* 227 */     checkSufficientData(matrix);
/* 228 */     int nVars = matrix.getColumnDimension();
/* 229 */     BlockRealMatrix blockRealMatrix = new BlockRealMatrix(nVars, nVars);
/* 230 */     for (int i = 0; i < nVars; i++) {
/* 231 */       for (int j = 0; j < i; j++) {
/* 232 */         double corr = correlation(matrix.getColumn(i), matrix.getColumn(j));
/* 233 */         blockRealMatrix.setEntry(i, j, corr);
/* 234 */         blockRealMatrix.setEntry(j, i, corr);
/*     */       } 
/* 236 */       blockRealMatrix.setEntry(i, i, 1.0D);
/*     */     } 
/* 238 */     return (RealMatrix)blockRealMatrix;
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
/*     */   public RealMatrix computeCorrelationMatrix(double[][] data) {
/* 256 */     return computeCorrelationMatrix((RealMatrix)new BlockRealMatrix(data));
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
/*     */   public double correlation(double[] xArray, double[] yArray) {
/* 274 */     SimpleRegression regression = new SimpleRegression();
/* 275 */     if (xArray.length != yArray.length)
/* 276 */       throw new DimensionMismatchException(xArray.length, yArray.length); 
/* 277 */     if (xArray.length < 2) {
/* 278 */       throw new MathIllegalArgumentException(LocalizedFormats.INSUFFICIENT_DIMENSION, new Object[] { Integer.valueOf(xArray.length), Integer.valueOf(2) });
/*     */     }
/*     */     
/* 281 */     for (int i = 0; i < xArray.length; i++) {
/* 282 */       regression.addData(xArray[i], yArray[i]);
/*     */     }
/* 284 */     return regression.getR();
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
/*     */   public RealMatrix covarianceToCorrelation(RealMatrix covarianceMatrix) {
/* 300 */     int nVars = covarianceMatrix.getColumnDimension();
/* 301 */     BlockRealMatrix blockRealMatrix = new BlockRealMatrix(nVars, nVars);
/* 302 */     for (int i = 0; i < nVars; i++) {
/* 303 */       double sigma = FastMath.sqrt(covarianceMatrix.getEntry(i, i));
/* 304 */       blockRealMatrix.setEntry(i, i, 1.0D);
/* 305 */       for (int j = 0; j < i; j++) {
/* 306 */         double entry = covarianceMatrix.getEntry(i, j) / sigma * FastMath.sqrt(covarianceMatrix.getEntry(j, j));
/*     */         
/* 308 */         blockRealMatrix.setEntry(i, j, entry);
/* 309 */         blockRealMatrix.setEntry(j, i, entry);
/*     */       } 
/*     */     } 
/* 312 */     return (RealMatrix)blockRealMatrix;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkSufficientData(RealMatrix matrix) {
/* 323 */     int nRows = matrix.getRowDimension();
/* 324 */     int nCols = matrix.getColumnDimension();
/* 325 */     if (nRows < 2 || nCols < 2)
/* 326 */       throw new MathIllegalArgumentException(LocalizedFormats.INSUFFICIENT_ROWS_AND_COLUMNS, new Object[] { Integer.valueOf(nRows), Integer.valueOf(nCols) }); 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/stat/correlation/PearsonsCorrelation.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */