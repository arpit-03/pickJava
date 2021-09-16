/*     */ package org.joda.time;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import org.joda.convert.FromString;
/*     */ import org.joda.time.base.AbstractInstant;
/*     */ import org.joda.time.chrono.ISOChronology;
/*     */ import org.joda.time.convert.ConverterManager;
/*     */ import org.joda.time.convert.InstantConverter;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Instant
/*     */   extends AbstractInstant
/*     */   implements ReadableInstant, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 3299096530934209741L;
/*     */   private final long iMillis;
/*     */   
/*     */   public static Instant now() {
/*  73 */     return new Instant();
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
/*     */   @FromString
/*     */   public static Instant parse(String paramString) {
/*  87 */     return parse(paramString, ISODateTimeFormat.dateTimeParser());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Instant parse(String paramString, DateTimeFormatter paramDateTimeFormatter) {
/*  98 */     return paramDateTimeFormatter.parseDateTime(paramString).toInstant();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Instant() {
/* 109 */     this.iMillis = DateTimeUtils.currentTimeMillis();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Instant(long paramLong) {
/* 119 */     this.iMillis = paramLong;
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
/*     */   public Instant(Object paramObject) {
/* 133 */     InstantConverter instantConverter = ConverterManager.getInstance().getInstantConverter(paramObject);
/* 134 */     this.iMillis = instantConverter.getInstantMillis(paramObject, (Chronology)ISOChronology.getInstanceUTC());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Instant toInstant() {
/* 144 */     return this;
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
/*     */   public Instant withMillis(long paramLong) {
/* 157 */     return (paramLong == this.iMillis) ? this : new Instant(paramLong);
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
/*     */   public Instant withDurationAdded(long paramLong, int paramInt) {
/* 171 */     if (paramLong == 0L || paramInt == 0) {
/* 172 */       return this;
/*     */     }
/* 174 */     long l = getChronology().add(getMillis(), paramLong, paramInt);
/* 175 */     return withMillis(l);
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
/*     */   public Instant withDurationAdded(ReadableDuration paramReadableDuration, int paramInt) {
/* 189 */     if (paramReadableDuration == null || paramInt == 0) {
/* 190 */       return this;
/*     */     }
/* 192 */     return withDurationAdded(paramReadableDuration.getMillis(), paramInt);
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
/*     */   public Instant plus(long paramLong) {
/* 206 */     return withDurationAdded(paramLong, 1);
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
/*     */   public Instant plus(ReadableDuration paramReadableDuration) {
/* 219 */     return withDurationAdded(paramReadableDuration, 1);
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
/*     */   public Instant minus(long paramLong) {
/* 233 */     return withDurationAdded(paramLong, -1);
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
/*     */   public Instant minus(ReadableDuration paramReadableDuration) {
/* 246 */     return withDurationAdded(paramReadableDuration, -1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getMillis() {
/* 256 */     return this.iMillis;
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
/*     */   public Chronology getChronology() {
/* 268 */     return (Chronology)ISOChronology.getInstanceUTC();
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
/*     */   public DateTime toDateTime() {
/* 288 */     return new DateTime(getMillis(), (Chronology)ISOChronology.getInstance());
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
/*     */   @Deprecated
/*     */   public DateTime toDateTimeISO() {
/* 315 */     return toDateTime();
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
/*     */   public MutableDateTime toMutableDateTime() {
/* 334 */     return new MutableDateTime(getMillis(), (Chronology)ISOChronology.getInstance());
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
/*     */   @Deprecated
/*     */   public MutableDateTime toMutableDateTimeISO() {
/* 361 */     return toMutableDateTime();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/Instant.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */