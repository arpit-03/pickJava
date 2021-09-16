/*     */ package org.apache.commons.math3.distribution;
/*     */ 
/*     */ import org.apache.commons.math3.random.RandomGenerator;
/*     */ import org.apache.commons.math3.random.Well19937c;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChiSquaredDistribution
/*     */   extends AbstractRealDistribution
/*     */ {
/*     */   public static final double DEFAULT_INVERSE_ABSOLUTE_ACCURACY = 1.0E-9D;
/*     */   private static final long serialVersionUID = -8352658048349159782L;
/*     */   private final GammaDistribution gamma;
/*     */   private final double solverAbsoluteAccuracy;
/*     */   
/*     */   public ChiSquaredDistribution(double degreesOfFreedom) {
/*  47 */     this(degreesOfFreedom, 1.0E-9D);
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
/*     */   public ChiSquaredDistribution(double degreesOfFreedom, double inverseCumAccuracy) {
/*  69 */     this((RandomGenerator)new Well19937c(), degreesOfFreedom, inverseCumAccuracy);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChiSquaredDistribution(RandomGenerator rng, double degreesOfFreedom) {
/*  80 */     this(rng, degreesOfFreedom, 1.0E-9D);
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
/*     */   public ChiSquaredDistribution(RandomGenerator rng, double degreesOfFreedom, double inverseCumAccuracy) {
/*  97 */     super(rng);
/*     */     
/*  99 */     this.gamma = new GammaDistribution(degreesOfFreedom / 2.0D, 2.0D);
/* 100 */     this.solverAbsoluteAccuracy = inverseCumAccuracy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getDegreesOfFreedom() {
/* 109 */     return this.gamma.getShape() * 2.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   public double density(double x) {
/* 114 */     return this.gamma.density(x);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double logDensity(double x) {
/* 120 */     return this.gamma.logDensity(x);
/*     */   }
/*     */ 
/*     */   
/*     */   public double cumulativeProbability(double x) {
/* 125 */     return this.gamma.cumulativeProbability(x);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected double getSolverAbsoluteAccuracy() {
/* 131 */     return this.solverAbsoluteAccuracy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getNumericalMean() {
/* 140 */     return getDegreesOfFreedom();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getNumericalVariance() {
/* 149 */     return 2.0D * getDegreesOfFreedom();
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
/*     */   public double getSupportLowerBound() {
/* 161 */     return 0.0D;
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
/*     */   public double getSupportUpperBound() {
/* 173 */     return Double.POSITIVE_INFINITY;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSupportLowerBoundInclusive() {
/* 178 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSupportUpperBoundInclusive() {
/* 183 */     return false;
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
/* 194 */     return true;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/distribution/ChiSquaredDistribution.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */