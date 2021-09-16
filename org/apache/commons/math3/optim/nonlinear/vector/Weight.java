/*    */ package org.apache.commons.math3.optim.nonlinear.vector;
/*    */ 
/*    */ import org.apache.commons.math3.linear.DiagonalMatrix;
/*    */ import org.apache.commons.math3.linear.NonSquareMatrixException;
/*    */ import org.apache.commons.math3.linear.RealMatrix;
/*    */ import org.apache.commons.math3.optim.OptimizationData;
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
/*    */ @Deprecated
/*    */ public class Weight
/*    */   implements OptimizationData
/*    */ {
/*    */   private final RealMatrix weightMatrix;
/*    */   
/*    */   public Weight(double[] weight) {
/* 46 */     this.weightMatrix = (RealMatrix)new DiagonalMatrix(weight);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Weight(RealMatrix weight) {
/* 55 */     if (weight.getColumnDimension() != weight.getRowDimension()) {
/* 56 */       throw new NonSquareMatrixException(weight.getColumnDimension(), weight.getRowDimension());
/*    */     }
/*    */ 
/*    */     
/* 60 */     this.weightMatrix = weight.copy();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public RealMatrix getWeight() {
/* 69 */     return this.weightMatrix.copy();
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optim/nonlinear/vector/Weight.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */