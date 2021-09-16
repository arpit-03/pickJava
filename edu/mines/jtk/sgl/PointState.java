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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PointState
/*     */   implements State
/*     */ {
/*     */   public boolean hasSmooth() {
/*  37 */     return this._smoothSet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getSmooth() {
/*  45 */     return this._smooth;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSmooth(boolean smooth) {
/*  53 */     this._smooth = smooth;
/*  54 */     this._smoothSet = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unsetSmooth() {
/*  61 */     this._smooth = _smoothDefault;
/*  62 */     this._smoothSet = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasSize() {
/*  70 */     return this._sizeSet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getSize() {
/*  78 */     return this._size;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSize(float size) {
/*  86 */     this._size = size;
/*  87 */     this._sizeSet = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unsetSize() {
/*  94 */     this._size = _sizeDefault;
/*  95 */     this._sizeSet = false;
/*     */   }
/*     */   
/*     */   public void apply() {
/*  99 */     if (this._smoothSet) {
/* 100 */       if (this._smooth) {
/* 101 */         Gl.glEnable(2832);
/*     */       } else {
/* 103 */         Gl.glDisable(2832);
/*     */       } 
/*     */     }
/* 106 */     if (this._sizeSet)
/* 107 */       Gl.glPointSize(this._size); 
/*     */   }
/*     */   
/*     */   public int getAttributeBits() {
/* 111 */     return 8194;
/*     */   }
/*     */   
/*     */   private static boolean _smoothDefault = false;
/* 115 */   private boolean _smooth = _smoothDefault;
/*     */   
/*     */   private boolean _smoothSet;
/* 118 */   private static float _sizeDefault = 1.0F;
/* 119 */   private float _size = _sizeDefault;
/*     */   private boolean _sizeSet;
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/sgl/PointState.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */