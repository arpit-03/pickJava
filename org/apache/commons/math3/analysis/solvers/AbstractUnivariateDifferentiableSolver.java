/*    */ package org.apache.commons.math3.analysis.solvers;
/*    */ 
/*    */ import org.apache.commons.math3.analysis.UnivariateFunction;
/*    */ import org.apache.commons.math3.analysis.differentiation.DerivativeStructure;
/*    */ import org.apache.commons.math3.analysis.differentiation.UnivariateDifferentiableFunction;
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
/*    */ public abstract class AbstractUnivariateDifferentiableSolver
/*    */   extends BaseAbstractUnivariateSolver<UnivariateDifferentiableFunction>
/*    */   implements UnivariateDifferentiableSolver
/*    */ {
/*    */   private UnivariateDifferentiableFunction function;
/*    */   
/*    */   protected AbstractUnivariateDifferentiableSolver(double absoluteAccuracy) {
/* 43 */     super(absoluteAccuracy);
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
/*    */   protected AbstractUnivariateDifferentiableSolver(double relativeAccuracy, double absoluteAccuracy, double functionValueAccuracy) {
/* 56 */     super(relativeAccuracy, absoluteAccuracy, functionValueAccuracy);
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
/*    */   protected DerivativeStructure computeObjectiveValueAndDerivative(double point) throws TooManyEvaluationsException {
/* 69 */     incrementEvaluationCount();
/* 70 */     return this.function.value(new DerivativeStructure(1, 1, 0, point));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void setup(int maxEval, UnivariateDifferentiableFunction f, double min, double max, double startValue) {
/* 79 */     super.setup(maxEval, f, min, max, startValue);
/* 80 */     this.function = f;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/analysis/solvers/AbstractUnivariateDifferentiableSolver.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */