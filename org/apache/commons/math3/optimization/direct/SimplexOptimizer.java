/*     */ package org.apache.commons.math3.optimization.direct;
/*     */ 
/*     */ import java.util.Comparator;
/*     */ import org.apache.commons.math3.analysis.MultivariateFunction;
/*     */ import org.apache.commons.math3.exception.NullArgumentException;
/*     */ import org.apache.commons.math3.optimization.ConvergenceChecker;
/*     */ import org.apache.commons.math3.optimization.GoalType;
/*     */ import org.apache.commons.math3.optimization.MultivariateOptimizer;
/*     */ import org.apache.commons.math3.optimization.OptimizationData;
/*     */ import org.apache.commons.math3.optimization.PointValuePair;
/*     */ import org.apache.commons.math3.optimization.SimpleValueChecker;
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
/*     */ public class SimplexOptimizer
/*     */   extends BaseAbstractMultivariateOptimizer<MultivariateFunction>
/*     */   implements MultivariateOptimizer
/*     */ {
/*     */   private AbstractSimplex simplex;
/*     */   
/*     */   @Deprecated
/*     */   public SimplexOptimizer() {
/* 102 */     this((ConvergenceChecker<PointValuePair>)new SimpleValueChecker());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SimplexOptimizer(ConvergenceChecker<PointValuePair> checker) {
/* 109 */     super(checker);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SimplexOptimizer(double rel, double abs) {
/* 117 */     this((ConvergenceChecker<PointValuePair>)new SimpleValueChecker(rel, abs));
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
/*     */   @Deprecated
/*     */   public void setSimplex(AbstractSimplex simplex) {
/* 130 */     parseOptimizationData(new OptimizationData[] { simplex });
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
/*     */   protected PointValuePair optimizeInternal(int maxEval, MultivariateFunction f, GoalType goalType, OptimizationData... optData) {
/* 152 */     parseOptimizationData(optData);
/*     */ 
/*     */ 
/*     */     
/* 156 */     return super.optimizeInternal(maxEval, f, goalType, optData);
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
/*     */   private void parseOptimizationData(OptimizationData... optData) {
/* 171 */     for (OptimizationData data : optData) {
/* 172 */       if (data instanceof AbstractSimplex) {
/* 173 */         this.simplex = (AbstractSimplex)data;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected PointValuePair doOptimize() {
/* 182 */     if (this.simplex == null) {
/* 183 */       throw new NullArgumentException();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 188 */     MultivariateFunction evalFunc = new MultivariateFunction()
/*     */       {
/*     */         public double value(double[] point)
/*     */         {
/* 192 */           return SimplexOptimizer.this.computeObjectiveValue(point);
/*     */         }
/*     */       };
/*     */     
/* 196 */     final boolean isMinim = (getGoalType() == GoalType.MINIMIZE);
/* 197 */     Comparator<PointValuePair> comparator = new Comparator<PointValuePair>()
/*     */       {
/*     */         
/*     */         public int compare(PointValuePair o1, PointValuePair o2)
/*     */         {
/* 202 */           double v1 = ((Double)o1.getValue()).doubleValue();
/* 203 */           double v2 = ((Double)o2.getValue()).doubleValue();
/* 204 */           return isMinim ? Double.compare(v1, v2) : Double.compare(v2, v1);
/*     */         }
/*     */       };
/*     */ 
/*     */     
/* 209 */     this.simplex.build(getStartPoint());
/* 210 */     this.simplex.evaluate(evalFunc, comparator);
/*     */     
/* 212 */     PointValuePair[] previous = null;
/* 213 */     int iteration = 0;
/* 214 */     ConvergenceChecker<PointValuePair> checker = getConvergenceChecker();
/*     */     while (true) {
/* 216 */       if (iteration > 0) {
/* 217 */         boolean converged = true;
/* 218 */         for (int i = 0; i < this.simplex.getSize(); i++) {
/* 219 */           PointValuePair prev = previous[i];
/* 220 */           converged = (converged && checker.converged(iteration, prev, this.simplex.getPoint(i)));
/*     */         } 
/*     */         
/* 223 */         if (converged)
/*     */         {
/* 225 */           return this.simplex.getPoint(0);
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 230 */       previous = this.simplex.getPoints();
/* 231 */       this.simplex.iterate(evalFunc, comparator);
/* 232 */       iteration++;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optimization/direct/SimplexOptimizer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */