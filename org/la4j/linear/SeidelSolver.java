/*    */ package org.la4j.linear;
/*    */ 
/*    */ import org.la4j.Matrices;
/*    */ import org.la4j.Matrix;
/*    */ import org.la4j.Vector;
/*    */ import org.la4j.matrix.functor.MatrixFunction;
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
/*    */ public class SeidelSolver
/*    */   extends AbstractSolver
/*    */   implements LinearSystemSolver
/*    */ {
/*    */   private static final long serialVersionUID = 4071505L;
/*    */   private final Matrix aa;
/*    */   
/*    */   public SeidelSolver(Matrix a) {
/* 41 */     super(a);
/*    */ 
/*    */     
/* 44 */     this.aa = a.copy();
/*    */     
/* 46 */     for (int i = 0; i < this.aa.rows(); i++) {
/* 47 */       MatrixFunction divider = Matrices.asDivFunction(this.aa.get(i, i));
/* 48 */       for (int j = 0; j < this.aa.columns(); j++) {
/* 49 */         if (i != j) {
/* 50 */           this.aa.updateAt(i, j, divider);
/*    */         }
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public Vector solve(Vector b) {
/* 58 */     ensureRHSIsCorrect(b);
/*    */     
/* 60 */     Vector current = b.blankOfLength(unknowns());
/*    */     
/* 62 */     while (!this.a.multiply(current).equals(b)) {
/*    */       
/* 64 */       for (int i = 0; i < this.aa.rows(); i++) {
/*    */         
/* 66 */         double acc = b.get(i) / this.aa.get(i, i);
/* 67 */         for (int j = 0; j < this.aa.columns(); j++) {
/* 68 */           if (i != j) {
/* 69 */             acc -= this.aa.get(i, j) * current.get(j);
/*    */           }
/*    */         } 
/*    */         
/* 73 */         current.set(i, acc);
/*    */       } 
/*    */     } 
/*    */     
/* 77 */     return current;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean applicableTo(Matrix matrix) {
/* 82 */     return matrix.is(Matrices.DIAGONALLY_DOMINANT_MATRIX);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/la4j/linear/SeidelSolver.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */