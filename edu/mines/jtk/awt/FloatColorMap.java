/*     */ package edu.mines.jtk.awt;
/*     */ 
/*     */ import edu.mines.jtk.util.Check;
/*     */ import edu.mines.jtk.util.FloatByteMap;
/*     */ import java.awt.image.IndexColorModel;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FloatColorMap
/*     */   extends ColorMap
/*     */ {
/*     */   private FloatByteMap _fbmic;
/*     */   private FloatByteMap _fbmi0;
/*     */   private FloatByteMap _fbmi1;
/*     */   private FloatByteMap _fbmi2;
/*     */   private FloatByteMap _fbmi3;
/*     */   private boolean _hsb;
/*     */   private float _hue000;
/*     */   private float _hue255;
/*     */   
/*     */   public FloatColorMap(float[][] f, IndexColorModel icm) {
/*  83 */     this(new float[][][] { f }, 0, icm);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FloatColorMap(float[][][] f, int ic, IndexColorModel icm) {
/*  93 */     super(icm);
/*  94 */     Check.argument((f.length == 1 || f.length == 3 || f.length == 4), "number of arrays (color components) equals 1, 3, or 4");
/*     */ 
/*     */     
/*  97 */     int nc = f.length;
/*  98 */     FloatByteMap[] fbm = new FloatByteMap[nc];
/*  99 */     for (int jc = 0; jc < nc; jc++)
/* 100 */       fbm[jc] = new FloatByteMap(f[jc]); 
/* 101 */     this._fbmi0 = fbm[0];
/* 102 */     this._fbmi1 = (nc > 1) ? fbm[1] : fbm[0];
/* 103 */     this._fbmi2 = (nc > 1) ? fbm[2] : fbm[0];
/* 104 */     this._fbmi3 = (nc > 3) ? fbm[3] : null;
/* 105 */     this._fbmic = fbm[ic];
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
/*     */   public void setHSB(boolean hsb) {
/* 117 */     this._hsb = hsb;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHues(double hue000, double hue255) {
/* 127 */     this._hue000 = (float)hue000;
/* 128 */     this._hue255 = (float)hue255;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIndex(float f) {
/* 137 */     return this._fbmic.getByte(f);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getARGB(float f) {
/* 147 */     return getColorModel().getRGB(getIndex(f));
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
/*     */   public int getRGB(float f0, float f1, float f2) {
/* 159 */     int i0 = this._fbmi0.getByte(f0);
/* 160 */     int i1 = this._fbmi1.getByte(f1);
/* 161 */     int i2 = this._fbmi2.getByte(f2);
/* 162 */     return this._hsb ? rgbFromHsb(i0, i1, i2) : rgbFromRgb(i0, i1, i2);
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
/*     */   public int getARGB(float f0, float f1, float f2, float f3) {
/* 175 */     int i0 = this._fbmi0.getByte(f0);
/* 176 */     int i1 = this._fbmi1.getByte(f1);
/* 177 */     int i2 = this._fbmi2.getByte(f2);
/* 178 */     int i3 = (this._fbmi3 != null) ? this._fbmi3.getByte(f3) : 255;
/* 179 */     return this._hsb ? argbFromHsba(i0, i1, i2, i3) : argbFromRgba(i0, i1, i2, i3);
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
/*     */   public int getIndex(double v) {
/* 191 */     return getIndex((float)v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getMinValue() {
/* 199 */     return this._fbmic.getClipMin();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getMaxValue() {
/* 207 */     return this._fbmic.getClipMax();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setValueRange(double vmin, double vmax) {
/* 218 */     if ((float)vmin != getMinValue() || (float)vmax != getMaxValue()) {
/* 219 */       this._fbmic.setClips((float)vmin, (float)vmax);
/* 220 */       super.setValueRange(vmin, vmax);
/*     */     } 
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
/*     */   private int argbFromRgba(int r, int g, int b, int a) {
/* 240 */     return a << 24 | r << 16 | g << 8 | b;
/*     */   }
/*     */   
/*     */   private int rgbFromRgb(int r, int g, int b) {
/* 244 */     return 0xFF000000 | r << 16 | g << 8 | b;
/*     */   }
/*     */   
/*     */   private int argbFromHsba(int hu, int sa, int br, int al) {
/* 248 */     return al << 24 | rgbFromHsb(hu, sa, br);
/*     */   }
/*     */   
/*     */   private int rgbFromHsb(int hu, int sa, int br) {
/* 252 */     float scale = 0.003921569F;
/* 253 */     if (hu < 0) hu += 256; 
/* 254 */     if (sa < 0) sa += 256; 
/* 255 */     if (br < 0) br += 256; 
/* 256 */     float hue = this._hue000 + (this._hue255 - this._hue000) * hu * scale;
/* 257 */     float sat = sa * scale;
/* 258 */     float bri = br * scale;
/* 259 */     int r = 0, g = 0, b = 0;
/* 260 */     if (sat == 0.0F) {
/* 261 */       r = g = b = (int)(bri * 255.0F + 0.5F);
/*     */     } else {
/* 263 */       float h = (hue - (float)Math.floor(hue)) * 6.0F;
/* 264 */       float f = h - (float)Math.floor(h);
/* 265 */       float p = bri * (1.0F - sat);
/* 266 */       float q = bri * (1.0F - sat * f);
/* 267 */       float t = bri * (1.0F - sat * (1.0F - f));
/* 268 */       switch ((int)h) {
/*     */         case 0:
/* 270 */           r = (int)(bri * 255.0F + 0.5F);
/* 271 */           g = (int)(t * 255.0F + 0.5F);
/* 272 */           b = (int)(p * 255.0F + 0.5F);
/*     */           break;
/*     */         case 1:
/* 275 */           r = (int)(q * 255.0F + 0.5F);
/* 276 */           g = (int)(bri * 255.0F + 0.5F);
/* 277 */           b = (int)(p * 255.0F + 0.5F);
/*     */           break;
/*     */         case 2:
/* 280 */           r = (int)(p * 255.0F + 0.5F);
/* 281 */           g = (int)(bri * 255.0F + 0.5F);
/* 282 */           b = (int)(t * 255.0F + 0.5F);
/*     */           break;
/*     */         case 3:
/* 285 */           r = (int)(p * 255.0F + 0.5F);
/* 286 */           g = (int)(q * 255.0F + 0.5F);
/* 287 */           b = (int)(bri * 255.0F + 0.5F);
/*     */           break;
/*     */         case 4:
/* 290 */           r = (int)(t * 255.0F + 0.5F);
/* 291 */           g = (int)(p * 255.0F + 0.5F);
/* 292 */           b = (int)(bri * 255.0F + 0.5F);
/*     */           break;
/*     */         case 5:
/* 295 */           r = (int)(bri * 255.0F + 0.5F);
/* 296 */           g = (int)(p * 255.0F + 0.5F);
/* 297 */           b = (int)(q * 255.0F + 0.5F);
/*     */           break;
/*     */       } 
/*     */     } 
/* 301 */     return rgbFromRgb(r, g, b);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/awt/FloatColorMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */