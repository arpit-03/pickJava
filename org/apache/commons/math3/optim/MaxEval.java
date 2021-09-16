/*    */ package org.apache.commons.math3.optim;
/*    */ 
/*    */ import org.apache.commons.math3.exception.NotStrictlyPositiveException;
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
/*    */ public class MaxEval
/*    */   implements OptimizationData
/*    */ {
/*    */   private final int maxEval;
/*    */   
/*    */   public MaxEval(int max) {
/* 35 */     if (max <= 0) {
/* 36 */       throw new NotStrictlyPositiveException(Integer.valueOf(max));
/*    */     }
/*    */     
/* 39 */     this.maxEval = max;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMaxEval() {
/* 48 */     return this.maxEval;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static MaxEval unlimited() {
/* 59 */     return new MaxEval(2147483647);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optim/MaxEval.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */