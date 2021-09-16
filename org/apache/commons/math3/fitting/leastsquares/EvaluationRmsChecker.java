/*    */ package org.apache.commons.math3.fitting.leastsquares;
/*    */ 
/*    */ import org.apache.commons.math3.optim.ConvergenceChecker;
/*    */ import org.apache.commons.math3.util.Precision;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EvaluationRmsChecker
/*    */   implements ConvergenceChecker<LeastSquaresProblem.Evaluation>
/*    */ {
/*    */   private final double relTol;
/*    */   private final double absTol;
/*    */   
/*    */   public EvaluationRmsChecker(double tol) {
/* 46 */     this(tol, tol);
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
/*    */   public EvaluationRmsChecker(double relTol, double absTol) {
/* 61 */     this.relTol = relTol;
/* 62 */     this.absTol = absTol;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean converged(int iteration, LeastSquaresProblem.Evaluation previous, LeastSquaresProblem.Evaluation current) {
/* 69 */     double prevRms = previous.getRMS();
/* 70 */     double currRms = current.getRMS();
/* 71 */     return (Precision.equals(prevRms, currRms, this.absTol) || Precision.equalsWithRelativeTolerance(prevRms, currRms, this.relTol));
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/fitting/leastsquares/EvaluationRmsChecker.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */