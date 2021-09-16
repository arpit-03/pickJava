/*     */ package org.apache.commons.math3.special;
/*     */ 
/*     */ import org.apache.commons.math3.exception.MaxCountExceededException;
/*     */ import org.apache.commons.math3.exception.NumberIsTooLargeException;
/*     */ import org.apache.commons.math3.exception.NumberIsTooSmallException;
/*     */ import org.apache.commons.math3.util.ContinuedFraction;
/*     */ import org.apache.commons.math3.util.FastMath;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Gamma
/*     */ {
/*     */   public static final double GAMMA = 0.5772156649015329D;
/*     */   public static final double LANCZOS_G = 4.7421875D;
/*     */   private static final double DEFAULT_EPSILON = 1.0E-14D;
/*  71 */   private static final double[] LANCZOS = new double[] { 0.9999999999999971D, 57.15623566586292D, -59.59796035547549D, 14.136097974741746D, -0.4919138160976202D, 3.399464998481189E-5D, 4.652362892704858E-5D, -9.837447530487956E-5D, 1.580887032249125E-4D, -2.1026444172410488E-4D, 2.1743961811521265E-4D, -1.643181065367639E-4D, 8.441822398385275E-5D, -2.6190838401581408E-5D, 3.6899182659531625E-6D };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  90 */   private static final double HALF_LOG_2_PI = 0.5D * FastMath.log(6.283185307179586D);
/*     */ 
/*     */ 
/*     */   
/*     */   private static final double SQRT_TWO_PI = 2.5066282746310007D;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final double C_LIMIT = 49.0D;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final double S_LIMIT = 1.0E-5D;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final double INV_GAMMA1P_M1_A0 = 6.116095104481416E-9D;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final double INV_GAMMA1P_M1_A1 = 6.247308301164655E-9D;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final double INV_GAMMA1P_M1_B1 = 0.203610414066807D;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final double INV_GAMMA1P_M1_B2 = 0.026620534842894922D;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final double INV_GAMMA1P_M1_B3 = 4.939449793824468E-4D;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final double INV_GAMMA1P_M1_B4 = -8.514194324403149E-6D;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final double INV_GAMMA1P_M1_B5 = -6.4304548177935305E-6D;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final double INV_GAMMA1P_M1_B6 = 9.926418406727737E-7D;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final double INV_GAMMA1P_M1_B7 = -6.077618957228252E-8D;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final double INV_GAMMA1P_M1_B8 = 1.9575583661463974E-10D;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final double INV_GAMMA1P_M1_P0 = 6.116095104481416E-9D;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final double INV_GAMMA1P_M1_P1 = 6.8716741130671986E-9D;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final double INV_GAMMA1P_M1_P2 = 6.820161668496171E-10D;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final double INV_GAMMA1P_M1_P3 = 4.686843322948848E-11D;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final double INV_GAMMA1P_M1_P4 = 1.5728330277104463E-12D;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final double INV_GAMMA1P_M1_P5 = -1.2494415722763663E-13D;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final double INV_GAMMA1P_M1_P6 = 4.343529937408594E-15D;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final double INV_GAMMA1P_M1_Q1 = 0.3056961078365221D;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final double INV_GAMMA1P_M1_Q2 = 0.054642130860422966D;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final double INV_GAMMA1P_M1_Q3 = 0.004956830093825887D;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final double INV_GAMMA1P_M1_Q4 = 2.6923694661863613E-4D;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final double INV_GAMMA1P_M1_C = -0.42278433509846713D;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final double INV_GAMMA1P_M1_C0 = 0.5772156649015329D;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final double INV_GAMMA1P_M1_C1 = -0.6558780715202539D;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final double INV_GAMMA1P_M1_C2 = -0.04200263503409524D;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final double INV_GAMMA1P_M1_C3 = 0.16653861138229148D;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final double INV_GAMMA1P_M1_C4 = -0.04219773455554433D;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final double INV_GAMMA1P_M1_C5 = -0.009621971527876973D;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final double INV_GAMMA1P_M1_C6 = 0.0072189432466631D;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final double INV_GAMMA1P_M1_C7 = -0.0011651675918590652D;
/*     */ 
/*     */   
/*     */   private static final double INV_GAMMA1P_M1_C8 = -2.1524167411495098E-4D;
/*     */ 
/*     */   
/*     */   private static final double INV_GAMMA1P_M1_C9 = 1.280502823881162E-4D;
/*     */ 
/*     */   
/*     */   private static final double INV_GAMMA1P_M1_C10 = -2.013485478078824E-5D;
/*     */ 
/*     */   
/*     */   private static final double INV_GAMMA1P_M1_C11 = -1.2504934821426706E-6D;
/*     */ 
/*     */   
/*     */   private static final double INV_GAMMA1P_M1_C12 = 1.133027231981696E-6D;
/*     */ 
/*     */   
/*     */   private static final double INV_GAMMA1P_M1_C13 = -2.056338416977607E-7D;
/*     */ 
/*     */ 
/*     */   
/*     */   public static double logGamma(double x) {
/*     */     double ret;
/* 246 */     if (Double.isNaN(x) || x <= 0.0D)
/* 247 */     { ret = Double.NaN; }
/* 248 */     else { if (x < 0.5D)
/* 249 */         return logGamma1p(x) - FastMath.log(x); 
/* 250 */       if (x <= 2.5D)
/* 251 */         return logGamma1p(x - 0.5D - 0.5D); 
/* 252 */       if (x <= 8.0D) {
/* 253 */         int n = (int)FastMath.floor(x - 1.5D);
/* 254 */         double prod = 1.0D;
/* 255 */         for (int i = 1; i <= n; i++) {
/* 256 */           prod *= x - i;
/*     */         }
/* 258 */         return logGamma1p(x - (n + 1)) + FastMath.log(prod);
/*     */       } 
/* 260 */       double sum = lanczos(x);
/* 261 */       double tmp = x + 4.7421875D + 0.5D;
/* 262 */       ret = (x + 0.5D) * FastMath.log(tmp) - tmp + HALF_LOG_2_PI + FastMath.log(sum / x); }
/*     */ 
/*     */ 
/*     */     
/* 266 */     return ret;
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
/*     */   public static double regularizedGammaP(double a, double x) {
/* 278 */     return regularizedGammaP(a, x, 1.0E-14D, 2147483647);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double regularizedGammaP(double a, double x, double epsilon, int maxIterations) {
/*     */     double ret;
/* 315 */     if (Double.isNaN(a) || Double.isNaN(x) || a <= 0.0D || x < 0.0D) {
/* 316 */       ret = Double.NaN;
/* 317 */     } else if (x == 0.0D) {
/* 318 */       ret = 0.0D;
/* 319 */     } else if (x >= a + 1.0D) {
/*     */ 
/*     */       
/* 322 */       ret = 1.0D - regularizedGammaQ(a, x, epsilon, maxIterations);
/*     */     } else {
/*     */       
/* 325 */       double n = 0.0D;
/* 326 */       double an = 1.0D / a;
/* 327 */       double sum = an;
/* 328 */       while (FastMath.abs(an / sum) > epsilon && n < maxIterations && sum < Double.POSITIVE_INFINITY) {
/*     */ 
/*     */ 
/*     */         
/* 332 */         n++;
/* 333 */         an *= x / (a + n);
/*     */ 
/*     */         
/* 336 */         sum += an;
/*     */       } 
/* 338 */       if (n >= maxIterations)
/* 339 */         throw new MaxCountExceededException(Integer.valueOf(maxIterations)); 
/* 340 */       if (Double.isInfinite(sum)) {
/* 341 */         ret = 1.0D;
/*     */       } else {
/* 343 */         ret = FastMath.exp(-x + a * FastMath.log(x) - logGamma(a)) * sum;
/*     */       } 
/*     */     } 
/*     */     
/* 347 */     return ret;
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
/*     */   public static double regularizedGammaQ(double a, double x) {
/* 359 */     return regularizedGammaQ(a, x, 1.0E-14D, 2147483647);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double regularizedGammaQ(final double a, double x, double epsilon, int maxIterations) {
/*     */     double ret;
/* 393 */     if (Double.isNaN(a) || Double.isNaN(x) || a <= 0.0D || x < 0.0D) {
/* 394 */       ret = Double.NaN;
/* 395 */     } else if (x == 0.0D) {
/* 396 */       ret = 1.0D;
/* 397 */     } else if (x < a + 1.0D) {
/*     */ 
/*     */       
/* 400 */       ret = 1.0D - regularizedGammaP(a, x, epsilon, maxIterations);
/*     */     } else {
/*     */       
/* 403 */       ContinuedFraction cf = new ContinuedFraction()
/*     */         {
/*     */           
/*     */           protected double getA(int n, double x)
/*     */           {
/* 408 */             return 2.0D * n + 1.0D - a + x;
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           protected double getB(int n, double x) {
/* 414 */             return n * (a - n);
/*     */           }
/*     */         };
/*     */       
/* 418 */       ret = 1.0D / cf.evaluate(x, epsilon, maxIterations);
/* 419 */       ret = FastMath.exp(-x + a * FastMath.log(x) - logGamma(a)) * ret;
/*     */     } 
/*     */     
/* 422 */     return ret;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static double digamma(double x) {
/* 447 */     if (Double.isNaN(x) || Double.isInfinite(x)) {
/* 448 */       return x;
/*     */     }
/*     */     
/* 451 */     if (x > 0.0D && x <= 1.0E-5D)
/*     */     {
/*     */       
/* 454 */       return -0.5772156649015329D - 1.0D / x;
/*     */     }
/*     */     
/* 457 */     if (x >= 49.0D) {
/*     */       
/* 459 */       double inv = 1.0D / x * x;
/*     */ 
/*     */ 
/*     */       
/* 463 */       return FastMath.log(x) - 0.5D / x - inv * (0.08333333333333333D + inv * (0.008333333333333333D - inv / 252.0D));
/*     */     } 
/*     */     
/* 466 */     return digamma(x + 1.0D) - 1.0D / x;
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
/*     */   public static double trigamma(double x) {
/* 481 */     if (Double.isNaN(x) || Double.isInfinite(x)) {
/* 482 */       return x;
/*     */     }
/*     */     
/* 485 */     if (x > 0.0D && x <= 1.0E-5D) {
/* 486 */       return 1.0D / x * x;
/*     */     }
/*     */     
/* 489 */     if (x >= 49.0D) {
/* 490 */       double inv = 1.0D / x * x;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 495 */       return 1.0D / x + inv / 2.0D + inv / x * (0.16666666666666666D - inv * (0.03333333333333333D + inv / 42.0D));
/*     */     } 
/*     */     
/* 498 */     return trigamma(x + 1.0D) + 1.0D / x * x;
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
/*     */ 
/*     */   
/*     */   public static double lanczos(double x) {
/* 522 */     double sum = 0.0D;
/* 523 */     for (int i = LANCZOS.length - 1; i > 0; i--) {
/* 524 */       sum += LANCZOS[i] / (x + i);
/*     */     }
/* 526 */     return sum + LANCZOS[0];
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
/*     */   public static double invGamma1pm1(double x) {
/*     */     double ret;
/* 543 */     if (x < -0.5D) {
/* 544 */       throw new NumberIsTooSmallException(Double.valueOf(x), Double.valueOf(-0.5D), true);
/*     */     }
/* 546 */     if (x > 1.5D) {
/* 547 */       throw new NumberIsTooLargeException(Double.valueOf(x), Double.valueOf(1.5D), true);
/*     */     }
/*     */ 
/*     */     
/* 551 */     double t = (x <= 0.5D) ? x : (x - 0.5D - 0.5D);
/* 552 */     if (t < 0.0D) {
/* 553 */       double a = 6.116095104481416E-9D + t * 6.247308301164655E-9D;
/* 554 */       double b = 1.9575583661463974E-10D;
/* 555 */       b = -6.077618957228252E-8D + t * b;
/* 556 */       b = 9.926418406727737E-7D + t * b;
/* 557 */       b = -6.4304548177935305E-6D + t * b;
/* 558 */       b = -8.514194324403149E-6D + t * b;
/* 559 */       b = 4.939449793824468E-4D + t * b;
/* 560 */       b = 0.026620534842894922D + t * b;
/* 561 */       b = 0.203610414066807D + t * b;
/* 562 */       b = 1.0D + t * b;
/*     */       
/* 564 */       double c = -2.056338416977607E-7D + t * a / b;
/* 565 */       c = 1.133027231981696E-6D + t * c;
/* 566 */       c = -1.2504934821426706E-6D + t * c;
/* 567 */       c = -2.013485478078824E-5D + t * c;
/* 568 */       c = 1.280502823881162E-4D + t * c;
/* 569 */       c = -2.1524167411495098E-4D + t * c;
/* 570 */       c = -0.0011651675918590652D + t * c;
/* 571 */       c = 0.0072189432466631D + t * c;
/* 572 */       c = -0.009621971527876973D + t * c;
/* 573 */       c = -0.04219773455554433D + t * c;
/* 574 */       c = 0.16653861138229148D + t * c;
/* 575 */       c = -0.04200263503409524D + t * c;
/* 576 */       c = -0.6558780715202539D + t * c;
/* 577 */       c = -0.42278433509846713D + t * c;
/* 578 */       if (x > 0.5D) {
/* 579 */         ret = t * c / x;
/*     */       } else {
/* 581 */         ret = x * (c + 0.5D + 0.5D);
/*     */       } 
/*     */     } else {
/* 584 */       double p = 4.343529937408594E-15D;
/* 585 */       p = -1.2494415722763663E-13D + t * p;
/* 586 */       p = 1.5728330277104463E-12D + t * p;
/* 587 */       p = 4.686843322948848E-11D + t * p;
/* 588 */       p = 6.820161668496171E-10D + t * p;
/* 589 */       p = 6.8716741130671986E-9D + t * p;
/* 590 */       p = 6.116095104481416E-9D + t * p;
/*     */       
/* 592 */       double q = 2.6923694661863613E-4D;
/* 593 */       q = 0.004956830093825887D + t * q;
/* 594 */       q = 0.054642130860422966D + t * q;
/* 595 */       q = 0.3056961078365221D + t * q;
/* 596 */       q = 1.0D + t * q;
/*     */       
/* 598 */       double c = -2.056338416977607E-7D + p / q * t;
/* 599 */       c = 1.133027231981696E-6D + t * c;
/* 600 */       c = -1.2504934821426706E-6D + t * c;
/* 601 */       c = -2.013485478078824E-5D + t * c;
/* 602 */       c = 1.280502823881162E-4D + t * c;
/* 603 */       c = -2.1524167411495098E-4D + t * c;
/* 604 */       c = -0.0011651675918590652D + t * c;
/* 605 */       c = 0.0072189432466631D + t * c;
/* 606 */       c = -0.009621971527876973D + t * c;
/* 607 */       c = -0.04219773455554433D + t * c;
/* 608 */       c = 0.16653861138229148D + t * c;
/* 609 */       c = -0.04200263503409524D + t * c;
/* 610 */       c = -0.6558780715202539D + t * c;
/* 611 */       c = 0.5772156649015329D + t * c;
/*     */       
/* 613 */       if (x > 0.5D) {
/* 614 */         ret = t / x * (c - 0.5D - 0.5D);
/*     */       } else {
/* 616 */         ret = x * c;
/*     */       } 
/*     */     } 
/*     */     
/* 620 */     return ret;
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
/*     */   public static double logGamma1p(double x) throws NumberIsTooSmallException, NumberIsTooLargeException {
/* 637 */     if (x < -0.5D) {
/* 638 */       throw new NumberIsTooSmallException(Double.valueOf(x), Double.valueOf(-0.5D), true);
/*     */     }
/* 640 */     if (x > 1.5D) {
/* 641 */       throw new NumberIsTooLargeException(Double.valueOf(x), Double.valueOf(1.5D), true);
/*     */     }
/*     */     
/* 644 */     return -FastMath.log1p(invGamma1pm1(x));
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
/*     */   public static double gamma(double x) {
/*     */     double ret;
/* 659 */     if (x == FastMath.rint(x) && x <= 0.0D) {
/* 660 */       return Double.NaN;
/*     */     }
/*     */ 
/*     */     
/* 664 */     double absX = FastMath.abs(x);
/* 665 */     if (absX <= 20.0D) {
/* 666 */       if (x >= 1.0D) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 675 */         double prod = 1.0D;
/* 676 */         double t = x;
/* 677 */         while (t > 2.5D) {
/* 678 */           t--;
/* 679 */           prod *= t;
/*     */         } 
/* 681 */         ret = prod / (1.0D + invGamma1pm1(t - 1.0D));
/*     */ 
/*     */ 
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */ 
/*     */         
/* 690 */         double prod = x;
/* 691 */         double t = x;
/* 692 */         while (t < -0.5D) {
/* 693 */           t++;
/* 694 */           prod *= t;
/*     */         } 
/* 696 */         ret = 1.0D / prod * (1.0D + invGamma1pm1(t));
/*     */       } 
/*     */     } else {
/* 699 */       double y = absX + 4.7421875D + 0.5D;
/* 700 */       double gammaAbs = 2.5066282746310007D / absX * FastMath.pow(y, absX + 0.5D) * FastMath.exp(-y) * lanczos(absX);
/*     */ 
/*     */       
/* 703 */       if (x > 0.0D) {
/* 704 */         ret = gammaAbs;
/*     */ 
/*     */ 
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 714 */         ret = -3.141592653589793D / x * FastMath.sin(Math.PI * x) * gammaAbs;
/*     */       } 
/*     */     } 
/*     */     
/* 718 */     return ret;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/special/Gamma.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */