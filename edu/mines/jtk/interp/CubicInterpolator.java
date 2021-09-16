/*     */ package edu.mines.jtk.interp;
/*     */ 
/*     */ import edu.mines.jtk.util.ArrayMath;
/*     */ import edu.mines.jtk.util.Check;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CubicInterpolator
/*     */ {
/*     */   private float[] _xd;
/*     */   private float[][] _yd;
/*     */   private int _index;
/*     */   private static final float FLT_O2 = 0.5F;
/*     */   private static final float FLT_O6 = 0.16666667F;
/*     */   
/*     */   public enum Method
/*     */   {
/*  49 */     LINEAR,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  56 */     MONOTONIC,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  62 */     SPLINE;
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
/*     */   public CubicInterpolator(float[] x, float[] y, float[] y1) {
/*  75 */     Check.argument(ArrayMath.isMonotonic(x), "array x is monotonic");
/*  76 */     int n = x.length;
/*  77 */     this._xd = new float[n];
/*  78 */     this._yd = new float[n][4];
/*  79 */     for (int i = 0; i < n; i++) {
/*  80 */       this._xd[i] = x[i];
/*  81 */       this._yd[i][0] = y[i];
/*  82 */       this._yd[i][1] = y1[i];
/*     */     } 
/*  84 */     compute2ndAnd3rdDerivatives(this._xd, this._yd);
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
/*     */   public CubicInterpolator(float[] x, float[] y) {
/*  96 */     this(Method.MONOTONIC, x, y);
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
/*     */   public CubicInterpolator(Method method, float[] x, float[] y) {
/* 109 */     this(method, x.length, x, y);
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
/*     */   public CubicInterpolator(Method method, int n, float[] x, float[] y) {
/* 123 */     Check.argument(ArrayMath.isMonotonic(x), "array x is monotonic");
/* 124 */     this._xd = new float[n];
/* 125 */     this._yd = new float[n][4];
/* 126 */     for (int i = 0; i < n; i++) {
/* 127 */       this._xd[i] = x[i];
/* 128 */       this._yd[i][0] = y[i];
/*     */     } 
/* 130 */     if (method == Method.LINEAR) {
/* 131 */       initLinear(n, this._xd, this._yd);
/* 132 */     } else if (method == Method.MONOTONIC) {
/* 133 */       initMonotonic(n, this._xd, this._yd);
/* 134 */     } else if (method == Method.SPLINE) {
/* 135 */       initSpline(n, this._xd, this._yd);
/*     */     } else {
/*     */       assert false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float interpolate(float x) {
/* 148 */     return interpolate0(x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float interpolate0(float x) {
/* 157 */     int i = index(x);
/* 158 */     return interpolate0(x - this._xd[i], this._yd[i]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float interpolate1(float x) {
/* 167 */     int i = index(x);
/* 168 */     return interpolate1(x - this._xd[i], this._yd[i]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float interpolate2(float x) {
/* 177 */     int i = index(x);
/* 178 */     return interpolate2(x - this._xd[i], this._yd[i]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float interpolate3(float x) {
/* 187 */     int i = index(x);
/* 188 */     return interpolate3(x - this._xd[i], this._yd[i]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] interpolate(float[] x) {
/* 199 */     return interpolate0(x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] interpolate0(float[] x) {
/* 208 */     float[] y = new float[x.length];
/* 209 */     interpolate0(x.length, x, y);
/* 210 */     return y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void interpolate(float[] x, float[] y) {
/* 221 */     interpolate0(x, y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void interpolate0(float[] x, float[] y) {
/* 230 */     interpolate0(x.length, x, y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] interpolate1(float[] x) {
/* 239 */     float[] y = new float[x.length];
/* 240 */     interpolate1(x.length, x, y);
/* 241 */     return y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void interpolate1(float[] x, float[] y) {
/* 250 */     interpolate1(x.length, x, y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] interpolate2(float[] x) {
/* 259 */     float[] y = new float[x.length];
/* 260 */     interpolate2(x.length, x, y);
/* 261 */     return y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void interpolate2(float[] x, float[] y) {
/* 270 */     interpolate2(x.length, x, y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] interpolate3(float[] x) {
/* 279 */     float[] y = new float[x.length];
/* 280 */     interpolate3(x.length, x, y);
/* 281 */     return y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void interpolate3(float[] x, float[] y) {
/* 290 */     interpolate3(x.length, x, y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void interpolate(int n, float[] x, float[] y) {
/* 301 */     interpolate0(n, x, y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void interpolate0(int n, float[] x, float[] y) {
/* 311 */     int[] js = { 0 };
/* 312 */     for (int i = 0; i < n; i++) {
/* 313 */       int j = index(x[i], this._xd, js);
/* 314 */       y[i] = interpolate0(x[i] - this._xd[j], this._yd[j]);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void interpolate1(int n, float[] x, float[] y) {
/* 325 */     int[] js = { 0 };
/* 326 */     for (int i = 0; i < n; i++) {
/* 327 */       int j = index(x[i], this._xd, js);
/* 328 */       y[i] = interpolate1(x[i] - this._xd[j], this._yd[j]);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void interpolate2(int n, float[] x, float[] y) {
/* 339 */     int[] js = { 0 };
/* 340 */     for (int i = 0; i < n; i++) {
/* 341 */       int j = index(x[i], this._xd, js);
/* 342 */       y[i] = interpolate2(x[i] - this._xd[j], this._yd[j]);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void interpolate3(int n, float[] x, float[] y) {
/* 353 */     int[] js = { 0 };
/* 354 */     for (int i = 0; i < n; i++) {
/* 355 */       int j = index(x[i], this._xd, js);
/* 356 */       y[i] = interpolate3(x[i] - this._xd[j], this._yd[j]);
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
/*     */   private int index(float x) {
/* 368 */     int index = ArrayMath.binarySearch(this._xd, x, this._index);
/* 369 */     if (index < 0)
/* 370 */       index = (index < -1) ? (-2 - index) : 0; 
/* 371 */     this._index = index;
/* 372 */     return index;
/*     */   }
/*     */   
/*     */   private static int index(float x, float[] xs, int[] i) {
/* 376 */     int index = ArrayMath.binarySearch(xs, x, i[0]);
/* 377 */     if (index < 0)
/* 378 */       index = (index < -1) ? (-2 - index) : 0; 
/* 379 */     i[0] = index;
/* 380 */     return index;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static float interpolate0(float dx, float[] yd) {
/* 386 */     return yd[0] + dx * (yd[1] + dx * (yd[2] * 0.5F + dx * yd[3] * 0.16666667F));
/*     */   }
/*     */   private static float interpolate1(float dx, float[] yd) {
/* 389 */     return yd[1] + dx * (yd[2] + dx * yd[3] * 0.5F);
/*     */   }
/*     */   private static float interpolate2(float dx, float[] yd) {
/* 392 */     return yd[2] + dx * yd[3];
/*     */   }
/*     */   private static float interpolate3(float dx, float[] yd) {
/* 395 */     return yd[3];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void initLinear(int n, float[] x, float[][] y) {
/* 404 */     if (n == 1) {
/* 405 */       y[0][1] = 0.0F;
/* 406 */       y[0][2] = 0.0F;
/* 407 */       y[0][3] = 0.0F;
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 412 */     for (int i = 0; i < n - 1; i++) {
/* 413 */       y[i][1] = (y[i + 1][0] - y[i][0]) / (x[i + 1] - x[i]);
/* 414 */       y[i][3] = 0.0F; y[i][2] = 0.0F;
/*     */     } 
/* 416 */     y[n - 1][1] = y[n - 2][1];
/* 417 */     y[n - 1][3] = 0.0F; y[n - 1][2] = 0.0F;
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
/*     */   private static void initMonotonic(int n, float[] x, float[][] y) {
/* 441 */     if (n == 1) {
/* 442 */       y[0][1] = 0.0F;
/* 443 */       y[0][2] = 0.0F;
/* 444 */       y[0][3] = 0.0F;
/*     */       
/*     */       return;
/*     */     } 
/* 448 */     if (n == 2) {
/* 449 */       y[1][1] = (y[1][0] - y[0][0]) / (x[1] - x[0]); y[0][1] = (y[1][0] - y[0][0]) / (x[1] - x[0]);
/* 450 */       y[1][2] = 0.0F; y[0][2] = 0.0F;
/* 451 */       y[1][3] = 0.0F; y[0][3] = 0.0F;
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 456 */     float h1 = x[1] - x[0];
/* 457 */     float h2 = x[2] - x[1];
/* 458 */     float hsum = h1 + h2;
/* 459 */     float del1 = (y[1][0] - y[0][0]) / h1;
/* 460 */     float del2 = (y[2][0] - y[1][0]) / h2;
/* 461 */     float w1 = (h1 + hsum) / hsum;
/* 462 */     float w2 = -h1 / hsum;
/* 463 */     y[0][1] = w1 * del1 + w2 * del2;
/* 464 */     if (y[0][1] * del1 <= 0.0F) {
/* 465 */       y[0][1] = 0.0F;
/* 466 */     } else if (del1 * del2 < 0.0F) {
/* 467 */       float dmax = 3.0F * del1;
/* 468 */       if (ArrayMath.abs(y[0][1]) > ArrayMath.abs(dmax)) y[0][1] = dmax;
/*     */     
/*     */     } 
/*     */     
/* 472 */     for (int i = 1; i < n - 1; i++) {
/*     */ 
/*     */       
/* 475 */       h1 = x[i] - x[i - 1];
/* 476 */       h2 = x[i + 1] - x[i];
/* 477 */       hsum = h1 + h2;
/* 478 */       del1 = (y[i][0] - y[i - 1][0]) / h1;
/* 479 */       del2 = (y[i + 1][0] - y[i][0]) / h2;
/*     */ 
/*     */       
/* 482 */       if (del1 * del2 <= 0.0F) {
/* 483 */         y[i][1] = 0.0F;
/*     */ 
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */ 
/*     */         
/* 491 */         float dmin = ArrayMath.min(ArrayMath.abs(del1), ArrayMath.abs(del2));
/* 492 */         float dmax = ArrayMath.max(ArrayMath.abs(del1), ArrayMath.abs(del2));
/* 493 */         float drat1 = del1 / dmax;
/* 494 */         float drat2 = del2 / dmax;
/* 495 */         float hsum3 = hsum + hsum + hsum;
/* 496 */         w1 = (hsum + h1) / hsum3;
/* 497 */         w2 = (hsum + h2) / hsum3;
/* 498 */         y[i][1] = dmin / (w1 * drat1 + w2 * drat2);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 503 */     w1 = -h2 / hsum;
/* 504 */     w2 = (h2 + hsum) / hsum;
/* 505 */     y[n - 1][1] = w1 * del1 + w2 * del2;
/* 506 */     if (y[n - 1][1] * del2 <= 0.0F) {
/* 507 */       y[n - 1][1] = 0.0F;
/* 508 */     } else if (del1 * del2 < 0.0F) {
/* 509 */       float dmax = 3.0F * del2;
/* 510 */       if (ArrayMath.abs(y[n - 1][1]) > ArrayMath.abs(dmax)) y[n - 1][1] = dmax;
/*     */     
/*     */     } 
/* 513 */     compute2ndAnd3rdDerivatives(x, y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void initSpline(int n, float[] x, float[][] y) {
/* 523 */     if (n == 1) {
/* 524 */       y[0][1] = 0.0F;
/* 525 */       y[0][2] = 0.0F;
/* 526 */       y[0][3] = 0.0F;
/*     */       
/*     */       return;
/*     */     } 
/* 530 */     if (n == 2) {
/* 531 */       y[1][1] = (y[1][0] - y[0][0]) / (x[1] - x[0]); y[0][1] = (y[1][0] - y[0][0]) / (x[1] - x[0]);
/* 532 */       y[1][2] = 0.0F; y[0][2] = 0.0F;
/* 533 */       y[1][3] = 0.0F; y[0][3] = 0.0F;
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 538 */     float h1 = x[1] - x[0];
/* 539 */     float h2 = x[2] - x[1];
/* 540 */     float hsum = h1 + h2;
/* 541 */     float del1 = (y[1][0] - y[0][0]) / h1;
/* 542 */     float del2 = (y[2][0] - y[1][0]) / h2;
/* 543 */     float w1 = (h1 + hsum) / hsum;
/* 544 */     float w2 = -h1 / hsum;
/* 545 */     float sleft = w1 * del1 + w2 * del2;
/* 546 */     if (sleft * del1 <= 0.0F) {
/* 547 */       sleft = 0.0F;
/* 548 */     } else if (del1 * del2 < 0.0F) {
/* 549 */       float dmax = 3.0F * del1;
/* 550 */       if (ArrayMath.abs(sleft) > ArrayMath.abs(dmax)) sleft = dmax;
/*     */     
/*     */     } 
/*     */     
/* 554 */     h1 = x[n - 2] - x[n - 3];
/* 555 */     h2 = x[n - 1] - x[n - 2];
/* 556 */     hsum = h1 + h2;
/* 557 */     del1 = (y[n - 2][0] - y[n - 3][0]) / h1;
/* 558 */     del2 = (y[n - 1][0] - y[n - 2][0]) / h2;
/* 559 */     w1 = -h2 / hsum;
/* 560 */     w2 = (h2 + hsum) / hsum;
/* 561 */     float sright = w1 * del1 + w2 * del2;
/* 562 */     if (sright * del2 <= 0.0F) {
/* 563 */       sright = 0.0F;
/* 564 */     } else if (del1 * del2 < 0.0F) {
/* 565 */       float dmax = 3.0F * del2;
/* 566 */       if (ArrayMath.abs(sright) > ArrayMath.abs(dmax)) sright = dmax;
/*     */     
/*     */     } 
/*     */     
/* 570 */     float[] work = new float[n];
/* 571 */     work[0] = 1.0F;
/* 572 */     y[0][2] = 2.0F * sleft;
/* 573 */     for (int i = 1; i < n - 1; i++) {
/* 574 */       h1 = x[i] - x[i - 1];
/* 575 */       h2 = x[i + 1] - x[i];
/* 576 */       del1 = (y[i][0] - y[i - 1][0]) / h1;
/* 577 */       del2 = (y[i + 1][0] - y[i][0]) / h2;
/* 578 */       float alpha = h2 / (h1 + h2);
/* 579 */       work[i] = alpha;
/* 580 */       y[i][2] = 3.0F * (alpha * del1 + (1.0F - alpha) * del2);
/*     */     } 
/* 582 */     work[n - 1] = 0.0F;
/* 583 */     y[n - 1][2] = 2.0F * sright;
/*     */ 
/*     */     
/* 586 */     float t = 2.0F;
/* 587 */     y[0][1] = y[0][2] / t; int j;
/* 588 */     for (j = 1; j < n; j++) {
/* 589 */       y[j][3] = (1.0F - work[j - 1]) / t;
/* 590 */       t = 2.0F - work[j] * y[j][3];
/* 591 */       y[j][1] = (y[j][2] - work[j] * y[j - 1][1]) / t;
/*     */     } 
/* 593 */     for (j = n - 2; j >= 0; j--) {
/* 594 */       y[j][1] = y[j][1] - y[j + 1][3] * y[j + 1][1];
/*     */     }
/* 596 */     compute2ndAnd3rdDerivatives(x, y);
/*     */   }
/*     */   
/*     */   private static void compute2ndAnd3rdDerivatives(float[] x, float[][] y) {
/* 600 */     int n = x.length;
/* 601 */     for (int i = 0; i < n - 1; i++) {
/* 602 */       float h2 = x[i + 1] - x[i];
/* 603 */       float del2 = (y[i + 1][0] - y[i][0]) / h2;
/* 604 */       float divdf3 = y[i][1] + y[i + 1][1] - 2.0F * del2;
/* 605 */       y[i][2] = 2.0F * (del2 - y[i][1] - divdf3) / h2;
/* 606 */       y[i][3] = divdf3 / h2 * 6.0F / h2;
/*     */     } 
/* 608 */     y[n - 1][2] = y[n - 2][2] + (x[n - 1] - x[n - 2]) * y[n - 2][3];
/* 609 */     y[n - 1][3] = y[n - 2][3];
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/interp/CubicInterpolator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */