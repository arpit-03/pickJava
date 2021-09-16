/*     */ package org.la4j.matrix;
/*     */ 
/*     */ import java.text.NumberFormat;
/*     */ import java.util.Random;
/*     */ import org.la4j.Matrices;
/*     */ import org.la4j.Matrix;
/*     */ import org.la4j.Vector;
/*     */ import org.la4j.matrix.dense.Basic1DMatrix;
/*     */ import org.la4j.matrix.dense.Basic2DMatrix;
/*     */ import org.la4j.operation.MatrixMatrixOperation;
/*     */ import org.la4j.operation.MatrixOperation;
/*     */ import org.la4j.operation.MatrixVectorOperation;
/*     */ import org.la4j.vector.DenseVector;
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
/*     */ public abstract class DenseMatrix
/*     */   extends Matrix
/*     */ {
/*     */   public static DenseMatrix zero(int rows, int columns) {
/*  44 */     return (DenseMatrix)Basic2DMatrix.zero(rows, columns);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DenseMatrix constant(int rows, int columns, double constant) {
/*  51 */     return (DenseMatrix)Basic2DMatrix.constant(rows, columns, constant);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DenseMatrix diagonal(int size, double diagonal) {
/*  59 */     return (DenseMatrix)Basic2DMatrix.diagonal(size, diagonal);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DenseMatrix unit(int rows, int columns) {
/*  67 */     return (DenseMatrix)Basic2DMatrix.unit(rows, columns);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DenseMatrix identity(int size) {
/*  74 */     return (DenseMatrix)Basic2DMatrix.identity(size);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DenseMatrix random(int rows, int columns, Random random) {
/*  82 */     return (DenseMatrix)Basic2DMatrix.random(rows, columns, random);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DenseMatrix randomSymmetric(int size, Random random) {
/*  89 */     return (DenseMatrix)Basic2DMatrix.randomSymmetric(size, random);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DenseMatrix from1DArray(int rows, int columns, double[] array) {
/*  97 */     return (DenseMatrix)Basic1DMatrix.from1DArray(rows, columns, array);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DenseMatrix from2DArray(double[][] array) {
/* 105 */     return (DenseMatrix)Basic2DMatrix.from2DArray(array);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DenseMatrix block(Matrix a, Matrix b, Matrix c, Matrix d) {
/* 113 */     return (DenseMatrix)Basic2DMatrix.block(a, b, c, d);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DenseMatrix fromCSV(String csv) {
/* 124 */     return (DenseMatrix)Matrix.fromCSV(csv).to(Matrices.DENSE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DenseMatrix fromMatrixMarket(String mm) {
/* 135 */     return (DenseMatrix)Matrix.fromMatrixMarket(mm).to(Matrices.DENSE);
/*     */   }
/*     */   
/*     */   public DenseMatrix(int rows, int columns) {
/* 139 */     super(rows, columns);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract double[][] toArray();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector getRow(int i) {
/* 151 */     DenseVector denseVector = DenseVector.zero(this.columns);
/*     */     
/* 153 */     for (int j = 0; j < this.columns; j++) {
/* 154 */       denseVector.set(j, get(i, j));
/*     */     }
/*     */     
/* 157 */     return (Vector)denseVector;
/*     */   }
/*     */ 
/*     */   
/*     */   public Vector getColumn(int j) {
/* 162 */     DenseVector denseVector = DenseVector.zero(this.rows);
/*     */     
/* 164 */     for (int i = 0; i < this.rows; i++) {
/* 165 */       denseVector.set(i, get(i, j));
/*     */     }
/*     */     
/* 168 */     return (Vector)denseVector;
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> T apply(MatrixOperation<T> operation) {
/* 173 */     operation.ensureApplicableTo(this);
/* 174 */     return (T)operation.apply(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> T apply(MatrixMatrixOperation<T> operation, Matrix that) {
/* 179 */     return (T)that.apply(operation.partiallyApply(this));
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> T apply(MatrixVectorOperation<T> operation, Vector that) {
/* 184 */     return (T)that.apply(operation.partiallyApply(this));
/*     */   }
/*     */ 
/*     */   
/*     */   public String toMatrixMarket(NumberFormat formatter) {
/* 189 */     StringBuilder out = new StringBuilder();
/*     */     
/* 191 */     out.append("%%MatrixMarket matrix array real general\n");
/* 192 */     out.append(this.rows).append(' ').append(this.columns).append('\n');
/* 193 */     for (int i = 0; i < this.rows; i++) {
/* 194 */       for (int j = 0; j < this.columns; j++) {
/* 195 */         out.append(formatter.format(get(i, j))).append('\n');
/*     */       }
/*     */     } 
/*     */     
/* 199 */     return out.toString();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/la4j/matrix/DenseMatrix.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */