/*      */ package org.apache.commons.math3.fraction;
/*      */ 
/*      */ import java.io.Serializable;
/*      */ import java.math.BigDecimal;
/*      */ import java.math.BigInteger;
/*      */ import org.apache.commons.math3.Field;
/*      */ import org.apache.commons.math3.FieldElement;
/*      */ import org.apache.commons.math3.exception.MathArithmeticException;
/*      */ import org.apache.commons.math3.exception.MathIllegalArgumentException;
/*      */ import org.apache.commons.math3.exception.NullArgumentException;
/*      */ import org.apache.commons.math3.exception.ZeroException;
/*      */ import org.apache.commons.math3.exception.util.Localizable;
/*      */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*      */ import org.apache.commons.math3.util.ArithmeticUtils;
/*      */ import org.apache.commons.math3.util.FastMath;
/*      */ import org.apache.commons.math3.util.MathUtils;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class BigFraction
/*      */   extends Number
/*      */   implements FieldElement<BigFraction>, Comparable<BigFraction>, Serializable
/*      */ {
/*   44 */   public static final BigFraction TWO = new BigFraction(2);
/*      */ 
/*      */   
/*   47 */   public static final BigFraction ONE = new BigFraction(1);
/*      */ 
/*      */   
/*   50 */   public static final BigFraction ZERO = new BigFraction(0);
/*      */ 
/*      */   
/*   53 */   public static final BigFraction MINUS_ONE = new BigFraction(-1);
/*      */ 
/*      */   
/*   56 */   public static final BigFraction FOUR_FIFTHS = new BigFraction(4, 5);
/*      */ 
/*      */   
/*   59 */   public static final BigFraction ONE_FIFTH = new BigFraction(1, 5);
/*      */ 
/*      */   
/*   62 */   public static final BigFraction ONE_HALF = new BigFraction(1, 2);
/*      */ 
/*      */   
/*   65 */   public static final BigFraction ONE_QUARTER = new BigFraction(1, 4);
/*      */ 
/*      */   
/*   68 */   public static final BigFraction ONE_THIRD = new BigFraction(1, 3);
/*      */ 
/*      */   
/*   71 */   public static final BigFraction THREE_FIFTHS = new BigFraction(3, 5);
/*      */ 
/*      */   
/*   74 */   public static final BigFraction THREE_QUARTERS = new BigFraction(3, 4);
/*      */ 
/*      */   
/*   77 */   public static final BigFraction TWO_FIFTHS = new BigFraction(2, 5);
/*      */ 
/*      */   
/*   80 */   public static final BigFraction TWO_QUARTERS = new BigFraction(2, 4);
/*      */ 
/*      */   
/*   83 */   public static final BigFraction TWO_THIRDS = new BigFraction(2, 3);
/*      */ 
/*      */   
/*      */   private static final long serialVersionUID = -5630213147331578515L;
/*      */ 
/*      */   
/*   89 */   private static final BigInteger ONE_HUNDRED = BigInteger.valueOf(100L);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final BigInteger numerator;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final BigInteger denominator;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigFraction(BigInteger num) {
/*  107 */     this(num, BigInteger.ONE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigFraction(BigInteger num, BigInteger den) {
/*  120 */     MathUtils.checkNotNull(num, (Localizable)LocalizedFormats.NUMERATOR, new Object[0]);
/*  121 */     MathUtils.checkNotNull(den, (Localizable)LocalizedFormats.DENOMINATOR, new Object[0]);
/*  122 */     if (den.signum() == 0) {
/*  123 */       throw new ZeroException(LocalizedFormats.ZERO_DENOMINATOR, new Object[0]);
/*      */     }
/*  125 */     if (num.signum() == 0) {
/*  126 */       this.numerator = BigInteger.ZERO;
/*  127 */       this.denominator = BigInteger.ONE;
/*      */     }
/*      */     else {
/*      */       
/*  131 */       BigInteger gcd = num.gcd(den);
/*  132 */       if (BigInteger.ONE.compareTo(gcd) < 0) {
/*  133 */         num = num.divide(gcd);
/*  134 */         den = den.divide(gcd);
/*      */       } 
/*      */ 
/*      */       
/*  138 */       if (den.signum() == -1) {
/*  139 */         num = num.negate();
/*  140 */         den = den.negate();
/*      */       } 
/*      */ 
/*      */       
/*  144 */       this.numerator = num;
/*  145 */       this.denominator = den;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigFraction(double value) throws MathIllegalArgumentException {
/*  172 */     if (Double.isNaN(value)) {
/*  173 */       throw new MathIllegalArgumentException(LocalizedFormats.NAN_VALUE_CONVERSION, new Object[0]);
/*      */     }
/*  175 */     if (Double.isInfinite(value)) {
/*  176 */       throw new MathIllegalArgumentException(LocalizedFormats.INFINITE_VALUE_CONVERSION, new Object[0]);
/*      */     }
/*      */ 
/*      */     
/*  180 */     long bits = Double.doubleToLongBits(value);
/*  181 */     long sign = bits & Long.MIN_VALUE;
/*  182 */     long exponent = bits & 0x7FF0000000000000L;
/*  183 */     long m = bits & 0xFFFFFFFFFFFFFL;
/*  184 */     if (exponent != 0L)
/*      */     {
/*  186 */       m |= 0x10000000000000L;
/*      */     }
/*  188 */     if (sign != 0L) {
/*  189 */       m = -m;
/*      */     }
/*  191 */     int k = (int)(exponent >> 52L) - 1075;
/*  192 */     while ((m & 0x1FFFFFFFFFFFFEL) != 0L && (m & 0x1L) == 0L) {
/*  193 */       m >>= 1L;
/*  194 */       k++;
/*      */     } 
/*      */     
/*  197 */     if (k < 0) {
/*  198 */       this.numerator = BigInteger.valueOf(m);
/*  199 */       this.denominator = BigInteger.ZERO.flipBit(-k);
/*      */     } else {
/*  201 */       this.numerator = BigInteger.valueOf(m).multiply(BigInteger.ZERO.flipBit(k));
/*  202 */       this.denominator = BigInteger.ONE;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigFraction(double value, double epsilon, int maxIterations) throws FractionConversionException {
/*  231 */     this(value, epsilon, 2147483647, maxIterations);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private BigFraction(double value, double epsilon, int maxDenominator, int maxIterations) throws FractionConversionException {
/*  271 */     long overflow = 2147483647L;
/*  272 */     double r0 = value;
/*  273 */     long a0 = (long)FastMath.floor(r0);
/*      */     
/*  275 */     if (FastMath.abs(a0) > overflow) {
/*  276 */       throw new FractionConversionException(value, a0, 1L);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  281 */     if (FastMath.abs(a0 - value) < epsilon) {
/*  282 */       this.numerator = BigInteger.valueOf(a0);
/*  283 */       this.denominator = BigInteger.ONE;
/*      */       
/*      */       return;
/*      */     } 
/*  287 */     long p0 = 1L;
/*  288 */     long q0 = 0L;
/*  289 */     long p1 = a0;
/*  290 */     long q1 = 1L;
/*      */     
/*  292 */     long p2 = 0L;
/*  293 */     long q2 = 1L;
/*      */     
/*  295 */     int n = 0;
/*  296 */     boolean stop = false;
/*      */     do {
/*  298 */       n++;
/*  299 */       double r1 = 1.0D / (r0 - a0);
/*  300 */       long a1 = (long)FastMath.floor(r1);
/*  301 */       p2 = a1 * p1 + p0;
/*  302 */       q2 = a1 * q1 + q0;
/*  303 */       if (p2 > overflow || q2 > overflow) {
/*      */ 
/*      */         
/*  306 */         if (epsilon == 0.0D && FastMath.abs(q1) < maxDenominator) {
/*      */           break;
/*      */         }
/*  309 */         throw new FractionConversionException(value, p2, q2);
/*      */       } 
/*      */       
/*  312 */       double convergent = p2 / q2;
/*  313 */       if (n < maxIterations && FastMath.abs(convergent - value) > epsilon && q2 < maxDenominator) {
/*      */ 
/*      */         
/*  316 */         p0 = p1;
/*  317 */         p1 = p2;
/*  318 */         q0 = q1;
/*  319 */         q1 = q2;
/*  320 */         a0 = a1;
/*  321 */         r0 = r1;
/*      */       } else {
/*  323 */         stop = true;
/*      */       } 
/*  325 */     } while (!stop);
/*      */     
/*  327 */     if (n >= maxIterations) {
/*  328 */       throw new FractionConversionException(value, maxIterations);
/*      */     }
/*      */     
/*  331 */     if (q2 < maxDenominator) {
/*  332 */       this.numerator = BigInteger.valueOf(p2);
/*  333 */       this.denominator = BigInteger.valueOf(q2);
/*      */     } else {
/*  335 */       this.numerator = BigInteger.valueOf(p1);
/*  336 */       this.denominator = BigInteger.valueOf(q1);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigFraction(double value, int maxDenominator) throws FractionConversionException {
/*  359 */     this(value, 0.0D, maxDenominator, 100);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigFraction(int num) {
/*  372 */     this(BigInteger.valueOf(num), BigInteger.ONE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigFraction(int num, int den) {
/*  387 */     this(BigInteger.valueOf(num), BigInteger.valueOf(den));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigFraction(long num) {
/*  399 */     this(BigInteger.valueOf(num), BigInteger.ONE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigFraction(long num, long den) {
/*  414 */     this(BigInteger.valueOf(num), BigInteger.valueOf(den));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static BigFraction getReducedFraction(int numerator, int denominator) {
/*  438 */     if (numerator == 0) {
/*  439 */       return ZERO;
/*      */     }
/*      */     
/*  442 */     return new BigFraction(numerator, denominator);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigFraction abs() {
/*  453 */     return (this.numerator.signum() == 1) ? this : negate();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigFraction add(BigInteger bg) throws NullArgumentException {
/*  469 */     MathUtils.checkNotNull(bg);
/*      */     
/*  471 */     if (this.numerator.signum() == 0) {
/*  472 */       return new BigFraction(bg);
/*      */     }
/*  474 */     if (bg.signum() == 0) {
/*  475 */       return this;
/*      */     }
/*      */     
/*  478 */     return new BigFraction(this.numerator.add(this.denominator.multiply(bg)), this.denominator);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigFraction add(int i) {
/*  492 */     return add(BigInteger.valueOf(i));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigFraction add(long l) {
/*  506 */     return add(BigInteger.valueOf(l));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigFraction add(BigFraction fraction) {
/*  521 */     if (fraction == null) {
/*  522 */       throw new NullArgumentException(LocalizedFormats.FRACTION, new Object[0]);
/*      */     }
/*  524 */     if (fraction.numerator.signum() == 0) {
/*  525 */       return this;
/*      */     }
/*  527 */     if (this.numerator.signum() == 0) {
/*  528 */       return fraction;
/*      */     }
/*      */     
/*  531 */     BigInteger num = null;
/*  532 */     BigInteger den = null;
/*      */     
/*  534 */     if (this.denominator.equals(fraction.denominator)) {
/*  535 */       num = this.numerator.add(fraction.numerator);
/*  536 */       den = this.denominator;
/*      */     } else {
/*  538 */       num = this.numerator.multiply(fraction.denominator).add(fraction.numerator.multiply(this.denominator));
/*  539 */       den = this.denominator.multiply(fraction.denominator);
/*      */     } 
/*      */     
/*  542 */     if (num.signum() == 0) {
/*  543 */       return ZERO;
/*      */     }
/*      */     
/*  546 */     return new BigFraction(num, den);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigDecimal bigDecimalValue() {
/*  563 */     return (new BigDecimal(this.numerator)).divide(new BigDecimal(this.denominator));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigDecimal bigDecimalValue(int roundingMode) {
/*  582 */     return (new BigDecimal(this.numerator)).divide(new BigDecimal(this.denominator), roundingMode);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigDecimal bigDecimalValue(int scale, int roundingMode) {
/*  601 */     return (new BigDecimal(this.numerator)).divide(new BigDecimal(this.denominator), scale, roundingMode);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int compareTo(BigFraction object) {
/*  616 */     int lhsSigNum = this.numerator.signum();
/*  617 */     int rhsSigNum = object.numerator.signum();
/*      */     
/*  619 */     if (lhsSigNum != rhsSigNum) {
/*  620 */       return (lhsSigNum > rhsSigNum) ? 1 : -1;
/*      */     }
/*  622 */     if (lhsSigNum == 0) {
/*  623 */       return 0;
/*      */     }
/*      */     
/*  626 */     BigInteger nOd = this.numerator.multiply(object.denominator);
/*  627 */     BigInteger dOn = this.denominator.multiply(object.numerator);
/*  628 */     return nOd.compareTo(dOn);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigFraction divide(BigInteger bg) {
/*  643 */     if (bg == null) {
/*  644 */       throw new NullArgumentException(LocalizedFormats.FRACTION, new Object[0]);
/*      */     }
/*  646 */     if (bg.signum() == 0) {
/*  647 */       throw new MathArithmeticException(LocalizedFormats.ZERO_DENOMINATOR, new Object[0]);
/*      */     }
/*  649 */     if (this.numerator.signum() == 0) {
/*  650 */       return ZERO;
/*      */     }
/*  652 */     return new BigFraction(this.numerator, this.denominator.multiply(bg));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigFraction divide(int i) {
/*  666 */     return divide(BigInteger.valueOf(i));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigFraction divide(long l) {
/*  680 */     return divide(BigInteger.valueOf(l));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigFraction divide(BigFraction fraction) {
/*  695 */     if (fraction == null) {
/*  696 */       throw new NullArgumentException(LocalizedFormats.FRACTION, new Object[0]);
/*      */     }
/*  698 */     if (fraction.numerator.signum() == 0) {
/*  699 */       throw new MathArithmeticException(LocalizedFormats.ZERO_DENOMINATOR, new Object[0]);
/*      */     }
/*  701 */     if (this.numerator.signum() == 0) {
/*  702 */       return ZERO;
/*      */     }
/*      */     
/*  705 */     return multiply(fraction.reciprocal());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double doubleValue() {
/*  719 */     double result = this.numerator.doubleValue() / this.denominator.doubleValue();
/*  720 */     if (Double.isNaN(result)) {
/*      */ 
/*      */       
/*  723 */       int shift = FastMath.max(this.numerator.bitLength(), this.denominator.bitLength()) - FastMath.getExponent(Double.MAX_VALUE);
/*      */       
/*  725 */       result = this.numerator.shiftRight(shift).doubleValue() / this.denominator.shiftRight(shift).doubleValue();
/*      */     } 
/*      */     
/*  728 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean equals(Object other) {
/*  748 */     boolean ret = false;
/*      */     
/*  750 */     if (this == other) {
/*  751 */       ret = true;
/*  752 */     } else if (other instanceof BigFraction) {
/*  753 */       BigFraction rhs = ((BigFraction)other).reduce();
/*  754 */       BigFraction thisOne = reduce();
/*  755 */       ret = (thisOne.numerator.equals(rhs.numerator) && thisOne.denominator.equals(rhs.denominator));
/*      */     } 
/*      */     
/*  758 */     return ret;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float floatValue() {
/*  772 */     float result = this.numerator.floatValue() / this.denominator.floatValue();
/*  773 */     if (Double.isNaN(result)) {
/*      */ 
/*      */       
/*  776 */       int shift = FastMath.max(this.numerator.bitLength(), this.denominator.bitLength()) - FastMath.getExponent(Float.MAX_VALUE);
/*      */       
/*  778 */       result = this.numerator.shiftRight(shift).floatValue() / this.denominator.shiftRight(shift).floatValue();
/*      */     } 
/*      */     
/*  781 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigInteger getDenominator() {
/*  792 */     return this.denominator;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getDenominatorAsInt() {
/*  803 */     return this.denominator.intValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long getDenominatorAsLong() {
/*  814 */     return this.denominator.longValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigInteger getNumerator() {
/*  825 */     return this.numerator;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getNumeratorAsInt() {
/*  836 */     return this.numerator.intValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long getNumeratorAsLong() {
/*  847 */     return this.numerator.longValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int hashCode() {
/*  860 */     return 37 * (629 + this.numerator.hashCode()) + this.denominator.hashCode();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int intValue() {
/*  874 */     return this.numerator.divide(this.denominator).intValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long longValue() {
/*  888 */     return this.numerator.divide(this.denominator).longValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigFraction multiply(BigInteger bg) {
/*  902 */     if (bg == null) {
/*  903 */       throw new NullArgumentException();
/*      */     }
/*  905 */     if (this.numerator.signum() == 0 || bg.signum() == 0) {
/*  906 */       return ZERO;
/*      */     }
/*  908 */     return new BigFraction(bg.multiply(this.numerator), this.denominator);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigFraction multiply(int i) {
/*  922 */     if (i == 0 || this.numerator.signum() == 0) {
/*  923 */       return ZERO;
/*      */     }
/*      */     
/*  926 */     return multiply(BigInteger.valueOf(i));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigFraction multiply(long l) {
/*  940 */     if (l == 0L || this.numerator.signum() == 0) {
/*  941 */       return ZERO;
/*      */     }
/*      */     
/*  944 */     return multiply(BigInteger.valueOf(l));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigFraction multiply(BigFraction fraction) {
/*  958 */     if (fraction == null) {
/*  959 */       throw new NullArgumentException(LocalizedFormats.FRACTION, new Object[0]);
/*      */     }
/*  961 */     if (this.numerator.signum() == 0 || fraction.numerator.signum() == 0)
/*      */     {
/*  963 */       return ZERO;
/*      */     }
/*  965 */     return new BigFraction(this.numerator.multiply(fraction.numerator), this.denominator.multiply(fraction.denominator));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigFraction negate() {
/*  978 */     return new BigFraction(this.numerator.negate(), this.denominator);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double percentageValue() {
/*  990 */     return multiply(ONE_HUNDRED).doubleValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigFraction pow(int exponent) {
/* 1005 */     if (exponent == 0) {
/* 1006 */       return ONE;
/*      */     }
/* 1008 */     if (this.numerator.signum() == 0) {
/* 1009 */       return this;
/*      */     }
/*      */     
/* 1012 */     if (exponent < 0) {
/* 1013 */       return new BigFraction(this.denominator.pow(-exponent), this.numerator.pow(-exponent));
/*      */     }
/* 1015 */     return new BigFraction(this.numerator.pow(exponent), this.denominator.pow(exponent));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigFraction pow(long exponent) {
/* 1029 */     if (exponent == 0L) {
/* 1030 */       return ONE;
/*      */     }
/* 1032 */     if (this.numerator.signum() == 0) {
/* 1033 */       return this;
/*      */     }
/*      */     
/* 1036 */     if (exponent < 0L) {
/* 1037 */       return new BigFraction(ArithmeticUtils.pow(this.denominator, -exponent), ArithmeticUtils.pow(this.numerator, -exponent));
/*      */     }
/*      */     
/* 1040 */     return new BigFraction(ArithmeticUtils.pow(this.numerator, exponent), ArithmeticUtils.pow(this.denominator, exponent));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigFraction pow(BigInteger exponent) {
/* 1055 */     if (exponent.signum() == 0) {
/* 1056 */       return ONE;
/*      */     }
/* 1058 */     if (this.numerator.signum() == 0) {
/* 1059 */       return this;
/*      */     }
/*      */     
/* 1062 */     if (exponent.signum() == -1) {
/* 1063 */       BigInteger eNeg = exponent.negate();
/* 1064 */       return new BigFraction(ArithmeticUtils.pow(this.denominator, eNeg), ArithmeticUtils.pow(this.numerator, eNeg));
/*      */     } 
/*      */     
/* 1067 */     return new BigFraction(ArithmeticUtils.pow(this.numerator, exponent), ArithmeticUtils.pow(this.denominator, exponent));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double pow(double exponent) {
/* 1082 */     return FastMath.pow(this.numerator.doubleValue(), exponent) / FastMath.pow(this.denominator.doubleValue(), exponent);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigFraction reciprocal() {
/* 1094 */     return new BigFraction(this.denominator, this.numerator);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigFraction reduce() {
/* 1106 */     BigInteger gcd = this.numerator.gcd(this.denominator);
/*      */     
/* 1108 */     if (BigInteger.ONE.compareTo(gcd) < 0) {
/* 1109 */       return new BigFraction(this.numerator.divide(gcd), this.denominator.divide(gcd));
/*      */     }
/* 1111 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigFraction subtract(BigInteger bg) {
/* 1126 */     if (bg == null) {
/* 1127 */       throw new NullArgumentException();
/*      */     }
/* 1129 */     if (bg.signum() == 0) {
/* 1130 */       return this;
/*      */     }
/* 1132 */     if (this.numerator.signum() == 0) {
/* 1133 */       return new BigFraction(bg.negate());
/*      */     }
/*      */     
/* 1136 */     return new BigFraction(this.numerator.subtract(this.denominator.multiply(bg)), this.denominator);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigFraction subtract(int i) {
/* 1149 */     return subtract(BigInteger.valueOf(i));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigFraction subtract(long l) {
/* 1162 */     return subtract(BigInteger.valueOf(l));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigFraction subtract(BigFraction fraction) {
/* 1176 */     if (fraction == null) {
/* 1177 */       throw new NullArgumentException(LocalizedFormats.FRACTION, new Object[0]);
/*      */     }
/* 1179 */     if (fraction.numerator.signum() == 0) {
/* 1180 */       return this;
/*      */     }
/* 1182 */     if (this.numerator.signum() == 0) {
/* 1183 */       return fraction.negate();
/*      */     }
/*      */     
/* 1186 */     BigInteger num = null;
/* 1187 */     BigInteger den = null;
/* 1188 */     if (this.denominator.equals(fraction.denominator)) {
/* 1189 */       num = this.numerator.subtract(fraction.numerator);
/* 1190 */       den = this.denominator;
/*      */     } else {
/* 1192 */       num = this.numerator.multiply(fraction.denominator).subtract(fraction.numerator.multiply(this.denominator));
/* 1193 */       den = this.denominator.multiply(fraction.denominator);
/*      */     } 
/* 1195 */     return new BigFraction(num, den);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1210 */     String str = null;
/* 1211 */     if (BigInteger.ONE.equals(this.denominator)) {
/* 1212 */       str = this.numerator.toString();
/* 1213 */     } else if (BigInteger.ZERO.equals(this.numerator)) {
/* 1214 */       str = "0";
/*      */     } else {
/* 1216 */       str = this.numerator + " / " + this.denominator;
/*      */     } 
/* 1218 */     return str;
/*      */   }
/*      */ 
/*      */   
/*      */   public BigFractionField getField() {
/* 1223 */     return BigFractionField.getInstance();
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/fraction/BigFraction.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */