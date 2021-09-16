/*     */ package org.apache.commons.math3.random;
/*     */ 
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.NotPositiveException;
/*     */ import org.apache.commons.math3.exception.NullArgumentException;
/*     */ import org.apache.commons.math3.exception.OutOfRangeException;
/*     */ import org.apache.commons.math3.util.MathUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HaltonSequenceGenerator
/*     */   implements RandomVectorGenerator
/*     */ {
/*  57 */   private static final int[] PRIMES = new int[] { 2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  64 */   private static final int[] WEIGHTS = new int[] { 1, 2, 3, 3, 8, 11, 12, 14, 7, 18, 12, 13, 17, 18, 29, 14, 18, 43, 41, 44, 40, 30, 47, 65, 71, 28, 40, 60, 79, 89, 56, 50, 52, 61, 108, 56, 66, 63, 60, 66 };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final int dimension;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  74 */   private int count = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final int[] base;
/*     */ 
/*     */ 
/*     */   
/*     */   private final int[] weight;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HaltonSequenceGenerator(int dimension) throws OutOfRangeException {
/*  89 */     this(dimension, PRIMES, WEIGHTS);
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
/*     */   public HaltonSequenceGenerator(int dimension, int[] bases, int[] weights) throws NullArgumentException, OutOfRangeException, DimensionMismatchException {
/* 107 */     MathUtils.checkNotNull(bases);
/*     */     
/* 109 */     if (dimension < 1 || dimension > bases.length) {
/* 110 */       throw new OutOfRangeException(Integer.valueOf(dimension), Integer.valueOf(1), Integer.valueOf(PRIMES.length));
/*     */     }
/*     */     
/* 113 */     if (weights != null && weights.length != bases.length) {
/* 114 */       throw new DimensionMismatchException(weights.length, bases.length);
/*     */     }
/*     */     
/* 117 */     this.dimension = dimension;
/* 118 */     this.base = (int[])bases.clone();
/* 119 */     this.weight = (weights == null) ? null : (int[])weights.clone();
/* 120 */     this.count = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public double[] nextVector() {
/* 125 */     double[] v = new double[this.dimension];
/* 126 */     for (int i = 0; i < this.dimension; i++) {
/* 127 */       int index = this.count;
/* 128 */       double f = 1.0D / this.base[i];
/*     */       
/* 130 */       int j = 0;
/* 131 */       while (index > 0) {
/* 132 */         int digit = scramble(i, j, this.base[i], index % this.base[i]);
/* 133 */         v[i] = v[i] + f * digit;
/* 134 */         index /= this.base[i];
/* 135 */         f /= this.base[i];
/*     */       } 
/*     */     } 
/* 138 */     this.count++;
/* 139 */     return v;
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
/*     */   protected int scramble(int i, int j, int b, int digit) {
/* 156 */     return (this.weight != null) ? (this.weight[i] * digit % b) : digit;
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
/*     */   public double[] skipTo(int index) throws NotPositiveException {
/* 169 */     this.count = index;
/* 170 */     return nextVector();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNextIndex() {
/* 180 */     return this.count;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/random/HaltonSequenceGenerator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */