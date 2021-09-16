/*    */ package org.apache.commons.math3.geometry.partitioning;
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
/*    */ public interface BSPTreeVisitor<S extends org.apache.commons.math3.geometry.Space>
/*    */ {
/*    */   Order visitOrder(BSPTree<S> paramBSPTree);
/*    */   
/*    */   void visitInternalNode(BSPTree<S> paramBSPTree);
/*    */   
/*    */   void visitLeafNode(BSPTree<S> paramBSPTree);
/*    */   
/*    */   public enum Order
/*    */   {
/* 57 */     PLUS_MINUS_SUB,
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 62 */     PLUS_SUB_MINUS,
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 67 */     MINUS_PLUS_SUB,
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 72 */     MINUS_SUB_PLUS,
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 77 */     SUB_PLUS_MINUS,
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 82 */     SUB_MINUS_PLUS;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/geometry/partitioning/BSPTreeVisitor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */