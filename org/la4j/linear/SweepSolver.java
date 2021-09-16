/*    */ package org.la4j.linear;
/*    */ 
/*    */ import org.la4j.Matrices;
/*    */ import org.la4j.Matrix;
/*    */ import org.la4j.Vector;
/*    */ import org.la4j.Vectors;
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
/*    */ public class SweepSolver
/*    */   extends AbstractSolver
/*    */   implements LinearSystemSolver
/*    */ {
/*    */   private static final long serialVersionUID = 4071505L;
/*    */   
/*    */   public SweepSolver(Matrix a) {
/* 39 */     super(a);
/*    */   }
/*    */ 
/*    */   
/*    */   public Vector solve(Vector b) {
/* 44 */     ensureRHSIsCorrect(b);
/*    */ 
/*    */     
/* 47 */     Matrix aa = this.a.copy();
/* 48 */     Vector bb = b.copy();
/*    */     
/* 50 */     Vector x = b.blankOfLength(aa.columns());
/*    */     int i;
/* 52 */     for (i = 0; i < aa.rows() - 1; i++) {
/*    */       
/* 54 */       double maxItem = Math.abs(aa.get(i, i));
/* 55 */       int maxIndex = i;
/*    */       int j;
/* 57 */       for (j = i + 1; j < aa.columns(); j++) {
/* 58 */         double value = Math.abs(aa.get(j, i));
/* 59 */         if (value > maxItem) {
/* 60 */           maxItem = value;
/* 61 */           maxIndex = j;
/*    */         } 
/*    */       } 
/*    */       
/* 65 */       if (maxIndex != i) {
/* 66 */         aa.swapRows(maxIndex, i);
/* 67 */         bb.swapElements(i, maxIndex);
/*    */       } 
/*    */       
/* 70 */       for (j = i + 1; j < aa.columns(); j++) {
/*    */         
/* 72 */         double c = aa.get(j, i) / aa.get(i, i);
/* 73 */         for (int k = i; k < aa.columns(); k++) {
/* 74 */           aa.updateAt(j, k, Matrices.asMinusFunction(aa.get(i, k) * c));
/*    */         }
/*    */         
/* 77 */         bb.updateAt(j, Vectors.asMinusFunction(bb.get(i) * c));
/*    */       } 
/*    */     } 
/*    */     
/* 81 */     for (i = aa.rows() - 1; i >= 0; i--) {
/*    */       
/* 83 */       double acc = 0.0D;
/*    */       
/* 85 */       for (int j = i + 1; j < aa.columns(); j++) {
/* 86 */         acc += aa.get(i, j) * x.get(j);
/*    */       }
/*    */       
/* 89 */       x.set(i, (bb.get(i) - acc) / aa.get(i, i));
/*    */     } 
/*    */     
/* 92 */     return x;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean applicableTo(Matrix matrix) {
/* 97 */     return matrix.is(Matrices.TRIDIAGONAL_MATRIX);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/la4j/linear/SweepSolver.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */