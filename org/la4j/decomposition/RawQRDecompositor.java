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
/*    */ public class RawQRDecompositor
/*    */   extends AbstractDecompositor
/*    */   implements MatrixDecompositor
/*    */ {
/*    */   public RawQRDecompositor(Matrix matrix) {
/* 30 */     super(matrix);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Matrix[] decompose() {
/* 36 */     Matrix qr = this.matrix.copy();
/* 37 */     Matrix r = this.matrix.blankOfShape(qr.columns(), qr.columns());
/*    */     
/* 39 */     for (int k = 0; k < qr.columns(); k++) {
/*    */       
/* 41 */       double norm = 0.0D;
/*    */       int i;
/* 43 */       for (i = k; i < qr.rows(); i++) {
/* 44 */         norm = Math.hypot(norm, qr.get(i, k));
/*    */       }
/*    */       
/* 47 */       if (Math.abs(norm) > Matrices.EPS) {
/*    */         
/* 49 */         if (qr.get(k, k) < 0.0D) {
/* 50 */           norm = -norm;
/*    */         }
/*    */         
/* 53 */         for (i = k; i < qr.rows(); i++) {
/* 54 */           qr.updateAt(i, k, Matrices.asDivFunction(norm));
/*    */         }
/*    */         
/* 57 */         qr.updateAt(k, k, Matrices.INC_FUNCTION);
/*    */         
/* 59 */         for (int j = k + 1; j < qr.columns(); j++) {
/*    */           
/* 61 */           double acc = 0.0D;
/*    */           int m;
/* 63 */           for (m = k; m < qr.rows(); m++) {
/* 64 */             acc += qr.get(m, k) * qr.get(m, j);
/*    */           }
/*    */           
/* 67 */           acc = -acc / qr.get(k, k);
/*    */           
/* 69 */           for (m = k; m < qr.rows(); m++) {
/* 70 */             qr.updateAt(m, j, Matrices.asPlusFunction(acc * qr.get(m, k)));
/*    */           }
/*    */         } 
/*    */       } 
/*    */       
/* 75 */       r.set(k, k, -norm);
/*    */     } 
/*    */     
/* 78 */     return new Matrix[] { qr, r };
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean applicableTo(Matrix matrix) {
/* 83 */     return (matrix.rows() >= matrix.columns());
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/la4j/decomposition/RawQRDecompositor.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */