/*     */ package org.la4j.matrix;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import org.la4j.Matrices;
/*     */ import org.la4j.Matrix;
/*     */ import org.la4j.Vector;
/*     */ import org.la4j.iterator.RowMajorMatrixIterator;
/*     */ import org.la4j.iterator.VectorIterator;
/*     */ import org.la4j.matrix.sparse.CRSMatrix;
/*     */ import org.la4j.operation.MatrixMatrixOperation;
/*     */ import org.la4j.operation.MatrixOperation;
/*     */ import org.la4j.operation.MatrixVectorOperation;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class RowMajorSparseMatrix
/*     */   extends SparseMatrix
/*     */ {
/*     */   public static RowMajorSparseMatrix zero(int rows, int columns) {
/*  46 */     return (RowMajorSparseMatrix)CRSMatrix.zero(rows, columns);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static RowMajorSparseMatrix zero(int rows, int columns, int capacity) {
/*  54 */     return (RowMajorSparseMatrix)CRSMatrix.zero(rows, columns, capacity);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static RowMajorSparseMatrix diagonal(int size, double diagonal) {
/*  62 */     return (RowMajorSparseMatrix)CRSMatrix.diagonal(size, diagonal);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static RowMajorSparseMatrix identity(int size) {
/*  69 */     return (RowMajorSparseMatrix)CRSMatrix.identity(size);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static RowMajorSparseMatrix random(int rows, int columns, double density, Random random) {
/*  77 */     return (RowMajorSparseMatrix)CRSMatrix.random(rows, columns, density, random);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static RowMajorSparseMatrix randomSymmetric(int size, double density, Random random) {
/*  84 */     return (RowMajorSparseMatrix)CRSMatrix.randomSymmetric(size, density, random);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static RowMajorSparseMatrix from1DArray(int rows, int columns, double[] array) {
/*  92 */     return (RowMajorSparseMatrix)CRSMatrix.from1DArray(rows, columns, array);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static RowMajorSparseMatrix from2DArray(double[][] array) {
/* 100 */     return (RowMajorSparseMatrix)CRSMatrix.from2DArray(array);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static RowMajorSparseMatrix block(Matrix a, Matrix b, Matrix c, Matrix d) {
/* 108 */     return (RowMajorSparseMatrix)CRSMatrix.block(a, b, c, d);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static RowMajorSparseMatrix fromCSV(String csv) {
/* 119 */     return (RowMajorSparseMatrix)Matrix.fromCSV(csv).to(Matrices.SPARSE_ROW_MAJOR);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static RowMajorSparseMatrix fromMatrixMarket(String mm) {
/* 130 */     return (RowMajorSparseMatrix)Matrix.fromMatrixMarket(mm).to(Matrices.SPARSE_ROW_MAJOR);
/*     */   }
/*     */   
/*     */   public RowMajorSparseMatrix(int rows, int columns) {
/* 134 */     super(rows, columns);
/*     */   }
/*     */   
/*     */   public RowMajorSparseMatrix(int rows, int columns, int cardinality) {
/* 138 */     super(rows, columns, cardinality);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isRowMajor() {
/* 143 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public Matrix transpose() {
/* 148 */     Matrix result = ColumnMajorSparseMatrix.zero(this.columns, this.rows);
/* 149 */     RowMajorMatrixIterator rowMajorMatrixIterator = nonZeroRowMajorIterator();
/*     */     
/* 151 */     while (rowMajorMatrixIterator.hasNext()) {
/* 152 */       double x = ((Double)rowMajorMatrixIterator.next()).doubleValue();
/* 153 */       int i = rowMajorMatrixIterator.rowIndex();
/* 154 */       int j = rowMajorMatrixIterator.columnIndex();
/* 155 */       result.set(j, i, x);
/*     */     } 
/*     */     
/* 158 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public Matrix rotate() {
/* 163 */     Matrix result = ColumnMajorSparseMatrix.zero(this.columns, this.rows);
/*     */     
/* 165 */     Iterator<Integer> nzRows = iteratorOfNonZeroRows();
/* 166 */     List<Integer> reversedNzRows = new LinkedList<>();
/* 167 */     while (nzRows.hasNext()) {
/* 168 */       reversedNzRows.add(0, nzRows.next());
/*     */     }
/*     */     
/* 171 */     for (Iterator<Integer> i$ = reversedNzRows.iterator(); i$.hasNext(); ) { int i = ((Integer)i$.next()).intValue();
/* 172 */       VectorIterator it = nonZeroIteratorOfRow(i);
/* 173 */       while (it.hasNext()) {
/* 174 */         double x = ((Double)it.next()).doubleValue();
/* 175 */         int j = it.index();
/* 176 */         result.set(j, this.rows - 1 - i, x);
/*     */       }  }
/*     */ 
/*     */     
/* 180 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract Iterator<Integer> iteratorOfNonZeroRows();
/*     */   
/*     */   public <T> T apply(MatrixOperation<T> operation) {
/* 187 */     operation.ensureApplicableTo(this);
/* 188 */     return (T)operation.apply(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> T apply(MatrixMatrixOperation<T> operation, Matrix that) {
/* 193 */     return (T)that.apply(operation.partiallyApply(this));
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> T apply(MatrixVectorOperation<T> operation, Vector that) {
/* 198 */     return (T)that.apply(operation.partiallyApply(this));
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/la4j/matrix/RowMajorSparseMatrix.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */