/*    */ package org.la4j.operation.ooplace;
/*    */ 
/*    */ import org.la4j.Matrix;
/*    */ import org.la4j.operation.CommonMatrixMatrixOperation;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class OoPlaceKroneckerProduct
/*    */   extends CommonMatrixMatrixOperation<Matrix>
/*    */ {
/*    */   public Matrix applyCommon(Matrix a, Matrix b) {
/* 12 */     int n = a.rows() * b.rows();
/* 13 */     int m = a.columns() * b.columns();
/* 14 */     int p = b.rows();
/* 15 */     int q = b.columns();
/*    */     
/* 17 */     Matrix result = a.blankOfShape(n, m);
/*    */     
/* 19 */     for (int i = 0; i < n; i++) {
/* 20 */       for (int j = 0; j < m; j++) {
/* 21 */         result.set(i, j, a.get(i / p, j / q) * b.get(i % p, j % q));
/*    */       }
/*    */     } 
/*    */     
/* 25 */     return result;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/la4j/operation/ooplace/OoPlaceKroneckerProduct.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */