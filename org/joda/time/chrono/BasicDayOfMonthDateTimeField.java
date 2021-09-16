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
/*     */ final class BasicDayOfMonthDateTimeField
/*     */   extends PreciseDurationDateTimeField
/*     */ {
/*     */   private static final long serialVersionUID = -4677223814028011723L;
/*     */   private final BasicChronology iChronology;
/*     */   
/*     */   BasicDayOfMonthDateTimeField(BasicChronology paramBasicChronology, DurationField paramDurationField) {
/*  42 */     super(DateTimeFieldType.dayOfMonth(), paramDurationField);
/*  43 */     this.iChronology = paramBasicChronology;
/*     */   }
/*     */ 
/*     */   
/*     */   public int get(long paramLong) {
/*  48 */     return this.iChronology.getDayOfMonth(paramLong);
/*     */   }
/*     */   
/*     */   public DurationField getRangeDurationField() {
/*  52 */     return this.iChronology.months();
/*     */   }
/*     */   
/*     */   public int getMinimumValue() {
/*  56 */     return 1;
/*     */   }
/*     */   
/*     */   public int getMaximumValue() {
/*  60 */     return this.iChronology.getDaysInMonthMax();
/*     */   }
/*     */   
/*     */   public int getMaximumValue(long paramLong) {
/*  64 */     return this.iChronology.getDaysInMonthMax(paramLong);
/*     */   }
/*     */   
/*     */   public int getMaximumValue(ReadablePartial paramReadablePartial) {
/*  68 */     if (paramReadablePartial.isSupported(DateTimeFieldType.monthOfYear())) {
/*  69 */       int i = paramReadablePartial.get(DateTimeFieldType.monthOfYear());
/*  70 */       if (paramReadablePartial.isSupported(DateTimeFieldType.year())) {
/*  71 */         int j = paramReadablePartial.get(DateTimeFieldType.year());
/*  72 */         return this.iChronology.getDaysInYearMonth(j, i);
/*     */       } 
/*  74 */       return this.iChronology.getDaysInMonthMax(i);
/*     */     } 
/*  76 */     return getMaximumValue();
/*     */   }
/*     */   
/*     */   public int getMaximumValue(ReadablePartial paramReadablePartial, int[] paramArrayOfint) {
/*  80 */     int i = paramReadablePartial.size();
/*  81 */     for (byte b = 0; b < i; b++) {
/*  82 */       if (paramReadablePartial.getFieldType(b) == DateTimeFieldType.monthOfYear()) {
/*  83 */         int j = paramArrayOfint[b];
/*  84 */         for (byte b1 = 0; b1 < i; b1++) {
/*  85 */           if (paramReadablePartial.getFieldType(b1) == DateTimeFieldType.year()) {
/*  86 */             int k = paramArrayOfint[b1];
/*  87 */             return this.iChronology.getDaysInYearMonth(k, j);
/*     */           } 
/*     */         } 
/*  90 */         return this.iChronology.getDaysInMonthMax(j);
/*     */       } 
/*     */     } 
/*  93 */     return getMaximumValue();
/*     */   }
/*     */   
/*     */   protected int getMaximumValueForSet(long paramLong, int paramInt) {
/*  97 */     return this.iChronology.getDaysInMonthMaxForSet(paramLong, paramInt);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isLeap(long paramLong) {
/* 102 */     return this.iChronology.isLeapDay(paramLong);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Object readResolve() {
/* 109 */     return this.iChronology.dayOfMonth();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/chrono/BasicDayOfMonthDateTimeField.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */