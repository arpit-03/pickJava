/*     */ package org.apache.commons.math3.distribution;
/*     */ 
/*     */ import org.apache.commons.math3.exception.OutOfRangeException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.random.RandomGenerator;
/*     */ import org.apache.commons.math3.random.Well19937c;
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
/*     */ public class GeometricDistribution
/*     */   extends AbstractIntegerDistribution
/*     */ {
/*     */   private static final long serialVersionUID = 20130507L;
/*     */   private final double probabilityOfSuccess;
/*     */   private final double logProbabilityOfSuccess;
/*     */   private final double log1mProbabilityOfSuccess;
/*     */   
/*     */   public GeometricDistribution(double p) {
/*  57 */     this((RandomGenerator)new Well19937c(), p);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GeometricDistribution(RandomGenerator rng, double p) {
/*  68 */     super(rng);
/*     */     
/*  70 */     if (p <= 0.0D || p > 1.0D) {
/*  71 */       throw new OutOfRangeException(LocalizedFormats.OUT_OF_RANGE_LEFT, Double.valueOf(p), Integer.valueOf(0), Integer.valueOf(1));
/*     */     }
/*     */     
/*  74 */     this.probabilityOfSuccess = p;
/*  75 */     this.logProbabilityOfSuccess = FastMath.log(p);
/*  76 */     this.log1mProbabilityOfSuccess = FastMath.log1p(-p);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getProbabilityOfSuccess() {
/*  85 */     return this.probabilityOfSuccess;
/*     */   }
/*     */ 
/*     */   
/*     */   public double probability(int x) {
/*  90 */     if (x < 0) {
/*  91 */       return 0.0D;
/*     */     }
/*  93 */     return FastMath.exp(this.log1mProbabilityOfSuccess * x) * this.probabilityOfSuccess;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double logProbability(int x) {
/* 100 */     if (x < 0) {
/* 101 */       return Double.NEGATIVE_INFINITY;
/*     */     }
/* 103 */     return x * this.log1mProbabilityOfSuccess + this.logProbabilityOfSuccess;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double cumulativeProbability(int x) {
/* 109 */     if (x < 0) {
/* 110 */       return 0.0D;
/*     */     }
/* 112 */     return -FastMath.expm1(this.log1mProbabilityOfSuccess * (x + 1));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getNumericalMean() {
/* 122 */     return (1.0D - this.probabilityOfSuccess) / this.probabilityOfSuccess;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getNumericalVariance() {
/* 132 */     return (1.0D - this.probabilityOfSuccess) / this.probabilityOfSuccess * this.probabilityOfSuccess;
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
/* 143 */     return 0;
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
/* 155 */     return Integer.MAX_VALUE;
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
/* 166 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int inverseCumulativeProbability(double p) throws OutOfRangeException {
/* 174 */     if (p < 0.0D || p > 1.0D) {
/* 175 */       throw new OutOfRangeException(Double.valueOf(p), Integer.valueOf(0), Integer.valueOf(1));
/*     */     }
/* 177 */     if (p == 1.0D) {
/* 178 */       return Integer.MAX_VALUE;
/*     */     }
/* 180 */     if (p == 0.0D) {
/* 181 */       return 0;
/*     */     }
/* 183 */     return Math.max(0, (int)Math.ceil(FastMath.log1p(-p) / this.log1mProbabilityOfSuccess - 1.0D));
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/distribution/GeometricDistribution.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */