/*     */ package Catalano.Statistics.Distributions;
/*     */ 
/*     */ import Catalano.Math.Matrix;
/*     */ import Catalano.Math.Special;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class KolmogorovSmirnovDistribution
/*     */   implements IDistribution
/*     */ {
/*     */   private static int eV;
/*     */   private int numberOfSamples;
/*     */   
/*     */   public int getNumberOfSamples() {
/*  46 */     return this.numberOfSamples;
/*     */   }
/*     */   
/*     */   public void setNumberOfSamples(int numberOfSamples) {
/*  50 */     this.numberOfSamples = numberOfSamples;
/*     */   }
/*     */   
/*     */   public KolmogorovSmirnovDistribution(int samples) {
/*  54 */     this.numberOfSamples = samples;
/*     */   }
/*     */ 
/*     */   
/*     */   public double Mean() {
/*  59 */     return 0.8687311606361592D / Math.sqrt(this.numberOfSamples);
/*     */   }
/*     */ 
/*     */   
/*     */   public double Variance() {
/*  64 */     return (0.8224670334241132D - Mean() * Mean()) / this.numberOfSamples;
/*     */   }
/*     */ 
/*     */   
/*     */   public double Entropy() {
/*  69 */     throw new UnsupportedOperationException("Not supported");
/*     */   }
/*     */ 
/*     */   
/*     */   public double DistributionFunction(double x) {
/*  74 */     return CumulativeFunction(this.numberOfSamples, x);
/*     */   }
/*     */ 
/*     */   
/*     */   public double ProbabilityDensityFunction(double x) {
/*  79 */     throw new UnsupportedOperationException("Not supported.");
/*     */   }
/*     */ 
/*     */   
/*     */   public double LogProbabilityDensityFunction(double x) {
/*  84 */     throw new UnsupportedOperationException("Not supported.");
/*     */   }
/*     */   
/*     */   public double ComplementaryDistributionFunction(double x) {
/*  88 */     return ComplementaryDistributionFunction(this.numberOfSamples, x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double OneSideDistributionFunction(double x) {
/*  97 */     return OneSideUpperTail(this.numberOfSamples, x);
/*     */   }
/*     */   
/*     */   public static double CumulativeFunction(int n, double x) {
/* 101 */     double nxx = n * x * x;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 108 */     if (x >= 1.0D || nxx >= 18.0D) {
/* 109 */       return 1.0D;
/*     */     }
/* 111 */     if (x <= 0.5D / n) {
/* 112 */       return 0.0D;
/*     */     }
/* 114 */     if (n == 1) {
/* 115 */       return 2.0D * x - 1.0D;
/*     */     }
/* 117 */     if (x <= 1.0D / n) {
/* 118 */       return (n <= 20) ? (Special.Factorial(n) * Math.pow(2.0D * x - 1.0D / n, n)) : 
/* 119 */         Math.exp(Special.LogFactorial(n) + n * Math.log(2.0D * x - 1.0D / n));
/*     */     }
/* 121 */     if (x >= 1.0D - 1.0D / n) {
/* 122 */       return 1.0D - 2.0D * Math.pow(1.0D - x, n);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 127 */     if (n <= 140) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 134 */       if (nxx < 0.754693D) {
/* 135 */         return Durbin(n, x);
/*     */       }
/*     */       
/* 138 */       if (nxx < 4.0D) {
/* 139 */         return Pomeranz(n, x);
/*     */       }
/*     */       
/* 142 */       return 1.0D - ComplementaryDistributionFunction(n, x);
/*     */     } 
/*     */ 
/*     */     
/* 146 */     if (n <= 100000) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 164 */       if (n * nxx * x <= 1.96D)
/* 165 */         return Durbin(n, x); 
/* 166 */       return PelzGood(n, x);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 172 */     return PelzGood(n, x);
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
/*     */   public static double ComplementaryDistributionFunction(int n, double x) {
/* 184 */     double nxx = n * x * x;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 191 */     if (x >= 1.0D || nxx >= 370.0D) {
/* 192 */       return 0.0D;
/*     */     }
/* 194 */     if (x <= 0.5D / n || nxx <= 0.0274D) {
/* 195 */       return 1.0D;
/*     */     }
/* 197 */     if (n == 1) {
/* 198 */       return 2.0D - 2.0D * x;
/*     */     }
/* 200 */     if (x <= 1.0D / n) {
/* 201 */       return (n <= 20) ? (1.0D - Special.Factorial(n) * Math.pow(2.0D * x - 1.0D / n, n)) : (
/* 202 */         1.0D - Math.exp(Special.LogFactorial(n) + n * Math.log(2.0D * x - 1.0D / n)));
/*     */     }
/* 204 */     if (x >= 1.0D - 1.0D / n) {
/* 205 */       return 2.0D * Math.pow(1.0D - x, n);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 210 */     if (n <= 140) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 215 */       if (nxx >= 4.0D)
/*     */       {
/*     */ 
/*     */         
/* 219 */         return 2.0D * OneSideUpperTail(n, x);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 225 */       return 1.0D - CumulativeFunction(n, x);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 233 */     if (nxx >= 2.2D)
/*     */     {
/*     */ 
/*     */       
/* 237 */       return 2.0D * OneSideUpperTail(n, x);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 243 */     return 1.0D - CumulativeFunction(n, x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double PelzGood(int n, double x) {
/* 252 */     int maxTerms = 20;
/* 253 */     double eps = 1.0E-10D;
/*     */     
/* 255 */     double PI2 = 9.869604401089358D;
/* 256 */     double PI4 = 97.40909103400243D;
/*     */     
/* 258 */     double sqrtN = Math.sqrt(n);
/*     */     
/* 260 */     double z = sqrtN * x;
/* 261 */     double z2 = z * z;
/* 262 */     double z3 = z2 * z;
/* 263 */     double z4 = z2 * z2;
/* 264 */     double z5 = z4 * z;
/* 265 */     double z6 = z4 * z2;
/* 266 */     double z7 = z4 * z3;
/* 267 */     double z8 = z4 * z4;
/* 268 */     double z10 = z8 * z2;
/*     */     
/* 270 */     double pz = -9.869604401089358D / 2.0D * z2;
/*     */ 
/*     */ 
/*     */     
/* 274 */     double k0 = 0.0D;
/* 275 */     for (int k = 0; k <= 20; k++) {
/*     */       
/* 277 */       double h = k + 0.5D; double term;
/* 278 */       k0 += term = Math.exp(h * h * pz);
/* 279 */       if (term <= 1.0E-10D * k0) {
/*     */         break;
/*     */       }
/*     */     } 
/* 283 */     double k1 = 0.0D;
/* 284 */     for (int i = 0; i <= 20; i++) {
/*     */       
/* 286 */       double hh = (i + 0.5D) * (i + 0.5D); double term;
/* 287 */       k1 += term = (9.869604401089358D * hh - z2) * Math.exp(hh * pz);
/* 288 */       if (Math.abs(term) <= 1.0E-10D * Math.abs(k1)) {
/*     */         break;
/*     */       }
/*     */     } 
/* 292 */     double k2a = 0.0D;
/* 293 */     for (int j = 0; j <= 20; j++) {
/*     */       
/* 295 */       double hh = (j + 0.5D) * (j + 0.5D); double term;
/* 296 */       k2a += term = (6.0D * z6 + 2.0D * z4 + 
/* 297 */         9.869604401089358D * (2.0D * z4 - 5.0D * z2) * hh + 
/* 298 */         97.40909103400243D * (1.0D - 2.0D * z2) * hh * hh) * Math.exp(hh * pz);
/* 299 */       if (Math.abs(term) <= 1.0E-10D * Math.abs(k2a))
/*     */         break; 
/*     */     } 
/* 302 */     double k2b = 0.0D;
/* 303 */     for (int m = 1; m <= 20; m++) {
/*     */       
/* 305 */       double kk = (m * m); double term;
/* 306 */       k2b += term = 9.869604401089358D * kk * Math.exp(kk * pz);
/* 307 */       if (term <= 1.0E-10D * k2b) {
/*     */         break;
/*     */       }
/*     */     } 
/* 311 */     double k3a = 0.0D;
/* 312 */     for (int i1 = 0; i1 <= 20; i1++) {
/*     */       
/* 314 */       double hh = (i1 + 0.5D) * (i1 + 0.5D); double term;
/* 315 */       k3a += term = (-30.0D * z6 - 90.0D * z8 + 
/* 316 */         9.869604401089358D * (135.0D * z4 - 96.0D * z6) * hh + 
/* 317 */         97.40909103400243D * (212.0D * z4 - 60.0D * z2) * hh * hh + 
/* 318 */         961.3891935753043D * hh * hh * hh * (5.0D - 30.0D * z2)) * Math.exp(hh * pz);
/* 319 */       if (Math.abs(term) <= 1.0E-10D * Math.abs(k3a))
/*     */         break; 
/*     */     } 
/* 322 */     double k3b = 0.0D;
/* 323 */     for (int i2 = 1; i2 <= 20; i2++) {
/*     */       
/* 325 */       double kk = (i2 * i2); double term;
/* 326 */       k3b += term = (29.608813203268074D * kk * z2 - 97.40909103400243D * kk * kk) * Math.exp(kk * pz);
/* 327 */       if (Math.abs(term) <= 1.0E-10D * Math.abs(k3b)) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */     
/* 332 */     double sum = k0 * 2.5066282746310007D / z + 
/* 333 */       k1 * 1.2533141373155003D / sqrtN * 3.0D * z4 + 
/* 334 */       k2a * 1.2533141373155003D / n * 36.0D * z7 - 
/* 335 */       k2b * 1.2533141373155003D / n * 18.0D * z3 + 
/* 336 */       k3a * 1.2533141373155003D / n * sqrtN * 3240.0D * z10 + 
/* 337 */       k3b * 1.2533141373155003D / n * sqrtN * 108.0D * z6;
/*     */     
/* 339 */     return sum;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double OneSideUpperTail(int n, double x) {
/* 346 */     if (n > 200000) {
/*     */ 
/*     */       
/* 349 */       double t = (6 * n) * x + 1.0D;
/* 350 */       double z = t * t / (18 * n);
/* 351 */       double v = (1.0D - (2.0D * z * z - 4.0D * z - 1.0D) / (18 * n)) * Math.exp(-z);
/*     */       
/* 353 */       if (v <= 0.0D) return 0.0D; 
/* 354 */       if (v >= 1.0D) return 1.0D; 
/* 355 */       return 1.0D * v;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 364 */     int jmax = (int)(n * (1.0D - x));
/* 365 */     if (1.0D - x - jmax / n <= 0.0D) {
/* 366 */       jmax--;
/*     */     }
/*     */     
/* 369 */     int jdiv = (n > 3000) ? 2 : 3;
/* 370 */     int jstart = jmax / jdiv + 1;
/*     */     
/* 372 */     double logBinomial = Special.LogBinomial(n, jstart);
/* 373 */     double LOGJMAX = logBinomial;
/* 374 */     double EPSILON = 1.0E-12D;
/*     */ 
/*     */ 
/*     */     
/* 378 */     double sum = 0.0D;
/*     */     int j;
/* 380 */     for (j = jstart; j <= jmax; j++) {
/*     */       
/* 382 */       double q = j / n + x;
/* 383 */       double term = logBinomial + (j - 1) * Math.log(q) + (n - j) * Special.Log1p(-q);
/* 384 */       double t = Math.exp(term);
/*     */       
/* 386 */       sum += t;
/* 387 */       logBinomial += Math.log((n - j) / (j + 1));
/*     */       
/* 389 */       if (t <= sum * EPSILON) {
/*     */         break;
/*     */       }
/*     */     } 
/* 393 */     jstart = jmax / jdiv;
/* 394 */     logBinomial = LOGJMAX + Math.log((jstart + 1) / (n - jstart));
/*     */     
/* 396 */     for (j = jstart; j > 0; j--) {
/*     */       
/* 398 */       double q = j / n + x;
/* 399 */       double term = logBinomial + (j - 1) * Math.log(q) + (n - j) * Special.Log1p(-q);
/* 400 */       double t = Math.exp(term);
/*     */       
/* 402 */       sum += t;
/* 403 */       logBinomial += Math.log(j / (n - j + 1));
/*     */       
/* 405 */       if (t <= sum * EPSILON) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */     
/* 410 */     return sum * x + Math.exp(n * Special.Log1p(-x));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double Pomeranz(int n, double x) {
/* 419 */     double EPS = 1.0E-15D;
/* 420 */     int ENO = 350;
/* 421 */     double RENO = Math.pow(2.0D, ENO);
/*     */     
/* 423 */     double t = n * x;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 429 */     double[] A = new double[2 * n + 3];
/* 430 */     double[] floors = new double[2 * n + 3];
/* 431 */     double[] ceilings = new double[2 * n + 3];
/*     */     
/* 433 */     double[][] V = new double[2][];
/* 434 */     for (int j = 0; j < V.length; j++) {
/* 435 */       V[j] = new double[n + 2];
/*     */     }
/* 437 */     double[][] H = new double[4][];
/* 438 */     for (int k = 0; k < H.length; k++) {
/* 439 */       H[k] = new double[n + 2];
/*     */     }
/* 441 */     double z = computeLimits(t, floors, ceilings);
/*     */     
/* 443 */     computeA(n, A, z);
/* 444 */     computeH(n, A, H);
/*     */     
/* 446 */     V[1][1] = RENO;
/* 447 */     int renormalizations = 1;
/*     */     
/* 449 */     int r1 = 0;
/* 450 */     int r2 = 1;
/* 451 */     for (int i = 2; i <= 2 * n + 2; i++) {
/*     */       
/* 453 */       int jlow = (int)(2.0D + floors[i]);
/* 454 */       if (jlow < 1)
/* 455 */         jlow = 1; 
/* 456 */       int jup = (int)ceilings[i];
/* 457 */       if (jup > n + 1) {
/* 458 */         jup = n + 1;
/*     */       }
/* 460 */       int klow = (int)(2.0D + floors[i - 1]);
/* 461 */       if (klow < 1)
/* 462 */         klow = 1; 
/* 463 */       int kup0 = (int)ceilings[i - 1];
/*     */ 
/*     */       
/* 466 */       double d1 = (A[i] - A[i - 1]) / n;
/* 467 */       int s = -1; int m;
/* 468 */       for (m = 0; m < 4; m++) {
/*     */         
/* 470 */         if (Math.abs(d1 - H[m][1]) <= EPS) {
/*     */           
/* 472 */           s = m;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/* 477 */       double minsum = RENO;
/* 478 */       r1 = r1 + 1 & 0x1;
/* 479 */       r2 = r2 + 1 & 0x1;
/*     */       
/* 481 */       for (m = jlow; m <= jup; m++) {
/*     */         
/* 483 */         int kup = kup0;
/* 484 */         if (kup > m)
/* 485 */           kup = m; 
/* 486 */         double d = 0.0D;
/* 487 */         for (int i1 = kup; i1 >= klow; i1--)
/* 488 */           d += V[r1][i1] * H[s][m - i1]; 
/* 489 */         V[r2][m] = d;
/* 490 */         if (d < minsum) {
/* 491 */           minsum = d;
/*     */         }
/*     */       } 
/* 494 */       if (minsum < 1.0E-280D) {
/*     */ 
/*     */         
/* 497 */         for (m = jlow; m <= jup; m++)
/* 498 */           V[r2][m] = V[r2][m] * RENO; 
/* 499 */         renormalizations++;
/*     */       } 
/*     */     } 
/*     */     
/* 503 */     double sum = V[r2][n + 1];
/* 504 */     double w = Special.LogFactorial(n) - (renormalizations * ENO) * 0.6931471805599453D + Math.log(sum);
/* 505 */     if (w >= 0.0D)
/* 506 */       return 1.0D; 
/* 507 */     return Math.exp(w);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double Durbin(int n, double d) {
/* 516 */     int k = (int)(n * d) + 1;
/* 517 */     int m = 2 * k - 1;
/* 518 */     double h = k - n * d;
/* 519 */     double[][] H = new double[m][m];
/* 520 */     double[][] Q = new double[m][m];
/* 521 */     double[][] B = new double[m][m];
/*     */     
/*     */     int i;
/* 524 */     for (i = 0; i < m; i++) {
/* 525 */       for (int i1 = 0; i1 < m; i1++) {
/* 526 */         if (i - i1 + 1 >= 0)
/* 527 */           H[i][i1] = 1.0D; 
/*     */       } 
/* 529 */     }  for (i = 0; i < m; i++) {
/*     */       
/* 531 */       H[i][0] = H[i][0] - Math.pow(h, (i + 1));
/* 532 */       H[m - 1][i] = H[m - 1][i] - Math.pow(h, (m - i));
/*     */     } 
/*     */     
/* 535 */     H[m - 1][0] = H[m - 1][0] + ((2.0D * h - 1.0D > 0.0D) ? Math.pow(2.0D * h - 1.0D, m) : 0.0D);
/*     */     
/* 537 */     for (i = 0; i < m; i++) {
/* 538 */       for (int i1 = 0; i1 < m; i1++) {
/* 539 */         if (i - i1 + 1 > 0)
/* 540 */           for (int g = 1; g <= i - i1 + 1; g++)
/* 541 */             H[i][i1] = H[i][i1] / g;  
/*     */       } 
/* 543 */     }  int pQ = 0;
/* 544 */     matrixPower(H, 0, Q, m, n, B);
/*     */     
/* 546 */     double s = Q[k - 1][k - 1];
/*     */     
/* 548 */     for (int j = 1; j <= n; j++) {
/*     */       
/* 550 */       s *= j / n;
/* 551 */       if (s < 1.0E-140D) {
/*     */         
/* 553 */         s *= 1.0E140D;
/* 554 */         pQ -= 140;
/*     */       } 
/*     */     } 
/*     */     
/* 558 */     return s * Math.pow(10.0D, pQ);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void matrixPower(double[][] A, int eA, double[][] V, int m, int n, double[][] B) {
/* 565 */     if (n == 1) {
/*     */       
/* 567 */       for (int i = 0; i < m; i++) {
/* 568 */         for (int j = 0; j < m; j++)
/* 569 */           V[i][j] = A[i][j]; 
/* 570 */       }  eV = eA;
/*     */       
/*     */       return;
/*     */     } 
/* 574 */     matrixPower(A, eA, V, m, n / 2, B);
/*     */     
/* 576 */     B = Matrix.Multiply(V, B);
/*     */     
/* 578 */     int eB = 2 * eV;
/* 579 */     if (B[m / 2][m / 2] > 1.0E140D) {
/*     */       
/* 581 */       for (int i = 0; i < m; i++) {
/* 582 */         for (int j = 0; j < m; j++)
/* 583 */           B[i][j] = B[i][j] * 1.0E-140D; 
/* 584 */       }  eB += 140;
/*     */     } 
/*     */     
/* 587 */     if (n % 2 == 0) {
/*     */       
/* 589 */       for (int i = 0; i < m; i++) {
/* 590 */         for (int j = 0; j < m; j++)
/* 591 */           V[i][j] = B[i][j]; 
/* 592 */       }  eV = eB;
/*     */     }
/*     */     else {
/*     */       
/* 596 */       V = Matrix.Multiply(A, B);
/* 597 */       eV = eA + eB;
/*     */     } 
/*     */     
/* 600 */     if (V[m / 2][m / 2] > 1.0E140D) {
/*     */       
/* 602 */       for (int i = 0; i < m; i++) {
/* 603 */         for (int j = 0; j < m; j++)
/* 604 */           V[i][j] = V[i][j] * 1.0E-140D; 
/* 605 */       }  eV += 140;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static double computeLimits(double t, double[] floors, double[] ceilings) {
/* 613 */     double floor = Math.floor(t);
/* 614 */     double ceiling = Math.ceil(t);
/*     */     
/* 616 */     double z = t - floor;
/* 617 */     double w = ceiling - t;
/*     */     
/* 619 */     if (z > 0.5D) {
/*     */       int i;
/* 621 */       for (i = 1; i < floors.length; i += 2)
/* 622 */         floors[i] = (i / 2 - 1) - floor; 
/* 623 */       for (i = 2; i < floors.length; i += 2)
/* 624 */         floors[i] = (i / 2 - 2) - floor; 
/* 625 */       for (i = 1; i < ceilings.length; i += 2)
/* 626 */         ceilings[i] = (i / 2 + 1) + floor; 
/* 627 */       for (i = 2; i < ceilings.length; i += 2) {
/* 628 */         ceilings[i] = (i / 2) + floor;
/*     */       }
/* 630 */     } else if (z > 0.0D) {
/*     */       
/* 632 */       ceilings[1] = 1.0D + floor; int i;
/* 633 */       for (i = 1; i < floors.length; i++)
/* 634 */         floors[i] = (i / 2 - 1) - floor; 
/* 635 */       for (i = 2; i < ceilings.length; i++) {
/* 636 */         ceilings[i] = (i / 2) + floor;
/*     */       }
/*     */     } else {
/*     */       int i;
/* 640 */       for (i = 1; i < floors.length; i += 2)
/* 641 */         floors[i] = (i / 2) - floor; 
/* 642 */       for (i = 2; i < floors.length; i += 2)
/* 643 */         floors[i] = (i / 2 - 1) - floor; 
/* 644 */       for (i = 1; i < ceilings.length; i += 2)
/* 645 */         ceilings[i] = (i / 2) + floor; 
/* 646 */       for (i = 2; i < ceilings.length; i += 2) {
/* 647 */         ceilings[i] = (i / 2 - 1) + floor;
/*     */       }
/*     */     } 
/* 650 */     if (w < z) z = w;
/*     */     
/* 652 */     return z;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void computeA(int n, double[] A, double z) {
/* 658 */     A[0] = 0.0D;
/* 659 */     A[1] = 0.0D;
/* 660 */     A[2] = z;
/* 661 */     A[3] = 1.0D - z;
/*     */     
/* 663 */     for (int i = 4; i < A.length - 1; i++) {
/* 664 */       A[i] = A[i - 2] + 1.0D;
/*     */     }
/* 666 */     A[A.length - 1] = n;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static double computeH(int n, double[] A, double[][] H) {
/* 676 */     H[0][0] = 1.0D;
/* 677 */     double w = 2.0D * A[2] / n; int j;
/* 678 */     for (j = 1; j <= n + 1; j++) {
/* 679 */       H[0][j] = w * H[0][j - 1] / j;
/*     */     }
/* 681 */     H[1][0] = 1.0D;
/* 682 */     w = (1.0D - 2.0D * A[2]) / n;
/* 683 */     for (j = 1; j <= n + 1; j++) {
/* 684 */       H[1][j] = w * H[1][j - 1] / j;
/*     */     }
/* 686 */     H[2][0] = 1.0D;
/* 687 */     w = A[2] / n;
/* 688 */     for (j = 1; j <= n + 1; j++) {
/* 689 */       H[2][j] = w * H[2][j - 1] / j;
/*     */     }
/* 691 */     H[3][0] = 1.0D;
/* 692 */     for (j = 1; j <= n + 1; j++)
/* 693 */       H[3][j] = 0.0D; 
/* 694 */     return w;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Statistics/Distributions/KolmogorovSmirnovDistribution.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */