/*     */ package org.apache.commons.math3.stat.inference;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import org.apache.commons.math3.distribution.RealDistribution;
/*     */ import org.apache.commons.math3.exception.ConvergenceException;
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.InsufficientDataException;
/*     */ import org.apache.commons.math3.exception.MaxCountExceededException;
/*     */ import org.apache.commons.math3.exception.NoDataException;
/*     */ import org.apache.commons.math3.exception.NotPositiveException;
/*     */ import org.apache.commons.math3.exception.NotStrictlyPositiveException;
/*     */ import org.apache.commons.math3.exception.NullArgumentException;
/*     */ import org.apache.commons.math3.exception.NumberIsTooSmallException;
/*     */ import org.apache.commons.math3.exception.OutOfRangeException;
/*     */ import org.apache.commons.math3.exception.ZeroException;
/*     */ import org.apache.commons.math3.stat.descriptive.StatisticalSummary;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TestUtils
/*     */ {
/*  44 */   private static final TTest T_TEST = new TTest();
/*     */ 
/*     */   
/*  47 */   private static final ChiSquareTest CHI_SQUARE_TEST = new ChiSquareTest();
/*     */ 
/*     */   
/*  50 */   private static final OneWayAnova ONE_WAY_ANANOVA = new OneWayAnova();
/*     */ 
/*     */   
/*  53 */   private static final GTest G_TEST = new GTest();
/*     */ 
/*     */   
/*  56 */   private static final KolmogorovSmirnovTest KS_TEST = new KolmogorovSmirnovTest();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double homoscedasticT(double[] sample1, double[] sample2) throws NullArgumentException, NumberIsTooSmallException {
/*  72 */     return T_TEST.homoscedasticT(sample1, sample2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double homoscedasticT(StatisticalSummary sampleStats1, StatisticalSummary sampleStats2) throws NullArgumentException, NumberIsTooSmallException {
/*  81 */     return T_TEST.homoscedasticT(sampleStats1, sampleStats2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean homoscedasticTTest(double[] sample1, double[] sample2, double alpha) throws NullArgumentException, NumberIsTooSmallException, OutOfRangeException, MaxCountExceededException {
/*  91 */     return T_TEST.homoscedasticTTest(sample1, sample2, alpha);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double homoscedasticTTest(double[] sample1, double[] sample2) throws NullArgumentException, NumberIsTooSmallException, MaxCountExceededException {
/*  99 */     return T_TEST.homoscedasticTTest(sample1, sample2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double homoscedasticTTest(StatisticalSummary sampleStats1, StatisticalSummary sampleStats2) throws NullArgumentException, NumberIsTooSmallException, MaxCountExceededException {
/* 108 */     return T_TEST.homoscedasticTTest(sampleStats1, sampleStats2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double pairedT(double[] sample1, double[] sample2) throws NullArgumentException, NoDataException, DimensionMismatchException, NumberIsTooSmallException {
/* 117 */     return T_TEST.pairedT(sample1, sample2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean pairedTTest(double[] sample1, double[] sample2, double alpha) throws NullArgumentException, NoDataException, DimensionMismatchException, NumberIsTooSmallException, OutOfRangeException, MaxCountExceededException {
/* 127 */     return T_TEST.pairedTTest(sample1, sample2, alpha);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double pairedTTest(double[] sample1, double[] sample2) throws NullArgumentException, NoDataException, DimensionMismatchException, NumberIsTooSmallException, MaxCountExceededException {
/* 136 */     return T_TEST.pairedTTest(sample1, sample2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double t(double mu, double[] observed) throws NullArgumentException, NumberIsTooSmallException {
/* 144 */     return T_TEST.t(mu, observed);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double t(double mu, StatisticalSummary sampleStats) throws NullArgumentException, NumberIsTooSmallException {
/* 152 */     return T_TEST.t(mu, sampleStats);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double t(double[] sample1, double[] sample2) throws NullArgumentException, NumberIsTooSmallException {
/* 160 */     return T_TEST.t(sample1, sample2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double t(StatisticalSummary sampleStats1, StatisticalSummary sampleStats2) throws NullArgumentException, NumberIsTooSmallException {
/* 169 */     return T_TEST.t(sampleStats1, sampleStats2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean tTest(double mu, double[] sample, double alpha) throws NullArgumentException, NumberIsTooSmallException, OutOfRangeException, MaxCountExceededException {
/* 178 */     return T_TEST.tTest(mu, sample, alpha);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double tTest(double mu, double[] sample) throws NullArgumentException, NumberIsTooSmallException, MaxCountExceededException {
/* 187 */     return T_TEST.tTest(mu, sample);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean tTest(double mu, StatisticalSummary sampleStats, double alpha) throws NullArgumentException, NumberIsTooSmallException, OutOfRangeException, MaxCountExceededException {
/* 197 */     return T_TEST.tTest(mu, sampleStats, alpha);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double tTest(double mu, StatisticalSummary sampleStats) throws NullArgumentException, NumberIsTooSmallException, MaxCountExceededException {
/* 206 */     return T_TEST.tTest(mu, sampleStats);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean tTest(double[] sample1, double[] sample2, double alpha) throws NullArgumentException, NumberIsTooSmallException, OutOfRangeException, MaxCountExceededException {
/* 216 */     return T_TEST.tTest(sample1, sample2, alpha);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double tTest(double[] sample1, double[] sample2) throws NullArgumentException, NumberIsTooSmallException, MaxCountExceededException {
/* 225 */     return T_TEST.tTest(sample1, sample2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean tTest(StatisticalSummary sampleStats1, StatisticalSummary sampleStats2, double alpha) throws NullArgumentException, NumberIsTooSmallException, OutOfRangeException, MaxCountExceededException {
/* 236 */     return T_TEST.tTest(sampleStats1, sampleStats2, alpha);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double tTest(StatisticalSummary sampleStats1, StatisticalSummary sampleStats2) throws NullArgumentException, NumberIsTooSmallException, MaxCountExceededException {
/* 246 */     return T_TEST.tTest(sampleStats1, sampleStats2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double chiSquare(double[] expected, long[] observed) throws NotPositiveException, NotStrictlyPositiveException, DimensionMismatchException {
/* 255 */     return CHI_SQUARE_TEST.chiSquare(expected, observed);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double chiSquare(long[][] counts) throws NullArgumentException, NotPositiveException, DimensionMismatchException {
/* 264 */     return CHI_SQUARE_TEST.chiSquare(counts);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean chiSquareTest(double[] expected, long[] observed, double alpha) throws NotPositiveException, NotStrictlyPositiveException, DimensionMismatchException, OutOfRangeException, MaxCountExceededException {
/* 274 */     return CHI_SQUARE_TEST.chiSquareTest(expected, observed, alpha);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double chiSquareTest(double[] expected, long[] observed) throws NotPositiveException, NotStrictlyPositiveException, DimensionMismatchException, MaxCountExceededException {
/* 283 */     return CHI_SQUARE_TEST.chiSquareTest(expected, observed);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean chiSquareTest(long[][] counts, double alpha) throws NullArgumentException, DimensionMismatchException, NotPositiveException, OutOfRangeException, MaxCountExceededException {
/* 292 */     return CHI_SQUARE_TEST.chiSquareTest(counts, alpha);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double chiSquareTest(long[][] counts) throws NullArgumentException, DimensionMismatchException, NotPositiveException, MaxCountExceededException {
/* 301 */     return CHI_SQUARE_TEST.chiSquareTest(counts);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double chiSquareDataSetsComparison(long[] observed1, long[] observed2) throws DimensionMismatchException, NotPositiveException, ZeroException {
/* 312 */     return CHI_SQUARE_TEST.chiSquareDataSetsComparison(observed1, observed2);
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
/*     */   public static double chiSquareTestDataSetsComparison(long[] observed1, long[] observed2) throws DimensionMismatchException, NotPositiveException, ZeroException, MaxCountExceededException {
/* 324 */     return CHI_SQUARE_TEST.chiSquareTestDataSetsComparison(observed1, observed2);
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
/*     */   public static boolean chiSquareTestDataSetsComparison(long[] observed1, long[] observed2, double alpha) throws DimensionMismatchException, NotPositiveException, ZeroException, OutOfRangeException, MaxCountExceededException {
/* 337 */     return CHI_SQUARE_TEST.chiSquareTestDataSetsComparison(observed1, observed2, alpha);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double oneWayAnovaFValue(Collection<double[]> categoryData) throws NullArgumentException, DimensionMismatchException {
/* 347 */     return ONE_WAY_ANANOVA.anovaFValue(categoryData);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double oneWayAnovaPValue(Collection<double[]> categoryData) throws NullArgumentException, DimensionMismatchException, ConvergenceException, MaxCountExceededException {
/* 358 */     return ONE_WAY_ANANOVA.anovaPValue(categoryData);
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
/*     */   public static boolean oneWayAnovaTest(Collection<double[]> categoryData, double alpha) throws NullArgumentException, DimensionMismatchException, OutOfRangeException, ConvergenceException, MaxCountExceededException {
/* 370 */     return ONE_WAY_ANANOVA.anovaTest(categoryData, alpha);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double g(double[] expected, long[] observed) throws NotPositiveException, NotStrictlyPositiveException, DimensionMismatchException {
/* 380 */     return G_TEST.g(expected, observed);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double gTest(double[] expected, long[] observed) throws NotPositiveException, NotStrictlyPositiveException, DimensionMismatchException, MaxCountExceededException {
/* 390 */     return G_TEST.gTest(expected, observed);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double gTestIntrinsic(double[] expected, long[] observed) throws NotPositiveException, NotStrictlyPositiveException, DimensionMismatchException, MaxCountExceededException {
/* 400 */     return G_TEST.gTestIntrinsic(expected, observed);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean gTest(double[] expected, long[] observed, double alpha) throws NotPositiveException, NotStrictlyPositiveException, DimensionMismatchException, OutOfRangeException, MaxCountExceededException {
/* 411 */     return G_TEST.gTest(expected, observed, alpha);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double gDataSetsComparison(long[] observed1, long[] observed2) throws DimensionMismatchException, NotPositiveException, ZeroException {
/* 421 */     return G_TEST.gDataSetsComparison(observed1, observed2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double rootLogLikelihoodRatio(long k11, long k12, long k21, long k22) throws DimensionMismatchException, NotPositiveException, ZeroException {
/* 430 */     return G_TEST.rootLogLikelihoodRatio(k11, k12, k21, k22);
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
/*     */   public static double gTestDataSetsComparison(long[] observed1, long[] observed2) throws DimensionMismatchException, NotPositiveException, ZeroException, MaxCountExceededException {
/* 442 */     return G_TEST.gTestDataSetsComparison(observed1, observed2);
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
/*     */   public static boolean gTestDataSetsComparison(long[] observed1, long[] observed2, double alpha) throws DimensionMismatchException, NotPositiveException, ZeroException, OutOfRangeException, MaxCountExceededException {
/* 454 */     return G_TEST.gTestDataSetsComparison(observed1, observed2, alpha);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double kolmogorovSmirnovStatistic(RealDistribution dist, double[] data) throws InsufficientDataException, NullArgumentException {
/* 463 */     return KS_TEST.kolmogorovSmirnovStatistic(dist, data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double kolmogorovSmirnovTest(RealDistribution dist, double[] data) throws InsufficientDataException, NullArgumentException {
/* 472 */     return KS_TEST.kolmogorovSmirnovTest(dist, data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double kolmogorovSmirnovTest(RealDistribution dist, double[] data, boolean strict) throws InsufficientDataException, NullArgumentException {
/* 481 */     return KS_TEST.kolmogorovSmirnovTest(dist, data, strict);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean kolmogorovSmirnovTest(RealDistribution dist, double[] data, double alpha) throws InsufficientDataException, NullArgumentException {
/* 490 */     return KS_TEST.kolmogorovSmirnovTest(dist, data, alpha);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double kolmogorovSmirnovStatistic(double[] x, double[] y) throws InsufficientDataException, NullArgumentException {
/* 499 */     return KS_TEST.kolmogorovSmirnovStatistic(x, y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double kolmogorovSmirnovTest(double[] x, double[] y) throws InsufficientDataException, NullArgumentException {
/* 508 */     return KS_TEST.kolmogorovSmirnovTest(x, y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double kolmogorovSmirnovTest(double[] x, double[] y, boolean strict) throws InsufficientDataException, NullArgumentException {
/* 517 */     return KS_TEST.kolmogorovSmirnovTest(x, y, strict);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double exactP(double d, int m, int n, boolean strict) {
/* 525 */     return KS_TEST.exactP(d, n, m, strict);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double approximateP(double d, int n, int m) {
/* 533 */     return KS_TEST.approximateP(d, n, m);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double monteCarloP(double d, int n, int m, boolean strict, int iterations) {
/* 541 */     return KS_TEST.monteCarloP(d, n, m, strict, iterations);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/stat/inference/TestUtils.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */