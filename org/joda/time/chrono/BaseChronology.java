/*     */ package org.joda.time.chrono;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import org.joda.time.Chronology;
/*     */ import org.joda.time.DateTimeField;
/*     */ import org.joda.time.DateTimeFieldType;
/*     */ import org.joda.time.DateTimeZone;
/*     */ import org.joda.time.DurationField;
/*     */ import org.joda.time.DurationFieldType;
/*     */ import org.joda.time.IllegalFieldValueException;
/*     */ import org.joda.time.ReadablePartial;
/*     */ import org.joda.time.ReadablePeriod;
/*     */ import org.joda.time.field.FieldUtils;
/*     */ import org.joda.time.field.UnsupportedDateTimeField;
/*     */ import org.joda.time.field.UnsupportedDurationField;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class BaseChronology
/*     */   extends Chronology
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -7310865996721419676L;
/*     */   
/*     */   public abstract DateTimeZone getZone();
/*     */   
/*     */   public abstract Chronology withUTC();
/*     */   
/*     */   public abstract Chronology withZone(DateTimeZone paramDateTimeZone);
/*     */   
/*     */   public long getDateTimeMillis(int paramInt1, int paramInt2, int paramInt3, int paramInt4) throws IllegalArgumentException {
/* 102 */     long l = year().set(0L, paramInt1);
/* 103 */     l = monthOfYear().set(l, paramInt2);
/* 104 */     l = dayOfMonth().set(l, paramInt3);
/* 105 */     return millisOfDay().set(l, paramInt4);
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
/*     */ 
/*     */   
/*     */   public long getDateTimeMillis(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7) throws IllegalArgumentException {
/* 132 */     long l = year().set(0L, paramInt1);
/* 133 */     l = monthOfYear().set(l, paramInt2);
/* 134 */     l = dayOfMonth().set(l, paramInt3);
/* 135 */     l = hourOfDay().set(l, paramInt4);
/* 136 */     l = minuteOfHour().set(l, paramInt5);
/* 137 */     l = secondOfMinute().set(l, paramInt6);
/* 138 */     return millisOfSecond().set(l, paramInt7);
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
/*     */   public long getDateTimeMillis(long paramLong, int paramInt1, int paramInt2, int paramInt3, int paramInt4) throws IllegalArgumentException {
/* 163 */     paramLong = hourOfDay().set(paramLong, paramInt1);
/* 164 */     paramLong = minuteOfHour().set(paramLong, paramInt2);
/* 165 */     paramLong = secondOfMinute().set(paramLong, paramInt3);
/* 166 */     return millisOfSecond().set(paramLong, paramInt4);
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
/*     */   public void validate(ReadablePartial paramReadablePartial, int[] paramArrayOfint) {
/* 183 */     int i = paramReadablePartial.size(); byte b;
/* 184 */     for (b = 0; b < i; b++) {
/* 185 */       int j = paramArrayOfint[b];
/* 186 */       DateTimeField dateTimeField = paramReadablePartial.getField(b);
/* 187 */       if (j < dateTimeField.getMinimumValue()) {
/* 188 */         throw new IllegalFieldValueException(dateTimeField.getType(), Integer.valueOf(j), Integer.valueOf(dateTimeField.getMinimumValue()), null);
/*     */       }
/*     */ 
/*     */       
/* 192 */       if (j > dateTimeField.getMaximumValue()) {
/* 193 */         throw new IllegalFieldValueException(dateTimeField.getType(), Integer.valueOf(j), null, Integer.valueOf(dateTimeField.getMaximumValue()));
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 199 */     for (b = 0; b < i; b++) {
/* 200 */       int j = paramArrayOfint[b];
/* 201 */       DateTimeField dateTimeField = paramReadablePartial.getField(b);
/* 202 */       if (j < dateTimeField.getMinimumValue(paramReadablePartial, paramArrayOfint)) {
/* 203 */         throw new IllegalFieldValueException(dateTimeField.getType(), Integer.valueOf(j), Integer.valueOf(dateTimeField.getMinimumValue(paramReadablePartial, paramArrayOfint)), null);
/*     */       }
/*     */ 
/*     */       
/* 207 */       if (j > dateTimeField.getMaximumValue(paramReadablePartial, paramArrayOfint)) {
/* 208 */         throw new IllegalFieldValueException(dateTimeField.getType(), Integer.valueOf(j), null, Integer.valueOf(dateTimeField.getMaximumValue(paramReadablePartial, paramArrayOfint)));
/*     */       }
/*     */     } 
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
/*     */   public int[] get(ReadablePartial paramReadablePartial, long paramLong) {
/* 223 */     int i = paramReadablePartial.size();
/* 224 */     int[] arrayOfInt = new int[i];
/* 225 */     for (byte b = 0; b < i; b++) {
/* 226 */       arrayOfInt[b] = paramReadablePartial.getFieldType(b).getField(this).get(paramLong);
/*     */     }
/* 228 */     return arrayOfInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long set(ReadablePartial paramReadablePartial, long paramLong) {
/*     */     byte b;
/*     */     int i;
/* 239 */     for (b = 0, i = paramReadablePartial.size(); b < i; b++) {
/* 240 */       paramLong = paramReadablePartial.getFieldType(b).getField(this).set(paramLong, paramReadablePartial.getValue(b));
/*     */     }
/* 242 */     return paramLong;
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
/*     */   public int[] get(ReadablePeriod paramReadablePeriod, long paramLong1, long paramLong2) {
/* 255 */     int i = paramReadablePeriod.size();
/* 256 */     int[] arrayOfInt = new int[i];
/* 257 */     if (paramLong1 != paramLong2) {
/* 258 */       for (byte b = 0; b < i; b++) {
/* 259 */         DurationField durationField = paramReadablePeriod.getFieldType(b).getField(this);
/* 260 */         int j = durationField.getDifference(paramLong2, paramLong1);
/* 261 */         if (j != 0) {
/* 262 */           paramLong1 = durationField.add(paramLong1, j);
/*     */         }
/* 264 */         arrayOfInt[b] = j;
/*     */       } 
/*     */     }
/* 267 */     return arrayOfInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] get(ReadablePeriod paramReadablePeriod, long paramLong) {
/* 278 */     int i = paramReadablePeriod.size();
/* 279 */     int[] arrayOfInt = new int[i];
/* 280 */     if (paramLong != 0L) {
/* 281 */       long l = 0L;
/* 282 */       for (byte b = 0; b < i; b++) {
/* 283 */         DurationField durationField = paramReadablePeriod.getFieldType(b).getField(this);
/* 284 */         if (durationField.isPrecise()) {
/* 285 */           int j = durationField.getDifference(paramLong, l);
/* 286 */           l = durationField.add(l, j);
/* 287 */           arrayOfInt[b] = j;
/*     */         } 
/*     */       } 
/*     */     } 
/* 291 */     return arrayOfInt;
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
/*     */   public long add(ReadablePeriod paramReadablePeriod, long paramLong, int paramInt) {
/* 303 */     if (paramInt != 0 && paramReadablePeriod != null) {
/* 304 */       byte b; int i; for (b = 0, i = paramReadablePeriod.size(); b < i; b++) {
/* 305 */         long l = paramReadablePeriod.getValue(b);
/* 306 */         if (l != 0L) {
/* 307 */           paramLong = paramReadablePeriod.getFieldType(b).getField(this).add(paramLong, l * paramInt);
/*     */         }
/*     */       } 
/*     */     } 
/* 311 */     return paramLong;
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
/*     */   public long add(long paramLong1, long paramLong2, int paramInt) {
/* 324 */     if (paramLong2 == 0L || paramInt == 0) {
/* 325 */       return paramLong1;
/*     */     }
/* 327 */     long l = FieldUtils.safeMultiply(paramLong2, paramInt);
/* 328 */     return FieldUtils.safeAdd(paramLong1, l);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DurationField millis() {
/* 339 */     return (DurationField)UnsupportedDurationField.getInstance(DurationFieldType.millis());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DateTimeField millisOfSecond() {
/* 348 */     return (DateTimeField)UnsupportedDateTimeField.getInstance(DateTimeFieldType.millisOfSecond(), millis());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DateTimeField millisOfDay() {
/* 357 */     return (DateTimeField)UnsupportedDateTimeField.getInstance(DateTimeFieldType.millisOfDay(), millis());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DurationField seconds() {
/* 368 */     return (DurationField)UnsupportedDurationField.getInstance(DurationFieldType.seconds());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DateTimeField secondOfMinute() {
/* 377 */     return (DateTimeField)UnsupportedDateTimeField.getInstance(DateTimeFieldType.secondOfMinute(), seconds());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DateTimeField secondOfDay() {
/* 386 */     return (DateTimeField)UnsupportedDateTimeField.getInstance(DateTimeFieldType.secondOfDay(), seconds());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DurationField minutes() {
/* 397 */     return (DurationField)UnsupportedDurationField.getInstance(DurationFieldType.minutes());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DateTimeField minuteOfHour() {
/* 406 */     return (DateTimeField)UnsupportedDateTimeField.getInstance(DateTimeFieldType.minuteOfHour(), minutes());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DateTimeField minuteOfDay() {
/* 415 */     return (DateTimeField)UnsupportedDateTimeField.getInstance(DateTimeFieldType.minuteOfDay(), minutes());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DurationField hours() {
/* 426 */     return (DurationField)UnsupportedDurationField.getInstance(DurationFieldType.hours());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DateTimeField hourOfDay() {
/* 435 */     return (DateTimeField)UnsupportedDateTimeField.getInstance(DateTimeFieldType.hourOfDay(), hours());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DateTimeField clockhourOfDay() {
/* 444 */     return (DateTimeField)UnsupportedDateTimeField.getInstance(DateTimeFieldType.clockhourOfDay(), hours());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DurationField halfdays() {
/* 455 */     return (DurationField)UnsupportedDurationField.getInstance(DurationFieldType.halfdays());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DateTimeField hourOfHalfday() {
/* 464 */     return (DateTimeField)UnsupportedDateTimeField.getInstance(DateTimeFieldType.hourOfHalfday(), hours());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DateTimeField clockhourOfHalfday() {
/* 473 */     return (DateTimeField)UnsupportedDateTimeField.getInstance(DateTimeFieldType.clockhourOfHalfday(), hours());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DateTimeField halfdayOfDay() {
/* 482 */     return (DateTimeField)UnsupportedDateTimeField.getInstance(DateTimeFieldType.halfdayOfDay(), halfdays());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DurationField days() {
/* 493 */     return (DurationField)UnsupportedDurationField.getInstance(DurationFieldType.days());
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
/*     */   public DateTimeField dayOfWeek() {
/* 506 */     return (DateTimeField)UnsupportedDateTimeField.getInstance(DateTimeFieldType.dayOfWeek(), days());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DateTimeField dayOfMonth() {
/* 515 */     return (DateTimeField)UnsupportedDateTimeField.getInstance(DateTimeFieldType.dayOfMonth(), days());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DateTimeField dayOfYear() {
/* 524 */     return (DateTimeField)UnsupportedDateTimeField.getInstance(DateTimeFieldType.dayOfYear(), days());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DurationField weeks() {
/* 535 */     return (DurationField)UnsupportedDurationField.getInstance(DurationFieldType.weeks());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DateTimeField weekOfWeekyear() {
/* 544 */     return (DateTimeField)UnsupportedDateTimeField.getInstance(DateTimeFieldType.weekOfWeekyear(), weeks());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DurationField weekyears() {
/* 555 */     return (DurationField)UnsupportedDurationField.getInstance(DurationFieldType.weekyears());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DateTimeField weekyear() {
/* 564 */     return (DateTimeField)UnsupportedDateTimeField.getInstance(DateTimeFieldType.weekyear(), weekyears());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DateTimeField weekyearOfCentury() {
/* 573 */     return (DateTimeField)UnsupportedDateTimeField.getInstance(DateTimeFieldType.weekyearOfCentury(), weekyears());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DurationField months() {
/* 584 */     return (DurationField)UnsupportedDurationField.getInstance(DurationFieldType.months());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DateTimeField monthOfYear() {
/* 593 */     return (DateTimeField)UnsupportedDateTimeField.getInstance(DateTimeFieldType.monthOfYear(), months());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DurationField years() {
/* 604 */     return (DurationField)UnsupportedDurationField.getInstance(DurationFieldType.years());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DateTimeField year() {
/* 613 */     return (DateTimeField)UnsupportedDateTimeField.getInstance(DateTimeFieldType.year(), years());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DateTimeField yearOfEra() {
/* 622 */     return (DateTimeField)UnsupportedDateTimeField.getInstance(DateTimeFieldType.yearOfEra(), years());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DateTimeField yearOfCentury() {
/* 631 */     return (DateTimeField)UnsupportedDateTimeField.getInstance(DateTimeFieldType.yearOfCentury(), years());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DurationField centuries() {
/* 642 */     return (DurationField)UnsupportedDurationField.getInstance(DurationFieldType.centuries());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DateTimeField centuryOfEra() {
/* 651 */     return (DateTimeField)UnsupportedDateTimeField.getInstance(DateTimeFieldType.centuryOfEra(), centuries());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DurationField eras() {
/* 662 */     return (DurationField)UnsupportedDurationField.getInstance(DurationFieldType.eras());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DateTimeField era() {
/* 671 */     return (DateTimeField)UnsupportedDateTimeField.getInstance(DateTimeFieldType.era(), eras());
/*     */   }
/*     */   
/*     */   public abstract String toString();
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/chrono/BaseChronology.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */