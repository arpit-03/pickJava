/*     */ package org.apache.commons.math3.util;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.concurrent.atomic.AtomicReference;
/*     */ import org.apache.commons.math3.exception.MathArithmeticException;
/*     */ import org.apache.commons.math3.exception.NotPositiveException;
/*     */ import org.apache.commons.math3.exception.NumberIsTooLargeException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class CombinatoricsUtils
/*     */ {
/*  35 */   static final long[] FACTORIALS = new long[] { 1L, 1L, 2L, 6L, 24L, 120L, 720L, 5040L, 40320L, 362880L, 3628800L, 39916800L, 479001600L, 6227020800L, 87178291200L, 1307674368000L, 20922789888000L, 355687428096000L, 6402373705728000L, 121645100408832000L, 2432902008176640000L };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  45 */   static final AtomicReference<long[][]> STIRLING_S2 = (AtomicReference)new AtomicReference<long>(null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long binomialCoefficient(int n, int k) throws NotPositiveException, NumberIsTooLargeException, MathArithmeticException {
/*  79 */     checkBinomial(n, k);
/*  80 */     if (n == k || k == 0) {
/*  81 */       return 1L;
/*     */     }
/*  83 */     if (k == 1 || k == n - 1) {
/*  84 */       return n;
/*     */     }
/*     */     
/*  87 */     if (k > n / 2) {
/*  88 */       return binomialCoefficient(n, n - k);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  96 */     long result = 1L;
/*  97 */     if (n <= 61) {
/*     */       
/*  99 */       int i = n - k + 1;
/* 100 */       for (int j = 1; j <= k; j++) {
/* 101 */         result = result * i / j;
/* 102 */         i++;
/*     */       } 
/* 104 */     } else if (n <= 66) {
/*     */ 
/*     */       
/* 107 */       int i = n - k + 1;
/* 108 */       for (int j = 1; j <= k; j++)
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 115 */         long d = ArithmeticUtils.gcd(i, j);
/* 116 */         result = result / j / d * i / d;
/* 117 */         i++;
/*     */       }
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 123 */       int i = n - k + 1;
/* 124 */       for (int j = 1; j <= k; j++) {
/* 125 */         long d = ArithmeticUtils.gcd(i, j);
/* 126 */         result = ArithmeticUtils.mulAndCheck(result / j / d, i / d);
/* 127 */         i++;
/*     */       } 
/*     */     } 
/* 130 */     return result;
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
/*     */   public static double binomialCoefficientDouble(int n, int k) throws NotPositiveException, NumberIsTooLargeException, MathArithmeticException {
/* 160 */     checkBinomial(n, k);
/* 161 */     if (n == k || k == 0) {
/* 162 */       return 1.0D;
/*     */     }
/* 164 */     if (k == 1 || k == n - 1) {
/* 165 */       return n;
/*     */     }
/* 167 */     if (k > n / 2) {
/* 168 */       return binomialCoefficientDouble(n, n - k);
/*     */     }
/* 170 */     if (n < 67) {
/* 171 */       return binomialCoefficient(n, k);
/*     */     }
/*     */     
/* 174 */     double result = 1.0D;
/* 175 */     for (int i = 1; i <= k; i++) {
/* 176 */       result *= (n - k + i) / i;
/*     */     }
/*     */     
/* 179 */     return FastMath.floor(result + 0.5D);
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
/*     */   public static double binomialCoefficientLog(int n, int k) throws NotPositiveException, NumberIsTooLargeException, MathArithmeticException {
/* 205 */     checkBinomial(n, k);
/* 206 */     if (n == k || k == 0) {
/* 207 */       return 0.0D;
/*     */     }
/* 209 */     if (k == 1 || k == n - 1) {
/* 210 */       return FastMath.log(n);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 217 */     if (n < 67) {
/* 218 */       return FastMath.log(binomialCoefficient(n, k));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 225 */     if (n < 1030) {
/* 226 */       return FastMath.log(binomialCoefficientDouble(n, k));
/*     */     }
/*     */     
/* 229 */     if (k > n / 2) {
/* 230 */       return binomialCoefficientLog(n, n - k);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 236 */     double logSum = 0.0D;
/*     */     
/*     */     int i;
/* 239 */     for (i = n - k + 1; i <= n; i++) {
/* 240 */       logSum += FastMath.log(i);
/*     */     }
/*     */ 
/*     */     
/* 244 */     for (i = 2; i <= k; i++) {
/* 245 */       logSum -= FastMath.log(i);
/*     */     }
/*     */     
/* 248 */     return logSum;
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
/*     */   public static long factorial(int n) throws NotPositiveException, MathArithmeticException {
/* 276 */     if (n < 0) {
/* 277 */       throw new NotPositiveException(LocalizedFormats.FACTORIAL_NEGATIVE_PARAMETER, Integer.valueOf(n));
/*     */     }
/*     */     
/* 280 */     if (n > 20) {
/* 281 */       throw new MathArithmeticException();
/*     */     }
/* 283 */     return FACTORIALS[n];
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
/*     */   public static double factorialDouble(int n) throws NotPositiveException {
/* 300 */     if (n < 0) {
/* 301 */       throw new NotPositiveException(LocalizedFormats.FACTORIAL_NEGATIVE_PARAMETER, Integer.valueOf(n));
/*     */     }
/*     */     
/* 304 */     if (n < 21) {
/* 305 */       return FACTORIALS[n];
/*     */     }
/* 307 */     return FastMath.floor(FastMath.exp(factorialLog(n)) + 0.5D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double factorialLog(int n) throws NotPositiveException {
/* 318 */     if (n < 0) {
/* 319 */       throw new NotPositiveException(LocalizedFormats.FACTORIAL_NEGATIVE_PARAMETER, Integer.valueOf(n));
/*     */     }
/*     */     
/* 322 */     if (n < 21) {
/* 323 */       return FastMath.log(FACTORIALS[n]);
/*     */     }
/* 325 */     double logSum = 0.0D;
/* 326 */     for (int i = 2; i <= n; i++) {
/* 327 */       logSum += FastMath.log(i);
/*     */     }
/* 329 */     return logSum;
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
/*     */   public static long stirlingS2(int n, int k) throws NotPositiveException, NumberIsTooLargeException, MathArithmeticException {
/* 353 */     if (k < 0) {
/* 354 */       throw new NotPositiveException(Integer.valueOf(k));
/*     */     }
/* 356 */     if (k > n) {
/* 357 */       throw new NumberIsTooLargeException(Integer.valueOf(k), Integer.valueOf(n), true);
/*     */     }
/*     */     
/* 360 */     long[][] stirlingS2 = STIRLING_S2.get();
/*     */     
/* 362 */     if (stirlingS2 == null) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 368 */       int maxIndex = 26;
/* 369 */       stirlingS2 = new long[26][];
/* 370 */       (new long[1])[0] = 1L; stirlingS2[0] = new long[1];
/* 371 */       for (int i = 1; i < stirlingS2.length; i++) {
/* 372 */         stirlingS2[i] = new long[i + 1];
/* 373 */         stirlingS2[i][0] = 0L;
/* 374 */         stirlingS2[i][1] = 1L;
/* 375 */         stirlingS2[i][i] = 1L;
/* 376 */         for (int m = 2; m < i; m++) {
/* 377 */           stirlingS2[i][m] = m * stirlingS2[i - 1][m] + stirlingS2[i - 1][m - 1];
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 382 */       STIRLING_S2.compareAndSet(null, stirlingS2);
/*     */     } 
/*     */ 
/*     */     
/* 386 */     if (n < stirlingS2.length)
/*     */     {
/* 388 */       return stirlingS2[n][k];
/*     */     }
/*     */     
/* 391 */     if (k == 0)
/* 392 */       return 0L; 
/* 393 */     if (k == 1 || k == n)
/* 394 */       return 1L; 
/* 395 */     if (k == 2)
/* 396 */       return (1L << n - 1) - 1L; 
/* 397 */     if (k == n - 1) {
/* 398 */       return binomialCoefficient(n, 2);
/*     */     }
/*     */     
/* 401 */     long sum = 0L;
/* 402 */     long sign = ((k & 0x1) == 0) ? 1L : -1L;
/* 403 */     for (int j = 1; j <= k; j++) {
/* 404 */       sign = -sign;
/* 405 */       sum += sign * binomialCoefficient(k, j) * ArithmeticUtils.pow(j, n);
/* 406 */       if (sum < 0L)
/*     */       {
/* 408 */         throw new MathArithmeticException(LocalizedFormats.ARGUMENT_OUTSIDE_DOMAIN, new Object[] { Integer.valueOf(n), Integer.valueOf(0), Integer.valueOf(stirlingS2.length - 1) });
/*     */       }
/*     */     } 
/*     */     
/* 412 */     return sum / factorial(k);
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
/*     */   public static Iterator<int[]> combinationsIterator(int n, int k) {
/* 439 */     return (new Combinations(n, k)).iterator();
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
/*     */   public static void checkBinomial(int n, int k) throws NumberIsTooLargeException, NotPositiveException {
/* 454 */     if (n < k) {
/* 455 */       throw new NumberIsTooLargeException(LocalizedFormats.BINOMIAL_INVALID_PARAMETERS_ORDER, Integer.valueOf(k), Integer.valueOf(n), true);
/*     */     }
/*     */     
/* 458 */     if (n < 0)
/* 459 */       throw new NotPositiveException(LocalizedFormats.BINOMIAL_NEGATIVE_PARAMETER, Integer.valueOf(n)); 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/util/CombinatoricsUtils.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */