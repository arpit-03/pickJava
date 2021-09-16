/*     */ package org.apache.commons.math3.fraction;
/*     */ 
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
/*     */ public class FractionFormat
/*     */   extends AbstractFormat
/*     */ {
/*     */   private static final long serialVersionUID = 3008655719530972611L;
/*     */   
/*     */   public FractionFormat() {}
/*     */   
/*     */   public FractionFormat(NumberFormat format) {
/*  54 */     super(format);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FractionFormat(NumberFormat numeratorFormat, NumberFormat denominatorFormat) {
/*  65 */     super(numeratorFormat, denominatorFormat);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Locale[] getAvailableLocales() {
/*  74 */     return NumberFormat.getAvailableLocales();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String formatFraction(Fraction f) {
/*  85 */     return getImproperInstance().format(f);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static FractionFormat getImproperInstance() {
/*  93 */     return getImproperInstance(Locale.getDefault());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static FractionFormat getImproperInstance(Locale locale) {
/* 102 */     return new FractionFormat(getDefaultNumberFormat(locale));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static FractionFormat getProperInstance() {
/* 110 */     return getProperInstance(Locale.getDefault());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static FractionFormat getProperInstance(Locale locale) {
/* 119 */     return new ProperFractionFormat(getDefaultNumberFormat(locale));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static NumberFormat getDefaultNumberFormat() {
/* 129 */     return getDefaultNumberFormat(Locale.getDefault());
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
/*     */   public StringBuffer format(Fraction fraction, StringBuffer toAppendTo, FieldPosition pos) {
/* 145 */     pos.setBeginIndex(0);
/* 146 */     pos.setEndIndex(0);
/*     */     
/* 148 */     getNumeratorFormat().format(fraction.getNumerator(), toAppendTo, pos);
/* 149 */     toAppendTo.append(" / ");
/* 150 */     getDenominatorFormat().format(fraction.getDenominator(), toAppendTo, pos);
/*     */ 
/*     */     
/* 153 */     return toAppendTo;
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
/*     */   public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) throws FractionConversionException, MathIllegalArgumentException {
/* 174 */     StringBuffer ret = null;
/*     */     
/* 176 */     if (obj instanceof Fraction) {
/* 177 */       ret = format((Fraction)obj, toAppendTo, pos);
/* 178 */     } else if (obj instanceof Number) {
/* 179 */       ret = format(new Fraction(((Number)obj).doubleValue()), toAppendTo, pos);
/*     */     } else {
/* 181 */       throw new MathIllegalArgumentException(LocalizedFormats.CANNOT_FORMAT_OBJECT_TO_FRACTION, new Object[0]);
/*     */     } 
/*     */     
/* 184 */     return ret;
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
/*     */   public Fraction parse(String source) throws MathParseException {
/* 196 */     ParsePosition parsePosition = new ParsePosition(0);
/* 197 */     Fraction result = parse(source, parsePosition);
/* 198 */     if (parsePosition.getIndex() == 0) {
/* 199 */       throw new MathParseException(source, parsePosition.getErrorIndex(), Fraction.class);
/*     */     }
/* 201 */     return result;
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
/*     */   public Fraction parse(String source, ParsePosition pos) {
/* 213 */     int initialIndex = pos.getIndex();
/*     */ 
/*     */     
/* 216 */     parseAndIgnoreWhitespace(source, pos);
/*     */ 
/*     */     
/* 219 */     Number num = getNumeratorFormat().parse(source, pos);
/* 220 */     if (num == null) {
/*     */ 
/*     */ 
/*     */       
/* 224 */       pos.setIndex(initialIndex);
/* 225 */       return null;
/*     */     } 
/*     */ 
/*     */     
/* 229 */     int startIndex = pos.getIndex();
/* 230 */     char c = parseNextCharacter(source, pos);
/* 231 */     switch (c) {
/*     */ 
/*     */       
/*     */       case '\000':
/* 235 */         return new Fraction(num.intValue(), 1);
/*     */ 
/*     */       
/*     */       case '/':
/*     */         break;
/*     */ 
/*     */       
/*     */       default:
/* 243 */         pos.setIndex(initialIndex);
/* 244 */         pos.setErrorIndex(startIndex);
/* 245 */         return null;
/*     */     } 
/*     */ 
/*     */     
/* 249 */     parseAndIgnoreWhitespace(source, pos);
/*     */ 
/*     */     
/* 252 */     Number den = getDenominatorFormat().parse(source, pos);
/* 253 */     if (den == null) {
/*     */ 
/*     */ 
/*     */       
/* 257 */       pos.setIndex(initialIndex);
/* 258 */       return null;
/*     */     } 
/*     */     
/* 261 */     return new Fraction(num.intValue(), den.intValue());
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/fraction/FractionFormat.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */