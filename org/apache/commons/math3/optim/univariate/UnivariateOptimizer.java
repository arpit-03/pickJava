/*     */ package org.apache.commons.math3.optim.univariate;
/*     */ 
/*     */ import org.apache.commons.math3.analysis.UnivariateFunction;
/*     */ import org.apache.commons.math3.exception.TooManyEvaluationsException;
/*     */ import org.apache.commons.math3.exception.TooManyIterationsException;
/*     */ import org.apache.commons.math3.optim.BaseOptimizer;
/*     */ import org.apache.commons.math3.optim.ConvergenceChecker;
/*     */ import org.apache.commons.math3.optim.OptimizationData;
/*     */ import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;
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
/*     */ public abstract class UnivariateOptimizer
/*     */   extends BaseOptimizer<UnivariatePointValuePair>
/*     */ {
/*     */   private UnivariateFunction function;
/*     */   private GoalType goal;
/*     */   private double start;
/*     */   private double min;
/*     */   private double max;
/*     */   
/*     */   protected UnivariateOptimizer(ConvergenceChecker<UnivariatePointValuePair> checker) {
/*  48 */     super(checker);
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
/*     */   public UnivariatePointValuePair optimize(OptimizationData... optData) throws TooManyEvaluationsException {
/*  70 */     return (UnivariatePointValuePair)super.optimize(optData);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GoalType getGoalType() {
/*  77 */     return this.goal;
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
/*     */   protected void parseOptimizationData(OptimizationData... optData) {
/*  95 */     super.parseOptimizationData(optData);
/*     */ 
/*     */ 
/*     */     
/*  99 */     for (OptimizationData data : optData) {
/* 100 */       if (data instanceof SearchInterval) {
/* 101 */         SearchInterval interval = (SearchInterval)data;
/* 102 */         this.min = interval.getMin();
/* 103 */         this.max = interval.getMax();
/* 104 */         this.start = interval.getStartValue();
/*     */       
/*     */       }
/* 107 */       else if (data instanceof UnivariateObjectiveFunction) {
/* 108 */         this.function = ((UnivariateObjectiveFunction)data).getObjectiveFunction();
/*     */       
/*     */       }
/* 111 */       else if (data instanceof GoalType) {
/* 112 */         this.goal = (GoalType)data;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getStartValue() {
/* 122 */     return this.start;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getMin() {
/* 128 */     return this.min;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getMax() {
/* 134 */     return this.max;
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
/*     */   protected double computeObjectiveValue(double x) {
/* 148 */     incrementEvaluationCount();
/* 149 */     return this.function.value(x);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optim/univariate/UnivariateOptimizer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */