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
/*    */ 
/*    */ public class JacobiSolver
/*    */   extends AbstractSolver
/*    */   implements LinearSystemSolver
/*    */ {
/*    */   private static final long serialVersionUID = 4071505L;
/*    */   private final Matrix aa;
/*    */   
/*    */   public JacobiSolver(Matrix a) {
/* 42 */     super(a);
/*    */ 
/*    */     
/* 45 */     this.aa = a.copy();
/*    */     
/* 47 */     for (int i = 0; i < this.aa.rows(); i++) {
/* 48 */       MatrixFunction divider = Matrices.asDivFunction(this.aa.get(i, i));
/* 49 */       for (int j = 0; j < this.aa.columns(); j++) {
/* 50 */         if (i != j) {
/* 51 */           this.aa.updateAt(i, j, divider);
/*    */         }
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public Vector solve(Vector b) {
/* 59 */     ensureRHSIsCorrect(b);
/*    */     
/* 61 */     Vector current = b.blankOfLength(unknowns());
/*    */ 
/*    */     
/* 64 */     while (!this.a.multiply(current).equals(b)) {
/*    */       
/* 66 */       Vector next = current.blank();
/*    */       
/* 68 */       for (int i = 0; i < this.aa.rows(); i++) {
/*    */         
/* 70 */         double acc = b.get(i) / this.aa.get(i, i);
/* 71 */         for (int j = 0; j < this.aa.columns(); j++) {
/* 72 */           if (i != j) {
/* 73 */             acc -= this.aa.get(i, j) * current.get(j);
/*    */           }
/*    */         } 
/*    */         
/* 77 */         next.set(i, acc);
/*    */       } 
/*    */       
/* 80 */       current = next;
/*    */     } 
/*    */     
/* 83 */     return current;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean applicableTo(Matrix matrix) {
/* 88 */     return matrix.is(Matrices.DIAGONALLY_DOMINANT_MATRIX);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/la4j/linear/JacobiSolver.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */