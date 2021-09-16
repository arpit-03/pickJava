/*     */ package org.joda.time.chrono;
/*     */ 
/*     */ import org.joda.time.DateTimeFieldType;
/*     */ import org.joda.time.DurationField;
/*     */ import org.joda.time.ReadablePartial;
/*     */ import org.joda.time.field.PreciseDurationDateTimeField;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class BasicWeekOfWeekyearDateTimeField
/*     */   extends PreciseDurationDateTimeField
/*     */ {
/*     */   private static final long serialVersionUID = -1587436826395135328L;
/*     */   private final BasicChronology iChronology;
/*     */   
/*     */   BasicWeekOfWeekyearDateTimeField(BasicChronology paramBasicChronology, DurationField paramDurationField) {
/*  43 */     super(DateTimeFieldType.weekOfWeekyear(), paramDurationField);
/*  44 */     this.iChronology = paramBasicChronology;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int get(long paramLong) {
/*  55 */     return this.iChronology.getWeekOfWeekyear(paramLong);
/*     */   }
/*     */   
/*     */   public DurationField getRangeDurationField() {
/*  59 */     return this.iChronology.weekyears();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long roundFloor(long paramLong) {
/*  66 */     return super.roundFloor(paramLong + 259200000L) - 259200000L;
/*     */   }
/*     */ 
/*     */   
/*     */   public long roundCeiling(long paramLong) {
/*  71 */     return super.roundCeiling(paramLong + 259200000L) - 259200000L;
/*     */   }
/*     */ 
/*     */   
/*     */   public long remainder(long paramLong) {
/*  76 */     return super.remainder(paramLong + 259200000L);
/*     */   }
/*     */   
/*     */   public int getMinimumValue() {
/*  80 */     return 1;
/*     */   }
/*     */   
/*     */   public int getMaximumValue() {
/*  84 */     return 53;
/*     */   }
/*     */   
/*     */   public int getMaximumValue(long paramLong) {
/*  88 */     int i = this.iChronology.getWeekyear(paramLong);
/*  89 */     return this.iChronology.getWeeksInYear(i);
/*     */   }
/*     */   
/*     */   public int getMaximumValue(ReadablePartial paramReadablePartial) {
/*  93 */     if (paramReadablePartial.isSupported(DateTimeFieldType.weekyear())) {
/*  94 */       int i = paramReadablePartial.get(DateTimeFieldType.weekyear());
/*  95 */       return this.iChronology.getWeeksInYear(i);
/*     */     } 
/*  97 */     return 53;
/*     */   }
/*     */   
/*     */   public int getMaximumValue(ReadablePartial paramReadablePartial, int[] paramArrayOfint) {
/* 101 */     int i = paramReadablePartial.size();
/* 102 */     for (byte b = 0; b < i; b++) {
/* 103 */       if (paramReadablePartial.getFieldType(b) == DateTimeFieldType.weekyear()) {
/* 104 */         int j = paramArrayOfint[b];
/* 105 */         return this.iChronology.getWeeksInYear(j);
/*     */       } 
/*     */     } 
/* 108 */     return 53;
/*     */   }
/*     */   
/*     */   protected int getMaximumValueForSet(long paramLong, int paramInt) {
/* 112 */     return (paramInt > 52) ? getMaximumValue(paramLong) : 52;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Object readResolve() {
/* 119 */     return this.iChronology.weekOfWeekyear();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/chrono/BasicWeekOfWeekyearDateTimeField.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */