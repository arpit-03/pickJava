/*     */ package org.apache.commons.math3.stat.inference;
/*     */ 
/*     */ import org.apache.commons.math3.distribution.ChiSquaredDistribution;
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.MaxCountExceededException;
/*     */ import org.apache.commons.math3.exception.NotPositiveException;
/*     */ import org.apache.commons.math3.exception.NotStrictlyPositiveException;
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
/*     */ public class GTest
/*     */ {
/*     */   public double g(double[] expected, long[] observed) throws NotPositiveException, NotStrictlyPositiveException, DimensionMismatchException {
/*  80 */     if (expected.length < 2) {
/*  81 */       throw new DimensionMismatchException(expected.length, 2);
/*     */     }
/*  83 */     if (expected.length != observed.length) {
/*  84 */       throw new DimensionMismatchException(expected.length, observed.length);
/*     */     }
/*  86 */     MathArrays.checkPositive(expected);
/*  87 */     MathArrays.checkNonNegative(observed);
/*     */     
/*  89 */     double sumExpected = 0.0D;
/*  90 */     double sumObserved = 0.0D;
/*  91 */     for (int i = 0; i < observed.length; i++) {
/*  92 */       sumExpected += expected[i];
/*  93 */       sumObserved += observed[i];
/*     */     } 
/*  95 */     double ratio = 1.0D;
/*  96 */     boolean rescale = false;
/*  97 */     if (FastMath.abs(sumExpected - sumObserved) > 1.0E-5D) {
/*  98 */       ratio = sumObserved / sumExpected;
/*  99 */       rescale = true;
/*     */     } 
/* 101 */     double sum = 0.0D;
/* 102 */     for (int j = 0; j < observed.length; j++) {
/* 103 */       double dev = rescale ? FastMath.log(observed[j] / ratio * expected[j]) : FastMath.log(observed[j] / expected[j]);
/*     */ 
/*     */       
/* 106 */       sum += observed[j] * dev;
/*     */     } 
/* 108 */     return 2.0D * sum;
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
/*     */   public double gTest(double[] expected, long[] observed) throws NotPositiveException, NotStrictlyPositiveException, DimensionMismatchException, MaxCountExceededException {
/* 156 */     ChiSquaredDistribution distribution = new ChiSquaredDistribution(null, expected.length - 1.0D);
/*     */     
/* 158 */     return 1.0D - distribution.cumulativeProbability(g(expected, observed));
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
/*     */   public double gTestIntrinsic(double[] expected, long[] observed) throws NotPositiveException, NotStrictlyPositiveException, DimensionMismatchException, MaxCountExceededException {
/* 187 */     ChiSquaredDistribution distribution = new ChiSquaredDistribution(null, expected.length - 2.0D);
/*     */     
/* 189 */     return 1.0D - distribution.cumulativeProbability(g(expected, observed));
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
/*     */   public boolean gTest(double[] expected, long[] observed, double alpha) throws NotPositiveException, NotStrictlyPositiveException, DimensionMismatchException, OutOfRangeException, MaxCountExceededException {
/* 241 */     if (alpha <= 0.0D || alpha > 0.5D) {
/* 242 */       throw new OutOfRangeException(LocalizedFormats.OUT_OF_BOUND_SIGNIFICANCE_LEVEL, Double.valueOf(alpha), Integer.valueOf(0), Double.valueOf(0.5D));
/*     */     }
/*     */     
/* 245 */     return (gTest(expected, observed) < alpha);
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
/*     */   private double entropy(long[][] k) {
/* 261 */     double h = 0.0D;
/* 262 */     double sum_k = 0.0D; int i;
/* 263 */     for (i = 0; i < k.length; i++) {
/* 264 */       for (int j = 0; j < (k[i]).length; j++) {
/* 265 */         sum_k += k[i][j];
/*     */       }
/*     */     } 
/* 268 */     for (i = 0; i < k.length; i++) {
/* 269 */       for (int j = 0; j < (k[i]).length; j++) {
/* 270 */         if (k[i][j] != 0L) {
/* 271 */           double p_ij = k[i][j] / sum_k;
/* 272 */           h += p_ij * FastMath.log(p_ij);
/*     */         } 
/*     */       } 
/*     */     } 
/* 276 */     return -h;
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
/*     */   private double entropy(long[] k) {
/* 291 */     double h = 0.0D;
/* 292 */     double sum_k = 0.0D; int i;
/* 293 */     for (i = 0; i < k.length; i++) {
/* 294 */       sum_k += k[i];
/*     */     }
/* 296 */     for (i = 0; i < k.length; i++) {
/* 297 */       if (k[i] != 0L) {
/* 298 */         double p_i = k[i] / sum_k;
/* 299 */         h += p_i * FastMath.log(p_i);
/*     */       } 
/*     */     } 
/* 302 */     return -h;
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
/*     */   public double gDataSetsComparison(long[] observed1, long[] observed2) throws DimensionMismatchException, NotPositiveException, ZeroException {
/* 351 */     if (observed1.length < 2) {
/* 352 */       throw new DimensionMismatchException(observed1.length, 2);
/*     */     }
/* 354 */     if (observed1.length != observed2.length) {
/* 355 */       throw new DimensionMismatchException(observed1.length, observed2.length);
/*     */     }
/*     */ 
/*     */     
/* 359 */     MathArrays.checkNonNegative(observed1);
/* 360 */     MathArrays.checkNonNegative(observed2);
/*     */ 
/*     */     
/* 363 */     long countSum1 = 0L;
/* 364 */     long countSum2 = 0L;
/*     */ 
/*     */     
/* 367 */     long[] collSums = new long[observed1.length];
/* 368 */     long[][] k = new long[2][observed1.length];
/*     */     
/* 370 */     for (int i = 0; i < observed1.length; i++) {
/* 371 */       if (observed1[i] == 0L && observed2[i] == 0L) {
/* 372 */         throw new ZeroException(LocalizedFormats.OBSERVED_COUNTS_BOTTH_ZERO_FOR_ENTRY, new Object[] { Integer.valueOf(i) });
/*     */       }
/* 374 */       countSum1 += observed1[i];
/* 375 */       countSum2 += observed2[i];
/* 376 */       collSums[i] = observed1[i] + observed2[i];
/* 377 */       k[0][i] = observed1[i];
/* 378 */       k[1][i] = observed2[i];
/*     */     } 
/*     */ 
/*     */     
/* 382 */     if (countSum1 == 0L || countSum2 == 0L) {
/* 383 */       throw new ZeroException();
/*     */     }
/* 385 */     long[] rowSums = { countSum1, countSum2 };
/* 386 */     double sum = countSum1 + countSum2;
/* 387 */     return 2.0D * sum * (entropy(rowSums) + entropy(collSums) - entropy(k));
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
/*     */   public double rootLogLikelihoodRatio(long k11, long k12, long k21, long k22) {
/* 422 */     double llr = gDataSetsComparison(new long[] { k11, k12 }, new long[] { k21, k22 });
/*     */     
/* 424 */     double sqrt = FastMath.sqrt(llr);
/* 425 */     if (k11 / (k11 + k12) < k21 / (k21 + k22)) {
/* 426 */       sqrt = -sqrt;
/*     */     }
/* 428 */     return sqrt;
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
/*     */   public double gTestDataSetsComparison(long[] observed1, long[] observed2) throws DimensionMismatchException, NotPositiveException, ZeroException, MaxCountExceededException {
/* 477 */     ChiSquaredDistribution distribution = new ChiSquaredDistribution(null, observed1.length - 1.0D);
/*     */     
/* 479 */     return 1.0D - distribution.cumulativeProbability(gDataSetsComparison(observed1, observed2));
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
/*     */   public boolean gTestDataSetsComparison(long[] observed1, long[] observed2, double alpha) throws DimensionMismatchException, NotPositiveException, ZeroException, OutOfRangeException, MaxCountExceededException {
/* 532 */     if (alpha <= 0.0D || alpha > 0.5D) {
/* 533 */       throw new OutOfRangeException(LocalizedFormats.OUT_OF_BOUND_SIGNIFICANCE_LEVEL, Double.valueOf(alpha), Integer.valueOf(0), Double.valueOf(0.5D));
/*     */     }
/*     */     
/* 536 */     return (gTestDataSetsComparison(observed1, observed2) < alpha);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/stat/inference/GTest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */