/*    */ package org.joda.time.convert;
/*    */ 
/*    */ import org.joda.time.Chronology;
/*    */ import org.joda.time.DateTimeUtils;
/*    */ import org.joda.time.ReadWritableInterval;
/*    */ import org.joda.time.ReadWritablePeriod;
/*    */ import org.joda.time.ReadablePeriod;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class NullConverter
/*    */   extends AbstractConverter
/*    */   implements InstantConverter, PartialConverter, DurationConverter, PeriodConverter, IntervalConverter
/*    */ {
/* 39 */   static final NullConverter INSTANCE = new NullConverter();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long getDurationMillis(Object paramObject) {
/* 56 */     return 0L;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setInto(ReadWritablePeriod paramReadWritablePeriod, Object paramObject, Chronology paramChronology) {
/* 69 */     paramReadWritablePeriod.setPeriod((ReadablePeriod)null);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setInto(ReadWritableInterval paramReadWritableInterval, Object paramObject, Chronology paramChronology) {
/* 83 */     paramReadWritableInterval.setChronology(paramChronology);
/* 84 */     long l = DateTimeUtils.currentTimeMillis();
/* 85 */     paramReadWritableInterval.setInterval(l, l);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Class<?> getSupportedType() {
/* 95 */     return null;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/convert/NullConverter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */