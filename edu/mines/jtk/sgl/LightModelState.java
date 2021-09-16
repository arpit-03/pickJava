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
/*     */ public class LightModelState
/*     */   implements State
/*     */ {
/*     */   public boolean hasAmbient() {
/*  39 */     return this._ambientSet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color getAmbient() {
/*  47 */     return toColor(this._ambient);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAmbient(Color color) {
/*  55 */     this._ambient = toArray(color);
/*  56 */     this._ambientSet = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unsetAmbient() {
/*  63 */     this._ambient = _ambientDefault;
/*  64 */     this._ambientSet = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasColorControl() {
/*  72 */     return this._colorControlSet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getColorControl() {
/*  80 */     return this._colorControl;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setColorControl(int control) {
/*  88 */     this._colorControl = control;
/*  89 */     this._colorControlSet = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unsetColorControl() {
/*  96 */     this._colorControl = _colorControlDefault;
/*  97 */     this._colorControlSet = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasLocalViewer() {
/* 105 */     return this._localViewerSet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getLocalViewer() {
/* 113 */     return this._localViewer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLocalViewer(boolean local) {
/* 121 */     this._localViewer = local;
/* 122 */     this._localViewerSet = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unsetLocalViewer() {
/* 129 */     this._localViewer = _localViewerDefault;
/* 130 */     this._localViewerSet = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasTwoSide() {
/* 138 */     return this._twoSideSet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getTwoSide() {
/* 146 */     return this._twoSide;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTwoSide(boolean local) {
/* 154 */     this._twoSide = local;
/* 155 */     this._twoSideSet = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unsetTwoSide() {
/* 162 */     this._twoSide = _twoSideDefault;
/* 163 */     this._twoSideSet = false;
/*     */   }
/*     */   
/*     */   public void apply() {
/* 167 */     if (this._ambientSet)
/* 168 */       Gl.glLightModelfv(2899, this._ambient, 0); 
/* 169 */     if (this._colorControlSet)
/* 170 */       Gl.glLightModelf(33272, this._colorControl); 
/* 171 */     if (this._localViewerSet)
/* 172 */       Gl.glLightModelf(2897, this._localViewer ? 1.0F : 0.0F); 
/* 173 */     if (this._twoSideSet)
/* 174 */       Gl.glLightModelf(2898, this._twoSide ? 1.0F : 0.0F); 
/*     */   }
/*     */   
/*     */   public int getAttributeBits() {
/* 178 */     return 2896;
/*     */   }
/*     */   
/* 181 */   private static float[] _ambientDefault = new float[] { 0.2F, 0.2F, 0.2F, 1.0F };
/* 182 */   private float[] _ambient = _ambientDefault;
/*     */   
/*     */   private boolean _ambientSet;
/* 185 */   private static int _colorControlDefault = 33273;
/* 186 */   private int _colorControl = _colorControlDefault;
/*     */   
/*     */   private boolean _colorControlSet;
/*     */   private static boolean _localViewerDefault = false;
/* 190 */   private boolean _localViewer = _localViewerDefault;
/*     */   
/*     */   private boolean _localViewerSet;
/*     */   private static boolean _twoSideDefault = false;
/* 194 */   private boolean _twoSide = _twoSideDefault;
/*     */   private boolean _twoSideSet;
/*     */   
/*     */   private static float[] toArray(Color c) {
/* 198 */     float r = c.getRed() / 255.0F;
/* 199 */     float g = c.getGreen() / 255.0F;
/* 200 */     float b = c.getBlue() / 255.0F;
/* 201 */     float a = c.getAlpha() / 255.0F;
/* 202 */     return new float[] { r, g, b, a };
/*     */   }
/*     */   
/*     */   private static Color toColor(float[] a) {
/* 206 */     return new Color(a[0], a[1], a[2], a[3]);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/sgl/LightModelState.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */