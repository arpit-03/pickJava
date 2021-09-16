/*    */ package org.apache.commons.math3.optim;
/*    */ 
/*    */ import org.apache.commons.math3.exception.TooManyEvaluationsException;
/*    */ import org.apache.commons.math3.exception.TooManyIterationsException;
/*    */ import org.apache.commons.math3.util.Incrementor;
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
/*    */ public abstract class AbstractOptimizationProblem<PAIR>
/*    */   implements OptimizationProblem<PAIR>
/*    */ {
/* 35 */   private static final MaxEvalCallback MAX_EVAL_CALLBACK = new MaxEvalCallback();
/*    */   
/* 37 */   private static final MaxIterCallback MAX_ITER_CALLBACK = new MaxIterCallback();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private final int maxEvaluations;
/*    */ 
/*    */ 
/*    */   
/*    */   private final int maxIterations;
/*    */ 
/*    */ 
/*    */   
/*    */   private final ConvergenceChecker<PAIR> checker;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected AbstractOptimizationProblem(int maxEvaluations, int maxIterations, ConvergenceChecker<PAIR> checker) {
/* 56 */     this.maxEvaluations = maxEvaluations;
/* 57 */     this.maxIterations = maxIterations;
/* 58 */     this.checker = checker;
/*    */   }
/*    */ 
/*    */   
/*    */   public Incrementor getEvaluationCounter() {
/* 63 */     return new Incrementor(this.maxEvaluations, MAX_EVAL_CALLBACK);
/*    */   }
/*    */ 
/*    */   
/*    */   public Incrementor getIterationCounter() {
/* 68 */     return new Incrementor(this.maxIterations, MAX_ITER_CALLBACK);
/*    */   }
/*    */ 
/*    */   
/*    */   public ConvergenceChecker<PAIR> getConvergenceChecker() {
/* 73 */     return this.checker;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private static class MaxEvalCallback
/*    */     implements Incrementor.MaxCountExceededCallback
/*    */   {
/*    */     private MaxEvalCallback() {}
/*    */ 
/*    */     
/*    */     public void trigger(int max) {
/* 85 */       throw new TooManyEvaluationsException(Integer.valueOf(max));
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private static class MaxIterCallback
/*    */     implements Incrementor.MaxCountExceededCallback
/*    */   {
/*    */     private MaxIterCallback() {}
/*    */ 
/*    */     
/*    */     public void trigger(int max) {
/* 98 */       throw new TooManyIterationsException(Integer.valueOf(max));
/*    */     }
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optim/AbstractOptimizationProblem.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */