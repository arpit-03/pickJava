/*    */ package org.apache.commons.math3.fitting.leastsquares;
/*    */ 
/*    */ import org.apache.commons.math3.linear.RealMatrix;
/*    */ import org.apache.commons.math3.linear.RealVector;
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
/*    */ class DenseWeightedEvaluation
/*    */   extends AbstractEvaluation
/*    */ {
/*    */   private final LeastSquaresProblem.Evaluation unweighted;
/*    */   private final RealMatrix weightSqrt;
/*    */   
/*    */   DenseWeightedEvaluation(LeastSquaresProblem.Evaluation unweighted, RealMatrix weightSqrt) {
/* 44 */     super(weightSqrt.getColumnDimension());
/* 45 */     this.unweighted = unweighted;
/* 46 */     this.weightSqrt = weightSqrt;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public RealMatrix getJacobian() {
/* 53 */     return this.weightSqrt.multiply(this.unweighted.getJacobian());
/*    */   }
/*    */ 
/*    */   
/*    */   public RealVector getResiduals() {
/* 58 */     return this.weightSqrt.operate(this.unweighted.getResiduals());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public RealVector getPoint() {
/* 65 */     return this.unweighted.getPoint();
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/fitting/leastsquares/DenseWeightedEvaluation.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */