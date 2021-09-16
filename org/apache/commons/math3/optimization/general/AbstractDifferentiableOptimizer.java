/*    */ package org.apache.commons.math3.optimization.general;
/*    */ 
/*    */ import org.apache.commons.math3.analysis.MultivariateFunction;
/*    */ import org.apache.commons.math3.analysis.MultivariateVectorFunction;
/*    */ import org.apache.commons.math3.analysis.differentiation.GradientFunction;
/*    */ import org.apache.commons.math3.analysis.differentiation.MultivariateDifferentiableFunction;
/*    */ import org.apache.commons.math3.exception.TooManyEvaluationsException;
/*    */ import org.apache.commons.math3.optimization.ConvergenceChecker;
/*    */ import org.apache.commons.math3.optimization.GoalType;
/*    */ import org.apache.commons.math3.optimization.InitialGuess;
/*    */ import org.apache.commons.math3.optimization.OptimizationData;
/*    */ import org.apache.commons.math3.optimization.PointValuePair;
/*    */ import org.apache.commons.math3.optimization.direct.BaseAbstractMultivariateOptimizer;
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
/*    */ @Deprecated
/*    */ public abstract class AbstractDifferentiableOptimizer
/*    */   extends BaseAbstractMultivariateOptimizer<MultivariateDifferentiableFunction>
/*    */ {
/*    */   private MultivariateVectorFunction gradient;
/*    */   
/*    */   protected AbstractDifferentiableOptimizer(ConvergenceChecker<PointValuePair> checker) {
/* 50 */     super(checker);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected double[] computeObjectiveGradient(double[] evaluationPoint) {
/* 60 */     return this.gradient.value(evaluationPoint);
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
/*    */   @Deprecated
/*    */   protected PointValuePair optimizeInternal(int maxEval, MultivariateDifferentiableFunction f, GoalType goalType, double[] startPoint) {
/* 75 */     return optimizeInternal(maxEval, f, goalType, new OptimizationData[] { (OptimizationData)new InitialGuess(startPoint) });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected PointValuePair optimizeInternal(int maxEval, MultivariateDifferentiableFunction f, GoalType goalType, OptimizationData... optData) {
/* 85 */     this.gradient = (MultivariateVectorFunction)new GradientFunction(f);
/*    */ 
/*    */     
/* 88 */     return super.optimizeInternal(maxEval, (MultivariateFunction)f, goalType, optData);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optimization/general/AbstractDifferentiableOptimizer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */