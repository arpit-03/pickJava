/*    */ package org.apache.commons.math3.random;
/*    */ 
/*    */ import org.apache.commons.math3.util.FastMath;
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
/*    */ public class UniformRandomGenerator
/*    */   implements NormalizedRandomGenerator
/*    */ {
/* 36 */   private static final double SQRT3 = FastMath.sqrt(3.0D);
/*    */ 
/*    */ 
/*    */   
/*    */   private final RandomGenerator generator;
/*    */ 
/*    */ 
/*    */   
/*    */   public UniformRandomGenerator(RandomGenerator generator) {
/* 45 */     this.generator = generator;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double nextNormalizedDouble() {
/* 54 */     return SQRT3 * (2.0D * this.generator.nextDouble() - 1.0D);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/random/UniformRandomGenerator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */