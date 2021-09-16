/*     */ package edu.mines.jtk.sgl;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TransformContext
/*     */   extends TraversalContext
/*     */ {
/*     */   private ViewCanvas _canvas;
/*     */   private View _view;
/*     */   private World _world;
/*     */   private Matrix44 _localToWorld;
/*     */   private Matrix44 _worldToView;
/*     */   private Matrix44 _viewToCube;
/*     */   private Matrix44 _cubeToPixel;
/*     */   private ArrayStack<Matrix44> _localToWorldStack;
/*     */   
/*     */   public TransformContext(ViewCanvas canvas) {
/* 303 */     this._localToWorld = new Matrix44();
/* 304 */     this._worldToView = new Matrix44();
/* 305 */     this._viewToCube = new Matrix44();
/* 306 */     this._cubeToPixel = new Matrix44();
/* 307 */     this._localToWorldStack = new ArrayStack<>();
/*     */     this._canvas = canvas;
/*     */     this._view = this._canvas.getView();
/*     */     this._world = this._view.getWorld();
/*     */     this._localToWorld = Matrix44.identity();
/*     */     this._worldToView = this._view.getWorldToView();
/*     */     this._viewToCube = this._canvas.getViewToCube();
/*     */     this._cubeToPixel = this._canvas.getCubeToPixel();
/*     */   }
/*     */   
/*     */   public ViewCanvas getViewCanvas() {
/*     */     return this._canvas;
/*     */   }
/*     */   
/*     */   public View getView() {
/*     */     return this._view;
/*     */   }
/*     */   
/*     */   public World getWorld() {
/*     */     return this._world;
/*     */   }
/*     */   
/*     */   public Matrix44 getLocalToWorld() {
/*     */     return new Matrix44(this._localToWorld);
/*     */   }
/*     */   
/*     */   public Matrix44 getWorldToLocal() {
/*     */     return getLocalToWorld().inverseEquals();
/*     */   }
/*     */   
/*     */   public Matrix44 getLocalToView() {
/*     */     return getWorldToView().timesEquals(this._localToWorld);
/*     */   }
/*     */   
/*     */   public Matrix44 getViewToLocal() {
/*     */     return getLocalToView().inverseEquals();
/*     */   }
/*     */   
/*     */   public Matrix44 getLocalToCube() {
/*     */     return getWorldToCube().timesEquals(this._localToWorld);
/*     */   }
/*     */   
/*     */   public Matrix44 getCubeToLocal() {
/*     */     return getLocalToCube().inverseEquals();
/*     */   }
/*     */   
/*     */   public Matrix44 getLocalToPixel() {
/*     */     return getWorldToPixel().timesEquals(this._localToWorld);
/*     */   }
/*     */   
/*     */   public Matrix44 getPixelToLocal() {
/*     */     return getLocalToPixel().inverseEquals();
/*     */   }
/*     */   
/*     */   public Matrix44 getWorldToView() {
/*     */     return new Matrix44(this._worldToView);
/*     */   }
/*     */   
/*     */   public Matrix44 getViewToWorld() {
/*     */     return getWorldToView().inverseEquals();
/*     */   }
/*     */   
/*     */   public Matrix44 getWorldToCube() {
/*     */     return getViewToCube().timesEquals(this._worldToView);
/*     */   }
/*     */   
/*     */   public Matrix44 getCubeToWorld() {
/*     */     return getWorldToCube().inverseEquals();
/*     */   }
/*     */   
/*     */   public Matrix44 getWorldToPixel() {
/*     */     return getViewToPixel().timesEquals(this._worldToView);
/*     */   }
/*     */   
/*     */   public Matrix44 getPixelToWorld() {
/*     */     return getWorldToPixel().inverseEquals();
/*     */   }
/*     */   
/*     */   public Matrix44 getViewToCube() {
/*     */     return new Matrix44(this._viewToCube);
/*     */   }
/*     */   
/*     */   public Matrix44 getCubeToView() {
/*     */     return getViewToCube().inverseEquals();
/*     */   }
/*     */   
/*     */   public Matrix44 getViewToPixel() {
/*     */     return getCubeToPixel().timesEquals(this._viewToCube);
/*     */   }
/*     */   
/*     */   public Matrix44 getPixelToView() {
/*     */     return getViewToPixel().inverseEquals();
/*     */   }
/*     */   
/*     */   public Matrix44 getCubeToPixel() {
/*     */     return new Matrix44(this._cubeToPixel);
/*     */   }
/*     */   
/*     */   public Matrix44 getPixelToCube() {
/*     */     return getCubeToPixel().inverseEquals();
/*     */   }
/*     */   
/*     */   public void pushLocalToWorld(Matrix44 transform) {
/*     */     this._localToWorldStack.push(new Matrix44(this._localToWorld));
/*     */     this._localToWorld.timesEquals(transform);
/*     */   }
/*     */   
/*     */   public void popLocalToWorld() {
/*     */     this._localToWorld = this._localToWorldStack.pop();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/sgl/TransformContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */