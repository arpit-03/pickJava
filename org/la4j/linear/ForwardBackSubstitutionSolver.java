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
/*    */ public class ForwardBackSubstitutionSolver
/*    */   extends AbstractSolver
/*    */   implements LinearSystemSolver
/*    */ {
/*    */   private static final long serialVersionUID = 4071505L;
/*    */   private final Matrix lu;
/*    */   private final Matrix p;
/*    */   
/*    */   public ForwardBackSubstitutionSolver(Matrix a) {
/* 39 */     super(a);
/*    */ 
/*    */     
/* 42 */     MatrixDecompositor decompositor = a.withDecompositor(LinearAlgebra.RAW_LU);
/* 43 */     Matrix[] lup = decompositor.decompose();
/*    */ 
/*    */     
/* 46 */     this.lu = lup[0];
/* 47 */     this.p = lup[1];
/*    */   }
/*    */ 
/*    */   
/*    */   public Vector solve(Vector b) {
/* 52 */     ensureRHSIsCorrect(b);
/*    */     
/* 54 */     int n = unknowns();
/*    */ 
/*    */     
/* 57 */     for (int i = 0; i < n; i++) {
/* 58 */       if (this.lu.get(i, i) == 0.0D) {
/* 59 */         fail("This system can not be solved: coefficient matrix is singular.");
/*    */       }
/*    */     } 
/*    */     
/* 63 */     Vector x = b.blankOfLength(n);
/*    */     
/* 65 */     for (int k = 0; k < n; k++) {
/* 66 */       for (int m = 0; m < n; m++) {
/* 67 */         if (this.p.get(k, m) != 0.0D) {
/* 68 */           x.set(k, b.get(m));
/*    */           break;
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     int j;
/* 74 */     for (j = 0; j < n; j++) {
/* 75 */       for (int m = j + 1; m < n; m++) {
/* 76 */         x.updateAt(m, Vectors.asMinusFunction(x.get(j) * this.lu.get(m, j)));
/*    */       }
/*    */     } 
/*    */     
/* 80 */     for (j = n - 1; j >= 0; j--) {
/* 81 */       x.updateAt(j, Vectors.asDivFunction(this.lu.get(j, j)));
/*    */       
/* 83 */       for (int m = 0; m < j; m++) {
/* 84 */         x.updateAt(m, Vectors.asMinusFunction(x.get(j) * this.lu.get(m, j)));
/*    */       }
/*    */     } 
/*    */     
/* 88 */     return x;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean applicableTo(Matrix matrix) {
/* 93 */     return (matrix.rows() == matrix.columns());
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/la4j/linear/ForwardBackSubstitutionSolver.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */