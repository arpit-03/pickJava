/*    */ package org.apache.commons.math3.fitting.leastsquares;
/*    */ 
/*    */ import org.apache.commons.math3.linear.RealVector;
/*    */ import org.apache.commons.math3.optim.ConvergenceChecker;
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
/*    */ 
/*    */ public class LeastSquaresAdapter
/*    */   implements LeastSquaresProblem
/*    */ {
/*    */   private final LeastSquaresProblem problem;
/*    */   
/*    */   public LeastSquaresAdapter(LeastSquaresProblem problem) {
/* 39 */     this.problem = problem;
/*    */   }
/*    */ 
/*    */   
/*    */   public RealVector getStart() {
/* 44 */     return this.problem.getStart();
/*    */   }
/*    */ 
/*    */   
/*    */   public int getObservationSize() {
/* 49 */     return this.problem.getObservationSize();
/*    */   }
/*    */ 
/*    */   
/*    */   public int getParameterSize() {
/* 54 */     return this.problem.getParameterSize();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public LeastSquaresProblem.Evaluation evaluate(RealVector point) {
/* 60 */     return this.problem.evaluate(point);
/*    */   }
/*    */ 
/*    */   
/*    */   public Incrementor getEvaluationCounter() {
/* 65 */     return this.problem.getEvaluationCounter();
/*    */   }
/*    */ 
/*    */   
/*    */   public Incrementor getIterationCounter() {
/* 70 */     return this.problem.getIterationCounter();
/*    */   }
/*    */ 
/*    */   
/*    */   public ConvergenceChecker<LeastSquaresProblem.Evaluation> getConvergenceChecker() {
/* 75 */     return this.problem.getConvergenceChecker();
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/fitting/leastsquares/LeastSquaresAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */