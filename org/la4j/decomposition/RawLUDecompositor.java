/*    */ package org.la4j.decomposition;
/*    */ 
/*    */ import org.la4j.Matrices;
/*    */ import org.la4j.Matrix;
/*    */ import org.la4j.matrix.SparseMatrix;
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
/*    */ public class RawLUDecompositor
/*    */   extends AbstractDecompositor
/*    */   implements MatrixDecompositor
/*    */ {
/*    */   public RawLUDecompositor(Matrix matrix) {
/* 31 */     super(matrix);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Matrix[] decompose() {
/* 37 */     Matrix lu = this.matrix.copy();
/* 38 */     SparseMatrix sparseMatrix = SparseMatrix.identity(lu.rows());
/*    */     
/* 40 */     for (int j = 0; j < lu.columns(); j++) {
/* 41 */       for (int i = 0; i < lu.rows(); i++) {
/*    */         
/* 43 */         int kmax = Math.min(i, j);
/*    */         
/* 45 */         double s = 0.0D;
/* 46 */         for (int m = 0; m < kmax; m++) {
/* 47 */           s += lu.get(i, m) * lu.get(m, j);
/*    */         }
/*    */         
/* 50 */         lu.updateAt(i, j, Matrices.asMinusFunction(s));
/*    */       } 
/*    */       
/* 53 */       int pivot = j;
/*    */       int k;
/* 55 */       for (k = j + 1; k < lu.rows(); k++) {
/* 56 */         if (Math.abs(lu.get(k, j)) > Math.abs(lu.get(pivot, j))) {
/* 57 */           pivot = k;
/*    */         }
/*    */       } 
/*    */       
/* 61 */       if (pivot != j) {
/* 62 */         lu.swapRows(pivot, j);
/* 63 */         sparseMatrix.swapRows(pivot, j);
/*    */       } 
/*    */       
/* 66 */       if (j < lu.rows() && Math.abs(lu.get(j, j)) > Matrices.EPS) {
/* 67 */         for (k = j + 1; k < lu.rows(); k++) {
/* 68 */           lu.updateAt(k, j, Matrices.asDivFunction(lu.get(j, j)));
/*    */         }
/*    */       }
/*    */     } 
/*    */     
/* 73 */     return new Matrix[] { lu, (Matrix)sparseMatrix };
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean applicableTo(Matrix matrix) {
/* 78 */     return (matrix.rows() == matrix.columns());
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/la4j/decomposition/RawLUDecompositor.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */