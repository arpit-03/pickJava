/*     */ package org.apache.commons.math3.util;
/*     */ 
/*     */ import java.math.BigInteger;
/*     */ import org.apache.commons.math3.exception.MathArithmeticException;
/*     */ import org.apache.commons.math3.exception.NotPositiveException;
/*     */ import org.apache.commons.math3.exception.NumberIsTooLargeException;
/*     */ import org.apache.commons.math3.exception.util.Localizable;
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
/*     */ public final class ArithmeticUtils
/*     */ {
/*     */   public static int addAndCheck(int x, int y) throws MathArithmeticException {
/*  51 */     long s = x + y;
/*  52 */     if (s < -2147483648L || s > 2147483647L) {
/*  53 */       throw new MathArithmeticException(LocalizedFormats.OVERFLOW_IN_ADDITION, new Object[] { Integer.valueOf(x), Integer.valueOf(y) });
/*     */     }
/*  55 */     return (int)s;
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
/*     */   public static long addAndCheck(long a, long b) throws MathArithmeticException {
/*  68 */     return addAndCheck(a, b, (Localizable)LocalizedFormats.OVERFLOW_IN_ADDITION);
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
/*     */   @Deprecated
/*     */   public static long binomialCoefficient(int n, int k) throws NotPositiveException, NumberIsTooLargeException, MathArithmeticException {
/* 101 */     return CombinatoricsUtils.binomialCoefficient(n, k);
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
/*     */   @Deprecated
/*     */   public static double binomialCoefficientDouble(int n, int k) throws NotPositiveException, NumberIsTooLargeException, MathArithmeticException {
/* 133 */     return CombinatoricsUtils.binomialCoefficientDouble(n, k);
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
/*     */   @Deprecated
/*     */   public static double binomialCoefficientLog(int n, int k) throws NotPositiveException, NumberIsTooLargeException, MathArithmeticException {
/* 161 */     return CombinatoricsUtils.binomialCoefficientLog(n, k);
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
/*     */   @Deprecated
/*     */   public static long factorial(int n) throws NotPositiveException, MathArithmeticException {
/* 191 */     return CombinatoricsUtils.factorial(n);
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
/*     */   @Deprecated
/*     */   public static double factorialDouble(int n) throws NotPositiveException {
/* 210 */     return CombinatoricsUtils.factorialDouble(n);
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
/*     */   @Deprecated
/*     */   public static double factorialLog(int n) throws NotPositiveException {
/* 223 */     return CombinatoricsUtils.factorialLog(n);
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
/*     */   public static int gcd(int p, int q) throws MathArithmeticException {
/* 255 */     int a = p;
/* 256 */     int b = q;
/* 257 */     if (a == 0 || b == 0) {
/*     */       
/* 259 */       if (a == Integer.MIN_VALUE || b == Integer.MIN_VALUE)
/*     */       {
/* 261 */         throw new MathArithmeticException(LocalizedFormats.GCD_OVERFLOW_32_BITS, new Object[] { Integer.valueOf(p), Integer.valueOf(q) });
/*     */       }
/*     */       
/* 264 */       return FastMath.abs(a + b);
/*     */     } 
/*     */     
/* 267 */     long al = a;
/* 268 */     long bl = b;
/* 269 */     boolean useLong = false;
/* 270 */     if (a < 0) {
/* 271 */       if (Integer.MIN_VALUE == a) {
/* 272 */         useLong = true;
/*     */       } else {
/* 274 */         a = -a;
/*     */       } 
/* 276 */       al = -al;
/*     */     } 
/* 278 */     if (b < 0) {
/* 279 */       if (Integer.MIN_VALUE == b) {
/* 280 */         useLong = true;
/*     */       } else {
/* 282 */         b = -b;
/*     */       } 
/* 284 */       bl = -bl;
/*     */     } 
/* 286 */     if (useLong) {
/* 287 */       if (al == bl) {
/* 288 */         throw new MathArithmeticException(LocalizedFormats.GCD_OVERFLOW_32_BITS, new Object[] { Integer.valueOf(p), Integer.valueOf(q) });
/*     */       }
/*     */       
/* 291 */       long blbu = bl;
/* 292 */       bl = al;
/* 293 */       al = blbu % al;
/* 294 */       if (al == 0L) {
/* 295 */         if (bl > 2147483647L) {
/* 296 */           throw new MathArithmeticException(LocalizedFormats.GCD_OVERFLOW_32_BITS, new Object[] { Integer.valueOf(p), Integer.valueOf(q) });
/*     */         }
/*     */         
/* 299 */         return (int)bl;
/*     */       } 
/* 301 */       blbu = bl;
/*     */ 
/*     */       
/* 304 */       b = (int)al;
/* 305 */       a = (int)(blbu % al);
/*     */     } 
/*     */     
/* 308 */     return gcdPositive(a, b);
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
/*     */   private static int gcdPositive(int a, int b) {
/* 332 */     if (a == 0) {
/* 333 */       return b;
/*     */     }
/* 335 */     if (b == 0) {
/* 336 */       return a;
/*     */     }
/*     */ 
/*     */     
/* 340 */     int aTwos = Integer.numberOfTrailingZeros(a);
/* 341 */     a >>= aTwos;
/* 342 */     int bTwos = Integer.numberOfTrailingZeros(b);
/* 343 */     b >>= bTwos;
/* 344 */     int shift = FastMath.min(aTwos, bTwos);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 352 */     while (a != b) {
/* 353 */       int delta = a - b;
/* 354 */       b = Math.min(a, b);
/* 355 */       a = Math.abs(delta);
/*     */ 
/*     */       
/* 358 */       a >>= Integer.numberOfTrailingZeros(a);
/*     */     } 
/*     */ 
/*     */     
/* 362 */     return a << shift;
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
/*     */   public static long gcd(long p, long q) throws MathArithmeticException {
/* 395 */     long u = p;
/* 396 */     long v = q;
/* 397 */     if (u == 0L || v == 0L) {
/* 398 */       if (u == Long.MIN_VALUE || v == Long.MIN_VALUE) {
/* 399 */         throw new MathArithmeticException(LocalizedFormats.GCD_OVERFLOW_64_BITS, new Object[] { Long.valueOf(p), Long.valueOf(q) });
/*     */       }
/*     */       
/* 402 */       return FastMath.abs(u) + FastMath.abs(v);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 409 */     if (u > 0L) {
/* 410 */       u = -u;
/*     */     }
/* 412 */     if (v > 0L) {
/* 413 */       v = -v;
/*     */     }
/*     */     
/* 416 */     int k = 0;
/* 417 */     while ((u & 0x1L) == 0L && (v & 0x1L) == 0L && k < 63) {
/*     */       
/* 419 */       u /= 2L;
/* 420 */       v /= 2L;
/* 421 */       k++;
/*     */     } 
/* 423 */     if (k == 63) {
/* 424 */       throw new MathArithmeticException(LocalizedFormats.GCD_OVERFLOW_64_BITS, new Object[] { Long.valueOf(p), Long.valueOf(q) });
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 429 */     long t = ((u & 0x1L) == 1L) ? v : -(u / 2L);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     while (true) {
/* 435 */       while ((t & 0x1L) == 0L) {
/* 436 */         t /= 2L;
/*     */       }
/*     */       
/* 439 */       if (t > 0L) {
/* 440 */         u = -t;
/*     */       } else {
/* 442 */         v = t;
/*     */       } 
/*     */       
/* 445 */       t = (v - u) / 2L;
/*     */ 
/*     */       
/* 448 */       if (t == 0L) {
/* 449 */         return -u * (1L << k);
/*     */       }
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
/*     */   
/*     */   public static int lcm(int a, int b) throws MathArithmeticException {
/* 475 */     if (a == 0 || b == 0) {
/* 476 */       return 0;
/*     */     }
/* 478 */     int lcm = FastMath.abs(mulAndCheck(a / gcd(a, b), b));
/* 479 */     if (lcm == Integer.MIN_VALUE) {
/* 480 */       throw new MathArithmeticException(LocalizedFormats.LCM_OVERFLOW_32_BITS, new Object[] { Integer.valueOf(a), Integer.valueOf(b) });
/*     */     }
/*     */     
/* 483 */     return lcm;
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
/*     */   public static long lcm(long a, long b) throws MathArithmeticException {
/* 509 */     if (a == 0L || b == 0L) {
/* 510 */       return 0L;
/*     */     }
/* 512 */     long lcm = FastMath.abs(mulAndCheck(a / gcd(a, b), b));
/* 513 */     if (lcm == Long.MIN_VALUE) {
/* 514 */       throw new MathArithmeticException(LocalizedFormats.LCM_OVERFLOW_64_BITS, new Object[] { Long.valueOf(a), Long.valueOf(b) });
/*     */     }
/*     */     
/* 517 */     return lcm;
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
/*     */   public static int mulAndCheck(int x, int y) throws MathArithmeticException {
/* 531 */     long m = x * y;
/* 532 */     if (m < -2147483648L || m > 2147483647L) {
/* 533 */       throw new MathArithmeticException();
/*     */     }
/* 535 */     return (int)m;
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
/*     */   public static long mulAndCheck(long a, long b) throws MathArithmeticException {
/*     */     long ret;
/* 550 */     if (a > b) {
/*     */       
/* 552 */       ret = mulAndCheck(b, a);
/*     */     }
/* 554 */     else if (a < 0L) {
/* 555 */       if (b < 0L) {
/*     */         
/* 557 */         if (a >= Long.MAX_VALUE / b) {
/* 558 */           ret = a * b;
/*     */         } else {
/* 560 */           throw new MathArithmeticException();
/*     */         } 
/* 562 */       } else if (b > 0L) {
/*     */         
/* 564 */         if (Long.MIN_VALUE / b <= a) {
/* 565 */           ret = a * b;
/*     */         } else {
/* 567 */           throw new MathArithmeticException();
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 572 */         ret = 0L;
/*     */       } 
/* 574 */     } else if (a > 0L) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 579 */       if (a <= Long.MAX_VALUE / b) {
/* 580 */         ret = a * b;
/*     */       } else {
/* 582 */         throw new MathArithmeticException();
/*     */       } 
/*     */     } else {
/*     */       
/* 586 */       ret = 0L;
/*     */     } 
/*     */     
/* 589 */     return ret;
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
/*     */   public static int subAndCheck(int x, int y) throws MathArithmeticException {
/* 603 */     long s = x - y;
/* 604 */     if (s < -2147483648L || s > 2147483647L) {
/* 605 */       throw new MathArithmeticException(LocalizedFormats.OVERFLOW_IN_SUBTRACTION, new Object[] { Integer.valueOf(x), Integer.valueOf(y) });
/*     */     }
/* 607 */     return (int)s;
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
/*     */   public static long subAndCheck(long a, long b) throws MathArithmeticException {
/*     */     long ret;
/* 622 */     if (b == Long.MIN_VALUE) {
/* 623 */       if (a < 0L) {
/* 624 */         ret = a - b;
/*     */       } else {
/* 626 */         throw new MathArithmeticException(LocalizedFormats.OVERFLOW_IN_ADDITION, new Object[] { Long.valueOf(a), Long.valueOf(-b) });
/*     */       } 
/*     */     } else {
/*     */       
/* 630 */       ret = addAndCheck(a, -b, (Localizable)LocalizedFormats.OVERFLOW_IN_ADDITION);
/*     */     } 
/* 632 */     return ret;
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
/*     */   public static int pow(int k, int e) throws NotPositiveException, MathArithmeticException {
/* 648 */     if (e < 0) {
/* 649 */       throw new NotPositiveException(LocalizedFormats.EXPONENT, Integer.valueOf(e));
/*     */     }
/*     */     
/*     */     try {
/* 653 */       int exp = e;
/* 654 */       int result = 1;
/* 655 */       int k2p = k;
/*     */       while (true) {
/* 657 */         if ((exp & 0x1) != 0) {
/* 658 */           result = mulAndCheck(result, k2p);
/*     */         }
/*     */         
/* 661 */         exp >>= 1;
/* 662 */         if (exp == 0) {
/*     */           break;
/*     */         }
/*     */         
/* 666 */         k2p = mulAndCheck(k2p, k2p);
/*     */       } 
/*     */       
/* 669 */       return result;
/* 670 */     } catch (MathArithmeticException mae) {
/*     */       
/* 672 */       mae.getContext().addMessage((Localizable)LocalizedFormats.OVERFLOW, new Object[0]);
/* 673 */       mae.getContext().addMessage((Localizable)LocalizedFormats.BASE, new Object[] { Integer.valueOf(k) });
/* 674 */       mae.getContext().addMessage((Localizable)LocalizedFormats.EXPONENT, new Object[] { Integer.valueOf(e) });
/*     */ 
/*     */       
/* 677 */       throw mae;
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
/*     */   @Deprecated
/*     */   public static int pow(int k, long e) throws NotPositiveException {
/* 692 */     if (e < 0L) {
/* 693 */       throw new NotPositiveException(LocalizedFormats.EXPONENT, Long.valueOf(e));
/*     */     }
/*     */     
/* 696 */     int result = 1;
/* 697 */     int k2p = k;
/* 698 */     while (e != 0L) {
/* 699 */       if ((e & 0x1L) != 0L) {
/* 700 */         result *= k2p;
/*     */       }
/* 702 */       k2p *= k2p;
/* 703 */       e >>= 1L;
/*     */     } 
/*     */     
/* 706 */     return result;
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
/*     */   public static long pow(long k, int e) throws NotPositiveException, MathArithmeticException {
/* 722 */     if (e < 0) {
/* 723 */       throw new NotPositiveException(LocalizedFormats.EXPONENT, Integer.valueOf(e));
/*     */     }
/*     */     
/*     */     try {
/* 727 */       int exp = e;
/* 728 */       long result = 1L;
/* 729 */       long k2p = k;
/*     */       while (true) {
/* 731 */         if ((exp & 0x1) != 0) {
/* 732 */           result = mulAndCheck(result, k2p);
/*     */         }
/*     */         
/* 735 */         exp >>= 1;
/* 736 */         if (exp == 0) {
/*     */           break;
/*     */         }
/*     */         
/* 740 */         k2p = mulAndCheck(k2p, k2p);
/*     */       } 
/*     */       
/* 743 */       return result;
/* 744 */     } catch (MathArithmeticException mae) {
/*     */       
/* 746 */       mae.getContext().addMessage((Localizable)LocalizedFormats.OVERFLOW, new Object[0]);
/* 747 */       mae.getContext().addMessage((Localizable)LocalizedFormats.BASE, new Object[] { Long.valueOf(k) });
/* 748 */       mae.getContext().addMessage((Localizable)LocalizedFormats.EXPONENT, new Object[] { Integer.valueOf(e) });
/*     */ 
/*     */       
/* 751 */       throw mae;
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
/*     */   @Deprecated
/*     */   public static long pow(long k, long e) throws NotPositiveException {
/* 766 */     if (e < 0L) {
/* 767 */       throw new NotPositiveException(LocalizedFormats.EXPONENT, Long.valueOf(e));
/*     */     }
/*     */     
/* 770 */     long result = 1L;
/* 771 */     long k2p = k;
/* 772 */     while (e != 0L) {
/* 773 */       if ((e & 0x1L) != 0L) {
/* 774 */         result *= k2p;
/*     */       }
/* 776 */       k2p *= k2p;
/* 777 */       e >>= 1L;
/*     */     } 
/*     */     
/* 780 */     return result;
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
/*     */   public static BigInteger pow(BigInteger k, int e) throws NotPositiveException {
/* 792 */     if (e < 0) {
/* 793 */       throw new NotPositiveException(LocalizedFormats.EXPONENT, Integer.valueOf(e));
/*     */     }
/*     */     
/* 796 */     return k.pow(e);
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
/*     */   public static BigInteger pow(BigInteger k, long e) throws NotPositiveException {
/* 808 */     if (e < 0L) {
/* 809 */       throw new NotPositiveException(LocalizedFormats.EXPONENT, Long.valueOf(e));
/*     */     }
/*     */     
/* 812 */     BigInteger result = BigInteger.ONE;
/* 813 */     BigInteger k2p = k;
/* 814 */     while (e != 0L) {
/* 815 */       if ((e & 0x1L) != 0L) {
/* 816 */         result = result.multiply(k2p);
/*     */       }
/* 818 */       k2p = k2p.multiply(k2p);
/* 819 */       e >>= 1L;
/*     */     } 
/*     */     
/* 822 */     return result;
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
/*     */   public static BigInteger pow(BigInteger k, BigInteger e) throws NotPositiveException {
/* 835 */     if (e.compareTo(BigInteger.ZERO) < 0) {
/* 836 */       throw new NotPositiveException(LocalizedFormats.EXPONENT, e);
/*     */     }
/*     */     
/* 839 */     BigInteger result = BigInteger.ONE;
/* 840 */     BigInteger k2p = k;
/* 841 */     while (!BigInteger.ZERO.equals(e)) {
/* 842 */       if (e.testBit(0)) {
/* 843 */         result = result.multiply(k2p);
/*     */       }
/* 845 */       k2p = k2p.multiply(k2p);
/* 846 */       e = e.shiftRight(1);
/*     */     } 
/*     */     
/* 849 */     return result;
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
/*     */   @Deprecated
/*     */   public static long stirlingS2(int n, int k) throws NotPositiveException, NumberIsTooLargeException, MathArithmeticException {
/* 875 */     return CombinatoricsUtils.stirlingS2(n, k);
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
/*     */   private static long addAndCheck(long a, long b, Localizable pattern) throws MathArithmeticException {
/* 891 */     long result = a + b;
/* 892 */     if (((((a ^ b) < 0L) ? 1 : 0) | (((a ^ result) >= 0L) ? 1 : 0)) == 0) {
/* 893 */       throw new MathArithmeticException(pattern, new Object[] { Long.valueOf(a), Long.valueOf(b) });
/*     */     }
/* 895 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isPowerOfTwo(long n) {
/* 905 */     return (n > 0L && (n & n - 1L) == 0L);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/util/ArithmeticUtils.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */