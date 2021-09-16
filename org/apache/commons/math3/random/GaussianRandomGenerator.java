/*    */ package org.apache.commons.math3.random;
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
/*    */ public class GaussianRandomGenerator
/*    */   implements NormalizedRandomGenerator
/*    */ {
/*    */   private final RandomGenerator generator;
/*    */   
/*    */   public GaussianRandomGenerator(RandomGenerator generator) {
/* 36 */     this.generator = generator;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double nextNormalizedDouble() {
/* 43 */     return this.generator.nextGaussian();
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/random/GaussianRandomGenerator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */