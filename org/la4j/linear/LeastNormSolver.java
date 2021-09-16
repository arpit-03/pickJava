/*    */ package org.la4j.linear;
/*    */ 
/*    */ import org.la4j.LinearAlgebra;
/*    */ import org.la4j.Matrix;
/*    */ import org.la4j.Vector;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LeastNormSolver
/*    */   extends AbstractSolver
/*    */   implements LinearSystemSolver
/*    */ {
/*    */   protected LeastNormSolver(Matrix a) {
/* 40 */     super(a);
/*    */   }
/*    */ 
/*    */   
/*    */   public Vector solve(Vector b) {
/* 45 */     ensureRHSIsCorrect(b);
/*    */     
/* 47 */     Matrix temp = self().multiply(self().rotate());
/* 48 */     Matrix pseudoInverse = self().rotate().multiply(temp.withInverter(LinearAlgebra.InverterFactory.GAUSS_JORDAN).inverse());
/*    */     
/* 50 */     return pseudoInverse.multiply(b);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean applicableTo(Matrix matrix) {
/* 57 */     int r = matrix.rank();
/* 58 */     return (r == matrix.rows() || r == matrix.columns());
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/la4j/linear/LeastNormSolver.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */