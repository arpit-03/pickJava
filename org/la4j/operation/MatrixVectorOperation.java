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
/*    */ public abstract class MatrixVectorOperation<R>
/*    */ {
/*    */   public abstract R apply(DenseMatrix paramDenseMatrix, DenseVector paramDenseVector);
/*    */   
/*    */   public abstract R apply(DenseMatrix paramDenseMatrix, SparseVector paramSparseVector);
/*    */   
/*    */   public abstract R apply(RowMajorSparseMatrix paramRowMajorSparseMatrix, DenseVector paramDenseVector);
/*    */   
/*    */   public abstract R apply(RowMajorSparseMatrix paramRowMajorSparseMatrix, SparseVector paramSparseVector);
/*    */   
/*    */   public abstract R apply(ColumnMajorSparseMatrix paramColumnMajorSparseMatrix, DenseVector paramDenseVector);
/*    */   
/*    */   public abstract R apply(ColumnMajorSparseMatrix paramColumnMajorSparseMatrix, SparseVector paramSparseVector);
/*    */   
/*    */   public void ensureApplicableTo(Matrix a, Vector b) {}
/*    */   
/*    */   public VectorOperation<R> partiallyApply(final DenseMatrix a) {
/* 45 */     return new VectorOperation<R>()
/*    */       {
/*    */         public R apply(SparseVector b) {
/* 48 */           return MatrixVectorOperation.this.apply(a, b);
/*    */         }
/*    */ 
/*    */         
/*    */         public R apply(DenseVector b) {
/* 53 */           return MatrixVectorOperation.this.apply(a, b);
/*    */         }
/*    */ 
/*    */         
/*    */         public void ensureApplicableTo(Vector b) {
/* 58 */           MatrixVectorOperation.this.ensureApplicableTo((Matrix)a, b);
/*    */         }
/*    */       };
/*    */   }
/*    */   
/*    */   public VectorOperation<R> partiallyApply(final RowMajorSparseMatrix a) {
/* 64 */     return new VectorOperation<R>()
/*    */       {
/*    */         public R apply(SparseVector b) {
/* 67 */           return MatrixVectorOperation.this.apply(a, b);
/*    */         }
/*    */ 
/*    */         
/*    */         public R apply(DenseVector b) {
/* 72 */           return MatrixVectorOperation.this.apply(a, b);
/*    */         }
/*    */ 
/*    */         
/*    */         public void ensureApplicableTo(Vector b) {
/* 77 */           MatrixVectorOperation.this.ensureApplicableTo((Matrix)a, b);
/*    */         }
/*    */       };
/*    */   }
/*    */   
/*    */   public VectorOperation<R> partiallyApply(final ColumnMajorSparseMatrix a) {
/* 83 */     return new VectorOperation<R>()
/*    */       {
/*    */         public R apply(SparseVector b) {
/* 86 */           return MatrixVectorOperation.this.apply(a, b);
/*    */         }
/*    */ 
/*    */         
/*    */         public R apply(DenseVector b) {
/* 91 */           return MatrixVectorOperation.this.apply(a, b);
/*    */         }
/*    */ 
/*    */         
/*    */         public void ensureApplicableTo(Vector b) {
/* 96 */           MatrixVectorOperation.this.ensureApplicableTo((Matrix)a, b);
/*    */         }
/*    */       };
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/la4j/operation/MatrixVectorOperation.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */