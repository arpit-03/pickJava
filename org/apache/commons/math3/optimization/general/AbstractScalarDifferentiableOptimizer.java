/*     */ package org.apache.commons.math3.optimization.general;
/*     */ 
/*     */ import org.apache.commons.math3.analysis.DifferentiableMultivariateFunction;
/*     */ import org.apache.commons.math3.analysis.FunctionUtils;
/*     */ import org.apache.commons.math3.analysis.MultivariateFunction;
/*     */ import org.apache.commons.math3.analysis.MultivariateVectorFunction;
/*     */ import org.apache.commons.math3.analysis.differentiation.MultivariateDifferentiableFunction;
/*     */ import org.apache.commons.math3.optimization.ConvergenceChecker;
/*     */ import org.apache.commons.math3.optimization.DifferentiableMultivariateOptimizer;
/*     */ import org.apache.commons.math3.optimization.GoalType;
/*     */ import org.apache.commons.math3.optimization.PointValuePair;
/*     */ import org.apache.commons.math3.optimization.direct.BaseAbstractMultivariateOptimizer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Deprecated
/*     */ public abstract class AbstractScalarDifferentiableOptimizer
/*     */   extends BaseAbstractMultivariateOptimizer<DifferentiableMultivariateFunction>
/*     */   implements DifferentiableMultivariateOptimizer
/*     */ {
/*     */   private MultivariateVectorFunction gradient;
/*     */   
/*     */   @Deprecated
/*     */   protected AbstractScalarDifferentiableOptimizer() {}
/*     */   
/*     */   protected AbstractScalarDifferentiableOptimizer(ConvergenceChecker<PointValuePair> checker) {
/*  61 */     super(checker);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected double[] computeObjectiveGradient(double[] evaluationPoint) {
/*  73 */     return this.gradient.value(evaluationPoint);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected PointValuePair optimizeInternal(int maxEval, DifferentiableMultivariateFunction f, GoalType goalType, double[] startPoint) {
/*  83 */     this.gradient = f.gradient();
/*     */     
/*  85 */     return super.optimizeInternal(maxEval, (MultivariateFunction)f, goalType, startPoint);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PointValuePair optimize(int maxEval, MultivariateDifferentiableFunction f, GoalType goalType, double[] startPoint) {
/* 109 */     return optimizeInternal(maxEval, FunctionUtils.toDifferentiableMultivariateFunction(f), goalType, startPoint);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optimization/general/AbstractScalarDifferentiableOptimizer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */