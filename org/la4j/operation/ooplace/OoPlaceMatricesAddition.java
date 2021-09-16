/*     */ package org.la4j.operation.ooplace;
/*     */ 
/*     */ import org.la4j.Matrix;
/*     */ import org.la4j.iterator.MatrixIterator;
/*     */ import org.la4j.iterator.RowMajorMatrixIterator;
/*     */ import org.la4j.matrix.ColumnMajorSparseMatrix;
/*     */ import org.la4j.matrix.DenseMatrix;
/*     */ import org.la4j.matrix.RowMajorSparseMatrix;
/*     */ import org.la4j.matrix.SparseMatrix;
/*     */ import org.la4j.operation.SymmetricMatrixMatrixOperation;
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
/*     */ 
/*     */ 
/*     */ public class OoPlaceMatricesAddition
/*     */   extends SymmetricMatrixMatrixOperation<Matrix>
/*     */ {
/*     */   public Matrix applySymmetric(DenseMatrix a, SparseMatrix b) {
/*  37 */     Matrix result = a.copy();
/*  38 */     MatrixIterator it = b.nonZeroIterator();
/*     */     
/*  40 */     while (it.hasNext()) {
/*  41 */       double x = ((Double)it.next()).doubleValue();
/*  42 */       int i = it.rowIndex();
/*  43 */       int j = it.columnIndex();
/*  44 */       result.set(i, j, result.get(i, j) + x);
/*     */     } 
/*     */     
/*  47 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public Matrix applySymmetric(RowMajorSparseMatrix a, ColumnMajorSparseMatrix b) {
/*  52 */     Matrix result = a.blank();
/*  53 */     RowMajorMatrixIterator these = a.nonZeroRowMajorIterator();
/*  54 */     RowMajorMatrixIterator those = b.nonZeroRowMajorIterator();
/*  55 */     MatrixIterator both = these.orElseAdd((MatrixIterator)those);
/*     */     
/*  57 */     while (both.hasNext()) {
/*  58 */       double x = ((Double)both.next()).doubleValue();
/*  59 */       int i = both.rowIndex();
/*  60 */       int j = both.columnIndex();
/*  61 */       result.set(i, j, x);
/*     */     } 
/*     */     
/*  64 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public Matrix apply(DenseMatrix a, DenseMatrix b) {
/*  69 */     Matrix result = a.blank();
/*     */     
/*  71 */     for (int i = 0; i < a.rows(); i++) {
/*  72 */       for (int j = 0; j < a.columns(); j++) {
/*  73 */         result.set(i, j, a.get(i, j) + b.get(i, j));
/*     */       }
/*     */     } 
/*     */     
/*  77 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public Matrix applySymmetric(SparseMatrix a, SparseMatrix b) {
/*  82 */     Matrix result = a.blank();
/*  83 */     MatrixIterator these = a.nonZeroIterator();
/*  84 */     MatrixIterator those = b.nonZeroIterator();
/*  85 */     MatrixIterator both = these.orElseAdd(those);
/*     */     
/*  87 */     while (both.hasNext()) {
/*  88 */       double x = ((Double)both.next()).doubleValue();
/*  89 */       int i = both.rowIndex();
/*  90 */       int j = both.columnIndex();
/*  91 */       result.set(i, j, x);
/*     */     } 
/*     */     
/*  94 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public void ensureApplicableTo(Matrix a, Matrix b) {
/*  99 */     if (a.rows() != b.rows() || a.columns() != b.columns())
/* 100 */       throw new IllegalArgumentException("Given matrices should have the same shape: " + a.rows() + "x" + a.columns() + " does not equal to " + b.rows() + "x" + b.columns() + "."); 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/la4j/operation/ooplace/OoPlaceMatricesAddition.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */