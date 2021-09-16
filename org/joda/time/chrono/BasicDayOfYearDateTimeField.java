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
/*     */ final class BasicDayOfYearDateTimeField
/*     */   extends PreciseDurationDateTimeField
/*     */ {
/*     */   private static final long serialVersionUID = -6821236822336841037L;
/*     */   private final BasicChronology iChronology;
/*     */   
/*     */   BasicDayOfYearDateTimeField(BasicChronology paramBasicChronology, DurationField paramDurationField) {
/*  42 */     super(DateTimeFieldType.dayOfYear(), paramDurationField);
/*  43 */     this.iChronology = paramBasicChronology;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int get(long paramLong) {
/*  53 */     return this.iChronology.getDayOfYear(paramLong);
/*     */   }
/*     */   
/*     */   public DurationField getRangeDurationField() {
/*  57 */     return this.iChronology.years();
/*     */   }
/*     */   
/*     */   public int getMinimumValue() {
/*  61 */     return 1;
/*     */   }
/*     */   
/*     */   public int getMaximumValue() {
/*  65 */     return this.iChronology.getDaysInYearMax();
/*     */   }
/*     */   
/*     */   public int getMaximumValue(long paramLong) {
/*  69 */     int i = this.iChronology.getYear(paramLong);
/*  70 */     return this.iChronology.getDaysInYear(i);
/*     */   }
/*     */   
/*     */   public int getMaximumValue(ReadablePartial paramReadablePartial) {
/*  74 */     if (paramReadablePartial.isSupported(DateTimeFieldType.year())) {
/*  75 */       int i = paramReadablePartial.get(DateTimeFieldType.year());
/*  76 */       return this.iChronology.getDaysInYear(i);
/*     */     } 
/*  78 */     return this.iChronology.getDaysInYearMax();
/*     */   }
/*     */   
/*     */   public int getMaximumValue(ReadablePartial paramReadablePartial, int[] paramArrayOfint) {
/*  82 */     int i = paramReadablePartial.size();
/*  83 */     for (byte b = 0; b < i; b++) {
/*  84 */       if (paramReadablePartial.getFieldType(b) == DateTimeFieldType.year()) {
/*  85 */         int j = paramArrayOfint[b];
/*  86 */         return this.iChronology.getDaysInYear(j);
/*     */       } 
/*     */     } 
/*  89 */     return this.iChronology.getDaysInYearMax();
/*     */   }
/*     */   
/*     */   protected int getMaximumValueForSet(long paramLong, int paramInt) {
/*  93 */     int i = this.iChronology.getDaysInYearMax() - 1;
/*  94 */     return (paramInt > i || paramInt < 1) ? getMaximumValue(paramLong) : i;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isLeap(long paramLong) {
/*  99 */     return this.iChronology.isLeapDay(paramLong);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Object readResolve() {
/* 106 */     return this.iChronology.dayOfYear();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/chrono/BasicDayOfYearDateTimeField.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */