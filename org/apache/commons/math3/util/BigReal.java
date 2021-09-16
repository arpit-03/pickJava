/*     */ package org.apache.commons.math3.util;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.math.BigDecimal;
/*     */ import java.math.BigInteger;
/*     */ import java.math.MathContext;
/*     */ import java.math.RoundingMode;
/*     */ import org.apache.commons.math3.Field;
/*     */ import org.apache.commons.math3.FieldElement;
/*     */ import org.apache.commons.math3.exception.MathArithmeticException;
/*     */ import org.apache.commons.math3.exception.NullArgumentException;
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
/*     */ public class BigReal
/*     */   implements FieldElement<BigReal>, Comparable<BigReal>, Serializable
/*     */ {
/*  42 */   public static final BigReal ZERO = new BigReal(BigDecimal.ZERO);
/*     */ 
/*     */   
/*  45 */   public static final BigReal ONE = new BigReal(BigDecimal.ONE);
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID = 4984534880991310382L;
/*     */ 
/*     */   
/*     */   private final BigDecimal d;
/*     */ 
/*     */   
/*  54 */   private RoundingMode roundingMode = RoundingMode.HALF_UP;
/*     */ 
/*     */   
/*  57 */   private int scale = 64;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BigReal(BigDecimal val) {
/*  63 */     this.d = val;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BigReal(BigInteger val) {
/*  70 */     this.d = new BigDecimal(val);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BigReal(BigInteger unscaledVal, int scale) {
/*  78 */     this.d = new BigDecimal(unscaledVal, scale);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BigReal(BigInteger unscaledVal, int scale, MathContext mc) {
/*  87 */     this.d = new BigDecimal(unscaledVal, scale, mc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BigReal(BigInteger val, MathContext mc) {
/*  95 */     this.d = new BigDecimal(val, mc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BigReal(char[] in) {
/* 102 */     this.d = new BigDecimal(in);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BigReal(char[] in, int offset, int len) {
/* 111 */     this.d = new BigDecimal(in, offset, len);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BigReal(char[] in, int offset, int len, MathContext mc) {
/* 121 */     this.d = new BigDecimal(in, offset, len, mc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BigReal(char[] in, MathContext mc) {
/* 129 */     this.d = new BigDecimal(in, mc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BigReal(double val) {
/* 136 */     this.d = new BigDecimal(val);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BigReal(double val, MathContext mc) {
/* 144 */     this.d = new BigDecimal(val, mc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BigReal(int val) {
/* 151 */     this.d = new BigDecimal(val);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BigReal(int val, MathContext mc) {
/* 159 */     this.d = new BigDecimal(val, mc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BigReal(long val) {
/* 166 */     this.d = new BigDecimal(val);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BigReal(long val, MathContext mc) {
/* 174 */     this.d = new BigDecimal(val, mc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BigReal(String val) {
/* 181 */     this.d = new BigDecimal(val);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BigReal(String val, MathContext mc) {
/* 189 */     this.d = new BigDecimal(val, mc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RoundingMode getRoundingMode() {
/* 199 */     return this.roundingMode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRoundingMode(RoundingMode roundingMode) {
/* 208 */     this.roundingMode = roundingMode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getScale() {
/* 218 */     return this.scale;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setScale(int scale) {
/* 227 */     this.scale = scale;
/*     */   }
/*     */ 
/*     */   
/*     */   public BigReal add(BigReal a) {
/* 232 */     return new BigReal(this.d.add(a.d));
/*     */   }
/*     */ 
/*     */   
/*     */   public BigReal subtract(BigReal a) {
/* 237 */     return new BigReal(this.d.subtract(a.d));
/*     */   }
/*     */ 
/*     */   
/*     */   public BigReal negate() {
/* 242 */     return new BigReal(this.d.negate());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BigReal divide(BigReal a) throws MathArithmeticException {
/*     */     try {
/* 252 */       return new BigReal(this.d.divide(a.d, this.scale, this.roundingMode));
/* 253 */     } catch (ArithmeticException e) {
/*     */       
/* 255 */       throw new MathArithmeticException(LocalizedFormats.ZERO_NOT_ALLOWED, new Object[0]);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BigReal reciprocal() throws MathArithmeticException {
/*     */     try {
/* 266 */       return new BigReal(BigDecimal.ONE.divide(this.d, this.scale, this.roundingMode));
/* 267 */     } catch (ArithmeticException e) {
/*     */       
/* 269 */       throw new MathArithmeticException(LocalizedFormats.ZERO_NOT_ALLOWED, new Object[0]);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public BigReal multiply(BigReal a) {
/* 275 */     return new BigReal(this.d.multiply(a.d));
/*     */   }
/*     */ 
/*     */   
/*     */   public BigReal multiply(int n) {
/* 280 */     return new BigReal(this.d.multiply(new BigDecimal(n)));
/*     */   }
/*     */ 
/*     */   
/*     */   public int compareTo(BigReal a) {
/* 285 */     return this.d.compareTo(a.d);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double doubleValue() {
/* 292 */     return this.d.doubleValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BigDecimal bigDecimalValue() {
/* 299 */     return this.d;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object other) {
/* 305 */     if (this == other) {
/* 306 */       return true;
/*     */     }
/*     */     
/* 309 */     if (other instanceof BigReal) {
/* 310 */       return this.d.equals(((BigReal)other).d);
/*     */     }
/* 312 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 318 */     return this.d.hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public Field<BigReal> getField() {
/* 323 */     return BigRealField.getInstance();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/util/BigReal.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */