/*     */ package edu.mines.jtk.dsp;
/*     */ 
/*     */ import edu.mines.jtk.io.ArrayInputStream;
/*     */ import edu.mines.jtk.io.ArrayOutputStream;
/*     */ import edu.mines.jtk.util.ArrayMath;
/*     */ import edu.mines.jtk.util.UnitSphereSampling;
/*     */ import java.io.IOException;
/*     */ import java.io.InvalidClassException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EigenTensors3
/*     */   implements Tensors3, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private static final float AS_SET = 32767.0F;
/*     */   private static final float AS_GET = 3.051851E-5F;
/*     */   private static UnitSphereSampling _uss;
/*     */   private boolean _compressed;
/*     */   private int _n1;
/*     */   private int _n2;
/*     */   private int _n3;
/*     */   private float[][][] _as;
/*     */   private short[][][] _bu;
/*     */   private short[][][] _bw;
/*     */   private short[][][] _iu;
/*     */   private short[][][] _iw;
/*     */   private float[][][] _au;
/*     */   private float[][][] _aw;
/*     */   private float[][][] _u1;
/*     */   private float[][][] _u2;
/*     */   private float[][][] _w1;
/*     */   private float[][][] _w2;
/*     */   
/*     */   public EigenTensors3(int n1, int n2, int n3, boolean compressed) {
/*  74 */     this._n1 = n1;
/*  75 */     this._n2 = n2;
/*  76 */     this._n3 = n3;
/*  77 */     this._compressed = compressed;
/*  78 */     if (compressed) {
/*  79 */       this._bu = new short[n3][n2][n1];
/*  80 */       this._bw = new short[n3][n2][n1];
/*  81 */       this._iu = new short[n3][n2][n1];
/*  82 */       this._iw = new short[n3][n2][n1];
/*  83 */       if (_uss == null)
/*  84 */         _uss = new UnitSphereSampling(16); 
/*     */     } else {
/*  86 */       this._au = new float[n3][n2][n1];
/*  87 */       this._aw = new float[n3][n2][n1];
/*  88 */       this._u1 = new float[n3][n2][n1];
/*  89 */       this._u2 = new float[n3][n2][n1];
/*  90 */       this._w1 = new float[n3][n2][n1];
/*  91 */       this._w2 = new float[n3][n2][n1];
/*     */     } 
/*  93 */     this._as = new float[n3][n2][n1];
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
/*     */   public EigenTensors3(float[][][] u1, float[][][] u2, float[][][] w1, float[][][] w2, float[][][] au, float[][][] av, float[][][] aw, boolean compressed) {
/* 115 */     this((u1[0][0]).length, (u1[0]).length, u1.length, compressed);
/* 116 */     for (int i3 = 0; i3 < this._n3; i3++) {
/* 117 */       for (int i2 = 0; i2 < this._n2; i2++) {
/* 118 */         for (int i1 = 0; i1 < this._n1; i1++) {
/* 119 */           float aui = au[i3][i2][i1];
/* 120 */           float avi = av[i3][i2][i1];
/* 121 */           float awi = aw[i3][i2][i1];
/* 122 */           float u1i = u1[i3][i2][i1];
/* 123 */           float u2i = u2[i3][i2][i1];
/* 124 */           float u3i = c3(u1i, u2i);
/* 125 */           float w1i = w1[i3][i2][i1];
/* 126 */           float w2i = w2[i3][i2][i1];
/* 127 */           float w3i = c3(w1i, w2i);
/* 128 */           setEigenvalues(i1, i2, i3, aui, avi, awi);
/* 129 */           setEigenvectorU(i1, i2, i3, u1i, u2i, u3i);
/* 130 */           setEigenvectorW(i1, i2, i3, w1i, w2i, w3i);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EigenTensors3(EigenTensors3 t) {
/* 141 */     this(t._n1, t._n2, t._n3, t._compressed);
/* 142 */     float[] a = new float[3];
/* 143 */     float[] u = new float[3];
/* 144 */     float[] w = new float[3];
/* 145 */     for (int i3 = 0; i3 < this._n3; i3++) {
/* 146 */       for (int i2 = 0; i2 < this._n2; i2++) {
/* 147 */         for (int i1 = 0; i1 < this._n1; i1++) {
/* 148 */           t.getEigenvalues(i1, i2, i3, a);
/* 149 */           t.getEigenvectorU(i1, i2, i3, u);
/* 150 */           t.getEigenvectorW(i1, i2, i3, w);
/* 151 */           setEigenvalues(i1, i2, i3, a);
/* 152 */           setEigenvectorU(i1, i2, i3, u);
/* 153 */           setEigenvectorW(i1, i2, i3, w);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getN1() {
/* 164 */     return this._n1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getN2() {
/* 172 */     return this._n2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getN3() {
/* 180 */     return this._n3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getTensor(int i1, int i2, int i3, float[] a) {
/* 191 */     float au, aw, u1, u2, u3, w1, w2, w3, asum = this._as[i3][i2][i1];
/*     */     
/* 193 */     if (this._compressed) {
/* 194 */       float ascale = asum * 3.051851E-5F;
/* 195 */       au = ascale * this._bu[i3][i2][i1];
/* 196 */       aw = ascale * this._bw[i3][i2][i1];
/* 197 */       float[] u = _uss.getPoint(this._iu[i3][i2][i1]);
/* 198 */       u1 = u[0]; u2 = u[1]; u3 = u[2];
/* 199 */       float[] w = _uss.getPoint(this._iw[i3][i2][i1]);
/* 200 */       w1 = w[0]; w2 = w[1]; w3 = w[2];
/*     */     } else {
/* 202 */       au = this._au[i3][i2][i1];
/* 203 */       aw = this._aw[i3][i2][i1];
/* 204 */       u1 = this._u1[i3][i2][i1];
/* 205 */       u2 = this._u2[i3][i2][i1];
/* 206 */       u3 = c3(u1, u2);
/* 207 */       w1 = this._w1[i3][i2][i1];
/* 208 */       w2 = this._w2[i3][i2][i1];
/* 209 */       w3 = c3(w1, w2);
/*     */     } 
/* 211 */     float av = asum - au - aw;
/* 212 */     au -= av;
/* 213 */     aw -= av;
/* 214 */     a[0] = au * u1 * u1 + aw * w1 * w1 + av;
/* 215 */     a[1] = au * u1 * u2 + aw * w1 * w2;
/* 216 */     a[2] = au * u1 * u3 + aw * w1 * w3;
/* 217 */     a[3] = au * u2 * u2 + aw * w2 * w2 + av;
/* 218 */     a[4] = au * u2 * u3 + aw * w2 * w3;
/* 219 */     a[5] = au * u3 * u3 + aw * w3 * w3 + av;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] getTensor(int i1, int i2, int i3) {
/* 230 */     float[] a = new float[6];
/* 231 */     getTensor(i1, i2, i3, a);
/* 232 */     return a;
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
/*     */   public void getEigenvalues(int i1, int i2, int i3, float[] a) {
/* 244 */     float au, aw, asum = this._as[i3][i2][i1];
/*     */     
/* 246 */     if (this._compressed) {
/* 247 */       float ascale = asum * 3.051851E-5F;
/* 248 */       au = ascale * this._bu[i3][i2][i1];
/* 249 */       aw = ascale * this._bw[i3][i2][i1];
/*     */     } else {
/* 251 */       au = this._au[i3][i2][i1];
/* 252 */       aw = this._aw[i3][i2][i1];
/*     */     } 
/* 254 */     a[0] = au;
/* 255 */     a[1] = asum - au - aw;
/* 256 */     a[2] = aw;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] getEigenvalues(int i1, int i2, int i3) {
/* 267 */     float[] a = new float[3];
/* 268 */     getEigenvalues(i1, i2, i3, a);
/* 269 */     return a;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getEigenvalues(float[][][] au, float[][][] av, float[][][] aw) {
/* 279 */     float[] auvw = new float[3];
/* 280 */     for (int i3 = 0; i3 < this._n3; i3++) {
/* 281 */       for (int i2 = 0; i2 < this._n2; i2++) {
/* 282 */         for (int i1 = 0; i1 < this._n1; i1++) {
/* 283 */           getEigenvalues(i1, i2, i3, auvw);
/* 284 */           au[i3][i2][i1] = auvw[0];
/* 285 */           av[i3][i2][i1] = auvw[1];
/* 286 */           aw[i3][i2][i1] = auvw[2];
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
/*     */   public void getEigenvectorU(int i1, int i2, int i3, float[] u) {
/* 300 */     if (this._compressed) {
/* 301 */       float[] ui = _uss.getPoint(this._iu[i3][i2][i1]);
/* 302 */       u[0] = ui[0];
/* 303 */       u[1] = ui[1];
/* 304 */       u[2] = ui[2];
/*     */     } else {
/* 306 */       u[0] = this._u1[i3][i2][i1];
/* 307 */       u[1] = this._u2[i3][i2][i1];
/* 308 */       u[2] = c3(u[0], u[1]);
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
/*     */   public float[] getEigenvectorU(int i1, int i2, int i3) {
/* 320 */     float[] u = new float[3];
/* 321 */     getEigenvectorU(i1, i2, i3, u);
/* 322 */     return u;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getEigenvectorV(int i1, int i2, int i3, float[] v) {
/* 333 */     float[] u = getEigenvectorU(i1, i2, i3);
/* 334 */     float[] w = getEigenvectorW(i1, i2, i3);
/* 335 */     v[0] = w[1] * u[2] - w[2] * u[1];
/* 336 */     v[1] = w[2] * u[0] - w[0] * u[2];
/* 337 */     v[2] = w[0] * u[1] - w[1] * u[0];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] getEigenvectorV(int i1, int i2, int i3) {
/* 348 */     float[] v = new float[3];
/* 349 */     getEigenvectorV(i1, i2, i3, v);
/* 350 */     return v;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getEigenvectorW(int i1, int i2, int i3, float[] w) {
/* 361 */     if (this._compressed) {
/* 362 */       float[] wi = _uss.getPoint(this._iw[i3][i2][i1]);
/* 363 */       w[0] = wi[0];
/* 364 */       w[1] = wi[1];
/* 365 */       w[2] = wi[2];
/*     */     } else {
/* 367 */       w[0] = this._w1[i3][i2][i1];
/* 368 */       w[1] = this._w2[i3][i2][i1];
/* 369 */       w[2] = c3(w[0], w[1]);
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
/*     */   public float[] getEigenvectorW(int i1, int i2, int i3) {
/* 381 */     float[] w = new float[3];
/* 382 */     getEigenvectorW(i1, i2, i3, w);
/* 383 */     return w;
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
/*     */   public void setTensor(int i1, int i2, int i3, float[] a) {
/* 397 */     setTensor(i1, i2, i3, a[0], a[1], a[2], a[3], a[4], a[5]);
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
/*     */   public void setTensor(int i1, int i2, int i3, float a11, float a12, float a13, float a22, float a23, float a33) {
/* 419 */     double[][] aa = { { a11, a12, a13 }, { a12, a22, a23 }, { a13, a23, a33 } };
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 424 */     double[][] vv = new double[3][3];
/* 425 */     double[] ev = new double[3];
/*     */     
/* 427 */     Eigen.solveSymmetric33(aa, vv, ev);
/* 428 */     double[] u = vv[0];
/* 429 */     double[] w = vv[2];
/* 430 */     float u1 = (float)u[0];
/* 431 */     float u2 = (float)u[1];
/* 432 */     float u3 = (float)u[2];
/* 433 */     float w1 = (float)w[0];
/* 434 */     float w2 = (float)w[1];
/* 435 */     float w3 = (float)w[2];
/* 436 */     float au = (float)ev[0]; if (au < 0.0F) au = 0.0F; 
/* 437 */     float av = (float)ev[1]; if (av < 0.0F) av = 0.0F; 
/* 438 */     float aw = (float)ev[2]; if (aw < 0.0F) aw = 0.0F; 
/* 439 */     setEigenvectorU(i1, i2, i3, u1, u2, u3);
/* 440 */     setEigenvectorW(i1, i2, i3, w1, w2, w3);
/* 441 */     setEigenvalues(i1, i2, i3, au, av, aw);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEigenvalues(float au, float av, float aw) {
/* 451 */     float as = au + av + aw;
/* 452 */     if (this._compressed) {
/* 453 */       float ascale = (as > 0.0F) ? (32767.0F / as) : 0.0F;
/* 454 */       short bu = (short)(int)(au * ascale + 0.5F);
/* 455 */       short bw = (short)(int)(aw * ascale + 0.5F);
/* 456 */       ArrayMath.fill(bu, this._bu);
/* 457 */       ArrayMath.fill(bw, this._bw);
/*     */     } else {
/* 459 */       ArrayMath.fill(au, this._au);
/* 460 */       ArrayMath.fill(aw, this._aw);
/*     */     } 
/* 462 */     ArrayMath.fill(as, this._as);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEigenvalues(float[][][] au, float[][][] av, float[][][] aw) {
/* 472 */     for (int i3 = 0; i3 < this._n3; i3++) {
/* 473 */       for (int i2 = 0; i2 < this._n2; i2++) {
/* 474 */         for (int i1 = 0; i1 < this._n1; i1++) {
/* 475 */           float aui = au[i3][i2][i1];
/* 476 */           float avi = av[i3][i2][i1];
/* 477 */           float awi = aw[i3][i2][i1];
/* 478 */           setEigenvalues(i1, i2, i3, aui, avi, awi);
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
/*     */   public void setEigenvalues(int i1, int i2, int i3, float au, float av, float aw) {
/* 496 */     float asum = au + av + aw;
/* 497 */     if (this._compressed) {
/* 498 */       float ascale = (asum > 0.0F) ? (32767.0F / asum) : 0.0F;
/* 499 */       this._bu[i3][i2][i1] = (short)(int)(au * ascale + 0.5F);
/* 500 */       this._bw[i3][i2][i1] = (short)(int)(aw * ascale + 0.5F);
/*     */     } else {
/* 502 */       this._au[i3][i2][i1] = au;
/* 503 */       this._aw[i3][i2][i1] = aw;
/*     */     } 
/* 505 */     this._as[i3][i2][i1] = asum;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEigenvalues(int i1, int i2, int i3, float[] a) {
/* 516 */     setEigenvalues(i1, i2, i3, a[0], a[1], a[2]);
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
/*     */   public void setEigenvectorU(int i1, int i2, int i3, float u1, float u2, float u3) {
/* 534 */     if (u3 < 0.0F) {
/* 535 */       u1 = -u1;
/* 536 */       u2 = -u2;
/* 537 */       u3 = -u3;
/*     */     } 
/* 539 */     if (this._compressed) {
/* 540 */       this._iu[i3][i2][i1] = (short)_uss.getIndex(u1, u2, u3);
/*     */     } else {
/* 542 */       this._u1[i3][i2][i1] = u1;
/* 543 */       this._u2[i3][i2][i1] = u2;
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
/*     */   public void setEigenvectorU(int i1, int i2, int i3, float[] u) {
/* 558 */     setEigenvectorU(i1, i2, i3, u[0], u[1], u[2]);
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
/*     */   public void setEigenvectorW(int i1, int i2, int i3, float w1, float w2, float w3) {
/* 576 */     if (w3 < 0.0F) {
/* 577 */       w1 = -w1;
/* 578 */       w2 = -w2;
/* 579 */       w3 = -w3;
/*     */     } 
/* 581 */     if (this._compressed) {
/* 582 */       this._iw[i3][i2][i1] = (short)_uss.getIndex(w1, w2, w3);
/*     */     } else {
/* 584 */       this._w1[i3][i2][i1] = w1;
/* 585 */       this._w2[i3][i2][i1] = w2;
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
/*     */   public void setEigenvectorW(int i1, int i2, int i3, float[] w) {
/* 600 */     setEigenvectorW(i1, i2, i3, w[0], w[1], w[2]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void scale(float[][][] s) {
/* 608 */     float[] a = new float[3];
/* 609 */     for (int i3 = 0; i3 < this._n3; i3++) {
/* 610 */       for (int i2 = 0; i2 < this._n2; i2++) {
/* 611 */         for (int i1 = 0; i1 < this._n1; i1++) {
/* 612 */           float si = s[i3][i2][i1];
/* 613 */           getEigenvalues(i1, i2, i3, a);
/* 614 */           a[0] = a[0] * si;
/* 615 */           a[1] = a[1] * si;
/* 616 */           a[2] = a[2] * si;
/* 617 */           setEigenvalues(i1, i2, i3, a);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void invert() {
/* 628 */     float[] a = new float[3];
/* 629 */     for (int i3 = 0; i3 < this._n3; i3++) {
/* 630 */       for (int i2 = 0; i2 < this._n2; i2++) {
/* 631 */         for (int i1 = 0; i1 < this._n1; i1++) {
/* 632 */           getEigenvalues(i1, i2, i3, a);
/* 633 */           a[0] = 1.0F / a[0];
/* 634 */           a[1] = 1.0F / a[1];
/* 635 */           a[2] = 1.0F / a[2];
/* 636 */           setEigenvalues(i1, i2, i3, a);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void invertStructure(double p0, double p1, double p2) {
/* 674 */     float[] a = new float[3];
/* 675 */     float amax = 0.0F;
/* 676 */     float amin = Float.MAX_VALUE;
/* 677 */     for (int i3 = 0; i3 < this._n3; i3++) {
/* 678 */       for (int i2 = 0; i2 < this._n2; i2++) {
/* 679 */         for (int i1 = 0; i1 < this._n1; i1++) {
/* 680 */           getEigenvalues(i1, i2, i3, a);
/* 681 */           float aui = a[0], avi = a[1], awi = a[2];
/* 682 */           if (awi < 0.0F) awi = 0.0F; 
/* 683 */           if (avi < awi) avi = awi; 
/* 684 */           if (aui < avi) aui = avi; 
/* 685 */           if (awi < amin) amin = awi; 
/* 686 */           if (aui > amax) amax = aui; 
/* 687 */           setEigenvalues(i1, i2, i3, aui, avi, awi);
/*     */         } 
/*     */       } 
/*     */     } 
/* 691 */     float aeps = ArrayMath.max(1.4E-43F, 1.1920929E-7F * amax);
/* 692 */     amin += aeps;
/* 693 */     amax += aeps;
/* 694 */     float fp0 = (float)p0;
/* 695 */     float fp1 = (float)p1;
/* 696 */     float fp2 = (float)p2;
/* 697 */     for (int i = 0; i < this._n3; i++) {
/* 698 */       for (int i2 = 0; i2 < this._n2; i2++) {
/* 699 */         for (int i1 = 0; i1 < this._n1; i1++) {
/* 700 */           getEigenvalues(i1, i2, i, a);
/* 701 */           float aui = a[0], avi = a[1], awi = a[2];
/* 702 */           aui += aeps;
/* 703 */           avi += aeps;
/* 704 */           awi += aeps;
/* 705 */           float a0i = ArrayMath.pow(amin / awi, fp0);
/* 706 */           float a1i = ArrayMath.pow(awi / avi, fp1);
/* 707 */           float a2i = ArrayMath.pow(avi / aui, fp2);
/* 708 */           aui = a0i * a1i * a2i;
/* 709 */           avi = a0i * a1i;
/* 710 */           awi = a0i;
/* 711 */           setEigenvalues(i1, i2, i, aui, avi, awi);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static float c3(float c1, float c2) {
/* 739 */     float c3s = 1.0F - c1 * c1 - c2 * c2;
/* 740 */     return (c3s > 0.0F) ? (float)Math.sqrt(c3s) : 0.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
/* 746 */     int format = ois.readInt();
/* 747 */     if (format == 1) {
/* 748 */       boolean compressed = this._compressed = ois.readBoolean();
/* 749 */       int n1 = this._n1 = ois.readInt();
/* 750 */       int n2 = this._n2 = ois.readInt();
/* 751 */       int n3 = this._n3 = ois.readInt();
/*     */       
/* 753 */       ArrayInputStream ais = new ArrayInputStream(ois);
/* 754 */       if (compressed) {
/* 755 */         this._bu = new short[n3][n2][n1];
/* 756 */         this._bw = new short[n3][n2][n1];
/* 757 */         this._iu = new short[n3][n2][n1];
/* 758 */         this._iw = new short[n3][n2][n1];
/* 759 */         ais.readShorts(this._bu);
/* 760 */         ais.readShorts(this._bw);
/* 761 */         ais.readShorts(this._iu);
/* 762 */         ais.readShorts(this._iw);
/* 763 */         if (_uss == null)
/* 764 */           _uss = new UnitSphereSampling(16); 
/*     */       } else {
/* 766 */         this._au = new float[n3][n2][n1];
/* 767 */         this._aw = new float[n3][n2][n1];
/* 768 */         this._u1 = new float[n3][n2][n1];
/* 769 */         this._u2 = new float[n3][n2][n1];
/* 770 */         this._w1 = new float[n3][n2][n1];
/* 771 */         this._w2 = new float[n3][n2][n1];
/* 772 */         ais.readFloats(this._au);
/* 773 */         ais.readFloats(this._aw);
/* 774 */         ais.readFloats(this._u1);
/* 775 */         ais.readFloats(this._u2);
/* 776 */         ais.readFloats(this._w1);
/* 777 */         ais.readFloats(this._w2);
/*     */       } 
/* 779 */       this._as = new float[n3][n2][n1];
/* 780 */       ais.readFloats(this._as);
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */ 
/*     */       
/* 788 */       throw new InvalidClassException("invalid format");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream oos) throws IOException {
/* 795 */     oos.writeInt(1);
/* 796 */     oos.writeBoolean(this._compressed);
/* 797 */     oos.writeInt(this._n1);
/* 798 */     oos.writeInt(this._n2);
/* 799 */     oos.writeInt(this._n3);
/*     */     
/* 801 */     ArrayOutputStream aos = new ArrayOutputStream(oos);
/* 802 */     if (this._compressed) {
/* 803 */       aos.writeShorts(this._bu);
/* 804 */       aos.writeShorts(this._bw);
/* 805 */       aos.writeShorts(this._iu);
/* 806 */       aos.writeShorts(this._iw);
/*     */     } else {
/* 808 */       aos.writeFloats(this._au);
/* 809 */       aos.writeFloats(this._aw);
/* 810 */       aos.writeFloats(this._u1);
/* 811 */       aos.writeFloats(this._u2);
/* 812 */       aos.writeFloats(this._w1);
/* 813 */       aos.writeFloats(this._w2);
/*     */     } 
/* 815 */     aos.writeFloats(this._as);
/* 816 */     aos.flush();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/dsp/EigenTensors3.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */