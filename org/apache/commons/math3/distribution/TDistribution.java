/*     */ package org.apache.commons.math3.distribution;
/*     */ 
/*     */ import org.apache.commons.math3.exception.NotStrictlyPositiveException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.random.RandomGenerator;
/*     */ import org.apache.commons.math3.random.Well19937c;
/*     */ import org.apache.commons.math3.special.Beta;
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
/*     */ public class TDistribution
/*     */   extends AbstractRealDistribution
/*     */ {
/*     */   public static final double DEFAULT_INVERSE_ABSOLUTE_ACCURACY = 1.0E-9D;
/*     */   private static final long serialVersionUID = -5852615386664158222L;
/*     */   private final double degreesOfFreedom;
/*     */   private final double solverAbsoluteAccuracy;
/*     */   private final double factor;
/*     */   
/*     */   public TDistribution(double degreesOfFreedom) throws NotStrictlyPositiveException {
/*  63 */     this(degreesOfFreedom, 1.0E-9D);
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
/*     */   public TDistribution(double degreesOfFreedom, double inverseCumAccuracy) throws NotStrictlyPositiveException {
/*  86 */     this((RandomGenerator)new Well19937c(), degreesOfFreedom, inverseCumAccuracy);
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
/*     */   public TDistribution(RandomGenerator rng, double degreesOfFreedom) throws NotStrictlyPositiveException {
/*  99 */     this(rng, degreesOfFreedom, 1.0E-9D);
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
/*     */   public TDistribution(RandomGenerator rng, double degreesOfFreedom, double inverseCumAccuracy) throws NotStrictlyPositiveException {
/* 117 */     super(rng);
/*     */     
/* 119 */     if (degreesOfFreedom <= 0.0D) {
/* 120 */       throw new NotStrictlyPositiveException(LocalizedFormats.DEGREES_OF_FREEDOM, Double.valueOf(degreesOfFreedom));
/*     */     }
/*     */     
/* 123 */     this.degreesOfFreedom = degreesOfFreedom;
/* 124 */     this.solverAbsoluteAccuracy = inverseCumAccuracy;
/*     */     
/* 126 */     double n = degreesOfFreedom;
/* 127 */     double nPlus1Over2 = (n + 1.0D) / 2.0D;
/* 128 */     this.factor = Gamma.logGamma(nPlus1Over2) - 0.5D * (FastMath.log(Math.PI) + FastMath.log(n)) - Gamma.logGamma(n / 2.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getDegreesOfFreedom() {
/* 139 */     return this.degreesOfFreedom;
/*     */   }
/*     */ 
/*     */   
/*     */   public double density(double x) {
/* 144 */     return FastMath.exp(logDensity(x));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double logDensity(double x) {
/* 150 */     double n = this.degreesOfFreedom;
/* 151 */     double nPlus1Over2 = (n + 1.0D) / 2.0D;
/* 152 */     return this.factor - nPlus1Over2 * FastMath.log(1.0D + x * x / n);
/*     */   }
/*     */ 
/*     */   
/*     */   public double cumulativeProbability(double x) {
/*     */     double ret;
/* 158 */     if (x == 0.0D) {
/* 159 */       ret = 0.5D;
/*     */     } else {
/* 161 */       double t = Beta.regularizedBeta(this.degreesOfFreedom / (this.degreesOfFreedom + x * x), 0.5D * this.degreesOfFreedom, 0.5D);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 166 */       if (x < 0.0D) {
/* 167 */         ret = 0.5D * t;
/*     */       } else {
/* 169 */         ret = 1.0D - 0.5D * t;
/*     */       } 
/*     */     } 
/*     */     
/* 173 */     return ret;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected double getSolverAbsoluteAccuracy() {
/* 179 */     return this.solverAbsoluteAccuracy;
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
/* 192 */     double df = getDegreesOfFreedom();
/*     */     
/* 194 */     if (df > 1.0D) {
/* 195 */       return 0.0D;
/*     */     }
/*     */     
/* 198 */     return Double.NaN;
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
/*     */   public double getNumericalVariance() {
/* 213 */     double df = getDegreesOfFreedom();
/*     */     
/* 215 */     if (df > 2.0D) {
/* 216 */       return df / (df - 2.0D);
/*     */     }
/*     */     
/* 219 */     if (df > 1.0D && df <= 2.0D) {
/* 220 */       return Double.POSITIVE_INFINITY;
/*     */     }
/*     */     
/* 223 */     return Double.NaN;
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
/* 236 */     return Double.NEGATIVE_INFINITY;
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
/* 249 */     return Double.POSITIVE_INFINITY;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSupportLowerBoundInclusive() {
/* 254 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSupportUpperBoundInclusive() {
/* 259 */     return false;
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
/* 270 */     return true;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/distribution/TDistribution.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */