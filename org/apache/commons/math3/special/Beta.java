/*     */ package org.apache.commons.math3.special;
/*     */ 
/*     */ import org.apache.commons.math3.exception.NumberIsTooSmallException;
/*     */ import org.apache.commons.math3.exception.OutOfRangeException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Beta
/*     */ {
/*     */   private static final double DEFAULT_EPSILON = 1.0E-14D;
/*     */   private static final double HALF_LOG_TWO_PI = 0.9189385332046727D;
/*  80 */   private static final double[] DELTA = new double[] { 0.08333333333333333D, -2.777777777777778E-5D, 7.936507936507937E-8D, -5.952380952380953E-10D, 8.417508417508329E-12D, -1.917526917518546E-13D, 6.410256405103255E-15D, -2.955065141253382E-16D, 1.7964371635940225E-17D, -1.3922896466162779E-18D, 1.338028550140209E-19D, -1.542460098679661E-20D, 1.9770199298095743E-21D, -2.3406566479399704E-22D, 1.713480149663986E-23D };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double regularizedBeta(double x, double a, double b) {
/* 116 */     return regularizedBeta(x, a, b, 1.0E-14D, 2147483647);
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
/*     */   public static double regularizedBeta(double x, double a, double b, double epsilon) {
/* 137 */     return regularizedBeta(x, a, b, epsilon, 2147483647);
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
/*     */   public static double regularizedBeta(double x, double a, double b, int maxIterations) {
/* 154 */     return regularizedBeta(x, a, b, 1.0E-14D, maxIterations);
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
/*     */   public static double regularizedBeta(double x, final double a, final double b, double epsilon, int maxIterations) {
/*     */     double ret;
/* 186 */     if (Double.isNaN(x) || Double.isNaN(a) || Double.isNaN(b) || x < 0.0D || x > 1.0D || a <= 0.0D || b <= 0.0D) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 193 */       ret = Double.NaN;
/* 194 */     } else if (x > (a + 1.0D) / (2.0D + b + a) && 1.0D - x <= (b + 1.0D) / (2.0D + b + a)) {
/*     */       
/* 196 */       ret = 1.0D - regularizedBeta(1.0D - x, b, a, epsilon, maxIterations);
/*     */     } else {
/* 198 */       ContinuedFraction fraction = new ContinuedFraction()
/*     */         {
/*     */           protected double getB(int n, double x)
/*     */           {
/*     */             double ret;
/*     */ 
/*     */             
/* 205 */             if (n % 2 == 0) {
/* 206 */               double m = n / 2.0D;
/* 207 */               ret = m * (b - m) * x / (a + 2.0D * m - 1.0D) * (a + 2.0D * m);
/*     */             } else {
/*     */               
/* 210 */               double m = (n - 1.0D) / 2.0D;
/* 211 */               ret = -((a + m) * (a + b + m) * x) / (a + 2.0D * m) * (a + 2.0D * m + 1.0D);
/*     */             } 
/*     */             
/* 214 */             return ret;
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           protected double getA(int n, double x) {
/* 220 */             return 1.0D;
/*     */           }
/*     */         };
/* 223 */       ret = FastMath.exp(a * FastMath.log(x) + b * FastMath.log1p(-x) - FastMath.log(a) - logBeta(a, b)) * 1.0D / fraction.evaluate(x, epsilon, maxIterations);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 228 */     return ret;
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
/*     */   @Deprecated
/*     */   public static double logBeta(double a, double b, double epsilon, int maxIterations) {
/* 255 */     return logBeta(a, b);
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
/*     */   private static double logGammaSum(double a, double b) throws OutOfRangeException {
/* 274 */     if (a < 1.0D || a > 2.0D) {
/* 275 */       throw new OutOfRangeException(Double.valueOf(a), Double.valueOf(1.0D), Double.valueOf(2.0D));
/*     */     }
/* 277 */     if (b < 1.0D || b > 2.0D) {
/* 278 */       throw new OutOfRangeException(Double.valueOf(b), Double.valueOf(1.0D), Double.valueOf(2.0D));
/*     */     }
/*     */     
/* 281 */     double x = a - 1.0D + b - 1.0D;
/* 282 */     if (x <= 0.5D)
/* 283 */       return Gamma.logGamma1p(1.0D + x); 
/* 284 */     if (x <= 1.5D) {
/* 285 */       return Gamma.logGamma1p(x) + FastMath.log1p(x);
/*     */     }
/* 287 */     return Gamma.logGamma1p(x - 1.0D) + FastMath.log(x * (1.0D + x));
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
/*     */   private static double logGammaMinusLogGammaSum(double a, double b) throws NumberIsTooSmallException {
/*     */     double d, w;
/* 307 */     if (a < 0.0D) {
/* 308 */       throw new NumberIsTooSmallException(Double.valueOf(a), Double.valueOf(0.0D), true);
/*     */     }
/* 310 */     if (b < 10.0D) {
/* 311 */       throw new NumberIsTooSmallException(Double.valueOf(b), Double.valueOf(10.0D), true);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 319 */     if (a <= b) {
/* 320 */       d = b + a - 0.5D;
/* 321 */       w = deltaMinusDeltaSum(a, b);
/*     */     } else {
/* 323 */       d = a + b - 0.5D;
/* 324 */       w = deltaMinusDeltaSum(b, a);
/*     */     } 
/*     */     
/* 327 */     double u = d * FastMath.log1p(a / b);
/* 328 */     double v = a * (FastMath.log(b) - 1.0D);
/*     */     
/* 330 */     return (u <= v) ? (w - u - v) : (w - v - u);
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
/*     */   private static double deltaMinusDeltaSum(double a, double b) throws OutOfRangeException, NumberIsTooSmallException {
/* 347 */     if (a < 0.0D || a > b) {
/* 348 */       throw new OutOfRangeException(Double.valueOf(a), Integer.valueOf(0), Double.valueOf(b));
/*     */     }
/* 350 */     if (b < 10.0D) {
/* 351 */       throw new NumberIsTooSmallException(Double.valueOf(b), Integer.valueOf(10), true);
/*     */     }
/*     */     
/* 354 */     double h = a / b;
/* 355 */     double p = h / (1.0D + h);
/* 356 */     double q = 1.0D / (1.0D + h);
/* 357 */     double q2 = q * q;
/*     */ 
/*     */ 
/*     */     
/* 361 */     double[] s = new double[DELTA.length];
/* 362 */     s[0] = 1.0D;
/* 363 */     for (int i = 1; i < s.length; i++) {
/* 364 */       s[i] = 1.0D + q + q2 * s[i - 1];
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 369 */     double sqrtT = 10.0D / b;
/* 370 */     double t = sqrtT * sqrtT;
/* 371 */     double w = DELTA[DELTA.length - 1] * s[s.length - 1];
/* 372 */     for (int j = DELTA.length - 2; j >= 0; j--) {
/* 373 */       w = t * w + DELTA[j] * s[j];
/*     */     }
/* 375 */     return w * p / b;
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
/*     */   private static double sumDeltaMinusDeltaSum(double p, double q) {
/* 393 */     if (p < 10.0D) {
/* 394 */       throw new NumberIsTooSmallException(Double.valueOf(p), Double.valueOf(10.0D), true);
/*     */     }
/* 396 */     if (q < 10.0D) {
/* 397 */       throw new NumberIsTooSmallException(Double.valueOf(q), Double.valueOf(10.0D), true);
/*     */     }
/*     */     
/* 400 */     double a = FastMath.min(p, q);
/* 401 */     double b = FastMath.max(p, q);
/* 402 */     double sqrtT = 10.0D / a;
/* 403 */     double t = sqrtT * sqrtT;
/* 404 */     double z = DELTA[DELTA.length - 1];
/* 405 */     for (int i = DELTA.length - 2; i >= 0; i--) {
/* 406 */       z = t * z + DELTA[i];
/*     */     }
/* 408 */     return z / a + deltaMinusDeltaSum(a, b);
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
/*     */   public static double logBeta(double p, double q) {
/* 422 */     if (Double.isNaN(p) || Double.isNaN(q) || p <= 0.0D || q <= 0.0D) {
/* 423 */       return Double.NaN;
/*     */     }
/*     */     
/* 426 */     double a = FastMath.min(p, q);
/* 427 */     double b = FastMath.max(p, q);
/* 428 */     if (a >= 10.0D) {
/* 429 */       double w = sumDeltaMinusDeltaSum(a, b);
/* 430 */       double h = a / b;
/* 431 */       double c = h / (1.0D + h);
/* 432 */       double u = -(a - 0.5D) * FastMath.log(c);
/* 433 */       double v = b * FastMath.log1p(h);
/* 434 */       if (u <= v) {
/* 435 */         return -0.5D * FastMath.log(b) + 0.9189385332046727D + w - u - v;
/*     */       }
/* 437 */       return -0.5D * FastMath.log(b) + 0.9189385332046727D + w - v - u;
/*     */     } 
/* 439 */     if (a > 2.0D) {
/* 440 */       if (b > 1000.0D) {
/* 441 */         int n = (int)FastMath.floor(a - 1.0D);
/* 442 */         double prod = 1.0D;
/* 443 */         double d1 = a;
/* 444 */         for (int i = 0; i < n; i++) {
/* 445 */           d1--;
/* 446 */           prod *= d1 / (1.0D + d1 / b);
/*     */         } 
/* 448 */         return FastMath.log(prod) - n * FastMath.log(b) + Gamma.logGamma(d1) + logGammaMinusLogGammaSum(d1, b);
/*     */       } 
/*     */ 
/*     */       
/* 452 */       double prod1 = 1.0D;
/* 453 */       double ared = a;
/* 454 */       while (ared > 2.0D) {
/* 455 */         ared--;
/* 456 */         double h = ared / b;
/* 457 */         prod1 *= h / (1.0D + h);
/*     */       } 
/* 459 */       if (b < 10.0D) {
/* 460 */         double prod2 = 1.0D;
/* 461 */         double bred = b;
/* 462 */         while (bred > 2.0D) {
/* 463 */           bred--;
/* 464 */           prod2 *= bred / (ared + bred);
/*     */         } 
/* 466 */         return FastMath.log(prod1) + FastMath.log(prod2) + Gamma.logGamma(ared) + Gamma.logGamma(bred) - logGammaSum(ared, bred);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 472 */       return FastMath.log(prod1) + Gamma.logGamma(ared) + logGammaMinusLogGammaSum(ared, b);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 477 */     if (a >= 1.0D) {
/* 478 */       if (b > 2.0D) {
/* 479 */         if (b < 10.0D) {
/* 480 */           double prod = 1.0D;
/* 481 */           double bred = b;
/* 482 */           while (bred > 2.0D) {
/* 483 */             bred--;
/* 484 */             prod *= bred / (a + bred);
/*     */           } 
/* 486 */           return FastMath.log(prod) + Gamma.logGamma(a) + Gamma.logGamma(bred) - logGammaSum(a, bred);
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 491 */         return Gamma.logGamma(a) + logGammaMinusLogGammaSum(a, b);
/*     */       } 
/*     */ 
/*     */       
/* 495 */       return Gamma.logGamma(a) + Gamma.logGamma(b) - logGammaSum(a, b);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 500 */     if (b >= 10.0D) {
/* 501 */       return Gamma.logGamma(a) + logGammaMinusLogGammaSum(a, b);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 508 */     return FastMath.log(Gamma.gamma(a) * Gamma.gamma(b) / Gamma.gamma(a + b));
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/special/Beta.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */