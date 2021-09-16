/*    */ package org.apache.commons.math3.fitting.leastsquares;
/*    */ 
/*    */ import org.apache.commons.math3.linear.ArrayRealVector;
/*    */ import org.apache.commons.math3.linear.DecompositionSolver;
/*    */ import org.apache.commons.math3.linear.QRDecomposition;
/*    */ import org.apache.commons.math3.linear.RealMatrix;
/*    */ import org.apache.commons.math3.linear.RealVector;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class AbstractEvaluation
/*    */   implements LeastSquaresProblem.Evaluation
/*    */ {
/*    */   private final int observationSize;
/*    */   
/*    */   AbstractEvaluation(int observationSize) {
/* 47 */     this.observationSize = observationSize;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public RealMatrix getCovariances(double threshold) {
/* 53 */     RealMatrix j = getJacobian();
/*    */ 
/*    */     
/* 56 */     RealMatrix jTj = j.transpose().multiply(j);
/*    */ 
/*    */     
/* 59 */     DecompositionSolver solver = (new QRDecomposition(jTj, threshold)).getSolver();
/*    */     
/* 61 */     return solver.getInverse();
/*    */   }
/*    */ 
/*    */   
/*    */   public RealVector getSigma(double covarianceSingularityThreshold) {
/* 66 */     RealMatrix cov = getCovariances(covarianceSingularityThreshold);
/* 67 */     int nC = cov.getColumnDimension();
/* 68 */     ArrayRealVector arrayRealVector = new ArrayRealVector(nC);
/* 69 */     for (int i = 0; i < nC; i++) {
/* 70 */       arrayRealVector.setEntry(i, FastMath.sqrt(cov.getEntry(i, i)));
/*    */     }
/* 72 */     return (RealVector)arrayRealVector;
/*    */   }
/*    */ 
/*    */   
/*    */   public double getRMS() {
/* 77 */     double cost = getCost();
/* 78 */     return FastMath.sqrt(cost * cost / this.observationSize);
/*    */   }
/*    */ 
/*    */   
/*    */   public double getCost() {
/* 83 */     ArrayRealVector r = new ArrayRealVector(getResiduals());
/* 84 */     return FastMath.sqrt(r.dotProduct((RealVector)r));
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/fitting/leastsquares/AbstractEvaluation.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */