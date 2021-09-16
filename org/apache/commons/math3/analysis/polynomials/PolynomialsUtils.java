/*     */ package org.apache.commons.math3.analysis.polynomials;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.math3.fraction.BigFraction;
/*     */ import org.apache.commons.math3.util.CombinatoricsUtils;
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
/*     */ public class PolynomialsUtils
/*     */ {
/*  54 */   private static final List<BigFraction> CHEBYSHEV_COEFFICIENTS = new ArrayList<BigFraction>(); static {
/*  55 */     CHEBYSHEV_COEFFICIENTS.add(BigFraction.ONE);
/*  56 */     CHEBYSHEV_COEFFICIENTS.add(BigFraction.ZERO);
/*  57 */     CHEBYSHEV_COEFFICIENTS.add(BigFraction.ONE);
/*     */   }
/*     */ 
/*     */   
/*  61 */   private static final List<BigFraction> HERMITE_COEFFICIENTS = new ArrayList<BigFraction>(); static {
/*  62 */     HERMITE_COEFFICIENTS.add(BigFraction.ONE);
/*  63 */     HERMITE_COEFFICIENTS.add(BigFraction.ZERO);
/*  64 */     HERMITE_COEFFICIENTS.add(BigFraction.TWO);
/*     */   }
/*     */ 
/*     */   
/*  68 */   private static final List<BigFraction> LAGUERRE_COEFFICIENTS = new ArrayList<BigFraction>(); static {
/*  69 */     LAGUERRE_COEFFICIENTS.add(BigFraction.ONE);
/*  70 */     LAGUERRE_COEFFICIENTS.add(BigFraction.ONE);
/*  71 */     LAGUERRE_COEFFICIENTS.add(BigFraction.MINUS_ONE);
/*     */   }
/*     */ 
/*     */   
/*  75 */   private static final List<BigFraction> LEGENDRE_COEFFICIENTS = new ArrayList<BigFraction>(); static {
/*  76 */     LEGENDRE_COEFFICIENTS.add(BigFraction.ONE);
/*  77 */     LEGENDRE_COEFFICIENTS.add(BigFraction.ZERO);
/*  78 */     LEGENDRE_COEFFICIENTS.add(BigFraction.ONE);
/*     */   }
/*     */   
/*  81 */   private static final Map<JacobiKey, List<BigFraction>> JACOBI_COEFFICIENTS = new HashMap<JacobiKey, List<BigFraction>>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PolynomialFunction createChebyshevPolynomial(int degree) {
/* 106 */     return buildPolynomial(degree, CHEBYSHEV_COEFFICIENTS, new RecurrenceCoefficientsGenerator()
/*     */         {
/*     */           
/* 109 */           private final BigFraction[] coeffs = new BigFraction[] { BigFraction.ZERO, BigFraction.TWO, BigFraction.ONE };
/*     */           
/*     */           public BigFraction[] generate(int k) {
/* 112 */             return this.coeffs;
/*     */           }
/*     */         });
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
/*     */   public static PolynomialFunction createHermitePolynomial(int degree) {
/* 133 */     return buildPolynomial(degree, HERMITE_COEFFICIENTS, new RecurrenceCoefficientsGenerator()
/*     */         {
/*     */           public BigFraction[] generate(int k)
/*     */           {
/* 137 */             return new BigFraction[] { BigFraction.ZERO, BigFraction.TWO, new BigFraction(2 * k) };
/*     */           }
/*     */         });
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
/*     */   public static PolynomialFunction createLaguerrePolynomial(int degree) {
/* 160 */     return buildPolynomial(degree, LAGUERRE_COEFFICIENTS, new RecurrenceCoefficientsGenerator()
/*     */         {
/*     */           public BigFraction[] generate(int k)
/*     */           {
/* 164 */             int kP1 = k + 1;
/* 165 */             return new BigFraction[] { new BigFraction(2 * k + 1, kP1), new BigFraction(-1, kP1), new BigFraction(k, kP1) };
/*     */           }
/*     */         });
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
/*     */   public static PolynomialFunction createLegendrePolynomial(int degree) {
/* 188 */     return buildPolynomial(degree, LEGENDRE_COEFFICIENTS, new RecurrenceCoefficientsGenerator()
/*     */         {
/*     */           public BigFraction[] generate(int k)
/*     */           {
/* 192 */             int kP1 = k + 1;
/* 193 */             return new BigFraction[] { BigFraction.ZERO, new BigFraction(k + kP1, kP1), new BigFraction(k, kP1) };
/*     */           }
/*     */         });
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
/*     */   public static PolynomialFunction createJacobiPolynomial(int degree, final int v, final int w) {
/* 222 */     JacobiKey key = new JacobiKey(v, w);
/*     */     
/* 224 */     if (!JACOBI_COEFFICIENTS.containsKey(key)) {
/*     */ 
/*     */       
/* 227 */       List<BigFraction> list = new ArrayList<BigFraction>();
/* 228 */       JACOBI_COEFFICIENTS.put(key, list);
/*     */ 
/*     */       
/* 231 */       list.add(BigFraction.ONE);
/*     */ 
/*     */       
/* 234 */       list.add(new BigFraction(v - w, 2));
/* 235 */       list.add(new BigFraction(2 + v + w, 2));
/*     */     } 
/*     */ 
/*     */     
/* 239 */     return buildPolynomial(degree, JACOBI_COEFFICIENTS.get(key), new RecurrenceCoefficientsGenerator()
/*     */         {
/*     */           public BigFraction[] generate(int k)
/*     */           {
/* 243 */             k++;
/* 244 */             int kvw = k + v + w;
/* 245 */             int twoKvw = kvw + k;
/* 246 */             int twoKvwM1 = twoKvw - 1;
/* 247 */             int twoKvwM2 = twoKvw - 2;
/* 248 */             int den = 2 * k * kvw * twoKvwM2;
/*     */             
/* 250 */             return new BigFraction[] { new BigFraction(twoKvwM1 * (this.val$v * this.val$v - this.val$w * this.val$w), den), new BigFraction(twoKvwM1 * twoKvw * twoKvwM2, den), new BigFraction(2 * (k + this.val$v - 1) * (k + this.val$w - 1) * twoKvw, den) };
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class JacobiKey
/*     */   {
/*     */     private final int v;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final int w;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     JacobiKey(int v, int w) {
/* 274 */       this.v = v;
/* 275 */       this.w = w;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 283 */       return this.v << 16 ^ this.w;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean equals(Object key) {
/* 293 */       if (key == null || !(key instanceof JacobiKey)) {
/* 294 */         return false;
/*     */       }
/*     */       
/* 297 */       JacobiKey otherK = (JacobiKey)key;
/* 298 */       return (this.v == otherK.v && this.w == otherK.w);
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
/*     */   
/*     */   public static double[] shift(double[] coefficients, double shift) {
/* 322 */     int dp1 = coefficients.length;
/* 323 */     double[] newCoefficients = new double[dp1];
/*     */ 
/*     */     
/* 326 */     int[][] coeff = new int[dp1][dp1]; int i;
/* 327 */     for (i = 0; i < dp1; i++) {
/* 328 */       for (int k = 0; k <= i; k++) {
/* 329 */         coeff[i][k] = (int)CombinatoricsUtils.binomialCoefficient(i, k);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 334 */     for (i = 0; i < dp1; i++) {
/* 335 */       newCoefficients[0] = newCoefficients[0] + coefficients[i] * FastMath.pow(shift, i);
/*     */     }
/*     */ 
/*     */     
/* 339 */     int d = dp1 - 1;
/* 340 */     for (int j = 0; j < d; j++) {
/* 341 */       for (int k = j; k < d; k++) {
/* 342 */         newCoefficients[j + 1] = newCoefficients[j + 1] + coeff[k + 1][k - j] * coefficients[k + 1] * FastMath.pow(shift, k - j);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 347 */     return newCoefficients;
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
/*     */   private static PolynomialFunction buildPolynomial(int degree, List<BigFraction> coefficients, RecurrenceCoefficientsGenerator generator) {
/* 360 */     synchronized (coefficients) {
/* 361 */       int maxDegree = (int)FastMath.floor(FastMath.sqrt((2 * coefficients.size()))) - 1;
/* 362 */       if (degree > maxDegree) {
/* 363 */         computeUpToDegree(degree, maxDegree, generator, coefficients);
/*     */       }
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
/* 375 */     int start = degree * (degree + 1) / 2;
/*     */     
/* 377 */     double[] a = new double[degree + 1];
/* 378 */     for (int i = 0; i <= degree; i++) {
/* 379 */       a[i] = ((BigFraction)coefficients.get(start + i)).doubleValue();
/*     */     }
/*     */ 
/*     */     
/* 383 */     return new PolynomialFunction(a);
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
/*     */   private static void computeUpToDegree(int degree, int maxDegree, RecurrenceCoefficientsGenerator generator, List<BigFraction> coefficients) {
/* 397 */     int startK = (maxDegree - 1) * maxDegree / 2;
/* 398 */     for (int k = maxDegree; k < degree; k++) {
/*     */ 
/*     */       
/* 401 */       int startKm1 = startK;
/* 402 */       startK += k;
/*     */ 
/*     */       
/* 405 */       BigFraction[] ai = generator.generate(k);
/*     */       
/* 407 */       BigFraction ck = coefficients.get(startK);
/* 408 */       BigFraction ckm1 = coefficients.get(startKm1);
/*     */ 
/*     */       
/* 411 */       coefficients.add(ck.multiply(ai[0]).subtract(ckm1.multiply(ai[2])));
/*     */ 
/*     */       
/* 414 */       for (int i = 1; i < k; i++) {
/* 415 */         BigFraction bigFraction = ck;
/* 416 */         ck = coefficients.get(startK + i);
/* 417 */         ckm1 = coefficients.get(startKm1 + i);
/* 418 */         coefficients.add(ck.multiply(ai[0]).add(bigFraction.multiply(ai[1])).subtract(ckm1.multiply(ai[2])));
/*     */       } 
/*     */ 
/*     */       
/* 422 */       BigFraction ckPrev = ck;
/* 423 */       ck = coefficients.get(startK + k);
/* 424 */       coefficients.add(ck.multiply(ai[0]).add(ckPrev.multiply(ai[1])));
/*     */ 
/*     */       
/* 427 */       coefficients.add(ck.multiply(ai[1]));
/*     */     } 
/*     */   }
/*     */   
/*     */   private static interface RecurrenceCoefficientsGenerator {
/*     */     BigFraction[] generate(int param1Int);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/analysis/polynomials/PolynomialsUtils.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */