/*    */ package org.apache.commons.math3.geometry.partitioning;
/*    */ 
/*    */ import org.apache.commons.math3.geometry.Point;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BoundaryProjection<S extends Space>
/*    */ {
/*    */   private final Point<S> original;
/*    */   private final Point<S> projected;
/*    */   private final double offset;
/*    */   
/*    */   public BoundaryProjection(Point<S> original, Point<S> projected, double offset) {
/* 47 */     this.original = original;
/* 48 */     this.projected = projected;
/* 49 */     this.offset = offset;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Point<S> getOriginal() {
/* 56 */     return this.original;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Point<S> getProjected() {
/* 63 */     return this.projected;
/*    */   }
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
/*    */   public double getOffset() {
/* 80 */     return this.offset;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/geometry/partitioning/BoundaryProjection.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */