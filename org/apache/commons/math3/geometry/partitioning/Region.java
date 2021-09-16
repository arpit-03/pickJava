/*    */ package org.apache.commons.math3.geometry.partitioning;
/*    */ 
/*    */ import org.apache.commons.math3.geometry.Point;
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
/*    */ public interface Region<S extends org.apache.commons.math3.geometry.Space>
/*    */ {
/*    */   Region<S> buildNew(BSPTree<S> paramBSPTree);
/*    */   
/*    */   Region<S> copySelf();
/*    */   
/*    */   boolean isEmpty();
/*    */   
/*    */   boolean isEmpty(BSPTree<S> paramBSPTree);
/*    */   
/*    */   boolean isFull();
/*    */   
/*    */   boolean isFull(BSPTree<S> paramBSPTree);
/*    */   
/*    */   boolean contains(Region<S> paramRegion);
/*    */   
/*    */   Location checkPoint(Point<S> paramPoint);
/*    */   
/*    */   BoundaryProjection<S> projectToBoundary(Point<S> paramPoint);
/*    */   
/*    */   BSPTree<S> getTree(boolean paramBoolean);
/*    */   
/*    */   double getBoundarySize();
/*    */   
/*    */   double getSize();
/*    */   
/*    */   Point<S> getBarycenter();
/*    */   
/*    */   @Deprecated
/*    */   Side side(Hyperplane<S> paramHyperplane);
/*    */   
/*    */   SubHyperplane<S> intersection(SubHyperplane<S> paramSubHyperplane);
/*    */   
/*    */   public enum Location
/*    */   {
/* 58 */     INSIDE,
/*    */ 
/*    */     
/* 61 */     OUTSIDE,
/*    */ 
/*    */     
/* 64 */     BOUNDARY;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/geometry/partitioning/Region.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */