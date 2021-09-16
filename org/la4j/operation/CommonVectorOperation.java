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
/*    */ public abstract class CommonVectorOperation<R>
/*    */   extends VectorOperation<R>
/*    */ {
/*    */   public R apply(SparseVector a) {
/* 31 */     return applyCommon((Vector)a);
/*    */   }
/*    */ 
/*    */   
/*    */   public R apply(DenseVector a) {
/* 36 */     return applyCommon((Vector)a);
/*    */   }
/*    */   
/*    */   abstract R applyCommon(Vector paramVector);
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/la4j/operation/CommonVectorOperation.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */