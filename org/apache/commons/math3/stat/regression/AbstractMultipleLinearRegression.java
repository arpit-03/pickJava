/*     */ package org.apache.commons.math3.stat.regression;
/*     */ 
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.InsufficientDataException;
/*     */ import org.apache.commons.math3.exception.MathIllegalArgumentException;
/*     */ import org.apache.commons.math3.exception.NoDataException;
/*     */ import org.apache.commons.math3.exception.NullArgumentException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.linear.Array2DRowRealMatrix;
/*     */ import org.apache.commons.math3.linear.ArrayRealVector;
/*     */ import org.apache.commons.math3.linear.NonSquareMatrixException;
/*     */ import org.apache.commons.math3.linear.RealMatrix;
/*     */ import org.apache.commons.math3.linear.RealVector;
/*     */ import org.apache.commons.math3.stat.descriptive.moment.Variance;
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
/*     */ public abstract class AbstractMultipleLinearRegression
/*     */   implements MultipleLinearRegression
/*     */ {
/*     */   private RealMatrix xMatrix;
/*     */   private RealVector yVector;
/*     */   private boolean noIntercept = false;
/*     */   
/*     */   protected RealMatrix getX() {
/*  53 */     return this.xMatrix;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected RealVector getY() {
/*  60 */     return this.yVector;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isNoIntercept() {
/*  68 */     return this.noIntercept;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNoIntercept(boolean noIntercept) {
/*  76 */     this.noIntercept = noIntercept;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void newSampleData(double[] data, int nobs, int nvars) {
/* 115 */     if (data == null) {
/* 116 */       throw new NullArgumentException();
/*     */     }
/* 118 */     if (data.length != nobs * (nvars + 1)) {
/* 119 */       throw new DimensionMismatchException(data.length, nobs * (nvars + 1));
/*     */     }
/* 121 */     if (nobs <= nvars) {
/* 122 */       throw new InsufficientDataException(LocalizedFormats.INSUFFICIENT_OBSERVED_POINTS_IN_SAMPLE, new Object[] { Integer.valueOf(nobs), Integer.valueOf(nvars + 1) });
/*     */     }
/* 124 */     double[] y = new double[nobs];
/* 125 */     int cols = this.noIntercept ? nvars : (nvars + 1);
/* 126 */     double[][] x = new double[nobs][cols];
/* 127 */     int pointer = 0;
/* 128 */     for (int i = 0; i < nobs; i++) {
/* 129 */       y[i] = data[pointer++];
/* 130 */       if (!this.noIntercept) {
/* 131 */         x[i][0] = 1.0D;
/*     */       }
/* 133 */       for (int j = this.noIntercept ? 0 : 1; j < cols; j++) {
/* 134 */         x[i][j] = data[pointer++];
/*     */       }
/*     */     } 
/* 137 */     this.xMatrix = (RealMatrix)new Array2DRowRealMatrix(x);
/* 138 */     this.yVector = (RealVector)new ArrayRealVector(y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void newYSampleData(double[] y) {
/* 149 */     if (y == null) {
/* 150 */       throw new NullArgumentException();
/*     */     }
/* 152 */     if (y.length == 0) {
/* 153 */       throw new NoDataException();
/*     */     }
/* 155 */     this.yVector = (RealVector)new ArrayRealVector(y);
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
/*     */ 
/*     */ 
/*     */   
/*     */   protected void newXSampleData(double[][] x) {
/* 184 */     if (x == null) {
/* 185 */       throw new NullArgumentException();
/*     */     }
/* 187 */     if (x.length == 0) {
/* 188 */       throw new NoDataException();
/*     */     }
/* 190 */     if (this.noIntercept) {
/* 191 */       this.xMatrix = (RealMatrix)new Array2DRowRealMatrix(x, true);
/*     */     } else {
/* 193 */       int nVars = (x[0]).length;
/* 194 */       double[][] xAug = new double[x.length][nVars + 1];
/* 195 */       for (int i = 0; i < x.length; i++) {
/* 196 */         if ((x[i]).length != nVars) {
/* 197 */           throw new DimensionMismatchException((x[i]).length, nVars);
/*     */         }
/* 199 */         xAug[i][0] = 1.0D;
/* 200 */         System.arraycopy(x[i], 0, xAug[i], 1, nVars);
/*     */       } 
/* 202 */       this.xMatrix = (RealMatrix)new Array2DRowRealMatrix(xAug, false);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void validateSampleData(double[][] x, double[] y) throws MathIllegalArgumentException {
/* 225 */     if (x == null || y == null) {
/* 226 */       throw new NullArgumentException();
/*     */     }
/* 228 */     if (x.length != y.length) {
/* 229 */       throw new DimensionMismatchException(y.length, x.length);
/*     */     }
/* 231 */     if (x.length == 0) {
/* 232 */       throw new NoDataException();
/*     */     }
/* 234 */     if ((x[0]).length + 1 > x.length) {
/* 235 */       throw new MathIllegalArgumentException(LocalizedFormats.NOT_ENOUGH_DATA_FOR_NUMBER_OF_PREDICTORS, new Object[] { Integer.valueOf(x.length), Integer.valueOf((x[0]).length) });
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
/*     */   
/*     */   protected void validateCovarianceData(double[][] x, double[][] covariance) {
/* 252 */     if (x.length != covariance.length) {
/* 253 */       throw new DimensionMismatchException(x.length, covariance.length);
/*     */     }
/* 255 */     if (covariance.length > 0 && covariance.length != (covariance[0]).length) {
/* 256 */       throw new NonSquareMatrixException(covariance.length, (covariance[0]).length);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] estimateRegressionParameters() {
/* 264 */     RealVector b = calculateBeta();
/* 265 */     return b.toArray();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] estimateResiduals() {
/* 272 */     RealVector b = calculateBeta();
/* 273 */     RealVector e = this.yVector.subtract(this.xMatrix.operate(b));
/* 274 */     return e.toArray();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[][] estimateRegressionParametersVariance() {
/* 281 */     return calculateBetaVariance().getData();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] estimateRegressionParametersStandardErrors() {
/* 288 */     double[][] betaVariance = estimateRegressionParametersVariance();
/* 289 */     double sigma = calculateErrorVariance();
/* 290 */     int length = (betaVariance[0]).length;
/* 291 */     double[] result = new double[length];
/* 292 */     for (int i = 0; i < length; i++) {
/* 293 */       result[i] = FastMath.sqrt(sigma * betaVariance[i][i]);
/*     */     }
/* 295 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double estimateRegressandVariance() {
/* 302 */     return calculateYVariance();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double estimateErrorVariance() {
/* 312 */     return calculateErrorVariance();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double estimateRegressionStandardError() {
/* 323 */     return FastMath.sqrt(estimateErrorVariance());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract RealVector calculateBeta();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract RealMatrix calculateBetaVariance();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected double calculateYVariance() {
/* 348 */     return (new Variance()).evaluate(this.yVector.toArray());
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
/*     */   protected double calculateErrorVariance() {
/* 363 */     RealVector residuals = calculateResiduals();
/* 364 */     return residuals.dotProduct(residuals) / (this.xMatrix.getRowDimension() - this.xMatrix.getColumnDimension());
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
/*     */   protected RealVector calculateResiduals() {
/* 379 */     RealVector b = calculateBeta();
/* 380 */     return this.yVector.subtract(this.xMatrix.operate(b));
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/stat/regression/AbstractMultipleLinearRegression.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */