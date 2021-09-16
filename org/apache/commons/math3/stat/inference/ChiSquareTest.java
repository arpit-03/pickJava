/*     */ package org.apache.commons.math3.stat.inference;
/*     */ 
/*     */ import org.apache.commons.math3.distribution.ChiSquaredDistribution;
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.MaxCountExceededException;
/*     */ import org.apache.commons.math3.exception.NotPositiveException;
/*     */ import org.apache.commons.math3.exception.NotStrictlyPositiveException;
/*     */ import org.apache.commons.math3.exception.NullArgumentException;
/*     */ import org.apache.commons.math3.exception.OutOfRangeException;
/*     */ import org.apache.commons.math3.exception.ZeroException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChiSquareTest
/*     */ {
/*     */   public double chiSquare(double[] expected, long[] observed) throws NotPositiveException, NotStrictlyPositiveException, DimensionMismatchException {
/*  84 */     if (expected.length < 2) {
/*  85 */       throw new DimensionMismatchException(expected.length, 2);
/*     */     }
/*  87 */     if (expected.length != observed.length) {
/*  88 */       throw new DimensionMismatchException(expected.length, observed.length);
/*     */     }
/*  90 */     MathArrays.checkPositive(expected);
/*  91 */     MathArrays.checkNonNegative(observed);
/*     */     
/*  93 */     double sumExpected = 0.0D;
/*  94 */     double sumObserved = 0.0D;
/*  95 */     for (int i = 0; i < observed.length; i++) {
/*  96 */       sumExpected += expected[i];
/*  97 */       sumObserved += observed[i];
/*     */     } 
/*  99 */     double ratio = 1.0D;
/* 100 */     boolean rescale = false;
/* 101 */     if (FastMath.abs(sumExpected - sumObserved) > 1.0E-5D) {
/* 102 */       ratio = sumObserved / sumExpected;
/* 103 */       rescale = true;
/*     */     } 
/* 105 */     double sumSq = 0.0D;
/* 106 */     for (int j = 0; j < observed.length; j++) {
/* 107 */       if (rescale) {
/* 108 */         double dev = observed[j] - ratio * expected[j];
/* 109 */         sumSq += dev * dev / ratio * expected[j];
/*     */       } else {
/* 111 */         double dev = observed[j] - expected[j];
/* 112 */         sumSq += dev * dev / expected[j];
/*     */       } 
/*     */     } 
/* 115 */     return sumSq;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double chiSquareTest(double[] expected, long[] observed) throws NotPositiveException, NotStrictlyPositiveException, DimensionMismatchException, MaxCountExceededException {
/* 159 */     ChiSquaredDistribution distribution = new ChiSquaredDistribution(null, expected.length - 1.0D);
/*     */     
/* 161 */     return 1.0D - distribution.cumulativeProbability(chiSquare(expected, observed));
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean chiSquareTest(double[] expected, long[] observed, double alpha) throws NotPositiveException, NotStrictlyPositiveException, DimensionMismatchException, OutOfRangeException, MaxCountExceededException {
/* 208 */     if (alpha <= 0.0D || alpha > 0.5D) {
/* 209 */       throw new OutOfRangeException(LocalizedFormats.OUT_OF_BOUND_SIGNIFICANCE_LEVEL, Double.valueOf(alpha), Integer.valueOf(0), Double.valueOf(0.5D));
/*     */     }
/*     */     
/* 212 */     return (chiSquareTest(expected, observed) < alpha);
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
/*     */   public double chiSquare(long[][] counts) throws NullArgumentException, NotPositiveException, DimensionMismatchException {
/* 248 */     checkArray(counts);
/* 249 */     int nRows = counts.length;
/* 250 */     int nCols = (counts[0]).length;
/*     */ 
/*     */     
/* 253 */     double[] rowSum = new double[nRows];
/* 254 */     double[] colSum = new double[nCols];
/* 255 */     double total = 0.0D;
/* 256 */     for (int row = 0; row < nRows; row++) {
/* 257 */       for (int col = 0; col < nCols; col++) {
/* 258 */         rowSum[row] = rowSum[row] + counts[row][col];
/* 259 */         colSum[col] = colSum[col] + counts[row][col];
/* 260 */         total += counts[row][col];
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 265 */     double sumSq = 0.0D;
/* 266 */     double expected = 0.0D;
/* 267 */     for (int i = 0; i < nRows; i++) {
/* 268 */       for (int col = 0; col < nCols; col++) {
/* 269 */         expected = rowSum[i] * colSum[col] / total;
/* 270 */         sumSq += (counts[i][col] - expected) * (counts[i][col] - expected) / expected;
/*     */       } 
/*     */     } 
/*     */     
/* 274 */     return sumSq;
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
/*     */   public double chiSquareTest(long[][] counts) throws NullArgumentException, DimensionMismatchException, NotPositiveException, MaxCountExceededException {
/* 313 */     checkArray(counts);
/* 314 */     double df = (counts.length - 1.0D) * ((counts[0]).length - 1.0D);
/*     */     
/* 316 */     ChiSquaredDistribution distribution = new ChiSquaredDistribution(df);
/* 317 */     return 1.0D - distribution.cumulativeProbability(chiSquare(counts));
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean chiSquareTest(long[][] counts, double alpha) throws NullArgumentException, DimensionMismatchException, NotPositiveException, OutOfRangeException, MaxCountExceededException {
/* 363 */     if (alpha <= 0.0D || alpha > 0.5D) {
/* 364 */       throw new OutOfRangeException(LocalizedFormats.OUT_OF_BOUND_SIGNIFICANCE_LEVEL, Double.valueOf(alpha), Integer.valueOf(0), Double.valueOf(0.5D));
/*     */     }
/*     */     
/* 367 */     return (chiSquareTest(counts) < alpha);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double chiSquareDataSetsComparison(long[] observed1, long[] observed2) throws DimensionMismatchException, NotPositiveException, ZeroException {
/* 414 */     if (observed1.length < 2) {
/* 415 */       throw new DimensionMismatchException(observed1.length, 2);
/*     */     }
/* 417 */     if (observed1.length != observed2.length) {
/* 418 */       throw new DimensionMismatchException(observed1.length, observed2.length);
/*     */     }
/*     */ 
/*     */     
/* 422 */     MathArrays.checkNonNegative(observed1);
/* 423 */     MathArrays.checkNonNegative(observed2);
/*     */ 
/*     */     
/* 426 */     long countSum1 = 0L;
/* 427 */     long countSum2 = 0L;
/* 428 */     boolean unequalCounts = false;
/* 429 */     double weight = 0.0D;
/* 430 */     for (int i = 0; i < observed1.length; i++) {
/* 431 */       countSum1 += observed1[i];
/* 432 */       countSum2 += observed2[i];
/*     */     } 
/*     */     
/* 435 */     if (countSum1 == 0L || countSum2 == 0L) {
/* 436 */       throw new ZeroException();
/*     */     }
/*     */     
/* 439 */     unequalCounts = (countSum1 != countSum2);
/* 440 */     if (unequalCounts) {
/* 441 */       weight = FastMath.sqrt(countSum1 / countSum2);
/*     */     }
/*     */     
/* 444 */     double sumSq = 0.0D;
/* 445 */     double dev = 0.0D;
/* 446 */     double obs1 = 0.0D;
/* 447 */     double obs2 = 0.0D;
/* 448 */     for (int j = 0; j < observed1.length; j++) {
/* 449 */       if (observed1[j] == 0L && observed2[j] == 0L) {
/* 450 */         throw new ZeroException(LocalizedFormats.OBSERVED_COUNTS_BOTTH_ZERO_FOR_ENTRY, new Object[] { Integer.valueOf(j) });
/*     */       }
/* 452 */       obs1 = observed1[j];
/* 453 */       obs2 = observed2[j];
/* 454 */       if (unequalCounts) {
/* 455 */         dev = obs1 / weight - obs2 * weight;
/*     */       } else {
/* 457 */         dev = obs1 - obs2;
/*     */       } 
/* 459 */       sumSq += dev * dev / (obs1 + obs2);
/*     */     } 
/*     */     
/* 462 */     return sumSq;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double chiSquareTestDataSetsComparison(long[] observed1, long[] observed2) throws DimensionMismatchException, NotPositiveException, ZeroException, MaxCountExceededException {
/* 512 */     ChiSquaredDistribution distribution = new ChiSquaredDistribution(null, observed1.length - 1.0D);
/*     */     
/* 514 */     return 1.0D - distribution.cumulativeProbability(chiSquareDataSetsComparison(observed1, observed2));
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean chiSquareTestDataSetsComparison(long[] observed1, long[] observed2, double alpha) throws DimensionMismatchException, NotPositiveException, ZeroException, OutOfRangeException, MaxCountExceededException {
/* 567 */     if (alpha <= 0.0D || alpha > 0.5D)
/*     */     {
/* 569 */       throw new OutOfRangeException(LocalizedFormats.OUT_OF_BOUND_SIGNIFICANCE_LEVEL, Double.valueOf(alpha), Integer.valueOf(0), Double.valueOf(0.5D));
/*     */     }
/*     */     
/* 572 */     return (chiSquareTestDataSetsComparison(observed1, observed2) < alpha);
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
/*     */   private void checkArray(long[][] in) throws NullArgumentException, DimensionMismatchException, NotPositiveException {
/* 589 */     if (in.length < 2) {
/* 590 */       throw new DimensionMismatchException(in.length, 2);
/*     */     }
/*     */     
/* 593 */     if ((in[0]).length < 2) {
/* 594 */       throw new DimensionMismatchException((in[0]).length, 2);
/*     */     }
/*     */     
/* 597 */     MathArrays.checkRectangular(in);
/* 598 */     MathArrays.checkNonNegative(in);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/stat/inference/ChiSquareTest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */