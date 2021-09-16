/*      */ package org.apache.commons.math3.analysis.differentiation;
/*      */ 
/*      */ import java.io.Serializable;
/*      */ import org.apache.commons.math3.Field;
/*      */ import org.apache.commons.math3.FieldElement;
/*      */ import org.apache.commons.math3.RealFieldElement;
/*      */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*      */ import org.apache.commons.math3.exception.MathArithmeticException;
/*      */ import org.apache.commons.math3.exception.NullArgumentException;
/*      */ import org.apache.commons.math3.exception.NumberIsTooLargeException;
/*      */ import org.apache.commons.math3.util.FastMath;
/*      */ import org.apache.commons.math3.util.MathArrays;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class DerivativeStructure
/*      */   implements RealFieldElement<DerivativeStructure>, Serializable
/*      */ {
/*      */   private static final long serialVersionUID = 20120730L;
/*      */   private transient DSCompiler compiler;
/*      */   private final double[] data;
/*      */   
/*      */   private DerivativeStructure(DSCompiler compiler) {
/*   76 */     this.compiler = compiler;
/*   77 */     this.data = new double[compiler.getSize()];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DerivativeStructure(int parameters, int order) throws NumberIsTooLargeException {
/*   87 */     this(DSCompiler.getCompiler(parameters, order));
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
/*      */   public DerivativeStructure(int parameters, int order, double value) throws NumberIsTooLargeException {
/*   99 */     this(parameters, order);
/*  100 */     this.data[0] = value;
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
/*      */   public DerivativeStructure(int parameters, int order, int index, double value) throws NumberIsTooLargeException {
/*  118 */     this(parameters, order, value);
/*      */     
/*  120 */     if (index >= parameters) {
/*  121 */       throw new NumberIsTooLargeException(Integer.valueOf(index), Integer.valueOf(parameters), false);
/*      */     }
/*      */     
/*  124 */     if (order > 0)
/*      */     {
/*  126 */       this.data[DSCompiler.getCompiler(index, order).getSize()] = 1.0D;
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
/*      */   public DerivativeStructure(double a1, DerivativeStructure ds1, double a2, DerivativeStructure ds2) throws DimensionMismatchException {
/*  142 */     this(ds1.compiler);
/*  143 */     this.compiler.checkCompatibility(ds2.compiler);
/*  144 */     this.compiler.linearCombination(a1, ds1.data, 0, a2, ds2.data, 0, this.data, 0);
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
/*      */   public DerivativeStructure(double a1, DerivativeStructure ds1, double a2, DerivativeStructure ds2, double a3, DerivativeStructure ds3) throws DimensionMismatchException {
/*  161 */     this(ds1.compiler);
/*  162 */     this.compiler.checkCompatibility(ds2.compiler);
/*  163 */     this.compiler.checkCompatibility(ds3.compiler);
/*  164 */     this.compiler.linearCombination(a1, ds1.data, 0, a2, ds2.data, 0, a3, ds3.data, 0, this.data, 0);
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
/*      */   public DerivativeStructure(double a1, DerivativeStructure ds1, double a2, DerivativeStructure ds2, double a3, DerivativeStructure ds3, double a4, DerivativeStructure ds4) throws DimensionMismatchException {
/*  184 */     this(ds1.compiler);
/*  185 */     this.compiler.checkCompatibility(ds2.compiler);
/*  186 */     this.compiler.checkCompatibility(ds3.compiler);
/*  187 */     this.compiler.checkCompatibility(ds4.compiler);
/*  188 */     this.compiler.linearCombination(a1, ds1.data, 0, a2, ds2.data, 0, a3, ds3.data, 0, a4, ds4.data, 0, this.data, 0);
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
/*      */   public DerivativeStructure(int parameters, int order, double... derivatives) throws DimensionMismatchException, NumberIsTooLargeException {
/*  205 */     this(parameters, order);
/*  206 */     if (derivatives.length != this.data.length) {
/*  207 */       throw new DimensionMismatchException(derivatives.length, this.data.length);
/*      */     }
/*  209 */     System.arraycopy(derivatives, 0, this.data, 0, this.data.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private DerivativeStructure(DerivativeStructure ds) {
/*  216 */     this.compiler = ds.compiler;
/*  217 */     this.data = (double[])ds.data.clone();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getFreeParameters() {
/*  224 */     return this.compiler.getFreeParameters();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getOrder() {
/*  231 */     return this.compiler.getOrder();
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
/*      */   public DerivativeStructure createConstant(double c) {
/*  245 */     return new DerivativeStructure(getFreeParameters(), getOrder(), c);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getReal() {
/*  252 */     return this.data[0];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getValue() {
/*  260 */     return this.data[0];
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
/*      */   public double getPartialDerivative(int... orders) throws DimensionMismatchException, NumberIsTooLargeException {
/*  275 */     return this.data[this.compiler.getPartialDerivativeIndex(orders)];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double[] getAllDerivatives() {
/*  283 */     return (double[])this.data.clone();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DerivativeStructure add(double a) {
/*  290 */     DerivativeStructure ds = new DerivativeStructure(this);
/*  291 */     ds.data[0] = ds.data[0] + a;
/*  292 */     return ds;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DerivativeStructure add(DerivativeStructure a) throws DimensionMismatchException {
/*  301 */     this.compiler.checkCompatibility(a.compiler);
/*  302 */     DerivativeStructure ds = new DerivativeStructure(this);
/*  303 */     this.compiler.add(this.data, 0, a.data, 0, ds.data, 0);
/*  304 */     return ds;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DerivativeStructure subtract(double a) {
/*  311 */     return add(-a);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DerivativeStructure subtract(DerivativeStructure a) throws DimensionMismatchException {
/*  320 */     this.compiler.checkCompatibility(a.compiler);
/*  321 */     DerivativeStructure ds = new DerivativeStructure(this);
/*  322 */     this.compiler.subtract(this.data, 0, a.data, 0, ds.data, 0);
/*  323 */     return ds;
/*      */   }
/*      */ 
/*      */   
/*      */   public DerivativeStructure multiply(int n) {
/*  328 */     return multiply(n);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DerivativeStructure multiply(double a) {
/*  335 */     DerivativeStructure ds = new DerivativeStructure(this);
/*  336 */     for (int i = 0; i < ds.data.length; i++) {
/*  337 */       ds.data[i] = ds.data[i] * a;
/*      */     }
/*  339 */     return ds;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DerivativeStructure multiply(DerivativeStructure a) throws DimensionMismatchException {
/*  348 */     this.compiler.checkCompatibility(a.compiler);
/*  349 */     DerivativeStructure result = new DerivativeStructure(this.compiler);
/*  350 */     this.compiler.multiply(this.data, 0, a.data, 0, result.data, 0);
/*  351 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DerivativeStructure divide(double a) {
/*  358 */     DerivativeStructure ds = new DerivativeStructure(this);
/*  359 */     for (int i = 0; i < ds.data.length; i++) {
/*  360 */       ds.data[i] = ds.data[i] / a;
/*      */     }
/*  362 */     return ds;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DerivativeStructure divide(DerivativeStructure a) throws DimensionMismatchException {
/*  371 */     this.compiler.checkCompatibility(a.compiler);
/*  372 */     DerivativeStructure result = new DerivativeStructure(this.compiler);
/*  373 */     this.compiler.divide(this.data, 0, a.data, 0, result.data, 0);
/*  374 */     return result;
/*      */   }
/*      */ 
/*      */   
/*      */   public DerivativeStructure remainder(double a) {
/*  379 */     DerivativeStructure ds = new DerivativeStructure(this);
/*  380 */     ds.data[0] = FastMath.IEEEremainder(ds.data[0], a);
/*  381 */     return ds;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DerivativeStructure remainder(DerivativeStructure a) throws DimensionMismatchException {
/*  391 */     this.compiler.checkCompatibility(a.compiler);
/*  392 */     DerivativeStructure result = new DerivativeStructure(this.compiler);
/*  393 */     this.compiler.remainder(this.data, 0, a.data, 0, result.data, 0);
/*  394 */     return result;
/*      */   }
/*      */ 
/*      */   
/*      */   public DerivativeStructure negate() {
/*  399 */     DerivativeStructure ds = new DerivativeStructure(this.compiler);
/*  400 */     for (int i = 0; i < ds.data.length; i++) {
/*  401 */       ds.data[i] = -this.data[i];
/*      */     }
/*  403 */     return ds;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DerivativeStructure abs() {
/*  410 */     if (Double.doubleToLongBits(this.data[0]) < 0L)
/*      */     {
/*  412 */       return negate();
/*      */     }
/*  414 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DerivativeStructure ceil() {
/*  422 */     return new DerivativeStructure(this.compiler.getFreeParameters(), this.compiler.getOrder(), FastMath.ceil(this.data[0]));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DerivativeStructure floor() {
/*  431 */     return new DerivativeStructure(this.compiler.getFreeParameters(), this.compiler.getOrder(), FastMath.floor(this.data[0]));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DerivativeStructure rint() {
/*  440 */     return new DerivativeStructure(this.compiler.getFreeParameters(), this.compiler.getOrder(), FastMath.rint(this.data[0]));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long round() {
/*  447 */     return FastMath.round(this.data[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DerivativeStructure signum() {
/*  454 */     return new DerivativeStructure(this.compiler.getFreeParameters(), this.compiler.getOrder(), FastMath.signum(this.data[0]));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DerivativeStructure copySign(DerivativeStructure sign) {
/*  463 */     long m = Double.doubleToLongBits(this.data[0]);
/*  464 */     long s = Double.doubleToLongBits(sign.data[0]);
/*  465 */     if ((m >= 0L && s >= 0L) || (m < 0L && s < 0L)) {
/*  466 */       return this;
/*      */     }
/*  468 */     return negate();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DerivativeStructure copySign(double sign) {
/*  475 */     long m = Double.doubleToLongBits(this.data[0]);
/*  476 */     long s = Double.doubleToLongBits(sign);
/*  477 */     if ((m >= 0L && s >= 0L) || (m < 0L && s < 0L)) {
/*  478 */       return this;
/*      */     }
/*  480 */     return negate();
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
/*      */   public int getExponent() {
/*  492 */     return FastMath.getExponent(this.data[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DerivativeStructure scalb(int n) {
/*  499 */     DerivativeStructure ds = new DerivativeStructure(this.compiler);
/*  500 */     for (int i = 0; i < ds.data.length; i++) {
/*  501 */       ds.data[i] = FastMath.scalb(this.data[i], n);
/*      */     }
/*  503 */     return ds;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DerivativeStructure hypot(DerivativeStructure y) throws DimensionMismatchException {
/*  514 */     this.compiler.checkCompatibility(y.compiler);
/*      */     
/*  516 */     if (Double.isInfinite(this.data[0]) || Double.isInfinite(y.data[0])) {
/*  517 */       return new DerivativeStructure(this.compiler.getFreeParameters(), this.compiler.getFreeParameters(), Double.POSITIVE_INFINITY);
/*      */     }
/*      */     
/*  520 */     if (Double.isNaN(this.data[0]) || Double.isNaN(y.data[0])) {
/*  521 */       return new DerivativeStructure(this.compiler.getFreeParameters(), this.compiler.getFreeParameters(), Double.NaN);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  526 */     int expX = getExponent();
/*  527 */     int expY = y.getExponent();
/*  528 */     if (expX > expY + 27)
/*      */     {
/*  530 */       return abs(); } 
/*  531 */     if (expY > expX + 27)
/*      */     {
/*  533 */       return y.abs();
/*      */     }
/*      */ 
/*      */     
/*  537 */     int middleExp = (expX + expY) / 2;
/*      */ 
/*      */     
/*  540 */     DerivativeStructure scaledX = scalb(-middleExp);
/*  541 */     DerivativeStructure scaledY = y.scalb(-middleExp);
/*      */ 
/*      */     
/*  544 */     DerivativeStructure scaledH = scaledX.multiply(scaledX).add(scaledY.multiply(scaledY)).sqrt();
/*      */ 
/*      */ 
/*      */     
/*  548 */     return scaledH.scalb(middleExp);
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
/*      */   public static DerivativeStructure hypot(DerivativeStructure x, DerivativeStructure y) throws DimensionMismatchException {
/*  574 */     return x.hypot(y);
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
/*      */   public DerivativeStructure compose(double... f) throws DimensionMismatchException {
/*  587 */     if (f.length != getOrder() + 1) {
/*  588 */       throw new DimensionMismatchException(f.length, getOrder() + 1);
/*      */     }
/*  590 */     DerivativeStructure result = new DerivativeStructure(this.compiler);
/*  591 */     this.compiler.compose(this.data, 0, f, result.data, 0);
/*  592 */     return result;
/*      */   }
/*      */ 
/*      */   
/*      */   public DerivativeStructure reciprocal() {
/*  597 */     DerivativeStructure result = new DerivativeStructure(this.compiler);
/*  598 */     this.compiler.pow(this.data, 0, -1, result.data, 0);
/*  599 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DerivativeStructure sqrt() {
/*  606 */     return rootN(2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DerivativeStructure cbrt() {
/*  613 */     return rootN(3);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DerivativeStructure rootN(int n) {
/*  620 */     DerivativeStructure result = new DerivativeStructure(this.compiler);
/*  621 */     this.compiler.rootN(this.data, 0, n, result.data, 0);
/*  622 */     return result;
/*      */   }
/*      */ 
/*      */   
/*      */   public Field<DerivativeStructure> getField() {
/*  627 */     return new Field<DerivativeStructure>()
/*      */       {
/*      */         public DerivativeStructure getZero()
/*      */         {
/*  631 */           return new DerivativeStructure(DerivativeStructure.this.compiler.getFreeParameters(), DerivativeStructure.this.compiler.getOrder(), 0.0D);
/*      */         }
/*      */ 
/*      */         
/*      */         public DerivativeStructure getOne() {
/*  636 */           return new DerivativeStructure(DerivativeStructure.this.compiler.getFreeParameters(), DerivativeStructure.this.compiler.getOrder(), 1.0D);
/*      */         }
/*      */ 
/*      */         
/*      */         public Class<? extends FieldElement<DerivativeStructure>> getRuntimeClass() {
/*  641 */           return (Class)DerivativeStructure.class;
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static DerivativeStructure pow(double a, DerivativeStructure x) {
/*  654 */     DerivativeStructure result = new DerivativeStructure(x.compiler);
/*  655 */     x.compiler.pow(a, x.data, 0, result.data, 0);
/*  656 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DerivativeStructure pow(double p) {
/*  663 */     DerivativeStructure result = new DerivativeStructure(this.compiler);
/*  664 */     this.compiler.pow(this.data, 0, p, result.data, 0);
/*  665 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DerivativeStructure pow(int n) {
/*  672 */     DerivativeStructure result = new DerivativeStructure(this.compiler);
/*  673 */     this.compiler.pow(this.data, 0, n, result.data, 0);
/*  674 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DerivativeStructure pow(DerivativeStructure e) throws DimensionMismatchException {
/*  684 */     this.compiler.checkCompatibility(e.compiler);
/*  685 */     DerivativeStructure result = new DerivativeStructure(this.compiler);
/*  686 */     this.compiler.pow(this.data, 0, e.data, 0, result.data, 0);
/*  687 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DerivativeStructure exp() {
/*  694 */     DerivativeStructure result = new DerivativeStructure(this.compiler);
/*  695 */     this.compiler.exp(this.data, 0, result.data, 0);
/*  696 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DerivativeStructure expm1() {
/*  703 */     DerivativeStructure result = new DerivativeStructure(this.compiler);
/*  704 */     this.compiler.expm1(this.data, 0, result.data, 0);
/*  705 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DerivativeStructure log() {
/*  712 */     DerivativeStructure result = new DerivativeStructure(this.compiler);
/*  713 */     this.compiler.log(this.data, 0, result.data, 0);
/*  714 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DerivativeStructure log1p() {
/*  721 */     DerivativeStructure result = new DerivativeStructure(this.compiler);
/*  722 */     this.compiler.log1p(this.data, 0, result.data, 0);
/*  723 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DerivativeStructure log10() {
/*  730 */     DerivativeStructure result = new DerivativeStructure(this.compiler);
/*  731 */     this.compiler.log10(this.data, 0, result.data, 0);
/*  732 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DerivativeStructure cos() {
/*  739 */     DerivativeStructure result = new DerivativeStructure(this.compiler);
/*  740 */     this.compiler.cos(this.data, 0, result.data, 0);
/*  741 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DerivativeStructure sin() {
/*  748 */     DerivativeStructure result = new DerivativeStructure(this.compiler);
/*  749 */     this.compiler.sin(this.data, 0, result.data, 0);
/*  750 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DerivativeStructure tan() {
/*  757 */     DerivativeStructure result = new DerivativeStructure(this.compiler);
/*  758 */     this.compiler.tan(this.data, 0, result.data, 0);
/*  759 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DerivativeStructure acos() {
/*  766 */     DerivativeStructure result = new DerivativeStructure(this.compiler);
/*  767 */     this.compiler.acos(this.data, 0, result.data, 0);
/*  768 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DerivativeStructure asin() {
/*  775 */     DerivativeStructure result = new DerivativeStructure(this.compiler);
/*  776 */     this.compiler.asin(this.data, 0, result.data, 0);
/*  777 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DerivativeStructure atan() {
/*  784 */     DerivativeStructure result = new DerivativeStructure(this.compiler);
/*  785 */     this.compiler.atan(this.data, 0, result.data, 0);
/*  786 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DerivativeStructure atan2(DerivativeStructure x) throws DimensionMismatchException {
/*  794 */     this.compiler.checkCompatibility(x.compiler);
/*  795 */     DerivativeStructure result = new DerivativeStructure(this.compiler);
/*  796 */     this.compiler.atan2(this.data, 0, x.data, 0, result.data, 0);
/*  797 */     return result;
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
/*      */   public static DerivativeStructure atan2(DerivativeStructure y, DerivativeStructure x) throws DimensionMismatchException {
/*  810 */     return y.atan2(x);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DerivativeStructure cosh() {
/*  817 */     DerivativeStructure result = new DerivativeStructure(this.compiler);
/*  818 */     this.compiler.cosh(this.data, 0, result.data, 0);
/*  819 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DerivativeStructure sinh() {
/*  826 */     DerivativeStructure result = new DerivativeStructure(this.compiler);
/*  827 */     this.compiler.sinh(this.data, 0, result.data, 0);
/*  828 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DerivativeStructure tanh() {
/*  835 */     DerivativeStructure result = new DerivativeStructure(this.compiler);
/*  836 */     this.compiler.tanh(this.data, 0, result.data, 0);
/*  837 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DerivativeStructure acosh() {
/*  844 */     DerivativeStructure result = new DerivativeStructure(this.compiler);
/*  845 */     this.compiler.acosh(this.data, 0, result.data, 0);
/*  846 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DerivativeStructure asinh() {
/*  853 */     DerivativeStructure result = new DerivativeStructure(this.compiler);
/*  854 */     this.compiler.asinh(this.data, 0, result.data, 0);
/*  855 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DerivativeStructure atanh() {
/*  862 */     DerivativeStructure result = new DerivativeStructure(this.compiler);
/*  863 */     this.compiler.atanh(this.data, 0, result.data, 0);
/*  864 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DerivativeStructure toDegrees() {
/*  871 */     DerivativeStructure ds = new DerivativeStructure(this.compiler);
/*  872 */     for (int i = 0; i < ds.data.length; i++) {
/*  873 */       ds.data[i] = FastMath.toDegrees(this.data[i]);
/*      */     }
/*  875 */     return ds;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DerivativeStructure toRadians() {
/*  882 */     DerivativeStructure ds = new DerivativeStructure(this.compiler);
/*  883 */     for (int i = 0; i < ds.data.length; i++) {
/*  884 */       ds.data[i] = FastMath.toRadians(this.data[i]);
/*      */     }
/*  886 */     return ds;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double taylor(double... delta) throws MathArithmeticException {
/*  895 */     return this.compiler.taylor(this.data, 0, delta);
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
/*      */   public DerivativeStructure linearCombination(DerivativeStructure[] a, DerivativeStructure[] b) throws DimensionMismatchException {
/*  907 */     double[] aDouble = new double[a.length];
/*  908 */     for (int i = 0; i < a.length; i++) {
/*  909 */       aDouble[i] = a[i].getValue();
/*      */     }
/*  911 */     double[] bDouble = new double[b.length];
/*  912 */     for (int j = 0; j < b.length; j++) {
/*  913 */       bDouble[j] = b[j].getValue();
/*      */     }
/*  915 */     double accurateValue = MathArrays.linearCombination(aDouble, bDouble);
/*      */ 
/*      */     
/*  918 */     DerivativeStructure simpleValue = (DerivativeStructure)a[0].getField().getZero();
/*  919 */     for (int k = 0; k < a.length; k++) {
/*  920 */       simpleValue = simpleValue.add(a[k].multiply(b[k]));
/*      */     }
/*      */ 
/*      */     
/*  924 */     double[] all = simpleValue.getAllDerivatives();
/*  925 */     all[0] = accurateValue;
/*  926 */     return new DerivativeStructure(simpleValue.getFreeParameters(), simpleValue.getOrder(), all);
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
/*      */   public DerivativeStructure linearCombination(double[] a, DerivativeStructure[] b) throws DimensionMismatchException {
/*  939 */     double[] bDouble = new double[b.length];
/*  940 */     for (int i = 0; i < b.length; i++) {
/*  941 */       bDouble[i] = b[i].getValue();
/*      */     }
/*  943 */     double accurateValue = MathArrays.linearCombination(a, bDouble);
/*      */ 
/*      */     
/*  946 */     DerivativeStructure simpleValue = (DerivativeStructure)b[0].getField().getZero();
/*  947 */     for (int j = 0; j < a.length; j++) {
/*  948 */       simpleValue = simpleValue.add(b[j].multiply(a[j]));
/*      */     }
/*      */ 
/*      */     
/*  952 */     double[] all = simpleValue.getAllDerivatives();
/*  953 */     all[0] = accurateValue;
/*  954 */     return new DerivativeStructure(simpleValue.getFreeParameters(), simpleValue.getOrder(), all);
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
/*      */   public DerivativeStructure linearCombination(DerivativeStructure a1, DerivativeStructure b1, DerivativeStructure a2, DerivativeStructure b2) throws DimensionMismatchException {
/*  968 */     double accurateValue = MathArrays.linearCombination(a1.getValue(), b1.getValue(), a2.getValue(), b2.getValue());
/*      */ 
/*      */ 
/*      */     
/*  972 */     DerivativeStructure simpleValue = a1.multiply(b1).add(a2.multiply(b2));
/*      */ 
/*      */     
/*  975 */     double[] all = simpleValue.getAllDerivatives();
/*  976 */     all[0] = accurateValue;
/*  977 */     return new DerivativeStructure(getFreeParameters(), getOrder(), all);
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
/*      */   public DerivativeStructure linearCombination(double a1, DerivativeStructure b1, double a2, DerivativeStructure b2) throws DimensionMismatchException {
/*  991 */     double accurateValue = MathArrays.linearCombination(a1, b1.getValue(), a2, b2.getValue());
/*      */ 
/*      */ 
/*      */     
/*  995 */     DerivativeStructure simpleValue = b1.multiply(a1).add(b2.multiply(a2));
/*      */ 
/*      */     
/*  998 */     double[] all = simpleValue.getAllDerivatives();
/*  999 */     all[0] = accurateValue;
/* 1000 */     return new DerivativeStructure(getFreeParameters(), getOrder(), all);
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
/*      */   public DerivativeStructure linearCombination(DerivativeStructure a1, DerivativeStructure b1, DerivativeStructure a2, DerivativeStructure b2, DerivativeStructure a3, DerivativeStructure b3) throws DimensionMismatchException {
/* 1015 */     double accurateValue = MathArrays.linearCombination(a1.getValue(), b1.getValue(), a2.getValue(), b2.getValue(), a3.getValue(), b3.getValue());
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1020 */     DerivativeStructure simpleValue = a1.multiply(b1).add(a2.multiply(b2)).add(a3.multiply(b3));
/*      */ 
/*      */     
/* 1023 */     double[] all = simpleValue.getAllDerivatives();
/* 1024 */     all[0] = accurateValue;
/* 1025 */     return new DerivativeStructure(getFreeParameters(), getOrder(), all);
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
/*      */   public DerivativeStructure linearCombination(double a1, DerivativeStructure b1, double a2, DerivativeStructure b2, double a3, DerivativeStructure b3) throws DimensionMismatchException {
/* 1040 */     double accurateValue = MathArrays.linearCombination(a1, b1.getValue(), a2, b2.getValue(), a3, b3.getValue());
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1045 */     DerivativeStructure simpleValue = b1.multiply(a1).add(b2.multiply(a2)).add(b3.multiply(a3));
/*      */ 
/*      */     
/* 1048 */     double[] all = simpleValue.getAllDerivatives();
/* 1049 */     all[0] = accurateValue;
/* 1050 */     return new DerivativeStructure(getFreeParameters(), getOrder(), all);
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
/*      */   public DerivativeStructure linearCombination(DerivativeStructure a1, DerivativeStructure b1, DerivativeStructure a2, DerivativeStructure b2, DerivativeStructure a3, DerivativeStructure b3, DerivativeStructure a4, DerivativeStructure b4) throws DimensionMismatchException {
/* 1066 */     double accurateValue = MathArrays.linearCombination(a1.getValue(), b1.getValue(), a2.getValue(), b2.getValue(), a3.getValue(), b3.getValue(), a4.getValue(), b4.getValue());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1072 */     DerivativeStructure simpleValue = a1.multiply(b1).add(a2.multiply(b2)).add(a3.multiply(b3)).add(a4.multiply(b4));
/*      */ 
/*      */     
/* 1075 */     double[] all = simpleValue.getAllDerivatives();
/* 1076 */     all[0] = accurateValue;
/* 1077 */     return new DerivativeStructure(getFreeParameters(), getOrder(), all);
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
/*      */   public DerivativeStructure linearCombination(double a1, DerivativeStructure b1, double a2, DerivativeStructure b2, double a3, DerivativeStructure b3, double a4, DerivativeStructure b4) throws DimensionMismatchException {
/* 1093 */     double accurateValue = MathArrays.linearCombination(a1, b1.getValue(), a2, b2.getValue(), a3, b3.getValue(), a4, b4.getValue());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1099 */     DerivativeStructure simpleValue = b1.multiply(a1).add(b2.multiply(a2)).add(b3.multiply(a3)).add(b4.multiply(a4));
/*      */ 
/*      */     
/* 1102 */     double[] all = simpleValue.getAllDerivatives();
/* 1103 */     all[0] = accurateValue;
/* 1104 */     return new DerivativeStructure(getFreeParameters(), getOrder(), all);
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
/*      */   public boolean equals(Object other) {
/* 1121 */     if (this == other) {
/* 1122 */       return true;
/*      */     }
/*      */     
/* 1125 */     if (other instanceof DerivativeStructure) {
/* 1126 */       DerivativeStructure rhs = (DerivativeStructure)other;
/* 1127 */       return (getFreeParameters() == rhs.getFreeParameters() && getOrder() == rhs.getOrder() && MathArrays.equals(this.data, rhs.data));
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1132 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int hashCode() {
/* 1143 */     return 227 + 229 * getFreeParameters() + 233 * getOrder() + 239 * MathUtils.hash(this.data);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Object writeReplace() {
/* 1151 */     return new DataTransferObject(this.compiler.getFreeParameters(), this.compiler.getOrder(), this.data);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class DataTransferObject
/*      */     implements Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 20120730L;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final int variables;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final int order;
/*      */ 
/*      */ 
/*      */     
/*      */     private final double[] data;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     DataTransferObject(int variables, int order, double[] data) {
/* 1181 */       this.variables = variables;
/* 1182 */       this.order = order;
/* 1183 */       this.data = data;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Object readResolve() {
/* 1190 */       return new DerivativeStructure(this.variables, this.order, this.data);
/*      */     }
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/analysis/differentiation/DerivativeStructure.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */