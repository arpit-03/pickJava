/*     */ package edu.mines.jtk.dsp;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LinearInterpolator
/*     */ {
/*     */   public enum Extrapolation
/*     */   {
/*  34 */     ZERO,
/*  35 */     CONSTANT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Extrapolation getExtrapolation() {
/*  43 */     return this._extrap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setExtrapolation(Extrapolation extrap) {
/*  52 */     this._extrap = extrap;
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
/*     */   public void setUniformSampling(int nxu, double dxu, double fxu) {
/*  64 */     this._nxu = nxu;
/*  65 */     this._dxu = dxu;
/*  66 */     this._fxu = fxu;
/*  67 */     this._xf = fxu;
/*  68 */     this._xs = 1.0D / dxu;
/*  69 */     this._xb = 2.0D - this._xf * this._xs;
/*  70 */     this._nxum = nxu - 1;
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
/*     */   public void setUniformSamples(float[] yu) {
/*  84 */     this._yu = yu;
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
/*     */   public void setUniform(int nxu, double dxu, double fxu, float[] yu) {
/* 100 */     setUniformSampling(nxu, dxu, fxu);
/* 101 */     setUniformSamples(yu);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float interpolate(double x) {
/*     */     float yr;
/* 112 */     double xn = this._xb + x * this._xs;
/* 113 */     int ixn = (int)xn;
/* 114 */     float a1 = (float)(xn - ixn);
/* 115 */     float a0 = 1.0F - a1;
/* 116 */     int kyu = ixn - 2;
/* 117 */     if (0 <= kyu && kyu < this._nxum) {
/* 118 */       yr = a0 * this._yu[kyu] + a1 * this._yu[kyu + 1];
/* 119 */     } else if (this._extrap == Extrapolation.ZERO) {
/* 120 */       yr = 0.0F;
/* 121 */       if (0 <= kyu && kyu < this._nxu)
/* 122 */         yr += a0 * this._yu[kyu]; 
/* 123 */       kyu++;
/* 124 */       if (0 <= kyu && kyu < this._nxu)
/* 125 */         yr += a1 * this._yu[kyu]; 
/*     */     } else {
/* 127 */       int jyu = (kyu < 0) ? 0 : ((this._nxu <= kyu) ? this._nxum : kyu);
/* 128 */       yr = a0 * this._yu[jyu];
/* 129 */       kyu++;
/* 130 */       jyu = (kyu < 0) ? 0 : ((this._nxu <= kyu) ? this._nxum : kyu);
/* 131 */       yr += a1 * this._yu[jyu];
/*     */     } 
/* 133 */     return yr;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void interpolate(int nx, float[] x, float[] y) {
/* 143 */     for (int ix = 0; ix < nx; ix++) {
/* 144 */       y[ix] = interpolate(x[ix]);
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
/*     */   public void interpolate(int nx, double dx, double fx, float[] y) {
/* 159 */     for (int ix = 0; ix < nx; ix++) {
/* 160 */       y[ix] = interpolate(fx + ix * dx);
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
/*     */   public void setUniformSampling(int nx1u, double dx1u, double fx1u, int nx2u, double dx2u, double fx2u) {
/* 177 */     this._nx1u = nx1u;
/* 178 */     this._x1f = fx1u;
/* 179 */     this._x1s = 1.0D / dx1u;
/* 180 */     this._x1b = 2.0D - this._x1f * this._x1s;
/* 181 */     this._nx1um = nx1u - 1;
/* 182 */     this._nx2u = nx2u;
/* 183 */     this._x2f = fx2u;
/* 184 */     this._x2s = 1.0D / dx2u;
/* 185 */     this._x2b = 2.0D - this._x2f * this._x2s;
/* 186 */     this._nx2um = nx2u - 1;
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
/*     */   public void setUniformSamples(float[][] yu) {
/* 200 */     this._yyu = yu;
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
/*     */   public void setUniform(int nx1u, double dx1u, double fx1u, int nx2u, double dx2u, double fx2u, float[][] yu) {
/* 222 */     setUniformSampling(nx1u, dx1u, fx1u, nx2u, dx2u, fx2u);
/* 223 */     setUniformSamples(yu);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float interpolate(double x1, double x2) {
/*     */     float yr;
/* 235 */     double x1n = this._x1b + x1 * this._x1s;
/* 236 */     double x2n = this._x2b + x2 * this._x2s;
/* 237 */     int ix1n = (int)x1n;
/* 238 */     int ix2n = (int)x2n;
/* 239 */     int ky1u = ix1n - 2;
/* 240 */     int ky2u = ix2n - 2;
/* 241 */     float a11 = (float)(x1n - ix1n);
/* 242 */     float a12 = (float)(x2n - ix2n);
/* 243 */     float a01 = 1.0F - a11;
/* 244 */     float a02 = 1.0F - a12;
/* 245 */     if (ky1u >= 0 && ky1u < this._nx1um && ky2u >= 0 && ky2u < this._nx2um) {
/*     */       
/* 247 */       float[] yyuk0 = this._yyu[ky2u];
/* 248 */       float[] yyuk1 = this._yyu[ky2u + 1];
/* 249 */       yr = a01 * a02 * yyuk0[ky1u] + a11 * a02 * yyuk0[ky1u + 1] + a01 * a12 * yyuk1[ky1u] + a11 * a12 * yyuk1[ky1u + 1];
/*     */ 
/*     */     
/*     */     }
/* 253 */     else if (this._extrap == Extrapolation.ZERO) {
/* 254 */       yr = 0.0F;
/* 255 */       if (0 <= ky2u && ky2u < this._nx2u) {
/* 256 */         int my1u = ky1u;
/* 257 */         if (0 <= my1u && my1u < this._nx1u)
/* 258 */           yr += a01 * a02 * this._yyu[ky2u][my1u]; 
/* 259 */         my1u++;
/* 260 */         if (0 <= my1u && my1u < this._nx1u)
/* 261 */           yr += a11 * a02 * this._yyu[ky2u][my1u]; 
/*     */       } 
/* 263 */       ky2u++;
/* 264 */       if (0 <= ky2u && ky2u < this._nx2u) {
/* 265 */         int my1u = ky1u;
/* 266 */         if (0 <= my1u && my1u < this._nx1u)
/* 267 */           yr += a01 * a12 * this._yyu[ky2u][my1u]; 
/* 268 */         my1u++;
/* 269 */         if (0 <= my1u && my1u < this._nx1u)
/* 270 */           yr += a11 * a12 * this._yyu[ky2u][my1u]; 
/*     */       } 
/*     */     } else {
/* 273 */       int jy2u = (ky2u < 0) ? 0 : ((this._nx2u <= ky2u) ? this._nx2um : ky2u);
/* 274 */       int my1u = ky1u;
/* 275 */       int jy1u = (my1u < 0) ? 0 : ((this._nx1u <= my1u) ? this._nx1um : my1u);
/* 276 */       yr = a01 * a02 * this._yyu[jy2u][jy1u];
/* 277 */       my1u++;
/* 278 */       jy1u = (my1u < 0) ? 0 : ((this._nx1u <= my1u) ? this._nx1um : my1u);
/* 279 */       yr += a11 * a02 * this._yyu[jy2u][jy1u];
/* 280 */       ky2u++;
/* 281 */       jy2u = (ky2u < 0) ? 0 : ((this._nx2u <= ky2u) ? this._nx2um : ky2u);
/* 282 */       my1u = ky1u;
/* 283 */       jy1u = (my1u < 0) ? 0 : ((this._nx1u <= my1u) ? this._nx1um : my1u);
/* 284 */       yr += a01 * a12 * this._yyu[jy2u][jy1u];
/* 285 */       my1u++;
/* 286 */       jy1u = (my1u < 0) ? 0 : ((this._nx1u <= my1u) ? this._nx1um : my1u);
/* 287 */       yr += a11 * a12 * this._yyu[jy2u][jy1u];
/*     */     } 
/* 289 */     return yr;
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
/*     */   public void setUniformSampling(int nx1u, double dx1u, double fx1u, int nx2u, double dx2u, double fx2u, int nx3u, double dx3u, double fx3u) {
/* 311 */     this._nx1u = nx1u;
/* 312 */     this._x1f = fx1u;
/* 313 */     this._x1s = 1.0D / dx1u;
/* 314 */     this._x1b = 2.0D - this._x1f * this._x1s;
/* 315 */     this._nx1um = nx1u - 1;
/* 316 */     this._nx2u = nx2u;
/* 317 */     this._x2f = fx2u;
/* 318 */     this._x2s = 1.0D / dx2u;
/* 319 */     this._x2b = 2.0D - this._x2f * this._x2s;
/* 320 */     this._nx2um = nx2u - 1;
/* 321 */     this._nx3u = nx3u;
/* 322 */     this._x3f = fx3u;
/* 323 */     this._x3s = 1.0D / dx3u;
/* 324 */     this._x3b = 2.0D - this._x3f * this._x3s;
/* 325 */     this._nx3um = nx3u - 1;
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
/*     */   public void setUniformSamples(float[][][] yu) {
/* 339 */     this._yyyu = yu;
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
/*     */   public void setUniform(int nx1u, double dx1u, double fx1u, int nx2u, double dx2u, double fx2u, int nx3u, double dx3u, double fx3u, float[][][] yu) {
/* 367 */     setUniformSampling(nx1u, dx1u, fx1u, nx2u, dx2u, fx2u, nx3u, dx3u, fx3u);
/* 368 */     setUniformSamples(yu);
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
/*     */   public float interpolate(double x1, double x2, double x3) {
/*     */     float yr;
/* 381 */     double x1n = this._x1b + x1 * this._x1s;
/* 382 */     double x2n = this._x2b + x2 * this._x2s;
/* 383 */     double x3n = this._x3b + x3 * this._x3s;
/* 384 */     int ix1n = (int)x1n;
/* 385 */     int ix2n = (int)x2n;
/* 386 */     int ix3n = (int)x3n;
/* 387 */     int ky1u = ix1n - 2;
/* 388 */     int ky2u = ix2n - 2;
/* 389 */     int ky3u = ix3n - 2;
/* 390 */     float a11 = (float)(x1n - ix1n);
/* 391 */     float a12 = (float)(x2n - ix2n);
/* 392 */     float a13 = (float)(x3n - ix3n);
/* 393 */     float a01 = 1.0F - a11;
/* 394 */     float a02 = 1.0F - a12;
/* 395 */     float a03 = 1.0F - a13;
/* 396 */     if (ky1u >= 0 && ky1u < this._nx1um && ky2u >= 0 && ky2u < this._nx2um && ky3u >= 0 && ky3u < this._nx3um) {
/*     */ 
/*     */       
/* 399 */       float[] yyyuk00 = this._yyyu[ky3u][ky2u];
/* 400 */       float[] yyyuk01 = this._yyyu[ky3u][ky2u + 1];
/* 401 */       float[] yyyuk10 = this._yyyu[ky3u + 1][ky2u];
/* 402 */       float[] yyyuk11 = this._yyyu[ky3u + 1][ky2u + 1];
/* 403 */       yr = a01 * a02 * a03 * yyyuk00[ky1u] + a11 * a02 * a03 * yyyuk00[ky1u + 1] + a01 * a12 * a03 * yyyuk01[ky1u] + a11 * a12 * a03 * yyyuk01[ky1u + 1] + a01 * a02 * a13 * yyyuk10[ky1u] + a11 * a02 * a13 * yyyuk10[ky1u + 1] + a01 * a12 * a13 * yyyuk11[ky1u] + a11 * a12 * a13 * yyyuk11[ky1u + 1];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 411 */     else if (this._extrap == Extrapolation.ZERO) {
/* 412 */       yr = 0.0F;
/* 413 */       if (0 <= ky3u && ky3u < this._nx3u) {
/* 414 */         int my2u = ky2u;
/* 415 */         if (0 <= my2u && my2u < this._nx2u) {
/* 416 */           int my1u = ky1u;
/* 417 */           if (0 <= my1u && my1u < this._nx1u)
/* 418 */             yr += a01 * a02 * a03 * this._yyyu[ky3u][my2u][my1u]; 
/* 419 */           my1u++;
/* 420 */           if (0 <= my1u && my1u < this._nx1u)
/* 421 */             yr += a11 * a02 * a03 * this._yyyu[ky3u][my2u][my1u]; 
/*     */         } 
/* 423 */         my2u++;
/* 424 */         if (0 <= my2u && my2u < this._nx2u) {
/* 425 */           int my1u = ky1u;
/* 426 */           if (0 <= my1u && my1u < this._nx1u)
/* 427 */             yr += a01 * a12 * a03 * this._yyyu[ky3u][my2u][my1u]; 
/* 428 */           my1u++;
/* 429 */           if (0 <= my1u && my1u < this._nx1u)
/* 430 */             yr += a11 * a12 * a03 * this._yyyu[ky3u][my2u][my1u]; 
/*     */         } 
/*     */       } 
/* 433 */       ky3u++;
/* 434 */       if (0 <= ky3u && ky3u < this._nx3u) {
/* 435 */         int my2u = ky2u;
/* 436 */         if (0 <= my2u && my2u < this._nx2u) {
/* 437 */           int my1u = ky1u;
/* 438 */           if (0 <= my1u && my1u < this._nx1u)
/* 439 */             yr += a01 * a02 * a13 * this._yyyu[ky3u][my2u][my1u]; 
/* 440 */           my1u++;
/* 441 */           if (0 <= my1u && my1u < this._nx1u)
/* 442 */             yr += a11 * a02 * a13 * this._yyyu[ky3u][my2u][my1u]; 
/*     */         } 
/* 444 */         my2u++;
/* 445 */         if (0 <= my2u && my2u < this._nx2u) {
/* 446 */           int my1u = ky1u;
/* 447 */           if (0 <= my1u && my1u < this._nx1u)
/* 448 */             yr += a01 * a12 * a13 * this._yyyu[ky3u][my2u][my1u]; 
/* 449 */           my1u++;
/* 450 */           if (0 <= my1u && my1u < this._nx1u)
/* 451 */             yr += a11 * a12 * a13 * this._yyyu[ky3u][my2u][my1u]; 
/*     */         } 
/*     */       } 
/*     */     } else {
/* 455 */       int jy3u = (ky3u < 0) ? 0 : ((this._nx3u <= ky3u) ? this._nx3um : ky3u);
/* 456 */       int my2u = ky2u;
/* 457 */       int jy2u = (my2u < 0) ? 0 : ((this._nx2u <= my2u) ? this._nx2um : my2u);
/* 458 */       int my1u = ky1u;
/* 459 */       int jy1u = (my1u < 0) ? 0 : ((this._nx1u <= my1u) ? this._nx1um : my1u);
/* 460 */       yr = a01 * a02 * a03 * this._yyyu[jy3u][jy2u][jy1u];
/* 461 */       my1u++;
/* 462 */       jy1u = (my1u < 0) ? 0 : ((this._nx1u <= my1u) ? this._nx1um : my1u);
/* 463 */       yr += a11 * a02 * a03 * this._yyyu[jy3u][jy2u][jy1u];
/* 464 */       my2u++;
/* 465 */       jy2u = (my2u < 0) ? 0 : ((this._nx2u <= my2u) ? this._nx2um : my2u);
/* 466 */       my1u = ky1u;
/* 467 */       jy1u = (my1u < 0) ? 0 : ((this._nx1u <= my1u) ? this._nx1um : my1u);
/* 468 */       yr += a01 * a12 * a03 * this._yyyu[jy3u][jy2u][jy1u];
/* 469 */       my1u++;
/* 470 */       jy1u = (my1u < 0) ? 0 : ((this._nx1u <= my1u) ? this._nx1um : my1u);
/* 471 */       yr += a11 * a12 * a03 * this._yyyu[jy3u][jy2u][jy1u];
/* 472 */       ky3u++;
/* 473 */       jy3u = (ky3u < 0) ? 0 : ((this._nx3u <= ky3u) ? this._nx3um : ky3u);
/* 474 */       my2u = ky2u;
/* 475 */       jy2u = (my2u < 0) ? 0 : ((this._nx2u <= my2u) ? this._nx2um : my2u);
/* 476 */       my1u = ky1u;
/* 477 */       jy1u = (my1u < 0) ? 0 : ((this._nx1u <= my1u) ? this._nx1um : my1u);
/* 478 */       yr += a01 * a02 * a13 * this._yyyu[jy3u][jy2u][jy1u];
/* 479 */       my1u++;
/* 480 */       jy1u = (my1u < 0) ? 0 : ((this._nx1u <= my1u) ? this._nx1um : my1u);
/* 481 */       yr += a11 * a02 * a13 * this._yyyu[jy3u][jy2u][jy1u];
/* 482 */       my2u++;
/* 483 */       jy2u = (my2u < 0) ? 0 : ((this._nx2u <= my2u) ? this._nx2um : my2u);
/* 484 */       my1u = ky1u;
/* 485 */       jy1u = (my1u < 0) ? 0 : ((this._nx1u <= my1u) ? this._nx1um : my1u);
/* 486 */       yr += a01 * a12 * a13 * this._yyyu[jy3u][jy2u][jy1u];
/* 487 */       my1u++;
/* 488 */       jy1u = (my1u < 0) ? 0 : ((this._nx1u <= my1u) ? this._nx1um : my1u);
/* 489 */       yr += a11 * a12 * a13 * this._yyyu[jy3u][jy2u][jy1u];
/*     */     } 
/* 491 */     return yr;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 498 */   private Extrapolation _extrap = Extrapolation.ZERO;
/*     */   private int _nxu;
/*     */   private double _dxu;
/*     */   private double _fxu;
/*     */   private double _xf;
/*     */   private double _xs;
/*     */   private double _xb;
/*     */   private int _nxum;
/*     */   private float[] _yu;
/*     */   private int _nx1u;
/*     */   private int _nx2u;
/*     */   private int _nx3u;
/*     */   private double _x1f;
/*     */   private double _x2f;
/*     */   private double _x3f;
/*     */   private double _x1s;
/*     */   private double _x2s;
/*     */   private double _x3s;
/*     */   private double _x1b;
/*     */   private double _x2b;
/*     */   private double _x3b;
/*     */   private int _nx1um;
/*     */   private int _nx2um;
/*     */   private int _nx3um;
/*     */   private float[][] _yyu;
/*     */   private float[][][] _yyyu;
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/dsp/LinearInterpolator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */