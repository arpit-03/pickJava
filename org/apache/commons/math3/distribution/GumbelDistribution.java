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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GumbelDistribution
/*     */   extends AbstractRealDistribution
/*     */ {
/*     */   private static final long serialVersionUID = 20141003L;
/*     */   private static final double EULER = 0.5778636748954609D;
/*     */   private final double mu;
/*     */   private final double beta;
/*     */   
/*     */   public GumbelDistribution(double mu, double beta) {
/*  66 */     this((RandomGenerator)new Well19937c(), mu, beta);
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
/*     */   public GumbelDistribution(RandomGenerator rng, double mu, double beta) {
/*  78 */     super(rng);
/*     */     
/*  80 */     if (beta <= 0.0D) {
/*  81 */       throw new NotStrictlyPositiveException(LocalizedFormats.SCALE, Double.valueOf(beta));
/*     */     }
/*     */     
/*  84 */     this.beta = beta;
/*  85 */     this.mu = mu;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getLocation() {
/*  94 */     return this.mu;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getScale() {
/* 103 */     return this.beta;
/*     */   }
/*     */ 
/*     */   
/*     */   public double density(double x) {
/* 108 */     double z = (x - this.mu) / this.beta;
/* 109 */     double t = FastMath.exp(-z);
/* 110 */     return FastMath.exp(-z - t) / this.beta;
/*     */   }
/*     */ 
/*     */   
/*     */   public double cumulativeProbability(double x) {
/* 115 */     double z = (x - this.mu) / this.beta;
/* 116 */     return FastMath.exp(-FastMath.exp(-z));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double inverseCumulativeProbability(double p) throws OutOfRangeException {
/* 122 */     if (p < 0.0D || p > 1.0D)
/* 123 */       throw new OutOfRangeException(Double.valueOf(p), Double.valueOf(0.0D), Double.valueOf(1.0D)); 
/* 124 */     if (p == 0.0D)
/* 125 */       return Double.NEGATIVE_INFINITY; 
/* 126 */     if (p == 1.0D) {
/* 127 */       return Double.POSITIVE_INFINITY;
/*     */     }
/* 129 */     return this.mu - FastMath.log(-FastMath.log(p)) * this.beta;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getNumericalMean() {
/* 134 */     return this.mu + 0.5778636748954609D * this.beta;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getNumericalVariance() {
/* 139 */     return 1.6449340668482264D * this.beta * this.beta;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getSupportLowerBound() {
/* 144 */     return Double.NEGATIVE_INFINITY;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getSupportUpperBound() {
/* 149 */     return Double.POSITIVE_INFINITY;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSupportLowerBoundInclusive() {
/* 154 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSupportUpperBoundInclusive() {
/* 159 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSupportConnected() {
/* 164 */     return true;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/distribution/GumbelDistribution.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */