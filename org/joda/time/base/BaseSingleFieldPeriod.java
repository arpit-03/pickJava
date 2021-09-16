/*     */ package org.joda.time.base;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import org.joda.time.Chronology;
/*     */ import org.joda.time.DateTimeUtils;
/*     */ import org.joda.time.DurationField;
/*     */ import org.joda.time.DurationFieldType;
/*     */ import org.joda.time.MutablePeriod;
/*     */ import org.joda.time.Period;
/*     */ import org.joda.time.PeriodType;
/*     */ import org.joda.time.ReadableInstant;
/*     */ import org.joda.time.ReadablePartial;
/*     */ import org.joda.time.ReadablePeriod;
/*     */ import org.joda.time.chrono.ISOChronology;
/*     */ import org.joda.time.field.FieldUtils;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class BaseSingleFieldPeriod
/*     */   implements ReadablePeriod, Comparable<BaseSingleFieldPeriod>, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 9386874258972L;
/*     */   private static final long START_1972 = 63072000000L;
/*     */   private volatile int iPeriod;
/*     */   
/*     */   protected static int between(ReadableInstant paramReadableInstant1, ReadableInstant paramReadableInstant2, DurationFieldType paramDurationFieldType) {
/*  68 */     if (paramReadableInstant1 == null || paramReadableInstant2 == null) {
/*  69 */       throw new IllegalArgumentException("ReadableInstant objects must not be null");
/*     */     }
/*  71 */     Chronology chronology = DateTimeUtils.getInstantChronology(paramReadableInstant1);
/*  72 */     return paramDurationFieldType.getField(chronology).getDifference(paramReadableInstant2.getMillis(), paramReadableInstant1.getMillis());
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
/*     */   protected static int between(ReadablePartial paramReadablePartial1, ReadablePartial paramReadablePartial2, ReadablePeriod paramReadablePeriod) {
/*  90 */     if (paramReadablePartial1 == null || paramReadablePartial2 == null) {
/*  91 */       throw new IllegalArgumentException("ReadablePartial objects must not be null");
/*     */     }
/*  93 */     if (paramReadablePartial1.size() != paramReadablePartial2.size())
/*  94 */       throw new IllegalArgumentException("ReadablePartial objects must have the same set of fields");  byte b;
/*     */     int i;
/*  96 */     for (b = 0, i = paramReadablePartial1.size(); b < i; b++) {
/*  97 */       if (paramReadablePartial1.getFieldType(b) != paramReadablePartial2.getFieldType(b)) {
/*  98 */         throw new IllegalArgumentException("ReadablePartial objects must have the same set of fields");
/*     */       }
/*     */     } 
/* 101 */     if (!DateTimeUtils.isContiguous(paramReadablePartial1)) {
/* 102 */       throw new IllegalArgumentException("ReadablePartial objects must be contiguous");
/*     */     }
/* 104 */     Chronology chronology = DateTimeUtils.getChronology(paramReadablePartial1.getChronology()).withUTC();
/* 105 */     int[] arrayOfInt = chronology.get(paramReadablePeriod, chronology.set(paramReadablePartial1, 63072000000L), chronology.set(paramReadablePartial2, 63072000000L));
/* 106 */     return arrayOfInt[0];
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static int standardPeriodIn(ReadablePeriod paramReadablePeriod, long paramLong) {
/* 130 */     if (paramReadablePeriod == null) {
/* 131 */       return 0;
/*     */     }
/* 133 */     ISOChronology iSOChronology = ISOChronology.getInstanceUTC();
/* 134 */     long l = 0L;
/* 135 */     for (byte b = 0; b < paramReadablePeriod.size(); b++) {
/* 136 */       int i = paramReadablePeriod.getValue(b);
/* 137 */       if (i != 0) {
/* 138 */         DurationField durationField = paramReadablePeriod.getFieldType(b).getField((Chronology)iSOChronology);
/* 139 */         if (!durationField.isPrecise()) {
/* 140 */           throw new IllegalArgumentException("Cannot convert period to duration as " + durationField.getName() + " is not precise in the period " + paramReadablePeriod);
/*     */         }
/*     */ 
/*     */         
/* 144 */         l = FieldUtils.safeAdd(l, FieldUtils.safeMultiply(durationField.getUnitMillis(), i));
/*     */       } 
/*     */     } 
/* 147 */     return FieldUtils.safeToInt(l / paramLong);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected BaseSingleFieldPeriod(int paramInt) {
/* 158 */     this.iPeriod = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getValue() {
/* 168 */     return this.iPeriod;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setValue(int paramInt) {
/* 178 */     this.iPeriod = paramInt;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 203 */     return 1;
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
/*     */   public DurationFieldType getFieldType(int paramInt) {
/* 217 */     if (paramInt != 0) {
/* 218 */       throw new IndexOutOfBoundsException(String.valueOf(paramInt));
/*     */     }
/* 220 */     return getFieldType();
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
/*     */   public int getValue(int paramInt) {
/* 233 */     if (paramInt != 0) {
/* 234 */       throw new IndexOutOfBoundsException(String.valueOf(paramInt));
/*     */     }
/* 236 */     return getValue();
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
/*     */   public int get(DurationFieldType paramDurationFieldType) {
/* 249 */     if (paramDurationFieldType == getFieldType()) {
/* 250 */       return getValue();
/*     */     }
/* 252 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSupported(DurationFieldType paramDurationFieldType) {
/* 262 */     return (paramDurationFieldType == getFieldType());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Period toPeriod() {
/* 273 */     return Period.ZERO.withFields(this);
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
/*     */   public MutablePeriod toMutablePeriod() {
/* 285 */     MutablePeriod mutablePeriod = new MutablePeriod();
/* 286 */     mutablePeriod.add(this);
/* 287 */     return mutablePeriod;
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
/*     */   public boolean equals(Object paramObject) {
/* 301 */     if (this == paramObject) {
/* 302 */       return true;
/*     */     }
/* 304 */     if (!(paramObject instanceof ReadablePeriod)) {
/* 305 */       return false;
/*     */     }
/* 307 */     ReadablePeriod readablePeriod = (ReadablePeriod)paramObject;
/* 308 */     return (readablePeriod.getPeriodType() == getPeriodType() && readablePeriod.getValue(0) == getValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 317 */     int i = 17;
/* 318 */     i = 27 * i + getValue();
/* 319 */     i = 27 * i + getFieldType().hashCode();
/* 320 */     return i;
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
/*     */   public int compareTo(BaseSingleFieldPeriod paramBaseSingleFieldPeriod) {
/* 332 */     if (paramBaseSingleFieldPeriod.getClass() != getClass()) {
/* 333 */       throw new ClassCastException(getClass() + " cannot be compared to " + paramBaseSingleFieldPeriod.getClass());
/*     */     }
/* 335 */     int i = paramBaseSingleFieldPeriod.getValue();
/* 336 */     int j = getValue();
/* 337 */     if (j > i) {
/* 338 */       return 1;
/*     */     }
/* 340 */     if (j < i) {
/* 341 */       return -1;
/*     */     }
/* 343 */     return 0;
/*     */   }
/*     */   
/*     */   public abstract DurationFieldType getFieldType();
/*     */   
/*     */   public abstract PeriodType getPeriodType();
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/base/BaseSingleFieldPeriod.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */