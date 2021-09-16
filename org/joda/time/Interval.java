/*     */ package org.joda.time;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import org.joda.time.base.BaseInterval;
/*     */ import org.joda.time.chrono.ISOChronology;
/*     */ import org.joda.time.format.DateTimeFormatter;
/*     */ import org.joda.time.format.ISODateTimeFormat;
/*     */ import org.joda.time.format.ISOPeriodFormat;
/*     */ import org.joda.time.format.PeriodFormatter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Interval
/*     */   extends BaseInterval
/*     */   implements ReadableInterval, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 4922451897541386752L;
/*     */   
/*     */   public static Interval parse(String paramString) {
/*  75 */     return new Interval(paramString);
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
/*     */   public static Interval parseWithOffset(String paramString) {
/* 105 */     int i = paramString.indexOf('/');
/* 106 */     if (i < 0) {
/* 107 */       throw new IllegalArgumentException("Format requires a '/' separator: " + paramString);
/*     */     }
/* 109 */     String str1 = paramString.substring(0, i);
/* 110 */     if (str1.length() <= 0) {
/* 111 */       throw new IllegalArgumentException("Format invalid: " + paramString);
/*     */     }
/* 113 */     String str2 = paramString.substring(i + 1);
/* 114 */     if (str2.length() <= 0) {
/* 115 */       throw new IllegalArgumentException("Format invalid: " + paramString);
/*     */     }
/*     */     
/* 118 */     DateTimeFormatter dateTimeFormatter = ISODateTimeFormat.dateTimeParser().withOffsetParsed();
/* 119 */     PeriodFormatter periodFormatter = ISOPeriodFormat.standard();
/* 120 */     DateTime dateTime1 = null;
/* 121 */     Period period = null;
/*     */ 
/*     */     
/* 124 */     char c = str1.charAt(0);
/* 125 */     if (c == 'P' || c == 'p') {
/* 126 */       period = periodFormatter.withParseType(PeriodType.standard()).parsePeriod(str1);
/*     */     } else {
/* 128 */       dateTime1 = dateTimeFormatter.parseDateTime(str1);
/*     */     } 
/*     */ 
/*     */     
/* 132 */     c = str2.charAt(0);
/* 133 */     if (c == 'P' || c == 'p') {
/* 134 */       if (period != null) {
/* 135 */         throw new IllegalArgumentException("Interval composed of two durations: " + paramString);
/*     */       }
/* 137 */       period = periodFormatter.withParseType(PeriodType.standard()).parsePeriod(str2);
/* 138 */       return new Interval(dateTime1, period);
/*     */     } 
/* 140 */     DateTime dateTime2 = dateTimeFormatter.parseDateTime(str2);
/* 141 */     if (period != null) {
/* 142 */       return new Interval(period, dateTime2);
/*     */     }
/* 144 */     return new Interval(dateTime1, dateTime2);
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
/*     */   public Interval(long paramLong1, long paramLong2) {
/* 159 */     super(paramLong1, paramLong2, null);
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
/*     */   public Interval(long paramLong1, long paramLong2, DateTimeZone paramDateTimeZone) {
/* 173 */     super(paramLong1, paramLong2, (Chronology)ISOChronology.getInstance(paramDateTimeZone));
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
/*     */   public Interval(long paramLong1, long paramLong2, Chronology paramChronology) {
/* 186 */     super(paramLong1, paramLong2, paramChronology);
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
/*     */   public Interval(ReadableInstant paramReadableInstant1, ReadableInstant paramReadableInstant2) {
/* 199 */     super(paramReadableInstant1, paramReadableInstant2);
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
/*     */   public Interval(ReadableInstant paramReadableInstant, ReadableDuration paramReadableDuration) {
/* 211 */     super(paramReadableInstant, paramReadableDuration);
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
/*     */   public Interval(ReadableDuration paramReadableDuration, ReadableInstant paramReadableInstant) {
/* 223 */     super(paramReadableDuration, paramReadableInstant);
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
/*     */   public Interval(ReadableInstant paramReadableInstant, ReadablePeriod paramReadablePeriod) {
/* 238 */     super(paramReadableInstant, paramReadablePeriod);
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
/*     */   public Interval(ReadablePeriod paramReadablePeriod, ReadableInstant paramReadableInstant) {
/* 253 */     super(paramReadablePeriod, paramReadableInstant);
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
/*     */   public Interval(Object paramObject) {
/* 270 */     super(paramObject, null);
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
/*     */   public Interval(Object paramObject, Chronology paramChronology) {
/* 289 */     super(paramObject, paramChronology);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Interval toInterval() {
/* 300 */     return this;
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
/*     */   public Interval overlap(ReadableInterval paramReadableInterval) {
/* 328 */     paramReadableInterval = DateTimeUtils.getReadableInterval(paramReadableInterval);
/* 329 */     if (!overlaps(paramReadableInterval)) {
/* 330 */       return null;
/*     */     }
/* 332 */     long l1 = Math.max(getStartMillis(), paramReadableInterval.getStartMillis());
/* 333 */     long l2 = Math.min(getEndMillis(), paramReadableInterval.getEndMillis());
/* 334 */     return new Interval(l1, l2, getChronology());
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
/*     */   public Interval gap(ReadableInterval paramReadableInterval) {
/* 363 */     paramReadableInterval = DateTimeUtils.getReadableInterval(paramReadableInterval);
/* 364 */     long l1 = paramReadableInterval.getStartMillis();
/* 365 */     long l2 = paramReadableInterval.getEndMillis();
/* 366 */     long l3 = getStartMillis();
/* 367 */     long l4 = getEndMillis();
/* 368 */     if (l3 > l2)
/* 369 */       return new Interval(l2, l3, getChronology()); 
/* 370 */     if (l1 > l4) {
/* 371 */       return new Interval(l4, l1, getChronology());
/*     */     }
/* 373 */     return null;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean abuts(ReadableInterval paramReadableInterval) {
/* 415 */     if (paramReadableInterval == null) {
/* 416 */       long l = DateTimeUtils.currentTimeMillis();
/* 417 */       return (getStartMillis() == l || getEndMillis() == l);
/*     */     } 
/* 419 */     return (paramReadableInterval.getEndMillis() == getStartMillis() || getEndMillis() == paramReadableInterval.getStartMillis());
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
/*     */   public Interval withChronology(Chronology paramChronology) {
/* 432 */     if (getChronology() == paramChronology) {
/* 433 */       return this;
/*     */     }
/* 435 */     return new Interval(getStartMillis(), getEndMillis(), paramChronology);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Interval withStartMillis(long paramLong) {
/* 446 */     if (paramLong == getStartMillis()) {
/* 447 */       return this;
/*     */     }
/* 449 */     return new Interval(paramLong, getEndMillis(), getChronology());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Interval withStart(ReadableInstant paramReadableInstant) {
/* 460 */     long l = DateTimeUtils.getInstantMillis(paramReadableInstant);
/* 461 */     return withStartMillis(l);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Interval withEndMillis(long paramLong) {
/* 472 */     if (paramLong == getEndMillis()) {
/* 473 */       return this;
/*     */     }
/* 475 */     return new Interval(getStartMillis(), paramLong, getChronology());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Interval withEnd(ReadableInstant paramReadableInstant) {
/* 486 */     long l = DateTimeUtils.getInstantMillis(paramReadableInstant);
/* 487 */     return withEndMillis(l);
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
/*     */   public Interval withDurationAfterStart(ReadableDuration paramReadableDuration) {
/* 499 */     long l1 = DateTimeUtils.getDurationMillis(paramReadableDuration);
/* 500 */     if (l1 == toDurationMillis()) {
/* 501 */       return this;
/*     */     }
/* 503 */     Chronology chronology = getChronology();
/* 504 */     long l2 = getStartMillis();
/* 505 */     long l3 = chronology.add(l2, l1, 1);
/* 506 */     return new Interval(l2, l3, chronology);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Interval withDurationBeforeEnd(ReadableDuration paramReadableDuration) {
/* 517 */     long l1 = DateTimeUtils.getDurationMillis(paramReadableDuration);
/* 518 */     if (l1 == toDurationMillis()) {
/* 519 */       return this;
/*     */     }
/* 521 */     Chronology chronology = getChronology();
/* 522 */     long l2 = getEndMillis();
/* 523 */     long l3 = chronology.add(l2, l1, -1);
/* 524 */     return new Interval(l3, l2, chronology);
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
/*     */   public Interval withPeriodAfterStart(ReadablePeriod paramReadablePeriod) {
/* 536 */     if (paramReadablePeriod == null) {
/* 537 */       return withDurationAfterStart((ReadableDuration)null);
/*     */     }
/* 539 */     Chronology chronology = getChronology();
/* 540 */     long l1 = getStartMillis();
/* 541 */     long l2 = chronology.add(paramReadablePeriod, l1, 1);
/* 542 */     return new Interval(l1, l2, chronology);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Interval withPeriodBeforeEnd(ReadablePeriod paramReadablePeriod) {
/* 553 */     if (paramReadablePeriod == null) {
/* 554 */       return withDurationBeforeEnd((ReadableDuration)null);
/*     */     }
/* 556 */     Chronology chronology = getChronology();
/* 557 */     long l1 = getEndMillis();
/* 558 */     long l2 = chronology.add(paramReadablePeriod, l1, -1);
/* 559 */     return new Interval(l2, l1, chronology);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/Interval.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */