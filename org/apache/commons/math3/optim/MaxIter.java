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
/*    */ public class MaxIter
/*    */   implements OptimizationData
/*    */ {
/*    */   private final int maxIter;
/*    */   
/*    */   public MaxIter(int max) {
/* 35 */     if (max <= 0) {
/* 36 */       throw new NotStrictlyPositiveException(Integer.valueOf(max));
/*    */     }
/*    */     
/* 39 */     this.maxIter = max;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMaxIter() {
/* 48 */     return this.maxIter;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static MaxIter unlimited() {
/* 59 */     return new MaxIter(2147483647);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optim/MaxIter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */