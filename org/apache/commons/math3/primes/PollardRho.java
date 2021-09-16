/*     */ package org.apache.commons.math3.primes;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ class PollardRho
/*     */ {
/*     */   public static List<Integer> primeFactors(int n) {
/*  42 */     List<Integer> factors = new ArrayList<Integer>();
/*     */     
/*  44 */     n = SmallPrimes.smallTrialDivision(n, factors);
/*  45 */     if (1 == n) {
/*  46 */       return factors;
/*     */     }
/*     */     
/*  49 */     if (SmallPrimes.millerRabinPrimeTest(n)) {
/*  50 */       factors.add(Integer.valueOf(n));
/*  51 */       return factors;
/*     */     } 
/*     */     
/*  54 */     int divisor = rhoBrent(n);
/*  55 */     factors.add(Integer.valueOf(divisor));
/*  56 */     factors.add(Integer.valueOf(n / divisor));
/*  57 */     return factors;
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
/*     */   static int rhoBrent(int n) {
/*  73 */     int x0 = 2;
/*  74 */     int m = 25;
/*  75 */     int cst = SmallPrimes.PRIMES_LAST;
/*  76 */     int y = 2;
/*  77 */     int r = 1;
/*     */     while (true) {
/*  79 */       int x = y;
/*  80 */       for (int i = 0; i < r; i++) {
/*  81 */         long y2 = y * y;
/*  82 */         y = (int)((y2 + cst) % n);
/*     */       } 
/*  84 */       int k = 0;
/*     */       while (true) {
/*  86 */         int bound = FastMath.min(25, r - k);
/*  87 */         int q = 1;
/*  88 */         for (int j = -3; j < bound; j++) {
/*  89 */           long y2 = y * y;
/*  90 */           y = (int)((y2 + cst) % n);
/*  91 */           long divisor = FastMath.abs(x - y);
/*  92 */           if (0L == divisor) {
/*  93 */             cst += SmallPrimes.PRIMES_LAST;
/*  94 */             k = -25;
/*  95 */             y = 2;
/*  96 */             r = 1;
/*     */             break;
/*     */           } 
/*  99 */           long prod = divisor * q;
/* 100 */           q = (int)(prod % n);
/* 101 */           if (0 == q) {
/* 102 */             return gcdPositive(FastMath.abs((int)divisor), n);
/*     */           }
/*     */         } 
/* 105 */         int out = gcdPositive(FastMath.abs(q), n);
/* 106 */         if (1 != out) {
/* 107 */           return out;
/*     */         }
/* 109 */         k += 25;
/* 110 */         if (k >= r) {
/* 111 */           r = 2 * r;
/*     */         }
/*     */       } 
/*     */       break;
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
/*     */   static int gcdPositive(int a, int b) {
/* 135 */     if (a == 0)
/* 136 */       return b; 
/* 137 */     if (b == 0) {
/* 138 */       return a;
/*     */     }
/*     */ 
/*     */     
/* 142 */     int aTwos = Integer.numberOfTrailingZeros(a);
/* 143 */     a >>= aTwos;
/* 144 */     int bTwos = Integer.numberOfTrailingZeros(b);
/* 145 */     b >>= bTwos;
/* 146 */     int shift = FastMath.min(aTwos, bTwos);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 152 */     while (a != b) {
/* 153 */       int delta = a - b;
/* 154 */       b = FastMath.min(a, b);
/* 155 */       a = FastMath.abs(delta);
/*     */ 
/*     */       
/* 158 */       a >>= Integer.numberOfTrailingZeros(a);
/*     */     } 
/*     */ 
/*     */     
/* 162 */     return a << shift;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/primes/PollardRho.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */