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
/*     */ public class Clips
/*     */ {
/*     */   private boolean _clipsDirty;
/*     */   private float _clipMin;
/*     */   private float _clipMax;
/*     */   private float _percMin;
/*     */   private float _percMax;
/*     */   private boolean _usePercentiles;
/*     */   private Object _f;
/*     */   
/*     */   public Clips(float[] f) {
/*  51 */     this(0.0D, 100.0D, f);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Clips(float[][] f) {
/*  60 */     this(0.0D, 100.0D, f);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Clips(float[][][] f) {
/*  69 */     this(0.0D, 100.0D, f);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Clips(Float3 f3) {
/*  78 */     this(0.0D, 100.0D, f3);
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
/*     */   public Clips(double percMin, double percMax, float[] f)
/*     */   {
/* 258 */     this._clipsDirty = true;
/*     */ 
/*     */     
/* 261 */     this._percMin = 0.0F;
/* 262 */     this._percMax = 100.0F;
/* 263 */     this._usePercentiles = true; this._percMin = (float)percMin; this._percMax = (float)percMax; this._f = f; } public Clips(double percMin, double percMax, float[][] f) { this._clipsDirty = true; this._percMin = 0.0F; this._percMax = 100.0F; this._usePercentiles = true; this._percMin = (float)percMin; this._percMax = (float)percMax; this._f = f; } public Clips(double percMin, double percMax, float[][][] f) { this._clipsDirty = true; this._percMin = 0.0F; this._percMax = 100.0F; this._usePercentiles = true; this._percMin = (float)percMin; this._percMax = (float)percMax; this._f = f; } public Clips(double percMin, double percMax, Float3 f3) { this._clipsDirty = true; this._percMin = 0.0F; this._percMax = 100.0F; this._usePercentiles = true; this._percMin = (float)percMin; this._percMax = (float)percMax; this._f = f3; }
/*     */   public void setArray(float[] f) { this._f = f; this._clipsDirty = true; }
/*     */   public void setArray(float[][] f) { this._f = f; this._clipsDirty = true; }
/*     */   public void setArray(float[][][] f) { this._f = f; this._clipsDirty = true; }
/* 267 */   public void setArray(Float3 f3) { this._f = f3; this._clipsDirty = true; } public void setClips(double clipMin, double clipMax) { Check.argument((clipMin < clipMax), "clipMin<clipMax"); if (this._clipMin != (float)clipMin || this._clipMax != (float)clipMax || this._usePercentiles) { this._usePercentiles = false; this._clipMin = (float)clipMin; this._clipMax = (float)clipMax; this._clipsDirty = false; }  } private void updateClips() { if (this._clipsDirty && this._usePercentiles)
/* 268 */     { boolean clipsComputed = false;
/*     */ 
/*     */       
/* 271 */       if (this._percMin == 0.0F && this._percMax == 100.0F) {
/* 272 */         if (this._f instanceof float[]) {
/* 273 */           float[] a = (float[])this._f;
/* 274 */           this._clipMin = ArrayMath.min(a);
/* 275 */           this._clipMax = ArrayMath.max(a);
/* 276 */           clipsComputed = true;
/* 277 */         } else if (this._f instanceof float[][]) {
/* 278 */           float[][] a = (float[][])this._f;
/* 279 */           this._clipMin = ArrayMath.min(a);
/* 280 */           this._clipMax = ArrayMath.max(a);
/* 281 */           clipsComputed = true;
/* 282 */         } else if (this._f instanceof float[][][]) {
/* 283 */           float[][][] a = (float[][][])this._f;
/* 284 */           this._clipMin = ArrayMath.min(a);
/* 285 */           this._clipMax = ArrayMath.max(a);
/* 286 */           clipsComputed = true;
/* 287 */         } else if (this._f instanceof Float3) {
/* 288 */           Float3 f3 = (Float3)this._f;
/* 289 */           int n1 = f3.getN1();
/* 290 */           int n2 = f3.getN2();
/* 291 */           int n3 = f3.getN3();
/* 292 */           float[][] a = new float[n2][n1];
/* 293 */           for (int i3 = 0; i3 < n3; i3++) {
/* 294 */             f3.get12(n1, n2, 0, 0, i3, a);
/* 295 */             this._clipMin = ArrayMath.min(this._clipMin, ArrayMath.min(a));
/* 296 */             this._clipMax = ArrayMath.max(this._clipMax, ArrayMath.max(a));
/*     */           } 
/* 298 */           clipsComputed = true;
/*     */         }
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 304 */         float[] a = null;
/* 305 */         if (this._f instanceof float[]) {
/* 306 */           a = ArrayMath.copy((float[])this._f);
/* 307 */         } else if (this._f instanceof float[][]) {
/* 308 */           a = ArrayMath.flatten((float[][])this._f);
/* 309 */         } else if (this._f instanceof float[][][]) {
/* 310 */           a = ArrayMath.flatten((float[][][])this._f);
/* 311 */         } else if (this._f instanceof Float3) {
/* 312 */           Float3 f3 = (Float3)this._f;
/* 313 */           int n1 = f3.getN1();
/* 314 */           int n2 = f3.getN2();
/* 315 */           int n3 = f3.getN3();
/* 316 */           a = new float[n1 * n2 * n3];
/* 317 */           f3.get123(n1, n2, n3, 0, 0, 0, a);
/*     */         } 
/* 319 */         if (a != null) {
/* 320 */           int n = a.length;
/* 321 */           int kmin = (int)ArrayMath.rint(this._percMin * 0.01D * (n - 1));
/* 322 */           if (kmin <= 0) {
/* 323 */             this._clipMin = ArrayMath.min(a);
/*     */           } else {
/* 325 */             ArrayMath.quickPartialSort(kmin, a);
/* 326 */             this._clipMin = a[kmin];
/*     */           } 
/* 328 */           int kmax = (int)ArrayMath.rint(this._percMax * 0.01D * (n - 1));
/* 329 */           if (kmax >= n - 1) {
/* 330 */             this._clipMax = ArrayMath.max(a);
/*     */           } else {
/* 332 */             ArrayMath.quickPartialSort(kmax, a);
/* 333 */             this._clipMax = a[kmax];
/*     */           } 
/* 335 */           clipsComputed = true;
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 340 */       if (clipsComputed)
/* 341 */       { makeClipsValid();
/* 342 */         this._clipsDirty = false; }  }  }
/*     */   public float getClipMin() { updateClips(); return this._clipMin; }
/*     */   public float getClipMax() { updateClips(); return this._clipMax; }
/*     */   public void setPercentiles(double percMin, double percMax) { Check.argument((0.0D <= percMin), "0<=percMin"); Check.argument((percMin < percMax), "percMin<percMax"); Check.argument((percMax <= 100.0D), "percMax<=100"); if (this._percMin != (float)percMin || this._percMax != (float)percMax || !this._usePercentiles) { this._usePercentiles = true; this._percMin = (float)percMin; this._percMax = (float)percMax; this._clipsDirty = true; }  }
/*     */   public float getPercentileMin() { return this._percMin; }
/* 347 */   public float getPercentileMax() { return this._percMax; } private void makeClipsValid() { if (this._clipMin >= this._clipMax) {
/* 348 */       double clipAvg = 0.5D * (this._clipMin + this._clipMax);
/* 349 */       double tiny = ArrayMath.max(1.0D, Math.ulp(1.0F) * ArrayMath.abs(clipAvg));
/* 350 */       this._clipMin = (float)(this._clipMin - tiny);
/* 351 */       this._clipMax = (float)(this._clipMax + tiny);
/*     */     }  }
/*     */ 
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/util/Clips.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */