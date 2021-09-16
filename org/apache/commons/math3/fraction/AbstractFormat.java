/*     */ package org.apache.commons.math3.fraction;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.text.FieldPosition;
/*     */ import java.text.NumberFormat;
/*     */ import java.text.ParsePosition;
/*     */ import java.util.Locale;
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
/*     */ public abstract class AbstractFormat
/*     */   extends NumberFormat
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -6981118387974191891L;
/*     */   private NumberFormat denominatorFormat;
/*     */   private NumberFormat numeratorFormat;
/*     */   
/*     */   protected AbstractFormat() {
/*  49 */     this(getDefaultNumberFormat());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractFormat(NumberFormat format) {
/*  58 */     this(format, (NumberFormat)format.clone());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractFormat(NumberFormat numeratorFormat, NumberFormat denominatorFormat) {
/*  69 */     this.numeratorFormat = numeratorFormat;
/*  70 */     this.denominatorFormat = denominatorFormat;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static NumberFormat getDefaultNumberFormat() {
/*  80 */     return getDefaultNumberFormat(Locale.getDefault());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static NumberFormat getDefaultNumberFormat(Locale locale) {
/*  91 */     NumberFormat nf = NumberFormat.getNumberInstance(locale);
/*  92 */     nf.setMaximumFractionDigits(0);
/*  93 */     nf.setParseIntegerOnly(true);
/*  94 */     return nf;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NumberFormat getDenominatorFormat() {
/* 102 */     return this.denominatorFormat;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NumberFormat getNumeratorFormat() {
/* 110 */     return this.numeratorFormat;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDenominatorFormat(NumberFormat format) {
/* 119 */     if (format == null) {
/* 120 */       throw new NullArgumentException(LocalizedFormats.DENOMINATOR_FORMAT, new Object[0]);
/*     */     }
/* 122 */     this.denominatorFormat = format;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNumeratorFormat(NumberFormat format) {
/* 131 */     if (format == null) {
/* 132 */       throw new NullArgumentException(LocalizedFormats.NUMERATOR_FORMAT, new Object[0]);
/*     */     }
/* 134 */     this.numeratorFormat = format;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static void parseAndIgnoreWhitespace(String source, ParsePosition pos) {
/* 145 */     parseNextCharacter(source, pos);
/* 146 */     pos.setIndex(pos.getIndex() - 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static char parseNextCharacter(String source, ParsePosition pos) {
/* 157 */     int index = pos.getIndex();
/* 158 */     int n = source.length();
/* 159 */     char ret = Character.MIN_VALUE;
/*     */     
/* 161 */     if (index < n) {
/*     */       char c;
/*     */       do {
/* 164 */         c = source.charAt(index++);
/* 165 */       } while (Character.isWhitespace(c) && index < n);
/* 166 */       pos.setIndex(index);
/*     */       
/* 168 */       if (index < n) {
/* 169 */         ret = c;
/*     */       }
/*     */     } 
/*     */     
/* 173 */     return ret;
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
/*     */   public StringBuffer format(double value, StringBuffer buffer, FieldPosition position) {
/* 189 */     return format(Double.valueOf(value), buffer, position);
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
/*     */   public StringBuffer format(long value, StringBuffer buffer, FieldPosition position) {
/* 206 */     return format(Long.valueOf(value), buffer, position);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/fraction/AbstractFormat.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */