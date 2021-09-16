/*     */ package org.apache.commons.math3.optim.nonlinear.scalar;
/*     */ 
/*     */ import org.apache.commons.math3.analysis.UnivariateFunction;
/*     */ import org.apache.commons.math3.optim.ConvergenceChecker;
/*     */ import org.apache.commons.math3.optim.MaxEval;
/*     */ import org.apache.commons.math3.optim.OptimizationData;
/*     */ import org.apache.commons.math3.optim.univariate.BracketFinder;
/*     */ import org.apache.commons.math3.optim.univariate.BrentOptimizer;
/*     */ import org.apache.commons.math3.optim.univariate.SearchInterval;
/*     */ import org.apache.commons.math3.optim.univariate.SimpleUnivariateValueChecker;
/*     */ import org.apache.commons.math3.optim.univariate.UnivariateObjectiveFunction;
/*     */ import org.apache.commons.math3.optim.univariate.UnivariateOptimizer;
/*     */ import org.apache.commons.math3.optim.univariate.UnivariatePointValuePair;
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
/*     */ public class LineSearch
/*     */ {
/*     */   private static final double REL_TOL_UNUSED = 1.0E-15D;
/*     */   private static final double ABS_TOL_UNUSED = 4.9E-324D;
/*     */   private final UnivariateOptimizer lineOptimizer;
/*  55 */   private final BracketFinder bracket = new BracketFinder();
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
/*     */   private final double initialBracketingRange;
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
/*     */   private final MultivariateOptimizer mainOptimizer;
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
/*     */   public LineSearch(MultivariateOptimizer optimizer, double relativeTolerance, double absoluteTolerance, double initialBracketingRange) {
/*  93 */     this.mainOptimizer = optimizer;
/*  94 */     this.lineOptimizer = (UnivariateOptimizer)new BrentOptimizer(1.0E-15D, Double.MIN_VALUE, (ConvergenceChecker)new SimpleUnivariateValueChecker(relativeTolerance, absoluteTolerance));
/*     */ 
/*     */ 
/*     */     
/*  98 */     this.initialBracketingRange = initialBracketingRange;
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
/*     */   public UnivariatePointValuePair search(final double[] startPoint, final double[] direction) {
/* 113 */     final int n = startPoint.length;
/* 114 */     UnivariateFunction f = new UnivariateFunction()
/*     */       {
/*     */         public double value(double alpha) {
/* 117 */           double[] x = new double[n];
/* 118 */           for (int i = 0; i < n; i++) {
/* 119 */             x[i] = startPoint[i] + alpha * direction[i];
/*     */           }
/* 121 */           double obj = LineSearch.this.mainOptimizer.computeObjectiveValue(x);
/* 122 */           return obj;
/*     */         }
/*     */       };
/*     */     
/* 126 */     GoalType goal = this.mainOptimizer.getGoalType();
/* 127 */     this.bracket.search(f, goal, 0.0D, this.initialBracketingRange);
/*     */ 
/*     */ 
/*     */     
/* 131 */     return this.lineOptimizer.optimize(new OptimizationData[] { (OptimizationData)new MaxEval(2147483647), (OptimizationData)new UnivariateObjectiveFunction(f), goal, (OptimizationData)new SearchInterval(this.bracket.getLo(), this.bracket.getHi(), this.bracket.getMid()) });
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optim/nonlinear/scalar/LineSearch.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */