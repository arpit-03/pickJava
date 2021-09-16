/*    */ package org.apache.commons.math3.linear;
/*    */ 
/*    */ import org.apache.commons.math3.FieldElement;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DefaultFieldMatrixChangingVisitor<T extends FieldElement<T>>
/*    */   implements FieldMatrixChangingVisitor<T>
/*    */ {
/*    */   private final T zero;
/*    */   
/*    */   public DefaultFieldMatrixChangingVisitor(T zero) {
/* 41 */     this.zero = zero;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void start(int rows, int columns, int startRow, int endRow, int startColumn, int endColumn) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public T visit(int row, int column, T value) {
/* 51 */     return value;
/*    */   }
/*    */ 
/*    */   
/*    */   public T end() {
/* 56 */     return this.zero;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/linear/DefaultFieldMatrixChangingVisitor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */