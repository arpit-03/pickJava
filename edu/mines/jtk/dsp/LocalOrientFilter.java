/*     */ package edu.mines.jtk.dsp;
/*     */ 
/*     */ import edu.mines.jtk.util.ArrayMath;
/*     */ import edu.mines.jtk.util.Parallel;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LocalOrientFilter
/*     */ {
/*     */   private RecursiveGaussianFilter _rgfGradient1;
/*     */   private RecursiveGaussianFilter _rgfGradient2;
/*     */   private RecursiveGaussianFilter _rgfGradient3;
/*     */   private RecursiveGaussianFilter _rgfSmoother1;
/*     */   private RecursiveGaussianFilter _rgfSmoother2;
/*     */   private RecursiveGaussianFilter _rgfSmoother3;
/*     */   
/*     */   public LocalOrientFilter(double sigma) {
/*  71 */     this(sigma, sigma, sigma);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LocalOrientFilter(double sigma1, double sigma2) {
/*  81 */     this(sigma1, sigma2, sigma2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LocalOrientFilter(double sigma1, double sigma2, double sigma3) {
/*  91 */     this._rgfSmoother1 = (sigma1 >= 1.0D) ? new RecursiveGaussianFilter(sigma1) : null;
/*  92 */     if (sigma2 == sigma1) {
/*  93 */       this._rgfSmoother2 = this._rgfSmoother1;
/*     */     } else {
/*  95 */       this._rgfSmoother2 = (sigma2 >= 1.0D) ? new RecursiveGaussianFilter(sigma2) : null;
/*     */     } 
/*  97 */     if (sigma3 == sigma2) {
/*  98 */       this._rgfSmoother3 = this._rgfSmoother2;
/*     */     } else {
/* 100 */       this._rgfSmoother3 = (sigma3 >= 1.0D) ? new RecursiveGaussianFilter(sigma3) : null;
/*     */     } 
/* 102 */     setGradientSmoothing(1.0D);
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
/*     */   public void setGradientSmoothing(double sigma) {
/* 114 */     setGradientSmoothing(sigma, sigma, sigma);
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
/*     */   public void setGradientSmoothing(double sigma1, double sigma2) {
/* 127 */     setGradientSmoothing(sigma1, sigma2, sigma2);
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
/*     */   public void setGradientSmoothing(double sigma1, double sigma2, double sigma3) {
/* 143 */     this._rgfGradient1 = new RecursiveGaussianFilter(sigma1);
/* 144 */     if (sigma2 == sigma1) {
/* 145 */       this._rgfGradient2 = this._rgfGradient1;
/*     */     } else {
/* 147 */       this._rgfGradient2 = new RecursiveGaussianFilter(sigma2);
/*     */     } 
/* 149 */     if (sigma3 == sigma2) {
/* 150 */       this._rgfGradient3 = this._rgfGradient2;
/*     */     } else {
/* 152 */       this._rgfGradient3 = new RecursiveGaussianFilter(sigma3);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void applyForTheta(float[][] x, float[][] theta) {
/* 162 */     apply(x, theta, (float[][])null, (float[][])null, (float[][])null, (float[][])null, (float[][])null, (float[][])null, (float[][])null);
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
/*     */   public void applyForNormal(float[][] x, float[][] u1, float[][] u2) {
/* 177 */     apply(x, (float[][])null, u1, u2, (float[][])null, (float[][])null, (float[][])null, (float[][])null, (float[][])null);
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
/*     */   public void applyForNormalLinear(float[][] x, float[][] u1, float[][] u2, float[][] el) {
/* 195 */     apply(x, (float[][])null, u1, u2, (float[][])null, (float[][])null, (float[][])null, (float[][])null, el);
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
/*     */   public EigenTensors2 applyForTensors(float[][] x) {
/* 209 */     int n1 = (x[0]).length;
/* 210 */     int n2 = x.length;
/* 211 */     float[][] u1 = new float[n2][n1];
/* 212 */     float[][] u2 = new float[n2][n1];
/* 213 */     float[][] eu = new float[n2][n1];
/* 214 */     float[][] ev = new float[n2][n1];
/* 215 */     apply(x, (float[][])null, u1, u2, (float[][])null, (float[][])null, eu, ev, (float[][])null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 221 */     return new EigenTensors2(u1, u2, eu, ev);
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
/*     */   public void apply(float[][] x, float[][] theta, float[][] u1, float[][] u2, float[][] v1, float[][] v2, float[][] eu, float[][] ev, float[][] el) {
/* 245 */     float[][][] t = new float[8][][];
/* 246 */     int nt = 0;
/* 247 */     if (theta != null) t[nt++] = theta; 
/* 248 */     if (u1 != null) t[nt++] = u1; 
/* 249 */     if (u2 != null) t[nt++] = u2; 
/* 250 */     if (v1 != null) t[nt++] = v1; 
/* 251 */     if (v2 != null) t[nt++] = v2; 
/* 252 */     if (eu != null) t[nt++] = eu; 
/* 253 */     if (ev != null) t[nt++] = ev; 
/* 254 */     if (el != null) t[nt++] = el;
/*     */ 
/*     */     
/* 257 */     int n1 = (x[0]).length;
/* 258 */     int n2 = x.length;
/* 259 */     float[][] g1 = (nt > 0) ? t[0] : new float[n2][n1];
/* 260 */     float[][] g2 = (nt > 1) ? t[1] : new float[n2][n1];
/* 261 */     this._rgfGradient1.apply10(x, g1);
/* 262 */     this._rgfGradient2.apply01(x, g2);
/*     */ 
/*     */     
/* 265 */     float[][] g11 = g1;
/* 266 */     float[][] g22 = g2;
/* 267 */     float[][] g12 = (nt > 2) ? t[2] : new float[n2][n1];
/* 268 */     for (int i2 = 0; i2 < n2; i2++) {
/* 269 */       for (int i1 = 0; i1 < n1; i1++) {
/* 270 */         float g1i = g1[i2][i1];
/* 271 */         float g2i = g2[i2][i1];
/* 272 */         g11[i2][i1] = g1i * g1i;
/* 273 */         g22[i2][i1] = g2i * g2i;
/* 274 */         g12[i2][i1] = g1i * g2i;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 279 */     if (this._rgfSmoother1 != null || this._rgfSmoother2 != null) {
/* 280 */       float[][] h = (nt > 3) ? t[3] : new float[n2][n1];
/* 281 */       float[][][] gs = { g11, g22, g12 };
/* 282 */       for (float[][] g : gs) {
/* 283 */         if (this._rgfSmoother1 != null) {
/* 284 */           this._rgfSmoother1.apply0X(g, h);
/*     */         } else {
/* 286 */           ArrayMath.copy(g, h);
/*     */         } 
/* 288 */         if (this._rgfSmoother2 != null) {
/* 289 */           this._rgfSmoother2.applyX0(h, g);
/*     */         } else {
/* 291 */           ArrayMath.copy(h, g);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 297 */     float[][] a = new float[2][2];
/* 298 */     float[][] z = new float[2][2];
/* 299 */     float[] e = new float[2];
/* 300 */     for (int i = 0; i < n2; i++) {
/* 301 */       for (int i1 = 0; i1 < n1; i1++) {
/* 302 */         a[0][0] = g11[i][i1];
/* 303 */         a[0][1] = g12[i][i1];
/* 304 */         a[1][0] = g12[i][i1];
/* 305 */         a[1][1] = g22[i][i1];
/* 306 */         Eigen.solveSymmetric22(a, z, e);
/* 307 */         float u1i = z[0][0];
/* 308 */         float u2i = z[0][1];
/* 309 */         if (u1i < 0.0F) {
/* 310 */           u1i = -u1i;
/* 311 */           u2i = -u2i;
/*     */         } 
/* 313 */         float v1i = -u2i;
/* 314 */         float v2i = u1i;
/* 315 */         float eui = e[0];
/* 316 */         float evi = e[1];
/* 317 */         if (evi < 0.0F) evi = 0.0F; 
/* 318 */         if (eui < evi) eui = evi; 
/* 319 */         if (theta != null) theta[i][i1] = ArrayMath.asin(u2i); 
/* 320 */         if (u1 != null) u1[i][i1] = u1i; 
/* 321 */         if (u2 != null) u2[i][i1] = u2i; 
/* 322 */         if (v1 != null) v1[i][i1] = v1i; 
/* 323 */         if (v2 != null) v2[i][i1] = v2i; 
/* 324 */         if (eu != null) eu[i][i1] = eui; 
/* 325 */         if (ev != null) ev[i][i1] = evi; 
/* 326 */         if (el != null) el[i][i1] = (eui - evi) / eui;
/*     */       
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
/*     */   public void applyForThetaPhi(float[][][] x, float[][][] theta, float[][][] phi) {
/* 340 */     apply(x, theta, phi, (float[][][])null, (float[][][])null, (float[][][])null, (float[][][])null, (float[][][])null, (float[][][])null, (float[][][])null, (float[][][])null, (float[][][])null, (float[][][])null, (float[][][])null, (float[][][])null, (float[][][])null, (float[][][])null);
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
/*     */   public void applyForNormal(float[][][] x, float[][][] u1, float[][][] u2, float[][][] u3) {
/* 359 */     apply(x, (float[][][])null, (float[][][])null, u1, u2, u3, (float[][][])null, (float[][][])null, (float[][][])null, (float[][][])null, (float[][][])null, (float[][][])null, (float[][][])null, (float[][][])null, (float[][][])null, (float[][][])null, (float[][][])null);
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
/*     */   public void applyForNormalPlanar(float[][][] x, float[][][] u1, float[][][] u2, float[][][] u3, float[][][] ep) {
/* 380 */     apply(x, (float[][][])null, (float[][][])null, u1, u2, u3, (float[][][])null, (float[][][])null, (float[][][])null, (float[][][])null, (float[][][])null, (float[][][])null, (float[][][])null, (float[][][])null, (float[][][])null, ep, (float[][][])null);
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
/*     */   public void applyForInline(float[][][] x, float[][][] w1, float[][][] w2, float[][][] w3) {
/* 399 */     apply(x, (float[][][])null, (float[][][])null, (float[][][])null, (float[][][])null, (float[][][])null, (float[][][])null, (float[][][])null, (float[][][])null, w1, w2, w3, (float[][][])null, (float[][][])null, (float[][][])null, (float[][][])null, (float[][][])null);
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
/*     */   public void applyForInlineLinear(float[][][] x, float[][][] w1, float[][][] w2, float[][][] w3, float[][][] el) {
/* 421 */     apply(x, (float[][][])null, (float[][][])null, (float[][][])null, (float[][][])null, (float[][][])null, (float[][][])null, (float[][][])null, (float[][][])null, w1, w2, w3, (float[][][])null, (float[][][])null, (float[][][])null, (float[][][])null, el);
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
/*     */   public EigenTensors3 applyForTensors(float[][][] x) {
/* 436 */     return applyForTensors(x, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EigenTensors3 applyForTensors(float[][][] x, boolean compressed) {
/* 446 */     int n1 = (x[0][0]).length;
/* 447 */     int n2 = (x[0]).length;
/* 448 */     int n3 = x.length;
/* 449 */     float[][][] u2 = new float[n3][n2][n1];
/* 450 */     float[][][] u3 = new float[n3][n2][n1];
/* 451 */     float[][][] w1 = new float[n3][n2][n1];
/* 452 */     float[][][] w2 = new float[n3][n2][n1];
/* 453 */     float[][][] eu = new float[n3][n2][n1];
/* 454 */     float[][][] ev = new float[n3][n2][n1];
/* 455 */     float[][][] ew = new float[n3][n2][n1];
/* 456 */     apply(x, (float[][][])null, (float[][][])null, (float[][][])null, u2, u3, (float[][][])null, (float[][][])null, (float[][][])null, w1, w2, (float[][][])null, eu, ev, ew, (float[][][])null, (float[][][])null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 465 */     float[][][] u1 = u3;
/* 466 */     for (int i3 = 0; i3 < n3; i3++) {
/* 467 */       for (int i2 = 0; i2 < n2; i2++) {
/* 468 */         for (int i1 = 0; i1 < n1; i1++) {
/* 469 */           float u2i = u2[i3][i2][i1];
/* 470 */           float u3i = u3[i3][i2][i1];
/* 471 */           float u1s = 1.0F - u2i * u2i - u3i * u3i;
/* 472 */           float u1i = (u1s > 0.0F) ? ArrayMath.sqrt(u1s) : 0.0F;
/* 473 */           if (u3i < 0.0F) {
/* 474 */             u1i = -u1i;
/* 475 */             u2i = -u2i;
/*     */           } 
/* 477 */           u1[i3][i2][i1] = u1i;
/* 478 */           u2[i3][i2][i1] = u2i;
/*     */         } 
/*     */       } 
/*     */     } 
/* 482 */     return new EigenTensors3(u1, u2, w1, w2, eu, ev, ew, compressed);
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
/*     */   public void apply(float[][][] x, float[][][] theta, float[][][] phi, float[][][] u1, float[][][] u2, float[][][] u3, float[][][] v1, float[][][] v2, float[][][] v3, float[][][] w1, float[][][] w2, float[][][] w3, float[][][] eu, float[][][] ev, float[][][] ew, float[][][] ep, float[][][] el) {
/* 515 */     float[][][][] t = new float[16][][][];
/* 516 */     int nt = 0;
/* 517 */     if (theta != null) t[nt++] = theta; 
/* 518 */     if (phi != null) t[nt++] = phi; 
/* 519 */     if (u1 != null) t[nt++] = u1; 
/* 520 */     if (u2 != null) t[nt++] = u2; 
/* 521 */     if (u3 != null) t[nt++] = u3; 
/* 522 */     if (v1 != null) t[nt++] = v1; 
/* 523 */     if (v2 != null) t[nt++] = v2; 
/* 524 */     if (v3 != null) t[nt++] = v3; 
/* 525 */     if (w1 != null) t[nt++] = w1; 
/* 526 */     if (w2 != null) t[nt++] = w2; 
/* 527 */     if (w3 != null) t[nt++] = w3; 
/* 528 */     if (eu != null) t[nt++] = eu; 
/* 529 */     if (ev != null) t[nt++] = ev; 
/* 530 */     if (ew != null) t[nt++] = ew; 
/* 531 */     if (ep != null) t[nt++] = ep; 
/* 532 */     if (el != null) t[nt++] = el;
/*     */ 
/*     */     
/* 535 */     int n1 = (x[0][0]).length;
/* 536 */     int n2 = (x[0]).length;
/* 537 */     int n3 = x.length;
/* 538 */     float[][][] g1 = (nt > 0) ? t[0] : new float[n3][n2][n1];
/* 539 */     float[][][] g2 = (nt > 1) ? t[1] : new float[n3][n2][n1];
/* 540 */     float[][][] g3 = (nt > 2) ? t[2] : new float[n3][n2][n1];
/* 541 */     this._rgfGradient1.apply100(x, g1);
/* 542 */     this._rgfGradient2.apply010(x, g2);
/* 543 */     this._rgfGradient3.apply001(x, g3);
/*     */ 
/*     */     
/* 546 */     float[][][] g11 = g1;
/* 547 */     float[][][] g22 = g2;
/* 548 */     float[][][] g33 = g3;
/* 549 */     float[][][] g12 = (nt > 3) ? t[3] : new float[n3][n2][n1];
/* 550 */     float[][][] g13 = (nt > 4) ? t[4] : new float[n3][n2][n1];
/* 551 */     float[][][] g23 = (nt > 5) ? t[5] : new float[n3][n2][n1];
/* 552 */     computeGradientProducts(g1, g2, g3, g11, g12, g13, g22, g23, g33);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 572 */     if (this._rgfSmoother1 != null || this._rgfSmoother2 != null || this._rgfSmoother3 != null) {
/* 573 */       float[][][] h = (nt > 6) ? t[6] : new float[n3][n2][n1];
/* 574 */       float[][][][] gs = { g11, g22, g33, g12, g13, g23 };
/* 575 */       for (float[][][] g : gs) {
/* 576 */         if (this._rgfSmoother1 != null) {
/* 577 */           this._rgfSmoother1.apply0XX(g, h);
/*     */         } else {
/* 579 */           ArrayMath.copy(g, h);
/*     */         } 
/* 581 */         if (this._rgfSmoother2 != null) {
/* 582 */           this._rgfSmoother2.applyX0X(h, g);
/*     */         } else {
/* 584 */           ArrayMath.copy(h, g);
/*     */         } 
/* 586 */         if (this._rgfSmoother3 != null) {
/* 587 */           this._rgfSmoother3.applyXX0(g, h);
/* 588 */           ArrayMath.copy(h, g);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 594 */     solveEigenproblems(g11, g12, g13, g22, g23, g33, theta, phi, u1, u2, u3, v1, v2, v3, w1, w2, w3, eu, ev, ew, ep, el);
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
/*     */   private void computeGradientProducts(final float[][][] g1, final float[][][] g2, final float[][][] g3, final float[][][] g11, final float[][][] g12, final float[][][] g13, final float[][][] g22, final float[][][] g23, final float[][][] g33) {
/* 678 */     final int n1 = (g1[0][0]).length;
/* 679 */     final int n2 = (g1[0]).length;
/* 680 */     int n3 = g1.length;
/* 681 */     Parallel.loop(n3, new Parallel.LoopInt() {
/*     */           public void compute(int i3) {
/* 683 */             for (int i2 = 0; i2 < n2; i2++) {
/* 684 */               float[] g1i = g1[i3][i2];
/* 685 */               float[] g2i = g2[i3][i2];
/* 686 */               float[] g3i = g3[i3][i2];
/* 687 */               float[] g11i = g11[i3][i2];
/* 688 */               float[] g12i = g12[i3][i2];
/* 689 */               float[] g13i = g13[i3][i2];
/* 690 */               float[] g22i = g22[i3][i2];
/* 691 */               float[] g23i = g23[i3][i2];
/* 692 */               float[] g33i = g33[i3][i2];
/* 693 */               for (int i1 = 0; i1 < n1; i1++) {
/* 694 */                 float g1ii = g1i[i1];
/* 695 */                 float g2ii = g2i[i1];
/* 696 */                 float g3ii = g3i[i1];
/* 697 */                 g11i[i1] = g1ii * g1ii;
/* 698 */                 g22i[i1] = g2ii * g2ii;
/* 699 */                 g33i[i1] = g3ii * g3ii;
/* 700 */                 g12i[i1] = g1ii * g2ii;
/* 701 */                 g13i[i1] = g1ii * g3ii;
/* 702 */                 g23i[i1] = g2ii * g3ii;
/*     */               } 
/*     */             } 
/*     */           }
/*     */         });
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
/*     */   private void solveEigenproblems(final float[][][] g11, final float[][][] g12, final float[][][] g13, final float[][][] g22, final float[][][] g23, final float[][][] g33, final float[][][] theta, final float[][][] phi, final float[][][] u1, final float[][][] u2, final float[][][] u3, final float[][][] v1, final float[][][] v2, final float[][][] v3, final float[][][] w1, final float[][][] w2, final float[][][] w3, final float[][][] eu, final float[][][] ev, final float[][][] ew, final float[][][] ep, final float[][][] el) {
/* 719 */     final int n1 = (g11[0][0]).length;
/* 720 */     final int n2 = (g11[0]).length;
/* 721 */     int n3 = g11.length;
/* 722 */     Parallel.loop(n3, new Parallel.LoopInt() {
/*     */           public void compute(int i3) {
/* 724 */             double[][] a = new double[3][3];
/* 725 */             double[][] z = new double[3][3];
/* 726 */             double[] e = new double[3];
/* 727 */             for (int i2 = 0; i2 < n2; i2++) {
/* 728 */               for (int i1 = 0; i1 < n1; i1++) {
/* 729 */                 a[0][0] = g11[i3][i2][i1];
/* 730 */                 a[0][1] = g12[i3][i2][i1];
/* 731 */                 a[0][2] = g13[i3][i2][i1];
/* 732 */                 a[1][0] = g12[i3][i2][i1];
/* 733 */                 a[1][1] = g22[i3][i2][i1];
/* 734 */                 a[1][2] = g23[i3][i2][i1];
/* 735 */                 a[2][0] = g13[i3][i2][i1];
/* 736 */                 a[2][1] = g23[i3][i2][i1];
/* 737 */                 a[2][2] = g33[i3][i2][i1];
/* 738 */                 Eigen.solveSymmetric33(a, z, e);
/* 739 */                 float u1i = (float)z[0][0];
/* 740 */                 float u2i = (float)z[0][1];
/* 741 */                 float u3i = (float)z[0][2];
/* 742 */                 float v1i = (float)z[1][0];
/* 743 */                 float v2i = (float)z[1][1];
/* 744 */                 float v3i = (float)z[1][2];
/* 745 */                 float w1i = (float)z[2][0];
/* 746 */                 float w2i = (float)z[2][1];
/* 747 */                 float w3i = (float)z[2][2];
/* 748 */                 if (u1i < 0.0F) {
/* 749 */                   u1i = -u1i;
/* 750 */                   u2i = -u2i;
/* 751 */                   u3i = -u3i;
/*     */                 } 
/* 753 */                 if (v2i < 0.0F) {
/* 754 */                   v1i = -v1i;
/* 755 */                   v2i = -v2i;
/* 756 */                   v3i = -v3i;
/*     */                 } 
/* 758 */                 if (w3i < 0.0F) {
/* 759 */                   w1i = -w1i;
/* 760 */                   w2i = -w2i;
/* 761 */                   w3i = -w3i;
/*     */                 } 
/* 763 */                 float eui = (float)e[0];
/* 764 */                 float evi = (float)e[1];
/* 765 */                 float ewi = (float)e[2];
/* 766 */                 if (ewi < 0.0F) ewi = 0.0F; 
/* 767 */                 if (evi < ewi) evi = ewi; 
/* 768 */                 if (eui < evi) eui = evi; 
/* 769 */                 if (theta != null) theta[i3][i2][i1] = ArrayMath.acos(u1i); 
/* 770 */                 if (phi != null) phi[i3][i2][i1] = ArrayMath.atan2(u3i, u2i); 
/* 771 */                 if (u1 != null) u1[i3][i2][i1] = u1i; 
/* 772 */                 if (u2 != null) u2[i3][i2][i1] = u2i; 
/* 773 */                 if (u3 != null) u3[i3][i2][i1] = u3i; 
/* 774 */                 if (v1 != null) v1[i3][i2][i1] = v1i; 
/* 775 */                 if (v2 != null) v2[i3][i2][i1] = v2i; 
/* 776 */                 if (v3 != null) v3[i3][i2][i1] = v3i; 
/* 777 */                 if (w1 != null) w1[i3][i2][i1] = w1i; 
/* 778 */                 if (w2 != null) w2[i3][i2][i1] = w2i; 
/* 779 */                 if (w3 != null) w3[i3][i2][i1] = w3i; 
/* 780 */                 if (eu != null) eu[i3][i2][i1] = eui; 
/* 781 */                 if (ev != null) ev[i3][i2][i1] = evi; 
/* 782 */                 if (ew != null) ew[i3][i2][i1] = ewi; 
/* 783 */                 if (ep != null || el != null) {
/* 784 */                   float esi = (eui > 0.0F) ? (1.0F / eui) : 1.0F;
/* 785 */                   if (ep != null) ep[i3][i2][i1] = (eui - evi) * esi; 
/* 786 */                   if (el != null) el[i3][i2][i1] = (evi - ewi) * esi; 
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/dsp/LocalOrientFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */