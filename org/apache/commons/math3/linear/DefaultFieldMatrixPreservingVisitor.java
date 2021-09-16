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
/*    */ public class DefaultFieldMatrixPreservingVisitor<T extends FieldElement<T>>
/*    */   implements FieldMatrixPreservingVisitor<T>
/*    */ {
/*    */   private final T zero;
/*    */   
/*    */   public DefaultFieldMatrixPreservingVisitor(T zero) {
/* 41 */     this.zero = zero;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void start(int rows, int columns, int startRow, int endRow, int startColumn, int endColumn) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void visit(int row, int column, T value) {}
/*    */ 
/*    */   
/*    */   public T end() {
/* 54 */     return this.zero;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/linear/DefaultFieldMatrixPreservingVisitor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */