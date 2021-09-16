/*     */ package org.apache.commons.math3.distribution;
/*     */ 
/*     */ import org.apache.commons.math3.exception.NotStrictlyPositiveException;
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
/*     */ public class ZipfDistribution
/*     */   extends AbstractIntegerDistribution
/*     */ {
/*     */   private static final long serialVersionUID = -140627372283420404L;
/*     */   private final int numberOfElements;
/*     */   private final double exponent;
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
/*     */   private boolean numericalVarianceIsCalculated = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private transient ZipfRejectionInversionSampler sampler;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ZipfDistribution(int numberOfElements, double exponent) {
/*  80 */     this((RandomGenerator)new Well19937c(), numberOfElements, exponent);
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
/*     */   public ZipfDistribution(RandomGenerator rng, int numberOfElements, double exponent) throws NotStrictlyPositiveException {
/*  97 */     super(rng);
/*     */     
/*  99 */     if (numberOfElements <= 0) {
/* 100 */       throw new NotStrictlyPositiveException(LocalizedFormats.DIMENSION, Integer.valueOf(numberOfElements));
/*     */     }
/*     */     
/* 103 */     if (exponent <= 0.0D) {
/* 104 */       throw new NotStrictlyPositiveException(LocalizedFormats.EXPONENT, Double.valueOf(exponent));
/*     */     }
/*     */ 
/*     */     
/* 108 */     this.numberOfElements = numberOfElements;
/* 109 */     this.exponent = exponent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNumberOfElements() {
/* 118 */     return this.numberOfElements;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getExponent() {
/* 127 */     return this.exponent;
/*     */   }
/*     */ 
/*     */   
/*     */   public double probability(int x) {
/* 132 */     if (x <= 0 || x > this.numberOfElements) {
/* 133 */       return 0.0D;
/*     */     }
/*     */     
/* 136 */     return 1.0D / FastMath.pow(x, this.exponent) / generalizedHarmonic(this.numberOfElements, this.exponent);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double logProbability(int x) {
/* 142 */     if (x <= 0 || x > this.numberOfElements) {
/* 143 */       return Double.NEGATIVE_INFINITY;
/*     */     }
/*     */     
/* 146 */     return -FastMath.log(x) * this.exponent - FastMath.log(generalizedHarmonic(this.numberOfElements, this.exponent));
/*     */   }
/*     */ 
/*     */   
/*     */   public double cumulativeProbability(int x) {
/* 151 */     if (x <= 0)
/* 152 */       return 0.0D; 
/* 153 */     if (x >= this.numberOfElements) {
/* 154 */       return 1.0D;
/*     */     }
/*     */     
/* 157 */     return generalizedHarmonic(x, this.exponent) / generalizedHarmonic(this.numberOfElements, this.exponent);
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
/*     */   public double getNumericalMean() {
/* 171 */     if (!this.numericalMeanIsCalculated) {
/* 172 */       this.numericalMean = calculateNumericalMean();
/* 173 */       this.numericalMeanIsCalculated = true;
/*     */     } 
/* 175 */     return this.numericalMean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected double calculateNumericalMean() {
/* 184 */     int N = getNumberOfElements();
/* 185 */     double s = getExponent();
/*     */     
/* 187 */     double Hs1 = generalizedHarmonic(N, s - 1.0D);
/* 188 */     double Hs = generalizedHarmonic(N, s);
/*     */     
/* 190 */     return Hs1 / Hs;
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
/* 205 */     if (!this.numericalVarianceIsCalculated) {
/* 206 */       this.numericalVariance = calculateNumericalVariance();
/* 207 */       this.numericalVarianceIsCalculated = true;
/*     */     } 
/* 209 */     return this.numericalVariance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected double calculateNumericalVariance() {
/* 218 */     int N = getNumberOfElements();
/* 219 */     double s = getExponent();
/*     */     
/* 221 */     double Hs2 = generalizedHarmonic(N, s - 2.0D);
/* 222 */     double Hs1 = generalizedHarmonic(N, s - 1.0D);
/* 223 */     double Hs = generalizedHarmonic(N, s);
/*     */     
/* 225 */     return Hs2 / Hs - Hs1 * Hs1 / Hs * Hs;
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
/*     */   private double generalizedHarmonic(int n, double m) {
/* 238 */     double value = 0.0D;
/* 239 */     for (int k = n; k > 0; k--) {
/* 240 */       value += 1.0D / FastMath.pow(k, m);
/*     */     }
/* 242 */     return value;
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
/* 253 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSupportUpperBound() {
/* 264 */     return getNumberOfElements();
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
/* 275 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int sample() {
/* 283 */     if (this.sampler == null) {
/* 284 */       this.sampler = new ZipfRejectionInversionSampler(this.numberOfElements, this.exponent);
/*     */     }
/* 286 */     return this.sampler.sample(this.random);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final class ZipfRejectionInversionSampler
/*     */   {
/*     */     private final double exponent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final int numberOfElements;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final double hIntegralX1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final double hIntegralNumberOfElements;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final double s;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     ZipfRejectionInversionSampler(int numberOfElements, double exponent) {
/* 331 */       this.exponent = exponent;
/* 332 */       this.numberOfElements = numberOfElements;
/* 333 */       this.hIntegralX1 = hIntegral(1.5D) - 1.0D;
/* 334 */       this.hIntegralNumberOfElements = hIntegral(numberOfElements + 0.5D);
/* 335 */       this.s = 2.0D - hIntegralInverse(hIntegral(2.5D) - h(2.0D));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     int sample(RandomGenerator random) {
/*     */       double u;
/*     */       double x;
/*     */       int k;
/*     */       do {
/* 345 */         u = this.hIntegralNumberOfElements + random.nextDouble() * (this.hIntegralX1 - this.hIntegralNumberOfElements);
/*     */ 
/*     */         
/* 348 */         x = hIntegralInverse(u);
/*     */         
/* 350 */         k = (int)(x + 0.5D);
/*     */ 
/*     */ 
/*     */         
/* 354 */         if (k < 1) {
/* 355 */           k = 1;
/*     */         }
/* 357 */         else if (k > this.numberOfElements) {
/* 358 */           k = this.numberOfElements;
/*     */ 
/*     */ 
/*     */         
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       }
/* 368 */       while (k - x > this.s && u < hIntegral(k + 0.5D) - h(k));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 406 */       return k;
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
/*     */ 
/*     */     
/*     */     private double hIntegral(double x) {
/* 424 */       double logX = FastMath.log(x);
/* 425 */       return helper2((1.0D - this.exponent) * logX) * logX;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private double h(double x) {
/* 435 */       return FastMath.exp(-this.exponent * FastMath.log(x));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private double hIntegralInverse(double x) {
/* 445 */       double t = x * (1.0D - this.exponent);
/* 446 */       if (t < -1.0D)
/*     */       {
/*     */         
/* 449 */         t = -1.0D;
/*     */       }
/* 451 */       return FastMath.exp(helper1(t) * x);
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
/*     */     static double helper1(double x) {
/* 463 */       if (FastMath.abs(x) > 1.0E-8D) {
/* 464 */         return FastMath.log1p(x) / x;
/*     */       }
/*     */       
/* 467 */       return 1.0D - x * (0.5D - x * (0.3333333333333333D - x * 0.25D));
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
/*     */     static double helper2(double x) {
/* 480 */       if (FastMath.abs(x) > 1.0E-8D) {
/* 481 */         return FastMath.expm1(x) / x;
/*     */       }
/*     */       
/* 484 */       return 1.0D + x * 0.5D * (1.0D + x * 0.3333333333333333D * (1.0D + x * 0.25D));
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/distribution/ZipfDistribution.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */