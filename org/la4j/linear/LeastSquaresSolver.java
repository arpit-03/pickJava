/*    */ package org.la4j.linear;
/*    */ 
/*    */ import org.la4j.LinearAlgebra;
/*    */ import org.la4j.Matrix;
/*    */ import org.la4j.Vector;
/*    */ import org.la4j.Vectors;
/*    */ import org.la4j.decomposition.MatrixDecompositor;
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
/*    */ public class LeastSquaresSolver
/*    */   extends AbstractSolver
/*    */   implements LinearSystemSolver
/*    */ {
/*    */   private static final long serialVersionUID = 4071505L;
/*    */   private final Matrix qr;
/*    */   private final Matrix r;
/*    */   
/*    */   public LeastSquaresSolver(Matrix a) {
/* 39 */     super(a);
/*    */ 
/*    */     
/* 42 */     MatrixDecompositor decompositor = a.withDecompositor(LinearAlgebra.RAW_QR);
/* 43 */     Matrix[] qrr = decompositor.decompose();
/*    */ 
/*    */     
/* 46 */     this.qr = qrr[0];
/* 47 */     this.r = qrr[1];
/*    */   }
/*    */ 
/*    */   
/*    */   public Vector solve(Vector b) {
/* 52 */     ensureRHSIsCorrect(b);
/*    */     
/* 54 */     int n = unknowns();
/* 55 */     int m = equations();
/*    */ 
/*    */     
/* 58 */     for (int i = 0; i < this.r.rows(); i++) {
/* 59 */       if (this.r.get(i, i) == 0.0D) {
/* 60 */         fail("This system can not be solved: coefficient matrix is rank deficient.");
/*    */       }
/*    */     } 
/*    */     
/* 64 */     Vector x = b.copy();
/*    */     int j;
/* 66 */     for (j = 0; j < n; j++) {
/*    */       
/* 68 */       double acc = 0.0D;
/*    */       int k;
/* 70 */       for (k = j; k < m; k++) {
/* 71 */         acc += this.qr.get(k, j) * x.get(k);
/*    */       }
/*    */       
/* 74 */       acc = -acc / this.qr.get(j, j);
/* 75 */       for (k = j; k < m; k++) {
/* 76 */         x.updateAt(k, Vectors.asPlusFunction(acc * this.qr.get(k, j)));
/*    */       }
/*    */     } 
/*    */     
/* 80 */     for (j = n - 1; j >= 0; j--) {
/* 81 */       x.updateAt(j, Vectors.asDivFunction(this.r.get(j, j)));
/*    */       
/* 83 */       for (int k = 0; k < j; k++) {
/* 84 */         x.updateAt(k, Vectors.asMinusFunction(x.get(j) * this.qr.get(k, j)));
/*    */       }
/*    */     } 
/*    */     
/* 88 */     return x.slice(0, n);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean applicableTo(Matrix matrix) {
/* 93 */     return (matrix.rows() >= matrix.columns());
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/la4j/linear/LeastSquaresSolver.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */