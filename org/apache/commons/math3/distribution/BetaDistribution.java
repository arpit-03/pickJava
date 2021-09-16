/*     */ package org.apache.commons.math3.distribution;
/*     */ 
/*     */ import org.apache.commons.math3.exception.NumberIsTooSmallException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.random.RandomGenerator;
/*     */ import org.apache.commons.math3.random.Well19937c;
/*     */ import org.apache.commons.math3.special.Beta;
/*     */ import org.apache.commons.math3.special.Gamma;
/*     */ import org.apache.commons.math3.util.FastMath;
/*     */ import org.apache.commons.math3.util.Precision;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BetaDistribution
/*     */   extends AbstractRealDistribution
/*     */ {
/*     */   public static final double DEFAULT_INVERSE_ABSOLUTE_ACCURACY = 1.0E-9D;
/*     */   private static final long serialVersionUID = -1221965979403477668L;
/*     */   private final double alpha;
/*     */   private final double beta;
/*     */   private double z;
/*     */   private final double solverAbsoluteAccuracy;
/*     */   
/*     */   public BetaDistribution(double alpha, double beta) {
/*  67 */     this(alpha, beta, 1.0E-9D);
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
/*     */   public BetaDistribution(double alpha, double beta, double inverseCumAccuracy) {
/*  88 */     this((RandomGenerator)new Well19937c(), alpha, beta, inverseCumAccuracy);
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
/*     */   public BetaDistribution(RandomGenerator rng, double alpha, double beta) {
/* 100 */     this(rng, alpha, beta, 1.0E-9D);
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
/*     */   public BetaDistribution(RandomGenerator rng, double alpha, double beta, double inverseCumAccuracy) {
/* 118 */     super(rng);
/*     */     
/* 120 */     this.alpha = alpha;
/* 121 */     this.beta = beta;
/* 122 */     this.z = Double.NaN;
/* 123 */     this.solverAbsoluteAccuracy = inverseCumAccuracy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getAlpha() {
/* 132 */     return this.alpha;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getBeta() {
/* 141 */     return this.beta;
/*     */   }
/*     */ 
/*     */   
/*     */   private void recomputeZ() {
/* 146 */     if (Double.isNaN(this.z)) {
/* 147 */       this.z = Gamma.logGamma(this.alpha) + Gamma.logGamma(this.beta) - Gamma.logGamma(this.alpha + this.beta);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public double density(double x) {
/* 153 */     double logDensity = logDensity(x);
/* 154 */     return (logDensity == Double.NEGATIVE_INFINITY) ? 0.0D : FastMath.exp(logDensity);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double logDensity(double x) {
/* 160 */     recomputeZ();
/* 161 */     if (x < 0.0D || x > 1.0D)
/* 162 */       return Double.NEGATIVE_INFINITY; 
/* 163 */     if (x == 0.0D) {
/* 164 */       if (this.alpha < 1.0D) {
/* 165 */         throw new NumberIsTooSmallException(LocalizedFormats.CANNOT_COMPUTE_BETA_DENSITY_AT_0_FOR_SOME_ALPHA, Double.valueOf(this.alpha), Integer.valueOf(1), false);
/*     */       }
/* 167 */       return Double.NEGATIVE_INFINITY;
/* 168 */     }  if (x == 1.0D) {
/* 169 */       if (this.beta < 1.0D) {
/* 170 */         throw new NumberIsTooSmallException(LocalizedFormats.CANNOT_COMPUTE_BETA_DENSITY_AT_1_FOR_SOME_BETA, Double.valueOf(this.beta), Integer.valueOf(1), false);
/*     */       }
/* 172 */       return Double.NEGATIVE_INFINITY;
/*     */     } 
/* 174 */     double logX = FastMath.log(x);
/* 175 */     double log1mX = FastMath.log1p(-x);
/* 176 */     return (this.alpha - 1.0D) * logX + (this.beta - 1.0D) * log1mX - this.z;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double cumulativeProbability(double x) {
/* 182 */     if (x <= 0.0D)
/* 183 */       return 0.0D; 
/* 184 */     if (x >= 1.0D) {
/* 185 */       return 1.0D;
/*     */     }
/* 187 */     return Beta.regularizedBeta(x, this.alpha, this.beta);
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
/*     */   protected double getSolverAbsoluteAccuracy() {
/* 200 */     return this.solverAbsoluteAccuracy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getNumericalMean() {
/* 210 */     double a = getAlpha();
/* 211 */     return a / (a + getBeta());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getNumericalVariance() {
/* 222 */     double a = getAlpha();
/* 223 */     double b = getBeta();
/* 224 */     double alphabetasum = a + b;
/* 225 */     return a * b / alphabetasum * alphabetasum * (alphabetasum + 1.0D);
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
/* 236 */     return 0.0D;
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
/* 247 */     return 1.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSupportLowerBoundInclusive() {
/* 252 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSupportUpperBoundInclusive() {
/* 257 */     return false;
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
/* 268 */     return true;
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
/*     */   public double sample() {
/* 283 */     return ChengBetaSampler.sample(this.random, this.alpha, this.beta);
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
/*     */   private static final class ChengBetaSampler
/*     */   {
/*     */     static double sample(RandomGenerator random, double alpha, double beta) {
/* 303 */       double a = FastMath.min(alpha, beta);
/* 304 */       double b = FastMath.max(alpha, beta);
/*     */       
/* 306 */       if (a > 1.0D) {
/* 307 */         return algorithmBB(random, alpha, a, b);
/*     */       }
/* 309 */       return algorithmBC(random, alpha, b, a);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static double algorithmBB(RandomGenerator random, double a0, double a, double b) {
/* 325 */       double r, t, alpha = a + b;
/* 326 */       double beta = FastMath.sqrt((alpha - 2.0D) / (2.0D * a * b - alpha));
/* 327 */       double gamma = a + 1.0D / beta;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       do {
/* 333 */         double u1 = random.nextDouble();
/* 334 */         double u2 = random.nextDouble();
/* 335 */         double v = beta * (FastMath.log(u1) - FastMath.log1p(-u1));
/* 336 */         w = a * FastMath.exp(v);
/* 337 */         double z = u1 * u1 * u2;
/* 338 */         r = gamma * v - 1.3862944D;
/* 339 */         double s = a + r - w;
/* 340 */         if (s + 2.609438D >= 5.0D * z) {
/*     */           break;
/*     */         }
/*     */         
/* 344 */         t = FastMath.log(z);
/* 345 */         if (s >= t) {
/*     */           break;
/*     */         }
/* 348 */       } while (r + alpha * (FastMath.log(alpha) - FastMath.log(b + w)) < t);
/*     */       
/* 350 */       double w = FastMath.min(w, Double.MAX_VALUE);
/* 351 */       return Precision.equals(a, a0) ? (w / (b + w)) : (b / (b + w));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static double algorithmBC(RandomGenerator random, double a0, double a, double b) {
/* 366 */       double alpha = a + b;
/* 367 */       double beta = 1.0D / b;
/* 368 */       double delta = 1.0D + a - b;
/* 369 */       double k1 = delta * (0.0138889D + 0.0416667D * b) / (a * beta - 0.777778D);
/* 370 */       double k2 = 0.25D + (0.5D + 0.25D / delta) * b;
/*     */ 
/*     */       
/*     */       while (true) {
/* 374 */         double u1 = random.nextDouble();
/* 375 */         double u2 = random.nextDouble();
/* 376 */         double y = u1 * u2;
/* 377 */         double z = u1 * y;
/* 378 */         if (u1 < 0.5D) {
/* 379 */           if (0.25D * u2 + z - y >= k1) {
/*     */             continue;
/*     */           }
/*     */         } else {
/* 383 */           if (z <= 0.25D) {
/* 384 */             double d2 = beta * (FastMath.log(u1) - FastMath.log1p(-u1));
/* 385 */             double d1 = a * FastMath.exp(d2);
/*     */             
/*     */             break;
/*     */           } 
/* 389 */           if (z >= k2) {
/*     */             continue;
/*     */           }
/*     */         } 
/*     */         
/* 394 */         double v = beta * (FastMath.log(u1) - FastMath.log1p(-u1));
/* 395 */         w = a * FastMath.exp(v);
/* 396 */         if (alpha * (FastMath.log(alpha) - FastMath.log(b + w) + v) - 1.3862944D >= FastMath.log(z)) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */       
/* 401 */       double w = FastMath.min(w, Double.MAX_VALUE);
/* 402 */       return Precision.equals(a, a0) ? (w / (b + w)) : (b / (b + w));
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/distribution/BetaDistribution.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */