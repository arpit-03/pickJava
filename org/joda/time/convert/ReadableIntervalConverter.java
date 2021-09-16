/*     */ package org.joda.time.convert;
/*     */ 
/*     */ import org.joda.time.Chronology;
/*     */ import org.joda.time.DateTimeUtils;
/*     */ import org.joda.time.ReadWritableInterval;
/*     */ import org.joda.time.ReadWritablePeriod;
/*     */ import org.joda.time.ReadableInterval;
/*     */ import org.joda.time.ReadablePeriod;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class ReadableIntervalConverter
/*     */   extends AbstractConverter
/*     */   implements IntervalConverter, DurationConverter, PeriodConverter
/*     */ {
/*  36 */   static final ReadableIntervalConverter INSTANCE = new ReadableIntervalConverter();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getDurationMillis(Object paramObject) {
/*  52 */     return ((ReadableInterval)paramObject).toDurationMillis();
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
/*     */   public void setInto(ReadWritablePeriod paramReadWritablePeriod, Object paramObject, Chronology paramChronology) {
/*  64 */     ReadableInterval readableInterval = (ReadableInterval)paramObject;
/*  65 */     paramChronology = (paramChronology != null) ? paramChronology : DateTimeUtils.getIntervalChronology(readableInterval);
/*  66 */     long l1 = readableInterval.getStartMillis();
/*  67 */     long l2 = readableInterval.getEndMillis();
/*  68 */     int[] arrayOfInt = paramChronology.get((ReadablePeriod)paramReadWritablePeriod, l1, l2);
/*  69 */     for (byte b = 0; b < arrayOfInt.length; b++) {
/*  70 */       paramReadWritablePeriod.setValue(b, arrayOfInt[b]);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isReadableInterval(Object paramObject, Chronology paramChronology) {
/*  86 */     return true;
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
/*     */   public void setInto(ReadWritableInterval paramReadWritableInterval, Object paramObject, Chronology paramChronology) {
/*  99 */     ReadableInterval readableInterval = (ReadableInterval)paramObject;
/* 100 */     paramReadWritableInterval.setInterval(readableInterval);
/* 101 */     if (paramChronology != null) {
/* 102 */       paramReadWritableInterval.setChronology(paramChronology);
/*     */     } else {
/* 104 */       paramReadWritableInterval.setChronology(readableInterval.getChronology());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Class<?> getSupportedType() {
/* 113 */     return ReadableInterval.class;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/convert/ReadableIntervalConverter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */