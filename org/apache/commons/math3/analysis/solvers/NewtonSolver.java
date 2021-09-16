/*    */ package org.apache.commons.math3.analysis.solvers;
/*    */ 
/*    */ import org.apache.commons.math3.analysis.DifferentiableUnivariateFunction;
/*    */ import org.apache.commons.math3.analysis.UnivariateFunction;
/*    */ import org.apache.commons.math3.exception.MathIllegalArgumentException;
/*    */ import org.apache.commons.math3.exception.TooManyEvaluationsException;
/*    */ import org.apache.commons.math3.util.FastMath;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Deprecated
/*    */ public class NewtonSolver
/*    */   extends AbstractDifferentiableUnivariateSolver
/*    */ {
/*    */   private static final double DEFAULT_ABSOLUTE_ACCURACY = 1.0E-6D;
/*    */   
/*    */   public NewtonSolver() {
/* 41 */     this(1.0E-6D);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public NewtonSolver(double absoluteAccuracy) {
/* 49 */     super(absoluteAccuracy);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double solve(int maxEval, DifferentiableUnivariateFunction f, double min, double max) throws TooManyEvaluationsException {
/* 69 */     return solve(maxEval, f, UnivariateSolverUtils.midpoint(min, max));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected double doSolve() throws TooManyEvaluationsException {
/* 78 */     double startValue = getStartValue();
/* 79 */     double absoluteAccuracy = getAbsoluteAccuracy();
/*    */     
/* 81 */     double x0 = startValue;
/*    */     
/*    */     while (true) {
/* 84 */       double x1 = x0 - computeObjectiveValue(x0) / computeDerivativeObjectiveValue(x0);
/* 85 */       if (FastMath.abs(x1 - x0) <= absoluteAccuracy) {
/* 86 */         return x1;
/*    */       }
/*    */       
/* 89 */       x0 = x1;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/analysis/solvers/NewtonSolver.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */