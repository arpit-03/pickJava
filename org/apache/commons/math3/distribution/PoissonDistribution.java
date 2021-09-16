/*     */ package org.apache.commons.math3.distribution;
/*     */ 
/*     */ import org.apache.commons.math3.exception.NotStrictlyPositiveException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.random.RandomGenerator;
/*     */ import org.apache.commons.math3.random.Well19937c;
/*     */ import org.apache.commons.math3.special.Gamma;
/*     */ import org.apache.commons.math3.util.CombinatoricsUtils;
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
/*     */ public class PoissonDistribution
/*     */   extends AbstractIntegerDistribution
/*     */ {
/*     */   public static final int DEFAULT_MAX_ITERATIONS = 10000000;
/*     */   public static final double DEFAULT_EPSILON = 1.0E-12D;
/*     */   private static final long serialVersionUID = -3349935121172596109L;
/*     */   private final NormalDistribution normal;
/*     */   private final ExponentialDistribution exponential;
/*     */   private final double mean;
/*     */   private final int maxIterations;
/*     */   private final double epsilon;
/*     */   
/*     */   public PoissonDistribution(double p) throws NotStrictlyPositiveException {
/*  80 */     this(p, 1.0E-12D, 10000000);
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
/*     */   public PoissonDistribution(double p, double epsilon, int maxIterations) throws NotStrictlyPositiveException {
/* 103 */     this((RandomGenerator)new Well19937c(), p, epsilon, maxIterations);
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
/*     */   public PoissonDistribution(RandomGenerator rng, double p, double epsilon, int maxIterations) throws NotStrictlyPositiveException {
/* 123 */     super(rng);
/*     */     
/* 125 */     if (p <= 0.0D) {
/* 126 */       throw new NotStrictlyPositiveException(LocalizedFormats.MEAN, Double.valueOf(p));
/*     */     }
/* 128 */     this.mean = p;
/* 129 */     this.epsilon = epsilon;
/* 130 */     this.maxIterations = maxIterations;
/*     */ 
/*     */     
/* 133 */     this.normal = new NormalDistribution(rng, p, FastMath.sqrt(p), 1.0E-9D);
/*     */     
/* 135 */     this.exponential = new ExponentialDistribution(rng, 1.0D, 1.0E-9D);
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
/*     */   public PoissonDistribution(double p, double epsilon) throws NotStrictlyPositiveException {
/* 150 */     this(p, epsilon, 10000000);
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
/*     */   public PoissonDistribution(double p, int maxIterations) {
/* 163 */     this(p, 1.0E-12D, maxIterations);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getMean() {
/* 172 */     return this.mean;
/*     */   }
/*     */ 
/*     */   
/*     */   public double probability(int x) {
/* 177 */     double logProbability = logProbability(x);
/* 178 */     return (logProbability == Double.NEGATIVE_INFINITY) ? 0.0D : FastMath.exp(logProbability);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double logProbability(int x) {
/*     */     double ret;
/* 185 */     if (x < 0 || x == Integer.MAX_VALUE) {
/* 186 */       ret = Double.NEGATIVE_INFINITY;
/* 187 */     } else if (x == 0) {
/* 188 */       ret = -this.mean;
/*     */     } else {
/* 190 */       ret = -SaddlePointExpansion.getStirlingError(x) - SaddlePointExpansion.getDeviancePart(x, this.mean) - 0.5D * FastMath.log(6.283185307179586D) - 0.5D * FastMath.log(x);
/*     */     } 
/*     */ 
/*     */     
/* 194 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   public double cumulativeProbability(int x) {
/* 199 */     if (x < 0) {
/* 200 */       return 0.0D;
/*     */     }
/* 202 */     if (x == Integer.MAX_VALUE) {
/* 203 */       return 1.0D;
/*     */     }
/* 205 */     return Gamma.regularizedGammaQ(x + 1.0D, this.mean, this.epsilon, this.maxIterations);
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
/*     */   public double normalApproximateProbability(int x) {
/* 222 */     return this.normal.cumulativeProbability(x + 0.5D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getNumericalMean() {
/* 231 */     return getMean();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getNumericalVariance() {
/* 240 */     return getMean();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSupportLowerBound() {
/* 251 */     return 0;
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
/*     */   public int getSupportUpperBound() {
/* 265 */     return Integer.MAX_VALUE;
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
/* 276 */     return true;
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
/*     */   public int sample() {
/* 303 */     return (int)FastMath.min(nextPoisson(this.mean), 2147483647L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private long nextPoisson(double meanPoisson) {
/* 311 */     double pivot = 40.0D;
/* 312 */     if (meanPoisson < 40.0D) {
/* 313 */       double p = FastMath.exp(-meanPoisson);
/* 314 */       long n = 0L;
/* 315 */       double r = 1.0D;
/* 316 */       double rnd = 1.0D;
/*     */       
/* 318 */       while (n < 1000.0D * meanPoisson) {
/* 319 */         rnd = this.random.nextDouble();
/* 320 */         r *= rnd;
/* 321 */         if (r >= p) {
/* 322 */           n++; continue;
/*     */         } 
/* 324 */         return n;
/*     */       } 
/*     */       
/* 327 */       return n;
/*     */     } 
/* 329 */     double lambda = FastMath.floor(meanPoisson);
/* 330 */     double lambdaFractional = meanPoisson - lambda;
/* 331 */     double logLambda = FastMath.log(lambda);
/* 332 */     double logLambdaFactorial = CombinatoricsUtils.factorialLog((int)lambda);
/* 333 */     long y2 = (lambdaFractional < Double.MIN_VALUE) ? 0L : nextPoisson(lambdaFractional);
/* 334 */     double delta = FastMath.sqrt(lambda * FastMath.log(32.0D * lambda / Math.PI + 1.0D));
/* 335 */     double halfDelta = delta / 2.0D;
/* 336 */     double twolpd = 2.0D * lambda + delta;
/* 337 */     double a1 = FastMath.sqrt(Math.PI * twolpd) * FastMath.exp(1.0D / 8.0D * lambda);
/* 338 */     double a2 = twolpd / delta * FastMath.exp(-delta * (1.0D + delta) / twolpd);
/* 339 */     double aSum = a1 + a2 + 1.0D;
/* 340 */     double p1 = a1 / aSum;
/* 341 */     double p2 = a2 / aSum;
/* 342 */     double c1 = 1.0D / 8.0D * lambda;
/*     */     
/* 344 */     double x = 0.0D;
/* 345 */     double y = 0.0D;
/* 346 */     double v = 0.0D;
/* 347 */     int a = 0;
/* 348 */     double t = 0.0D;
/* 349 */     double qr = 0.0D;
/* 350 */     double qa = 0.0D;
/*     */     while (true) {
/* 352 */       double u = this.random.nextDouble();
/* 353 */       if (u <= p1) {
/* 354 */         double n = this.random.nextGaussian();
/* 355 */         x = n * FastMath.sqrt(lambda + halfDelta) - 0.5D;
/* 356 */         if (x > delta || x < -lambda) {
/*     */           continue;
/*     */         }
/* 359 */         y = (x < 0.0D) ? FastMath.floor(x) : FastMath.ceil(x);
/* 360 */         double e = this.exponential.sample();
/* 361 */         v = -e - n * n / 2.0D + c1;
/*     */       } else {
/* 363 */         if (u > p1 + p2) {
/* 364 */           y = lambda;
/*     */           break;
/*     */         } 
/* 367 */         x = delta + twolpd / delta * this.exponential.sample();
/* 368 */         y = FastMath.ceil(x);
/* 369 */         v = -this.exponential.sample() - delta * (x + 1.0D) / twolpd;
/*     */       } 
/*     */       
/* 372 */       a = (x < 0.0D) ? 1 : 0;
/* 373 */       t = y * (y + 1.0D) / 2.0D * lambda;
/* 374 */       if (v < -t && a == 0) {
/* 375 */         y = lambda + y;
/*     */         break;
/*     */       } 
/* 378 */       qr = t * ((2.0D * y + 1.0D) / 6.0D * lambda - 1.0D);
/* 379 */       qa = qr - t * t / 3.0D * (lambda + a * (y + 1.0D));
/* 380 */       if (v < qa) {
/* 381 */         y = lambda + y;
/*     */         break;
/*     */       } 
/* 384 */       if (v > qr) {
/*     */         continue;
/*     */       }
/* 387 */       if (v < y * logLambda - CombinatoricsUtils.factorialLog((int)(y + lambda)) + logLambdaFactorial) {
/* 388 */         y = lambda + y;
/*     */         break;
/*     */       } 
/*     */     } 
/* 392 */     return y2 + (long)y;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/distribution/PoissonDistribution.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */