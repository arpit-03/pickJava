/*     */ package org.la4j.operation.ooplace;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.la4j.Matrices;
/*     */ import org.la4j.Matrix;
/*     */ import org.la4j.Vector;
/*     */ import org.la4j.iterator.ColumnMajorMatrixIterator;
/*     */ import org.la4j.iterator.RowMajorMatrixIterator;
/*     */ import org.la4j.iterator.VectorIterator;
/*     */ import org.la4j.matrix.ColumnMajorSparseMatrix;
/*     */ import org.la4j.matrix.DenseMatrix;
/*     */ import org.la4j.matrix.RowMajorSparseMatrix;
/*     */ import org.la4j.operation.MatrixMatrixOperation;
/*     */ 
/*     */ public class OoPlaceMatricesMultiplication
/*     */   extends MatrixMatrixOperation<Matrix>
/*     */ {
/*     */   public Matrix apply(DenseMatrix a, DenseMatrix b) {
/*  21 */     Matrix result = a.blankOfShape(a.rows(), b.columns());
/*     */     
/*  23 */     for (int j = 0; j < b.columns(); j++) {
/*  24 */       Vector column = b.getColumn(j);
/*  25 */       for (int i = 0; i < a.rows(); i++) {
/*  26 */         double acc = 0.0D;
/*  27 */         for (int k = 0; k < a.columns(); k++) {
/*  28 */           acc += a.get(i, k) * column.get(k);
/*     */         }
/*  30 */         result.set(i, j, acc);
/*     */       } 
/*     */     } 
/*     */     
/*  34 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public Matrix apply(DenseMatrix a, RowMajorSparseMatrix b) {
/*  39 */     ColumnMajorSparseMatrix columnMajorSparseMatrix = ColumnMajorSparseMatrix.zero(a.rows(), b.columns());
/*  40 */     RowMajorMatrixIterator rowMajorMatrixIterator = b.nonZeroRowMajorIterator();
/*     */     
/*  42 */     while (rowMajorMatrixIterator.hasNext()) {
/*  43 */       double x = ((Double)rowMajorMatrixIterator.next()).doubleValue();
/*  44 */       int i = rowMajorMatrixIterator.rowIndex();
/*  45 */       int j = rowMajorMatrixIterator.columnIndex();
/*     */       
/*  47 */       for (int k = 0; k < a.rows(); k++) {
/*  48 */         columnMajorSparseMatrix.updateAt(k, j, Matrices.asPlusFunction(x * a.get(k, i)));
/*     */       }
/*     */     } 
/*     */     
/*  52 */     return (Matrix)columnMajorSparseMatrix;
/*     */   }
/*     */ 
/*     */   
/*     */   public Matrix apply(DenseMatrix a, ColumnMajorSparseMatrix b) {
/*  57 */     Matrix result = b.blankOfShape(a.rows(), b.columns());
/*  58 */     Iterator<Integer> nzColumns = b.iteratorOrNonZeroColumns();
/*     */     
/*  60 */     while (nzColumns.hasNext()) {
/*  61 */       int j = ((Integer)nzColumns.next()).intValue();
/*     */       
/*  63 */       for (int i = 0; i < a.rows(); i++) {
/*  64 */         double acc = 0.0D;
/*  65 */         VectorIterator it = b.nonZeroIteratorOfColumn(j);
/*  66 */         while (it.hasNext()) {
/*  67 */           double x = ((Double)it.next()).doubleValue();
/*  68 */           acc += x * a.get(i, it.index());
/*     */         } 
/*     */         
/*  71 */         result.set(i, j, acc);
/*     */       } 
/*     */     } 
/*     */     
/*  75 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public Matrix apply(RowMajorSparseMatrix a, DenseMatrix b) {
/*  80 */     Matrix result = a.blankOfShape(a.rows(), b.columns());
/*  81 */     Iterator<Integer> nzRows = a.iteratorOfNonZeroRows();
/*     */     
/*  83 */     while (nzRows.hasNext()) {
/*  84 */       int i = ((Integer)nzRows.next()).intValue();
/*     */       
/*  86 */       for (int j = 0; j < b.columns(); j++) {
/*  87 */         double acc = 0.0D;
/*  88 */         VectorIterator it = a.nonZeroIteratorOfRow(i);
/*  89 */         while (it.hasNext()) {
/*  90 */           double x = ((Double)it.next()).doubleValue();
/*  91 */           acc += x * b.get(it.index(), j);
/*     */         } 
/*     */         
/*  94 */         result.set(i, j, acc);
/*     */       } 
/*     */     } 
/*     */     
/*  98 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Matrix apply(RowMajorSparseMatrix a, RowMajorSparseMatrix b) {
/* 104 */     Matrix result = a.blankOfShape(a.rows(), b.columns());
/* 105 */     RowMajorMatrixIterator rowMajorMatrixIterator = a.nonZeroRowMajorIterator();
/*     */     
/* 107 */     while (rowMajorMatrixIterator.hasNext()) {
/* 108 */       double x = ((Double)rowMajorMatrixIterator.next()).doubleValue();
/* 109 */       int i = rowMajorMatrixIterator.rowIndex();
/* 110 */       int j = rowMajorMatrixIterator.columnIndex();
/*     */       
/* 112 */       VectorIterator those = b.nonZeroIteratorOfRow(j);
/* 113 */       while (those.hasNext()) {
/* 114 */         double y = ((Double)those.next()).doubleValue();
/* 115 */         int k = those.index();
/* 116 */         result.updateAt(i, k, Matrices.asPlusFunction(x * y));
/*     */       } 
/*     */     } 
/*     */     
/* 120 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public Matrix apply(RowMajorSparseMatrix a, ColumnMajorSparseMatrix b) {
/* 125 */     Matrix result = a.blankOfShape(a.rows(), b.columns());
/* 126 */     Iterator<Integer> nzRows = a.iteratorOfNonZeroRows();
/* 127 */     Iterator<Integer> nzColumnsIt = b.iteratorOrNonZeroColumns();
/* 128 */     List<Integer> nzColumns = new ArrayList<>();
/* 129 */     while (nzColumnsIt.hasNext()) {
/* 130 */       nzColumns.add(nzColumnsIt.next());
/*     */     }
/*     */     
/* 133 */     while (nzRows.hasNext()) {
/* 134 */       int i = ((Integer)nzRows.next()).intValue();
/* 135 */       for (Iterator<Integer> i$ = nzColumns.iterator(); i$.hasNext(); ) { int j = ((Integer)i$.next()).intValue();
/* 136 */         result.set(i, j, a.nonZeroIteratorOfRow(i).innerProduct(b.nonZeroIteratorOfColumn(j))); }
/*     */     
/*     */     } 
/*     */ 
/*     */     
/* 141 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public Matrix apply(ColumnMajorSparseMatrix a, DenseMatrix b) {
/* 146 */     Matrix result = a.blankOfShape(a.rows(), b.columns());
/* 147 */     ColumnMajorMatrixIterator columnMajorMatrixIterator = a.nonZeroColumnMajorIterator();
/*     */     
/* 149 */     while (columnMajorMatrixIterator.hasNext()) {
/* 150 */       double x = ((Double)columnMajorMatrixIterator.next()).doubleValue();
/* 151 */       int i = columnMajorMatrixIterator.rowIndex();
/* 152 */       int j = columnMajorMatrixIterator.columnIndex();
/*     */       
/* 154 */       for (int k = 0; k < b.columns(); k++) {
/* 155 */         result.updateAt(i, k, Matrices.asPlusFunction(x * b.get(j, k)));
/*     */       }
/*     */     } 
/*     */     
/* 159 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Matrix apply(ColumnMajorSparseMatrix a, RowMajorSparseMatrix b) {
/* 165 */     Matrix result = b.blankOfShape(a.rows(), b.columns());
/* 166 */     ColumnMajorMatrixIterator columnMajorMatrixIterator = a.nonZeroColumnMajorIterator();
/*     */     
/* 168 */     while (columnMajorMatrixIterator.hasNext()) {
/* 169 */       double x = ((Double)columnMajorMatrixIterator.next()).doubleValue();
/* 170 */       int i = columnMajorMatrixIterator.rowIndex();
/* 171 */       int j = columnMajorMatrixIterator.columnIndex();
/*     */       
/* 173 */       VectorIterator those = b.nonZeroIteratorOfRow(j);
/* 174 */       while (those.hasNext()) {
/* 175 */         double y = ((Double)those.next()).doubleValue();
/* 176 */         int k = those.index();
/* 177 */         result.updateAt(i, k, Matrices.asPlusFunction(x * y));
/*     */       } 
/*     */     } 
/*     */     
/* 181 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Matrix apply(ColumnMajorSparseMatrix a, ColumnMajorSparseMatrix b) {
/* 187 */     Matrix result = a.blankOfShape(a.rows(), b.columns());
/* 188 */     ColumnMajorMatrixIterator columnMajorMatrixIterator = b.nonZeroColumnMajorIterator();
/*     */     
/* 190 */     while (columnMajorMatrixIterator.hasNext()) {
/* 191 */       double x = ((Double)columnMajorMatrixIterator.next()).doubleValue();
/* 192 */       int i = columnMajorMatrixIterator.rowIndex();
/* 193 */       int j = columnMajorMatrixIterator.columnIndex();
/*     */       
/* 195 */       VectorIterator those = a.nonZeroIteratorOfColumn(i);
/* 196 */       while (those.hasNext()) {
/* 197 */         double y = ((Double)those.next()).doubleValue();
/* 198 */         int k = those.index();
/* 199 */         result.updateAt(k, j, Matrices.asPlusFunction(x * y));
/*     */       } 
/*     */     } 
/*     */     
/* 203 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public void ensureApplicableTo(Matrix a, Matrix b) {
/* 208 */     if (a.columns() != b.rows())
/* 209 */       throw new IllegalArgumentException("The number of rows in the left-hand matrix should be equal to the number of columns in the right-hand matrix: " + a.rows() + " does not equal to " + b.columns() + "."); 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/la4j/operation/ooplace/OoPlaceMatricesMultiplication.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */