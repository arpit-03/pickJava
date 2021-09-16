/*    */ package org.joda.time.convert;
/*    */ 
/*    */ import java.util.Date;
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
/*    */ final class DateConverter
/*    */   extends AbstractConverter
/*    */   implements InstantConverter, PartialConverter
/*    */ {
/* 35 */   static final DateConverter INSTANCE = new DateConverter();
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
/* 55 */     Date date = (Date)paramObject;
/* 56 */     return date.getTime();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Class<?> getSupportedType() {
/* 66 */     return Date.class;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/convert/DateConverter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */