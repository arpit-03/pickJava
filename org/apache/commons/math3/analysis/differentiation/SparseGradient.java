/*     */ package org.apache.commons.math3.analysis.differentiation;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.math3.Field;
/*     */ import org.apache.commons.math3.FieldElement;
/*     */ import org.apache.commons.math3.RealFieldElement;
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.MathArithmeticException;
/*     */ import org.apache.commons.math3.exception.NullArgumentException;
/*     */ import org.apache.commons.math3.util.FastMath;
/*     */ import org.apache.commons.math3.util.MathArrays;
/*     */ import org.apache.commons.math3.util.MathUtils;
/*     */ import org.apache.commons.math3.util.Precision;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SparseGradient
/*     */   implements RealFieldElement<SparseGradient>, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 20131025L;
/*     */   private double value;
/*     */   private final Map<Integer, Double> derivatives;
/*     */   
/*     */   private SparseGradient(double value, Map<Integer, Double> derivatives) {
/*  63 */     this.value = value;
/*  64 */     this.derivatives = new HashMap<Integer, Double>();
/*  65 */     if (derivatives != null) {
/*  66 */       this.derivatives.putAll(derivatives);
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
/*     */   private SparseGradient(double value, double scale, Map<Integer, Double> derivatives) {
/*  79 */     this.value = value;
/*  80 */     this.derivatives = new HashMap<Integer, Double>();
/*  81 */     if (derivatives != null) {
/*  82 */       for (Map.Entry<Integer, Double> entry : derivatives.entrySet()) {
/*  83 */         this.derivatives.put(entry.getKey(), Double.valueOf(scale * ((Double)entry.getValue()).doubleValue()));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SparseGradient createConstant(double value) {
/*  93 */     return new SparseGradient(value, Collections.emptyMap());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SparseGradient createVariable(int idx, double value) {
/* 102 */     return new SparseGradient(value, Collections.singletonMap(Integer.valueOf(idx), Double.valueOf(1.0D)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int numVars() {
/* 110 */     return this.derivatives.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getDerivative(int index) {
/* 120 */     Double out = this.derivatives.get(Integer.valueOf(index));
/* 121 */     return (out == null) ? 0.0D : out.doubleValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getValue() {
/* 129 */     return this.value;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getReal() {
/* 134 */     return this.value;
/*     */   }
/*     */ 
/*     */   
/*     */   public SparseGradient add(SparseGradient a) {
/* 139 */     SparseGradient out = new SparseGradient(this.value + a.value, this.derivatives);
/* 140 */     for (Map.Entry<Integer, Double> entry : a.derivatives.entrySet()) {
/* 141 */       int id = ((Integer)entry.getKey()).intValue();
/* 142 */       Double old = out.derivatives.get(Integer.valueOf(id));
/* 143 */       if (old == null) {
/* 144 */         out.derivatives.put(Integer.valueOf(id), entry.getValue()); continue;
/*     */       } 
/* 146 */       out.derivatives.put(Integer.valueOf(id), Double.valueOf(old.doubleValue() + ((Double)entry.getValue()).doubleValue()));
/*     */     } 
/*     */ 
/*     */     
/* 150 */     return out;
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
/*     */   public void addInPlace(SparseGradient a) {
/* 166 */     this.value += a.value;
/* 167 */     for (Map.Entry<Integer, Double> entry : a.derivatives.entrySet()) {
/* 168 */       int id = ((Integer)entry.getKey()).intValue();
/* 169 */       Double old = this.derivatives.get(Integer.valueOf(id));
/* 170 */       if (old == null) {
/* 171 */         this.derivatives.put(Integer.valueOf(id), entry.getValue()); continue;
/*     */       } 
/* 173 */       this.derivatives.put(Integer.valueOf(id), Double.valueOf(old.doubleValue() + ((Double)entry.getValue()).doubleValue()));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SparseGradient add(double c) {
/* 180 */     SparseGradient out = new SparseGradient(this.value + c, this.derivatives);
/* 181 */     return out;
/*     */   }
/*     */ 
/*     */   
/*     */   public SparseGradient subtract(SparseGradient a) {
/* 186 */     SparseGradient out = new SparseGradient(this.value - a.value, this.derivatives);
/* 187 */     for (Map.Entry<Integer, Double> entry : a.derivatives.entrySet()) {
/* 188 */       int id = ((Integer)entry.getKey()).intValue();
/* 189 */       Double old = out.derivatives.get(Integer.valueOf(id));
/* 190 */       if (old == null) {
/* 191 */         out.derivatives.put(Integer.valueOf(id), Double.valueOf(-((Double)entry.getValue()).doubleValue())); continue;
/*     */       } 
/* 193 */       out.derivatives.put(Integer.valueOf(id), Double.valueOf(old.doubleValue() - ((Double)entry.getValue()).doubleValue()));
/*     */     } 
/*     */     
/* 196 */     return out;
/*     */   }
/*     */ 
/*     */   
/*     */   public SparseGradient subtract(double c) {
/* 201 */     return new SparseGradient(this.value - c, this.derivatives);
/*     */   }
/*     */ 
/*     */   
/*     */   public SparseGradient multiply(SparseGradient a) {
/* 206 */     SparseGradient out = new SparseGradient(this.value * a.value, Collections.emptyMap());
/*     */ 
/*     */ 
/*     */     
/* 210 */     for (Map.Entry<Integer, Double> entry : this.derivatives.entrySet()) {
/* 211 */       out.derivatives.put(entry.getKey(), Double.valueOf(a.value * ((Double)entry.getValue()).doubleValue()));
/*     */     }
/* 213 */     for (Map.Entry<Integer, Double> entry : a.derivatives.entrySet()) {
/* 214 */       int id = ((Integer)entry.getKey()).intValue();
/* 215 */       Double old = out.derivatives.get(Integer.valueOf(id));
/* 216 */       if (old == null) {
/* 217 */         out.derivatives.put(Integer.valueOf(id), Double.valueOf(this.value * ((Double)entry.getValue()).doubleValue())); continue;
/*     */       } 
/* 219 */       out.derivatives.put(Integer.valueOf(id), Double.valueOf(old.doubleValue() + this.value * ((Double)entry.getValue()).doubleValue()));
/*     */     } 
/*     */     
/* 222 */     return out;
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
/*     */   public void multiplyInPlace(SparseGradient a) {
/* 239 */     for (Map.Entry<Integer, Double> entry : this.derivatives.entrySet()) {
/* 240 */       this.derivatives.put(entry.getKey(), Double.valueOf(a.value * ((Double)entry.getValue()).doubleValue()));
/*     */     }
/* 242 */     for (Map.Entry<Integer, Double> entry : a.derivatives.entrySet()) {
/* 243 */       int id = ((Integer)entry.getKey()).intValue();
/* 244 */       Double old = this.derivatives.get(Integer.valueOf(id));
/* 245 */       if (old == null) {
/* 246 */         this.derivatives.put(Integer.valueOf(id), Double.valueOf(this.value * ((Double)entry.getValue()).doubleValue())); continue;
/*     */       } 
/* 248 */       this.derivatives.put(Integer.valueOf(id), Double.valueOf(old.doubleValue() + this.value * ((Double)entry.getValue()).doubleValue()));
/*     */     } 
/*     */     
/* 251 */     this.value *= a.value;
/*     */   }
/*     */ 
/*     */   
/*     */   public SparseGradient multiply(double c) {
/* 256 */     return new SparseGradient(this.value * c, c, this.derivatives);
/*     */   }
/*     */ 
/*     */   
/*     */   public SparseGradient multiply(int n) {
/* 261 */     return new SparseGradient(this.value * n, n, this.derivatives);
/*     */   }
/*     */ 
/*     */   
/*     */   public SparseGradient divide(SparseGradient a) {
/* 266 */     SparseGradient out = new SparseGradient(this.value / a.value, Collections.emptyMap());
/*     */ 
/*     */     
/* 269 */     for (Map.Entry<Integer, Double> entry : this.derivatives.entrySet()) {
/* 270 */       out.derivatives.put(entry.getKey(), Double.valueOf(((Double)entry.getValue()).doubleValue() / a.value));
/*     */     }
/* 272 */     for (Map.Entry<Integer, Double> entry : a.derivatives.entrySet()) {
/* 273 */       int id = ((Integer)entry.getKey()).intValue();
/* 274 */       Double old = out.derivatives.get(Integer.valueOf(id));
/* 275 */       if (old == null) {
/* 276 */         out.derivatives.put(Integer.valueOf(id), Double.valueOf(-out.value / a.value * ((Double)entry.getValue()).doubleValue())); continue;
/*     */       } 
/* 278 */       out.derivatives.put(Integer.valueOf(id), Double.valueOf(old.doubleValue() - out.value / a.value * ((Double)entry.getValue()).doubleValue()));
/*     */     } 
/*     */     
/* 281 */     return out;
/*     */   }
/*     */ 
/*     */   
/*     */   public SparseGradient divide(double c) {
/* 286 */     return new SparseGradient(this.value / c, 1.0D / c, this.derivatives);
/*     */   }
/*     */ 
/*     */   
/*     */   public SparseGradient negate() {
/* 291 */     return new SparseGradient(-this.value, -1.0D, this.derivatives);
/*     */   }
/*     */ 
/*     */   
/*     */   public Field<SparseGradient> getField() {
/* 296 */     return new Field<SparseGradient>()
/*     */       {
/*     */         public SparseGradient getZero()
/*     */         {
/* 300 */           return SparseGradient.createConstant(0.0D);
/*     */         }
/*     */ 
/*     */         
/*     */         public SparseGradient getOne() {
/* 305 */           return SparseGradient.createConstant(1.0D);
/*     */         }
/*     */ 
/*     */         
/*     */         public Class<? extends FieldElement<SparseGradient>> getRuntimeClass() {
/* 310 */           return (Class)SparseGradient.class;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SparseGradient remainder(double a) {
/* 318 */     return new SparseGradient(FastMath.IEEEremainder(this.value, a), this.derivatives);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SparseGradient remainder(SparseGradient a) {
/* 325 */     double rem = FastMath.IEEEremainder(this.value, a.value);
/* 326 */     double k = FastMath.rint((this.value - rem) / a.value);
/*     */     
/* 328 */     return subtract(a.multiply(k));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SparseGradient abs() {
/* 334 */     if (Double.doubleToLongBits(this.value) < 0L)
/*     */     {
/* 336 */       return negate();
/*     */     }
/* 338 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SparseGradient ceil() {
/* 344 */     return createConstant(FastMath.ceil(this.value));
/*     */   }
/*     */ 
/*     */   
/*     */   public SparseGradient floor() {
/* 349 */     return createConstant(FastMath.floor(this.value));
/*     */   }
/*     */ 
/*     */   
/*     */   public SparseGradient rint() {
/* 354 */     return createConstant(FastMath.rint(this.value));
/*     */   }
/*     */ 
/*     */   
/*     */   public long round() {
/* 359 */     return FastMath.round(this.value);
/*     */   }
/*     */ 
/*     */   
/*     */   public SparseGradient signum() {
/* 364 */     return createConstant(FastMath.signum(this.value));
/*     */   }
/*     */ 
/*     */   
/*     */   public SparseGradient copySign(SparseGradient sign) {
/* 369 */     long m = Double.doubleToLongBits(this.value);
/* 370 */     long s = Double.doubleToLongBits(sign.value);
/* 371 */     if ((m >= 0L && s >= 0L) || (m < 0L && s < 0L)) {
/* 372 */       return this;
/*     */     }
/* 374 */     return negate();
/*     */   }
/*     */ 
/*     */   
/*     */   public SparseGradient copySign(double sign) {
/* 379 */     long m = Double.doubleToLongBits(this.value);
/* 380 */     long s = Double.doubleToLongBits(sign);
/* 381 */     if ((m >= 0L && s >= 0L) || (m < 0L && s < 0L)) {
/* 382 */       return this;
/*     */     }
/* 384 */     return negate();
/*     */   }
/*     */ 
/*     */   
/*     */   public SparseGradient scalb(int n) {
/* 389 */     SparseGradient out = new SparseGradient(FastMath.scalb(this.value, n), Collections.emptyMap());
/* 390 */     for (Map.Entry<Integer, Double> entry : this.derivatives.entrySet()) {
/* 391 */       out.derivatives.put(entry.getKey(), Double.valueOf(FastMath.scalb(((Double)entry.getValue()).doubleValue(), n)));
/*     */     }
/* 393 */     return out;
/*     */   }
/*     */ 
/*     */   
/*     */   public SparseGradient hypot(SparseGradient y) {
/* 398 */     if (Double.isInfinite(this.value) || Double.isInfinite(y.value))
/* 399 */       return createConstant(Double.POSITIVE_INFINITY); 
/* 400 */     if (Double.isNaN(this.value) || Double.isNaN(y.value)) {
/* 401 */       return createConstant(Double.NaN);
/*     */     }
/*     */     
/* 404 */     int expX = FastMath.getExponent(this.value);
/* 405 */     int expY = FastMath.getExponent(y.value);
/* 406 */     if (expX > expY + 27)
/*     */     {
/* 408 */       return abs(); } 
/* 409 */     if (expY > expX + 27)
/*     */     {
/* 411 */       return y.abs();
/*     */     }
/*     */ 
/*     */     
/* 415 */     int middleExp = (expX + expY) / 2;
/*     */ 
/*     */     
/* 418 */     SparseGradient scaledX = scalb(-middleExp);
/* 419 */     SparseGradient scaledY = y.scalb(-middleExp);
/*     */ 
/*     */     
/* 422 */     SparseGradient scaledH = scaledX.multiply(scaledX).add(scaledY.multiply(scaledY)).sqrt();
/*     */ 
/*     */ 
/*     */     
/* 426 */     return scaledH.scalb(middleExp);
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
/*     */   public static SparseGradient hypot(SparseGradient x, SparseGradient y) {
/* 448 */     return x.hypot(y);
/*     */   }
/*     */ 
/*     */   
/*     */   public SparseGradient reciprocal() {
/* 453 */     return new SparseGradient(1.0D / this.value, -1.0D / this.value * this.value, this.derivatives);
/*     */   }
/*     */ 
/*     */   
/*     */   public SparseGradient sqrt() {
/* 458 */     double sqrt = FastMath.sqrt(this.value);
/* 459 */     return new SparseGradient(sqrt, 0.5D / sqrt, this.derivatives);
/*     */   }
/*     */ 
/*     */   
/*     */   public SparseGradient cbrt() {
/* 464 */     double cbrt = FastMath.cbrt(this.value);
/* 465 */     return new SparseGradient(cbrt, 1.0D / 3.0D * cbrt * cbrt, this.derivatives);
/*     */   }
/*     */ 
/*     */   
/*     */   public SparseGradient rootN(int n) {
/* 470 */     if (n == 2)
/* 471 */       return sqrt(); 
/* 472 */     if (n == 3) {
/* 473 */       return cbrt();
/*     */     }
/* 475 */     double root = FastMath.pow(this.value, 1.0D / n);
/* 476 */     return new SparseGradient(root, 1.0D / n * FastMath.pow(root, n - 1), this.derivatives);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SparseGradient pow(double p) {
/* 482 */     return new SparseGradient(FastMath.pow(this.value, p), p * FastMath.pow(this.value, p - 1.0D), this.derivatives);
/*     */   }
/*     */ 
/*     */   
/*     */   public SparseGradient pow(int n) {
/* 487 */     if (n == 0) {
/* 488 */       return (SparseGradient)getField().getOne();
/*     */     }
/* 490 */     double valueNm1 = FastMath.pow(this.value, n - 1);
/* 491 */     return new SparseGradient(this.value * valueNm1, n * valueNm1, this.derivatives);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SparseGradient pow(SparseGradient e) {
/* 497 */     return log().multiply(e).exp();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SparseGradient pow(double a, SparseGradient x) {
/* 506 */     if (a == 0.0D) {
/* 507 */       if (x.value == 0.0D)
/* 508 */         return x.compose(1.0D, Double.NEGATIVE_INFINITY); 
/* 509 */       if (x.value < 0.0D) {
/* 510 */         return x.compose(Double.NaN, Double.NaN);
/*     */       }
/* 512 */       return (SparseGradient)x.getField().getZero();
/*     */     } 
/*     */     
/* 515 */     double ax = FastMath.pow(a, x.value);
/* 516 */     return new SparseGradient(ax, ax * FastMath.log(a), x.derivatives);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SparseGradient exp() {
/* 522 */     double e = FastMath.exp(this.value);
/* 523 */     return new SparseGradient(e, e, this.derivatives);
/*     */   }
/*     */ 
/*     */   
/*     */   public SparseGradient expm1() {
/* 528 */     return new SparseGradient(FastMath.expm1(this.value), FastMath.exp(this.value), this.derivatives);
/*     */   }
/*     */ 
/*     */   
/*     */   public SparseGradient log() {
/* 533 */     return new SparseGradient(FastMath.log(this.value), 1.0D / this.value, this.derivatives);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SparseGradient log10() {
/* 540 */     return new SparseGradient(FastMath.log10(this.value), 1.0D / FastMath.log(10.0D) * this.value, this.derivatives);
/*     */   }
/*     */ 
/*     */   
/*     */   public SparseGradient log1p() {
/* 545 */     return new SparseGradient(FastMath.log1p(this.value), 1.0D / (1.0D + this.value), this.derivatives);
/*     */   }
/*     */ 
/*     */   
/*     */   public SparseGradient cos() {
/* 550 */     return new SparseGradient(FastMath.cos(this.value), -FastMath.sin(this.value), this.derivatives);
/*     */   }
/*     */ 
/*     */   
/*     */   public SparseGradient sin() {
/* 555 */     return new SparseGradient(FastMath.sin(this.value), FastMath.cos(this.value), this.derivatives);
/*     */   }
/*     */ 
/*     */   
/*     */   public SparseGradient tan() {
/* 560 */     double t = FastMath.tan(this.value);
/* 561 */     return new SparseGradient(t, 1.0D + t * t, this.derivatives);
/*     */   }
/*     */ 
/*     */   
/*     */   public SparseGradient acos() {
/* 566 */     return new SparseGradient(FastMath.acos(this.value), -1.0D / FastMath.sqrt(1.0D - this.value * this.value), this.derivatives);
/*     */   }
/*     */ 
/*     */   
/*     */   public SparseGradient asin() {
/* 571 */     return new SparseGradient(FastMath.asin(this.value), 1.0D / FastMath.sqrt(1.0D - this.value * this.value), this.derivatives);
/*     */   }
/*     */ 
/*     */   
/*     */   public SparseGradient atan() {
/* 576 */     return new SparseGradient(FastMath.atan(this.value), 1.0D / (1.0D + this.value * this.value), this.derivatives);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SparseGradient atan2(SparseGradient x) {
/* 583 */     SparseGradient a, r = multiply(this).add(x.multiply(x)).sqrt();
/*     */ 
/*     */     
/* 586 */     if (x.value >= 0.0D) {
/*     */ 
/*     */       
/* 589 */       a = divide(r.add(x)).atan().multiply(2);
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 594 */       SparseGradient tmp = divide(r.subtract(x)).atan().multiply(-2);
/* 595 */       a = tmp.add((tmp.value <= 0.0D) ? -3.141592653589793D : Math.PI);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 600 */     a.value = FastMath.atan2(this.value, x.value);
/*     */     
/* 602 */     return a;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SparseGradient atan2(SparseGradient y, SparseGradient x) {
/* 612 */     return y.atan2(x);
/*     */   }
/*     */ 
/*     */   
/*     */   public SparseGradient cosh() {
/* 617 */     return new SparseGradient(FastMath.cosh(this.value), FastMath.sinh(this.value), this.derivatives);
/*     */   }
/*     */ 
/*     */   
/*     */   public SparseGradient sinh() {
/* 622 */     return new SparseGradient(FastMath.sinh(this.value), FastMath.cosh(this.value), this.derivatives);
/*     */   }
/*     */ 
/*     */   
/*     */   public SparseGradient tanh() {
/* 627 */     double t = FastMath.tanh(this.value);
/* 628 */     return new SparseGradient(t, 1.0D - t * t, this.derivatives);
/*     */   }
/*     */ 
/*     */   
/*     */   public SparseGradient acosh() {
/* 633 */     return new SparseGradient(FastMath.acosh(this.value), 1.0D / FastMath.sqrt(this.value * this.value - 1.0D), this.derivatives);
/*     */   }
/*     */ 
/*     */   
/*     */   public SparseGradient asinh() {
/* 638 */     return new SparseGradient(FastMath.asinh(this.value), 1.0D / FastMath.sqrt(this.value * this.value + 1.0D), this.derivatives);
/*     */   }
/*     */ 
/*     */   
/*     */   public SparseGradient atanh() {
/* 643 */     return new SparseGradient(FastMath.atanh(this.value), 1.0D / (1.0D - this.value * this.value), this.derivatives);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SparseGradient toDegrees() {
/* 650 */     return new SparseGradient(FastMath.toDegrees(this.value), FastMath.toDegrees(1.0D), this.derivatives);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SparseGradient toRadians() {
/* 657 */     return new SparseGradient(FastMath.toRadians(this.value), FastMath.toRadians(1.0D), this.derivatives);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double taylor(double... delta) {
/* 665 */     double y = this.value;
/* 666 */     for (int i = 0; i < delta.length; i++) {
/* 667 */       y += delta[i] * getDerivative(i);
/*     */     }
/* 669 */     return y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SparseGradient compose(double f0, double f1) {
/* 679 */     return new SparseGradient(f0, f1, this.derivatives);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SparseGradient linearCombination(SparseGradient[] a, SparseGradient[] b) throws DimensionMismatchException {
/* 688 */     SparseGradient out = (SparseGradient)a[0].getField().getZero();
/* 689 */     for (int i = 0; i < a.length; i++) {
/* 690 */       out = out.add(a[i].multiply(b[i]));
/*     */     }
/*     */ 
/*     */     
/* 694 */     double[] aDouble = new double[a.length];
/* 695 */     for (int j = 0; j < a.length; j++) {
/* 696 */       aDouble[j] = a[j].getValue();
/*     */     }
/* 698 */     double[] bDouble = new double[b.length];
/* 699 */     for (int k = 0; k < b.length; k++) {
/* 700 */       bDouble[k] = b[k].getValue();
/*     */     }
/* 702 */     out.value = MathArrays.linearCombination(aDouble, bDouble);
/*     */     
/* 704 */     return out;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SparseGradient linearCombination(double[] a, SparseGradient[] b) {
/* 712 */     SparseGradient out = (SparseGradient)b[0].getField().getZero();
/* 713 */     for (int i = 0; i < a.length; i++) {
/* 714 */       out = out.add(b[i].multiply(a[i]));
/*     */     }
/*     */ 
/*     */     
/* 718 */     double[] bDouble = new double[b.length];
/* 719 */     for (int j = 0; j < b.length; j++) {
/* 720 */       bDouble[j] = b[j].getValue();
/*     */     }
/* 722 */     out.value = MathArrays.linearCombination(a, bDouble);
/*     */     
/* 724 */     return out;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SparseGradient linearCombination(SparseGradient a1, SparseGradient b1, SparseGradient a2, SparseGradient b2) {
/* 733 */     SparseGradient out = a1.multiply(b1).add(a2.multiply(b2));
/*     */ 
/*     */     
/* 736 */     out.value = MathArrays.linearCombination(a1.value, b1.value, a2.value, b2.value);
/*     */     
/* 738 */     return out;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SparseGradient linearCombination(double a1, SparseGradient b1, double a2, SparseGradient b2) {
/* 747 */     SparseGradient out = b1.multiply(a1).add(b2.multiply(a2));
/*     */ 
/*     */     
/* 750 */     out.value = MathArrays.linearCombination(a1, b1.value, a2, b2.value);
/*     */     
/* 752 */     return out;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SparseGradient linearCombination(SparseGradient a1, SparseGradient b1, SparseGradient a2, SparseGradient b2, SparseGradient a3, SparseGradient b3) {
/* 762 */     SparseGradient out = a1.multiply(b1).add(a2.multiply(b2)).add(a3.multiply(b3));
/*     */ 
/*     */     
/* 765 */     out.value = MathArrays.linearCombination(a1.value, b1.value, a2.value, b2.value, a3.value, b3.value);
/*     */ 
/*     */ 
/*     */     
/* 769 */     return out;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SparseGradient linearCombination(double a1, SparseGradient b1, double a2, SparseGradient b2, double a3, SparseGradient b3) {
/* 779 */     SparseGradient out = b1.multiply(a1).add(b2.multiply(a2)).add(b3.multiply(a3));
/*     */ 
/*     */     
/* 782 */     out.value = MathArrays.linearCombination(a1, b1.value, a2, b2.value, a3, b3.value);
/*     */ 
/*     */ 
/*     */     
/* 786 */     return out;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SparseGradient linearCombination(SparseGradient a1, SparseGradient b1, SparseGradient a2, SparseGradient b2, SparseGradient a3, SparseGradient b3, SparseGradient a4, SparseGradient b4) {
/* 797 */     SparseGradient out = a1.multiply(b1).add(a2.multiply(b2)).add(a3.multiply(b3)).add(a4.multiply(b4));
/*     */ 
/*     */     
/* 800 */     out.value = MathArrays.linearCombination(a1.value, b1.value, a2.value, b2.value, a3.value, b3.value, a4.value, b4.value);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 805 */     return out;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SparseGradient linearCombination(double a1, SparseGradient b1, double a2, SparseGradient b2, double a3, SparseGradient b3, double a4, SparseGradient b4) {
/* 816 */     SparseGradient out = b1.multiply(a1).add(b2.multiply(a2)).add(b3.multiply(a3)).add(b4.multiply(a4));
/*     */ 
/*     */     
/* 819 */     out.value = MathArrays.linearCombination(a1, b1.value, a2, b2.value, a3, b3.value, a4, b4.value);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 824 */     return out;
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
/*     */   public boolean equals(Object other) {
/* 840 */     if (this == other) {
/* 841 */       return true;
/*     */     }
/*     */     
/* 844 */     if (other instanceof SparseGradient) {
/* 845 */       SparseGradient rhs = (SparseGradient)other;
/* 846 */       if (!Precision.equals(this.value, rhs.value, 1)) {
/* 847 */         return false;
/*     */       }
/* 849 */       if (this.derivatives.size() != rhs.derivatives.size()) {
/* 850 */         return false;
/*     */       }
/* 852 */       for (Map.Entry<Integer, Double> entry : this.derivatives.entrySet()) {
/* 853 */         if (!rhs.derivatives.containsKey(entry.getKey())) {
/* 854 */           return false;
/*     */         }
/* 856 */         if (!Precision.equals(((Double)entry.getValue()).doubleValue(), ((Double)rhs.derivatives.get(entry.getKey())).doubleValue(), 1)) {
/* 857 */           return false;
/*     */         }
/*     */       } 
/* 860 */       return true;
/*     */     } 
/*     */     
/* 863 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 874 */     return 743 + 809 * MathUtils.hash(this.value) + 167 * this.derivatives.hashCode();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/analysis/differentiation/SparseGradient.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */