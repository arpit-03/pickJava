/*    */ package org.la4j.operation.ooplace;
/*    */ 
/*    */ import org.la4j.Vector;
/*    */ import org.la4j.iterator.VectorIterator;
/*    */ import org.la4j.operation.VectorVectorOperation;
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
/*    */ public class OoPlaceVectorsSubtraction
/*    */   extends VectorVectorOperation<Vector>
/*    */ {
/*    */   public Vector apply(SparseVector a, SparseVector b) {
/* 34 */     VectorIterator these = a.nonZeroIterator();
/* 35 */     VectorIterator those = b.nonZeroIterator();
/* 36 */     VectorIterator both = these.orElseSubtract(those);
/* 37 */     Vector result = a.blank();
/*    */     
/* 39 */     while (both.hasNext()) {
/* 40 */       double x = ((Double)both.next()).doubleValue();
/* 41 */       int i = both.index();
/* 42 */       result.set(i, x);
/*    */     } 
/*    */     
/* 45 */     return result;
/*    */   }
/*    */ 
/*    */   
/*    */   public Vector apply(SparseVector a, DenseVector b) {
/* 50 */     Vector result = b.multiply(-1.0D);
/* 51 */     VectorIterator it = a.nonZeroIterator();
/*    */     
/* 53 */     while (it.hasNext()) {
/* 54 */       double x = ((Double)it.next()).doubleValue();
/* 55 */       int i = it.index();
/* 56 */       result.set(i, result.get(i) + x);
/*    */     } 
/*    */     
/* 59 */     return result;
/*    */   }
/*    */ 
/*    */   
/*    */   public Vector apply(DenseVector a, DenseVector b) {
/* 64 */     Vector result = a.blank();
/*    */     
/* 66 */     for (int i = 0; i < b.length(); i++) {
/* 67 */       result.set(i, a.get(i) - b.get(i));
/*    */     }
/*    */     
/* 70 */     return result;
/*    */   }
/*    */ 
/*    */   
/*    */   public Vector apply(DenseVector a, SparseVector b) {
/* 75 */     Vector result = a.copy();
/* 76 */     VectorIterator it = b.nonZeroIterator();
/*    */     
/* 78 */     while (it.hasNext()) {
/* 79 */       double x = ((Double)it.next()).doubleValue();
/* 80 */       int i = it.index();
/* 81 */       result.set(i, result.get(i) - x);
/*    */     } 
/*    */     
/* 84 */     return result;
/*    */   }
/*    */ 
/*    */   
/*    */   public void ensureApplicableTo(Vector a, Vector b) {
/* 89 */     if (a.length() != b.length())
/* 90 */       throw new IllegalArgumentException("Given vectors should have the same length: " + a.length() + " does not equal to " + b.length() + "."); 
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/la4j/operation/ooplace/OoPlaceVectorsSubtraction.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */