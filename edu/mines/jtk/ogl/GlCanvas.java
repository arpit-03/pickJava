/*     */ package edu.mines.jtk.ogl;
/*     */ 
/*     */ import com.jogamp.opengl.GLAutoDrawable;
/*     */ import com.jogamp.opengl.GLCapabilities;
/*     */ import com.jogamp.opengl.GLCapabilitiesImmutable;
/*     */ import com.jogamp.opengl.GLEventListener;
/*     */ import com.jogamp.opengl.awt.GLCanvas;
/*     */ import com.jogamp.opengl.util.GLReadBufferUtil;
/*     */ import java.awt.Graphics;
/*     */ import java.io.File;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GlCanvas
/*     */   extends GLCanvas
/*     */   implements GLEventListener
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private boolean _autoRepaint;
/*     */   private Runnable _runnable;
/*     */   private String _fileName;
/*     */   
/*     */   public GlCanvas() {
/*  46 */     addGLEventListener(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GlCanvas(GLCapabilities capabilities) {
/*  54 */     super((GLCapabilitiesImmutable)capabilities);
/*  55 */     addGLEventListener(this);
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
/*     */   public void setAutoRepaint(boolean autoRepaint) {
/*  82 */     this._autoRepaint = autoRepaint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void runWithContext(Runnable runnable) {
/*  92 */     this._runnable = runnable;
/*     */     try {
/*  94 */       display();
/*     */     } finally {
/*  96 */       this._runnable = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void paintToFile(String fileName) {
/* 106 */     this._fileName = fileName;
/* 107 */     Graphics g = getGraphics();
/* 108 */     paint(g);
/* 109 */     g.dispose();
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
/*     */   public void glInit() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void glResize(int x, int y, int width, int height) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void glPaint() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init(GLAutoDrawable drawable) {
/* 153 */     glInit();
/*     */   }
/*     */   
/*     */   public void reshape(GLAutoDrawable drawable, int x, int y, int w, int h) {
/* 157 */     glResize(x, y, w, h);
/*     */   }
/*     */   
/*     */   public void display(GLAutoDrawable drawable) {
/* 161 */     if (this._runnable != null) {
/* 162 */       this._runnable.run();
/*     */     } else {
/* 164 */       glPaint();
/* 165 */       if (this._fileName != null) {
/*     */         try {
/* 167 */           File file = new File(this._fileName);
/* 168 */           int w = getWidth();
/* 169 */           int h = getHeight();
/*     */           
/* 171 */           GLReadBufferUtil rbu = new GLReadBufferUtil(true, false);
/* 172 */           rbu.readPixels(getGL(), false);
/* 173 */           rbu.write(file);
/* 174 */         } catch (Exception e) {
/* 175 */           throw new RuntimeException(e);
/*     */         } 
/* 177 */         this._fileName = null;
/*     */       } 
/* 179 */       if (this._autoRepaint)
/* 180 */         repaint(); 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void dispose(GLAutoDrawable drawable) {}
/*     */   
/*     */   public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {}
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/ogl/GlCanvas.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */