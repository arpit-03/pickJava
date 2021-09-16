/*     */ package edu.mines.jtk.ogl;
/*     */ 
/*     */ import com.jogamp.opengl.GLContext;
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
/*     */ public class GlDisplayList
/*     */ {
/*     */   GLContext _context;
/*     */   int _list;
/*     */   int _range;
/*     */   
/*     */   public GlDisplayList() {
/*  49 */     this(1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GlDisplayList(int range) {
/*  59 */     this._context = GLContext.getCurrent();
/*  60 */     Check.state((this._context != null), "OpenGL context is not null");
/*  61 */     this._list = Gl.glGenLists(range);
/*  62 */     this._range = range;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int list() {
/*  71 */     return this._list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int range() {
/*  79 */     return this._range;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void dispose() {
/*  88 */     if (this._context != null) {
/*  89 */       GLContext current = GLContext.getCurrent();
/*  90 */       if (this._context == current || this._context
/*  91 */         .makeCurrent() == 1) {
/*     */         try {
/*  93 */           Gl.glDeleteLists(this._list, this._range);
/*     */         } finally {
/*  95 */           if (this._context != current) {
/*  96 */             this._context.release();
/*  97 */             current.makeCurrent();
/*     */           } 
/*     */         } 
/*     */       }
/* 101 */       this._context = null;
/* 102 */       this._list = 0;
/* 103 */       this._range = 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void finalize() throws Throwable {
/*     */     try {
/* 112 */       dispose();
/*     */     } finally {
/* 114 */       super.finalize();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/ogl/GlDisplayList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */