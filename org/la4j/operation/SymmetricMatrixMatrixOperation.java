/*    */ package org.la4j.operation;
/*    */ 
/*    */ import org.la4j.matrix.ColumnMajorSparseMatrix;
/*    */ import org.la4j.matrix.DenseMatrix;
/*    */ import org.la4j.matrix.RowMajorSparseMatrix;
/*    */ import org.la4j.matrix.SparseMatrix;
/*    */ 
/*    */ public abstract class SymmetricMatrixMatrixOperation<R>
/*    */   extends MatrixMatrixOperation<R>
/*    */ {
/*    */   public R apply(DenseMatrix a, RowMajorSparseMatrix b) {
/* 12 */     return applySymmetric(a, (SparseMatrix)b);
/*    */   }
/*    */ 
/*    */   
/*    */   public R apply(DenseMatrix a, ColumnMajorSparseMatrix b) {
/* 17 */     return applySymmetric(a, (SparseMatrix)b);
/*    */   }
/*    */ 
/*    */   
/*    */   public R apply(RowMajorSparseMatrix a, DenseMatrix b) {
/* 22 */     return applySymmetric(b, (SparseMatrix)a);
/*    */   }
/*    */ 
/*    */   
/*    */   public R apply(RowMajorSparseMatrix a, ColumnMajorSparseMatrix b) {
/* 27 */     return applySymmetric(a, b);
/*    */   }
/*    */ 
/*    */   
/*    */   public R apply(ColumnMajorSparseMatrix a, DenseMatrix b) {
/* 32 */     return applySymmetric(b, (SparseMatrix)a);
/*    */   }
/*    */ 
/*    */   
/*    */   public R apply(RowMajorSparseMatrix a, RowMajorSparseMatrix b) {
/* 37 */     return applySymmetric((SparseMatrix)a, (SparseMatrix)b);
/*    */   }
/*    */ 
/*    */   
/*    */   public R apply(ColumnMajorSparseMatrix a, ColumnMajorSparseMatrix b) {
/* 42 */     return applySymmetric((SparseMatrix)a, (SparseMatrix)b);
/*    */   }
/*    */ 
/*    */   
/*    */   public R apply(ColumnMajorSparseMatrix a, RowMajorSparseMatrix b) {
/* 47 */     return applySymmetric(b, a);
/*    */   }
/*    */   
/*    */   public abstract R applySymmetric(DenseMatrix paramDenseMatrix, SparseMatrix paramSparseMatrix);
/*    */   
/*    */   public abstract R applySymmetric(SparseMatrix paramSparseMatrix1, SparseMatrix paramSparseMatrix2);
/*    */   
/*    */   public abstract R applySymmetric(RowMajorSparseMatrix paramRowMajorSparseMatrix, ColumnMajorSparseMatrix paramColumnMajorSparseMatrix);
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/la4j/operation/SymmetricMatrixMatrixOperation.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */