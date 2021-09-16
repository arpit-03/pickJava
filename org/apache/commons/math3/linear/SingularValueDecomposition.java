/*     */ package org.apache.commons.math3.linear;
/*     */ 
/*     */ import org.apache.commons.math3.exception.NumberIsTooLargeException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.util.FastMath;
/*     */ import org.apache.commons.math3.util.Precision;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ {
/*     */   private static final double EPS = 2.220446049250313E-16D;
/*     */   private static final double TINY = 1.6033346880071782E-291D;
/*     */   private final double[] singularValues;
/*     */   private final int m;
/*     */   private final int n;
/*     */   private final boolean transposed;
/*     */   private final RealMatrix cachedU;
/*     */   private RealMatrix cachedUt;
/*     */   private RealMatrix cachedS;
/*     */   private final RealMatrix cachedV;
/*     */   private RealMatrix cachedVt;
/*     */   private final double tol;
/*     */   
/*     */   public SingularValueDecomposition(RealMatrix matrix) {
/*     */     double[][] A;
/*  91 */     if (matrix.getRowDimension() < matrix.getColumnDimension()) {
/*  92 */       this.transposed = true;
/*  93 */       A = matrix.transpose().getData();
/*  94 */       this.m = matrix.getColumnDimension();
/*  95 */       this.n = matrix.getRowDimension();
/*     */     } else {
/*  97 */       this.transposed = false;
/*  98 */       A = matrix.getData();
/*  99 */       this.m = matrix.getRowDimension();
/* 100 */       this.n = matrix.getColumnDimension();
/*     */     } 
/*     */     
/* 103 */     this.singularValues = new double[this.n];
/* 104 */     double[][] U = new double[this.m][this.n];
/* 105 */     double[][] V = new double[this.n][this.n];
/* 106 */     double[] e = new double[this.n];
/* 107 */     double[] work = new double[this.m];
/*     */ 
/*     */     
/* 110 */     int nct = FastMath.min(this.m - 1, this.n);
/* 111 */     int nrt = FastMath.max(0, this.n - 2);
/* 112 */     for (int k = 0; k < FastMath.max(nct, nrt); k++) {
/* 113 */       if (k < nct) {
/*     */ 
/*     */ 
/*     */         
/* 117 */         this.singularValues[k] = 0.0D; int n;
/* 118 */         for (n = k; n < this.m; n++) {
/* 119 */           this.singularValues[k] = FastMath.hypot(this.singularValues[k], A[n][k]);
/*     */         }
/* 121 */         if (this.singularValues[k] != 0.0D) {
/* 122 */           if (A[k][k] < 0.0D) {
/* 123 */             this.singularValues[k] = -this.singularValues[k];
/*     */           }
/* 125 */           for (n = k; n < this.m; n++) {
/* 126 */             A[n][k] = A[n][k] / this.singularValues[k];
/*     */           }
/* 128 */           A[k][k] = A[k][k] + 1.0D;
/*     */         } 
/* 130 */         this.singularValues[k] = -this.singularValues[k];
/*     */       } 
/* 132 */       for (int m = k + 1; m < this.n; m++) {
/* 133 */         if (k < nct && this.singularValues[k] != 0.0D) {
/*     */ 
/*     */           
/* 136 */           double t = 0.0D; int n;
/* 137 */           for (n = k; n < this.m; n++) {
/* 138 */             t += A[n][k] * A[n][m];
/*     */           }
/* 140 */           t = -t / A[k][k];
/* 141 */           for (n = k; n < this.m; n++) {
/* 142 */             A[n][m] = A[n][m] + t * A[n][k];
/*     */           }
/*     */         } 
/*     */ 
/*     */         
/* 147 */         e[m] = A[k][m];
/*     */       } 
/* 149 */       if (k < nct)
/*     */       {
/*     */         
/* 152 */         for (int n = k; n < this.m; n++) {
/* 153 */           U[n][k] = A[n][k];
/*     */         }
/*     */       }
/* 156 */       if (k < nrt) {
/*     */ 
/*     */ 
/*     */         
/* 160 */         e[k] = 0.0D; int n;
/* 161 */         for (n = k + 1; n < this.n; n++) {
/* 162 */           e[k] = FastMath.hypot(e[k], e[n]);
/*     */         }
/* 164 */         if (e[k] != 0.0D) {
/* 165 */           if (e[k + 1] < 0.0D) {
/* 166 */             e[k] = -e[k];
/*     */           }
/* 168 */           for (n = k + 1; n < this.n; n++) {
/* 169 */             e[n] = e[n] / e[k];
/*     */           }
/* 171 */           e[k + 1] = e[k + 1] + 1.0D;
/*     */         } 
/* 173 */         e[k] = -e[k];
/* 174 */         if (k + 1 < this.m && e[k] != 0.0D) {
/*     */ 
/*     */           
/* 177 */           for (n = k + 1; n < this.m; n++)
/* 178 */             work[n] = 0.0D; 
/*     */           int i1;
/* 180 */           for (i1 = k + 1; i1 < this.n; i1++) {
/* 181 */             for (int i2 = k + 1; i2 < this.m; i2++) {
/* 182 */               work[i2] = work[i2] + e[i1] * A[i2][i1];
/*     */             }
/*     */           } 
/* 185 */           for (i1 = k + 1; i1 < this.n; i1++) {
/* 186 */             double t = -e[i1] / e[k + 1];
/* 187 */             for (int i2 = k + 1; i2 < this.m; i2++) {
/* 188 */               A[i2][i1] = A[i2][i1] + t * work[i2];
/*     */             }
/*     */           } 
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 195 */         for (n = k + 1; n < this.n; n++) {
/* 196 */           V[n][k] = e[n];
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 201 */     int p = this.n;
/* 202 */     if (nct < this.n) {
/* 203 */       this.singularValues[nct] = A[nct][nct];
/*     */     }
/* 205 */     if (this.m < p) {
/* 206 */       this.singularValues[p - 1] = 0.0D;
/*     */     }
/* 208 */     if (nrt + 1 < p) {
/* 209 */       e[nrt] = A[nrt][p - 1];
/*     */     }
/* 211 */     e[p - 1] = 0.0D;
/*     */ 
/*     */     
/* 214 */     for (int j = nct; j < this.n; j++) {
/* 215 */       for (int m = 0; m < this.m; m++) {
/* 216 */         U[m][j] = 0.0D;
/*     */       }
/* 218 */       U[j][j] = 1.0D;
/*     */     }  int i;
/* 220 */     for (i = nct - 1; i >= 0; i--) {
/* 221 */       if (this.singularValues[i] != 0.0D) {
/* 222 */         for (int n = i + 1; n < this.n; n++) {
/* 223 */           double t = 0.0D; int i1;
/* 224 */           for (i1 = i; i1 < this.m; i1++) {
/* 225 */             t += U[i1][i] * U[i1][n];
/*     */           }
/* 227 */           t = -t / U[i][i];
/* 228 */           for (i1 = i; i1 < this.m; i1++)
/* 229 */             U[i1][n] = U[i1][n] + t * U[i1][i]; 
/*     */         } 
/*     */         int m;
/* 232 */         for (m = i; m < this.m; m++) {
/* 233 */           U[m][i] = -U[m][i];
/*     */         }
/* 235 */         U[i][i] = 1.0D + U[i][i];
/* 236 */         for (m = 0; m < i - 1; m++) {
/* 237 */           U[m][i] = 0.0D;
/*     */         }
/*     */       } else {
/* 240 */         for (int m = 0; m < this.m; m++) {
/* 241 */           U[m][i] = 0.0D;
/*     */         }
/* 243 */         U[i][i] = 1.0D;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 248 */     for (i = this.n - 1; i >= 0; i--) {
/* 249 */       if (i < nrt && e[i] != 0.0D)
/*     */       {
/* 251 */         for (int n = i + 1; n < this.n; n++) {
/* 252 */           double t = 0.0D; int i1;
/* 253 */           for (i1 = i + 1; i1 < this.n; i1++) {
/* 254 */             t += V[i1][i] * V[i1][n];
/*     */           }
/* 256 */           t = -t / V[i + 1][i];
/* 257 */           for (i1 = i + 1; i1 < this.n; i1++) {
/* 258 */             V[i1][n] = V[i1][n] + t * V[i1][i];
/*     */           }
/*     */         } 
/*     */       }
/* 262 */       for (int m = 0; m < this.n; m++) {
/* 263 */         V[m][i] = 0.0D;
/*     */       }
/* 265 */       V[i][i] = 1.0D;
/*     */     } 
/*     */ 
/*     */     
/* 269 */     int pp = p - 1;
/* 270 */     while (p > 0) {
/*     */       int kase;
/*     */ 
/*     */       
/*     */       double f, maxPm1Pm2;
/*     */       
/*     */       int n;
/*     */       
/*     */       double scale, sp, spm1, epm1, sk, ek, b, c, shift, d1, g;
/*     */       
/*     */       int i1, m;
/*     */       
/* 282 */       for (m = p - 2; m >= 0; m--) {
/* 283 */         double threshold = 1.6033346880071782E-291D + 2.220446049250313E-16D * (FastMath.abs(this.singularValues[m]) + FastMath.abs(this.singularValues[m + 1]));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 293 */         if (FastMath.abs(e[m]) <= threshold) {
/* 294 */           e[m] = 0.0D;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */       
/* 300 */       if (m == p - 2) {
/* 301 */         kase = 4;
/*     */       } else {
/*     */         int ks;
/* 304 */         for (ks = p - 1; ks >= m && 
/* 305 */           ks != m; ks--) {
/*     */ 
/*     */           
/* 308 */           double t = ((ks != p) ? FastMath.abs(e[ks]) : 0.0D) + ((ks != m + 1) ? FastMath.abs(e[ks - 1]) : 0.0D);
/*     */           
/* 310 */           if (FastMath.abs(this.singularValues[ks]) <= 1.6033346880071782E-291D + 2.220446049250313E-16D * t) {
/* 311 */             this.singularValues[ks] = 0.0D;
/*     */             break;
/*     */           } 
/*     */         } 
/* 315 */         if (ks == m) {
/* 316 */           kase = 3;
/* 317 */         } else if (ks == p - 1) {
/* 318 */           kase = 1;
/*     */         } else {
/* 320 */           kase = 2;
/* 321 */           m = ks;
/*     */         } 
/*     */       } 
/* 324 */       m++;
/*     */       
/* 326 */       switch (kase) {
/*     */         
/*     */         case 1:
/* 329 */           f = e[p - 2];
/* 330 */           e[p - 2] = 0.0D;
/* 331 */           for (n = p - 2; n >= m; n--) {
/* 332 */             double t = FastMath.hypot(this.singularValues[n], f);
/* 333 */             double cs = this.singularValues[n] / t;
/* 334 */             double sn = f / t;
/* 335 */             this.singularValues[n] = t;
/* 336 */             if (n != m) {
/* 337 */               f = -sn * e[n - 1];
/* 338 */               e[n - 1] = cs * e[n - 1];
/*     */             } 
/*     */             
/* 341 */             for (int i2 = 0; i2 < this.n; i2++) {
/* 342 */               t = cs * V[i2][n] + sn * V[i2][p - 1];
/* 343 */               V[i2][p - 1] = -sn * V[i2][n] + cs * V[i2][p - 1];
/* 344 */               V[i2][n] = t;
/*     */             } 
/*     */           } 
/*     */           continue;
/*     */ 
/*     */         
/*     */         case 2:
/* 351 */           f = e[m - 1];
/* 352 */           e[m - 1] = 0.0D;
/* 353 */           for (n = m; n < p; n++) {
/* 354 */             double t = FastMath.hypot(this.singularValues[n], f);
/* 355 */             double cs = this.singularValues[n] / t;
/* 356 */             double sn = f / t;
/* 357 */             this.singularValues[n] = t;
/* 358 */             f = -sn * e[n];
/* 359 */             e[n] = cs * e[n];
/*     */             
/* 361 */             for (int i2 = 0; i2 < this.m; i2++) {
/* 362 */               t = cs * U[i2][n] + sn * U[i2][m - 1];
/* 363 */               U[i2][m - 1] = -sn * U[i2][n] + cs * U[i2][m - 1];
/* 364 */               U[i2][n] = t;
/*     */             } 
/*     */           } 
/*     */           continue;
/*     */ 
/*     */ 
/*     */         
/*     */         case 3:
/* 372 */           maxPm1Pm2 = FastMath.max(FastMath.abs(this.singularValues[p - 1]), FastMath.abs(this.singularValues[p - 2]));
/*     */           
/* 374 */           scale = FastMath.max(FastMath.max(FastMath.max(maxPm1Pm2, FastMath.abs(e[p - 2])), FastMath.abs(this.singularValues[m])), FastMath.abs(e[m]));
/*     */ 
/*     */ 
/*     */           
/* 378 */           sp = this.singularValues[p - 1] / scale;
/* 379 */           spm1 = this.singularValues[p - 2] / scale;
/* 380 */           epm1 = e[p - 2] / scale;
/* 381 */           sk = this.singularValues[m] / scale;
/* 382 */           ek = e[m] / scale;
/* 383 */           b = ((spm1 + sp) * (spm1 - sp) + epm1 * epm1) / 2.0D;
/* 384 */           c = sp * epm1 * sp * epm1;
/* 385 */           shift = 0.0D;
/* 386 */           if (b != 0.0D || c != 0.0D) {
/*     */             
/* 388 */             shift = FastMath.sqrt(b * b + c);
/* 389 */             if (b < 0.0D) {
/* 390 */               shift = -shift;
/*     */             }
/* 392 */             shift = c / (b + shift);
/*     */           } 
/* 394 */           d1 = (sk + sp) * (sk - sp) + shift;
/* 395 */           g = sk * ek;
/*     */           
/* 397 */           for (i1 = m; i1 < p - 1; i1++) {
/* 398 */             double t = FastMath.hypot(d1, g);
/* 399 */             double cs = d1 / t;
/* 400 */             double sn = g / t;
/* 401 */             if (i1 != m) {
/* 402 */               e[i1 - 1] = t;
/*     */             }
/* 404 */             d1 = cs * this.singularValues[i1] + sn * e[i1];
/* 405 */             e[i1] = cs * e[i1] - sn * this.singularValues[i1];
/* 406 */             g = sn * this.singularValues[i1 + 1];
/* 407 */             this.singularValues[i1 + 1] = cs * this.singularValues[i1 + 1];
/*     */             int i2;
/* 409 */             for (i2 = 0; i2 < this.n; i2++) {
/* 410 */               t = cs * V[i2][i1] + sn * V[i2][i1 + 1];
/* 411 */               V[i2][i1 + 1] = -sn * V[i2][i1] + cs * V[i2][i1 + 1];
/* 412 */               V[i2][i1] = t;
/*     */             } 
/* 414 */             t = FastMath.hypot(d1, g);
/* 415 */             cs = d1 / t;
/* 416 */             sn = g / t;
/* 417 */             this.singularValues[i1] = t;
/* 418 */             d1 = cs * e[i1] + sn * this.singularValues[i1 + 1];
/* 419 */             this.singularValues[i1 + 1] = -sn * e[i1] + cs * this.singularValues[i1 + 1];
/* 420 */             g = sn * e[i1 + 1];
/* 421 */             e[i1 + 1] = cs * e[i1 + 1];
/* 422 */             if (i1 < this.m - 1) {
/* 423 */               for (i2 = 0; i2 < this.m; i2++) {
/* 424 */                 t = cs * U[i2][i1] + sn * U[i2][i1 + 1];
/* 425 */                 U[i2][i1 + 1] = -sn * U[i2][i1] + cs * U[i2][i1 + 1];
/* 426 */                 U[i2][i1] = t;
/*     */               } 
/*     */             }
/*     */           } 
/* 430 */           e[p - 2] = d1;
/*     */           continue;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 436 */       if (this.singularValues[m] <= 0.0D) {
/* 437 */         this.singularValues[m] = (this.singularValues[m] < 0.0D) ? -this.singularValues[m] : 0.0D;
/*     */         
/* 439 */         for (int i2 = 0; i2 <= pp; i2++) {
/* 440 */           V[i2][m] = -V[i2][m];
/*     */         }
/*     */       } 
/*     */       
/* 444 */       while (m < pp && 
/* 445 */         this.singularValues[m] < this.singularValues[m + 1]) {
/*     */ 
/*     */         
/* 448 */         double t = this.singularValues[m];
/* 449 */         this.singularValues[m] = this.singularValues[m + 1];
/* 450 */         this.singularValues[m + 1] = t;
/* 451 */         if (m < this.n - 1) {
/* 452 */           for (int i2 = 0; i2 < this.n; i2++) {
/* 453 */             t = V[i2][m + 1];
/* 454 */             V[i2][m + 1] = V[i2][m];
/* 455 */             V[i2][m] = t;
/*     */           } 
/*     */         }
/* 458 */         if (m < this.m - 1) {
/* 459 */           for (int i2 = 0; i2 < this.m; i2++) {
/* 460 */             t = U[i2][m + 1];
/* 461 */             U[i2][m + 1] = U[i2][m];
/* 462 */             U[i2][m] = t;
/*     */           } 
/*     */         }
/* 465 */         m++;
/*     */       } 
/* 467 */       p--;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 474 */     this.tol = FastMath.max(this.m * this.singularValues[0] * 2.220446049250313E-16D, FastMath.sqrt(Precision.SAFE_MIN));
/*     */ 
/*     */     
/* 477 */     if (!this.transposed) {
/* 478 */       this.cachedU = MatrixUtils.createRealMatrix(U);
/* 479 */       this.cachedV = MatrixUtils.createRealMatrix(V);
/*     */     } else {
/* 481 */       this.cachedU = MatrixUtils.createRealMatrix(V);
/* 482 */       this.cachedV = MatrixUtils.createRealMatrix(U);
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
/*     */   public RealMatrix getU() {
/* 494 */     return this.cachedU;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RealMatrix getUT() {
/* 505 */     if (this.cachedUt == null) {
/* 506 */       this.cachedUt = getU().transpose();
/*     */     }
/*     */     
/* 509 */     return this.cachedUt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RealMatrix getS() {
/* 519 */     if (this.cachedS == null)
/*     */     {
/* 521 */       this.cachedS = MatrixUtils.createRealDiagonalMatrix(this.singularValues);
/*     */     }
/* 523 */     return this.cachedS;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] getSingularValues() {
/* 533 */     return (double[])this.singularValues.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RealMatrix getV() {
/* 544 */     return this.cachedV;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RealMatrix getVT() {
/* 554 */     if (this.cachedVt == null) {
/* 555 */       this.cachedVt = getV().transpose();
/*     */     }
/*     */     
/* 558 */     return this.cachedVt;
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
/*     */   public RealMatrix getCovariance(double minSingularValue) {
/* 574 */     int p = this.singularValues.length;
/* 575 */     int dimension = 0;
/* 576 */     while (dimension < p && this.singularValues[dimension] >= minSingularValue)
/*     */     {
/* 578 */       dimension++;
/*     */     }
/*     */     
/* 581 */     if (dimension == 0) {
/* 582 */       throw new NumberIsTooLargeException(LocalizedFormats.TOO_LARGE_CUTOFF_SINGULAR_VALUE, Double.valueOf(minSingularValue), Double.valueOf(this.singularValues[0]), true);
/*     */     }
/*     */ 
/*     */     
/* 586 */     final double[][] data = new double[dimension][p];
/* 587 */     getVT().walkInOptimizedOrder(new DefaultRealMatrixPreservingVisitor()
/*     */         {
/*     */           
/*     */           public void visit(int row, int column, double value)
/*     */           {
/* 592 */             data[row][column] = value / SingularValueDecomposition.this.singularValues[row];
/*     */           }
/*     */         },  0, dimension - 1, 0, p - 1);
/*     */     
/* 596 */     RealMatrix jv = new Array2DRowRealMatrix(data, false);
/* 597 */     return jv.transpose().multiply(jv);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getNorm() {
/* 608 */     return this.singularValues[0];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getConditionNumber() {
/* 616 */     return this.singularValues[0] / this.singularValues[this.n - 1];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getInverseConditionNumber() {
/* 627 */     return this.singularValues[this.n - 1] / this.singularValues[0];
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
/*     */   public int getRank() {
/* 639 */     int r = 0;
/* 640 */     for (int i = 0; i < this.singularValues.length; i++) {
/* 641 */       if (this.singularValues[i] > this.tol) {
/* 642 */         r++;
/*     */       }
/*     */     } 
/* 645 */     return r;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DecompositionSolver getSolver() {
/* 653 */     return new Solver(this.singularValues, getUT(), getV(), (getRank() == this.m), this.tol);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class Solver
/*     */     implements DecompositionSolver
/*     */   {
/*     */     private final RealMatrix pseudoInverse;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private boolean nonSingular;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Solver(double[] singularValues, RealMatrix uT, RealMatrix v, boolean nonSingular, double tol) {
/* 674 */       double[][] suT = uT.getData();
/* 675 */       for (int i = 0; i < singularValues.length; i++) {
/*     */         double a;
/* 677 */         if (singularValues[i] > tol) {
/* 678 */           a = 1.0D / singularValues[i];
/*     */         } else {
/* 680 */           a = 0.0D;
/*     */         } 
/* 682 */         double[] suTi = suT[i];
/* 683 */         for (int j = 0; j < suTi.length; j++) {
/* 684 */           suTi[j] = suTi[j] * a;
/*     */         }
/*     */       } 
/* 687 */       this.pseudoInverse = v.multiply(new Array2DRowRealMatrix(suT, false));
/* 688 */       this.nonSingular = nonSingular;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public RealVector solve(RealVector b) {
/* 703 */       return this.pseudoInverse.operate(b);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public RealMatrix solve(RealMatrix b) {
/* 719 */       return this.pseudoInverse.multiply(b);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isNonSingular() {
/* 728 */       return this.nonSingular;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public RealMatrix getInverse() {
/* 737 */       return this.pseudoInverse;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/linear/SingularValueDecomposition.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */