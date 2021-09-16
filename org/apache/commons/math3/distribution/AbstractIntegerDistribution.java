/*     */ package org.apache.commons.math3.distribution;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import org.apache.commons.math3.exception.MathInternalError;
/*     */ import org.apache.commons.math3.exception.NotStrictlyPositiveException;
/*     */ import org.apache.commons.math3.exception.NumberIsTooLargeException;
/*     */ import org.apache.commons.math3.exception.OutOfRangeException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.random.RandomDataImpl;
/*     */ import org.apache.commons.math3.random.RandomGenerator;
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
/*     */ public abstract class AbstractIntegerDistribution
/*     */   implements IntegerDistribution, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -1146319659338487221L;
/*     */   @Deprecated
/*  45 */   protected final RandomDataImpl randomData = new RandomDataImpl();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final RandomGenerator random;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected AbstractIntegerDistribution() {
/*  63 */     this.random = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractIntegerDistribution(RandomGenerator rng) {
/*  71 */     this.random = rng;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double cumulativeProbability(int x0, int x1) throws NumberIsTooLargeException {
/*  81 */     if (x1 < x0) {
/*  82 */       throw new NumberIsTooLargeException(LocalizedFormats.LOWER_ENDPOINT_ABOVE_UPPER_ENDPOINT, Integer.valueOf(x0), Integer.valueOf(x1), true);
/*     */     }
/*     */     
/*  85 */     return cumulativeProbability(x1) - cumulativeProbability(x0);
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
/*     */   public int inverseCumulativeProbability(double p) throws OutOfRangeException {
/* 100 */     if (p < 0.0D || p > 1.0D) {
/* 101 */       throw new OutOfRangeException(Double.valueOf(p), Integer.valueOf(0), Integer.valueOf(1));
/*     */     }
/*     */     
/* 104 */     int lower = getSupportLowerBound();
/* 105 */     if (p == 0.0D) {
/* 106 */       return lower;
/*     */     }
/* 108 */     if (lower == Integer.MIN_VALUE) {
/* 109 */       if (checkedCumulativeProbability(lower) >= p) {
/* 110 */         return lower;
/*     */       }
/*     */     } else {
/* 113 */       lower--;
/*     */     } 
/*     */ 
/*     */     
/* 117 */     int upper = getSupportUpperBound();
/* 118 */     if (p == 1.0D) {
/* 119 */       return upper;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 124 */     double mu = getNumericalMean();
/* 125 */     double sigma = FastMath.sqrt(getNumericalVariance());
/* 126 */     boolean chebyshevApplies = (!Double.isInfinite(mu) && !Double.isNaN(mu) && !Double.isInfinite(sigma) && !Double.isNaN(sigma) && sigma != 0.0D);
/*     */     
/* 128 */     if (chebyshevApplies) {
/* 129 */       double k = FastMath.sqrt((1.0D - p) / p);
/* 130 */       double tmp = mu - k * sigma;
/* 131 */       if (tmp > lower) {
/* 132 */         lower = (int)FastMath.ceil(tmp) - 1;
/*     */       }
/* 134 */       k = 1.0D / k;
/* 135 */       tmp = mu + k * sigma;
/* 136 */       if (tmp < upper) {
/* 137 */         upper = (int)FastMath.ceil(tmp) - 1;
/*     */       }
/*     */     } 
/*     */     
/* 141 */     return solveInverseCumulativeProbability(p, lower, upper);
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
/*     */   protected int solveInverseCumulativeProbability(double p, int lower, int upper) {
/* 157 */     while (lower + 1 < upper) {
/* 158 */       int xm = (lower + upper) / 2;
/* 159 */       if (xm < lower || xm > upper)
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 165 */         xm = lower + (upper - lower) / 2;
/*     */       }
/*     */       
/* 168 */       double pm = checkedCumulativeProbability(xm);
/* 169 */       if (pm >= p) {
/* 170 */         upper = xm; continue;
/*     */       } 
/* 172 */       lower = xm;
/*     */     } 
/*     */     
/* 175 */     return upper;
/*     */   }
/*     */ 
/*     */   
/*     */   public void reseedRandomGenerator(long seed) {
/* 180 */     this.random.setSeed(seed);
/* 181 */     this.randomData.reSeed(seed);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int sample() {
/* 192 */     return inverseCumulativeProbability(this.random.nextDouble());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] sample(int sampleSize) {
/* 202 */     if (sampleSize <= 0) {
/* 203 */       throw new NotStrictlyPositiveException(LocalizedFormats.NUMBER_OF_SAMPLES, Integer.valueOf(sampleSize));
/*     */     }
/*     */     
/* 206 */     int[] out = new int[sampleSize];
/* 207 */     for (int i = 0; i < sampleSize; i++) {
/* 208 */       out[i] = sample();
/*     */     }
/* 210 */     return out;
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
/*     */   private double checkedCumulativeProbability(int argument) throws MathInternalError {
/* 226 */     double result = Double.NaN;
/* 227 */     result = cumulativeProbability(argument);
/* 228 */     if (Double.isNaN(result)) {
/* 229 */       throw new MathInternalError(LocalizedFormats.DISCRETE_CUMULATIVE_PROBABILITY_RETURNED_NAN, new Object[] { Integer.valueOf(argument) });
/*     */     }
/*     */     
/* 232 */     return result;
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
/*     */   public double logProbability(int x) {
/* 251 */     return FastMath.log(probability(x));
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/distribution/AbstractIntegerDistribution.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */