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
/*    */ public class QRDecompositor
/*    */   extends RawQRDecompositor
/*    */   implements MatrixDecompositor
/*    */ {
/*    */   public QRDecompositor(Matrix matrix) {
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
/*    */   public Matrix[] decompose() {
/* 51 */     Matrix[] qrr = super.decompose();
/* 52 */     Matrix qr = qrr[0];
/* 53 */     Matrix r = qrr[1];
/*    */     
/* 55 */     Matrix q = qr.blank();
/*    */     
/* 57 */     for (int k = q.columns() - 1; k >= 0; k--) {
/*    */       
/* 59 */       q.set(k, k, 1.0D);
/*    */       
/* 61 */       for (int j = k; j < q.columns(); j++) {
/*    */         
/* 63 */         if (Math.abs(qr.get(k, k)) > Matrices.EPS) {
/*    */           
/* 65 */           double acc = 0.0D;
/*    */           int m;
/* 67 */           for (m = k; m < q.rows(); m++) {
/* 68 */             acc += qr.get(m, k) * q.get(m, j);
/*    */           }
/*    */           
/* 71 */           acc = -acc / qr.get(k, k);
/*    */           
/* 73 */           for (m = k; m < q.rows(); m++) {
/* 74 */             q.updateAt(m, j, Matrices.asPlusFunction(acc * qr.get(m, k)));
/*    */           }
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 80 */     for (int i = 0; i < r.rows(); i++) {
/* 81 */       for (int j = i + 1; j < r.columns(); j++) {
/* 82 */         r.set(i, j, qr.get(i, j));
/*    */       }
/*    */     } 
/*    */     
/* 86 */     return new Matrix[] { q, r };
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/la4j/decomposition/QRDecompositor.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */