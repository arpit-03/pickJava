/*     */ package org.apache.commons.math3.distribution;
/*     */ 
/*     */ import org.apache.commons.math3.exception.NotStrictlyPositiveException;
/*     */ import org.apache.commons.math3.exception.NumberIsTooLargeException;
/*     */ import org.apache.commons.math3.exception.OutOfRangeException;
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
/*     */ public class NormalDistribution
/*     */   extends AbstractRealDistribution
/*     */ {
/*     */   public static final double DEFAULT_INVERSE_ABSOLUTE_ACCURACY = 1.0E-9D;
/*     */   private static final long serialVersionUID = 8589540077390120676L;
/*  44 */   private static final double SQRT2 = FastMath.sqrt(2.0D);
/*     */ 
/*     */ 
/*     */   
/*     */   private final double mean;
/*     */ 
/*     */ 
/*     */   
/*     */   private final double standardDeviation;
/*     */ 
/*     */ 
/*     */   
/*     */   private final double logStandardDeviationPlusHalfLog2Pi;
/*     */ 
/*     */ 
/*     */   
/*     */   private final double solverAbsoluteAccuracy;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NormalDistribution() {
/*  66 */     this(0.0D, 1.0D);
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
/*     */   public NormalDistribution(double mean, double sd) throws NotStrictlyPositiveException {
/*  85 */     this(mean, sd, 1.0E-9D);
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
/*     */   public NormalDistribution(double mean, double sd, double inverseCumAccuracy) throws NotStrictlyPositiveException {
/* 107 */     this((RandomGenerator)new Well19937c(), mean, sd, inverseCumAccuracy);
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
/*     */   public NormalDistribution(RandomGenerator rng, double mean, double sd) throws NotStrictlyPositiveException {
/* 121 */     this(rng, mean, sd, 1.0E-9D);
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
/*     */   public NormalDistribution(RandomGenerator rng, double mean, double sd, double inverseCumAccuracy) throws NotStrictlyPositiveException {
/* 139 */     super(rng);
/*     */     
/* 141 */     if (sd <= 0.0D) {
/* 142 */       throw new NotStrictlyPositiveException(LocalizedFormats.STANDARD_DEVIATION, Double.valueOf(sd));
/*     */     }
/*     */     
/* 145 */     this.mean = mean;
/* 146 */     this.standardDeviation = sd;
/* 147 */     this.logStandardDeviationPlusHalfLog2Pi = FastMath.log(sd) + 0.5D * FastMath.log(6.283185307179586D);
/* 148 */     this.solverAbsoluteAccuracy = inverseCumAccuracy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getMean() {
/* 157 */     return this.mean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getStandardDeviation() {
/* 166 */     return this.standardDeviation;
/*     */   }
/*     */ 
/*     */   
/*     */   public double density(double x) {
/* 171 */     return FastMath.exp(logDensity(x));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double logDensity(double x) {
/* 177 */     double x0 = x - this.mean;
/* 178 */     double x1 = x0 / this.standardDeviation;
/* 179 */     return -0.5D * x1 * x1 - this.logStandardDeviationPlusHalfLog2Pi;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double cumulativeProbability(double x) {
/* 190 */     double dev = x - this.mean;
/* 191 */     if (FastMath.abs(dev) > 40.0D * this.standardDeviation) {
/* 192 */       return (dev < 0.0D) ? 0.0D : 1.0D;
/*     */     }
/* 194 */     return 0.5D * Erf.erfc(-dev / this.standardDeviation * SQRT2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double inverseCumulativeProbability(double p) throws OutOfRangeException {
/* 202 */     if (p < 0.0D || p > 1.0D) {
/* 203 */       throw new OutOfRangeException(Double.valueOf(p), Integer.valueOf(0), Integer.valueOf(1));
/*     */     }
/* 205 */     return this.mean + this.standardDeviation * SQRT2 * Erf.erfInv(2.0D * p - 1.0D);
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
/* 216 */     return probability(x0, x1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double probability(double x0, double x1) throws NumberIsTooLargeException {
/* 224 */     if (x0 > x1) {
/* 225 */       throw new NumberIsTooLargeException(LocalizedFormats.LOWER_ENDPOINT_ABOVE_UPPER_ENDPOINT, Double.valueOf(x0), Double.valueOf(x1), true);
/*     */     }
/*     */     
/* 228 */     double denom = this.standardDeviation * SQRT2;
/* 229 */     double v0 = (x0 - this.mean) / denom;
/* 230 */     double v1 = (x1 - this.mean) / denom;
/* 231 */     return 0.5D * Erf.erf(v0, v1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected double getSolverAbsoluteAccuracy() {
/* 237 */     return this.solverAbsoluteAccuracy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getNumericalMean() {
/* 246 */     return getMean();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getNumericalVariance() {
/* 255 */     double s = getStandardDeviation();
/* 256 */     return s * s;
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
/*     */   public double getSupportLowerBound() {
/* 269 */     return Double.NEGATIVE_INFINITY;
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
/* 282 */     return Double.POSITIVE_INFINITY;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSupportLowerBoundInclusive() {
/* 287 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSupportUpperBoundInclusive() {
/* 292 */     return false;
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
/* 303 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double sample() {
/* 309 */     return this.standardDeviation * this.random.nextGaussian() + this.mean;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/distribution/NormalDistribution.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */