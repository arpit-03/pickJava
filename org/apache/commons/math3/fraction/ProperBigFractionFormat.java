/*     */ package org.apache.commons.math3.fraction;
/*     */ 
/*     */ import java.math.BigInteger;
/*     */ import java.text.FieldPosition;
/*     */ import java.text.NumberFormat;
/*     */ import java.text.ParsePosition;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ProperBigFractionFormat
/*     */   extends BigFractionFormat
/*     */ {
/*     */   private static final long serialVersionUID = -6337346779577272307L;
/*     */   private NumberFormat wholeFormat;
/*     */   
/*     */   public ProperBigFractionFormat() {
/*  50 */     this(getDefaultNumberFormat());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ProperBigFractionFormat(NumberFormat format) {
/*  60 */     this(format, (NumberFormat)format.clone(), (NumberFormat)format.clone());
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
/*     */   public ProperBigFractionFormat(NumberFormat wholeFormat, NumberFormat numeratorFormat, NumberFormat denominatorFormat) {
/*  73 */     super(numeratorFormat, denominatorFormat);
/*  74 */     setWholeFormat(wholeFormat);
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
/*     */   public StringBuffer format(BigFraction fraction, StringBuffer toAppendTo, FieldPosition pos) {
/*  91 */     pos.setBeginIndex(0);
/*  92 */     pos.setEndIndex(0);
/*     */     
/*  94 */     BigInteger num = fraction.getNumerator();
/*  95 */     BigInteger den = fraction.getDenominator();
/*  96 */     BigInteger whole = num.divide(den);
/*  97 */     num = num.remainder(den);
/*     */     
/*  99 */     if (!BigInteger.ZERO.equals(whole)) {
/* 100 */       getWholeFormat().format(whole, toAppendTo, pos);
/* 101 */       toAppendTo.append(' ');
/* 102 */       if (num.compareTo(BigInteger.ZERO) < 0) {
/* 103 */         num = num.negate();
/*     */       }
/*     */     } 
/* 106 */     getNumeratorFormat().format(num, toAppendTo, pos);
/* 107 */     toAppendTo.append(" / ");
/* 108 */     getDenominatorFormat().format(den, toAppendTo, pos);
/*     */     
/* 110 */     return toAppendTo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NumberFormat getWholeFormat() {
/* 118 */     return this.wholeFormat;
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
/*     */   public BigFraction parse(String source, ParsePosition pos) {
/* 136 */     BigFraction ret = super.parse(source, pos);
/* 137 */     if (ret != null) {
/* 138 */       return ret;
/*     */     }
/*     */     
/* 141 */     int initialIndex = pos.getIndex();
/*     */ 
/*     */     
/* 144 */     parseAndIgnoreWhitespace(source, pos);
/*     */ 
/*     */     
/* 147 */     BigInteger whole = parseNextBigInteger(source, pos);
/* 148 */     if (whole == null) {
/*     */ 
/*     */ 
/*     */       
/* 152 */       pos.setIndex(initialIndex);
/* 153 */       return null;
/*     */     } 
/*     */ 
/*     */     
/* 157 */     parseAndIgnoreWhitespace(source, pos);
/*     */ 
/*     */     
/* 160 */     BigInteger num = parseNextBigInteger(source, pos);
/* 161 */     if (num == null) {
/*     */ 
/*     */ 
/*     */       
/* 165 */       pos.setIndex(initialIndex);
/* 166 */       return null;
/*     */     } 
/*     */     
/* 169 */     if (num.compareTo(BigInteger.ZERO) < 0) {
/*     */       
/* 171 */       pos.setIndex(initialIndex);
/* 172 */       return null;
/*     */     } 
/*     */ 
/*     */     
/* 176 */     int startIndex = pos.getIndex();
/* 177 */     char c = parseNextCharacter(source, pos);
/* 178 */     switch (c) {
/*     */ 
/*     */       
/*     */       case '\000':
/* 182 */         return new BigFraction(num);
/*     */ 
/*     */       
/*     */       case '/':
/*     */         break;
/*     */ 
/*     */       
/*     */       default:
/* 190 */         pos.setIndex(initialIndex);
/* 191 */         pos.setErrorIndex(startIndex);
/* 192 */         return null;
/*     */     } 
/*     */ 
/*     */     
/* 196 */     parseAndIgnoreWhitespace(source, pos);
/*     */ 
/*     */     
/* 199 */     BigInteger den = parseNextBigInteger(source, pos);
/* 200 */     if (den == null) {
/*     */ 
/*     */ 
/*     */       
/* 204 */       pos.setIndex(initialIndex);
/* 205 */       return null;
/*     */     } 
/*     */     
/* 208 */     if (den.compareTo(BigInteger.ZERO) < 0) {
/*     */       
/* 210 */       pos.setIndex(initialIndex);
/* 211 */       return null;
/*     */     } 
/*     */     
/* 214 */     boolean wholeIsNeg = (whole.compareTo(BigInteger.ZERO) < 0);
/* 215 */     if (wholeIsNeg) {
/* 216 */       whole = whole.negate();
/*     */     }
/* 218 */     num = whole.multiply(den).add(num);
/* 219 */     if (wholeIsNeg) {
/* 220 */       num = num.negate();
/*     */     }
/*     */     
/* 223 */     return new BigFraction(num, den);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWholeFormat(NumberFormat format) {
/* 233 */     if (format == null) {
/* 234 */       throw new NullArgumentException(LocalizedFormats.WHOLE_FORMAT, new Object[0]);
/*     */     }
/* 236 */     this.wholeFormat = format;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/fraction/ProperBigFractionFormat.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */