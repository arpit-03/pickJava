/*     */ package org.joda.time.field;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Locale;
/*     */ import org.joda.time.Chronology;
/*     */ import org.joda.time.DateTimeField;
/*     */ import org.joda.time.DateTimeFieldType;
/*     */ import org.joda.time.DateTimeUtils;
/*     */ import org.joda.time.DurationField;
/*     */ import org.joda.time.Interval;
/*     */ import org.joda.time.ReadableInstant;
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
/*     */ public abstract class AbstractReadableInstantFieldProperty
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1971226328211649661L;
/*     */   
/*     */   public abstract DateTimeField getField();
/*     */   
/*     */   public DateTimeFieldType getFieldType() {
/*  72 */     return getField().getType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  81 */     return getField().getName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract long getMillis();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Chronology getChronology() {
/* 101 */     throw new UnsupportedOperationException("The method getChronology() was added in v1.4 and needs to be implemented by subclasses of AbstractReadableInstantFieldProperty");
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
/*     */   public int get() {
/* 120 */     return getField().get(getMillis());
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
/*     */   public String getAsString() {
/* 136 */     return Integer.toString(get());
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
/*     */   public String getAsText() {
/* 151 */     return getAsText(null);
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
/*     */   public String getAsText(Locale paramLocale) {
/* 167 */     return getField().getAsText(getMillis(), paramLocale);
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
/*     */   public String getAsShortText() {
/* 182 */     return getAsShortText(null);
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
/*     */   public String getAsShortText(Locale paramLocale) {
/* 198 */     return getField().getAsShortText(getMillis(), paramLocale);
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
/*     */   public int getDifference(ReadableInstant paramReadableInstant) {
/* 213 */     if (paramReadableInstant == null) {
/* 214 */       return getField().getDifference(getMillis(), DateTimeUtils.currentTimeMillis());
/*     */     }
/* 216 */     return getField().getDifference(getMillis(), paramReadableInstant.getMillis());
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
/*     */   public long getDifferenceAsLong(ReadableInstant paramReadableInstant) {
/* 230 */     if (paramReadableInstant == null) {
/* 231 */       return getField().getDifferenceAsLong(getMillis(), DateTimeUtils.currentTimeMillis());
/*     */     }
/* 233 */     return getField().getDifferenceAsLong(getMillis(), paramReadableInstant.getMillis());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DurationField getDurationField() {
/* 244 */     return getField().getDurationField();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DurationField getRangeDurationField() {
/* 254 */     return getField().getRangeDurationField();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isLeap() {
/* 264 */     return getField().isLeap(getMillis());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getLeapAmount() {
/* 274 */     return getField().getLeapAmount(getMillis());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DurationField getLeapDurationField() {
/* 282 */     return getField().getLeapDurationField();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMinimumValueOverall() {
/* 293 */     return getField().getMinimumValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMinimumValue() {
/* 303 */     return getField().getMinimumValue(getMillis());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaximumValueOverall() {
/* 313 */     return getField().getMaximumValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaximumValue() {
/* 323 */     return getField().getMaximumValue(getMillis());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaximumTextLength(Locale paramLocale) {
/* 334 */     return getField().getMaximumTextLength(paramLocale);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaximumShortTextLength(Locale paramLocale) {
/* 345 */     return getField().getMaximumShortTextLength(paramLocale);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long remainder() {
/* 356 */     return getField().remainder(getMillis());
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
/*     */   public Interval toInterval() {
/* 370 */     DateTimeField dateTimeField = getField();
/* 371 */     long l1 = dateTimeField.roundFloor(getMillis());
/* 372 */     long l2 = dateTimeField.add(l1, 1);
/* 373 */     return new Interval(l1, l2, getChronology());
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
/*     */   public int compareTo(ReadableInstant paramReadableInstant) {
/* 391 */     if (paramReadableInstant == null) {
/* 392 */       throw new IllegalArgumentException("The instant must not be null");
/*     */     }
/* 394 */     int i = get();
/* 395 */     int j = paramReadableInstant.get(getFieldType());
/* 396 */     if (i < j)
/* 397 */       return -1; 
/* 398 */     if (i > j) {
/* 399 */       return 1;
/*     */     }
/* 401 */     return 0;
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
/*     */   public int compareTo(ReadablePartial paramReadablePartial) {
/* 420 */     if (paramReadablePartial == null) {
/* 421 */       throw new IllegalArgumentException("The partial must not be null");
/*     */     }
/* 423 */     int i = get();
/* 424 */     int j = paramReadablePartial.get(getFieldType());
/* 425 */     if (i < j)
/* 426 */       return -1; 
/* 427 */     if (i > j) {
/* 428 */       return 1;
/*     */     }
/* 430 */     return 0;
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
/*     */   public boolean equals(Object paramObject) {
/* 442 */     if (this == paramObject) {
/* 443 */       return true;
/*     */     }
/* 445 */     if (!(paramObject instanceof AbstractReadableInstantFieldProperty)) {
/* 446 */       return false;
/*     */     }
/* 448 */     AbstractReadableInstantFieldProperty abstractReadableInstantFieldProperty = (AbstractReadableInstantFieldProperty)paramObject;
/* 449 */     return (get() == abstractReadableInstantFieldProperty.get() && getFieldType().equals(abstractReadableInstantFieldProperty.getFieldType()) && FieldUtils.equals(getChronology(), abstractReadableInstantFieldProperty.getChronology()));
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
/*     */   public int hashCode() {
/* 461 */     return get() * 17 + getFieldType().hashCode() + getChronology().hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 471 */     return "Property[" + getName() + "]";
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/field/AbstractReadableInstantFieldProperty.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */