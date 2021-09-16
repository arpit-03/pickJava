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
/*     */ public class LineState
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
/*     */   public boolean hasStipple() {
/*  70 */     return this._stippleSet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getStippleFactor() {
/*  78 */     return this._factor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public short getStipplePattern() {
/*  86 */     return this._pattern;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setStipple(int factor, short pattern) {
/*  95 */     this._factor = factor;
/*  96 */     this._pattern = pattern;
/*  97 */     this._stippleSet = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unsetStipple() {
/* 104 */     this._factor = _factorDefault;
/* 105 */     this._pattern = _patternDefault;
/* 106 */     this._stippleSet = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasWidth() {
/* 114 */     return this._widthSet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getWidth() {
/* 122 */     return this._width;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWidth(float width) {
/* 130 */     this._width = width;
/* 131 */     this._widthSet = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unsetWidth() {
/* 138 */     this._width = _widthDefault;
/* 139 */     this._widthSet = false;
/*     */   }
/*     */   
/*     */   public void apply() {
/* 143 */     if (this._smoothSet) {
/* 144 */       if (this._smooth) {
/* 145 */         Gl.glEnable(2848);
/*     */       } else {
/* 147 */         Gl.glDisable(2848);
/*     */       } 
/*     */     }
/* 150 */     if (this._stippleSet) {
/* 151 */       Gl.glEnable(2852);
/* 152 */       Gl.glLineStipple(this._factor, this._pattern);
/*     */     } 
/* 154 */     if (this._widthSet)
/* 155 */       Gl.glLineWidth(this._width); 
/*     */   }
/*     */   
/*     */   public int getAttributeBits() {
/* 159 */     return 8196;
/*     */   }
/*     */   
/*     */   private static boolean _smoothDefault = false;
/* 163 */   private boolean _smooth = _smoothDefault;
/*     */   
/*     */   private boolean _smoothSet;
/* 166 */   private static int _factorDefault = 1;
/* 167 */   private int _factor = _factorDefault;
/* 168 */   private static short _patternDefault = -1;
/* 169 */   private short _pattern = _patternDefault;
/*     */   
/*     */   private boolean _stippleSet;
/* 172 */   private static float _widthDefault = 1.0F;
/* 173 */   private float _width = _widthDefault;
/*     */   private boolean _widthSet;
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/sgl/LineState.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */