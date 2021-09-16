/*     */ package org.joda.time.base;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import org.joda.time.Chronology;
/*     */ import org.joda.time.DateTimeUtils;
/*     */ import org.joda.time.Interval;
/*     */ import org.joda.time.Period;
/*     */ import org.joda.time.PeriodType;
/*     */ import org.joda.time.ReadableDuration;
/*     */ import org.joda.time.ReadableInstant;
/*     */ import org.joda.time.convert.ConverterManager;
/*     */ import org.joda.time.convert.DurationConverter;
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
/*     */ public abstract class BaseDuration
/*     */   extends AbstractDuration
/*     */   implements ReadableDuration, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 2581698638990L;
/*     */   private volatile long iMillis;
/*     */   
/*     */   protected BaseDuration(long paramLong) {
/*  62 */     this.iMillis = paramLong;
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
/*     */   protected BaseDuration(long paramLong1, long paramLong2) {
/*  74 */     this.iMillis = FieldUtils.safeSubtract(paramLong2, paramLong1);
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
/*     */   protected BaseDuration(ReadableInstant paramReadableInstant1, ReadableInstant paramReadableInstant2) {
/*  86 */     if (paramReadableInstant1 == paramReadableInstant2) {
/*  87 */       this.iMillis = 0L;
/*     */     } else {
/*  89 */       long l1 = DateTimeUtils.getInstantMillis(paramReadableInstant1);
/*  90 */       long l2 = DateTimeUtils.getInstantMillis(paramReadableInstant2);
/*  91 */       this.iMillis = FieldUtils.safeSubtract(l2, l1);
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
/*     */   protected BaseDuration(Object paramObject) {
/* 104 */     DurationConverter durationConverter = ConverterManager.getInstance().getDurationConverter(paramObject);
/* 105 */     this.iMillis = durationConverter.getDurationMillis(paramObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getMillis() {
/* 115 */     return this.iMillis;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setMillis(long paramLong) {
/* 125 */     this.iMillis = paramLong;
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
/*     */   public Period toPeriod(PeriodType paramPeriodType) {
/* 144 */     return new Period(getMillis(), paramPeriodType);
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
/*     */   public Period toPeriod(Chronology paramChronology) {
/* 164 */     return new Period(getMillis(), paramChronology);
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
/*     */   public Period toPeriod(PeriodType paramPeriodType, Chronology paramChronology) {
/* 185 */     return new Period(getMillis(), paramPeriodType, paramChronology);
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
/*     */   public Period toPeriodFrom(ReadableInstant paramReadableInstant) {
/* 200 */     return new Period(paramReadableInstant, this);
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
/*     */   public Period toPeriodFrom(ReadableInstant paramReadableInstant, PeriodType paramPeriodType) {
/* 216 */     return new Period(paramReadableInstant, this, paramPeriodType);
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
/*     */   public Period toPeriodTo(ReadableInstant paramReadableInstant) {
/* 232 */     return new Period(this, paramReadableInstant);
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
/*     */   public Period toPeriodTo(ReadableInstant paramReadableInstant, PeriodType paramPeriodType) {
/* 249 */     return new Period(this, paramReadableInstant, paramPeriodType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Interval toIntervalFrom(ReadableInstant paramReadableInstant) {
/* 259 */     return new Interval(paramReadableInstant, this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Interval toIntervalTo(ReadableInstant paramReadableInstant) {
/* 269 */     return new Interval(this, paramReadableInstant);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/base/BaseDuration.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */