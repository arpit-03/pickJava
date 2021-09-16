/*     */ package org.joda.time.field;
/*     */ 
/*     */ import org.joda.time.DateTimeField;
/*     */ import org.joda.time.DateTimeFieldType;
/*     */ import org.joda.time.DurationField;
/*     */ import org.joda.time.ReadablePartial;
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
/*     */ public final class ZeroIsMaxDateTimeField
/*     */   extends DecoratedDateTimeField
/*     */ {
/*     */   private static final long serialVersionUID = 961749798233026866L;
/*     */   
/*     */   public ZeroIsMaxDateTimeField(DateTimeField paramDateTimeField, DateTimeFieldType paramDateTimeFieldType) {
/*  46 */     super(paramDateTimeField, paramDateTimeFieldType);
/*  47 */     if (paramDateTimeField.getMinimumValue() != 0) {
/*  48 */       throw new IllegalArgumentException("Wrapped field's minumum value must be zero");
/*     */     }
/*     */   }
/*     */   
/*     */   public int get(long paramLong) {
/*  53 */     int i = getWrappedField().get(paramLong);
/*  54 */     if (i == 0) {
/*  55 */       i = getMaximumValue();
/*     */     }
/*  57 */     return i;
/*     */   }
/*     */   
/*     */   public long add(long paramLong, int paramInt) {
/*  61 */     return getWrappedField().add(paramLong, paramInt);
/*     */   }
/*     */   
/*     */   public long add(long paramLong1, long paramLong2) {
/*  65 */     return getWrappedField().add(paramLong1, paramLong2);
/*     */   }
/*     */   
/*     */   public long addWrapField(long paramLong, int paramInt) {
/*  69 */     return getWrappedField().addWrapField(paramLong, paramInt);
/*     */   }
/*     */   
/*     */   public int[] addWrapField(ReadablePartial paramReadablePartial, int paramInt1, int[] paramArrayOfint, int paramInt2) {
/*  73 */     return getWrappedField().addWrapField(paramReadablePartial, paramInt1, paramArrayOfint, paramInt2);
/*     */   }
/*     */   
/*     */   public int getDifference(long paramLong1, long paramLong2) {
/*  77 */     return getWrappedField().getDifference(paramLong1, paramLong2);
/*     */   }
/*     */   
/*     */   public long getDifferenceAsLong(long paramLong1, long paramLong2) {
/*  81 */     return getWrappedField().getDifferenceAsLong(paramLong1, paramLong2);
/*     */   }
/*     */   
/*     */   public long set(long paramLong, int paramInt) {
/*  85 */     int i = getMaximumValue();
/*  86 */     FieldUtils.verifyValueBounds(this, paramInt, 1, i);
/*  87 */     if (paramInt == i) {
/*  88 */       paramInt = 0;
/*     */     }
/*  90 */     return getWrappedField().set(paramLong, paramInt);
/*     */   }
/*     */   
/*     */   public boolean isLeap(long paramLong) {
/*  94 */     return getWrappedField().isLeap(paramLong);
/*     */   }
/*     */   
/*     */   public int getLeapAmount(long paramLong) {
/*  98 */     return getWrappedField().getLeapAmount(paramLong);
/*     */   }
/*     */   
/*     */   public DurationField getLeapDurationField() {
/* 102 */     return getWrappedField().getLeapDurationField();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMinimumValue() {
/* 111 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMinimumValue(long paramLong) {
/* 120 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMinimumValue(ReadablePartial paramReadablePartial) {
/* 129 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMinimumValue(ReadablePartial paramReadablePartial, int[] paramArrayOfint) {
/* 138 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaximumValue() {
/* 148 */     return getWrappedField().getMaximumValue() + 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaximumValue(long paramLong) {
/* 158 */     return getWrappedField().getMaximumValue(paramLong) + 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaximumValue(ReadablePartial paramReadablePartial) {
/* 168 */     return getWrappedField().getMaximumValue(paramReadablePartial) + 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaximumValue(ReadablePartial paramReadablePartial, int[] paramArrayOfint) {
/* 178 */     return getWrappedField().getMaximumValue(paramReadablePartial, paramArrayOfint) + 1;
/*     */   }
/*     */   
/*     */   public long roundFloor(long paramLong) {
/* 182 */     return getWrappedField().roundFloor(paramLong);
/*     */   }
/*     */   
/*     */   public long roundCeiling(long paramLong) {
/* 186 */     return getWrappedField().roundCeiling(paramLong);
/*     */   }
/*     */   
/*     */   public long roundHalfFloor(long paramLong) {
/* 190 */     return getWrappedField().roundHalfFloor(paramLong);
/*     */   }
/*     */   
/*     */   public long roundHalfCeiling(long paramLong) {
/* 194 */     return getWrappedField().roundHalfCeiling(paramLong);
/*     */   }
/*     */   
/*     */   public long roundHalfEven(long paramLong) {
/* 198 */     return getWrappedField().roundHalfEven(paramLong);
/*     */   }
/*     */   
/*     */   public long remainder(long paramLong) {
/* 202 */     return getWrappedField().remainder(paramLong);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/field/ZeroIsMaxDateTimeField.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */