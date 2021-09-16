/*     */ package org.apache.commons.math3.primes;
/*     */ 
/*     */ import java.util.List;
/*     */ import org.apache.commons.math3.exception.MathIllegalArgumentException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Primes
/*     */ {
/*     */   public static boolean isPrime(int n) {
/*  54 */     if (n < 2) {
/*  55 */       return false;
/*     */     }
/*     */     
/*  58 */     for (int p : SmallPrimes.PRIMES) {
/*  59 */       if (0 == n % p) {
/*  60 */         return (n == p);
/*     */       }
/*     */     } 
/*  63 */     return SmallPrimes.millerRabinPrimeTest(n);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int nextPrime(int n) {
/*  74 */     if (n < 0) {
/*  75 */       throw new MathIllegalArgumentException(LocalizedFormats.NUMBER_TOO_SMALL, new Object[] { Integer.valueOf(n), Integer.valueOf(0) });
/*     */     }
/*  77 */     if (n == 2) {
/*  78 */       return 2;
/*     */     }
/*  80 */     n |= 0x1;
/*  81 */     if (n == 1) {
/*  82 */       return 2;
/*     */     }
/*     */     
/*  85 */     if (isPrime(n)) {
/*  86 */       return n;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  91 */     int rem = n % 3;
/*  92 */     if (0 == rem) {
/*  93 */       n += 2;
/*  94 */     } else if (1 == rem) {
/*     */       
/*  96 */       n += 4;
/*     */     } 
/*     */     while (true) {
/*  99 */       if (isPrime(n)) {
/* 100 */         return n;
/*     */       }
/* 102 */       n += 2;
/* 103 */       if (isPrime(n)) {
/* 104 */         return n;
/*     */       }
/* 106 */       n += 4;
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
/*     */   public static List<Integer> primeFactors(int n) {
/* 119 */     if (n < 2) {
/* 120 */       throw new MathIllegalArgumentException(LocalizedFormats.NUMBER_TOO_SMALL, new Object[] { Integer.valueOf(n), Integer.valueOf(2) });
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 125 */     return SmallPrimes.trialDivision(n);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/primes/Primes.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */