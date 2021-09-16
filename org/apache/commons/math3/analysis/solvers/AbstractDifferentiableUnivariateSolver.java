/*    */ package org.apache.commons.math3.analysis.solvers;
/*    */ 
/*    */ import org.apache.commons.math3.analysis.DifferentiableUnivariateFunction;
/*    */ import org.apache.commons.math3.analysis.UnivariateFunction;
/*    */ import org.apache.commons.math3.exception.NullArgumentException;
/*    */ import org.apache.commons.math3.exception.TooManyEvaluationsException;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ @Deprecated
/*    */ public abstract class AbstractDifferentiableUnivariateSolver
/*    */   extends BaseAbstractUnivariateSolver<DifferentiableUnivariateFunction>
/*    */   implements DifferentiableUnivariateSolver
/*    */ {
/*    */   private UnivariateFunction functionDerivative;
/*    */   
/*    */   protected AbstractDifferentiableUnivariateSolver(double absoluteAccuracy) {
/* 44 */     super(absoluteAccuracy);
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
/*    */   protected AbstractDifferentiableUnivariateSolver(double relativeAccuracy, double absoluteAccuracy, double functionValueAccuracy) {
/* 57 */     super(relativeAccuracy, absoluteAccuracy, functionValueAccuracy);
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
/*    */   protected double computeDerivativeObjectiveValue(double point) throws TooManyEvaluationsException {
/* 69 */     incrementEvaluationCount();
/* 70 */     return this.functionDerivative.value(point);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void setup(int maxEval, DifferentiableUnivariateFunction f, double min, double max, double startValue) {
/* 79 */     super.setup(maxEval, f, min, max, startValue);
/* 80 */     this.functionDerivative = f.derivative();
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/analysis/solvers/AbstractDifferentiableUnivariateSolver.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */