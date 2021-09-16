/*     */ package edu.mines.jtk.sgl;
/*     */ 
/*     */ import java.awt.Component;
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
/*     */ public class MouseOnLine
/*     */   extends MouseConstrained
/*     */ {
/*     */   private double _ymouse;
/*     */   private Point3 _origin;
/*     */   private Vector3 _vector;
/*     */   private Vector3 _delta;
/*     */   private double _length;
/*     */   private Mode _mode;
/*     */   
/*     */   public MouseOnLine(MouseEvent event, Point3 origin, Vector3 vector, Matrix44 localToPixel) {
/*  46 */     super(localToPixel);
/*     */     
/*  48 */     this._ymouse = event.getY();
/*  49 */     this._origin = new Point3(origin);
/*  50 */     this._vector = vector.normalize();
/*     */ 
/*     */ 
/*     */     
/*  54 */     Segment mouseSegment = getMouseSegment(event);
/*  55 */     Point3 mouseNear = mouseSegment.getA();
/*  56 */     Point3 mouseFar = mouseSegment.getB();
/*  57 */     Vector3 mouseVector = mouseFar.minus(mouseNear).normalize();
/*  58 */     double d = mouseVector.dot(this._vector);
/*  59 */     if (d < 0.0D) {
/*  60 */       d = -d;
/*  61 */       this._vector.negateEquals();
/*     */     } 
/*  63 */     this._mode = (d < 0.867D) ? Mode.NEAREST : Mode.PUSH_PULL;
/*  64 */     this._length = mouseSegment.length();
/*  65 */     this._delta = origin.minus(getPointOnLine(event));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point3 getPoint(MouseEvent event) {
/*  74 */     return getPointOnLine(event).plusEquals(this._delta);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private enum Mode
/*     */   {
/*  81 */     NEAREST,
/*  82 */     PUSH_PULL;
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
/*     */   private Point3 getPointOnLine(MouseEvent event) {
/*  94 */     Point3 point = null;
/*     */ 
/*     */     
/*  97 */     if (this._mode == Mode.NEAREST) {
/*     */ 
/*     */       
/* 100 */       Segment segment = getMouseSegment(event);
/* 101 */       Point3 p1 = segment.getA();
/* 102 */       Vector3 v1 = segment.getB().minus(p1);
/*     */ 
/*     */       
/* 105 */       Point3 p2 = this._origin;
/* 106 */       Vector3 v2 = this._vector;
/*     */ 
/*     */       
/* 109 */       Vector3 a = p2.minus(p1);
/* 110 */       Vector3 b = v1;
/* 111 */       Vector3 c = v1.cross(v2);
/* 112 */       double cc = c.lengthSquared();
/* 113 */       double t = a.cross(b).dot(c) / cc;
/* 114 */       point = p2.plus(v2.times(t));
/*     */ 
/*     */     
/*     */     }
/* 118 */     else if (this._mode == Mode.PUSH_PULL) {
/*     */ 
/*     */       
/* 121 */       Component component = (Component)event.getSource();
/* 122 */       double height = component.getHeight();
/* 123 */       double ymouse = event.getY();
/* 124 */       double scale = 0.05D * (this._ymouse - ymouse) / height;
/* 125 */       point = this._origin.plus(this._vector.times(scale * this._length));
/*     */     } 
/*     */     
/* 128 */     return point;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/sgl/MouseOnLine.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */