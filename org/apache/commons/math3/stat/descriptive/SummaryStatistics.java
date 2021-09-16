/*     */ package org.apache.commons.math3.stat.descriptive;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import org.apache.commons.math3.exception.MathIllegalStateException;
/*     */ import org.apache.commons.math3.exception.NullArgumentException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.stat.descriptive.moment.FirstMoment;
/*     */ import org.apache.commons.math3.stat.descriptive.moment.GeometricMean;
/*     */ import org.apache.commons.math3.stat.descriptive.moment.Mean;
/*     */ import org.apache.commons.math3.stat.descriptive.moment.SecondMoment;
/*     */ import org.apache.commons.math3.stat.descriptive.moment.Variance;
/*     */ import org.apache.commons.math3.stat.descriptive.rank.Max;
/*     */ import org.apache.commons.math3.stat.descriptive.rank.Min;
/*     */ import org.apache.commons.math3.stat.descriptive.summary.Sum;
/*     */ import org.apache.commons.math3.stat.descriptive.summary.SumOfLogs;
/*     */ import org.apache.commons.math3.stat.descriptive.summary.SumOfSquares;
/*     */ import org.apache.commons.math3.util.FastMath;
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
/*     */ public class SummaryStatistics
/*     */   implements StatisticalSummary, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -2021321786743555871L;
/*  66 */   private long n = 0L;
/*     */ 
/*     */   
/*  69 */   private SecondMoment secondMoment = new SecondMoment();
/*     */ 
/*     */   
/*  72 */   private Sum sum = new Sum();
/*     */ 
/*     */   
/*  75 */   private SumOfSquares sumsq = new SumOfSquares();
/*     */ 
/*     */   
/*  78 */   private Min min = new Min();
/*     */ 
/*     */   
/*  81 */   private Max max = new Max();
/*     */ 
/*     */   
/*  84 */   private SumOfLogs sumLog = new SumOfLogs();
/*     */ 
/*     */   
/*  87 */   private GeometricMean geoMean = new GeometricMean(this.sumLog);
/*     */ 
/*     */   
/*  90 */   private Mean mean = new Mean((FirstMoment)this.secondMoment);
/*     */ 
/*     */   
/*  93 */   private Variance variance = new Variance(this.secondMoment);
/*     */ 
/*     */   
/*  96 */   private StorelessUnivariateStatistic sumImpl = (StorelessUnivariateStatistic)this.sum;
/*     */ 
/*     */   
/*  99 */   private StorelessUnivariateStatistic sumsqImpl = (StorelessUnivariateStatistic)this.sumsq;
/*     */ 
/*     */   
/* 102 */   private StorelessUnivariateStatistic minImpl = (StorelessUnivariateStatistic)this.min;
/*     */ 
/*     */   
/* 105 */   private StorelessUnivariateStatistic maxImpl = (StorelessUnivariateStatistic)this.max;
/*     */ 
/*     */   
/* 108 */   private StorelessUnivariateStatistic sumLogImpl = (StorelessUnivariateStatistic)this.sumLog;
/*     */ 
/*     */   
/* 111 */   private StorelessUnivariateStatistic geoMeanImpl = (StorelessUnivariateStatistic)this.geoMean;
/*     */ 
/*     */   
/* 114 */   private StorelessUnivariateStatistic meanImpl = (StorelessUnivariateStatistic)this.mean;
/*     */ 
/*     */   
/* 117 */   private StorelessUnivariateStatistic varianceImpl = (StorelessUnivariateStatistic)this.variance;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SummaryStatistics() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SummaryStatistics(SummaryStatistics original) throws NullArgumentException {
/* 132 */     copy(original, this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StatisticalSummary getSummary() {
/* 141 */     return new StatisticalSummaryValues(getMean(), getVariance(), getN(), getMax(), getMin(), getSum());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addValue(double value) {
/* 150 */     this.sumImpl.increment(value);
/* 151 */     this.sumsqImpl.increment(value);
/* 152 */     this.minImpl.increment(value);
/* 153 */     this.maxImpl.increment(value);
/* 154 */     this.sumLogImpl.increment(value);
/* 155 */     this.secondMoment.increment(value);
/*     */ 
/*     */     
/* 158 */     if (this.meanImpl != this.mean) {
/* 159 */       this.meanImpl.increment(value);
/*     */     }
/* 161 */     if (this.varianceImpl != this.variance) {
/* 162 */       this.varianceImpl.increment(value);
/*     */     }
/* 164 */     if (this.geoMeanImpl != this.geoMean) {
/* 165 */       this.geoMeanImpl.increment(value);
/*     */     }
/* 167 */     this.n++;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getN() {
/* 175 */     return this.n;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getSum() {
/* 183 */     return this.sumImpl.getResult();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getSumsq() {
/* 194 */     return this.sumsqImpl.getResult();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getMean() {
/* 205 */     return this.meanImpl.getResult();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getStandardDeviation() {
/* 216 */     double stdDev = Double.NaN;
/* 217 */     if (getN() > 0L) {
/* 218 */       if (getN() > 1L) {
/* 219 */         stdDev = FastMath.sqrt(getVariance());
/*     */       } else {
/* 221 */         stdDev = 0.0D;
/*     */       } 
/*     */     }
/* 224 */     return stdDev;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getQuadraticMean() {
/* 235 */     long size = getN();
/* 236 */     return (size > 0L) ? FastMath.sqrt(getSumsq() / size) : Double.NaN;
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
/*     */   public double getVariance() {
/* 251 */     return this.varianceImpl.getResult();
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
/*     */   public double getPopulationVariance() {
/* 263 */     Variance populationVariance = new Variance(this.secondMoment);
/* 264 */     populationVariance.setBiasCorrected(false);
/* 265 */     return populationVariance.getResult();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getMax() {
/* 276 */     return this.maxImpl.getResult();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getMin() {
/* 287 */     return this.minImpl.getResult();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getGeometricMean() {
/* 298 */     return this.geoMeanImpl.getResult();
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
/*     */   public double getSumOfLogs() {
/* 310 */     return this.sumLogImpl.getResult();
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
/*     */   public double getSecondMoment() {
/* 325 */     return this.secondMoment.getResult();
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
/* 336 */     StringBuilder outBuffer = new StringBuilder();
/* 337 */     String endl = "\n";
/* 338 */     outBuffer.append("SummaryStatistics:").append(endl);
/* 339 */     outBuffer.append("n: ").append(getN()).append(endl);
/* 340 */     outBuffer.append("min: ").append(getMin()).append(endl);
/* 341 */     outBuffer.append("max: ").append(getMax()).append(endl);
/* 342 */     outBuffer.append("sum: ").append(getSum()).append(endl);
/* 343 */     outBuffer.append("mean: ").append(getMean()).append(endl);
/* 344 */     outBuffer.append("geometric mean: ").append(getGeometricMean()).append(endl);
/*     */     
/* 346 */     outBuffer.append("variance: ").append(getVariance()).append(endl);
/* 347 */     outBuffer.append("population variance: ").append(getPopulationVariance()).append(endl);
/* 348 */     outBuffer.append("second moment: ").append(getSecondMoment()).append(endl);
/* 349 */     outBuffer.append("sum of squares: ").append(getSumsq()).append(endl);
/* 350 */     outBuffer.append("standard deviation: ").append(getStandardDeviation()).append(endl);
/*     */     
/* 352 */     outBuffer.append("sum of logs: ").append(getSumOfLogs()).append(endl);
/* 353 */     return outBuffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 360 */     this.n = 0L;
/* 361 */     this.minImpl.clear();
/* 362 */     this.maxImpl.clear();
/* 363 */     this.sumImpl.clear();
/* 364 */     this.sumLogImpl.clear();
/* 365 */     this.sumsqImpl.clear();
/* 366 */     this.geoMeanImpl.clear();
/* 367 */     this.secondMoment.clear();
/* 368 */     if (this.meanImpl != this.mean) {
/* 369 */       this.meanImpl.clear();
/*     */     }
/* 371 */     if (this.varianceImpl != this.variance) {
/* 372 */       this.varianceImpl.clear();
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
/*     */   public boolean equals(Object object) {
/* 385 */     if (object == this) {
/* 386 */       return true;
/*     */     }
/* 388 */     if (!(object instanceof SummaryStatistics)) {
/* 389 */       return false;
/*     */     }
/* 391 */     SummaryStatistics stat = (SummaryStatistics)object;
/* 392 */     return (Precision.equalsIncludingNaN(stat.getGeometricMean(), getGeometricMean()) && Precision.equalsIncludingNaN(stat.getMax(), getMax()) && Precision.equalsIncludingNaN(stat.getMean(), getMean()) && Precision.equalsIncludingNaN(stat.getMin(), getMin()) && Precision.equalsIncludingNaN((float)stat.getN(), (float)getN()) && Precision.equalsIncludingNaN(stat.getSum(), getSum()) && Precision.equalsIncludingNaN(stat.getSumsq(), getSumsq()) && Precision.equalsIncludingNaN(stat.getVariance(), getVariance()));
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
/*     */   public int hashCode() {
/* 408 */     int result = 31 + MathUtils.hash(getGeometricMean());
/* 409 */     result = result * 31 + MathUtils.hash(getGeometricMean());
/* 410 */     result = result * 31 + MathUtils.hash(getMax());
/* 411 */     result = result * 31 + MathUtils.hash(getMean());
/* 412 */     result = result * 31 + MathUtils.hash(getMin());
/* 413 */     result = result * 31 + MathUtils.hash(getN());
/* 414 */     result = result * 31 + MathUtils.hash(getSum());
/* 415 */     result = result * 31 + MathUtils.hash(getSumsq());
/* 416 */     result = result * 31 + MathUtils.hash(getVariance());
/* 417 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StorelessUnivariateStatistic getSumImpl() {
/* 427 */     return this.sumImpl;
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
/*     */   public void setSumImpl(StorelessUnivariateStatistic sumImpl) throws MathIllegalStateException {
/* 447 */     checkEmpty();
/* 448 */     this.sumImpl = sumImpl;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StorelessUnivariateStatistic getSumsqImpl() {
/* 457 */     return this.sumsqImpl;
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
/*     */   public void setSumsqImpl(StorelessUnivariateStatistic sumsqImpl) throws MathIllegalStateException {
/* 477 */     checkEmpty();
/* 478 */     this.sumsqImpl = sumsqImpl;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StorelessUnivariateStatistic getMinImpl() {
/* 487 */     return this.minImpl;
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
/*     */   public void setMinImpl(StorelessUnivariateStatistic minImpl) throws MathIllegalStateException {
/* 507 */     checkEmpty();
/* 508 */     this.minImpl = minImpl;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StorelessUnivariateStatistic getMaxImpl() {
/* 517 */     return this.maxImpl;
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
/*     */   public void setMaxImpl(StorelessUnivariateStatistic maxImpl) throws MathIllegalStateException {
/* 537 */     checkEmpty();
/* 538 */     this.maxImpl = maxImpl;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StorelessUnivariateStatistic getSumLogImpl() {
/* 547 */     return this.sumLogImpl;
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
/*     */   public void setSumLogImpl(StorelessUnivariateStatistic sumLogImpl) throws MathIllegalStateException {
/* 567 */     checkEmpty();
/* 568 */     this.sumLogImpl = sumLogImpl;
/* 569 */     this.geoMean.setSumLogImpl(sumLogImpl);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StorelessUnivariateStatistic getGeoMeanImpl() {
/* 578 */     return this.geoMeanImpl;
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
/*     */   public void setGeoMeanImpl(StorelessUnivariateStatistic geoMeanImpl) throws MathIllegalStateException {
/* 598 */     checkEmpty();
/* 599 */     this.geoMeanImpl = geoMeanImpl;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StorelessUnivariateStatistic getMeanImpl() {
/* 608 */     return this.meanImpl;
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
/*     */   public void setMeanImpl(StorelessUnivariateStatistic meanImpl) throws MathIllegalStateException {
/* 628 */     checkEmpty();
/* 629 */     this.meanImpl = meanImpl;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StorelessUnivariateStatistic getVarianceImpl() {
/* 638 */     return this.varianceImpl;
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
/*     */   public void setVarianceImpl(StorelessUnivariateStatistic varianceImpl) throws MathIllegalStateException {
/* 658 */     checkEmpty();
/* 659 */     this.varianceImpl = varianceImpl;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkEmpty() throws MathIllegalStateException {
/* 667 */     if (this.n > 0L) {
/* 668 */       throw new MathIllegalStateException(LocalizedFormats.VALUES_ADDED_BEFORE_CONFIGURING_STATISTIC, new Object[] { Long.valueOf(this.n) });
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SummaryStatistics copy() {
/* 679 */     SummaryStatistics result = new SummaryStatistics();
/*     */     
/* 681 */     copy(this, result);
/* 682 */     return result;
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
/*     */   public static void copy(SummaryStatistics source, SummaryStatistics dest) throws NullArgumentException {
/* 695 */     MathUtils.checkNotNull(source);
/* 696 */     MathUtils.checkNotNull(dest);
/* 697 */     dest.maxImpl = source.maxImpl.copy();
/* 698 */     dest.minImpl = source.minImpl.copy();
/* 699 */     dest.sumImpl = source.sumImpl.copy();
/* 700 */     dest.sumLogImpl = source.sumLogImpl.copy();
/* 701 */     dest.sumsqImpl = source.sumsqImpl.copy();
/* 702 */     dest.secondMoment = source.secondMoment.copy();
/* 703 */     dest.n = source.n;
/*     */ 
/*     */     
/* 706 */     if (source.getVarianceImpl() instanceof Variance) {
/* 707 */       dest.varianceImpl = (StorelessUnivariateStatistic)new Variance(dest.secondMoment);
/*     */     } else {
/* 709 */       dest.varianceImpl = source.varianceImpl.copy();
/*     */     } 
/* 711 */     if (source.meanImpl instanceof Mean) {
/* 712 */       dest.meanImpl = (StorelessUnivariateStatistic)new Mean((FirstMoment)dest.secondMoment);
/*     */     } else {
/* 714 */       dest.meanImpl = source.meanImpl.copy();
/*     */     } 
/* 716 */     if (source.getGeoMeanImpl() instanceof GeometricMean) {
/* 717 */       dest.geoMeanImpl = (StorelessUnivariateStatistic)new GeometricMean((SumOfLogs)dest.sumLogImpl);
/*     */     } else {
/* 719 */       dest.geoMeanImpl = source.geoMeanImpl.copy();
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 724 */     if (source.geoMean == source.geoMeanImpl) {
/* 725 */       dest.geoMean = (GeometricMean)dest.geoMeanImpl;
/*     */     } else {
/* 727 */       GeometricMean.copy(source.geoMean, dest.geoMean);
/*     */     } 
/* 729 */     if (source.max == source.maxImpl) {
/* 730 */       dest.max = (Max)dest.maxImpl;
/*     */     } else {
/* 732 */       Max.copy(source.max, dest.max);
/*     */     } 
/* 734 */     if (source.mean == source.meanImpl) {
/* 735 */       dest.mean = (Mean)dest.meanImpl;
/*     */     } else {
/* 737 */       Mean.copy(source.mean, dest.mean);
/*     */     } 
/* 739 */     if (source.min == source.minImpl) {
/* 740 */       dest.min = (Min)dest.minImpl;
/*     */     } else {
/* 742 */       Min.copy(source.min, dest.min);
/*     */     } 
/* 744 */     if (source.sum == source.sumImpl) {
/* 745 */       dest.sum = (Sum)dest.sumImpl;
/*     */     } else {
/* 747 */       Sum.copy(source.sum, dest.sum);
/*     */     } 
/* 749 */     if (source.variance == source.varianceImpl) {
/* 750 */       dest.variance = (Variance)dest.varianceImpl;
/*     */     } else {
/* 752 */       Variance.copy(source.variance, dest.variance);
/*     */     } 
/* 754 */     if (source.sumLog == source.sumLogImpl) {
/* 755 */       dest.sumLog = (SumOfLogs)dest.sumLogImpl;
/*     */     } else {
/* 757 */       SumOfLogs.copy(source.sumLog, dest.sumLog);
/*     */     } 
/* 759 */     if (source.sumsq == source.sumsqImpl) {
/* 760 */       dest.sumsq = (SumOfSquares)dest.sumsqImpl;
/*     */     } else {
/* 762 */       SumOfSquares.copy(source.sumsq, dest.sumsq);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/stat/descriptive/SummaryStatistics.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */