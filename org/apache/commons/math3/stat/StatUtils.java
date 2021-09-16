/*     */ package org.apache.commons.math3.stat;
/*     */ 
/*     */ import java.util.List;
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.MathIllegalArgumentException;
/*     */ import org.apache.commons.math3.exception.NoDataException;
/*     */ import org.apache.commons.math3.exception.NotPositiveException;
/*     */ import org.apache.commons.math3.exception.NullArgumentException;
/*     */ import org.apache.commons.math3.exception.NumberIsTooSmallException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
/*     */ import org.apache.commons.math3.stat.descriptive.UnivariateStatistic;
/*     */ import org.apache.commons.math3.stat.descriptive.moment.GeometricMean;
/*     */ import org.apache.commons.math3.stat.descriptive.moment.Mean;
/*     */ import org.apache.commons.math3.stat.descriptive.moment.Variance;
/*     */ import org.apache.commons.math3.stat.descriptive.rank.Max;
/*     */ import org.apache.commons.math3.stat.descriptive.rank.Min;
/*     */ import org.apache.commons.math3.stat.descriptive.rank.Percentile;
/*     */ import org.apache.commons.math3.stat.descriptive.summary.Product;
/*     */ import org.apache.commons.math3.stat.descriptive.summary.Sum;
/*     */ import org.apache.commons.math3.stat.descriptive.summary.SumOfLogs;
/*     */ import org.apache.commons.math3.stat.descriptive.summary.SumOfSquares;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class StatUtils
/*     */ {
/*  49 */   private static final UnivariateStatistic SUM = (UnivariateStatistic)new Sum();
/*     */ 
/*     */   
/*  52 */   private static final UnivariateStatistic SUM_OF_SQUARES = (UnivariateStatistic)new SumOfSquares();
/*     */ 
/*     */   
/*  55 */   private static final UnivariateStatistic PRODUCT = (UnivariateStatistic)new Product();
/*     */ 
/*     */   
/*  58 */   private static final UnivariateStatistic SUM_OF_LOGS = (UnivariateStatistic)new SumOfLogs();
/*     */ 
/*     */   
/*  61 */   private static final UnivariateStatistic MIN = (UnivariateStatistic)new Min();
/*     */ 
/*     */   
/*  64 */   private static final UnivariateStatistic MAX = (UnivariateStatistic)new Max();
/*     */ 
/*     */   
/*  67 */   private static final UnivariateStatistic MEAN = (UnivariateStatistic)new Mean();
/*     */ 
/*     */   
/*  70 */   private static final Variance VARIANCE = new Variance();
/*     */ 
/*     */   
/*  73 */   private static final Percentile PERCENTILE = new Percentile();
/*     */ 
/*     */   
/*  76 */   private static final GeometricMean GEOMETRIC_MEAN = new GeometricMean();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double sum(double[] values) throws MathIllegalArgumentException {
/*  98 */     return SUM.evaluate(values);
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
/*     */   public static double sum(double[] values, int begin, int length) throws MathIllegalArgumentException {
/* 117 */     return SUM.evaluate(values, begin, length);
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
/*     */   public static double sumSq(double[] values) throws MathIllegalArgumentException {
/* 132 */     return SUM_OF_SQUARES.evaluate(values);
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
/*     */   public static double sumSq(double[] values, int begin, int length) throws MathIllegalArgumentException {
/* 151 */     return SUM_OF_SQUARES.evaluate(values, begin, length);
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
/*     */   public static double product(double[] values) throws MathIllegalArgumentException {
/* 166 */     return PRODUCT.evaluate(values);
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
/*     */   public static double product(double[] values, int begin, int length) throws MathIllegalArgumentException {
/* 185 */     return PRODUCT.evaluate(values, begin, length);
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
/*     */   public static double sumLog(double[] values) throws MathIllegalArgumentException {
/* 204 */     return SUM_OF_LOGS.evaluate(values);
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
/*     */   public static double sumLog(double[] values, int begin, int length) throws MathIllegalArgumentException {
/* 227 */     return SUM_OF_LOGS.evaluate(values, begin, length);
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
/*     */   public static double mean(double[] values) throws MathIllegalArgumentException {
/* 245 */     return MEAN.evaluate(values);
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
/*     */   public static double mean(double[] values, int begin, int length) throws MathIllegalArgumentException {
/* 267 */     return MEAN.evaluate(values, begin, length);
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
/*     */   public static double geometricMean(double[] values) throws MathIllegalArgumentException {
/* 285 */     return GEOMETRIC_MEAN.evaluate(values);
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
/*     */   public static double geometricMean(double[] values, int begin, int length) throws MathIllegalArgumentException {
/* 307 */     return GEOMETRIC_MEAN.evaluate(values, begin, length);
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
/*     */   public static double variance(double[] values) throws MathIllegalArgumentException {
/* 331 */     return VARIANCE.evaluate(values);
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
/*     */   public static double variance(double[] values, int begin, int length) throws MathIllegalArgumentException {
/* 360 */     return VARIANCE.evaluate(values, begin, length);
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
/*     */   public static double variance(double[] values, double mean, int begin, int length) throws MathIllegalArgumentException {
/* 395 */     return VARIANCE.evaluate(values, mean, begin, length);
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
/*     */   public static double variance(double[] values, double mean) throws MathIllegalArgumentException {
/* 426 */     return VARIANCE.evaluate(values, mean);
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
/*     */   public static double populationVariance(double[] values) throws MathIllegalArgumentException {
/* 447 */     return (new Variance(false)).evaluate(values);
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
/*     */   public static double populationVariance(double[] values, int begin, int length) throws MathIllegalArgumentException {
/* 473 */     return (new Variance(false)).evaluate(values, begin, length);
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
/*     */   public static double populationVariance(double[] values, double mean, int begin, int length) throws MathIllegalArgumentException {
/* 505 */     return (new Variance(false)).evaluate(values, mean, begin, length);
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
/*     */   public static double populationVariance(double[] values, double mean) throws MathIllegalArgumentException {
/* 533 */     return (new Variance(false)).evaluate(values, mean);
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
/*     */   public static double max(double[] values) throws MathIllegalArgumentException {
/* 554 */     return MAX.evaluate(values);
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
/*     */   public static double max(double[] values, int begin, int length) throws MathIllegalArgumentException {
/* 581 */     return MAX.evaluate(values, begin, length);
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
/*     */   public static double min(double[] values) throws MathIllegalArgumentException {
/* 602 */     return MIN.evaluate(values);
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
/*     */   public static double min(double[] values, int begin, int length) throws MathIllegalArgumentException {
/* 629 */     return MIN.evaluate(values, begin, length);
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
/*     */   public static double percentile(double[] values, double p) throws MathIllegalArgumentException {
/* 657 */     return PERCENTILE.evaluate(values, p);
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
/*     */   public static double percentile(double[] values, int begin, int length, double p) throws MathIllegalArgumentException {
/* 689 */     return PERCENTILE.evaluate(values, begin, length, p);
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
/*     */   public static double sumDifference(double[] sample1, double[] sample2) throws DimensionMismatchException, NoDataException {
/* 705 */     int n = sample1.length;
/* 706 */     if (n != sample2.length) {
/* 707 */       throw new DimensionMismatchException(n, sample2.length);
/*     */     }
/* 709 */     if (n <= 0) {
/* 710 */       throw new NoDataException(LocalizedFormats.INSUFFICIENT_DIMENSION);
/*     */     }
/* 712 */     double result = 0.0D;
/* 713 */     for (int i = 0; i < n; i++) {
/* 714 */       result += sample1[i] - sample2[i];
/*     */     }
/* 716 */     return result;
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
/*     */   public static double meanDifference(double[] sample1, double[] sample2) throws DimensionMismatchException, NoDataException {
/* 732 */     return sumDifference(sample1, sample2) / sample1.length;
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
/*     */   public static double varianceDifference(double[] sample1, double[] sample2, double meanDifference) throws DimensionMismatchException, NumberIsTooSmallException {
/* 751 */     double sum1 = 0.0D;
/* 752 */     double sum2 = 0.0D;
/* 753 */     double diff = 0.0D;
/* 754 */     int n = sample1.length;
/* 755 */     if (n != sample2.length) {
/* 756 */       throw new DimensionMismatchException(n, sample2.length);
/*     */     }
/* 758 */     if (n < 2) {
/* 759 */       throw new NumberIsTooSmallException(Integer.valueOf(n), Integer.valueOf(2), true);
/*     */     }
/* 761 */     for (int i = 0; i < n; i++) {
/* 762 */       diff = sample1[i] - sample2[i];
/* 763 */       sum1 += (diff - meanDifference) * (diff - meanDifference);
/* 764 */       sum2 += diff - meanDifference;
/*     */     } 
/* 766 */     return (sum1 - sum2 * sum2 / n) / (n - 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double[] normalize(double[] sample) {
/* 777 */     DescriptiveStatistics stats = new DescriptiveStatistics();
/*     */ 
/*     */     
/* 780 */     for (int i = 0; i < sample.length; i++) {
/* 781 */       stats.addValue(sample[i]);
/*     */     }
/*     */ 
/*     */     
/* 785 */     double mean = stats.getMean();
/* 786 */     double standardDeviation = stats.getStandardDeviation();
/*     */ 
/*     */     
/* 789 */     double[] standardizedSample = new double[sample.length];
/*     */     
/* 791 */     for (int j = 0; j < sample.length; j++)
/*     */     {
/* 793 */       standardizedSample[j] = (sample[j] - mean) / standardDeviation;
/*     */     }
/* 795 */     return standardizedSample;
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
/*     */   public static double[] mode(double[] sample) throws MathIllegalArgumentException {
/* 817 */     if (sample == null) {
/* 818 */       throw new NullArgumentException(LocalizedFormats.INPUT_ARRAY, new Object[0]);
/*     */     }
/* 820 */     return getMode(sample, 0, sample.length);
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
/*     */   public static double[] mode(double[] sample, int begin, int length) {
/* 845 */     if (sample == null) {
/* 846 */       throw new NullArgumentException(LocalizedFormats.INPUT_ARRAY, new Object[0]);
/*     */     }
/*     */     
/* 849 */     if (begin < 0) {
/* 850 */       throw new NotPositiveException(LocalizedFormats.START_POSITION, Integer.valueOf(begin));
/*     */     }
/*     */     
/* 853 */     if (length < 0) {
/* 854 */       throw new NotPositiveException(LocalizedFormats.LENGTH, Integer.valueOf(length));
/*     */     }
/*     */     
/* 857 */     return getMode(sample, begin, length);
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
/*     */   private static double[] getMode(double[] values, int begin, int length) {
/* 870 */     Frequency freq = new Frequency();
/* 871 */     for (int i = begin; i < begin + length; i++) {
/* 872 */       double value = values[i];
/* 873 */       if (!Double.isNaN(value)) {
/* 874 */         freq.addValue(Double.valueOf(value));
/*     */       }
/*     */     } 
/* 877 */     List<Comparable<?>> list = freq.getMode();
/*     */     
/* 879 */     double[] modes = new double[list.size()];
/* 880 */     int j = 0;
/* 881 */     for (Comparable<?> c : list) {
/* 882 */       modes[j++] = ((Double)c).doubleValue();
/*     */     }
/* 884 */     return modes;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/stat/StatUtils.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */