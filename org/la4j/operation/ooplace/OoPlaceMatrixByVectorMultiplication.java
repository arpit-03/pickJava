/*     */ package org.la4j.operation.ooplace;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import org.la4j.Matrix;
/*     */ import org.la4j.Vector;
/*     */ import org.la4j.Vectors;
/*     */ import org.la4j.iterator.MatrixIterator;
/*     */ import org.la4j.iterator.VectorIterator;
/*     */ import org.la4j.matrix.ColumnMajorSparseMatrix;
/*     */ import org.la4j.matrix.DenseMatrix;
/*     */ import org.la4j.matrix.RowMajorSparseMatrix;
/*     */ import org.la4j.operation.MatrixVectorOperation;
/*     */ import org.la4j.vector.DenseVector;
/*     */ import org.la4j.vector.SparseVector;
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
/*     */ public class OoPlaceMatrixByVectorMultiplication
/*     */   extends MatrixVectorOperation<Vector>
/*     */ {
/*     */   public Vector apply(DenseMatrix a, DenseVector b) {
/*  40 */     Vector result = b.blankOfLength(a.rows());
/*     */     
/*  42 */     for (int i = 0; i < a.rows(); i++) {
/*  43 */       double acc = 0.0D;
/*  44 */       for (int j = 0; j < a.columns(); j++) {
/*  45 */         acc += a.get(i, j) * b.get(j);
/*     */       }
/*  47 */       result.set(i, acc);
/*     */     } 
/*     */     
/*  50 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public Vector apply(DenseMatrix a, SparseVector b) {
/*  55 */     DenseVector denseVector = DenseVector.zero(a.rows());
/*     */     
/*  57 */     for (int i = 0; i < a.rows(); i++) {
/*  58 */       double acc = 0.0D;
/*  59 */       VectorIterator it = b.nonZeroIterator();
/*     */       
/*  61 */       while (it.hasNext()) {
/*  62 */         double x = ((Double)it.next()).doubleValue();
/*  63 */         int j = it.index();
/*  64 */         acc += a.get(i, j) * x;
/*     */       } 
/*     */       
/*  67 */       denseVector.set(i, acc);
/*     */     } 
/*     */     
/*  70 */     return (Vector)denseVector;
/*     */   }
/*     */ 
/*     */   
/*     */   public Vector apply(RowMajorSparseMatrix a, DenseVector b) {
/*  75 */     DenseVector denseVector = DenseVector.zero(a.rows());
/*  76 */     MatrixIterator it = a.nonZeroIterator();
/*     */     
/*  78 */     while (it.hasNext()) {
/*  79 */       double x = ((Double)it.next()).doubleValue();
/*  80 */       int i = it.rowIndex();
/*  81 */       int j = it.columnIndex();
/*  82 */       denseVector.set(i, denseVector.get(i) + x * b.get(j));
/*     */     } 
/*     */     
/*  85 */     return (Vector)denseVector;
/*     */   }
/*     */ 
/*     */   
/*     */   public Vector apply(RowMajorSparseMatrix a, SparseVector b) {
/*  90 */     Vector result = b.blankOfLength(a.rows());
/*  91 */     Iterator<Integer> it = a.iteratorOfNonZeroRows();
/*     */     
/*  93 */     while (it.hasNext()) {
/*  94 */       int i = ((Integer)it.next()).intValue();
/*  95 */       VectorIterator these = a.nonZeroIteratorOfRow(i);
/*  96 */       VectorIterator those = b.nonZeroIterator();
/*  97 */       result.set(i, these.innerProduct(those));
/*     */     } 
/*     */     
/* 100 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public Vector apply(ColumnMajorSparseMatrix a, DenseVector b) {
/* 105 */     DenseVector denseVector = DenseVector.zero(a.rows());
/* 106 */     MatrixIterator it = a.nonZeroIterator();
/*     */     
/* 108 */     while (it.hasNext()) {
/* 109 */       double x = ((Double)it.next()).doubleValue();
/* 110 */       int i = it.rowIndex();
/* 111 */       int j = it.columnIndex();
/* 112 */       denseVector.set(i, denseVector.get(i) + x * b.get(j));
/*     */     } 
/*     */     
/* 115 */     return (Vector)denseVector;
/*     */   }
/*     */ 
/*     */   
/*     */   public Vector apply(ColumnMajorSparseMatrix a, SparseVector b) {
/* 120 */     Vector result = b.blankOfLength(a.rows());
/* 121 */     VectorIterator it = b.nonZeroIterator();
/*     */     
/* 123 */     while (it.hasNext()) {
/* 124 */       double x = ((Double)it.next()).doubleValue();
/* 125 */       int j = it.index();
/* 126 */       VectorIterator these = a.nonZeroIteratorOfColumn(j);
/*     */       
/* 128 */       while (these.hasNext()) {
/* 129 */         double y = ((Double)these.next()).doubleValue();
/* 130 */         int i = these.index();
/* 131 */         result.updateAt(i, Vectors.asPlusFunction(x * y));
/*     */       } 
/*     */     } 
/*     */     
/* 135 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public void ensureApplicableTo(Matrix a, Vector b) {
/* 140 */     if (a.columns() != b.length())
/* 141 */       throw new IllegalArgumentException("Given vector should have the same length as number of columns in the given matrix: " + b.length() + " does not equal to " + a.columns() + "."); 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/la4j/operation/ooplace/OoPlaceMatrixByVectorMultiplication.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */