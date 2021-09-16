/*    */ package org.la4j.operation;
/*    */ 
/*    */ import org.la4j.matrix.ColumnMajorSparseMatrix;
/*    */ import org.la4j.matrix.DenseMatrix;
/*    */ import org.la4j.matrix.RowMajorSparseMatrix;
/*    */ import org.la4j.matrix.SparseMatrix;
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
/*    */ public abstract class SimpleMatrixMatrixOperation<R>
/*    */   extends MatrixMatrixOperation<R>
/*    */ {
/*    */   public R apply(DenseMatrix a, RowMajorSparseMatrix b) {
/* 33 */     return applySimple(a, (SparseMatrix)b);
/*    */   }
/*    */ 
/*    */   
/*    */   public R apply(DenseMatrix a, ColumnMajorSparseMatrix b) {
/* 38 */     return applySimple(a, (SparseMatrix)b);
/*    */   }
/*    */ 
/*    */   
/*    */   public R apply(RowMajorSparseMatrix a, DenseMatrix b) {
/* 43 */     return applySimple((SparseMatrix)a, b);
/*    */   }
/*    */ 
/*    */   
/*    */   public R apply(ColumnMajorSparseMatrix a, DenseMatrix b) {
/* 48 */     return applySimple((SparseMatrix)a, b);
/*    */   }
/*    */ 
/*    */   
/*    */   public R apply(RowMajorSparseMatrix a, RowMajorSparseMatrix b) {
/* 53 */     return applySimple((SparseMatrix)a, (SparseMatrix)b);
/*    */   }
/*    */ 
/*    */   
/*    */   public R apply(ColumnMajorSparseMatrix a, ColumnMajorSparseMatrix b) {
/* 58 */     return applySimple((SparseMatrix)a, (SparseMatrix)b);
/*    */   }
/*    */   
/*    */   public abstract R applySimple(DenseMatrix paramDenseMatrix, SparseMatrix paramSparseMatrix);
/*    */   
/*    */   public abstract R applySimple(SparseMatrix paramSparseMatrix, DenseMatrix paramDenseMatrix);
/*    */   
/*    */   public abstract R applySimple(SparseMatrix paramSparseMatrix1, SparseMatrix paramSparseMatrix2);
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/la4j/operation/SimpleMatrixMatrixOperation.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */