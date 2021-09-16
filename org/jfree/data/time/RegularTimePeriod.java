/*     */ package org.jfree.data.time;
/*     */ 
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.TimeZone;
/*     */ import org.jfree.date.MonthConstants;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class RegularTimePeriod
/*     */   implements TimePeriod, Comparable, MonthConstants
/*     */ {
/*     */   public static RegularTimePeriod createInstance(Class c, Date millisecond, TimeZone zone) {
/*  87 */     RegularTimePeriod result = null;
/*     */     try {
/*  89 */       Constructor<RegularTimePeriod> constructor = c.getDeclaredConstructor(new Class[] { Date.class, TimeZone.class });
/*     */       
/*  91 */       result = constructor.newInstance(new Object[] { millisecond, zone });
/*     */     
/*     */     }
/*  94 */     catch (Exception e) {}
/*     */ 
/*     */     
/*  97 */     return result;
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
/*     */   public static Class downsize(Class c) {
/* 109 */     if (c.equals(Year.class)) {
/* 110 */       return Quarter.class;
/*     */     }
/* 112 */     if (c.equals(Quarter.class)) {
/* 113 */       return Month.class;
/*     */     }
/* 115 */     if (c.equals(Month.class)) {
/* 116 */       return Day.class;
/*     */     }
/* 118 */     if (c.equals(Day.class)) {
/* 119 */       return Hour.class;
/*     */     }
/* 121 */     if (c.equals(Hour.class)) {
/* 122 */       return Minute.class;
/*     */     }
/* 124 */     if (c.equals(Minute.class)) {
/* 125 */       return Second.class;
/*     */     }
/* 127 */     if (c.equals(Second.class)) {
/* 128 */       return Millisecond.class;
/*     */     }
/*     */     
/* 131 */     return Millisecond.class;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 166 */   public static final TimeZone DEFAULT_TIME_ZONE = TimeZone.getDefault();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 173 */   public static final Calendar WORKING_CALENDAR = Calendar.getInstance(DEFAULT_TIME_ZONE);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract RegularTimePeriod previous();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract RegularTimePeriod next();
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract long getSerialIndex();
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void peg(Calendar paramCalendar);
/*     */ 
/*     */ 
/*     */   
/*     */   public Date getStart() {
/* 196 */     return new Date(getFirstMillisecond());
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
/*     */   public Date getEnd() {
/* 209 */     return new Date(getLastMillisecond());
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
/*     */   public abstract long getFirstMillisecond();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getFirstMillisecond(TimeZone zone) {
/* 239 */     Calendar calendar = Calendar.getInstance(zone);
/* 240 */     return getFirstMillisecond(calendar);
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
/*     */   public abstract long getFirstMillisecond(Calendar paramCalendar);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract long getLastMillisecond();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getLastMillisecond(TimeZone zone) {
/* 284 */     Calendar calendar = Calendar.getInstance(zone);
/* 285 */     return getLastMillisecond(calendar);
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
/*     */   public abstract long getLastMillisecond(Calendar paramCalendar);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getMiddleMillisecond() {
/* 306 */     long m1 = getFirstMillisecond();
/* 307 */     long m2 = getLastMillisecond();
/* 308 */     return m1 + (m2 - m1) / 2L;
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
/*     */   public long getMiddleMillisecond(TimeZone zone) {
/* 324 */     Calendar calendar = Calendar.getInstance(zone);
/* 325 */     long m1 = getFirstMillisecond(calendar);
/* 326 */     long m2 = getLastMillisecond(calendar);
/* 327 */     return m1 + (m2 - m1) / 2L;
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
/*     */   public long getMiddleMillisecond(Calendar calendar) {
/* 339 */     long m1 = getFirstMillisecond(calendar);
/* 340 */     long m2 = getLastMillisecond(calendar);
/* 341 */     return m1 + (m2 - m1) / 2L;
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
/*     */   public long getMillisecond(TimePeriodAnchor anchor, Calendar calendar) {
/* 357 */     if (anchor.equals(TimePeriodAnchor.START))
/* 358 */       return getFirstMillisecond(calendar); 
/* 359 */     if (anchor.equals(TimePeriodAnchor.MIDDLE))
/* 360 */       return getMiddleMillisecond(calendar); 
/* 361 */     if (anchor.equals(TimePeriodAnchor.END)) {
/* 362 */       return getLastMillisecond(calendar);
/*     */     }
/* 364 */     throw new IllegalStateException("Unrecognised anchor: " + anchor);
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
/* 375 */     return String.valueOf(getStart());
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/time/RegularTimePeriod.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */