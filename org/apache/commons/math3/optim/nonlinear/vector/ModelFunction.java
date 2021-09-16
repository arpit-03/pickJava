/*    */ package org.apache.commons.math3.optim.nonlinear.vector;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Deprecated
/*    */ public class ModelFunction
/*    */   implements OptimizationData
/*    */ {
/*    */   private final MultivariateVectorFunction model;
/*    */   
/*    */   public ModelFunction(MultivariateVectorFunction m) {
/* 40 */     this.model = m;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public MultivariateVectorFunction getModelFunction() {
/* 49 */     return this.model;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optim/nonlinear/vector/ModelFunction.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */