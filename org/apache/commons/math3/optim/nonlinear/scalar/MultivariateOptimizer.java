/*     */ package org.apache.commons.math3.optim.nonlinear.scalar;
/*     */ 
/*     */ import org.apache.commons.math3.analysis.MultivariateFunction;
/*     */ import org.apache.commons.math3.exception.TooManyEvaluationsException;
/*     */ import org.apache.commons.math3.optim.BaseMultivariateOptimizer;
/*     */ import org.apache.commons.math3.optim.ConvergenceChecker;
/*     */ import org.apache.commons.math3.optim.OptimizationData;
/*     */ import org.apache.commons.math3.optim.PointValuePair;
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
/*     */ public abstract class MultivariateOptimizer
/*     */   extends BaseMultivariateOptimizer<PointValuePair>
/*     */ {
/*     */   private MultivariateFunction function;
/*     */   private GoalType goal;
/*     */   
/*     */   protected MultivariateOptimizer(ConvergenceChecker<PointValuePair> checker) {
/*  42 */     super(checker);
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
/*     */   public PointValuePair optimize(OptimizationData... optData) throws TooManyEvaluationsException {
/*  63 */     return (PointValuePair)super.optimize(optData);
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
/*     */   protected void parseOptimizationData(OptimizationData... optData) {
/*  80 */     super.parseOptimizationData(optData);
/*     */ 
/*     */ 
/*     */     
/*  84 */     for (OptimizationData data : optData) {
/*  85 */       if (data instanceof GoalType) {
/*  86 */         this.goal = (GoalType)data;
/*     */       
/*     */       }
/*  89 */       else if (data instanceof ObjectiveFunction) {
/*  90 */         this.function = ((ObjectiveFunction)data).getObjectiveFunction();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GoalType getGoalType() {
/* 100 */     return this.goal;
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
/*     */   public double computeObjectiveValue(double[] params) {
/* 114 */     incrementEvaluationCount();
/* 115 */     return this.function.value(params);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optim/nonlinear/scalar/MultivariateOptimizer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */