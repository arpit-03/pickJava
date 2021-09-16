/*    */ package org.apache.commons.math3.genetics;
/*    */ 
/*    */ import org.apache.commons.math3.exception.NumberIsTooSmallException;
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
/*    */ public class FixedGenerationCount
/*    */   implements StoppingCondition
/*    */ {
/* 30 */   private int numGenerations = 0;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private final int maxGenerations;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public FixedGenerationCount(int maxGenerations) throws NumberIsTooSmallException {
/* 42 */     if (maxGenerations <= 0) {
/* 43 */       throw new NumberIsTooSmallException(Integer.valueOf(maxGenerations), Integer.valueOf(1), true);
/*    */     }
/* 45 */     this.maxGenerations = maxGenerations;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isSatisfied(Population population) {
/* 56 */     if (this.numGenerations < this.maxGenerations) {
/* 57 */       this.numGenerations++;
/* 58 */       return false;
/*    */     } 
/* 60 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getNumGenerations() {
/* 68 */     return this.numGenerations;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/genetics/FixedGenerationCount.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */