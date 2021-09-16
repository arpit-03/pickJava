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
/*     */ public class ColorState
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
/*     */   public boolean hasShadeModel() {
/*  72 */     return this._shadeModelSet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getShadeModel() {
/*  80 */     return this._shadeModel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setShadeModel(int shadeModel) {
/*  88 */     this._shadeModel = shadeModel;
/*  89 */     this._shadeModelSet = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unsetShadeModel() {
/*  96 */     this._shadeModel = _shadeModelDefault;
/*  97 */     this._shadeModelSet = false;
/*     */   }
/*     */   
/*     */   public void apply() {
/* 101 */     if (this._colorSet) {
/* 102 */       byte r = (byte)this._color.getRed();
/* 103 */       byte g = (byte)this._color.getGreen();
/* 104 */       byte b = (byte)this._color.getBlue();
/* 105 */       byte a = (byte)this._color.getAlpha();
/* 106 */       Gl.glColor4ub(r, g, b, a);
/*     */     } 
/* 108 */     if (this._shadeModelSet) {
/* 109 */       Gl.glShadeModel(this._shadeModel);
/*     */     }
/*     */   }
/*     */   
/*     */   public int getAttributeBits() {
/* 114 */     int bits = 0;
/* 115 */     if (this._colorSet)
/* 116 */       bits |= 0x1; 
/* 117 */     if (this._shadeModelSet)
/* 118 */       bits |= 0x40; 
/* 119 */     return bits;
/*     */   }
/*     */   
/* 122 */   private static Color _colorDefault = new Color(1.0F, 1.0F, 1.0F, 1.0F);
/* 123 */   private Color _color = _colorDefault;
/*     */   
/*     */   private boolean _colorSet;
/* 126 */   private static int _shadeModelDefault = 7425;
/* 127 */   private int _shadeModel = _shadeModelDefault;
/*     */   private boolean _shadeModelSet;
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/sgl/ColorState.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */