/*     */ package org.la4j.matrix;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.Random;
/*     */ import org.la4j.Matrices;
/*     */ import org.la4j.Matrix;
/*     */ import org.la4j.Vector;
/*     */ import org.la4j.iterator.ColumnMajorMatrixIterator;
/*     */ import org.la4j.iterator.MatrixIterator;
/*     */ import org.la4j.matrix.sparse.CCSMatrix;
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
/*     */ public abstract class ColumnMajorSparseMatrix
/*     */   extends SparseMatrix
/*     */ {
/*     */   public static ColumnMajorSparseMatrix zero(int rows, int columns) {
/*  44 */     return (ColumnMajorSparseMatrix)CCSMatrix.zero(rows, columns);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ColumnMajorSparseMatrix zero(int rows, int columns, int capacity) {
/*  52 */     return (ColumnMajorSparseMatrix)CCSMatrix.zero(rows, columns, capacity);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ColumnMajorSparseMatrix diagonal(int size, double diagonal) {
/*  60 */     return (ColumnMajorSparseMatrix)CCSMatrix.diagonal(size, diagonal);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ColumnMajorSparseMatrix identity(int size) {
/*  67 */     return (ColumnMajorSparseMatrix)CCSMatrix.identity(size);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ColumnMajorSparseMatrix random(int rows, int columns, double density, Random random) {
/*  75 */     return (ColumnMajorSparseMatrix)CCSMatrix.random(rows, columns, density, random);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ColumnMajorSparseMatrix randomSymmetric(int size, double density, Random random) {
/*  82 */     return (ColumnMajorSparseMatrix)CCSMatrix.randomSymmetric(size, density, random);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ColumnMajorSparseMatrix from1DArray(int rows, int columns, double[] array) {
/*  90 */     return (ColumnMajorSparseMatrix)CCSMatrix.from1DArray(rows, columns, array);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ColumnMajorSparseMatrix from2DArray(double[][] array) {
/*  98 */     return (ColumnMajorSparseMatrix)CCSMatrix.from2DArray(array);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ColumnMajorSparseMatrix block(Matrix a, Matrix b, Matrix c, Matrix d) {
/* 106 */     return (ColumnMajorSparseMatrix)CCSMatrix.block(a, b, c, d);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ColumnMajorSparseMatrix fromCSV(String csv) {
/* 117 */     return (ColumnMajorSparseMatrix)Matrix.fromCSV(csv).to(Matrices.SPARSE_COLUMN_MAJOR);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ColumnMajorSparseMatrix fromMatrixMarket(String mm) {
/* 128 */     return (ColumnMajorSparseMatrix)Matrix.fromMatrixMarket(mm).to(Matrices.SPARSE_COLUMN_MAJOR);
/*     */   }
/*     */   
/*     */   public ColumnMajorSparseMatrix(int rows, int columns) {
/* 132 */     super(rows, columns);
/*     */   }
/*     */   
/*     */   public ColumnMajorSparseMatrix(int rows, int columns, int cardinality) {
/* 136 */     super(rows, columns, cardinality);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isRowMajor() {
/* 141 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public Matrix transpose() {
/* 146 */     Matrix result = RowMajorSparseMatrix.zero(this.columns, this.rows);
/* 147 */     ColumnMajorMatrixIterator columnMajorMatrixIterator = nonZeroColumnMajorIterator();
/*     */     
/* 149 */     while (columnMajorMatrixIterator.hasNext()) {
/* 150 */       double x = ((Double)columnMajorMatrixIterator.next()).doubleValue();
/* 151 */       int i = columnMajorMatrixIterator.rowIndex();
/* 152 */       int j = columnMajorMatrixIterator.columnIndex();
/* 153 */       result.set(j, i, x);
/*     */     } 
/*     */     
/* 156 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Matrix rotate() {
/* 162 */     Matrix result = RowMajorSparseMatrix.zero(this.columns, this.rows);
/* 163 */     ColumnMajorMatrixIterator columnMajorMatrixIterator = nonZeroColumnMajorIterator();
/*     */     
/* 165 */     while (columnMajorMatrixIterator.hasNext()) {
/* 166 */       double x = ((Double)columnMajorMatrixIterator.next()).doubleValue();
/* 167 */       int i = columnMajorMatrixIterator.rowIndex();
/* 168 */       int j = columnMajorMatrixIterator.columnIndex();
/* 169 */       result.set(j, this.rows - 1 - i, x);
/*     */     } 
/*     */     
/* 172 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public ColumnMajorMatrixIterator iterator() {
/* 177 */     return columnMajorIterator();
/*     */   }
/*     */ 
/*     */   
/*     */   public MatrixIterator nonZeroIterator() {
/* 182 */     return (MatrixIterator)nonZeroColumnMajorIterator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T apply(MatrixOperation<T> operation) {
/* 189 */     operation.ensureApplicableTo(this);
/* 190 */     return (T)operation.apply(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> T apply(MatrixMatrixOperation<T> operation, Matrix that) {
/* 195 */     return (T)that.apply(operation.partiallyApply(this));
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> T apply(MatrixVectorOperation<T> operation, Vector that) {
/* 200 */     return (T)that.apply(operation.partiallyApply(this));
/*     */   }
/*     */   
/*     */   public abstract Iterator<Integer> iteratorOrNonZeroColumns();
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/la4j/matrix/ColumnMajorSparseMatrix.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */