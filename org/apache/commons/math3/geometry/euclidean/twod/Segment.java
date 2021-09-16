/*     */ package org.apache.commons.math3.geometry.euclidean.twod;
/*     */ 
/*     */ import org.apache.commons.math3.geometry.Point;
/*     */ import org.apache.commons.math3.util.FastMath;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Segment
/*     */ {
/*     */   private final Vector2D start;
/*     */   private final Vector2D end;
/*     */   private final Line line;
/*     */   
/*     */   public Segment(Vector2D start, Vector2D end, Line line) {
/*  42 */     this.start = start;
/*  43 */     this.end = end;
/*  44 */     this.line = line;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector2D getStart() {
/*  51 */     return this.start;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector2D getEnd() {
/*  58 */     return this.end;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Line getLine() {
/*  65 */     return this.line;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double distance(Vector2D p) {
/*  84 */     double deltaX = this.end.getX() - this.start.getX();
/*  85 */     double deltaY = this.end.getY() - this.start.getY();
/*     */     
/*  87 */     double r = ((p.getX() - this.start.getX()) * deltaX + (p.getY() - this.start.getY()) * deltaY) / (deltaX * deltaX + deltaY * deltaY);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  97 */     if (r < 0.0D || r > 1.0D) {
/*  98 */       double dist1 = getStart().distance((Point<Euclidean2D>)p);
/*  99 */       double dist2 = getEnd().distance((Point<Euclidean2D>)p);
/*     */       
/* 101 */       return FastMath.min(dist1, dist2);
/*     */     } 
/*     */ 
/*     */     
/* 105 */     double px = this.start.getX() + r * deltaX;
/* 106 */     double py = this.start.getY() + r * deltaY;
/*     */     
/* 108 */     Vector2D interPt = new Vector2D(px, py);
/* 109 */     return interPt.distance((Point<Euclidean2D>)p);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/geometry/euclidean/twod/Segment.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */