/*     */ package edu.mines.jtk.sgl;
/*     */ 
/*     */ import edu.mines.jtk.ogl.Gl;
/*     */ import edu.mines.jtk.util.Check;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AxisAlignedFrame
/*     */   extends Group
/*     */ {
/*     */   private Axis _axis;
/*     */   private BoxConstraint _constraint;
/*     */   private Point3[] _p;
/*     */   
/*     */   public Axis getAxis() {
/*     */     return this._axis;
/*     */   }
/*     */   
/*     */   public Point3 getCornerMin() {
/*     */     return new Point3(this._p[0]);
/*     */   }
/*     */   
/*     */   public Point3 getCornerMax() {
/*     */     return new Point3(this._p[3]);
/*     */   }
/*     */   
/*     */   public Point3 getCorner(int index) {
/*     */     return new Point3(this._p[index]);
/*     */   }
/*     */   
/*     */   public void setCorners(Point3 pa, Point3 pb) {
/*     */     Point3 pmin = new Point3(Math.min(pa.x, pb.x), Math.min(pa.y, pb.y), Math.min(pa.z, pb.z));
/*     */     Point3 pmax = new Point3(Math.max(pa.x, pb.x), Math.max(pa.y, pb.y), Math.max(pa.z, pb.z));
/*     */     if (this._constraint != null)
/*     */       this._constraint.constrainBox(pmin, pmax); 
/*     */     if (this._axis == Axis.X) {
/*     */       double x = 0.5D * (pmin.x + pmax.x);
/*     */       this._p[0] = new Point3(x, pmin.y, pmin.z);
/*     */       this._p[1] = new Point3(x, pmax.y, pmin.z);
/*     */       this._p[2] = new Point3(x, pmin.y, pmax.z);
/*     */       this._p[3] = new Point3(x, pmax.y, pmax.z);
/*     */     } else if (this._axis == Axis.Y) {
/*     */       double y = 0.5D * (pmin.y + pmax.y);
/*     */       this._p[0] = new Point3(pmin.x, y, pmin.z);
/*     */       this._p[1] = new Point3(pmin.x, y, pmax.z);
/*     */       this._p[2] = new Point3(pmax.x, y, pmin.z);
/*     */       this._p[3] = new Point3(pmax.x, y, pmax.z);
/*     */     } else {
/*     */       double z = 0.5D * (pmin.z + pmax.z);
/*     */       this._p[0] = new Point3(pmin.x, pmin.y, z);
/*     */       this._p[1] = new Point3(pmax.x, pmin.y, z);
/*     */       this._p[2] = new Point3(pmin.x, pmax.y, z);
/*     */       this._p[3] = new Point3(pmax.x, pmax.y, z);
/*     */     } 
/*     */     dirtyBoundingSphere();
/*     */     dirtyDraw();
/*     */   }
/*     */   
/*     */   public BoxConstraint getBoxConstraint() {
/*     */     return this._constraint;
/*     */   }
/*     */   
/*     */   public void setBoxConstraint(BoxConstraint constraint) {
/*     */     this._constraint = constraint;
/*     */     setCorners(this._p[0], this._p[3]);
/*     */   }
/*     */   
/*     */   public void addChild(Node node) {
/*     */     super.addChild(node);
/*     */     if (node instanceof AxisAlignedPanel) {
/*     */       AxisAlignedPanel panel = (AxisAlignedPanel)node;
/*     */       Check.state((panel.getFrame() == null), "frame of panel equals null");
/*     */       panel.setFrame(this);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void removeChild(Node node) {
/*     */     super.removeChild(node);
/*     */     if (node instanceof AxisAlignedPanel) {
/*     */       AxisAlignedPanel panel = (AxisAlignedPanel)node;
/*     */       Check.state((panel.getFrame() == this), "frame of panel equals this");
/*     */       panel.setFrame(null);
/*     */     } 
/*     */   }
/*     */   
/*     */   BoundingSphere computeBoundingSphereOfFrame(boolean finite) {
/*     */     double dx = (this._p[3]).x - (this._p[0]).x;
/*     */     double dy = (this._p[3]).y - (this._p[0]).y;
/*     */     double dz = (this._p[3]).z - (this._p[0]).z;
/*     */     double r = Math.sqrt(dx * dx + dy * dy + dz * dz);
/*     */     double x = 0.5D * ((this._p[0]).x + (this._p[3]).x);
/*     */     double y = 0.5D * ((this._p[0]).y + (this._p[3]).y);
/*     */     double z = 0.5D * ((this._p[0]).z + (this._p[3]).z);
/*     */     Point3 c = new Point3(x, y, z);
/*     */     return new BoundingSphere(c, r);
/*     */   }
/*     */   
/*     */   void pickOnFrame(PickContext pc) {
/*     */     Segment ps = pc.getPickSegment();
/*     */     Point3 pa = ps.intersectWithTriangle((this._p[0]).x, (this._p[0]).y, (this._p[0]).z, (this._p[1]).x, (this._p[1]).y, (this._p[1]).z, (this._p[3]).x, (this._p[3]).y, (this._p[3]).z);
/*     */     Point3 pb = ps.intersectWithTriangle((this._p[0]).x, (this._p[0]).y, (this._p[0]).z, (this._p[3]).x, (this._p[3]).y, (this._p[3]).z, (this._p[2]).x, (this._p[2]).y, (this._p[2]).z);
/*     */     pc.addResult(pa);
/*     */     pc.addResult(pb);
/*     */   }
/*     */   
/*     */   public AxisAlignedFrame(Axis axis, Point3 pa, Point3 pb) {
/* 207 */     this._p = new Point3[4];
/*     */     this._axis = axis;
/*     */     setCorners(pa, pb);
/*     */     addChild(new Wire());
/*     */   } private class Wire extends Node { protected BoundingSphere computeBoundingSphere(boolean finite) {
/* 212 */       return AxisAlignedFrame.this.computeBoundingSphereOfFrame(finite);
/*     */     } private Wire() {}
/*     */     protected void draw(DrawContext dc) {
/* 215 */       Gl.glColor3f(1.0F, 1.0F, 1.0F);
/* 216 */       Gl.glLineWidth(1.5F);
/* 217 */       Gl.glPolygonMode(1032, 6913);
/*     */ 
/*     */       
/* 220 */       Gl.glBegin(7);
/* 221 */       Gl.glVertex3d((AxisAlignedFrame.this._p[0]).x, (AxisAlignedFrame.this._p[0]).y, (AxisAlignedFrame.this._p[0]).z);
/* 222 */       Gl.glVertex3d((AxisAlignedFrame.this._p[1]).x, (AxisAlignedFrame.this._p[1]).y, (AxisAlignedFrame.this._p[1]).z);
/* 223 */       Gl.glVertex3d((AxisAlignedFrame.this._p[3]).x, (AxisAlignedFrame.this._p[3]).y, (AxisAlignedFrame.this._p[3]).z);
/* 224 */       Gl.glVertex3d((AxisAlignedFrame.this._p[2]).x, (AxisAlignedFrame.this._p[2]).y, (AxisAlignedFrame.this._p[2]).z);
/* 225 */       Gl.glEnd();
/*     */     }
/*     */     public void pick(PickContext pc) {
/* 228 */       AxisAlignedFrame.this.pickOnFrame(pc);
/*     */     } }
/*     */ 
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/sgl/AxisAlignedFrame.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */