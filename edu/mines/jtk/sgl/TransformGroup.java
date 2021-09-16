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
/*     */ public class TransformGroup
/*     */   extends Group
/*     */ {
/*     */   private Matrix44 _transform;
/*     */   
/*     */   public TransformGroup(Matrix44 transform) {
/*  32 */     this._transform = new Matrix44(transform);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Matrix44 getTransform() {
/*  40 */     return new Matrix44(this._transform);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTransform(Matrix44 transform) {
/*  48 */     this._transform = new Matrix44(transform);
/*  49 */     dirtyBoundingSphere();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void cullBegin(CullContext cc) {
/*  60 */     super.cullBegin(cc);
/*  61 */     cc.pushLocalToWorld(this._transform);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void cullEnd(CullContext cc) {
/*  69 */     cc.popLocalToWorld();
/*  70 */     super.cullEnd(cc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawBegin(DrawContext dc) {
/*  78 */     super.drawBegin(dc);
/*  79 */     dc.pushLocalToWorld(this._transform);
/*  80 */     Gl.glPushMatrix();
/*  81 */     Gl.glMultMatrixd(this._transform.m, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawEnd(DrawContext dc) {
/*  89 */     dc.popLocalToWorld();
/*  90 */     Gl.glPopMatrix();
/*  91 */     super.drawEnd(dc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void pickBegin(PickContext pc) {
/*  99 */     super.pickBegin(pc);
/* 100 */     pc.pushLocalToWorld(this._transform);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void pickEnd(PickContext pc) {
/* 108 */     pc.popLocalToWorld();
/* 109 */     super.pickEnd(pc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected BoundingSphere computeBoundingSphere(boolean finite) {
/* 120 */     BoundingSphere bs = super.computeBoundingSphere(finite);
/* 121 */     if (!bs.isEmpty() && !bs.isInfinite()) {
/* 122 */       double r = bs.getRadius();
/* 123 */       Point3 c = bs.getCenter();
/* 124 */       Point3 x = new Point3(c.x + r, c.y, c.z);
/* 125 */       Point3 y = new Point3(c.x, c.y + r, c.z);
/* 126 */       Point3 z = new Point3(c.x, c.y, c.z + r);
/* 127 */       c = this._transform.times(c);
/* 128 */       x = this._transform.times(x);
/* 129 */       y = this._transform.times(y);
/* 130 */       z = this._transform.times(z);
/* 131 */       Vector3 cx = c.minus(x);
/* 132 */       Vector3 cy = c.minus(y);
/* 133 */       Vector3 cz = c.minus(z);
/* 134 */       double lx = cx.length();
/* 135 */       double ly = cy.length();
/* 136 */       double lz = cz.length();
/* 137 */       r = lx;
/* 138 */       if (r < ly) r = ly; 
/* 139 */       if (r < lz) r = lz; 
/* 140 */       bs = new BoundingSphere(c, r);
/*     */     } 
/* 142 */     return bs;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/sgl/TransformGroup.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */