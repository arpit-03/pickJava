/*     */ package org.apache.commons.math3.random;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SynchronizedRandomGenerator
/*     */   implements RandomGenerator
/*     */ {
/*     */   private final RandomGenerator wrapped;
/*     */   
/*     */   public SynchronizedRandomGenerator(RandomGenerator rng) {
/*  42 */     this.wrapped = rng;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void setSeed(int seed) {
/*  49 */     this.wrapped.setSeed(seed);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void setSeed(int[] seed) {
/*  56 */     this.wrapped.setSeed(seed);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void setSeed(long seed) {
/*  63 */     this.wrapped.setSeed(seed);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void nextBytes(byte[] bytes) {
/*  70 */     this.wrapped.nextBytes(bytes);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized int nextInt() {
/*  77 */     return this.wrapped.nextInt();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized int nextInt(int n) {
/*  84 */     return this.wrapped.nextInt(n);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized long nextLong() {
/*  91 */     return this.wrapped.nextLong();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized boolean nextBoolean() {
/*  98 */     return this.wrapped.nextBoolean();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized float nextFloat() {
/* 105 */     return this.wrapped.nextFloat();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized double nextDouble() {
/* 112 */     return this.wrapped.nextDouble();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized double nextGaussian() {
/* 119 */     return this.wrapped.nextGaussian();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/random/SynchronizedRandomGenerator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */