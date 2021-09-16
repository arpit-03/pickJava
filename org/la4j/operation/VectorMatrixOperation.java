/*    */ package org.la4j.operation;
/*    */ 
/*    */ import org.la4j.Matrix;
/*    */ import org.la4j.Vector;
/*    */ import org.la4j.matrix.ColumnMajorSparseMatrix;
/*    */ import org.la4j.matrix.DenseMatrix;
/*    */ import org.la4j.matrix.RowMajorSparseMatrix;
/*    */ import org.la4j.vector.DenseVector;
/*    */ import org.la4j.vector.SparseVector;
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
/*    */ public abstract class VectorMatrixOperation<R>
/*    */ {
/*    */   public abstract R apply(SparseVector paramSparseVector, DenseMatrix paramDenseMatrix);
/*    */   
/*    */   public abstract R apply(SparseVector paramSparseVector, RowMajorSparseMatrix paramRowMajorSparseMatrix);
/*    */   
/*    */   public abstract R apply(SparseVector paramSparseVector, ColumnMajorSparseMatrix paramColumnMajorSparseMatrix);
/*    */   
/*    */   public abstract R apply(DenseVector paramDenseVector, DenseMatrix paramDenseMatrix);
/*    */   
/*    */   public abstract R apply(DenseVector paramDenseVector, RowMajorSparseMatrix paramRowMajorSparseMatrix);
/*    */   
/*    */   public abstract R apply(DenseVector paramDenseVector, ColumnMajorSparseMatrix paramColumnMajorSparseMatrix);
/*    */   
/*    */   public void ensureApplicableTo(Vector a, Matrix b) {}
/*    */   
/*    */   public MatrixOperation<R> partiallyApply(final SparseVector a) {
/* 45 */     return new MatrixOperation<R>()
/*    */       {
/*    */         public R apply(DenseMatrix b) {
/* 48 */           return VectorMatrixOperation.this.apply(a, b);
/*    */         }
/*    */ 
/*    */         
/*    */         public R apply(RowMajorSparseMatrix b) {
/* 53 */           return VectorMatrixOperation.this.apply(a, b);
/*    */         }
/*    */ 
/*    */         
/*    */         public R apply(ColumnMajorSparseMatrix b) {
/* 58 */           return VectorMatrixOperation.this.apply(a, b);
/*    */         }
/*    */ 
/*    */         
/*    */         public void ensureApplicableTo(Matrix b) {
/* 63 */           VectorMatrixOperation.this.ensureApplicableTo((Vector)a, b);
/*    */         }
/*    */       };
/*    */   }
/*    */   
/*    */   public MatrixOperation<R> partiallyApply(final DenseVector a) {
/* 69 */     return new MatrixOperation<R>()
/*    */       {
/*    */         public R apply(DenseMatrix b) {
/* 72 */           return VectorMatrixOperation.this.apply(a, b);
/*    */         }
/*    */ 
/*    */         
/*    */         public R apply(RowMajorSparseMatrix b) {
/* 77 */           return VectorMatrixOperation.this.apply(a, b);
/*    */         }
/*    */ 
/*    */         
/*    */         public R apply(ColumnMajorSparseMatrix b) {
/* 82 */           return VectorMatrixOperation.this.apply(a, b);
/*    */         }
/*    */ 
/*    */         
/*    */         public void ensureApplicableTo(Matrix b) {
/* 87 */           VectorMatrixOperation.this.ensureApplicableTo((Vector)a, b);
/*    */         }
/*    */       };
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/la4j/operation/VectorMatrixOperation.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */