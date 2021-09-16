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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Minute
/*     */   extends RegularTimePeriod
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 2144572840034842871L;
/*     */   public static final int FIRST_MINUTE_IN_HOUR = 0;
/*     */   public static final int LAST_MINUTE_IN_HOUR = 59;
/*     */   private Day day;
/*     */   private byte hour;
/*     */   private byte minute;
/*     */   private long firstMillisecond;
/*     */   private long lastMillisecond;
/*     */   
/*     */   public Minute() {
/* 111 */     this(new Date());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Minute(int minute, Hour hour) {
/* 121 */     ParamChecks.nullNotPermitted(hour, "hour");
/* 122 */     this.minute = (byte)minute;
/* 123 */     this.hour = (byte)hour.getHour();
/* 124 */     this.day = hour.getDay();
/* 125 */     peg(Calendar.getInstance());
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
/*     */   public Minute(Date time) {
/* 138 */     this(time, TimeZone.getDefault(), Locale.getDefault());
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
/*     */   public Minute(Date time, TimeZone zone) {
/* 151 */     this(time, zone, Locale.getDefault());
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
/*     */   public Minute(Date time, TimeZone zone, Locale locale) {
/* 164 */     ParamChecks.nullNotPermitted(time, "time");
/* 165 */     ParamChecks.nullNotPermitted(zone, "zone");
/* 166 */     ParamChecks.nullNotPermitted(locale, "locale");
/* 167 */     Calendar calendar = Calendar.getInstance(zone, locale);
/* 168 */     calendar.setTime(time);
/* 169 */     int min = calendar.get(12);
/* 170 */     this.minute = (byte)min;
/* 171 */     this.hour = (byte)calendar.get(11);
/* 172 */     this.day = new Day(time, zone, locale);
/* 173 */     peg(calendar);
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
/*     */   public Minute(int minute, int hour, int day, int month, int year) {
/* 186 */     this(minute, new Hour(hour, new Day(day, month, year)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Day getDay() {
/* 197 */     return this.day;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Hour getHour() {
/* 206 */     return new Hour(this.hour, this.day);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getHourValue() {
/* 217 */     return this.hour;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMinute() {
/* 226 */     return this.minute;
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
/* 241 */     return this.firstMillisecond;
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
/* 256 */     return this.lastMillisecond;
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
/* 269 */     this.firstMillisecond = getFirstMillisecond(calendar);
/* 270 */     this.lastMillisecond = getLastMillisecond(calendar);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RegularTimePeriod previous() {
/*     */     Minute result;
/* 281 */     if (this.minute != 0) {
/* 282 */       result = new Minute(this.minute - 1, getHour());
/*     */     } else {
/*     */       
/* 285 */       Hour h = (Hour)getHour().previous();
/* 286 */       if (h != null) {
/* 287 */         result = new Minute(59, h);
/*     */       } else {
/*     */         
/* 290 */         result = null;
/*     */       } 
/*     */     } 
/* 293 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RegularTimePeriod next() {
/*     */     Minute result;
/* 304 */     if (this.minute != 59) {
/* 305 */       result = new Minute(this.minute + 1, getHour());
/*     */     } else {
/*     */       
/* 308 */       Hour nextHour = (Hour)getHour().next();
/* 309 */       if (nextHour != null) {
/* 310 */         result = new Minute(0, nextHour);
/*     */       } else {
/*     */         
/* 313 */         result = null;
/*     */       } 
/*     */     } 
/* 316 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getSerialIndex() {
/* 326 */     long hourIndex = this.day.getSerialIndex() * 24L + this.hour;
/* 327 */     return hourIndex * 60L + this.minute;
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
/* 343 */     int year = this.day.getYear();
/* 344 */     int month = this.day.getMonth() - 1;
/* 345 */     int d = this.day.getDayOfMonth();
/*     */     
/* 347 */     calendar.clear();
/* 348 */     calendar.set(year, month, d, this.hour, this.minute, 0);
/* 349 */     calendar.set(14, 0);
/*     */     
/* 351 */     return calendar.getTimeInMillis();
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
/* 367 */     int year = this.day.getYear();
/* 368 */     int month = this.day.getMonth() - 1;
/* 369 */     int d = this.day.getDayOfMonth();
/*     */     
/* 371 */     calendar.clear();
/* 372 */     calendar.set(year, month, d, this.hour, this.minute, 59);
/* 373 */     calendar.set(14, 999);
/*     */     
/* 375 */     return calendar.getTimeInMillis();
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
/* 391 */     if (obj == this) {
/* 392 */       return true;
/*     */     }
/* 394 */     if (!(obj instanceof Minute)) {
/* 395 */       return false;
/*     */     }
/* 397 */     Minute that = (Minute)obj;
/* 398 */     if (this.minute != that.minute) {
/* 399 */       return false;
/*     */     }
/* 401 */     if (this.hour != that.hour) {
/* 402 */       return false;
/*     */     }
/* 404 */     return true;
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
/* 418 */     int result = 17;
/* 419 */     result = 37 * result + this.minute;
/* 420 */     result = 37 * result + this.hour;
/* 421 */     result = 37 * result + this.day.hashCode();
/* 422 */     return result;
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
/* 441 */     if (o1 instanceof Minute) {
/* 442 */       Minute m = (Minute)o1;
/* 443 */       result = getHour().compareTo(m.getHour());
/* 444 */       if (result == 0) {
/* 445 */         result = this.minute - m.getMinute();
/*     */ 
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 451 */     else if (o1 instanceof RegularTimePeriod) {
/*     */       
/* 453 */       result = 0;
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 460 */       result = 1;
/*     */     } 
/*     */     
/* 463 */     return result;
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
/*     */   public static Minute parseMinute(String s) {
/* 477 */     Minute result = null;
/* 478 */     s = s.trim();
/*     */     
/* 480 */     String daystr = s.substring(0, Math.min(10, s.length()));
/* 481 */     Day day = Day.parseDay(daystr);
/* 482 */     if (day != null) {
/* 483 */       String hmstr = s.substring(
/* 484 */           Math.min(daystr.length() + 1, s.length()), s.length());
/*     */       
/* 486 */       hmstr = hmstr.trim();
/*     */       
/* 488 */       String hourstr = hmstr.substring(0, Math.min(2, hmstr.length()));
/* 489 */       int hour = Integer.parseInt(hourstr);
/*     */       
/* 491 */       if (hour >= 0 && hour <= 23) {
/* 492 */         String minstr = hmstr.substring(
/* 493 */             Math.min(hourstr.length() + 1, hmstr.length()), hmstr
/* 494 */             .length());
/*     */         
/* 496 */         int minute = Integer.parseInt(minstr);
/* 497 */         if (minute >= 0 && minute <= 59) {
/* 498 */           result = new Minute(minute, new Hour(hour, day));
/*     */         }
/*     */       } 
/*     */     } 
/* 502 */     return result;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/time/Minute.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */