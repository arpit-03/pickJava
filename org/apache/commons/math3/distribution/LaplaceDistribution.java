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
/*     */ public class LaplaceDistribution
/*     */   extends AbstractRealDistribution
/*     */ {
/*     */   private static final long serialVersionUID = 20141003L;
/*     */   private final double mu;
/*     */   private final double beta;
/*     */   
/*     */   public LaplaceDistribution(double mu, double beta) {
/*  58 */     this((RandomGenerator)new Well19937c(), mu, beta);
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
/*     */   public LaplaceDistribution(RandomGenerator rng, double mu, double beta) {
/*  70 */     super(rng);
/*     */     
/*  72 */     if (beta <= 0.0D) {
/*  73 */       throw new NotStrictlyPositiveException(LocalizedFormats.NOT_POSITIVE_SCALE, Double.valueOf(beta));
/*     */     }
/*     */     
/*  76 */     this.mu = mu;
/*  77 */     this.beta = beta;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getLocation() {
/*  86 */     return this.mu;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getScale() {
/*  95 */     return this.beta;
/*     */   }
/*     */ 
/*     */   
/*     */   public double density(double x) {
/* 100 */     return FastMath.exp(-FastMath.abs(x - this.mu) / this.beta) / 2.0D * this.beta;
/*     */   }
/*     */ 
/*     */   
/*     */   public double cumulativeProbability(double x) {
/* 105 */     if (x <= this.mu) {
/* 106 */       return FastMath.exp((x - this.mu) / this.beta) / 2.0D;
/*     */     }
/* 108 */     return 1.0D - FastMath.exp((this.mu - x) / this.beta) / 2.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double inverseCumulativeProbability(double p) throws OutOfRangeException {
/* 115 */     if (p < 0.0D || p > 1.0D)
/* 116 */       throw new OutOfRangeException(Double.valueOf(p), Double.valueOf(0.0D), Double.valueOf(1.0D)); 
/* 117 */     if (p == 0.0D)
/* 118 */       return Double.NEGATIVE_INFINITY; 
/* 119 */     if (p == 1.0D) {
/* 120 */       return Double.POSITIVE_INFINITY;
/*     */     }
/* 122 */     double x = (p > 0.5D) ? -Math.log(2.0D - 2.0D * p) : Math.log(2.0D * p);
/* 123 */     return this.mu + this.beta * x;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getNumericalMean() {
/* 128 */     return this.mu;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getNumericalVariance() {
/* 133 */     return 2.0D * this.beta * this.beta;
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


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/distribution/LaplaceDistribution.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */