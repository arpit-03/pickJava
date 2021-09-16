/*     */ package org.apache.commons.math3.distribution;
/*     */ 
/*     */ import org.apache.commons.math3.exception.NotStrictlyPositiveException;
/*     */ import org.apache.commons.math3.exception.OutOfRangeException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.random.RandomGenerator;
/*     */ import org.apache.commons.math3.random.Well19937c;
/*     */ import org.apache.commons.math3.util.CombinatoricsUtils;
/*     */ import org.apache.commons.math3.util.FastMath;
/*     */ import org.apache.commons.math3.util.ResizableDoubleArray;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ExponentialDistribution
/*     */   extends AbstractRealDistribution
/*     */ {
/*     */   public static final double DEFAULT_INVERSE_ABSOLUTE_ACCURACY = 1.0E-9D;
/*     */   private static final long serialVersionUID = 2401296428283614780L;
/*     */   private static final double[] EXPONENTIAL_SA_QI;
/*     */   private final double mean;
/*     */   private final double logMean;
/*     */   private final double solverAbsoluteAccuracy;
/*     */   
/*     */   static {
/*  71 */     double LN2 = FastMath.log(2.0D);
/*  72 */     double qi = 0.0D;
/*  73 */     int i = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  81 */     ResizableDoubleArray ra = new ResizableDoubleArray(20);
/*     */     
/*  83 */     while (qi < 1.0D) {
/*  84 */       qi += FastMath.pow(LN2, i) / CombinatoricsUtils.factorial(i);
/*  85 */       ra.addElement(qi);
/*  86 */       i++;
/*     */     } 
/*     */     
/*  89 */     EXPONENTIAL_SA_QI = ra.getElements();
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
/*     */   public ExponentialDistribution(double mean) {
/* 105 */     this(mean, 1.0E-9D);
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
/*     */   public ExponentialDistribution(double mean, double inverseCumAccuracy) {
/* 126 */     this((RandomGenerator)new Well19937c(), mean, inverseCumAccuracy);
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
/*     */   public ExponentialDistribution(RandomGenerator rng, double mean) throws NotStrictlyPositiveException {
/* 139 */     this(rng, mean, 1.0E-9D);
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
/*     */   public ExponentialDistribution(RandomGenerator rng, double mean, double inverseCumAccuracy) throws NotStrictlyPositiveException {
/* 157 */     super(rng);
/*     */     
/* 159 */     if (mean <= 0.0D) {
/* 160 */       throw new NotStrictlyPositiveException(LocalizedFormats.MEAN, Double.valueOf(mean));
/*     */     }
/* 162 */     this.mean = mean;
/* 163 */     this.logMean = FastMath.log(mean);
/* 164 */     this.solverAbsoluteAccuracy = inverseCumAccuracy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getMean() {
/* 173 */     return this.mean;
/*     */   }
/*     */ 
/*     */   
/*     */   public double density(double x) {
/* 178 */     double logDensity = logDensity(x);
/* 179 */     return (logDensity == Double.NEGATIVE_INFINITY) ? 0.0D : FastMath.exp(logDensity);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double logDensity(double x) {
/* 185 */     if (x < 0.0D) {
/* 186 */       return Double.NEGATIVE_INFINITY;
/*     */     }
/* 188 */     return -x / this.mean - this.logMean;
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
/*     */   public double cumulativeProbability(double x) {
/*     */     double ret;
/* 203 */     if (x <= 0.0D) {
/* 204 */       ret = 0.0D;
/*     */     } else {
/* 206 */       ret = 1.0D - FastMath.exp(-x / this.mean);
/*     */     } 
/* 208 */     return ret;
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
/*     */   public double inverseCumulativeProbability(double p) throws OutOfRangeException {
/*     */     double ret;
/* 221 */     if (p < 0.0D || p > 1.0D)
/* 222 */       throw new OutOfRangeException(Double.valueOf(p), Double.valueOf(0.0D), Double.valueOf(1.0D)); 
/* 223 */     if (p == 1.0D) {
/* 224 */       ret = Double.POSITIVE_INFINITY;
/*     */     } else {
/* 226 */       ret = -this.mean * FastMath.log(1.0D - p);
/*     */     } 
/*     */     
/* 229 */     return ret;
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
/*     */   public double sample() {
/* 246 */     double a = 0.0D;
/* 247 */     double u = this.random.nextDouble();
/*     */ 
/*     */     
/* 250 */     while (u < 0.5D) {
/* 251 */       a += EXPONENTIAL_SA_QI[0];
/* 252 */       u *= 2.0D;
/*     */     } 
/*     */ 
/*     */     
/* 256 */     u += u - 1.0D;
/*     */ 
/*     */     
/* 259 */     if (u <= EXPONENTIAL_SA_QI[0]) {
/* 260 */       return this.mean * (a + u);
/*     */     }
/*     */ 
/*     */     
/* 264 */     int i = 0;
/* 265 */     double u2 = this.random.nextDouble();
/* 266 */     double umin = u2;
/*     */ 
/*     */     
/*     */     do {
/* 270 */       i++;
/* 271 */       u2 = this.random.nextDouble();
/*     */       
/* 273 */       if (u2 >= umin)
/* 274 */         continue;  umin = u2;
/*     */ 
/*     */     
/*     */     }
/* 278 */     while (u > EXPONENTIAL_SA_QI[i]);
/*     */     
/* 280 */     return this.mean * (a + umin * EXPONENTIAL_SA_QI[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected double getSolverAbsoluteAccuracy() {
/* 286 */     return this.solverAbsoluteAccuracy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getNumericalMean() {
/* 295 */     return getMean();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getNumericalVariance() {
/* 304 */     double m = getMean();
/* 305 */     return m * m;
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
/*     */   public double getSupportUpperBound() {
/* 328 */     return Double.POSITIVE_INFINITY;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSupportLowerBoundInclusive() {
/* 333 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSupportUpperBoundInclusive() {
/* 338 */     return false;
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
/* 349 */     return true;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/distribution/ExponentialDistribution.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */