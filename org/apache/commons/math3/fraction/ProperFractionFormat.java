/*     */ package org.apache.commons.math3.fraction;
/*     */ 
/*     */ import java.text.FieldPosition;
/*     */ import java.text.NumberFormat;
/*     */ import java.text.ParsePosition;
/*     */ import org.apache.commons.math3.exception.NullArgumentException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.util.FastMath;
/*     */ import org.apache.commons.math3.util.MathUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ProperFractionFormat
/*     */   extends FractionFormat
/*     */ {
/*     */   private static final long serialVersionUID = 760934726031766749L;
/*     */   private NumberFormat wholeFormat;
/*     */   
/*     */   public ProperFractionFormat() {
/*  51 */     this(getDefaultNumberFormat());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ProperFractionFormat(NumberFormat format) {
/*  61 */     this(format, (NumberFormat)format.clone(), (NumberFormat)format.clone());
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
/*     */   public ProperFractionFormat(NumberFormat wholeFormat, NumberFormat numeratorFormat, NumberFormat denominatorFormat) {
/*  75 */     super(numeratorFormat, denominatorFormat);
/*  76 */     setWholeFormat(wholeFormat);
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
/*     */   public StringBuffer format(Fraction fraction, StringBuffer toAppendTo, FieldPosition pos) {
/*  93 */     pos.setBeginIndex(0);
/*  94 */     pos.setEndIndex(0);
/*     */     
/*  96 */     int num = fraction.getNumerator();
/*  97 */     int den = fraction.getDenominator();
/*  98 */     int whole = num / den;
/*  99 */     num %= den;
/*     */     
/* 101 */     if (whole != 0) {
/* 102 */       getWholeFormat().format(whole, toAppendTo, pos);
/* 103 */       toAppendTo.append(' ');
/* 104 */       num = FastMath.abs(num);
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
/*     */   public Fraction parse(String source, ParsePosition pos) {
/* 136 */     Fraction ret = super.parse(source, pos);
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
/* 147 */     Number whole = getWholeFormat().parse(source, pos);
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
/* 160 */     Number num = getNumeratorFormat().parse(source, pos);
/* 161 */     if (num == null) {
/*     */ 
/*     */ 
/*     */       
/* 165 */       pos.setIndex(initialIndex);
/* 166 */       return null;
/*     */     } 
/*     */     
/* 169 */     if (num.intValue() < 0) {
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
/* 182 */         return new Fraction(num.intValue(), 1);
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
/* 199 */     Number den = getDenominatorFormat().parse(source, pos);
/* 200 */     if (den == null) {
/*     */ 
/*     */ 
/*     */       
/* 204 */       pos.setIndex(initialIndex);
/* 205 */       return null;
/*     */     } 
/*     */     
/* 208 */     if (den.intValue() < 0) {
/*     */       
/* 210 */       pos.setIndex(initialIndex);
/* 211 */       return null;
/*     */     } 
/*     */     
/* 214 */     int w = whole.intValue();
/* 215 */     int n = num.intValue();
/* 216 */     int d = den.intValue();
/* 217 */     return new Fraction((FastMath.abs(w) * d + n) * MathUtils.copySign(1, w), d);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWholeFormat(NumberFormat format) {
/* 226 */     if (format == null) {
/* 227 */       throw new NullArgumentException(LocalizedFormats.WHOLE_FORMAT, new Object[0]);
/*     */     }
/* 229 */     this.wholeFormat = format;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/fraction/ProperFractionFormat.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */