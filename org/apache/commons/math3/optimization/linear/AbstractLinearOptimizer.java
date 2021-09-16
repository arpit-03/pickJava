/*     */ package org.apache.commons.math3.optimization.linear;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import org.apache.commons.math3.exception.MathIllegalStateException;
/*     */ import org.apache.commons.math3.exception.MaxCountExceededException;
/*     */ import org.apache.commons.math3.optimization.GoalType;
/*     */ import org.apache.commons.math3.optimization.PointValuePair;
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
/*     */ public abstract class AbstractLinearOptimizer
/*     */   implements LinearOptimizer
/*     */ {
/*     */   public static final int DEFAULT_MAX_ITERATIONS = 100;
/*     */   private LinearObjectiveFunction function;
/*     */   private Collection<LinearConstraint> linearConstraints;
/*     */   private GoalType goal;
/*     */   private boolean nonNegative;
/*     */   private int maxIterations;
/*     */   private int iterations;
/*     */   
/*     */   protected AbstractLinearOptimizer() {
/*  78 */     setMaxIterations(100);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean restrictToNonNegative() {
/*  85 */     return this.nonNegative;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected GoalType getGoalType() {
/*  92 */     return this.goal;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected LinearObjectiveFunction getFunction() {
/*  99 */     return this.function;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Collection<LinearConstraint> getConstraints() {
/* 106 */     return Collections.unmodifiableCollection(this.linearConstraints);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setMaxIterations(int maxIterations) {
/* 111 */     this.maxIterations = maxIterations;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxIterations() {
/* 116 */     return this.maxIterations;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getIterations() {
/* 121 */     return this.iterations;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void incrementIterationsCounter() throws MaxCountExceededException {
/* 130 */     if (++this.iterations > this.maxIterations) {
/* 131 */       throw new MaxCountExceededException(Integer.valueOf(this.maxIterations));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PointValuePair optimize(LinearObjectiveFunction f, Collection<LinearConstraint> constraints, GoalType goalType, boolean restrictToNonNegative) throws MathIllegalStateException {
/* 142 */     this.function = f;
/* 143 */     this.linearConstraints = constraints;
/* 144 */     this.goal = goalType;
/* 145 */     this.nonNegative = restrictToNonNegative;
/*     */     
/* 147 */     this.iterations = 0;
/*     */ 
/*     */     
/* 150 */     return doOptimize();
/*     */   }
/*     */   
/*     */   protected abstract PointValuePair doOptimize() throws MathIllegalStateException;
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optimization/linear/AbstractLinearOptimizer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */