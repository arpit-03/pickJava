/*      */ package org.apache.commons.math3.complex;
/*      */ 
/*      */ import java.io.Serializable;
/*      */ import java.util.ArrayList;
/*      */ import java.util.List;
/*      */ import org.apache.commons.math3.Field;
/*      */ import org.apache.commons.math3.FieldElement;
/*      */ import org.apache.commons.math3.exception.MathArithmeticException;
/*      */ import org.apache.commons.math3.exception.NotPositiveException;
/*      */ import org.apache.commons.math3.exception.NullArgumentException;
/*      */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*      */ import org.apache.commons.math3.util.FastMath;
/*      */ import org.apache.commons.math3.util.MathUtils;
/*      */ import org.apache.commons.math3.util.Precision;
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
/*      */ 
/*      */ 
/*      */ public class Complex
/*      */   implements FieldElement<Complex>, Serializable
/*      */ {
/*   57 */   public static final Complex I = new Complex(0.0D, 1.0D);
/*      */ 
/*      */   
/*   60 */   public static final Complex NaN = new Complex(Double.NaN, Double.NaN);
/*      */ 
/*      */   
/*   63 */   public static final Complex INF = new Complex(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
/*      */   
/*   65 */   public static final Complex ONE = new Complex(1.0D, 0.0D);
/*      */   
/*   67 */   public static final Complex ZERO = new Complex(0.0D, 0.0D);
/*      */ 
/*      */   
/*      */   private static final long serialVersionUID = -6195664516687396620L;
/*      */ 
/*      */   
/*      */   private final double imaginary;
/*      */ 
/*      */   
/*      */   private final double real;
/*      */ 
/*      */   
/*      */   private final transient boolean isNaN;
/*      */ 
/*      */   
/*      */   private final transient boolean isInfinite;
/*      */ 
/*      */ 
/*      */   
/*      */   public Complex(double real) {
/*   87 */     this(real, 0.0D);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Complex(double real, double imaginary) {
/*   97 */     this.real = real;
/*   98 */     this.imaginary = imaginary;
/*      */     
/*  100 */     this.isNaN = (Double.isNaN(real) || Double.isNaN(imaginary));
/*  101 */     this.isInfinite = (!this.isNaN && (Double.isInfinite(real) || Double.isInfinite(imaginary)));
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
/*      */   public double abs() {
/*  114 */     if (this.isNaN) {
/*  115 */       return Double.NaN;
/*      */     }
/*  117 */     if (isInfinite()) {
/*  118 */       return Double.POSITIVE_INFINITY;
/*      */     }
/*  120 */     if (FastMath.abs(this.real) < FastMath.abs(this.imaginary)) {
/*  121 */       if (this.imaginary == 0.0D) {
/*  122 */         return FastMath.abs(this.real);
/*      */       }
/*  124 */       double d = this.real / this.imaginary;
/*  125 */       return FastMath.abs(this.imaginary) * FastMath.sqrt(1.0D + d * d);
/*      */     } 
/*  127 */     if (this.real == 0.0D) {
/*  128 */       return FastMath.abs(this.imaginary);
/*      */     }
/*  130 */     double q = this.imaginary / this.real;
/*  131 */     return FastMath.abs(this.real) * FastMath.sqrt(1.0D + q * q);
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
/*      */   public Complex add(Complex addend) throws NullArgumentException {
/*  152 */     MathUtils.checkNotNull(addend);
/*  153 */     if (this.isNaN || addend.isNaN) {
/*  154 */       return NaN;
/*      */     }
/*      */     
/*  157 */     return createComplex(this.real + addend.getReal(), this.imaginary + addend.getImaginary());
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
/*      */   public Complex add(double addend) {
/*  170 */     if (this.isNaN || Double.isNaN(addend)) {
/*  171 */       return NaN;
/*      */     }
/*      */     
/*  174 */     return createComplex(this.real + addend, this.imaginary);
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
/*      */   public Complex conjugate() {
/*  192 */     if (this.isNaN) {
/*  193 */       return NaN;
/*      */     }
/*      */     
/*  196 */     return createComplex(this.real, -this.imaginary);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Complex divide(Complex divisor) throws NullArgumentException {
/*  243 */     MathUtils.checkNotNull(divisor);
/*  244 */     if (this.isNaN || divisor.isNaN) {
/*  245 */       return NaN;
/*      */     }
/*      */     
/*  248 */     double c = divisor.getReal();
/*  249 */     double d = divisor.getImaginary();
/*  250 */     if (c == 0.0D && d == 0.0D) {
/*  251 */       return NaN;
/*      */     }
/*      */     
/*  254 */     if (divisor.isInfinite() && !isInfinite()) {
/*  255 */       return ZERO;
/*      */     }
/*      */     
/*  258 */     if (FastMath.abs(c) < FastMath.abs(d)) {
/*  259 */       double d1 = c / d;
/*  260 */       double d2 = c * d1 + d;
/*  261 */       return createComplex((this.real * d1 + this.imaginary) / d2, (this.imaginary * d1 - this.real) / d2);
/*      */     } 
/*      */     
/*  264 */     double q = d / c;
/*  265 */     double denominator = d * q + c;
/*  266 */     return createComplex((this.imaginary * q + this.real) / denominator, (this.imaginary - this.real * q) / denominator);
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
/*      */   public Complex divide(double divisor) {
/*  280 */     if (this.isNaN || Double.isNaN(divisor)) {
/*  281 */       return NaN;
/*      */     }
/*  283 */     if (divisor == 0.0D) {
/*  284 */       return NaN;
/*      */     }
/*  286 */     if (Double.isInfinite(divisor)) {
/*  287 */       return !isInfinite() ? ZERO : NaN;
/*      */     }
/*  289 */     return createComplex(this.real / divisor, this.imaginary / divisor);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Complex reciprocal() {
/*  295 */     if (this.isNaN) {
/*  296 */       return NaN;
/*      */     }
/*      */     
/*  299 */     if (this.real == 0.0D && this.imaginary == 0.0D) {
/*  300 */       return INF;
/*      */     }
/*      */     
/*  303 */     if (this.isInfinite) {
/*  304 */       return ZERO;
/*      */     }
/*      */     
/*  307 */     if (FastMath.abs(this.real) < FastMath.abs(this.imaginary)) {
/*  308 */       double d1 = this.real / this.imaginary;
/*  309 */       double d2 = 1.0D / (this.real * d1 + this.imaginary);
/*  310 */       return createComplex(d2 * d1, -d2);
/*      */     } 
/*  312 */     double q = this.imaginary / this.real;
/*  313 */     double scale = 1.0D / (this.imaginary * q + this.real);
/*  314 */     return createComplex(scale, -scale * q);
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
/*      */   public boolean equals(Object other) {
/*  344 */     if (this == other) {
/*  345 */       return true;
/*      */     }
/*  347 */     if (other instanceof Complex) {
/*  348 */       Complex c = (Complex)other;
/*  349 */       if (c.isNaN) {
/*  350 */         return this.isNaN;
/*      */       }
/*  352 */       return (MathUtils.equals(this.real, c.real) && MathUtils.equals(this.imaginary, c.imaginary));
/*      */     } 
/*      */ 
/*      */     
/*  356 */     return false;
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
/*      */   public static boolean equals(Complex x, Complex y, int maxUlps) {
/*  377 */     return (Precision.equals(x.real, y.real, maxUlps) && Precision.equals(x.imaginary, y.imaginary, maxUlps));
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
/*      */   public static boolean equals(Complex x, Complex y) {
/*  392 */     return equals(x, y, 1);
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
/*      */   public static boolean equals(Complex x, Complex y, double eps) {
/*  411 */     return (Precision.equals(x.real, y.real, eps) && Precision.equals(x.imaginary, y.imaginary, eps));
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
/*      */   public static boolean equalsWithRelativeTolerance(Complex x, Complex y, double eps) {
/*  432 */     return (Precision.equalsWithRelativeTolerance(x.real, y.real, eps) && Precision.equalsWithRelativeTolerance(x.imaginary, y.imaginary, eps));
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
/*  445 */     if (this.isNaN) {
/*  446 */       return 7;
/*      */     }
/*  448 */     return 37 * (17 * MathUtils.hash(this.imaginary) + MathUtils.hash(this.real));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getImaginary() {
/*  458 */     return this.imaginary;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getReal() {
/*  467 */     return this.real;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isNaN() {
/*  478 */     return this.isNaN;
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
/*      */   public boolean isInfinite() {
/*  491 */     return this.isInfinite;
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
/*      */   public Complex multiply(Complex factor) throws NullArgumentException {
/*  518 */     MathUtils.checkNotNull(factor);
/*  519 */     if (this.isNaN || factor.isNaN) {
/*  520 */       return NaN;
/*      */     }
/*  522 */     if (Double.isInfinite(this.real) || Double.isInfinite(this.imaginary) || Double.isInfinite(factor.real) || Double.isInfinite(factor.imaginary))
/*      */     {
/*      */ 
/*      */ 
/*      */       
/*  527 */       return INF;
/*      */     }
/*  529 */     return createComplex(this.real * factor.real - this.imaginary * factor.imaginary, this.real * factor.imaginary + this.imaginary * factor.real);
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
/*      */   public Complex multiply(int factor) {
/*  542 */     if (this.isNaN) {
/*  543 */       return NaN;
/*      */     }
/*  545 */     if (Double.isInfinite(this.real) || Double.isInfinite(this.imaginary))
/*      */     {
/*  547 */       return INF;
/*      */     }
/*  549 */     return createComplex(this.real * factor, this.imaginary * factor);
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
/*      */   public Complex multiply(double factor) {
/*  561 */     if (this.isNaN || Double.isNaN(factor)) {
/*  562 */       return NaN;
/*      */     }
/*  564 */     if (Double.isInfinite(this.real) || Double.isInfinite(this.imaginary) || Double.isInfinite(factor))
/*      */     {
/*      */ 
/*      */       
/*  568 */       return INF;
/*      */     }
/*  570 */     return createComplex(this.real * factor, this.imaginary * factor);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Complex negate() {
/*  581 */     if (this.isNaN) {
/*  582 */       return NaN;
/*      */     }
/*      */     
/*  585 */     return createComplex(-this.real, -this.imaginary);
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
/*      */   public Complex subtract(Complex subtrahend) throws NullArgumentException {
/*  606 */     MathUtils.checkNotNull(subtrahend);
/*  607 */     if (this.isNaN || subtrahend.isNaN) {
/*  608 */       return NaN;
/*      */     }
/*      */     
/*  611 */     return createComplex(this.real - subtrahend.getReal(), this.imaginary - subtrahend.getImaginary());
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
/*      */   public Complex subtract(double subtrahend) {
/*  624 */     if (this.isNaN || Double.isNaN(subtrahend)) {
/*  625 */       return NaN;
/*      */     }
/*  627 */     return createComplex(this.real - subtrahend, this.imaginary);
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
/*      */   public Complex acos() {
/*  645 */     if (this.isNaN) {
/*  646 */       return NaN;
/*      */     }
/*      */     
/*  649 */     return add(sqrt1z().multiply(I)).log().multiply(I.negate());
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
/*      */   public Complex asin() {
/*  667 */     if (this.isNaN) {
/*  668 */       return NaN;
/*      */     }
/*      */     
/*  671 */     return sqrt1z().add(multiply(I)).log().multiply(I.negate());
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
/*      */   public Complex atan() {
/*  689 */     if (this.isNaN) {
/*  690 */       return NaN;
/*      */     }
/*      */     
/*  693 */     return add(I).divide(I.subtract(this)).log().multiply(I.divide(createComplex(2.0D, 0.0D)));
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
/*      */   public Complex cos() {
/*  727 */     if (this.isNaN) {
/*  728 */       return NaN;
/*      */     }
/*      */     
/*  731 */     return createComplex(FastMath.cos(this.real) * FastMath.cosh(this.imaginary), -FastMath.sin(this.real) * FastMath.sinh(this.imaginary));
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
/*      */   public Complex cosh() {
/*  767 */     if (this.isNaN) {
/*  768 */       return NaN;
/*      */     }
/*      */     
/*  771 */     return createComplex(FastMath.cosh(this.real) * FastMath.cos(this.imaginary), FastMath.sinh(this.real) * FastMath.sin(this.imaginary));
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
/*      */   public Complex exp() {
/*  808 */     if (this.isNaN) {
/*  809 */       return NaN;
/*      */     }
/*      */     
/*  812 */     double expReal = FastMath.exp(this.real);
/*  813 */     return createComplex(expReal * FastMath.cos(this.imaginary), expReal * FastMath.sin(this.imaginary));
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
/*      */   public Complex log() {
/*  853 */     if (this.isNaN) {
/*  854 */       return NaN;
/*      */     }
/*      */     
/*  857 */     return createComplex(FastMath.log(abs()), FastMath.atan2(this.imaginary, this.real));
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
/*      */   public Complex pow(Complex x) throws NullArgumentException {
/*  883 */     MathUtils.checkNotNull(x);
/*  884 */     return log().multiply(x).exp();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Complex pow(double x) {
/*  895 */     return log().multiply(x).exp();
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
/*      */   public Complex sin() {
/*  931 */     if (this.isNaN) {
/*  932 */       return NaN;
/*      */     }
/*      */     
/*  935 */     return createComplex(FastMath.sin(this.real) * FastMath.cosh(this.imaginary), FastMath.cos(this.real) * FastMath.sinh(this.imaginary));
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
/*      */   public Complex sinh() {
/*  971 */     if (this.isNaN) {
/*  972 */       return NaN;
/*      */     }
/*      */     
/*  975 */     return createComplex(FastMath.sinh(this.real) * FastMath.cos(this.imaginary), FastMath.cosh(this.real) * FastMath.sin(this.imaginary));
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
/*      */   public Complex sqrt() {
/* 1014 */     if (this.isNaN) {
/* 1015 */       return NaN;
/*      */     }
/*      */     
/* 1018 */     if (this.real == 0.0D && this.imaginary == 0.0D) {
/* 1019 */       return createComplex(0.0D, 0.0D);
/*      */     }
/*      */     
/* 1022 */     double t = FastMath.sqrt((FastMath.abs(this.real) + abs()) / 2.0D);
/* 1023 */     if (this.real >= 0.0D) {
/* 1024 */       return createComplex(t, this.imaginary / 2.0D * t);
/*      */     }
/* 1026 */     return createComplex(FastMath.abs(this.imaginary) / 2.0D * t, FastMath.copySign(1.0D, this.imaginary) * t);
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
/*      */   public Complex sqrt1z() {
/* 1049 */     return createComplex(1.0D, 0.0D).subtract(multiply(this)).sqrt();
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
/*      */   public Complex tan() {
/* 1085 */     if (this.isNaN || Double.isInfinite(this.real)) {
/* 1086 */       return NaN;
/*      */     }
/* 1088 */     if (this.imaginary > 20.0D) {
/* 1089 */       return createComplex(0.0D, 1.0D);
/*      */     }
/* 1091 */     if (this.imaginary < -20.0D) {
/* 1092 */       return createComplex(0.0D, -1.0D);
/*      */     }
/*      */     
/* 1095 */     double real2 = 2.0D * this.real;
/* 1096 */     double imaginary2 = 2.0D * this.imaginary;
/* 1097 */     double d = FastMath.cos(real2) + FastMath.cosh(imaginary2);
/*      */     
/* 1099 */     return createComplex(FastMath.sin(real2) / d, FastMath.sinh(imaginary2) / d);
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
/*      */   public Complex tanh() {
/* 1136 */     if (this.isNaN || Double.isInfinite(this.imaginary)) {
/* 1137 */       return NaN;
/*      */     }
/* 1139 */     if (this.real > 20.0D) {
/* 1140 */       return createComplex(1.0D, 0.0D);
/*      */     }
/* 1142 */     if (this.real < -20.0D) {
/* 1143 */       return createComplex(-1.0D, 0.0D);
/*      */     }
/* 1145 */     double real2 = 2.0D * this.real;
/* 1146 */     double imaginary2 = 2.0D * this.imaginary;
/* 1147 */     double d = FastMath.cosh(real2) + FastMath.cos(imaginary2);
/*      */     
/* 1149 */     return createComplex(FastMath.sinh(real2) / d, FastMath.sin(imaginary2) / d);
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
/*      */   public double getArgument() {
/* 1173 */     return FastMath.atan2(getImaginary(), getReal());
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
/*      */   public List<Complex> nthRoot(int n) throws NotPositiveException {
/* 1200 */     if (n <= 0) {
/* 1201 */       throw new NotPositiveException(LocalizedFormats.CANNOT_COMPUTE_NTH_ROOT_FOR_NEGATIVE_N, Integer.valueOf(n));
/*      */     }
/*      */ 
/*      */     
/* 1205 */     List<Complex> result = new ArrayList<Complex>();
/*      */     
/* 1207 */     if (this.isNaN) {
/* 1208 */       result.add(NaN);
/* 1209 */       return result;
/*      */     } 
/* 1211 */     if (isInfinite()) {
/* 1212 */       result.add(INF);
/* 1213 */       return result;
/*      */     } 
/*      */ 
/*      */     
/* 1217 */     double nthRootOfAbs = FastMath.pow(abs(), 1.0D / n);
/*      */ 
/*      */     
/* 1220 */     double nthPhi = getArgument() / n;
/* 1221 */     double slice = 6.283185307179586D / n;
/* 1222 */     double innerPart = nthPhi;
/* 1223 */     for (int k = 0; k < n; k++) {
/*      */       
/* 1225 */       double realPart = nthRootOfAbs * FastMath.cos(innerPart);
/* 1226 */       double imaginaryPart = nthRootOfAbs * FastMath.sin(innerPart);
/* 1227 */       result.add(createComplex(realPart, imaginaryPart));
/* 1228 */       innerPart += slice;
/*      */     } 
/*      */     
/* 1231 */     return result;
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
/*      */   protected Complex createComplex(double realPart, double imaginaryPart) {
/* 1245 */     return new Complex(realPart, imaginaryPart);
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
/*      */   public static Complex valueOf(double realPart, double imaginaryPart) {
/* 1257 */     if (Double.isNaN(realPart) || Double.isNaN(imaginaryPart))
/*      */     {
/* 1259 */       return NaN;
/*      */     }
/* 1261 */     return new Complex(realPart, imaginaryPart);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Complex valueOf(double realPart) {
/* 1271 */     if (Double.isNaN(realPart)) {
/* 1272 */       return NaN;
/*      */     }
/* 1274 */     return new Complex(realPart);
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
/*      */   protected final Object readResolve() {
/* 1286 */     return createComplex(this.real, this.imaginary);
/*      */   }
/*      */ 
/*      */   
/*      */   public ComplexField getField() {
/* 1291 */     return ComplexField.getInstance();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1297 */     return "(" + this.real + ", " + this.imaginary + ")";
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/complex/Complex.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */