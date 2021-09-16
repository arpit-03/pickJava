/*    */ package org.la4j.linear;
/*    */ 
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
/*    */ public abstract class AbstractSolver
/*    */   implements LinearSystemSolver
/*    */ {
/*    */   protected Matrix a;
/*    */   protected int unknowns;
/*    */   protected int equations;
/*    */   
/*    */   protected AbstractSolver(Matrix a) {
/* 35 */     if (!applicableTo(a)) {
/* 36 */       fail("Given coefficient matrix can not be used with this solver.");
/*    */     }
/*    */     
/* 39 */     this.a = a;
/* 40 */     this.unknowns = a.columns();
/* 41 */     this.equations = a.rows();
/*    */   }
/*    */ 
/*    */   
/*    */   public Matrix self() {
/* 46 */     return this.a;
/*    */   }
/*    */ 
/*    */   
/*    */   public int unknowns() {
/* 51 */     return this.unknowns;
/*    */   }
/*    */ 
/*    */   
/*    */   public int equations() {
/* 56 */     return this.equations;
/*    */   }
/*    */   
/*    */   protected void ensureRHSIsCorrect(Vector vector) {
/* 60 */     if (vector.length() != this.equations) {
/* 61 */       fail("Wrong length of RHS vector: " + vector.length() + ".");
/*    */     }
/*    */   }
/*    */   
/*    */   protected void fail(String message) {
/* 66 */     throw new IllegalArgumentException(message);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/la4j/linear/AbstractSolver.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */