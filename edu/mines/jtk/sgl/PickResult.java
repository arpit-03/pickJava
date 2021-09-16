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
/*     */ public class PickResult
/*     */ {
/*     */   private MouseEvent _event;
/*     */   private Node[] _nodes;
/*     */   private Point3 _pointLocal;
/*     */   private Point3 _pointWorld;
/*     */   private Point3 _pointPixel;
/*     */   private double _depthPixel;
/*     */   private Matrix44 _localToWorld;
/*     */   private Matrix44 _localToPixel;
/*     */   private Matrix44 _worldToPixel;
/*     */   
/*     */   public PickResult(PickContext pc, Point3 point) {
/*  40 */     this._event = pc.getMouseEvent();
/*  41 */     this._nodes = pc.getNodes();
/*  42 */     this._localToWorld = pc.getLocalToWorld();
/*  43 */     this._localToPixel = pc.getLocalToPixel();
/*  44 */     this._worldToPixel = pc.getWorldToPixel();
/*  45 */     this._pointLocal = new Point3(point);
/*  46 */     this._pointWorld = this._localToWorld.times(point);
/*  47 */     this._pointPixel = this._localToPixel.times(point);
/*  48 */     this._depthPixel = this._pointPixel.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MouseEvent getMouseEvent() {
/*  57 */     return this._event;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Node[] getNodes() {
/*  66 */     return (Node[])this._nodes.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Node getNode() {
/*  75 */     return this._nodes[this._nodes.length - 1];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dragable getDragableNode() {
/*  86 */     return (Dragable)getNode(Dragable.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Selectable getSelectableNode() {
/*  97 */     return (Selectable)getNode(Selectable.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Node getNode(Class<?> nodeClass) {
/* 108 */     for (int i = this._nodes.length - 1; i >= 0; i--) {
/* 109 */       Node node = this._nodes[i];
/* 110 */       if (nodeClass.isAssignableFrom(node.getClass()))
/* 111 */         return node; 
/*     */     } 
/* 113 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point3 getPointLocal() {
/* 121 */     return new Point3(this._pointLocal);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point3 getPointWorld() {
/* 129 */     return new Point3(this._pointWorld);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point3 getPointPixel() {
/* 137 */     return new Point3(this._pointPixel);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getPixelZ() {
/* 147 */     return this._depthPixel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Matrix44 getLocalToWorld() {
/* 155 */     return new Matrix44(this._localToWorld);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Matrix44 getLocalToPixel() {
/* 163 */     return new Matrix44(this._localToPixel);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Matrix44 getWorldToPixel() {
/* 171 */     return new Matrix44(this._worldToPixel);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ViewCanvas getViewCanvas() {
/* 179 */     return (ViewCanvas)this._event.getSource();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public View getView() {
/* 187 */     return getViewCanvas().getView();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public World getWorld() {
/* 195 */     return getView().getWorld();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/sgl/PickResult.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */