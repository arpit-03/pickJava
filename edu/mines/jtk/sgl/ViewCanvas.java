/*     */ package edu.mines.jtk.sgl;
/*     */ 
/*     */ import edu.mines.jtk.ogl.Gl;
/*     */ import edu.mines.jtk.ogl.GlCanvas;
/*     */ import edu.mines.jtk.util.Direct;
/*     */ import java.nio.FloatBuffer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ViewCanvas
/*     */   extends GlCanvas
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private View _view;
/*     */   
/*     */   public ViewCanvas() {}
/*     */   
/*     */   public ViewCanvas(View view) {
/*  46 */     setView(view);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setView(View view) {
/*  54 */     if (this._view != null)
/*  55 */       this._view.removeCanvas(this); 
/*  56 */     this._view = view;
/*  57 */     if (this._view != null) {
/*  58 */       this._view.addCanvas(this);
/*  59 */       this._view.updateTransforms();
/*  60 */       this._view.repaint();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public View getView() {
/*  69 */     return this._view;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setViewToCube(Matrix44 viewToCube) {
/*  78 */     this._viewToCube = new Matrix44(viewToCube);
/*  79 */     repaint();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Matrix44 getViewToCube() {
/*  87 */     return new Matrix44(this._viewToCube);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCubeToPixel(Matrix44 cubeToPixel) {
/*  96 */     this._cubeToPixel = new Matrix44(cubeToPixel);
/*  97 */     repaint();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Matrix44 getCubeToPixel() {
/* 105 */     return new Matrix44(this._cubeToPixel);
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
/*     */   public double getPixelZ(final int xp, final int yp) {
/* 121 */     final int hp = getHeight();
/* 122 */     final FloatBuffer pixels = Direct.newFloatBuffer(1);
/* 123 */     runWithContext(new Runnable() {
/*     */           public void run() {
/* 125 */             Gl.glPushAttrib(32);
/* 126 */             Gl.glReadBuffer(1028);
/* 127 */             Gl.glReadPixels(xp, hp - 1 - yp, 1, 1, 6402, 5126, pixels);
/* 128 */             Gl.glPopAttrib();
/*     */           }
/*     */         });
/* 131 */     return pixels.get(0);
/*     */   }
/*     */   
/*     */   public void glInit() {
/* 135 */     Gl.setSwapInterval(1);
/*     */   }
/*     */   
/*     */   public void glPaint() {
/* 139 */     if (this._view != null)
/* 140 */       this._view.draw(this); 
/*     */   }
/*     */   
/*     */   public void glResize(int x, int y, int width, int height) {
/* 144 */     if (this._view != null) {
/* 145 */       this._view.updateTransforms(this);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 152 */   private Matrix44 _viewToCube = Matrix44.identity();
/* 153 */   private Matrix44 _cubeToPixel = Matrix44.identity();
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/sgl/ViewCanvas.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */