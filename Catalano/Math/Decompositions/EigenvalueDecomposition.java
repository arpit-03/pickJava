/*     */ package Catalano.Math.Decompositions;
/*     */ 
/*     */ import Catalano.Math.Matrix;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EigenvalueDecomposition
/*     */   implements Serializable
/*     */ {
/*     */   private int n;
/*     */   private boolean issymmetric;
/*     */   private double[] d;
/*     */   private double[] e;
/*     */   private double[][] V;
/*     */   private double[][] H;
/*     */   private double[] ort;
/*     */   private transient double cdivr;
/*     */   private transient double cdivi;
/*     */   
/*     */   public EigenvalueDecomposition(double[][] matrix) {
/*  68 */     double[][] A = matrix;
/*  69 */     this.n = (matrix[0]).length;
/*  70 */     this.V = new double[this.n][this.n];
/*  71 */     this.d = new double[this.n];
/*  72 */     this.e = new double[this.n];
/*     */     
/*  74 */     this.issymmetric = true; int j;
/*  75 */     for (j = 0; (((j < this.n) ? 1 : 0) & this.issymmetric) != 0; ) {
/*  76 */       int i = 0; while (true) { if ((((i < this.n) ? 1 : 0) & this.issymmetric) == 0) { j++; continue; }
/*  77 */          this.issymmetric = (A[i][j] == A[j][i]);
/*     */         i++; }
/*     */     
/*     */     } 
/*  81 */     if (this.issymmetric) {
/*  82 */       for (int i = 0; i < this.n; i++) {
/*  83 */         for (int k = 0; k < this.n; k++) {
/*  84 */           this.V[i][k] = A[i][k];
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/*  89 */       tred2();
/*     */ 
/*     */       
/*  92 */       tql2();
/*     */     } else {
/*     */       
/*  95 */       this.H = new double[this.n][this.n];
/*  96 */       this.ort = new double[this.n];
/*     */       
/*  98 */       for (j = 0; j < this.n; j++) {
/*  99 */         for (int i = 0; i < this.n; i++) {
/* 100 */           this.H[i][j] = A[i][j];
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 105 */       orthes();
/*     */ 
/*     */       
/* 108 */       hqr2();
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
/*     */   private void tred2() {
/* 122 */     for (int k = 0; k < this.n; k++) {
/* 123 */       this.d[k] = this.V[this.n - 1][k];
/*     */     }
/*     */     
/*     */     int i;
/*     */     
/* 128 */     for (i = this.n - 1; i > 0; i--) {
/*     */ 
/*     */ 
/*     */       
/* 132 */       double scale = 0.0D;
/* 133 */       double h = 0.0D; int m;
/* 134 */       for (m = 0; m < i; m++) {
/* 135 */         scale += Math.abs(this.d[m]);
/*     */       }
/* 137 */       if (scale == 0.0D) {
/* 138 */         this.e[i] = this.d[i - 1];
/* 139 */         for (int n = 0; n < i; n++) {
/* 140 */           this.d[n] = this.V[i - 1][n];
/* 141 */           this.V[i][n] = 0.0D;
/* 142 */           this.V[n][i] = 0.0D;
/*     */         }
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 148 */         for (m = 0; m < i; m++) {
/* 149 */           this.d[m] = this.d[m] / scale;
/* 150 */           h += this.d[m] * this.d[m];
/*     */         } 
/* 152 */         double f = this.d[i - 1];
/* 153 */         double g = Math.sqrt(h);
/* 154 */         if (f > 0.0D) {
/* 155 */           g = -g;
/*     */         }
/* 157 */         this.e[i] = scale * g;
/* 158 */         h -= f * g;
/* 159 */         this.d[i - 1] = f - g; int n;
/* 160 */         for (n = 0; n < i; n++) {
/* 161 */           this.e[n] = 0.0D;
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 166 */         for (n = 0; n < i; n++) {
/* 167 */           f = this.d[n];
/* 168 */           this.V[n][i] = f;
/* 169 */           g = this.e[n] + this.V[n][n] * f;
/* 170 */           for (int i2 = n + 1; i2 <= i - 1; i2++) {
/* 171 */             g += this.V[i2][n] * this.d[i2];
/* 172 */             this.e[i2] = this.e[i2] + this.V[i2][n] * f;
/*     */           } 
/* 174 */           this.e[n] = g;
/*     */         } 
/* 176 */         f = 0.0D;
/* 177 */         for (n = 0; n < i; n++) {
/* 178 */           this.e[n] = this.e[n] / h;
/* 179 */           f += this.e[n] * this.d[n];
/*     */         } 
/* 181 */         double hh = f / (h + h); int i1;
/* 182 */         for (i1 = 0; i1 < i; i1++) {
/* 183 */           this.e[i1] = this.e[i1] - hh * this.d[i1];
/*     */         }
/* 185 */         for (i1 = 0; i1 < i; i1++) {
/* 186 */           f = this.d[i1];
/* 187 */           g = this.e[i1];
/* 188 */           for (int i2 = i1; i2 <= i - 1; i2++) {
/* 189 */             this.V[i2][i1] = this.V[i2][i1] - f * this.e[i2] + g * this.d[i2];
/*     */           }
/* 191 */           this.d[i1] = this.V[i - 1][i1];
/* 192 */           this.V[i][i1] = 0.0D;
/*     */         } 
/*     */       } 
/* 195 */       this.d[i] = h;
/*     */     } 
/*     */ 
/*     */     
/* 199 */     for (i = 0; i < this.n - 1; i++) {
/* 200 */       this.V[this.n - 1][i] = this.V[i][i];
/* 201 */       this.V[i][i] = 1.0D;
/* 202 */       double h = this.d[i + 1];
/* 203 */       if (h != 0.0D) {
/* 204 */         for (int i1 = 0; i1 <= i; i1++) {
/* 205 */           this.d[i1] = this.V[i1][i + 1] / h;
/*     */         }
/* 207 */         for (int n = 0; n <= i; n++) {
/* 208 */           double g = 0.0D; int i2;
/* 209 */           for (i2 = 0; i2 <= i; i2++) {
/* 210 */             g += this.V[i2][i + 1] * this.V[i2][n];
/*     */           }
/* 212 */           for (i2 = 0; i2 <= i; i2++) {
/* 213 */             this.V[i2][n] = this.V[i2][n] - g * this.d[i2];
/*     */           }
/*     */         } 
/*     */       } 
/* 217 */       for (int m = 0; m <= i; m++) {
/* 218 */         this.V[m][i + 1] = 0.0D;
/*     */       }
/*     */     } 
/* 221 */     for (int j = 0; j < this.n; j++) {
/* 222 */       this.d[j] = this.V[this.n - 1][j];
/* 223 */       this.V[this.n - 1][j] = 0.0D;
/*     */     } 
/* 225 */     this.V[this.n - 1][this.n - 1] = 1.0D;
/* 226 */     this.e[0] = 0.0D;
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
/*     */   private void tql2() {
/* 239 */     for (int i = 1; i < this.n; i++) {
/* 240 */       this.e[i - 1] = this.e[i];
/*     */     }
/* 242 */     this.e[this.n - 1] = 0.0D;
/*     */     
/* 244 */     double f = 0.0D;
/* 245 */     double tst1 = 0.0D;
/* 246 */     double eps = Math.pow(2.0D, -52.0D);
/* 247 */     for (int l = 0; l < this.n; l++) {
/*     */ 
/*     */ 
/*     */       
/* 251 */       tst1 = Math.max(tst1, Math.abs(this.d[l]) + Math.abs(this.e[l]));
/* 252 */       int m = l;
/* 253 */       while (m < this.n && 
/* 254 */         Math.abs(this.e[m]) > eps * tst1)
/*     */       {
/*     */         
/* 257 */         m++;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 263 */       if (m > l) {
/* 264 */         int iter = 0;
/*     */         do {
/* 266 */           iter++;
/*     */ 
/*     */ 
/*     */           
/* 270 */           double g = this.d[l];
/* 271 */           double p = (this.d[l + 1] - g) / 2.0D * this.e[l];
/* 272 */           double r = Math.hypot(p, 1.0D);
/* 273 */           if (p < 0.0D) {
/* 274 */             r = -r;
/*     */           }
/* 276 */           this.d[l] = this.e[l] / (p + r);
/* 277 */           this.d[l + 1] = this.e[l] * (p + r);
/* 278 */           double dl1 = this.d[l + 1];
/* 279 */           double h = g - this.d[l];
/* 280 */           for (int k = l + 2; k < this.n; k++) {
/* 281 */             this.d[k] = this.d[k] - h;
/*     */           }
/* 283 */           f += h;
/*     */ 
/*     */ 
/*     */           
/* 287 */           p = this.d[m];
/* 288 */           double c = 1.0D;
/* 289 */           double c2 = c;
/* 290 */           double c3 = c;
/* 291 */           double el1 = this.e[l + 1];
/* 292 */           double s = 0.0D;
/* 293 */           double s2 = 0.0D;
/* 294 */           for (int n = m - 1; n >= l; n--) {
/* 295 */             c3 = c2;
/* 296 */             c2 = c;
/* 297 */             s2 = s;
/* 298 */             g = c * this.e[n];
/* 299 */             h = c * p;
/* 300 */             r = Math.hypot(p, this.e[n]);
/* 301 */             this.e[n + 1] = s * r;
/* 302 */             s = this.e[n] / r;
/* 303 */             c = p / r;
/* 304 */             p = c * this.d[n] - s * g;
/* 305 */             this.d[n + 1] = h + s * (c * g + s * this.d[n]);
/*     */ 
/*     */ 
/*     */             
/* 309 */             for (int i1 = 0; i1 < this.n; i1++) {
/* 310 */               h = this.V[i1][n + 1];
/* 311 */               this.V[i1][n + 1] = s * this.V[i1][n] + c * h;
/* 312 */               this.V[i1][n] = c * this.V[i1][n] - s * h;
/*     */             } 
/*     */           } 
/* 315 */           p = -s * s2 * c3 * el1 * this.e[l] / dl1;
/* 316 */           this.e[l] = s * p;
/* 317 */           this.d[l] = c * p;
/*     */ 
/*     */         
/*     */         }
/* 321 */         while (Math.abs(this.e[l]) > eps * tst1);
/*     */       } 
/* 323 */       this.d[l] = this.d[l] + f;
/* 324 */       this.e[l] = 0.0D;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 329 */     for (int j = 0; j < this.n - 1; j++) {
/* 330 */       int k = j;
/* 331 */       double p = this.d[j]; int m;
/* 332 */       for (m = j + 1; m < this.n; m++) {
/* 333 */         if (this.d[m] < p) {
/* 334 */           k = m;
/* 335 */           p = this.d[m];
/*     */         } 
/*     */       } 
/* 338 */       if (k != j) {
/* 339 */         this.d[k] = this.d[j];
/* 340 */         this.d[j] = p;
/* 341 */         for (m = 0; m < this.n; m++) {
/* 342 */           p = this.V[m][j];
/* 343 */           this.V[m][j] = this.V[m][k];
/* 344 */           this.V[m][k] = p;
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
/*     */   private void orthes() {
/* 359 */     int low = 0;
/* 360 */     int high = this.n - 1;
/*     */     
/* 362 */     for (int j = low + 1; j <= high - 1; j++) {
/*     */ 
/*     */ 
/*     */       
/* 366 */       double scale = 0.0D;
/* 367 */       for (int k = j; k <= high; k++) {
/* 368 */         scale += Math.abs(this.H[k][j - 1]);
/*     */       }
/* 370 */       if (scale != 0.0D) {
/*     */ 
/*     */ 
/*     */         
/* 374 */         double h = 0.0D;
/* 375 */         for (int n = high; n >= j; n--) {
/* 376 */           this.ort[n] = this.H[n][j - 1] / scale;
/* 377 */           h += this.ort[n] * this.ort[n];
/*     */         } 
/* 379 */         double g = Math.sqrt(h);
/* 380 */         if (this.ort[j] > 0.0D) {
/* 381 */           g = -g;
/*     */         }
/* 383 */         h -= this.ort[j] * g;
/* 384 */         this.ort[j] = this.ort[j] - g;
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 389 */         for (int i2 = j; i2 < this.n; i2++) {
/* 390 */           double f = 0.0D; int i3;
/* 391 */           for (i3 = high; i3 >= j; i3--) {
/* 392 */             f += this.ort[i3] * this.H[i3][i2];
/*     */           }
/* 394 */           f /= h;
/* 395 */           for (i3 = j; i3 <= high; i3++) {
/* 396 */             this.H[i3][i2] = this.H[i3][i2] - f * this.ort[i3];
/*     */           }
/*     */         } 
/*     */         
/* 400 */         for (int i1 = 0; i1 <= high; i1++) {
/* 401 */           double f = 0.0D; int i3;
/* 402 */           for (i3 = high; i3 >= j; i3--) {
/* 403 */             f += this.ort[i3] * this.H[i1][i3];
/*     */           }
/* 405 */           f /= h;
/* 406 */           for (i3 = j; i3 <= high; i3++) {
/* 407 */             this.H[i1][i3] = this.H[i1][i3] - f * this.ort[i3];
/*     */           }
/*     */         } 
/* 410 */         this.ort[j] = scale * this.ort[j];
/* 411 */         this.H[j][j - 1] = scale * g;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 417 */     for (int i = 0; i < this.n; i++) {
/* 418 */       for (int k = 0; k < this.n; k++) {
/* 419 */         this.V[i][k] = (i == k) ? 1.0D : 0.0D;
/*     */       }
/*     */     } 
/*     */     
/* 423 */     for (int m = high - 1; m >= low + 1; m--) {
/* 424 */       if (this.H[m][m - 1] != 0.0D) {
/* 425 */         for (int n = m + 1; n <= high; n++) {
/* 426 */           this.ort[n] = this.H[n][m - 1];
/*     */         }
/* 428 */         for (int k = m; k <= high; k++) {
/* 429 */           double g = 0.0D; int i1;
/* 430 */           for (i1 = m; i1 <= high; i1++) {
/* 431 */             g += this.ort[i1] * this.V[i1][k];
/*     */           }
/*     */           
/* 434 */           g = g / this.ort[m] / this.H[m][m - 1];
/* 435 */           for (i1 = m; i1 <= high; i1++) {
/* 436 */             this.V[i1][k] = this.V[i1][k] + g * this.ort[i1];
/*     */           }
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
/*     */   private void cdiv(double xr, double xi, double yr, double yi) {
/* 449 */     if (Math.abs(yr) > Math.abs(yi)) {
/* 450 */       double r = yi / yr;
/* 451 */       double d = yr + r * yi;
/* 452 */       this.cdivr = (xr + r * xi) / d;
/* 453 */       this.cdivi = (xi - r * xr) / d;
/*     */     } else {
/* 455 */       double r = yr / yi;
/* 456 */       double d = yi + r * yr;
/* 457 */       this.cdivr = (r * xr + xi) / d;
/* 458 */       this.cdivi = (r * xi - xr) / d;
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
/*     */   private void hqr2() {
/* 474 */     int nn = this.n;
/* 475 */     int n = nn - 1;
/* 476 */     int low = 0;
/* 477 */     int high = nn - 1;
/* 478 */     double eps = Math.pow(2.0D, -52.0D);
/* 479 */     double exshift = 0.0D;
/* 480 */     double p = 0.0D, q = 0.0D, r = 0.0D, s = 0.0D, z = 0.0D;
/*     */ 
/*     */ 
/*     */     
/* 484 */     double norm = 0.0D;
/* 485 */     for (int i = 0; i < nn; i++) {
/* 486 */       if ((((i < low) ? 1 : 0) | ((i > high) ? 1 : 0)) != 0) {
/* 487 */         this.d[i] = this.H[i][i];
/* 488 */         this.e[i] = 0.0D;
/*     */       } 
/* 490 */       for (int m = Math.max(i - 1, 0); m < nn; m++) {
/* 491 */         norm += Math.abs(this.H[i][m]);
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 497 */     int iter = 0;
/* 498 */     while (n >= low) {
/*     */ 
/*     */ 
/*     */       
/* 502 */       int l = n;
/* 503 */       while (l > low) {
/* 504 */         s = Math.abs(this.H[l - 1][l - 1]) + Math.abs(this.H[l][l]);
/* 505 */         if (s == 0.0D) {
/* 506 */           s = norm;
/*     */         }
/* 508 */         if (Math.abs(this.H[l][l - 1]) < eps * s) {
/*     */           break;
/*     */         }
/* 511 */         l--;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 517 */       if (l == n) {
/* 518 */         this.H[n][n] = this.H[n][n] + exshift;
/* 519 */         this.d[n] = this.H[n][n];
/* 520 */         this.e[n] = 0.0D;
/* 521 */         n--;
/* 522 */         iter = 0;
/*     */         
/*     */         continue;
/*     */       } 
/* 526 */       if (l == n - 1) {
/* 527 */         double d1 = this.H[n][n - 1] * this.H[n - 1][n];
/* 528 */         p = (this.H[n - 1][n - 1] - this.H[n][n]) / 2.0D;
/* 529 */         q = p * p + d1;
/* 530 */         z = Math.sqrt(Math.abs(q));
/* 531 */         this.H[n][n] = this.H[n][n] + exshift;
/* 532 */         this.H[n - 1][n - 1] = this.H[n - 1][n - 1] + exshift;
/* 533 */         double d2 = this.H[n][n];
/*     */ 
/*     */ 
/*     */         
/* 537 */         if (q >= 0.0D) {
/* 538 */           if (p >= 0.0D) {
/* 539 */             z = p + z;
/*     */           } else {
/* 541 */             z = p - z;
/*     */           } 
/* 543 */           this.d[n - 1] = d2 + z;
/* 544 */           this.d[n] = this.d[n - 1];
/* 545 */           if (z != 0.0D) {
/* 546 */             this.d[n] = d2 - d1 / z;
/*     */           }
/* 548 */           this.e[n - 1] = 0.0D;
/* 549 */           this.e[n] = 0.0D;
/* 550 */           d2 = this.H[n][n - 1];
/* 551 */           s = Math.abs(d2) + Math.abs(z);
/* 552 */           p = d2 / s;
/* 553 */           q = z / s;
/* 554 */           r = Math.sqrt(p * p + q * q);
/* 555 */           p /= r;
/* 556 */           q /= r;
/*     */ 
/*     */ 
/*     */           
/* 560 */           for (int i4 = n - 1; i4 < nn; i4++) {
/* 561 */             z = this.H[n - 1][i4];
/* 562 */             this.H[n - 1][i4] = q * z + p * this.H[n][i4];
/* 563 */             this.H[n][i4] = q * this.H[n][i4] - p * z;
/*     */           } 
/*     */           
/*     */           int i3;
/*     */           
/* 568 */           for (i3 = 0; i3 <= n; i3++) {
/* 569 */             z = this.H[i3][n - 1];
/* 570 */             this.H[i3][n - 1] = q * z + p * this.H[i3][n];
/* 571 */             this.H[i3][n] = q * this.H[i3][n] - p * z;
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 576 */           for (i3 = low; i3 <= high; i3++) {
/* 577 */             z = this.V[i3][n - 1];
/* 578 */             this.V[i3][n - 1] = q * z + p * this.V[i3][n];
/* 579 */             this.V[i3][n] = q * this.V[i3][n] - p * z;
/*     */           }
/*     */         
/*     */         }
/*     */         else {
/*     */           
/* 585 */           this.d[n - 1] = d2 + p;
/* 586 */           this.d[n] = d2 + p;
/* 587 */           this.e[n - 1] = z;
/* 588 */           this.e[n] = -z;
/*     */         } 
/* 590 */         n -= 2;
/* 591 */         iter = 0;
/*     */ 
/*     */ 
/*     */         
/*     */         continue;
/*     */       } 
/*     */ 
/*     */       
/* 599 */       double x = this.H[n][n];
/* 600 */       double y = 0.0D;
/* 601 */       double w = 0.0D;
/* 602 */       if (l < n) {
/* 603 */         y = this.H[n - 1][n - 1];
/* 604 */         w = this.H[n][n - 1] * this.H[n - 1][n];
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 609 */       if (iter == 10) {
/* 610 */         exshift += x;
/* 611 */         for (int i3 = low; i3 <= n; i3++) {
/* 612 */           this.H[i3][i3] = this.H[i3][i3] - x;
/*     */         }
/* 614 */         s = Math.abs(this.H[n][n - 1]) + Math.abs(this.H[n - 1][n - 2]);
/* 615 */         x = y = 0.75D * s;
/* 616 */         w = -0.4375D * s * s;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 621 */       if (iter == 30) {
/* 622 */         s = (y - x) / 2.0D;
/* 623 */         s = s * s + w;
/* 624 */         if (s > 0.0D) {
/* 625 */           s = Math.sqrt(s);
/* 626 */           if (y < x) {
/* 627 */             s = -s;
/*     */           }
/* 629 */           s = x - w / ((y - x) / 2.0D + s);
/* 630 */           for (int i3 = low; i3 <= n; i3++) {
/* 631 */             this.H[i3][i3] = this.H[i3][i3] - s;
/*     */           }
/* 633 */           exshift += s;
/* 634 */           x = y = w = 0.964D;
/*     */         } 
/*     */       } 
/*     */       
/* 638 */       iter++;
/*     */ 
/*     */ 
/*     */       
/* 642 */       int m = n - 2;
/* 643 */       while (m >= l) {
/* 644 */         z = this.H[m][m];
/* 645 */         r = x - z;
/* 646 */         s = y - z;
/* 647 */         p = (r * s - w) / this.H[m + 1][m] + this.H[m][m + 1];
/* 648 */         q = this.H[m + 1][m + 1] - z - r - s;
/* 649 */         r = this.H[m + 2][m + 1];
/* 650 */         s = Math.abs(p) + Math.abs(q) + Math.abs(r);
/* 651 */         p /= s;
/* 652 */         q /= s;
/* 653 */         r /= s;
/* 654 */         if (m == l) {
/*     */           break;
/*     */         }
/* 657 */         if (Math.abs(this.H[m][m - 1]) * (Math.abs(q) + Math.abs(r)) < 
/* 658 */           eps * Math.abs(p) * (Math.abs(this.H[m - 1][m - 1]) + Math.abs(z) + 
/* 659 */           Math.abs(this.H[m + 1][m + 1]))) {
/*     */           break;
/*     */         }
/* 662 */         m--;
/*     */       } 
/*     */       
/* 665 */       for (int i2 = m + 2; i2 <= n; i2++) {
/* 666 */         this.H[i2][i2 - 2] = 0.0D;
/* 667 */         if (i2 > m + 2) {
/* 668 */           this.H[i2][i2 - 3] = 0.0D;
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 675 */       for (int i1 = m; i1 <= n - 1; i1++) {
/* 676 */         boolean notlast = (i1 != n - 1);
/* 677 */         if (i1 != m) {
/* 678 */           p = this.H[i1][i1 - 1];
/* 679 */           q = this.H[i1 + 1][i1 - 1];
/* 680 */           r = notlast ? this.H[i1 + 2][i1 - 1] : 0.0D;
/* 681 */           x = Math.abs(p) + Math.abs(q) + Math.abs(r);
/* 682 */           if (x == 0.0D) {
/*     */             continue;
/*     */           }
/* 685 */           p /= x;
/* 686 */           q /= x;
/* 687 */           r /= x;
/*     */         } 
/*     */         
/* 690 */         s = Math.sqrt(p * p + q * q + r * r);
/* 691 */         if (p < 0.0D) {
/* 692 */           s = -s;
/*     */         }
/* 694 */         if (s != 0.0D) {
/* 695 */           if (i1 != m) {
/* 696 */             this.H[i1][i1 - 1] = -s * x;
/* 697 */           } else if (l != m) {
/* 698 */             this.H[i1][i1 - 1] = -this.H[i1][i1 - 1];
/*     */           } 
/* 700 */           p += s;
/* 701 */           x = p / s;
/* 702 */           y = q / s;
/* 703 */           z = r / s;
/* 704 */           q /= p;
/* 705 */           r /= p;
/*     */ 
/*     */ 
/*     */           
/* 709 */           for (int i4 = i1; i4 < nn; i4++) {
/* 710 */             p = this.H[i1][i4] + q * this.H[i1 + 1][i4];
/* 711 */             if (notlast) {
/* 712 */               p += r * this.H[i1 + 2][i4];
/* 713 */               this.H[i1 + 2][i4] = this.H[i1 + 2][i4] - p * z;
/*     */             } 
/* 715 */             this.H[i1][i4] = this.H[i1][i4] - p * x;
/* 716 */             this.H[i1 + 1][i4] = this.H[i1 + 1][i4] - p * y;
/*     */           } 
/*     */           
/*     */           int i3;
/*     */           
/* 721 */           for (i3 = 0; i3 <= Math.min(n, i1 + 3); i3++) {
/* 722 */             p = x * this.H[i3][i1] + y * this.H[i3][i1 + 1];
/* 723 */             if (notlast) {
/* 724 */               p += z * this.H[i3][i1 + 2];
/* 725 */               this.H[i3][i1 + 2] = this.H[i3][i1 + 2] - p * r;
/*     */             } 
/* 727 */             this.H[i3][i1] = this.H[i3][i1] - p;
/* 728 */             this.H[i3][i1 + 1] = this.H[i3][i1 + 1] - p * q;
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 733 */           for (i3 = low; i3 <= high; i3++) {
/* 734 */             p = x * this.V[i3][i1] + y * this.V[i3][i1 + 1];
/* 735 */             if (notlast) {
/* 736 */               p += z * this.V[i3][i1 + 2];
/* 737 */               this.V[i3][i1 + 2] = this.V[i3][i1 + 2] - p * r;
/*     */             } 
/* 739 */             this.V[i3][i1] = this.V[i3][i1] - p;
/* 740 */             this.V[i3][i1 + 1] = this.V[i3][i1 + 1] - p * q;
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/*     */         continue;
/*     */       } 
/*     */     } 
/*     */     
/* 749 */     if (norm == 0.0D) {
/*     */       return;
/*     */     }
/*     */     
/* 753 */     for (n = nn - 1; n >= 0; n--) {
/* 754 */       p = this.d[n];
/* 755 */       q = this.e[n];
/*     */ 
/*     */ 
/*     */       
/* 759 */       if (q == 0.0D) {
/* 760 */         int l = n;
/* 761 */         this.H[n][n] = 1.0D;
/* 762 */         for (int m = n - 1; m >= 0; m--) {
/* 763 */           double w = this.H[m][m] - p;
/* 764 */           r = 0.0D; int i1;
/* 765 */           for (i1 = l; i1 <= n; i1++) {
/* 766 */             r += this.H[m][i1] * this.H[i1][n];
/*     */           }
/* 768 */           if (this.e[m] < 0.0D) {
/* 769 */             z = w;
/* 770 */             s = r;
/*     */           } else {
/* 772 */             l = m;
/* 773 */             if (this.e[m] == 0.0D) {
/* 774 */               if (w != 0.0D) {
/* 775 */                 this.H[m][n] = -r / w;
/*     */               } else {
/* 777 */                 this.H[m][n] = -r / eps * norm;
/*     */               }
/*     */             
/*     */             }
/*     */             else {
/*     */               
/* 783 */               double x = this.H[m][m + 1];
/* 784 */               double y = this.H[m + 1][m];
/* 785 */               q = (this.d[m] - p) * (this.d[m] - p) + this.e[m] * this.e[m];
/* 786 */               double d1 = (x * s - z * r) / q;
/* 787 */               this.H[m][n] = d1;
/* 788 */               if (Math.abs(x) > Math.abs(z)) {
/* 789 */                 this.H[m + 1][n] = (-r - w * d1) / x;
/*     */               } else {
/* 791 */                 this.H[m + 1][n] = (-s - y * d1) / z;
/*     */               } 
/*     */             } 
/*     */ 
/*     */ 
/*     */             
/* 797 */             double t = Math.abs(this.H[m][n]);
/* 798 */             if (eps * t * t > 1.0D) {
/* 799 */               for (i1 = m; i1 <= n; i1++) {
/* 800 */                 this.H[i1][n] = this.H[i1][n] / t;
/*     */               }
/*     */             }
/*     */           }
/*     */         
/*     */         }
/*     */       
/*     */       }
/* 808 */       else if (q < 0.0D) {
/* 809 */         int l = n - 1;
/*     */ 
/*     */ 
/*     */         
/* 813 */         if (Math.abs(this.H[n][n - 1]) > Math.abs(this.H[n - 1][n])) {
/* 814 */           this.H[n - 1][n - 1] = q / this.H[n][n - 1];
/* 815 */           this.H[n - 1][n] = -(this.H[n][n] - p) / this.H[n][n - 1];
/*     */         } else {
/* 817 */           cdiv(0.0D, -this.H[n - 1][n], this.H[n - 1][n - 1] - p, q);
/* 818 */           this.H[n - 1][n - 1] = this.cdivr;
/* 819 */           this.H[n - 1][n] = this.cdivi;
/*     */         } 
/* 821 */         this.H[n][n - 1] = 0.0D;
/* 822 */         this.H[n][n] = 1.0D;
/* 823 */         for (int m = n - 2; m >= 0; m--) {
/*     */           
/* 825 */           double ra = 0.0D;
/* 826 */           double sa = 0.0D; int i1;
/* 827 */           for (i1 = l; i1 <= n; i1++) {
/* 828 */             ra += this.H[m][i1] * this.H[i1][n - 1];
/* 829 */             sa += this.H[m][i1] * this.H[i1][n];
/*     */           } 
/* 831 */           double w = this.H[m][m] - p;
/*     */           
/* 833 */           if (this.e[m] < 0.0D) {
/* 834 */             z = w;
/* 835 */             r = ra;
/* 836 */             s = sa;
/*     */           } else {
/* 838 */             l = m;
/* 839 */             if (this.e[m] == 0.0D) {
/* 840 */               cdiv(-ra, -sa, w, q);
/* 841 */               this.H[m][n - 1] = this.cdivr;
/* 842 */               this.H[m][n] = this.cdivi;
/*     */             
/*     */             }
/*     */             else {
/*     */               
/* 847 */               double x = this.H[m][m + 1];
/* 848 */               double y = this.H[m + 1][m];
/* 849 */               double vr = (this.d[m] - p) * (this.d[m] - p) + this.e[m] * this.e[m] - q * q;
/* 850 */               double vi = (this.d[m] - p) * 2.0D * q;
/* 851 */               if ((((vr == 0.0D) ? 1 : 0) & ((vi == 0.0D) ? 1 : 0)) != 0) {
/* 852 */                 vr = eps * norm * (Math.abs(w) + Math.abs(q) + 
/* 853 */                   Math.abs(x) + Math.abs(y) + Math.abs(z));
/*     */               }
/* 855 */               cdiv(x * r - z * ra + q * sa, x * s - z * sa - q * ra, vr, vi);
/* 856 */               this.H[m][n - 1] = this.cdivr;
/* 857 */               this.H[m][n] = this.cdivi;
/* 858 */               if (Math.abs(x) > Math.abs(z) + Math.abs(q)) {
/* 859 */                 this.H[m + 1][n - 1] = (-ra - w * this.H[m][n - 1] + q * this.H[m][n]) / x;
/* 860 */                 this.H[m + 1][n] = (-sa - w * this.H[m][n] - q * this.H[m][n - 1]) / x;
/*     */               } else {
/* 862 */                 cdiv(-r - y * this.H[m][n - 1], -s - y * this.H[m][n], z, q);
/* 863 */                 this.H[m + 1][n - 1] = this.cdivr;
/* 864 */                 this.H[m + 1][n] = this.cdivi;
/*     */               } 
/*     */             } 
/*     */ 
/*     */ 
/*     */             
/* 870 */             double t = Math.max(Math.abs(this.H[m][n - 1]), Math.abs(this.H[m][n]));
/* 871 */             if (eps * t * t > 1.0D) {
/* 872 */               for (i1 = m; i1 <= n; i1++) {
/* 873 */                 this.H[i1][n - 1] = this.H[i1][n - 1] / t;
/* 874 */                 this.H[i1][n] = this.H[i1][n] / t;
/*     */               } 
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 884 */     for (int k = 0; k < nn; k++) {
/* 885 */       if ((((k < low) ? 1 : 0) | ((k > high) ? 1 : 0)) != 0) {
/* 886 */         for (int m = k; m < nn; m++) {
/* 887 */           this.V[k][m] = this.H[k][m];
/*     */         }
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 894 */     for (int j = nn - 1; j >= low; j--) {
/* 895 */       for (int m = low; m <= high; m++) {
/* 896 */         z = 0.0D;
/* 897 */         for (int i1 = low; i1 <= Math.min(j, high); i1++) {
/* 898 */           z += this.V[m][i1] * this.H[i1][j];
/*     */         }
/* 900 */         this.V[m][j] = z;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[][] getV() {
/* 910 */     return Matrix.SubMatrix(this.V, this.n, this.n);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] getRealEigenvalues() {
/* 918 */     return this.d;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] getImagEigenvalues() {
/* 926 */     return this.e;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[][] getD() {
/* 934 */     double[][] X = new double[this.n][this.n];
/* 935 */     double[][] D = X;
/* 936 */     for (int i = 0; i < this.n; i++) {
/* 937 */       for (int j = 0; j < this.n; j++) {
/* 938 */         D[i][j] = 0.0D;
/*     */       }
/* 940 */       D[i][i] = this.d[i];
/* 941 */       if (this.e[i] > 0.0D) {
/* 942 */         D[i][i + 1] = this.e[i];
/* 943 */       } else if (this.e[i] < 0.0D) {
/* 944 */         D[i][i - 1] = this.e[i];
/*     */       } 
/*     */     } 
/* 947 */     return X;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Math/Decompositions/EigenvalueDecomposition.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */