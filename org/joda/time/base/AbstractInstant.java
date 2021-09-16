/*     */ package org.joda.time.base;
/*     */ 
/*     */ import java.util.Date;
/*     */ import org.joda.convert.ToString;
/*     */ import org.joda.time.Chronology;
/*     */ import org.joda.time.DateTime;
/*     */ import org.joda.time.DateTimeField;
/*     */ import org.joda.time.DateTimeFieldType;
/*     */ import org.joda.time.DateTimeUtils;
/*     */ import org.joda.time.DateTimeZone;
/*     */ import org.joda.time.Instant;
/*     */ import org.joda.time.MutableDateTime;
/*     */ import org.joda.time.ReadableInstant;
/*     */ import org.joda.time.chrono.ISOChronology;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractInstant
/*     */   implements ReadableInstant
/*     */ {
/*     */   public DateTimeZone getZone() {
/*  71 */     return getChronology().getZone();
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
/*     */   public int get(DateTimeFieldType paramDateTimeFieldType) {
/*  89 */     if (paramDateTimeFieldType == null) {
/*  90 */       throw new IllegalArgumentException("The DateTimeFieldType must not be null");
/*     */     }
/*  92 */     return paramDateTimeFieldType.getField(getChronology()).get(getMillis());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSupported(DateTimeFieldType paramDateTimeFieldType) {
/* 103 */     if (paramDateTimeFieldType == null) {
/* 104 */       return false;
/*     */     }
/* 106 */     return paramDateTimeFieldType.getField(getChronology()).isSupported();
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
/*     */   public int get(DateTimeField paramDateTimeField) {
/* 124 */     if (paramDateTimeField == null) {
/* 125 */       throw new IllegalArgumentException("The DateTimeField must not be null");
/*     */     }
/* 127 */     return paramDateTimeField.get(getMillis());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Instant toInstant() {
/* 137 */     return new Instant(getMillis());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DateTime toDateTime() {
/* 146 */     return new DateTime(getMillis(), getZone());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DateTime toDateTimeISO() {
/* 155 */     return new DateTime(getMillis(), (Chronology)ISOChronology.getInstance(getZone()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DateTime toDateTime(DateTimeZone paramDateTimeZone) {
/* 165 */     Chronology chronology = DateTimeUtils.getChronology(getChronology());
/* 166 */     chronology = chronology.withZone(paramDateTimeZone);
/* 167 */     return new DateTime(getMillis(), chronology);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DateTime toDateTime(Chronology paramChronology) {
/* 177 */     return new DateTime(getMillis(), paramChronology);
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
/*     */   public MutableDateTime toMutableDateTime() {
/* 191 */     return new MutableDateTime(getMillis(), getZone());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MutableDateTime toMutableDateTimeISO() {
/* 200 */     return new MutableDateTime(getMillis(), (Chronology)ISOChronology.getInstance(getZone()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MutableDateTime toMutableDateTime(DateTimeZone paramDateTimeZone) {
/* 210 */     Chronology chronology = DateTimeUtils.getChronology(getChronology());
/* 211 */     chronology = chronology.withZone(paramDateTimeZone);
/* 212 */     return new MutableDateTime(getMillis(), chronology);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MutableDateTime toMutableDateTime(Chronology paramChronology) {
/* 222 */     return new MutableDateTime(getMillis(), paramChronology);
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
/*     */   public Date toDate() {
/* 235 */     return new Date(getMillis());
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
/*     */   public boolean equals(Object paramObject) {
/* 259 */     if (this == paramObject) {
/* 260 */       return true;
/*     */     }
/* 262 */     if (!(paramObject instanceof ReadableInstant)) {
/* 263 */       return false;
/*     */     }
/* 265 */     ReadableInstant readableInstant = (ReadableInstant)paramObject;
/* 266 */     return (getMillis() == readableInstant.getMillis() && FieldUtils.equals(getChronology(), readableInstant.getChronology()));
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
/* 278 */     return (int)(getMillis() ^ getMillis() >>> 32L) + getChronology().hashCode();
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
/*     */   public int compareTo(ReadableInstant paramReadableInstant) {
/* 296 */     if (this == paramReadableInstant) {
/* 297 */       return 0;
/*     */     }
/*     */     
/* 300 */     long l1 = paramReadableInstant.getMillis();
/* 301 */     long l2 = getMillis();
/*     */ 
/*     */     
/* 304 */     if (l2 == l1) {
/* 305 */       return 0;
/*     */     }
/* 307 */     if (l2 < l1) {
/* 308 */       return -1;
/*     */     }
/* 310 */     return 1;
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
/*     */   public boolean isAfter(long paramLong) {
/* 323 */     return (getMillis() > paramLong);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAfterNow() {
/* 333 */     return isAfter(DateTimeUtils.currentTimeMillis());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAfter(ReadableInstant paramReadableInstant) {
/* 344 */     long l = DateTimeUtils.getInstantMillis(paramReadableInstant);
/* 345 */     return isAfter(l);
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
/*     */   public boolean isBefore(long paramLong) {
/* 357 */     return (getMillis() < paramLong);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBeforeNow() {
/* 367 */     return isBefore(DateTimeUtils.currentTimeMillis());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBefore(ReadableInstant paramReadableInstant) {
/* 378 */     long l = DateTimeUtils.getInstantMillis(paramReadableInstant);
/* 379 */     return isBefore(l);
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
/*     */   public boolean isEqual(long paramLong) {
/* 391 */     return (getMillis() == paramLong);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEqualNow() {
/* 401 */     return isEqual(DateTimeUtils.currentTimeMillis());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEqual(ReadableInstant paramReadableInstant) {
/* 412 */     long l = DateTimeUtils.getInstantMillis(paramReadableInstant);
/* 413 */     return isEqual(l);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ToString
/*     */   public String toString() {
/* 424 */     return ISODateTimeFormat.dateTime().print(this);
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
/*     */   public String toString(DateTimeFormatter paramDateTimeFormatter) {
/* 436 */     if (paramDateTimeFormatter == null) {
/* 437 */       return toString();
/*     */     }
/* 439 */     return paramDateTimeFormatter.print(this);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/base/AbstractInstant.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */