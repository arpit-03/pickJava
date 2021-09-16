/*     */ package edu.mines.jtk.dsp;
/*     */ 
/*     */ import edu.mines.jtk.util.ArrayMath;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class WarpFunction3
/*     */ {
/*     */   private int _n1;
/*     */   private int _n2;
/*     */   private int _n3;
/*     */   
/*     */   public static WarpFunction3 constant(double u1, double u2, double u3, int n1, int n2, int n3) {
/*  52 */     return new ConstantWarp3(u1, u2, u3, n1, n2, n3);
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
/*     */   public static WarpFunction3 gaussian(double u1, double u2, double u3, int n1, int n2, int n3) {
/*  69 */     return new GaussianWarp3(u1, u2, u3, n1, n2, n3);
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
/*     */   public static WarpFunction3 sinusoid(double u1, double u2, double u3, int n1, int n2, int n3) {
/*  86 */     return new SinusoidWarp3(u1, u2, u3, n1, n2, n3);
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
/*     */   public static WarpFunction3 constantPlusSinusoid(double c1, double c2, double c3, double u1, double u2, double u3, int n1, int n2, int n3) {
/* 107 */     return new SinusoidWarp3(c1, c2, c3, u1, u2, u3, n1, n2, n3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract double u1(double paramDouble1, double paramDouble2, double paramDouble3);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract double u2(double paramDouble1, double paramDouble2, double paramDouble3);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract double u3(double paramDouble1, double paramDouble2, double paramDouble3);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double u1x(double x1, double x2, double x3) {
/* 145 */     return u1(x1, x2, x3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double u2x(double x1, double x2, double x3) {
/* 156 */     return u2(x1, x2, x3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double u3x(double x1, double x2, double x3) {
/* 167 */     return u3(x1, x2, x3);
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
/*     */   public double u1m(double m1, double m2, double m3) {
/* 179 */     double u1m = 0.0D;
/* 180 */     double u2m = 0.0D;
/* 181 */     double u3m = 0.0D;
/*     */     while (true) {
/* 183 */       double u1p = u1m;
/* 184 */       u1m = u1(m1 - 0.5D * u1m, m2 - 0.5D * u2m, m3 - 0.5D * u3m);
/* 185 */       u2m = u2(m1 - 0.5D * u1m, m2 - 0.5D * u2m, m3 - 0.5D * u3m);
/* 186 */       u3m = u3(m1 - 0.5D * u1m, m2 - 0.5D * u2m, m3 - 0.5D * u3m);
/* 187 */       if (ArrayMath.abs(u1m - u1p) <= 1.0E-4D) {
/* 188 */         return u1m;
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
/*     */   public double u2m(double m1, double m2, double m3) {
/* 200 */     double u1m = 0.0D;
/* 201 */     double u2m = 0.0D;
/* 202 */     double u3m = 0.0D;
/*     */     while (true) {
/* 204 */       double u2p = u2m;
/* 205 */       u1m = u1(m1 - 0.5D * u1m, m2 - 0.5D * u2m, m3 - 0.5D * u3m);
/* 206 */       u2m = u2(m1 - 0.5D * u1m, m2 - 0.5D * u2m, m3 - 0.5D * u3m);
/* 207 */       u3m = u3(m1 - 0.5D * u1m, m2 - 0.5D * u2m, m3 - 0.5D * u3m);
/* 208 */       if (ArrayMath.abs(u2m - u2p) <= 1.0E-4D) {
/* 209 */         return u2m;
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
/*     */   public double u3m(double m1, double m2, double m3) {
/* 221 */     double u1m = 0.0D;
/* 222 */     double u2m = 0.0D;
/* 223 */     double u3m = 0.0D;
/*     */     while (true) {
/* 225 */       double u3p = u3m;
/* 226 */       u1m = u1(m1 - 0.5D * u1m, m2 - 0.5D * u2m, m3 - 0.5D * u3m);
/* 227 */       u2m = u2(m1 - 0.5D * u1m, m2 - 0.5D * u2m, m3 - 0.5D * u3m);
/* 228 */       u3m = u3(m1 - 0.5D * u1m, m2 - 0.5D * u2m, m3 - 0.5D * u3m);
/* 229 */       if (ArrayMath.abs(u3m - u3p) <= 1.0E-4D) {
/* 230 */         return u3m;
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
/*     */   public double u1y(double y1, double y2, double y3) {
/* 242 */     double u1y = 0.0D;
/* 243 */     double u2y = 0.0D;
/* 244 */     double u3y = 0.0D;
/*     */     while (true) {
/* 246 */       double u1p = u1y;
/* 247 */       u1y = u1(y1 - u1y, y2 - u2y, y3 - u3y);
/* 248 */       u2y = u2(y1 - u1y, y2 - u2y, y3 - u3y);
/* 249 */       u3y = u3(y1 - u1y, y2 - u2y, y3 - u3y);
/* 250 */       if (ArrayMath.abs(u1y - u1p) <= 1.0E-4D) {
/* 251 */         return u1y;
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
/*     */   public double u2y(double y1, double y2, double y3) {
/* 263 */     double u1y = 0.0D;
/* 264 */     double u2y = 0.0D;
/* 265 */     double u3y = 0.0D;
/*     */     while (true) {
/* 267 */       double u2p = u2y;
/* 268 */       u1y = u1(y1 - u1y, y2 - u2y, y3 - u3y);
/* 269 */       u2y = u2(y1 - u1y, y2 - u2y, y3 - u3y);
/* 270 */       u3y = u3(y1 - u1y, y2 - u2y, y3 - u3y);
/* 271 */       if (ArrayMath.abs(u2y - u2p) <= 1.0E-4D) {
/* 272 */         return u2y;
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
/*     */   public double u3y(double y1, double y2, double y3) {
/* 284 */     double u1y = 0.0D;
/* 285 */     double u2y = 0.0D;
/* 286 */     double u3y = 0.0D;
/*     */     while (true) {
/* 288 */       double u3p = u3y;
/* 289 */       u1y = u1(y1 - u1y, y2 - u2y, y3 - u3y);
/* 290 */       u2y = u2(y1 - u1y, y2 - u2y, y3 - u3y);
/* 291 */       u3y = u3(y1 - u1y, y2 - u2y, y3 - u3y);
/* 292 */       if (ArrayMath.abs(u3y - u3p) <= 1.0E-4D) {
/* 293 */         return u3y;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float[][][] u1x() {
/* 301 */     float[][][] u = new float[this._n3][this._n2][this._n1];
/* 302 */     for (int i3 = 0; i3 < this._n3; i3++) {
/* 303 */       double x3 = i3;
/* 304 */       for (int i2 = 0; i2 < this._n2; i2++) {
/* 305 */         double x2 = i2;
/* 306 */         for (int i1 = 0; i1 < this._n1; i1++) {
/* 307 */           double x1 = i1;
/* 308 */           u[i3][i2][i1] = (float)u1x(x1, x2, x3);
/*     */         } 
/*     */       } 
/*     */     } 
/* 312 */     return u;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[][][] u2x() {
/* 320 */     float[][][] u = new float[this._n3][this._n2][this._n1];
/* 321 */     for (int i3 = 0; i3 < this._n3; i3++) {
/* 322 */       double x3 = i3;
/* 323 */       for (int i2 = 0; i2 < this._n2; i2++) {
/* 324 */         double x2 = i2;
/* 325 */         for (int i1 = 0; i1 < this._n1; i1++) {
/* 326 */           double x1 = i1;
/* 327 */           u[i3][i2][i1] = (float)u2x(x1, x2, x3);
/*     */         } 
/*     */       } 
/*     */     } 
/* 331 */     return u;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[][][] u3x() {
/* 339 */     float[][][] u = new float[this._n3][this._n2][this._n1];
/* 340 */     for (int i3 = 0; i3 < this._n3; i3++) {
/* 341 */       double x3 = i3;
/* 342 */       for (int i2 = 0; i2 < this._n2; i2++) {
/* 343 */         double x2 = i2;
/* 344 */         for (int i1 = 0; i1 < this._n1; i1++) {
/* 345 */           double x1 = i1;
/* 346 */           u[i3][i2][i1] = (float)u3x(x1, x2, x3);
/*     */         } 
/*     */       } 
/*     */     } 
/* 350 */     return u;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[][][] u1m() {
/* 358 */     float[][][] u = new float[this._n3][this._n2][this._n1];
/* 359 */     for (int i3 = 0; i3 < this._n3; i3++) {
/* 360 */       double x3 = i3;
/* 361 */       for (int i2 = 0; i2 < this._n2; i2++) {
/* 362 */         double x2 = i2;
/* 363 */         for (int i1 = 0; i1 < this._n1; i1++) {
/* 364 */           double x1 = i1;
/* 365 */           u[i3][i2][i1] = (float)u1m(x1, x2, x3);
/*     */         } 
/*     */       } 
/*     */     } 
/* 369 */     return u;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[][][] u2m() {
/* 377 */     float[][][] u = new float[this._n3][this._n2][this._n1];
/* 378 */     for (int i3 = 0; i3 < this._n3; i3++) {
/* 379 */       double x3 = i3;
/* 380 */       for (int i2 = 0; i2 < this._n2; i2++) {
/* 381 */         double x2 = i2;
/* 382 */         for (int i1 = 0; i1 < this._n1; i1++) {
/* 383 */           double x1 = i1;
/* 384 */           u[i3][i2][i1] = (float)u2m(x1, x2, x3);
/*     */         } 
/*     */       } 
/*     */     } 
/* 388 */     return u;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[][][] u3m() {
/* 396 */     float[][][] u = new float[this._n3][this._n2][this._n1];
/* 397 */     for (int i3 = 0; i3 < this._n3; i3++) {
/* 398 */       double x3 = i3;
/* 399 */       for (int i2 = 0; i2 < this._n2; i2++) {
/* 400 */         double x2 = i2;
/* 401 */         for (int i1 = 0; i1 < this._n1; i1++) {
/* 402 */           double x1 = i1;
/* 403 */           u[i3][i2][i1] = (float)u3m(x1, x2, x3);
/*     */         } 
/*     */       } 
/*     */     } 
/* 407 */     return u;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[][][] u1y() {
/* 415 */     float[][][] u = new float[this._n3][this._n2][this._n1];
/* 416 */     for (int i3 = 0; i3 < this._n3; i3++) {
/* 417 */       double x3 = i3;
/* 418 */       for (int i2 = 0; i2 < this._n2; i2++) {
/* 419 */         double x2 = i2;
/* 420 */         for (int i1 = 0; i1 < this._n1; i1++) {
/* 421 */           double x1 = i1;
/* 422 */           u[i3][i2][i1] = (float)u1y(x1, x2, x3);
/*     */         } 
/*     */       } 
/*     */     } 
/* 426 */     return u;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[][][] u2y() {
/* 434 */     float[][][] u = new float[this._n3][this._n2][this._n1];
/* 435 */     for (int i3 = 0; i3 < this._n3; i3++) {
/* 436 */       double x3 = i3;
/* 437 */       for (int i2 = 0; i2 < this._n2; i2++) {
/* 438 */         double x2 = i2;
/* 439 */         for (int i1 = 0; i1 < this._n1; i1++) {
/* 440 */           double x1 = i1;
/* 441 */           u[i3][i2][i1] = (float)u2y(x1, x2, x3);
/*     */         } 
/*     */       } 
/*     */     } 
/* 445 */     return u;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[][][] u3y() {
/* 453 */     float[][][] u = new float[this._n3][this._n2][this._n1];
/* 454 */     for (int i3 = 0; i3 < this._n3; i3++) {
/* 455 */       double x3 = i3;
/* 456 */       for (int i2 = 0; i2 < this._n2; i2++) {
/* 457 */         double x2 = i2;
/* 458 */         for (int i1 = 0; i1 < this._n1; i1++) {
/* 459 */           double x1 = i1;
/* 460 */           u[i3][i2][i1] = (float)u3y(x1, x2, x3);
/*     */         } 
/*     */       } 
/*     */     } 
/* 464 */     return u;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[][][] warp1(float[][][] f) {
/* 473 */     SincInterpolator si = new SincInterpolator();
/* 474 */     float[][][] g = new float[this._n3][this._n2][this._n1];
/* 475 */     for (int i3 = 0; i3 < this._n3; i3++) {
/* 476 */       for (int i2 = 0; i2 < this._n2; i2++) {
/* 477 */         for (int i1 = 0; i1 < this._n1; i1++) {
/* 478 */           g[i3][i2][i1] = si.interpolate(this._n1, 1.0D, 0.0D, f[i3][i2], i1 - 
/* 479 */               u1y(i1, i2, i3));
/*     */         }
/*     */       } 
/*     */     } 
/* 483 */     return g;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[][][] warp(float[][][] f) {
/* 492 */     SincInterpolator si = new SincInterpolator();
/* 493 */     float[][][] g = new float[this._n3][this._n2][this._n1];
/* 494 */     for (int i3 = 0; i3 < this._n3; i3++) {
/* 495 */       double y3 = i3;
/* 496 */       for (int i2 = 0; i2 < this._n2; i2++) {
/* 497 */         double y2 = i2;
/* 498 */         for (int i1 = 0; i1 < this._n1; i1++) {
/* 499 */           double y1 = i1;
/* 500 */           double x1 = y1 - u1y(y1, y2, y3);
/* 501 */           double x2 = y2 - u2y(y1, y2, y3);
/* 502 */           double x3 = y3 - u3y(y1, y2, y3);
/* 503 */           g[i3][i2][i1] = si.interpolate(this._n1, 1.0D, 0.0D, this._n2, 1.0D, 0.0D, this._n3, 1.0D, 0.0D, f, x1, x2, x3);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 509 */     return g;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[][][] unwarp1(float[][][] g) {
/* 518 */     SincInterpolator si = new SincInterpolator();
/* 519 */     float[][][] f = new float[this._n3][this._n2][this._n1];
/* 520 */     for (int i3 = 0; i3 < this._n3; i3++) {
/* 521 */       for (int i2 = 0; i2 < this._n2; i2++) {
/* 522 */         for (int i1 = 0; i1 < this._n1; i1++) {
/* 523 */           f[i3][i2][i1] = si.interpolate(this._n1, 1.0D, 0.0D, g[i3][i2], i1 + 
/* 524 */               u1x(i1, i2, i3));
/*     */         }
/*     */       } 
/*     */     } 
/* 528 */     return f;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[][][] unwarp(float[][][] g) {
/* 537 */     SincInterpolator si = new SincInterpolator();
/* 538 */     float[][][] f = new float[this._n3][this._n2][this._n1];
/* 539 */     for (int i3 = 0; i3 < this._n3; i3++) {
/* 540 */       double x3 = i3;
/* 541 */       for (int i2 = 0; i2 < this._n2; i2++) {
/* 542 */         double x2 = i2;
/* 543 */         for (int i1 = 0; i1 < this._n1; i1++) {
/* 544 */           double x1 = i1;
/* 545 */           double y1 = x1 + u1x(x1, x2, x3);
/* 546 */           double y2 = x2 + u2x(x1, x2, x3);
/* 547 */           double y3 = x3 + u3x(x1, x2, x3);
/* 548 */           f[i3][i2][i1] = si.interpolate(this._n1, 1.0D, 0.0D, this._n2, 1.0D, 0.0D, this._n3, 1.0D, 0.0D, g, y1, y2, y3);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 554 */     return f;
/*     */   }
/*     */   
/*     */   protected WarpFunction3(int n1, int n2, int n3) {
/* 558 */     this._n1 = n1;
/* 559 */     this._n2 = n2;
/* 560 */     this._n3 = n3;
/*     */   }
/*     */ 
/*     */   
/*     */   private static class ConstantWarp3
/*     */     extends WarpFunction3
/*     */   {
/*     */     private double _u1;
/*     */     
/*     */     private double _u2;
/*     */     
/*     */     private double _u3;
/*     */     
/*     */     public ConstantWarp3(double u1, double u2, double u3, int n1, int n2, int n3) {
/* 574 */       super(n1, n2, n3);
/* 575 */       this._u1 = u1;
/* 576 */       this._u2 = u2;
/* 577 */       this._u3 = u3;
/*     */     }
/* 579 */     public double u1(double x1, double x2, double x3) { return this._u1; }
/* 580 */     public double u2(double x1, double x2, double x3) { return this._u2; } public double u3(double x1, double x2, double x3) {
/* 581 */       return this._u3;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class GaussianWarp3
/*     */     extends WarpFunction3 {
/*     */     private double _a1;
/*     */     private double _a2;
/*     */     private double _a3;
/*     */     private double _b1;
/*     */     
/*     */     public GaussianWarp3(double u1max, double u2max, double u3max, int n1, int n2, int n3) {
/* 593 */       super(n1, n2, n3);
/* 594 */       this._a1 = (n1 - 1) / 2.0D;
/* 595 */       this._a2 = (n2 - 1) / 2.0D;
/* 596 */       this._a3 = (n3 - 1) / 2.0D;
/* 597 */       this._b1 = this._a1 / 3.0D;
/* 598 */       this._b2 = this._a2 / 3.0D;
/* 599 */       this._b3 = this._a3 / 3.0D;
/* 600 */       this._c1 = u1max * ArrayMath.exp(0.5D) / this._b1;
/* 601 */       this._c2 = u2max * ArrayMath.exp(0.5D) / this._b2;
/* 602 */       this._c3 = u3max * ArrayMath.exp(0.5D) / this._b3;
/*     */     } private double _b2; private double _b3; private double _c1; private double _c2; private double _c3;
/*     */     public double u1(double x1, double x2, double x3) {
/* 605 */       double xa1 = x1 - this._a1;
/* 606 */       double xa2 = x2 - this._a2;
/* 607 */       double xa3 = x3 - this._a3;
/* 608 */       return -this._c1 * xa1 * ArrayMath.exp(-0.5D * (xa1 * xa1 / this._b1 * this._b1 + xa2 * xa2 / this._b2 * this._b2 + xa3 * xa3 / this._b3 * this._b3));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public double u2(double x1, double x2, double x3) {
/* 614 */       double xa1 = x1 - this._a1;
/* 615 */       double xa2 = x2 - this._a2;
/* 616 */       double xa3 = x3 - this._a3;
/* 617 */       return -this._c2 * xa2 * ArrayMath.exp(-0.5D * (xa1 * xa1 / this._b1 * this._b1 + xa2 * xa2 / this._b2 * this._b2 + xa3 * xa3 / this._b3 * this._b3));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public double u3(double x1, double x2, double x3) {
/* 623 */       double xa1 = x1 - this._a1;
/* 624 */       double xa2 = x2 - this._a2;
/* 625 */       double xa3 = x3 - this._a3;
/* 626 */       return -this._c3 * xa3 * ArrayMath.exp(-0.5D * (xa1 * xa1 / this._b1 * this._b1 + xa2 * xa2 / this._b2 * this._b2 + xa3 * xa3 / this._b3 * this._b3));
/*     */     }
/*     */   }
/*     */   
/*     */   private static class SinusoidWarp3
/*     */     extends WarpFunction3 {
/*     */     private double _a1;
/*     */     private double _a2;
/*     */     private double _a3;
/*     */     private double _b1;
/*     */     private double _b2;
/*     */     private double _b3;
/*     */     private double _c1;
/*     */     private double _c2;
/*     */     private double _c3;
/*     */     
/*     */     public SinusoidWarp3(double u1max, double u2max, double u3max, int n1, int n2, int n3) {
/* 643 */       this(0.0D, 0.0D, 0.0D, u1max, u2max, u3max, n1, n2, n3);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public SinusoidWarp3(double u1add, double u2add, double u3add, double u1sin, double u2sin, double u3sin, int n1, int n2, int n3) {
/* 650 */       super(n1, n2, n3);
/* 651 */       double l1 = (n1 - 1);
/* 652 */       double l2 = (n2 - 1);
/* 653 */       double l3 = (n3 - 1);
/* 654 */       this._c1 = u1add;
/* 655 */       this._c2 = u2add;
/* 656 */       this._c3 = u3add;
/* 657 */       this._a1 = u1sin;
/* 658 */       this._a2 = u2sin;
/* 659 */       this._a3 = u3sin;
/* 660 */       this._b1 = 6.283185307179586D / l1;
/* 661 */       this._b2 = 6.283185307179586D / l2;
/* 662 */       this._b3 = 6.283185307179586D / l3;
/*     */     }
/*     */     public double u1(double x1, double x2, double x3) {
/* 665 */       return this._c1 + this._a1 * ArrayMath.sin(this._b1 * x1) * ArrayMath.sin(0.5D * this._b2 * x2) * ArrayMath.sin(0.5D * this._b3 * x3);
/*     */     }
/*     */     public double u2(double x1, double x2, double x3) {
/* 668 */       return this._c2 + this._a2 * ArrayMath.sin(this._b2 * x2) * ArrayMath.sin(0.5D * this._b1 * x1) * ArrayMath.sin(0.5D * this._b3 * x3);
/*     */     }
/*     */     public double u3(double x1, double x2, double x3) {
/* 671 */       return this._c3 + this._a3 * ArrayMath.sin(this._b3 * x3) * ArrayMath.sin(0.5D * this._b1 * x1) * ArrayMath.sin(0.5D * this._b2 * x2);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/dsp/WarpFunction3.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */