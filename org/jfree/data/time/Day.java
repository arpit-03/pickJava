/*     */ package org.jfree.data.time;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.text.DateFormat;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.Locale;
/*     */ import java.util.TimeZone;
/*     */ import org.jfree.chart.util.ParamChecks;
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
/*     */ public class Day
/*     */   extends RegularTimePeriod
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -7082667380758962755L;
/*  91 */   protected static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  96 */   protected static final DateFormat DATE_FORMAT_SHORT = DateFormat.getDateInstance(3);
/*     */ 
/*     */ 
/*     */   
/* 100 */   protected static final DateFormat DATE_FORMAT_MEDIUM = DateFormat.getDateInstance(2);
/*     */ 
/*     */ 
/*     */   
/* 104 */   protected static final DateFormat DATE_FORMAT_LONG = DateFormat.getDateInstance(1);
/*     */ 
/*     */ 
/*     */   
/*     */   private SerialDate serialDate;
/*     */ 
/*     */ 
/*     */   
/*     */   private long firstMillisecond;
/*     */ 
/*     */   
/*     */   private long lastMillisecond;
/*     */ 
/*     */ 
/*     */   
/*     */   public Day() {
/* 120 */     this(new Date());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Day(int day, int month, int year) {
/* 131 */     this.serialDate = SerialDate.createInstance(day, month, year);
/* 132 */     peg(Calendar.getInstance());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Day(SerialDate serialDate) {
/* 141 */     ParamChecks.nullNotPermitted(serialDate, "serialDate");
/* 142 */     this.serialDate = serialDate;
/* 143 */     peg(Calendar.getInstance());
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
/*     */   public Day(Date time) {
/* 156 */     this(time, TimeZone.getDefault(), Locale.getDefault());
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
/*     */   public Day(Date time, TimeZone zone) {
/* 169 */     this(time, zone, Locale.getDefault());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Day(Date time, TimeZone zone, Locale locale) {
/* 180 */     ParamChecks.nullNotPermitted(time, "time");
/* 181 */     ParamChecks.nullNotPermitted(zone, "zone");
/* 182 */     ParamChecks.nullNotPermitted(locale, "locale");
/* 183 */     Calendar calendar = Calendar.getInstance(zone, locale);
/* 184 */     calendar.setTime(time);
/* 185 */     int d = calendar.get(5);
/* 186 */     int m = calendar.get(2) + 1;
/* 187 */     int y = calendar.get(1);
/* 188 */     this.serialDate = SerialDate.createInstance(d, m, y);
/* 189 */     peg(calendar);
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
/*     */   public SerialDate getSerialDate() {
/* 202 */     return this.serialDate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getYear() {
/* 211 */     return this.serialDate.getYYYY();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMonth() {
/* 220 */     return this.serialDate.getMonth();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDayOfMonth() {
/* 229 */     return this.serialDate.getDayOfMonth();
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
/* 244 */     return this.firstMillisecond;
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
/* 259 */     return this.lastMillisecond;
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
/* 272 */     this.firstMillisecond = getFirstMillisecond(calendar);
/* 273 */     this.lastMillisecond = getLastMillisecond(calendar);
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
/* 284 */     int serial = this.serialDate.toSerial();
/* 285 */     if (serial > 2) {
/* 286 */       SerialDate yesterday = SerialDate.createInstance(serial - 1);
/* 287 */       return new Day(yesterday);
/*     */     } 
/*     */     
/* 290 */     Day result = null;
/*     */     
/* 292 */     return result;
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
/* 305 */     int serial = this.serialDate.toSerial();
/* 306 */     if (serial < 2958465) {
/* 307 */       SerialDate tomorrow = SerialDate.createInstance(serial + 1);
/* 308 */       return new Day(tomorrow);
/*     */     } 
/*     */     
/* 311 */     Day result = null;
/*     */     
/* 313 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getSerialIndex() {
/* 323 */     return this.serialDate.toSerial();
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
/* 339 */     int year = this.serialDate.getYYYY();
/* 340 */     int month = this.serialDate.getMonth();
/* 341 */     int day = this.serialDate.getDayOfMonth();
/* 342 */     calendar.clear();
/* 343 */     calendar.set(year, month - 1, day, 0, 0, 0);
/* 344 */     calendar.set(14, 0);
/* 345 */     return calendar.getTimeInMillis();
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
/* 361 */     int year = this.serialDate.getYYYY();
/* 362 */     int month = this.serialDate.getMonth();
/* 363 */     int day = this.serialDate.getDayOfMonth();
/* 364 */     calendar.clear();
/* 365 */     calendar.set(year, month - 1, day, 23, 59, 59);
/* 366 */     calendar.set(14, 999);
/* 367 */     return calendar.getTimeInMillis();
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
/* 382 */     if (obj == this) {
/* 383 */       return true;
/*     */     }
/* 385 */     if (!(obj instanceof Day)) {
/* 386 */       return false;
/*     */     }
/* 388 */     Day that = (Day)obj;
/* 389 */     if (!this.serialDate.equals(that.getSerialDate())) {
/* 390 */       return false;
/*     */     }
/* 392 */     return true;
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
/* 406 */     return this.serialDate.hashCode();
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
/* 425 */     if (o1 instanceof Day) {
/* 426 */       Day d = (Day)o1;
/* 427 */       result = -d.getSerialDate().compare(this.serialDate);
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 432 */     else if (o1 instanceof RegularTimePeriod) {
/*     */       
/* 434 */       result = 0;
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 441 */       result = 1;
/*     */     } 
/*     */     
/* 444 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 454 */     return this.serialDate.toString();
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
/*     */   public static Day parseDay(String s) {
/*     */     try {
/* 470 */       return new Day(DATE_FORMAT.parse(s));
/*     */     }
/* 472 */     catch (ParseException e1) {
/*     */       try {
/* 474 */         return new Day(DATE_FORMAT_SHORT.parse(s));
/*     */       }
/* 476 */       catch (ParseException e2) {
/*     */ 
/*     */ 
/*     */         
/* 480 */         return null;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/time/Day.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */