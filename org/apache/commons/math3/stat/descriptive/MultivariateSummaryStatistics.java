/*     */ package org.apache.commons.math3.stat.descriptive;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Arrays;
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.MathIllegalStateException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.linear.RealMatrix;
/*     */ import org.apache.commons.math3.stat.descriptive.moment.GeometricMean;
/*     */ import org.apache.commons.math3.stat.descriptive.moment.Mean;
/*     */ import org.apache.commons.math3.stat.descriptive.moment.VectorialCovariance;
/*     */ import org.apache.commons.math3.stat.descriptive.rank.Max;
/*     */ import org.apache.commons.math3.stat.descriptive.rank.Min;
/*     */ import org.apache.commons.math3.stat.descriptive.summary.Sum;
/*     */ import org.apache.commons.math3.stat.descriptive.summary.SumOfLogs;
/*     */ import org.apache.commons.math3.stat.descriptive.summary.SumOfSquares;
/*     */ import org.apache.commons.math3.util.FastMath;
/*     */ import org.apache.commons.math3.util.MathArrays;
/*     */ import org.apache.commons.math3.util.MathUtils;
/*     */ import org.apache.commons.math3.util.Precision;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MultivariateSummaryStatistics
/*     */   implements StatisticalMultivariateSummary, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 2271900808994826718L;
/*     */   private int k;
/*  81 */   private long n = 0L;
/*     */ 
/*     */ 
/*     */   
/*     */   private StorelessUnivariateStatistic[] sumImpl;
/*     */ 
/*     */ 
/*     */   
/*     */   private StorelessUnivariateStatistic[] sumSqImpl;
/*     */ 
/*     */ 
/*     */   
/*     */   private StorelessUnivariateStatistic[] minImpl;
/*     */ 
/*     */ 
/*     */   
/*     */   private StorelessUnivariateStatistic[] maxImpl;
/*     */ 
/*     */ 
/*     */   
/*     */   private StorelessUnivariateStatistic[] sumLogImpl;
/*     */ 
/*     */   
/*     */   private StorelessUnivariateStatistic[] geoMeanImpl;
/*     */ 
/*     */   
/*     */   private StorelessUnivariateStatistic[] meanImpl;
/*     */ 
/*     */   
/*     */   private VectorialCovariance covarianceImpl;
/*     */ 
/*     */ 
/*     */   
/*     */   public MultivariateSummaryStatistics(int k, boolean isCovarianceBiasCorrected) {
/* 115 */     this.k = k;
/*     */     
/* 117 */     this.sumImpl = new StorelessUnivariateStatistic[k];
/* 118 */     this.sumSqImpl = new StorelessUnivariateStatistic[k];
/* 119 */     this.minImpl = new StorelessUnivariateStatistic[k];
/* 120 */     this.maxImpl = new StorelessUnivariateStatistic[k];
/* 121 */     this.sumLogImpl = new StorelessUnivariateStatistic[k];
/* 122 */     this.geoMeanImpl = new StorelessUnivariateStatistic[k];
/* 123 */     this.meanImpl = new StorelessUnivariateStatistic[k];
/*     */     
/* 125 */     for (int i = 0; i < k; i++) {
/* 126 */       this.sumImpl[i] = (StorelessUnivariateStatistic)new Sum();
/* 127 */       this.sumSqImpl[i] = (StorelessUnivariateStatistic)new SumOfSquares();
/* 128 */       this.minImpl[i] = (StorelessUnivariateStatistic)new Min();
/* 129 */       this.maxImpl[i] = (StorelessUnivariateStatistic)new Max();
/* 130 */       this.sumLogImpl[i] = (StorelessUnivariateStatistic)new SumOfLogs();
/* 131 */       this.geoMeanImpl[i] = (StorelessUnivariateStatistic)new GeometricMean();
/* 132 */       this.meanImpl[i] = (StorelessUnivariateStatistic)new Mean();
/*     */     } 
/*     */     
/* 135 */     this.covarianceImpl = new VectorialCovariance(k, isCovarianceBiasCorrected);
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
/*     */   public void addValue(double[] value) throws DimensionMismatchException {
/* 148 */     checkDimension(value.length);
/* 149 */     for (int i = 0; i < this.k; i++) {
/* 150 */       double v = value[i];
/* 151 */       this.sumImpl[i].increment(v);
/* 152 */       this.sumSqImpl[i].increment(v);
/* 153 */       this.minImpl[i].increment(v);
/* 154 */       this.maxImpl[i].increment(v);
/* 155 */       this.sumLogImpl[i].increment(v);
/* 156 */       this.geoMeanImpl[i].increment(v);
/* 157 */       this.meanImpl[i].increment(v);
/*     */     } 
/* 159 */     this.covarianceImpl.increment(value);
/* 160 */     this.n++;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDimension() {
/* 168 */     return this.k;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getN() {
/* 176 */     return this.n;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private double[] getResults(StorelessUnivariateStatistic[] stats) {
/* 185 */     double[] results = new double[stats.length];
/* 186 */     for (int i = 0; i < results.length; i++) {
/* 187 */       results[i] = stats[i].getResult();
/*     */     }
/* 189 */     return results;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] getSum() {
/* 200 */     return getResults(this.sumImpl);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] getSumSq() {
/* 211 */     return getResults(this.sumSqImpl);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] getSumLog() {
/* 222 */     return getResults(this.sumLogImpl);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] getMean() {
/* 233 */     return getResults(this.meanImpl);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] getStandardDeviation() {
/* 244 */     double[] stdDev = new double[this.k];
/* 245 */     if (getN() < 1L) {
/* 246 */       Arrays.fill(stdDev, Double.NaN);
/* 247 */     } else if (getN() < 2L) {
/* 248 */       Arrays.fill(stdDev, 0.0D);
/*     */     } else {
/* 250 */       RealMatrix matrix = this.covarianceImpl.getResult();
/* 251 */       for (int i = 0; i < this.k; i++) {
/* 252 */         stdDev[i] = FastMath.sqrt(matrix.getEntry(i, i));
/*     */       }
/*     */     } 
/* 255 */     return stdDev;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RealMatrix getCovariance() {
/* 264 */     return this.covarianceImpl.getResult();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] getMax() {
/* 275 */     return getResults(this.maxImpl);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] getMin() {
/* 286 */     return getResults(this.minImpl);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] getGeometricMean() {
/* 297 */     return getResults(this.geoMeanImpl);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 308 */     String separator = ", ";
/* 309 */     String suffix = System.getProperty("line.separator");
/* 310 */     StringBuilder outBuffer = new StringBuilder();
/* 311 */     outBuffer.append("MultivariateSummaryStatistics:" + suffix);
/* 312 */     outBuffer.append("n: " + getN() + suffix);
/* 313 */     append(outBuffer, getMin(), "min: ", ", ", suffix);
/* 314 */     append(outBuffer, getMax(), "max: ", ", ", suffix);
/* 315 */     append(outBuffer, getMean(), "mean: ", ", ", suffix);
/* 316 */     append(outBuffer, getGeometricMean(), "geometric mean: ", ", ", suffix);
/* 317 */     append(outBuffer, getSumSq(), "sum of squares: ", ", ", suffix);
/* 318 */     append(outBuffer, getSumLog(), "sum of logarithms: ", ", ", suffix);
/* 319 */     append(outBuffer, getStandardDeviation(), "standard deviation: ", ", ", suffix);
/* 320 */     outBuffer.append("covariance: " + getCovariance().toString() + suffix);
/* 321 */     return outBuffer.toString();
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
/*     */   private void append(StringBuilder buffer, double[] data, String prefix, String separator, String suffix) {
/* 334 */     buffer.append(prefix);
/* 335 */     for (int i = 0; i < data.length; i++) {
/* 336 */       if (i > 0) {
/* 337 */         buffer.append(separator);
/*     */       }
/* 339 */       buffer.append(data[i]);
/*     */     } 
/* 341 */     buffer.append(suffix);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 348 */     this.n = 0L;
/* 349 */     for (int i = 0; i < this.k; i++) {
/* 350 */       this.minImpl[i].clear();
/* 351 */       this.maxImpl[i].clear();
/* 352 */       this.sumImpl[i].clear();
/* 353 */       this.sumLogImpl[i].clear();
/* 354 */       this.sumSqImpl[i].clear();
/* 355 */       this.geoMeanImpl[i].clear();
/* 356 */       this.meanImpl[i].clear();
/*     */     } 
/* 358 */     this.covarianceImpl.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object object) {
/* 369 */     if (object == this) {
/* 370 */       return true;
/*     */     }
/* 372 */     if (!(object instanceof MultivariateSummaryStatistics)) {
/* 373 */       return false;
/*     */     }
/* 375 */     MultivariateSummaryStatistics stat = (MultivariateSummaryStatistics)object;
/* 376 */     return (MathArrays.equalsIncludingNaN(stat.getGeometricMean(), getGeometricMean()) && MathArrays.equalsIncludingNaN(stat.getMax(), getMax()) && MathArrays.equalsIncludingNaN(stat.getMean(), getMean()) && MathArrays.equalsIncludingNaN(stat.getMin(), getMin()) && Precision.equalsIncludingNaN((float)stat.getN(), (float)getN()) && MathArrays.equalsIncludingNaN(stat.getSum(), getSum()) && MathArrays.equalsIncludingNaN(stat.getSumSq(), getSumSq()) && MathArrays.equalsIncludingNaN(stat.getSumLog(), getSumLog()) && stat.getCovariance().equals(getCovariance()));
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
/*     */   public int hashCode() {
/* 394 */     int result = 31 + MathUtils.hash(getGeometricMean());
/* 395 */     result = result * 31 + MathUtils.hash(getGeometricMean());
/* 396 */     result = result * 31 + MathUtils.hash(getMax());
/* 397 */     result = result * 31 + MathUtils.hash(getMean());
/* 398 */     result = result * 31 + MathUtils.hash(getMin());
/* 399 */     result = result * 31 + MathUtils.hash(getN());
/* 400 */     result = result * 31 + MathUtils.hash(getSum());
/* 401 */     result = result * 31 + MathUtils.hash(getSumSq());
/* 402 */     result = result * 31 + MathUtils.hash(getSumLog());
/* 403 */     result = result * 31 + getCovariance().hashCode();
/* 404 */     return result;
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
/*     */   private void setImpl(StorelessUnivariateStatistic[] newImpl, StorelessUnivariateStatistic[] oldImpl) throws MathIllegalStateException, DimensionMismatchException {
/* 420 */     checkEmpty();
/* 421 */     checkDimension(newImpl.length);
/* 422 */     System.arraycopy(newImpl, 0, oldImpl, 0, newImpl.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StorelessUnivariateStatistic[] getSumImpl() {
/* 431 */     return (StorelessUnivariateStatistic[])this.sumImpl.clone();
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
/*     */   public void setSumImpl(StorelessUnivariateStatistic[] sumImpl) throws MathIllegalStateException, DimensionMismatchException {
/* 449 */     setImpl(sumImpl, this.sumImpl);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StorelessUnivariateStatistic[] getSumsqImpl() {
/* 458 */     return (StorelessUnivariateStatistic[])this.sumSqImpl.clone();
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
/*     */   public void setSumsqImpl(StorelessUnivariateStatistic[] sumsqImpl) throws MathIllegalStateException, DimensionMismatchException {
/* 476 */     setImpl(sumsqImpl, this.sumSqImpl);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StorelessUnivariateStatistic[] getMinImpl() {
/* 485 */     return (StorelessUnivariateStatistic[])this.minImpl.clone();
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
/*     */   public void setMinImpl(StorelessUnivariateStatistic[] minImpl) throws MathIllegalStateException, DimensionMismatchException {
/* 503 */     setImpl(minImpl, this.minImpl);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StorelessUnivariateStatistic[] getMaxImpl() {
/* 512 */     return (StorelessUnivariateStatistic[])this.maxImpl.clone();
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
/*     */   public void setMaxImpl(StorelessUnivariateStatistic[] maxImpl) throws MathIllegalStateException, DimensionMismatchException {
/* 530 */     setImpl(maxImpl, this.maxImpl);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StorelessUnivariateStatistic[] getSumLogImpl() {
/* 539 */     return (StorelessUnivariateStatistic[])this.sumLogImpl.clone();
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
/*     */   public void setSumLogImpl(StorelessUnivariateStatistic[] sumLogImpl) throws MathIllegalStateException, DimensionMismatchException {
/* 557 */     setImpl(sumLogImpl, this.sumLogImpl);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StorelessUnivariateStatistic[] getGeoMeanImpl() {
/* 566 */     return (StorelessUnivariateStatistic[])this.geoMeanImpl.clone();
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
/*     */   public void setGeoMeanImpl(StorelessUnivariateStatistic[] geoMeanImpl) throws MathIllegalStateException, DimensionMismatchException {
/* 584 */     setImpl(geoMeanImpl, this.geoMeanImpl);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StorelessUnivariateStatistic[] getMeanImpl() {
/* 593 */     return (StorelessUnivariateStatistic[])this.meanImpl.clone();
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
/*     */   public void setMeanImpl(StorelessUnivariateStatistic[] meanImpl) throws MathIllegalStateException, DimensionMismatchException {
/* 611 */     setImpl(meanImpl, this.meanImpl);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkEmpty() throws MathIllegalStateException {
/* 619 */     if (this.n > 0L) {
/* 620 */       throw new MathIllegalStateException(LocalizedFormats.VALUES_ADDED_BEFORE_CONFIGURING_STATISTIC, new Object[] { Long.valueOf(this.n) });
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkDimension(int dimension) throws DimensionMismatchException {
/* 631 */     if (dimension != this.k)
/* 632 */       throw new DimensionMismatchException(dimension, this.k); 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/stat/descriptive/MultivariateSummaryStatistics.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */