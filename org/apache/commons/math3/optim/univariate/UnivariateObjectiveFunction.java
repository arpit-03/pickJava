/*    */ package org.apache.commons.math3.optim.univariate;
/*    */ 
/*    */ import org.apache.commons.math3.analysis.UnivariateFunction;
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
/*    */ public class UnivariateObjectiveFunction
/*    */   implements OptimizationData
/*    */ {
/*    */   private final UnivariateFunction function;
/*    */   
/*    */   public UnivariateObjectiveFunction(UnivariateFunction f) {
/* 35 */     this.function = f;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public UnivariateFunction getObjectiveFunction() {
/* 44 */     return this.function;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optim/univariate/UnivariateObjectiveFunction.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */