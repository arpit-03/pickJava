/*     */ package org.jfree.data.time;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Hour
/*     */   extends RegularTimePeriod
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -835471579831937652L;
/*     */   public static final int FIRST_HOUR_IN_DAY = 0;
/*     */   public static final int LAST_HOUR_IN_DAY = 23;
/*     */   private Day day;
/*     */   private byte hour;
/*     */   private long firstMillisecond;
/*     */   private long lastMillisecond;
/*     */   
/*     */   public Hour() {
/* 107 */     this(new Date());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Hour(int hour, Day day) {
/* 117 */     ParamChecks.nullNotPermitted(day, "day");
/* 118 */     this.hour = (byte)hour;
/* 119 */     this.day = day;
/* 120 */     peg(Calendar.getInstance());
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
/*     */   public Hour(int hour, int day, int month, int year) {
/* 132 */     this(hour, new Day(day, month, year));
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
/*     */   public Hour(Date time) {
/* 145 */     this(time, TimeZone.getDefault(), Locale.getDefault());
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
/*     */   public Hour(Date time, TimeZone zone) {
/* 159 */     this(time, zone, Locale.getDefault());
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
/*     */   public Hour(Date time, TimeZone zone, Locale locale) {
/* 173 */     ParamChecks.nullNotPermitted(time, "time");
/* 174 */     ParamChecks.nullNotPermitted(zone, "zone");
/* 175 */     ParamChecks.nullNotPermitted(locale, "locale");
/* 176 */     Calendar calendar = Calendar.getInstance(zone, locale);
/* 177 */     calendar.setTime(time);
/* 178 */     this.hour = (byte)calendar.get(11);
/* 179 */     this.day = new Day(time, zone, locale);
/* 180 */     peg(calendar);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getHour() {
/* 189 */     return this.hour;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Day getDay() {
/* 198 */     return this.day;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getYear() {
/* 207 */     return this.day.getYear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMonth() {
/* 216 */     return this.day.getMonth();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDayOfMonth() {
/* 225 */     return this.day.getDayOfMonth();
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
/* 240 */     return this.firstMillisecond;
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
/* 255 */     return this.lastMillisecond;
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
/* 268 */     this.firstMillisecond = getFirstMillisecond(calendar);
/* 269 */     this.lastMillisecond = getLastMillisecond(calendar);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RegularTimePeriod previous() {
/*     */     Hour result;
/* 280 */     if (this.hour != 0) {
/* 281 */       result = new Hour(this.hour - 1, this.day);
/*     */     } else {
/*     */       
/* 284 */       Day prevDay = (Day)this.day.previous();
/* 285 */       if (prevDay != null) {
/* 286 */         result = new Hour(23, prevDay);
/*     */       } else {
/*     */         
/* 289 */         result = null;
/*     */       } 
/*     */     } 
/* 292 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RegularTimePeriod next() {
/*     */     Hour result;
/* 303 */     if (this.hour != 23) {
/* 304 */       result = new Hour(this.hour + 1, this.day);
/*     */     } else {
/*     */       
/* 307 */       Day nextDay = (Day)this.day.next();
/* 308 */       if (nextDay != null) {
/* 309 */         result = new Hour(0, nextDay);
/*     */       } else {
/*     */         
/* 312 */         result = null;
/*     */       } 
/*     */     } 
/* 315 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getSerialIndex() {
/* 325 */     return this.day.getSerialIndex() * 24L + this.hour;
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
/*     */   public long getFirstMillisecond(Calendar calendar) {
/* 340 */     int year = this.day.getYear();
/* 341 */     int month = this.day.getMonth() - 1;
/* 342 */     int dom = this.day.getDayOfMonth();
/* 343 */     calendar.set(year, month, dom, this.hour, 0, 0);
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
/*     */   public long getLastMillisecond(Calendar calendar) {
/* 360 */     int year = this.day.getYear();
/* 361 */     int month = this.day.getMonth() - 1;
/* 362 */     int dom = this.day.getDayOfMonth();
/* 363 */     calendar.set(year, month, dom, this.hour, 59, 59);
/* 364 */     calendar.set(14, 999);
/* 365 */     return calendar.getTimeInMillis();
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
/*     */   public boolean equals(Object obj) {
/* 381 */     if (obj == this) {
/* 382 */       return true;
/*     */     }
/* 384 */     if (!(obj instanceof Hour)) {
/* 385 */       return false;
/*     */     }
/* 387 */     Hour that = (Hour)obj;
/* 388 */     if (this.hour != that.hour) {
/* 389 */       return false;
/*     */     }
/* 391 */     if (!this.day.equals(that.day)) {
/* 392 */       return false;
/*     */     }
/* 394 */     return true;
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
/* 405 */     return "[" + this.hour + "," + getDayOfMonth() + "/" + getMonth() + "/" + 
/* 406 */       getYear() + "]";
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
/* 420 */     int result = 17;
/* 421 */     result = 37 * result + this.hour;
/* 422 */     result = 37 * result + this.day.hashCode();
/* 423 */     return result;
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
/* 442 */     if (o1 instanceof Hour) {
/* 443 */       Hour h = (Hour)o1;
/* 444 */       result = getDay().compareTo(h.getDay());
/* 445 */       if (result == 0) {
/* 446 */         result = this.hour - h.getHour();
/*     */ 
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 452 */     else if (o1 instanceof RegularTimePeriod) {
/*     */       
/* 454 */       result = 0;
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 461 */       result = 1;
/*     */     } 
/*     */     
/* 464 */     return result;
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
/*     */   public static Hour parseHour(String s) {
/* 478 */     Hour result = null;
/* 479 */     s = s.trim();
/*     */     
/* 481 */     String daystr = s.substring(0, Math.min(10, s.length()));
/* 482 */     Day day = Day.parseDay(daystr);
/* 483 */     if (day != null) {
/* 484 */       String hourstr = s.substring(
/* 485 */           Math.min(daystr.length() + 1, s.length()), s.length());
/*     */       
/* 487 */       hourstr = hourstr.trim();
/* 488 */       int hour = Integer.parseInt(hourstr);
/*     */       
/* 490 */       if (hour >= 0 && hour <= 23) {
/* 491 */         result = new Hour(hour, day);
/*     */       }
/*     */     } 
/*     */     
/* 495 */     return result;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/time/Hour.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */