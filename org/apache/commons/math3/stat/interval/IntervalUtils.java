/*     */ package org.apache.commons.math3.stat.interval;
/*     */ 
/*     */ import org.apache.commons.math3.exception.NotPositiveException;
/*     */ import org.apache.commons.math3.exception.NotStrictlyPositiveException;
/*     */ import org.apache.commons.math3.exception.NumberIsTooLargeException;
/*     */ import org.apache.commons.math3.exception.OutOfRangeException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class IntervalUtils
/*     */ {
/*  40 */   private static final BinomialConfidenceInterval AGRESTI_COULL = new AgrestiCoullInterval();
/*     */ 
/*     */   
/*  43 */   private static final BinomialConfidenceInterval CLOPPER_PEARSON = new ClopperPearsonInterval();
/*     */ 
/*     */   
/*  46 */   private static final BinomialConfidenceInterval NORMAL_APPROXIMATION = new NormalApproximationInterval();
/*     */ 
/*     */   
/*  49 */   private static final BinomialConfidenceInterval WILSON_SCORE = new WilsonScoreInterval();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ConfidenceInterval getAgrestiCoullInterval(int numberOfTrials, int numberOfSuccesses, double confidenceLevel) {
/*  75 */     return AGRESTI_COULL.createInterval(numberOfTrials, numberOfSuccesses, confidenceLevel);
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
/*     */   public static ConfidenceInterval getClopperPearsonInterval(int numberOfTrials, int numberOfSuccesses, double confidenceLevel) {
/* 104 */     return CLOPPER_PEARSON.createInterval(numberOfTrials, numberOfSuccesses, confidenceLevel);
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
/*     */   public static ConfidenceInterval getNormalApproximationInterval(int numberOfTrials, int numberOfSuccesses, double confidenceLevel) {
/* 122 */     return NORMAL_APPROXIMATION.createInterval(numberOfTrials, numberOfSuccesses, confidenceLevel);
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
/*     */   public static ConfidenceInterval getWilsonScoreInterval(int numberOfTrials, int numberOfSuccesses, double confidenceLevel) {
/* 143 */     return WILSON_SCORE.createInterval(numberOfTrials, numberOfSuccesses, confidenceLevel);
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
/*     */   static void checkParameters(int numberOfTrials, int numberOfSuccesses, double confidenceLevel) {
/* 158 */     if (numberOfTrials <= 0) {
/* 159 */       throw new NotStrictlyPositiveException(LocalizedFormats.NUMBER_OF_TRIALS, Integer.valueOf(numberOfTrials));
/*     */     }
/* 161 */     if (numberOfSuccesses < 0) {
/* 162 */       throw new NotPositiveException(LocalizedFormats.NEGATIVE_NUMBER_OF_SUCCESSES, Integer.valueOf(numberOfSuccesses));
/*     */     }
/* 164 */     if (numberOfSuccesses > numberOfTrials) {
/* 165 */       throw new NumberIsTooLargeException(LocalizedFormats.NUMBER_OF_SUCCESS_LARGER_THAN_POPULATION_SIZE, Integer.valueOf(numberOfSuccesses), Integer.valueOf(numberOfTrials), true);
/*     */     }
/*     */     
/* 168 */     if (confidenceLevel <= 0.0D || confidenceLevel >= 1.0D)
/* 169 */       throw new OutOfRangeException(LocalizedFormats.OUT_OF_BOUNDS_CONFIDENCE_LEVEL, Double.valueOf(confidenceLevel), Integer.valueOf(0), Integer.valueOf(1)); 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/stat/interval/IntervalUtils.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */