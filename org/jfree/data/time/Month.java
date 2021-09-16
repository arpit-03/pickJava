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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Month
/*     */   extends RegularTimePeriod
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -5090216912548722570L;
/*     */   private int month;
/*     */   private int year;
/*     */   private long firstMillisecond;
/*     */   private long lastMillisecond;
/*     */   
/*     */   public Month() {
/* 103 */     this(new Date());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Month(int month, int year) {
/* 113 */     if (month < 1 || month > 12) {
/* 114 */       throw new IllegalArgumentException("Month outside valid range.");
/*     */     }
/* 116 */     this.month = month;
/* 117 */     this.year = year;
/* 118 */     peg(Calendar.getInstance());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Month(int month, Year year) {
/* 128 */     if (month < 1 || month > 12) {
/* 129 */       throw new IllegalArgumentException("Month outside valid range.");
/*     */     }
/* 131 */     this.month = month;
/* 132 */     this.year = year.getYear();
/* 133 */     peg(Calendar.getInstance());
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
/*     */   public Month(Date time) {
/* 145 */     this(time, TimeZone.getDefault());
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
/*     */   public Month(Date time, TimeZone zone) {
/* 160 */     this(time, zone, Locale.getDefault());
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
/*     */   public Month(Date time, TimeZone zone, Locale locale) {
/* 174 */     Calendar calendar = Calendar.getInstance(zone, locale);
/* 175 */     calendar.setTime(time);
/* 176 */     this.month = calendar.get(2) + 1;
/* 177 */     this.year = calendar.get(1);
/* 178 */     peg(calendar);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Year getYear() {
/* 187 */     return new Year(this.year);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getYearValue() {
/* 196 */     return this.year;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMonth() {
/* 205 */     return this.month;
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
/* 220 */     return this.firstMillisecond;
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
/* 235 */     return this.lastMillisecond;
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
/* 248 */     this.firstMillisecond = getFirstMillisecond(calendar);
/* 249 */     this.lastMillisecond = getLastMillisecond(calendar);
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
/*     */   public RegularTimePeriod previous() {
/*     */     Month result;
/* 263 */     if (this.month != 1) {
/* 264 */       result = new Month(this.month - 1, this.year);
/*     */     
/*     */     }
/* 267 */     else if (this.year > 1900) {
/* 268 */       result = new Month(12, this.year - 1);
/*     */     } else {
/*     */       
/* 271 */       result = null;
/*     */     } 
/*     */     
/* 274 */     return result;
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
/*     */   public RegularTimePeriod next() {
/*     */     Month result;
/* 288 */     if (this.month != 12) {
/* 289 */       result = new Month(this.month + 1, this.year);
/*     */     
/*     */     }
/* 292 */     else if (this.year < 9999) {
/* 293 */       result = new Month(1, this.year + 1);
/*     */     } else {
/*     */       
/* 296 */       result = null;
/*     */     } 
/*     */     
/* 299 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getSerialIndex() {
/* 309 */     return this.year * 12L + this.month;
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
/*     */   public String toString() {
/* 321 */     return SerialDate.monthCodeToString(this.month) + " " + this.year;
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
/*     */   public boolean equals(Object obj) {
/* 336 */     if (obj == this) {
/* 337 */       return true;
/*     */     }
/* 339 */     if (!(obj instanceof Month)) {
/* 340 */       return false;
/*     */     }
/* 342 */     Month that = (Month)obj;
/* 343 */     if (this.month != that.month) {
/* 344 */       return false;
/*     */     }
/* 346 */     if (this.year != that.year) {
/* 347 */       return false;
/*     */     }
/* 349 */     return true;
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
/*     */   public int hashCode() {
/* 363 */     int result = 17;
/* 364 */     result = 37 * result + this.month;
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
/*     */   public int compareTo(Object o1) {
/*     */     int result;
/* 385 */     if (o1 instanceof Month) {
/* 386 */       Month m = (Month)o1;
/* 387 */       result = this.year - m.getYearValue();
/* 388 */       if (result == 0) {
/* 389 */         result = this.month - m.getMonth();
/*     */ 
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 395 */     else if (o1 instanceof RegularTimePeriod) {
/*     */       
/* 397 */       result = 0;
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 404 */       result = 1;
/*     */     } 
/*     */     
/* 407 */     return result;
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
/*     */   public long getFirstMillisecond(Calendar calendar) {
/* 424 */     calendar.set(this.year, this.month - 1, 1, 0, 0, 0);
/* 425 */     calendar.set(14, 0);
/* 426 */     return calendar.getTimeInMillis();
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
/* 442 */     int eom = SerialDate.lastDayOfMonth(this.month, this.year);
/* 443 */     calendar.set(this.year, this.month - 1, eom, 23, 59, 59);
/* 444 */     calendar.set(14, 999);
/* 445 */     return calendar.getTimeInMillis();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Month parseMonth(String s) {
/*     */     String s1, s2;
/*     */     boolean yearIsFirst;
/*     */     Year year;
/*     */     int month;
/* 459 */     Month result = null;
/* 460 */     if (s == null) {
/* 461 */       return result;
/*     */     }
/*     */     
/* 464 */     s = s.trim();
/* 465 */     int i = findSeparator(s);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 470 */     if (i == -1) {
/* 471 */       yearIsFirst = true;
/* 472 */       s1 = s.substring(0, 5);
/* 473 */       s2 = s.substring(5);
/*     */     } else {
/*     */       
/* 476 */       s1 = s.substring(0, i).trim();
/* 477 */       s2 = s.substring(i + 1, s.length()).trim();
/*     */       
/* 479 */       Year y1 = evaluateAsYear(s1);
/* 480 */       if (y1 == null) {
/* 481 */         yearIsFirst = false;
/*     */       } else {
/*     */         
/* 484 */         Year y2 = evaluateAsYear(s2);
/* 485 */         if (y2 == null) {
/* 486 */           yearIsFirst = true;
/*     */         } else {
/*     */           
/* 489 */           yearIsFirst = (s1.length() > s2.length());
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 495 */     if (yearIsFirst) {
/* 496 */       year = evaluateAsYear(s1);
/* 497 */       month = SerialDate.stringToMonthCode(s2);
/*     */     } else {
/*     */       
/* 500 */       year = evaluateAsYear(s2);
/* 501 */       month = SerialDate.stringToMonthCode(s1);
/*     */     } 
/* 503 */     if (month == -1) {
/* 504 */       throw new TimePeriodFormatException("Can't evaluate the month.");
/*     */     }
/* 506 */     if (year == null) {
/* 507 */       throw new TimePeriodFormatException("Can't evaluate the year.");
/*     */     }
/* 509 */     result = new Month(month, year);
/* 510 */     return result;
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
/*     */   private static int findSeparator(String s) {
/* 523 */     int result = s.indexOf('-');
/* 524 */     if (result == -1) {
/* 525 */       result = s.indexOf(',');
/*     */     }
/* 527 */     if (result == -1) {
/* 528 */       result = s.indexOf(' ');
/*     */     }
/* 530 */     if (result == -1) {
/* 531 */       result = s.indexOf('.');
/*     */     }
/* 533 */     return result;
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
/*     */   private static Year evaluateAsYear(String s) {
/* 546 */     Year result = null;
/*     */     try {
/* 548 */       result = Year.parseYear(s);
/*     */     }
/* 550 */     catch (TimePeriodFormatException e) {}
/*     */ 
/*     */     
/* 553 */     return result;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/time/Month.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */