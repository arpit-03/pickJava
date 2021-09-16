/*     */ package org.apache.commons.math3.optim.nonlinear.scalar.gradient;
/*     */ 
/*     */ import org.apache.commons.math3.analysis.solvers.UnivariateSolver;
/*     */ import org.apache.commons.math3.exception.MathInternalError;
/*     */ import org.apache.commons.math3.exception.MathUnsupportedOperationException;
/*     */ import org.apache.commons.math3.exception.TooManyEvaluationsException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.optim.ConvergenceChecker;
/*     */ import org.apache.commons.math3.optim.OptimizationData;
/*     */ import org.apache.commons.math3.optim.PointValuePair;
/*     */ import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;
/*     */ import org.apache.commons.math3.optim.nonlinear.scalar.GradientMultivariateOptimizer;
/*     */ import org.apache.commons.math3.optim.nonlinear.scalar.LineSearch;
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
/*     */ public class NonLinearConjugateGradientOptimizer
/*     */   extends GradientMultivariateOptimizer
/*     */ {
/*     */   private final Formula updateFormula;
/*     */   private final Preconditioner preconditioner;
/*     */   private final LineSearch line;
/*     */   
/*     */   public enum Formula
/*     */   {
/*  75 */     FLETCHER_REEVES,
/*     */     
/*  77 */     POLAK_RIBIERE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static class BracketingStep
/*     */     implements OptimizationData
/*     */   {
/*     */     private final double initialStep;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public BracketingStep(double step) {
/* 101 */       this.initialStep = step;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public double getBracketingStep() {
/* 110 */       return this.initialStep;
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
/*     */   public NonLinearConjugateGradientOptimizer(Formula updateFormula, ConvergenceChecker<PointValuePair> checker) {
/* 125 */     this(updateFormula, checker, 1.0E-8D, 1.0E-8D, 1.0E-8D, new IdentityPreconditioner());
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
/*     */   @Deprecated
/*     */   public NonLinearConjugateGradientOptimizer(Formula updateFormula, ConvergenceChecker<PointValuePair> checker, UnivariateSolver lineSearchSolver) {
/* 148 */     this(updateFormula, checker, lineSearchSolver, new IdentityPreconditioner());
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
/*     */ 
/*     */ 
/*     */   
/*     */   public NonLinearConjugateGradientOptimizer(Formula updateFormula, ConvergenceChecker<PointValuePair> checker, double relativeTolerance, double absoluteTolerance, double initialBracketingRange) {
/* 175 */     this(updateFormula, checker, relativeTolerance, absoluteTolerance, initialBracketingRange, new IdentityPreconditioner());
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
/*     */   @Deprecated
/*     */   public NonLinearConjugateGradientOptimizer(Formula updateFormula, ConvergenceChecker<PointValuePair> checker, UnivariateSolver lineSearchSolver, Preconditioner preconditioner) {
/* 198 */     this(updateFormula, checker, lineSearchSolver.getRelativeAccuracy(), lineSearchSolver.getAbsoluteAccuracy(), lineSearchSolver.getAbsoluteAccuracy(), preconditioner);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NonLinearConjugateGradientOptimizer(Formula updateFormula, ConvergenceChecker<PointValuePair> checker, double relativeTolerance, double absoluteTolerance, double initialBracketingRange, Preconditioner preconditioner) {
/* 227 */     super(checker);
/*     */     
/* 229 */     this.updateFormula = updateFormula;
/* 230 */     this.preconditioner = preconditioner;
/* 231 */     this.line = new LineSearch((MultivariateOptimizer)this, relativeTolerance, absoluteTolerance, initialBracketingRange);
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
/*     */   public PointValuePair optimize(OptimizationData... optData) throws TooManyEvaluationsException {
/* 244 */     return super.optimize(optData);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected PointValuePair doOptimize() {
/* 250 */     ConvergenceChecker<PointValuePair> checker = getConvergenceChecker();
/* 251 */     double[] point = getStartPoint();
/* 252 */     GoalType goal = getGoalType();
/* 253 */     int n = point.length;
/* 254 */     double[] r = computeObjectiveGradient(point);
/* 255 */     if (goal == GoalType.MINIMIZE) {
/* 256 */       for (int j = 0; j < n; j++) {
/* 257 */         r[j] = -r[j];
/*     */       }
/*     */     }
/*     */ 
/*     */     
/* 262 */     double[] steepestDescent = this.preconditioner.precondition(point, r);
/* 263 */     double[] searchDirection = (double[])steepestDescent.clone();
/*     */     
/* 265 */     double delta = 0.0D;
/* 266 */     for (int i = 0; i < n; i++) {
/* 267 */       delta += r[i] * searchDirection[i];
/*     */     }
/*     */     
/* 270 */     PointValuePair current = null; while (true) {
/*     */       double beta, deltaMid; int i1;
/* 272 */       incrementIterationCount();
/*     */       
/* 274 */       double objective = computeObjectiveValue(point);
/* 275 */       PointValuePair previous = current;
/* 276 */       current = new PointValuePair(point, objective);
/* 277 */       if (previous != null && checker.converged(getIterations(), previous, current))
/*     */       {
/* 279 */         return current;
/*     */       }
/*     */       
/* 282 */       double step = this.line.search(point, searchDirection).getPoint();
/*     */       
/*     */       int j;
/* 285 */       for (j = 0; j < point.length; j++) {
/* 286 */         point[j] = point[j] + step * searchDirection[j];
/*     */       }
/*     */       
/* 289 */       r = computeObjectiveGradient(point);
/* 290 */       if (goal == GoalType.MINIMIZE) {
/* 291 */         for (j = 0; j < n; j++) {
/* 292 */           r[j] = -r[j];
/*     */         }
/*     */       }
/*     */ 
/*     */       
/* 297 */       double deltaOld = delta;
/* 298 */       double[] newSteepestDescent = this.preconditioner.precondition(point, r);
/* 299 */       delta = 0.0D;
/* 300 */       for (int k = 0; k < n; k++) {
/* 301 */         delta += r[k] * newSteepestDescent[k];
/*     */       }
/*     */ 
/*     */       
/* 305 */       switch (this.updateFormula) {
/*     */         case FLETCHER_REEVES:
/* 307 */           beta = delta / deltaOld;
/*     */           break;
/*     */         case POLAK_RIBIERE:
/* 310 */           deltaMid = 0.0D;
/* 311 */           for (i1 = 0; i1 < r.length; i1++) {
/* 312 */             deltaMid += r[i1] * steepestDescent[i1];
/*     */           }
/* 314 */           beta = (delta - deltaMid) / deltaOld;
/*     */           break;
/*     */         
/*     */         default:
/* 318 */           throw new MathInternalError();
/*     */       } 
/* 320 */       steepestDescent = newSteepestDescent;
/*     */ 
/*     */       
/* 323 */       if (getIterations() % n == 0 || beta < 0.0D) {
/*     */ 
/*     */         
/* 326 */         searchDirection = (double[])steepestDescent.clone();
/*     */         continue;
/*     */       } 
/* 329 */       for (int m = 0; m < n; m++) {
/* 330 */         searchDirection[m] = steepestDescent[m] + beta * searchDirection[m];
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
/*     */   protected void parseOptimizationData(OptimizationData... optData) {
/* 342 */     super.parseOptimizationData(optData);
/*     */     
/* 344 */     checkParameters();
/*     */   }
/*     */   
/*     */   public static class IdentityPreconditioner
/*     */     implements Preconditioner
/*     */   {
/*     */     public double[] precondition(double[] variables, double[] r) {
/* 351 */       return (double[])r.clone();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 410 */     if (getLowerBound() != null || getUpperBound() != null)
/*     */     {
/* 412 */       throw new MathUnsupportedOperationException(LocalizedFormats.CONSTRAINT, new Object[0]);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optim/nonlinear/scalar/gradient/NonLinearConjugateGradientOptimizer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */