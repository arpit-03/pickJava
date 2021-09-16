/*    */ package org.la4j.iterator;
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
/*    */ public abstract class VectorIterator
/*    */   extends CursorIterator
/*    */ {
/*    */   protected final int length;
/*    */   
/*    */   public VectorIterator(int length) {
/* 29 */     this.length = length;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public VectorIterator orElseAdd(VectorIterator those) {
/* 40 */     return new CursorToVectorIterator(orElse(those, JoinFunction.ADD), this.length);
/*    */   }
/*    */   
/*    */   public VectorIterator orElseSubtract(VectorIterator those) {
/* 44 */     return new CursorToVectorIterator(orElse(those, JoinFunction.SUB), this.length);
/*    */   }
/*    */   
/*    */   public VectorIterator andAlsoMultiply(VectorIterator those) {
/* 48 */     return new CursorToVectorIterator(andAlso(those, JoinFunction.MUL), this.length);
/*    */   }
/*    */   
/*    */   public VectorIterator andAlsoDivide(VectorIterator those) {
/* 52 */     return new CursorToVectorIterator(andAlso(those, JoinFunction.DIV), this.length);
/*    */   }
/*    */   
/*    */   public double innerProduct(VectorIterator those) {
/* 56 */     VectorIterator both = andAlsoMultiply(those);
/* 57 */     double acc = 0.0D;
/* 58 */     while (both.hasNext()) {
/* 59 */       acc += both.next().doubleValue();
/*    */     }
/*    */     
/* 62 */     return acc;
/*    */   }
/*    */ 
/*    */   
/*    */   protected int cursor() {
/* 67 */     return index();
/*    */   }
/*    */   
/*    */   public abstract int index();
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/la4j/iterator/VectorIterator.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */