/*    */ package org.apache.commons.math3.random;
/*    */ 
/*    */ import java.util.Random;
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
/*    */ public class JDKRandomGenerator
/*    */   extends Random
/*    */   implements RandomGenerator
/*    */ {
/*    */   private static final long serialVersionUID = -7745277476784028798L;
/*    */   
/*    */   public JDKRandomGenerator() {}
/*    */   
/*    */   public JDKRandomGenerator(int seed) {
/* 46 */     setSeed(seed);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setSeed(int seed) {
/* 51 */     setSeed(seed);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setSeed(int[] seed) {
/* 56 */     setSeed(RandomGeneratorFactory.convertToLong(seed));
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/random/JDKRandomGenerator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */