/*     */ package org.la4j.decomposition;
/*     */ 
/*     */ import org.la4j.Matrices;
/*     */ import org.la4j.Matrix;
/*     */ import org.la4j.Vectors;
/*     */ import org.la4j.vector.DenseVector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SingularValueDecompositor
/*     */   extends AbstractDecompositor
/*     */   implements MatrixDecompositor
/*     */ {
/*     */   public SingularValueDecompositor(Matrix matrix) {
/*  38 */     super(matrix);
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
/*     */   public Matrix[] decompose() {
/*  57 */     Matrix a = this.matrix.copy();
/*     */     
/*  59 */     int n = Math.min(a.rows(), a.columns());
/*     */     
/*  61 */     Matrix u = this.matrix.blankOfShape(a.rows(), n);
/*  62 */     Matrix s = this.matrix.blankOfShape(a.columns(), a.columns());
/*  63 */     Matrix v = this.matrix.blankOfShape(a.columns(), a.columns());
/*     */     
/*  65 */     DenseVector denseVector1 = DenseVector.zero(a.columns());
/*  66 */     DenseVector denseVector2 = DenseVector.zero(a.rows());
/*     */     
/*  68 */     int nct = Math.min(a.rows() - 1, a.columns());
/*  69 */     int nrt = Math.max(0, Math.min(a.columns() - 2, a.rows()));
/*     */     
/*  71 */     for (int k = 0; k < Math.max(nct, nrt); k++) {
/*     */       
/*  73 */       if (k < nct) {
/*     */         
/*  75 */         s.set(k, k, 0.0D);
/*     */         
/*  77 */         for (int i1 = k; i1 < a.rows(); i1++) {
/*  78 */           s.set(k, k, hypot(s.get(k, k), a.get(i1, k)));
/*     */         }
/*     */         
/*  81 */         if (Math.abs(s.get(k, k)) > Matrices.EPS) {
/*     */           
/*  83 */           if (a.get(k, k) < 0.0D) {
/*  84 */             s.updateAt(k, k, Matrices.INV_FUNCTION);
/*     */           }
/*     */           
/*  87 */           double skk = s.get(k, k);
/*  88 */           for (int i2 = k; i2 < a.rows(); i2++) {
/*  89 */             a.updateAt(i2, k, Matrices.asDivFunction(skk));
/*     */           }
/*     */           
/*  92 */           a.updateAt(k, k, Matrices.INC_FUNCTION);
/*     */         } 
/*     */         
/*  95 */         s.updateAt(k, k, Matrices.INV_FUNCTION);
/*     */       } 
/*     */       
/*  98 */       for (int m = k + 1; m < a.columns(); m++) {
/*     */         
/* 100 */         if ((((k < nct) ? 1 : 0) & ((Math.abs(s.get(k, k)) > Matrices.EPS) ? 1 : 0)) != 0) {
/*     */           
/* 102 */           double t = 0.0D;
/*     */           int i1;
/* 104 */           for (i1 = k; i1 < a.rows(); i1++) {
/* 105 */             t += a.get(i1, k) * a.get(i1, m);
/*     */           }
/*     */           
/* 108 */           t = -t / a.get(k, k);
/*     */           
/* 110 */           for (i1 = k; i1 < a.rows(); i1++) {
/* 111 */             a.updateAt(i1, m, Matrices.asPlusFunction(t * a.get(i1, k)));
/*     */           }
/*     */         } 
/*     */         
/* 115 */         denseVector1.set(m, a.get(k, m));
/*     */       } 
/*     */       
/* 118 */       if (k < nct)
/*     */       {
/* 120 */         for (int i1 = k; i1 < a.rows(); i1++) {
/* 121 */           u.set(i1, k, a.get(i1, k));
/*     */         }
/*     */       }
/*     */ 
/*     */       
/* 126 */       if (k < nrt) {
/*     */         
/* 128 */         denseVector1.set(k, 0.0D);
/*     */         int i1;
/* 130 */         for (i1 = k + 1; i1 < a.columns(); i1++) {
/* 131 */           denseVector1.set(k, hypot(denseVector1.get(k), denseVector1.get(i1)));
/*     */         }
/*     */         
/* 134 */         if (Math.abs(denseVector1.get(k)) > Matrices.EPS) {
/*     */           
/* 136 */           if (denseVector1.get(k + 1) < 0.0D)
/*     */           {
/* 138 */             denseVector1.updateAt(k, Vectors.INV_FUNCTION);
/*     */           }
/*     */           
/* 141 */           double ek = denseVector1.get(k);
/* 142 */           for (int i2 = k + 1; i2 < a.columns(); i2++) {
/* 143 */             denseVector1.updateAt(i2, Vectors.asDivFunction(ek));
/*     */           }
/*     */           
/* 146 */           denseVector1.updateAt(k + 1, Vectors.INC_FUNCTION);
/*     */         } 
/*     */         
/* 149 */         denseVector1.updateAt(k, Vectors.INV_FUNCTION);
/*     */         
/* 151 */         if (k + 1 < a.rows() && Math.abs(denseVector1.get(k)) > Matrices.EPS) {
/*     */           
/* 153 */           for (i1 = k + 1; i1 < a.rows(); i1++) {
/* 154 */             denseVector2.set(i1, 0.0D);
/*     */           }
/*     */           int i2;
/* 157 */           for (i2 = k + 1; i2 < a.columns(); i2++) {
/* 158 */             for (int i3 = k + 1; i3 < a.rows(); i3++) {
/* 159 */               denseVector2.updateAt(i3, Vectors.asPlusFunction(denseVector1.get(i2) * a.get(i3, i2)));
/*     */             }
/*     */           } 
/*     */ 
/*     */           
/* 164 */           for (i2 = k + 1; i2 < a.columns(); i2++) {
/*     */             
/* 166 */             double t = -denseVector1.get(i2) / denseVector1.get(k + 1);
/*     */             
/* 168 */             for (int i3 = k + 1; i3 < a.rows(); i3++) {
/* 169 */               a.updateAt(i3, i2, Matrices.asPlusFunction(t * denseVector2.get(i3)));
/*     */             }
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 175 */         for (i1 = k + 1; i1 < a.columns(); i1++) {
/* 176 */           v.set(i1, k, denseVector1.get(i1));
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 181 */     int p = Math.min(a.columns(), a.rows() + 1);
/*     */     
/* 183 */     if (nct < a.columns()) {
/* 184 */       s.set(nct, nct, a.get(nct, nct));
/*     */     }
/*     */     
/* 187 */     if (a.rows() < p) {
/* 188 */       s.set(p - 1, p - 1, 0.0D);
/*     */     }
/*     */     
/* 191 */     if (nrt + 1 < p) {
/* 192 */       denseVector1.set(nrt, a.get(nrt, p - 1));
/*     */     }
/*     */     
/* 195 */     denseVector1.set(p - 1, 0.0D);
/*     */     
/* 197 */     for (int j = nct; j < n; j++) {
/*     */       
/* 199 */       for (int m = 0; m < a.rows(); m++) {
/* 200 */         u.set(m, j, 0.0D);
/*     */       }
/*     */       
/* 203 */       u.set(j, j, 1.0D);
/*     */     } 
/*     */     int i;
/* 206 */     for (i = nct - 1; i >= 0; i--) {
/*     */       
/* 208 */       if (Math.abs(s.get(i, i)) > Matrices.EPS) {
/*     */         
/* 210 */         for (int i1 = i + 1; i1 < n; i1++) {
/*     */           
/* 212 */           double t = 0.0D; int i2;
/* 213 */           for (i2 = i; i2 < a.rows(); i2++) {
/* 214 */             t += u.get(i2, i) * u.get(i2, i1);
/*     */           }
/*     */           
/* 217 */           t = -t / u.get(i, i);
/*     */           
/* 219 */           for (i2 = i; i2 < a.rows(); i2++) {
/* 220 */             u.updateAt(i2, i1, Matrices.asPlusFunction(t * u.get(i2, i)));
/*     */           }
/*     */         } 
/*     */         int m;
/* 224 */         for (m = i; m < a.rows(); m++) {
/* 225 */           u.updateAt(m, i, Matrices.INV_FUNCTION);
/*     */         }
/*     */         
/* 228 */         u.updateAt(i, i, Matrices.INC_FUNCTION);
/*     */         
/* 230 */         for (m = 0; m < i - 1; m++) {
/* 231 */           u.set(m, i, 0.0D);
/*     */         }
/*     */       }
/*     */       else {
/*     */         
/* 236 */         for (int m = 0; m < a.rows(); m++) {
/* 237 */           u.set(m, i, 0.0D);
/*     */         }
/*     */         
/* 240 */         u.set(i, i, 1.0D);
/*     */       } 
/*     */     } 
/*     */     
/* 244 */     for (i = n - 1; i >= 0; i--) {
/*     */       
/* 246 */       if ((((i < nrt) ? 1 : 0) & ((Math.abs(denseVector1.get(i)) > Matrices.EPS) ? 1 : 0)) != 0)
/*     */       {
/* 248 */         for (int i1 = i + 1; i1 < n; i1++) {
/*     */           
/* 250 */           double t = 0.0D;
/*     */           int i2;
/* 252 */           for (i2 = i + 1; i2 < a.columns(); i2++) {
/* 253 */             t += v.get(i2, i) * v.get(i2, i1);
/*     */           }
/*     */           
/* 256 */           t = -t / v.get(i + 1, i);
/*     */           
/* 258 */           for (i2 = i + 1; i2 < a.columns(); i2++) {
/* 259 */             v.updateAt(i2, i1, Matrices.asPlusFunction(t * v.get(i2, i)));
/*     */           }
/*     */         } 
/*     */       }
/*     */       
/* 264 */       for (int m = 0; m < a.columns(); m++) {
/* 265 */         v.set(m, i, 0.0D);
/*     */       }
/*     */       
/* 268 */       v.set(i, i, 1.0D);
/*     */     } 
/*     */     
/* 271 */     int pp = p - 1;
/* 272 */     int iter = 0;
/* 273 */     double eps = Math.pow(2.0D, -52.0D);
/* 274 */     double tiny = Math.pow(2.0D, -966.0D);
/*     */     
/* 276 */     while (p > 0) {
/*     */       int kase; double f, scale; int i1;
/*     */       double sp, spm1, epm1, sk, ek, b, c, shift, d1, g;
/*     */       int i2, m;
/* 280 */       for (m = p - 2; m >= -1 && 
/* 281 */         m != -1; m--) {
/*     */ 
/*     */         
/* 284 */         if (Math.abs(denseVector1.get(m)) <= tiny + eps * (Math.abs(s.get(m, m)) + Math.abs(s.get(m + 1, m + 1)))) {
/*     */ 
/*     */           
/* 287 */           denseVector1.set(m, 0.0D);
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/* 292 */       if (m == p - 2) {
/*     */         
/* 294 */         kase = 4;
/*     */       } else {
/*     */         int ks;
/*     */ 
/*     */ 
/*     */         
/* 300 */         for (ks = p - 1; ks >= m; ks--) {
/*     */           
/* 302 */           if (ks == m) {
/*     */             break;
/*     */           }
/* 305 */           double t = ((ks != p) ? Math.abs(denseVector1.get(ks)) : 0.0D) + ((ks != m + 1) ? Math.abs(denseVector1.get(ks - 1)) : 0.0D);
/*     */ 
/*     */ 
/*     */           
/* 309 */           if (Math.abs(s.get(ks, ks)) <= tiny + eps * t) {
/* 310 */             s.set(ks, ks, 0.0D);
/*     */             
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
/*     */       
/* 325 */       m++;
/*     */       
/* 327 */       switch (kase) {
/*     */         
/*     */         case 1:
/* 330 */           f = denseVector1.get(p - 2);
/* 331 */           denseVector1.set(p - 2, 0.0D);
/*     */           
/* 333 */           for (i1 = p - 2; i1 >= m; i1--) {
/*     */             
/* 335 */             double t = hypot(s.get(i1, i1), f);
/* 336 */             double cs = s.get(i1, i1) / t;
/* 337 */             double sn = f / t;
/*     */             
/* 339 */             s.set(i1, i1, t);
/*     */             
/* 341 */             if (i1 != m) {
/* 342 */               f = -sn * denseVector1.get(i1 - 1);
/* 343 */               denseVector1.set(i1 - 1, cs * denseVector1.get(i1 - 1));
/*     */             } 
/*     */             
/* 346 */             for (int i3 = 0; i3 < a.columns(); i3++) {
/* 347 */               t = cs * v.get(i3, i1) + sn * v.get(i3, p - 1);
/*     */               
/* 349 */               v.set(i3, p - 1, -sn * v.get(i3, i1) + cs * v.get(i3, p - 1));
/*     */ 
/*     */               
/* 352 */               v.set(i3, i1, t);
/*     */             } 
/*     */           } 
/*     */ 
/*     */ 
/*     */         
/*     */         case 2:
/* 359 */           f = denseVector1.get(m - 1);
/* 360 */           denseVector1.set(m - 1, 0.0D);
/*     */           
/* 362 */           for (i1 = m; i1 < p; i1++) {
/*     */             
/* 364 */             double t = hypot(s.get(i1, i1), f);
/* 365 */             double cs = s.get(i1, i1) / t;
/* 366 */             double sn = f / t;
/*     */             
/* 368 */             s.set(i1, i1, t);
/* 369 */             f = -sn * denseVector1.get(i1);
/* 370 */             denseVector1.set(i1, cs * denseVector1.get(i1));
/*     */             
/* 372 */             for (int i3 = 0; i3 < a.rows(); i3++) {
/* 373 */               t = cs * u.get(i3, i1) + sn * u.get(i3, m - 1);
/*     */               
/* 375 */               u.set(i3, m - 1, -sn * u.get(i3, i1) + cs * u.get(i3, m - 1));
/*     */ 
/*     */               
/* 378 */               u.set(i3, i1, t);
/*     */             } 
/*     */           } 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         case 3:
/* 386 */           scale = Math.max(Math.max(Math.max(Math.max(Math.abs(s.get(p - 1, p - 1)), Math.abs(s.get(p - 2, p - 2))), Math.abs(denseVector1.get(p - 2))), Math.abs(s.get(m, m))), Math.abs(denseVector1.get(m)));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 394 */           sp = s.get(p - 1, p - 1) / scale;
/* 395 */           spm1 = s.get(p - 2, p - 2) / scale;
/* 396 */           epm1 = denseVector1.get(p - 2) / scale;
/* 397 */           sk = s.get(m, m) / scale;
/* 398 */           ek = denseVector1.get(m) / scale;
/* 399 */           b = ((spm1 + sp) * (spm1 - sp) + epm1 * epm1) / 2.0D;
/* 400 */           c = sp * epm1 * sp * epm1;
/* 401 */           shift = 0.0D;
/*     */           
/* 403 */           if ((((b != 0.0D) ? 1 : 0) | ((c != 0.0D) ? 1 : 0)) != 0) {
/* 404 */             shift = Math.sqrt(b * b + c);
/* 405 */             if (b < 0.0D) {
/* 406 */               shift = -shift;
/*     */             }
/* 408 */             shift = c / (b + shift);
/*     */           } 
/*     */           
/* 411 */           d1 = (sk + sp) * (sk - sp) + shift;
/* 412 */           g = sk * ek;
/*     */           
/* 414 */           for (i2 = m; i2 < p - 1; i2++) {
/* 415 */             double t = hypot(d1, g);
/* 416 */             double cs = d1 / t;
/* 417 */             double sn = g / t;
/*     */             
/* 419 */             if (i2 != m) {
/* 420 */               denseVector1.set(i2 - 1, t);
/*     */             }
/*     */             
/* 423 */             d1 = cs * s.get(i2, i2) + sn * denseVector1.get(i2);
/* 424 */             denseVector1.set(i2, cs * denseVector1.get(i2) - sn * s.get(i2, i2));
/*     */             
/* 426 */             g = sn * s.get(i2 + 1, i2 + 1);
/* 427 */             s.set(i2 + 1, i2 + 1, cs * s.get(i2 + 1, i2 + 1));
/*     */             
/*     */             int i3;
/* 430 */             for (i3 = 0; i3 < a.columns(); i3++) {
/* 431 */               t = cs * v.get(i3, i2) + sn * v.get(i3, i2 + 1);
/*     */               
/* 433 */               v.set(i3, i2 + 1, -sn * v.get(i3, i2) + cs * v.get(i3, i2 + 1));
/*     */ 
/*     */               
/* 436 */               v.set(i3, i2, t);
/*     */             } 
/*     */             
/* 439 */             t = hypot(d1, g);
/* 440 */             cs = d1 / t;
/* 441 */             sn = g / t;
/* 442 */             s.set(i2, i2, t);
/* 443 */             d1 = cs * denseVector1.get(i2) + sn * s.get(i2 + 1, i2 + 1);
/* 444 */             s.set(i2 + 1, i2 + 1, -sn * denseVector1.get(i2) + cs * s.get(i2 + 1, i2 + 1));
/*     */ 
/*     */             
/* 447 */             g = sn * denseVector1.get(i2 + 1);
/* 448 */             denseVector1.updateAt(i2 + 1, Vectors.asMulFunction(cs));
/*     */             
/* 450 */             if (i2 < a.rows() - 1) {
/* 451 */               for (i3 = 0; i3 < a.rows(); i3++) {
/* 452 */                 t = cs * u.get(i3, i2) + sn * u.get(i3, i2 + 1);
/*     */                 
/* 454 */                 u.set(i3, i2 + 1, -sn * u.get(i3, i2) + cs * u.get(i3, i2 + 1));
/*     */ 
/*     */                 
/* 457 */                 u.set(i3, i2, t);
/*     */               } 
/*     */             }
/*     */           } 
/*     */           
/* 462 */           denseVector1.set(p - 2, d1);
/* 463 */           iter++;
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         case 4:
/* 469 */           if (s.get(m, m) <= 0.0D) {
/* 470 */             s.set(m, m, (s.get(m, m) < 0.0D) ? -s.get(m, m) : 0.0D);
/* 471 */             for (int i3 = 0; i3 <= pp; i3++) {
/* 472 */               v.updateAt(i3, m, Matrices.INV_FUNCTION);
/*     */             }
/*     */           } 
/*     */           
/* 476 */           while (m < pp) {
/*     */             
/* 478 */             if (s.get(m, m) >= s.get(m + 1, m + 1)) {
/*     */               break;
/*     */             }
/*     */             
/* 482 */             double t = s.get(m, m);
/* 483 */             s.set(m, m, s.get(m + 1, m + 1));
/* 484 */             s.set(m + 1, m + 1, t);
/*     */             
/* 486 */             if (m < a.columns() - 1) {
/* 487 */               for (int i3 = 0; i3 < a.columns(); i3++) {
/* 488 */                 t = v.get(i3, m + 1);
/* 489 */                 v.set(i3, m + 1, v.get(i3, m));
/* 490 */                 v.set(i3, m, t);
/*     */               } 
/*     */             }
/*     */             
/* 494 */             if (m < a.rows() - 1) {
/* 495 */               for (int i3 = 0; i3 < a.rows(); i3++) {
/* 496 */                 t = u.get(i3, m + 1);
/* 497 */                 u.set(i3, m + 1, u.get(i3, m));
/* 498 */                 u.set(i3, m, t);
/*     */               } 
/*     */             }
/*     */             
/* 502 */             m++;
/*     */           } 
/*     */           
/* 505 */           iter = 0;
/* 506 */           p--;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     } 
/* 516 */     return new Matrix[] { u, s.copyOfShape(n, a.columns()), v };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private double hypot(double a, double b) {
/*     */     double result;
/* 523 */     if (Math.abs(a) > Math.abs(b)) {
/* 524 */       result = b / a;
/* 525 */       result = Math.abs(a) * Math.sqrt(1.0D + result * result);
/* 526 */     } else if (b != 0.0D) {
/* 527 */       result = a / b;
/* 528 */       result = Math.abs(b) * Math.sqrt(1.0D + result * result);
/*     */     } else {
/* 530 */       result = 0.0D;
/*     */     } 
/*     */     
/* 533 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean applicableTo(Matrix matrix) {
/* 538 */     return true;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/la4j/decomposition/SingularValueDecompositor.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */