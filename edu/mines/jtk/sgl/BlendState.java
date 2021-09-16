/*     */ package edu.mines.jtk.sgl;
/*     */ 
/*     */ import edu.mines.jtk.ogl.Gl;
/*     */ import java.awt.Color;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlendState
/*     */   implements State
/*     */ {
/*     */   public boolean hasColor() {
/*  39 */     return this._colorSet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color getColor() {
/*  47 */     return this._color;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setColor(Color color) {
/*  55 */     this._color = color;
/*  56 */     this._colorSet = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unsetColor() {
/*  63 */     this._color = _colorDefault;
/*  64 */     this._colorSet = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasEquation() {
/*  72 */     return this._equationSet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEquation() {
/*  80 */     return this._equation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEquation(int mode) {
/*  88 */     this._equation = mode;
/*  89 */     this._equationSet = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unsetEquation() {
/*  96 */     this._equation = _equationDefault;
/*  97 */     this._equationSet = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasBlendFunction() {
/* 105 */     return this._functionSet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSfactor() {
/* 113 */     return this._sfactor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDfactor() {
/* 121 */     return this._dfactor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFunction(int sfactor, int dfactor) {
/* 130 */     this._sfactor = sfactor;
/* 131 */     this._dfactor = dfactor;
/* 132 */     this._functionSet = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unsetFunction() {
/* 139 */     this._sfactor = _sfactorDefault;
/* 140 */     this._dfactor = _dfactorDefault;
/* 141 */     this._functionSet = false;
/*     */   }
/*     */   
/*     */   public void apply() {
/* 145 */     Gl.glEnable(3042);
/* 146 */     if (this._colorSet) {
/* 147 */       float r = this._color.getRed() / 255.0F;
/* 148 */       float g = this._color.getGreen() / 255.0F;
/* 149 */       float b = this._color.getBlue() / 255.0F;
/* 150 */       float a = this._color.getAlpha() / 255.0F;
/* 151 */       Gl.glBlendColor(r, g, b, a);
/*     */     } 
/* 153 */     if (this._equationSet)
/* 154 */       Gl.glBlendEquation(this._equation); 
/* 155 */     if (this._functionSet)
/* 156 */       Gl.glBlendFunc(this._sfactor, this._dfactor); 
/*     */   }
/*     */   
/*     */   public int getAttributeBits() {
/* 160 */     return 24576;
/*     */   }
/*     */   
/* 163 */   private static Color _colorDefault = new Color(0.0F, 0.0F, 0.0F, 0.0F);
/* 164 */   private Color _color = _colorDefault;
/*     */   
/*     */   private boolean _colorSet;
/* 167 */   private static int _equationDefault = 32774;
/* 168 */   private int _equation = _equationDefault;
/*     */   
/*     */   private boolean _equationSet;
/* 171 */   private static int _sfactorDefault = 1;
/* 172 */   private static int _dfactorDefault = 0;
/* 173 */   private int _sfactor = _sfactorDefault;
/* 174 */   private int _dfactor = _dfactorDefault;
/*     */   private boolean _functionSet;
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/sgl/BlendState.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */