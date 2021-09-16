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
/*    */ public class CursorToColumnMajorMatrixIterator
/*    */   extends ColumnMajorMatrixIterator
/*    */ {
/*    */   private final CursorIterator underlying;
/*    */   
/*    */   public CursorToColumnMajorMatrixIterator(CursorIterator underlying, int rows, int columns) {
/* 29 */     super(rows, columns);
/* 30 */     this.underlying = underlying;
/*    */   }
/*    */ 
/*    */   
/*    */   public int rowIndex() {
/* 35 */     return this.underlying.cursor() - columnIndex() * this.rows;
/*    */   }
/*    */ 
/*    */   
/*    */   public int columnIndex() {
/* 40 */     return this.underlying.cursor() / this.rows;
/*    */   }
/*    */ 
/*    */   
/*    */   public double get() {
/* 45 */     return this.underlying.get();
/*    */   }
/*    */ 
/*    */   
/*    */   public void set(double value) {
/* 50 */     this.underlying.set(value);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasNext() {
/* 55 */     return this.underlying.hasNext();
/*    */   }
/*    */ 
/*    */   
/*    */   public Double next() {
/* 60 */     return this.underlying.next();
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/la4j/iterator/CursorToColumnMajorMatrixIterator.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */