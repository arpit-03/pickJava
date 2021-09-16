/*    */ package org.apache.commons.math3.analysis.solvers;
/*    */ 
/*    */ import org.apache.commons.math3.analysis.UnivariateFunction;
/*    */ import org.apache.commons.math3.analysis.differentiation.DerivativeStructure;
/*    */ import org.apache.commons.math3.analysis.differentiation.UnivariateDifferentiableFunction;
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
/*    */ public class NewtonRaphsonSolver
/*    */   extends AbstractUnivariateDifferentiableSolver
/*    */ {
/*    */   private static final double DEFAULT_ABSOLUTE_ACCURACY = 1.0E-6D;
/*    */   
/*    */   public NewtonRaphsonSolver() {
/* 40 */     this(1.0E-6D);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public NewtonRaphsonSolver(double absoluteAccuracy) {
/* 48 */     super(absoluteAccuracy);
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
/*    */   public double solve(int maxEval, UnivariateDifferentiableFunction f, double min, double max) throws TooManyEvaluationsException {
/* 68 */     return solve(maxEval, f, UnivariateSolverUtils.midpoint(min, max));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected double doSolve() throws TooManyEvaluationsException {
/* 77 */     double startValue = getStartValue();
/* 78 */     double absoluteAccuracy = getAbsoluteAccuracy();
/*    */     
/* 80 */     double x0 = startValue;
/*    */     
/*    */     while (true) {
/* 83 */       DerivativeStructure y0 = computeObjectiveValueAndDerivative(x0);
/* 84 */       double x1 = x0 - y0.getValue() / y0.getPartialDerivative(new int[] { 1 });
/* 85 */       if (FastMath.abs(x1 - x0) <= absoluteAccuracy) {
/* 86 */         return x1;
/*    */       }
/*    */       
/* 89 */       x0 = x1;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/analysis/solvers/NewtonRaphsonSolver.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */