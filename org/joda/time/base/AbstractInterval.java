/*     */ package org.joda.time.base;
/*     */ 
/*     */ import org.joda.time.DateTime;
/*     */ import org.joda.time.DateTimeUtils;
/*     */ import org.joda.time.Duration;
/*     */ import org.joda.time.Interval;
/*     */ import org.joda.time.MutableInterval;
/*     */ import org.joda.time.Period;
/*     */ import org.joda.time.PeriodType;
/*     */ import org.joda.time.ReadableInstant;
/*     */ import org.joda.time.ReadableInterval;
/*     */ import org.joda.time.field.FieldUtils;
/*     */ import org.joda.time.format.DateTimeFormatter;
/*     */ import org.joda.time.format.ISODateTimeFormat;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractInterval
/*     */   implements ReadableInterval
/*     */ {
/*     */   protected void checkInterval(long paramLong1, long paramLong2) {
/*  62 */     if (paramLong2 < paramLong1) {
/*  63 */       throw new IllegalArgumentException("The end instant must be greater the start");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DateTime getStart() {
/*  74 */     return new DateTime(getStartMillis(), getChronology());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DateTime getEnd() {
/*  83 */     return new DateTime(getEndMillis(), getChronology());
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
/*     */   public boolean contains(long paramLong) {
/*  98 */     long l1 = getStartMillis();
/*  99 */     long l2 = getEndMillis();
/* 100 */     return (paramLong >= l1 && paramLong < l2);
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
/*     */   public boolean containsNow() {
/* 112 */     return contains(DateTimeUtils.currentTimeMillis());
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
/*     */   public boolean contains(ReadableInstant paramReadableInstant) {
/* 138 */     if (paramReadableInstant == null) {
/* 139 */       return containsNow();
/*     */     }
/* 141 */     return contains(paramReadableInstant.getMillis());
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
/*     */   public boolean contains(ReadableInterval paramReadableInterval) {
/* 179 */     if (paramReadableInterval == null) {
/* 180 */       return containsNow();
/*     */     }
/* 182 */     long l1 = paramReadableInterval.getStartMillis();
/* 183 */     long l2 = paramReadableInterval.getEndMillis();
/* 184 */     long l3 = getStartMillis();
/* 185 */     long l4 = getEndMillis();
/* 186 */     return (l3 <= l1 && l1 < l4 && l2 <= l4);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean overlaps(ReadableInterval paramReadableInterval) {
/* 231 */     long l1 = getStartMillis();
/* 232 */     long l2 = getEndMillis();
/* 233 */     if (paramReadableInterval == null) {
/* 234 */       long l = DateTimeUtils.currentTimeMillis();
/* 235 */       return (l1 < l && l < l2);
/*     */     } 
/* 237 */     long l3 = paramReadableInterval.getStartMillis();
/* 238 */     long l4 = paramReadableInterval.getEndMillis();
/* 239 */     return (l1 < l4 && l3 < l2);
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
/*     */   public boolean isEqual(ReadableInterval paramReadableInterval) {
/* 254 */     return (getStartMillis() == paramReadableInterval.getStartMillis() && getEndMillis() == paramReadableInterval.getEndMillis());
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
/*     */   public boolean isBefore(long paramLong) {
/* 268 */     return (getEndMillis() <= paramLong);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBeforeNow() {
/* 279 */     return isBefore(DateTimeUtils.currentTimeMillis());
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
/*     */   public boolean isBefore(ReadableInstant paramReadableInstant) {
/* 291 */     if (paramReadableInstant == null) {
/* 292 */       return isBeforeNow();
/*     */     }
/* 294 */     return isBefore(paramReadableInstant.getMillis());
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
/*     */   public boolean isBefore(ReadableInterval paramReadableInterval) {
/* 306 */     if (paramReadableInterval == null) {
/* 307 */       return isBeforeNow();
/*     */     }
/* 309 */     return isBefore(paramReadableInterval.getStartMillis());
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
/*     */   public boolean isAfter(long paramLong) {
/* 323 */     return (getStartMillis() > paramLong);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAfterNow() {
/* 334 */     return isAfter(DateTimeUtils.currentTimeMillis());
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
/*     */   public boolean isAfter(ReadableInstant paramReadableInstant) {
/* 346 */     if (paramReadableInstant == null) {
/* 347 */       return isAfterNow();
/*     */     }
/* 349 */     return isAfter(paramReadableInstant.getMillis());
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
/*     */   public boolean isAfter(ReadableInterval paramReadableInterval) {
/*     */     long l;
/* 363 */     if (paramReadableInterval == null) {
/* 364 */       l = DateTimeUtils.currentTimeMillis();
/*     */     } else {
/* 366 */       l = paramReadableInterval.getEndMillis();
/*     */     } 
/* 368 */     return (getStartMillis() >= l);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Interval toInterval() {
/* 378 */     return new Interval(getStartMillis(), getEndMillis(), getChronology());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MutableInterval toMutableInterval() {
/* 389 */     return new MutableInterval(getStartMillis(), getEndMillis(), getChronology());
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
/*     */   public long toDurationMillis() {
/* 402 */     return FieldUtils.safeSubtract(getEndMillis(), getStartMillis());
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
/*     */   public Duration toDuration() {
/* 414 */     long l = toDurationMillis();
/* 415 */     if (l == 0L) {
/* 416 */       return Duration.ZERO;
/*     */     }
/* 418 */     return new Duration(l);
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
/*     */   public Period toPeriod() {
/* 433 */     return new Period(getStartMillis(), getEndMillis(), getChronology());
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
/*     */   public Period toPeriod(PeriodType paramPeriodType) {
/* 447 */     return new Period(getStartMillis(), getEndMillis(), paramPeriodType, getChronology());
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
/*     */   public boolean equals(Object paramObject) {
/* 464 */     if (this == paramObject) {
/* 465 */       return true;
/*     */     }
/* 467 */     if (!(paramObject instanceof ReadableInterval)) {
/* 468 */       return false;
/*     */     }
/* 470 */     ReadableInterval readableInterval = (ReadableInterval)paramObject;
/* 471 */     return (getStartMillis() == readableInterval.getStartMillis() && getEndMillis() == readableInterval.getEndMillis() && FieldUtils.equals(getChronology(), readableInterval.getChronology()));
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
/*     */   public int hashCode() {
/* 483 */     long l1 = getStartMillis();
/* 484 */     long l2 = getEndMillis();
/* 485 */     int i = 97;
/* 486 */     i = 31 * i + (int)(l1 ^ l1 >>> 32L);
/* 487 */     i = 31 * i + (int)(l2 ^ l2 >>> 32L);
/* 488 */     i = 31 * i + getChronology().hashCode();
/* 489 */     return i;
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
/* 500 */     DateTimeFormatter dateTimeFormatter = ISODateTimeFormat.dateTime();
/* 501 */     dateTimeFormatter = dateTimeFormatter.withChronology(getChronology());
/* 502 */     StringBuffer stringBuffer = new StringBuffer(48);
/* 503 */     dateTimeFormatter.printTo(stringBuffer, getStartMillis());
/* 504 */     stringBuffer.append('/');
/* 505 */     dateTimeFormatter.printTo(stringBuffer, getEndMillis());
/* 506 */     return stringBuffer.toString();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/base/AbstractInterval.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */