/*     */ package edu.mines.jtk.dsp;
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
/*     */ public class Eigen
/*     */ {
/*     */   private static final double ONE_THIRD = 0.3333333333333333D;
/*     */   
/*     */   public static void solveSymmetric22(float[][] a, float[][] v, float[] d) {
/*  40 */     float a00 = a[0][0];
/*  41 */     float a01 = a[0][1], a11 = a[1][1];
/*     */ 
/*     */     
/*  44 */     float v00 = 1.0F, v01 = 0.0F;
/*  45 */     float v10 = 0.0F, v11 = 1.0F;
/*     */ 
/*     */     
/*  48 */     if (a01 != 0.0F) {
/*  49 */       float t, tiny = 0.1F * ArrayMath.sqrt(1.1920929E-7F);
/*     */       
/*  51 */       float u = a11 - a00;
/*  52 */       if (ArrayMath.abs(a01) < tiny * ArrayMath.abs(u)) {
/*  53 */         t = a01 / u;
/*     */       } else {
/*  55 */         float f = 0.5F * u / a01;
/*  56 */         t = (f >= 0.0F) ? (1.0F / (f + ArrayMath.sqrt(1.0F + f * f))) : (1.0F / (f - ArrayMath.sqrt(1.0F + f * f)));
/*     */       } 
/*  58 */       float c = 1.0F / ArrayMath.sqrt(1.0F + t * t);
/*  59 */       float s = t * c;
/*  60 */       u = s / (1.0F + c);
/*  61 */       float r = t * a01;
/*  62 */       a00 -= r;
/*  63 */       a11 += r;
/*     */       
/*  65 */       float vpr = v00;
/*  66 */       float vqr = v10;
/*  67 */       v00 = vpr - s * (vqr + vpr * u);
/*  68 */       v10 = vqr + s * (vpr - vqr * u);
/*  69 */       vpr = v01;
/*  70 */       vqr = v11;
/*  71 */       v01 = vpr - s * (vqr + vpr * u);
/*  72 */       v11 = vqr + s * (vpr - vqr * u);
/*     */     } 
/*     */ 
/*     */     
/*  76 */     d[0] = a00;
/*  77 */     d[1] = a11;
/*  78 */     v[0][0] = v00; v[0][1] = v01;
/*  79 */     v[1][0] = v10; v[1][1] = v11;
/*     */ 
/*     */     
/*  82 */     if (d[0] < d[1]) {
/*  83 */       float dt = d[1];
/*  84 */       d[1] = d[0];
/*  85 */       d[0] = dt;
/*  86 */       float[] vt = v[1];
/*  87 */       v[1] = v[0];
/*  88 */       v[0] = vt;
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
/*     */   public static void solveSymmetric22(double[][] a, double[][] v, double[] d) {
/* 104 */     double a00 = a[0][0];
/* 105 */     double a01 = a[0][1], a11 = a[1][1];
/*     */ 
/*     */     
/* 108 */     double v00 = 1.0D, v01 = 0.0D;
/* 109 */     double v10 = 0.0D, v11 = 1.0D;
/*     */ 
/*     */     
/* 112 */     if (a01 != 0.0D) {
/* 113 */       double t, tiny = 0.1D * ArrayMath.sqrt(2.220446049250313E-16D);
/*     */       
/* 115 */       double u = a11 - a00;
/* 116 */       if (ArrayMath.abs(a01) < tiny * ArrayMath.abs(u)) {
/* 117 */         t = a01 / u;
/*     */       } else {
/* 119 */         double d1 = 0.5D * u / a01;
/* 120 */         t = (d1 >= 0.0D) ? (1.0D / (d1 + ArrayMath.sqrt(1.0D + d1 * d1))) : (1.0D / (d1 - ArrayMath.sqrt(1.0D + d1 * d1)));
/*     */       } 
/* 122 */       double c = 1.0D / ArrayMath.sqrt(1.0D + t * t);
/* 123 */       double s = t * c;
/* 124 */       u = s / (1.0D + c);
/* 125 */       double r = t * a01;
/* 126 */       a00 -= r;
/* 127 */       a11 += r;
/*     */       
/* 129 */       double vpr = v00;
/* 130 */       double vqr = v10;
/* 131 */       v00 = vpr - s * (vqr + vpr * u);
/* 132 */       v10 = vqr + s * (vpr - vqr * u);
/* 133 */       vpr = v01;
/* 134 */       vqr = v11;
/* 135 */       v01 = vpr - s * (vqr + vpr * u);
/* 136 */       v11 = vqr + s * (vpr - vqr * u);
/*     */     } 
/*     */ 
/*     */     
/* 140 */     d[0] = a00;
/* 141 */     d[1] = a11;
/* 142 */     v[0][0] = v00; v[0][1] = v01;
/* 143 */     v[1][0] = v10; v[1][1] = v11;
/*     */ 
/*     */     
/* 146 */     if (d[0] < d[1]) {
/* 147 */       double dt = d[1];
/* 148 */       d[1] = d[0];
/* 149 */       d[0] = dt;
/* 150 */       double[] vt = v[1];
/* 151 */       v[1] = v[0];
/* 152 */       v[0] = vt;
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
/*     */   public static void solveSymmetric33(double[][] a, double[][] v, double[] d) {
/* 166 */     solveSymmetric33Jacobi(a, v, d);
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
/*     */   public static void solveSymmetric33Fast(double[][] a, double[][] v, double[] d) {
/* 184 */     solveSymmetric33Hybrid(a, v, d);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void sortDescending33(double[][] v, double[] d) {
/* 194 */     for (int i = 0; i < 3; i++) {
/* 195 */       for (int j = i; j > 0 && d[j - 1] < d[j]; j--) {
/* 196 */         double dj = d[j];
/* 197 */         d[j] = d[j - 1];
/* 198 */         d[j - 1] = dj;
/* 199 */         double[] vj = v[j];
/* 200 */         v[j] = v[j - 1];
/* 201 */         v[j - 1] = vj;
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
/* 212 */   private static final double ONE_OVER_SQRT3 = 1.0D / ArrayMath.sqrt(3.0D);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void getEigenvaluesSymmetric33(double[][] a, double[] d) {
/* 219 */     double a00 = a[0][0];
/* 220 */     double a01 = a[0][1], a11 = a[1][1];
/* 221 */     double a02 = a[0][2], a12 = a[1][2], a22 = a[2][2];
/* 222 */     double de = a01 * a12;
/* 223 */     double dd = a01 * a01;
/* 224 */     double ee = a12 * a12;
/* 225 */     double ff = a02 * a02;
/* 226 */     double c2 = a00 + a11 + a22;
/* 227 */     double c1 = a00 * a11 + a00 * a22 + a11 * a22 - dd + ee + ff;
/* 228 */     double c0 = a22 * dd + a00 * ee + a11 * ff - a00 * a11 * a22 - 2.0D * a02 * de;
/* 229 */     double p = c2 * c2 - 3.0D * c1;
/* 230 */     double q = c2 * (p - 1.5D * c1) - 13.5D * c0;
/* 231 */     double t = 27.0D * (0.25D * c1 * c1 * (p - c1) + c0 * (q + 6.75D * c0));
/* 232 */     double phi = 0.3333333333333333D * ArrayMath.atan2(ArrayMath.sqrt(ArrayMath.abs(t)), q);
/* 233 */     double sqrtp = ArrayMath.sqrt(ArrayMath.abs(p));
/* 234 */     double c = sqrtp * ArrayMath.cos(phi);
/* 235 */     double s = ONE_OVER_SQRT3 * sqrtp * ArrayMath.sin(phi);
/* 236 */     double dt = 0.3333333333333333D * (c2 - c);
/* 237 */     d[0] = dt + c;
/* 238 */     d[1] = dt + s;
/* 239 */     d[2] = dt - s;
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
/*     */   private static void solveSymmetric33Hybrid(double[][] a, double[][] v, double[] d) {
/* 252 */     getEigenvaluesSymmetric33(a, d);
/* 253 */     double a00 = a[0][0];
/* 254 */     double a01 = a[0][1], a11 = a[1][1];
/* 255 */     double a02 = a[0][2], a12 = a[1][2];
/* 256 */     double d0 = d[0], d1 = d[1], d2 = d[2];
/* 257 */     double n0 = a00 * a00 + a01 * a01 + a02 * a02;
/* 258 */     double n1 = a01 * a01 + a11 * a11 + a12 * a12;
/* 259 */     double t = ArrayMath.abs(d0);
/* 260 */     double u = ArrayMath.abs(d1);
/* 261 */     if (u > t) t = u; 
/* 262 */     u = ArrayMath.abs(d2);
/* 263 */     if (u > t) t = u; 
/* 264 */     if (t < 1.0D) {
/* 265 */       u = t;
/*     */     } else {
/* 267 */       u = ArrayMath.sqrt(t);
/*     */     } 
/* 269 */     double error = 5.6843418860808015E-14D * (n0 + u) * (n1 + u);
/* 270 */     double v10 = a01 * a12 - a02 * a11;
/* 271 */     double v11 = a02 * a01 - a12 * a00;
/* 272 */     double v12 = a01 * a01;
/*     */ 
/*     */     
/* 275 */     double v00 = v10 + a02 * d0;
/* 276 */     double v01 = v11 + a12 * d0;
/* 277 */     double v02 = (a00 - d0) * (a11 - d0) - v12;
/* 278 */     double v0s = v00 * v00 + v01 * v01 + v02 * v02;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 283 */     if (v0s <= error) {
/* 284 */       solveSymmetric33Ql(a, v, d);
/*     */       return;
/*     */     } 
/* 287 */     v0s = ArrayMath.sqrt(1.0D / v0s);
/* 288 */     v00 *= v0s;
/* 289 */     v01 *= v0s;
/* 290 */     v02 *= v0s;
/*     */ 
/*     */ 
/*     */     
/* 294 */     v10 += a02 * d1;
/* 295 */     v11 += a12 * d1;
/* 296 */     v12 = (a00 - d1) * (a11 - d1) - v12;
/* 297 */     double v1s = v10 * v10 + v11 * v11 + v12 * v12;
/*     */ 
/*     */     
/* 300 */     if (v1s <= error) {
/* 301 */       solveSymmetric33Ql(a, v, d);
/*     */       return;
/*     */     } 
/* 304 */     v1s = ArrayMath.sqrt(1.0D / v1s);
/* 305 */     v10 *= v1s;
/* 306 */     v11 *= v1s;
/* 307 */     v12 *= v1s;
/*     */ 
/*     */ 
/*     */     
/* 311 */     double v20 = v01 * v12 - v02 * v11;
/* 312 */     double v21 = v02 * v10 - v00 * v12;
/* 313 */     double v22 = v00 * v11 - v01 * v10;
/*     */ 
/*     */     
/* 316 */     v[0][0] = v00; v[0][1] = v01; v[0][2] = v02;
/* 317 */     v[1][0] = v10; v[1][1] = v11; v[1][2] = v12;
/* 318 */     v[2][0] = v20; v[2][1] = v21; v[2][2] = v22;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void solveSymmetric33Ql(double[][] a, double[][] v, double[] d) {
/* 328 */     double[] e = new double[3];
/* 329 */     reduceSymmetric33(a, v, d, e);
/*     */ 
/*     */     
/* 332 */     for (int l = 0; l < 2; l++) {
/*     */ 
/*     */       
/* 335 */       for (int niter = 0; niter <= 100; niter++) {
/* 336 */         if (niter == 100) {
/* 337 */           System.out.println("A ="); ArrayMath.dump(a);
/* 338 */           System.out.println("V ="); ArrayMath.dump(v);
/* 339 */           System.out.println("d ="); ArrayMath.dump(d);
/*     */         } 
/* 341 */         Check.state((niter < 100), "number of QL iterations is less than 100");
/*     */         
/*     */         int m;
/*     */         
/* 345 */         for (m = l; m < 2; m++) {
/* 346 */           double d1 = ArrayMath.abs(d[m]) + ArrayMath.abs(d[m + 1]);
/* 347 */           if (ArrayMath.abs(e[m]) + d1 == d1)
/*     */             break; 
/*     */         } 
/* 350 */         if (m == l) {
/*     */           break;
/*     */         }
/*     */         
/* 354 */         double g = (d[l + 1] - d[l]) / (e[l] + e[l]);
/* 355 */         double r = ArrayMath.sqrt(g * g + 1.0D);
/* 356 */         if (g > 0.0D) {
/* 357 */           g = d[m] - d[l] + e[l] / (g + r);
/*     */         } else {
/* 359 */           g = d[m] - d[l] + e[l] / (g - r);
/*     */         } 
/* 361 */         double s = 1.0D;
/* 362 */         double c = 1.0D;
/* 363 */         double p = 0.0D;
/* 364 */         for (int i = m - 1; i >= l; i--) {
/* 365 */           double f = s * e[i];
/* 366 */           double b = c * e[i];
/* 367 */           if (ArrayMath.abs(f) > ArrayMath.abs(g)) {
/* 368 */             c = g / f;
/* 369 */             r = ArrayMath.sqrt(c * c + 1.0D);
/* 370 */             e[i + 1] = f * r;
/* 371 */             s = 1.0D / r;
/* 372 */             c *= s;
/*     */           } else {
/* 374 */             s = f / g;
/* 375 */             r = ArrayMath.sqrt(s * s + 1.0D);
/* 376 */             e[i + 1] = g * r;
/* 377 */             c = 1.0D / r;
/* 378 */             s *= c;
/*     */           } 
/* 380 */           g = d[i + 1] - p;
/* 381 */           r = (d[i] - g) * s + 2.0D * c * b;
/* 382 */           p = s * r;
/* 383 */           d[i + 1] = g + p;
/* 384 */           g = c * r - b;
/*     */ 
/*     */           
/* 387 */           for (int k = 0; k < 3; k++) {
/* 388 */             double t = v[i + 1][k];
/* 389 */             v[i + 1][k] = s * v[i][k] + c * t;
/* 390 */             v[i][k] = c * v[i][k] - s * t;
/*     */           } 
/*     */         } 
/* 393 */         d[l] = d[l] - p;
/* 394 */         e[l] = g;
/* 395 */         e[m] = 0.0D;
/*     */       } 
/*     */     } 
/* 398 */     sortDescending33(v, d);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void reduceSymmetric33(double[][] a, double[][] v, double[] d, double[] e) {
/* 409 */     double d0, d1, d2, e1, a00 = a[0][0];
/* 410 */     double a01 = a[0][1], a11 = a[1][1];
/* 411 */     double a02 = a[0][2], a12 = a[1][2], a22 = a[2][2];
/* 412 */     double v11 = 1.0D;
/* 413 */     double v12 = 0.0D;
/* 414 */     double v21 = 0.0D;
/* 415 */     double v22 = 1.0D;
/* 416 */     double h = a01 * a01 + a02 * a02;
/* 417 */     double g = (a01 > 0.0D) ? -ArrayMath.sqrt(h) : ArrayMath.sqrt(h);
/* 418 */     double e0 = g;
/* 419 */     double f = g * a01;
/* 420 */     double u1 = a01 - g;
/* 421 */     double u2 = a02;
/* 422 */     double omega = h - f;
/*     */     
/* 424 */     if (omega > 0.0D) {
/* 425 */       omega = 1.0D / omega;
/* 426 */       double s = 0.0D;
/* 427 */       f = a11 * u1 + a12 * u2;
/* 428 */       double q1 = omega * f;
/* 429 */       s += u1 * f;
/* 430 */       f = a12 * u1 + a22 * u2;
/* 431 */       double q2 = omega * f;
/* 432 */       s += u2 * f;
/* 433 */       s *= 0.5D * omega * omega;
/* 434 */       q1 -= s * u1;
/* 435 */       q2 -= s * u2;
/* 436 */       d0 = a00;
/* 437 */       d1 = a11 - 2.0D * q1 * u1;
/* 438 */       d2 = a22 - 2.0D * q2 * u2;
/* 439 */       f = omega * u1;
/* 440 */       v11 -= f * u1;
/* 441 */       v12 -= f * u2;
/* 442 */       f = omega * u2;
/* 443 */       v21 -= f * u1;
/* 444 */       v22 -= f * u2;
/* 445 */       e1 = a12 - q1 * u2 - u1 * q2;
/*     */     } else {
/* 447 */       d0 = a00;
/* 448 */       d1 = a11;
/* 449 */       d2 = a22;
/* 450 */       e1 = a12;
/*     */     } 
/* 452 */     d[0] = d0;
/* 453 */     d[1] = d1;
/* 454 */     d[2] = d2;
/* 455 */     e[0] = e0;
/* 456 */     e[1] = e1;
/* 457 */     v[0][0] = 1.0D; v[0][1] = 0.0D; v[0][2] = 0.0D;
/* 458 */     v[1][0] = 0.0D; v[1][1] = v11; v[1][2] = v12;
/* 459 */     v[2][0] = 0.0D; v[2][1] = v21; v[2][2] = v22;
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
/*     */   private static void solveSymmetric33Jacobi(double[][] a, double[][] v, double[] d) {
/* 475 */     double a00 = a[0][0];
/* 476 */     double a01 = a[0][1], a11 = a[1][1];
/* 477 */     double a02 = a[0][2], a12 = a[1][2], a22 = a[2][2];
/*     */ 
/*     */     
/* 480 */     double v00 = 1.0D, v01 = 0.0D, v02 = 0.0D;
/* 481 */     double v10 = 0.0D, v11 = 1.0D, v12 = 0.0D;
/* 482 */     double v20 = 0.0D, v21 = 0.0D, v22 = 1.0D;
/*     */ 
/*     */     
/* 485 */     double tiny = 0.1D * ArrayMath.sqrt(2.220446049250313E-16D);
/*     */ 
/*     */     
/* 488 */     double aa01 = ArrayMath.abs(a01);
/* 489 */     double aa02 = ArrayMath.abs(a02);
/* 490 */     double aa12 = ArrayMath.abs(a12);
/*     */ 
/*     */ 
/*     */     
/* 494 */     for (int nrot = 0; aa01 + aa02 + aa12 > 0.0D; nrot++) {
/* 495 */       Check.state((nrot < 100), "number of Jacobi rotations is less than 100");
/*     */ 
/*     */ 
/*     */       
/* 499 */       if (aa01 >= aa02 && aa01 >= aa12) {
/* 500 */         double t, u = a11 - a00;
/* 501 */         if (ArrayMath.abs(a01) < tiny * ArrayMath.abs(u)) {
/* 502 */           t = a01 / u;
/*     */         } else {
/* 504 */           double d1 = 0.5D * u / a01;
/* 505 */           t = (d1 >= 0.0D) ? (1.0D / (d1 + ArrayMath.sqrt(1.0D + d1 * d1))) : (1.0D / (d1 - ArrayMath.sqrt(1.0D + d1 * d1)));
/*     */         } 
/* 507 */         double c = 1.0D / ArrayMath.sqrt(1.0D + t * t);
/* 508 */         double s = t * c;
/* 509 */         u = s / (1.0D + c);
/* 510 */         double r = t * a01;
/* 511 */         a00 -= r;
/* 512 */         a11 += r;
/* 513 */         a01 = 0.0D;
/* 514 */         double apr = a02;
/* 515 */         double aqr = a12;
/* 516 */         a02 = apr - s * (aqr + apr * u);
/* 517 */         a12 = aqr + s * (apr - aqr * u);
/* 518 */         double vpr = v00;
/* 519 */         double vqr = v10;
/* 520 */         v00 = vpr - s * (vqr + vpr * u);
/* 521 */         v10 = vqr + s * (vpr - vqr * u);
/* 522 */         vpr = v01;
/* 523 */         vqr = v11;
/* 524 */         v01 = vpr - s * (vqr + vpr * u);
/* 525 */         v11 = vqr + s * (vpr - vqr * u);
/* 526 */         vpr = v02;
/* 527 */         vqr = v12;
/* 528 */         v02 = vpr - s * (vqr + vpr * u);
/* 529 */         v12 = vqr + s * (vpr - vqr * u);
/*     */ 
/*     */       
/*     */       }
/* 533 */       else if (aa02 >= aa01 && aa02 >= aa12) {
/* 534 */         double t, u = a22 - a00;
/* 535 */         if (ArrayMath.abs(a02) < tiny * ArrayMath.abs(u)) {
/* 536 */           t = a02 / u;
/*     */         } else {
/* 538 */           double d1 = 0.5D * u / a02;
/* 539 */           t = (d1 >= 0.0D) ? (1.0D / (d1 + ArrayMath.sqrt(1.0D + d1 * d1))) : (1.0D / (d1 - ArrayMath.sqrt(1.0D + d1 * d1)));
/*     */         } 
/* 541 */         double c = 1.0D / ArrayMath.sqrt(1.0D + t * t);
/* 542 */         double s = t * c;
/* 543 */         u = s / (1.0D + c);
/* 544 */         double r = t * a02;
/* 545 */         a00 -= r;
/* 546 */         a22 += r;
/* 547 */         a02 = 0.0D;
/* 548 */         double apr = a01;
/* 549 */         double aqr = a12;
/* 550 */         a01 = apr - s * (aqr + apr * u);
/* 551 */         a12 = aqr + s * (apr - aqr * u);
/* 552 */         double vpr = v00;
/* 553 */         double vqr = v20;
/* 554 */         v00 = vpr - s * (vqr + vpr * u);
/* 555 */         v20 = vqr + s * (vpr - vqr * u);
/* 556 */         vpr = v01;
/* 557 */         vqr = v21;
/* 558 */         v01 = vpr - s * (vqr + vpr * u);
/* 559 */         v21 = vqr + s * (vpr - vqr * u);
/* 560 */         vpr = v02;
/* 561 */         vqr = v22;
/* 562 */         v02 = vpr - s * (vqr + vpr * u);
/* 563 */         v22 = vqr + s * (vpr - vqr * u);
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 568 */         double t, u = a22 - a11;
/* 569 */         if (ArrayMath.abs(a12) < tiny * ArrayMath.abs(u)) {
/* 570 */           t = a12 / u;
/*     */         } else {
/* 572 */           double d1 = 0.5D * u / a12;
/* 573 */           t = (d1 >= 0.0D) ? (1.0D / (d1 + ArrayMath.sqrt(1.0D + d1 * d1))) : (1.0D / (d1 - ArrayMath.sqrt(1.0D + d1 * d1)));
/*     */         } 
/* 575 */         double c = 1.0D / ArrayMath.sqrt(1.0D + t * t);
/* 576 */         double s = t * c;
/* 577 */         u = s / (1.0D + c);
/* 578 */         double r = t * a12;
/* 579 */         a11 -= r;
/* 580 */         a22 += r;
/* 581 */         a12 = 0.0D;
/* 582 */         double apr = a01;
/* 583 */         double aqr = a02;
/* 584 */         a01 = apr - s * (aqr + apr * u);
/* 585 */         a02 = aqr + s * (apr - aqr * u);
/* 586 */         double vpr = v10;
/* 587 */         double vqr = v20;
/* 588 */         v10 = vpr - s * (vqr + vpr * u);
/* 589 */         v20 = vqr + s * (vpr - vqr * u);
/* 590 */         vpr = v11;
/* 591 */         vqr = v21;
/* 592 */         v11 = vpr - s * (vqr + vpr * u);
/* 593 */         v21 = vqr + s * (vpr - vqr * u);
/* 594 */         vpr = v12;
/* 595 */         vqr = v22;
/* 596 */         v12 = vpr - s * (vqr + vpr * u);
/* 597 */         v22 = vqr + s * (vpr - vqr * u);
/*     */       } 
/*     */ 
/*     */       
/* 601 */       aa01 = ArrayMath.abs(a01);
/* 602 */       aa02 = ArrayMath.abs(a02);
/* 603 */       aa12 = ArrayMath.abs(a12);
/*     */     } 
/*     */ 
/*     */     
/* 607 */     d[0] = a00;
/* 608 */     d[1] = a11;
/* 609 */     d[2] = a22;
/* 610 */     v[0][0] = v00; v[0][1] = v01; v[0][2] = v02;
/* 611 */     v[1][0] = v10; v[1][1] = v11; v[1][2] = v12;
/* 612 */     v[2][0] = v20; v[2][1] = v21; v[2][2] = v22;
/*     */ 
/*     */     
/* 615 */     sortDescending33(v, d);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/dsp/Eigen.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */