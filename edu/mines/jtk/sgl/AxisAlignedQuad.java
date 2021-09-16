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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AxisAlignedQuad
/*     */   extends Group
/*     */   implements Selectable, Dragable
/*     */ {
/*     */   private AxisAlignedFrame _frame;
/*     */   private boolean _handlesVisible;
/*     */   private Handle[] _h;
/*     */   private Dragger _dragger;
/*     */   
/*     */   public AxisAlignedFrame getFrame() {
/*     */     return this._frame;
/*     */   }
/*     */   
/*     */   public void dragBegin(DragContext dc) {
/*     */     this._dragger = new Dragger();
/*     */     this._dragger.dragBegin(dc);
/*     */   }
/*     */   
/*     */   public void drag(DragContext dc) {
/*     */     this._dragger.drag(dc);
/*     */   }
/*     */   
/*     */   public void dragEnd(DragContext dc) {
/*     */     this._dragger.dragEnd(dc);
/*     */     this._dragger = null;
/*     */   }
/*     */   
/*     */   protected void selectedChanged() {
/*     */     if (isSelected()) {
/*     */       updateHandles();
/*     */       showHandles();
/*     */     } else {
/*     */       hideHandles();
/*     */     } 
/*     */   }
/*     */   
/*     */   public AxisAlignedQuad(Axis axis, Point3 qa, Point3 qb) {
/*  81 */     this._h = new Handle[4];
/*     */     this._frame = new AxisAlignedFrame(axis, qa, qb);
/*     */     addChild(this._frame);
/*     */   }
/*     */   private class Dragger implements Dragable { private MouseConstrained _mouseConstrained;
/*     */     private Point3 _origin;
/*     */     
/*     */     public void dragBegin(DragContext dc) {
/*  89 */       Point3 origin = dc.getPointWorld();
/*  90 */       Vector3 normal = null;
/*  91 */       Axis axis = AxisAlignedQuad.this._frame.getAxis();
/*  92 */       if (axis == Axis.X) {
/*  93 */         normal = new Vector3(1.0D, 0.0D, 0.0D);
/*  94 */       } else if (axis == Axis.Y) {
/*  95 */         normal = new Vector3(0.0D, 1.0D, 0.0D);
/*  96 */       } else if (axis == Axis.Z) {
/*  97 */         normal = new Vector3(0.0D, 0.0D, 1.0D);
/*     */       } 
/*  99 */       Plane plane = new Plane(origin, normal);
/* 100 */       MouseEvent event = dc.getMouseEvent();
/* 101 */       Matrix44 worldToPixel = dc.getWorldToPixel();
/* 102 */       if (event.isControlDown() || event.isAltDown()) {
/* 103 */         this._mouseConstrained = new MouseOnPlane(event, origin, plane, worldToPixel);
/*     */       } else {
/* 105 */         this._mouseConstrained = new MouseOnLine(event, origin, normal, worldToPixel);
/*     */       } 
/* 107 */       this._origin = origin;
/* 108 */       this._qa = AxisAlignedQuad.this._frame.getCornerMin();
/* 109 */       this._qb = AxisAlignedQuad.this._frame.getCornerMax();
/*     */     } private Point3 _qa; private Point3 _qb; private Dragger() {}
/*     */     public void drag(DragContext dc) {
/* 112 */       assert this._mouseConstrained != null : "mouseConstrained!=null";
/* 113 */       Point3 point = this._mouseConstrained.getPoint(dc.getMouseEvent());
/*     */ 
/*     */ 
/*     */       
/* 117 */       Vector3 vector = point.minus(this._origin);
/* 118 */       Point3 qa = this._qa.plus(vector);
/* 119 */       Point3 qb = this._qb.plus(vector);
/* 120 */       AxisAlignedQuad.this._frame.setCorners(qa, qb);
/* 121 */       AxisAlignedQuad.this.updateHandles();
/*     */     }
/*     */     public void dragEnd(DragContext dc) {
/* 124 */       this._mouseConstrained = null;
/*     */     } }
/*     */ 
/*     */ 
/*     */   
/*     */   private class Handle
/*     */     extends HandleBox
/*     */     implements Dragable
/*     */   {
/*     */     private MouseOnPlane _mouseOnPlane;
/*     */     
/*     */     Handle(Point3 p) {
/* 136 */       super(p);
/*     */     }
/*     */     public void dragBegin(DragContext dc) {
/* 139 */       Point3 p = dc.getPointWorld();
/* 140 */       Vector3 n = null;
/* 141 */       Axis axis = AxisAlignedQuad.this._frame.getAxis();
/* 142 */       if (axis == Axis.X) {
/* 143 */         n = new Vector3(1.0D, 0.0D, 0.0D);
/* 144 */       } else if (axis == Axis.Y) {
/* 145 */         n = new Vector3(0.0D, 1.0D, 0.0D);
/* 146 */       } else if (axis == Axis.Z) {
/* 147 */         n = new Vector3(0.0D, 0.0D, 1.0D);
/*     */       } 
/* 149 */       MouseEvent event = dc.getMouseEvent();
/* 150 */       Point3 origin = getLocation();
/* 151 */       Plane plane = new Plane(p, n);
/* 152 */       Matrix44 worldToPixel = dc.getWorldToPixel();
/* 153 */       this._mouseOnPlane = new MouseOnPlane(event, origin, plane, worldToPixel);
/*     */     }
/*     */     public void drag(DragContext dc) {
/* 156 */       Point3 qnew = this._mouseOnPlane.getPoint(dc.getMouseEvent());
/*     */ 
/*     */ 
/*     */       
/* 160 */       if (this == AxisAlignedQuad.this._h[0]) {
/* 161 */         AxisAlignedQuad.this._frame.setCorners(qnew, AxisAlignedQuad.this._frame.getCorner(3));
/* 162 */       } else if (this == AxisAlignedQuad.this._h[1]) {
/* 163 */         AxisAlignedQuad.this._frame.setCorners(qnew, AxisAlignedQuad.this._frame.getCorner(2));
/* 164 */       } else if (this == AxisAlignedQuad.this._h[2]) {
/* 165 */         AxisAlignedQuad.this._frame.setCorners(qnew, AxisAlignedQuad.this._frame.getCorner(1));
/* 166 */       } else if (this == AxisAlignedQuad.this._h[3]) {
/* 167 */         AxisAlignedQuad.this._frame.setCorners(qnew, AxisAlignedQuad.this._frame.getCorner(0));
/*     */       } 
/* 169 */       AxisAlignedQuad.this.updateHandles();
/*     */     }
/*     */     public void dragEnd(DragContext dc) {
/* 172 */       this._mouseOnPlane = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateHandles() {
/* 178 */     Point3 q0 = this._frame.getCorner(0);
/* 179 */     Point3 q1 = this._frame.getCorner(1);
/* 180 */     Point3 q2 = this._frame.getCorner(2);
/* 181 */     Point3 q3 = this._frame.getCorner(3);
/* 182 */     if (this._h[0] == null) {
/* 183 */       this._h[0] = new Handle(q0);
/* 184 */       this._h[1] = new Handle(q1);
/* 185 */       this._h[2] = new Handle(q2);
/* 186 */       this._h[3] = new Handle(q3);
/*     */     } else {
/* 188 */       this._h[0].setLocation(q0);
/* 189 */       this._h[1].setLocation(q1);
/* 190 */       this._h[2].setLocation(q2);
/* 191 */       this._h[3].setLocation(q3);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void showHandles() {
/* 196 */     if (!this._handlesVisible) {
/* 197 */       this._handlesVisible = true;
/* 198 */       addChild(this._h[0]);
/* 199 */       addChild(this._h[1]);
/* 200 */       addChild(this._h[2]);
/* 201 */       addChild(this._h[3]);
/* 202 */       dirtyDraw();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void hideHandles() {
/* 207 */     if (this._handlesVisible) {
/* 208 */       this._handlesVisible = false;
/* 209 */       removeChild(this._h[0]);
/* 210 */       removeChild(this._h[1]);
/* 211 */       removeChild(this._h[2]);
/* 212 */       removeChild(this._h[3]);
/* 213 */       dirtyDraw();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/sgl/AxisAlignedQuad.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */