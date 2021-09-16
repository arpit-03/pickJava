/*     */ package org.apache.commons.math3.util;
/*     */ 
/*     */ import java.text.FieldPosition;
/*     */ import java.text.NumberFormat;
/*     */ import java.text.ParsePosition;
/*     */ import java.util.Locale;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CompositeFormat
/*     */ {
/*     */   public static NumberFormat getDefaultNumberFormat() {
/*  42 */     return getDefaultNumberFormat(Locale.getDefault());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static NumberFormat getDefaultNumberFormat(Locale locale) {
/*  53 */     NumberFormat nf = NumberFormat.getInstance(locale);
/*  54 */     nf.setMaximumFractionDigits(10);
/*  55 */     return nf;
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
/*     */   public static void parseAndIgnoreWhitespace(String source, ParsePosition pos) {
/*  67 */     parseNextCharacter(source, pos);
/*  68 */     pos.setIndex(pos.getIndex() - 1);
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
/*     */   public static char parseNextCharacter(String source, ParsePosition pos) {
/*  80 */     int index = pos.getIndex();
/*  81 */     int n = source.length();
/*  82 */     char ret = Character.MIN_VALUE;
/*     */     
/*  84 */     if (index < n) {
/*     */       char c;
/*     */       do {
/*  87 */         c = source.charAt(index++);
/*  88 */       } while (Character.isWhitespace(c) && index < n);
/*  89 */       pos.setIndex(index);
/*     */       
/*  91 */       if (index < n) {
/*  92 */         ret = c;
/*     */       }
/*     */     } 
/*     */     
/*  96 */     return ret;
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
/*     */   private static Number parseNumber(String source, double value, ParsePosition pos) {
/* 110 */     Number ret = null;
/*     */     
/* 112 */     StringBuilder sb = new StringBuilder();
/* 113 */     sb.append('(');
/* 114 */     sb.append(value);
/* 115 */     sb.append(')');
/*     */     
/* 117 */     int n = sb.length();
/* 118 */     int startIndex = pos.getIndex();
/* 119 */     int endIndex = startIndex + n;
/* 120 */     if (endIndex < source.length() && source.substring(startIndex, endIndex).compareTo(sb.toString()) == 0) {
/*     */       
/* 122 */       ret = Double.valueOf(value);
/* 123 */       pos.setIndex(endIndex);
/*     */     } 
/*     */     
/* 126 */     return ret;
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
/*     */   public static Number parseNumber(String source, NumberFormat format, ParsePosition pos) {
/* 141 */     int startIndex = pos.getIndex();
/* 142 */     Number number = format.parse(source, pos);
/* 143 */     int endIndex = pos.getIndex();
/*     */ 
/*     */     
/* 146 */     if (startIndex == endIndex) {
/*     */       
/* 148 */       double[] special = { Double.NaN, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY };
/*     */ 
/*     */       
/* 151 */       for (int i = 0; i < special.length; i++) {
/* 152 */         number = parseNumber(source, special[i], pos);
/* 153 */         if (number != null) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 159 */     return number;
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
/*     */   public static boolean parseFixedstring(String source, String expected, ParsePosition pos) {
/* 173 */     int startIndex = pos.getIndex();
/* 174 */     int endIndex = startIndex + expected.length();
/* 175 */     if (startIndex >= source.length() || endIndex > source.length() || source.substring(startIndex, endIndex).compareTo(expected) != 0) {
/*     */ 
/*     */ 
/*     */       
/* 179 */       pos.setIndex(startIndex);
/* 180 */       pos.setErrorIndex(startIndex);
/* 181 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 185 */     pos.setIndex(endIndex);
/* 186 */     return true;
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
/*     */   
/*     */   public static StringBuffer formatDouble(double value, NumberFormat format, StringBuffer toAppendTo, FieldPosition pos) {
/* 209 */     if (Double.isNaN(value) || Double.isInfinite(value)) {
/* 210 */       toAppendTo.append('(');
/* 211 */       toAppendTo.append(value);
/* 212 */       toAppendTo.append(')');
/*     */     } else {
/* 214 */       format.format(value, toAppendTo, pos);
/*     */     } 
/* 216 */     return toAppendTo;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/util/CompositeFormat.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */