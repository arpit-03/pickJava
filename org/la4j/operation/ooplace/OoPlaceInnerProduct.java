/*    */ package org.la4j.operation.ooplace;
/*    */ 
/*    */ import org.la4j.Vector;
/*    */ import org.la4j.Vectors;
/*    */ import org.la4j.iterator.VectorIterator;
/*    */ import org.la4j.operation.SymmetricVectorVectorOperation;
/*    */ import org.la4j.vector.DenseVector;
/*    */ import org.la4j.vector.SparseVector;
/*    */ import org.la4j.vector.functor.VectorFunction;
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
/*    */ public class OoPlaceInnerProduct
/*    */   extends SymmetricVectorVectorOperation<Double>
/*    */ {
/*    */   public Double apply(SparseVector a, SparseVector b) {
/* 36 */     VectorIterator these = a.nonZeroIterator();
/* 37 */     VectorIterator those = b.nonZeroIterator();
/*    */     
/* 39 */     return Double.valueOf(these.innerProduct(those));
/*    */   }
/*    */ 
/*    */   
/*    */   public Double applySymmetric(DenseVector a, SparseVector b) {
/* 44 */     return Double.valueOf(b.foldNonZero(Vectors.asSumFunctionAccumulator(0.0D, dot((Vector)a))));
/*    */   }
/*    */ 
/*    */   
/*    */   public Double apply(DenseVector a, DenseVector b) {
/* 49 */     double result = 0.0D;
/*    */     
/* 51 */     for (int i = 0; i < a.length(); i++) {
/* 52 */       result += a.get(i) * b.get(i);
/*    */     }
/*    */     
/* 55 */     return Double.valueOf(result);
/*    */   }
/*    */   
/*    */   private VectorFunction dot(final Vector b) {
/* 59 */     return new VectorFunction()
/*    */       {
/*    */         public double evaluate(int i, double value) {
/* 62 */           return b.get(i) * value;
/*    */         }
/*    */       };
/*    */   }
/*    */ 
/*    */   
/*    */   public void ensureApplicableTo(Vector a, Vector b) {
/* 69 */     if (a.length() != b.length())
/* 70 */       throw new IllegalArgumentException("Given vectors should have the same length: " + a.length() + " does not equal to " + b.length() + "."); 
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/la4j/operation/ooplace/OoPlaceInnerProduct.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */