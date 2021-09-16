/*     */ package edu.mines.jtk.sgl;
/*     */ 
/*     */ import java.awt.event.MouseEvent;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MouseOnPlane
/*     */   extends MouseConstrained
/*     */ {
/*     */   private Vector3 _normal;
/*     */   private Plane _plane;
/*     */   private Vector3 _delta;
/*     */   
/*     */   public MouseOnPlane(MouseEvent event, Point3 origin, Plane plane, Matrix44 localToPixel) {
/*  46 */     super(localToPixel);
/*     */     
/*  48 */     this._normal = plane.getNormal();
/*  49 */     this._plane = new Plane(plane);
/*  50 */     this._delta = origin.minus(getPointOnPlane(event));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point3 getPoint(MouseEvent event) {
/*  61 */     return getPointOnPlane(event).plusEquals(this._delta);
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
/*     */ 
/*     */   
/*     */   private Point3 getPointOnPlane(MouseEvent event) {
/*     */     Point3 point;
/*  83 */     Segment segment = getMouseSegment(event);
/*  84 */     Point3 a = segment.getA();
/*  85 */     Point3 b = segment.getB();
/*  86 */     Vector3 d = b.minus(a);
/*     */ 
/*     */     
/*  89 */     double den = d.dot(this._normal);
/*     */     
/*  91 */     double num = -this._plane.distanceTo(a);
/*  92 */     double t = num / den;
/*     */     
/*  94 */     if (t <= 0.0D) {
/*  95 */       point = a;
/*  96 */     } else if (t >= 1.0D) {
/*  97 */       point = b;
/*     */     } else {
/*  99 */       point = a.plus(d.times(t));
/*     */     } 
/*     */     
/* 102 */     return point;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/sgl/MouseOnPlane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */