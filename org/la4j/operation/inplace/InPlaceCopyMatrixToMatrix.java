/*    */ package org.la4j.operation.inplace;
/*    */ 
/*    */ import org.la4j.Matrix;
/*    */ import org.la4j.iterator.MatrixIterator;
/*    */ import org.la4j.matrix.ColumnMajorSparseMatrix;
/*    */ import org.la4j.matrix.DenseMatrix;
/*    */ import org.la4j.matrix.RowMajorSparseMatrix;
/*    */ import org.la4j.matrix.SparseMatrix;
/*    */ import org.la4j.operation.SimpleMatrixMatrixOperation;
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
/*    */ public class InPlaceCopyMatrixToMatrix
/*    */   extends SimpleMatrixMatrixOperation<Matrix>
/*    */ {
/*    */   public Matrix applySimple(DenseMatrix a, SparseMatrix b) {
/* 36 */     MatrixIterator it = b.iterator();
/* 37 */     while (it.hasNext()) {
/* 38 */       it.next();
/* 39 */       int i = it.rowIndex();
/* 40 */       int j = it.columnIndex();
/* 41 */       double x = a.get(i, j);
/* 42 */       if (x != 0.0D) {
/* 43 */         it.set(x);
/*    */       }
/*    */     } 
/*    */     
/* 47 */     return (Matrix)b;
/*    */   }
/*    */ 
/*    */   
/*    */   public Matrix applySimple(SparseMatrix a, DenseMatrix b) {
/* 52 */     return fromSparseToMatrix(a, (Matrix)b);
/*    */   }
/*    */ 
/*    */   
/*    */   public Matrix applySimple(SparseMatrix a, SparseMatrix b) {
/* 57 */     return fromSparseToMatrix(a, (Matrix)b);
/*    */   }
/*    */ 
/*    */   
/*    */   public Matrix apply(DenseMatrix a, DenseMatrix b) {
/* 62 */     for (int i = 0; i < a.rows(); i++) {
/* 63 */       for (int j = 0; j < a.columns(); j++) {
/* 64 */         b.set(i, j, a.get(i, j));
/*    */       }
/*    */     } 
/*    */     
/* 68 */     return (Matrix)b;
/*    */   }
/*    */ 
/*    */   
/*    */   public Matrix apply(RowMajorSparseMatrix a, ColumnMajorSparseMatrix b) {
/* 73 */     return fromSparseToMatrix((SparseMatrix)a, (Matrix)b);
/*    */   }
/*    */ 
/*    */   
/*    */   public Matrix apply(ColumnMajorSparseMatrix a, RowMajorSparseMatrix b) {
/* 78 */     return fromSparseToMatrix((SparseMatrix)a, (Matrix)b);
/*    */   }
/*    */   
/*    */   private Matrix fromSparseToMatrix(SparseMatrix a, Matrix b) {
/* 82 */     MatrixIterator it = a.nonZeroIterator();
/* 83 */     while (it.hasNext()) {
/* 84 */       double x = ((Double)it.next()).doubleValue();
/* 85 */       int i = it.rowIndex();
/* 86 */       int j = it.columnIndex();
/* 87 */       b.set(i, j, x);
/*    */     } 
/*    */     
/* 90 */     return b;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/la4j/operation/inplace/InPlaceCopyMatrixToMatrix.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */