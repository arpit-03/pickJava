/*    */ package org.la4j.operation;
/*    */ 
/*    */ import org.la4j.Vector;
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
/*    */ public abstract class CommonVectorVectorOperation<R>
/*    */   extends VectorVectorOperation<R>
/*    */ {
/*    */   public R apply(SparseVector a, SparseVector b) {
/* 31 */     return applyCommon((Vector)a, (Vector)b);
/*    */   }
/*    */ 
/*    */   
/*    */   public R apply(SparseVector a, DenseVector b) {
/* 36 */     return applyCommon((Vector)a, (Vector)b);
/*    */   }
/*    */ 
/*    */   
/*    */   public R apply(DenseVector a, DenseVector b) {
/* 41 */     return applyCommon((Vector)a, (Vector)b);
/*    */   }
/*    */ 
/*    */   
/*    */   public R apply(DenseVector a, SparseVector b) {
/* 46 */     return applyCommon((Vector)a, (Vector)b);
/*    */   }
/*    */   
/*    */   public abstract R applyCommon(Vector paramVector1, Vector paramVector2);
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/la4j/operation/CommonVectorVectorOperation.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */