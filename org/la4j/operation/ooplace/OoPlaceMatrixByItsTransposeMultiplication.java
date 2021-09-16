/*    */ package org.la4j.operation.ooplace;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import org.la4j.Matrix;
/*    */ import org.la4j.matrix.ColumnMajorSparseMatrix;
/*    */ import org.la4j.matrix.DenseMatrix;
/*    */ import org.la4j.matrix.RowMajorSparseMatrix;
/*    */ import org.la4j.operation.MatrixOperation;
/*    */ 
/*    */ 
/*    */ public class OoPlaceMatrixByItsTransposeMultiplication
/*    */   extends MatrixOperation<Matrix>
/*    */ {
/*    */   public Matrix apply(DenseMatrix a) {
/* 17 */     Matrix result = a.blankOfShape(a.rows(), a.rows());
/*    */     
/* 19 */     for (int i = 0; i < a.rows(); i++) {
/* 20 */       for (int j = 0; j < a.rows(); j++) {
/* 21 */         double acc = 0.0D;
/* 22 */         for (int k = 0; k < a.columns(); k++) {
/* 23 */           acc += a.get(i, k) * a.get(j, k);
/*    */         }
/* 25 */         result.set(i, j, acc);
/*    */       } 
/*    */     } 
/*    */     
/* 29 */     return result;
/*    */   }
/*    */ 
/*    */   
/*    */   public Matrix apply(RowMajorSparseMatrix a) {
/* 34 */     Matrix result = a.blankOfShape(a.rows(), a.rows());
/* 35 */     List<Integer> nzRows = new ArrayList<>();
/* 36 */     Iterator<Integer> it = a.iteratorOfNonZeroRows();
/*    */     
/* 38 */     while (it.hasNext()) {
/* 39 */       nzRows.add(it.next());
/*    */     }
/*    */     
/* 42 */     for (Iterator<Integer> i$ = nzRows.iterator(); i$.hasNext(); ) { int i = ((Integer)i$.next()).intValue();
/* 43 */       for (Iterator<Integer> iterator = nzRows.iterator(); iterator.hasNext(); ) { int j = ((Integer)iterator.next()).intValue();
/* 44 */         result.set(i, j, a.nonZeroIteratorOfRow(i).innerProduct(a.nonZeroIteratorOfRow(j))); }
/*    */        }
/*    */ 
/*    */ 
/*    */     
/* 49 */     return result;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Matrix apply(ColumnMajorSparseMatrix a) {
/* 55 */     return apply(a.toRowMajorSparseMatrix());
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/la4j/operation/ooplace/OoPlaceMatrixByItsTransposeMultiplication.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */