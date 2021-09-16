/*     */ package org.apache.commons.math3.random;
/*     */ 
/*     */ import java.util.Random;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RandomAdaptor
/*     */   extends Random
/*     */   implements RandomGenerator
/*     */ {
/*     */   private static final long serialVersionUID = 2306581345647615033L;
/*     */   private final RandomGenerator randomGenerator;
/*     */   
/*     */   private RandomAdaptor() {
/*  39 */     this.randomGenerator = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RandomAdaptor(RandomGenerator randomGenerator) {
/*  47 */     this.randomGenerator = randomGenerator;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Random createAdaptor(RandomGenerator randomGenerator) {
/*  58 */     return new RandomAdaptor(randomGenerator);
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
/*     */   public boolean nextBoolean() {
/*  72 */     return this.randomGenerator.nextBoolean();
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
/*     */   public void nextBytes(byte[] bytes) {
/*  85 */     this.randomGenerator.nextBytes(bytes);
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
/*     */   public double nextDouble() {
/*  99 */     return this.randomGenerator.nextDouble();
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
/*     */   public float nextFloat() {
/* 113 */     return this.randomGenerator.nextFloat();
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
/*     */   public double nextGaussian() {
/* 128 */     return this.randomGenerator.nextGaussian();
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
/*     */   public int nextInt() {
/* 142 */     return this.randomGenerator.nextInt();
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
/*     */   public int nextInt(int n) {
/* 158 */     return this.randomGenerator.nextInt(n);
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
/*     */   public long nextLong() {
/* 172 */     return this.randomGenerator.nextLong();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSeed(int seed) {
/* 177 */     if (this.randomGenerator != null) {
/* 178 */       this.randomGenerator.setSeed(seed);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSeed(int[] seed) {
/* 184 */     if (this.randomGenerator != null) {
/* 185 */       this.randomGenerator.setSeed(seed);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSeed(long seed) {
/* 192 */     if (this.randomGenerator != null)
/* 193 */       this.randomGenerator.setSeed(seed); 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/random/RandomAdaptor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */