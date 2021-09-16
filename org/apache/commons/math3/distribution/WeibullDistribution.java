/*     */ package org.apache.commons.math3.distribution;
/*     */ 
/*     */ import org.apache.commons.math3.exception.NotStrictlyPositiveException;
/*     */ import org.apache.commons.math3.exception.OutOfRangeException;
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
/*     */ public class WeibullDistribution
/*     */   extends AbstractRealDistribution
/*     */ {
/*     */   public static final double DEFAULT_INVERSE_ABSOLUTE_ACCURACY = 1.0E-9D;
/*     */   private static final long serialVersionUID = 8589540077390120676L;
/*     */   private final double shape;
/*     */   private final double scale;
/*     */   private final double solverAbsoluteAccuracy;
/*  53 */   private double numericalMean = Double.NaN;
/*     */   
/*     */   private boolean numericalMeanIsCalculated = false;
/*     */   
/*  57 */   private double numericalVariance = Double.NaN;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean numericalVarianceIsCalculated = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WeibullDistribution(double alpha, double beta) throws NotStrictlyPositiveException {
/*  79 */     this(alpha, beta, 1.0E-9D);
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
/*     */   public WeibullDistribution(double alpha, double beta, double inverseCumAccuracy) {
/* 104 */     this((RandomGenerator)new Well19937c(), alpha, beta, inverseCumAccuracy);
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
/*     */   public WeibullDistribution(RandomGenerator rng, double alpha, double beta) throws NotStrictlyPositiveException {
/* 118 */     this(rng, alpha, beta, 1.0E-9D);
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
/*     */   public WeibullDistribution(RandomGenerator rng, double alpha, double beta, double inverseCumAccuracy) throws NotStrictlyPositiveException {
/* 138 */     super(rng);
/*     */     
/* 140 */     if (alpha <= 0.0D) {
/* 141 */       throw new NotStrictlyPositiveException(LocalizedFormats.SHAPE, Double.valueOf(alpha));
/*     */     }
/*     */     
/* 144 */     if (beta <= 0.0D) {
/* 145 */       throw new NotStrictlyPositiveException(LocalizedFormats.SCALE, Double.valueOf(beta));
/*     */     }
/*     */     
/* 148 */     this.scale = beta;
/* 149 */     this.shape = alpha;
/* 150 */     this.solverAbsoluteAccuracy = inverseCumAccuracy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getShape() {
/* 159 */     return this.shape;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getScale() {
/* 168 */     return this.scale;
/*     */   }
/*     */ 
/*     */   
/*     */   public double density(double x) {
/* 173 */     if (x < 0.0D) {
/* 174 */       return 0.0D;
/*     */     }
/*     */     
/* 177 */     double xscale = x / this.scale;
/* 178 */     double xscalepow = FastMath.pow(xscale, this.shape - 1.0D);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 185 */     double xscalepowshape = xscalepow * xscale;
/*     */     
/* 187 */     return this.shape / this.scale * xscalepow * FastMath.exp(-xscalepowshape);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double logDensity(double x) {
/* 193 */     if (x < 0.0D) {
/* 194 */       return Double.NEGATIVE_INFINITY;
/*     */     }
/*     */     
/* 197 */     double xscale = x / this.scale;
/* 198 */     double logxscalepow = FastMath.log(xscale) * (this.shape - 1.0D);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 205 */     double xscalepowshape = FastMath.exp(logxscalepow) * xscale;
/*     */     
/* 207 */     return FastMath.log(this.shape / this.scale) + logxscalepow - xscalepowshape;
/*     */   }
/*     */ 
/*     */   
/*     */   public double cumulativeProbability(double x) {
/*     */     double ret;
/* 213 */     if (x <= 0.0D) {
/* 214 */       ret = 0.0D;
/*     */     } else {
/* 216 */       ret = 1.0D - FastMath.exp(-FastMath.pow(x / this.scale, this.shape));
/*     */     } 
/* 218 */     return ret;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double inverseCumulativeProbability(double p) {
/*     */     double ret;
/* 230 */     if (p < 0.0D || p > 1.0D)
/* 231 */       throw new OutOfRangeException(Double.valueOf(p), Double.valueOf(0.0D), Double.valueOf(1.0D)); 
/* 232 */     if (p == 0.0D) {
/* 233 */       ret = 0.0D;
/* 234 */     } else if (p == 1.0D) {
/* 235 */       ret = Double.POSITIVE_INFINITY;
/*     */     } else {
/* 237 */       ret = this.scale * FastMath.pow(-FastMath.log1p(-p), 1.0D / this.shape);
/*     */     } 
/* 239 */     return ret;
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
/*     */   protected double getSolverAbsoluteAccuracy() {
/* 251 */     return this.solverAbsoluteAccuracy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getNumericalMean() {
/* 261 */     if (!this.numericalMeanIsCalculated) {
/* 262 */       this.numericalMean = calculateNumericalMean();
/* 263 */       this.numericalMeanIsCalculated = true;
/*     */     } 
/* 265 */     return this.numericalMean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected double calculateNumericalMean() {
/* 274 */     double sh = getShape();
/* 275 */     double sc = getScale();
/*     */     
/* 277 */     return sc * FastMath.exp(Gamma.logGamma(1.0D + 1.0D / sh));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getNumericalVariance() {
/* 287 */     if (!this.numericalVarianceIsCalculated) {
/* 288 */       this.numericalVariance = calculateNumericalVariance();
/* 289 */       this.numericalVarianceIsCalculated = true;
/*     */     } 
/* 291 */     return this.numericalVariance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected double calculateNumericalVariance() {
/* 300 */     double sh = getShape();
/* 301 */     double sc = getScale();
/* 302 */     double mn = getNumericalMean();
/*     */     
/* 304 */     return sc * sc * FastMath.exp(Gamma.logGamma(1.0D + 2.0D / sh)) - mn * mn;
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
/* 316 */     return 0.0D;
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
/* 329 */     return Double.POSITIVE_INFINITY;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSupportLowerBoundInclusive() {
/* 334 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSupportUpperBoundInclusive() {
/* 339 */     return false;
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
/* 350 */     return true;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/distribution/WeibullDistribution.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */