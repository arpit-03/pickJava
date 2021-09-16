/*     */ package org.la4j.operation.ooplace;
/*     */ 
/*     */ import org.la4j.Matrix;
/*     */ import org.la4j.iterator.ColumnMajorMatrixIterator;
/*     */ import org.la4j.iterator.MatrixIterator;
/*     */ import org.la4j.iterator.RowMajorMatrixIterator;
/*     */ import org.la4j.matrix.ColumnMajorSparseMatrix;
/*     */ import org.la4j.matrix.DenseMatrix;
/*     */ import org.la4j.matrix.RowMajorSparseMatrix;
/*     */ import org.la4j.matrix.SparseMatrix;
/*     */ import org.la4j.operation.SimpleMatrixMatrixOperation;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class OoPlaceMatricesSubtraction
/*     */   extends SimpleMatrixMatrixOperation<Matrix>
/*     */ {
/*     */   public Matrix apply(DenseMatrix a, DenseMatrix b) {
/*  36 */     Matrix result = a.blank();
/*     */     
/*  38 */     for (int i = 0; i < a.rows(); i++) {
/*  39 */       for (int j = 0; j < a.columns(); j++) {
/*  40 */         result.set(i, j, a.get(i, j) - b.get(i, j));
/*     */       }
/*     */     } 
/*     */     
/*  44 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public Matrix applySimple(DenseMatrix a, SparseMatrix b) {
/*  49 */     Matrix result = a.copy();
/*  50 */     MatrixIterator it = b.nonZeroIterator();
/*     */     
/*  52 */     while (it.hasNext()) {
/*  53 */       double x = ((Double)it.next()).doubleValue();
/*  54 */       int i = it.rowIndex();
/*  55 */       int j = it.columnIndex();
/*  56 */       result.set(i, j, result.get(i, j) - x);
/*     */     } 
/*     */     
/*  59 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public Matrix applySimple(SparseMatrix a, DenseMatrix b) {
/*  64 */     Matrix result = b.multiply(-1.0D);
/*  65 */     MatrixIterator it = a.nonZeroIterator();
/*     */     
/*  67 */     while (it.hasNext()) {
/*  68 */       double x = ((Double)it.next()).doubleValue();
/*  69 */       int i = it.rowIndex();
/*  70 */       int j = it.columnIndex();
/*  71 */       result.set(i, j, result.get(i, j) + x);
/*     */     } 
/*     */     
/*  74 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public Matrix applySimple(SparseMatrix a, SparseMatrix b) {
/*  79 */     Matrix result = a.blank();
/*  80 */     MatrixIterator these = a.nonZeroIterator();
/*  81 */     MatrixIterator those = b.nonZeroIterator();
/*  82 */     MatrixIterator both = these.orElseSubtract(those);
/*     */     
/*  84 */     while (both.hasNext()) {
/*  85 */       double x = ((Double)both.next()).doubleValue();
/*  86 */       int i = both.rowIndex();
/*  87 */       int j = both.columnIndex();
/*  88 */       result.set(i, j, x);
/*     */     } 
/*     */     
/*  91 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public Matrix apply(RowMajorSparseMatrix a, ColumnMajorSparseMatrix b) {
/*  96 */     Matrix result = a.blank();
/*  97 */     RowMajorMatrixIterator rowMajorMatrixIterator1 = a.nonZeroRowMajorIterator();
/*  98 */     RowMajorMatrixIterator rowMajorMatrixIterator2 = b.nonZeroRowMajorIterator();
/*  99 */     MatrixIterator both = rowMajorMatrixIterator1.orElseSubtract((MatrixIterator)rowMajorMatrixIterator2);
/*     */     
/* 101 */     while (both.hasNext()) {
/* 102 */       double x = ((Double)both.next()).doubleValue();
/* 103 */       int i = both.rowIndex();
/* 104 */       int j = both.columnIndex();
/* 105 */       result.set(i, j, x);
/*     */     } 
/*     */     
/* 108 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public Matrix apply(ColumnMajorSparseMatrix a, RowMajorSparseMatrix b) {
/* 113 */     Matrix result = a.blank();
/* 114 */     ColumnMajorMatrixIterator columnMajorMatrixIterator1 = a.nonZeroColumnMajorIterator();
/* 115 */     ColumnMajorMatrixIterator columnMajorMatrixIterator2 = b.nonZeroColumnMajorIterator();
/* 116 */     MatrixIterator both = columnMajorMatrixIterator1.orElseSubtract((MatrixIterator)columnMajorMatrixIterator2);
/*     */     
/* 118 */     while (both.hasNext()) {
/* 119 */       double x = ((Double)both.next()).doubleValue();
/* 120 */       int i = both.rowIndex();
/* 121 */       int j = both.columnIndex();
/* 122 */       result.set(i, j, x);
/*     */     } 
/*     */     
/* 125 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public void ensureApplicableTo(Matrix a, Matrix b) {
/* 130 */     if (a.rows() != b.rows() || a.columns() != b.columns())
/* 131 */       throw new IllegalArgumentException("Given matrices should have the same shape: " + a.rows() + "x" + a.columns() + " does not equal to " + b.rows() + "x" + b.columns() + "."); 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/la4j/operation/ooplace/OoPlaceMatricesSubtraction.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */