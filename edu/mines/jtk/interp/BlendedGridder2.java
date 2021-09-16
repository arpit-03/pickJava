/*     */ package edu.mines.jtk.interp;
/*     */ 
/*     */ import edu.mines.jtk.dsp.LocalDiffusionKernel;
/*     */ import edu.mines.jtk.dsp.LocalSmoothingFilter;
/*     */ import edu.mines.jtk.dsp.Sampling;
/*     */ import edu.mines.jtk.dsp.Tensors2;
/*     */ import edu.mines.jtk.util.ArrayMath;
/*     */ import edu.mines.jtk.util.Check;
/*     */ import edu.mines.jtk.util.Stopwatch;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlendedGridder2
/*     */   implements Gridder2
/*     */ {
/*     */   private boolean _tmx;
/*     */   private double _tms;
/*     */   private Tensors2 _tensors;
/*     */   private float[] _f;
/*     */   private float[] _x1;
/*     */   private float[] _x2;
/*     */   private boolean _blending;
/*     */   private float _tmax;
/*     */   private float _c;
/*     */   private LocalDiffusionKernel _ldk;
/*     */   
/*     */   public BlendedGridder2() {
/*  60 */     this(null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlendedGridder2(float[] f, float[] x1, float[] x2) {
/*  71 */     this(null);
/*  72 */     setScattered(f, x1, x2);
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
/*     */   public BlendedGridder2(Tensors2 tensors)
/*     */   {
/* 333 */     this._blending = true;
/* 334 */     this._tmax = Float.MAX_VALUE;
/* 335 */     this._c = 0.5F;
/* 336 */     this._ldk = new LocalDiffusionKernel(LocalDiffusionKernel.Stencil.D22); setTensors(tensors); } public BlendedGridder2(Tensors2 tensors, float[] f, float[] x1, float[] x2) { this._blending = true; this._tmax = Float.MAX_VALUE; this._c = 0.5F; this._ldk = new LocalDiffusionKernel(LocalDiffusionKernel.Stencil.D22); setTensors(tensors); setScattered(f, x1, x2); }
/*     */   public void setTensors(Tensors2 tensors) { this._tensors = tensors; if (this._tensors == null) this._tensors = new Tensors2() {
/*     */           public void getTensor(int i1, int i2, float[] d) { d[0] = 1.0F; d[1] = 0.0F; d[2] = 1.0F; }
/*     */         };  }
/* 340 */   public void setBlending(boolean blending) { this._blending = blending; } public void setBlendingKernel(LocalDiffusionKernel ldk) { this._ldk = ldk; } private void gridNearest(int nmark, float[][] t, float[][] p) { int n1 = (t[0]).length;
/* 341 */     int n2 = t.length;
/*     */ 
/*     */ 
/*     */     
/* 345 */     float[] pmark = new float[nmark];
/* 346 */     int[][] m = new int[n2][n1];
/* 347 */     int mark = 0;
/* 348 */     for (int i2 = 0; i2 < n2; i2++) {
/* 349 */       for (int i1 = 0; i1 < n1; i1++) {
/* 350 */         if (t[i2][i1] == 0.0F) {
/* 351 */           pmark[mark] = p[i2][i1];
/* 352 */           m[i2][i1] = mark;
/* 353 */           mark++;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 359 */     Stopwatch sw = new Stopwatch();
/* 360 */     if (this._tmx) {
/* 361 */       TimeMarker2X tm = new TimeMarker2X(n1, n2, this._tensors);
/*     */       
/* 363 */       sw.start();
/* 364 */       tm.apply(t, m);
/* 365 */       sw.stop();
/*     */     } else {
/* 367 */       TimeMarker2 tm = new TimeMarker2(n1, n2, this._tensors);
/*     */       
/* 369 */       sw.start();
/* 370 */       tm.apply(t, m);
/* 371 */       sw.stop();
/*     */     } 
/* 373 */     this._tms = sw.time();
/*     */ 
/*     */     
/* 376 */     adjustTimes(nmark, m, t);
/*     */ 
/*     */     
/* 379 */     for (int i = 0; i < n2; i++) {
/* 380 */       for (int i1 = 0; i1 < n1; i1++) {
/* 381 */         float ti = t[i][i1];
/* 382 */         if (ti != 0.0F)
/* 383 */           p[i][i1] = pmark[m[i][i1]]; 
/* 384 */         if (ti > this._tmax)
/* 385 */           t[i][i1] = this._tmax; 
/*     */       } 
/*     */     }  }
/*     */   public void setSmoothness(double smoothness) { this._c = 0.25F / (float)smoothness; }
/*     */   public void setTimeMax(double tmax) { this._tmax = (float)tmax; }
/*     */   public void setTimeMarkerX(boolean tmx) { this._tmx = tmx; }
/*     */   public double getTimeMarkerS() { return this._tms; } private void adjustTimes(int nmark, int[][] m, float[][] t) {
/* 392 */     int n1 = (m[0]).length;
/* 393 */     int n2 = m.length;
/* 394 */     int n1m = n1 - 1;
/* 395 */     int n2m = n2 - 1;
/* 396 */     float[] s = new float[nmark]; int i2;
/* 397 */     for (i2 = 0; i2 < n2; i2++) {
/* 398 */       int i2m = (i2 > 0) ? (i2 - 1) : i2;
/* 399 */       int i2p = (i2 < n2m) ? (i2 + 1) : i2;
/* 400 */       for (int i1 = 0; i1 < n1; i1++) {
/* 401 */         int i1m = (i1 > 0) ? (i1 - 1) : i1;
/* 402 */         int i1p = (i1 < n1m) ? (i1 + 1) : i1;
/* 403 */         if (t[i2][i1] == 0.0F) {
/* 404 */           float tmax = 0.0F;
/* 405 */           tmax = ArrayMath.max(tmax, t[i2m][i1m]);
/* 406 */           tmax = ArrayMath.max(tmax, t[i2m][i1]);
/* 407 */           tmax = ArrayMath.max(tmax, t[i2m][i1p]);
/* 408 */           tmax = ArrayMath.max(tmax, t[i2][i1m]);
/* 409 */           tmax = ArrayMath.max(tmax, t[i2][i1p]);
/* 410 */           tmax = ArrayMath.max(tmax, t[i2p][i1m]);
/* 411 */           tmax = ArrayMath.max(tmax, t[i2p][i1]);
/* 412 */           tmax = ArrayMath.max(tmax, t[i2p][i1p]);
/* 413 */           s[m[i2][i1]] = tmax;
/*     */         } 
/*     */       } 
/*     */     } 
/* 417 */     for (i2 = 0; i2 < n2; i2++) {
/* 418 */       for (int i1 = 0; i1 < n1; i1++) {
/* 419 */         if (t[i2][i1] > 0.0F)
/* 420 */           t[i2][i1] = ArrayMath.max(Float.MIN_VALUE, t[i2][i1] - s[m[i2][i1]]); 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public float[][] gridNearest(float pnull, float[][] p) {
/*     */     int n1 = (p[0]).length;
/*     */     int n2 = p.length;
/*     */     int nmark = 0;
/*     */     float[][] t = new float[n2][n1];
/*     */     float tnull = -3.4028235E38F;
/*     */     for (int i2 = 0; i2 < n2; i2++) {
/*     */       for (int i1 = 0; i1 < n1; i1++) {
/*     */         if (p[i2][i1] == pnull) {
/*     */           t[i2][i1] = tnull;
/*     */         } else {
/*     */           t[i2][i1] = 0.0F;
/*     */           nmark++;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     gridNearest(nmark, t, p);
/*     */     return t;
/*     */   }
/*     */   
/*     */   public void gridNearest(float[][] t, float[][] p) {
/*     */     int n1 = (t[0]).length;
/*     */     int n2 = t.length;
/*     */     int nmark = 0;
/*     */     for (int i2 = 0; i2 < n2; i2++) {
/*     */       for (int i1 = 0; i1 < n1; i1++) {
/*     */         if (t[i2][i1] == 0.0F)
/*     */           nmark++; 
/*     */       } 
/*     */     } 
/*     */     gridNearest(nmark, t, p);
/*     */   }
/*     */   
/*     */   public void gridBlended(float[][] t, float[][] p, float[][] q) {
/*     */     int n1 = (t[0]).length;
/*     */     int n2 = t.length;
/*     */     float[][] s = ArrayMath.mul(t, t);
/*     */     if (this._ldk.getStencil() != LocalDiffusionKernel.Stencil.D21)
/*     */       for (int i = n2 - 1; i > 0; i--) {
/*     */         for (int i1 = n1 - 1; i1 > 0; i1--)
/*     */           s[i][i1] = 0.25F * (s[i][i1] + s[i][i1 - 1] + s[i - 1][i1] + s[i - 1][i1 - 1]); 
/*     */       }  
/*     */     LocalSmoothingFilter lsf = new LocalSmoothingFilter(0.01D, 10000, this._ldk);
/*     */     lsf.setPreconditioner(true);
/*     */     float pavg = ArrayMath.sum(p) / n1 / n2;
/*     */     float[][] r = ArrayMath.sub(p, pavg);
/*     */     if (this._ldk.getStencil() != LocalDiffusionKernel.Stencil.D21)
/*     */       lsf.applySmoothS(r, r); 
/*     */     lsf.apply(this._tensors, this._c, s, r, q);
/*     */     ArrayMath.add(q, pavg, q);
/*     */     for (int i2 = 0; i2 < n2; i2++) {
/*     */       for (int i1 = 0; i1 < n1; i1++) {
/*     */         if (t[i2][i1] == 0.0F)
/*     */           q[i2][i1] = p[i2][i1]; 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setScattered(float[] f, float[] x1, float[] x2) {
/*     */     this._f = f;
/*     */     this._x1 = x1;
/*     */     this._x2 = x2;
/*     */   }
/*     */   
/*     */   public float[][] grid(Sampling s1, Sampling s2) {
/*     */     Check.argument(s1.isUniform(), "s1 is uniform");
/*     */     Check.argument(s2.isUniform(), "s2 is uniform");
/*     */     Check.state((this._f != null), "scattered samples have been set");
/*     */     Check.state((this._x1 != null), "scattered samples have been set");
/*     */     Check.state((this._x2 != null), "scattered samples have been set");
/*     */     int n1 = s1.getCount();
/*     */     int n2 = s2.getCount();
/*     */     SimpleGridder2 sg = new SimpleGridder2(this._f, this._x1, this._x2);
/*     */     float pnull = -3.4028235E38F;
/*     */     float tnull = -3.4028235E38F;
/*     */     sg.setNullValue(pnull);
/*     */     float[][] p = sg.grid(s1, s2);
/*     */     float[][] t = new float[n2][n1];
/*     */     for (int i2 = 0; i2 < n2; i2++) {
/*     */       for (int i1 = 0; i1 < n1; i1++)
/*     */         t[i2][i1] = (p[i2][i1] != pnull) ? 0.0F : tnull; 
/*     */     } 
/*     */     gridNearest(t, p);
/*     */     float[][] q = p;
/*     */     if (this._blending) {
/*     */       q = new float[n2][n1];
/*     */       gridBlended(t, p, q);
/*     */     } 
/*     */     return q;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/interp/BlendedGridder2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */