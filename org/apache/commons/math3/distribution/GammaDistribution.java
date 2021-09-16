/*     */ package org.apache.commons.math3.distribution;
/*     */ 
/*     */ import org.apache.commons.math3.exception.NotStrictlyPositiveException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GammaDistribution
/*     */   extends AbstractRealDistribution
/*     */ {
/*     */   public static final double DEFAULT_INVERSE_ABSOLUTE_ACCURACY = 1.0E-9D;
/*     */   private static final long serialVersionUID = 20120524L;
/*     */   private final double shape;
/*     */   private final double scale;
/*     */   private final double shiftedShape;
/*     */   private final double densityPrefactor1;
/*     */   private final double logDensityPrefactor1;
/*     */   private final double densityPrefactor2;
/*     */   private final double logDensityPrefactor2;
/*     */   private final double minY;
/*     */   private final double maxLogY;
/*     */   private final double solverAbsoluteAccuracy;
/*     */   
/*     */   public GammaDistribution(double shape, double scale) throws NotStrictlyPositiveException {
/* 117 */     this(shape, scale, 1.0E-9D);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public GammaDistribution(double shape, double scale, double inverseCumAccuracy) throws NotStrictlyPositiveException {
/* 142 */     this((RandomGenerator)new Well19937c(), shape, scale, inverseCumAccuracy);
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
/*     */   public GammaDistribution(RandomGenerator rng, double shape, double scale) throws NotStrictlyPositiveException {
/* 157 */     this(rng, shape, scale, 1.0E-9D);
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
/*     */   public GammaDistribution(RandomGenerator rng, double shape, double scale, double inverseCumAccuracy) throws NotStrictlyPositiveException {
/* 178 */     super(rng);
/*     */     
/* 180 */     if (shape <= 0.0D) {
/* 181 */       throw new NotStrictlyPositiveException(LocalizedFormats.SHAPE, Double.valueOf(shape));
/*     */     }
/* 183 */     if (scale <= 0.0D) {
/* 184 */       throw new NotStrictlyPositiveException(LocalizedFormats.SCALE, Double.valueOf(scale));
/*     */     }
/*     */     
/* 187 */     this.shape = shape;
/* 188 */     this.scale = scale;
/* 189 */     this.solverAbsoluteAccuracy = inverseCumAccuracy;
/* 190 */     this.shiftedShape = shape + 4.7421875D + 0.5D;
/* 191 */     double aux = Math.E / 6.283185307179586D * this.shiftedShape;
/* 192 */     this.densityPrefactor2 = shape * FastMath.sqrt(aux) / Gamma.lanczos(shape);
/* 193 */     this.logDensityPrefactor2 = FastMath.log(shape) + 0.5D * FastMath.log(aux) - FastMath.log(Gamma.lanczos(shape));
/*     */     
/* 195 */     this.densityPrefactor1 = this.densityPrefactor2 / scale * FastMath.pow(this.shiftedShape, -shape) * FastMath.exp(shape + 4.7421875D);
/*     */ 
/*     */     
/* 198 */     this.logDensityPrefactor1 = this.logDensityPrefactor2 - FastMath.log(scale) - FastMath.log(this.shiftedShape) * shape + shape + 4.7421875D;
/*     */ 
/*     */     
/* 201 */     this.minY = shape + 4.7421875D - FastMath.log(Double.MAX_VALUE);
/* 202 */     this.maxLogY = FastMath.log(Double.MAX_VALUE) / (shape - 1.0D);
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
/*     */   public double getAlpha() {
/* 214 */     return this.shape;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getShape() {
/* 224 */     return this.shape;
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
/*     */   public double getBeta() {
/* 236 */     return this.scale;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getScale() {
/* 246 */     return this.scale;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 289 */     if (x < 0.0D) {
/* 290 */       return 0.0D;
/*     */     }
/* 292 */     double y = x / this.scale;
/* 293 */     if (y <= this.minY || FastMath.log(y) >= this.maxLogY) {
/*     */ 
/*     */ 
/*     */       
/* 297 */       double aux1 = (y - this.shiftedShape) / this.shiftedShape;
/* 298 */       double aux2 = this.shape * (FastMath.log1p(aux1) - aux1);
/* 299 */       double aux3 = -y * 5.2421875D / this.shiftedShape + 4.7421875D + aux2;
/*     */       
/* 301 */       return this.densityPrefactor2 / x * FastMath.exp(aux3);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 306 */     return this.densityPrefactor1 * FastMath.exp(-y) * FastMath.pow(y, this.shape - 1.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double logDensity(double x) {
/* 315 */     if (x < 0.0D) {
/* 316 */       return Double.NEGATIVE_INFINITY;
/*     */     }
/* 318 */     double y = x / this.scale;
/* 319 */     if (y <= this.minY || FastMath.log(y) >= this.maxLogY) {
/*     */ 
/*     */ 
/*     */       
/* 323 */       double aux1 = (y - this.shiftedShape) / this.shiftedShape;
/* 324 */       double aux2 = this.shape * (FastMath.log1p(aux1) - aux1);
/* 325 */       double aux3 = -y * 5.2421875D / this.shiftedShape + 4.7421875D + aux2;
/*     */       
/* 327 */       return this.logDensityPrefactor2 - FastMath.log(x) + aux3;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 332 */     return this.logDensityPrefactor1 - y + FastMath.log(y) * (this.shape - 1.0D);
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
/*     */     double ret;
/* 352 */     if (x <= 0.0D) {
/* 353 */       ret = 0.0D;
/*     */     } else {
/* 355 */       ret = Gamma.regularizedGammaP(this.shape, x / this.scale);
/*     */     } 
/*     */     
/* 358 */     return ret;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected double getSolverAbsoluteAccuracy() {
/* 364 */     return this.solverAbsoluteAccuracy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getNumericalMean() {
/* 374 */     return this.shape * this.scale;
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
/*     */   public double getNumericalVariance() {
/* 386 */     return this.shape * this.scale * this.scale;
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
/* 397 */     return 0.0D;
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
/* 409 */     return Double.POSITIVE_INFINITY;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSupportLowerBoundInclusive() {
/* 414 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSupportUpperBoundInclusive() {
/* 419 */     return false;
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
/* 430 */     return true;
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
/*     */   public double sample() {
/* 450 */     if (this.shape < 1.0D) {
/*     */       double x;
/*     */ 
/*     */       
/*     */       while (true) {
/* 455 */         double u = this.random.nextDouble();
/* 456 */         double bGS = 1.0D + this.shape / Math.E;
/* 457 */         double p = bGS * u;
/*     */         
/* 459 */         if (p <= 1.0D) {
/*     */ 
/*     */           
/* 462 */           double d1 = FastMath.pow(p, 1.0D / this.shape);
/* 463 */           double d2 = this.random.nextDouble();
/*     */           
/* 465 */           if (d2 > FastMath.exp(-d1)) {
/*     */             continue;
/*     */           }
/*     */           
/* 469 */           return this.scale * d1;
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 474 */         x = -1.0D * FastMath.log((bGS - p) / this.shape);
/* 475 */         double u2 = this.random.nextDouble();
/*     */         
/* 477 */         if (u2 > FastMath.pow(x, this.shape - 1.0D))
/*     */           continue; 
/*     */         break;
/*     */       } 
/* 481 */       return this.scale * x;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 489 */     double d = this.shape - 0.3333333333333333D;
/* 490 */     double c = 1.0D / 3.0D * FastMath.sqrt(d);
/*     */     
/*     */     while (true) {
/* 493 */       double x = this.random.nextGaussian();
/* 494 */       double v = (1.0D + c * x) * (1.0D + c * x) * (1.0D + c * x);
/*     */       
/* 496 */       if (v <= 0.0D) {
/*     */         continue;
/*     */       }
/*     */       
/* 500 */       double x2 = x * x;
/* 501 */       double u = this.random.nextDouble();
/*     */ 
/*     */       
/* 504 */       if (u < 1.0D - 0.0331D * x2 * x2) {
/* 505 */         return this.scale * d * v;
/*     */       }
/*     */       
/* 508 */       if (FastMath.log(u) < 0.5D * x2 + d * (1.0D - v + FastMath.log(v)))
/* 509 */         return this.scale * d * v; 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/distribution/GammaDistribution.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */