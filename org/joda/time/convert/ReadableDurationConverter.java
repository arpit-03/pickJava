/*    */ package org.joda.time.convert;
/*    */ 
/*    */ import org.joda.time.Chronology;
/*    */ import org.joda.time.DateTimeUtils;
/*    */ import org.joda.time.ReadWritablePeriod;
/*    */ import org.joda.time.ReadableDuration;
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
/*    */ class ReadableDurationConverter
/*    */   extends AbstractConverter
/*    */   implements DurationConverter, PeriodConverter
/*    */ {
/* 36 */   static final ReadableDurationConverter INSTANCE = new ReadableDurationConverter();
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
/*    */   public long getDurationMillis(Object paramObject) {
/* 56 */     return ((ReadableDuration)paramObject).getMillis();
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
/*    */ 
/*    */   
/*    */   public void setInto(ReadWritablePeriod paramReadWritablePeriod, Object paramObject, Chronology paramChronology) {
/* 72 */     ReadableDuration readableDuration = (ReadableDuration)paramObject;
/* 73 */     paramChronology = DateTimeUtils.getChronology(paramChronology);
/* 74 */     long l = readableDuration.getMillis();
/* 75 */     int[] arrayOfInt = paramChronology.get((ReadablePeriod)paramReadWritablePeriod, l);
/* 76 */     for (byte b = 0; b < arrayOfInt.length; b++) {
/* 77 */       paramReadWritablePeriod.setValue(b, arrayOfInt[b]);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Class<?> getSupportedType() {
/* 88 */     return ReadableDuration.class;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/convert/ReadableDurationConverter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */