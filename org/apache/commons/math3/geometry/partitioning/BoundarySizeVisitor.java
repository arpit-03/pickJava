/*    */ package org.apache.commons.math3.geometry.partitioning;
/*    */ 
/*    */ import org.apache.commons.math3.geometry.Space;
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
/*    */ class BoundarySizeVisitor<S extends Space>
/*    */   implements BSPTreeVisitor<S>
/*    */ {
/* 33 */   private double boundarySize = 0.0D;
/*    */ 
/*    */ 
/*    */   
/*    */   public BSPTreeVisitor.Order visitOrder(BSPTree<S> node) {
/* 38 */     return BSPTreeVisitor.Order.MINUS_SUB_PLUS;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void visitInternalNode(BSPTree<S> node) {
/* 44 */     BoundaryAttribute<S> attribute = (BoundaryAttribute<S>)node.getAttribute();
/*    */     
/* 46 */     if (attribute.getPlusOutside() != null) {
/* 47 */       this.boundarySize += attribute.getPlusOutside().getSize();
/*    */     }
/* 49 */     if (attribute.getPlusInside() != null) {
/* 50 */       this.boundarySize += attribute.getPlusInside().getSize();
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void visitLeafNode(BSPTree<S> node) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public double getSize() {
/* 62 */     return this.boundarySize;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/geometry/partitioning/BoundarySizeVisitor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */