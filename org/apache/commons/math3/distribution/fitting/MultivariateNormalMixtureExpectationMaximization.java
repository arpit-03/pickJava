/*     */ package org.apache.commons.math3.distribution.fitting;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import org.apache.commons.math3.distribution.MixtureMultivariateNormalDistribution;
/*     */ import org.apache.commons.math3.distribution.MultivariateNormalDistribution;
/*     */ import org.apache.commons.math3.exception.ConvergenceException;
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.NotStrictlyPositiveException;
/*     */ import org.apache.commons.math3.exception.NumberIsTooLargeException;
/*     */ import org.apache.commons.math3.exception.NumberIsTooSmallException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.linear.Array2DRowRealMatrix;
/*     */ import org.apache.commons.math3.linear.RealMatrix;
/*     */ import org.apache.commons.math3.linear.SingularMatrixException;
/*     */ import org.apache.commons.math3.stat.correlation.Covariance;
/*     */ import org.apache.commons.math3.util.FastMath;
/*     */ import org.apache.commons.math3.util.MathArrays;
/*     */ import org.apache.commons.math3.util.Pair;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MultivariateNormalMixtureExpectationMaximization
/*     */ {
/*     */   private static final int DEFAULT_MAX_ITERATIONS = 1000;
/*     */   private static final double DEFAULT_THRESHOLD = 1.0E-5D;
/*     */   private final double[][] data;
/*     */   private MixtureMultivariateNormalDistribution fittedModel;
/*  74 */   private double logLikelihood = 0.0D;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MultivariateNormalMixtureExpectationMaximization(double[][] data) throws NotStrictlyPositiveException, DimensionMismatchException, NumberIsTooSmallException {
/*  90 */     if (data.length < 1) {
/*  91 */       throw new NotStrictlyPositiveException(Integer.valueOf(data.length));
/*     */     }
/*     */     
/*  94 */     this.data = new double[data.length][(data[0]).length];
/*     */     
/*  96 */     for (int i = 0; i < data.length; i++) {
/*  97 */       if ((data[i]).length != (data[0]).length)
/*     */       {
/*  99 */         throw new DimensionMismatchException((data[i]).length, (data[0]).length);
/*     */       }
/*     */       
/* 102 */       if ((data[i]).length < 2) {
/* 103 */         throw new NumberIsTooSmallException(LocalizedFormats.NUMBER_TOO_SMALL, Integer.valueOf((data[i]).length), Integer.valueOf(2), true);
/*     */       }
/*     */       
/* 106 */       this.data[i] = MathArrays.copyOf(data[i], (data[i]).length);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fit(MixtureMultivariateNormalDistribution initialMixture, int maxIterations, double threshold) throws SingularMatrixException, NotStrictlyPositiveException, DimensionMismatchException {
/* 138 */     if (maxIterations < 1) {
/* 139 */       throw new NotStrictlyPositiveException(Integer.valueOf(maxIterations));
/*     */     }
/*     */     
/* 142 */     if (threshold < Double.MIN_VALUE) {
/* 143 */       throw new NotStrictlyPositiveException(Double.valueOf(threshold));
/*     */     }
/*     */     
/* 146 */     int n = this.data.length;
/*     */ 
/*     */ 
/*     */     
/* 150 */     int numCols = (this.data[0]).length;
/* 151 */     int k = initialMixture.getComponents().size();
/*     */     
/* 153 */     int numMeanColumns = (((MultivariateNormalDistribution)((Pair)initialMixture.getComponents().get(0)).getSecond()).getMeans()).length;
/*     */ 
/*     */     
/* 156 */     if (numMeanColumns != numCols) {
/* 157 */       throw new DimensionMismatchException(numMeanColumns, numCols);
/*     */     }
/*     */     
/* 160 */     int numIterations = 0;
/* 161 */     double previousLogLikelihood = 0.0D;
/*     */     
/* 163 */     this.logLikelihood = Double.NEGATIVE_INFINITY;
/*     */ 
/*     */     
/* 166 */     this.fittedModel = new MixtureMultivariateNormalDistribution(initialMixture.getComponents());
/*     */     
/* 168 */     while (numIterations++ <= maxIterations && FastMath.abs(previousLogLikelihood - this.logLikelihood) > threshold) {
/*     */       
/* 170 */       previousLogLikelihood = this.logLikelihood;
/* 171 */       double sumLogLikelihood = 0.0D;
/*     */ 
/*     */       
/* 174 */       List<Pair<Double, MultivariateNormalDistribution>> components = this.fittedModel.getComponents();
/*     */ 
/*     */ 
/*     */       
/* 178 */       double[] weights = new double[k];
/*     */       
/* 180 */       MultivariateNormalDistribution[] mvns = new MultivariateNormalDistribution[k];
/*     */       
/* 182 */       for (int j = 0; j < k; j++) {
/* 183 */         weights[j] = ((Double)((Pair)components.get(j)).getFirst()).doubleValue();
/* 184 */         mvns[j] = (MultivariateNormalDistribution)((Pair)components.get(j)).getSecond();
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 191 */       double[][] gamma = new double[n][k];
/*     */ 
/*     */       
/* 194 */       double[] gammaSums = new double[k];
/*     */ 
/*     */       
/* 197 */       double[][] gammaDataProdSums = new double[k][numCols];
/*     */       
/* 199 */       for (int i = 0; i < n; i++) {
/* 200 */         double rowDensity = this.fittedModel.density(this.data[i]);
/* 201 */         sumLogLikelihood += FastMath.log(rowDensity);
/*     */         
/* 203 */         for (int i4 = 0; i4 < k; i4++) {
/* 204 */           gamma[i][i4] = weights[i4] * mvns[i4].density(this.data[i]) / rowDensity;
/* 205 */           gammaSums[i4] = gammaSums[i4] + gamma[i][i4];
/*     */           
/* 207 */           for (int col = 0; col < numCols; col++) {
/* 208 */             gammaDataProdSums[i4][col] = gammaDataProdSums[i4][col] + gamma[i][i4] * this.data[i][col];
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 213 */       this.logLikelihood = sumLogLikelihood / n;
/*     */ 
/*     */ 
/*     */       
/* 217 */       double[] newWeights = new double[k];
/* 218 */       double[][] newMeans = new double[k][numCols];
/*     */       
/* 220 */       for (int m = 0; m < k; m++) {
/* 221 */         newWeights[m] = gammaSums[m] / n;
/* 222 */         for (int col = 0; col < numCols; col++) {
/* 223 */           newMeans[m][col] = gammaDataProdSums[m][col] / gammaSums[m];
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 228 */       RealMatrix[] newCovMats = new RealMatrix[k];
/* 229 */       for (int i2 = 0; i2 < k; i2++) {
/* 230 */         newCovMats[i2] = (RealMatrix)new Array2DRowRealMatrix(numCols, numCols);
/*     */       }
/* 232 */       for (int i1 = 0; i1 < n; i1++) {
/* 233 */         for (int i4 = 0; i4 < k; i4++) {
/* 234 */           Array2DRowRealMatrix array2DRowRealMatrix = new Array2DRowRealMatrix(MathArrays.ebeSubtract(this.data[i1], newMeans[i4]));
/*     */           
/* 236 */           RealMatrix dataCov = array2DRowRealMatrix.multiply(array2DRowRealMatrix.transpose()).scalarMultiply(gamma[i1][i4]);
/*     */           
/* 238 */           newCovMats[i4] = newCovMats[i4].add(dataCov);
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 243 */       double[][][] newCovMatArrays = new double[k][numCols][numCols];
/* 244 */       for (int i3 = 0; i3 < k; i3++) {
/* 245 */         newCovMats[i3] = newCovMats[i3].scalarMultiply(1.0D / gammaSums[i3]);
/* 246 */         newCovMatArrays[i3] = newCovMats[i3].getData();
/*     */       } 
/*     */ 
/*     */       
/* 250 */       this.fittedModel = new MixtureMultivariateNormalDistribution(newWeights, newMeans, newCovMatArrays);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 255 */     if (FastMath.abs(previousLogLikelihood - this.logLikelihood) > threshold)
/*     */     {
/* 257 */       throw new ConvergenceException();
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
/*     */   
/*     */   public void fit(MixtureMultivariateNormalDistribution initialMixture) throws SingularMatrixException, NotStrictlyPositiveException {
/* 281 */     fit(initialMixture, 1000, 1.0E-5D);
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
/*     */   public static MixtureMultivariateNormalDistribution estimate(double[][] data, int numComponents) throws NotStrictlyPositiveException, DimensionMismatchException {
/* 306 */     if (data.length < 2) {
/* 307 */       throw new NotStrictlyPositiveException(Integer.valueOf(data.length));
/*     */     }
/* 309 */     if (numComponents < 2) {
/* 310 */       throw new NumberIsTooSmallException(Integer.valueOf(numComponents), Integer.valueOf(2), true);
/*     */     }
/* 312 */     if (numComponents > data.length) {
/* 313 */       throw new NumberIsTooLargeException(Integer.valueOf(numComponents), Integer.valueOf(data.length), true);
/*     */     }
/*     */     
/* 316 */     int numRows = data.length;
/* 317 */     int numCols = (data[0]).length;
/*     */ 
/*     */     
/* 320 */     DataRow[] sortedData = new DataRow[numRows];
/* 321 */     for (int i = 0; i < numRows; i++) {
/* 322 */       sortedData[i] = new DataRow(data[i]);
/*     */     }
/* 324 */     Arrays.sort((Object[])sortedData);
/*     */ 
/*     */     
/* 327 */     double weight = 1.0D / numComponents;
/*     */ 
/*     */     
/* 330 */     List<Pair<Double, MultivariateNormalDistribution>> components = new ArrayList<Pair<Double, MultivariateNormalDistribution>>(numComponents);
/*     */ 
/*     */ 
/*     */     
/* 334 */     for (int binIndex = 0; binIndex < numComponents; binIndex++) {
/*     */       
/* 336 */       int minIndex = binIndex * numRows / numComponents;
/*     */ 
/*     */       
/* 339 */       int maxIndex = (binIndex + 1) * numRows / numComponents;
/*     */ 
/*     */       
/* 342 */       int numBinRows = maxIndex - minIndex;
/*     */ 
/*     */       
/* 345 */       double[][] binData = new double[numBinRows][numCols];
/*     */ 
/*     */       
/* 348 */       double[] columnMeans = new double[numCols];
/*     */ 
/*     */       
/* 351 */       for (int j = minIndex, iBin = 0; j < maxIndex; j++, iBin++) {
/* 352 */         for (int k = 0; k < numCols; k++) {
/* 353 */           double val = sortedData[j].getRow()[k];
/* 354 */           columnMeans[k] = columnMeans[k] + val;
/* 355 */           binData[iBin][k] = val;
/*     */         } 
/*     */       } 
/*     */       
/* 359 */       MathArrays.scaleInPlace(1.0D / numBinRows, columnMeans);
/*     */ 
/*     */       
/* 362 */       double[][] covMat = (new Covariance(binData)).getCovarianceMatrix().getData();
/*     */       
/* 364 */       MultivariateNormalDistribution mvn = new MultivariateNormalDistribution(columnMeans, covMat);
/*     */ 
/*     */       
/* 367 */       components.add(new Pair(Double.valueOf(weight), mvn));
/*     */     } 
/*     */     
/* 370 */     return new MixtureMultivariateNormalDistribution(components);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getLogLikelihood() {
/* 379 */     return this.logLikelihood;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MixtureMultivariateNormalDistribution getFittedModel() {
/* 388 */     return new MixtureMultivariateNormalDistribution(this.fittedModel.getComponents());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class DataRow
/*     */     implements Comparable<DataRow>
/*     */   {
/*     */     private final double[] row;
/*     */ 
/*     */ 
/*     */     
/*     */     private Double mean;
/*     */ 
/*     */ 
/*     */     
/*     */     DataRow(double[] data) {
/* 406 */       this.row = data;
/*     */       
/* 408 */       this.mean = Double.valueOf(0.0D);
/* 409 */       for (int i = 0; i < data.length; i++) {
/* 410 */         this.mean = Double.valueOf(this.mean.doubleValue() + data[i]);
/*     */       }
/* 412 */       this.mean = Double.valueOf(this.mean.doubleValue() / data.length);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int compareTo(DataRow other) {
/* 421 */       return this.mean.compareTo(other.mean);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean equals(Object other) {
/* 428 */       if (this == other) {
/* 429 */         return true;
/*     */       }
/*     */       
/* 432 */       if (other instanceof DataRow) {
/* 433 */         return MathArrays.equals(this.row, ((DataRow)other).row);
/*     */       }
/*     */       
/* 436 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 443 */       return Arrays.hashCode(this.row);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public double[] getRow() {
/* 450 */       return this.row;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/distribution/fitting/MultivariateNormalMixtureExpectationMaximization.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */