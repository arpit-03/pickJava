/*    */ package org.apache.commons.math3.util;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import org.apache.commons.math3.exception.MathIllegalArgumentException;
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
/*    */ public class RandomPivotingStrategy
/*    */   implements PivotingStrategyInterface, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 20140713L;
/*    */   private final RandomGenerator random;
/*    */   
/*    */   public RandomPivotingStrategy(RandomGenerator random) {
/* 41 */     this.random = random;
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
/*    */   public int pivotIndex(double[] work, int begin, int end) throws MathIllegalArgumentException {
/* 53 */     MathArrays.verifyValues(work, begin, end - begin);
/* 54 */     return begin + this.random.nextInt(end - begin - 1);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/util/RandomPivotingStrategy.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */