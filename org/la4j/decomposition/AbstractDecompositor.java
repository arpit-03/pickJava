/*    */ package org.la4j.decomposition;
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
/*    */ public abstract class AbstractDecompositor
/*    */   implements MatrixDecompositor
/*    */ {
/*    */   protected Matrix matrix;
/*    */   
/*    */   public AbstractDecompositor(Matrix matrix) {
/* 31 */     if (!applicableTo(matrix)) {
/* 32 */       fail("Given matrix can not be used with this decompositor.");
/*    */     }
/*    */     
/* 35 */     this.matrix = matrix;
/*    */   }
/*    */ 
/*    */   
/*    */   public Matrix self() {
/* 40 */     return this.matrix;
/*    */   }
/*    */   
/*    */   protected void fail(String message) {
/* 44 */     throw new IllegalArgumentException(message);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/la4j/decomposition/AbstractDecompositor.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */