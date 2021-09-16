/*     */ package edu.mines.jtk.dsp;
/*     */ 
/*     */ import edu.mines.jtk.util.ArrayMath;
/*     */ import edu.mines.jtk.util.Check;
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
/*     */ public class RecursiveExponentialFilter
/*     */ {
/*     */   private float _sigma1;
/*     */   private float _a1;
/*     */   private float _sigma2;
/*     */   private float _a2;
/*     */   private float _sigma3;
/*     */   private float _a3;
/*     */   private boolean _ei;
/*     */   private boolean _zs;
/*     */   
/*     */   public enum Edges
/*     */   {
/*  71 */     INPUT_ZERO_VALUE,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  77 */     INPUT_ZERO_SLOPE,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  83 */     OUTPUT_ZERO_VALUE,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  89 */     OUTPUT_ZERO_SLOPE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RecursiveExponentialFilter(double sigma) {
/*  99 */     this(sigma, sigma, sigma);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RecursiveExponentialFilter(double sigma1, double sigma23) {
/* 108 */     this(sigma1, sigma23, sigma23);
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
/*     */   public void setEdges(Edges edges) {
/*     */     this._ei = (edges == Edges.INPUT_ZERO_VALUE || edges == Edges.INPUT_ZERO_SLOPE);
/*     */     this._zs = (edges == Edges.INPUT_ZERO_SLOPE || edges == Edges.OUTPUT_ZERO_SLOPE);
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
/*     */   public void apply(float[] x, float[] y) {
/*     */     apply1(x, y);
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
/*     */   public void apply(float[][] x, float[][] y) {
/*     */     apply2(x, y);
/*     */     apply1(y, y);
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
/*     */   public void apply(float[][][] x, float[][][] y) {
/*     */     apply3(x, y);
/*     */     apply2(y, y);
/*     */     apply1(y, y);
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
/*     */   public void apply1(float[] x, float[] y) {
/*     */     smooth1(this._ei, this._zs, this._a1, x, y);
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
/*     */   public RecursiveExponentialFilter(double sigma1, double sigma2, double sigma3)
/*     */   {
/* 278 */     this._ei = false;
/* 279 */     this._zs = true; Check.argument((sigma1 >= 0.0D), "sigma is non-negative"); Check.argument((sigma2 >= 0.0D), "sigma is non-negative"); Check.argument((sigma3 >= 0.0D), "sigma is non-negative"); this._sigma1 = (float)sigma1; this._sigma2 = (float)sigma2; this._sigma3 = (float)sigma3;
/*     */     this._a1 = aFromSigma(sigma1);
/*     */     this._a2 = aFromSigma(sigma2);
/* 282 */     this._a3 = aFromSigma(sigma3); } private static float aFromSigma(double sigma) { if (sigma <= 0.0D)
/* 283 */       return 0.0F; 
/* 284 */     double ss = sigma * sigma;
/* 285 */     return (float)((1.0D + ss - ArrayMath.sqrt(1.0D + 2.0D * ss)) / ss); } public void apply1(float[][] x, float[][] y) { int n2 = x.length;
/*     */     for (int i2 = 0; i2 < n2; i2++) {
/*     */       float[] x2 = x[i2];
/*     */       float[] y2 = y[i2];
/*     */       smooth1(this._ei, this._zs, this._a1, x2, y2);
/*     */     }  }
/*     */   public void apply2(float[][] x, float[][] y) { smooth2(this._ei, this._zs, this._a2, x, y); }
/* 292 */   private static void smooth1(boolean ei, boolean zs, float a, float[] x, float[] y) { if (a == 0.0F) {
/* 293 */       ArrayMath.copy(x, y);
/* 294 */     } else if (ei) {
/* 295 */       smooth1Ei(zs, a, x, y);
/*     */     } else {
/* 297 */       smooth1Eo(zs, a, x, y);
/*     */     }  }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void smooth2(boolean ei, boolean zs, float a, float[][] x, float[][] y) {
/* 305 */     if (a == 0.0F) {
/* 306 */       ArrayMath.copy(x, y);
/* 307 */     } else if (ei) {
/* 308 */       smooth2Ei(zs, a, x, y);
/*     */     } else {
/* 310 */       smooth2Eo(zs, a, x, y);
/*     */     } 
/*     */   } public void apply1(float[][][] x, float[][][] y) { final float[][][] xx = x; final float[][][] yy = y; final int n2 = (x[0]).length; int n3 = x.length; Parallel.loop(n3, new Parallel.LoopInt() { public void compute(int i3) { for (int i2 = 0; i2 < n2; i2++) {
/*     */               float[] x32 = xx[i3][i2]; float[] y32 = yy[i3][i2]; RecursiveExponentialFilter.smooth1(RecursiveExponentialFilter.this._ei, RecursiveExponentialFilter.this._zs, RecursiveExponentialFilter.this._a1, x32, y32);
/*     */             }  } }
/*     */       ); } public void apply2(float[][][] x, float[][][] y) { final float[][][] xx = x; final float[][][] yy = y; int n3 = x.length; Parallel.loop(n3, new Parallel.LoopInt() {
/*     */           public void compute(int i3) { float[][] x3 = xx[i3]; float[][] y3 = yy[i3];
/*     */             RecursiveExponentialFilter.smooth2(RecursiveExponentialFilter.this._ei, RecursiveExponentialFilter.this._zs, RecursiveExponentialFilter.this._a2, x3, y3); }
/* 318 */         }); } private static void smooth1Ei(boolean zs, float a, float[] x, float[] y) { int n1 = x.length;
/* 319 */     float b = 1.0F - a;
/* 320 */     float sx = zs ? 1.0F : b;
/* 321 */     float sy = a;
/* 322 */     float yi = y[0] = sx * x[0]; int i1;
/* 323 */     for (i1 = 1; i1 < n1 - 1; i1++)
/* 324 */       y[i1] = yi = a * yi + b * x[i1]; 
/* 325 */     sx /= 1.0F + a;
/* 326 */     sy /= 1.0F + a;
/* 327 */     y[n1 - 1] = yi = sy * yi + sx * x[n1 - 1];
/* 328 */     for (i1 = n1 - 2; i1 >= 0; i1--) {
/* 329 */       y[i1] = yi = a * yi + b * y[i1];
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void smooth2Ei(boolean zs, float a, float[][] x, float[][] y) {
/* 336 */     int n1 = (x[0]).length;
/* 337 */     int n2 = x.length;
/* 338 */     float b = 1.0F - a;
/* 339 */     float sx = zs ? 1.0F : b;
/* 340 */     float sy = a;
/* 341 */     for (int j = 0; j < n1; j++)
/* 342 */       y[0][j] = sx * x[0][j]; 
/* 343 */     for (int i = 1; i < n2 - 1; i++) {
/* 344 */       for (int k = 0; k < n1; k++)
/* 345 */         y[i][k] = a * y[i - 1][k] + b * x[i][k]; 
/* 346 */     }  sx /= 1.0F + a;
/* 347 */     sy /= 1.0F + a;
/* 348 */     for (int i1 = 0; i1 < n1; i1++)
/* 349 */       y[n2 - 1][i1] = sy * y[n2 - 2][i1] + sx * x[n2 - 1][i1]; 
/* 350 */     for (int i2 = n2 - 2; i2 >= 0; i2--) {
/* 351 */       for (int k = 0; k < n1; k++)
/* 352 */         y[i2][k] = a * y[i2 + 1][k] + b * y[i2][k]; 
/*     */     } 
/*     */   } public void apply3(float[][][] x, float[][][] y) { final float[][][] xx = x; final float[][][] yy = y; int n2 = (x[0]).length; final int n3 = x.length;
/*     */     Parallel.loop(n2, new Parallel.LoopInt() { public void compute(int i2) { float[][] x2 = new float[n3][];
/*     */             float[][] y2 = new float[n3][];
/*     */             for (int i3 = 0; i3 < n3; i3++) {
/*     */               x2[i3] = xx[i3][i2];
/*     */               y2[i3] = yy[i3][i2];
/*     */             } 
/*     */             RecursiveExponentialFilter.smooth2(RecursiveExponentialFilter.this._ei, RecursiveExponentialFilter.this._zs, RecursiveExponentialFilter.this._a3, x2, y2); } }
/* 362 */       ); } private static void smooth1Eo(boolean zs, float a, float[] x, float[] y) { int n1 = x.length;
/* 363 */     float aa = a * a;
/* 364 */     float ss = zs ? (1.0F - a) : 1.0F;
/* 365 */     float gg = zs ? (aa - a) : aa;
/* 366 */     float c = (1.0F - aa - ss) / ss;
/* 367 */     float d = 1.0F / (1.0F - aa + gg * (1.0F + c * ArrayMath.pow(aa, (n1 - 1))));
/* 368 */     float e = (1.0F - a) * (1.0F - a) * 1.1920929E-7F / 4.0F;
/*     */ 
/*     */     
/* 371 */     ArrayMath.mul((1.0F - a) * (1.0F - a), x, y);
/*     */ 
/*     */     
/* 374 */     int k1 = ArrayMath.min((int)ArrayMath.ceil(ArrayMath.log(e) / ArrayMath.log(a)), 2 * n1 - 2);
/* 375 */     float ynm1 = 0.0F;
/* 376 */     int m1 = k1 - n1 + 1; int i1;
/* 377 */     for (i1 = m1; i1 > 0; i1--)
/* 378 */       ynm1 = a * ynm1 + y[i1]; 
/* 379 */     ynm1 *= c;
/* 380 */     if (n1 - k1 < 1)
/* 381 */       ynm1 = a * ynm1 + (1.0F + c) * y[0]; 
/* 382 */     m1 = ArrayMath.max(n1 - k1, 1);
/* 383 */     for (i1 = m1; i1 < n1; i1++)
/* 384 */       ynm1 = a * ynm1 + y[i1]; 
/* 385 */     ynm1 *= d;
/*     */ 
/*     */     
/* 388 */     y[n1 - 1] = y[n1 - 1] - gg * ynm1;
/* 389 */     for (i1 = n1 - 2; i1 >= 0; i1--)
/* 390 */       y[i1] = y[i1] + a * y[i1 + 1]; 
/* 391 */     y[0] = y[0] / ss;
/*     */ 
/*     */     
/* 394 */     for (i1 = 1; i1 < n1 - 1; i1++)
/* 395 */       y[i1] = y[i1] + a * y[i1 - 1]; 
/* 396 */     y[n1 - 1] = ynm1; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void smooth2Eo(boolean zs, float a, float[][] x, float[][] y) {
/* 403 */     int n1 = (x[0]).length;
/* 404 */     int n2 = x.length;
/* 405 */     float aa = a * a;
/* 406 */     float ss = zs ? (1.0F - a) : 1.0F;
/* 407 */     float gg = zs ? (aa - a) : aa;
/* 408 */     float c = (1.0F - aa - ss) / ss;
/* 409 */     float d = 1.0F / (1.0F - aa + gg * (1.0F + c * ArrayMath.pow(aa, (n2 - 1))));
/* 410 */     float e = (1.0F - a) * (1.0F - a) * 1.1920929E-7F / 4.0F;
/*     */ 
/*     */     
/* 413 */     ArrayMath.mul((1.0F - a) * (1.0F - a), x, y);
/*     */ 
/*     */     
/* 416 */     int k2 = ArrayMath.min((int)ArrayMath.ceil(ArrayMath.log(e) / ArrayMath.log(a)), 2 * n2 - 2);
/* 417 */     float[] ynm1 = new float[n1];
/* 418 */     int m2 = k2 - n2 + 1;
/* 419 */     for (int k = m2; k > 0; k--) {
/* 420 */       float[] yi = y[k];
/* 421 */       for (int i4 = 0; i4 < n1; i4++)
/* 422 */         ynm1[i4] = a * ynm1[i4] + yi[i4]; 
/*     */     }  int j;
/* 424 */     for (j = 0; j < n1; j++)
/* 425 */       ynm1[j] = ynm1[j] * c; 
/* 426 */     if (n2 - k2 < 1)
/* 427 */       for (j = 0; j < n1; j++) {
/* 428 */         ynm1[j] = a * ynm1[j] + (1.0F + c) * y[0][j];
/*     */       } 
/* 430 */     m2 = ArrayMath.max(n2 - k2, 1);
/* 431 */     for (int i = m2; i < n2; i++) {
/* 432 */       float[] yi = y[i];
/* 433 */       for (int i4 = 0; i4 < n1; i4++)
/* 434 */         ynm1[i4] = a * ynm1[i4] + yi[i4]; 
/*     */     }  int i1;
/* 436 */     for (i1 = 0; i1 < n1; i1++) {
/* 437 */       ynm1[i1] = ynm1[i1] * d;
/*     */     }
/*     */     
/* 440 */     for (i1 = 0; i1 < n1; i1++)
/* 441 */       y[n2 - 1][i1] = y[n2 - 1][i1] - gg * ynm1[i1]; 
/* 442 */     for (int i2 = n2 - 2; i2 >= 0; i2--) {
/* 443 */       float[] yi = y[i2];
/* 444 */       float[] yp = y[i2 + 1];
/* 445 */       for (int i4 = 0; i4 < n1; i4++)
/* 446 */         yi[i4] = yi[i4] + a * yp[i4]; 
/*     */     } 
/* 448 */     float oss = 1.0F / ss;
/* 449 */     for (int i3 = 0; i3 < n1; i3++) {
/* 450 */       y[0][i3] = y[0][i3] * oss;
/*     */     }
/*     */     
/* 453 */     for (int n = 1; n < n2 - 1; n++) {
/* 454 */       float[] yi = y[n];
/* 455 */       float[] ym = y[n - 1];
/* 456 */       for (int i4 = 0; i4 < n1; i4++)
/* 457 */         yi[i4] = yi[i4] + a * ym[i4]; 
/*     */     } 
/* 459 */     for (int m = 0; m < n1; m++)
/* 460 */       y[n2 - 1][m] = ynm1[m]; 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/dsp/RecursiveExponentialFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */