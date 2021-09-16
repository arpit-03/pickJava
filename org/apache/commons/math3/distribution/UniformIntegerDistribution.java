/*     */ package org.apache.commons.math3.distribution;
/*     */ 
/*     */ import org.apache.commons.math3.exception.NumberIsTooLargeException;
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
/*     */ 
/*     */ 
/*     */ public class UniformIntegerDistribution
/*     */   extends AbstractIntegerDistribution
/*     */ {
/*     */   private static final long serialVersionUID = 20120109L;
/*     */   private final int lower;
/*     */   private final int upper;
/*     */   
/*     */   public UniformIntegerDistribution(int lower, int upper) throws NumberIsTooLargeException {
/*  58 */     this((RandomGenerator)new Well19937c(), lower, upper);
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
/*     */   public UniformIntegerDistribution(RandomGenerator rng, int lower, int upper) throws NumberIsTooLargeException {
/*  75 */     super(rng);
/*     */     
/*  77 */     if (lower > upper) {
/*  78 */       throw new NumberIsTooLargeException(LocalizedFormats.LOWER_BOUND_NOT_BELOW_UPPER_BOUND, Integer.valueOf(lower), Integer.valueOf(upper), true);
/*     */     }
/*     */ 
/*     */     
/*  82 */     this.lower = lower;
/*  83 */     this.upper = upper;
/*     */   }
/*     */ 
/*     */   
/*     */   public double probability(int x) {
/*  88 */     if (x < this.lower || x > this.upper) {
/*  89 */       return 0.0D;
/*     */     }
/*  91 */     return 1.0D / (this.upper - this.lower + 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public double cumulativeProbability(int x) {
/*  96 */     if (x < this.lower) {
/*  97 */       return 0.0D;
/*     */     }
/*  99 */     if (x > this.upper) {
/* 100 */       return 1.0D;
/*     */     }
/* 102 */     return ((x - this.lower) + 1.0D) / ((this.upper - this.lower) + 1.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getNumericalMean() {
/* 112 */     return 0.5D * (this.lower + this.upper);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getNumericalVariance() {
/* 122 */     double n = (this.upper - this.lower + 1);
/* 123 */     return (n * n - 1.0D) / 12.0D;
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
/*     */   public int getSupportLowerBound() {
/* 135 */     return this.lower;
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
/*     */   public int getSupportUpperBound() {
/* 147 */     return this.upper;
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
/* 158 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int sample() {
/* 164 */     int max = this.upper - this.lower + 1;
/* 165 */     if (max <= 0) {
/*     */       int r;
/*     */ 
/*     */       
/*     */       do {
/* 170 */         r = this.random.nextInt();
/* 171 */       } while (r < this.lower || r > this.upper);
/*     */       
/* 173 */       return r;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 178 */     return this.lower + this.random.nextInt(max);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/distribution/UniformIntegerDistribution.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */