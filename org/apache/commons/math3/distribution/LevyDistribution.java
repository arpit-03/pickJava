/*     */ package org.apache.commons.math3.distribution;
/*     */ 
/*     */ import org.apache.commons.math3.exception.OutOfRangeException;
/*     */ import org.apache.commons.math3.random.RandomGenerator;
/*     */ import org.apache.commons.math3.random.Well19937c;
/*     */ import org.apache.commons.math3.special.Erf;
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
/*     */ public class LevyDistribution
/*     */   extends AbstractRealDistribution
/*     */ {
/*     */   private static final long serialVersionUID = 20130314L;
/*     */   private final double mu;
/*     */   private final double c;
/*     */   private final double halfC;
/*     */   
/*     */   public LevyDistribution(double mu, double c) {
/*  60 */     this((RandomGenerator)new Well19937c(), mu, c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LevyDistribution(RandomGenerator rng, double mu, double c) {
/*  70 */     super(rng);
/*  71 */     this.mu = mu;
/*  72 */     this.c = c;
/*  73 */     this.halfC = 0.5D * c;
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
/*     */   public double density(double x) {
/*  91 */     if (x < this.mu) {
/*  92 */       return Double.NaN;
/*     */     }
/*     */     
/*  95 */     double delta = x - this.mu;
/*  96 */     double f = this.halfC / delta;
/*  97 */     return FastMath.sqrt(f / Math.PI) * FastMath.exp(-f) / delta;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double logDensity(double x) {
/* 106 */     if (x < this.mu) {
/* 107 */       return Double.NaN;
/*     */     }
/*     */     
/* 110 */     double delta = x - this.mu;
/* 111 */     double f = this.halfC / delta;
/* 112 */     return 0.5D * FastMath.log(f / Math.PI) - f - FastMath.log(delta);
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
/*     */   public double cumulativeProbability(double x) {
/* 124 */     if (x < this.mu) {
/* 125 */       return Double.NaN;
/*     */     }
/* 127 */     return Erf.erfc(FastMath.sqrt(this.halfC / (x - this.mu)));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double inverseCumulativeProbability(double p) throws OutOfRangeException {
/* 133 */     if (p < 0.0D || p > 1.0D) {
/* 134 */       throw new OutOfRangeException(Double.valueOf(p), Integer.valueOf(0), Integer.valueOf(1));
/*     */     }
/* 136 */     double t = Erf.erfcInv(p);
/* 137 */     return this.mu + this.halfC / t * t;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getScale() {
/* 144 */     return this.c;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getLocation() {
/* 151 */     return this.mu;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getNumericalMean() {
/* 156 */     return Double.POSITIVE_INFINITY;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getNumericalVariance() {
/* 161 */     return Double.POSITIVE_INFINITY;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getSupportLowerBound() {
/* 166 */     return this.mu;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getSupportUpperBound() {
/* 171 */     return Double.POSITIVE_INFINITY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSupportLowerBoundInclusive() {
/* 178 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSupportUpperBoundInclusive() {
/* 184 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSupportConnected() {
/* 189 */     return true;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/distribution/LevyDistribution.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */