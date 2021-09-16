/*    */ package org.apache.commons.math3.random;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import org.apache.commons.math3.exception.DimensionMismatchException;
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
/*    */ public class UncorrelatedRandomVectorGenerator
/*    */   implements RandomVectorGenerator
/*    */ {
/*    */   private final NormalizedRandomGenerator generator;
/*    */   private final double[] mean;
/*    */   private final double[] standardDeviation;
/*    */   
/*    */   public UncorrelatedRandomVectorGenerator(double[] mean, double[] standardDeviation, NormalizedRandomGenerator generator) {
/* 55 */     if (mean.length != standardDeviation.length) {
/* 56 */       throw new DimensionMismatchException(mean.length, standardDeviation.length);
/*    */     }
/* 58 */     this.mean = (double[])mean.clone();
/* 59 */     this.standardDeviation = (double[])standardDeviation.clone();
/* 60 */     this.generator = generator;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public UncorrelatedRandomVectorGenerator(int dimension, NormalizedRandomGenerator generator) {
/* 72 */     this.mean = new double[dimension];
/* 73 */     this.standardDeviation = new double[dimension];
/* 74 */     Arrays.fill(this.standardDeviation, 1.0D);
/* 75 */     this.generator = generator;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double[] nextVector() {
/* 83 */     double[] random = new double[this.mean.length];
/* 84 */     for (int i = 0; i < random.length; i++) {
/* 85 */       random[i] = this.mean[i] + this.standardDeviation[i] * this.generator.nextNormalizedDouble();
/*    */     }
/*    */     
/* 88 */     return random;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/random/UncorrelatedRandomVectorGenerator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */