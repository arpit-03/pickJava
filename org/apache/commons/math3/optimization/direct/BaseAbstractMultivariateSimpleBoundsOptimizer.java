/*    */ package org.apache.commons.math3.optimization.direct;
/*    */ 
/*    */ import org.apache.commons.math3.analysis.MultivariateFunction;
/*    */ import org.apache.commons.math3.optimization.BaseMultivariateOptimizer;
/*    */ import org.apache.commons.math3.optimization.BaseMultivariateSimpleBoundsOptimizer;
/*    */ import org.apache.commons.math3.optimization.ConvergenceChecker;
/*    */ import org.apache.commons.math3.optimization.GoalType;
/*    */ import org.apache.commons.math3.optimization.InitialGuess;
/*    */ import org.apache.commons.math3.optimization.OptimizationData;
/*    */ import org.apache.commons.math3.optimization.PointValuePair;
/*    */ import org.apache.commons.math3.optimization.SimpleBounds;
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
/*    */ public abstract class BaseAbstractMultivariateSimpleBoundsOptimizer<FUNC extends MultivariateFunction>
/*    */   extends BaseAbstractMultivariateOptimizer<FUNC>
/*    */   implements BaseMultivariateOptimizer<FUNC>, BaseMultivariateSimpleBoundsOptimizer<FUNC>
/*    */ {
/*    */   @Deprecated
/*    */   protected BaseAbstractMultivariateSimpleBoundsOptimizer() {}
/*    */   
/*    */   protected BaseAbstractMultivariateSimpleBoundsOptimizer(ConvergenceChecker<PointValuePair> checker) {
/* 63 */     super(checker);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PointValuePair optimize(int maxEval, FUNC f, GoalType goalType, double[] startPoint) {
/* 70 */     return optimizeInternal(maxEval, f, goalType, new OptimizationData[] { (OptimizationData)new InitialGuess(startPoint) });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PointValuePair optimize(int maxEval, FUNC f, GoalType goalType, double[] startPoint, double[] lower, double[] upper) {
/* 78 */     return optimizeInternal(maxEval, f, goalType, new OptimizationData[] { (OptimizationData)new InitialGuess(startPoint), (OptimizationData)new SimpleBounds(lower, upper) });
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optimization/direct/BaseAbstractMultivariateSimpleBoundsOptimizer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */