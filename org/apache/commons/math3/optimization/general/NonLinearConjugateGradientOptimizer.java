/*     */ package org.apache.commons.math3.optimization.general;
/*     */ 
/*     */ import org.apache.commons.math3.analysis.UnivariateFunction;
/*     */ import org.apache.commons.math3.analysis.solvers.BrentSolver;
/*     */ import org.apache.commons.math3.analysis.solvers.UnivariateSolver;
/*     */ import org.apache.commons.math3.exception.MathIllegalStateException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.optimization.ConvergenceChecker;
/*     */ import org.apache.commons.math3.optimization.GoalType;
/*     */ import org.apache.commons.math3.optimization.PointValuePair;
/*     */ import org.apache.commons.math3.optimization.SimpleValueChecker;
/*     */ import org.apache.commons.math3.util.FastMath;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public class NonLinearConjugateGradientOptimizer
/*     */   extends AbstractScalarDifferentiableOptimizer
/*     */ {
/*     */   private final ConjugateGradientFormula updateFormula;
/*     */   private final Preconditioner preconditioner;
/*     */   private final UnivariateSolver solver;
/*     */   private double initialStep;
/*     */   private double[] point;
/*     */   
/*     */   @Deprecated
/*     */   public NonLinearConjugateGradientOptimizer(ConjugateGradientFormula updateFormula) {
/*  69 */     this(updateFormula, (ConvergenceChecker<PointValuePair>)new SimpleValueChecker());
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
/*     */   public NonLinearConjugateGradientOptimizer(ConjugateGradientFormula updateFormula, ConvergenceChecker<PointValuePair> checker) {
/*  84 */     this(updateFormula, checker, (UnivariateSolver)new BrentSolver(), new IdentityPreconditioner());
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
/*     */   public NonLinearConjugateGradientOptimizer(ConjugateGradientFormula updateFormula, ConvergenceChecker<PointValuePair> checker, UnivariateSolver lineSearchSolver) {
/* 103 */     this(updateFormula, checker, lineSearchSolver, new IdentityPreconditioner());
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
/*     */   public NonLinearConjugateGradientOptimizer(ConjugateGradientFormula updateFormula, ConvergenceChecker<PointValuePair> checker, UnivariateSolver lineSearchSolver, Preconditioner preconditioner) {
/* 121 */     super(checker);
/*     */     
/* 123 */     this.updateFormula = updateFormula;
/* 124 */     this.solver = lineSearchSolver;
/* 125 */     this.preconditioner = preconditioner;
/* 126 */     this.initialStep = 1.0D;
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
/*     */   public void setInitialStep(double initialStep) {
/* 140 */     if (initialStep <= 0.0D) {
/* 141 */       this.initialStep = 1.0D;
/*     */     } else {
/* 143 */       this.initialStep = initialStep;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected PointValuePair doOptimize() {
/* 150 */     ConvergenceChecker<PointValuePair> checker = getConvergenceChecker();
/* 151 */     this.point = getStartPoint();
/* 152 */     GoalType goal = getGoalType();
/* 153 */     int n = this.point.length;
/* 154 */     double[] r = computeObjectiveGradient(this.point);
/* 155 */     if (goal == GoalType.MINIMIZE) {
/* 156 */       for (int j = 0; j < n; j++) {
/* 157 */         r[j] = -r[j];
/*     */       }
/*     */     }
/*     */ 
/*     */     
/* 162 */     double[] steepestDescent = this.preconditioner.precondition(this.point, r);
/* 163 */     double[] searchDirection = (double[])steepestDescent.clone();
/*     */     
/* 165 */     double delta = 0.0D;
/* 166 */     for (int i = 0; i < n; i++) {
/* 167 */       delta += r[i] * searchDirection[i];
/*     */     }
/*     */     
/* 170 */     PointValuePair current = null;
/* 171 */     int iter = 0;
/* 172 */     int maxEval = getMaxEvaluations(); while (true) {
/*     */       double beta;
/* 174 */       iter++;
/*     */       
/* 176 */       double objective = computeObjectiveValue(this.point);
/* 177 */       PointValuePair previous = current;
/* 178 */       current = new PointValuePair(this.point, objective);
/* 179 */       if (previous != null && checker.converged(iter, previous, current))
/*     */       {
/* 181 */         return current;
/*     */       }
/*     */ 
/*     */       
/* 185 */       UnivariateFunction lsf = new LineSearchFunction(searchDirection);
/* 186 */       double uB = findUpperBound(lsf, 0.0D, this.initialStep);
/*     */ 
/*     */ 
/*     */       
/* 190 */       double step = this.solver.solve(maxEval, lsf, 0.0D, uB, 1.0E-15D);
/* 191 */       maxEval -= this.solver.getEvaluations();
/*     */       
/*     */       int j;
/* 194 */       for (j = 0; j < this.point.length; j++) {
/* 195 */         this.point[j] = this.point[j] + step * searchDirection[j];
/*     */       }
/*     */       
/* 198 */       r = computeObjectiveGradient(this.point);
/* 199 */       if (goal == GoalType.MINIMIZE) {
/* 200 */         for (j = 0; j < n; j++) {
/* 201 */           r[j] = -r[j];
/*     */         }
/*     */       }
/*     */ 
/*     */       
/* 206 */       double deltaOld = delta;
/* 207 */       double[] newSteepestDescent = this.preconditioner.precondition(this.point, r);
/* 208 */       delta = 0.0D;
/* 209 */       for (int k = 0; k < n; k++) {
/* 210 */         delta += r[k] * newSteepestDescent[k];
/*     */       }
/*     */ 
/*     */       
/* 214 */       if (this.updateFormula == ConjugateGradientFormula.FLETCHER_REEVES) {
/* 215 */         beta = delta / deltaOld;
/*     */       } else {
/* 217 */         double deltaMid = 0.0D;
/* 218 */         for (int i1 = 0; i1 < r.length; i1++) {
/* 219 */           deltaMid += r[i1] * steepestDescent[i1];
/*     */         }
/* 221 */         beta = (delta - deltaMid) / deltaOld;
/*     */       } 
/* 223 */       steepestDescent = newSteepestDescent;
/*     */ 
/*     */       
/* 226 */       if (iter % n == 0 || beta < 0.0D) {
/*     */ 
/*     */         
/* 229 */         searchDirection = (double[])steepestDescent.clone();
/*     */         continue;
/*     */       } 
/* 232 */       for (int m = 0; m < n; m++) {
/* 233 */         searchDirection[m] = steepestDescent[m] + beta * searchDirection[m];
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
/*     */ 
/*     */ 
/*     */   
/*     */   private double findUpperBound(UnivariateFunction f, double a, double h) {
/* 250 */     double yA = f.value(a);
/* 251 */     double yB = yA; double step;
/* 252 */     for (step = h; step < Double.MAX_VALUE; step *= FastMath.max(2.0D, yA / yB)) {
/* 253 */       double b = a + step;
/* 254 */       yB = f.value(b);
/* 255 */       if (yA * yB <= 0.0D) {
/* 256 */         return b;
/*     */       }
/*     */     } 
/* 259 */     throw new MathIllegalStateException(LocalizedFormats.UNABLE_TO_BRACKET_OPTIMUM_IN_LINE_SEARCH, new Object[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   public static class IdentityPreconditioner
/*     */     implements Preconditioner
/*     */   {
/*     */     public double[] precondition(double[] variables, double[] r) {
/* 267 */       return (double[])r.clone();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class LineSearchFunction
/*     */     implements UnivariateFunction
/*     */   {
/*     */     private final double[] searchDirection;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     LineSearchFunction(double[] searchDirection) {
/* 288 */       this.searchDirection = searchDirection;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public double value(double x) {
/* 294 */       double[] shiftedPoint = (double[])NonLinearConjugateGradientOptimizer.this.point.clone();
/* 295 */       for (int i = 0; i < shiftedPoint.length; i++) {
/* 296 */         shiftedPoint[i] = shiftedPoint[i] + x * this.searchDirection[i];
/*     */       }
/*     */ 
/*     */       
/* 300 */       double[] gradient = NonLinearConjugateGradientOptimizer.this.computeObjectiveGradient(shiftedPoint);
/*     */ 
/*     */       
/* 303 */       double dotProduct = 0.0D;
/* 304 */       for (int j = 0; j < gradient.length; j++) {
/* 305 */         dotProduct += gradient[j] * this.searchDirection[j];
/*     */       }
/*     */       
/* 308 */       return dotProduct;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optimization/general/NonLinearConjugateGradientOptimizer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */