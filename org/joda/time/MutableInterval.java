/*     */ package org.joda.time;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import org.joda.time.base.BaseInterval;
/*     */ import org.joda.time.field.FieldUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MutableInterval
/*     */   extends BaseInterval
/*     */   implements ReadWritableInterval, Cloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -5982824024992428470L;
/*     */   
/*     */   public static MutableInterval parse(String paramString) {
/*  71 */     return new MutableInterval(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MutableInterval() {
/*  79 */     super(0L, 0L, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MutableInterval(long paramLong1, long paramLong2) {
/*  90 */     super(paramLong1, paramLong2, null);
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
/*     */   public MutableInterval(long paramLong1, long paramLong2, Chronology paramChronology) {
/* 102 */     super(paramLong1, paramLong2, paramChronology);
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
/*     */   public MutableInterval(ReadableInstant paramReadableInstant1, ReadableInstant paramReadableInstant2) {
/* 115 */     super(paramReadableInstant1, paramReadableInstant2);
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
/*     */   public MutableInterval(ReadableInstant paramReadableInstant, ReadableDuration paramReadableDuration) {
/* 127 */     super(paramReadableInstant, paramReadableDuration);
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
/*     */   public MutableInterval(ReadableDuration paramReadableDuration, ReadableInstant paramReadableInstant) {
/* 139 */     super(paramReadableDuration, paramReadableInstant);
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
/*     */   public MutableInterval(ReadableInstant paramReadableInstant, ReadablePeriod paramReadablePeriod) {
/* 154 */     super(paramReadableInstant, paramReadablePeriod);
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
/*     */   public MutableInterval(ReadablePeriod paramReadablePeriod, ReadableInstant paramReadableInstant) {
/* 169 */     super(paramReadablePeriod, paramReadableInstant);
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
/*     */   public MutableInterval(Object paramObject) {
/* 186 */     super(paramObject, null);
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
/*     */   public MutableInterval(Object paramObject, Chronology paramChronology) {
/* 205 */     super(paramObject, paramChronology);
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
/*     */   public void setInterval(long paramLong1, long paramLong2) {
/* 217 */     setInterval(paramLong1, paramLong2, getChronology());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInterval(ReadableInterval paramReadableInterval) {
/* 227 */     if (paramReadableInterval == null) {
/* 228 */       throw new IllegalArgumentException("Interval must not be null");
/*     */     }
/* 230 */     long l1 = paramReadableInterval.getStartMillis();
/* 231 */     long l2 = paramReadableInterval.getEndMillis();
/* 232 */     Chronology chronology = paramReadableInterval.getChronology();
/* 233 */     setInterval(l1, l2, chronology);
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
/*     */   public void setInterval(ReadableInstant paramReadableInstant1, ReadableInstant paramReadableInstant2) {
/* 245 */     if (paramReadableInstant1 == null && paramReadableInstant2 == null) {
/* 246 */       long l = DateTimeUtils.currentTimeMillis();
/* 247 */       setInterval(l, l);
/*     */     } else {
/* 249 */       long l1 = DateTimeUtils.getInstantMillis(paramReadableInstant1);
/* 250 */       long l2 = DateTimeUtils.getInstantMillis(paramReadableInstant2);
/* 251 */       Chronology chronology = DateTimeUtils.getInstantChronology(paramReadableInstant1);
/* 252 */       setInterval(l1, l2, chronology);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setChronology(Chronology paramChronology) {
/* 263 */     setInterval(getStartMillis(), getEndMillis(), paramChronology);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setStartMillis(long paramLong) {
/* 274 */     setInterval(paramLong, getEndMillis(), getChronology());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setStart(ReadableInstant paramReadableInstant) {
/* 284 */     long l = DateTimeUtils.getInstantMillis(paramReadableInstant);
/* 285 */     setInterval(l, getEndMillis(), getChronology());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEndMillis(long paramLong) {
/* 296 */     setInterval(getStartMillis(), paramLong, getChronology());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEnd(ReadableInstant paramReadableInstant) {
/* 306 */     long l = DateTimeUtils.getInstantMillis(paramReadableInstant);
/* 307 */     setInterval(getStartMillis(), l, getChronology());
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
/*     */   public void setDurationAfterStart(long paramLong) {
/* 319 */     setEndMillis(FieldUtils.safeAdd(getStartMillis(), paramLong));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDurationBeforeEnd(long paramLong) {
/* 330 */     setStartMillis(FieldUtils.safeAdd(getEndMillis(), -paramLong));
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
/*     */   public void setDurationAfterStart(ReadableDuration paramReadableDuration) {
/* 342 */     long l = DateTimeUtils.getDurationMillis(paramReadableDuration);
/* 343 */     setEndMillis(FieldUtils.safeAdd(getStartMillis(), l));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDurationBeforeEnd(ReadableDuration paramReadableDuration) {
/* 354 */     long l = DateTimeUtils.getDurationMillis(paramReadableDuration);
/* 355 */     setStartMillis(FieldUtils.safeAdd(getEndMillis(), -l));
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
/*     */   public void setPeriodAfterStart(ReadablePeriod paramReadablePeriod) {
/* 368 */     if (paramReadablePeriod == null) {
/* 369 */       setEndMillis(getStartMillis());
/*     */     } else {
/* 371 */       setEndMillis(getChronology().add(paramReadablePeriod, getStartMillis(), 1));
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
/*     */   
/*     */   public void setPeriodBeforeEnd(ReadablePeriod paramReadablePeriod) {
/* 384 */     if (paramReadablePeriod == null) {
/* 385 */       setStartMillis(getEndMillis());
/*     */     } else {
/* 387 */       setStartMillis(getChronology().add(paramReadablePeriod, getEndMillis(), -1));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MutableInterval copy() {
/* 398 */     return (MutableInterval)clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object clone() {
/*     */     try {
/* 408 */       return super.clone();
/* 409 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/* 410 */       throw new InternalError("Clone error");
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/MutableInterval.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */