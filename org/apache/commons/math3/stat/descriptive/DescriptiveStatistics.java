/*     */ package org.apache.commons.math3.stat.descriptive;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.util.Arrays;
/*     */ import org.apache.commons.math3.exception.MathIllegalArgumentException;
/*     */ import org.apache.commons.math3.exception.MathIllegalStateException;
/*     */ import org.apache.commons.math3.exception.NullArgumentException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.stat.descriptive.moment.GeometricMean;
/*     */ import org.apache.commons.math3.stat.descriptive.moment.Kurtosis;
/*     */ import org.apache.commons.math3.stat.descriptive.moment.Mean;
/*     */ import org.apache.commons.math3.stat.descriptive.moment.Skewness;
/*     */ import org.apache.commons.math3.stat.descriptive.moment.Variance;
/*     */ import org.apache.commons.math3.stat.descriptive.rank.Max;
/*     */ import org.apache.commons.math3.stat.descriptive.rank.Min;
/*     */ import org.apache.commons.math3.stat.descriptive.rank.Percentile;
/*     */ import org.apache.commons.math3.stat.descriptive.summary.Sum;
/*     */ import org.apache.commons.math3.stat.descriptive.summary.SumOfSquares;
/*     */ import org.apache.commons.math3.util.FastMath;
/*     */ import org.apache.commons.math3.util.MathUtils;
/*     */ import org.apache.commons.math3.util.ResizableDoubleArray;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DescriptiveStatistics
/*     */   implements StatisticalSummary, Serializable
/*     */ {
/*     */   public static final int INFINITE_WINDOW = -1;
/*     */   private static final long serialVersionUID = 4133067267405273064L;
/*     */   private static final String SET_QUANTILE_METHOD_NAME = "setQuantile";
/*  76 */   protected int windowSize = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  81 */   private ResizableDoubleArray eDA = new ResizableDoubleArray();
/*     */ 
/*     */   
/*  84 */   private UnivariateStatistic meanImpl = (UnivariateStatistic)new Mean();
/*     */ 
/*     */   
/*  87 */   private UnivariateStatistic geometricMeanImpl = (UnivariateStatistic)new GeometricMean();
/*     */ 
/*     */   
/*  90 */   private UnivariateStatistic kurtosisImpl = (UnivariateStatistic)new Kurtosis();
/*     */ 
/*     */   
/*  93 */   private UnivariateStatistic maxImpl = (UnivariateStatistic)new Max();
/*     */ 
/*     */   
/*  96 */   private UnivariateStatistic minImpl = (UnivariateStatistic)new Min();
/*     */ 
/*     */   
/*  99 */   private UnivariateStatistic percentileImpl = (UnivariateStatistic)new Percentile();
/*     */ 
/*     */   
/* 102 */   private UnivariateStatistic skewnessImpl = (UnivariateStatistic)new Skewness();
/*     */ 
/*     */   
/* 105 */   private UnivariateStatistic varianceImpl = (UnivariateStatistic)new Variance();
/*     */ 
/*     */   
/* 108 */   private UnivariateStatistic sumsqImpl = (UnivariateStatistic)new SumOfSquares();
/*     */ 
/*     */   
/* 111 */   private UnivariateStatistic sumImpl = (UnivariateStatistic)new Sum();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DescriptiveStatistics() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DescriptiveStatistics(int window) throws MathIllegalArgumentException {
/* 127 */     setWindowSize(window);
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
/*     */   public DescriptiveStatistics(double[] initialDoubleArray) {
/* 139 */     if (initialDoubleArray != null) {
/* 140 */       this.eDA = new ResizableDoubleArray(initialDoubleArray);
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
/*     */   public DescriptiveStatistics(DescriptiveStatistics original) throws NullArgumentException {
/* 152 */     copy(original, this);
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
/*     */   public void addValue(double v) {
/* 164 */     if (this.windowSize != -1) {
/* 165 */       if (getN() == this.windowSize) {
/* 166 */         this.eDA.addElementRolling(v);
/* 167 */       } else if (getN() < this.windowSize) {
/* 168 */         this.eDA.addElement(v);
/*     */       } 
/*     */     } else {
/* 171 */       this.eDA.addElement(v);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeMostRecentValue() throws MathIllegalStateException {
/*     */     try {
/* 182 */       this.eDA.discardMostRecentElements(1);
/* 183 */     } catch (MathIllegalArgumentException ex) {
/* 184 */       throw new MathIllegalStateException(LocalizedFormats.NO_DATA, new Object[0]);
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
/*     */   public double replaceMostRecentValue(double v) throws MathIllegalStateException {
/* 197 */     return this.eDA.substituteMostRecentElement(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getMean() {
/* 206 */     return apply(this.meanImpl);
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
/*     */   public double getGeometricMean() {
/* 219 */     return apply(this.geometricMeanImpl);
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
/*     */   public double getVariance() {
/* 233 */     return apply(this.varianceImpl);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getPopulationVariance() {
/* 244 */     return apply((UnivariateStatistic)new Variance(false));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getStandardDeviation() {
/* 253 */     double stdDev = Double.NaN;
/* 254 */     if (getN() > 0L) {
/* 255 */       if (getN() > 1L) {
/* 256 */         stdDev = FastMath.sqrt(getVariance());
/*     */       } else {
/* 258 */         stdDev = 0.0D;
/*     */       } 
/*     */     }
/* 261 */     return stdDev;
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
/* 272 */     long n = getN();
/* 273 */     return (n > 0L) ? FastMath.sqrt(getSumsq() / n) : Double.NaN;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getSkewness() {
/* 283 */     return apply(this.skewnessImpl);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getKurtosis() {
/* 293 */     return apply(this.kurtosisImpl);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getMax() {
/* 301 */     return apply(this.maxImpl);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getMin() {
/* 309 */     return apply(this.minImpl);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getN() {
/* 317 */     return this.eDA.getNumElements();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getSum() {
/* 325 */     return apply(this.sumImpl);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getSumsq() {
/* 334 */     return apply(this.sumsqImpl);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 341 */     this.eDA.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWindowSize() {
/* 352 */     return this.windowSize;
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
/*     */   public void setWindowSize(int windowSize) throws MathIllegalArgumentException {
/* 370 */     if (windowSize < 1 && windowSize != -1) {
/* 371 */       throw new MathIllegalArgumentException(LocalizedFormats.NOT_POSITIVE_WINDOW_SIZE, new Object[] { Integer.valueOf(windowSize) });
/*     */     }
/*     */ 
/*     */     
/* 375 */     this.windowSize = windowSize;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 380 */     if (windowSize != -1 && windowSize < this.eDA.getNumElements()) {
/* 381 */       this.eDA.discardFrontElements(this.eDA.getNumElements() - windowSize);
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
/*     */   public double[] getValues() {
/* 395 */     return this.eDA.getElements();
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
/*     */   public double[] getSortedValues() {
/* 407 */     double[] sort = getValues();
/* 408 */     Arrays.sort(sort);
/* 409 */     return sort;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getElement(int index) {
/* 418 */     return this.eDA.getElement(index);
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
/*     */   public double getPercentile(double p) throws MathIllegalStateException, MathIllegalArgumentException {
/* 441 */     if (this.percentileImpl instanceof Percentile) {
/* 442 */       ((Percentile)this.percentileImpl).setQuantile(p);
/*     */     } else {
/*     */       try {
/* 445 */         this.percentileImpl.getClass().getMethod("setQuantile", new Class[] { double.class }).invoke(this.percentileImpl, new Object[] { Double.valueOf(p) });
/*     */       
/*     */       }
/* 448 */       catch (NoSuchMethodException e1) {
/* 449 */         throw new MathIllegalStateException(LocalizedFormats.PERCENTILE_IMPLEMENTATION_UNSUPPORTED_METHOD, new Object[] { this.percentileImpl.getClass().getName(), "setQuantile" });
/*     */       
/*     */       }
/* 452 */       catch (IllegalAccessException e2) {
/* 453 */         throw new MathIllegalStateException(LocalizedFormats.PERCENTILE_IMPLEMENTATION_CANNOT_ACCESS_METHOD, new Object[] { "setQuantile", this.percentileImpl.getClass().getName() });
/*     */       
/*     */       }
/* 456 */       catch (InvocationTargetException e3) {
/* 457 */         throw new IllegalStateException(e3.getCause());
/*     */       } 
/*     */     } 
/* 460 */     return apply(this.percentileImpl);
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
/*     */   public String toString() {
/* 472 */     StringBuilder outBuffer = new StringBuilder();
/* 473 */     String endl = "\n";
/* 474 */     outBuffer.append("DescriptiveStatistics:").append(endl);
/* 475 */     outBuffer.append("n: ").append(getN()).append(endl);
/* 476 */     outBuffer.append("min: ").append(getMin()).append(endl);
/* 477 */     outBuffer.append("max: ").append(getMax()).append(endl);
/* 478 */     outBuffer.append("mean: ").append(getMean()).append(endl);
/* 479 */     outBuffer.append("std dev: ").append(getStandardDeviation()).append(endl);
/*     */ 
/*     */     
/*     */     try {
/* 483 */       outBuffer.append("median: ").append(getPercentile(50.0D)).append(endl);
/* 484 */     } catch (MathIllegalStateException ex) {
/* 485 */       outBuffer.append("median: unavailable").append(endl);
/*     */     } 
/* 487 */     outBuffer.append("skewness: ").append(getSkewness()).append(endl);
/* 488 */     outBuffer.append("kurtosis: ").append(getKurtosis()).append(endl);
/* 489 */     return outBuffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double apply(UnivariateStatistic stat) {
/* 499 */     return this.eDA.compute(stat);
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
/*     */   public synchronized UnivariateStatistic getMeanImpl() {
/* 511 */     return this.meanImpl;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void setMeanImpl(UnivariateStatistic meanImpl) {
/* 522 */     this.meanImpl = meanImpl;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized UnivariateStatistic getGeometricMeanImpl() {
/* 532 */     return this.geometricMeanImpl;
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
/*     */   public synchronized void setGeometricMeanImpl(UnivariateStatistic geometricMeanImpl) {
/* 544 */     this.geometricMeanImpl = geometricMeanImpl;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized UnivariateStatistic getKurtosisImpl() {
/* 554 */     return this.kurtosisImpl;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void setKurtosisImpl(UnivariateStatistic kurtosisImpl) {
/* 565 */     this.kurtosisImpl = kurtosisImpl;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized UnivariateStatistic getMaxImpl() {
/* 575 */     return this.maxImpl;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void setMaxImpl(UnivariateStatistic maxImpl) {
/* 586 */     this.maxImpl = maxImpl;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized UnivariateStatistic getMinImpl() {
/* 596 */     return this.minImpl;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void setMinImpl(UnivariateStatistic minImpl) {
/* 607 */     this.minImpl = minImpl;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized UnivariateStatistic getPercentileImpl() {
/* 617 */     return this.percentileImpl;
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
/*     */   public synchronized void setPercentileImpl(UnivariateStatistic percentileImpl) throws MathIllegalArgumentException {
/*     */     try {
/* 634 */       percentileImpl.getClass().getMethod("setQuantile", new Class[] { double.class }).invoke(percentileImpl, new Object[] { Double.valueOf(50.0D) });
/*     */     
/*     */     }
/* 637 */     catch (NoSuchMethodException e1) {
/* 638 */       throw new MathIllegalArgumentException(LocalizedFormats.PERCENTILE_IMPLEMENTATION_UNSUPPORTED_METHOD, new Object[] { percentileImpl.getClass().getName(), "setQuantile" });
/*     */     
/*     */     }
/* 641 */     catch (IllegalAccessException e2) {
/* 642 */       throw new MathIllegalArgumentException(LocalizedFormats.PERCENTILE_IMPLEMENTATION_CANNOT_ACCESS_METHOD, new Object[] { "setQuantile", percentileImpl.getClass().getName() });
/*     */     
/*     */     }
/* 645 */     catch (InvocationTargetException e3) {
/* 646 */       throw new IllegalArgumentException(e3.getCause());
/*     */     } 
/* 648 */     this.percentileImpl = percentileImpl;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized UnivariateStatistic getSkewnessImpl() {
/* 658 */     return this.skewnessImpl;
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
/*     */   public synchronized void setSkewnessImpl(UnivariateStatistic skewnessImpl) {
/* 670 */     this.skewnessImpl = skewnessImpl;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized UnivariateStatistic getVarianceImpl() {
/* 680 */     return this.varianceImpl;
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
/*     */   public synchronized void setVarianceImpl(UnivariateStatistic varianceImpl) {
/* 692 */     this.varianceImpl = varianceImpl;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized UnivariateStatistic getSumsqImpl() {
/* 702 */     return this.sumsqImpl;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void setSumsqImpl(UnivariateStatistic sumsqImpl) {
/* 713 */     this.sumsqImpl = sumsqImpl;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized UnivariateStatistic getSumImpl() {
/* 723 */     return this.sumImpl;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void setSumImpl(UnivariateStatistic sumImpl) {
/* 734 */     this.sumImpl = sumImpl;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DescriptiveStatistics copy() {
/* 743 */     DescriptiveStatistics result = new DescriptiveStatistics();
/*     */     
/* 745 */     copy(this, result);
/* 746 */     return result;
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
/*     */   public static void copy(DescriptiveStatistics source, DescriptiveStatistics dest) throws NullArgumentException {
/* 759 */     MathUtils.checkNotNull(source);
/* 760 */     MathUtils.checkNotNull(dest);
/*     */     
/* 762 */     dest.eDA = source.eDA.copy();
/* 763 */     dest.windowSize = source.windowSize;
/*     */ 
/*     */     
/* 766 */     dest.maxImpl = source.maxImpl.copy();
/* 767 */     dest.meanImpl = source.meanImpl.copy();
/* 768 */     dest.minImpl = source.minImpl.copy();
/* 769 */     dest.sumImpl = source.sumImpl.copy();
/* 770 */     dest.varianceImpl = source.varianceImpl.copy();
/* 771 */     dest.sumsqImpl = source.sumsqImpl.copy();
/* 772 */     dest.geometricMeanImpl = source.geometricMeanImpl.copy();
/* 773 */     dest.kurtosisImpl = source.kurtosisImpl;
/* 774 */     dest.skewnessImpl = source.skewnessImpl;
/* 775 */     dest.percentileImpl = source.percentileImpl;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/stat/descriptive/DescriptiveStatistics.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */