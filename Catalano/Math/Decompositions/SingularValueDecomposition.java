/*     */ package Catalano.Math.Decompositions;
/*     */ 
/*     */ import Catalano.Math.Matrix;
/*     */ import Catalano.Math.Tools;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SingularValueDecomposition
/*     */   implements Serializable
/*     */ {
/*     */   private double[][] U;
/*     */   private double[][] V;
/*     */   private double[] s;
/*     */   private int m;
/*     */   private int n;
/*     */   boolean wantu = true;
/*     */   boolean wantv = true;
/*     */   
/*     */   public boolean isWantU() {
/*  73 */     return this.wantu;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWantU(boolean wantu) {
/*  81 */     this.wantu = wantu;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isWantV() {
/*  89 */     return this.wantv;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWantV(boolean wantv) {
/*  97 */     this.wantv = wantv;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SingularValueDecomposition(double[][] matrix, boolean wantU, boolean wantV) {
/* 107 */     this.wantu = wantU;
/* 108 */     this.wantv = wantV;
/* 109 */     Compute(matrix);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SingularValueDecomposition(double[][] matrix) {
/* 118 */     Compute(matrix);
/*     */   }
/*     */   
/*     */   private void Compute(double[][] matrix) {
/*     */     double[][] A;
/* 123 */     boolean swapped = false;
/*     */     
/* 125 */     if (matrix.length == 0 && (matrix[0]).length == 0) {
/* 126 */       throw new IllegalArgumentException("Matrix does not have any rows or columns.");
/*     */     }
/*     */     
/* 129 */     if (matrix.length < (matrix[0]).length) {
/* 130 */       A = Matrix.Transpose(matrix);
/* 131 */       swapped = true;
/*     */     } else {
/*     */       
/* 134 */       A = Matrix.Copy(matrix);
/*     */     } 
/*     */ 
/*     */     
/* 138 */     this.m = A.length;
/* 139 */     this.n = (A[0]).length;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 146 */     int nu = Math.min(this.m, this.n);
/* 147 */     this.s = new double[Math.min(this.m + 1, this.n)];
/* 148 */     this.U = new double[this.m][nu];
/* 149 */     this.V = new double[this.n][this.n];
/* 150 */     double[] e = new double[this.n];
/* 151 */     double[] work = new double[this.m];
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 156 */     int nct = Math.min(this.m - 1, this.n);
/* 157 */     int nrt = Math.max(0, Math.min(this.n - 2, this.m));
/* 158 */     for (int k = 0; k < Math.max(nct, nrt); k++) {
/* 159 */       if (k < nct) {
/*     */ 
/*     */ 
/*     */         
/* 163 */         this.s[k] = 0.0D; int i;
/* 164 */         for (i = k; i < this.m; i++) {
/* 165 */           this.s[k] = Tools.Hypotenuse(this.s[k], A[i][k]);
/*     */         }
/* 167 */         if (this.s[k] != 0.0D) {
/* 168 */           if (A[k][k] < 0.0D) {
/* 169 */             this.s[k] = -this.s[k];
/*     */           }
/* 171 */           for (i = k; i < this.m; i++) {
/* 172 */             A[i][k] = A[i][k] / this.s[k];
/*     */           }
/* 174 */           A[k][k] = A[k][k] + 1.0D;
/*     */         } 
/* 176 */         this.s[k] = -this.s[k];
/*     */       } 
/* 178 */       for (int j = k + 1; j < this.n; j++) {
/* 179 */         if (k < nct && this.s[k] != 0.0D) {
/*     */ 
/*     */ 
/*     */           
/* 183 */           double t = 0.0D; int i;
/* 184 */           for (i = k; i < this.m; i++) {
/* 185 */             t += A[i][k] * A[i][j];
/*     */           }
/* 187 */           t = -t / A[k][k];
/* 188 */           for (i = k; i < this.m; i++) {
/* 189 */             A[i][j] = A[i][j] + t * A[i][k];
/*     */           }
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 196 */         e[j] = A[k][j];
/*     */       } 
/* 198 */       if ((this.wantu & ((k < nct) ? 1 : 0)) != 0)
/*     */       {
/*     */ 
/*     */ 
/*     */         
/* 203 */         for (int i = k; i < this.m; i++) {
/* 204 */           this.U[i][k] = A[i][k];
/*     */         }
/*     */       }
/* 207 */       if (k < nrt) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 212 */         e[k] = 0.0D; int i;
/* 213 */         for (i = k + 1; i < this.n; i++) {
/* 214 */           e[k] = Tools.Hypotenuse(e[k], e[i]);
/*     */         }
/* 216 */         if (e[k] != 0.0D) {
/* 217 */           if (e[k + 1] < 0.0D) {
/* 218 */             e[k] = -e[k];
/*     */           }
/* 220 */           for (i = k + 1; i < this.n; i++) {
/* 221 */             e[i] = e[i] / e[k];
/*     */           }
/* 223 */           e[k + 1] = e[k + 1] + 1.0D;
/*     */         } 
/* 225 */         e[k] = -e[k];
/* 226 */         if ((((k + 1 < this.m) ? 1 : 0) & ((e[k] != 0.0D) ? 1 : 0)) != 0) {
/*     */ 
/*     */ 
/*     */           
/* 230 */           for (i = k + 1; i < this.m; i++)
/* 231 */             work[i] = 0.0D; 
/*     */           int m;
/* 233 */           for (m = k + 1; m < this.n; m++) {
/* 234 */             for (int n = k + 1; n < this.m; n++) {
/* 235 */               work[n] = work[n] + e[m] * A[n][m];
/*     */             }
/*     */           } 
/* 238 */           for (m = k + 1; m < this.n; m++) {
/* 239 */             double t = -e[m] / e[k + 1];
/* 240 */             for (int n = k + 1; n < this.m; n++) {
/* 241 */               A[n][m] = A[n][m] + t * work[n];
/*     */             }
/*     */           } 
/*     */         } 
/* 245 */         if (this.wantv)
/*     */         {
/*     */ 
/*     */ 
/*     */           
/* 250 */           for (i = k + 1; i < this.n; i++) {
/* 251 */             this.V[i][k] = e[i];
/*     */           }
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 259 */     int p = Math.min(this.n, this.m + 1);
/* 260 */     if (nct < this.n) {
/* 261 */       this.s[nct] = A[nct][nct];
/*     */     }
/* 263 */     if (this.m < p) {
/* 264 */       this.s[p - 1] = 0.0D;
/*     */     }
/* 266 */     if (nrt + 1 < p) {
/* 267 */       e[nrt] = A[nrt][p - 1];
/*     */     }
/* 269 */     e[p - 1] = 0.0D;
/*     */ 
/*     */ 
/*     */     
/* 273 */     if (this.wantu) {
/* 274 */       for (int j = nct; j < nu; j++) {
/* 275 */         for (int m = 0; m < this.m; m++) {
/* 276 */           this.U[m][j] = 0.0D;
/*     */         }
/* 278 */         this.U[j][j] = 1.0D;
/*     */       } 
/* 280 */       for (int i = nct - 1; i >= 0; i--) {
/* 281 */         if (this.s[i] != 0.0D) {
/* 282 */           for (int n = i + 1; n < nu; n++) {
/* 283 */             double t = 0.0D; int i1;
/* 284 */             for (i1 = i; i1 < this.m; i1++) {
/* 285 */               t += this.U[i1][i] * this.U[i1][n];
/*     */             }
/* 287 */             t = -t / this.U[i][i];
/* 288 */             for (i1 = i; i1 < this.m; i1++)
/* 289 */               this.U[i1][n] = this.U[i1][n] + t * this.U[i1][i]; 
/*     */           } 
/*     */           int m;
/* 292 */           for (m = i; m < this.m; m++) {
/* 293 */             this.U[m][i] = -this.U[m][i];
/*     */           }
/* 295 */           this.U[i][i] = 1.0D + this.U[i][i];
/* 296 */           for (m = 0; m < i - 1; m++) {
/* 297 */             this.U[m][i] = 0.0D;
/*     */           }
/*     */         } else {
/* 300 */           for (int m = 0; m < this.m; m++) {
/* 301 */             this.U[m][i] = 0.0D;
/*     */           }
/* 303 */           this.U[i][i] = 1.0D;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 310 */     if (this.wantv) {
/* 311 */       for (int i = this.n - 1; i >= 0; i--) {
/* 312 */         if ((((i < nrt) ? 1 : 0) & ((e[i] != 0.0D) ? 1 : 0)) != 0) {
/* 313 */           for (int m = i + 1; m < this.n; m++) {
/* 314 */             double t = 0.0D; int n;
/* 315 */             for (n = i + 1; n < this.n; n++) {
/* 316 */               t += this.V[n][i] * this.V[n][m];
/*     */             }
/* 318 */             t = -t / this.V[i + 1][i];
/* 319 */             for (n = i + 1; n < this.n; n++) {
/* 320 */               this.V[n][m] = this.V[n][m] + t * this.V[n][i];
/*     */             }
/*     */           } 
/*     */         }
/* 324 */         for (int j = 0; j < this.n; j++) {
/* 325 */           this.V[j][i] = 0.0D;
/*     */         }
/* 327 */         this.V[i][i] = 1.0D;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 333 */     int pp = p - 1;
/* 334 */     int iter = 0;
/* 335 */     double eps = Math.pow(2.0D, -52.0D);
/* 336 */     double tiny = Math.pow(2.0D, -966.0D);
/* 337 */     while (p > 0) {
/*     */       int kase;
/*     */ 
/*     */       
/*     */       double f, scale;
/*     */ 
/*     */       
/*     */       int j;
/*     */ 
/*     */       
/*     */       double sp, spm1, epm1, sk, ek, b, c, shift, d1, g;
/*     */ 
/*     */       
/*     */       int m, i;
/*     */       
/* 352 */       for (i = p - 2; i >= -1 && 
/* 353 */         i != -1; i--) {
/*     */ 
/*     */         
/* 356 */         if (Math.abs(e[i]) <= 
/* 357 */           tiny + eps * (Math.abs(this.s[i]) + Math.abs(this.s[i + 1]))) {
/* 358 */           e[i] = 0.0D;
/*     */           break;
/*     */         } 
/*     */       } 
/* 362 */       if (i == p - 2) {
/* 363 */         kase = 4;
/*     */       } else {
/*     */         int ks;
/* 366 */         for (ks = p - 1; ks >= i && 
/* 367 */           ks != i; ks--) {
/*     */ 
/*     */           
/* 370 */           double t = ((ks != p) ? Math.abs(e[ks]) : 0.0D) + (
/* 371 */             (ks != i + 1) ? Math.abs(e[ks - 1]) : 0.0D);
/* 372 */           if (Math.abs(this.s[ks]) <= tiny + eps * t) {
/* 373 */             this.s[ks] = 0.0D;
/*     */             break;
/*     */           } 
/*     */         } 
/* 377 */         if (ks == i) {
/* 378 */           kase = 3;
/* 379 */         } else if (ks == p - 1) {
/* 380 */           kase = 1;
/*     */         } else {
/* 382 */           kase = 2;
/* 383 */           i = ks;
/*     */         } 
/*     */       } 
/* 386 */       i++;
/*     */ 
/*     */       
/* 389 */       switch (kase) {
/*     */ 
/*     */ 
/*     */         
/*     */         case 1:
/* 394 */           f = e[p - 2];
/* 395 */           e[p - 2] = 0.0D;
/* 396 */           for (j = p - 2; j >= i; j--) {
/* 397 */             double t = Tools.Hypotenuse(this.s[j], f);
/* 398 */             double cs = this.s[j] / t;
/* 399 */             double sn = f / t;
/* 400 */             this.s[j] = t;
/* 401 */             if (j != i) {
/* 402 */               f = -sn * e[j - 1];
/* 403 */               e[j - 1] = cs * e[j - 1];
/*     */             } 
/* 405 */             if (this.wantv) {
/* 406 */               for (int n = 0; n < this.n; n++) {
/* 407 */                 t = cs * this.V[n][j] + sn * this.V[n][p - 1];
/* 408 */                 this.V[n][p - 1] = -sn * this.V[n][j] + cs * this.V[n][p - 1];
/* 409 */                 this.V[n][j] = t;
/*     */               } 
/*     */             }
/*     */           } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         case 2:
/* 419 */           f = e[i - 1];
/* 420 */           e[i - 1] = 0.0D;
/* 421 */           for (j = i; j < p; j++) {
/* 422 */             double t = Tools.Hypotenuse(this.s[j], f);
/* 423 */             double cs = this.s[j] / t;
/* 424 */             double sn = f / t;
/* 425 */             this.s[j] = t;
/* 426 */             f = -sn * e[j];
/* 427 */             e[j] = cs * e[j];
/* 428 */             if (this.wantu) {
/* 429 */               for (int n = 0; n < this.m; n++) {
/* 430 */                 t = cs * this.U[n][j] + sn * this.U[n][i - 1];
/* 431 */                 this.U[n][i - 1] = -sn * this.U[n][j] + cs * this.U[n][i - 1];
/* 432 */                 this.U[n][j] = t;
/*     */               } 
/*     */             }
/*     */           } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         case 3:
/* 445 */           scale = Math.max(Math.max(Math.max(Math.max(
/* 446 */                     Math.abs(this.s[p - 1]), Math.abs(this.s[p - 2])), Math.abs(e[p - 2])), 
/* 447 */                 Math.abs(this.s[i])), Math.abs(e[i]));
/* 448 */           sp = this.s[p - 1] / scale;
/* 449 */           spm1 = this.s[p - 2] / scale;
/* 450 */           epm1 = e[p - 2] / scale;
/* 451 */           sk = this.s[i] / scale;
/* 452 */           ek = e[i] / scale;
/* 453 */           b = ((spm1 + sp) * (spm1 - sp) + epm1 * epm1) / 2.0D;
/* 454 */           c = sp * epm1 * sp * epm1;
/* 455 */           shift = 0.0D;
/* 456 */           if ((((b != 0.0D) ? 1 : 0) | ((c != 0.0D) ? 1 : 0)) != 0) {
/* 457 */             shift = Math.sqrt(b * b + c);
/* 458 */             if (b < 0.0D) {
/* 459 */               shift = -shift;
/*     */             }
/* 461 */             shift = c / (b + shift);
/*     */           } 
/* 463 */           d1 = (sk + sp) * (sk - sp) + shift;
/* 464 */           g = sk * ek;
/*     */ 
/*     */ 
/*     */           
/* 468 */           for (m = i; m < p - 1; m++) {
/* 469 */             double t = Tools.Hypotenuse(d1, g);
/* 470 */             double cs = d1 / t;
/* 471 */             double sn = g / t;
/* 472 */             if (m != i) {
/* 473 */               e[m - 1] = t;
/*     */             }
/* 475 */             d1 = cs * this.s[m] + sn * e[m];
/* 476 */             e[m] = cs * e[m] - sn * this.s[m];
/* 477 */             g = sn * this.s[m + 1];
/* 478 */             this.s[m + 1] = cs * this.s[m + 1];
/* 479 */             if (this.wantv) {
/* 480 */               for (int n = 0; n < this.n; n++) {
/* 481 */                 t = cs * this.V[n][m] + sn * this.V[n][m + 1];
/* 482 */                 this.V[n][m + 1] = -sn * this.V[n][m] + cs * this.V[n][m + 1];
/* 483 */                 this.V[n][m] = t;
/*     */               } 
/*     */             }
/* 486 */             t = Tools.Hypotenuse(d1, g);
/* 487 */             cs = d1 / t;
/* 488 */             sn = g / t;
/* 489 */             this.s[m] = t;
/* 490 */             d1 = cs * e[m] + sn * this.s[m + 1];
/* 491 */             this.s[m + 1] = -sn * e[m] + cs * this.s[m + 1];
/* 492 */             g = sn * e[m + 1];
/* 493 */             e[m + 1] = cs * e[m + 1];
/* 494 */             if (this.wantu && m < this.m - 1) {
/* 495 */               for (int n = 0; n < this.m; n++) {
/* 496 */                 t = cs * this.U[n][m] + sn * this.U[n][m + 1];
/* 497 */                 this.U[n][m + 1] = -sn * this.U[n][m] + cs * this.U[n][m + 1];
/* 498 */                 this.U[n][m] = t;
/*     */               } 
/*     */             }
/*     */           } 
/* 502 */           e[p - 2] = d1;
/* 503 */           iter++;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         case 4:
/* 512 */           if (this.s[i] <= 0.0D) {
/* 513 */             this.s[i] = (this.s[i] < 0.0D) ? -this.s[i] : 0.0D;
/* 514 */             if (this.wantv) {
/* 515 */               for (int n = 0; n <= pp; n++) {
/* 516 */                 this.V[n][i] = -this.V[n][i];
/*     */               }
/*     */             }
/*     */           } 
/*     */ 
/*     */           
/* 522 */           while (i < pp && 
/* 523 */             this.s[i] < this.s[i + 1]) {
/*     */ 
/*     */             
/* 526 */             double t = this.s[i];
/* 527 */             this.s[i] = this.s[i + 1];
/* 528 */             this.s[i + 1] = t;
/* 529 */             if (this.wantv && i < this.n - 1) {
/* 530 */               for (int n = 0; n < this.n; n++) {
/* 531 */                 t = this.V[n][i + 1];
/* 532 */                 this.V[n][i + 1] = this.V[n][i];
/* 533 */                 this.V[n][i] = -t;
/*     */               } 
/*     */             }
/* 536 */             if (this.wantu && i < this.m - 1) {
/* 537 */               for (int n = 0; n < this.m; n++) {
/* 538 */                 t = this.U[n][i + 1];
/* 539 */                 this.U[n][i + 1] = this.U[n][i];
/* 540 */                 this.U[n][i] = t;
/*     */               } 
/*     */             }
/* 543 */             i++;
/*     */           } 
/* 545 */           iter = 0;
/* 546 */           p--;
/*     */       } 
/*     */ 
/*     */ 
/*     */     
/*     */     } 
/* 552 */     if (swapped) {
/* 553 */       double[][] temp = Matrix.Copy(this.U);
/* 554 */       this.U = this.V;
/* 555 */       this.V = temp;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[][] getU() {
/* 565 */     return this.U;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[][] getV() {
/* 573 */     return this.V;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] getSingularValues() {
/* 581 */     return this.s;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[][] getS() {
/* 589 */     double[][] S = new double[this.n][this.n];
/* 590 */     for (int i = 0; i < this.n; i++) {
/* 591 */       for (int j = 0; j < this.n; j++) {
/* 592 */         S[i][j] = 0.0D;
/*     */       }
/* 594 */       S[i][i] = this.s[i];
/*     */     } 
/* 596 */     return S;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double norm2() {
/* 605 */     return this.s[0];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double cond() {
/* 614 */     return this.s[0] / this.s[Math.min(this.m, this.n) - 1];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int rank() {
/* 622 */     double eps = Math.pow(2.0D, -52.0D);
/* 623 */     double tol = Math.max(this.m, this.n) * this.s[0] * eps;
/* 624 */     int r = 0;
/* 625 */     for (int i = 0; i < this.s.length; i++) {
/* 626 */       if (this.s[i] > tol) {
/* 627 */         r++;
/*     */       }
/*     */     } 
/* 630 */     return r;
/*     */   }
/*     */   
/*     */   public double threshold() {
/* 634 */     return 1.1102230246251565E-16D * Math.max(this.m, this.n) * this.s[0];
/*     */   }
/*     */ 
/*     */   
/*     */   public double[][] inverse() {
/* 639 */     double e = threshold();
/*     */ 
/*     */     
/* 642 */     int vrows = this.V.length;
/* 643 */     int vcols = (this.V[0]).length;
/* 644 */     double[][] X = new double[vrows][this.s.length];
/* 645 */     for (int i = 0; i < vrows; i++) {
/*     */       
/* 647 */       for (int k = 0; k < vcols; k++) {
/*     */         
/* 649 */         if (Math.abs(this.s[k]) > e) {
/* 650 */           X[i][k] = this.V[i][k] / this.s[k];
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 655 */     int urows = this.U.length;
/* 656 */     int ucols = (this.U[0]).length;
/* 657 */     double[][] Y = new double[vrows][urows];
/* 658 */     for (int j = 0; j < vrows; j++) {
/*     */       
/* 660 */       for (int k = 0; k < urows; k++) {
/*     */         
/* 662 */         double sum = 0.0D;
/* 663 */         for (int m = 0; m < ucols; m++)
/* 664 */           sum += X[j][m] * this.U[k][m]; 
/* 665 */         Y[j][k] = sum;
/*     */       } 
/*     */     } 
/*     */     
/* 669 */     return Y;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Math/Decompositions/SingularValueDecomposition.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */