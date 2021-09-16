/*     */ package edu.mines.jtk.interp;
/*     */ 
/*     */ import edu.mines.jtk.dsp.LocalDiffusionKernel;
/*     */ import edu.mines.jtk.dsp.LocalSmoothingFilter;
/*     */ import edu.mines.jtk.dsp.Sampling;
/*     */ import edu.mines.jtk.dsp.Tensors3;
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
/*     */ public class BlendedGridder3
/*     */   implements Gridder3
/*     */ {
/*     */   private boolean _tmx;
/*     */   private double _tms;
/*     */   private Tensors3 _tensors;
/*     */   private float[] _f;
/*     */   private float[] _x1;
/*     */   private float[] _x2;
/*     */   private float[] _x3;
/*     */   private boolean _blending;
/*     */   private float _tmax;
/*     */   private float _c;
/*     */   private LocalDiffusionKernel _ldk;
/*     */   
/*     */   public BlendedGridder3() {
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
/*     */   
/*     */   public BlendedGridder3(float[] f, float[] x1, float[] x2, float[] x3) {
/*  72 */     this(null);
/*  73 */     setScattered(f, x1, x2, x3);
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
/*     */   public BlendedGridder3(Tensors3 tensors)
/*     */   {
/* 358 */     this._blending = true;
/* 359 */     this._tmax = Float.MAX_VALUE;
/* 360 */     this._c = 0.5F;
/* 361 */     this._ldk = new LocalDiffusionKernel(LocalDiffusionKernel.Stencil.D22); setTensors(tensors); } public BlendedGridder3(Tensors3 tensors, float[] f, float[] x1, float[] x2, float[] x3) { this._blending = true; this._tmax = Float.MAX_VALUE; this._c = 0.5F; this._ldk = new LocalDiffusionKernel(LocalDiffusionKernel.Stencil.D22); setTensors(tensors); setScattered(f, x1, x2, x3); }
/*     */   public void setTensors(Tensors3 tensors) { this._tensors = tensors; if (this._tensors == null) this._tensors = new Tensors3() {
/*     */           public void getTensor(int i1, int i2, int i3, float[] d) { d[0] = 1.0F; d[1] = 0.0F; d[2] = 0.0F; d[3] = 1.0F; d[4] = 0.0F; d[5] = 1.0F; }
/*     */         };  }
/* 365 */   public void setBlending(boolean blending) { this._blending = blending; } public void setBlendingKernel(LocalDiffusionKernel ldk) { this._ldk = ldk; } private void gridNearest(int nmark, float[][][] t, float[][][] p) { int n1 = (t[0][0]).length;
/* 366 */     int n2 = (t[0]).length;
/* 367 */     int n3 = t.length;
/*     */ 
/*     */ 
/*     */     
/* 371 */     float[] pmark = new float[nmark];
/* 372 */     int[][][] m = new int[n3][n2][n1];
/* 373 */     int mark = 0;
/* 374 */     for (int i3 = 0; i3 < n3; i3++) {
/* 375 */       for (int i2 = 0; i2 < n2; i2++) {
/* 376 */         for (int i1 = 0; i1 < n1; i1++) {
/* 377 */           if (t[i3][i2][i1] == 0.0F) {
/* 378 */             pmark[mark] = p[i3][i2][i1];
/* 379 */             m[i3][i2][i1] = mark;
/* 380 */             mark++;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 387 */     Stopwatch sw = new Stopwatch();
/* 388 */     if (this._tmx) {
/* 389 */       TimeMarker3X tm = new TimeMarker3X(n1, n2, n3, this._tensors);
/* 390 */       sw.start();
/* 391 */       tm.apply(t, m);
/* 392 */       sw.stop();
/*     */     } else {
/* 394 */       TimeMarker3 tm = new TimeMarker3(n1, n2, n3, this._tensors);
/* 395 */       sw.start();
/* 396 */       tm.apply(t, m);
/* 397 */       sw.stop();
/*     */     } 
/* 399 */     this._tms = sw.time();
/*     */ 
/*     */     
/* 402 */     adjustTimes(nmark, m, t);
/*     */ 
/*     */ 
/*     */     
/* 406 */     for (int i = 0; i < n3; i++) {
/* 407 */       for (int i2 = 0; i2 < n2; i2++) {
/* 408 */         for (int i1 = 0; i1 < n1; i1++) {
/* 409 */           float ti = t[i][i2][i1];
/* 410 */           if (ti != 0.0F)
/* 411 */             p[i][i2][i1] = pmark[m[i][i2][i1]]; 
/* 412 */           if (ti > this._tmax)
/* 413 */             t[i][i2][i1] = this._tmax; 
/*     */         } 
/*     */       } 
/*     */     }  }
/*     */   public void setSmoothness(double smoothness) { this._c = 0.25F / (float)smoothness; }
/*     */   public void setTimeMax(double tmax) { this._tmax = (float)tmax; }
/*     */   public void setTimeMarkerX(boolean tmx) { this._tmx = tmx; }
/*     */   public double getTimeMarkerS() { return this._tms; } private void adjustTimes(int nmark, int[][][] m, float[][][] t) {
/* 421 */     int n1 = (m[0][0]).length;
/* 422 */     int n2 = (m[0]).length;
/* 423 */     int n3 = m.length;
/* 424 */     int n1m = n1 - 1;
/* 425 */     int n2m = n2 - 1;
/* 426 */     int n3m = n3 - 1;
/* 427 */     float[] s = new float[nmark]; int i3;
/* 428 */     for (i3 = 0; i3 < n3; i3++) {
/* 429 */       int i3m = (i3 > 0) ? (i3 - 1) : i3;
/* 430 */       int i3p = (i3 < n3m) ? (i3 + 1) : i3;
/* 431 */       for (int i2 = 0; i2 < n2; i2++) {
/* 432 */         int i2m = (i2 > 0) ? (i2 - 1) : i2;
/* 433 */         int i2p = (i2 < n2m) ? (i2 + 1) : i2;
/* 434 */         for (int i1 = 0; i1 < n1; i1++) {
/* 435 */           int i1m = (i1 > 0) ? (i1 - 1) : i1;
/* 436 */           int i1p = (i1 < n1m) ? (i1 + 1) : i1;
/* 437 */           if (t[i3][i2][i1] == 0.0F) {
/* 438 */             float tmax = 0.0F;
/* 439 */             tmax = ArrayMath.max(tmax, t[i3m][i2m][i1m]);
/* 440 */             tmax = ArrayMath.max(tmax, t[i3m][i2m][i1]);
/* 441 */             tmax = ArrayMath.max(tmax, t[i3m][i2m][i1p]);
/* 442 */             tmax = ArrayMath.max(tmax, t[i3m][i2][i1m]);
/* 443 */             tmax = ArrayMath.max(tmax, t[i3m][i2][i1]);
/* 444 */             tmax = ArrayMath.max(tmax, t[i3m][i2][i1p]);
/* 445 */             tmax = ArrayMath.max(tmax, t[i3m][i2p][i1m]);
/* 446 */             tmax = ArrayMath.max(tmax, t[i3m][i2p][i1]);
/* 447 */             tmax = ArrayMath.max(tmax, t[i3m][i2p][i1p]);
/* 448 */             tmax = ArrayMath.max(tmax, t[i3][i2m][i1m]);
/* 449 */             tmax = ArrayMath.max(tmax, t[i3][i2m][i1]);
/* 450 */             tmax = ArrayMath.max(tmax, t[i3][i2m][i1p]);
/* 451 */             tmax = ArrayMath.max(tmax, t[i3][i2][i1m]);
/* 452 */             tmax = ArrayMath.max(tmax, t[i3][i2][i1p]);
/* 453 */             tmax = ArrayMath.max(tmax, t[i3][i2p][i1m]);
/* 454 */             tmax = ArrayMath.max(tmax, t[i3][i2p][i1]);
/* 455 */             tmax = ArrayMath.max(tmax, t[i3][i2p][i1p]);
/* 456 */             tmax = ArrayMath.max(tmax, t[i3p][i2m][i1m]);
/* 457 */             tmax = ArrayMath.max(tmax, t[i3p][i2m][i1]);
/* 458 */             tmax = ArrayMath.max(tmax, t[i3p][i2m][i1p]);
/* 459 */             tmax = ArrayMath.max(tmax, t[i3p][i2][i1m]);
/* 460 */             tmax = ArrayMath.max(tmax, t[i3p][i2][i1]);
/* 461 */             tmax = ArrayMath.max(tmax, t[i3p][i2][i1p]);
/* 462 */             tmax = ArrayMath.max(tmax, t[i3p][i2p][i1m]);
/* 463 */             tmax = ArrayMath.max(tmax, t[i3p][i2p][i1]);
/* 464 */             tmax = ArrayMath.max(tmax, t[i3p][i2p][i1p]);
/* 465 */             s[m[i3][i2][i1]] = tmax;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 470 */     for (i3 = 0; i3 < n3; i3++) {
/* 471 */       for (int i2 = 0; i2 < n2; i2++) {
/* 472 */         for (int i1 = 0; i1 < n1; i1++) {
/* 473 */           if (t[i3][i2][i1] > 0.0F)
/* 474 */             t[i3][i2][i1] = ArrayMath.max(Float.MIN_VALUE, t[i3][i2][i1] - s[m[i3][i2][i1]]); 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public float[][][] gridNearest(float pnull, float[][][] p) {
/*     */     int n1 = (p[0][0]).length;
/*     */     int n2 = (p[0]).length;
/*     */     int n3 = p.length;
/*     */     int nmark = 0;
/*     */     float[][][] t = new float[n3][n2][n1];
/*     */     float tnull = -3.4028235E38F;
/*     */     for (int i3 = 0; i3 < n3; i3++) {
/*     */       for (int i2 = 0; i2 < n2; i2++) {
/*     */         for (int i1 = 0; i1 < n1; i1++) {
/*     */           if (p[i3][i2][i1] == pnull) {
/*     */             t[i3][i2][i1] = tnull;
/*     */           } else {
/*     */             t[i3][i2][i1] = 0.0F;
/*     */             nmark++;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     gridNearest(nmark, t, p);
/*     */     return t;
/*     */   }
/*     */   
/*     */   public void gridNearest(float[][][] t, float[][][] p) {
/*     */     int n1 = (t[0][0]).length;
/*     */     int n2 = (t[0]).length;
/*     */     int n3 = t.length;
/*     */     int nmark = 0;
/*     */     for (int i3 = 0; i3 < n3; i3++) {
/*     */       for (int i2 = 0; i2 < n2; i2++) {
/*     */         for (int i1 = 0; i1 < n1; i1++) {
/*     */           if (t[i3][i2][i1] == 0.0F)
/*     */             nmark++; 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     gridNearest(nmark, t, p);
/*     */   }
/*     */   
/*     */   public void gridBlended(float[][][] t, float[][][] p, float[][][] q) {
/*     */     int n1 = (t[0][0]).length;
/*     */     int n2 = (t[0]).length;
/*     */     int n3 = t.length;
/*     */     float[][][] s = ArrayMath.mul(t, t);
/*     */     if (this._ldk.getStencil() != LocalDiffusionKernel.Stencil.D21)
/*     */       for (int i = n3 - 1; i > 0; i--) {
/*     */         for (int i2 = n2 - 1; i2 > 0; i2--) {
/*     */           for (int i1 = n1 - 1; i1 > 0; i1--)
/*     */             s[i][i2][i1] = 0.125F * (s[i][i2][i1] + s[i][i2][i1 - 1] + s[i][i2 - 1][i1] + s[i][i2 - 1][i1 - 1] + s[i - 1][i2][i1] + s[i - 1][i2][i1 - 1] + s[i - 1][i2 - 1][i1] + s[i - 1][i2 - 1][i1 - 1]); 
/*     */         } 
/*     */       }  
/*     */     LocalSmoothingFilter lsf = new LocalSmoothingFilter(0.01D, 10000, this._ldk);
/*     */     lsf.setPreconditioner(true);
/*     */     float pavg = ArrayMath.sum(p) / n1 / n2 / n3;
/*     */     float[][][] r = ArrayMath.sub(p, pavg);
/*     */     if (this._ldk.getStencil() != LocalDiffusionKernel.Stencil.D21)
/*     */       lsf.applySmoothS(r, r); 
/*     */     lsf.apply(this._tensors, this._c, s, r, q);
/*     */     ArrayMath.add(q, pavg, q);
/*     */     for (int i3 = 0; i3 < n3; i3++) {
/*     */       for (int i2 = 0; i2 < n2; i2++) {
/*     */         for (int i1 = 0; i1 < n1; i1++) {
/*     */           if (t[i3][i2][i1] == 0.0F)
/*     */             q[i3][i2][i1] = p[i3][i2][i1]; 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setScattered(float[] f, float[] x1, float[] x2, float[] x3) {
/*     */     this._f = f;
/*     */     this._x1 = x1;
/*     */     this._x2 = x2;
/*     */     this._x3 = x3;
/*     */   }
/*     */   
/*     */   public float[][][] grid(Sampling s1, Sampling s2, Sampling s3) {
/*     */     Check.argument(s1.isUniform(), "s1 is uniform");
/*     */     Check.argument(s2.isUniform(), "s2 is uniform");
/*     */     Check.argument(s3.isUniform(), "s3 is uniform");
/*     */     Check.state((this._f != null), "scattered samples have been set");
/*     */     Check.state((this._x1 != null), "scattered samples have been set");
/*     */     Check.state((this._x2 != null), "scattered samples have been set");
/*     */     Check.state((this._x3 != null), "scattered samples have been set");
/*     */     int n1 = s1.getCount();
/*     */     int n2 = s2.getCount();
/*     */     int n3 = s3.getCount();
/*     */     SimpleGridder3 sg = new SimpleGridder3(this._f, this._x1, this._x2, this._x3);
/*     */     float pnull = -3.4028235E38F;
/*     */     float tnull = -3.4028235E38F;
/*     */     sg.setNullValue(pnull);
/*     */     float[][][] p = sg.grid(s1, s2, s3);
/*     */     float[][][] t = new float[n3][n2][n1];
/*     */     for (int i3 = 0; i3 < n3; i3++) {
/*     */       for (int i2 = 0; i2 < n2; i2++) {
/*     */         for (int i1 = 0; i1 < n1; i1++)
/*     */           t[i3][i2][i1] = (p[i3][i2][i1] != pnull) ? 0.0F : tnull; 
/*     */       } 
/*     */     } 
/*     */     gridNearest(t, p);
/*     */     float[][][] q = p;
/*     */     if (this._blending) {
/*     */       q = new float[n3][n2][n1];
/*     */       gridBlended(t, p, q);
/*     */     } 
/*     */     return q;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/interp/BlendedGridder3.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */