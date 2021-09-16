/*    */ package org.la4j.operation.ooplace;
/*    */ 
/*    */ import org.la4j.Vector;
/*    */ import org.la4j.iterator.VectorIterator;
/*    */ import org.la4j.operation.SymmetricVectorVectorOperation;
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
/*    */ public class OoPlaceVectorsAddition
/*    */   extends SymmetricVectorVectorOperation<Vector>
/*    */ {
/*    */   public Vector apply(SparseVector a, SparseVector b) {
/* 34 */     VectorIterator these = a.nonZeroIterator();
/* 35 */     VectorIterator those = b.nonZeroIterator();
/* 36 */     VectorIterator both = these.orElseAdd(those);
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
/*    */   public Vector apply(DenseVector a, DenseVector b) {
/* 50 */     Vector result = a.blank();
/*    */     
/* 52 */     for (int i = 0; i < a.length(); i++) {
/* 53 */       result.set(i, a.get(i) + b.get(i));
/*    */     }
/*    */     
/* 56 */     return result;
/*    */   }
/*    */ 
/*    */   
/*    */   public Vector applySymmetric(DenseVector a, SparseVector b) {
/* 61 */     Vector result = a.copy();
/* 62 */     VectorIterator it = b.nonZeroIterator();
/*    */     
/* 64 */     while (it.hasNext()) {
/* 65 */       double x = ((Double)it.next()).doubleValue();
/* 66 */       int i = it.index();
/* 67 */       result.set(i, result.get(i) + x);
/*    */     } 
/*    */     
/* 70 */     return result;
/*    */   }
/*    */ 
/*    */   
/*    */   public void ensureApplicableTo(Vector a, Vector b) {
/* 75 */     if (a.length() != b.length())
/* 76 */       throw new IllegalArgumentException("Given vectors should have the same length: " + a.length() + " does not equal to " + b.length() + "."); 
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/la4j/operation/ooplace/OoPlaceVectorsAddition.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */