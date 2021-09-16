/*     */ package org.apache.commons.math3.optim.nonlinear.scalar.noderiv;
/*     */ 
/*     */ import java.util.Comparator;
/*     */ import org.apache.commons.math3.analysis.MultivariateFunction;
/*     */ import org.apache.commons.math3.exception.MathUnsupportedOperationException;
/*     */ import org.apache.commons.math3.exception.NullArgumentException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.optim.ConvergenceChecker;
/*     */ import org.apache.commons.math3.optim.OptimizationData;
/*     */ import org.apache.commons.math3.optim.PointValuePair;
/*     */ import org.apache.commons.math3.optim.SimpleValueChecker;
/*     */ import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;
/*     */ import org.apache.commons.math3.optim.nonlinear.scalar.MultivariateOptimizer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SimplexOptimizer
/*     */   extends MultivariateOptimizer
/*     */ {
/*     */   private AbstractSimplex simplex;
/*     */   
/*     */   public SimplexOptimizer(ConvergenceChecker<PointValuePair> checker) {
/*  96 */     super(checker);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SimplexOptimizer(double rel, double abs) {
/* 104 */     this((ConvergenceChecker<PointValuePair>)new SimpleValueChecker(rel, abs));
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
/*     */   public PointValuePair optimize(OptimizationData... optData) {
/* 121 */     return super.optimize(optData);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected PointValuePair doOptimize() {
/* 127 */     checkParameters();
/*     */ 
/*     */ 
/*     */     
/* 131 */     MultivariateFunction evalFunc = new MultivariateFunction()
/*     */       {
/*     */         public double value(double[] point)
/*     */         {
/* 135 */           return SimplexOptimizer.this.computeObjectiveValue(point);
/*     */         }
/*     */       };
/*     */     
/* 139 */     final boolean isMinim = (getGoalType() == GoalType.MINIMIZE);
/* 140 */     Comparator<PointValuePair> comparator = new Comparator<PointValuePair>()
/*     */       {
/*     */         
/*     */         public int compare(PointValuePair o1, PointValuePair o2)
/*     */         {
/* 145 */           double v1 = ((Double)o1.getValue()).doubleValue();
/* 146 */           double v2 = ((Double)o2.getValue()).doubleValue();
/* 147 */           return isMinim ? Double.compare(v1, v2) : Double.compare(v2, v1);
/*     */         }
/*     */       };
/*     */ 
/*     */     
/* 152 */     this.simplex.build(getStartPoint());
/* 153 */     this.simplex.evaluate(evalFunc, comparator);
/*     */     
/* 155 */     PointValuePair[] previous = null;
/* 156 */     int iteration = 0;
/* 157 */     ConvergenceChecker<PointValuePair> checker = getConvergenceChecker();
/*     */     while (true) {
/* 159 */       if (getIterations() > 0) {
/* 160 */         boolean converged = true;
/* 161 */         for (int i = 0; i < this.simplex.getSize(); i++) {
/* 162 */           PointValuePair prev = previous[i];
/* 163 */           converged = (converged && checker.converged(iteration, prev, this.simplex.getPoint(i)));
/*     */         } 
/*     */         
/* 166 */         if (converged)
/*     */         {
/* 168 */           return this.simplex.getPoint(0);
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 173 */       previous = this.simplex.getPoints();
/* 174 */       this.simplex.iterate(evalFunc, comparator);
/*     */       
/* 176 */       incrementIterationCount();
/*     */     } 
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
/*     */   protected void parseOptimizationData(OptimizationData... optData) {
/* 193 */     super.parseOptimizationData(optData);
/*     */ 
/*     */ 
/*     */     
/* 197 */     for (OptimizationData data : optData) {
/* 198 */       if (data instanceof AbstractSimplex) {
/* 199 */         this.simplex = (AbstractSimplex)data;
/*     */         break;
/*     */       } 
/*     */     } 
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
/*     */   private void checkParameters() {
/* 214 */     if (this.simplex == null) {
/* 215 */       throw new NullArgumentException();
/*     */     }
/* 217 */     if (getLowerBound() != null || getUpperBound() != null)
/*     */     {
/* 219 */       throw new MathUnsupportedOperationException(LocalizedFormats.CONSTRAINT, new Object[0]);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optim/nonlinear/scalar/noderiv/SimplexOptimizer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */