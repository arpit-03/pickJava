/*    */ package org.apache.commons.math3.optimization;
/*    */ 
/*    */ import org.apache.commons.math3.linear.DiagonalMatrix;
/*    */ import org.apache.commons.math3.linear.NonSquareMatrixException;
/*    */ import org.apache.commons.math3.linear.RealMatrix;
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
/* 43 */     this.weightMatrix = (RealMatrix)new DiagonalMatrix(weight);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Weight(RealMatrix weight) {
/* 52 */     if (weight.getColumnDimension() != weight.getRowDimension()) {
/* 53 */       throw new NonSquareMatrixException(weight.getColumnDimension(), weight.getRowDimension());
/*    */     }
/*    */ 
/*    */     
/* 57 */     this.weightMatrix = weight.copy();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public RealMatrix getWeight() {
/* 66 */     return this.weightMatrix.copy();
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optimization/Weight.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */