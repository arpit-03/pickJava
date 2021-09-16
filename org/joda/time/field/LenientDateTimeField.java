/*    */ package org.joda.time.field;
/*    */ 
/*    */ import org.joda.time.Chronology;
/*    */ import org.joda.time.DateTimeField;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LenientDateTimeField
/*    */   extends DelegatedDateTimeField
/*    */ {
/*    */   private static final long serialVersionUID = 8714085824173290599L;
/*    */   private final Chronology iBase;
/*    */   
/*    */   public static DateTimeField getInstance(DateTimeField paramDateTimeField, Chronology paramChronology) {
/* 44 */     if (paramDateTimeField == null) {
/* 45 */       return null;
/*    */     }
/* 47 */     if (paramDateTimeField instanceof StrictDateTimeField) {
/* 48 */       paramDateTimeField = ((StrictDateTimeField)paramDateTimeField).getWrappedField();
/*    */     }
/* 50 */     if (paramDateTimeField.isLenient()) {
/* 51 */       return paramDateTimeField;
/*    */     }
/* 53 */     return new LenientDateTimeField(paramDateTimeField, paramChronology);
/*    */   }
/*    */   
/*    */   protected LenientDateTimeField(DateTimeField paramDateTimeField, Chronology paramChronology) {
/* 57 */     super(paramDateTimeField);
/* 58 */     this.iBase = paramChronology;
/*    */   }
/*    */   
/*    */   public final boolean isLenient() {
/* 62 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long set(long paramLong, int paramInt) {
/* 72 */     long l1 = this.iBase.getZone().convertUTCToLocal(paramLong);
/* 73 */     long l2 = FieldUtils.safeSubtract(paramInt, get(paramLong));
/* 74 */     l1 = getType().getField(this.iBase.withUTC()).add(l1, l2);
/* 75 */     return this.iBase.getZone().convertLocalToUTC(l1, false, paramLong);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/field/LenientDateTimeField.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */