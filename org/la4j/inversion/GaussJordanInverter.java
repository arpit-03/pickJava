/*    */ package org.la4j.inversion;
/*    */ 
/*    */ import org.la4j.LinearAlgebra;
/*    */ import org.la4j.Matrix;
/*    */ import org.la4j.Vector;
/*    */ import org.la4j.linear.LinearSystemSolver;
/*    */ import org.la4j.vector.DenseVector;
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
/*    */ public class GaussJordanInverter
/*    */   implements MatrixInverter
/*    */ {
/*    */   private final Matrix matrix;
/*    */   
/*    */   public GaussJordanInverter(Matrix matrix) {
/* 35 */     this.matrix = matrix;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Matrix inverse() {
/* 41 */     if (this.matrix.rows() != this.matrix.columns()) {
/* 42 */       throw new IllegalArgumentException("Wrong matrix size: rows != columns");
/*    */     }
/*    */ 
/*    */     
/* 46 */     Matrix result = this.matrix.blankOfShape(this.matrix.rows(), this.matrix.columns());
/*    */     
/* 48 */     for (int i = 0; i < this.matrix.rows(); i++) {
/*    */       
/* 50 */       DenseVector denseVector = DenseVector.zero(this.matrix.rows());
/* 51 */       denseVector.set(i, 1.0D);
/*    */       
/*    */       try {
/* 54 */         LinearSystemSolver solver = this.matrix.withSolver(LinearAlgebra.GAUSSIAN);
/* 55 */         Vector x = solver.solve((Vector)denseVector);
/* 56 */         result.setColumn(i, x);
/* 57 */       } catch (IllegalArgumentException ex) {
/* 58 */         throw new IllegalArgumentException("This matrix is not invertible.");
/*    */       } 
/*    */     } 
/*    */     
/* 62 */     return result;
/*    */   }
/*    */ 
/*    */   
/*    */   public Matrix self() {
/* 67 */     return this.matrix;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/la4j/inversion/GaussJordanInverter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */