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
/*     */ public class Second
/*     */   extends RegularTimePeriod
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -6536564190712383466L;
/*     */   public static final int FIRST_SECOND_IN_MINUTE = 0;
/*     */   public static final int LAST_SECOND_IN_MINUTE = 59;
/*     */   private Day day;
/*     */   private byte hour;
/*     */   private byte minute;
/*     */   private byte second;
/*     */   private long firstMillisecond;
/*     */   
/*     */   public Second() {
/* 109 */     this(new Date());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Second(int second, Minute minute) {
/* 119 */     ParamChecks.nullNotPermitted(minute, "minute");
/* 120 */     this.day = minute.getDay();
/* 121 */     this.hour = (byte)minute.getHourValue();
/* 122 */     this.minute = (byte)minute.getMinute();
/* 123 */     this.second = (byte)second;
/* 124 */     peg(Calendar.getInstance());
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
/*     */   public Second(int second, int minute, int hour, int day, int month, int year) {
/* 139 */     this(second, new Minute(minute, hour, day, month, year));
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
/*     */   public Second(Date time) {
/* 151 */     this(time, TimeZone.getDefault(), Locale.getDefault());
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
/*     */   public Second(Date time, TimeZone zone) {
/* 164 */     this(time, zone, Locale.getDefault());
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
/*     */   public Second(Date time, TimeZone zone, Locale locale) {
/* 177 */     Calendar calendar = Calendar.getInstance(zone, locale);
/* 178 */     calendar.setTime(time);
/* 179 */     this.second = (byte)calendar.get(13);
/* 180 */     this.minute = (byte)calendar.get(12);
/* 181 */     this.hour = (byte)calendar.get(11);
/* 182 */     this.day = new Day(time, zone, locale);
/* 183 */     peg(calendar);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSecond() {
/* 192 */     return this.second;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Minute getMinute() {
/* 201 */     return new Minute(this.minute, new Hour(this.hour, this.day));
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
/* 216 */     return this.firstMillisecond;
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
/* 231 */     return this.firstMillisecond + 999L;
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
/* 244 */     this.firstMillisecond = getFirstMillisecond(calendar);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RegularTimePeriod previous() {
/* 254 */     Second result = null;
/* 255 */     if (this.second != 0) {
/* 256 */       result = new Second(this.second - 1, getMinute());
/*     */     } else {
/*     */       
/* 259 */       Minute previous = (Minute)getMinute().previous();
/* 260 */       if (previous != null) {
/* 261 */         result = new Second(59, previous);
/*     */       }
/*     */     } 
/* 264 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RegularTimePeriod next() {
/* 274 */     Second result = null;
/* 275 */     if (this.second != 59) {
/* 276 */       result = new Second(this.second + 1, getMinute());
/*     */     } else {
/*     */       
/* 279 */       Minute next = (Minute)getMinute().next();
/* 280 */       if (next != null) {
/* 281 */         result = new Second(0, next);
/*     */       }
/*     */     } 
/* 284 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getSerialIndex() {
/* 294 */     long hourIndex = this.day.getSerialIndex() * 24L + this.hour;
/* 295 */     long minuteIndex = hourIndex * 60L + this.minute;
/* 296 */     return minuteIndex * 60L + this.second;
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
/* 311 */     int year = this.day.getYear();
/* 312 */     int month = this.day.getMonth() - 1;
/* 313 */     int d = this.day.getDayOfMonth();
/* 314 */     calendar.clear();
/* 315 */     calendar.set(year, month, d, this.hour, this.minute, this.second);
/* 316 */     calendar.set(14, 0);
/* 317 */     return calendar.getTimeInMillis();
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
/* 332 */     return getFirstMillisecond(calendar) + 999L;
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
/* 348 */     if (obj == this) {
/* 349 */       return true;
/*     */     }
/* 351 */     if (!(obj instanceof Second)) {
/* 352 */       return false;
/*     */     }
/* 354 */     Second that = (Second)obj;
/* 355 */     if (this.second != that.second) {
/* 356 */       return false;
/*     */     }
/* 358 */     if (this.minute != that.minute) {
/* 359 */       return false;
/*     */     }
/* 361 */     if (this.hour != that.hour) {
/* 362 */       return false;
/*     */     }
/* 364 */     if (!this.day.equals(that.day)) {
/* 365 */       return false;
/*     */     }
/* 367 */     return true;
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
/* 381 */     int result = 17;
/* 382 */     result = 37 * result + this.second;
/* 383 */     result = 37 * result + this.minute;
/* 384 */     result = 37 * result + this.hour;
/* 385 */     result = 37 * result + this.day.hashCode();
/* 386 */     return result;
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
/*     */   public int compareTo(Object o1) {
/*     */     int result;
/* 404 */     if (o1 instanceof Second) {
/* 405 */       Second s = (Second)o1;
/* 406 */       if (this.firstMillisecond < s.firstMillisecond) {
/* 407 */         return -1;
/*     */       }
/* 409 */       if (this.firstMillisecond > s.firstMillisecond) {
/* 410 */         return 1;
/*     */       }
/*     */       
/* 413 */       return 0;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 419 */     if (o1 instanceof RegularTimePeriod) {
/*     */       
/* 421 */       result = 0;
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 428 */       result = 1;
/*     */     } 
/*     */     
/* 431 */     return result;
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
/*     */   public static Second parseSecond(String s) {
/* 444 */     Second result = null;
/* 445 */     s = s.trim();
/* 446 */     String daystr = s.substring(0, Math.min(10, s.length()));
/* 447 */     Day day = Day.parseDay(daystr);
/* 448 */     if (day != null) {
/* 449 */       String hmsstr = s.substring(Math.min(daystr.length() + 1, s
/* 450 */             .length()), s.length());
/* 451 */       hmsstr = hmsstr.trim();
/*     */       
/* 453 */       int l = hmsstr.length();
/* 454 */       String hourstr = hmsstr.substring(0, Math.min(2, l));
/* 455 */       String minstr = hmsstr.substring(Math.min(3, l), Math.min(5, l));
/* 456 */       String secstr = hmsstr.substring(Math.min(6, l), Math.min(8, l));
/* 457 */       int hour = Integer.parseInt(hourstr);
/*     */       
/* 459 */       if (hour >= 0 && hour <= 23) {
/*     */         
/* 461 */         int minute = Integer.parseInt(minstr);
/* 462 */         if (minute >= 0 && minute <= 59) {
/*     */           
/* 464 */           Minute m = new Minute(minute, new Hour(hour, day));
/* 465 */           int second = Integer.parseInt(secstr);
/* 466 */           if (second >= 0 && second <= 59) {
/* 467 */             result = new Second(second, m);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 472 */     return result;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/time/Second.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */