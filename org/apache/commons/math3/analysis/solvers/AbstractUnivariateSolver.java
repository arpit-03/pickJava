/*    */ package org.apache.commons.math3.analysis.solvers;
/*    */ 
/*    */ import org.apache.commons.math3.analysis.UnivariateFunction;
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
/*    */ public abstract class AbstractUnivariateSolver
/*    */   extends BaseAbstractUnivariateSolver<UnivariateFunction>
/*    */   implements UnivariateSolver
/*    */ {
/*    */   protected AbstractUnivariateSolver(double absoluteAccuracy) {
/* 36 */     super(absoluteAccuracy);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected AbstractUnivariateSolver(double relativeAccuracy, double absoluteAccuracy) {
/* 46 */     super(relativeAccuracy, absoluteAccuracy);
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
/*    */   protected AbstractUnivariateSolver(double relativeAccuracy, double absoluteAccuracy, double functionValueAccuracy) {
/* 58 */     super(relativeAccuracy, absoluteAccuracy, functionValueAccuracy);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/analysis/solvers/AbstractUnivariateSolver.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */