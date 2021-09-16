/*     */ package org.joda.time.field;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import org.joda.time.DurationField;
/*     */ import org.joda.time.DurationFieldType;
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
/*     */ public abstract class BaseDurationField
/*     */   extends DurationField
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -2554245107589433218L;
/*     */   private final DurationFieldType iType;
/*     */   
/*     */   protected BaseDurationField(DurationFieldType paramDurationFieldType) {
/*  48 */     if (paramDurationFieldType == null) {
/*  49 */       throw new IllegalArgumentException("The type must not be null");
/*     */     }
/*  51 */     this.iType = paramDurationFieldType;
/*     */   }
/*     */   
/*     */   public final DurationFieldType getType() {
/*  55 */     return this.iType;
/*     */   }
/*     */   
/*     */   public final String getName() {
/*  59 */     return this.iType.getName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean isSupported() {
/*  66 */     return true;
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
/*     */   public int getValue(long paramLong) {
/*  79 */     return FieldUtils.safeToInt(getValueAsLong(paramLong));
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
/*     */   public long getValueAsLong(long paramLong) {
/*  91 */     return paramLong / getUnitMillis();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getValue(long paramLong1, long paramLong2) {
/* 111 */     return FieldUtils.safeToInt(getValueAsLong(paramLong1, paramLong2));
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
/*     */   public long getMillis(int paramInt) {
/* 123 */     return paramInt * getUnitMillis();
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
/*     */   public long getMillis(long paramLong) {
/* 135 */     return FieldUtils.safeMultiply(paramLong, getUnitMillis());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDifference(long paramLong1, long paramLong2) {
/* 141 */     return FieldUtils.safeToInt(getDifferenceAsLong(paramLong1, paramLong2));
/*     */   }
/*     */ 
/*     */   
/*     */   public int compareTo(DurationField paramDurationField) {
/* 146 */     long l1 = paramDurationField.getUnitMillis();
/* 147 */     long l2 = getUnitMillis();
/*     */     
/* 149 */     if (l2 == l1) {
/* 150 */       return 0;
/*     */     }
/* 152 */     if (l2 < l1) {
/* 153 */       return -1;
/*     */     }
/* 155 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 165 */     return "DurationField[" + getName() + ']';
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/field/BaseDurationField.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */