/*    */ package org.la4j.decomposition;
/*    */ 
/*    */ import org.la4j.Matrices;
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
/*    */ public class CholeskyDecompositor
/*    */   extends AbstractDecompositor
/*    */   implements MatrixDecompositor
/*    */ {
/*    */   public CholeskyDecompositor(Matrix matrix) {
/* 36 */     super(matrix);
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
/*    */   
/*    */   public Matrix[] decompose() {
/* 52 */     Matrix l = this.matrix.blankOfShape(this.matrix.rows(), this.matrix.rows());
/*    */     
/* 54 */     for (int j = 0; j < l.rows(); j++) {
/*    */       
/* 56 */       double d = 0.0D;
/*    */       int k;
/* 58 */       for (k = 0; k < j; k++) {
/*    */         
/* 60 */         double s = 0.0D;
/*    */         
/* 62 */         for (int i = 0; i < k; i++) {
/* 63 */           s += l.get(k, i) * l.get(j, i);
/*    */         }
/*    */         
/* 66 */         s = (this.matrix.get(j, k) - s) / l.get(k, k);
/*    */         
/* 68 */         l.set(j, k, s);
/*    */         
/* 70 */         d += s * s;
/*    */       } 
/*    */       
/* 73 */       d = this.matrix.get(j, j) - d;
/*    */       
/* 75 */       l.set(j, j, Math.sqrt(Math.max(d, 0.0D)));
/*    */       
/* 77 */       for (k = j + 1; k < l.rows(); k++) {
/* 78 */         l.set(j, k, 0.0D);
/*    */       }
/*    */     } 
/*    */     
/* 82 */     return new Matrix[] { l };
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean applicableTo(Matrix matrix) {
/* 87 */     return (matrix.rows() == matrix.columns() && matrix.is(Matrices.SYMMETRIC_MATRIX) && matrix.is(Matrices.POSITIVE_DEFINITE_MATRIX));
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/la4j/decomposition/CholeskyDecompositor.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */