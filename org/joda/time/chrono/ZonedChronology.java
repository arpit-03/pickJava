/*     */ package org.joda.time.chrono;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Locale;
/*     */ import org.joda.time.Chronology;
/*     */ import org.joda.time.DateTimeField;
/*     */ import org.joda.time.DateTimeZone;
/*     */ import org.joda.time.DurationField;
/*     */ import org.joda.time.IllegalFieldValueException;
/*     */ import org.joda.time.IllegalInstantException;
/*     */ import org.joda.time.ReadablePartial;
/*     */ import org.joda.time.field.BaseDateTimeField;
/*     */ import org.joda.time.field.BaseDurationField;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ZonedChronology
/*     */   extends AssembledChronology
/*     */ {
/*     */   private static final long serialVersionUID = -1079258847191166848L;
/*     */   private static final long NEAR_ZERO = 604800000L;
/*     */   
/*     */   public static ZonedChronology getInstance(Chronology paramChronology, DateTimeZone paramDateTimeZone) {
/*  59 */     if (paramChronology == null) {
/*  60 */       throw new IllegalArgumentException("Must supply a chronology");
/*     */     }
/*  62 */     paramChronology = paramChronology.withUTC();
/*  63 */     if (paramChronology == null) {
/*  64 */       throw new IllegalArgumentException("UTC chronology must not be null");
/*     */     }
/*  66 */     if (paramDateTimeZone == null) {
/*  67 */       throw new IllegalArgumentException("DateTimeZone must not be null");
/*     */     }
/*  69 */     return new ZonedChronology(paramChronology, paramDateTimeZone);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean useTimeArithmetic(DurationField paramDurationField) {
/*  75 */     return (paramDurationField != null && paramDurationField.getUnitMillis() < 43200000L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ZonedChronology(Chronology paramChronology, DateTimeZone paramDateTimeZone) {
/*  85 */     super(paramChronology, paramDateTimeZone);
/*     */   }
/*     */   
/*     */   public DateTimeZone getZone() {
/*  89 */     return (DateTimeZone)getParam();
/*     */   }
/*     */   
/*     */   public Chronology withUTC() {
/*  93 */     return getBase();
/*     */   }
/*     */   
/*     */   public Chronology withZone(DateTimeZone paramDateTimeZone) {
/*  97 */     if (paramDateTimeZone == null) {
/*  98 */       paramDateTimeZone = DateTimeZone.getDefault();
/*     */     }
/* 100 */     if (paramDateTimeZone == getParam()) {
/* 101 */       return this;
/*     */     }
/* 103 */     if (paramDateTimeZone == DateTimeZone.UTC) {
/* 104 */       return getBase();
/*     */     }
/* 106 */     return new ZonedChronology(getBase(), paramDateTimeZone);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getDateTimeMillis(int paramInt1, int paramInt2, int paramInt3, int paramInt4) throws IllegalArgumentException {
/* 113 */     return localToUTC(getBase().getDateTimeMillis(paramInt1, paramInt2, paramInt3, paramInt4));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getDateTimeMillis(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7) throws IllegalArgumentException {
/* 122 */     return localToUTC(getBase().getDateTimeMillis(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getDateTimeMillis(long paramLong, int paramInt1, int paramInt2, int paramInt3, int paramInt4) throws IllegalArgumentException {
/* 132 */     return localToUTC(getBase().getDateTimeMillis(paramLong + getZone().getOffset(paramLong), paramInt1, paramInt2, paramInt3, paramInt4));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private long localToUTC(long paramLong) {
/* 142 */     if (paramLong == Long.MAX_VALUE)
/* 143 */       return Long.MAX_VALUE; 
/* 144 */     if (paramLong == Long.MIN_VALUE) {
/* 145 */       return Long.MIN_VALUE;
/*     */     }
/* 147 */     DateTimeZone dateTimeZone = getZone();
/* 148 */     int i = dateTimeZone.getOffsetFromLocal(paramLong);
/* 149 */     long l = paramLong - i;
/* 150 */     if (paramLong > 604800000L && l < 0L)
/* 151 */       return Long.MAX_VALUE; 
/* 152 */     if (paramLong < -604800000L && l > 0L) {
/* 153 */       return Long.MIN_VALUE;
/*     */     }
/* 155 */     int j = dateTimeZone.getOffset(l);
/* 156 */     if (i != j) {
/* 157 */       throw new IllegalInstantException(paramLong, dateTimeZone.getID());
/*     */     }
/* 159 */     return l;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void assemble(AssembledChronology.Fields paramFields) {
/* 165 */     HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
/*     */ 
/*     */ 
/*     */     
/* 169 */     paramFields.eras = convertField(paramFields.eras, hashMap);
/* 170 */     paramFields.centuries = convertField(paramFields.centuries, hashMap);
/* 171 */     paramFields.years = convertField(paramFields.years, hashMap);
/* 172 */     paramFields.months = convertField(paramFields.months, hashMap);
/* 173 */     paramFields.weekyears = convertField(paramFields.weekyears, hashMap);
/* 174 */     paramFields.weeks = convertField(paramFields.weeks, hashMap);
/* 175 */     paramFields.days = convertField(paramFields.days, hashMap);
/*     */     
/* 177 */     paramFields.halfdays = convertField(paramFields.halfdays, hashMap);
/* 178 */     paramFields.hours = convertField(paramFields.hours, hashMap);
/* 179 */     paramFields.minutes = convertField(paramFields.minutes, hashMap);
/* 180 */     paramFields.seconds = convertField(paramFields.seconds, hashMap);
/* 181 */     paramFields.millis = convertField(paramFields.millis, hashMap);
/*     */ 
/*     */ 
/*     */     
/* 185 */     paramFields.year = convertField(paramFields.year, hashMap);
/* 186 */     paramFields.yearOfEra = convertField(paramFields.yearOfEra, hashMap);
/* 187 */     paramFields.yearOfCentury = convertField(paramFields.yearOfCentury, hashMap);
/* 188 */     paramFields.centuryOfEra = convertField(paramFields.centuryOfEra, hashMap);
/* 189 */     paramFields.era = convertField(paramFields.era, hashMap);
/* 190 */     paramFields.dayOfWeek = convertField(paramFields.dayOfWeek, hashMap);
/* 191 */     paramFields.dayOfMonth = convertField(paramFields.dayOfMonth, hashMap);
/* 192 */     paramFields.dayOfYear = convertField(paramFields.dayOfYear, hashMap);
/* 193 */     paramFields.monthOfYear = convertField(paramFields.monthOfYear, hashMap);
/* 194 */     paramFields.weekOfWeekyear = convertField(paramFields.weekOfWeekyear, hashMap);
/* 195 */     paramFields.weekyear = convertField(paramFields.weekyear, hashMap);
/* 196 */     paramFields.weekyearOfCentury = convertField(paramFields.weekyearOfCentury, hashMap);
/*     */     
/* 198 */     paramFields.millisOfSecond = convertField(paramFields.millisOfSecond, hashMap);
/* 199 */     paramFields.millisOfDay = convertField(paramFields.millisOfDay, hashMap);
/* 200 */     paramFields.secondOfMinute = convertField(paramFields.secondOfMinute, hashMap);
/* 201 */     paramFields.secondOfDay = convertField(paramFields.secondOfDay, hashMap);
/* 202 */     paramFields.minuteOfHour = convertField(paramFields.minuteOfHour, hashMap);
/* 203 */     paramFields.minuteOfDay = convertField(paramFields.minuteOfDay, hashMap);
/* 204 */     paramFields.hourOfDay = convertField(paramFields.hourOfDay, hashMap);
/* 205 */     paramFields.hourOfHalfday = convertField(paramFields.hourOfHalfday, hashMap);
/* 206 */     paramFields.clockhourOfDay = convertField(paramFields.clockhourOfDay, hashMap);
/* 207 */     paramFields.clockhourOfHalfday = convertField(paramFields.clockhourOfHalfday, hashMap);
/* 208 */     paramFields.halfdayOfDay = convertField(paramFields.halfdayOfDay, hashMap);
/*     */   }
/*     */   
/*     */   private DurationField convertField(DurationField paramDurationField, HashMap<Object, Object> paramHashMap) {
/* 212 */     if (paramDurationField == null || !paramDurationField.isSupported()) {
/* 213 */       return paramDurationField;
/*     */     }
/* 215 */     if (paramHashMap.containsKey(paramDurationField)) {
/* 216 */       return (DurationField)paramHashMap.get(paramDurationField);
/*     */     }
/* 218 */     ZonedDurationField zonedDurationField = new ZonedDurationField(paramDurationField, getZone());
/* 219 */     paramHashMap.put(paramDurationField, zonedDurationField);
/* 220 */     return (DurationField)zonedDurationField;
/*     */   }
/*     */   
/*     */   private DateTimeField convertField(DateTimeField paramDateTimeField, HashMap<Object, Object> paramHashMap) {
/* 224 */     if (paramDateTimeField == null || !paramDateTimeField.isSupported()) {
/* 225 */       return paramDateTimeField;
/*     */     }
/* 227 */     if (paramHashMap.containsKey(paramDateTimeField)) {
/* 228 */       return (DateTimeField)paramHashMap.get(paramDateTimeField);
/*     */     }
/* 230 */     ZonedDateTimeField zonedDateTimeField = new ZonedDateTimeField(paramDateTimeField, getZone(), convertField(paramDateTimeField.getDurationField(), paramHashMap), convertField(paramDateTimeField.getRangeDurationField(), paramHashMap), convertField(paramDateTimeField.getLeapDurationField(), paramHashMap));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 235 */     paramHashMap.put(paramDateTimeField, zonedDateTimeField);
/* 236 */     return (DateTimeField)zonedDateTimeField;
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
/*     */   public boolean equals(Object paramObject) {
/* 249 */     if (this == paramObject) {
/* 250 */       return true;
/*     */     }
/* 252 */     if (!(paramObject instanceof ZonedChronology)) {
/* 253 */       return false;
/*     */     }
/* 255 */     ZonedChronology zonedChronology = (ZonedChronology)paramObject;
/* 256 */     return (getBase().equals(zonedChronology.getBase()) && getZone().equals(zonedChronology.getZone()));
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
/* 268 */     return 326565 + getZone().hashCode() * 11 + getBase().hashCode() * 7;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 277 */     return "ZonedChronology[" + getBase() + ", " + getZone().getID() + ']';
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static class ZonedDurationField
/*     */     extends BaseDurationField
/*     */   {
/*     */     private static final long serialVersionUID = -485345310999208286L;
/*     */ 
/*     */     
/*     */     final DurationField iField;
/*     */     
/*     */     final boolean iTimeField;
/*     */     
/*     */     final DateTimeZone iZone;
/*     */ 
/*     */     
/*     */     ZonedDurationField(DurationField param1DurationField, DateTimeZone param1DateTimeZone) {
/* 296 */       super(param1DurationField.getType());
/* 297 */       if (!param1DurationField.isSupported()) {
/* 298 */         throw new IllegalArgumentException();
/*     */       }
/* 300 */       this.iField = param1DurationField;
/* 301 */       this.iTimeField = ZonedChronology.useTimeArithmetic(param1DurationField);
/* 302 */       this.iZone = param1DateTimeZone;
/*     */     }
/*     */     
/*     */     public boolean isPrecise() {
/* 306 */       return this.iTimeField ? this.iField.isPrecise() : ((this.iField.isPrecise() && this.iZone.isFixed()));
/*     */     }
/*     */     
/*     */     public long getUnitMillis() {
/* 310 */       return this.iField.getUnitMillis();
/*     */     }
/*     */     
/*     */     public int getValue(long param1Long1, long param1Long2) {
/* 314 */       return this.iField.getValue(param1Long1, addOffset(param1Long2));
/*     */     }
/*     */     
/*     */     public long getValueAsLong(long param1Long1, long param1Long2) {
/* 318 */       return this.iField.getValueAsLong(param1Long1, addOffset(param1Long2));
/*     */     }
/*     */     
/*     */     public long getMillis(int param1Int, long param1Long) {
/* 322 */       return this.iField.getMillis(param1Int, addOffset(param1Long));
/*     */     }
/*     */     
/*     */     public long getMillis(long param1Long1, long param1Long2) {
/* 326 */       return this.iField.getMillis(param1Long1, addOffset(param1Long2));
/*     */     }
/*     */     
/*     */     public long add(long param1Long, int param1Int) {
/* 330 */       int i = getOffsetToAdd(param1Long);
/* 331 */       param1Long = this.iField.add(param1Long + i, param1Int);
/* 332 */       return param1Long - (this.iTimeField ? i : getOffsetFromLocalToSubtract(param1Long));
/*     */     }
/*     */     
/*     */     public long add(long param1Long1, long param1Long2) {
/* 336 */       int i = getOffsetToAdd(param1Long1);
/* 337 */       param1Long1 = this.iField.add(param1Long1 + i, param1Long2);
/* 338 */       return param1Long1 - (this.iTimeField ? i : getOffsetFromLocalToSubtract(param1Long1));
/*     */     }
/*     */     
/*     */     public int getDifference(long param1Long1, long param1Long2) {
/* 342 */       int i = getOffsetToAdd(param1Long2);
/* 343 */       return this.iField.getDifference(param1Long1 + (this.iTimeField ? i : getOffsetToAdd(param1Long1)), param1Long2 + i);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public long getDifferenceAsLong(long param1Long1, long param1Long2) {
/* 349 */       int i = getOffsetToAdd(param1Long2);
/* 350 */       return this.iField.getDifferenceAsLong(param1Long1 + (this.iTimeField ? i : getOffsetToAdd(param1Long1)), param1Long2 + i);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private int getOffsetToAdd(long param1Long) {
/* 356 */       int i = this.iZone.getOffset(param1Long);
/* 357 */       long l = param1Long + i;
/*     */       
/* 359 */       if ((param1Long ^ l) < 0L && (param1Long ^ i) >= 0L) {
/* 360 */         throw new ArithmeticException("Adding time zone offset caused overflow");
/*     */       }
/* 362 */       return i;
/*     */     }
/*     */     
/*     */     private int getOffsetFromLocalToSubtract(long param1Long) {
/* 366 */       int i = this.iZone.getOffsetFromLocal(param1Long);
/* 367 */       long l = param1Long - i;
/*     */       
/* 369 */       if ((param1Long ^ l) < 0L && (param1Long ^ i) < 0L) {
/* 370 */         throw new ArithmeticException("Subtracting time zone offset caused overflow");
/*     */       }
/* 372 */       return i;
/*     */     }
/*     */     
/*     */     private long addOffset(long param1Long) {
/* 376 */       return this.iZone.convertUTCToLocal(param1Long);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object param1Object) {
/* 381 */       if (this == param1Object)
/* 382 */         return true; 
/* 383 */       if (param1Object instanceof ZonedDurationField) {
/* 384 */         ZonedDurationField zonedDurationField = (ZonedDurationField)param1Object;
/* 385 */         return (this.iField.equals(zonedDurationField.iField) && this.iZone.equals(zonedDurationField.iZone));
/*     */       } 
/*     */       
/* 388 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 393 */       return this.iField.hashCode() ^ this.iZone.hashCode();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static final class ZonedDateTimeField
/*     */     extends BaseDateTimeField
/*     */   {
/*     */     private static final long serialVersionUID = -3968986277775529794L;
/*     */ 
/*     */     
/*     */     final DateTimeField iField;
/*     */     
/*     */     final DateTimeZone iZone;
/*     */     
/*     */     final DurationField iDurationField;
/*     */     
/*     */     final boolean iTimeField;
/*     */     
/*     */     final DurationField iRangeDurationField;
/*     */     
/*     */     final DurationField iLeapDurationField;
/*     */ 
/*     */     
/*     */     ZonedDateTimeField(DateTimeField param1DateTimeField, DateTimeZone param1DateTimeZone, DurationField param1DurationField1, DurationField param1DurationField2, DurationField param1DurationField3) {
/* 419 */       super(param1DateTimeField.getType());
/* 420 */       if (!param1DateTimeField.isSupported()) {
/* 421 */         throw new IllegalArgumentException();
/*     */       }
/* 423 */       this.iField = param1DateTimeField;
/* 424 */       this.iZone = param1DateTimeZone;
/* 425 */       this.iDurationField = param1DurationField1;
/* 426 */       this.iTimeField = ZonedChronology.useTimeArithmetic(param1DurationField1);
/* 427 */       this.iRangeDurationField = param1DurationField2;
/* 428 */       this.iLeapDurationField = param1DurationField3;
/*     */     }
/*     */     
/*     */     public boolean isLenient() {
/* 432 */       return this.iField.isLenient();
/*     */     }
/*     */     
/*     */     public int get(long param1Long) {
/* 436 */       long l = this.iZone.convertUTCToLocal(param1Long);
/* 437 */       return this.iField.get(l);
/*     */     }
/*     */     
/*     */     public String getAsText(long param1Long, Locale param1Locale) {
/* 441 */       long l = this.iZone.convertUTCToLocal(param1Long);
/* 442 */       return this.iField.getAsText(l, param1Locale);
/*     */     }
/*     */     
/*     */     public String getAsShortText(long param1Long, Locale param1Locale) {
/* 446 */       long l = this.iZone.convertUTCToLocal(param1Long);
/* 447 */       return this.iField.getAsShortText(l, param1Locale);
/*     */     }
/*     */     
/*     */     public String getAsText(int param1Int, Locale param1Locale) {
/* 451 */       return this.iField.getAsText(param1Int, param1Locale);
/*     */     }
/*     */     
/*     */     public String getAsShortText(int param1Int, Locale param1Locale) {
/* 455 */       return this.iField.getAsShortText(param1Int, param1Locale);
/*     */     }
/*     */     
/*     */     public long add(long param1Long, int param1Int) {
/* 459 */       if (this.iTimeField) {
/* 460 */         int i = getOffsetToAdd(param1Long);
/* 461 */         long l1 = this.iField.add(param1Long + i, param1Int);
/* 462 */         return l1 - i;
/*     */       } 
/* 464 */       long l = this.iZone.convertUTCToLocal(param1Long);
/* 465 */       l = this.iField.add(l, param1Int);
/* 466 */       return this.iZone.convertLocalToUTC(l, false, param1Long);
/*     */     }
/*     */ 
/*     */     
/*     */     public long add(long param1Long1, long param1Long2) {
/* 471 */       if (this.iTimeField) {
/* 472 */         int i = getOffsetToAdd(param1Long1);
/* 473 */         long l1 = this.iField.add(param1Long1 + i, param1Long2);
/* 474 */         return l1 - i;
/*     */       } 
/* 476 */       long l = this.iZone.convertUTCToLocal(param1Long1);
/* 477 */       l = this.iField.add(l, param1Long2);
/* 478 */       return this.iZone.convertLocalToUTC(l, false, param1Long1);
/*     */     }
/*     */ 
/*     */     
/*     */     public long addWrapField(long param1Long, int param1Int) {
/* 483 */       if (this.iTimeField) {
/* 484 */         int i = getOffsetToAdd(param1Long);
/* 485 */         long l1 = this.iField.addWrapField(param1Long + i, param1Int);
/* 486 */         return l1 - i;
/*     */       } 
/* 488 */       long l = this.iZone.convertUTCToLocal(param1Long);
/* 489 */       l = this.iField.addWrapField(l, param1Int);
/* 490 */       return this.iZone.convertLocalToUTC(l, false, param1Long);
/*     */     }
/*     */ 
/*     */     
/*     */     public long set(long param1Long, int param1Int) {
/* 495 */       long l1 = this.iZone.convertUTCToLocal(param1Long);
/* 496 */       l1 = this.iField.set(l1, param1Int);
/* 497 */       long l2 = this.iZone.convertLocalToUTC(l1, false, param1Long);
/* 498 */       if (get(l2) != param1Int) {
/* 499 */         IllegalInstantException illegalInstantException = new IllegalInstantException(l1, this.iZone.getID());
/* 500 */         IllegalFieldValueException illegalFieldValueException = new IllegalFieldValueException(this.iField.getType(), Integer.valueOf(param1Int), illegalInstantException.getMessage());
/* 501 */         illegalFieldValueException.initCause((Throwable)illegalInstantException);
/* 502 */         throw illegalFieldValueException;
/*     */       } 
/* 504 */       return l2;
/*     */     }
/*     */ 
/*     */     
/*     */     public long set(long param1Long, String param1String, Locale param1Locale) {
/* 509 */       long l = this.iZone.convertUTCToLocal(param1Long);
/* 510 */       l = this.iField.set(l, param1String, param1Locale);
/* 511 */       return this.iZone.convertLocalToUTC(l, false, param1Long);
/*     */     }
/*     */     
/*     */     public int getDifference(long param1Long1, long param1Long2) {
/* 515 */       int i = getOffsetToAdd(param1Long2);
/* 516 */       return this.iField.getDifference(param1Long1 + (this.iTimeField ? i : getOffsetToAdd(param1Long1)), param1Long2 + i);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public long getDifferenceAsLong(long param1Long1, long param1Long2) {
/* 522 */       int i = getOffsetToAdd(param1Long2);
/* 523 */       return this.iField.getDifferenceAsLong(param1Long1 + (this.iTimeField ? i : getOffsetToAdd(param1Long1)), param1Long2 + i);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public final DurationField getDurationField() {
/* 529 */       return this.iDurationField;
/*     */     }
/*     */     
/*     */     public final DurationField getRangeDurationField() {
/* 533 */       return this.iRangeDurationField;
/*     */     }
/*     */     
/*     */     public boolean isLeap(long param1Long) {
/* 537 */       long l = this.iZone.convertUTCToLocal(param1Long);
/* 538 */       return this.iField.isLeap(l);
/*     */     }
/*     */     
/*     */     public int getLeapAmount(long param1Long) {
/* 542 */       long l = this.iZone.convertUTCToLocal(param1Long);
/* 543 */       return this.iField.getLeapAmount(l);
/*     */     }
/*     */     
/*     */     public final DurationField getLeapDurationField() {
/* 547 */       return this.iLeapDurationField;
/*     */     }
/*     */     
/*     */     public long roundFloor(long param1Long) {
/* 551 */       if (this.iTimeField) {
/* 552 */         int i = getOffsetToAdd(param1Long);
/* 553 */         param1Long = this.iField.roundFloor(param1Long + i);
/* 554 */         return param1Long - i;
/*     */       } 
/* 556 */       long l = this.iZone.convertUTCToLocal(param1Long);
/* 557 */       l = this.iField.roundFloor(l);
/* 558 */       return this.iZone.convertLocalToUTC(l, false, param1Long);
/*     */     }
/*     */ 
/*     */     
/*     */     public long roundCeiling(long param1Long) {
/* 563 */       if (this.iTimeField) {
/* 564 */         int i = getOffsetToAdd(param1Long);
/* 565 */         param1Long = this.iField.roundCeiling(param1Long + i);
/* 566 */         return param1Long - i;
/*     */       } 
/* 568 */       long l = this.iZone.convertUTCToLocal(param1Long);
/* 569 */       l = this.iField.roundCeiling(l);
/* 570 */       return this.iZone.convertLocalToUTC(l, false, param1Long);
/*     */     }
/*     */ 
/*     */     
/*     */     public long remainder(long param1Long) {
/* 575 */       long l = this.iZone.convertUTCToLocal(param1Long);
/* 576 */       return this.iField.remainder(l);
/*     */     }
/*     */     
/*     */     public int getMinimumValue() {
/* 580 */       return this.iField.getMinimumValue();
/*     */     }
/*     */     
/*     */     public int getMinimumValue(long param1Long) {
/* 584 */       long l = this.iZone.convertUTCToLocal(param1Long);
/* 585 */       return this.iField.getMinimumValue(l);
/*     */     }
/*     */     
/*     */     public int getMinimumValue(ReadablePartial param1ReadablePartial) {
/* 589 */       return this.iField.getMinimumValue(param1ReadablePartial);
/*     */     }
/*     */     
/*     */     public int getMinimumValue(ReadablePartial param1ReadablePartial, int[] param1ArrayOfint) {
/* 593 */       return this.iField.getMinimumValue(param1ReadablePartial, param1ArrayOfint);
/*     */     }
/*     */     
/*     */     public int getMaximumValue() {
/* 597 */       return this.iField.getMaximumValue();
/*     */     }
/*     */     
/*     */     public int getMaximumValue(long param1Long) {
/* 601 */       long l = this.iZone.convertUTCToLocal(param1Long);
/* 602 */       return this.iField.getMaximumValue(l);
/*     */     }
/*     */     
/*     */     public int getMaximumValue(ReadablePartial param1ReadablePartial) {
/* 606 */       return this.iField.getMaximumValue(param1ReadablePartial);
/*     */     }
/*     */     
/*     */     public int getMaximumValue(ReadablePartial param1ReadablePartial, int[] param1ArrayOfint) {
/* 610 */       return this.iField.getMaximumValue(param1ReadablePartial, param1ArrayOfint);
/*     */     }
/*     */     
/*     */     public int getMaximumTextLength(Locale param1Locale) {
/* 614 */       return this.iField.getMaximumTextLength(param1Locale);
/*     */     }
/*     */     
/*     */     public int getMaximumShortTextLength(Locale param1Locale) {
/* 618 */       return this.iField.getMaximumShortTextLength(param1Locale);
/*     */     }
/*     */     
/*     */     private int getOffsetToAdd(long param1Long) {
/* 622 */       int i = this.iZone.getOffset(param1Long);
/* 623 */       long l = param1Long + i;
/*     */       
/* 625 */       if ((param1Long ^ l) < 0L && (param1Long ^ i) >= 0L) {
/* 626 */         throw new ArithmeticException("Adding time zone offset caused overflow");
/*     */       }
/* 628 */       return i;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object param1Object) {
/* 633 */       if (this == param1Object)
/* 634 */         return true; 
/* 635 */       if (param1Object instanceof ZonedDateTimeField) {
/* 636 */         ZonedDateTimeField zonedDateTimeField = (ZonedDateTimeField)param1Object;
/* 637 */         return (this.iField.equals(zonedDateTimeField.iField) && this.iZone.equals(zonedDateTimeField.iZone) && this.iDurationField.equals(zonedDateTimeField.iDurationField) && this.iRangeDurationField.equals(zonedDateTimeField.iRangeDurationField));
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 642 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 647 */       return this.iField.hashCode() ^ this.iZone.hashCode();
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/chrono/ZonedChronology.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */