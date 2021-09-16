/*    */ package org.apache.commons.math3.linear;
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
/*    */ public class DefaultRealMatrixPreservingVisitor
/*    */   implements RealMatrixPreservingVisitor
/*    */ {
/*    */   public void start(int rows, int columns, int startRow, int endRow, int startColumn, int endColumn) {}
/*    */   
/*    */   public void visit(int row, int column, double value) {}
/*    */   
/*    */   public double end() {
/* 40 */     return 0.0D;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/linear/DefaultRealMatrixPreservingVisitor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */