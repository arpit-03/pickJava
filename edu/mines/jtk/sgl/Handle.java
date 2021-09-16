/*     */ package edu.mines.jtk.sgl;
/*     */ 
/*     */ import edu.mines.jtk.ogl.Gl;
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
/*     */ public abstract class Handle
/*     */   extends Group
/*     */ {
/*     */   public static double getSize() {
/*  56 */     return _size;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setSize(double size) {
/*  66 */     _size = size;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Matrix44 getTransform() {
/*  76 */     return new Matrix44(this._transform);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTransform(Matrix44 transform) {
/*  86 */     this._transform = new Matrix44(transform);
/*  87 */     dirtyBoundingSphere();
/*  88 */     dirtyDraw();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point3 getLocation() {
/*  96 */     return this._transform.times(new Point3());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLocation(Point3 p) {
/* 106 */     setLocation(p.x, p.y, p.z);
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
/*     */   public void setLocation(double x, double y, double z) {
/* 118 */     this._transform = Matrix44.translate(x, y, z);
/* 119 */     dirtyBoundingSphere();
/* 120 */     dirtyDraw();
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
/*     */   protected BoundingSphere computeBoundingSphere(boolean finite) {
/* 132 */     this._boundingSphereChildren = super.computeBoundingSphere(true);
/* 133 */     return finite ? BoundingSphere.empty() : BoundingSphere.infinite();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Handle(Matrix44 transform)
/*     */   {
/* 229 */     this._transform = Matrix44.identity(); setTransform(transform); } protected Handle(Point3 p) { this._transform = Matrix44.identity(); setLocation(p.x, p.y, p.z); } protected Handle(double x, double y, double z) { this._transform = Matrix44.identity();
/*     */     setLocation(x, y, z); }
/*     */   
/*     */   protected void cullBegin(CullContext cc) {
/*     */     super.cullBegin(cc);
/*     */     Matrix44 transform = computeTransform(cc);
/*     */     cc.pushLocalToWorld(transform);
/*     */   }
/*     */   protected void cullEnd(CullContext cc) {
/*     */     cc.popLocalToWorld();
/*     */     super.cullEnd(cc);
/*     */   }
/*     */   
/*     */   private Matrix44 computeTransform(TransformContext tc) {
/* 243 */     View view = tc.getView();
/* 244 */     Tuple3 as = view.getAxesScale();
/* 245 */     Matrix44 localToPixel = tc.getLocalToPixel().times(this._transform);
/* 246 */     Matrix44 pixelToLocal = localToPixel.inverse();
/* 247 */     Point3 p = new Point3(0.0D, 0.0D, 0.0D);
/* 248 */     Point3 q = localToPixel.times(p);
/* 249 */     q.x += getSize();
/* 250 */     q = pixelToLocal.times(q);
/* 251 */     double dx = (q.x - p.x) * as.x;
/* 252 */     double dy = (q.y - p.y) * as.y;
/* 253 */     double dz = (q.z - p.z) * as.z;
/* 254 */     double d = Math.sqrt(dx * dx + dy * dy + dz * dz);
/* 255 */     double r = this._boundingSphereChildren.getRadius();
/* 256 */     double s = d / r;
/* 257 */     double sx = s / as.x;
/* 258 */     double sy = s / as.y;
/* 259 */     double sz = s / as.z;
/* 260 */     return this._transform.times(Matrix44.scale(sx, sy, sz));
/*     */   }
/*     */   
/*     */   protected void drawBegin(DrawContext dc) {
/*     */     super.drawBegin(dc);
/*     */     Matrix44 transform = computeTransform(dc);
/*     */     dc.pushLocalToWorld(transform);
/*     */     Gl.glPushMatrix();
/*     */     Gl.glMultMatrixd(transform.m, 0);
/*     */   }
/*     */   
/*     */   protected void drawEnd(DrawContext dc) {
/*     */     dc.popLocalToWorld();
/*     */     Gl.glPopMatrix();
/*     */     super.drawEnd(dc);
/*     */   }
/*     */   
/*     */   protected void pickBegin(PickContext pc) {
/*     */     super.pickBegin(pc);
/*     */     Matrix44 transform = computeTransform(pc);
/*     */     pc.pushLocalToWorld(transform);
/*     */   }
/*     */   
/*     */   protected void pickEnd(PickContext pc) {
/*     */     pc.popLocalToWorld();
/*     */     super.pickEnd(pc);
/*     */   }
/*     */   
/*     */   private static double _size = 10.0D;
/*     */   private Matrix44 _transform;
/*     */   private BoundingSphere _boundingSphereChildren;
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/sgl/Handle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */