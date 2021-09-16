/*    */ package org.apache.commons.math3.geometry.euclidean.threed;
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
/*    */ public class Segment
/*    */ {
/*    */   private final Vector3D start;
/*    */   private final Vector3D end;
/*    */   private final Line line;
/*    */   
/*    */   public Segment(Vector3D start, Vector3D end, Line line) {
/* 40 */     this.start = start;
/* 41 */     this.end = end;
/* 42 */     this.line = line;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Vector3D getStart() {
/* 49 */     return this.start;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Vector3D getEnd() {
/* 56 */     return this.end;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Line getLine() {
/* 63 */     return this.line;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/geometry/euclidean/threed/Segment.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */