/*     */ package org.joda.time.chrono;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import org.joda.time.Chronology;
/*     */ import org.joda.time.DateTime;
/*     */ import org.joda.time.DateTimeField;
/*     */ import org.joda.time.DateTimeZone;
/*     */ import org.joda.time.ReadableDateTime;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class IslamicChronology
/*     */   extends BasicChronology
/*     */ {
/*     */   private static final long serialVersionUID = -3663823829888L;
/*     */   public static final int AH = 1;
/*  77 */   private static final DateTimeField ERA_FIELD = (DateTimeField)new BasicSingleEraDateTimeField("AH");
/*     */ 
/*     */   
/*  80 */   public static final LeapYearPatternType LEAP_YEAR_15_BASED = new LeapYearPatternType(0, 623158436);
/*     */   
/*  82 */   public static final LeapYearPatternType LEAP_YEAR_16_BASED = new LeapYearPatternType(1, 623191204);
/*     */   
/*  84 */   public static final LeapYearPatternType LEAP_YEAR_INDIAN = new LeapYearPatternType(2, 690562340);
/*     */   
/*  86 */   public static final LeapYearPatternType LEAP_YEAR_HABASH_AL_HASIB = new LeapYearPatternType(3, 153692453);
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int MIN_YEAR = -292269337;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int MAX_YEAR = 292271022;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int MONTH_PAIR_LENGTH = 59;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int LONG_MONTH_LENGTH = 30;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int SHORT_MONTH_LENGTH = 29;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long MILLIS_PER_MONTH_PAIR = 5097600000L;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long MILLIS_PER_MONTH = 2551440384L;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long MILLIS_PER_LONG_MONTH = 2592000000L;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long MILLIS_PER_YEAR = 30617280288L;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long MILLIS_PER_SHORT_YEAR = 30585600000L;
/*     */ 
/*     */   
/*     */   private static final long MILLIS_PER_LONG_YEAR = 30672000000L;
/*     */ 
/*     */   
/*     */   private static final long MILLIS_YEAR_1 = -42521587200000L;
/*     */ 
/*     */   
/*     */   private static final int CYCLE = 30;
/*     */ 
/*     */   
/*     */   private static final long MILLIS_PER_CYCLE = 918518400000L;
/*     */ 
/*     */   
/* 141 */   private static final ConcurrentHashMap<DateTimeZone, IslamicChronology[]> cCache = (ConcurrentHashMap)new ConcurrentHashMap<DateTimeZone, IslamicChronology>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 147 */   private static final IslamicChronology INSTANCE_UTC = getInstance(DateTimeZone.UTC);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final LeapYearPatternType iLeapYears;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static IslamicChronology getInstanceUTC() {
/* 161 */     return INSTANCE_UTC;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static IslamicChronology getInstance() {
/* 170 */     return getInstance(DateTimeZone.getDefault(), LEAP_YEAR_16_BASED);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static IslamicChronology getInstance(DateTimeZone paramDateTimeZone) {
/* 180 */     return getInstance(paramDateTimeZone, LEAP_YEAR_16_BASED);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static IslamicChronology getInstance(DateTimeZone paramDateTimeZone, LeapYearPatternType paramLeapYearPatternType) {
/* 191 */     if (paramDateTimeZone == null) {
/* 192 */       paramDateTimeZone = DateTimeZone.getDefault();
/*     */     }
/*     */     
/* 195 */     IslamicChronology[] arrayOfIslamicChronology = cCache.get(paramDateTimeZone);
/* 196 */     if (arrayOfIslamicChronology == null) {
/* 197 */       arrayOfIslamicChronology = new IslamicChronology[4];
/* 198 */       IslamicChronology[] arrayOfIslamicChronology1 = cCache.putIfAbsent(paramDateTimeZone, arrayOfIslamicChronology);
/* 199 */       if (arrayOfIslamicChronology1 != null) {
/* 200 */         arrayOfIslamicChronology = arrayOfIslamicChronology1;
/*     */       }
/*     */     } 
/* 203 */     IslamicChronology islamicChronology = arrayOfIslamicChronology[paramLeapYearPatternType.index];
/* 204 */     if (islamicChronology == null) {
/* 205 */       synchronized (arrayOfIslamicChronology) {
/* 206 */         islamicChronology = arrayOfIslamicChronology[paramLeapYearPatternType.index];
/* 207 */         if (islamicChronology == null) {
/* 208 */           if (paramDateTimeZone == DateTimeZone.UTC) {
/*     */             
/* 210 */             islamicChronology = new IslamicChronology(null, null, paramLeapYearPatternType);
/*     */             
/* 212 */             DateTime dateTime = new DateTime(1, 1, 1, 0, 0, 0, 0, islamicChronology);
/* 213 */             islamicChronology = new IslamicChronology(LimitChronology.getInstance(islamicChronology, (ReadableDateTime)dateTime, null), null, paramLeapYearPatternType);
/*     */           }
/*     */           else {
/*     */             
/* 217 */             islamicChronology = getInstance(DateTimeZone.UTC, paramLeapYearPatternType);
/* 218 */             islamicChronology = new IslamicChronology(ZonedChronology.getInstance(islamicChronology, paramDateTimeZone), null, paramLeapYearPatternType);
/*     */           } 
/*     */           
/* 221 */           arrayOfIslamicChronology[paramLeapYearPatternType.index] = islamicChronology;
/*     */         } 
/*     */       } 
/*     */     }
/* 225 */     return islamicChronology;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   IslamicChronology(Chronology paramChronology, Object paramObject, LeapYearPatternType paramLeapYearPatternType) {
/* 234 */     super(paramChronology, paramObject, 4);
/* 235 */     this.iLeapYears = paramLeapYearPatternType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Object readResolve() {
/* 242 */     Chronology chronology = getBase();
/* 243 */     return (chronology == null) ? getInstanceUTC() : getInstance(chronology.getZone());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LeapYearPatternType getLeapYearPatternType() {
/* 253 */     return this.iLeapYears;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Chronology withUTC() {
/* 264 */     return INSTANCE_UTC;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Chronology withZone(DateTimeZone paramDateTimeZone) {
/* 274 */     if (paramDateTimeZone == null) {
/* 275 */       paramDateTimeZone = DateTimeZone.getDefault();
/*     */     }
/* 277 */     if (paramDateTimeZone == getZone()) {
/* 278 */       return this;
/*     */     }
/* 280 */     return getInstance(paramDateTimeZone);
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
/*     */   public boolean equals(Object paramObject) {
/* 292 */     if (this == paramObject) {
/* 293 */       return true;
/*     */     }
/* 295 */     if (paramObject instanceof IslamicChronology) {
/* 296 */       IslamicChronology islamicChronology = (IslamicChronology)paramObject;
/* 297 */       return ((getLeapYearPatternType()).index == (islamicChronology.getLeapYearPatternType()).index && super.equals(paramObject));
/*     */     } 
/*     */     
/* 300 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 310 */     return super.hashCode() * 13 + getLeapYearPatternType().hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   int getYear(long paramLong) {
/* 315 */     long l1 = paramLong - -42521587200000L;
/* 316 */     long l2 = l1 / 918518400000L;
/* 317 */     long l3 = l1 % 918518400000L;
/*     */     
/* 319 */     int i = (int)(l2 * 30L + 1L);
/* 320 */     long l4 = isLeapYear(i) ? 30672000000L : 30585600000L;
/* 321 */     while (l3 >= l4) {
/* 322 */       l3 -= l4;
/* 323 */       l4 = isLeapYear(++i) ? 30672000000L : 30585600000L;
/*     */     } 
/* 325 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   long setYear(long paramLong, int paramInt) {
/* 330 */     int i = getYear(paramLong);
/* 331 */     int j = getDayOfYear(paramLong, i);
/* 332 */     int k = getMillisOfDay(paramLong);
/*     */ 
/*     */     
/* 335 */     if (j > 354 && !isLeapYear(paramInt))
/*     */     {
/* 337 */       j--;
/*     */     }
/*     */     
/* 340 */     paramLong = getYearMonthDayMillis(paramInt, 1, j);
/* 341 */     paramLong += k;
/* 342 */     return paramLong;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   long getYearDifference(long paramLong1, long paramLong2) {
/* 348 */     int i = getYear(paramLong1);
/* 349 */     int j = getYear(paramLong2);
/*     */ 
/*     */     
/* 352 */     long l1 = paramLong1 - getYearMillis(i);
/* 353 */     long l2 = paramLong2 - getYearMillis(j);
/*     */     
/* 355 */     int k = i - j;
/* 356 */     if (l1 < l2) {
/* 357 */       k--;
/*     */     }
/* 359 */     return k;
/*     */   }
/*     */ 
/*     */   
/*     */   long getTotalMillisByYearMonth(int paramInt1, int paramInt2) {
/* 364 */     if (--paramInt2 % 2 == 1) {
/* 365 */       paramInt2 /= 2;
/* 366 */       return paramInt2 * 5097600000L + 2592000000L;
/*     */     } 
/* 368 */     paramInt2 /= 2;
/* 369 */     return paramInt2 * 5097600000L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int getDayOfMonth(long paramLong) {
/* 376 */     int i = getDayOfYear(paramLong) - 1;
/* 377 */     if (i == 354) {
/* 378 */       return 30;
/*     */     }
/* 380 */     return i % 59 % 30 + 1;
/*     */   }
/*     */ 
/*     */   
/*     */   boolean isLeapYear(int paramInt) {
/* 385 */     return this.iLeapYears.isLeapYear(paramInt);
/*     */   }
/*     */ 
/*     */   
/*     */   int getDaysInYearMax() {
/* 390 */     return 355;
/*     */   }
/*     */ 
/*     */   
/*     */   int getDaysInYear(int paramInt) {
/* 395 */     return isLeapYear(paramInt) ? 355 : 354;
/*     */   }
/*     */ 
/*     */   
/*     */   int getDaysInYearMonth(int paramInt1, int paramInt2) {
/* 400 */     if (paramInt2 == 12 && isLeapYear(paramInt1)) {
/* 401 */       return 30;
/*     */     }
/* 403 */     return (--paramInt2 % 2 == 0) ? 30 : 29;
/*     */   }
/*     */ 
/*     */   
/*     */   int getDaysInMonthMax() {
/* 408 */     return 30;
/*     */   }
/*     */ 
/*     */   
/*     */   int getDaysInMonthMax(int paramInt) {
/* 413 */     if (paramInt == 12) {
/* 414 */       return 30;
/*     */     }
/* 416 */     return (--paramInt % 2 == 0) ? 30 : 29;
/*     */   }
/*     */ 
/*     */   
/*     */   int getMonthOfYear(long paramLong, int paramInt) {
/* 421 */     int i = (int)((paramLong - getYearMillis(paramInt)) / 86400000L);
/* 422 */     if (i == 354) {
/* 423 */       return 12;
/*     */     }
/* 425 */     return i * 2 / 59 + 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   long getAverageMillisPerYear() {
/* 435 */     return 30617280288L;
/*     */   }
/*     */ 
/*     */   
/*     */   long getAverageMillisPerYearDividedByTwo() {
/* 440 */     return 15308640144L;
/*     */   }
/*     */ 
/*     */   
/*     */   long getAverageMillisPerMonth() {
/* 445 */     return 2551440384L;
/*     */   }
/*     */ 
/*     */   
/*     */   long calculateFirstDayOfYearMillis(int paramInt) {
/* 450 */     if (paramInt > 292271022) {
/* 451 */       throw new ArithmeticException("Year is too large: " + paramInt + " > " + 292271022);
/*     */     }
/* 453 */     if (paramInt < -292269337) {
/* 454 */       throw new ArithmeticException("Year is too small: " + paramInt + " < " + -292269337);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 461 */     paramInt--;
/* 462 */     long l1 = (paramInt / 30);
/* 463 */     long l2 = -42521587200000L + l1 * 918518400000L;
/* 464 */     int i = paramInt % 30 + 1;
/*     */     
/* 466 */     for (byte b = 1; b < i; b++) {
/* 467 */       l2 += isLeapYear(b) ? 30672000000L : 30585600000L;
/*     */     }
/*     */     
/* 470 */     return l2;
/*     */   }
/*     */ 
/*     */   
/*     */   int getMinYear() {
/* 475 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   int getMaxYear() {
/* 480 */     return 292271022;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   long getApproxMillisAtEpochDividedByTwo() {
/* 486 */     return 21260793600000L;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void assemble(AssembledChronology.Fields paramFields) {
/* 491 */     if (getBase() == null) {
/* 492 */       super.assemble(paramFields);
/*     */       
/* 494 */       paramFields.era = ERA_FIELD;
/* 495 */       paramFields.monthOfYear = (DateTimeField)new BasicMonthOfYearDateTimeField(this, 12);
/* 496 */       paramFields.months = paramFields.monthOfYear.getDurationField();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class LeapYearPatternType
/*     */     implements Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 26581275372698L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     final byte index;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     final int pattern;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     LeapYearPatternType(int param1Int1, int param1Int2) {
/* 533 */       this.index = (byte)param1Int1;
/* 534 */       this.pattern = param1Int2;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     boolean isLeapYear(int param1Int) {
/* 543 */       int i = 1 << param1Int % 30;
/* 544 */       return ((this.pattern & i) > 0);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Object readResolve() {
/* 552 */       switch (this.index) {
/*     */         case 0:
/* 554 */           return IslamicChronology.LEAP_YEAR_15_BASED;
/*     */         case 1:
/* 556 */           return IslamicChronology.LEAP_YEAR_16_BASED;
/*     */         case 2:
/* 558 */           return IslamicChronology.LEAP_YEAR_INDIAN;
/*     */         case 3:
/* 560 */           return IslamicChronology.LEAP_YEAR_HABASH_AL_HASIB;
/*     */       } 
/* 562 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean equals(Object param1Object) {
/* 568 */       if (param1Object instanceof LeapYearPatternType) {
/* 569 */         return (this.index == ((LeapYearPatternType)param1Object).index);
/*     */       }
/* 571 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 576 */       return this.index;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/chrono/IslamicChronology.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */