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
/*     */ public class OoPlaceMatrixHadamardProduct
/*     */   extends SymmetricMatrixMatrixOperation<Matrix>
/*     */ {
/*     */   public Matrix applySymmetric(DenseMatrix a, SparseMatrix b) {
/*  37 */     Matrix result = b.blank();
/*  38 */     MatrixIterator it = b.nonZeroIterator();
/*     */     
/*  40 */     while (it.hasNext()) {
/*  41 */       double x = ((Double)it.next()).doubleValue();
/*  42 */       int i = it.rowIndex();
/*  43 */       int j = it.columnIndex();
/*  44 */       result.set(i, j, a.get(i, j) * x);
/*     */     } 
/*     */     
/*  47 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public Matrix applySymmetric(SparseMatrix a, SparseMatrix b) {
/*  52 */     Matrix result = a.blank();
/*  53 */     MatrixIterator these = a.nonZeroIterator();
/*  54 */     MatrixIterator those = b.nonZeroIterator();
/*  55 */     MatrixIterator both = these.andAlsoMultiply(those);
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
/*     */   public Matrix applySymmetric(RowMajorSparseMatrix a, ColumnMajorSparseMatrix b) {
/*  69 */     Matrix result = a.blank();
/*  70 */     RowMajorMatrixIterator these = a.nonZeroRowMajorIterator();
/*  71 */     RowMajorMatrixIterator those = b.nonZeroRowMajorIterator();
/*  72 */     MatrixIterator both = these.andAlsoMultiply((MatrixIterator)those);
/*     */     
/*  74 */     while (both.hasNext()) {
/*  75 */       double x = ((Double)both.next()).doubleValue();
/*  76 */       int i = both.rowIndex();
/*  77 */       int j = both.columnIndex();
/*  78 */       result.set(i, j, x);
/*     */     } 
/*     */     
/*  81 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public Matrix apply(DenseMatrix a, DenseMatrix b) {
/*  86 */     Matrix result = a.blank();
/*     */     
/*  88 */     for (int i = 0; i < a.rows(); i++) {
/*  89 */       for (int j = 0; j < a.columns(); j++) {
/*  90 */         result.set(i, j, a.get(i, j) * b.get(i, j));
/*     */       }
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


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/la4j/operation/ooplace/OoPlaceMatrixHadamardProduct.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */