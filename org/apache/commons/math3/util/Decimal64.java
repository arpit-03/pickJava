/*     */ package org.apache.commons.math3.util;
/*     */ 
/*     */ import org.apache.commons.math3.Field;
/*     */ import org.apache.commons.math3.RealFieldElement;
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.MathArithmeticException;
/*     */ import org.apache.commons.math3.exception.NullArgumentException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Decimal64
/*     */   extends Number
/*     */   implements RealFieldElement<Decimal64>, Comparable<Decimal64>
/*     */ {
/*  58 */   public static final Decimal64 ZERO = new Decimal64(0.0D);
/*  59 */   public static final Decimal64 ONE = new Decimal64(1.0D);
/*  60 */   public static final Decimal64 NEGATIVE_INFINITY = new Decimal64(Double.NEGATIVE_INFINITY);
/*  61 */   public static final Decimal64 POSITIVE_INFINITY = new Decimal64(Double.POSITIVE_INFINITY);
/*  62 */   public static final Decimal64 NAN = new Decimal64(Double.NaN);
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID = 20120227L;
/*     */ 
/*     */   
/*     */   private final double value;
/*     */ 
/*     */ 
/*     */   
/*     */   public Decimal64(double x) {
/*  74 */     this.value = x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Field<Decimal64> getField() {
/*  83 */     return Decimal64Field.getInstance();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Decimal64 add(Decimal64 a) {
/*  94 */     return new Decimal64(this.value + a.value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Decimal64 subtract(Decimal64 a) {
/* 105 */     return new Decimal64(this.value - a.value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Decimal64 negate() {
/* 115 */     return new Decimal64(-this.value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Decimal64 multiply(Decimal64 a) {
/* 126 */     return new Decimal64(this.value * a.value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Decimal64 multiply(int n) {
/* 136 */     return new Decimal64(n * this.value);
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
/*     */   public Decimal64 divide(Decimal64 a) {
/* 148 */     return new Decimal64(this.value / a.value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Decimal64 reciprocal() {
/* 159 */     return new Decimal64(1.0D / this.value);
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
/*     */   public byte byteValue() {
/* 173 */     return (byte)(int)this.value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public short shortValue() {
/* 183 */     return (short)(int)this.value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int intValue() {
/* 193 */     return (int)this.value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long longValue() {
/* 203 */     return (long)this.value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float floatValue() {
/* 213 */     return (float)this.value;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double doubleValue() {
/* 219 */     return this.value;
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
/*     */   public int compareTo(Decimal64 o) {
/* 236 */     return Double.compare(this.value, o.value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 246 */     if (obj instanceof Decimal64) {
/* 247 */       Decimal64 that = (Decimal64)obj;
/* 248 */       return (Double.doubleToLongBits(this.value) == Double.doubleToLongBits(that.value));
/*     */     } 
/*     */     
/* 251 */     return false;
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
/*     */   public int hashCode() {
/* 264 */     long v = Double.doubleToLongBits(this.value);
/* 265 */     return (int)(v ^ v >>> 32L);
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
/*     */   public String toString() {
/* 278 */     return Double.toString(this.value);
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
/*     */   public boolean isInfinite() {
/* 292 */     return Double.isInfinite(this.value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isNaN() {
/* 302 */     return Double.isNaN(this.value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getReal() {
/* 309 */     return this.value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Decimal64 add(double a) {
/* 316 */     return new Decimal64(this.value + a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Decimal64 subtract(double a) {
/* 323 */     return new Decimal64(this.value - a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Decimal64 multiply(double a) {
/* 330 */     return new Decimal64(this.value * a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Decimal64 divide(double a) {
/* 337 */     return new Decimal64(this.value / a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Decimal64 remainder(double a) {
/* 344 */     return new Decimal64(FastMath.IEEEremainder(this.value, a));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Decimal64 remainder(Decimal64 a) {
/* 351 */     return new Decimal64(FastMath.IEEEremainder(this.value, a.value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Decimal64 abs() {
/* 358 */     return new Decimal64(FastMath.abs(this.value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Decimal64 ceil() {
/* 365 */     return new Decimal64(FastMath.ceil(this.value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Decimal64 floor() {
/* 372 */     return new Decimal64(FastMath.floor(this.value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Decimal64 rint() {
/* 379 */     return new Decimal64(FastMath.rint(this.value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long round() {
/* 386 */     return FastMath.round(this.value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Decimal64 signum() {
/* 393 */     return new Decimal64(FastMath.signum(this.value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Decimal64 copySign(Decimal64 sign) {
/* 400 */     return new Decimal64(FastMath.copySign(this.value, sign.value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Decimal64 copySign(double sign) {
/* 407 */     return new Decimal64(FastMath.copySign(this.value, sign));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Decimal64 scalb(int n) {
/* 414 */     return new Decimal64(FastMath.scalb(this.value, n));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Decimal64 hypot(Decimal64 y) {
/* 421 */     return new Decimal64(FastMath.hypot(this.value, y.value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Decimal64 sqrt() {
/* 428 */     return new Decimal64(FastMath.sqrt(this.value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Decimal64 cbrt() {
/* 435 */     return new Decimal64(FastMath.cbrt(this.value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Decimal64 rootN(int n) {
/* 442 */     if (this.value < 0.0D) {
/* 443 */       return new Decimal64(-FastMath.pow(-this.value, 1.0D / n));
/*     */     }
/* 445 */     return new Decimal64(FastMath.pow(this.value, 1.0D / n));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Decimal64 pow(double p) {
/* 453 */     return new Decimal64(FastMath.pow(this.value, p));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Decimal64 pow(int n) {
/* 460 */     return new Decimal64(FastMath.pow(this.value, n));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Decimal64 pow(Decimal64 e) {
/* 467 */     return new Decimal64(FastMath.pow(this.value, e.value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Decimal64 exp() {
/* 474 */     return new Decimal64(FastMath.exp(this.value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Decimal64 expm1() {
/* 481 */     return new Decimal64(FastMath.expm1(this.value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Decimal64 log() {
/* 488 */     return new Decimal64(FastMath.log(this.value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Decimal64 log1p() {
/* 495 */     return new Decimal64(FastMath.log1p(this.value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Decimal64 log10() {
/* 503 */     return new Decimal64(FastMath.log10(this.value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Decimal64 cos() {
/* 510 */     return new Decimal64(FastMath.cos(this.value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Decimal64 sin() {
/* 517 */     return new Decimal64(FastMath.sin(this.value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Decimal64 tan() {
/* 524 */     return new Decimal64(FastMath.tan(this.value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Decimal64 acos() {
/* 531 */     return new Decimal64(FastMath.acos(this.value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Decimal64 asin() {
/* 538 */     return new Decimal64(FastMath.asin(this.value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Decimal64 atan() {
/* 545 */     return new Decimal64(FastMath.atan(this.value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Decimal64 atan2(Decimal64 x) {
/* 552 */     return new Decimal64(FastMath.atan2(this.value, x.value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Decimal64 cosh() {
/* 559 */     return new Decimal64(FastMath.cosh(this.value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Decimal64 sinh() {
/* 566 */     return new Decimal64(FastMath.sinh(this.value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Decimal64 tanh() {
/* 573 */     return new Decimal64(FastMath.tanh(this.value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Decimal64 acosh() {
/* 580 */     return new Decimal64(FastMath.acosh(this.value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Decimal64 asinh() {
/* 587 */     return new Decimal64(FastMath.asinh(this.value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Decimal64 atanh() {
/* 594 */     return new Decimal64(FastMath.atanh(this.value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Decimal64 linearCombination(Decimal64[] a, Decimal64[] b) throws DimensionMismatchException {
/* 602 */     if (a.length != b.length) {
/* 603 */       throw new DimensionMismatchException(a.length, b.length);
/*     */     }
/* 605 */     double[] aDouble = new double[a.length];
/* 606 */     double[] bDouble = new double[b.length];
/* 607 */     for (int i = 0; i < a.length; i++) {
/* 608 */       aDouble[i] = (a[i]).value;
/* 609 */       bDouble[i] = (b[i]).value;
/*     */     } 
/* 611 */     return new Decimal64(MathArrays.linearCombination(aDouble, bDouble));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Decimal64 linearCombination(double[] a, Decimal64[] b) throws DimensionMismatchException {
/* 619 */     if (a.length != b.length) {
/* 620 */       throw new DimensionMismatchException(a.length, b.length);
/*     */     }
/* 622 */     double[] bDouble = new double[b.length];
/* 623 */     for (int i = 0; i < a.length; i++) {
/* 624 */       bDouble[i] = (b[i]).value;
/*     */     }
/* 626 */     return new Decimal64(MathArrays.linearCombination(a, bDouble));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Decimal64 linearCombination(Decimal64 a1, Decimal64 b1, Decimal64 a2, Decimal64 b2) {
/* 634 */     return new Decimal64(MathArrays.linearCombination(a1.value, b1.value, a2.value, b2.value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Decimal64 linearCombination(double a1, Decimal64 b1, double a2, Decimal64 b2) {
/* 643 */     return new Decimal64(MathArrays.linearCombination(a1, b1.value, a2, b2.value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Decimal64 linearCombination(Decimal64 a1, Decimal64 b1, Decimal64 a2, Decimal64 b2, Decimal64 a3, Decimal64 b3) {
/* 653 */     return new Decimal64(MathArrays.linearCombination(a1.value, b1.value, a2.value, b2.value, a3.value, b3.value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Decimal64 linearCombination(double a1, Decimal64 b1, double a2, Decimal64 b2, double a3, Decimal64 b3) {
/* 664 */     return new Decimal64(MathArrays.linearCombination(a1, b1.value, a2, b2.value, a3, b3.value));
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
/*     */   public Decimal64 linearCombination(Decimal64 a1, Decimal64 b1, Decimal64 a2, Decimal64 b2, Decimal64 a3, Decimal64 b3, Decimal64 a4, Decimal64 b4) {
/* 676 */     return new Decimal64(MathArrays.linearCombination(a1.value, b1.value, a2.value, b2.value, a3.value, b3.value, a4.value, b4.value));
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
/*     */   public Decimal64 linearCombination(double a1, Decimal64 b1, double a2, Decimal64 b2, double a3, Decimal64 b3, double a4, Decimal64 b4) {
/* 689 */     return new Decimal64(MathArrays.linearCombination(a1, b1.value, a2, b2.value, a3, b3.value, a4, b4.value));
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/util/Decimal64.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */