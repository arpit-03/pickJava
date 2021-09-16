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
/*     */ public class DragContext
/*     */ {
/*     */   private MouseEvent _event;
/*     */   private ViewCanvas _canvas;
/*     */   private View _view;
/*     */   private World _world;
/*     */   private Point3 _pointLocal;
/*     */   private Point3 _pointWorld;
/*     */   private Point3 _pointPixel;
/*     */   private Matrix44 _localToWorld;
/*     */   private Matrix44 _localToPixel;
/*     */   private Matrix44 _worldToPixel;
/*     */   private Matrix44 _pixelToLocal;
/*     */   private Matrix44 _pixelToWorld;
/*     */   
/*     */   public DragContext(PickResult pr) {
/*  44 */     this._event = pr.getMouseEvent();
/*  45 */     this._canvas = pr.getViewCanvas();
/*  46 */     this._view = pr.getView();
/*  47 */     this._world = pr.getWorld();
/*  48 */     this._pointLocal = pr.getPointLocal();
/*  49 */     this._pointWorld = pr.getPointWorld();
/*  50 */     this._pointPixel = pr.getPointPixel();
/*  51 */     this._localToWorld = pr.getLocalToWorld();
/*  52 */     this._localToPixel = pr.getLocalToPixel();
/*  53 */     this._worldToPixel = pr.getWorldToPixel();
/*  54 */     this._pixelToLocal = this._localToPixel.inverse();
/*  55 */     this._pixelToWorld = this._worldToPixel.inverse();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update(MouseEvent event) {
/*  66 */     this._event = event;
/*  67 */     this._pointPixel.x = event.getX();
/*  68 */     this._pointPixel.y = event.getY();
/*  69 */     this._pointLocal = this._pixelToLocal.times(this._pointPixel);
/*  70 */     this._pointWorld = this._pixelToWorld.times(this._pointPixel);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ViewCanvas getViewCanvas() {
/*  78 */     return this._canvas;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public View getView() {
/*  86 */     return this._view;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public World getWorld() {
/*  94 */     return this._world;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MouseEvent getMouseEvent() {
/* 102 */     return this._event;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point3 getPointLocal() {
/* 110 */     return new Point3(this._pointLocal);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point3 getPointWorld() {
/* 118 */     return new Point3(this._pointWorld);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point3 getPointPixel() {
/* 126 */     return new Point3(this._pointPixel);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getPixelZ() {
/* 136 */     return this._pointPixel.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Matrix44 getLocalToWorld() {
/* 144 */     return new Matrix44(this._localToWorld);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Matrix44 getLocalToPixel() {
/* 152 */     return new Matrix44(this._localToPixel);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Matrix44 getWorldToPixel() {
/* 160 */     return new Matrix44(this._worldToPixel);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/sgl/DragContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */