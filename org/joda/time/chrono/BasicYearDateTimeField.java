/*     */ package org.joda.time.chrono;
/*     */ 
/*     */ import org.joda.time.DateTimeField;
/*     */ import org.joda.time.DateTimeFieldType;
/*     */ import org.joda.time.DurationField;
/*     */ import org.joda.time.field.FieldUtils;
/*     */ import org.joda.time.field.ImpreciseDateTimeField;
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
/*     */ class BasicYearDateTimeField
/*     */   extends ImpreciseDateTimeField
/*     */ {
/*     */   private static final long serialVersionUID = -98628754872287L;
/*     */   protected final BasicChronology iChronology;
/*     */   
/*     */   BasicYearDateTimeField(BasicChronology paramBasicChronology) {
/*  46 */     super(DateTimeFieldType.year(), paramBasicChronology.getAverageMillisPerYear());
/*  47 */     this.iChronology = paramBasicChronology;
/*     */   }
/*     */   
/*     */   public boolean isLenient() {
/*  51 */     return false;
/*     */   }
/*     */   
/*     */   public int get(long paramLong) {
/*  55 */     return this.iChronology.getYear(paramLong);
/*     */   }
/*     */   
/*     */   public long add(long paramLong, int paramInt) {
/*  59 */     if (paramInt == 0) {
/*  60 */       return paramLong;
/*     */     }
/*  62 */     int i = get(paramLong);
/*  63 */     int j = FieldUtils.safeAdd(i, paramInt);
/*  64 */     return set(paramLong, j);
/*     */   }
/*     */   
/*     */   public long add(long paramLong1, long paramLong2) {
/*  68 */     return add(paramLong1, FieldUtils.safeToInt(paramLong2));
/*     */   }
/*     */   
/*     */   public long addWrapField(long paramLong, int paramInt) {
/*  72 */     if (paramInt == 0) {
/*  73 */       return paramLong;
/*     */     }
/*     */     
/*  76 */     int i = this.iChronology.getYear(paramLong);
/*  77 */     int j = FieldUtils.getWrappedValue(i, paramInt, this.iChronology.getMinYear(), this.iChronology.getMaxYear());
/*     */     
/*  79 */     return set(paramLong, j);
/*     */   }
/*     */   
/*     */   public long set(long paramLong, int paramInt) {
/*  83 */     FieldUtils.verifyValueBounds((DateTimeField)this, paramInt, this.iChronology.getMinYear(), this.iChronology.getMaxYear());
/*     */     
/*  85 */     return this.iChronology.setYear(paramLong, paramInt);
/*     */   }
/*     */ 
/*     */   
/*     */   public long setExtended(long paramLong, int paramInt) {
/*  90 */     FieldUtils.verifyValueBounds((DateTimeField)this, paramInt, this.iChronology.getMinYear() - 1, this.iChronology.getMaxYear() + 1);
/*     */     
/*  92 */     return this.iChronology.setYear(paramLong, paramInt);
/*     */   }
/*     */   
/*     */   public long getDifferenceAsLong(long paramLong1, long paramLong2) {
/*  96 */     if (paramLong1 < paramLong2) {
/*  97 */       return -this.iChronology.getYearDifference(paramLong2, paramLong1);
/*     */     }
/*  99 */     return this.iChronology.getYearDifference(paramLong1, paramLong2);
/*     */   }
/*     */   
/*     */   public DurationField getRangeDurationField() {
/* 103 */     return null;
/*     */   }
/*     */   
/*     */   public boolean isLeap(long paramLong) {
/* 107 */     return this.iChronology.isLeapYear(get(paramLong));
/*     */   }
/*     */   
/*     */   public int getLeapAmount(long paramLong) {
/* 111 */     if (this.iChronology.isLeapYear(get(paramLong))) {
/* 112 */       return 1;
/*     */     }
/* 114 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public DurationField getLeapDurationField() {
/* 119 */     return this.iChronology.days();
/*     */   }
/*     */   
/*     */   public int getMinimumValue() {
/* 123 */     return this.iChronology.getMinYear();
/*     */   }
/*     */   
/*     */   public int getMaximumValue() {
/* 127 */     return this.iChronology.getMaxYear();
/*     */   }
/*     */   
/*     */   public long roundFloor(long paramLong) {
/* 131 */     return this.iChronology.getYearMillis(get(paramLong));
/*     */   }
/*     */   
/*     */   public long roundCeiling(long paramLong) {
/* 135 */     int i = get(paramLong);
/* 136 */     long l = this.iChronology.getYearMillis(i);
/* 137 */     if (paramLong != l)
/*     */     {
/* 139 */       paramLong = this.iChronology.getYearMillis(i + 1);
/*     */     }
/* 141 */     return paramLong;
/*     */   }
/*     */   
/*     */   public long remainder(long paramLong) {
/* 145 */     return paramLong - roundFloor(paramLong);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Object readResolve() {
/* 152 */     return this.iChronology.year();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/chrono/BasicYearDateTimeField.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */