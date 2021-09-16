/*     */ package edu.mines.jtk.dsp;
/*     */ 
/*     */ import edu.mines.jtk.util.ArrayMath;
/*     */ import edu.mines.jtk.util.Parallel;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LocalSmoothingFilter
/*     */ {
/*     */   private static final boolean PARALLEL = true;
/*     */   
/*     */   public LocalSmoothingFilter() {
/*  69 */     this(0.01D, 100);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LocalSmoothingFilter(double small, int niter) {
/*  80 */     this._small = (float)small;
/*  81 */     this._niter = niter;
/*  82 */     this._ldk = new LocalDiffusionKernel(LocalDiffusionKernel.Stencil.D22);
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
/*     */   public LocalSmoothingFilter(double small, int niter, LocalDiffusionKernel ldk) {
/*  95 */     this._small = (float)small;
/*  96 */     this._niter = niter;
/*  97 */     this._ldk = ldk;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPreconditioner(boolean pc) {
/* 108 */     this._pc = pc;
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
/*     */   public void apply(float c, float[] x, float[] y) {
/* 121 */     apply(c, (float[])null, x, y);
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
/*     */   public void apply(float c, float[] s, float[] x, float[] y) {
/* 135 */     int n1 = x.length;
/*     */ 
/*     */     
/* 138 */     float[] e = new float[n1 + 1];
/* 139 */     if (s != null) {
/* 140 */       c = -0.5F * c;
/* 141 */       for (int i = 1; i < n1; i++)
/* 142 */         e[i] = c * (s[i] + s[i - 1]); 
/*     */     } else {
/* 144 */       c = -c;
/* 145 */       for (int i = 1; i < n1; i++) {
/* 146 */         e[i] = c;
/*     */       }
/*     */     } 
/*     */     
/* 150 */     float[] w = e;
/*     */ 
/*     */     
/* 153 */     float t = 1.0F - e[0] - e[1];
/* 154 */     y[0] = x[0] / t; int i1;
/* 155 */     for (i1 = 1; i1 < n1; i1++) {
/* 156 */       float di = 1.0F - e[i1] - e[i1 + 1];
/* 157 */       float ei = e[i1];
/* 158 */       w[i1] = ei / t;
/* 159 */       t = di - ei * w[i1];
/* 160 */       y[i1] = (x[i1] - ei * y[i1 - 1]) / t;
/*     */     } 
/* 162 */     for (i1 = n1 - 1; i1 > 0; i1--) {
/* 163 */       y[i1 - 1] = y[i1 - 1] - w[i1] * y[i1];
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void apply(float[][] x, float[][] y) {
/* 173 */     apply((Tensors2)null, 1.0F, (float[][])null, x, y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void apply(float c, float[][] x, float[][] y) {
/* 183 */     apply((Tensors2)null, c, (float[][])null, x, y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void apply(float c, float[][] s, float[][] x, float[][] y) {
/* 194 */     apply((Tensors2)null, c, s, x, y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void apply(Tensors2 d, float[][] x, float[][] y) {
/* 205 */     apply(d, 1.0F, (float[][])null, x, y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void apply(Tensors2 d, float c, float[][] x, float[][] y) {
/* 216 */     apply(d, c, (float[][])null, x, y);
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
/*     */   public void apply(Tensors2 d, float c, float[][] s, float[][] x, float[][] y) {
/* 230 */     Operator2 a = new A2(this._ldk, d, c, s);
/* 231 */     scopy(x, y);
/* 232 */     if (this._pc) {
/* 233 */       Operator2 m = new M2(d, c, s, x);
/* 234 */       solve(a, m, x, y);
/*     */     } else {
/* 236 */       solve(a, x, y);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void apply(float[][][] x, float[][][] y) {
/* 247 */     apply((Tensors3)null, 1.0F, (float[][][])null, x, y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void apply(float c, float[][][] x, float[][][] y) {
/* 257 */     apply((Tensors3)null, c, (float[][][])null, x, y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void apply(float c, float[][][] s, float[][][] x, float[][][] y) {
/* 268 */     apply((Tensors3)null, c, s, x, y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void apply(Tensors3 d, float[][][] x, float[][][] y) {
/* 279 */     apply(d, 1.0F, (float[][][])null, x, y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void apply(Tensors3 d, float c, float[][][] x, float[][][] y) {
/* 290 */     apply(d, c, (float[][][])null, x, y);
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
/*     */   public void apply(Tensors3 d, float c, float[][][] s, float[][][] x, float[][][] y) {
/* 304 */     Operator3 a = new A3(this._ldk, d, c, s);
/* 305 */     scopy(x, y);
/* 306 */     if (this._pc) {
/* 307 */       Operator3 m = new M3(d, c, s, x);
/* 308 */       solve(a, m, x, y);
/*     */     } else {
/* 310 */       solve(a, x, y);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void applySmoothS(float[][] x, float[][] y) {
/* 321 */     smoothS(x, y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void applySmoothS(float[][][] x, float[][][] y) {
/* 331 */     smoothS(x, y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void applySmoothL(double kmax, float[][] x, float[][] y) {
/* 342 */     smoothL(kmax, x, y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void applySmoothL(double kmax, float[][][] x, float[][][] y) {
/* 353 */     smoothL(kmax, x, y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 362 */   private static Logger log = Logger.getLogger(LocalSmoothingFilter.class.getName());
/*     */   
/*     */   private float _small;
/*     */   
/*     */   private int _niter;
/*     */   
/*     */   private boolean _pc;
/*     */   
/*     */   private LocalDiffusionKernel _ldk;
/*     */   private BandPassFilter _lpf;
/*     */   private double _kmax;
/*     */   
/*     */   private static class A2
/*     */     implements Operator2
/*     */   {
/*     */     private LocalDiffusionKernel _ldk;
/*     */     private Tensors2 _d;
/*     */     private float _c;
/*     */     private float[][] _s;
/*     */     
/*     */     A2(LocalDiffusionKernel ldk, Tensors2 d, float c, float[][] s) {
/* 383 */       this._ldk = ldk;
/* 384 */       this._d = d;
/* 385 */       this._c = c;
/* 386 */       this._s = s;
/*     */     }
/*     */     public void apply(float[][] x, float[][] y) {
/* 389 */       LocalSmoothingFilter.scopy(x, y);
/* 390 */       this._ldk.apply(this._d, this._c, this._s, x, y);
/*     */     }
/*     */   }
/*     */   
/*     */   private static class M2
/*     */     implements Operator2
/*     */   {
/*     */     private float[][] _p;
/*     */     
/*     */     M2(Tensors2 d, float c, float[][] s, float[][] x) {
/* 400 */       int n1 = (x[0]).length;
/* 401 */       int n2 = x.length;
/* 402 */       this._p = ArrayMath.fillfloat(1.0F, n1, n2);
/* 403 */       c *= 0.25F;
/* 404 */       float[] di = new float[3];
/* 405 */       for (int i2 = 1, m2 = 0; i2 < n2; i2++, m2++) {
/* 406 */         for (int i1 = 1, m1 = 0; i1 < n1; i1++, m1++) {
/* 407 */           float si = (s != null) ? s[i2][i1] : 1.0F;
/* 408 */           float csi = c * si;
/* 409 */           float d11 = csi;
/* 410 */           float d12 = 0.0F;
/* 411 */           float d22 = csi;
/* 412 */           if (d != null) {
/* 413 */             d.getTensor(i1, i2, di);
/* 414 */             d11 = di[0] * csi;
/* 415 */             d12 = di[1] * csi;
/* 416 */             d22 = di[2] * csi;
/*     */           } 
/* 418 */           this._p[i2][i1] = this._p[i2][i1] + d11 + d12 + d12 + d22;
/* 419 */           this._p[m2][m1] = this._p[m2][m1] + d11 + d12 + d12 + d22;
/* 420 */           this._p[i2][m1] = this._p[i2][m1] + d11 - d12 + -d12 + d22;
/* 421 */           this._p[m2][i1] = this._p[m2][i1] + d11 - d12 + -d12 + d22;
/*     */         } 
/*     */       } 
/* 424 */       ArrayMath.div(1.0F, this._p, this._p);
/*     */     }
/*     */     public void apply(float[][] x, float[][] y) {
/* 427 */       LocalSmoothingFilter.sxy(this._p, x, y);
/*     */     } }
/*     */   
/*     */   private static class A3 implements Operator3 { private LocalDiffusionKernel _ldk;
/*     */     private Tensors3 _d;
/*     */     
/*     */     A3(LocalDiffusionKernel ldk, Tensors3 d, float c, float[][][] s) {
/* 434 */       this._ldk = ldk;
/* 435 */       this._d = d;
/* 436 */       this._c = c;
/* 437 */       this._s = s;
/*     */     } private float _c; private float[][][] _s;
/*     */     public void apply(float[][][] x, float[][][] y) {
/* 440 */       LocalSmoothingFilter.scopy(x, y);
/* 441 */       this._ldk.apply(this._d, this._c, this._s, x, y);
/*     */     } }
/*     */ 
/*     */   
/*     */   private static class M3
/*     */     implements Operator3
/*     */   {
/*     */     private float[][][] _p;
/*     */     
/*     */     M3(Tensors3 d, float c, float[][][] s, float[][][] x) {
/* 451 */       int n1 = (x[0][0]).length;
/* 452 */       int n2 = (x[0]).length;
/* 453 */       int n3 = x.length;
/* 454 */       this._p = ArrayMath.fillfloat(1.0F, n1, n2, n3);
/* 455 */       c *= 0.0625F;
/* 456 */       float[] di = new float[6];
/* 457 */       for (int i3 = 1, m3 = 0; i3 < n3; i3++, m3++) {
/* 458 */         for (int i2 = 1, m2 = 0; i2 < n2; i2++, m2++) {
/* 459 */           for (int i1 = 1, m1 = 0; i1 < n1; i1++, m1++) {
/* 460 */             float si = (s != null) ? s[i3][i2][i1] : 1.0F;
/* 461 */             float csi = c * si;
/* 462 */             float d11 = csi;
/* 463 */             float d12 = 0.0F;
/* 464 */             float d13 = 0.0F;
/* 465 */             float d22 = csi;
/* 466 */             float d23 = 0.0F;
/* 467 */             float d33 = csi;
/* 468 */             if (d != null) {
/* 469 */               d.getTensor(i1, i2, i3, di);
/* 470 */               d11 = di[0] * csi;
/* 471 */               d12 = di[1] * csi;
/* 472 */               d13 = di[2] * csi;
/* 473 */               d22 = di[3] * csi;
/* 474 */               d23 = di[4] * csi;
/* 475 */               d33 = di[5] * csi;
/*     */             } 
/* 477 */             this._p[i3][i2][i1] = this._p[i3][i2][i1] + d11 + d12 + d13 + d12 + d22 + d23 + d13 + d23 + d33;
/* 478 */             this._p[m3][m2][m1] = this._p[m3][m2][m1] + d11 + d12 + d13 + d12 + d22 + d23 + d13 + d23 + d33;
/* 479 */             this._p[i3][m2][i1] = this._p[i3][m2][i1] + d11 - d12 + d13 + -d12 + d22 - d23 + d13 - d23 + d33;
/* 480 */             this._p[m3][i2][m1] = this._p[m3][i2][m1] + d11 - d12 + d13 + -d12 + d22 - d23 + d13 - d23 + d33;
/* 481 */             this._p[m3][i2][i1] = this._p[m3][i2][i1] + d11 + d12 - d13 + d12 + d22 - d23 + -d13 - d23 + d33;
/* 482 */             this._p[i3][m2][m1] = this._p[i3][m2][m1] + d11 + d12 - d13 + d12 + d22 - d23 + -d13 - d23 + d33;
/* 483 */             this._p[m3][m2][i1] = this._p[m3][m2][i1] + d11 - d12 - d13 + -d12 + d22 + d23 + -d13 + d23 + d33;
/* 484 */             this._p[i3][i2][m1] = this._p[i3][i2][m1] + d11 - d12 - d13 + -d12 + d22 + d23 + -d13 + d23 + d33;
/*     */           } 
/*     */         } 
/*     */       } 
/* 488 */       ArrayMath.div(1.0F, this._p, this._p);
/*     */     }
/*     */     public void apply(float[][][] x, float[][][] y) {
/* 491 */       LocalSmoothingFilter.sxy(this._p, x, y);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void smoothL(double kmax, float[][] x, float[][] y) {
/* 500 */     ensureLowpassFilter(kmax);
/* 501 */     this._lpf.apply(x, y);
/*     */   }
/*     */   private void smoothL(double kmax, float[][][] x, float[][][] y) {
/* 504 */     ensureLowpassFilter(kmax);
/* 505 */     this._lpf.apply(x, y);
/*     */   }
/*     */   private void ensureLowpassFilter(double kmax) {
/* 508 */     if (this._lpf == null || this._kmax != kmax) {
/* 509 */       this._kmax = kmax;
/* 510 */       double kdelta = 0.5D - kmax;
/* 511 */       double kupper = kmax + 0.5D * kdelta;
/* 512 */       this._lpf = new BandPassFilter(0.0D, kupper, kdelta, 0.01D);
/* 513 */       this._lpf.setExtrapolation(BandPassFilter.Extrapolation.ZERO_SLOPE);
/* 514 */       this._lpf.setFilterCaching(false);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void smoothS(float[][] x, float[][] y) {
/* 522 */     int n1 = (x[0]).length;
/* 523 */     int n2 = x.length;
/* 524 */     int n1m = n1 - 1;
/* 525 */     int n2m = n2 - 1;
/* 526 */     float[][] t = new float[3][n1];
/* 527 */     scopy(x[0], t[0]);
/* 528 */     scopy(x[0], t[1]);
/* 529 */     for (int i2 = 0; i2 < n2; i2++) {
/* 530 */       int i2m = (i2 > 0) ? (i2 - 1) : 0;
/* 531 */       int i2p = (i2 < n2m) ? (i2 + 1) : n2m;
/* 532 */       int j2m = i2m % 3;
/* 533 */       int j2 = i2 % 3;
/* 534 */       int j2p = i2p % 3;
/* 535 */       scopy(x[i2p], t[j2p]);
/* 536 */       float[] x2m = t[j2m];
/* 537 */       float[] x2p = t[j2p];
/* 538 */       float[] x20 = t[j2];
/* 539 */       float[] y2 = y[i2];
/* 540 */       for (int i1 = 0; i1 < n1; i1++) {
/* 541 */         int i1m = (i1 > 0) ? (i1 - 1) : 0;
/* 542 */         int i1p = (i1 < n1m) ? (i1 + 1) : n1m;
/* 543 */         y2[i1] = 0.25F * x20[i1] + 0.125F * (x20[i1m] + x20[i1p] + x2m[i1] + x2p[i1]) + 0.0625F * (x2m[i1m] + x2m[i1p] + x2p[i1m] + x2p[i1p]);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void smoothS(float[][][] x, float[][][] y) {
/* 554 */     int n1 = (x[0][0]).length;
/* 555 */     int n2 = (x[0]).length;
/* 556 */     int n3 = x.length;
/* 557 */     int n1m = n1 - 1;
/* 558 */     int n2m = n2 - 1;
/* 559 */     int n3m = n3 - 1;
/* 560 */     float[][][] t = new float[3][n2][n1];
/* 561 */     scopy(x[0], t[0]);
/* 562 */     scopy(x[0], t[1]);
/* 563 */     for (int i3 = 0; i3 < n3; i3++) {
/* 564 */       int i3m = (i3 > 0) ? (i3 - 1) : 0;
/* 565 */       int i3p = (i3 < n3m) ? (i3 + 1) : n3m;
/* 566 */       int j3m = i3m % 3;
/* 567 */       int j3 = i3 % 3;
/* 568 */       int j3p = i3p % 3;
/* 569 */       scopy(x[i3p], t[j3p]);
/* 570 */       float[][] x3m = t[j3m];
/* 571 */       float[][] x3p = t[j3p];
/* 572 */       float[][] x30 = t[j3];
/* 573 */       float[][] y30 = y[i3];
/* 574 */       for (int i2 = 0; i2 < n2; i2++) {
/* 575 */         int i2m = (i2 > 0) ? (i2 - 1) : 0;
/* 576 */         int i2p = (i2 < n2m) ? (i2 + 1) : n2m;
/* 577 */         float[] x3m2m = x3m[i2m];
/* 578 */         float[] x3m20 = x3m[i2];
/* 579 */         float[] x3m2p = x3m[i2p];
/* 580 */         float[] x302m = x30[i2m];
/* 581 */         float[] x3020 = x30[i2];
/* 582 */         float[] x302p = x30[i2p];
/* 583 */         float[] x3p2m = x3p[i2m];
/* 584 */         float[] x3p20 = x3p[i2];
/* 585 */         float[] x3p2p = x3p[i2p];
/* 586 */         float[] y3020 = y30[i2];
/* 587 */         for (int i1 = 0; i1 < n1; i1++) {
/* 588 */           int i1m = (i1 > 0) ? (i1 - 1) : 0;
/* 589 */           int i1p = (i1 < n1m) ? (i1 + 1) : n1m;
/* 590 */           y3020[i1] = 0.125F * x3020[i1] + 0.0625F * (x3020[i1m] + x3020[i1p] + x302m[i1] + x302p[i1] + x3m20[i1] + x3p20[i1]) + 0.03125F * (x3m20[i1m] + x3m20[i1p] + x3m2m[i1] + x3m2p[i1] + x302m[i1m] + x302m[i1p] + x302p[i1m] + x302p[i1p] + x3p20[i1m] + x3p20[i1p] + x3p2m[i1] + x3p2p[i1]) + 0.015625F * (x3m2m[i1m] + x3m2m[i1p] + x3m2p[i1m] + x3m2p[i1p] + x3p2m[i1m] + x3p2m[i1p] + x3p2p[i1m] + x3p2p[i1p]);
/*     */         } 
/*     */       } 
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
/*     */   private void solve(Operator2 a, float[][] b, float[][] x) {
/* 612 */     int n1 = (b[0]).length;
/* 613 */     int n2 = b.length;
/* 614 */     float[][] d = new float[n2][n1];
/* 615 */     float[][] q = new float[n2][n1];
/* 616 */     float[][] r = new float[n2][n1];
/* 617 */     scopy(b, r);
/* 618 */     a.apply(x, q);
/* 619 */     saxpy(-1.0F, q, r);
/* 620 */     scopy(r, d);
/* 621 */     float delta = sdot(r, r);
/* 622 */     float bnorm = ArrayMath.sqrt(sdot(b, b));
/* 623 */     float rnorm = ArrayMath.sqrt(delta);
/* 624 */     float rnormBegin = rnorm;
/* 625 */     float rnormSmall = bnorm * this._small;
/*     */     
/* 627 */     log.fine("solve: bnorm=" + bnorm + " rnorm=" + rnorm); int iter;
/* 628 */     for (iter = 0; iter < this._niter && rnorm > rnormSmall; iter++) {
/* 629 */       log.finer("  iter=" + iter + " rnorm=" + rnorm + " ratio=" + (rnorm / rnormBegin));
/* 630 */       a.apply(d, q);
/* 631 */       float dq = sdot(d, q);
/* 632 */       float alpha = delta / dq;
/* 633 */       saxpy(alpha, d, x);
/* 634 */       saxpy(-alpha, q, r);
/* 635 */       float deltaOld = delta;
/* 636 */       delta = sdot(r, r);
/* 637 */       float beta = delta / deltaOld;
/* 638 */       sxpay(beta, r, d);
/* 639 */       rnorm = ArrayMath.sqrt(delta);
/*     */     } 
/* 641 */     log.fine("  iter=" + iter + " rnorm=" + rnorm + " ratio=" + (rnorm / rnormBegin));
/*     */   }
/*     */   private void solve(Operator3 a, float[][][] b, float[][][] x) {
/* 644 */     int n1 = (b[0][0]).length;
/* 645 */     int n2 = (b[0]).length;
/* 646 */     int n3 = b.length;
/* 647 */     float[][][] d = new float[n3][n2][n1];
/* 648 */     float[][][] q = new float[n3][n2][n1];
/* 649 */     float[][][] r = new float[n3][n2][n1];
/* 650 */     scopy(b, r); a.apply(x, q); saxpy(-1.0F, q, r);
/* 651 */     scopy(r, d);
/* 652 */     float delta = sdot(r, r);
/* 653 */     float bnorm = ArrayMath.sqrt(sdot(b, b));
/* 654 */     float rnorm = ArrayMath.sqrt(delta);
/* 655 */     float rnormBegin = rnorm;
/* 656 */     float rnormSmall = bnorm * this._small;
/*     */     
/* 658 */     log.fine("solve: bnorm=" + bnorm + " rnorm=" + rnorm); int iter;
/* 659 */     for (iter = 0; iter < this._niter && rnorm > rnormSmall; iter++) {
/* 660 */       log.finer("  iter=" + iter + " rnorm=" + rnorm + " ratio=" + (rnorm / rnormBegin));
/* 661 */       a.apply(d, q);
/* 662 */       float dq = sdot(d, q);
/* 663 */       float alpha = delta / dq;
/* 664 */       saxpy(alpha, d, x);
/* 665 */       if (iter % 100 < 99) {
/* 666 */         saxpy(-alpha, q, r);
/*     */       } else {
/* 668 */         scopy(b, r); a.apply(x, q); saxpy(-1.0F, q, r);
/*     */       } 
/* 670 */       float deltaOld = delta;
/* 671 */       delta = sdot(r, r);
/* 672 */       float beta = delta / deltaOld;
/* 673 */       sxpay(beta, r, d);
/* 674 */       rnorm = ArrayMath.sqrt(delta);
/*     */     } 
/* 676 */     log.fine("  iter=" + iter + " rnorm=" + rnorm + " ratio=" + (rnorm / rnormBegin));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void solve(Operator2 a, Operator2 m, float[][] b, float[][] x) {
/* 682 */     int n1 = (b[0]).length;
/* 683 */     int n2 = b.length;
/* 684 */     float[][] d = new float[n2][n1];
/* 685 */     float[][] q = new float[n2][n1];
/* 686 */     float[][] r = new float[n2][n1];
/* 687 */     float[][] s = new float[n2][n1];
/* 688 */     scopy(b, r);
/* 689 */     a.apply(x, q);
/* 690 */     saxpy(-1.0F, q, r);
/* 691 */     float bnorm = ArrayMath.sqrt(sdot(b, b));
/* 692 */     float rnorm = ArrayMath.sqrt(sdot(r, r));
/* 693 */     float rnormBegin = rnorm;
/* 694 */     float rnormSmall = bnorm * this._small;
/* 695 */     m.apply(r, s);
/* 696 */     scopy(s, d);
/* 697 */     float delta = sdot(r, s);
/*     */     
/* 699 */     log.fine("msolve: bnorm=" + bnorm + " rnorm=" + rnorm); int iter;
/* 700 */     for (iter = 0; iter < this._niter && rnorm > rnormSmall; iter++) {
/* 701 */       log.finer("  iter=" + iter + " rnorm=" + rnorm + " ratio=" + (rnorm / rnormBegin));
/* 702 */       a.apply(d, q);
/* 703 */       float alpha = delta / sdot(d, q);
/* 704 */       saxpy(alpha, d, x);
/* 705 */       saxpy(-alpha, q, r);
/* 706 */       m.apply(r, s);
/* 707 */       float deltaOld = delta;
/* 708 */       delta = sdot(r, s);
/* 709 */       float beta = delta / deltaOld;
/* 710 */       sxpay(beta, s, d);
/* 711 */       rnorm = ArrayMath.sqrt(sdot(r, r));
/*     */     } 
/* 713 */     log.fine("  iter=" + iter + " rnorm=" + rnorm + " ratio=" + (rnorm / rnormBegin));
/*     */   }
/*     */   private void solve(Operator3 a, Operator3 m, float[][][] b, float[][][] x) {
/* 716 */     int n1 = (b[0][0]).length;
/* 717 */     int n2 = (b[0]).length;
/* 718 */     int n3 = b.length;
/* 719 */     float[][][] d = new float[n3][n2][n1];
/* 720 */     float[][][] q = new float[n3][n2][n1];
/* 721 */     float[][][] r = new float[n3][n2][n1];
/* 722 */     float[][][] s = new float[n3][n2][n1];
/* 723 */     scopy(b, r); a.apply(x, q); saxpy(-1.0F, q, r);
/* 724 */     float bnorm = ArrayMath.sqrt(sdot(b, b));
/* 725 */     float rnorm = ArrayMath.sqrt(sdot(r, r));
/* 726 */     float rnormBegin = rnorm;
/* 727 */     float rnormSmall = bnorm * this._small;
/* 728 */     m.apply(r, s);
/* 729 */     scopy(s, d);
/* 730 */     float delta = sdot(r, s);
/*     */     
/* 732 */     log.fine("msolve: bnorm=" + bnorm + " rnorm=" + rnorm); int iter;
/* 733 */     for (iter = 0; iter < this._niter && rnorm > rnormSmall; iter++) {
/* 734 */       log.finer("  iter=" + iter + " rnorm=" + rnorm + " ratio=" + (rnorm / rnormBegin));
/* 735 */       a.apply(d, q);
/* 736 */       float alpha = delta / sdot(d, q);
/* 737 */       saxpy(alpha, d, x);
/* 738 */       if (iter % 100 < 99) {
/* 739 */         saxpy(-alpha, q, r);
/*     */       } else {
/* 741 */         scopy(b, r); a.apply(x, q); saxpy(-1.0F, q, r);
/*     */       } 
/* 743 */       m.apply(r, s);
/* 744 */       float deltaOld = delta;
/* 745 */       delta = sdot(r, s);
/* 746 */       float beta = delta / deltaOld;
/* 747 */       sxpay(beta, s, d);
/* 748 */       rnorm = ArrayMath.sqrt(sdot(r, r));
/*     */     } 
/* 750 */     log.fine("  iter=" + iter + " rnorm=" + rnorm + " ratio=" + (rnorm / rnormBegin));
/*     */   }
/*     */ 
/*     */   
/*     */   private static void szero(float[] x) {
/* 755 */     ArrayMath.zero(x);
/*     */   }
/*     */   private static void szero(float[][] x) {
/* 758 */     ArrayMath.zero(x);
/*     */   }
/*     */   private static void szero(final float[][][] x) {
/* 761 */     int n3 = x.length;
/* 762 */     Parallel.loop(n3, new Parallel.LoopInt() {
/*     */           public void compute(int i3) {
/* 764 */             LocalSmoothingFilter.szero(x[i3]);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   private static void scopy(float[] x, float[] y) {
/* 771 */     ArrayMath.copy(x, y);
/*     */   }
/*     */   private static void scopy(float[][] x, float[][] y) {
/* 774 */     ArrayMath.copy(x, y);
/*     */   }
/*     */   private static void scopy(final float[][][] x, final float[][][] y) {
/* 777 */     int n3 = x.length;
/* 778 */     Parallel.loop(n3, new Parallel.LoopInt() {
/*     */           public void compute(int i3) {
/* 780 */             LocalSmoothingFilter.scopy(x[i3], y[i3]);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   private static float sdot(float[][] x, float[][] y) {
/* 787 */     int n1 = (x[0]).length;
/* 788 */     int n2 = x.length;
/* 789 */     float d = 0.0F;
/* 790 */     for (int i2 = 0; i2 < n2; i2++) {
/* 791 */       float[] x2 = x[i2], y2 = y[i2];
/* 792 */       for (int i1 = 0; i1 < n1; i1++) {
/* 793 */         d += x2[i1] * y2[i1];
/*     */       }
/*     */     } 
/* 796 */     return d;
/*     */   }
/*     */   private static float sdot(final float[][][] x, final float[][][] y) {
/* 799 */     int n3 = x.length;
/* 800 */     final float[] d3 = new float[n3];
/* 801 */     Parallel.loop(n3, new Parallel.LoopInt() {
/*     */           public void compute(int i3) {
/* 803 */             d3[i3] = LocalSmoothingFilter.sdot(x[i3], y[i3]);
/*     */           }
/*     */         });
/* 806 */     float d = 0.0F;
/* 807 */     for (int i3 = 0; i3 < n3; i3++)
/* 808 */       d += d3[i3]; 
/* 809 */     return d;
/*     */   }
/*     */ 
/*     */   
/*     */   private static void saxpy(float a, float[][] x, float[][] y) {
/* 814 */     int n1 = (x[0]).length;
/* 815 */     int n2 = x.length;
/* 816 */     for (int i2 = 0; i2 < n2; i2++) {
/* 817 */       float[] x2 = x[i2], y2 = y[i2];
/* 818 */       for (int i1 = 0; i1 < n1; i1++) {
/* 819 */         y2[i1] = y2[i1] + a * x2[i1];
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void saxpy(final float a, final float[][][] x, final float[][][] y) {
/* 826 */     int n3 = x.length;
/* 827 */     Parallel.loop(n3, new Parallel.LoopInt() {
/*     */           public void compute(int i3) {
/* 829 */             LocalSmoothingFilter.saxpy(a, x[i3], y[i3]);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   private static void sxpay(float a, float[][] x, float[][] y) {
/* 836 */     int n1 = (x[0]).length;
/* 837 */     int n2 = x.length;
/* 838 */     for (int i2 = 0; i2 < n2; i2++) {
/* 839 */       float[] x2 = x[i2], y2 = y[i2];
/* 840 */       for (int i1 = 0; i1 < n1; i1++) {
/* 841 */         y2[i1] = a * y2[i1] + x2[i1];
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void sxpay(final float a, final float[][][] x, final float[][][] y) {
/* 848 */     int n3 = x.length;
/* 849 */     Parallel.loop(n3, new Parallel.LoopInt() {
/*     */           public void compute(int i3) {
/* 851 */             LocalSmoothingFilter.sxpay(a, x[i3], y[i3]);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   private static void sxy(float[][] x, float[][] y, float[][] z) {
/* 858 */     int n1 = (x[0]).length;
/* 859 */     int n2 = x.length;
/* 860 */     for (int i2 = 0; i2 < n2; i2++) {
/* 861 */       float[] x2 = x[i2], y2 = y[i2], z2 = z[i2];
/* 862 */       for (int i1 = 0; i1 < n1; i1++) {
/* 863 */         z2[i1] = x2[i1] * y2[i1];
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void sxy(final float[][][] x, final float[][][] y, final float[][][] z) {
/* 870 */     int n3 = x.length;
/* 871 */     Parallel.loop(n3, new Parallel.LoopInt() {
/*     */           public void compute(int i3) {
/* 873 */             LocalSmoothingFilter.sxy(x[i3], y[i3], z[i3]);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   private static interface Operator3 {
/*     */     void apply(float[][][] param1ArrayOffloat1, float[][][] param1ArrayOffloat2);
/*     */   }
/*     */   
/*     */   private static interface Operator2 {
/*     */     void apply(float[][] param1ArrayOffloat1, float[][] param1ArrayOffloat2);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/dsp/LocalSmoothingFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */