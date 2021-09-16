/*     */ package org.joda.time.chrono;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Locale;
/*     */ import org.joda.time.Chronology;
/*     */ import org.joda.time.DateTime;
/*     */ import org.joda.time.DateTimeField;
/*     */ import org.joda.time.DateTimeZone;
/*     */ import org.joda.time.DurationField;
/*     */ import org.joda.time.MutableDateTime;
/*     */ import org.joda.time.ReadableDateTime;
/*     */ import org.joda.time.ReadableInstant;
/*     */ import org.joda.time.field.DecoratedDateTimeField;
/*     */ import org.joda.time.field.DecoratedDurationField;
/*     */ import org.joda.time.field.FieldUtils;
/*     */ import org.joda.time.format.DateTimeFormatter;
/*     */ import org.joda.time.format.ISODateTimeFormat;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class LimitChronology
/*     */   extends AssembledChronology
/*     */ {
/*     */   private static final long serialVersionUID = 7670866536893052522L;
/*     */   final DateTime iLowerLimit;
/*     */   final DateTime iUpperLimit;
/*     */   private transient LimitChronology iWithUTC;
/*     */   
/*     */   public static LimitChronology getInstance(Chronology paramChronology, ReadableDateTime paramReadableDateTime1, ReadableDateTime paramReadableDateTime2) {
/*  67 */     if (paramChronology == null) {
/*  68 */       throw new IllegalArgumentException("Must supply a chronology");
/*     */     }
/*     */     
/*  71 */     DateTime dateTime1 = (paramReadableDateTime1 == null) ? null : paramReadableDateTime1.toDateTime();
/*  72 */     DateTime dateTime2 = (paramReadableDateTime2 == null) ? null : paramReadableDateTime2.toDateTime();
/*     */     
/*  74 */     if (dateTime1 != null && dateTime2 != null && !dateTime1.isBefore((ReadableInstant)dateTime2)) {
/*  75 */       throw new IllegalArgumentException("The lower limit must be come before than the upper limit");
/*     */     }
/*     */ 
/*     */     
/*  79 */     return new LimitChronology(paramChronology, dateTime1, dateTime2);
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
/*     */   private LimitChronology(Chronology paramChronology, DateTime paramDateTime1, DateTime paramDateTime2) {
/*  97 */     super(paramChronology, null);
/*     */     
/*  99 */     this.iLowerLimit = paramDateTime1;
/* 100 */     this.iUpperLimit = paramDateTime2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DateTime getLowerLimit() {
/* 109 */     return this.iLowerLimit;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DateTime getUpperLimit() {
/* 118 */     return this.iUpperLimit;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Chronology withUTC() {
/* 127 */     return withZone(DateTimeZone.UTC);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Chronology withZone(DateTimeZone paramDateTimeZone) {
/* 136 */     if (paramDateTimeZone == null) {
/* 137 */       paramDateTimeZone = DateTimeZone.getDefault();
/*     */     }
/* 139 */     if (paramDateTimeZone == getZone()) {
/* 140 */       return this;
/*     */     }
/*     */     
/* 143 */     if (paramDateTimeZone == DateTimeZone.UTC && this.iWithUTC != null) {
/* 144 */       return this.iWithUTC;
/*     */     }
/*     */     
/* 147 */     DateTime dateTime1 = this.iLowerLimit;
/* 148 */     if (dateTime1 != null) {
/* 149 */       MutableDateTime mutableDateTime = dateTime1.toMutableDateTime();
/* 150 */       mutableDateTime.setZoneRetainFields(paramDateTimeZone);
/* 151 */       dateTime1 = mutableDateTime.toDateTime();
/*     */     } 
/*     */     
/* 154 */     DateTime dateTime2 = this.iUpperLimit;
/* 155 */     if (dateTime2 != null) {
/* 156 */       MutableDateTime mutableDateTime = dateTime2.toMutableDateTime();
/* 157 */       mutableDateTime.setZoneRetainFields(paramDateTimeZone);
/* 158 */       dateTime2 = mutableDateTime.toDateTime();
/*     */     } 
/*     */     
/* 161 */     LimitChronology limitChronology = getInstance(getBase().withZone(paramDateTimeZone), (ReadableDateTime)dateTime1, (ReadableDateTime)dateTime2);
/*     */ 
/*     */     
/* 164 */     if (paramDateTimeZone == DateTimeZone.UTC) {
/* 165 */       this.iWithUTC = limitChronology;
/*     */     }
/*     */     
/* 168 */     return limitChronology;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getDateTimeMillis(int paramInt1, int paramInt2, int paramInt3, int paramInt4) throws IllegalArgumentException {
/* 175 */     long l = getBase().getDateTimeMillis(paramInt1, paramInt2, paramInt3, paramInt4);
/* 176 */     checkLimits(l, "resulting");
/* 177 */     return l;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getDateTimeMillis(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7) throws IllegalArgumentException {
/* 185 */     long l = getBase().getDateTimeMillis(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7);
/*     */ 
/*     */     
/* 188 */     checkLimits(l, "resulting");
/* 189 */     return l;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getDateTimeMillis(long paramLong, int paramInt1, int paramInt2, int paramInt3, int paramInt4) throws IllegalArgumentException {
/* 197 */     checkLimits(paramLong, (String)null);
/* 198 */     paramLong = getBase().getDateTimeMillis(paramLong, paramInt1, paramInt2, paramInt3, paramInt4);
/*     */     
/* 200 */     checkLimits(paramLong, "resulting");
/* 201 */     return paramLong;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void assemble(AssembledChronology.Fields paramFields) {
/* 207 */     HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
/*     */ 
/*     */ 
/*     */     
/* 211 */     paramFields.eras = convertField(paramFields.eras, hashMap);
/* 212 */     paramFields.centuries = convertField(paramFields.centuries, hashMap);
/* 213 */     paramFields.years = convertField(paramFields.years, hashMap);
/* 214 */     paramFields.months = convertField(paramFields.months, hashMap);
/* 215 */     paramFields.weekyears = convertField(paramFields.weekyears, hashMap);
/* 216 */     paramFields.weeks = convertField(paramFields.weeks, hashMap);
/* 217 */     paramFields.days = convertField(paramFields.days, hashMap);
/*     */     
/* 219 */     paramFields.halfdays = convertField(paramFields.halfdays, hashMap);
/* 220 */     paramFields.hours = convertField(paramFields.hours, hashMap);
/* 221 */     paramFields.minutes = convertField(paramFields.minutes, hashMap);
/* 222 */     paramFields.seconds = convertField(paramFields.seconds, hashMap);
/* 223 */     paramFields.millis = convertField(paramFields.millis, hashMap);
/*     */ 
/*     */ 
/*     */     
/* 227 */     paramFields.year = convertField(paramFields.year, hashMap);
/* 228 */     paramFields.yearOfEra = convertField(paramFields.yearOfEra, hashMap);
/* 229 */     paramFields.yearOfCentury = convertField(paramFields.yearOfCentury, hashMap);
/* 230 */     paramFields.centuryOfEra = convertField(paramFields.centuryOfEra, hashMap);
/* 231 */     paramFields.era = convertField(paramFields.era, hashMap);
/* 232 */     paramFields.dayOfWeek = convertField(paramFields.dayOfWeek, hashMap);
/* 233 */     paramFields.dayOfMonth = convertField(paramFields.dayOfMonth, hashMap);
/* 234 */     paramFields.dayOfYear = convertField(paramFields.dayOfYear, hashMap);
/* 235 */     paramFields.monthOfYear = convertField(paramFields.monthOfYear, hashMap);
/* 236 */     paramFields.weekOfWeekyear = convertField(paramFields.weekOfWeekyear, hashMap);
/* 237 */     paramFields.weekyear = convertField(paramFields.weekyear, hashMap);
/* 238 */     paramFields.weekyearOfCentury = convertField(paramFields.weekyearOfCentury, hashMap);
/*     */     
/* 240 */     paramFields.millisOfSecond = convertField(paramFields.millisOfSecond, hashMap);
/* 241 */     paramFields.millisOfDay = convertField(paramFields.millisOfDay, hashMap);
/* 242 */     paramFields.secondOfMinute = convertField(paramFields.secondOfMinute, hashMap);
/* 243 */     paramFields.secondOfDay = convertField(paramFields.secondOfDay, hashMap);
/* 244 */     paramFields.minuteOfHour = convertField(paramFields.minuteOfHour, hashMap);
/* 245 */     paramFields.minuteOfDay = convertField(paramFields.minuteOfDay, hashMap);
/* 246 */     paramFields.hourOfDay = convertField(paramFields.hourOfDay, hashMap);
/* 247 */     paramFields.hourOfHalfday = convertField(paramFields.hourOfHalfday, hashMap);
/* 248 */     paramFields.clockhourOfDay = convertField(paramFields.clockhourOfDay, hashMap);
/* 249 */     paramFields.clockhourOfHalfday = convertField(paramFields.clockhourOfHalfday, hashMap);
/* 250 */     paramFields.halfdayOfDay = convertField(paramFields.halfdayOfDay, hashMap);
/*     */   }
/*     */   
/*     */   private DurationField convertField(DurationField paramDurationField, HashMap<Object, Object> paramHashMap) {
/* 254 */     if (paramDurationField == null || !paramDurationField.isSupported()) {
/* 255 */       return paramDurationField;
/*     */     }
/* 257 */     if (paramHashMap.containsKey(paramDurationField)) {
/* 258 */       return (DurationField)paramHashMap.get(paramDurationField);
/*     */     }
/* 260 */     LimitDurationField limitDurationField = new LimitDurationField(paramDurationField);
/* 261 */     paramHashMap.put(paramDurationField, limitDurationField);
/* 262 */     return (DurationField)limitDurationField;
/*     */   }
/*     */   
/*     */   private DateTimeField convertField(DateTimeField paramDateTimeField, HashMap<Object, Object> paramHashMap) {
/* 266 */     if (paramDateTimeField == null || !paramDateTimeField.isSupported()) {
/* 267 */       return paramDateTimeField;
/*     */     }
/* 269 */     if (paramHashMap.containsKey(paramDateTimeField)) {
/* 270 */       return (DateTimeField)paramHashMap.get(paramDateTimeField);
/*     */     }
/* 272 */     LimitDateTimeField limitDateTimeField = new LimitDateTimeField(paramDateTimeField, convertField(paramDateTimeField.getDurationField(), paramHashMap), convertField(paramDateTimeField.getRangeDurationField(), paramHashMap), convertField(paramDateTimeField.getLeapDurationField(), paramHashMap));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 277 */     paramHashMap.put(paramDateTimeField, limitDateTimeField);
/* 278 */     return (DateTimeField)limitDateTimeField;
/*     */   }
/*     */   
/*     */   void checkLimits(long paramLong, String paramString) {
/*     */     DateTime dateTime;
/* 283 */     if ((dateTime = this.iLowerLimit) != null && paramLong < dateTime.getMillis()) {
/* 284 */       throw new LimitException(paramString, true);
/*     */     }
/* 286 */     if ((dateTime = this.iUpperLimit) != null && paramLong >= dateTime.getMillis()) {
/* 287 */       throw new LimitException(paramString, false);
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
/*     */   public boolean equals(Object paramObject) {
/* 301 */     if (this == paramObject) {
/* 302 */       return true;
/*     */     }
/* 304 */     if (!(paramObject instanceof LimitChronology)) {
/* 305 */       return false;
/*     */     }
/* 307 */     LimitChronology limitChronology = (LimitChronology)paramObject;
/* 308 */     return (getBase().equals(limitChronology.getBase()) && FieldUtils.equals(getLowerLimit(), limitChronology.getLowerLimit()) && FieldUtils.equals(getUpperLimit(), limitChronology.getUpperLimit()));
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
/*     */   public int hashCode() {
/* 321 */     int i = 317351877;
/* 322 */     i += (getLowerLimit() != null) ? getLowerLimit().hashCode() : 0;
/* 323 */     i += (getUpperLimit() != null) ? getUpperLimit().hashCode() : 0;
/* 324 */     i += getBase().hashCode() * 7;
/* 325 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 334 */     return "LimitChronology[" + getBase().toString() + ", " + ((getLowerLimit() == null) ? "NoLimit" : getLowerLimit().toString()) + ", " + ((getUpperLimit() == null) ? "NoLimit" : getUpperLimit().toString()) + ']';
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private class LimitException
/*     */     extends IllegalArgumentException
/*     */   {
/*     */     private static final long serialVersionUID = -5924689995607498581L;
/*     */ 
/*     */     
/*     */     private final boolean iIsLow;
/*     */ 
/*     */ 
/*     */     
/*     */     LimitException(String param1String, boolean param1Boolean) {
/* 350 */       super(param1String);
/* 351 */       this.iIsLow = param1Boolean;
/*     */     }
/*     */     
/*     */     public String getMessage() {
/* 355 */       StringBuffer stringBuffer = new StringBuffer(85);
/* 356 */       stringBuffer.append("The");
/* 357 */       String str = super.getMessage();
/* 358 */       if (str != null) {
/* 359 */         stringBuffer.append(' ');
/* 360 */         stringBuffer.append(str);
/*     */       } 
/* 362 */       stringBuffer.append(" instant is ");
/*     */       
/* 364 */       DateTimeFormatter dateTimeFormatter = ISODateTimeFormat.dateTime();
/* 365 */       dateTimeFormatter = dateTimeFormatter.withChronology(LimitChronology.this.getBase());
/* 366 */       if (this.iIsLow) {
/* 367 */         stringBuffer.append("below the supported minimum of ");
/* 368 */         dateTimeFormatter.printTo(stringBuffer, LimitChronology.this.getLowerLimit().getMillis());
/*     */       } else {
/* 370 */         stringBuffer.append("above the supported maximum of ");
/* 371 */         dateTimeFormatter.printTo(stringBuffer, LimitChronology.this.getUpperLimit().getMillis());
/*     */       } 
/*     */       
/* 374 */       stringBuffer.append(" (");
/* 375 */       stringBuffer.append(LimitChronology.this.getBase());
/* 376 */       stringBuffer.append(')');
/*     */       
/* 378 */       return stringBuffer.toString();
/*     */     }
/*     */     
/*     */     public String toString() {
/* 382 */       return "IllegalArgumentException: " + getMessage();
/*     */     }
/*     */   }
/*     */   
/*     */   private class LimitDurationField extends DecoratedDurationField {
/*     */     private static final long serialVersionUID = 8049297699408782284L;
/*     */     
/*     */     LimitDurationField(DurationField param1DurationField) {
/* 390 */       super(param1DurationField, param1DurationField.getType());
/*     */     }
/*     */     
/*     */     public int getValue(long param1Long1, long param1Long2) {
/* 394 */       LimitChronology.this.checkLimits(param1Long2, (String)null);
/* 395 */       return getWrappedField().getValue(param1Long1, param1Long2);
/*     */     }
/*     */     
/*     */     public long getValueAsLong(long param1Long1, long param1Long2) {
/* 399 */       LimitChronology.this.checkLimits(param1Long2, (String)null);
/* 400 */       return getWrappedField().getValueAsLong(param1Long1, param1Long2);
/*     */     }
/*     */     
/*     */     public long getMillis(int param1Int, long param1Long) {
/* 404 */       LimitChronology.this.checkLimits(param1Long, (String)null);
/* 405 */       return getWrappedField().getMillis(param1Int, param1Long);
/*     */     }
/*     */     
/*     */     public long getMillis(long param1Long1, long param1Long2) {
/* 409 */       LimitChronology.this.checkLimits(param1Long2, (String)null);
/* 410 */       return getWrappedField().getMillis(param1Long1, param1Long2);
/*     */     }
/*     */     
/*     */     public long add(long param1Long, int param1Int) {
/* 414 */       LimitChronology.this.checkLimits(param1Long, (String)null);
/* 415 */       long l = getWrappedField().add(param1Long, param1Int);
/* 416 */       LimitChronology.this.checkLimits(l, "resulting");
/* 417 */       return l;
/*     */     }
/*     */     
/*     */     public long add(long param1Long1, long param1Long2) {
/* 421 */       LimitChronology.this.checkLimits(param1Long1, (String)null);
/* 422 */       long l = getWrappedField().add(param1Long1, param1Long2);
/* 423 */       LimitChronology.this.checkLimits(l, "resulting");
/* 424 */       return l;
/*     */     }
/*     */     
/*     */     public int getDifference(long param1Long1, long param1Long2) {
/* 428 */       LimitChronology.this.checkLimits(param1Long1, "minuend");
/* 429 */       LimitChronology.this.checkLimits(param1Long2, "subtrahend");
/* 430 */       return getWrappedField().getDifference(param1Long1, param1Long2);
/*     */     }
/*     */     
/*     */     public long getDifferenceAsLong(long param1Long1, long param1Long2) {
/* 434 */       LimitChronology.this.checkLimits(param1Long1, "minuend");
/* 435 */       LimitChronology.this.checkLimits(param1Long2, "subtrahend");
/* 436 */       return getWrappedField().getDifferenceAsLong(param1Long1, param1Long2);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private class LimitDateTimeField
/*     */     extends DecoratedDateTimeField
/*     */   {
/*     */     private static final long serialVersionUID = -2435306746995699312L;
/*     */     
/*     */     private final DurationField iDurationField;
/*     */     
/*     */     private final DurationField iRangeDurationField;
/*     */     
/*     */     private final DurationField iLeapDurationField;
/*     */     
/*     */     LimitDateTimeField(DateTimeField param1DateTimeField, DurationField param1DurationField1, DurationField param1DurationField2, DurationField param1DurationField3) {
/* 453 */       super(param1DateTimeField, param1DateTimeField.getType());
/* 454 */       this.iDurationField = param1DurationField1;
/* 455 */       this.iRangeDurationField = param1DurationField2;
/* 456 */       this.iLeapDurationField = param1DurationField3;
/*     */     }
/*     */     
/*     */     public int get(long param1Long) {
/* 460 */       LimitChronology.this.checkLimits(param1Long, (String)null);
/* 461 */       return getWrappedField().get(param1Long);
/*     */     }
/*     */     
/*     */     public String getAsText(long param1Long, Locale param1Locale) {
/* 465 */       LimitChronology.this.checkLimits(param1Long, (String)null);
/* 466 */       return getWrappedField().getAsText(param1Long, param1Locale);
/*     */     }
/*     */     
/*     */     public String getAsShortText(long param1Long, Locale param1Locale) {
/* 470 */       LimitChronology.this.checkLimits(param1Long, (String)null);
/* 471 */       return getWrappedField().getAsShortText(param1Long, param1Locale);
/*     */     }
/*     */     
/*     */     public long add(long param1Long, int param1Int) {
/* 475 */       LimitChronology.this.checkLimits(param1Long, (String)null);
/* 476 */       long l = getWrappedField().add(param1Long, param1Int);
/* 477 */       LimitChronology.this.checkLimits(l, "resulting");
/* 478 */       return l;
/*     */     }
/*     */     
/*     */     public long add(long param1Long1, long param1Long2) {
/* 482 */       LimitChronology.this.checkLimits(param1Long1, (String)null);
/* 483 */       long l = getWrappedField().add(param1Long1, param1Long2);
/* 484 */       LimitChronology.this.checkLimits(l, "resulting");
/* 485 */       return l;
/*     */     }
/*     */     
/*     */     public long addWrapField(long param1Long, int param1Int) {
/* 489 */       LimitChronology.this.checkLimits(param1Long, (String)null);
/* 490 */       long l = getWrappedField().addWrapField(param1Long, param1Int);
/* 491 */       LimitChronology.this.checkLimits(l, "resulting");
/* 492 */       return l;
/*     */     }
/*     */     
/*     */     public int getDifference(long param1Long1, long param1Long2) {
/* 496 */       LimitChronology.this.checkLimits(param1Long1, "minuend");
/* 497 */       LimitChronology.this.checkLimits(param1Long2, "subtrahend");
/* 498 */       return getWrappedField().getDifference(param1Long1, param1Long2);
/*     */     }
/*     */     
/*     */     public long getDifferenceAsLong(long param1Long1, long param1Long2) {
/* 502 */       LimitChronology.this.checkLimits(param1Long1, "minuend");
/* 503 */       LimitChronology.this.checkLimits(param1Long2, "subtrahend");
/* 504 */       return getWrappedField().getDifferenceAsLong(param1Long1, param1Long2);
/*     */     }
/*     */     
/*     */     public long set(long param1Long, int param1Int) {
/* 508 */       LimitChronology.this.checkLimits(param1Long, (String)null);
/* 509 */       long l = getWrappedField().set(param1Long, param1Int);
/* 510 */       LimitChronology.this.checkLimits(l, "resulting");
/* 511 */       return l;
/*     */     }
/*     */     
/*     */     public long set(long param1Long, String param1String, Locale param1Locale) {
/* 515 */       LimitChronology.this.checkLimits(param1Long, (String)null);
/* 516 */       long l = getWrappedField().set(param1Long, param1String, param1Locale);
/* 517 */       LimitChronology.this.checkLimits(l, "resulting");
/* 518 */       return l;
/*     */     }
/*     */     
/*     */     public final DurationField getDurationField() {
/* 522 */       return this.iDurationField;
/*     */     }
/*     */     
/*     */     public final DurationField getRangeDurationField() {
/* 526 */       return this.iRangeDurationField;
/*     */     }
/*     */     
/*     */     public boolean isLeap(long param1Long) {
/* 530 */       LimitChronology.this.checkLimits(param1Long, (String)null);
/* 531 */       return getWrappedField().isLeap(param1Long);
/*     */     }
/*     */     
/*     */     public int getLeapAmount(long param1Long) {
/* 535 */       LimitChronology.this.checkLimits(param1Long, (String)null);
/* 536 */       return getWrappedField().getLeapAmount(param1Long);
/*     */     }
/*     */     
/*     */     public final DurationField getLeapDurationField() {
/* 540 */       return this.iLeapDurationField;
/*     */     }
/*     */     
/*     */     public long roundFloor(long param1Long) {
/* 544 */       LimitChronology.this.checkLimits(param1Long, (String)null);
/* 545 */       long l = getWrappedField().roundFloor(param1Long);
/* 546 */       LimitChronology.this.checkLimits(l, "resulting");
/* 547 */       return l;
/*     */     }
/*     */     
/*     */     public long roundCeiling(long param1Long) {
/* 551 */       LimitChronology.this.checkLimits(param1Long, (String)null);
/* 552 */       long l = getWrappedField().roundCeiling(param1Long);
/* 553 */       LimitChronology.this.checkLimits(l, "resulting");
/* 554 */       return l;
/*     */     }
/*     */     
/*     */     public long roundHalfFloor(long param1Long) {
/* 558 */       LimitChronology.this.checkLimits(param1Long, (String)null);
/* 559 */       long l = getWrappedField().roundHalfFloor(param1Long);
/* 560 */       LimitChronology.this.checkLimits(l, "resulting");
/* 561 */       return l;
/*     */     }
/*     */     
/*     */     public long roundHalfCeiling(long param1Long) {
/* 565 */       LimitChronology.this.checkLimits(param1Long, (String)null);
/* 566 */       long l = getWrappedField().roundHalfCeiling(param1Long);
/* 567 */       LimitChronology.this.checkLimits(l, "resulting");
/* 568 */       return l;
/*     */     }
/*     */     
/*     */     public long roundHalfEven(long param1Long) {
/* 572 */       LimitChronology.this.checkLimits(param1Long, (String)null);
/* 573 */       long l = getWrappedField().roundHalfEven(param1Long);
/* 574 */       LimitChronology.this.checkLimits(l, "resulting");
/* 575 */       return l;
/*     */     }
/*     */     
/*     */     public long remainder(long param1Long) {
/* 579 */       LimitChronology.this.checkLimits(param1Long, (String)null);
/* 580 */       long l = getWrappedField().remainder(param1Long);
/* 581 */       LimitChronology.this.checkLimits(l, "resulting");
/* 582 */       return l;
/*     */     }
/*     */     
/*     */     public int getMinimumValue(long param1Long) {
/* 586 */       LimitChronology.this.checkLimits(param1Long, (String)null);
/* 587 */       return getWrappedField().getMinimumValue(param1Long);
/*     */     }
/*     */     
/*     */     public int getMaximumValue(long param1Long) {
/* 591 */       LimitChronology.this.checkLimits(param1Long, (String)null);
/* 592 */       return getWrappedField().getMaximumValue(param1Long);
/*     */     }
/*     */     
/*     */     public int getMaximumTextLength(Locale param1Locale) {
/* 596 */       return getWrappedField().getMaximumTextLength(param1Locale);
/*     */     }
/*     */     
/*     */     public int getMaximumShortTextLength(Locale param1Locale) {
/* 600 */       return getWrappedField().getMaximumShortTextLength(param1Locale);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/chrono/LimitChronology.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */