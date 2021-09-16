/*     */ package org.apache.commons.math3.random;
/*     */ 
/*     */ import org.apache.commons.math3.exception.NotStrictlyPositiveException;
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
/*     */ public abstract class AbstractRandomGenerator
/*     */   implements RandomGenerator
/*     */ {
/*  44 */   private double cachedNormalDeviate = Double.NaN;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/*  61 */     this.cachedNormalDeviate = Double.NaN;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSeed(int seed) {
/*  66 */     setSeed(seed);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSeed(int[] seed) {
/*  72 */     long prime = 4294967291L;
/*     */     
/*  74 */     long combined = 0L;
/*  75 */     for (int s : seed) {
/*  76 */       combined = combined * 4294967291L + s;
/*     */     }
/*  78 */     setSeed(combined);
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
/*     */   public abstract void setSeed(long paramLong);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void nextBytes(byte[] bytes) {
/* 106 */     int bytesOut = 0;
/* 107 */     while (bytesOut < bytes.length) {
/* 108 */       int randInt = nextInt();
/* 109 */       for (int i = 0; i < 3; i++) {
/* 110 */         if (i > 0) {
/* 111 */           randInt >>= 8;
/*     */         }
/* 113 */         bytes[bytesOut++] = (byte)randInt;
/* 114 */         if (bytesOut == bytes.length) {
/*     */           return;
/*     */         }
/*     */       } 
/*     */     } 
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
/*     */   public int nextInt() {
/* 136 */     return (int)((2.0D * nextDouble() - 1.0D) * 2.147483647E9D);
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
/*     */   public int nextInt(int n) {
/* 156 */     if (n <= 0) {
/* 157 */       throw new NotStrictlyPositiveException(Integer.valueOf(n));
/*     */     }
/* 159 */     int result = (int)(nextDouble() * n);
/* 160 */     return (result < n) ? result : (n - 1);
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
/*     */   public long nextLong() {
/* 178 */     return (long)((2.0D * nextDouble() - 1.0D) * 9.223372036854776E18D);
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
/*     */   public boolean nextBoolean() {
/* 196 */     return (nextDouble() <= 0.5D);
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
/*     */   public float nextFloat() {
/* 214 */     return (float)nextDouble();
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
/*     */   public abstract double nextDouble();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double nextGaussian() {
/* 252 */     if (!Double.isNaN(this.cachedNormalDeviate)) {
/* 253 */       double dev = this.cachedNormalDeviate;
/* 254 */       this.cachedNormalDeviate = Double.NaN;
/* 255 */       return dev;
/*     */     } 
/* 257 */     double v1 = 0.0D;
/* 258 */     double v2 = 0.0D;
/* 259 */     double s = 1.0D;
/* 260 */     while (s >= 1.0D) {
/* 261 */       v1 = 2.0D * nextDouble() - 1.0D;
/* 262 */       v2 = 2.0D * nextDouble() - 1.0D;
/* 263 */       s = v1 * v1 + v2 * v2;
/*     */     } 
/* 265 */     if (s != 0.0D) {
/* 266 */       s = FastMath.sqrt(-2.0D * FastMath.log(s) / s);
/*     */     }
/* 268 */     this.cachedNormalDeviate = v2 * s;
/* 269 */     return v1 * s;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/random/AbstractRandomGenerator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */