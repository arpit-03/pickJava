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
/*    */ 
/*    */ public abstract class CommonMatrixOperation<R>
/*    */   extends MatrixOperation<R>
/*    */ {
/*    */   public R apply(DenseMatrix a) {
/* 33 */     return applyCommon((Matrix)a);
/*    */   }
/*    */ 
/*    */   
/*    */   public R apply(RowMajorSparseMatrix a) {
/* 38 */     return applyCommon((Matrix)a);
/*    */   }
/*    */ 
/*    */   
/*    */   public R apply(ColumnMajorSparseMatrix a) {
/* 43 */     return applyCommon((Matrix)a);
/*    */   }
/*    */   
/*    */   public abstract R applyCommon(Matrix paramMatrix);
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/la4j/operation/CommonMatrixOperation.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */