/*     */ package org.apache.commons.math3.stat.regression;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Arrays;
/*     */ import org.apache.commons.math3.exception.OutOfRangeException;
/*     */ import org.apache.commons.math3.util.FastMath;
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
/*     */ 
/*     */ public class RegressionResults
/*     */   implements Serializable
/*     */ {
/*     */   private static final int SSE_IDX = 0;
/*     */   private static final int SST_IDX = 1;
/*     */   private static final int RSQ_IDX = 2;
/*     */   private static final int MSE_IDX = 3;
/*     */   private static final int ADJRSQ_IDX = 4;
/*     */   private static final long serialVersionUID = 1L;
/*     */   private final double[] parameters;
/*     */   private final double[][] varCovData;
/*     */   private final boolean isSymmetricVCD;
/*     */   private final int rank;
/*     */   private final long nobs;
/*     */   private final boolean containsConstant;
/*     */   private final double[] globalFitInfo;
/*     */   
/*     */   private RegressionResults() {
/*  66 */     this.parameters = null;
/*  67 */     this.varCovData = (double[][])null;
/*  68 */     this.rank = -1;
/*  69 */     this.nobs = -1L;
/*  70 */     this.containsConstant = false;
/*  71 */     this.isSymmetricVCD = false;
/*  72 */     this.globalFitInfo = null;
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
/*     */   public RegressionResults(double[] parameters, double[][] varcov, boolean isSymmetricCompressed, long nobs, int rank, double sumy, double sumysq, double sse, boolean containsConstant, boolean copyData) {
/*  99 */     if (copyData) {
/* 100 */       this.parameters = MathArrays.copyOf(parameters);
/* 101 */       this.varCovData = new double[varcov.length][];
/* 102 */       for (int i = 0; i < varcov.length; i++) {
/* 103 */         this.varCovData[i] = MathArrays.copyOf(varcov[i]);
/*     */       }
/*     */     } else {
/* 106 */       this.parameters = parameters;
/* 107 */       this.varCovData = varcov;
/*     */     } 
/* 109 */     this.isSymmetricVCD = isSymmetricCompressed;
/* 110 */     this.nobs = nobs;
/* 111 */     this.rank = rank;
/* 112 */     this.containsConstant = containsConstant;
/* 113 */     this.globalFitInfo = new double[5];
/* 114 */     Arrays.fill(this.globalFitInfo, Double.NaN);
/*     */     
/* 116 */     if (rank > 0) {
/* 117 */       this.globalFitInfo[1] = containsConstant ? (sumysq - sumy * sumy / nobs) : sumysq;
/*     */     }
/*     */ 
/*     */     
/* 121 */     this.globalFitInfo[0] = sse;
/* 122 */     this.globalFitInfo[3] = this.globalFitInfo[0] / (nobs - rank);
/*     */     
/* 124 */     this.globalFitInfo[2] = 1.0D - this.globalFitInfo[0] / this.globalFitInfo[1];
/*     */ 
/*     */ 
/*     */     
/* 128 */     if (!containsConstant) {
/* 129 */       this.globalFitInfo[4] = 1.0D - (1.0D - this.globalFitInfo[2]) * nobs / (nobs - rank);
/*     */     }
/*     */     else {
/*     */       
/* 133 */       this.globalFitInfo[4] = 1.0D - sse * (nobs - 1.0D) / this.globalFitInfo[1] * (nobs - rank);
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
/*     */   public double getParameterEstimate(int index) throws OutOfRangeException {
/* 150 */     if (this.parameters == null) {
/* 151 */       return Double.NaN;
/*     */     }
/* 153 */     if (index < 0 || index >= this.parameters.length) {
/* 154 */       throw new OutOfRangeException(Integer.valueOf(index), Integer.valueOf(0), Integer.valueOf(this.parameters.length - 1));
/*     */     }
/* 156 */     return this.parameters[index];
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
/*     */   public double[] getParameterEstimates() {
/* 170 */     if (this.parameters == null) {
/* 171 */       return null;
/*     */     }
/* 173 */     return MathArrays.copyOf(this.parameters);
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
/*     */   public double getStdErrorOfEstimate(int index) throws OutOfRangeException {
/* 187 */     if (this.parameters == null) {
/* 188 */       return Double.NaN;
/*     */     }
/* 190 */     if (index < 0 || index >= this.parameters.length) {
/* 191 */       throw new OutOfRangeException(Integer.valueOf(index), Integer.valueOf(0), Integer.valueOf(this.parameters.length - 1));
/*     */     }
/* 193 */     double var = getVcvElement(index, index);
/* 194 */     if (!Double.isNaN(var) && var > Double.MIN_VALUE) {
/* 195 */       return FastMath.sqrt(var);
/*     */     }
/* 197 */     return Double.NaN;
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
/*     */   public double[] getStdErrorOfEstimates() {
/* 212 */     if (this.parameters == null) {
/* 213 */       return null;
/*     */     }
/* 215 */     double[] se = new double[this.parameters.length];
/* 216 */     for (int i = 0; i < this.parameters.length; i++) {
/* 217 */       double var = getVcvElement(i, i);
/* 218 */       if (!Double.isNaN(var) && var > Double.MIN_VALUE) {
/* 219 */         se[i] = FastMath.sqrt(var);
/*     */       } else {
/*     */         
/* 222 */         se[i] = Double.NaN;
/*     */       } 
/* 224 */     }  return se;
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
/*     */   public double getCovarianceOfParameters(int i, int j) throws OutOfRangeException {
/* 240 */     if (this.parameters == null) {
/* 241 */       return Double.NaN;
/*     */     }
/* 243 */     if (i < 0 || i >= this.parameters.length) {
/* 244 */       throw new OutOfRangeException(Integer.valueOf(i), Integer.valueOf(0), Integer.valueOf(this.parameters.length - 1));
/*     */     }
/* 246 */     if (j < 0 || j >= this.parameters.length) {
/* 247 */       throw new OutOfRangeException(Integer.valueOf(j), Integer.valueOf(0), Integer.valueOf(this.parameters.length - 1));
/*     */     }
/* 249 */     return getVcvElement(i, j);
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
/*     */   public int getNumberOfParameters() {
/* 261 */     if (this.parameters == null) {
/* 262 */       return -1;
/*     */     }
/* 264 */     return this.parameters.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getN() {
/* 273 */     return this.nobs;
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
/*     */   public double getTotalSumSquares() {
/* 287 */     return this.globalFitInfo[1];
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
/*     */   public double getRegressionSumSquares() {
/* 307 */     return this.globalFitInfo[1] - this.globalFitInfo[0];
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
/*     */   public double getErrorSumSquares() {
/* 329 */     return this.globalFitInfo[0];
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
/*     */   public double getMeanSquareError() {
/* 343 */     return this.globalFitInfo[3];
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
/*     */   public double getRSquared() {
/* 361 */     return this.globalFitInfo[2];
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
/*     */   public double getAdjustedRSquared() {
/* 379 */     return this.globalFitInfo[4];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasIntercept() {
/* 389 */     return this.containsConstant;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private double getVcvElement(int i, int j) {
/* 400 */     if (this.isSymmetricVCD) {
/* 401 */       if (this.varCovData.length > 1) {
/*     */         
/* 403 */         if (i == j)
/* 404 */           return this.varCovData[i][i]; 
/* 405 */         if (i >= (this.varCovData[j]).length) {
/* 406 */           return this.varCovData[i][j];
/*     */         }
/* 408 */         return this.varCovData[j][i];
/*     */       } 
/*     */       
/* 411 */       if (i > j) {
/* 412 */         return this.varCovData[0][(i + 1) * i / 2 + j];
/*     */       }
/* 414 */       return this.varCovData[0][(j + 1) * j / 2 + i];
/*     */     } 
/*     */ 
/*     */     
/* 418 */     return this.varCovData[i][j];
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/stat/regression/RegressionResults.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */