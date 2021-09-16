/*     */ package org.joda.time.field;
/*     */ 
/*     */ import org.joda.time.DateTimeField;
/*     */ import org.joda.time.DateTimeFieldType;
/*     */ import org.joda.time.DurationField;
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
/*     */ public class DividedDateTimeField
/*     */   extends DecoratedDateTimeField
/*     */ {
/*     */   private static final long serialVersionUID = 8318475124230605365L;
/*     */   final int iDivisor;
/*     */   final DurationField iDurationField;
/*     */   final DurationField iRangeDurationField;
/*     */   private final int iMin;
/*     */   private final int iMax;
/*     */   
/*     */   public DividedDateTimeField(DateTimeField paramDateTimeField, DateTimeFieldType paramDateTimeFieldType, int paramInt) {
/*  58 */     this(paramDateTimeField, paramDateTimeField.getRangeDurationField(), paramDateTimeFieldType, paramInt);
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
/*     */   public DividedDateTimeField(DateTimeField paramDateTimeField, DurationField paramDurationField, DateTimeFieldType paramDateTimeFieldType, int paramInt) {
/*  72 */     super(paramDateTimeField, paramDateTimeFieldType);
/*  73 */     if (paramInt < 2) {
/*  74 */       throw new IllegalArgumentException("The divisor must be at least 2");
/*     */     }
/*  76 */     DurationField durationField = paramDateTimeField.getDurationField();
/*  77 */     if (durationField == null) {
/*  78 */       this.iDurationField = null;
/*     */     } else {
/*  80 */       this.iDurationField = new ScaledDurationField(durationField, paramDateTimeFieldType.getDurationType(), paramInt);
/*     */     } 
/*     */     
/*  83 */     this.iRangeDurationField = paramDurationField;
/*  84 */     this.iDivisor = paramInt;
/*  85 */     int i = paramDateTimeField.getMinimumValue();
/*  86 */     int j = (i >= 0) ? (i / paramInt) : ((i + 1) / paramInt - 1);
/*  87 */     int k = paramDateTimeField.getMaximumValue();
/*  88 */     int m = (k >= 0) ? (k / paramInt) : ((k + 1) / paramInt - 1);
/*  89 */     this.iMin = j;
/*  90 */     this.iMax = m;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DividedDateTimeField(RemainderDateTimeField paramRemainderDateTimeField, DateTimeFieldType paramDateTimeFieldType) {
/* 101 */     this(paramRemainderDateTimeField, (DurationField)null, paramDateTimeFieldType);
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
/*     */   public DividedDateTimeField(RemainderDateTimeField paramRemainderDateTimeField, DurationField paramDurationField, DateTimeFieldType paramDateTimeFieldType) {
/* 113 */     super(paramRemainderDateTimeField.getWrappedField(), paramDateTimeFieldType);
/* 114 */     int i = this.iDivisor = paramRemainderDateTimeField.iDivisor;
/* 115 */     this.iDurationField = paramRemainderDateTimeField.iRangeField;
/* 116 */     this.iRangeDurationField = paramDurationField;
/* 117 */     DateTimeField dateTimeField = getWrappedField();
/* 118 */     int j = dateTimeField.getMinimumValue();
/* 119 */     int k = (j >= 0) ? (j / i) : ((j + 1) / i - 1);
/* 120 */     int m = dateTimeField.getMaximumValue();
/* 121 */     int n = (m >= 0) ? (m / i) : ((m + 1) / i - 1);
/* 122 */     this.iMin = k;
/* 123 */     this.iMax = n;
/*     */   }
/*     */ 
/*     */   
/*     */   public DurationField getRangeDurationField() {
/* 128 */     if (this.iRangeDurationField != null) {
/* 129 */       return this.iRangeDurationField;
/*     */     }
/* 131 */     return super.getRangeDurationField();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int get(long paramLong) {
/* 141 */     int i = getWrappedField().get(paramLong);
/* 142 */     if (i >= 0) {
/* 143 */       return i / this.iDivisor;
/*     */     }
/* 145 */     return (i + 1) / this.iDivisor - 1;
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
/*     */   public long add(long paramLong, int paramInt) {
/* 158 */     return getWrappedField().add(paramLong, paramInt * this.iDivisor);
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
/*     */   public long add(long paramLong1, long paramLong2) {
/* 170 */     return getWrappedField().add(paramLong1, paramLong2 * this.iDivisor);
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
/*     */   public long addWrapField(long paramLong, int paramInt) {
/* 182 */     return set(paramLong, FieldUtils.getWrappedValue(get(paramLong), paramInt, this.iMin, this.iMax));
/*     */   }
/*     */   
/*     */   public int getDifference(long paramLong1, long paramLong2) {
/* 186 */     return getWrappedField().getDifference(paramLong1, paramLong2) / this.iDivisor;
/*     */   }
/*     */   
/*     */   public long getDifferenceAsLong(long paramLong1, long paramLong2) {
/* 190 */     return getWrappedField().getDifferenceAsLong(paramLong1, paramLong2) / this.iDivisor;
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
/*     */   public long set(long paramLong, int paramInt) {
/* 202 */     FieldUtils.verifyValueBounds(this, paramInt, this.iMin, this.iMax);
/* 203 */     int i = getRemainder(getWrappedField().get(paramLong));
/* 204 */     return getWrappedField().set(paramLong, paramInt * this.iDivisor + i);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DurationField getDurationField() {
/* 211 */     return this.iDurationField;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMinimumValue() {
/* 220 */     return this.iMin;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaximumValue() {
/* 229 */     return this.iMax;
/*     */   }
/*     */   
/*     */   public long roundFloor(long paramLong) {
/* 233 */     DateTimeField dateTimeField = getWrappedField();
/* 234 */     return dateTimeField.roundFloor(dateTimeField.set(paramLong, get(paramLong) * this.iDivisor));
/*     */   }
/*     */   
/*     */   public long remainder(long paramLong) {
/* 238 */     return set(paramLong, get(getWrappedField().remainder(paramLong)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDivisor() {
/* 247 */     return this.iDivisor;
/*     */   }
/*     */   
/*     */   private int getRemainder(int paramInt) {
/* 251 */     if (paramInt >= 0) {
/* 252 */       return paramInt % this.iDivisor;
/*     */     }
/* 254 */     return this.iDivisor - 1 + (paramInt + 1) % this.iDivisor;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/field/DividedDateTimeField.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */