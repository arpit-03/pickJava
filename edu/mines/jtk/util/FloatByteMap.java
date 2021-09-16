/*     */ package edu.mines.jtk.util;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FloatByteMap
/*     */ {
/*     */   private Clips _clips;
/*     */   private float _fmin;
/*     */   private float _fmax;
/*     */   private float _flower;
/*     */   private float _fupper;
/*     */   private float _fscale;
/*     */   private boolean _dirty;
/*     */   
/*     */   public FloatByteMap(float[] f) {
/* 265 */     this._dirty = true; this._clips = new Clips(f); } public FloatByteMap(float[][] f) { this._dirty = true; this._clips = new Clips(f); } public FloatByteMap(float[][][] f) { this._dirty = true; this._clips = new Clips(f); } public FloatByteMap(Float3 f3) { this._dirty = true; this._clips = new Clips(f3); } public FloatByteMap(double percMin, double percMax, float[] f) { this._dirty = true; this._clips = new Clips(percMin, percMax, f); } public FloatByteMap(double percMin, double percMax, float[][] f) { this._dirty = true; this._clips = new Clips(percMin, percMax, f); } public FloatByteMap(double percMin, double percMax, float[][][] f) { this._dirty = true; this._clips = new Clips(percMin, percMax, f); } public FloatByteMap(double percMin, double percMax, Float3 f3) { this._dirty = true; this._clips = new Clips(percMin, percMax, f3); }
/*     */   public int getByte(float f) { if (this._dirty) update();  if (f < this._flower) f = this._flower;  if (f > this._fupper) f = this._fupper;  return (int)((f - this._flower) * this._fscale); }
/*     */   public void getBytes(float[] f, byte[] b) { getBytes(f, b, 0); }
/*     */   public void getBytes(float[][] f, byte[][] b) { int n = f.length; for (int i = 0; i < n; i++) getBytes(f[i], b[i]);  }
/*     */   public void getBytes(float[][] f, byte[] b) { int n1 = (f[0]).length; int n2 = f.length; for (int i2 = 0; i2 < n2; i2++) getBytes(f[i2], b, i2 * n1);  }
/*     */   public void getBytes(float[][][] f, byte[][][] b) { int n = f.length; for (int i = 0; i < n; i++) getBytes(f[i], b[i]);  }
/*     */   public void getBytes(float[][][] f, byte[] b) { int n1 = (f[0][0]).length; int n2 = (f[0]).length; int n3 = f.length; for (int i3 = 0; i3 < n3; i3++) { for (int i2 = 0; i2 < n2; i2++)
/*     */         getBytes(f[i3][i2], b, i2 * n1 + i3 * n1 * n2);  }  }
/* 273 */   public void getBytes(Float3 f3, byte[][][] b) { int n1 = f3.getN1(); int n2 = f3.getN2(); int n3 = f3.getN3(); float[][] fi3 = new float[n2][n1]; for (int i3 = 0; i3 < n3; i3++) { f3.get12(n1, n2, 0, 0, i3, fi3); getBytes(fi3, b[i3]); }  } private void update() { if (this._dirty)
/* 274 */     { this._fmin = this._clips.getClipMin();
/* 275 */       this._fmax = this._clips.getClipMax();
/* 276 */       this._fscale = 256.0F / (this._fmax - this._fmin);
/* 277 */       this._flower = this._fmin;
/* 278 */       this._fupper = this._flower + 255.5F / this._fscale;
/* 279 */       this._dirty = false; }  }
/*     */   public void getBytes(Float3 f3, byte[] b) { int n1 = f3.getN1(); int n2 = f3.getN2(); int n3 = f3.getN3(); float[][] fi3 = new float[n2][n1]; for (int i3 = 0; i3 < n3; i3++) { f3.get12(n1, n2, 0, 0, i3, fi3); for (int i2 = 0; i2 < n2; i2++) getBytes(fi3[i2], b, i2 * n1 + i3 * n1 * n2);  }  }
/*     */   public void setClips(double clipMin, double clipMax) { this._clips.setClips(clipMin, clipMax); }
/*     */   public float getClipMin() { return this._clips.getClipMin(); }
/*     */   public float getClipMax() { return this._clips.getClipMax(); }
/* 284 */   public void setPercentiles(double percMin, double percMax) { this._clips.setPercentiles(percMin, percMax); } public float getPercentileMin() { return this._clips.getPercentileMin(); } public float getPercentileMax() { return this._clips.getPercentileMax(); } private void getBytes(float[] f, byte[] b, int j) { if (this._dirty)
/* 285 */       update(); 
/* 286 */     int n = f.length;
/* 287 */     for (int i = 0; i < n; i++, j++) {
/* 288 */       float fi = f[i];
/* 289 */       if (fi < this._flower) fi = this._flower; 
/* 290 */       if (fi > this._fupper) fi = this._fupper; 
/* 291 */       b[j] = (byte)(int)((fi - this._flower) * this._fscale);
/*     */     }  }
/*     */ 
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/util/FloatByteMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */