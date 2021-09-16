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
/*    */ public abstract class MatrixIterator
/*    */   extends CursorIterator
/*    */ {
/*    */   protected final int rows;
/*    */   protected final int columns;
/*    */   
/*    */   public abstract MatrixIterator andAlsoDivide(MatrixIterator paramMatrixIterator);
/*    */   
/*    */   public abstract MatrixIterator andAlsoMultiply(MatrixIterator paramMatrixIterator);
/*    */   
/*    */   public abstract MatrixIterator orElseSubtract(MatrixIterator paramMatrixIterator);
/*    */   
/*    */   public abstract MatrixIterator orElseAdd(MatrixIterator paramMatrixIterator);
/*    */   
/*    */   public abstract int columnIndex();
/*    */   
/*    */   public abstract int rowIndex();
/*    */   
/*    */   public MatrixIterator(int rows, int columns) {
/* 30 */     this.rows = rows;
/* 31 */     this.columns = columns;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/la4j/iterator/MatrixIterator.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */