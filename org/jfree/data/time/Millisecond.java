/*     */ package org.jfree.data.time;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.Locale;
/*     */ import java.util.TimeZone;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Millisecond
/*     */   extends RegularTimePeriod
/*     */   implements Serializable
/*     */ {
/*     */   static final long serialVersionUID = -5316836467277638485L;
/*     */   public static final int FIRST_MILLISECOND_IN_SECOND = 0;
/*     */   public static final int LAST_MILLISECOND_IN_SECOND = 999;
/*     */   private Day day;
/*     */   private byte hour;
/*     */   private byte minute;
/*     */   private byte second;
/*     */   private int millisecond;
/*     */   private long firstMillisecond;
/*     */   
/*     */   public Millisecond() {
/* 108 */     this(new Date());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Millisecond(int millisecond, Second second) {
/* 118 */     this.millisecond = millisecond;
/* 119 */     this.second = (byte)second.getSecond();
/* 120 */     this.minute = (byte)second.getMinute().getMinute();
/* 121 */     this.hour = (byte)second.getMinute().getHourValue();
/* 122 */     this.day = second.getMinute().getDay();
/* 123 */     peg(Calendar.getInstance());
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
/*     */   public Millisecond(int millisecond, int second, int minute, int hour, int day, int month, int year) {
/* 140 */     this(millisecond, new Second(second, minute, hour, day, month, year));
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
/*     */   public Millisecond(Date time) {
/* 152 */     this(time, TimeZone.getDefault(), Locale.getDefault());
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
/*     */   public Millisecond(Date time, TimeZone zone) {
/* 165 */     this(time, zone, Locale.getDefault());
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
/*     */   public Millisecond(Date time, TimeZone zone, Locale locale) {
/* 178 */     Calendar calendar = Calendar.getInstance(zone, locale);
/* 179 */     calendar.setTime(time);
/* 180 */     this.millisecond = calendar.get(14);
/* 181 */     this.second = (byte)calendar.get(13);
/* 182 */     this.minute = (byte)calendar.get(12);
/* 183 */     this.hour = (byte)calendar.get(11);
/* 184 */     this.day = new Day(time, zone, locale);
/* 185 */     peg(calendar);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Second getSecond() {
/* 194 */     return new Second(this.second, this.minute, this.hour, this.day
/* 195 */         .getDayOfMonth(), this.day.getMonth(), this.day
/* 196 */         .getYear());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getMillisecond() {
/* 205 */     return this.millisecond;
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
/*     */   public void peg(Calendar calendar) {
/* 248 */     this.firstMillisecond = getFirstMillisecond(calendar);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RegularTimePeriod previous() {
/* 258 */     RegularTimePeriod result = null;
/* 259 */     if (this.millisecond != 0) {
/* 260 */       result = new Millisecond(this.millisecond - 1, getSecond());
/*     */     } else {
/*     */       
/* 263 */       Second previous = (Second)getSecond().previous();
/* 264 */       if (previous != null) {
/* 265 */         result = new Millisecond(999, previous);
/*     */       }
/*     */     } 
/* 268 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RegularTimePeriod next() {
/* 278 */     RegularTimePeriod result = null;
/* 279 */     if (this.millisecond != 999) {
/* 280 */       result = new Millisecond(this.millisecond + 1, getSecond());
/*     */     } else {
/*     */       
/* 283 */       Second next = (Second)getSecond().next();
/* 284 */       if (next != null) {
/* 285 */         result = new Millisecond(0, next);
/*     */       }
/*     */     } 
/* 288 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getSerialIndex() {
/* 298 */     long hourIndex = this.day.getSerialIndex() * 24L + this.hour;
/* 299 */     long minuteIndex = hourIndex * 60L + this.minute;
/* 300 */     long secondIndex = minuteIndex * 60L + this.second;
/* 301 */     return secondIndex * 1000L + this.millisecond;
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
/* 317 */     if (obj == this) {
/* 318 */       return true;
/*     */     }
/* 320 */     if (!(obj instanceof Millisecond)) {
/* 321 */       return false;
/*     */     }
/* 323 */     Millisecond that = (Millisecond)obj;
/* 324 */     if (this.millisecond != that.millisecond) {
/* 325 */       return false;
/*     */     }
/* 327 */     if (this.second != that.second) {
/* 328 */       return false;
/*     */     }
/* 330 */     if (this.minute != that.minute) {
/* 331 */       return false;
/*     */     }
/* 333 */     if (this.hour != that.hour) {
/* 334 */       return false;
/*     */     }
/* 336 */     if (!this.day.equals(that.day)) {
/* 337 */       return false;
/*     */     }
/* 339 */     return true;
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
/* 353 */     int result = 17;
/* 354 */     result = 37 * result + this.millisecond;
/* 355 */     result = 37 * result + getSecond().hashCode();
/* 356 */     return result;
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
/*     */   public int compareTo(Object obj) {
/*     */     int result;
/* 376 */     if (obj instanceof Millisecond) {
/* 377 */       Millisecond ms = (Millisecond)obj;
/* 378 */       long difference = getFirstMillisecond() - ms.getFirstMillisecond();
/* 379 */       if (difference > 0L) {
/* 380 */         result = 1;
/*     */       
/*     */       }
/* 383 */       else if (difference < 0L) {
/* 384 */         result = -1;
/*     */       } else {
/*     */         
/* 387 */         result = 0;
/*     */ 
/*     */       
/*     */       }
/*     */ 
/*     */     
/*     */     }
/* 394 */     else if (obj instanceof RegularTimePeriod) {
/* 395 */       RegularTimePeriod rtp = (RegularTimePeriod)obj;
/* 396 */       long thisVal = getFirstMillisecond();
/* 397 */       long anotherVal = rtp.getFirstMillisecond();
/* 398 */       result = (thisVal < anotherVal) ? -1 : ((thisVal == anotherVal) ? 0 : 1);
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */ 
/*     */       
/* 406 */       result = 1;
/*     */     } 
/*     */     
/* 409 */     return result;
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
/* 424 */     int year = this.day.getYear();
/* 425 */     int month = this.day.getMonth() - 1;
/* 426 */     int d = this.day.getDayOfMonth();
/* 427 */     calendar.clear();
/* 428 */     calendar.set(year, month, d, this.hour, this.minute, this.second);
/* 429 */     calendar.set(14, this.millisecond);
/* 430 */     return calendar.getTimeInMillis();
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
/* 445 */     return getFirstMillisecond(calendar);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/time/Millisecond.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */