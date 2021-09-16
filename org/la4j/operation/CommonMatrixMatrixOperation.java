/*    */ package org.la4j.operation;
/*    */ 
/*    */ import org.la4j.Matrix;
/*    */ import org.la4j.matrix.ColumnMajorSparseMatrix;
/*    */ import org.la4j.matrix.DenseMatrix;
/*    */ import org.la4j.matrix.RowMajorSparseMatrix;
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
/*    */ public abstract class CommonMatrixMatrixOperation<R>
/*    */   extends MatrixMatrixOperation<R>
/*    */ {
/*    */   public R apply(DenseMatrix a, DenseMatrix b) {
/* 32 */     return applyCommon((Matrix)a, (Matrix)b);
/*    */   }
/*    */ 
/*    */   
/*    */   public R apply(DenseMatrix a, RowMajorSparseMatrix b) {
/* 37 */     return applyCommon((Matrix)a, (Matrix)b);
/*    */   }
/*    */ 
/*    */   
/*    */   public R apply(DenseMatrix a, ColumnMajorSparseMatrix b) {
/* 42 */     return applyCommon((Matrix)a, (Matrix)b);
/*    */   }
/*    */ 
/*    */   
/*    */   public R apply(RowMajorSparseMatrix a, DenseMatrix b) {
/* 47 */     return applyCommon((Matrix)a, (Matrix)b);
/*    */   }
/*    */ 
/*    */   
/*    */   public R apply(RowMajorSparseMatrix a, RowMajorSparseMatrix b) {
/* 52 */     return applyCommon((Matrix)a, (Matrix)b);
/*    */   }
/*    */ 
/*    */   
/*    */   public R apply(RowMajorSparseMatrix a, ColumnMajorSparseMatrix b) {
/* 57 */     return applyCommon((Matrix)a, (Matrix)b);
/*    */   }
/*    */ 
/*    */   
/*    */   public R apply(ColumnMajorSparseMatrix a, DenseMatrix b) {
/* 62 */     return applyCommon((Matrix)a, (Matrix)b);
/*    */   }
/*    */ 
/*    */   
/*    */   public R apply(ColumnMajorSparseMatrix a, RowMajorSparseMatrix b) {
/* 67 */     return applyCommon((Matrix)a, (Matrix)b);
/*    */   }
/*    */ 
/*    */   
/*    */   public R apply(ColumnMajorSparseMatrix a, ColumnMajorSparseMatrix b) {
/* 72 */     return applyCommon((Matrix)a, (Matrix)b);
/*    */   }
/*    */   
/*    */   public abstract R applyCommon(Matrix paramMatrix1, Matrix paramMatrix2);
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/la4j/operation/CommonMatrixMatrixOperation.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */