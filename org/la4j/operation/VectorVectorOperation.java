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
/*    */ public abstract class VectorVectorOperation<R>
/*    */ {
/*    */   public abstract R apply(SparseVector paramSparseVector1, SparseVector paramSparseVector2);
/*    */   
/*    */   public abstract R apply(SparseVector paramSparseVector, DenseVector paramDenseVector);
/*    */   
/*    */   public abstract R apply(DenseVector paramDenseVector1, DenseVector paramDenseVector2);
/*    */   
/*    */   public abstract R apply(DenseVector paramDenseVector, SparseVector paramSparseVector);
/*    */   
/*    */   public void ensureApplicableTo(Vector a, Vector b) {}
/*    */   
/*    */   public VectorOperation<R> partiallyApply(final SparseVector a) {
/* 38 */     return new VectorOperation<R>()
/*    */       {
/*    */         public R apply(SparseVector b) {
/* 41 */           return VectorVectorOperation.this.apply(a, b);
/*    */         }
/*    */ 
/*    */         
/*    */         public R apply(DenseVector b) {
/* 46 */           return VectorVectorOperation.this.apply(a, b);
/*    */         }
/*    */ 
/*    */         
/*    */         public void ensureApplicableTo(Vector b) {
/* 51 */           VectorVectorOperation.this.ensureApplicableTo((Vector)a, b);
/*    */         }
/*    */       };
/*    */   }
/*    */   
/*    */   public VectorOperation<R> partiallyApply(final DenseVector a) {
/* 57 */     return new VectorOperation<R>()
/*    */       {
/*    */         public R apply(SparseVector b) {
/* 60 */           return VectorVectorOperation.this.apply(a, b);
/*    */         }
/*    */ 
/*    */         
/*    */         public R apply(DenseVector b) {
/* 65 */           return VectorVectorOperation.this.apply(a, b);
/*    */         }
/*    */ 
/*    */         
/*    */         public void ensureApplicableTo(Vector b) {
/* 70 */           VectorVectorOperation.this.ensureApplicableTo((Vector)a, b);
/*    */         }
/*    */       };
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/la4j/operation/VectorVectorOperation.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */