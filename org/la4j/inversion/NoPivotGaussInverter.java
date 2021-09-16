/*    */ package org.la4j.inversion;
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
/*    */ public class NoPivotGaussInverter
/*    */   implements MatrixInverter
/*    */ {
/*    */   private final Matrix matrix;
/*    */   
/*    */   public NoPivotGaussInverter(Matrix matrix) {
/* 31 */     this.matrix = matrix;
/*    */   }
/*    */ 
/*    */   
/*    */   public Matrix inverse() {
/* 36 */     if (this.matrix.rows() != this.matrix.columns()) {
/* 37 */       throw new IllegalArgumentException("Wrong matrix size: rows != columns");
/*    */     }
/*    */ 
/*    */ 
/*    */     
/* 42 */     Matrix result = this.matrix.copy();
/*    */     
/* 44 */     for (int k = 0; k < this.matrix.rows(); k++) {
/* 45 */       double diagonalTerm = result.get(k, k);
/*    */       
/* 47 */       if (Math.abs(diagonalTerm) <= Double.MIN_VALUE) {
/* 48 */         throw new IllegalArgumentException("This matrix cannot be inverted with a non-pivoting Gauss elimination method.");
/*    */       }
/*    */ 
/*    */       
/* 52 */       double var = 1.0D / result.get(k, k);
/* 53 */       result.set(k, k, 1.0D);
/*    */       
/* 55 */       for (int j = 0; j < this.matrix.rows(); j++) {
/* 56 */         result.set(k, j, result.get(k, j) * var);
/*    */       }
/*    */       
/* 59 */       for (int i = 0; i < this.matrix.rows(); i++) {
/* 60 */         if (i != k) {
/*    */ 
/*    */ 
/*    */           
/* 64 */           var = result.get(i, k);
/* 65 */           result.set(i, k, 0.0D);
/* 66 */           for (int m = 0; m < this.matrix.rows(); m++) {
/* 67 */             result.set(i, m, result.get(i, m) - var * result.get(k, m));
/*    */           }
/*    */         } 
/*    */       } 
/*    */     } 
/* 72 */     return result;
/*    */   }
/*    */ 
/*    */   
/*    */   public Matrix self() {
/* 77 */     return this.matrix;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/la4j/inversion/NoPivotGaussInverter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */