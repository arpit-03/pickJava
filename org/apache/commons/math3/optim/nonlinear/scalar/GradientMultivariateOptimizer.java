/*    */ package org.apache.commons.math3.optim.nonlinear.scalar;
/*    */ 
/*    */ import org.apache.commons.math3.analysis.MultivariateVectorFunction;
/*    */ import org.apache.commons.math3.exception.TooManyEvaluationsException;
/*    */ import org.apache.commons.math3.optim.ConvergenceChecker;
/*    */ import org.apache.commons.math3.optim.OptimizationData;
/*    */ import org.apache.commons.math3.optim.PointValuePair;
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
/*    */ 
/*    */ public abstract class GradientMultivariateOptimizer
/*    */   extends MultivariateOptimizer
/*    */ {
/*    */   private MultivariateVectorFunction gradient;
/*    */   
/*    */   protected GradientMultivariateOptimizer(ConvergenceChecker<PointValuePair> checker) {
/* 43 */     super(checker);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected double[] computeObjectiveGradient(double[] params) {
/* 53 */     return this.gradient.value(params);
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PointValuePair optimize(OptimizationData... optData) throws TooManyEvaluationsException {
/* 73 */     return super.optimize(optData);
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
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void parseOptimizationData(OptimizationData... optData) {
/* 89 */     super.parseOptimizationData(optData);
/*    */ 
/*    */ 
/*    */     
/* 93 */     for (OptimizationData data : optData) {
/* 94 */       if (data instanceof ObjectiveFunctionGradient) {
/* 95 */         this.gradient = ((ObjectiveFunctionGradient)data).getObjectiveFunctionGradient();
/*    */         break;
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optim/nonlinear/scalar/GradientMultivariateOptimizer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */