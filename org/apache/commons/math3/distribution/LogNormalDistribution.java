/*     */ package org.apache.commons.math3.distribution;
/*     */ 
/*     */ import org.apache.commons.math3.exception.NotStrictlyPositiveException;
/*     */ import org.apache.commons.math3.exception.NumberIsTooLargeException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LogNormalDistribution
/*     */   extends AbstractRealDistribution
/*     */ {
/*     */   public static final double DEFAULT_INVERSE_ABSOLUTE_ACCURACY = 1.0E-9D;
/*     */   private static final long serialVersionUID = 20120112L;
/*  63 */   private static final double SQRT2PI = FastMath.sqrt(6.283185307179586D);
/*     */ 
/*     */   
/*  66 */   private static final double SQRT2 = FastMath.sqrt(2.0D);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final double scale;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final double shape;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final double logShapePlusHalfLog2Pi;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final double solverAbsoluteAccuracy;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LogNormalDistribution() {
/*  94 */     this(0.0D, 1.0D);
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
/*     */   public LogNormalDistribution(double scale, double shape) throws NotStrictlyPositiveException {
/* 113 */     this(scale, shape, 1.0E-9D);
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
/*     */   public LogNormalDistribution(double scale, double shape, double inverseCumAccuracy) throws NotStrictlyPositiveException {
/* 134 */     this((RandomGenerator)new Well19937c(), scale, shape, inverseCumAccuracy);
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
/*     */   public LogNormalDistribution(RandomGenerator rng, double scale, double shape) throws NotStrictlyPositiveException {
/* 148 */     this(rng, scale, shape, 1.0E-9D);
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
/*     */   public LogNormalDistribution(RandomGenerator rng, double scale, double shape, double inverseCumAccuracy) throws NotStrictlyPositiveException {
/* 166 */     super(rng);
/*     */     
/* 168 */     if (shape <= 0.0D) {
/* 169 */       throw new NotStrictlyPositiveException(LocalizedFormats.SHAPE, Double.valueOf(shape));
/*     */     }
/*     */     
/* 172 */     this.scale = scale;
/* 173 */     this.shape = shape;
/* 174 */     this.logShapePlusHalfLog2Pi = FastMath.log(shape) + 0.5D * FastMath.log(6.283185307179586D);
/* 175 */     this.solverAbsoluteAccuracy = inverseCumAccuracy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getScale() {
/* 184 */     return this.scale;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getShape() {
/* 193 */     return this.shape;
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
/*     */   public double density(double x) {
/* 208 */     if (x <= 0.0D) {
/* 209 */       return 0.0D;
/*     */     }
/* 211 */     double x0 = FastMath.log(x) - this.scale;
/* 212 */     double x1 = x0 / this.shape;
/* 213 */     return FastMath.exp(-0.5D * x1 * x1) / this.shape * SQRT2PI * x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double logDensity(double x) {
/* 222 */     if (x <= 0.0D) {
/* 223 */       return Double.NEGATIVE_INFINITY;
/*     */     }
/* 225 */     double logX = FastMath.log(x);
/* 226 */     double x0 = logX - this.scale;
/* 227 */     double x1 = x0 / this.shape;
/* 228 */     return -0.5D * x1 * x1 - this.logShapePlusHalfLog2Pi + logX;
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
/*     */   public double cumulativeProbability(double x) {
/* 247 */     if (x <= 0.0D) {
/* 248 */       return 0.0D;
/*     */     }
/* 250 */     double dev = FastMath.log(x) - this.scale;
/* 251 */     if (FastMath.abs(dev) > 40.0D * this.shape) {
/* 252 */       return (dev < 0.0D) ? 0.0D : 1.0D;
/*     */     }
/* 254 */     return 0.5D + 0.5D * Erf.erf(dev / this.shape * SQRT2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public double cumulativeProbability(double x0, double x1) throws NumberIsTooLargeException {
/* 265 */     return probability(x0, x1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double probability(double x0, double x1) throws NumberIsTooLargeException {
/* 273 */     if (x0 > x1) {
/* 274 */       throw new NumberIsTooLargeException(LocalizedFormats.LOWER_ENDPOINT_ABOVE_UPPER_ENDPOINT, Double.valueOf(x0), Double.valueOf(x1), true);
/*     */     }
/*     */     
/* 277 */     if (x0 <= 0.0D || x1 <= 0.0D) {
/* 278 */       return super.probability(x0, x1);
/*     */     }
/* 280 */     double denom = this.shape * SQRT2;
/* 281 */     double v0 = (FastMath.log(x0) - this.scale) / denom;
/* 282 */     double v1 = (FastMath.log(x1) - this.scale) / denom;
/* 283 */     return 0.5D * Erf.erf(v0, v1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected double getSolverAbsoluteAccuracy() {
/* 289 */     return this.solverAbsoluteAccuracy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getNumericalMean() {
/* 299 */     double s = this.shape;
/* 300 */     return FastMath.exp(this.scale + s * s / 2.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getNumericalVariance() {
/* 310 */     double s = this.shape;
/* 311 */     double ss = s * s;
/* 312 */     return FastMath.expm1(ss) * FastMath.exp(2.0D * this.scale + ss);
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
/* 323 */     return 0.0D;
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
/*     */   public double getSupportUpperBound() {
/* 336 */     return Double.POSITIVE_INFINITY;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSupportLowerBoundInclusive() {
/* 341 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSupportUpperBoundInclusive() {
/* 346 */     return false;
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
/* 357 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double sample() {
/* 363 */     double n = this.random.nextGaussian();
/* 364 */     return FastMath.exp(this.scale + this.shape * n);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/distribution/LogNormalDistribution.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */