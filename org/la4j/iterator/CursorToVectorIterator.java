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
/*    */ class CursorToVectorIterator
/*    */   extends VectorIterator
/*    */ {
/*    */   private final CursorIterator underlying;
/*    */   
/*    */   public CursorToVectorIterator(CursorIterator underlying, int length) {
/* 29 */     super(length);
/* 30 */     this.underlying = underlying;
/*    */   }
/*    */ 
/*    */   
/*    */   public int index() {
/* 35 */     return this.underlying.cursor();
/*    */   }
/*    */ 
/*    */   
/*    */   public double get() {
/* 40 */     return this.underlying.get();
/*    */   }
/*    */ 
/*    */   
/*    */   public void set(double value) {
/* 45 */     this.underlying.set(value);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasNext() {
/* 50 */     return this.underlying.hasNext();
/*    */   }
/*    */ 
/*    */   
/*    */   public Double next() {
/* 55 */     return this.underlying.next();
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/la4j/iterator/CursorToVectorIterator.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */