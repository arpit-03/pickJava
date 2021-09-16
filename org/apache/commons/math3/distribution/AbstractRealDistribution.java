/*     */ package org.apache.commons.math3.distribution;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import org.apache.commons.math3.analysis.UnivariateFunction;
/*     */ import org.apache.commons.math3.analysis.solvers.UnivariateSolverUtils;
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
/*     */ 
/*     */ public abstract class AbstractRealDistribution
/*     */   implements RealDistribution, Serializable
/*     */ {
/*     */   public static final double SOLVER_DEFAULT_ABSOLUTE_ACCURACY = 1.0E-6D;
/*     */   private static final long serialVersionUID = -38038050983108802L;
/*     */   @Deprecated
/*  48 */   protected RandomDataImpl randomData = new RandomDataImpl();
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
/*  59 */   private double solverAbsoluteAccuracy = 1.0E-6D;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected AbstractRealDistribution() {
/*  69 */     this.random = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractRealDistribution(RandomGenerator rng) {
/*  76 */     this.random = rng;
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
/*     */   @Deprecated
/*     */   public double cumulativeProbability(double x0, double x1) throws NumberIsTooLargeException {
/*  90 */     return probability(x0, x1);
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
/*     */   public double probability(double x0, double x1) {
/* 111 */     if (x0 > x1) {
/* 112 */       throw new NumberIsTooLargeException(LocalizedFormats.LOWER_ENDPOINT_ABOVE_UPPER_ENDPOINT, Double.valueOf(x0), Double.valueOf(x1), true);
/*     */     }
/*     */     
/* 115 */     return cumulativeProbability(x1) - cumulativeProbability(x0);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double inverseCumulativeProbability(final double p) throws OutOfRangeException {
/* 156 */     if (p < 0.0D || p > 1.0D) {
/* 157 */       throw new OutOfRangeException(Double.valueOf(p), Integer.valueOf(0), Integer.valueOf(1));
/*     */     }
/*     */     
/* 160 */     double lowerBound = getSupportLowerBound();
/* 161 */     if (p == 0.0D) {
/* 162 */       return lowerBound;
/*     */     }
/*     */     
/* 165 */     double upperBound = getSupportUpperBound();
/* 166 */     if (p == 1.0D) {
/* 167 */       return upperBound;
/*     */     }
/*     */     
/* 170 */     double mu = getNumericalMean();
/* 171 */     double sig = FastMath.sqrt(getNumericalVariance());
/*     */     
/* 173 */     boolean chebyshevApplies = (!Double.isInfinite(mu) && !Double.isNaN(mu) && !Double.isInfinite(sig) && !Double.isNaN(sig));
/*     */ 
/*     */     
/* 176 */     if (lowerBound == Double.NEGATIVE_INFINITY) {
/* 177 */       if (chebyshevApplies) {
/* 178 */         lowerBound = mu - sig * FastMath.sqrt((1.0D - p) / p);
/*     */       } else {
/* 180 */         lowerBound = -1.0D;
/* 181 */         while (cumulativeProbability(lowerBound) >= p) {
/* 182 */           lowerBound *= 2.0D;
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 187 */     if (upperBound == Double.POSITIVE_INFINITY) {
/* 188 */       if (chebyshevApplies) {
/* 189 */         upperBound = mu + sig * FastMath.sqrt(p / (1.0D - p));
/*     */       } else {
/* 191 */         upperBound = 1.0D;
/* 192 */         while (cumulativeProbability(upperBound) < p) {
/* 193 */           upperBound *= 2.0D;
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 198 */     UnivariateFunction toSolve = new UnivariateFunction()
/*     */       {
/*     */         public double value(double x) {
/* 201 */           return AbstractRealDistribution.this.cumulativeProbability(x) - p;
/*     */         }
/*     */       };
/*     */     
/* 205 */     double x = UnivariateSolverUtils.solve(toSolve, lowerBound, upperBound, getSolverAbsoluteAccuracy());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 210 */     if (!isSupportConnected()) {
/*     */       
/* 212 */       double dx = getSolverAbsoluteAccuracy();
/* 213 */       if (x - dx >= getSupportLowerBound()) {
/* 214 */         double px = cumulativeProbability(x);
/* 215 */         if (cumulativeProbability(x - dx) == px) {
/* 216 */           upperBound = x;
/* 217 */           while (upperBound - lowerBound > dx) {
/* 218 */             double midPoint = 0.5D * (lowerBound + upperBound);
/* 219 */             if (cumulativeProbability(midPoint) < px) {
/* 220 */               lowerBound = midPoint; continue;
/*     */             } 
/* 222 */             upperBound = midPoint;
/*     */           } 
/*     */           
/* 225 */           return upperBound;
/*     */         } 
/*     */       } 
/*     */     } 
/* 229 */     return x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected double getSolverAbsoluteAccuracy() {
/* 240 */     return this.solverAbsoluteAccuracy;
/*     */   }
/*     */ 
/*     */   
/*     */   public void reseedRandomGenerator(long seed) {
/* 245 */     this.random.setSeed(seed);
/* 246 */     this.randomData.reSeed(seed);
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
/*     */   public double sample() {
/* 258 */     return inverseCumulativeProbability(this.random.nextDouble());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] sample(int sampleSize) {
/* 268 */     if (sampleSize <= 0) {
/* 269 */       throw new NotStrictlyPositiveException(LocalizedFormats.NUMBER_OF_SAMPLES, Integer.valueOf(sampleSize));
/*     */     }
/*     */     
/* 272 */     double[] out = new double[sampleSize];
/* 273 */     for (int i = 0; i < sampleSize; i++) {
/* 274 */       out[i] = sample();
/*     */     }
/* 276 */     return out;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double probability(double x) {
/* 286 */     return 0.0D;
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
/*     */   public double logDensity(double x) {
/* 304 */     return FastMath.log(density(x));
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/distribution/AbstractRealDistribution.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */