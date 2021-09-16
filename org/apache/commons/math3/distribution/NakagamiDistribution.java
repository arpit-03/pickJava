/*     */ package org.apache.commons.math3.distribution;
/*     */ 
/*     */ import org.apache.commons.math3.exception.NotStrictlyPositiveException;
/*     */ import org.apache.commons.math3.exception.NumberIsTooSmallException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.random.RandomGenerator;
/*     */ import org.apache.commons.math3.random.Well19937c;
/*     */ import org.apache.commons.math3.special.Gamma;
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
/*     */ public class NakagamiDistribution
/*     */   extends AbstractRealDistribution
/*     */ {
/*     */   public static final double DEFAULT_INVERSE_ABSOLUTE_ACCURACY = 1.0E-9D;
/*     */   private static final long serialVersionUID = 20141003L;
/*     */   private final double mu;
/*     */   private final double omega;
/*     */   private final double inverseAbsoluteAccuracy;
/*     */   
/*     */   public NakagamiDistribution(double mu, double omega) {
/*  65 */     this(mu, omega, 1.0E-9D);
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
/*     */   public NakagamiDistribution(double mu, double omega, double inverseAbsoluteAccuracy) {
/*  86 */     this((RandomGenerator)new Well19937c(), mu, omega, inverseAbsoluteAccuracy);
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
/*     */   public NakagamiDistribution(RandomGenerator rng, double mu, double omega, double inverseAbsoluteAccuracy) {
/* 101 */     super(rng);
/*     */     
/* 103 */     if (mu < 0.5D) {
/* 104 */       throw new NumberIsTooSmallException(Double.valueOf(mu), Double.valueOf(0.5D), true);
/*     */     }
/* 106 */     if (omega <= 0.0D) {
/* 107 */       throw new NotStrictlyPositiveException(LocalizedFormats.NOT_POSITIVE_SCALE, Double.valueOf(omega));
/*     */     }
/*     */     
/* 110 */     this.mu = mu;
/* 111 */     this.omega = omega;
/* 112 */     this.inverseAbsoluteAccuracy = inverseAbsoluteAccuracy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getShape() {
/* 121 */     return this.mu;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getScale() {
/* 130 */     return this.omega;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected double getSolverAbsoluteAccuracy() {
/* 136 */     return this.inverseAbsoluteAccuracy;
/*     */   }
/*     */ 
/*     */   
/*     */   public double density(double x) {
/* 141 */     if (x <= 0.0D) {
/* 142 */       return 0.0D;
/*     */     }
/* 144 */     return 2.0D * FastMath.pow(this.mu, this.mu) / Gamma.gamma(this.mu) * FastMath.pow(this.omega, this.mu) * FastMath.pow(x, 2.0D * this.mu - 1.0D) * FastMath.exp(-this.mu * x * x / this.omega);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double cumulativeProbability(double x) {
/* 150 */     return Gamma.regularizedGammaP(this.mu, this.mu * x * x / this.omega);
/*     */   }
/*     */ 
/*     */   
/*     */   public double getNumericalMean() {
/* 155 */     return Gamma.gamma(this.mu + 0.5D) / Gamma.gamma(this.mu) * FastMath.sqrt(this.omega / this.mu);
/*     */   }
/*     */ 
/*     */   
/*     */   public double getNumericalVariance() {
/* 160 */     double v = Gamma.gamma(this.mu + 0.5D) / Gamma.gamma(this.mu);
/* 161 */     return this.omega * (1.0D - 1.0D / this.mu * v * v);
/*     */   }
/*     */ 
/*     */   
/*     */   public double getSupportLowerBound() {
/* 166 */     return 0.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getSupportUpperBound() {
/* 171 */     return Double.POSITIVE_INFINITY;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSupportLowerBoundInclusive() {
/* 176 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSupportUpperBoundInclusive() {
/* 181 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSupportConnected() {
/* 186 */     return true;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/distribution/NakagamiDistribution.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */