/*     */ package edu.mines.jtk.awt;
/*     */ 
/*     */ import edu.mines.jtk.util.Check;
/*     */ import java.awt.Color;
/*     */ import java.awt.image.IndexColorModel;
/*     */ import javax.swing.event.EventListenerList;
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
/*     */ public class ColorMap
/*     */ {
/*  48 */   public static final IndexColorModel GRAY = getGray();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  53 */   public static final IndexColorModel JET = getJet();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  58 */   public static final IndexColorModel GMT_JET = getGmtJet();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  63 */   public static final IndexColorModel HUE = getHue();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  68 */   public static final IndexColorModel HUE_RED_TO_BLUE = getHueRedToBlue();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  73 */   public static final IndexColorModel HUE_BLUE_TO_RED = getHueBlueToRed();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  78 */   public static final IndexColorModel PRISM = getPrism();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  83 */   public static final IndexColorModel RED_WHITE_BLUE = getRedWhiteBlue();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  88 */   public static final IndexColorModel BLUE_WHITE_RED = getBlueWhiteRed();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  93 */   public static final IndexColorModel GRAY_YELLOW_RED = getGrayYellowRed();
/*     */   private double _vmin;
/*     */   private double _vmax;
/*     */   private IndexColorModel _colorModel;
/*     */   private Color[] _colors;
/*     */   private EventListenerList _colorMapListeners;
/*     */   
/*     */   public ColorMap(IndexColorModel colorModel) {
/* 101 */     this(0.0D, 1.0D, colorModel);
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
/*     */   public ColorMap(double vmin, double vmax, IndexColorModel colorModel) {
/* 749 */     this._vmin = 0.0D;
/* 750 */     this._vmax = 1.0D;
/*     */     
/* 752 */     this._colors = new Color[256];
/* 753 */     this._colorMapListeners = new EventListenerList(); Check.argument(colorModel.isValid(0), "0 is valid for color model"); Check.argument(colorModel.isValid(255), "255 is valid for color model"); this._vmin = vmin; this._vmax = vmax; this._colorModel = colorModel; cacheColors();
/*     */   }
/*     */   public ColorMap(double vmin, double vmax, Color[] c) { this(vmin, vmax, getReds(c), getGreens(c), getBlues(c)); }
/* 756 */   public ColorMap(double vmin, double vmax, byte[] r, byte[] g, byte[] b) { this(vmin, vmax, new IndexColorModel(8, 256, r, g, b)); } public ColorMap(double vmin, double vmax, float[] r, float[] g, float[] b) { this(vmin, vmax, getBytes(r), getBytes(g), getBytes(b)); } public ColorMap(Color c) { this(0.0D, 1.0D, c); } public ColorMap(double vmin, double vmax, Color c) { this(vmin, vmax, makeSolidColors(c)); } public double getMinValue() { return this._vmin; } public double getMaxValue() { return this._vmax; } public IndexColorModel getColorModel() { return this._colorModel; } public Color getColor(double v) { return this._colors[getIndex(v)]; } public int getIndex(double v) { v = Math.max(this._vmin, Math.min(this._vmax, v)); double s = (256.0D - Math.ulp(256.0D)) / (this._vmax - this._vmin); return (int)((v - this._vmin) * s); } public float[] getRgbFloats(float[] v) { int nv = v.length; float[] rgb = new float[3 * nv]; float scale = 0.003921569F; for (int i = 0, iv = 0; iv < nv; iv++) { Color c = this._colors[getIndex(v[iv])]; rgb[i++] = scale * c.getRed(); rgb[i++] = scale * c.getGreen(); rgb[i++] = scale * c.getBlue(); }  return rgb; } public float[] getHslFloats(float[] v) { float[] rgb = getRgbFloats(v); int nv = v.length; float[] hsl = new float[3 * nv]; float[] value = new float[3]; for (int i = 0, iv = 0; iv < nv; iv++) { value = rgbToHsl(rgb[i + 0], rgb[i + 1], rgb[i + 2]); hsl[i++] = value[0]; hsl[i++] = value[1]; hsl[i++] = value[2]; }  return hsl; } public float[] getCieLabFloats(float[] v) { int nv = v.length; float[] rgb = getRgbFloats(v); float[] lab = new float[3]; float[] cielab = new float[3 * nv]; float Xn = 0.95047F; float Yn = 1.0F; float Zn = 1.08883F; for (int i = 0, j = 0, iv = 0; iv < nv; iv++) { float r = rgb[j++], g = rgb[j++], b = rgb[j++]; lab = rgbToCieLab(r, g, b); cielab[i++] = lab[0]; cielab[i++] = lab[1]; cielab[i++] = lab[2]; }  return cielab; } public static float[] rgbToHsl(float r, float g, float b) { float h, s, hsl[] = new float[3]; float min = Math.min(Math.min(r, g), b); float max = Math.max(Math.max(r, g), b); float l = (max + min) / 2.0F; if (max == min) { h = s = 0.0F; } else { float diff = max - min; s = (l > 0.5F) ? (diff / (2.0F - max - min)) : (diff / (max + min)); if (max == r) { h = (g - b) / diff + ((g < b) ? 6.0F : 0.0F); } else if (max == g) { h = (b - r) / diff + 2.0F; } else { h = (r - g) / diff + 4.0F; }  h /= 6.0F; }  hsl[0] = h * 360.0F; hsl[1] = s; hsl[2] = l; return hsl; } public static float[] hslToRgb(float h, float s, float l) { float r = 0.0F, g = 0.0F, b = 0.0F; float c = (1.0F - Math.abs(2.0F * l - 1.0F)) * s; float x = c * (1.0F - Math.abs(h / 60.0F % 2.0F - 1.0F)); float m = l - c / 2.0F; if (h >= 0.0F && h < 60.0F) { r = c; g = x; b = 0.0F; } else if (h >= 60.0F && h < 120.0F) { r = x; g = c; b = 0.0F; } else if (h >= 120.0F && h < 180.0F) { r = 0.0F; g = c; b = x; } else if (h >= 180.0F && h < 240.0F) { r = 0.0F; g = x; b = c; } else if (h >= 240.0F && h < 300.0F) { r = x; g = 0.0F; b = c; } else { r = c; g = 0.0F; b = x; }  r += m; g += m; b += m; r = Math.min(1.0F, Math.max(0.0F, r)); g = Math.min(1.0F, Math.max(0.0F, g)); b = Math.min(1.0F, Math.max(0.0F, b)); return new float[] { r, g, b }; } public static float[] rgbToCieLab(float[] rgb) { float[] xyz = rgbToCieXyz(rgb); float Xn = 95.047F; float Yn = 100.0F; float Zn = 108.883F; xyz[0] = xyz[0] / Xn; xyz[1] = xyz[1] / Yn; xyz[2] = xyz[2] / Zn; return cieXyzToCieLab(xyz); } public static float[] rgbToCieLab(float r, float g, float b) { return rgbToCieLab(new float[] { r, g, b }); } public static float[] cieLabToRgb(float[] lab) { float[] xyz = cieLabToCieXyz(lab); float Xn = 95.047F; float Yn = 100.0F; float Zn = 108.883F; xyz[0] = xyz[0] * Xn; xyz[1] = xyz[1] * Yn; xyz[2] = xyz[2] * Zn; float[] rgb = cieXyzToRgb(xyz); rgb[0] = Math.min(1.0F, Math.max(0.0F, rgb[0])); rgb[1] = Math.min(1.0F, Math.max(0.0F, rgb[1])); rgb[2] = Math.min(1.0F, Math.max(0.0F, rgb[2])); return rgb; } public static float[] cieLabToRgb(float Ls, float as, float bs) { return cieLabToRgb(new float[] { Ls, as, bs }); } public void setValueRange(double vmin, double vmax) { this._vmin = vmin; this._vmax = vmax; fireColorMapChanged(); } public void setColorModel(IndexColorModel colorModel) { this._colorModel = colorModel; cacheColors(); fireColorMapChanged(); } public void setColorModel(Color c) { this._colorModel = makeSolidColors(c); cacheColors(); fireColorMapChanged(); } private void fireColorMapChanged() { Object[] listeners = this._colorMapListeners.getListenerList();
/* 757 */     for (int i = listeners.length - 2; i >= 0; i -= 2)
/* 758 */     { ColorMapListener cml = (ColorMapListener)listeners[i + 1];
/* 759 */       cml.colorMapChanged(this); }  }
/*     */   public void addListener(ColorMapListener cml) { this._colorMapListeners.add(ColorMapListener.class, cml); cml.colorMapChanged(this); }
/*     */   public void removeListener(ColorMapListener cml) { this._colorMapListeners.remove(ColorMapListener.class, cml); }
/*     */   public static IndexColorModel getGray() { return getGray(0.0D, 1.0D); }
/*     */   public static IndexColorModel getGray(double g0, double g255) { return getGray(g0, g255, 1.0D); }
/* 764 */   public static IndexColorModel getGray(double g0, double g255, double alpha) { float a = (float)alpha; Color[] c = new Color[256]; for (int i = 0; i < 256; i++) { float g = (float)(g0 + i * (g255 - g0) / 255.0D); c[i] = new Color(g, g, g, a); }  return makeIndexColorModel(c); } public static IndexColorModel getJet() { return getJet(1.0D); } public static IndexColorModel getJet(double alpha) { return makeIndexColorModel(getJetColors(alpha)); } public static IndexColorModel getGmtJet() { return getGmtJet(1.0D); } public static IndexColorModel getGmtJet(double alpha) { return makeIndexColorModel(getGmtJetColors(alpha)); } public static IndexColorModel getPrism() { return getHue(0.0D, 8.0D); } public static IndexColorModel getHue() { return getHue(0.0D, 0.67D); } public static IndexColorModel getHueRedToBlue() { return getHue(0.0D, 0.67D); } public static IndexColorModel getHueBlueToRed() { return getHue(0.67D, 0.0D); } public static IndexColorModel getHue(double h0, double h255) { return getHue(h0, h255, 1.0D); } public static IndexColorModel getHue(double h0, double h255, double alpha) { Color[] c = new Color[256]; int a = (int)(0.5D + Math.max(0.0D, Math.min(1.0D, alpha)) * 255.0D); for (int i = 0; i < 256; i++) { float h = (float)(h0 + i * (h255 - h0) / 255.0D); Color rgb = Color.getHSBColor(h, 1.0F, 1.0F); c[i] = new Color(rgb.getRed(), rgb.getGreen(), rgb.getBlue(), a); }  return makeIndexColorModel(c); } public static IndexColorModel getRedWhiteBlue() { Color[] c = new Color[256]; for (int i = 0; i < 256; i++) { float x = i / 255.0F; if (x < 0.5F) { float a = x / 0.5F; c[i] = new Color(1.0F, a, a); } else { float a = (x - 0.5F) / 0.5F; c[i] = new Color(1.0F - a, 1.0F - a, 1.0F); }  }  return makeIndexColorModel(c); } public static IndexColorModel getBlueWhiteRed() { Color[] c = new Color[256]; for (int i = 0; i < 256; i++) { float x = i / 255.0F; if (x < 0.5F) { float a = x / 0.5F; c[i] = new Color(a, a, 1.0F); } else { float a = (x - 0.5F) / 0.5F; c[i] = new Color(1.0F, 1.0F - a, 1.0F - a); }  }  return makeIndexColorModel(c); } public static IndexColorModel getGrayYellowRed() { Color[] c = new Color[256]; for (int i = 0; i < 256; i++) { float x = i / 255.0F; if (x < 0.5F) { float y = 2.0F * x; c[i] = new Color(y, y, y); } else { float g = (x < 0.67F) ? 1.0F : (3.0F - 3.0F * x); float b = 2.0F - 2.0F * x; c[i] = new Color(1.0F, g, b); }  }  return makeIndexColorModel(c); } public static IndexColorModel makeIndexColorModel(Color[] c) { if (hasAlpha(c)) return new IndexColorModel(8, 256, getReds(c), getGreens(c), getBlues(c), getAlphas(c));  return new IndexColorModel(8, 256, getReds(c), getGreens(c), getBlues(c)); } public static IndexColorModel makeSolidColors(Color c) { Color[] colors = new Color[256]; for (int i = 0; i < 256; i++) colors[i] = c;  return makeIndexColorModel(colors); } public static IndexColorModel setAlpha(IndexColorModel icm, double alpha) { int bits = icm.getPixelSize(); int size = icm.getMapSize(); byte[] r = new byte[size]; byte[] g = new byte[size]; byte[] b = new byte[size]; byte[] a = new byte[size]; icm.getReds(r); icm.getGreens(g); icm.getBlues(b); byte ia = (byte)(int)(255.0D * alpha + 0.5D); for (int i = 0; i < size; i++) a[i] = ia;  return new IndexColorModel(bits, size, r, g, b, a); } public static IndexColorModel setAlpha(IndexColorModel icm, float[] alpha) { int bits = icm.getPixelSize(); int size = icm.getMapSize(); byte[] r = new byte[size]; byte[] g = new byte[size]; byte[] b = new byte[size]; byte[] a = new byte[size]; icm.getReds(r); icm.getGreens(g); icm.getBlues(b); int n = Math.min(size, alpha.length); for (int i = 0; i < n; i++) a[i] = (byte)(int)(255.0F * alpha[i] + 0.5F);  return new IndexColorModel(bits, size, r, g, b, a); } private void cacheColors() { for (int index = 0; index < 256; index++)
/* 765 */       this._colors[index] = new Color(this._colorModel.getRGB(index));  }
/*     */ 
/*     */   
/*     */   private static byte[] getReds(Color[] color) {
/* 769 */     int n = color.length;
/* 770 */     byte[] r = new byte[n];
/* 771 */     for (int i = 0; i < n; i++)
/* 772 */       r[i] = (byte)color[i].getRed(); 
/* 773 */     return r;
/*     */   }
/*     */   
/*     */   private static byte[] getGreens(Color[] color) {
/* 777 */     int n = color.length;
/* 778 */     byte[] g = new byte[n];
/* 779 */     for (int i = 0; i < n; i++)
/* 780 */       g[i] = (byte)color[i].getGreen(); 
/* 781 */     return g;
/*     */   }
/*     */   
/*     */   private static byte[] getBlues(Color[] color) {
/* 785 */     int n = color.length;
/* 786 */     byte[] b = new byte[n];
/* 787 */     for (int i = 0; i < n; i++)
/* 788 */       b[i] = (byte)color[i].getBlue(); 
/* 789 */     return b;
/*     */   }
/*     */   
/*     */   private static byte[] getAlphas(Color[] color) {
/* 793 */     int n = color.length;
/* 794 */     byte[] b = new byte[n];
/* 795 */     for (int i = 0; i < n; i++)
/* 796 */       b[i] = (byte)color[i].getAlpha(); 
/* 797 */     return b;
/*     */   }
/*     */   
/*     */   private static boolean hasAlpha(Color[] color) {
/* 801 */     int n = color.length;
/* 802 */     for (int i = 0; i < n; i++) {
/* 803 */       if (color[i].getAlpha() != 255)
/* 804 */         return true; 
/* 805 */     }  return false;
/*     */   }
/*     */   
/*     */   private static byte[] getBytes(float[] f) {
/* 809 */     int n = f.length;
/* 810 */     byte[] b = new byte[n];
/* 811 */     for (int i = 0; i < n; i++)
/* 812 */       b[i] = (byte)(int)(f[i] * 255.0F + 0.5F); 
/* 813 */     return b;
/*     */   }
/*     */   
/*     */   private static Color[] addAlpha(Color[] color, double alpha) {
/* 817 */     int n = color.length;
/* 818 */     float a = (float)alpha;
/* 819 */     Color[] c = new Color[n];
/* 820 */     for (int i = 0; i < n; i++) {
/* 821 */       float r = color[i].getRed() / 255.0F;
/* 822 */       float g = color[i].getGreen() / 255.0F;
/* 823 */       float b = color[i].getBlue() / 255.0F;
/* 824 */       c[i] = new Color(r, g, b, a);
/*     */     } 
/* 826 */     return c;
/*     */   }
/*     */   
/*     */   private static Color[] addAlpha(Color[] color, float[] alpha) {
/* 830 */     int n = color.length;
/* 831 */     Color[] c = new Color[n];
/* 832 */     for (int i = 0; i < n; i++) {
/* 833 */       float r = color[i].getRed() / 255.0F;
/* 834 */       float g = color[i].getGreen() / 255.0F;
/* 835 */       float b = color[i].getBlue() / 255.0F;
/* 836 */       float a = alpha[i];
/* 837 */       c[i] = new Color(r, g, b, a);
/*     */     } 
/* 839 */     return c;
/*     */   }
/*     */   
/*     */   private static Color[] getJetColors(double alpha) {
/* 843 */     float a = (float)alpha;
/* 844 */     Color[] c = new Color[256];
/* 845 */     for (int i = 0; i < 256; i++) {
/* 846 */       float x = i / 255.0F;
/* 847 */       if (x < 0.125F) {
/* 848 */         float y = x / 0.125F;
/* 849 */         c[i] = new Color(0.0F, 0.0F, 0.5F + 0.5F * y, a);
/* 850 */       } else if (x < 0.375F) {
/* 851 */         float y = (x - 0.125F) / 0.25F;
/* 852 */         c[i] = new Color(0.0F, y, 1.0F, a);
/* 853 */       } else if (x < 0.625F) {
/* 854 */         float y = (x - 0.375F) / 0.25F;
/* 855 */         c[i] = new Color(y, 1.0F, 1.0F - y, a);
/* 856 */       } else if (x < 0.875F) {
/* 857 */         float y = (x - 0.625F) / 0.25F;
/* 858 */         c[i] = new Color(1.0F, 1.0F - y, 0.0F, a);
/*     */       } else {
/* 860 */         float y = (x - 0.875F) / 0.125F;
/* 861 */         c[i] = new Color(1.0F - 0.5F * y, 0.0F, 0.0F, a);
/*     */       } 
/*     */     } 
/* 864 */     return c;
/*     */   }
/*     */   
/*     */   private static Color[] getGmtJetColors(double alpha) {
/* 868 */     float a = (float)alpha;
/* 869 */     Color[] c = new Color[256];
/* 870 */     for (int i = 0; i < 256; i++) {
/* 871 */       float x = i / 255.0F;
/* 872 */       if (x < 0.125F) {
/* 873 */         float y = x / 0.125F;
/* 874 */         c[i] = new Color(0.0F, 0.0F, 0.5F + 0.5F * y, a);
/* 875 */       } else if (x < 0.375F) {
/* 876 */         float y = (x - 0.125F) / 0.25F;
/* 877 */         c[i] = new Color(0.0F, y, 1.0F, a);
/* 878 */       } else if (x < 0.625F) {
/* 879 */         float y = (x - 0.375F) / 0.25F;
/* 880 */         c[i] = new Color(1.0F, 1.0F, 1.0F - 0.5F * y, a);
/* 881 */       } else if (x < 0.875F) {
/* 882 */         float y = (x - 0.625F) / 0.25F;
/* 883 */         c[i] = new Color(1.0F, 1.0F - y, 0.0F, a);
/*     */       } else {
/* 885 */         float y = (x - 0.875F) / 0.125F;
/* 886 */         c[i] = new Color(1.0F - 0.5F * y, 0.0F, 0.0F, a);
/*     */       } 
/*     */     } 
/* 889 */     return c;
/*     */   }
/*     */   
/*     */   private static float[] cieXyzToCieLab(float[] xyz) {
/* 893 */     float c = 0.008856F;
/*     */     
/* 895 */     for (int i = 0; i < 3; i++) {
/* 896 */       xyz[i] = (xyz[i] > c) ? (float)Math.pow(xyz[i], 0.3333333432674408D) : (7.787F * xyz[i] + 0.13793103F);
/*     */     }
/*     */     
/* 899 */     float Ls = 116.0F * xyz[1] - 16.0F;
/* 900 */     float as = 500.0F * (xyz[0] - xyz[1]);
/* 901 */     float bs = 200.0F * (xyz[1] - xyz[2]);
/*     */     
/* 903 */     return new float[] { Ls, as, bs };
/*     */   }
/*     */   
/*     */   private static float[] cieLabToCieXyz(float[] lab) {
/* 907 */     float c = 0.008856F;
/*     */     
/* 909 */     float y = (lab[0] + 16.0F) / 116.0F;
/* 910 */     float x = lab[1] / 500.0F + y;
/* 911 */     float z = y - lab[2] / 200.0F;
/*     */     
/* 913 */     float[] xyz = { x, y, z };
/*     */     
/* 915 */     for (int i = 0; i < 3; i++) {
/* 916 */       xyz[i] = (Math.pow(xyz[i], 3.0D) > c) ? 
/* 917 */         (float)Math.pow(xyz[i], 3.0D) : ((xyz[i] - 0.13793103F) / 7.787F);
/*     */     }
/* 919 */     return xyz;
/*     */   }
/*     */   
/*     */   private static float[] rgbToCieXyz(float[] rgb) {
/* 923 */     float[] xyz = new float[3];
/* 924 */     float c = 0.04045F;
/*     */     
/* 926 */     for (int i = 0; i < 3; i++) {
/* 927 */       rgb[i] = (rgb[i] > c) ? 
/* 928 */         (float)Math.pow(((rgb[i] + 0.055F) / 1.055F), 2.4000000953674316D) : (rgb[i] / 12.92F);
/* 929 */       rgb[i] = rgb[i] * 100.0F;
/*     */     } 
/*     */     
/* 932 */     xyz[0] = rgb[0] * 0.4124F + rgb[1] * 0.3576F + rgb[2] * 0.1805F;
/* 933 */     xyz[1] = rgb[0] * 0.2126F + rgb[1] * 0.7152F + rgb[2] * 0.0722F;
/* 934 */     xyz[2] = rgb[0] * 0.0193F + rgb[1] * 0.1192F + rgb[2] * 0.9505F;
/*     */     
/* 936 */     return xyz;
/*     */   }
/*     */   
/*     */   private static float[] cieXyzToRgb(float[] xyz) {
/* 940 */     xyz[0] = xyz[0] / 100.0F; xyz[1] = xyz[1] / 100.0F; xyz[2] = xyz[2] / 100.0F;
/* 941 */     float[] rgb = new float[3];
/* 942 */     float c = 0.0031308F;
/*     */     
/* 944 */     rgb[0] = xyz[0] * 3.2406F + xyz[1] * -1.5372F + xyz[2] * -0.4986F;
/* 945 */     rgb[1] = xyz[0] * -0.9689F + xyz[1] * 1.8758F + xyz[2] * 0.0415F;
/* 946 */     rgb[2] = xyz[0] * 0.0557F + xyz[1] * -0.204F + xyz[2] * 1.057F;
/*     */     
/* 948 */     for (int i = 0; i < 3; i++) {
/* 949 */       rgb[i] = (rgb[i] > c) ? (1.055F * 
/* 950 */         (float)Math.pow(rgb[i], 0.4166666567325592D) - 0.055F) : (rgb[i] * 12.92F);
/*     */     }
/*     */     
/* 953 */     return rgb;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/awt/ColorMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */