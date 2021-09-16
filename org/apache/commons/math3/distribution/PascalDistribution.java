/*     */ package org.apache.commons.math3.distribution;
/*     */ 
/*     */ import org.apache.commons.math3.exception.NotStrictlyPositiveException;
/*     */ import org.apache.commons.math3.exception.OutOfRangeException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.random.RandomGenerator;
/*     */ import org.apache.commons.math3.random.Well19937c;
/*     */ import org.apache.commons.math3.special.Beta;
/*     */ import org.apache.commons.math3.util.CombinatoricsUtils;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PascalDistribution
/*     */   extends AbstractIntegerDistribution
/*     */ {
/*     */   private static final long serialVersionUID = 6751309484392813623L;
/*     */   private final int numberOfSuccesses;
/*     */   private final double probabilityOfSuccess;
/*     */   private final double logProbabilityOfSuccess;
/*     */   private final double log1mProbabilityOfSuccess;
/*     */   
/*     */   public PascalDistribution(int r, double p) throws NotStrictlyPositiveException, OutOfRangeException {
/*  96 */     this((RandomGenerator)new Well19937c(), r, p);
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
/*     */   public PascalDistribution(RandomGenerator rng, int r, double p) throws NotStrictlyPositiveException, OutOfRangeException {
/* 115 */     super(rng);
/*     */     
/* 117 */     if (r <= 0) {
/* 118 */       throw new NotStrictlyPositiveException(LocalizedFormats.NUMBER_OF_SUCCESSES, Integer.valueOf(r));
/*     */     }
/*     */     
/* 121 */     if (p < 0.0D || p > 1.0D) {
/* 122 */       throw new OutOfRangeException(Double.valueOf(p), Integer.valueOf(0), Integer.valueOf(1));
/*     */     }
/*     */     
/* 125 */     this.numberOfSuccesses = r;
/* 126 */     this.probabilityOfSuccess = p;
/* 127 */     this.logProbabilityOfSuccess = FastMath.log(p);
/* 128 */     this.log1mProbabilityOfSuccess = FastMath.log1p(-p);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNumberOfSuccesses() {
/* 137 */     return this.numberOfSuccesses;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getProbabilityOfSuccess() {
/* 146 */     return this.probabilityOfSuccess;
/*     */   }
/*     */ 
/*     */   
/*     */   public double probability(int x) {
/*     */     double ret;
/* 152 */     if (x < 0) {
/* 153 */       ret = 0.0D;
/*     */     } else {
/* 155 */       ret = CombinatoricsUtils.binomialCoefficientDouble(x + this.numberOfSuccesses - 1, this.numberOfSuccesses - 1) * FastMath.pow(this.probabilityOfSuccess, this.numberOfSuccesses) * FastMath.pow(1.0D - this.probabilityOfSuccess, x);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 160 */     return ret;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double logProbability(int x) {
/*     */     double ret;
/* 167 */     if (x < 0) {
/* 168 */       ret = Double.NEGATIVE_INFINITY;
/*     */     } else {
/* 170 */       ret = CombinatoricsUtils.binomialCoefficientLog(x + this.numberOfSuccesses - 1, this.numberOfSuccesses - 1) + this.logProbabilityOfSuccess * this.numberOfSuccesses + this.log1mProbabilityOfSuccess * x;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 175 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   public double cumulativeProbability(int x) {
/*     */     double ret;
/* 181 */     if (x < 0) {
/* 182 */       ret = 0.0D;
/*     */     } else {
/* 184 */       ret = Beta.regularizedBeta(this.probabilityOfSuccess, this.numberOfSuccesses, x + 1.0D);
/*     */     } 
/*     */     
/* 187 */     return ret;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getNumericalMean() {
/* 197 */     double p = getProbabilityOfSuccess();
/* 198 */     double r = getNumberOfSuccesses();
/* 199 */     return r * (1.0D - p) / p;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getNumericalVariance() {
/* 209 */     double p = getProbabilityOfSuccess();
/* 210 */     double r = getNumberOfSuccesses();
/* 211 */     return r * (1.0D - p) / p * p;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSupportLowerBound() {
/* 222 */     return 0;
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
/*     */   public int getSupportUpperBound() {
/* 235 */     return Integer.MAX_VALUE;
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
/* 246 */     return true;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/distribution/PascalDistribution.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */