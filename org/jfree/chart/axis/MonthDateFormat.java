/*     */ package org.jfree.chart.axis;
/*     */ 
/*     */ import java.text.DateFormat;
/*     */ import java.text.DateFormatSymbols;
/*     */ import java.text.FieldPosition;
/*     */ import java.text.NumberFormat;
/*     */ import java.text.ParsePosition;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Arrays;
/*     */ import java.util.Date;
/*     */ import java.util.GregorianCalendar;
/*     */ import java.util.Locale;
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
/*     */ 
/*     */ 
/*     */ public class MonthDateFormat
/*     */   extends DateFormat
/*     */ {
/*     */   private String[] months;
/*     */   private boolean[] showYear;
/*     */   private DateFormat yearFormatter;
/*     */   
/*     */   public MonthDateFormat() {
/*  79 */     this(TimeZone.getDefault());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MonthDateFormat(TimeZone zone) {
/*  88 */     this(zone, Locale.getDefault(), 1, true, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MonthDateFormat(Locale locale) {
/*  98 */     this(TimeZone.getDefault(), locale, 1, true, false);
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
/*     */   public MonthDateFormat(TimeZone zone, int chars) {
/* 111 */     this(zone, Locale.getDefault(), chars, true, false);
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
/*     */   public MonthDateFormat(Locale locale, int chars) {
/* 124 */     this(TimeZone.getDefault(), locale, chars, true, false);
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
/*     */   public MonthDateFormat(TimeZone zone, Locale locale, int chars, boolean showYearForJan, boolean showYearForDec) {
/* 146 */     this(zone, locale, chars, new boolean[] { showYearForJan, false, false, false, false, false, false, false, false, false, false, false, showYearForDec }, new SimpleDateFormat("yy"));
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
/*     */   public MonthDateFormat(TimeZone zone, Locale locale, int chars, boolean[] showYear, DateFormat yearFormatter) {
/* 167 */     ParamChecks.nullNotPermitted(locale, "locale");
/* 168 */     DateFormatSymbols dfs = new DateFormatSymbols(locale);
/* 169 */     String[] monthsFromLocale = dfs.getMonths();
/* 170 */     this.months = new String[12];
/* 171 */     for (int i = 0; i < 12; i++) {
/* 172 */       if (chars > 0) {
/* 173 */         this.months[i] = monthsFromLocale[i].substring(0, 
/* 174 */             Math.min(chars, monthsFromLocale[i].length()));
/*     */       } else {
/*     */         
/* 177 */         this.months[i] = monthsFromLocale[i];
/*     */       } 
/*     */     } 
/* 180 */     this.calendar = new GregorianCalendar(zone);
/* 181 */     this.showYear = showYear;
/* 182 */     this.yearFormatter = yearFormatter;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 187 */     this.numberFormat = NumberFormat.getNumberInstance();
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
/*     */   public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition fieldPosition) {
/* 202 */     this.calendar.setTime(date);
/* 203 */     int month = this.calendar.get(2);
/* 204 */     toAppendTo.append(this.months[month]);
/* 205 */     if (this.showYear[month]) {
/* 206 */       toAppendTo.append(this.yearFormatter.format(date));
/*     */     }
/* 208 */     return toAppendTo;
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
/* 221 */     return null;
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
/* 233 */     if (obj == this) {
/* 234 */       return true;
/*     */     }
/* 236 */     if (!(obj instanceof MonthDateFormat)) {
/* 237 */       return false;
/*     */     }
/* 239 */     if (!super.equals(obj)) {
/* 240 */       return false;
/*     */     }
/* 242 */     MonthDateFormat that = (MonthDateFormat)obj;
/* 243 */     if (!Arrays.equals((Object[])this.months, (Object[])that.months)) {
/* 244 */       return false;
/*     */     }
/* 246 */     if (!Arrays.equals(this.showYear, that.showYear)) {
/* 247 */       return false;
/*     */     }
/* 249 */     if (!this.yearFormatter.equals(that.yearFormatter)) {
/* 250 */       return false;
/*     */     }
/* 252 */     return true;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/axis/MonthDateFormat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */