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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Week
/*     */   extends RegularTimePeriod
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1856387786939865061L;
/*     */   public static final int FIRST_WEEK_IN_YEAR = 1;
/*     */   public static final int LAST_WEEK_IN_YEAR = 53;
/*     */   private short year;
/*     */   private byte week;
/*     */   private long firstMillisecond;
/*     */   private long lastMillisecond;
/*     */   
/*     */   public Week() {
/* 122 */     this(new Date());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Week(int week, int year) {
/* 132 */     if (week < 1 && week > 53) {
/* 133 */       throw new IllegalArgumentException("The 'week' argument must be in the range 1 - 53.");
/*     */     }
/*     */     
/* 136 */     this.week = (byte)week;
/* 137 */     this.year = (short)year;
/* 138 */     peg(Calendar.getInstance());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Week(int week, Year year) {
/* 148 */     if (week < 1 && week > 53) {
/* 149 */       throw new IllegalArgumentException("The 'week' argument must be in the range 1 - 53.");
/*     */     }
/*     */     
/* 152 */     this.week = (byte)week;
/* 153 */     this.year = (short)year.getYear();
/* 154 */     peg(Calendar.getInstance());
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
/*     */   public Week(Date time) {
/* 169 */     this(time, TimeZone.getDefault(), Locale.getDefault());
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
/*     */   public Week(Date time, TimeZone zone) {
/* 183 */     this(time, zone, Locale.getDefault());
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
/*     */   public Week(Date time, TimeZone zone, Locale locale) {
/* 197 */     ParamChecks.nullNotPermitted(time, "time");
/* 198 */     ParamChecks.nullNotPermitted(zone, "zone");
/* 199 */     ParamChecks.nullNotPermitted(locale, "locale");
/* 200 */     Calendar calendar = Calendar.getInstance(zone, locale);
/* 201 */     calendar.setTime(time);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 206 */     int tempWeek = calendar.get(3);
/* 207 */     if (tempWeek == 1 && calendar
/* 208 */       .get(2) == 11) {
/* 209 */       this.week = 1;
/* 210 */       this.year = (short)(calendar.get(1) + 1);
/*     */     } else {
/*     */       
/* 213 */       this.week = (byte)Math.min(tempWeek, 53);
/* 214 */       int yyyy = calendar.get(1);
/*     */ 
/*     */       
/* 217 */       if (calendar.get(2) == 0 && this.week >= 52)
/*     */       {
/* 219 */         yyyy--;
/*     */       }
/* 221 */       this.year = (short)yyyy;
/*     */     } 
/* 223 */     peg(calendar);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Year getYear() {
/* 232 */     return new Year(this.year);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getYearValue() {
/* 241 */     return this.year;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWeek() {
/* 250 */     return this.week;
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
/* 265 */     return this.firstMillisecond;
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
/* 280 */     return this.lastMillisecond;
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
/* 293 */     this.firstMillisecond = getFirstMillisecond(calendar);
/* 294 */     this.lastMillisecond = getLastMillisecond(calendar);
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
/*     */   public RegularTimePeriod previous() {
/*     */     Week result;
/* 309 */     if (this.week != 1) {
/* 310 */       result = new Week(this.week - 1, this.year);
/*     */ 
/*     */     
/*     */     }
/* 314 */     else if (this.year > 1900) {
/* 315 */       int yy = this.year - 1;
/* 316 */       Calendar prevYearCalendar = Calendar.getInstance();
/* 317 */       prevYearCalendar.set(yy, 11, 31);
/* 318 */       result = new Week(prevYearCalendar.getActualMaximum(3), yy);
/*     */     }
/*     */     else {
/*     */       
/* 322 */       result = null;
/*     */     } 
/*     */     
/* 325 */     return result;
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
/*     */   public RegularTimePeriod next() {
/*     */     Week result;
/* 342 */     if (this.week < 52) {
/* 343 */       result = new Week(this.week + 1, this.year);
/*     */     } else {
/*     */       
/* 346 */       Calendar calendar = Calendar.getInstance();
/* 347 */       calendar.set(this.year, 11, 31);
/*     */       
/* 349 */       int actualMaxWeek = calendar.getActualMaximum(3);
/* 350 */       if (this.week < actualMaxWeek) {
/* 351 */         result = new Week(this.week + 1, this.year);
/*     */       
/*     */       }
/* 354 */       else if (this.year < 9999) {
/* 355 */         result = new Week(1, this.year + 1);
/*     */       } else {
/*     */         
/* 358 */         result = null;
/*     */       } 
/*     */     } 
/*     */     
/* 362 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getSerialIndex() {
/* 373 */     return this.year * 53L + this.week;
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
/* 389 */     Calendar c = (Calendar)calendar.clone();
/* 390 */     c.clear();
/* 391 */     c.set(1, this.year);
/* 392 */     c.set(3, this.week);
/* 393 */     c.set(7, c.getFirstDayOfWeek());
/* 394 */     c.set(10, 0);
/* 395 */     c.set(12, 0);
/* 396 */     c.set(13, 0);
/* 397 */     c.set(14, 0);
/* 398 */     return c.getTimeInMillis();
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
/* 414 */     Calendar c = (Calendar)calendar.clone();
/* 415 */     c.clear();
/* 416 */     c.set(1, this.year);
/* 417 */     c.set(3, this.week + 1);
/* 418 */     c.set(7, c.getFirstDayOfWeek());
/* 419 */     c.set(10, 0);
/* 420 */     c.set(12, 0);
/* 421 */     c.set(13, 0);
/* 422 */     c.set(14, 0);
/* 423 */     return c.getTimeInMillis() - 1L;
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
/* 435 */     return "Week " + this.week + ", " + this.year;
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
/* 451 */     if (obj == this) {
/* 452 */       return true;
/*     */     }
/* 454 */     if (!(obj instanceof Week)) {
/* 455 */       return false;
/*     */     }
/* 457 */     Week that = (Week)obj;
/* 458 */     if (this.week != that.week) {
/* 459 */       return false;
/*     */     }
/* 461 */     if (this.year != that.year) {
/* 462 */       return false;
/*     */     }
/* 464 */     return true;
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
/*     */   public int hashCode() {
/* 479 */     int result = 17;
/* 480 */     result = 37 * result + this.week;
/* 481 */     result = 37 * result + this.year;
/* 482 */     return result;
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
/* 502 */     if (o1 instanceof Week) {
/* 503 */       Week w = (Week)o1;
/* 504 */       result = this.year - w.getYear().getYear();
/* 505 */       if (result == 0) {
/* 506 */         result = this.week - w.getWeek();
/*     */ 
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 512 */     else if (o1 instanceof RegularTimePeriod) {
/*     */       
/* 514 */       result = 0;
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 521 */       result = 1;
/*     */     } 
/*     */     
/* 524 */     return result;
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
/*     */   public static Week parseWeek(String s) {
/* 541 */     Week result = null;
/* 542 */     if (s != null) {
/*     */ 
/*     */       
/* 545 */       s = s.trim();
/*     */       
/* 547 */       int i = findSeparator(s);
/* 548 */       if (i != -1) {
/* 549 */         String s1 = s.substring(0, i).trim();
/* 550 */         String s2 = s.substring(i + 1, s.length()).trim();
/*     */         
/* 552 */         Year y = evaluateAsYear(s1);
/*     */         
/* 554 */         if (y != null) {
/* 555 */           int w = stringToWeek(s2);
/* 556 */           if (w == -1) {
/* 557 */             throw new TimePeriodFormatException("Can't evaluate the week.");
/*     */           }
/*     */           
/* 560 */           result = new Week(w, y);
/*     */         } else {
/*     */           
/* 563 */           y = evaluateAsYear(s2);
/* 564 */           if (y != null) {
/* 565 */             int w = stringToWeek(s1);
/* 566 */             if (w == -1) {
/* 567 */               throw new TimePeriodFormatException("Can't evaluate the week.");
/*     */             }
/*     */             
/* 570 */             result = new Week(w, y);
/*     */           } else {
/*     */             
/* 573 */             throw new TimePeriodFormatException("Can't evaluate the year.");
/*     */           }
/*     */         
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 580 */         throw new TimePeriodFormatException("Could not find separator.");
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 585 */     return result;
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
/*     */   private static int findSeparator(String s) {
/* 599 */     int result = s.indexOf('-');
/* 600 */     if (result == -1) {
/* 601 */       result = s.indexOf(',');
/*     */     }
/* 603 */     if (result == -1) {
/* 604 */       result = s.indexOf(' ');
/*     */     }
/* 606 */     if (result == -1) {
/* 607 */       result = s.indexOf('.');
/*     */     }
/* 609 */     return result;
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
/*     */   private static Year evaluateAsYear(String s) {
/* 623 */     Year result = null;
/*     */     try {
/* 625 */       result = Year.parseYear(s);
/*     */     }
/* 627 */     catch (TimePeriodFormatException e) {}
/*     */ 
/*     */     
/* 630 */     return result;
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
/*     */   private static int stringToWeek(String s) {
/* 643 */     int result = -1;
/* 644 */     s = s.replace('W', ' ');
/* 645 */     s = s.trim();
/*     */     try {
/* 647 */       result = Integer.parseInt(s);
/* 648 */       if (result < 1 || result > 53) {
/* 649 */         result = -1;
/*     */       }
/*     */     }
/* 652 */     catch (NumberFormatException e) {}
/*     */ 
/*     */     
/* 655 */     return result;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/time/Week.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */