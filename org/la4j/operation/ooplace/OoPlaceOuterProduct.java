/*    */ package org.la4j.operation.ooplace;
/*    */ 
/*    */ import org.la4j.Matrix;
/*    */ import org.la4j.iterator.VectorIterator;
/*    */ import org.la4j.matrix.ColumnMajorSparseMatrix;
/*    */ import org.la4j.matrix.DenseMatrix;
/*    */ import org.la4j.matrix.RowMajorSparseMatrix;
/*    */ import org.la4j.operation.VectorVectorOperation;
/*    */ import org.la4j.vector.DenseVector;
/*    */ import org.la4j.vector.SparseVector;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class OoPlaceOuterProduct
/*    */   extends VectorVectorOperation<Matrix>
/*    */ {
/*    */   public Matrix apply(SparseVector a, SparseVector b) {
/* 37 */     RowMajorSparseMatrix rowMajorSparseMatrix = RowMajorSparseMatrix.zero(a.length(), b.length());
/*    */     
/* 39 */     VectorIterator these = a.nonZeroIterator();
/* 40 */     while (these.hasNext()) {
/* 41 */       double x = ((Double)these.next()).doubleValue();
/* 42 */       int i = these.index();
/* 43 */       VectorIterator those = b.nonZeroIterator();
/* 44 */       while (those.hasNext()) {
/* 45 */         double y = ((Double)those.next()).doubleValue();
/* 46 */         int j = those.index();
/*    */         
/* 48 */         rowMajorSparseMatrix.set(i, j, x * y);
/*    */       } 
/*    */     } 
/*    */     
/* 52 */     return (Matrix)rowMajorSparseMatrix;
/*    */   }
/*    */ 
/*    */   
/*    */   public Matrix apply(DenseVector a, DenseVector b) {
/* 57 */     DenseMatrix denseMatrix = DenseMatrix.zero(a.length(), b.length());
/*    */     
/* 59 */     for (int i = 0; i < a.length(); i++) {
/* 60 */       for (int j = 0; j < b.length(); j++) {
/* 61 */         denseMatrix.set(i, j, a.get(i) * b.get(j));
/*    */       }
/*    */     } 
/*    */     
/* 65 */     return (Matrix)denseMatrix;
/*    */   }
/*    */ 
/*    */   
/*    */   public Matrix apply(DenseVector a, SparseVector b) {
/* 70 */     ColumnMajorSparseMatrix columnMajorSparseMatrix = ColumnMajorSparseMatrix.zero(a.length(), b.length());
/* 71 */     VectorIterator it = b.nonZeroIterator();
/*    */     
/* 73 */     while (it.hasNext()) {
/* 74 */       double x = ((Double)it.next()).doubleValue();
/* 75 */       int j = it.index();
/* 76 */       for (int i = 0; i < a.length(); i++) {
/* 77 */         columnMajorSparseMatrix.set(i, j, x * a.get(i));
/*    */       }
/*    */     } 
/*    */     
/* 81 */     return (Matrix)columnMajorSparseMatrix;
/*    */   }
/*    */ 
/*    */   
/*    */   public Matrix apply(SparseVector a, DenseVector b) {
/* 86 */     RowMajorSparseMatrix rowMajorSparseMatrix = RowMajorSparseMatrix.zero(a.length(), b.length());
/* 87 */     VectorIterator it = a.nonZeroIterator();
/*    */     
/* 89 */     while (it.hasNext()) {
/* 90 */       double x = ((Double)it.next()).doubleValue();
/* 91 */       int i = it.index();
/* 92 */       for (int j = 0; j < b.length(); j++) {
/* 93 */         rowMajorSparseMatrix.set(i, j, x * b.get(j));
/*    */       }
/*    */     } 
/*    */     
/* 97 */     return (Matrix)rowMajorSparseMatrix;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/la4j/operation/ooplace/OoPlaceOuterProduct.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */