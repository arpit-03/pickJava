/*     */ package edu.mines.jtk.interp;
/*     */ 
/*     */ import edu.mines.jtk.dsp.LocalDiffusionKernel;
/*     */ import edu.mines.jtk.dsp.Sampling;
/*     */ import edu.mines.jtk.dsp.Tensors2;
/*     */ import edu.mines.jtk.util.ArrayMath;
/*     */ import edu.mines.jtk.util.Check;
/*     */ import java.util.ArrayList;
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
/*     */ public class SplinesGridder2
/*     */   implements Gridder2
/*     */ {
/*     */   private static final float QNULL = 1.4E-45F;
/*     */   private Tensors2 _tensors;
/*     */   private float _tension;
/*     */   private float[] _f;
/*     */   private float[] _x1;
/*     */   private float[] _x2;
/*     */   private float _small;
/*     */   private int _niter;
/*     */   private ArrayList<Float> _residuals;
/*     */   private LocalDiffusionKernel _ldk;
/*     */   
/*     */   public SplinesGridder2() {
/*  71 */     this(null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SplinesGridder2(float[] f, float[] x1, float[] x2) {
/*  82 */     this(null);
/*  83 */     setScattered(f, x1, x2);
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
/*     */   public SplinesGridder2(Tensors2 tensors)
/*     */   {
/* 245 */     this._tension = 0.0F;
/*     */     
/* 247 */     this._small = 1.0E-4F;
/* 248 */     this._niter = 10000;
/* 249 */     this._residuals = new ArrayList<>();
/* 250 */     this._ldk = new LocalDiffusionKernel(LocalDiffusionKernel.Stencil.D22); setTensors(tensors); } public SplinesGridder2(Tensors2 tensors, float[] f, float[] x1, float[] x2) { this._tension = 0.0F; this._small = 1.0E-4F; this._niter = 10000; this._residuals = new ArrayList<>(); this._ldk = new LocalDiffusionKernel(LocalDiffusionKernel.Stencil.D22); setTensors(tensors); setScattered(f, x1, x2); } public void setTensors(Tensors2 tensors) { this._tensors = tensors; if (this._tensors == null) this._tensors = new Tensors2() { public void getTensor(int i1, int i2, float[] d) { d[0] = 1.0F; d[1] = 0.0F; d[2] = 1.0F; } }
/*     */         ;  } public void setTension(double tension) { Check.argument((0.0D <= tension), "0<=tension"); Check.argument((tension < 1.0D), "tension<1"); this._tension = (float)tension; }
/*     */   public void setMaxIterations(int niter) { this._niter = niter; }
/*     */   public int getIterationCount() { return this._residuals.size() - 1; }
/* 254 */   private static Logger log = Logger.getLogger(SplinesGridder2.class.getName());
/*     */   public float[] getResiduals() { int n = this._residuals.size(); float[] r = new float[n]; for (int i = 0; i < n; i++)
/*     */       r[i] = ((Float)this._residuals.get(i)).floatValue();  return r; }
/*     */   public void gridMissing(float qnull, float[][] q) { int n1 = (q[0]).length; int n2 = q.length; boolean[][] m = new boolean[n2][n1]; for (int i2 = 0; i2 < n2; i2++) {
/*     */       for (int i1 = 0; i1 < n1; i1++)
/*     */         m[i2][i1] = (q[i2][i1] == qnull); 
/*     */     }  gridMissing(m, q); }
/*     */   public void gridMissing(boolean[][] m, float[][] q) { int n1 = (m[0]).length; int n2 = m.length; float s = 0.02F * (n1 - 1 + n2 - 1); float t = this._tension / (1.0F - this._tension) / s * s; LaplaceOperator2 lop = new LaplaceOperator2(this._ldk, this._tensors, t, m); SmoothOperator2 sop = new SmoothOperator2(); float[][] b = new float[n2][n1]; lop.applyRhs(q, b); solve(lop, sop, b, q); } public void setScattered(float[] f, float[] x1, float[] x2) { this._f = f; this._x1 = x1; this._x2 = x2; } public float[][] grid(Sampling s1, Sampling s2) { Check.argument(s1.isUniform(), "s1 is uniform"); Check.argument(s2.isUniform(), "s2 is uniform"); Check.state((this._f != null), "scattered samples have been set"); Check.state((this._x1 != null), "scattered samples have been set"); Check.state((this._x2 != null), "scattered samples have been set"); PolyTrend2 pt = new PolyTrend2(1, this._f, this._x1, this._x2); pt.detrend(); SimpleGridder2 sg = new SimpleGridder2(this._f, this._x1, this._x2); sg.setNullValue(Float.MIN_VALUE); float[][] q = sg.grid(s1, s2); gridMissing(Float.MIN_VALUE, q); pt.restore(q, s1, s2); pt.restore();
/*     */     return q; } private static interface Operator2 {
/*     */     void apply(float[][] param1ArrayOffloat1, float[][] param1ArrayOffloat2);
/*     */   } private static class SmoothOperator2 implements Operator2 {
/* 265 */     public void apply(float[][] x, float[][] y) { SplinesGridder2.smoothS(x, y);
/* 266 */       SplinesGridder2.smoothS(y, y); }
/*     */     
/*     */     private SmoothOperator2() {} }
/*     */   private static class LaplaceOperator2 implements Operator2 { private LocalDiffusionKernel _ldk;
/*     */     private Tensors2 _d;
/*     */     private float _t;
/*     */     private boolean[][] _m;
/*     */     private float[][] _z;
/*     */     
/*     */     LaplaceOperator2(LocalDiffusionKernel ldk, Tensors2 d, float t, boolean[][] m) {
/* 276 */       this._ldk = ldk;
/* 277 */       this._d = d;
/* 278 */       this._t = t;
/* 279 */       this._m = m;
/* 280 */       this._z = new float[m.length][(m[0]).length];
/*     */     }
/*     */     public void apply(float[][] x, float[][] y) {
/* 283 */       int n1 = (x[0]).length;
/* 284 */       int n2 = x.length;
/*     */       int i2;
/* 286 */       for (i2 = 0; i2 < n2; i2++) {
/* 287 */         for (int i1 = 0; i1 < n1; i1++)
/* 288 */           this._z[i2][i1] = this._m[i2][i1] ? x[i2][i1] : 0.0F; 
/*     */       } 
/* 290 */       SplinesGridder2.szero(y);
/* 291 */       this._ldk.apply(this._d, this._z, y);
/*     */       
/* 293 */       ArrayMath.mul(this._t, y, this._z);
/* 294 */       this._ldk.apply(this._d, y, this._z);
/*     */       
/* 296 */       for (i2 = 0; i2 < n2; i2++) {
/* 297 */         for (int i1 = 0; i1 < n1; i1++)
/* 298 */           y[i2][i1] = this._m[i2][i1] ? this._z[i2][i1] : x[i2][i1]; 
/*     */       } 
/*     */     } public void applyRhs(float[][] x, float[][] y) {
/* 301 */       int n1 = (x[0]).length;
/* 302 */       int n2 = x.length;
/*     */       int i2;
/* 304 */       for (i2 = 0; i2 < n2; i2++) {
/* 305 */         for (int i1 = 0; i1 < n1; i1++)
/* 306 */           this._z[i2][i1] = this._m[i2][i1] ? 0.0F : x[i2][i1]; 
/*     */       } 
/* 308 */       SplinesGridder2.szero(y);
/* 309 */       this._ldk.apply(this._d, this._z, y);
/*     */       
/* 311 */       ArrayMath.mul(this._t, y, this._z);
/* 312 */       this._ldk.apply(this._d, y, this._z);
/*     */       
/* 314 */       for (i2 = 0; i2 < n2; i2++) {
/* 315 */         for (int i1 = 0; i1 < n1; i1++) {
/* 316 */           y[i2][i1] = this._m[i2][i1] ? -this._z[i2][i1] : x[i2][i1];
/*     */         }
/*     */       } 
/*     */     } }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class XLaplaceOperator2
/*     */     implements Operator2
/*     */   {
/*     */     private LocalDiffusionKernel _ldk;
/*     */     
/*     */     private Tensors2 _d;
/*     */     private float _t;
/*     */     private boolean[][] _m;
/*     */     private float[][] _w;
/*     */     private float[][] _z;
/*     */     
/*     */     XLaplaceOperator2(LocalDiffusionKernel ldk, Tensors2 d, float t, boolean[][] m) {
/* 335 */       this._ldk = ldk;
/* 336 */       this._d = d;
/* 337 */       this._t = t;
/* 338 */       this._m = m;
/* 339 */       this._w = new float[m.length][(m[0]).length];
/* 340 */       this._z = new float[m.length][(m[0]).length];
/*     */     }
/*     */     public void apply(float[][] x, float[][] y) {
/* 343 */       int n1 = (x[0]).length;
/* 344 */       int n2 = x.length;
/*     */       
/* 346 */       SplinesGridder2.smoothS(x, this._w);
/*     */       int i2;
/* 348 */       for (i2 = 0; i2 < n2; i2++) {
/* 349 */         for (int i1 = 0; i1 < n1; i1++)
/* 350 */           this._z[i2][i1] = this._m[i2][i1] ? this._w[i2][i1] : 0.0F; 
/*     */       } 
/* 352 */       SplinesGridder2.szero(y);
/* 353 */       this._ldk.apply(this._d, this._z, y);
/*     */       
/* 355 */       ArrayMath.mul(this._t, y, this._z);
/* 356 */       this._ldk.apply(this._d, y, this._z);
/*     */       
/* 358 */       for (i2 = 0; i2 < n2; i2++) {
/* 359 */         for (int i1 = 0; i1 < n1; i1++)
/* 360 */           y[i2][i1] = this._m[i2][i1] ? this._z[i2][i1] : this._w[i2][i1]; 
/*     */       } 
/* 362 */       SplinesGridder2.smoothS(y, y);
/*     */     }
/*     */     public void applyRhs(float[][] x, float[][] y) {
/* 365 */       int n1 = (x[0]).length;
/* 366 */       int n2 = x.length;
/*     */       int i2;
/* 368 */       for (i2 = 0; i2 < n2; i2++) {
/* 369 */         for (int i1 = 0; i1 < n1; i1++)
/* 370 */           this._z[i2][i1] = this._m[i2][i1] ? 0.0F : x[i2][i1]; 
/*     */       } 
/* 372 */       SplinesGridder2.szero(y);
/* 373 */       this._ldk.apply(this._d, this._z, y);
/*     */       
/* 375 */       ArrayMath.mul(this._t, y, this._z);
/* 376 */       this._ldk.apply(this._d, y, this._z);
/*     */       
/* 378 */       for (i2 = 0; i2 < n2; i2++) {
/* 379 */         for (int i1 = 0; i1 < n1; i1++)
/* 380 */           y[i2][i1] = this._m[i2][i1] ? -this._z[i2][i1] : x[i2][i1]; 
/*     */       } 
/* 382 */       SplinesGridder2.smoothS(y, y);
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
/*     */   private void solve(Operator2 a, Operator2 m, float[][] b, float[][] x) {
/* 394 */     this._residuals.clear();
/* 395 */     int n1 = (b[0]).length;
/* 396 */     int n2 = b.length;
/*     */ 
/*     */     
/* 399 */     float small = this._small * 100000.0F / n1 / n2;
/* 400 */     float[][] d = new float[n2][n1];
/* 401 */     float[][] q = new float[n2][n1];
/* 402 */     float[][] r = new float[n2][n1];
/* 403 */     float[][] s = new float[n2][n1];
/* 404 */     szero(x);
/* 405 */     scopy(b, r);
/* 406 */     m.apply(r, s);
/* 407 */     scopy(s, d);
/* 408 */     float delta = sdot(r, s);
/* 409 */     float rnorm = ArrayMath.sqrt(delta);
/* 410 */     float rnormBegin = rnorm;
/* 411 */     float rnormSmall = rnorm * small;
/* 412 */     this._residuals.add(Float.valueOf(1.0F));
/* 413 */     log.fine("solve: small=" + small);
/*     */     int iter;
/* 415 */     for (iter = 0; iter < this._niter && rnorm > rnormSmall; iter++) {
/* 416 */       log.finer("  iter=" + iter + " rnorm=" + (rnorm / rnormBegin));
/* 417 */       a.apply(d, q);
/* 418 */       float alpha = delta / sdot(d, q);
/* 419 */       saxpy(alpha, d, x);
/* 420 */       saxpy(-alpha, q, r);
/* 421 */       m.apply(r, s);
/* 422 */       float deltaOld = delta;
/* 423 */       delta = sdot(r, s);
/* 424 */       float beta = delta / deltaOld;
/* 425 */       sxpay(beta, s, d);
/* 426 */       rnorm = ArrayMath.sqrt(delta);
/* 427 */       this._residuals.add(Float.valueOf(rnorm / rnormBegin));
/*     */     } 
/* 429 */     log.fine("        iter=" + iter + " rnorm=" + (rnorm / rnormBegin));
/*     */   }
/*     */   private static void szero(float[][] x) {
/* 432 */     ArrayMath.zero(x);
/*     */   }
/*     */   private static void scopy(float[][] x, float[][] y) {
/* 435 */     ArrayMath.copy(x, y);
/*     */   }
/*     */   private static float sdot(float[][] x, float[][] y) {
/* 438 */     int n1 = (x[0]).length;
/* 439 */     int n2 = x.length;
/* 440 */     float d = 0.0F;
/* 441 */     for (int i2 = 0; i2 < n2; i2++) {
/* 442 */       float[] x2 = x[i2], y2 = y[i2];
/* 443 */       for (int i1 = 0; i1 < n1; i1++) {
/* 444 */         d += x2[i1] * y2[i1];
/*     */       }
/*     */     } 
/* 447 */     return d;
/*     */   }
/*     */   private static void saxpy(float a, float[][] x, float[][] y) {
/* 450 */     int n1 = (x[0]).length;
/* 451 */     int n2 = x.length;
/* 452 */     for (int i2 = 0; i2 < n2; i2++) {
/* 453 */       float[] x2 = x[i2], y2 = y[i2];
/* 454 */       for (int i1 = 0; i1 < n1; i1++)
/* 455 */         y2[i1] = y2[i1] + a * x2[i1]; 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void sxpay(float a, float[][] x, float[][] y) {
/* 460 */     int n1 = (x[0]).length;
/* 461 */     int n2 = x.length;
/* 462 */     for (int i2 = 0; i2 < n2; i2++) {
/* 463 */       float[] x2 = x[i2], y2 = y[i2];
/* 464 */       for (int i1 = 0; i1 < n1; i1++)
/* 465 */         y2[i1] = a * y2[i1] + x2[i1]; 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void smoothS(float[][] x, float[][] y) {
/* 470 */     int n1 = (x[0]).length;
/* 471 */     int n2 = x.length;
/* 472 */     int n1m = n1 - 1;
/* 473 */     int n2m = n2 - 1;
/* 474 */     float[][] t = new float[3][n1];
/* 475 */     ArrayMath.copy(x[0], t[0]);
/* 476 */     ArrayMath.copy(x[1], t[1]);
/* 477 */     for (int i2 = 0; i2 < n2; i2++) {
/* 478 */       int i2m = (i2 > 0) ? (i2 - 1) : 0;
/* 479 */       int i2p = (i2 < n2m) ? (i2 + 1) : n2m;
/* 480 */       int j2m = i2m % 3;
/* 481 */       int j2 = i2 % 3;
/* 482 */       int j2p = i2p % 3;
/* 483 */       ArrayMath.copy(x[i2p], t[j2p]);
/* 484 */       float[] x2m = t[j2m];
/* 485 */       float[] x2p = t[j2p];
/* 486 */       float[] x20 = t[j2];
/* 487 */       float[] y20 = y[i2];
/* 488 */       for (int i1 = 0; i1 < n1; i1++) {
/* 489 */         int i1m = (i1 > 0) ? (i1 - 1) : 0;
/* 490 */         int i1p = (i1 < n1m) ? (i1 + 1) : n1m;
/* 491 */         y20[i1] = 0.25F * x20[i1] + 0.125F * (x20[i1m] + x20[i1p] + x2m[i1] + x2p[i1]) + 0.0625F * (x2m[i1m] + x2m[i1p] + x2p[i1m] + x2p[i1p]);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void xsmoothS(float[][] x, float[][] y) {
/* 498 */     if (x == y) x = ArrayMath.copy(x); 
/* 499 */     int n1 = (x[0]).length;
/* 500 */     int n2 = x.length;
/* 501 */     for (int i2 = 0; i2 < n2; i2++) {
/* 502 */       int i2m = i2 - 1; if (i2m < 0) i2m += 2; 
/* 503 */       int i2p = i2 + 1; if (i2p >= n2) i2p -= 2; 
/* 504 */       float[] x2m = x[i2m];
/* 505 */       float[] x2p = x[i2p];
/* 506 */       float[] x20 = x[i2];
/* 507 */       float[] y20 = y[i2];
/* 508 */       for (int i1 = 0; i1 < n1; i1++) {
/* 509 */         int i1m = i1 - 1; if (i1m < 0) i1m += 2; 
/* 510 */         int i1p = i1 + 1; if (i1p >= n1) i1p -= 2; 
/* 511 */         y20[i1] = 0.25F * x20[i1] + 0.125F * (x20[i1m] + x20[i1p] + x2m[i1] + x2p[i1]) + 0.0625F * (x2m[i1m] + x2m[i1p] + x2p[i1m] + x2p[i1p]);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void xxsmoothS(float[][] x, float[][] y) {
/* 518 */     if (x == y)
/* 519 */       x = ArrayMath.copy(x); 
/* 520 */     ArrayMath.zero(y);
/* 521 */     int n1 = (x[0]).length;
/* 522 */     int n2 = x.length;
/* 523 */     for (int i2 = 1, m2 = 0; i2 < n2; i2++, m2++) {
/* 524 */       for (int i1 = 1, m1 = 0; i1 < n1; i1++, m1++) {
/* 525 */         float xs = 0.0625F * (x[i2][i1] + x[i2][m1] + x[m2][i1] + x[m2][m1]);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 530 */         y[i2][i1] = y[i2][i1] + xs;
/* 531 */         y[i2][m1] = y[i2][m1] + xs;
/* 532 */         y[m2][i1] = y[m2][i1] + xs;
/* 533 */         y[m2][m1] = y[m2][m1] + xs;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void testSmooth() {
/* 539 */     int n1 = 5;
/* 540 */     int n2 = 5;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 545 */     float[][] x = ArrayMath.zerofloat(n1, n2);
/* 546 */     float[][] y = ArrayMath.zerofloat(n1, n2);
/* 547 */     x[n2 - 1][n1 - 1] = 1.0F; x[0][n1 - 1] = 1.0F; x[n2 - 1][0] = 1.0F; x[0][0] = 1.0F;
/* 548 */     y[n2 - 1][n1 - 1] = 1.0F; y[0][n1 - 1] = 1.0F; y[n2 - 1][0] = 1.0F; y[0][0] = 1.0F;
/*     */ 
/*     */     
/* 551 */     float[][] sx = ArrayMath.zerofloat(n1, n2);
/* 552 */     float[][] sy = ArrayMath.zerofloat(n1, n2);
/* 553 */     smoothS(x, sx); smoothS(sx, sx);
/* 554 */     smoothS(y, sy); smoothS(sy, sy);
/* 555 */     ArrayMath.dump(sx);
/* 556 */     ArrayMath.dump(sy);
/* 557 */     float ysx = ArrayMath.sum(ArrayMath.mul(y, sx));
/* 558 */     float xsy = ArrayMath.sum(ArrayMath.mul(x, sy));
/* 559 */     System.out.println("ysx=" + ysx + " xsy=" + xsy);
/*     */   }
/*     */   private static void main(String[] args) {
/* 562 */     testSmooth();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/interp/SplinesGridder2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */