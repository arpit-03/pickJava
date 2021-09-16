/*     */ package edu.mines.jtk.sgl;
/*     */ 
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.util.ArrayList;
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
/*     */ public class PickContext
/*     */   extends TransformContext
/*     */ {
/*     */   private MouseEvent _event;
/*     */   private Segment _pickSegment;
/*     */   private Point3 _nearPoint;
/*     */   private Point3 _farPoint;
/*     */   private ArrayStack<Segment> _pickSegmentStack;
/*     */   private ArrayList<PickResult> _pickResults;
/*     */   
/*     */   public PickContext(MouseEvent event) {
/*  47 */     super((ViewCanvas)event.getSource());
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
/* 212 */     this._pickSegmentStack = new ArrayStack<>();
/*     */     
/* 214 */     this._pickResults = new ArrayList<>();
/*     */     this._event = event;
/*     */     int xp = event.getX();
/*     */     int yp = event.getY();
/*     */     Point3 near = new Point3(xp, yp, 0.0D);
/*     */     Point3 far = new Point3(xp, yp, 1.0D);
/*     */     this._pickSegment = new Segment(near, far);
/*     */     this._pickSegment.transform(getPixelToWorld());
/*     */     this._nearPoint = this._pickSegment.getA();
/*     */     this._farPoint = this._pickSegment.getB();
/*     */   }
/*     */   
/*     */   public MouseEvent getMouseEvent() {
/*     */     return this._event;
/*     */   }
/*     */   
/*     */   public Segment getPickSegment() {
/*     */     return new Segment(this._pickSegment);
/*     */   }
/*     */   
/*     */   public boolean segmentIntersectsSphereOf(Node node) {
/*     */     double px, py, pz;
/*     */     BoundingSphere bs = node.getBoundingSphere(false);
/*     */     if (bs.isEmpty())
/*     */       return false; 
/*     */     if (bs.isInfinite())
/*     */       return true; 
/*     */     Point3 a = this._farPoint;
/*     */     Point3 b = this._nearPoint;
/*     */     Point3 c = bs.getCenter();
/*     */     double r = bs.getRadius();
/*     */     double rr = r * r;
/*     */     double ax = a.x;
/*     */     double ay = a.y;
/*     */     double az = a.z;
/*     */     double bx = b.x;
/*     */     double by = b.y;
/*     */     double bz = b.z;
/*     */     double cx = c.x;
/*     */     double cy = c.y;
/*     */     double cz = c.z;
/*     */     double bax = bx - ax;
/*     */     double bay = by - ay;
/*     */     double baz = bz - az;
/*     */     double cax = cx - ax;
/*     */     double cay = cy - ay;
/*     */     double caz = cz - az;
/*     */     double caba = cax * bax + cay * bay + caz * baz;
/*     */     if (caba <= 0.0D) {
/*     */       px = ax;
/*     */       py = ay;
/*     */       pz = az;
/*     */     } else {
/*     */       double baba = bax * bax + bay * bay + baz * baz;
/*     */       if (baba <= caba) {
/*     */         px = bx;
/*     */         py = by;
/*     */         pz = bz;
/*     */       } else {
/*     */         double u = caba / baba;
/*     */         px = ax + u * bax;
/*     */         py = ay + u * bay;
/*     */         pz = az + u * baz;
/*     */       } 
/*     */     } 
/*     */     double dx = px - cx;
/*     */     double dy = py - cy;
/*     */     double dz = pz - cz;
/*     */     return (dx * dx + dy * dy + dz * dz <= rr);
/*     */   }
/*     */   
/*     */   public void addResult(Point3 point) {
/*     */     if (point != null) {
/*     */       PickResult pr = new PickResult(this, point);
/*     */       this._pickResults.add(pr);
/*     */     } 
/*     */   }
/*     */   
/*     */   public PickResult getClosest() {
/*     */     PickResult prmin = null;
/*     */     double zpmin = Double.MAX_VALUE;
/*     */     for (PickResult pr : this._pickResults) {
/*     */       double zp = pr.getPixelZ();
/*     */       if (zp < zpmin) {
/*     */         zpmin = zp;
/*     */         prmin = pr;
/*     */       } 
/*     */     } 
/*     */     return prmin;
/*     */   }
/*     */   
/*     */   public void pushLocalToWorld(Matrix44 transform) {
/*     */     super.pushLocalToWorld(transform);
/*     */     this._pickSegmentStack.push(new Segment(this._pickSegment));
/*     */     this._pickSegment.transform(transform.inverse());
/*     */     this._nearPoint = this._pickSegment.getA();
/*     */     this._farPoint = this._pickSegment.getB();
/*     */   }
/*     */   
/*     */   public void popLocalToWorld() {
/*     */     super.popLocalToWorld();
/*     */     this._pickSegment = this._pickSegmentStack.pop();
/*     */     this._nearPoint = this._pickSegment.getA();
/*     */     this._farPoint = this._pickSegment.getB();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/sgl/PickContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */