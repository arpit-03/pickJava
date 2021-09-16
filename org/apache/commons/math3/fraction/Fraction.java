/*     */ package org.apache.commons.math3.fraction;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.math.BigInteger;
/*     */ import org.apache.commons.math3.Field;
/*     */ import org.apache.commons.math3.FieldElement;
/*     */ import org.apache.commons.math3.exception.MathArithmeticException;
/*     */ import org.apache.commons.math3.exception.NullArgumentException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.util.ArithmeticUtils;
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
/*     */ public class Fraction
/*     */   extends Number
/*     */   implements FieldElement<Fraction>, Comparable<Fraction>, Serializable
/*     */ {
/*  41 */   public static final Fraction TWO = new Fraction(2, 1);
/*     */ 
/*     */   
/*  44 */   public static final Fraction ONE = new Fraction(1, 1);
/*     */ 
/*     */   
/*  47 */   public static final Fraction ZERO = new Fraction(0, 1);
/*     */ 
/*     */   
/*  50 */   public static final Fraction FOUR_FIFTHS = new Fraction(4, 5);
/*     */ 
/*     */   
/*  53 */   public static final Fraction ONE_FIFTH = new Fraction(1, 5);
/*     */ 
/*     */   
/*  56 */   public static final Fraction ONE_HALF = new Fraction(1, 2);
/*     */ 
/*     */   
/*  59 */   public static final Fraction ONE_QUARTER = new Fraction(1, 4);
/*     */ 
/*     */   
/*  62 */   public static final Fraction ONE_THIRD = new Fraction(1, 3);
/*     */ 
/*     */   
/*  65 */   public static final Fraction THREE_FIFTHS = new Fraction(3, 5);
/*     */ 
/*     */   
/*  68 */   public static final Fraction THREE_QUARTERS = new Fraction(3, 4);
/*     */ 
/*     */   
/*  71 */   public static final Fraction TWO_FIFTHS = new Fraction(2, 5);
/*     */ 
/*     */   
/*  74 */   public static final Fraction TWO_QUARTERS = new Fraction(2, 4);
/*     */ 
/*     */   
/*  77 */   public static final Fraction TWO_THIRDS = new Fraction(2, 3);
/*     */ 
/*     */   
/*  80 */   public static final Fraction MINUS_ONE = new Fraction(-1, 1);
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID = 3698073679419233275L;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final double DEFAULT_EPSILON = 1.0E-5D;
/*     */ 
/*     */ 
/*     */   
/*     */   private final int denominator;
/*     */ 
/*     */ 
/*     */   
/*     */   private final int numerator;
/*     */ 
/*     */ 
/*     */   
/*     */   public Fraction(double value) throws FractionConversionException {
/* 101 */     this(value, 1.0E-5D, 100);
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
/*     */   public Fraction(double value, double epsilon, int maxIterations) throws FractionConversionException {
/* 123 */     this(value, epsilon, 2147483647, maxIterations);
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
/*     */   public Fraction(double value, int maxDenominator) throws FractionConversionException {
/* 143 */     this(value, 0.0D, maxDenominator, 100);
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
/*     */   
/*     */   private Fraction(double value, double epsilon, int maxDenominator, int maxIterations) throws FractionConversionException {
/* 180 */     long overflow = 2147483647L;
/* 181 */     double r0 = value;
/* 182 */     long a0 = (long)FastMath.floor(r0);
/* 183 */     if (FastMath.abs(a0) > overflow) {
/* 184 */       throw new FractionConversionException(value, a0, 1L);
/*     */     }
/*     */ 
/*     */     
/* 188 */     if (FastMath.abs(a0 - value) < epsilon) {
/* 189 */       this.numerator = (int)a0;
/* 190 */       this.denominator = 1;
/*     */       
/*     */       return;
/*     */     } 
/* 194 */     long p0 = 1L;
/* 195 */     long q0 = 0L;
/* 196 */     long p1 = a0;
/* 197 */     long q1 = 1L;
/*     */     
/* 199 */     long p2 = 0L;
/* 200 */     long q2 = 1L;
/*     */     
/* 202 */     int n = 0;
/* 203 */     boolean stop = false;
/*     */     do {
/* 205 */       n++;
/* 206 */       double r1 = 1.0D / (r0 - a0);
/* 207 */       long a1 = (long)FastMath.floor(r1);
/* 208 */       p2 = a1 * p1 + p0;
/* 209 */       q2 = a1 * q1 + q0;
/*     */       
/* 211 */       if (FastMath.abs(p2) > overflow || FastMath.abs(q2) > overflow) {
/*     */ 
/*     */         
/* 214 */         if (epsilon == 0.0D && FastMath.abs(q1) < maxDenominator) {
/*     */           break;
/*     */         }
/* 217 */         throw new FractionConversionException(value, p2, q2);
/*     */       } 
/*     */       
/* 220 */       double convergent = p2 / q2;
/* 221 */       if (n < maxIterations && FastMath.abs(convergent - value) > epsilon && q2 < maxDenominator) {
/* 222 */         p0 = p1;
/* 223 */         p1 = p2;
/* 224 */         q0 = q1;
/* 225 */         q1 = q2;
/* 226 */         a0 = a1;
/* 227 */         r0 = r1;
/*     */       } else {
/* 229 */         stop = true;
/*     */       } 
/* 231 */     } while (!stop);
/*     */     
/* 233 */     if (n >= maxIterations) {
/* 234 */       throw new FractionConversionException(value, maxIterations);
/*     */     }
/*     */     
/* 237 */     if (q2 < maxDenominator) {
/* 238 */       this.numerator = (int)p2;
/* 239 */       this.denominator = (int)q2;
/*     */     } else {
/* 241 */       this.numerator = (int)p1;
/* 242 */       this.denominator = (int)q1;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Fraction(int num) {
/* 253 */     this(num, 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Fraction(int num, int den) {
/* 264 */     if (den == 0) {
/* 265 */       throw new MathArithmeticException(LocalizedFormats.ZERO_DENOMINATOR_IN_FRACTION, new Object[] { Integer.valueOf(num), Integer.valueOf(den) });
/*     */     }
/*     */     
/* 268 */     if (den < 0) {
/* 269 */       if (num == Integer.MIN_VALUE || den == Integer.MIN_VALUE)
/*     */       {
/* 271 */         throw new MathArithmeticException(LocalizedFormats.OVERFLOW_IN_FRACTION, new Object[] { Integer.valueOf(num), Integer.valueOf(den) });
/*     */       }
/*     */       
/* 274 */       num = -num;
/* 275 */       den = -den;
/*     */     } 
/*     */     
/* 278 */     int d = ArithmeticUtils.gcd(num, den);
/* 279 */     if (d > 1) {
/* 280 */       num /= d;
/* 281 */       den /= d;
/*     */     } 
/*     */ 
/*     */     
/* 285 */     if (den < 0) {
/* 286 */       num = -num;
/* 287 */       den = -den;
/*     */     } 
/* 289 */     this.numerator = num;
/* 290 */     this.denominator = den;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Fraction abs() {
/*     */     Fraction ret;
/* 299 */     if (this.numerator >= 0) {
/* 300 */       ret = this;
/*     */     } else {
/* 302 */       ret = negate();
/*     */     } 
/* 304 */     return ret;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int compareTo(Fraction object) {
/* 314 */     long nOd = this.numerator * object.denominator;
/* 315 */     long dOn = this.denominator * object.numerator;
/* 316 */     return (nOd < dOn) ? -1 : ((nOd > dOn) ? 1 : 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double doubleValue() {
/* 326 */     return this.numerator / this.denominator;
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
/*     */   public boolean equals(Object other) {
/* 340 */     if (this == other) {
/* 341 */       return true;
/*     */     }
/* 343 */     if (other instanceof Fraction) {
/*     */ 
/*     */       
/* 346 */       Fraction rhs = (Fraction)other;
/* 347 */       return (this.numerator == rhs.numerator && this.denominator == rhs.denominator);
/*     */     } 
/*     */     
/* 350 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float floatValue() {
/* 360 */     return (float)doubleValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDenominator() {
/* 368 */     return this.denominator;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNumerator() {
/* 376 */     return this.numerator;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 385 */     return 37 * (629 + this.numerator) + this.denominator;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int intValue() {
/* 395 */     return (int)doubleValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long longValue() {
/* 405 */     return (long)doubleValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Fraction negate() {
/* 413 */     if (this.numerator == Integer.MIN_VALUE) {
/* 414 */       throw new MathArithmeticException(LocalizedFormats.OVERFLOW_IN_FRACTION, new Object[] { Integer.valueOf(this.numerator), Integer.valueOf(this.denominator) });
/*     */     }
/* 416 */     return new Fraction(-this.numerator, this.denominator);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Fraction reciprocal() {
/* 424 */     return new Fraction(this.denominator, this.numerator);
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
/*     */   public Fraction add(Fraction fraction) {
/* 438 */     return addSub(fraction, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Fraction add(int i) {
/* 447 */     return new Fraction(this.numerator + i * this.denominator, this.denominator);
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
/*     */   public Fraction subtract(Fraction fraction) {
/* 461 */     return addSub(fraction, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Fraction subtract(int i) {
/* 470 */     return new Fraction(this.numerator - i * this.denominator, this.denominator);
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
/*     */   private Fraction addSub(Fraction fraction, boolean isAdd) {
/* 484 */     if (fraction == null) {
/* 485 */       throw new NullArgumentException(LocalizedFormats.FRACTION, new Object[0]);
/*     */     }
/*     */     
/* 488 */     if (this.numerator == 0) {
/* 489 */       return isAdd ? fraction : fraction.negate();
/*     */     }
/* 491 */     if (fraction.numerator == 0) {
/* 492 */       return this;
/*     */     }
/*     */ 
/*     */     
/* 496 */     int d1 = ArithmeticUtils.gcd(this.denominator, fraction.denominator);
/* 497 */     if (d1 == 1) {
/*     */       
/* 499 */       int i = ArithmeticUtils.mulAndCheck(this.numerator, fraction.denominator);
/* 500 */       int j = ArithmeticUtils.mulAndCheck(fraction.numerator, this.denominator);
/* 501 */       return new Fraction(isAdd ? ArithmeticUtils.addAndCheck(i, j) : ArithmeticUtils.subAndCheck(i, j), ArithmeticUtils.mulAndCheck(this.denominator, fraction.denominator));
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 509 */     BigInteger uvp = BigInteger.valueOf(this.numerator).multiply(BigInteger.valueOf((fraction.denominator / d1)));
/*     */     
/* 511 */     BigInteger upv = BigInteger.valueOf(fraction.numerator).multiply(BigInteger.valueOf((this.denominator / d1)));
/*     */     
/* 513 */     BigInteger t = isAdd ? uvp.add(upv) : uvp.subtract(upv);
/*     */ 
/*     */     
/* 516 */     int tmodd1 = t.mod(BigInteger.valueOf(d1)).intValue();
/* 517 */     int d2 = (tmodd1 == 0) ? d1 : ArithmeticUtils.gcd(tmodd1, d1);
/*     */ 
/*     */     
/* 520 */     BigInteger w = t.divide(BigInteger.valueOf(d2));
/* 521 */     if (w.bitLength() > 31) {
/* 522 */       throw new MathArithmeticException(LocalizedFormats.NUMERATOR_OVERFLOW_AFTER_MULTIPLY, new Object[] { w });
/*     */     }
/*     */     
/* 525 */     return new Fraction(w.intValue(), ArithmeticUtils.mulAndCheck(this.denominator / d1, fraction.denominator / d2));
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
/*     */   public Fraction multiply(Fraction fraction) {
/* 541 */     if (fraction == null) {
/* 542 */       throw new NullArgumentException(LocalizedFormats.FRACTION, new Object[0]);
/*     */     }
/* 544 */     if (this.numerator == 0 || fraction.numerator == 0) {
/* 545 */       return ZERO;
/*     */     }
/*     */ 
/*     */     
/* 549 */     int d1 = ArithmeticUtils.gcd(this.numerator, fraction.denominator);
/* 550 */     int d2 = ArithmeticUtils.gcd(fraction.numerator, this.denominator);
/* 551 */     return getReducedFraction(ArithmeticUtils.mulAndCheck(this.numerator / d1, fraction.numerator / d2), ArithmeticUtils.mulAndCheck(this.denominator / d2, fraction.denominator / d1));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Fraction multiply(int i) {
/* 562 */     return multiply(new Fraction(i));
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
/*     */   public Fraction divide(Fraction fraction) {
/* 576 */     if (fraction == null) {
/* 577 */       throw new NullArgumentException(LocalizedFormats.FRACTION, new Object[0]);
/*     */     }
/* 579 */     if (fraction.numerator == 0) {
/* 580 */       throw new MathArithmeticException(LocalizedFormats.ZERO_FRACTION_TO_DIVIDE_BY, new Object[] { Integer.valueOf(fraction.numerator), Integer.valueOf(fraction.denominator) });
/*     */     }
/*     */     
/* 583 */     return multiply(fraction.reciprocal());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Fraction divide(int i) {
/* 592 */     return divide(new Fraction(i));
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
/*     */   public double percentageValue() {
/* 604 */     return 100.0D * doubleValue();
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
/*     */   public static Fraction getReducedFraction(int numerator, int denominator) {
/* 619 */     if (denominator == 0) {
/* 620 */       throw new MathArithmeticException(LocalizedFormats.ZERO_DENOMINATOR_IN_FRACTION, new Object[] { Integer.valueOf(numerator), Integer.valueOf(denominator) });
/*     */     }
/*     */     
/* 623 */     if (numerator == 0) {
/* 624 */       return ZERO;
/*     */     }
/*     */     
/* 627 */     if (denominator == Integer.MIN_VALUE && (numerator & 0x1) == 0) {
/* 628 */       numerator /= 2; denominator /= 2;
/*     */     } 
/* 630 */     if (denominator < 0) {
/* 631 */       if (numerator == Integer.MIN_VALUE || denominator == Integer.MIN_VALUE)
/*     */       {
/* 633 */         throw new MathArithmeticException(LocalizedFormats.OVERFLOW_IN_FRACTION, new Object[] { Integer.valueOf(numerator), Integer.valueOf(denominator) });
/*     */       }
/*     */       
/* 636 */       numerator = -numerator;
/* 637 */       denominator = -denominator;
/*     */     } 
/*     */     
/* 640 */     int gcd = ArithmeticUtils.gcd(numerator, denominator);
/* 641 */     numerator /= gcd;
/* 642 */     denominator /= gcd;
/* 643 */     return new Fraction(numerator, denominator);
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
/*     */   public String toString() {
/* 657 */     String str = null;
/* 658 */     if (this.denominator == 1) {
/* 659 */       str = Integer.toString(this.numerator);
/* 660 */     } else if (this.numerator == 0) {
/* 661 */       str = "0";
/*     */     } else {
/* 663 */       str = this.numerator + " / " + this.denominator;
/*     */     } 
/* 665 */     return str;
/*     */   }
/*     */ 
/*     */   
/*     */   public FractionField getField() {
/* 670 */     return FractionField.getInstance();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/fraction/Fraction.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */