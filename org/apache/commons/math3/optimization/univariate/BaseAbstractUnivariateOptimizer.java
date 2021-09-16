/*     */ package org.apache.commons.math3.optimization.univariate;
/*     */ 
/*     */ import org.apache.commons.math3.analysis.UnivariateFunction;
/*     */ import org.apache.commons.math3.exception.MaxCountExceededException;
/*     */ import org.apache.commons.math3.exception.NullArgumentException;
/*     */ import org.apache.commons.math3.exception.TooManyEvaluationsException;
/*     */ import org.apache.commons.math3.optimization.ConvergenceChecker;
/*     */ import org.apache.commons.math3.optimization.GoalType;
/*     */ import org.apache.commons.math3.util.Incrementor;
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
/*     */ public abstract class BaseAbstractUnivariateOptimizer
/*     */   implements UnivariateOptimizer
/*     */ {
/*     */   private final ConvergenceChecker<UnivariatePointValuePair> checker;
/*  41 */   private final Incrementor evaluations = new Incrementor();
/*     */ 
/*     */   
/*     */   private GoalType goal;
/*     */ 
/*     */   
/*     */   private double searchMin;
/*     */   
/*     */   private double searchMax;
/*     */   
/*     */   private double searchStart;
/*     */   
/*     */   private UnivariateFunction function;
/*     */ 
/*     */   
/*     */   protected BaseAbstractUnivariateOptimizer(ConvergenceChecker<UnivariatePointValuePair> checker) {
/*  57 */     this.checker = checker;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxEvaluations() {
/*  62 */     return this.evaluations.getMaximalCount();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getEvaluations() {
/*  67 */     return this.evaluations.getCount();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GoalType getGoalType() {
/*  74 */     return this.goal;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getMin() {
/*  80 */     return this.searchMin;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getMax() {
/*  86 */     return this.searchMax;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getStartValue() {
/*  92 */     return this.searchStart;
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
/*     */   protected double computeObjectiveValue(double point) {
/*     */     try {
/* 105 */       this.evaluations.incrementCount();
/* 106 */     } catch (MaxCountExceededException e) {
/* 107 */       throw new TooManyEvaluationsException(e.getMax());
/*     */     } 
/* 109 */     return this.function.value(point);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UnivariatePointValuePair optimize(int maxEval, UnivariateFunction f, GoalType goalType, double min, double max, double startValue) {
/* 118 */     if (f == null) {
/* 119 */       throw new NullArgumentException();
/*     */     }
/* 121 */     if (goalType == null) {
/* 122 */       throw new NullArgumentException();
/*     */     }
/*     */ 
/*     */     
/* 126 */     this.searchMin = min;
/* 127 */     this.searchMax = max;
/* 128 */     this.searchStart = startValue;
/* 129 */     this.goal = goalType;
/* 130 */     this.function = f;
/* 131 */     this.evaluations.setMaximalCount(maxEval);
/* 132 */     this.evaluations.resetCount();
/*     */ 
/*     */     
/* 135 */     return doOptimize();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UnivariatePointValuePair optimize(int maxEval, UnivariateFunction f, GoalType goalType, double min, double max) {
/* 143 */     return optimize(maxEval, f, goalType, min, max, min + 0.5D * (max - min));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ConvergenceChecker<UnivariatePointValuePair> getConvergenceChecker() {
/* 150 */     return this.checker;
/*     */   }
/*     */   
/*     */   protected abstract UnivariatePointValuePair doOptimize();
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optimization/univariate/BaseAbstractUnivariateOptimizer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */