/*    */ package edu.mines.jtk.ogl;
/*    */ 
/*    */ import com.jogamp.opengl.GLContext;
/*    */ import edu.mines.jtk.util.Check;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GlTextureName
/*    */ {
/*    */   GLContext _context;
/*    */   int _name;
/*    */   
/*    */   public GlTextureName() {
/* 49 */     this._context = GLContext.getCurrent();
/* 50 */     Check.state((this._context != null), "OpenGL context is not null");
/* 51 */     int[] names = new int[1];
/* 52 */     Gl.glGenTextures(1, names, 0);
/* 53 */     this._name = names[0];
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int name() {
/* 62 */     return this._name;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public synchronized void dispose() {
/* 71 */     if (this._context != null) {
/* 72 */       GLContext current = GLContext.getCurrent();
/* 73 */       if (this._context == current || this._context
/* 74 */         .makeCurrent() == 1) {
/*    */         
/*    */         try {
/* 77 */           int[] names = { this._name };
/* 78 */           Gl.glDeleteTextures(1, names, 0);
/*    */         } finally {
/* 80 */           if (this._context != current) {
/* 81 */             this._context.release();
/* 82 */             current.makeCurrent();
/*    */           } 
/*    */         } 
/*    */       }
/* 86 */       this._context = null;
/* 87 */       this._name = 0;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void finalize() throws Throwable {
/*    */     try {
/* 96 */       dispose();
/*    */     } finally {
/* 98 */       super.finalize();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/ogl/GlTextureName.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */