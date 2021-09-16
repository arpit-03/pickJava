/*     */ package org.apache.commons.math3.distribution;
/*     */ 
/*     */ import org.apache.commons.math3.exception.NumberIsTooLargeException;
/*     */ import org.apache.commons.math3.exception.OutOfRangeException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.random.RandomGenerator;
/*     */ import org.apache.commons.math3.random.Well19937c;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class UniformRealDistribution
/*     */   extends AbstractRealDistribution
/*     */ {
/*     */   @Deprecated
/*     */   public static final double DEFAULT_INVERSE_ABSOLUTE_ACCURACY = 1.0E-9D;
/*     */   private static final long serialVersionUID = 20120109L;
/*     */   private final double lower;
/*     */   private final double upper;
/*     */   
/*     */   public UniformRealDistribution() {
/*  59 */     this(0.0D, 1.0D);
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
/*     */   public UniformRealDistribution(double lower, double upper) throws NumberIsTooLargeException {
/*  79 */     this((RandomGenerator)new Well19937c(), lower, upper);
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
/*     */   @Deprecated
/*     */   public UniformRealDistribution(double lower, double upper, double inverseCumAccuracy) throws NumberIsTooLargeException {
/*  95 */     this((RandomGenerator)new Well19937c(), lower, upper);
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
/*     */   @Deprecated
/*     */   public UniformRealDistribution(RandomGenerator rng, double lower, double upper, double inverseCumAccuracy) {
/* 116 */     this(rng, lower, upper);
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
/*     */   public UniformRealDistribution(RandomGenerator rng, double lower, double upper) throws NumberIsTooLargeException {
/* 132 */     super(rng);
/* 133 */     if (lower >= upper) {
/* 134 */       throw new NumberIsTooLargeException(LocalizedFormats.LOWER_BOUND_NOT_BELOW_UPPER_BOUND, Double.valueOf(lower), Double.valueOf(upper), false);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 139 */     this.lower = lower;
/* 140 */     this.upper = upper;
/*     */   }
/*     */ 
/*     */   
/*     */   public double density(double x) {
/* 145 */     if (x < this.lower || x > this.upper) {
/* 146 */       return 0.0D;
/*     */     }
/* 148 */     return 1.0D / (this.upper - this.lower);
/*     */   }
/*     */ 
/*     */   
/*     */   public double cumulativeProbability(double x) {
/* 153 */     if (x <= this.lower) {
/* 154 */       return 0.0D;
/*     */     }
/* 156 */     if (x >= this.upper) {
/* 157 */       return 1.0D;
/*     */     }
/* 159 */     return (x - this.lower) / (this.upper - this.lower);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double inverseCumulativeProbability(double p) throws OutOfRangeException {
/* 166 */     if (p < 0.0D || p > 1.0D) {
/* 167 */       throw new OutOfRangeException(Double.valueOf(p), Integer.valueOf(0), Integer.valueOf(1));
/*     */     }
/* 169 */     return p * (this.upper - this.lower) + this.lower;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getNumericalMean() {
/* 179 */     return 0.5D * (this.lower + this.upper);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getNumericalVariance() {
/* 189 */     double ul = this.upper - this.lower;
/* 190 */     return ul * ul / 12.0D;
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
/*     */   public double getSupportLowerBound() {
/* 202 */     return this.lower;
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
/* 214 */     return this.upper;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSupportLowerBoundInclusive() {
/* 219 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSupportUpperBoundInclusive() {
/* 224 */     return true;
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
/* 235 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double sample() {
/* 241 */     double u = this.random.nextDouble();
/* 242 */     return u * this.upper + (1.0D - u) * this.lower;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/distribution/UniformRealDistribution.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */