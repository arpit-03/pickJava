/*     */ package edu.mines.jtk.sgl;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class View
/*     */ {
/*     */   private World _world;
/*     */   private Matrix44 _worldToView;
/*     */   private ArrayList<ViewCanvas> _canvasList;
/*     */   private Tuple3 _axesScale;
/*     */   private AxesOrientation _axesOrientation;
/*     */   
/*     */   public AxesOrientation getAxesOrientation() {
/*  56 */     return this._axesOrientation;
/*     */   }
/*     */   
/*     */   public void setAxesOrientation(AxesOrientation axesOrientation) {
/*  60 */     this._axesOrientation = axesOrientation;
/*  61 */     updateTransforms();
/*  62 */     repaint();
/*     */   }
/*     */   
/*     */   public Tuple3 getAxesScale() {
/*  66 */     return new Tuple3(this._axesScale);
/*     */   }
/*     */   
/*     */   public void setAxesScale(Tuple3 s) {
/*  70 */     setAxesScale(s.x, s.y, s.z);
/*     */   }
/*     */   
/*     */   public void setAxesScale(double sx, double sy, double sz) {
/*  74 */     this._axesScale = new Tuple3(sx, sy, sz);
/*  75 */     updateTransforms();
/*  76 */     repaint();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public View() {
/* 222 */     this._worldToView = Matrix44.identity();
/* 223 */     this._canvasList = new ArrayList<>(1);
/* 224 */     this._axesScale = new Tuple3(1.0D, 1.0D, 1.0D);
/* 225 */     this._axesOrientation = AxesOrientation.XRIGHT_YUP_ZOUT; } public View(World world) { this._worldToView = Matrix44.identity(); this._canvasList = new ArrayList<>(1); this._axesScale = new Tuple3(1.0D, 1.0D, 1.0D); this._axesOrientation = AxesOrientation.XRIGHT_YUP_ZOUT;
/*     */     setWorld(world); }
/*     */ 
/*     */   
/*     */   public void setWorld(World world) {
/*     */     if (this._world != null)
/*     */       this._world.removeView(this); 
/*     */     this._world = world;
/*     */     if (this._world != null)
/*     */       this._world.addView(this); 
/*     */     updateTransforms();
/*     */     repaint();
/*     */   }
/*     */   
/*     */   public World getWorld() {
/*     */     return this._world;
/*     */   }
/*     */   
/*     */   public void setWorldToView(Matrix44 worldToView) {
/*     */     this._worldToView = new Matrix44(worldToView);
/*     */     repaint();
/*     */   }
/*     */   
/*     */   public Matrix44 getWorldToView() {
/*     */     return new Matrix44(this._worldToView);
/*     */   }
/*     */   
/*     */   public int countCanvases() {
/*     */     return this._canvasList.size();
/*     */   }
/*     */   
/*     */   public Iterator<ViewCanvas> getCanvases() {
/*     */     return this._canvasList.iterator();
/*     */   }
/*     */   
/*     */   public void updateTransforms() {
/*     */     for (ViewCanvas canvas : this._canvasList)
/*     */       updateTransforms(canvas); 
/*     */   }
/*     */   
/*     */   public void repaint() {
/*     */     for (ViewCanvas canvas : this._canvasList)
/*     */       canvas.repaint(); 
/*     */   }
/*     */   
/*     */   protected abstract void updateTransforms(ViewCanvas paramViewCanvas);
/*     */   
/*     */   protected abstract void draw(ViewCanvas paramViewCanvas);
/*     */   
/*     */   boolean addCanvas(ViewCanvas canvas) {
/*     */     if (!this._canvasList.contains(canvas)) {
/*     */       this._canvasList.add(canvas);
/*     */       updateTransforms();
/*     */       repaint();
/*     */       return true;
/*     */     } 
/*     */     return false;
/*     */   }
/*     */   
/*     */   boolean removeCanvas(ViewCanvas canvas) {
/*     */     if (this._canvasList.remove(canvas)) {
/*     */       updateTransforms();
/*     */       repaint();
/*     */       return true;
/*     */     } 
/*     */     return false;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/sgl/View.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */