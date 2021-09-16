/*     */ package org.apache.commons.math3.distribution;
/*     */ 
/*     */ import org.apache.commons.math3.exception.NotStrictlyPositiveException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.random.RandomGenerator;
/*     */ import org.apache.commons.math3.random.Well19937c;
/*     */ import org.apache.commons.math3.special.Beta;
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
/*     */ public class FDistribution
/*     */   extends AbstractRealDistribution
/*     */ {
/*     */   public static final double DEFAULT_INVERSE_ABSOLUTE_ACCURACY = 1.0E-9D;
/*     */   private static final long serialVersionUID = -8516354193418641566L;
/*     */   private final double numeratorDegreesOfFreedom;
/*     */   private final double denominatorDegreesOfFreedom;
/*     */   private final double solverAbsoluteAccuracy;
/*  48 */   private double numericalVariance = Double.NaN;
/*     */ 
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
/*     */   public FDistribution(double numeratorDegreesOfFreedom, double denominatorDegreesOfFreedom) throws NotStrictlyPositiveException {
/*  71 */     this(numeratorDegreesOfFreedom, denominatorDegreesOfFreedom, 1.0E-9D);
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
/*     */   public FDistribution(double numeratorDegreesOfFreedom, double denominatorDegreesOfFreedom, double inverseCumAccuracy) throws NotStrictlyPositiveException {
/*  99 */     this((RandomGenerator)new Well19937c(), numeratorDegreesOfFreedom, denominatorDegreesOfFreedom, inverseCumAccuracy);
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
/*     */   public FDistribution(RandomGenerator rng, double numeratorDegreesOfFreedom, double denominatorDegreesOfFreedom) throws NotStrictlyPositiveException {
/* 117 */     this(rng, numeratorDegreesOfFreedom, denominatorDegreesOfFreedom, 1.0E-9D);
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
/*     */   public FDistribution(RandomGenerator rng, double numeratorDegreesOfFreedom, double denominatorDegreesOfFreedom, double inverseCumAccuracy) throws NotStrictlyPositiveException {
/* 137 */     super(rng);
/*     */     
/* 139 */     if (numeratorDegreesOfFreedom <= 0.0D) {
/* 140 */       throw new NotStrictlyPositiveException(LocalizedFormats.DEGREES_OF_FREEDOM, Double.valueOf(numeratorDegreesOfFreedom));
/*     */     }
/*     */     
/* 143 */     if (denominatorDegreesOfFreedom <= 0.0D) {
/* 144 */       throw new NotStrictlyPositiveException(LocalizedFormats.DEGREES_OF_FREEDOM, Double.valueOf(denominatorDegreesOfFreedom));
/*     */     }
/*     */     
/* 147 */     this.numeratorDegreesOfFreedom = numeratorDegreesOfFreedom;
/* 148 */     this.denominatorDegreesOfFreedom = denominatorDegreesOfFreedom;
/* 149 */     this.solverAbsoluteAccuracy = inverseCumAccuracy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double density(double x) {
/* 158 */     return FastMath.exp(logDensity(x));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double logDensity(double x) {
/* 164 */     double nhalf = this.numeratorDegreesOfFreedom / 2.0D;
/* 165 */     double mhalf = this.denominatorDegreesOfFreedom / 2.0D;
/* 166 */     double logx = FastMath.log(x);
/* 167 */     double logn = FastMath.log(this.numeratorDegreesOfFreedom);
/* 168 */     double logm = FastMath.log(this.denominatorDegreesOfFreedom);
/* 169 */     double lognxm = FastMath.log(this.numeratorDegreesOfFreedom * x + this.denominatorDegreesOfFreedom);
/*     */     
/* 171 */     return nhalf * logn + nhalf * logx - logx + mhalf * logm - nhalf * lognxm - mhalf * lognxm - Beta.logBeta(nhalf, mhalf);
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
/*     */   public double cumulativeProbability(double x) {
/*     */     double ret;
/* 189 */     if (x <= 0.0D) {
/* 190 */       ret = 0.0D;
/*     */     } else {
/* 192 */       double n = this.numeratorDegreesOfFreedom;
/* 193 */       double m = this.denominatorDegreesOfFreedom;
/*     */       
/* 195 */       ret = Beta.regularizedBeta(n * x / (m + n * x), 0.5D * n, 0.5D * m);
/*     */     } 
/*     */ 
/*     */     
/* 199 */     return ret;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getNumeratorDegreesOfFreedom() {
/* 208 */     return this.numeratorDegreesOfFreedom;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getDenominatorDegreesOfFreedom() {
/* 217 */     return this.denominatorDegreesOfFreedom;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected double getSolverAbsoluteAccuracy() {
/* 223 */     return this.solverAbsoluteAccuracy;
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
/* 236 */     double denominatorDF = getDenominatorDegreesOfFreedom();
/*     */     
/* 238 */     if (denominatorDF > 2.0D) {
/* 239 */       return denominatorDF / (denominatorDF - 2.0D);
/*     */     }
/*     */     
/* 242 */     return Double.NaN;
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
/*     */   public double getNumericalVariance() {
/* 259 */     if (!this.numericalVarianceIsCalculated) {
/* 260 */       this.numericalVariance = calculateNumericalVariance();
/* 261 */       this.numericalVarianceIsCalculated = true;
/*     */     } 
/* 263 */     return this.numericalVariance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected double calculateNumericalVariance() {
/* 272 */     double denominatorDF = getDenominatorDegreesOfFreedom();
/*     */     
/* 274 */     if (denominatorDF > 4.0D) {
/* 275 */       double numeratorDF = getNumeratorDegreesOfFreedom();
/* 276 */       double denomDFMinusTwo = denominatorDF - 2.0D;
/*     */       
/* 278 */       return 2.0D * denominatorDF * denominatorDF * (numeratorDF + denominatorDF - 2.0D) / numeratorDF * denomDFMinusTwo * denomDFMinusTwo * (denominatorDF - 4.0D);
/*     */     } 
/*     */ 
/*     */     
/* 282 */     return Double.NaN;
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
/* 293 */     return 0.0D;
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
/* 305 */     return Double.POSITIVE_INFINITY;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSupportLowerBoundInclusive() {
/* 310 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSupportUpperBoundInclusive() {
/* 315 */     return false;
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
/* 326 */     return true;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/distribution/FDistribution.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */