/*     */ package org.joda.time.base;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import org.joda.time.Chronology;
/*     */ import org.joda.time.DateTimeUtils;
/*     */ import org.joda.time.MutableInterval;
/*     */ import org.joda.time.ReadWritableInterval;
/*     */ import org.joda.time.ReadableDuration;
/*     */ import org.joda.time.ReadableInstant;
/*     */ import org.joda.time.ReadableInterval;
/*     */ import org.joda.time.ReadablePeriod;
/*     */ import org.joda.time.chrono.ISOChronology;
/*     */ import org.joda.time.convert.ConverterManager;
/*     */ import org.joda.time.convert.IntervalConverter;
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
/*     */ public abstract class BaseInterval
/*     */   extends AbstractInterval
/*     */   implements ReadableInterval, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 576586928732749278L;
/*     */   private volatile Chronology iChronology;
/*     */   private volatile long iStartMillis;
/*     */   private volatile long iEndMillis;
/*     */   
/*     */   protected BaseInterval(long paramLong1, long paramLong2, Chronology paramChronology) {
/*  72 */     this.iChronology = DateTimeUtils.getChronology(paramChronology);
/*  73 */     checkInterval(paramLong1, paramLong2);
/*  74 */     this.iStartMillis = paramLong1;
/*  75 */     this.iEndMillis = paramLong2;
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
/*     */   protected BaseInterval(ReadableInstant paramReadableInstant1, ReadableInstant paramReadableInstant2) {
/*  88 */     this.iStartMillis = this.iEndMillis = DateTimeUtils.currentTimeMillis();
/*  89 */     this.iChronology = (Chronology)ISOChronology.getInstance();
/*     */     
/*  91 */     this.iChronology = DateTimeUtils.getInstantChronology(paramReadableInstant1);
/*  92 */     this.iStartMillis = DateTimeUtils.getInstantMillis(paramReadableInstant1);
/*  93 */     this.iEndMillis = DateTimeUtils.getInstantMillis(paramReadableInstant2);
/*  94 */     checkInterval(this.iStartMillis, this.iEndMillis);
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
/*     */   protected BaseInterval(ReadableInstant paramReadableInstant, ReadableDuration paramReadableDuration) {
/* 108 */     this.iChronology = DateTimeUtils.getInstantChronology(paramReadableInstant);
/* 109 */     this.iStartMillis = DateTimeUtils.getInstantMillis(paramReadableInstant);
/* 110 */     long l = DateTimeUtils.getDurationMillis(paramReadableDuration);
/* 111 */     this.iEndMillis = FieldUtils.safeAdd(this.iStartMillis, l);
/* 112 */     checkInterval(this.iStartMillis, this.iEndMillis);
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
/*     */   protected BaseInterval(ReadableDuration paramReadableDuration, ReadableInstant paramReadableInstant) {
/* 125 */     this.iChronology = DateTimeUtils.getInstantChronology(paramReadableInstant);
/* 126 */     this.iEndMillis = DateTimeUtils.getInstantMillis(paramReadableInstant);
/* 127 */     long l = DateTimeUtils.getDurationMillis(paramReadableDuration);
/* 128 */     this.iStartMillis = FieldUtils.safeAdd(this.iEndMillis, -l);
/* 129 */     checkInterval(this.iStartMillis, this.iEndMillis);
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
/*     */   protected BaseInterval(ReadableInstant paramReadableInstant, ReadablePeriod paramReadablePeriod) {
/* 145 */     Chronology chronology = DateTimeUtils.getInstantChronology(paramReadableInstant);
/* 146 */     this.iChronology = chronology;
/* 147 */     this.iStartMillis = DateTimeUtils.getInstantMillis(paramReadableInstant);
/* 148 */     if (paramReadablePeriod == null) {
/* 149 */       this.iEndMillis = this.iStartMillis;
/*     */     } else {
/* 151 */       this.iEndMillis = chronology.add(paramReadablePeriod, this.iStartMillis, 1);
/*     */     } 
/* 153 */     checkInterval(this.iStartMillis, this.iEndMillis);
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
/*     */   protected BaseInterval(ReadablePeriod paramReadablePeriod, ReadableInstant paramReadableInstant) {
/* 169 */     Chronology chronology = DateTimeUtils.getInstantChronology(paramReadableInstant);
/* 170 */     this.iChronology = chronology;
/* 171 */     this.iEndMillis = DateTimeUtils.getInstantMillis(paramReadableInstant);
/* 172 */     if (paramReadablePeriod == null) {
/* 173 */       this.iStartMillis = this.iEndMillis;
/*     */     } else {
/* 175 */       this.iStartMillis = chronology.add(paramReadablePeriod, this.iEndMillis, -1);
/*     */     } 
/* 177 */     checkInterval(this.iStartMillis, this.iEndMillis);
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
/*     */   protected BaseInterval(Object paramObject, Chronology paramChronology) {
/* 190 */     IntervalConverter intervalConverter = ConverterManager.getInstance().getIntervalConverter(paramObject);
/* 191 */     if (intervalConverter.isReadableInterval(paramObject, paramChronology)) {
/* 192 */       ReadableInterval readableInterval = (ReadableInterval)paramObject;
/* 193 */       this.iChronology = (paramChronology != null) ? paramChronology : readableInterval.getChronology();
/* 194 */       this.iStartMillis = readableInterval.getStartMillis();
/* 195 */       this.iEndMillis = readableInterval.getEndMillis();
/* 196 */     } else if (this instanceof ReadWritableInterval) {
/* 197 */       intervalConverter.setInto((ReadWritableInterval)this, paramObject, paramChronology);
/*     */     } else {
/* 199 */       MutableInterval mutableInterval = new MutableInterval();
/* 200 */       intervalConverter.setInto((ReadWritableInterval)mutableInterval, paramObject, paramChronology);
/* 201 */       this.iChronology = mutableInterval.getChronology();
/* 202 */       this.iStartMillis = mutableInterval.getStartMillis();
/* 203 */       this.iEndMillis = mutableInterval.getEndMillis();
/*     */     } 
/* 205 */     checkInterval(this.iStartMillis, this.iEndMillis);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Chronology getChronology() {
/* 215 */     return this.iChronology;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getStartMillis() {
/* 225 */     return this.iStartMillis;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getEndMillis() {
/* 235 */     return this.iEndMillis;
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
/*     */   protected void setInterval(long paramLong1, long paramLong2, Chronology paramChronology) {
/* 248 */     checkInterval(paramLong1, paramLong2);
/* 249 */     this.iStartMillis = paramLong1;
/* 250 */     this.iEndMillis = paramLong2;
/* 251 */     this.iChronology = DateTimeUtils.getChronology(paramChronology);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/base/BaseInterval.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */