/*     */ package org.jfree.chart.axis;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.text.DateFormat;
/*     */ import java.text.FieldPosition;
/*     */ import java.text.NumberFormat;
/*     */ import java.text.ParsePosition;
/*     */ import java.util.Arrays;
/*     */ import java.util.Date;
/*     */ import java.util.GregorianCalendar;
/*     */ import java.util.TimeZone;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class QuarterDateFormat
/*     */   extends DateFormat
/*     */   implements Cloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -6738465248529797176L;
/*  72 */   public static final String[] REGULAR_QUARTERS = new String[] { "1", "2", "3", "4" };
/*     */ 
/*     */ 
/*     */   
/*  76 */   public static final String[] ROMAN_QUARTERS = new String[] { "I", "II", "III", "IV" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  84 */   public static final String[] GREEK_QUARTERS = new String[] { "Α", "Β", "Γ", "Δ" };
/*     */ 
/*     */ 
/*     */   
/*  88 */   private String[] quarters = REGULAR_QUARTERS;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean quarterFirst;
/*     */ 
/*     */ 
/*     */   
/*     */   public QuarterDateFormat() {
/*  97 */     this(TimeZone.getDefault());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public QuarterDateFormat(TimeZone zone) {
/* 106 */     this(zone, REGULAR_QUARTERS);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public QuarterDateFormat(TimeZone zone, String[] quarterSymbols) {
/* 116 */     this(zone, quarterSymbols, false);
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
/*     */   public QuarterDateFormat(TimeZone zone, String[] quarterSymbols, boolean quarterFirst) {
/* 131 */     ParamChecks.nullNotPermitted(zone, "zone");
/* 132 */     this.calendar = new GregorianCalendar(zone);
/* 133 */     this.quarters = quarterSymbols;
/* 134 */     this.quarterFirst = quarterFirst;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 139 */     this.numberFormat = NumberFormat.getNumberInstance();
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
/*     */   public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition fieldPosition) {
/* 155 */     this.calendar.setTime(date);
/* 156 */     int year = this.calendar.get(1);
/* 157 */     int month = this.calendar.get(2);
/* 158 */     int quarter = month / 3;
/* 159 */     if (this.quarterFirst) {
/* 160 */       toAppendTo.append(this.quarters[quarter]);
/* 161 */       toAppendTo.append(" ");
/* 162 */       toAppendTo.append(year);
/*     */     } else {
/*     */       
/* 165 */       toAppendTo.append(year);
/* 166 */       toAppendTo.append(" ");
/* 167 */       toAppendTo.append(this.quarters[quarter]);
/*     */     } 
/* 169 */     return toAppendTo;
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
/*     */   public Date parse(String source, ParsePosition pos) {
/* 182 */     return null;
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
/*     */   public boolean equals(Object obj) {
/* 194 */     if (obj == this) {
/* 195 */       return true;
/*     */     }
/* 197 */     if (!(obj instanceof QuarterDateFormat)) {
/* 198 */       return false;
/*     */     }
/* 200 */     QuarterDateFormat that = (QuarterDateFormat)obj;
/* 201 */     if (!Arrays.equals((Object[])this.quarters, (Object[])that.quarters)) {
/* 202 */       return false;
/*     */     }
/* 204 */     if (this.quarterFirst != that.quarterFirst) {
/* 205 */       return false;
/*     */     }
/* 207 */     return super.equals(obj);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/axis/QuarterDateFormat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */