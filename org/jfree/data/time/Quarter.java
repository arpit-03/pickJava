/*     */ package org.jfree.data.time;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.Locale;
/*     */ import java.util.TimeZone;
/*     */ import org.jfree.date.SerialDate;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Quarter
/*     */   extends RegularTimePeriod
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 3810061714380888671L;
/*     */   public static final int FIRST_QUARTER = 1;
/*     */   public static final int LAST_QUARTER = 4;
/*  90 */   public static final int[] FIRST_MONTH_IN_QUARTER = new int[] { 0, 1, 4, 7, 10 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  96 */   public static final int[] LAST_MONTH_IN_QUARTER = new int[] { 0, 3, 6, 9, 12 };
/*     */ 
/*     */ 
/*     */   
/*     */   private short year;
/*     */ 
/*     */ 
/*     */   
/*     */   private byte quarter;
/*     */ 
/*     */ 
/*     */   
/*     */   private long firstMillisecond;
/*     */ 
/*     */ 
/*     */   
/*     */   private long lastMillisecond;
/*     */ 
/*     */ 
/*     */   
/*     */   public Quarter() {
/* 117 */     this(new Date());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Quarter(int quarter, int year) {
/* 127 */     if (quarter < 1 || quarter > 4) {
/* 128 */       throw new IllegalArgumentException("Quarter outside valid range.");
/*     */     }
/* 130 */     this.year = (short)year;
/* 131 */     this.quarter = (byte)quarter;
/* 132 */     peg(Calendar.getInstance());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Quarter(int quarter, Year year) {
/* 142 */     if (quarter < 1 || quarter > 4) {
/* 143 */       throw new IllegalArgumentException("Quarter outside valid range.");
/*     */     }
/* 145 */     this.year = (short)year.getYear();
/* 146 */     this.quarter = (byte)quarter;
/* 147 */     peg(Calendar.getInstance());
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
/*     */   public Quarter(Date time) {
/* 159 */     this(time, TimeZone.getDefault());
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
/*     */   public Quarter(Date time, TimeZone zone) {
/* 172 */     this(time, zone, Locale.getDefault());
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
/*     */   public Quarter(Date time, TimeZone zone, Locale locale) {
/* 186 */     Calendar calendar = Calendar.getInstance(zone, locale);
/* 187 */     calendar.setTime(time);
/* 188 */     int month = calendar.get(2) + 1;
/* 189 */     this.quarter = (byte)SerialDate.monthCodeToQuarter(month);
/* 190 */     this.year = (short)calendar.get(1);
/* 191 */     peg(calendar);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getQuarter() {
/* 200 */     return this.quarter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Year getYear() {
/* 209 */     return new Year(this.year);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getYearValue() {
/* 220 */     return this.year;
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
/*     */   public long getFirstMillisecond() {
/* 235 */     return this.firstMillisecond;
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
/*     */   public long getLastMillisecond() {
/* 250 */     return this.lastMillisecond;
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
/*     */   public void peg(Calendar calendar) {
/* 263 */     this.firstMillisecond = getFirstMillisecond(calendar);
/* 264 */     this.lastMillisecond = getLastMillisecond(calendar);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RegularTimePeriod previous() {
/*     */     Quarter result;
/* 276 */     if (this.quarter > 1) {
/* 277 */       result = new Quarter(this.quarter - 1, this.year);
/*     */     
/*     */     }
/* 280 */     else if (this.year > 1900) {
/* 281 */       result = new Quarter(4, this.year - 1);
/*     */     } else {
/*     */       
/* 284 */       result = null;
/*     */     } 
/*     */     
/* 287 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RegularTimePeriod next() {
/*     */     Quarter result;
/* 298 */     if (this.quarter < 4) {
/* 299 */       result = new Quarter(this.quarter + 1, this.year);
/*     */     
/*     */     }
/* 302 */     else if (this.year < 9999) {
/* 303 */       result = new Quarter(1, this.year + 1);
/*     */     } else {
/*     */       
/* 306 */       result = null;
/*     */     } 
/*     */     
/* 309 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getSerialIndex() {
/* 319 */     return this.year * 4L + this.quarter;
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
/*     */   public boolean equals(Object obj) {
/* 336 */     if (obj != null) {
/* 337 */       if (obj instanceof Quarter) {
/* 338 */         Quarter target = (Quarter)obj;
/* 339 */         return (this.quarter == target.getQuarter() && this.year == target
/* 340 */           .getYearValue());
/*     */       } 
/*     */       
/* 343 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 347 */     return false;
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
/*     */   public int hashCode() {
/* 363 */     int result = 17;
/* 364 */     result = 37 * result + this.quarter;
/* 365 */     result = 37 * result + this.year;
/* 366 */     return result;
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
/*     */   public int compareTo(Object o1) {
/*     */     int result;
/* 386 */     if (o1 instanceof Quarter) {
/* 387 */       Quarter q = (Quarter)o1;
/* 388 */       result = this.year - q.getYearValue();
/* 389 */       if (result == 0) {
/* 390 */         result = this.quarter - q.getQuarter();
/*     */ 
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 396 */     else if (o1 instanceof RegularTimePeriod) {
/*     */       
/* 398 */       result = 0;
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 405 */       result = 1;
/*     */     } 
/*     */     
/* 408 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 419 */     return "Q" + this.quarter + "/" + this.year;
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
/*     */   public long getFirstMillisecond(Calendar calendar) {
/* 435 */     int month = FIRST_MONTH_IN_QUARTER[this.quarter];
/* 436 */     calendar.set(this.year, month - 1, 1, 0, 0, 0);
/* 437 */     calendar.set(14, 0);
/* 438 */     return calendar.getTimeInMillis();
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
/*     */   public long getLastMillisecond(Calendar calendar) {
/* 454 */     int month = LAST_MONTH_IN_QUARTER[this.quarter];
/* 455 */     int eom = SerialDate.lastDayOfMonth(month, this.year);
/* 456 */     calendar.set(this.year, month - 1, eom, 23, 59, 59);
/* 457 */     calendar.set(14, 999);
/* 458 */     return calendar.getTimeInMillis();
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
/*     */   public static Quarter parseQuarter(String s) {
/* 474 */     int i = s.indexOf("Q");
/* 475 */     if (i == -1) {
/* 476 */       throw new TimePeriodFormatException("Missing Q.");
/*     */     }
/*     */     
/* 479 */     if (i == s.length() - 1) {
/* 480 */       throw new TimePeriodFormatException("Q found at end of string.");
/*     */     }
/*     */     
/* 483 */     String qstr = s.substring(i + 1, i + 2);
/* 484 */     int quarter = Integer.parseInt(qstr);
/* 485 */     String remaining = s.substring(0, i) + s.substring(i + 2, s.length());
/*     */ 
/*     */     
/* 488 */     remaining = remaining.replace('/', ' ');
/* 489 */     remaining = remaining.replace(',', ' ');
/* 490 */     remaining = remaining.replace('-', ' ');
/*     */ 
/*     */     
/* 493 */     Year year = Year.parseYear(remaining.trim());
/* 494 */     Quarter result = new Quarter(quarter, year);
/* 495 */     return result;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/time/Quarter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */