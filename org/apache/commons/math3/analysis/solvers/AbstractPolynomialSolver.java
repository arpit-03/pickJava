/*    */ package org.apache.commons.math3.analysis.solvers;
/*    */ 
/*    */ import org.apache.commons.math3.analysis.UnivariateFunction;
/*    */ import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
/*    */ import org.apache.commons.math3.exception.NullArgumentException;
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
/*    */ public abstract class AbstractPolynomialSolver
/*    */   extends BaseAbstractUnivariateSolver<PolynomialFunction>
/*    */   implements PolynomialSolver
/*    */ {
/*    */   private PolynomialFunction polynomialFunction;
/*    */   
/*    */   protected AbstractPolynomialSolver(double absoluteAccuracy) {
/* 39 */     super(absoluteAccuracy);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected AbstractPolynomialSolver(double relativeAccuracy, double absoluteAccuracy) {
/* 49 */     super(relativeAccuracy, absoluteAccuracy);
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
/*    */   protected AbstractPolynomialSolver(double relativeAccuracy, double absoluteAccuracy, double functionValueAccuracy) {
/* 61 */     super(relativeAccuracy, absoluteAccuracy, functionValueAccuracy);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void setup(int maxEval, PolynomialFunction f, double min, double max, double startValue) {
/* 70 */     super.setup(maxEval, f, min, max, startValue);
/* 71 */     this.polynomialFunction = f;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected double[] getCoefficients() {
/* 78 */     return this.polynomialFunction.getCoefficients();
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/analysis/solvers/AbstractPolynomialSolver.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */