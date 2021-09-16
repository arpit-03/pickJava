/*    */ package org.la4j.operation;
/*    */ 
/*    */ import org.la4j.vector.DenseVector;
/*    */ import org.la4j.vector.SparseVector;
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
/*    */ public abstract class SymmetricVectorVectorOperation<R>
/*    */   extends VectorVectorOperation<R>
/*    */ {
/*    */   public R apply(SparseVector a, DenseVector b) {
/* 31 */     return applySymmetric(b, a);
/*    */   }
/*    */ 
/*    */   
/*    */   public R apply(DenseVector a, SparseVector b) {
/* 36 */     return applySymmetric(a, b);
/*    */   }
/*    */   
/*    */   public abstract R applySymmetric(DenseVector paramDenseVector, SparseVector paramSparseVector);
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/la4j/operation/SymmetricVectorVectorOperation.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */