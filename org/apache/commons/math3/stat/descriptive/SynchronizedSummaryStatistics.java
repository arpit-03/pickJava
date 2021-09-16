/*     */ package org.apache.commons.math3.stat.descriptive;
/*     */ 
/*     */ import org.apache.commons.math3.exception.MathIllegalStateException;
/*     */ import org.apache.commons.math3.exception.NullArgumentException;
/*     */ import org.apache.commons.math3.util.MathUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SynchronizedSummaryStatistics
/*     */   extends SummaryStatistics
/*     */ {
/*     */   private static final long serialVersionUID = 1909861009042253704L;
/*     */   
/*     */   public SynchronizedSummaryStatistics() {}
/*     */   
/*     */   public SynchronizedSummaryStatistics(SynchronizedSummaryStatistics original) throws NullArgumentException {
/*  55 */     copy(original, this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized StatisticalSummary getSummary() {
/*  63 */     return super.getSummary();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void addValue(double value) {
/*  71 */     super.addValue(value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized long getN() {
/*  79 */     return super.getN();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized double getSum() {
/*  87 */     return super.getSum();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized double getSumsq() {
/*  95 */     return super.getSumsq();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized double getMean() {
/* 103 */     return super.getMean();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized double getStandardDeviation() {
/* 111 */     return super.getStandardDeviation();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized double getQuadraticMean() {
/* 119 */     return super.getQuadraticMean();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized double getVariance() {
/* 127 */     return super.getVariance();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized double getPopulationVariance() {
/* 135 */     return super.getPopulationVariance();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized double getMax() {
/* 143 */     return super.getMax();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized double getMin() {
/* 151 */     return super.getMin();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized double getGeometricMean() {
/* 159 */     return super.getGeometricMean();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized String toString() {
/* 167 */     return super.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void clear() {
/* 175 */     super.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized boolean equals(Object object) {
/* 183 */     return super.equals(object);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized int hashCode() {
/* 191 */     return super.hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized StorelessUnivariateStatistic getSumImpl() {
/* 199 */     return super.getSumImpl();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void setSumImpl(StorelessUnivariateStatistic sumImpl) throws MathIllegalStateException {
/* 208 */     super.setSumImpl(sumImpl);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized StorelessUnivariateStatistic getSumsqImpl() {
/* 216 */     return super.getSumsqImpl();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void setSumsqImpl(StorelessUnivariateStatistic sumsqImpl) throws MathIllegalStateException {
/* 225 */     super.setSumsqImpl(sumsqImpl);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized StorelessUnivariateStatistic getMinImpl() {
/* 233 */     return super.getMinImpl();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void setMinImpl(StorelessUnivariateStatistic minImpl) throws MathIllegalStateException {
/* 242 */     super.setMinImpl(minImpl);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized StorelessUnivariateStatistic getMaxImpl() {
/* 250 */     return super.getMaxImpl();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void setMaxImpl(StorelessUnivariateStatistic maxImpl) throws MathIllegalStateException {
/* 259 */     super.setMaxImpl(maxImpl);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized StorelessUnivariateStatistic getSumLogImpl() {
/* 267 */     return super.getSumLogImpl();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void setSumLogImpl(StorelessUnivariateStatistic sumLogImpl) throws MathIllegalStateException {
/* 276 */     super.setSumLogImpl(sumLogImpl);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized StorelessUnivariateStatistic getGeoMeanImpl() {
/* 284 */     return super.getGeoMeanImpl();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void setGeoMeanImpl(StorelessUnivariateStatistic geoMeanImpl) throws MathIllegalStateException {
/* 293 */     super.setGeoMeanImpl(geoMeanImpl);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized StorelessUnivariateStatistic getMeanImpl() {
/* 301 */     return super.getMeanImpl();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void setMeanImpl(StorelessUnivariateStatistic meanImpl) throws MathIllegalStateException {
/* 310 */     super.setMeanImpl(meanImpl);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized StorelessUnivariateStatistic getVarianceImpl() {
/* 318 */     return super.getVarianceImpl();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void setVarianceImpl(StorelessUnivariateStatistic varianceImpl) throws MathIllegalStateException {
/* 327 */     super.setVarianceImpl(varianceImpl);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized SynchronizedSummaryStatistics copy() {
/* 338 */     SynchronizedSummaryStatistics result = new SynchronizedSummaryStatistics();
/*     */ 
/*     */     
/* 341 */     copy(this, result);
/* 342 */     return result;
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
/*     */   public static void copy(SynchronizedSummaryStatistics source, SynchronizedSummaryStatistics dest) throws NullArgumentException {
/* 357 */     MathUtils.checkNotNull(source);
/* 358 */     MathUtils.checkNotNull(dest);
/* 359 */     synchronized (source) {
/* 360 */       synchronized (dest) {
/* 361 */         SummaryStatistics.copy(source, dest);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/stat/descriptive/SynchronizedSummaryStatistics.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */