/*    */ package org.joda.time.convert;
/*    */ 
/*    */ import org.joda.time.Chronology;
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
/*    */ class LongConverter
/*    */   extends AbstractConverter
/*    */   implements InstantConverter, PartialConverter, DurationConverter
/*    */ {
/* 34 */   static final LongConverter INSTANCE = new LongConverter();
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
/*    */   public long getInstantMillis(Object paramObject, Chronology paramChronology) {
/* 54 */     return ((Long)paramObject).longValue();
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
/*    */   public long getDurationMillis(Object paramObject) {
/* 67 */     return ((Long)paramObject).longValue();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Class<?> getSupportedType() {
/* 77 */     return Long.class;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/convert/LongConverter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */