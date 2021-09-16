/*     */ package edu.mines.jtk.la;
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
/*     */ public class DMatrixEvd
/*     */ {
/*     */   private int _n;
/*     */   private double[][] _v;
/*     */   private double[] _d;
/*     */   private double[] _e;
/*     */   private double[][] _h;
/*     */   private double _cdivr;
/*     */   private double _cdivi;
/*     */   
/*     */   public DMatrixEvd(DMatrix a) {
/*  47 */     Check.argument(a.isSquare(), "matrix a is square");
/*  48 */     double[][] aa = a.getArray();
/*  49 */     int n = a.getN();
/*  50 */     this._n = n;
/*  51 */     this._v = new double[n][n];
/*  52 */     this._d = new double[n];
/*  53 */     this._e = new double[n];
/*  54 */     if (a.isSymmetric()) {
/*  55 */       for (int i = 0; i < n; i++) {
/*  56 */         for (int j = 0; j < n; j++) {
/*  57 */           this._v[i][j] = aa[i][j];
/*     */         }
/*     */       } 
/*  60 */       tred2();
/*  61 */       tql2();
/*     */     } else {
/*  63 */       this._h = new double[n][n];
/*  64 */       for (int i = 0; i < n; i++) {
/*  65 */         for (int j = 0; j < n; j++) {
/*  66 */           this._h[i][j] = aa[i][j];
/*     */         }
/*     */       } 
/*  69 */       orthes();
/*  70 */       hqr2();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DMatrix getV() {
/*  79 */     return new DMatrix(this._n, this._n, this._v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DMatrix getD() {
/*  87 */     DMatrix d = new DMatrix(this._n, this._n);
/*  88 */     double[][] da = d.getArray();
/*  89 */     for (int i = 0; i < this._n; i++) {
/*  90 */       for (int j = 0; j < this._n; j++) {
/*  91 */         da[i][j] = 0.0D;
/*     */       }
/*  93 */       da[i][i] = this._d[i];
/*  94 */       if (this._e[i] > 0.0D) {
/*  95 */         da[i][i + 1] = this._e[i];
/*  96 */       } else if (this._e[i] < 0.0D) {
/*  97 */         da[i][i - 1] = this._e[i];
/*     */       } 
/*     */     } 
/* 100 */     return d;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] getRealEigenvalues() {
/* 108 */     return ArrayMath.copy(this._d);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] getImagEigenvalues() {
/* 116 */     return ArrayMath.copy(this._e);
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
/*     */   private void tred2() {
/* 133 */     int n = this._n;
/* 134 */     for (int k = 0; k < n; k++) {
/* 135 */       this._d[k] = this._v[n - 1][k];
/*     */     }
/*     */     int i;
/* 138 */     for (i = n - 1; i > 0; i--) {
/*     */ 
/*     */       
/* 141 */       double scale = 0.0D;
/* 142 */       double h = 0.0D; int m;
/* 143 */       for (m = 0; m < i; m++)
/* 144 */         scale += ArrayMath.abs(this._d[m]); 
/* 145 */       if (scale == 0.0D) {
/* 146 */         this._e[i] = this._d[i - 1];
/* 147 */         for (int i1 = 0; i1 < i; i1++) {
/* 148 */           this._d[i1] = this._v[i - 1][i1];
/* 149 */           this._v[i][i1] = 0.0D;
/* 150 */           this._v[i1][i] = 0.0D;
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 155 */         for (m = 0; m < i; m++) {
/* 156 */           this._d[m] = this._d[m] / scale;
/* 157 */           h += this._d[m] * this._d[m];
/*     */         } 
/* 159 */         double f = this._d[i - 1];
/* 160 */         double g = ArrayMath.sqrt(h);
/* 161 */         if (f > 0.0D)
/* 162 */           g = -g; 
/* 163 */         this._e[i] = scale * g;
/* 164 */         h -= f * g;
/* 165 */         this._d[i - 1] = f - g; int i1;
/* 166 */         for (i1 = 0; i1 < i; i1++) {
/* 167 */           this._e[i1] = 0.0D;
/*     */         }
/*     */         
/* 170 */         for (i1 = 0; i1 < i; i1++) {
/* 171 */           f = this._d[i1];
/* 172 */           this._v[i1][i] = f;
/* 173 */           g = this._e[i1] + this._v[i1][i1] * f;
/* 174 */           for (int i3 = i1 + 1; i3 <= i - 1; i3++) {
/* 175 */             g += this._v[i3][i1] * this._d[i3];
/* 176 */             this._e[i3] = this._e[i3] + this._v[i3][i1] * f;
/*     */           } 
/* 178 */           this._e[i1] = g;
/*     */         } 
/* 180 */         f = 0.0D;
/* 181 */         for (i1 = 0; i1 < i; i1++) {
/* 182 */           this._e[i1] = this._e[i1] / h;
/* 183 */           f += this._e[i1] * this._d[i1];
/*     */         } 
/* 185 */         double hh = f / (h + h); int i2;
/* 186 */         for (i2 = 0; i2 < i; i2++) {
/* 187 */           this._e[i2] = this._e[i2] - hh * this._d[i2];
/*     */         }
/* 189 */         for (i2 = 0; i2 < i; i2++) {
/* 190 */           f = this._d[i2];
/* 191 */           g = this._e[i2];
/* 192 */           for (int i3 = i2; i3 <= i - 1; i3++) {
/* 193 */             this._v[i3][i2] = this._v[i3][i2] - f * this._e[i3] + g * this._d[i3];
/*     */           }
/* 195 */           this._d[i2] = this._v[i - 1][i2];
/* 196 */           this._v[i][i2] = 0.0D;
/*     */         } 
/*     */       } 
/* 199 */       this._d[i] = h;
/*     */     } 
/*     */ 
/*     */     
/* 203 */     for (i = 0; i < n - 1; i++) {
/* 204 */       this._v[n - 1][i] = this._v[i][i];
/* 205 */       this._v[i][i] = 1.0D;
/* 206 */       double h = this._d[i + 1];
/* 207 */       if (h != 0.0D) {
/* 208 */         for (int i2 = 0; i2 <= i; i2++)
/* 209 */           this._d[i2] = this._v[i2][i + 1] / h; 
/* 210 */         for (int i1 = 0; i1 <= i; i1++) {
/* 211 */           double g = 0.0D; int i3;
/* 212 */           for (i3 = 0; i3 <= i; i3++)
/* 213 */             g += this._v[i3][i + 1] * this._v[i3][i1]; 
/* 214 */           for (i3 = 0; i3 <= i; i3++)
/* 215 */             this._v[i3][i1] = this._v[i3][i1] - g * this._d[i3]; 
/*     */         } 
/*     */       } 
/* 218 */       for (int m = 0; m <= i; m++)
/* 219 */         this._v[m][i + 1] = 0.0D; 
/*     */     } 
/* 221 */     for (int j = 0; j < n; j++) {
/* 222 */       this._d[j] = this._v[n - 1][j];
/* 223 */       this._v[n - 1][j] = 0.0D;
/*     */     } 
/* 225 */     this._v[n - 1][n - 1] = 1.0D;
/* 226 */     this._e[0] = 0.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void tql2() {
/* 235 */     int n = this._n;
/* 236 */     for (int i = 1; i < n; i++)
/* 237 */       this._e[i - 1] = this._e[i]; 
/* 238 */     this._e[n - 1] = 0.0D;
/* 239 */     double f = 0.0D;
/* 240 */     double tst1 = 0.0D;
/* 241 */     double eps = ArrayMath.pow(2.0D, -52.0D);
/* 242 */     for (int l = 0; l < n; l++) {
/*     */ 
/*     */       
/* 245 */       tst1 = ArrayMath.max(tst1, ArrayMath.abs(this._d[l]) + ArrayMath.abs(this._e[l]));
/* 246 */       int m = l;
/* 247 */       while (m < n && 
/* 248 */         ArrayMath.abs(this._e[m]) > eps * tst1)
/*     */       {
/* 250 */         m++;
/*     */       }
/*     */ 
/*     */       
/* 254 */       if (m > l) {
/*     */         do
/*     */         {
/*     */ 
/*     */ 
/*     */           
/* 260 */           double g = this._d[l];
/* 261 */           double p = (this._d[l + 1] - g) / 2.0D * this._e[l];
/* 262 */           double r = ArrayMath.hypot(p, 1.0D);
/* 263 */           if (p < 0.0D)
/* 264 */             r = -r; 
/* 265 */           this._d[l] = this._e[l] / (p + r);
/* 266 */           this._d[l + 1] = this._e[l] * (p + r);
/* 267 */           double dl1 = this._d[l + 1];
/* 268 */           double h = g - this._d[l];
/* 269 */           for (int k = l + 2; k < n; k++)
/* 270 */             this._d[k] = this._d[k] - h; 
/* 271 */           f += h;
/*     */ 
/*     */           
/* 274 */           p = this._d[m];
/* 275 */           double c = 1.0D;
/* 276 */           double c2 = c;
/* 277 */           double c3 = c;
/* 278 */           double el1 = this._e[l + 1];
/* 279 */           double s = 0.0D;
/* 280 */           double s2 = 0.0D;
/* 281 */           for (int i1 = m - 1; i1 >= l; i1--) {
/* 282 */             c3 = c2;
/* 283 */             c2 = c;
/* 284 */             s2 = s;
/* 285 */             g = c * this._e[i1];
/* 286 */             h = c * p;
/* 287 */             r = ArrayMath.hypot(p, this._e[i1]);
/* 288 */             this._e[i1 + 1] = s * r;
/* 289 */             s = this._e[i1] / r;
/* 290 */             c = p / r;
/* 291 */             p = c * this._d[i1] - s * g;
/* 292 */             this._d[i1 + 1] = h + s * (c * g + s * this._d[i1]);
/*     */ 
/*     */             
/* 295 */             for (int i2 = 0; i2 < n; i2++) {
/* 296 */               h = this._v[i2][i1 + 1];
/* 297 */               this._v[i2][i1 + 1] = s * this._v[i2][i1] + c * h;
/* 298 */               this._v[i2][i1] = c * this._v[i2][i1] - s * h;
/*     */             } 
/*     */           } 
/* 301 */           p = -s * s2 * c3 * el1 * this._e[l] / dl1;
/* 302 */           this._e[l] = s * p;
/* 303 */           this._d[l] = c * p;
/*     */         
/*     */         }
/* 306 */         while (ArrayMath.abs(this._e[l]) > eps * tst1);
/*     */       }
/* 308 */       this._d[l] = this._d[l] + f;
/* 309 */       this._e[l] = 0.0D;
/*     */     } 
/*     */ 
/*     */     
/* 313 */     for (int j = 0; j < n - 1; j++) {
/* 314 */       int k = j;
/* 315 */       double p = this._d[j]; int m;
/* 316 */       for (m = j + 1; m < n; m++) {
/* 317 */         if (this._d[m] < p) {
/* 318 */           k = m;
/* 319 */           p = this._d[m];
/*     */         } 
/*     */       } 
/* 322 */       if (k != j) {
/* 323 */         this._d[k] = this._d[j];
/* 324 */         this._d[j] = p;
/* 325 */         for (m = 0; m < n; m++) {
/* 326 */           p = this._v[m][j];
/* 327 */           this._v[m][j] = this._v[m][k];
/* 328 */           this._v[m][k] = p;
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
/*     */   private void orthes() {
/* 340 */     int n = this._n;
/* 341 */     int low = 0;
/* 342 */     int high = n - 1;
/* 343 */     double[] ort = new double[n];
/* 344 */     for (int j = low + 1; j <= high - 1; j++) {
/*     */ 
/*     */       
/* 347 */       double scale = 0.0D;
/* 348 */       for (int k = j; k <= high; k++)
/* 349 */         scale += ArrayMath.abs(this._h[k][j - 1]); 
/* 350 */       if (scale != 0.0D) {
/*     */ 
/*     */         
/* 353 */         double h = 0.0D;
/* 354 */         for (int i1 = high; i1 >= j; i1--) {
/* 355 */           ort[i1] = this._h[i1][j - 1] / scale;
/* 356 */           h += ort[i1] * ort[i1];
/*     */         } 
/* 358 */         double g = ArrayMath.sqrt(h);
/* 359 */         if (ort[j] > 0.0D)
/* 360 */           g = -g; 
/* 361 */         h -= ort[j] * g;
/* 362 */         ort[j] = ort[j] - g;
/*     */ 
/*     */         
/* 365 */         for (int i3 = j; i3 < n; i3++) {
/* 366 */           double f = 0.0D; int i4;
/* 367 */           for (i4 = high; i4 >= j; i4--)
/* 368 */             f += ort[i4] * this._h[i4][i3]; 
/* 369 */           f /= h;
/* 370 */           for (i4 = j; i4 <= high; i4++)
/* 371 */             this._h[i4][i3] = this._h[i4][i3] - f * ort[i4]; 
/*     */         } 
/* 373 */         for (int i2 = 0; i2 <= high; i2++) {
/* 374 */           double f = 0.0D; int i4;
/* 375 */           for (i4 = high; i4 >= j; i4--)
/* 376 */             f += ort[i4] * this._h[i2][i4]; 
/* 377 */           f /= h;
/* 378 */           for (i4 = j; i4 <= high; i4++)
/* 379 */             this._h[i2][i4] = this._h[i2][i4] - f * ort[i4]; 
/*     */         } 
/* 381 */         ort[j] = scale * ort[j];
/* 382 */         this._h[j][j - 1] = scale * g;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 387 */     for (int i = 0; i < n; i++) {
/* 388 */       for (int k = 0; k < n; k++) {
/* 389 */         this._v[i][k] = (i == k) ? 1.0D : 0.0D;
/*     */       }
/*     */     } 
/* 392 */     for (int m = high - 1; m >= low + 1; m--) {
/* 393 */       if (this._h[m][m - 1] != 0.0D) {
/* 394 */         for (int i1 = m + 1; i1 <= high; i1++)
/* 395 */           ort[i1] = this._h[i1][m - 1]; 
/* 396 */         for (int k = m; k <= high; k++) {
/* 397 */           double g = 0.0D; int i2;
/* 398 */           for (i2 = m; i2 <= high; i2++)
/* 399 */             g += ort[i2] * this._v[i2][k]; 
/* 400 */           g = g / ort[m] / this._h[m][m - 1];
/* 401 */           for (i2 = m; i2 <= high; i2++) {
/* 402 */             this._v[i2][k] = this._v[i2][k] + g * ort[i2];
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void cdiv(double xr, double xi, double yr, double yi) {
/* 413 */     if (ArrayMath.abs(yr) > ArrayMath.abs(yi)) {
/* 414 */       double r = yi / yr;
/* 415 */       double d = yr + r * yi;
/* 416 */       this._cdivr = (xr + r * xi) / d;
/* 417 */       this._cdivi = (xi - r * xr) / d;
/*     */     } else {
/* 419 */       double r = yr / yi;
/* 420 */       double d = yi + r * yr;
/* 421 */       this._cdivr = (r * xr + xi) / d;
/* 422 */       this._cdivi = (r * xi - xr) / d;
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
/*     */   private void hqr2() {
/* 435 */     int nn = this._n;
/* 436 */     int n = nn - 1;
/* 437 */     int low = 0;
/* 438 */     int high = nn - 1;
/* 439 */     double eps = ArrayMath.pow(2.0D, -52.0D);
/* 440 */     double exshift = 0.0D;
/* 441 */     double p = 0.0D, q = 0.0D, r = 0.0D, s = 0.0D, z = 0.0D;
/*     */ 
/*     */     
/* 444 */     double norm = 0.0D;
/* 445 */     for (int i = 0; i < nn; i++) {
/* 446 */       if (i < low || i > high) {
/* 447 */         this._d[i] = this._h[i][i];
/* 448 */         this._e[i] = 0.0D;
/*     */       } 
/* 450 */       for (int m = ArrayMath.max(i - 1, 0); m < nn; m++) {
/* 451 */         norm += ArrayMath.abs(this._h[i][m]);
/*     */       }
/*     */     } 
/*     */     
/* 455 */     int iter = 0;
/* 456 */     while (n >= low) {
/*     */ 
/*     */       
/* 459 */       int l = n;
/* 460 */       while (l > low) {
/* 461 */         s = ArrayMath.abs(this._h[l - 1][l - 1]) + ArrayMath.abs(this._h[l][l]);
/* 462 */         if (s == 0.0D)
/* 463 */           s = norm; 
/* 464 */         if (ArrayMath.abs(this._h[l][l - 1]) < eps * s)
/*     */           break; 
/* 466 */         l--;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 472 */       if (l == n) {
/* 473 */         this._h[n][n] = this._h[n][n] + exshift;
/* 474 */         this._d[n] = this._h[n][n];
/* 475 */         this._e[n] = 0.0D;
/* 476 */         n--;
/* 477 */         iter = 0;
/*     */         
/*     */         continue;
/*     */       } 
/* 481 */       if (l == n - 1) {
/* 482 */         double d1 = this._h[n][n - 1] * this._h[n - 1][n];
/* 483 */         p = (this._h[n - 1][n - 1] - this._h[n][n]) / 2.0D;
/* 484 */         q = p * p + d1;
/* 485 */         z = ArrayMath.sqrt(ArrayMath.abs(q));
/* 486 */         this._h[n][n] = this._h[n][n] + exshift;
/* 487 */         this._h[n - 1][n - 1] = this._h[n - 1][n - 1] + exshift;
/* 488 */         double d2 = this._h[n][n];
/*     */ 
/*     */         
/* 491 */         if (q >= 0.0D) {
/* 492 */           if (p >= 0.0D) {
/* 493 */             z = p + z;
/*     */           } else {
/* 495 */             z = p - z;
/*     */           } 
/* 497 */           this._d[n - 1] = d2 + z;
/* 498 */           this._d[n] = this._d[n - 1];
/* 499 */           if (z != 0.0D)
/* 500 */             this._d[n] = d2 - d1 / z; 
/* 501 */           this._e[n - 1] = 0.0D;
/* 502 */           this._e[n] = 0.0D;
/* 503 */           d2 = this._h[n][n - 1];
/* 504 */           s = ArrayMath.abs(d2) + ArrayMath.abs(z);
/* 505 */           p = d2 / s;
/* 506 */           q = z / s;
/* 507 */           r = ArrayMath.sqrt(p * p + q * q);
/* 508 */           p /= r;
/* 509 */           q /= r;
/*     */ 
/*     */           
/* 512 */           for (int i4 = n - 1; i4 < nn; i4++) {
/* 513 */             z = this._h[n - 1][i4];
/* 514 */             this._h[n - 1][i4] = q * z + p * this._h[n][i4];
/* 515 */             this._h[n][i4] = q * this._h[n][i4] - p * z;
/*     */           } 
/*     */           
/*     */           int i3;
/* 519 */           for (i3 = 0; i3 <= n; i3++) {
/* 520 */             z = this._h[i3][n - 1];
/* 521 */             this._h[i3][n - 1] = q * z + p * this._h[i3][n];
/* 522 */             this._h[i3][n] = q * this._h[i3][n] - p * z;
/*     */           } 
/*     */ 
/*     */           
/* 526 */           for (i3 = low; i3 <= high; i3++) {
/* 527 */             z = this._v[i3][n - 1];
/* 528 */             this._v[i3][n - 1] = q * z + p * this._v[i3][n];
/* 529 */             this._v[i3][n] = q * this._v[i3][n] - p * z;
/*     */           }
/*     */         
/*     */         }
/*     */         else {
/*     */           
/* 535 */           this._d[n - 1] = d2 + p;
/* 536 */           this._d[n] = d2 + p;
/* 537 */           this._e[n - 1] = z;
/* 538 */           this._e[n] = -z;
/*     */         } 
/* 540 */         n -= 2;
/* 541 */         iter = 0;
/*     */ 
/*     */         
/*     */         continue;
/*     */       } 
/*     */ 
/*     */       
/* 548 */       double x = this._h[n][n];
/* 549 */       double y = 0.0D;
/* 550 */       double w = 0.0D;
/* 551 */       if (l < n) {
/* 552 */         y = this._h[n - 1][n - 1];
/* 553 */         w = this._h[n][n - 1] * this._h[n - 1][n];
/*     */       } 
/*     */ 
/*     */       
/* 557 */       if (iter == 10) {
/* 558 */         exshift += x;
/* 559 */         for (int i3 = low; i3 <= n; i3++)
/* 560 */           this._h[i3][i3] = this._h[i3][i3] - x; 
/* 561 */         s = ArrayMath.abs(this._h[n][n - 1]) + ArrayMath.abs(this._h[n - 1][n - 2]);
/* 562 */         x = y = 0.75D * s;
/* 563 */         w = -0.4375D * s * s;
/*     */       } 
/*     */ 
/*     */       
/* 567 */       if (iter == 30) {
/* 568 */         s = (y - x) / 2.0D;
/* 569 */         s = s * s + w;
/* 570 */         if (s > 0.0D) {
/* 571 */           s = ArrayMath.sqrt(s);
/* 572 */           if (y < x)
/* 573 */             s = -s; 
/* 574 */           s = x - w / ((y - x) / 2.0D + s);
/* 575 */           for (int i3 = low; i3 <= n; i3++)
/* 576 */             this._h[i3][i3] = this._h[i3][i3] - s; 
/* 577 */           exshift += s;
/* 578 */           x = y = w = 0.964D;
/*     */         } 
/*     */       } 
/*     */       
/* 582 */       iter++;
/*     */ 
/*     */       
/* 585 */       int m = n - 2;
/* 586 */       while (m >= l) {
/* 587 */         z = this._h[m][m];
/* 588 */         r = x - z;
/* 589 */         s = y - z;
/* 590 */         p = (r * s - w) / this._h[m + 1][m] + this._h[m][m + 1];
/* 591 */         q = this._h[m + 1][m + 1] - z - r - s;
/* 592 */         r = this._h[m + 2][m + 1];
/* 593 */         s = ArrayMath.abs(p) + ArrayMath.abs(q) + ArrayMath.abs(r);
/* 594 */         p /= s;
/* 595 */         q /= s;
/* 596 */         r /= s;
/* 597 */         if (m == l)
/*     */           break; 
/* 599 */         if (ArrayMath.abs(this._h[m][m - 1]) * (ArrayMath.abs(q) + ArrayMath.abs(r)) < eps * 
/* 600 */           ArrayMath.abs(p) * (ArrayMath.abs(this._h[m - 1][m - 1]) + ArrayMath.abs(z) + ArrayMath.abs(this._h[m + 1][m + 1]))) {
/*     */           break;
/*     */         }
/* 603 */         m--;
/*     */       } 
/* 605 */       for (int i2 = m + 2; i2 <= n; i2++) {
/* 606 */         this._h[i2][i2 - 2] = 0.0D;
/* 607 */         if (i2 > m + 2) {
/* 608 */           this._h[i2][i2 - 3] = 0.0D;
/*     */         }
/*     */       } 
/*     */       
/* 612 */       for (int i1 = m; i1 <= n - 1; i1++) {
/* 613 */         boolean notlast = (i1 != n - 1);
/* 614 */         if (i1 != m) {
/* 615 */           p = this._h[i1][i1 - 1];
/* 616 */           q = this._h[i1 + 1][i1 - 1];
/* 617 */           r = notlast ? this._h[i1 + 2][i1 - 1] : 0.0D;
/* 618 */           x = ArrayMath.abs(p) + ArrayMath.abs(q) + ArrayMath.abs(r);
/* 619 */           if (x != 0.0D) {
/* 620 */             p /= x;
/* 621 */             q /= x;
/* 622 */             r /= x;
/*     */           } 
/*     */         } 
/* 625 */         if (x == 0.0D)
/*     */           break; 
/* 627 */         s = ArrayMath.sqrt(p * p + q * q + r * r);
/* 628 */         if (p < 0.0D)
/* 629 */           s = -s; 
/* 630 */         if (s != 0.0D) {
/* 631 */           if (i1 != m) {
/* 632 */             this._h[i1][i1 - 1] = -s * x;
/* 633 */           } else if (l != m) {
/* 634 */             this._h[i1][i1 - 1] = -this._h[i1][i1 - 1];
/*     */           } 
/* 636 */           p += s;
/* 637 */           x = p / s;
/* 638 */           y = q / s;
/* 639 */           z = r / s;
/* 640 */           q /= p;
/* 641 */           r /= p;
/*     */ 
/*     */           
/* 644 */           for (int i4 = i1; i4 < nn; i4++) {
/* 645 */             p = this._h[i1][i4] + q * this._h[i1 + 1][i4];
/* 646 */             if (notlast) {
/* 647 */               p += r * this._h[i1 + 2][i4];
/* 648 */               this._h[i1 + 2][i4] = this._h[i1 + 2][i4] - p * z;
/*     */             } 
/* 650 */             this._h[i1][i4] = this._h[i1][i4] - p * x;
/* 651 */             this._h[i1 + 1][i4] = this._h[i1 + 1][i4] - p * y;
/*     */           } 
/*     */           
/*     */           int i3;
/* 655 */           for (i3 = 0; i3 <= ArrayMath.min(n, i1 + 3); i3++) {
/* 656 */             p = x * this._h[i3][i1] + y * this._h[i3][i1 + 1];
/* 657 */             if (notlast) {
/* 658 */               p += z * this._h[i3][i1 + 2];
/* 659 */               this._h[i3][i1 + 2] = this._h[i3][i1 + 2] - p * r;
/*     */             } 
/* 661 */             this._h[i3][i1] = this._h[i3][i1] - p;
/* 662 */             this._h[i3][i1 + 1] = this._h[i3][i1 + 1] - p * q;
/*     */           } 
/*     */ 
/*     */           
/* 666 */           for (i3 = low; i3 <= high; i3++) {
/* 667 */             p = x * this._v[i3][i1] + y * this._v[i3][i1 + 1];
/* 668 */             if (notlast) {
/* 669 */               p += z * this._v[i3][i1 + 2];
/* 670 */               this._v[i3][i1 + 2] = this._v[i3][i1 + 2] - p * r;
/*     */             } 
/* 672 */             this._v[i3][i1] = this._v[i3][i1] - p;
/* 673 */             this._v[i3][i1 + 1] = this._v[i3][i1 + 1] - p * q;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 681 */     if (norm == 0.0D) {
/*     */       return;
/*     */     }
/* 684 */     for (n = nn - 1; n >= 0; n--) {
/* 685 */       p = this._d[n];
/* 686 */       q = this._e[n];
/*     */ 
/*     */       
/* 689 */       if (q == 0.0D) {
/* 690 */         int l = n;
/* 691 */         this._h[n][n] = 1.0D;
/* 692 */         for (int m = n - 1; m >= 0; m--) {
/* 693 */           double w = this._h[m][m] - p;
/* 694 */           r = 0.0D; int i1;
/* 695 */           for (i1 = l; i1 <= n; i1++)
/* 696 */             r += this._h[m][i1] * this._h[i1][n]; 
/* 697 */           if (this._e[m] < 0.0D) {
/* 698 */             z = w;
/* 699 */             s = r;
/*     */           } else {
/* 701 */             l = m;
/* 702 */             if (this._e[m] == 0.0D) {
/* 703 */               if (w != 0.0D) {
/* 704 */                 this._h[m][n] = -r / w;
/*     */               } else {
/* 706 */                 this._h[m][n] = -r / eps * norm;
/*     */               }
/*     */             
/*     */             }
/*     */             else {
/*     */               
/* 712 */               double x = this._h[m][m + 1];
/* 713 */               double y = this._h[m + 1][m];
/* 714 */               q = (this._d[m] - p) * (this._d[m] - p) + this._e[m] * this._e[m];
/* 715 */               double d1 = (x * s - z * r) / q;
/* 716 */               this._h[m][n] = d1;
/* 717 */               if (ArrayMath.abs(x) > ArrayMath.abs(z)) {
/* 718 */                 this._h[m + 1][n] = (-r - w * d1) / x;
/*     */               } else {
/* 720 */                 this._h[m + 1][n] = (-s - y * d1) / z;
/*     */               } 
/*     */             } 
/*     */ 
/*     */             
/* 725 */             double t = ArrayMath.abs(this._h[m][n]);
/* 726 */             if (eps * t * t > 1.0D) {
/* 727 */               for (i1 = m; i1 <= n; i1++) {
/* 728 */                 this._h[i1][n] = this._h[i1][n] / t;
/*     */               }
/*     */             }
/*     */           }
/*     */         
/*     */         }
/*     */       
/* 735 */       } else if (q < 0.0D) {
/* 736 */         int l = n - 1;
/*     */ 
/*     */         
/* 739 */         if (ArrayMath.abs(this._h[n][n - 1]) > ArrayMath.abs(this._h[n - 1][n])) {
/* 740 */           this._h[n - 1][n - 1] = q / this._h[n][n - 1];
/* 741 */           this._h[n - 1][n] = -(this._h[n][n] - p) / this._h[n][n - 1];
/*     */         } else {
/* 743 */           cdiv(0.0D, -this._h[n - 1][n], this._h[n - 1][n - 1] - p, q);
/* 744 */           this._h[n - 1][n - 1] = this._cdivr;
/* 745 */           this._h[n - 1][n] = this._cdivi;
/*     */         } 
/* 747 */         this._h[n][n - 1] = 0.0D;
/* 748 */         this._h[n][n] = 1.0D;
/* 749 */         for (int m = n - 2; m >= 0; m--) {
/*     */           
/* 751 */           double ra = 0.0D;
/* 752 */           double sa = 0.0D; int i1;
/* 753 */           for (i1 = l; i1 <= n; i1++) {
/* 754 */             ra += this._h[m][i1] * this._h[i1][n - 1];
/* 755 */             sa += this._h[m][i1] * this._h[i1][n];
/*     */           } 
/* 757 */           double w = this._h[m][m] - p;
/* 758 */           if (this._e[m] < 0.0D) {
/* 759 */             z = w;
/* 760 */             r = ra;
/* 761 */             s = sa;
/*     */           } else {
/* 763 */             l = m;
/* 764 */             if (this._e[m] == 0.0D) {
/* 765 */               cdiv(-ra, -sa, w, q);
/* 766 */               this._h[m][n - 1] = this._cdivr;
/* 767 */               this._h[m][n] = this._cdivi;
/*     */             }
/*     */             else {
/*     */               
/* 771 */               double x = this._h[m][m + 1];
/* 772 */               double y = this._h[m + 1][m];
/* 773 */               double vr = (this._d[m] - p) * (this._d[m] - p) + this._e[m] * this._e[m] - q * q;
/* 774 */               double vi = (this._d[m] - p) * 2.0D * q;
/* 775 */               if (vr == 0.0D && vi == 0.0D)
/* 776 */                 vr = eps * norm * (ArrayMath.abs(w) + ArrayMath.abs(q) + ArrayMath.abs(x) + ArrayMath.abs(y) + ArrayMath.abs(z)); 
/* 777 */               cdiv(x * r - z * ra + q * sa, x * s - z * sa - q * ra, vr, vi);
/* 778 */               this._h[m][n - 1] = this._cdivr;
/* 779 */               this._h[m][n] = this._cdivi;
/* 780 */               if (ArrayMath.abs(x) > ArrayMath.abs(z) + ArrayMath.abs(q)) {
/* 781 */                 this._h[m + 1][n - 1] = (-ra - w * this._h[m][n - 1] + q * this._h[m][n]) / x;
/* 782 */                 this._h[m + 1][n] = (-sa - w * this._h[m][n] - q * this._h[m][n - 1]) / x;
/*     */               } else {
/* 784 */                 cdiv(-r - y * this._h[m][n - 1], -s - y * this._h[m][n], z, q);
/* 785 */                 this._h[m + 1][n - 1] = this._cdivr;
/* 786 */                 this._h[m + 1][n] = this._cdivi;
/*     */               } 
/*     */             } 
/*     */ 
/*     */             
/* 791 */             double t = ArrayMath.max(ArrayMath.abs(this._h[m][n - 1]), ArrayMath.abs(this._h[m][n]));
/* 792 */             if (eps * t * t > 1.0D) {
/* 793 */               for (i1 = m; i1 < n; i1++) {
/* 794 */                 this._h[i1][n - 1] = this._h[i1][n - 1] / t;
/* 795 */                 this._h[i1][n] = this._h[i1][n] / t;
/*     */               } 
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 804 */     for (int k = 0; k < nn; k++) {
/* 805 */       if (k < low || k > high) {
/* 806 */         for (int m = k; m < nn; m++) {
/* 807 */           this._v[k][m] = this._h[k][m];
/*     */         }
/*     */       }
/*     */     } 
/*     */     
/* 812 */     for (int j = nn - 1; j >= low; j--) {
/* 813 */       for (int m = low; m <= high; m++) {
/* 814 */         z = 0.0D;
/* 815 */         for (int i1 = low; i1 <= ArrayMath.min(j, high); i1++)
/* 816 */           z += this._v[m][i1] * this._h[i1][j]; 
/* 817 */         this._v[m][j] = z;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/la/DMatrixEvd.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */