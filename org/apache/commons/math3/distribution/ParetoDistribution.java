/*     */ package org.apache.commons.math3.distribution;
/*     */ 
/*     */ import org.apache.commons.math3.exception.NotStrictlyPositiveException;
/*     */ import org.apache.commons.math3.exception.NumberIsTooLargeException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ParetoDistribution
/*     */   extends AbstractRealDistribution
/*     */ {
/*     */   public static final double DEFAULT_INVERSE_ABSOLUTE_ACCURACY = 1.0E-9D;
/*     */   private static final long serialVersionUID = 20130424L;
/*     */   private final double scale;
/*     */   private final double shape;
/*     */   private final double solverAbsoluteAccuracy;
/*     */   
/*     */   public ParetoDistribution() {
/*  70 */     this(1.0D, 1.0D);
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
/*     */   public ParetoDistribution(double scale, double shape) throws NotStrictlyPositiveException {
/*  89 */     this(scale, shape, 1.0E-9D);
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
/*     */   public ParetoDistribution(double scale, double shape, double inverseCumAccuracy) throws NotStrictlyPositiveException {
/* 110 */     this((RandomGenerator)new Well19937c(), scale, shape, inverseCumAccuracy);
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
/*     */   public ParetoDistribution(RandomGenerator rng, double scale, double shape) throws NotStrictlyPositiveException {
/* 123 */     this(rng, scale, shape, 1.0E-9D);
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
/*     */   public ParetoDistribution(RandomGenerator rng, double scale, double shape, double inverseCumAccuracy) throws NotStrictlyPositiveException {
/* 140 */     super(rng);
/*     */     
/* 142 */     if (scale <= 0.0D) {
/* 143 */       throw new NotStrictlyPositiveException(LocalizedFormats.SCALE, Double.valueOf(scale));
/*     */     }
/*     */     
/* 146 */     if (shape <= 0.0D) {
/* 147 */       throw new NotStrictlyPositiveException(LocalizedFormats.SHAPE, Double.valueOf(shape));
/*     */     }
/*     */     
/* 150 */     this.scale = scale;
/* 151 */     this.shape = shape;
/* 152 */     this.solverAbsoluteAccuracy = inverseCumAccuracy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getScale() {
/* 161 */     return this.scale;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getShape() {
/* 170 */     return this.shape;
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
/*     */   public double density(double x) {
/* 184 */     if (x < this.scale) {
/* 185 */       return 0.0D;
/*     */     }
/* 187 */     return FastMath.pow(this.scale, this.shape) / FastMath.pow(x, this.shape + 1.0D) * this.shape;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double logDensity(double x) {
/* 196 */     if (x < this.scale) {
/* 197 */       return Double.NEGATIVE_INFINITY;
/*     */     }
/* 199 */     return FastMath.log(this.scale) * this.shape - FastMath.log(x) * (this.shape + 1.0D) + FastMath.log(this.shape);
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
/*     */   public double cumulativeProbability(double x) {
/* 212 */     if (x <= this.scale) {
/* 213 */       return 0.0D;
/*     */     }
/* 215 */     return 1.0D - FastMath.pow(this.scale / x, this.shape);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public double cumulativeProbability(double x0, double x1) throws NumberIsTooLargeException {
/* 227 */     return probability(x0, x1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected double getSolverAbsoluteAccuracy() {
/* 233 */     return this.solverAbsoluteAccuracy;
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
/*     */   public double getNumericalMean() {
/* 246 */     if (this.shape <= 1.0D) {
/* 247 */       return Double.POSITIVE_INFINITY;
/*     */     }
/* 249 */     return this.shape * this.scale / (this.shape - 1.0D);
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
/*     */   public double getNumericalVariance() {
/* 262 */     if (this.shape <= 2.0D) {
/* 263 */       return Double.POSITIVE_INFINITY;
/*     */     }
/* 265 */     double s = this.shape - 1.0D;
/* 266 */     return this.scale * this.scale * this.shape / s * s / (this.shape - 2.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getSupportLowerBound() {
/* 277 */     return this.scale;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getSupportUpperBound() {
/* 288 */     return Double.POSITIVE_INFINITY;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSupportLowerBoundInclusive() {
/* 293 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSupportUpperBoundInclusive() {
/* 298 */     return false;
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
/* 309 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double sample() {
/* 315 */     double n = this.random.nextDouble();
/* 316 */     return this.scale / FastMath.pow(n, 1.0D / this.shape);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/distribution/ParetoDistribution.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */