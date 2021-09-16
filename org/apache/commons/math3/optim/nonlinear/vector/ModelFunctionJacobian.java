/*    */ package org.apache.commons.math3.optim.nonlinear.vector;
/*    */ 
/*    */ import org.apache.commons.math3.analysis.MultivariateMatrixFunction;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ @Deprecated
/*    */ public class ModelFunctionJacobian
/*    */   implements OptimizationData
/*    */ {
/*    */   private final MultivariateMatrixFunction jacobian;
/*    */   
/*    */   public ModelFunctionJacobian(MultivariateMatrixFunction j) {
/* 40 */     this.jacobian = j;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public MultivariateMatrixFunction getModelFunctionJacobian() {
/* 49 */     return this.jacobian;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optim/nonlinear/vector/ModelFunctionJacobian.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */