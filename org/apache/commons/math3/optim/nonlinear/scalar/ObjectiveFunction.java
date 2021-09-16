/*    */ package org.apache.commons.math3.optim.nonlinear.scalar;
/*    */ 
/*    */ import org.apache.commons.math3.analysis.MultivariateFunction;
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
/*    */ public class ObjectiveFunction
/*    */   implements OptimizationData
/*    */ {
/*    */   private final MultivariateFunction function;
/*    */   
/*    */   public ObjectiveFunction(MultivariateFunction f) {
/* 35 */     this.function = f;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public MultivariateFunction getObjectiveFunction() {
/* 44 */     return this.function;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optim/nonlinear/scalar/ObjectiveFunction.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */