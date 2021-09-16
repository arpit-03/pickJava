/*     */ package org.la4j.decomposition;
/*     */ 
/*     */ import org.la4j.Matrices;
/*     */ import org.la4j.Matrix;
/*     */ import org.la4j.Vector;
/*     */ import org.la4j.Vectors;
/*     */ import org.la4j.matrix.SparseMatrix;
/*     */ import org.la4j.vector.DenseVector;
/*     */ import org.la4j.vector.functor.VectorAccumulator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EigenDecompositor
/*     */   extends AbstractDecompositor
/*     */   implements MatrixDecompositor
/*     */ {
/*     */   public EigenDecompositor(Matrix matrix) {
/*  41 */     super(matrix);
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
/*     */   public Matrix[] decompose() {
/*  57 */     if (this.matrix.is(Matrices.SYMMETRIC_MATRIX))
/*  58 */       return decomposeSymmetricMatrix(this.matrix); 
/*  59 */     if (this.matrix.rows() == this.matrix.columns()) {
/*  60 */       return decomposeNonSymmetricMatrix(this.matrix);
/*     */     }
/*  62 */     throw new IllegalArgumentException("Can't decompose rectangle matrix");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean applicableTo(Matrix matrix) {
/*  68 */     return (matrix.rows() == matrix.columns());
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
/*     */   private Matrix[] decomposeSymmetricMatrix(Matrix matrix) {
/*  86 */     Matrix matrix1, d = matrix.copy();
/*  87 */     SparseMatrix sparseMatrix1 = SparseMatrix.identity(matrix.rows());
/*     */     
/*  89 */     Vector r = generateR(d);
/*  90 */     SparseMatrix sparseMatrix2 = SparseMatrix.identity(matrix.rows());
/*     */     
/*  92 */     VectorAccumulator normAccumulator = Vectors.mkEuclideanNormAccumulator();
/*     */     
/*  94 */     double n = Matrices.EPS;
/*  95 */     double nn = r.fold(normAccumulator);
/*     */     
/*  97 */     int kk = 0, ll = 0;
/*     */     
/*  99 */     while (Math.abs(n - nn) > Matrices.EPS) {
/*     */       
/* 101 */       int k = findMax(r);
/* 102 */       int l = findMax(d, k);
/*     */       
/* 104 */       regenerateU((Matrix)sparseMatrix2, d, k, l, kk, ll);
/*     */       
/* 106 */       kk = k;
/* 107 */       ll = l;
/*     */       
/* 109 */       matrix1 = sparseMatrix1.multiply((Matrix)sparseMatrix2);
/* 110 */       d = sparseMatrix2.transpose().multiply(d.multiply((Matrix)sparseMatrix2));
/*     */       
/* 112 */       r.set(k, generateRi(d, k));
/* 113 */       r.set(l, generateRi(d, l));
/*     */       
/* 115 */       n = nn;
/* 116 */       nn = r.fold(normAccumulator);
/*     */     } 
/*     */     
/* 119 */     return new Matrix[] { matrix1, d };
/*     */   }
/*     */ 
/*     */   
/*     */   private int findMax(Vector vector) {
/* 124 */     double value = vector.get(0);
/* 125 */     int result = 0;
/*     */     
/* 127 */     for (int i = 1; i < vector.length(); i++) {
/* 128 */       double v = vector.get(i);
/* 129 */       if (Math.abs(value) < Math.abs(v)) {
/* 130 */         result = i;
/* 131 */         value = v;
/*     */       } 
/*     */     } 
/*     */     
/* 135 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   private int findMax(Matrix matrix, int i) {
/* 140 */     double value = (i > 0) ? matrix.get(i, 0) : matrix.get(i, 1);
/* 141 */     int result = (i > 0) ? 0 : 1;
/*     */     
/* 143 */     for (int j = 0; j < matrix.columns(); j++) {
/* 144 */       if (i != j) {
/* 145 */         double v = matrix.get(i, j);
/* 146 */         if (Math.abs(value) < Math.abs(v)) {
/* 147 */           result = j;
/* 148 */           value = v;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 153 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   private Vector generateR(Matrix matrix) {
/* 158 */     DenseVector denseVector = DenseVector.zero(matrix.rows());
/*     */     
/* 160 */     for (int i = 0; i < matrix.rows(); i++) {
/* 161 */       denseVector.set(i, generateRi(matrix, i));
/*     */     }
/*     */     
/* 164 */     return (Vector)denseVector;
/*     */   }
/*     */   
/*     */   private double generateRi(Matrix matrix, int i) {
/* 168 */     double acc = 0.0D;
/* 169 */     for (int j = 0; j < matrix.columns(); j++) {
/* 170 */       if (j != i) {
/* 171 */         double value = matrix.get(i, j);
/* 172 */         acc += value * value;
/*     */       } 
/*     */     } 
/*     */     
/* 176 */     return acc;
/*     */   }
/*     */ 
/*     */   
/*     */   private void regenerateU(Matrix u, Matrix matrix, int k, int l, int kk, int ll) {
/* 181 */     u.set(kk, kk, 1.0D);
/* 182 */     u.set(ll, ll, 1.0D);
/* 183 */     u.set(kk, ll, 0.0D);
/* 184 */     u.set(ll, kk, 0.0D);
/*     */     
/* 186 */     double alpha = 0.0D, beta = 0.0D;
/*     */ 
/*     */     
/* 189 */     alpha = beta = Math.sqrt(0.5D);
/*     */     
/* 191 */     double mu = 2.0D * matrix.get(k, l) / (matrix.get(k, k) - matrix.get(l, l));
/* 192 */     mu = 1.0D / Math.sqrt(1.0D + mu * mu);
/* 193 */     alpha = Math.sqrt(0.5D * (1.0D + mu));
/* 194 */     beta = Math.signum(mu) * Math.sqrt(0.5D * (1.0D - mu));
/*     */ 
/*     */     
/* 197 */     u.set(k, k, alpha);
/* 198 */     u.set(l, l, alpha);
/* 199 */     u.set(k, l, -beta);
/* 200 */     u.set(l, k, beta);
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
/*     */   private Matrix[] decomposeNonSymmetricMatrix(Matrix matrix) {
/* 218 */     Matrix A = matrix.copy();
/* 219 */     int n = matrix.columns();
/*     */     
/* 221 */     SparseMatrix sparseMatrix = SparseMatrix.identity(n);
/* 222 */     DenseVector denseVector1 = DenseVector.zero(n);
/* 223 */     DenseVector denseVector2 = DenseVector.zero(n);
/*     */     
/* 225 */     Matrix h = A.copy();
/* 226 */     DenseVector denseVector3 = DenseVector.zero(n);
/*     */ 
/*     */     
/* 229 */     orthes(h, (Matrix)sparseMatrix, (Vector)denseVector3);
/*     */ 
/*     */     
/* 232 */     hqr2(h, (Matrix)sparseMatrix, (Vector)denseVector1, (Vector)denseVector2);
/*     */     
/* 234 */     Matrix dd = matrix.blankOfShape(n, n);
/* 235 */     for (int i = 0; i < n; i++) {
/* 236 */       dd.set(i, i, denseVector1.get(i));
/*     */       
/* 238 */       if (denseVector2.get(i) > 0.0D) {
/* 239 */         dd.set(i, i + 1, denseVector2.get(i));
/* 240 */       } else if (denseVector2.get(i) < 0.0D) {
/* 241 */         dd.set(i, i - 1, denseVector2.get(i));
/*     */       } 
/*     */     } 
/*     */     
/* 245 */     return new Matrix[] { (Matrix)sparseMatrix, dd };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void orthes(Matrix h, Matrix v, Vector ort) {
/* 255 */     int n = ort.length();
/* 256 */     int low = 0;
/* 257 */     int high = n - 1;
/*     */     int m;
/* 259 */     for (m = low + 1; m <= high - 1; m++) {
/*     */ 
/*     */ 
/*     */       
/* 263 */       double scale = 0.0D;
/*     */       
/* 265 */       for (int i = m; i <= high; i++) {
/* 266 */         scale += Math.abs(h.get(i, m - 1));
/*     */       }
/*     */       
/* 269 */       if (scale != 0.0D) {
/*     */ 
/*     */ 
/*     */         
/* 273 */         double hh = 0.0D;
/* 274 */         for (int k = high; k >= m; k--) {
/* 275 */           ort.set(k, h.get(k, m - 1) / scale);
/* 276 */           hh += ort.get(k) * ort.get(k);
/*     */         } 
/*     */         
/* 279 */         double g = Math.sqrt(hh);
/*     */         
/* 281 */         if (ort.get(m) > Matrices.EPS) {
/* 282 */           g = -g;
/*     */         }
/*     */         
/* 285 */         hh -= ort.get(m) * g;
/* 286 */         ort.updateAt(m, Vectors.asMinusFunction(g));
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 291 */         for (int j = m; j < n; j++) {
/* 292 */           double f = 0.0D; int i2;
/* 293 */           for (i2 = high; i2 >= m; i2--) {
/* 294 */             f += ort.get(i2) * h.get(i2, j);
/*     */           }
/* 296 */           f /= hh;
/* 297 */           for (i2 = m; i2 <= high; i2++) {
/* 298 */             h.updateAt(i2, j, Matrices.asMinusFunction(f * ort.get(i2)));
/*     */           }
/*     */         } 
/*     */         
/* 302 */         for (int i1 = 0; i1 <= high; i1++) {
/* 303 */           double f = 0.0D; int i2;
/* 304 */           for (i2 = high; i2 >= m; i2--) {
/* 305 */             f += ort.get(i2) * h.get(i1, i2);
/*     */           }
/* 307 */           f /= hh;
/* 308 */           for (i2 = m; i2 <= high; i2++) {
/* 309 */             h.updateAt(i1, i2, Matrices.asMinusFunction(f * ort.get(i2)));
/*     */           }
/*     */         } 
/* 312 */         ort.set(m, scale * ort.get(m));
/* 313 */         h.set(m, m - 1, scale * g);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 319 */     for (m = high - 1; m >= low + 1; m--) {
/* 320 */       if (Math.abs(h.get(m, m - 1)) > Matrices.EPS) {
/* 321 */         for (int i = m + 1; i <= high; i++) {
/* 322 */           ort.set(i, h.get(i, m - 1));
/*     */         }
/* 324 */         for (int j = m; j <= high; j++) {
/* 325 */           double g = 0.0D; int k;
/* 326 */           for (k = m; k <= high; k++) {
/* 327 */             g += ort.get(k) * v.get(k, j);
/*     */           }
/*     */           
/* 330 */           g = g / ort.get(m) / h.get(m, m - 1);
/* 331 */           for (k = m; k <= high; k++) {
/* 332 */             v.updateAt(k, j, Matrices.asPlusFunction(g * ort.get(k)));
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void hqr2(Matrix H, Matrix V, Vector d, Vector e) {
/* 350 */     int nn = e.length();
/* 351 */     int n = nn - 1;
/* 352 */     int low = 0;
/* 353 */     int high = nn - 1;
/* 354 */     double eps = Math.pow(2.0D, -52.0D);
/* 355 */     double exshift = 0.0D;
/* 356 */     double p = 0.0D, q = 0.0D, r = 0.0D, s = 0.0D, z = 0.0D;
/*     */ 
/*     */ 
/*     */     
/* 360 */     double norm = 0.0D;
/* 361 */     for (int i = 0; i < nn; i++) {
/* 362 */       if ((((i < low) ? 1 : 0) | ((i > high) ? 1 : 0)) != 0) {
/* 363 */         d.set(i, H.get(i, i));
/* 364 */         e.set(i, 0.0D);
/*     */       } 
/* 366 */       for (int m = Math.max(i - 1, 0); m < nn; m++) {
/* 367 */         norm += Math.abs(H.get(i, m));
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 373 */     int iter = 0;
/* 374 */     while (n >= low) {
/*     */ 
/*     */ 
/*     */       
/* 378 */       int l = n;
/* 379 */       while (l > low) {
/* 380 */         s = Math.abs(H.get(l - 1, l - 1)) + Math.abs(H.get(l, l));
/*     */ 
/*     */         
/* 383 */         if (s == 0.0D) {
/* 384 */           s = norm;
/*     */         }
/* 386 */         if (Math.abs(H.get(l, l - 1)) < eps * s) {
/*     */           break;
/*     */         }
/* 389 */         l--;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 395 */       if (l == n) {
/* 396 */         H.updateAt(n, n, Matrices.asPlusFunction(exshift));
/* 397 */         d.set(n, H.get(n, n));
/* 398 */         e.set(n, 0.0D);
/* 399 */         n--;
/* 400 */         iter = 0;
/*     */         
/*     */         continue;
/*     */       } 
/* 404 */       if (l == n - 1) {
/* 405 */         double d1 = H.get(n, n - 1) * H.get(n - 1, n);
/* 406 */         p = (H.get(n - 1, n - 1) - H.get(n, n)) / 2.0D;
/* 407 */         q = p * p + d1;
/* 408 */         z = Math.sqrt(Math.abs(q));
/* 409 */         H.updateAt(n, n, Matrices.asPlusFunction(exshift));
/* 410 */         H.updateAt(n - 1, n - 1, Matrices.asPlusFunction(exshift));
/* 411 */         double d2 = H.get(n, n);
/*     */ 
/*     */ 
/*     */         
/* 415 */         if (q >= 0.0D) {
/* 416 */           if (p >= 0.0D) {
/* 417 */             z = p + z;
/*     */           } else {
/* 419 */             z = p - z;
/*     */           } 
/* 421 */           d.set(n - 1, d2 + z);
/* 422 */           d.set(n, d.get(n - 1));
/* 423 */           if (z != 0.0D) {
/* 424 */             d.set(n, d2 - d1 / z);
/*     */           }
/* 426 */           e.set(n - 1, 0.0D);
/* 427 */           e.set(n, 0.0D);
/* 428 */           d2 = H.get(n, n - 1);
/* 429 */           s = Math.abs(d2) + Math.abs(z);
/* 430 */           p = d2 / s;
/* 431 */           q = z / s;
/* 432 */           r = Math.sqrt(p * p + q * q);
/* 433 */           p /= r;
/* 434 */           q /= r;
/*     */ 
/*     */ 
/*     */           
/* 438 */           for (int i4 = n - 1; i4 < nn; i4++) {
/* 439 */             z = H.get(n - 1, i4);
/* 440 */             H.set(n - 1, i4, q * z + p * H.get(n, i4));
/* 441 */             H.set(n, i4, q * H.get(n, i4) - p * z);
/*     */           } 
/*     */           
/*     */           int i3;
/*     */           
/* 446 */           for (i3 = 0; i3 <= n; i3++) {
/* 447 */             z = H.get(i3, n - 1);
/* 448 */             H.set(i3, n - 1, q * z + p * H.get(i3, n));
/* 449 */             H.set(i3, n, q * H.get(i3, n) - p * z);
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 454 */           for (i3 = low; i3 <= high; i3++) {
/* 455 */             z = V.get(i3, n - 1);
/* 456 */             V.set(i3, n - 1, q * z + p * V.get(i3, n));
/* 457 */             V.set(i3, n, q * V.get(i3, n) - p * z);
/*     */           }
/*     */         
/*     */         }
/*     */         else {
/*     */           
/* 463 */           d.set(n - 1, d2 + p);
/* 464 */           d.set(n, d2 + p);
/* 465 */           e.set(n - 1, z);
/* 466 */           e.set(n, -z);
/*     */         } 
/* 468 */         n -= 2;
/* 469 */         iter = 0;
/*     */ 
/*     */ 
/*     */         
/*     */         continue;
/*     */       } 
/*     */ 
/*     */       
/* 477 */       double x = H.get(n, n);
/* 478 */       double y = 0.0D;
/* 479 */       double w = 0.0D;
/* 480 */       if (l < n) {
/* 481 */         y = H.get(n - 1, n - 1);
/* 482 */         w = H.get(n, n - 1) * H.get(n - 1, n);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 487 */       if (iter == 10) {
/* 488 */         exshift += x;
/* 489 */         for (int i3 = low; i3 <= n; i3++) {
/* 490 */           H.updateAt(i3, i3, Matrices.asMinusFunction(x));
/*     */         }
/* 492 */         s = Math.abs(H.get(n, n - 1)) + Math.abs(H.get(n - 1, n - 2));
/*     */         
/* 494 */         x = y = 0.75D * s;
/* 495 */         w = -0.4375D * s * s;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 500 */       if (iter == 30) {
/* 501 */         s = (y - x) / 2.0D;
/* 502 */         s = s * s + w;
/* 503 */         if (s > 0.0D) {
/* 504 */           s = Math.sqrt(s);
/* 505 */           if (y < x) {
/* 506 */             s = -s;
/*     */           }
/* 508 */           s = x - w / ((y - x) / 2.0D + s);
/* 509 */           for (int i3 = low; i3 <= n; i3++) {
/* 510 */             H.updateAt(i3, i3, Matrices.asMinusFunction(s));
/*     */           }
/* 512 */           exshift += s;
/* 513 */           x = y = w = 0.964D;
/*     */         } 
/*     */       } 
/*     */       
/* 517 */       iter++;
/*     */ 
/*     */ 
/*     */       
/* 521 */       int m = n - 2;
/* 522 */       while (m >= l) {
/* 523 */         z = H.get(m, m);
/* 524 */         r = x - z;
/* 525 */         s = y - z;
/* 526 */         p = (r * s - w) / H.get(m + 1, m) + H.get(m, m + 1);
/*     */         
/* 528 */         q = H.get(m + 1, m + 1) - z - r - s;
/* 529 */         r = H.get(m + 2, m + 1);
/* 530 */         s = Math.abs(p) + Math.abs(q) + Math.abs(r);
/* 531 */         p /= s;
/* 532 */         q /= s;
/* 533 */         r /= s;
/* 534 */         if (m == l) {
/*     */           break;
/*     */         }
/* 537 */         if (Math.abs(H.get(m, m - 1)) * (Math.abs(q) + Math.abs(r)) < eps * Math.abs(p) * (Math.abs(H.get(m - 1, m - 1)) + Math.abs(z) + Math.abs(H.get(m + 1, m + 1)))) {
/*     */           break;
/*     */         }
/*     */ 
/*     */         
/* 542 */         m--;
/*     */       } 
/*     */       
/* 545 */       for (int i2 = m + 2; i2 <= n; i2++) {
/* 546 */         H.set(i2, i2 - 2, 0.0D);
/* 547 */         if (i2 > m + 2) {
/* 548 */           H.set(i2, i2 - 3, 0.0D);
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 554 */       for (int i1 = m; i1 <= n - 1; i1++) {
/* 555 */         boolean notlast = (i1 != n - 1);
/* 556 */         if (i1 != m) {
/* 557 */           p = H.get(i1, i1 - 1);
/* 558 */           q = H.get(i1 + 1, i1 - 1);
/* 559 */           r = notlast ? H.get(i1 + 2, i1 - 1) : 0.0D;
/* 560 */           x = Math.abs(p) + Math.abs(q) + Math.abs(r);
/* 561 */           if (x == 0.0D) {
/*     */             continue;
/*     */           }
/* 564 */           p /= x;
/* 565 */           q /= x;
/* 566 */           r /= x;
/*     */         } 
/*     */         
/* 569 */         s = Math.sqrt(p * p + q * q + r * r);
/* 570 */         if (p < 0.0D) {
/* 571 */           s = -s;
/*     */         }
/* 573 */         if (s != 0.0D) {
/* 574 */           if (i1 != m) {
/* 575 */             H.set(i1, i1 - 1, -s * x);
/* 576 */           } else if (l != m) {
/* 577 */             H.updateAt(i1, i1 - 1, Matrices.INV_FUNCTION);
/*     */           } 
/* 579 */           p += s;
/* 580 */           x = p / s;
/* 581 */           y = q / s;
/* 582 */           z = r / s;
/* 583 */           q /= p;
/* 584 */           r /= p;
/*     */ 
/*     */ 
/*     */           
/* 588 */           for (int i4 = i1; i4 < nn; i4++) {
/* 589 */             p = H.get(i1, i4) + q * H.get(i1 + 1, i4);
/* 590 */             if (notlast) {
/* 591 */               p += r * H.get(i1 + 2, i4);
/* 592 */               H.updateAt(i1 + 2, i4, Matrices.asMinusFunction(p * z));
/*     */             } 
/*     */             
/* 595 */             H.updateAt(i1, i4, Matrices.asMinusFunction(p * x));
/* 596 */             H.updateAt(i1 + 1, i4, Matrices.asMinusFunction(p * y));
/*     */           } 
/*     */           
/*     */           int i3;
/*     */           
/* 601 */           for (i3 = 0; i3 <= Math.min(n, i1 + 3); i3++) {
/* 602 */             p = x * H.get(i3, i1) + y * H.get(i3, i1 + 1);
/*     */             
/* 604 */             if (notlast) {
/* 605 */               p += z * H.get(i3, i1 + 2);
/* 606 */               H.updateAt(i3, i1 + 2, Matrices.asMinusFunction(p * r));
/*     */             } 
/*     */             
/* 609 */             H.updateAt(i3, i1, Matrices.asMinusFunction(p));
/* 610 */             H.updateAt(i3, i1 + 1, Matrices.asMinusFunction(p * q));
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 615 */           for (i3 = low; i3 <= high; i3++) {
/* 616 */             p = x * V.get(i3, i1) + y * V.get(i3, i1 + 1);
/*     */             
/* 618 */             if (notlast) {
/* 619 */               p += z * V.get(i3, i1 + 2);
/* 620 */               V.updateAt(i3, i1 + 2, Matrices.asMinusFunction(p * r));
/*     */             } 
/*     */             
/* 623 */             V.updateAt(i3, i1, Matrices.asMinusFunction(p));
/* 624 */             V.updateAt(i3, i1 + 1, Matrices.asMinusFunction(p * q));
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/*     */         continue;
/*     */       } 
/*     */     } 
/*     */     
/* 633 */     if (norm == 0.0D) {
/*     */       return;
/*     */     }
/*     */     
/* 637 */     for (n = nn - 1; n >= 0; n--) {
/* 638 */       p = d.get(n);
/* 639 */       q = e.get(n);
/*     */ 
/*     */ 
/*     */       
/* 643 */       if (q == 0.0D) {
/* 644 */         int l = n;
/* 645 */         H.set(n, n, 1.0D);
/* 646 */         for (int m = n - 1; m >= 0; m--) {
/* 647 */           double w = H.get(m, m) - p;
/* 648 */           r = 0.0D; int i1;
/* 649 */           for (i1 = l; i1 <= n; i1++) {
/* 650 */             r += H.get(m, i1) * H.get(i1, n);
/*     */           }
/* 652 */           if (e.get(m) < 0.0D) {
/* 653 */             z = w;
/* 654 */             s = r;
/*     */           } else {
/* 656 */             l = m;
/* 657 */             if (e.get(m) == 0.0D) {
/* 658 */               if (w != 0.0D) {
/* 659 */                 H.set(m, n, -r / w);
/*     */               } else {
/* 661 */                 H.set(m, n, -r / eps * norm);
/*     */               }
/*     */             
/*     */             }
/*     */             else {
/*     */               
/* 667 */               double x = H.get(m, m + 1);
/* 668 */               double y = H.get(m + 1, m);
/* 669 */               q = (d.get(m) - p) * (d.get(m) - p) + e.get(m) * e.get(m);
/*     */               
/* 671 */               double d1 = (x * s - z * r) / q;
/* 672 */               H.set(m, n, d1);
/* 673 */               if (Math.abs(x) > Math.abs(z)) {
/* 674 */                 H.set(m + 1, n, (-r - w * d1) / x);
/*     */               } else {
/* 676 */                 H.set(m + 1, n, (-s - y * d1) / z);
/*     */               } 
/*     */             } 
/*     */ 
/*     */ 
/*     */             
/* 682 */             double t = Math.abs(H.get(m, n));
/* 683 */             if (eps * t * t > 1.0D) {
/* 684 */               for (i1 = m; i1 <= n; i1++) {
/* 685 */                 H.updateAt(i1, n, Matrices.asDivFunction(t));
/*     */               }
/*     */             }
/*     */           }
/*     */         
/*     */         }
/*     */       
/*     */       }
/* 693 */       else if (q < 0.0D) {
/* 694 */         int l = n - 1;
/*     */ 
/*     */ 
/*     */         
/* 698 */         if (Math.abs(H.get(n, n - 1)) > Math.abs(H.get(n - 1, n))) {
/*     */           
/* 700 */           H.set(n - 1, n - 1, q / H.get(n, n - 1));
/* 701 */           H.set(n - 1, n, -(H.get(n, n) - p) / H.get(n, n - 1));
/*     */         } else {
/*     */           
/* 704 */           double[] cdiv = cdiv(0.0D, -H.get(n - 1, n), H.get(n - 1, n - 1) - p, q);
/*     */           
/* 706 */           H.set(n - 1, n - 1, cdiv[0]);
/* 707 */           H.set(n - 1, n, cdiv[1]);
/*     */         } 
/* 709 */         H.set(n, n - 1, 0.0D);
/* 710 */         H.set(n, n, 1.0D);
/* 711 */         for (int m = n - 2; m >= 0; m--) {
/*     */           
/* 713 */           double ra = 0.0D;
/* 714 */           double sa = 0.0D; int i1;
/* 715 */           for (i1 = l; i1 <= n; i1++) {
/* 716 */             ra += H.get(m, i1) * H.get(i1, n - 1);
/* 717 */             sa += H.get(m, i1) * H.get(i1, n);
/*     */           } 
/* 719 */           double w = H.get(m, m) - p;
/*     */           
/* 721 */           if (e.get(m) < 0.0D) {
/* 722 */             z = w;
/* 723 */             r = ra;
/* 724 */             s = sa;
/*     */           } else {
/* 726 */             l = m;
/* 727 */             if (e.get(m) == 0.0D) {
/* 728 */               double[] cdiv = cdiv(-ra, -sa, w, q);
/* 729 */               H.set(m, n - 1, cdiv[0]);
/* 730 */               H.set(m, n, cdiv[1]);
/*     */             
/*     */             }
/*     */             else {
/*     */               
/* 735 */               double x = H.get(m, m + 1);
/* 736 */               double y = H.get(m + 1, m);
/* 737 */               double vr = (d.get(m) - p) * (d.get(m) - p) + e.get(m) * e.get(m) - q * q;
/*     */               
/* 739 */               double vi = (d.get(m) - p) * 2.0D * q;
/* 740 */               if ((((vr == 0.0D) ? 1 : 0) & ((vi == 0.0D) ? 1 : 0)) != 0) {
/* 741 */                 vr = eps * norm * (Math.abs(w) + Math.abs(q) + Math.abs(x) + Math.abs(y) + Math.abs(z));
/*     */               }
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 747 */               double[] cdiv = cdiv(x * r - z * ra + q * sa, x * s - z * sa - q * ra, vr, vi);
/*     */               
/* 749 */               H.set(m, n - 1, cdiv[0]);
/* 750 */               H.set(m, n, cdiv[1]);
/* 751 */               if (Math.abs(x) > Math.abs(z) + Math.abs(q)) {
/* 752 */                 H.set(m + 1, n - 1, (-ra - w * H.get(m, n - 1) + q * H.get(m, n)) / x);
/*     */ 
/*     */                 
/* 755 */                 H.set(m + 1, n, (-sa - w * H.get(m, n) - q * H.get(m, n - 1)) / x);
/*     */               }
/*     */               else {
/*     */                 
/* 759 */                 cdiv = cdiv(-r - y * H.get(m, n - 1), -s - y * H.get(m, n), z, q);
/*     */ 
/*     */                 
/* 762 */                 H.set(m + 1, n - 1, cdiv[0]);
/* 763 */                 H.set(m + 1, n, cdiv[1]);
/*     */               } 
/*     */             } 
/*     */ 
/*     */ 
/*     */             
/* 769 */             double t = Math.max(Math.abs(H.get(m, n - 1)), Math.abs(H.get(m, n)));
/*     */             
/* 771 */             if (eps * t * t > 1.0D) {
/* 772 */               for (i1 = m; i1 <= n; i1++) {
/* 773 */                 H.updateAt(i1, n - 1, Matrices.asDivFunction(t));
/* 774 */                 H.updateAt(i1, n, Matrices.asDivFunction(t));
/*     */               } 
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 784 */     for (int k = 0; k < nn; k++) {
/* 785 */       if ((((k < low) ? 1 : 0) | ((k > high) ? 1 : 0)) != 0) {
/* 786 */         for (int m = k; m < nn; m++) {
/* 787 */           V.set(k, m, H.get(k, m));
/*     */         }
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 794 */     for (int j = nn - 1; j >= low; j--) {
/* 795 */       for (int m = low; m <= high; m++) {
/* 796 */         z = 0.0D;
/* 797 */         for (int i1 = low; i1 <= Math.min(j, high); i1++) {
/* 798 */           z += V.get(m, i1) * H.get(i1, j);
/*     */         }
/* 800 */         V.set(m, j, z);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private double[] cdiv(double xr, double xi, double yr, double yi) {
/*     */     double cdivr;
/*     */     double cdivi;
/* 809 */     if (Math.abs(yr) > Math.abs(yi)) {
/* 810 */       double r = yi / yr;
/* 811 */       double d = yr + r * yi;
/* 812 */       cdivr = (xr + r * xi) / d;
/* 813 */       cdivi = (xi - r * xr) / d;
/*     */     } else {
/* 815 */       double r = yr / yi;
/* 816 */       double d = yi + r * yr;
/* 817 */       cdivr = (r * xr + xi) / d;
/* 818 */       cdivi = (r * xi - xr) / d;
/*     */     } 
/*     */     
/* 821 */     return new double[] { cdivr, cdivi };
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/la4j/decomposition/EigenDecompositor.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */