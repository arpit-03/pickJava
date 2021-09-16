/*     */ package org.apache.commons.math3.distribution;
/*     */ 
/*     */ import org.apache.commons.math3.exception.NotPositiveException;
/*     */ import org.apache.commons.math3.exception.NotStrictlyPositiveException;
/*     */ import org.apache.commons.math3.exception.NumberIsTooLargeException;
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
/*     */ public class HypergeometricDistribution
/*     */   extends AbstractIntegerDistribution
/*     */ {
/*     */   private static final long serialVersionUID = -436928820673516179L;
/*     */   private final int numberOfSuccesses;
/*     */   private final int populationSize;
/*     */   private final int sampleSize;
/*  44 */   private double numericalVariance = Double.NaN;
/*     */ 
/*     */ 
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
/*     */   
/*     */   public HypergeometricDistribution(int populationSize, int numberOfSuccesses, int sampleSize) throws NotPositiveException, NotStrictlyPositiveException, NumberIsTooLargeException {
/*  69 */     this((RandomGenerator)new Well19937c(), populationSize, numberOfSuccesses, sampleSize);
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
/*     */   public HypergeometricDistribution(RandomGenerator rng, int populationSize, int numberOfSuccesses, int sampleSize) throws NotPositiveException, NotStrictlyPositiveException, NumberIsTooLargeException {
/*  90 */     super(rng);
/*     */     
/*  92 */     if (populationSize <= 0) {
/*  93 */       throw new NotStrictlyPositiveException(LocalizedFormats.POPULATION_SIZE, Integer.valueOf(populationSize));
/*     */     }
/*     */     
/*  96 */     if (numberOfSuccesses < 0) {
/*  97 */       throw new NotPositiveException(LocalizedFormats.NUMBER_OF_SUCCESSES, Integer.valueOf(numberOfSuccesses));
/*     */     }
/*     */     
/* 100 */     if (sampleSize < 0) {
/* 101 */       throw new NotPositiveException(LocalizedFormats.NUMBER_OF_SAMPLES, Integer.valueOf(sampleSize));
/*     */     }
/*     */ 
/*     */     
/* 105 */     if (numberOfSuccesses > populationSize) {
/* 106 */       throw new NumberIsTooLargeException(LocalizedFormats.NUMBER_OF_SUCCESS_LARGER_THAN_POPULATION_SIZE, Integer.valueOf(numberOfSuccesses), Integer.valueOf(populationSize), true);
/*     */     }
/*     */     
/* 109 */     if (sampleSize > populationSize) {
/* 110 */       throw new NumberIsTooLargeException(LocalizedFormats.SAMPLE_SIZE_LARGER_THAN_POPULATION_SIZE, Integer.valueOf(sampleSize), Integer.valueOf(populationSize), true);
/*     */     }
/*     */ 
/*     */     
/* 114 */     this.numberOfSuccesses = numberOfSuccesses;
/* 115 */     this.populationSize = populationSize;
/* 116 */     this.sampleSize = sampleSize;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double cumulativeProbability(int x) {
/*     */     double ret;
/* 123 */     int[] domain = getDomain(this.populationSize, this.numberOfSuccesses, this.sampleSize);
/* 124 */     if (x < domain[0]) {
/* 125 */       ret = 0.0D;
/* 126 */     } else if (x >= domain[1]) {
/* 127 */       ret = 1.0D;
/*     */     } else {
/* 129 */       ret = innerCumulativeProbability(domain[0], x, 1);
/*     */     } 
/*     */     
/* 132 */     return ret;
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
/*     */   private int[] getDomain(int n, int m, int k) {
/* 145 */     return new int[] { getLowerDomain(n, m, k), getUpperDomain(m, k) };
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
/*     */   private int getLowerDomain(int n, int m, int k) {
/* 158 */     return FastMath.max(0, m - n - k);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNumberOfSuccesses() {
/* 167 */     return this.numberOfSuccesses;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPopulationSize() {
/* 176 */     return this.populationSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSampleSize() {
/* 185 */     return this.sampleSize;
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
/*     */   private int getUpperDomain(int m, int k) {
/* 197 */     return FastMath.min(k, m);
/*     */   }
/*     */ 
/*     */   
/*     */   public double probability(int x) {
/* 202 */     double logProbability = logProbability(x);
/* 203 */     return (logProbability == Double.NEGATIVE_INFINITY) ? 0.0D : FastMath.exp(logProbability);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double logProbability(int x) {
/*     */     double ret;
/* 211 */     int[] domain = getDomain(this.populationSize, this.numberOfSuccesses, this.sampleSize);
/* 212 */     if (x < domain[0] || x > domain[1]) {
/* 213 */       ret = Double.NEGATIVE_INFINITY;
/*     */     } else {
/* 215 */       double p = this.sampleSize / this.populationSize;
/* 216 */       double q = (this.populationSize - this.sampleSize) / this.populationSize;
/* 217 */       double p1 = SaddlePointExpansion.logBinomialProbability(x, this.numberOfSuccesses, p, q);
/*     */       
/* 219 */       double p2 = SaddlePointExpansion.logBinomialProbability(this.sampleSize - x, this.populationSize - this.numberOfSuccesses, p, q);
/*     */ 
/*     */       
/* 222 */       double p3 = SaddlePointExpansion.logBinomialProbability(this.sampleSize, this.populationSize, p, q);
/*     */       
/* 224 */       ret = p1 + p2 - p3;
/*     */     } 
/*     */     
/* 227 */     return ret;
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
/*     */   public double upperCumulativeProbability(int x) {
/*     */     double ret;
/* 240 */     int[] domain = getDomain(this.populationSize, this.numberOfSuccesses, this.sampleSize);
/* 241 */     if (x <= domain[0]) {
/* 242 */       ret = 1.0D;
/* 243 */     } else if (x > domain[1]) {
/* 244 */       ret = 0.0D;
/*     */     } else {
/* 246 */       ret = innerCumulativeProbability(domain[1], x, -1);
/*     */     } 
/*     */     
/* 249 */     return ret;
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
/*     */   private double innerCumulativeProbability(int x0, int x1, int dx) {
/* 266 */     double ret = probability(x0);
/* 267 */     while (x0 != x1) {
/* 268 */       x0 += dx;
/* 269 */       ret += probability(x0);
/*     */     } 
/* 271 */     return ret;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getNumericalMean() {
/* 281 */     return getSampleSize() * getNumberOfSuccesses() / getPopulationSize();
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
/* 292 */     if (!this.numericalVarianceIsCalculated) {
/* 293 */       this.numericalVariance = calculateNumericalVariance();
/* 294 */       this.numericalVarianceIsCalculated = true;
/*     */     } 
/* 296 */     return this.numericalVariance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected double calculateNumericalVariance() {
/* 305 */     double N = getPopulationSize();
/* 306 */     double m = getNumberOfSuccesses();
/* 307 */     double n = getSampleSize();
/* 308 */     return n * m * (N - n) * (N - m) / N * N * (N - 1.0D);
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
/*     */   public int getSupportLowerBound() {
/* 321 */     return FastMath.max(0, getSampleSize() + getNumberOfSuccesses() - getPopulationSize());
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
/*     */   public int getSupportUpperBound() {
/* 334 */     return FastMath.min(getNumberOfSuccesses(), getSampleSize());
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
/* 345 */     return true;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/distribution/HypergeometricDistribution.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */