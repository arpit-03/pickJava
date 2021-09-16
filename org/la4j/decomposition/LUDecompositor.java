/*    */ package org.la4j.decomposition;
/*    */ 
/*    */ import org.la4j.Matrix;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LUDecompositor
/*    */   extends RawLUDecompositor
/*    */   implements MatrixDecompositor
/*    */ {
/*    */   public LUDecompositor(Matrix matrix) {
/* 35 */     super(matrix);
/*    */   }
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
/*    */   public Matrix[] decompose() {
/* 50 */     Matrix[] lup = super.decompose();
/* 51 */     Matrix lu = lup[0];
/* 52 */     Matrix p = lup[1];
/*    */     
/* 54 */     Matrix l = this.matrix.blankOfShape(lu.rows(), lu.columns());
/*    */     
/* 56 */     for (int i = 0; i < l.rows(); i++) {
/* 57 */       for (int k = 0; k <= i; k++) {
/* 58 */         if (i > k) {
/* 59 */           l.set(i, k, lu.get(i, k));
/*    */         } else {
/* 61 */           l.set(i, k, 1.0D);
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 66 */     Matrix u = this.matrix.blankOfShape(lu.columns(), lu.columns());
/*    */     
/* 68 */     for (int j = 0; j < u.rows(); j++) {
/* 69 */       for (int k = j; k < u.columns(); k++) {
/* 70 */         u.set(j, k, lu.get(j, k));
/*    */       }
/*    */     } 
/*    */     
/* 74 */     return new Matrix[] { l, u, p };
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/la4j/decomposition/LUDecompositor.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */