/*     */ package org.apache.commons.math3.random;
/*     */ 
/*     */ import java.util.Random;
/*     */ import org.apache.commons.math3.exception.NotStrictlyPositiveException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RandomGeneratorFactory
/*     */ {
/*     */   public static RandomGenerator createRandomGenerator(final Random rng) {
/*  42 */     return new RandomGenerator()
/*     */       {
/*     */         public void setSeed(int seed) {
/*  45 */           rng.setSeed(seed);
/*     */         }
/*     */ 
/*     */         
/*     */         public void setSeed(int[] seed) {
/*  50 */           rng.setSeed(RandomGeneratorFactory.convertToLong(seed));
/*     */         }
/*     */ 
/*     */         
/*     */         public void setSeed(long seed) {
/*  55 */           rng.setSeed(seed);
/*     */         }
/*     */ 
/*     */         
/*     */         public void nextBytes(byte[] bytes) {
/*  60 */           rng.nextBytes(bytes);
/*     */         }
/*     */ 
/*     */         
/*     */         public int nextInt() {
/*  65 */           return rng.nextInt();
/*     */         }
/*     */ 
/*     */         
/*     */         public int nextInt(int n) {
/*  70 */           if (n <= 0) {
/*  71 */             throw new NotStrictlyPositiveException(Integer.valueOf(n));
/*     */           }
/*  73 */           return rng.nextInt(n);
/*     */         }
/*     */ 
/*     */         
/*     */         public long nextLong() {
/*  78 */           return rng.nextLong();
/*     */         }
/*     */ 
/*     */         
/*     */         public boolean nextBoolean() {
/*  83 */           return rng.nextBoolean();
/*     */         }
/*     */ 
/*     */         
/*     */         public float nextFloat() {
/*  88 */           return rng.nextFloat();
/*     */         }
/*     */ 
/*     */         
/*     */         public double nextDouble() {
/*  93 */           return rng.nextDouble();
/*     */         }
/*     */ 
/*     */         
/*     */         public double nextGaussian() {
/*  98 */           return rng.nextGaussian();
/*     */         }
/*     */       };
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
/*     */   public static long convertToLong(int[] seed) {
/* 112 */     long prime = 4294967291L;
/*     */     
/* 114 */     long combined = 0L;
/* 115 */     for (int s : seed) {
/* 116 */       combined = combined * 4294967291L + s;
/*     */     }
/*     */     
/* 119 */     return combined;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/random/RandomGeneratorFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */