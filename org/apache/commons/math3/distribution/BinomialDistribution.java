/*     */ package org.apache.commons.math3.distribution;
/*     */ 
/*     */ import org.apache.commons.math3.exception.NotPositiveException;
/*     */ import org.apache.commons.math3.exception.OutOfRangeException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.random.RandomGenerator;
/*     */ import org.apache.commons.math3.random.Well19937c;
/*     */ import org.apache.commons.math3.special.Beta;
/*     */ import org.apache.commons.math3.util.FastMath;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BinomialDistribution
/*     */   extends AbstractIntegerDistribution
/*     */ {
/*     */   private static final long serialVersionUID = 6751309484392813623L;
/*     */   private final int numberOfTrials;
/*     */   private final double probabilityOfSuccess;
/*     */   
/*     */   public BinomialDistribution(int trials, double p) {
/*  58 */     this((RandomGenerator)new Well19937c(), trials, p);
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
/*     */   public BinomialDistribution(RandomGenerator rng, int trials, double p) {
/*  74 */     super(rng);
/*     */     
/*  76 */     if (trials < 0) {
/*  77 */       throw new NotPositiveException(LocalizedFormats.NUMBER_OF_TRIALS, Integer.valueOf(trials));
/*     */     }
/*     */     
/*  80 */     if (p < 0.0D || p > 1.0D) {
/*  81 */       throw new OutOfRangeException(Double.valueOf(p), Integer.valueOf(0), Integer.valueOf(1));
/*     */     }
/*     */     
/*  84 */     this.probabilityOfSuccess = p;
/*  85 */     this.numberOfTrials = trials;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNumberOfTrials() {
/*  94 */     return this.numberOfTrials;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getProbabilityOfSuccess() {
/* 103 */     return this.probabilityOfSuccess;
/*     */   }
/*     */ 
/*     */   
/*     */   public double probability(int x) {
/* 108 */     double logProbability = logProbability(x);
/* 109 */     return (logProbability == Double.NEGATIVE_INFINITY) ? 0.0D : FastMath.exp(logProbability);
/*     */   }
/*     */ 
/*     */   
/*     */   public double logProbability(int x) {
/*     */     double ret;
/* 115 */     if (this.numberOfTrials == 0) {
/* 116 */       return (x == 0) ? 0.0D : Double.NEGATIVE_INFINITY;
/*     */     }
/*     */     
/* 119 */     if (x < 0 || x > this.numberOfTrials) {
/* 120 */       ret = Double.NEGATIVE_INFINITY;
/*     */     } else {
/* 122 */       ret = SaddlePointExpansion.logBinomialProbability(x, this.numberOfTrials, this.probabilityOfSuccess, 1.0D - this.probabilityOfSuccess);
/*     */     } 
/*     */ 
/*     */     
/* 126 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   public double cumulativeProbability(int x) {
/*     */     double ret;
/* 132 */     if (x < 0) {
/* 133 */       ret = 0.0D;
/* 134 */     } else if (x >= this.numberOfTrials) {
/* 135 */       ret = 1.0D;
/*     */     } else {
/* 137 */       ret = 1.0D - Beta.regularizedBeta(this.probabilityOfSuccess, x + 1.0D, (this.numberOfTrials - x));
/*     */     } 
/*     */     
/* 140 */     return ret;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getNumericalMean() {
/* 150 */     return this.numberOfTrials * this.probabilityOfSuccess;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getNumericalVariance() {
/* 160 */     double p = this.probabilityOfSuccess;
/* 161 */     return this.numberOfTrials * p * (1.0D - p);
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
/*     */   public int getSupportLowerBound() {
/* 173 */     return (this.probabilityOfSuccess < 1.0D) ? 0 : this.numberOfTrials;
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
/*     */   public int getSupportUpperBound() {
/* 185 */     return (this.probabilityOfSuccess > 0.0D) ? this.numberOfTrials : 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSupportConnected() {
/* 196 */     return true;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/distribution/BinomialDistribution.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */