/*     */ package org.apache.commons.math3.distribution;
/*     */ 
/*     */ import org.apache.commons.math3.exception.NotStrictlyPositiveException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LogisticDistribution
/*     */   extends AbstractRealDistribution
/*     */ {
/*     */   private static final long serialVersionUID = 20141003L;
/*     */   private final double mu;
/*     */   private final double s;
/*     */   
/*     */   public LogisticDistribution(double mu, double s) {
/*  60 */     this((RandomGenerator)new Well19937c(), mu, s);
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
/*     */   public LogisticDistribution(RandomGenerator rng, double mu, double s) {
/*  72 */     super(rng);
/*     */     
/*  74 */     if (s <= 0.0D) {
/*  75 */       throw new NotStrictlyPositiveException(LocalizedFormats.NOT_POSITIVE_SCALE, Double.valueOf(s));
/*     */     }
/*     */     
/*  78 */     this.mu = mu;
/*  79 */     this.s = s;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getLocation() {
/*  88 */     return this.mu;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getScale() {
/*  97 */     return this.s;
/*     */   }
/*     */ 
/*     */   
/*     */   public double density(double x) {
/* 102 */     double z = (x - this.mu) / this.s;
/* 103 */     double v = FastMath.exp(-z);
/* 104 */     return 1.0D / this.s * v / (1.0D + v) * (1.0D + v);
/*     */   }
/*     */ 
/*     */   
/*     */   public double cumulativeProbability(double x) {
/* 109 */     double z = 1.0D / this.s * (x - this.mu);
/* 110 */     return 1.0D / (1.0D + FastMath.exp(-z));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double inverseCumulativeProbability(double p) throws OutOfRangeException {
/* 116 */     if (p < 0.0D || p > 1.0D)
/* 117 */       throw new OutOfRangeException(Double.valueOf(p), Double.valueOf(0.0D), Double.valueOf(1.0D)); 
/* 118 */     if (p == 0.0D)
/* 119 */       return 0.0D; 
/* 120 */     if (p == 1.0D) {
/* 121 */       return Double.POSITIVE_INFINITY;
/*     */     }
/* 123 */     return this.s * Math.log(p / (1.0D - p)) + this.mu;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getNumericalMean() {
/* 128 */     return this.mu;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getNumericalVariance() {
/* 133 */     return 3.289868133696453D * 1.0D / this.s * this.s;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getSupportLowerBound() {
/* 138 */     return Double.NEGATIVE_INFINITY;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getSupportUpperBound() {
/* 143 */     return Double.POSITIVE_INFINITY;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSupportLowerBoundInclusive() {
/* 148 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSupportUpperBoundInclusive() {
/* 153 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSupportConnected() {
/* 158 */     return true;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/distribution/LogisticDistribution.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */