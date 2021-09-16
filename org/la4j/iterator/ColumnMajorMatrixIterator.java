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
/*    */ public abstract class ColumnMajorMatrixIterator
/*    */   extends MatrixIterator
/*    */ {
/*    */   public ColumnMajorMatrixIterator(int rows, int columns) {
/* 27 */     super(rows, columns);
/*    */   }
/*    */ 
/*    */   
/*    */   protected int cursor() {
/* 32 */     return columnIndex() * this.rows + rowIndex();
/*    */   }
/*    */ 
/*    */   
/*    */   public MatrixIterator orElseAdd(MatrixIterator those) {
/* 37 */     return new CursorToColumnMajorMatrixIterator(orElse(those, JoinFunction.ADD), this.rows, this.columns);
/*    */   }
/*    */ 
/*    */   
/*    */   public MatrixIterator orElseSubtract(MatrixIterator those) {
/* 42 */     return new CursorToColumnMajorMatrixIterator(orElse(those, JoinFunction.SUB), this.rows, this.columns);
/*    */   }
/*    */ 
/*    */   
/*    */   public MatrixIterator andAlsoMultiply(MatrixIterator those) {
/* 47 */     return new CursorToColumnMajorMatrixIterator(andAlso(those, JoinFunction.MUL), this.rows, this.columns);
/*    */   }
/*    */ 
/*    */   
/*    */   public MatrixIterator andAlsoDivide(MatrixIterator those) {
/* 52 */     return new CursorToColumnMajorMatrixIterator(andAlso(those, JoinFunction.DIV), this.rows, this.columns);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/la4j/iterator/ColumnMajorMatrixIterator.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */