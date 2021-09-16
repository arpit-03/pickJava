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
/*    */ 
/*    */ class OptimumImpl
/*    */   implements LeastSquaresOptimizer.Optimum
/*    */ {
/*    */   private final LeastSquaresProblem.Evaluation value;
/*    */   private final int evaluations;
/*    */   private final int iterations;
/*    */   
/*    */   OptimumImpl(LeastSquaresProblem.Evaluation value, int evaluations, int iterations) {
/* 46 */     this.value = value;
/* 47 */     this.evaluations = evaluations;
/* 48 */     this.iterations = iterations;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getEvaluations() {
/* 55 */     return this.evaluations;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getIterations() {
/* 60 */     return this.iterations;
/*    */   }
/*    */ 
/*    */   
/*    */   public RealMatrix getCovariances(double threshold) {
/* 65 */     return this.value.getCovariances(threshold);
/*    */   }
/*    */ 
/*    */   
/*    */   public RealVector getSigma(double covarianceSingularityThreshold) {
/* 70 */     return this.value.getSigma(covarianceSingularityThreshold);
/*    */   }
/*    */ 
/*    */   
/*    */   public double getRMS() {
/* 75 */     return this.value.getRMS();
/*    */   }
/*    */ 
/*    */   
/*    */   public RealMatrix getJacobian() {
/* 80 */     return this.value.getJacobian();
/*    */   }
/*    */ 
/*    */   
/*    */   public double getCost() {
/* 85 */     return this.value.getCost();
/*    */   }
/*    */ 
/*    */   
/*    */   public RealVector getResiduals() {
/* 90 */     return this.value.getResiduals();
/*    */   }
/*    */ 
/*    */   
/*    */   public RealVector getPoint() {
/* 95 */     return this.value.getPoint();
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/fitting/leastsquares/OptimumImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */