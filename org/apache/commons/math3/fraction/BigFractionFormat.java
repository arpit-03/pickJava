/*     */ package org.apache.commons.math3.fraction;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.math.BigInteger;
/*     */ import java.text.FieldPosition;
/*     */ import java.text.NumberFormat;
/*     */ import java.text.ParseException;
/*     */ import java.text.ParsePosition;
/*     */ import java.util.Locale;
/*     */ import org.apache.commons.math3.exception.MathIllegalArgumentException;
/*     */ import org.apache.commons.math3.exception.MathParseException;
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
/*     */ public class BigFractionFormat
/*     */   extends AbstractFormat
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -2932167925527338976L;
/*     */   
/*     */   public BigFractionFormat() {}
/*     */   
/*     */   public BigFractionFormat(NumberFormat format) {
/*  58 */     super(format);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BigFractionFormat(NumberFormat numeratorFormat, NumberFormat denominatorFormat) {
/*  69 */     super(numeratorFormat, denominatorFormat);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Locale[] getAvailableLocales() {
/*  78 */     return NumberFormat.getAvailableLocales();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String formatBigFraction(BigFraction f) {
/*  89 */     return getImproperInstance().format(f);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BigFractionFormat getImproperInstance() {
/*  97 */     return getImproperInstance(Locale.getDefault());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BigFractionFormat getImproperInstance(Locale locale) {
/* 106 */     return new BigFractionFormat(getDefaultNumberFormat(locale));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BigFractionFormat getProperInstance() {
/* 114 */     return getProperInstance(Locale.getDefault());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BigFractionFormat getProperInstance(Locale locale) {
/* 123 */     return new ProperBigFractionFormat(getDefaultNumberFormat(locale));
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
/*     */   public StringBuffer format(BigFraction bigFraction, StringBuffer toAppendTo, FieldPosition pos) {
/* 139 */     pos.setBeginIndex(0);
/* 140 */     pos.setEndIndex(0);
/*     */     
/* 142 */     getNumeratorFormat().format(bigFraction.getNumerator(), toAppendTo, pos);
/* 143 */     toAppendTo.append(" / ");
/* 144 */     getDenominatorFormat().format(bigFraction.getDenominator(), toAppendTo, pos);
/*     */     
/* 146 */     return toAppendTo;
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
/*     */   public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
/*     */     StringBuffer ret;
/* 168 */     if (obj instanceof BigFraction) {
/* 169 */       ret = format((BigFraction)obj, toAppendTo, pos);
/* 170 */     } else if (obj instanceof BigInteger) {
/* 171 */       ret = format(new BigFraction((BigInteger)obj), toAppendTo, pos);
/* 172 */     } else if (obj instanceof Number) {
/* 173 */       ret = format(new BigFraction(((Number)obj).doubleValue()), toAppendTo, pos);
/*     */     } else {
/*     */       
/* 176 */       throw new MathIllegalArgumentException(LocalizedFormats.CANNOT_FORMAT_OBJECT_TO_FRACTION, new Object[0]);
/*     */     } 
/*     */     
/* 179 */     return ret;
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
/*     */   public BigFraction parse(String source) throws MathParseException {
/* 191 */     ParsePosition parsePosition = new ParsePosition(0);
/* 192 */     BigFraction result = parse(source, parsePosition);
/* 193 */     if (parsePosition.getIndex() == 0) {
/* 194 */       throw new MathParseException(source, parsePosition.getErrorIndex(), BigFraction.class);
/*     */     }
/* 196 */     return result;
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
/*     */   public BigFraction parse(String source, ParsePosition pos) {
/* 208 */     int initialIndex = pos.getIndex();
/*     */ 
/*     */     
/* 211 */     parseAndIgnoreWhitespace(source, pos);
/*     */ 
/*     */     
/* 214 */     BigInteger num = parseNextBigInteger(source, pos);
/* 215 */     if (num == null) {
/*     */ 
/*     */ 
/*     */       
/* 219 */       pos.setIndex(initialIndex);
/* 220 */       return null;
/*     */     } 
/*     */ 
/*     */     
/* 224 */     int startIndex = pos.getIndex();
/* 225 */     char c = parseNextCharacter(source, pos);
/* 226 */     switch (c) {
/*     */ 
/*     */       
/*     */       case '\000':
/* 230 */         return new BigFraction(num);
/*     */ 
/*     */       
/*     */       case '/':
/*     */         break;
/*     */ 
/*     */       
/*     */       default:
/* 238 */         pos.setIndex(initialIndex);
/* 239 */         pos.setErrorIndex(startIndex);
/* 240 */         return null;
/*     */     } 
/*     */ 
/*     */     
/* 244 */     parseAndIgnoreWhitespace(source, pos);
/*     */ 
/*     */     
/* 247 */     BigInteger den = parseNextBigInteger(source, pos);
/* 248 */     if (den == null) {
/*     */ 
/*     */ 
/*     */       
/* 252 */       pos.setIndex(initialIndex);
/* 253 */       return null;
/*     */     } 
/*     */     
/* 256 */     return new BigFraction(num, den);
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
/*     */   protected BigInteger parseNextBigInteger(String source, ParsePosition pos) {
/* 269 */     int start = pos.getIndex();
/* 270 */     int end = (source.charAt(start) == '-') ? (start + 1) : start;
/* 271 */     while (end < source.length() && Character.isDigit(source.charAt(end)))
/*     */     {
/* 273 */       end++;
/*     */     }
/*     */     
/*     */     try {
/* 277 */       BigInteger n = new BigInteger(source.substring(start, end));
/* 278 */       pos.setIndex(end);
/* 279 */       return n;
/* 280 */     } catch (NumberFormatException nfe) {
/* 281 */       pos.setErrorIndex(start);
/* 282 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/fraction/BigFractionFormat.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */