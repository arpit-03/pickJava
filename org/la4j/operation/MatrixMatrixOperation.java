/*     */ package org.la4j.operation;
/*     */ 
/*     */ import org.la4j.Matrix;
/*     */ import org.la4j.matrix.ColumnMajorSparseMatrix;
/*     */ import org.la4j.matrix.DenseMatrix;
/*     */ import org.la4j.matrix.RowMajorSparseMatrix;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class MatrixMatrixOperation<R>
/*     */ {
/*     */   public abstract R apply(DenseMatrix paramDenseMatrix1, DenseMatrix paramDenseMatrix2);
/*     */   
/*     */   public abstract R apply(DenseMatrix paramDenseMatrix, RowMajorSparseMatrix paramRowMajorSparseMatrix);
/*     */   
/*     */   public abstract R apply(DenseMatrix paramDenseMatrix, ColumnMajorSparseMatrix paramColumnMajorSparseMatrix);
/*     */   
/*     */   public abstract R apply(RowMajorSparseMatrix paramRowMajorSparseMatrix, DenseMatrix paramDenseMatrix);
/*     */   
/*     */   public abstract R apply(RowMajorSparseMatrix paramRowMajorSparseMatrix1, RowMajorSparseMatrix paramRowMajorSparseMatrix2);
/*     */   
/*     */   public abstract R apply(RowMajorSparseMatrix paramRowMajorSparseMatrix, ColumnMajorSparseMatrix paramColumnMajorSparseMatrix);
/*     */   
/*     */   public abstract R apply(ColumnMajorSparseMatrix paramColumnMajorSparseMatrix, DenseMatrix paramDenseMatrix);
/*     */   
/*     */   public abstract R apply(ColumnMajorSparseMatrix paramColumnMajorSparseMatrix, RowMajorSparseMatrix paramRowMajorSparseMatrix);
/*     */   
/*     */   public abstract R apply(ColumnMajorSparseMatrix paramColumnMajorSparseMatrix1, ColumnMajorSparseMatrix paramColumnMajorSparseMatrix2);
/*     */   
/*     */   public void ensureApplicableTo(Matrix a, Matrix b) {}
/*     */   
/*     */   public MatrixOperation<R> partiallyApply(final DenseMatrix a) {
/*  46 */     return new MatrixOperation<R>()
/*     */       {
/*     */         public R apply(DenseMatrix b) {
/*  49 */           return MatrixMatrixOperation.this.apply(a, b);
/*     */         }
/*     */ 
/*     */         
/*     */         public R apply(RowMajorSparseMatrix b) {
/*  54 */           return MatrixMatrixOperation.this.apply(a, b);
/*     */         }
/*     */ 
/*     */         
/*     */         public R apply(ColumnMajorSparseMatrix b) {
/*  59 */           return MatrixMatrixOperation.this.apply(a, b);
/*     */         }
/*     */ 
/*     */         
/*     */         public void ensureApplicableTo(Matrix b) {
/*  64 */           MatrixMatrixOperation.this.ensureApplicableTo((Matrix)a, b);
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public MatrixOperation<R> partiallyApply(final RowMajorSparseMatrix a) {
/*  70 */     return new MatrixOperation<R>()
/*     */       {
/*     */         public R apply(DenseMatrix b) {
/*  73 */           return MatrixMatrixOperation.this.apply(a, b);
/*     */         }
/*     */ 
/*     */         
/*     */         public R apply(RowMajorSparseMatrix b) {
/*  78 */           return MatrixMatrixOperation.this.apply(a, b);
/*     */         }
/*     */ 
/*     */         
/*     */         public R apply(ColumnMajorSparseMatrix b) {
/*  83 */           return MatrixMatrixOperation.this.apply(a, b);
/*     */         }
/*     */ 
/*     */         
/*     */         public void ensureApplicableTo(Matrix b) {
/*  88 */           MatrixMatrixOperation.this.ensureApplicableTo((Matrix)a, b);
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public MatrixOperation<R> partiallyApply(final ColumnMajorSparseMatrix a) {
/*  94 */     return new MatrixOperation<R>()
/*     */       {
/*     */         public R apply(DenseMatrix b) {
/*  97 */           return MatrixMatrixOperation.this.apply(a, b);
/*     */         }
/*     */ 
/*     */         
/*     */         public R apply(RowMajorSparseMatrix b) {
/* 102 */           return MatrixMatrixOperation.this.apply(a, b);
/*     */         }
/*     */ 
/*     */         
/*     */         public R apply(ColumnMajorSparseMatrix b) {
/* 107 */           return MatrixMatrixOperation.this.apply(a, b);
/*     */         }
/*     */ 
/*     */         
/*     */         public void ensureApplicableTo(Matrix b) {
/* 112 */           MatrixMatrixOperation.this.ensureApplicableTo((Matrix)a, b);
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/la4j/operation/MatrixMatrixOperation.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */