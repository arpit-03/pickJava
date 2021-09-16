/*     */ package Catalano.Math.Functions;
/*     */ 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Gamma
/*     */ {
/*     */   public static final double GammaMax = 171.6243769563027D;
/*     */   
/*     */   public static double Function(double x) {
/*  52 */     double[] P = {
/*     */         
/*  54 */         1.6011952247675185E-4D, 
/*  55 */         0.0011913514700658638D, 
/*  56 */         0.010421379756176158D, 
/*  57 */         0.04763678004571372D, 
/*  58 */         0.20744822764843598D, 
/*  59 */         0.4942148268014971D, 
/*  60 */         1.0D
/*     */       };
/*  62 */     double[] Q = {
/*     */         
/*  64 */         -2.3158187332412014E-5D, 
/*  65 */         5.396055804933034E-4D, 
/*  66 */         -0.004456419138517973D, 
/*  67 */         0.011813978522206043D, 
/*  68 */         0.035823639860549865D, 
/*  69 */         -0.23459179571824335D, 
/*  70 */         0.0714304917030273D, 
/*  71 */         1.0D
/*     */       };
/*     */ 
/*     */ 
/*     */     
/*  76 */     double q = Math.abs(x);
/*     */     
/*  78 */     if (q > 33.0D) {
/*     */       
/*  80 */       if (x < 0.0D) {
/*     */         
/*  82 */         double d1 = Math.floor(q);
/*     */         
/*  84 */         if (d1 == q) {
/*     */           try {
/*  86 */             throw new ArithmeticException("Overflow");
/*  87 */           } catch (Exception e) {
/*  88 */             e.printStackTrace();
/*     */           } 
/*     */         }
/*     */         
/*  92 */         double d2 = q - d1;
/*  93 */         if (d2 > 0.5D) {
/*     */           
/*  95 */           d1++;
/*  96 */           d2 = q - d1;
/*     */         } 
/*  98 */         d2 = q * Math.sin(Math.PI * d2);
/*     */         
/* 100 */         if (d2 == 0.0D) {
/*     */           try {
/* 102 */             throw new ArithmeticException("Overflow");
/* 103 */           } catch (Exception e) {
/* 104 */             e.printStackTrace();
/*     */           } 
/*     */         }
/*     */         
/* 108 */         d2 = Math.abs(d2);
/* 109 */         d2 = Math.PI / d2 * Stirling(q);
/*     */         
/* 111 */         return -d2;
/*     */       } 
/*     */ 
/*     */       
/* 115 */       return Stirling(x);
/*     */     } 
/*     */ 
/*     */     
/* 119 */     double z = 1.0D;
/* 120 */     while (x >= 3.0D) {
/*     */       
/* 122 */       x--;
/* 123 */       z *= x;
/*     */     } 
/*     */     
/* 126 */     while (x < 0.0D) {
/*     */       
/* 128 */       if (x == 0.0D)
/*     */       {
/* 130 */         throw new ArithmeticException();
/*     */       }
/* 132 */       if (x > -1.0E-9D)
/*     */       {
/* 134 */         return z / (1.0D + 0.5772156649015329D * x) * x;
/*     */       }
/* 136 */       z /= x;
/* 137 */       x++;
/*     */     } 
/*     */     
/* 140 */     while (x < 2.0D) {
/*     */       
/* 142 */       if (x == 0.0D)
/*     */       {
/* 144 */         throw new ArithmeticException();
/*     */       }
/* 146 */       if (x < 1.0E-9D)
/*     */       {
/* 148 */         return z / (1.0D + 0.5772156649015329D * x) * x;
/*     */       }
/*     */       
/* 151 */       z /= x;
/* 152 */       x++;
/*     */     } 
/*     */     
/* 155 */     if (x == 2.0D || x == 3.0D) return z;
/*     */     
/* 157 */     x -= 2.0D;
/* 158 */     double p = Special.Polevl(x, P, 6);
/* 159 */     q = Special.Polevl(x, Q, 7);
/* 160 */     return z * p / q;
/*     */   }
/*     */ 
/*     */   
/*     */   public static double LowerIncomplete(double a, double x) {
/* 165 */     double eps = 1.0E-15D;
/* 166 */     double big = 4.503599627370496E15D;
/* 167 */     double biginv = 2.220446049250313E-16D;
/*     */     
/* 169 */     if (a < 0.0D) {
/*     */       try {
/* 171 */         throw new IllegalArgumentException("Out of Range: a");
/* 172 */       } catch (Exception e) {
/* 173 */         e.printStackTrace();
/*     */       } 
/*     */     }
/*     */     
/* 177 */     if (x < 0.0D) {
/*     */       try {
/* 179 */         throw new IllegalArgumentException("Out of Range: x");
/* 180 */       } catch (Exception e) {
/* 181 */         e.printStackTrace();
/*     */       } 
/*     */     }
/*     */     
/* 185 */     if (a == 0.0D) {
/*     */       
/* 187 */       if (x == 0.0D)
/* 188 */         return Double.NaN; 
/* 189 */       return 1.0D;
/*     */     } 
/*     */     
/* 192 */     if (x == 0.0D) return 0.0D;
/*     */     
/* 194 */     double ax = a * Math.log(x) - x - Log(a);
/*     */     
/* 196 */     if (ax < -709.782712893384D) {
/* 197 */       return 1.0D;
/*     */     }
/* 199 */     if (x <= 1.0D || x <= a) {
/*     */       
/* 201 */       double r2 = a;
/* 202 */       double c2 = 1.0D;
/* 203 */       double ans2 = 1.0D;
/*     */ 
/*     */       
/*     */       do {
/* 207 */         r2++;
/* 208 */         c2 = c2 * x / r2;
/* 209 */         ans2 += c2;
/*     */       }
/* 211 */       while (c2 / ans2 > 1.0E-15D);
/*     */       
/* 213 */       return Math.exp(ax) * ans2 / a;
/*     */     } 
/*     */     
/* 216 */     int c = 0;
/* 217 */     double y = 1.0D - a;
/* 218 */     double z = x + y + 1.0D;
/*     */     
/* 220 */     double p3 = 1.0D;
/* 221 */     double q3 = x;
/* 222 */     double p2 = x + 1.0D;
/* 223 */     double q2 = z * x;
/* 224 */     double ans = p2 / q2;
/*     */     
/* 226 */     double error = 0.0D;
/*     */ 
/*     */     
/*     */     do {
/* 230 */       c++;
/* 231 */       y++;
/* 232 */       z += 2.0D;
/* 233 */       double yc = y * c;
/*     */       
/* 235 */       double p = p2 * z - p3 * yc;
/* 236 */       double q = q2 * z - q3 * yc;
/*     */       
/* 238 */       if (q != 0.0D) {
/*     */         
/* 240 */         double nextans = p / q;
/* 241 */         error = Math.abs((ans - nextans) / nextans);
/* 242 */         ans = nextans;
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 247 */         error = 1.0D;
/*     */       } 
/*     */ 
/*     */       
/* 251 */       p3 = p2;
/* 252 */       p2 = p;
/* 253 */       q3 = q2;
/* 254 */       q2 = q;
/*     */ 
/*     */       
/* 257 */       if (Math.abs(p) <= 4.503599627370496E15D)
/*     */         continue; 
/* 259 */       p3 *= 2.220446049250313E-16D;
/* 260 */       p2 *= 2.220446049250313E-16D;
/* 261 */       q3 *= 2.220446049250313E-16D;
/* 262 */       q2 *= 2.220446049250313E-16D;
/*     */     
/*     */     }
/* 265 */     while (error > 1.0E-15D);
/*     */     
/* 267 */     return 1.0D - Math.exp(ax) * ans;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double Stirling(double x) {
/* 276 */     double[] STIR = {
/* 277 */         7.873113957930937E-4D, 
/* 278 */         -2.2954996161337813E-4D, 
/* 279 */         -0.0026813261780578124D, 
/* 280 */         0.0034722222160545866D, 
/* 281 */         0.08333333333334822D
/*     */       };
/*     */     
/* 284 */     double MAXSTIR = 143.01608D;
/*     */     
/* 286 */     double w = 1.0D / x;
/* 287 */     double y = Math.exp(x);
/*     */     
/* 289 */     w = 1.0D + w * Special.Polevl(w, STIR, 4);
/*     */     
/* 291 */     if (x > MAXSTIR) {
/*     */       
/* 293 */       double v = Math.pow(x, 0.5D * x - 0.25D);
/* 294 */       y = v * v / y;
/*     */     }
/*     */     else {
/*     */       
/* 298 */       y = Math.pow(x, x - 0.5D) / y;
/*     */     } 
/*     */     
/* 301 */     y = 2.5066282746310007D * y * w;
/* 302 */     return y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double Digamma(double x) {
/* 311 */     double s = 0.0D;
/* 312 */     double w = 0.0D;
/* 313 */     double y = 0.0D;
/* 314 */     double z = 0.0D;
/* 315 */     double nz = 0.0D;
/*     */     
/* 317 */     boolean negative = false;
/*     */     
/* 319 */     if (x <= 0.0D) {
/*     */       
/* 321 */       negative = true;
/* 322 */       double q = x;
/* 323 */       double p = (int)Math.floor(q);
/*     */       
/* 325 */       if (p == q) {
/*     */         try {
/* 327 */           throw new ArithmeticException("Function computation resulted in arithmetic overflow.");
/* 328 */         } catch (Exception e) {
/* 329 */           e.printStackTrace();
/*     */         } 
/*     */       }
/*     */       
/* 333 */       nz = q - p;
/*     */       
/* 335 */       if (nz != 0.5D) {
/*     */         
/* 337 */         if (nz > 0.5D) {
/*     */           
/* 339 */           p++;
/* 340 */           nz = q - p;
/*     */         } 
/* 342 */         nz = Math.PI / Math.tan(Math.PI * nz);
/*     */       }
/*     */       else {
/*     */         
/* 346 */         nz = 0.0D;
/*     */       } 
/*     */       
/* 349 */       x = 1.0D - x;
/*     */     } 
/*     */     
/* 352 */     if (x <= 10.0D && x == Math.floor(x)) {
/*     */       
/* 354 */       y = 0.0D;
/* 355 */       int n = (int)Math.floor(x);
/* 356 */       for (int i = 1; i <= n - 1; i++) {
/*     */         
/* 358 */         w = i;
/* 359 */         y += 1.0D / w;
/*     */       } 
/* 361 */       y -= 0.5772156649015329D;
/*     */     }
/*     */     else {
/*     */       
/* 365 */       s = x;
/* 366 */       w = 0.0D;
/*     */       
/* 368 */       while (s < 10.0D) {
/*     */         
/* 370 */         w += 1.0D / s;
/* 371 */         s++;
/*     */       } 
/*     */       
/* 374 */       if (s < 1.0E17D) {
/*     */         
/* 376 */         z = 1.0D / s * s;
/*     */         
/* 378 */         double polv = 0.08333333333333333D;
/* 379 */         polv = polv * z - 0.021092796092796094D;
/* 380 */         polv = polv * z + 0.007575757575757576D;
/* 381 */         polv = polv * z - 0.004166666666666667D;
/* 382 */         polv = polv * z + 0.003968253968253968D;
/* 383 */         polv = polv * z - 0.008333333333333333D;
/* 384 */         polv = polv * z + 0.08333333333333333D;
/* 385 */         y = z * polv;
/*     */       }
/*     */       else {
/*     */         
/* 389 */         y = 0.0D;
/*     */       } 
/* 391 */       y = Math.log(s) - 0.5D / s - y - w;
/*     */     } 
/*     */     
/* 394 */     if (negative)
/*     */     {
/* 396 */       y -= nz;
/*     */     }
/*     */     
/* 399 */     return y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double ComplementedIncomplete(double a, double x) {
/* 409 */     double t, big = 4.503599627370496E15D;
/* 410 */     double biginv = 2.220446049250313E-16D;
/*     */ 
/*     */ 
/*     */     
/* 414 */     if (x <= 0.0D || a <= 0.0D) return 1.0D;
/*     */     
/* 416 */     if (x < 1.0D || x < a) return 1.0D - Incomplete(a, x);
/*     */     
/* 418 */     double ax = a * Math.log(x) - x - Log(a);
/* 419 */     if (ax < -709.782712893384D) return 0.0D;
/*     */     
/* 421 */     ax = Math.exp(ax);
/*     */ 
/*     */     
/* 424 */     double y = 1.0D - a;
/* 425 */     double z = x + y + 1.0D;
/* 426 */     double c = 0.0D;
/* 427 */     double pkm2 = 1.0D;
/* 428 */     double qkm2 = x;
/* 429 */     double pkm1 = x + 1.0D;
/* 430 */     double qkm1 = z * x;
/* 431 */     double ans = pkm1 / qkm1;
/*     */ 
/*     */     
/*     */     do {
/* 435 */       c++;
/* 436 */       y++;
/* 437 */       z += 2.0D;
/* 438 */       double yc = y * c;
/* 439 */       double pk = pkm1 * z - pkm2 * yc;
/* 440 */       double qk = qkm1 * z - qkm2 * yc;
/* 441 */       if (qk != 0.0D) {
/*     */         
/* 443 */         double r = pk / qk;
/* 444 */         t = Math.abs((ans - r) / r);
/* 445 */         ans = r;
/*     */       } else {
/*     */         
/* 448 */         t = 1.0D;
/*     */       } 
/* 450 */       pkm2 = pkm1;
/* 451 */       pkm1 = pk;
/* 452 */       qkm2 = qkm1;
/* 453 */       qkm1 = qk;
/* 454 */       if (Math.abs(pk) <= 4.503599627370496E15D)
/*     */         continue; 
/* 456 */       pkm2 *= 2.220446049250313E-16D;
/* 457 */       pkm1 *= 2.220446049250313E-16D;
/* 458 */       qkm2 *= 2.220446049250313E-16D;
/* 459 */       qkm1 *= 2.220446049250313E-16D;
/*     */     }
/* 461 */     while (t > 1.1102230246251565E-16D);
/*     */     
/* 463 */     return ans * ax;
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
/*     */   public static double Incomplete(double a, double x) {
/* 475 */     if (x <= 0.0D || a <= 0.0D) return 0.0D;
/*     */     
/* 477 */     if (x > 1.0D && x > a) return 1.0D - ComplementedIncomplete(a, x);
/*     */     
/* 479 */     double ax = a * Math.log(x) - x - Log(a);
/* 480 */     if (ax < -709.782712893384D) return 0.0D;
/*     */     
/* 482 */     ax = Math.exp(ax);
/*     */     
/* 484 */     double r = a;
/* 485 */     double c = 1.0D;
/* 486 */     double ans = 1.0D;
/*     */ 
/*     */     
/*     */     do {
/* 490 */       r++;
/* 491 */       c *= x / r;
/* 492 */       ans += c;
/* 493 */     } while (c / ans > 1.1102230246251565E-16D);
/*     */     
/* 495 */     return ans * ax / a;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double Log(double x) {
/* 506 */     double[] A = {
/*     */         
/* 508 */         8.116141674705085E-4D, 
/* 509 */         -5.950619042843014E-4D, 
/* 510 */         7.936503404577169E-4D, 
/* 511 */         -0.002777777777300997D, 
/* 512 */         0.08333333333333319D
/*     */       };
/*     */     
/* 515 */     double[] B = {
/*     */         
/* 517 */         -1378.2515256912086D, 
/* 518 */         -38801.631513463784D, 
/* 519 */         -331612.9927388712D, 
/* 520 */         -1162370.974927623D, 
/* 521 */         -1721737.0082083966D, 
/* 522 */         -853555.6642457654D
/*     */       };
/*     */     
/* 525 */     double[] C = {
/*     */         
/* 527 */         -351.81570143652345D, 
/* 528 */         -17064.210665188115D, 
/* 529 */         -220528.59055385445D, 
/* 530 */         -1139334.4436798252D, 
/* 531 */         -2532523.0717758294D, 
/* 532 */         -2018891.4143353277D
/*     */       };
/*     */     
/* 535 */     if (x < -34.0D) {
/*     */       
/* 537 */       double d2 = -x;
/* 538 */       double w = Log(d2);
/* 539 */       double d1 = Math.floor(d2);
/*     */       
/* 541 */       if (d1 == d2) {
/*     */         try {
/* 543 */           throw new ArithmeticException("Overflow.");
/* 544 */         } catch (Exception e) {
/* 545 */           e.printStackTrace();
/*     */         } 
/*     */       }
/*     */       
/* 549 */       double z = d2 - d1;
/* 550 */       if (z > 0.5D) {
/*     */         
/* 552 */         d1++;
/* 553 */         z = d1 - d2;
/*     */       } 
/* 555 */       z = d2 * Math.sin(Math.PI * z);
/*     */       
/* 557 */       if (z == 0.0D) {
/*     */         try {
/* 559 */           throw new ArithmeticException("Overflow.");
/* 560 */         } catch (Exception e) {
/* 561 */           e.printStackTrace();
/*     */         } 
/*     */       }
/*     */       
/* 565 */       z = 1.1447298858494002D - Math.log(z) - w;
/* 566 */       return z;
/*     */     } 
/*     */     
/* 569 */     if (x < 13.0D) {
/*     */       
/* 571 */       double z = 1.0D;
/* 572 */       while (x >= 3.0D) {
/*     */         
/* 574 */         x--;
/* 575 */         z *= x;
/*     */       } 
/* 577 */       while (x < 2.0D) {
/*     */         
/* 579 */         if (x == 0.0D) {
/*     */           try {
/* 581 */             throw new ArithmeticException("Overflow.");
/* 582 */           } catch (Exception e) {
/* 583 */             e.printStackTrace();
/*     */           } 
/*     */         }
/*     */         
/* 587 */         z /= x;
/* 588 */         x++;
/*     */       } 
/* 590 */       if (z < 0.0D) z = -z; 
/* 591 */       if (x == 2.0D) return Math.log(z); 
/* 592 */       x -= 2.0D;
/* 593 */       double d1 = x * Special.Polevl(x, B, 5) / Special.P1evl(x, C, 6);
/* 594 */       return Math.log(z) + d1;
/*     */     } 
/*     */     
/* 597 */     if (x > 2.556348E305D) {
/*     */       try {
/* 599 */         throw new ArithmeticException("Overflow.");
/* 600 */       } catch (Exception e) {
/* 601 */         e.printStackTrace();
/*     */       } 
/*     */     }
/*     */     
/* 605 */     double q = (x - 0.5D) * Math.log(x) - x + 0.9189385332046728D;
/* 606 */     if (x > 1.0E8D) return q;
/*     */     
/* 608 */     double p = 1.0D / x * x;
/* 609 */     if (x >= 1000.0D) {
/*     */       
/* 611 */       q += ((7.936507936507937E-4D * p - 
/* 612 */         0.002777777777777778D) * p + 
/* 613 */         0.08333333333333333D) / x;
/*     */     }
/*     */     else {
/*     */       
/* 617 */       q += Special.Polevl(p, A, 4) / x;
/*     */     } 
/*     */     
/* 620 */     return q;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Math/Functions/Gamma.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */