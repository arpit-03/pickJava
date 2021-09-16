/*     */ package org.la4j.operation.ooplace;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import org.la4j.Matrix;
/*     */ import org.la4j.Vector;
/*     */ import org.la4j.Vectors;
/*     */ import org.la4j.iterator.RowMajorMatrixIterator;
/*     */ import org.la4j.iterator.VectorIterator;
/*     */ import org.la4j.matrix.ColumnMajorSparseMatrix;
/*     */ import org.la4j.matrix.DenseMatrix;
/*     */ import org.la4j.matrix.RowMajorSparseMatrix;
/*     */ import org.la4j.operation.VectorMatrixOperation;
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
/*     */ 
/*     */ public class OoPlaceVectorByMatrixMultiplication
/*     */   extends VectorMatrixOperation<Vector>
/*     */ {
/*     */   public Vector apply(SparseVector a, DenseMatrix b) {
/*  41 */     DenseVector denseVector = DenseVector.zero(b.columns());
/*     */     
/*  43 */     for (int j = 0; j < b.columns(); j++) {
/*  44 */       double acc = 0.0D;
/*  45 */       VectorIterator it = a.nonZeroIterator();
/*     */       
/*  47 */       while (it.hasNext()) {
/*  48 */         double x = ((Double)it.next()).doubleValue();
/*  49 */         int i = it.index();
/*  50 */         acc += x * b.get(i, j);
/*     */       } 
/*  52 */       denseVector.set(j, acc);
/*     */     } 
/*     */     
/*  55 */     return (Vector)denseVector;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector apply(SparseVector a, RowMajorSparseMatrix b) {
/*  61 */     Vector result = a.blankOfLength(b.columns());
/*  62 */     VectorIterator these = a.nonZeroIterator();
/*     */     
/*  64 */     while (these.hasNext()) {
/*  65 */       double x = ((Double)these.next()).doubleValue();
/*  66 */       int i = these.index();
/*  67 */       VectorIterator those = b.iteratorOfRow(i);
/*     */       
/*  69 */       while (those.hasNext()) {
/*  70 */         double y = ((Double)those.next()).doubleValue();
/*  71 */         int j = those.index();
/*  72 */         result.updateAt(j, Vectors.asPlusFunction(x * y));
/*     */       } 
/*     */     } 
/*     */     
/*  76 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public Vector apply(SparseVector a, ColumnMajorSparseMatrix b) {
/*  81 */     Vector result = a.blankOfLength(b.columns());
/*  82 */     Iterator<Integer> columns = b.iteratorOrNonZeroColumns();
/*     */     
/*  84 */     while (columns.hasNext()) {
/*  85 */       int j = ((Integer)columns.next()).intValue();
/*  86 */       VectorIterator these = a.nonZeroIterator();
/*  87 */       VectorIterator those = b.nonZeroIteratorOfColumn(j);
/*  88 */       result.set(j, these.innerProduct(those));
/*     */     } 
/*     */     
/*  91 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public Vector apply(DenseVector a, DenseMatrix b) {
/*  96 */     Vector result = a.blankOfLength(b.columns());
/*     */     
/*  98 */     for (int j = 0; j < b.columns(); j++) {
/*  99 */       double acc = 0.0D;
/*     */       
/* 101 */       for (int i = 0; i < b.rows(); i++) {
/* 102 */         acc += a.get(i) * b.get(i, j);
/*     */       }
/* 104 */       result.set(j, acc);
/*     */     } 
/*     */     
/* 107 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector apply(DenseVector a, RowMajorSparseMatrix b) {
/* 113 */     SparseVector sparseVector = SparseVector.zero(b.columns());
/* 114 */     RowMajorMatrixIterator rowMajorMatrixIterator = b.rowMajorIterator();
/*     */     
/* 116 */     while (rowMajorMatrixIterator.hasNext()) {
/* 117 */       double x = ((Double)rowMajorMatrixIterator.next()).doubleValue();
/* 118 */       int i = rowMajorMatrixIterator.rowIndex();
/* 119 */       int j = rowMajorMatrixIterator.columnIndex();
/* 120 */       sparseVector.updateAt(j, Vectors.asPlusFunction(x * a.get(i)));
/*     */     } 
/*     */     
/* 123 */     return (Vector)sparseVector;
/*     */   }
/*     */ 
/*     */   
/*     */   public Vector apply(DenseVector a, ColumnMajorSparseMatrix b) {
/* 128 */     SparseVector sparseVector = SparseVector.zero(b.columns());
/* 129 */     Iterator<Integer> columns = b.iteratorOrNonZeroColumns();
/*     */     
/* 131 */     while (columns.hasNext()) {
/* 132 */       int j = ((Integer)columns.next()).intValue();
/* 133 */       VectorIterator it = b.nonZeroIteratorOfColumn(j);
/* 134 */       double acc = 0.0D;
/*     */       
/* 136 */       while (it.hasNext()) {
/* 137 */         double x = ((Double)it.next()).doubleValue();
/* 138 */         int i = it.index();
/* 139 */         acc += x * a.get(i);
/*     */       } 
/*     */       
/* 142 */       sparseVector.set(j, acc);
/*     */     } 
/*     */ 
/*     */     
/* 146 */     return (Vector)sparseVector;
/*     */   }
/*     */ 
/*     */   
/*     */   public void ensureApplicableTo(Vector a, Matrix b) {
/* 151 */     if (a.length() != b.rows())
/* 152 */       throw new IllegalArgumentException("Given vector should have the same length as number of rows in the given matrix: " + a.length() + " does not equal to " + b.rows() + "."); 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/la4j/operation/ooplace/OoPlaceVectorByMatrixMultiplication.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */