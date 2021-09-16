/*     */ package org.apache.commons.math3.stat.descriptive;
/*     */ 
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.MathIllegalStateException;
/*     */ import org.apache.commons.math3.linear.RealMatrix;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SynchronizedMultivariateSummaryStatistics
/*     */   extends MultivariateSummaryStatistics
/*     */ {
/*     */   private static final long serialVersionUID = 7099834153347155363L;
/*     */   
/*     */   public SynchronizedMultivariateSummaryStatistics(int k, boolean isCovarianceBiasCorrected) {
/*  48 */     super(k, isCovarianceBiasCorrected);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void addValue(double[] value) throws DimensionMismatchException {
/*  56 */     super.addValue(value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized int getDimension() {
/*  64 */     return super.getDimension();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized long getN() {
/*  72 */     return super.getN();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized double[] getSum() {
/*  80 */     return super.getSum();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized double[] getSumSq() {
/*  88 */     return super.getSumSq();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized double[] getSumLog() {
/*  96 */     return super.getSumLog();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized double[] getMean() {
/* 104 */     return super.getMean();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized double[] getStandardDeviation() {
/* 112 */     return super.getStandardDeviation();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized RealMatrix getCovariance() {
/* 120 */     return super.getCovariance();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized double[] getMax() {
/* 128 */     return super.getMax();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized double[] getMin() {
/* 136 */     return super.getMin();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized double[] getGeometricMean() {
/* 144 */     return super.getGeometricMean();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized String toString() {
/* 152 */     return super.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void clear() {
/* 160 */     super.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized boolean equals(Object object) {
/* 168 */     return super.equals(object);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized int hashCode() {
/* 176 */     return super.hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized StorelessUnivariateStatistic[] getSumImpl() {
/* 184 */     return super.getSumImpl();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void setSumImpl(StorelessUnivariateStatistic[] sumImpl) throws DimensionMismatchException, MathIllegalStateException {
/* 193 */     super.setSumImpl(sumImpl);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized StorelessUnivariateStatistic[] getSumsqImpl() {
/* 201 */     return super.getSumsqImpl();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void setSumsqImpl(StorelessUnivariateStatistic[] sumsqImpl) throws DimensionMismatchException, MathIllegalStateException {
/* 210 */     super.setSumsqImpl(sumsqImpl);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized StorelessUnivariateStatistic[] getMinImpl() {
/* 218 */     return super.getMinImpl();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void setMinImpl(StorelessUnivariateStatistic[] minImpl) throws DimensionMismatchException, MathIllegalStateException {
/* 227 */     super.setMinImpl(minImpl);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized StorelessUnivariateStatistic[] getMaxImpl() {
/* 235 */     return super.getMaxImpl();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void setMaxImpl(StorelessUnivariateStatistic[] maxImpl) throws DimensionMismatchException, MathIllegalStateException {
/* 244 */     super.setMaxImpl(maxImpl);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized StorelessUnivariateStatistic[] getSumLogImpl() {
/* 252 */     return super.getSumLogImpl();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void setSumLogImpl(StorelessUnivariateStatistic[] sumLogImpl) throws DimensionMismatchException, MathIllegalStateException {
/* 261 */     super.setSumLogImpl(sumLogImpl);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized StorelessUnivariateStatistic[] getGeoMeanImpl() {
/* 269 */     return super.getGeoMeanImpl();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void setGeoMeanImpl(StorelessUnivariateStatistic[] geoMeanImpl) throws DimensionMismatchException, MathIllegalStateException {
/* 278 */     super.setGeoMeanImpl(geoMeanImpl);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized StorelessUnivariateStatistic[] getMeanImpl() {
/* 286 */     return super.getMeanImpl();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void setMeanImpl(StorelessUnivariateStatistic[] meanImpl) throws DimensionMismatchException, MathIllegalStateException {
/* 295 */     super.setMeanImpl(meanImpl);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/stat/descriptive/SynchronizedMultivariateSummaryStatistics.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */