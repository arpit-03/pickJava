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
/*    */ class BoundaryBuilder<S extends Space>
/*    */   implements BSPTreeVisitor<S>
/*    */ {
/*    */   public BSPTreeVisitor.Order visitOrder(BSPTree<S> node) {
/* 33 */     return BSPTreeVisitor.Order.PLUS_MINUS_SUB;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void visitInternalNode(BSPTree<S> node) {
/* 39 */     SubHyperplane<S> plusOutside = null;
/* 40 */     SubHyperplane<S> plusInside = null;
/* 41 */     NodesSet<S> splitters = null;
/*    */ 
/*    */ 
/*    */     
/* 45 */     Characterization<S> plusChar = new Characterization<S>(node.getPlus(), node.getCut().copySelf());
/*    */     
/* 47 */     if (plusChar.touchOutside()) {
/*    */ 
/*    */ 
/*    */       
/* 51 */       Characterization<S> minusChar = new Characterization<S>(node.getMinus(), plusChar.outsideTouching());
/* 52 */       if (minusChar.touchInside()) {
/*    */ 
/*    */         
/* 55 */         plusOutside = minusChar.insideTouching();
/* 56 */         splitters = new NodesSet<S>();
/* 57 */         splitters.addAll(minusChar.getInsideSplitters());
/* 58 */         splitters.addAll(plusChar.getOutsideSplitters());
/*    */       } 
/*    */     } 
/*    */     
/* 62 */     if (plusChar.touchInside()) {
/*    */ 
/*    */ 
/*    */       
/* 66 */       Characterization<S> minusChar = new Characterization<S>(node.getMinus(), plusChar.insideTouching());
/* 67 */       if (minusChar.touchOutside()) {
/*    */ 
/*    */         
/* 70 */         plusInside = minusChar.outsideTouching();
/* 71 */         if (splitters == null) {
/* 72 */           splitters = new NodesSet<S>();
/*    */         }
/* 74 */         splitters.addAll(minusChar.getOutsideSplitters());
/* 75 */         splitters.addAll(plusChar.getInsideSplitters());
/*    */       } 
/*    */     } 
/*    */     
/* 79 */     if (splitters != null)
/*    */     {
/* 81 */       for (BSPTree<S> up = node.getParent(); up != null; up = up.getParent()) {
/* 82 */         splitters.add(up);
/*    */       }
/*    */     }
/*    */ 
/*    */     
/* 87 */     node.setAttribute(new BoundaryAttribute<S>(plusOutside, plusInside, splitters));
/*    */   }
/*    */   
/*    */   public void visitLeafNode(BSPTree<S> node) {}
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/geometry/partitioning/BoundaryBuilder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */