/*    */ package org.apache.commons.math3.optim.nonlinear.scalar;
/*    */ 
/*    */ import org.apache.commons.math3.analysis.MultivariateVectorFunction;
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
/*    */ public class ObjectiveFunctionGradient
/*    */   implements OptimizationData
/*    */ {
/*    */   private final MultivariateVectorFunction gradient;
/*    */   
/*    */   public ObjectiveFunctionGradient(MultivariateVectorFunction g) {
/* 35 */     this.gradient = g;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public MultivariateVectorFunction getObjectiveFunctionGradient() {
/* 44 */     return this.gradient;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optim/nonlinear/scalar/ObjectiveFunctionGradient.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */