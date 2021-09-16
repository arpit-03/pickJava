/*     */ package org.apache.commons.math3.special;
/*     */ 
/*     */ import org.apache.commons.math3.analysis.UnivariateFunction;
/*     */ import org.apache.commons.math3.exception.ConvergenceException;
/*     */ import org.apache.commons.math3.exception.MathIllegalArgumentException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.util.FastMath;
/*     */ import org.apache.commons.math3.util.MathArrays;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BesselJ
/*     */   implements UnivariateFunction
/*     */ {
/*     */   private static final double PI2 = 0.6366197723675814D;
/*     */   private static final double TOWPI1 = 6.28125D;
/*     */   private static final double TWOPI2 = 0.001935307179586477D;
/*     */   private static final double TWOPI = 6.283185307179586D;
/*     */   private static final double ENTEN = 1.0E308D;
/*     */   private static final double ENSIG = 1.0E16D;
/*     */   private static final double RTNSIG = 1.0E-4D;
/*     */   private static final double ENMTEN = 8.9E-308D;
/*     */   private static final double X_MIN = 0.0D;
/*     */   private static final double X_MAX = 10000.0D;
/* 111 */   private static final double[] FACT = new double[] { 1.0D, 1.0D, 2.0D, 6.0D, 24.0D, 120.0D, 720.0D, 5040.0D, 40320.0D, 362880.0D, 3628800.0D, 3.99168E7D, 4.790016E8D, 6.2270208E9D, 8.71782912E10D, 1.307674368E12D, 2.0922789888E13D, 3.55687428096E14D, 6.402373705728E15D, 1.21645100408832E17D, 2.43290200817664E18D, 5.109094217170944E19D, 1.1240007277776077E21D, 2.585201673888498E22D, 6.204484017332394E23D };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final double order;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BesselJ(double order) {
/* 129 */     this.order = order;
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
/*     */   public double value(double x) throws MathIllegalArgumentException, ConvergenceException {
/* 143 */     return value(this.order, x);
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
/*     */   public static double value(double order, double x) throws MathIllegalArgumentException, ConvergenceException {
/* 157 */     int n = (int)order;
/* 158 */     double alpha = order - n;
/* 159 */     int nb = n + 1;
/* 160 */     BesselJResult res = rjBesl(x, alpha, nb);
/*     */     
/* 162 */     if (res.nVals >= nb)
/* 163 */       return res.vals[n]; 
/* 164 */     if (res.nVals < 0)
/* 165 */       throw new MathIllegalArgumentException(LocalizedFormats.BESSEL_FUNCTION_BAD_ARGUMENT, new Object[] { Double.valueOf(order), Double.valueOf(x) }); 
/* 166 */     if (FastMath.abs(res.vals[res.nVals - 1]) < 1.0E-100D) {
/* 167 */       return res.vals[n];
/*     */     }
/* 169 */     throw new ConvergenceException(LocalizedFormats.BESSEL_FUNCTION_FAILED_CONVERGENCE, new Object[] { Double.valueOf(order), Double.valueOf(x) });
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
/*     */   public static class BesselJResult
/*     */   {
/*     */     private final double[] vals;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final int nVals;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public BesselJResult(double[] b, int n) {
/* 207 */       this.vals = MathArrays.copyOf(b, b.length);
/* 208 */       this.nVals = n;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public double[] getVals() {
/* 215 */       return MathArrays.copyOf(this.vals, this.vals.length);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getnVals() {
/* 223 */       return this.nVals;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BesselJResult rjBesl(double x, double alpha, int nb) {
/* 246 */     double[] b = new double[nb];
/*     */     
/* 248 */     int ncalc = 0;
/* 249 */     double alpem = 0.0D;
/* 250 */     double alp2em = 0.0D;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 255 */     int magx = (int)x;
/* 256 */     if (nb > 0 && x >= 0.0D && x <= 10000.0D && alpha >= 0.0D && alpha < 1.0D) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 261 */       ncalc = nb;
/* 262 */       for (int i = 0; i < nb; i++) {
/* 263 */         b[i] = 0.0D;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 274 */       if (x < 1.0E-4D) {
/*     */ 
/*     */ 
/*     */         
/* 278 */         double tempa = 1.0D;
/* 279 */         alpem = 1.0D + alpha;
/* 280 */         double halfx = 0.0D;
/* 281 */         if (x > 8.9E-308D) {
/* 282 */           halfx = 0.5D * x;
/*     */         }
/* 284 */         if (alpha != 0.0D) {
/* 285 */           tempa = FastMath.pow(halfx, alpha) / alpha * Gamma.gamma(alpha);
/*     */         }
/*     */         
/* 288 */         double tempb = 0.0D;
/* 289 */         if (x + 1.0D > 1.0D) {
/* 290 */           tempb = -halfx * halfx;
/*     */         }
/* 292 */         b[0] = tempa + tempa * tempb / alpem;
/* 293 */         if (x != 0.0D && b[0] == 0.0D) {
/* 294 */           ncalc = 0;
/*     */         }
/* 296 */         if (nb != 1) {
/* 297 */           if (x <= 0.0D) {
/* 298 */             for (int n = 1; n < nb; n++) {
/* 299 */               b[n] = 0.0D;
/*     */             
/*     */             }
/*     */           }
/*     */           else {
/*     */             
/* 305 */             double tempc = halfx;
/* 306 */             double tover = (tempb != 0.0D) ? (8.9E-308D / tempb) : (1.78E-307D / x);
/* 307 */             for (int n = 1; n < nb; n++) {
/* 308 */               tempa /= alpem;
/* 309 */               alpem++;
/* 310 */               tempa *= tempc;
/* 311 */               if (tempa <= tover * alpem) {
/* 312 */                 tempa = 0.0D;
/*     */               }
/* 314 */               b[n] = tempa + tempa * tempb / alpem;
/* 315 */               if (b[n] == 0.0D && ncalc > n) {
/* 316 */                 ncalc = n;
/*     */               }
/*     */             } 
/*     */           } 
/*     */         }
/* 321 */       } else if (x > 25.0D && nb <= magx + 1) {
/*     */ 
/*     */ 
/*     */         
/* 325 */         double xc = FastMath.sqrt(0.6366197723675814D / x);
/* 326 */         double mul = 0.125D / x;
/* 327 */         double xin = mul * mul;
/* 328 */         int m = 0;
/* 329 */         if (x >= 130.0D) {
/* 330 */           m = 4;
/* 331 */         } else if (x >= 35.0D) {
/* 332 */           m = 8;
/*     */         } else {
/* 334 */           m = 11;
/*     */         } 
/*     */         
/* 337 */         double xm = 4.0D * m;
/*     */ 
/*     */ 
/*     */         
/* 341 */         double t = (int)(x / 6.283185307179586D + 0.5D);
/* 342 */         double z = x - t * 6.28125D - t * 0.001935307179586477D - (alpha + 0.5D) / 0.6366197723675814D;
/* 343 */         double vsin = FastMath.sin(z);
/* 344 */         double vcos = FastMath.cos(z);
/* 345 */         double gnu = 2.0D * alpha;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 351 */         for (int j = 1; j <= 2; j++) {
/* 352 */           double s = (xm - 1.0D - gnu) * (xm - 1.0D + gnu) * xin * 0.5D;
/* 353 */           t = (gnu - xm - 3.0D) * (gnu + xm - 3.0D);
/* 354 */           double capp = s * t / FACT[2 * m];
/* 355 */           double t1 = (gnu - xm + 1.0D) * (gnu + xm + 1.0D);
/* 356 */           double capq = s * t1 / FACT[2 * m + 1];
/* 357 */           double xk = xm;
/* 358 */           int k = 2 * m;
/* 359 */           t1 = t;
/*     */           
/* 361 */           for (int n = 2; n <= m; n++) {
/* 362 */             xk -= 4.0D;
/* 363 */             s = (xk - 1.0D - gnu) * (xk - 1.0D + gnu);
/* 364 */             t = (gnu - xk - 3.0D) * (gnu + xk - 3.0D);
/* 365 */             capp = (capp + 1.0D / FACT[k - 2]) * s * t * xin;
/* 366 */             capq = (capq + 1.0D / FACT[k - 1]) * s * t1 * xin;
/* 367 */             k -= 2;
/* 368 */             t1 = t;
/*     */           } 
/*     */           
/* 371 */           capp++;
/* 372 */           capq = (capq + 1.0D) * (gnu * gnu - 1.0D) * 0.125D / x;
/* 373 */           b[j - 1] = xc * (capp * vcos - capq * vsin);
/* 374 */           if (nb == 1) {
/* 375 */             return new BesselJResult(MathArrays.copyOf(b, b.length), ncalc);
/*     */           }
/*     */           
/* 378 */           t = vsin;
/* 379 */           vsin = -vcos;
/* 380 */           vcos = t;
/* 381 */           gnu += 2.0D;
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 387 */         if (nb > 2) {
/* 388 */           gnu = 2.0D * alpha + 2.0D;
/* 389 */           for (int k = 2; k < nb; k++) {
/* 390 */             b[k] = gnu * b[k - 1] / x - b[k - 2];
/* 391 */             gnu += 2.0D;
/*     */           }
/*     */         
/*     */         }
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 399 */         int nbmx = nb - magx;
/* 400 */         int n = magx + 1;
/* 401 */         int nstart = 0;
/* 402 */         int nend = 0;
/* 403 */         double en = 2.0D * (n + alpha);
/* 404 */         double plast = 1.0D;
/* 405 */         double p = en / x;
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 410 */         double test = 2.0E16D;
/* 411 */         boolean readyToInitialize = false;
/* 412 */         if (nbmx >= 3) {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 417 */           double tover = 1.0E292D;
/* 418 */           nstart = magx + 2;
/* 419 */           nend = nb - 1;
/* 420 */           en = 2.0D * ((nstart - 1) + alpha);
/*     */ 
/*     */           
/* 423 */           for (int k = nstart; k <= nend; k++) {
/* 424 */             n = k;
/* 425 */             en += 2.0D;
/* 426 */             double pold = plast;
/* 427 */             plast = p;
/* 428 */             p = en * plast / x - pold;
/* 429 */             if (p > tover) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 435 */               tover = 1.0E308D;
/* 436 */               p /= tover;
/* 437 */               plast /= tover;
/* 438 */               double psave = p;
/* 439 */               double psavel = plast;
/* 440 */               nstart = n + 1;
/*     */               while (true) {
/* 442 */                 n++;
/* 443 */                 en += 2.0D;
/* 444 */                 pold = plast;
/* 445 */                 plast = p;
/* 446 */                 p = en * plast / x - pold;
/* 447 */                 if (p > 1.0D) {
/* 448 */                   double d = en / x;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                   
/* 454 */                   test = pold * plast * (0.5D - 0.5D / d * d);
/* 455 */                   test /= 1.0E16D;
/* 456 */                   p = plast * tover;
/* 457 */                   n--;
/* 458 */                   en -= 2.0D;
/* 459 */                   nend = FastMath.min(nb, n);
/* 460 */                   for (int j = nstart; j <= nend; j++) {
/* 461 */                     pold = psavel;
/* 462 */                     psavel = psave;
/* 463 */                     psave = en * psavel / x - pold;
/* 464 */                     if (psave * psavel > test) {
/* 465 */                       ncalc = j - 1;
/* 466 */                       readyToInitialize = true;
/*     */                       break;
/*     */                     } 
/*     */                   } 
/* 470 */                   ncalc = nend;
/* 471 */                   readyToInitialize = true; break;
/*     */                 } 
/*     */               }  break;
/*     */             } 
/* 475 */           }  if (!readyToInitialize) {
/* 476 */             n = nend;
/* 477 */             en = 2.0D * (n + alpha);
/*     */ 
/*     */ 
/*     */             
/* 481 */             test = FastMath.max(test, FastMath.sqrt(plast * 1.0E16D) * FastMath.sqrt(2.0D * p));
/*     */           } 
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 488 */         if (!readyToInitialize) {
/*     */           do {
/* 490 */             n++;
/* 491 */             en += 2.0D;
/* 492 */             double pold = plast;
/* 493 */             plast = p;
/* 494 */             p = en * plast / x - pold;
/* 495 */           } while (p < test);
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 500 */         n++;
/* 501 */         en += 2.0D;
/* 502 */         double tempb = 0.0D;
/* 503 */         double tempa = 1.0D / p;
/* 504 */         int m = 2 * n - 4 * n / 2;
/* 505 */         double sum = 0.0D;
/* 506 */         double em = (n / 2);
/* 507 */         alpem = em - 1.0D + alpha;
/* 508 */         alp2em = 2.0D * em + alpha;
/* 509 */         if (m != 0) {
/* 510 */           sum = tempa * alpem * alp2em / em;
/*     */         }
/* 512 */         nend = n - nb;
/*     */         
/* 514 */         boolean readyToNormalize = false;
/* 515 */         boolean calculatedB0 = false;
/*     */ 
/*     */         
/*     */         int l;
/*     */ 
/*     */         
/* 521 */         for (l = 1; l <= nend; l++) {
/* 522 */           n--;
/* 523 */           en -= 2.0D;
/* 524 */           double tempc = tempb;
/* 525 */           tempb = tempa;
/* 526 */           tempa = en * tempb / x - tempc;
/* 527 */           m = 2 - m;
/* 528 */           if (m != 0) {
/* 529 */             em--;
/* 530 */             alp2em = 2.0D * em + alpha;
/* 531 */             if (n == 1) {
/*     */               break;
/*     */             }
/* 534 */             alpem = em - 1.0D + alpha;
/* 535 */             if (alpem == 0.0D) {
/* 536 */               alpem = 1.0D;
/*     */             }
/* 538 */             sum = (sum + tempa * alp2em) * alpem / em;
/*     */           } 
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 545 */         b[n - 1] = tempa;
/* 546 */         if (nend >= 0) {
/* 547 */           if (nb <= 1) {
/* 548 */             alp2em = alpha;
/* 549 */             if (alpha + 1.0D == 1.0D) {
/* 550 */               alp2em = 1.0D;
/*     */             }
/* 552 */             sum += b[0] * alp2em;
/* 553 */             readyToNormalize = true;
/*     */           
/*     */           }
/*     */           else {
/*     */             
/* 558 */             n--;
/* 559 */             en -= 2.0D;
/* 560 */             b[n - 1] = en * tempa / x - tempb;
/* 561 */             if (n == 1) {
/* 562 */               calculatedB0 = true;
/*     */             } else {
/* 564 */               m = 2 - m;
/* 565 */               if (m != 0) {
/* 566 */                 em--;
/* 567 */                 alp2em = 2.0D * em + alpha;
/* 568 */                 alpem = em - 1.0D + alpha;
/* 569 */                 if (alpem == 0.0D) {
/* 570 */                   alpem = 1.0D;
/*     */                 }
/*     */                 
/* 573 */                 sum = (sum + b[n - 1] * alp2em) * alpem / em;
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         }
/* 578 */         if (!readyToNormalize && !calculatedB0) {
/* 579 */           nend = n - 2;
/* 580 */           if (nend != 0)
/*     */           {
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 586 */             for (l = 1; l <= nend; l++) {
/* 587 */               n--;
/* 588 */               en -= 2.0D;
/* 589 */               b[n - 1] = en * b[n] / x - b[n + 1];
/* 590 */               m = 2 - m;
/* 591 */               if (m != 0) {
/* 592 */                 em--;
/* 593 */                 alp2em = 2.0D * em + alpha;
/* 594 */                 alpem = em - 1.0D + alpha;
/* 595 */                 if (alpem == 0.0D) {
/* 596 */                   alpem = 1.0D;
/*     */                 }
/*     */                 
/* 599 */                 sum = (sum + b[n - 1] * alp2em) * alpem / em;
/*     */               } 
/*     */             } 
/*     */           }
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 607 */         if (!readyToNormalize) {
/* 608 */           if (!calculatedB0) {
/* 609 */             b[0] = 2.0D * (alpha + 1.0D) * b[1] / x - b[2];
/*     */           }
/* 611 */           em--;
/* 612 */           alp2em = 2.0D * em + alpha;
/* 613 */           if (alp2em == 0.0D) {
/* 614 */             alp2em = 1.0D;
/*     */           }
/* 616 */           sum += b[0] * alp2em;
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 622 */         if (FastMath.abs(alpha) > 1.0E-16D) {
/* 623 */           sum *= Gamma.gamma(alpha) * FastMath.pow(x * 0.5D, -alpha);
/*     */         }
/* 625 */         tempa = 8.9E-308D;
/* 626 */         if (sum > 1.0D) {
/* 627 */           tempa *= sum;
/*     */         }
/*     */         
/* 630 */         for (n = 0; n < nb; n++) {
/* 631 */           if (FastMath.abs(b[n]) < tempa) {
/* 632 */             b[n] = 0.0D;
/*     */           }
/* 634 */           b[n] = b[n] / sum;
/*     */         }
/*     */       
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 641 */       if (b.length > 0) {
/* 642 */         b[0] = 0.0D;
/*     */       }
/* 644 */       ncalc = FastMath.min(nb, 0) - 1;
/*     */     } 
/* 646 */     return new BesselJResult(MathArrays.copyOf(b, b.length), ncalc);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/special/BesselJ.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */