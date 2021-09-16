/*    */ package org.apache.commons.math3.optim.linear;
/*    */ 
/*    */ import org.apache.commons.math3.optim.OptimizationData;
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
/*    */ public class NonNegativeConstraint
/*    */   implements OptimizationData
/*    */ {
/*    */   private final boolean isRestricted;
/*    */   
/*    */   public NonNegativeConstraint(boolean restricted) {
/* 35 */     this.isRestricted = restricted;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isRestrictedToNonNegative() {
/* 45 */     return this.isRestricted;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optim/linear/NonNegativeConstraint.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */