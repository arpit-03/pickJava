/*    */ package org.apache.commons.math3.distribution;
/*    */ 
/*    */ import org.apache.commons.math3.exception.NotStrictlyPositiveException;
/*    */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*    */ import org.apache.commons.math3.random.RandomGenerator;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class AbstractMultivariateRealDistribution
/*    */   implements MultivariateRealDistribution
/*    */ {
/*    */   protected final RandomGenerator random;
/*    */   private final int dimension;
/*    */   
/*    */   protected AbstractMultivariateRealDistribution(RandomGenerator rng, int n) {
/* 41 */     this.random = rng;
/* 42 */     this.dimension = n;
/*    */   }
/*    */ 
/*    */   
/*    */   public void reseedRandomGenerator(long seed) {
/* 47 */     this.random.setSeed(seed);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getDimension() {
/* 52 */     return this.dimension;
/*    */   }
/*    */ 
/*    */   
/*    */   public abstract double[] sample();
/*    */ 
/*    */   
/*    */   public double[][] sample(int sampleSize) {
/* 60 */     if (sampleSize <= 0) {
/* 61 */       throw new NotStrictlyPositiveException(LocalizedFormats.NUMBER_OF_SAMPLES, Integer.valueOf(sampleSize));
/*    */     }
/*    */     
/* 64 */     double[][] out = new double[sampleSize][this.dimension];
/* 65 */     for (int i = 0; i < sampleSize; i++) {
/* 66 */       out[i] = sample();
/*    */     }
/* 68 */     return out;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/distribution/AbstractMultivariateRealDistribution.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */