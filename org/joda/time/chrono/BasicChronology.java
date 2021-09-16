/*     */ package org.joda.time.chrono;
/*     */ 
/*     */ import java.util.Locale;
/*     */ import org.joda.time.Chronology;
/*     */ import org.joda.time.DateTimeField;
/*     */ import org.joda.time.DateTimeFieldType;
/*     */ import org.joda.time.DateTimeZone;
/*     */ import org.joda.time.DurationField;
/*     */ import org.joda.time.DurationFieldType;
/*     */ import org.joda.time.field.DividedDateTimeField;
/*     */ import org.joda.time.field.FieldUtils;
/*     */ import org.joda.time.field.MillisDurationField;
/*     */ import org.joda.time.field.OffsetDateTimeField;
/*     */ import org.joda.time.field.PreciseDateTimeField;
/*     */ import org.joda.time.field.PreciseDurationField;
/*     */ import org.joda.time.field.RemainderDateTimeField;
/*     */ import org.joda.time.field.ZeroIsMaxDateTimeField;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class BasicChronology
/*     */   extends AssembledChronology
/*     */ {
/*     */   private static final long serialVersionUID = 8283225332206808863L;
/*  76 */   private static final DurationField cMillisField = MillisDurationField.INSTANCE;
/*  77 */   private static final DurationField cSecondsField = (DurationField)new PreciseDurationField(DurationFieldType.seconds(), 1000L);
/*     */   
/*  79 */   private static final DurationField cMinutesField = (DurationField)new PreciseDurationField(DurationFieldType.minutes(), 60000L);
/*     */   
/*  81 */   private static final DurationField cHoursField = (DurationField)new PreciseDurationField(DurationFieldType.hours(), 3600000L);
/*     */   
/*  83 */   private static final DurationField cHalfdaysField = (DurationField)new PreciseDurationField(DurationFieldType.halfdays(), 43200000L);
/*     */   
/*  85 */   private static final DurationField cDaysField = (DurationField)new PreciseDurationField(DurationFieldType.days(), 86400000L);
/*     */   
/*  87 */   private static final DurationField cWeeksField = (DurationField)new PreciseDurationField(DurationFieldType.weeks(), 604800000L);
/*     */ 
/*     */   
/*  90 */   private static final DateTimeField cMillisOfSecondField = (DateTimeField)new PreciseDateTimeField(DateTimeFieldType.millisOfSecond(), cMillisField, cSecondsField);
/*     */ 
/*     */   
/*  93 */   private static final DateTimeField cMillisOfDayField = (DateTimeField)new PreciseDateTimeField(DateTimeFieldType.millisOfDay(), cMillisField, cDaysField);
/*     */ 
/*     */   
/*  96 */   private static final DateTimeField cSecondOfMinuteField = (DateTimeField)new PreciseDateTimeField(DateTimeFieldType.secondOfMinute(), cSecondsField, cMinutesField);
/*     */ 
/*     */   
/*  99 */   private static final DateTimeField cSecondOfDayField = (DateTimeField)new PreciseDateTimeField(DateTimeFieldType.secondOfDay(), cSecondsField, cDaysField);
/*     */ 
/*     */   
/* 102 */   private static final DateTimeField cMinuteOfHourField = (DateTimeField)new PreciseDateTimeField(DateTimeFieldType.minuteOfHour(), cMinutesField, cHoursField);
/*     */ 
/*     */   
/* 105 */   private static final DateTimeField cMinuteOfDayField = (DateTimeField)new PreciseDateTimeField(DateTimeFieldType.minuteOfDay(), cMinutesField, cDaysField);
/*     */ 
/*     */   
/* 108 */   private static final DateTimeField cHourOfDayField = (DateTimeField)new PreciseDateTimeField(DateTimeFieldType.hourOfDay(), cHoursField, cDaysField);
/*     */ 
/*     */   
/* 111 */   private static final DateTimeField cHourOfHalfdayField = (DateTimeField)new PreciseDateTimeField(DateTimeFieldType.hourOfHalfday(), cHoursField, cHalfdaysField);
/*     */ 
/*     */   
/* 114 */   private static final DateTimeField cClockhourOfDayField = (DateTimeField)new ZeroIsMaxDateTimeField(cHourOfDayField, DateTimeFieldType.clockhourOfDay());
/*     */ 
/*     */   
/* 117 */   private static final DateTimeField cClockhourOfHalfdayField = (DateTimeField)new ZeroIsMaxDateTimeField(cHourOfHalfdayField, DateTimeFieldType.clockhourOfHalfday());
/*     */ 
/*     */   
/* 120 */   private static final DateTimeField cHalfdayOfDayField = (DateTimeField)new HalfdayField();
/*     */   
/*     */   private static final int CACHE_SIZE = 1024;
/*     */   
/*     */   private static final int CACHE_MASK = 1023;
/*     */   
/* 126 */   private final transient YearInfo[] iYearInfoCache = new YearInfo[1024];
/*     */   
/*     */   private final int iMinDaysInFirstWeek;
/*     */   
/*     */   BasicChronology(Chronology paramChronology, Object paramObject, int paramInt) {
/* 131 */     super(paramChronology, paramObject);
/*     */     
/* 133 */     if (paramInt < 1 || paramInt > 7) {
/* 134 */       throw new IllegalArgumentException("Invalid min days in first week: " + paramInt);
/*     */     }
/*     */ 
/*     */     
/* 138 */     this.iMinDaysInFirstWeek = paramInt;
/*     */   }
/*     */   
/*     */   public DateTimeZone getZone() {
/*     */     Chronology chronology;
/* 143 */     if ((chronology = getBase()) != null) {
/* 144 */       return chronology.getZone();
/*     */     }
/* 146 */     return DateTimeZone.UTC;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getDateTimeMillis(int paramInt1, int paramInt2, int paramInt3, int paramInt4) throws IllegalArgumentException {
/*     */     Chronology chronology;
/* 154 */     if ((chronology = getBase()) != null) {
/* 155 */       return chronology.getDateTimeMillis(paramInt1, paramInt2, paramInt3, paramInt4);
/*     */     }
/*     */     
/* 158 */     FieldUtils.verifyValueBounds(DateTimeFieldType.millisOfDay(), paramInt4, 0, 86399999);
/*     */     
/* 160 */     return getDateTimeMillis0(paramInt1, paramInt2, paramInt3, paramInt4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getDateTimeMillis(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7) throws IllegalArgumentException {
/*     */     Chronology chronology;
/* 169 */     if ((chronology = getBase()) != null) {
/* 170 */       return chronology.getDateTimeMillis(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7);
/*     */     }
/*     */ 
/*     */     
/* 174 */     FieldUtils.verifyValueBounds(DateTimeFieldType.hourOfDay(), paramInt4, 0, 23);
/* 175 */     FieldUtils.verifyValueBounds(DateTimeFieldType.minuteOfHour(), paramInt5, 0, 59);
/* 176 */     FieldUtils.verifyValueBounds(DateTimeFieldType.secondOfMinute(), paramInt6, 0, 59);
/* 177 */     FieldUtils.verifyValueBounds(DateTimeFieldType.millisOfSecond(), paramInt7, 0, 999);
/* 178 */     long l = (paramInt4 * 3600000 + paramInt5 * 60000 + paramInt6 * 1000 + paramInt7);
/*     */ 
/*     */ 
/*     */     
/* 182 */     return getDateTimeMillis0(paramInt1, paramInt2, paramInt3, (int)l);
/*     */   }
/*     */   
/*     */   private long getDateTimeMillis0(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 186 */     long l1 = getDateMidnightMillis(paramInt1, paramInt2, paramInt3);
/*     */     
/* 188 */     if (l1 == Long.MIN_VALUE) {
/* 189 */       l1 = getDateMidnightMillis(paramInt1, paramInt2, paramInt3 + 1);
/* 190 */       paramInt4 -= 86400000;
/*     */     } 
/*     */ 
/*     */     
/* 194 */     long l2 = l1 + paramInt4;
/* 195 */     if (l2 < 0L && l1 > 0L)
/* 196 */       return Long.MAX_VALUE; 
/* 197 */     if (l2 > 0L && l1 < 0L) {
/* 198 */       return Long.MIN_VALUE;
/*     */     }
/* 200 */     return l2;
/*     */   }
/*     */   
/*     */   public int getMinimumDaysInFirstWeek() {
/* 204 */     return this.iMinDaysInFirstWeek;
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
/* 216 */     if (this == paramObject) {
/* 217 */       return true;
/*     */     }
/* 219 */     if (paramObject != null && getClass() == paramObject.getClass()) {
/* 220 */       BasicChronology basicChronology = (BasicChronology)paramObject;
/* 221 */       return (getMinimumDaysInFirstWeek() == basicChronology.getMinimumDaysInFirstWeek() && getZone().equals(basicChronology.getZone()));
/*     */     } 
/*     */     
/* 224 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 234 */     return getClass().getName().hashCode() * 11 + getZone().hashCode() + getMinimumDaysInFirstWeek();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 245 */     StringBuilder stringBuilder = new StringBuilder(60);
/* 246 */     String str = getClass().getName();
/* 247 */     int i = str.lastIndexOf('.');
/* 248 */     if (i >= 0) {
/* 249 */       str = str.substring(i + 1);
/*     */     }
/* 251 */     stringBuilder.append(str);
/* 252 */     stringBuilder.append('[');
/* 253 */     DateTimeZone dateTimeZone = getZone();
/* 254 */     if (dateTimeZone != null) {
/* 255 */       stringBuilder.append(dateTimeZone.getID());
/*     */     }
/* 257 */     if (getMinimumDaysInFirstWeek() != 4) {
/* 258 */       stringBuilder.append(",mdfw=");
/* 259 */       stringBuilder.append(getMinimumDaysInFirstWeek());
/*     */     } 
/* 261 */     stringBuilder.append(']');
/* 262 */     return stringBuilder.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void assemble(AssembledChronology.Fields paramFields) {
/* 269 */     paramFields.millis = cMillisField;
/* 270 */     paramFields.seconds = cSecondsField;
/* 271 */     paramFields.minutes = cMinutesField;
/* 272 */     paramFields.hours = cHoursField;
/* 273 */     paramFields.halfdays = cHalfdaysField;
/* 274 */     paramFields.days = cDaysField;
/* 275 */     paramFields.weeks = cWeeksField;
/*     */     
/* 277 */     paramFields.millisOfSecond = cMillisOfSecondField;
/* 278 */     paramFields.millisOfDay = cMillisOfDayField;
/* 279 */     paramFields.secondOfMinute = cSecondOfMinuteField;
/* 280 */     paramFields.secondOfDay = cSecondOfDayField;
/* 281 */     paramFields.minuteOfHour = cMinuteOfHourField;
/* 282 */     paramFields.minuteOfDay = cMinuteOfDayField;
/* 283 */     paramFields.hourOfDay = cHourOfDayField;
/* 284 */     paramFields.hourOfHalfday = cHourOfHalfdayField;
/* 285 */     paramFields.clockhourOfDay = cClockhourOfDayField;
/* 286 */     paramFields.clockhourOfHalfday = cClockhourOfHalfdayField;
/* 287 */     paramFields.halfdayOfDay = cHalfdayOfDayField;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 292 */     paramFields.year = (DateTimeField)new BasicYearDateTimeField(this);
/* 293 */     paramFields.yearOfEra = (DateTimeField)new GJYearOfEraDateTimeField(paramFields.year, this);
/*     */ 
/*     */     
/* 296 */     OffsetDateTimeField offsetDateTimeField = new OffsetDateTimeField(paramFields.yearOfEra, 99);
/*     */     
/* 298 */     paramFields.centuryOfEra = (DateTimeField)new DividedDateTimeField((DateTimeField)offsetDateTimeField, DateTimeFieldType.centuryOfEra(), 100);
/*     */     
/* 300 */     paramFields.centuries = paramFields.centuryOfEra.getDurationField();
/*     */     
/* 302 */     RemainderDateTimeField remainderDateTimeField = new RemainderDateTimeField((DividedDateTimeField)paramFields.centuryOfEra);
/*     */     
/* 304 */     paramFields.yearOfCentury = (DateTimeField)new OffsetDateTimeField((DateTimeField)remainderDateTimeField, DateTimeFieldType.yearOfCentury(), 1);
/*     */ 
/*     */     
/* 307 */     paramFields.era = (DateTimeField)new GJEraDateTimeField(this);
/* 308 */     paramFields.dayOfWeek = (DateTimeField)new GJDayOfWeekDateTimeField(this, paramFields.days);
/* 309 */     paramFields.dayOfMonth = (DateTimeField)new BasicDayOfMonthDateTimeField(this, paramFields.days);
/* 310 */     paramFields.dayOfYear = (DateTimeField)new BasicDayOfYearDateTimeField(this, paramFields.days);
/* 311 */     paramFields.monthOfYear = (DateTimeField)new GJMonthOfYearDateTimeField(this);
/* 312 */     paramFields.weekyear = (DateTimeField)new BasicWeekyearDateTimeField(this);
/* 313 */     paramFields.weekOfWeekyear = (DateTimeField)new BasicWeekOfWeekyearDateTimeField(this, paramFields.weeks);
/*     */     
/* 315 */     remainderDateTimeField = new RemainderDateTimeField(paramFields.weekyear, paramFields.centuries, DateTimeFieldType.weekyearOfCentury(), 100);
/*     */     
/* 317 */     paramFields.weekyearOfCentury = (DateTimeField)new OffsetDateTimeField((DateTimeField)remainderDateTimeField, DateTimeFieldType.weekyearOfCentury(), 1);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 322 */     paramFields.years = paramFields.year.getDurationField();
/* 323 */     paramFields.months = paramFields.monthOfYear.getDurationField();
/* 324 */     paramFields.weekyears = paramFields.weekyear.getDurationField();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int getDaysInYearMax() {
/* 334 */     return 366;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int getDaysInYear(int paramInt) {
/* 344 */     return isLeapYear(paramInt) ? 366 : 365;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int getWeeksInYear(int paramInt) {
/* 354 */     long l1 = getFirstWeekOfYearMillis(paramInt);
/* 355 */     long l2 = getFirstWeekOfYearMillis(paramInt + 1);
/* 356 */     return (int)((l2 - l1) / 604800000L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   long getFirstWeekOfYearMillis(int paramInt) {
/* 366 */     long l = getYearMillis(paramInt);
/* 367 */     int i = getDayOfWeek(l);
/*     */     
/* 369 */     if (i > 8 - this.iMinDaysInFirstWeek)
/*     */     {
/* 371 */       return l + (8 - i) * 86400000L;
/*     */     }
/*     */ 
/*     */     
/* 375 */     return l - (i - 1) * 86400000L;
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
/*     */   long getYearMillis(int paramInt) {
/* 387 */     return (getYearInfo(paramInt)).iFirstDayMillis;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   long getYearMonthMillis(int paramInt1, int paramInt2) {
/* 398 */     long l = getYearMillis(paramInt1);
/* 399 */     l += getTotalMillisByYearMonth(paramInt1, paramInt2);
/* 400 */     return l;
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
/*     */   long getYearMonthDayMillis(int paramInt1, int paramInt2, int paramInt3) {
/* 412 */     long l = getYearMillis(paramInt1);
/* 413 */     l += getTotalMillisByYearMonth(paramInt1, paramInt2);
/* 414 */     return l + (paramInt3 - 1) * 86400000L;
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
/*     */   int getYear(long paramLong) {
/* 426 */     long l1 = getAverageMillisPerYearDividedByTwo();
/* 427 */     long l2 = (paramLong >> 1L) + getApproxMillisAtEpochDividedByTwo();
/* 428 */     if (l2 < 0L) {
/* 429 */       l2 = l2 - l1 + 1L;
/*     */     }
/* 431 */     int i = (int)(l2 / l1);
/*     */     
/* 433 */     long l3 = getYearMillis(i);
/* 434 */     long l4 = paramLong - l3;
/*     */     
/* 436 */     if (l4 < 0L) {
/* 437 */       i--;
/* 438 */     } else if (l4 >= 31536000000L) {
/*     */       long l;
/*     */       
/* 441 */       if (isLeapYear(i)) {
/* 442 */         l = 31622400000L;
/*     */       } else {
/* 444 */         l = 31536000000L;
/*     */       } 
/*     */       
/* 447 */       l3 += l;
/*     */       
/* 449 */       if (l3 <= paramLong)
/*     */       {
/* 451 */         i++;
/*     */       }
/*     */     } 
/*     */     
/* 455 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int getMonthOfYear(long paramLong) {
/* 462 */     return getMonthOfYear(paramLong, getYear(paramLong));
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
/*     */   int getDayOfMonth(long paramLong) {
/* 475 */     int i = getYear(paramLong);
/* 476 */     int j = getMonthOfYear(paramLong, i);
/* 477 */     return getDayOfMonth(paramLong, i, j);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int getDayOfMonth(long paramLong, int paramInt) {
/* 485 */     int i = getMonthOfYear(paramLong, paramInt);
/* 486 */     return getDayOfMonth(paramLong, paramInt, i);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int getDayOfMonth(long paramLong, int paramInt1, int paramInt2) {
/* 495 */     long l = getYearMillis(paramInt1);
/* 496 */     l += getTotalMillisByYearMonth(paramInt1, paramInt2);
/* 497 */     return (int)((paramLong - l) / 86400000L) + 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int getDayOfYear(long paramLong) {
/* 504 */     return getDayOfYear(paramLong, getYear(paramLong));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int getDayOfYear(long paramLong, int paramInt) {
/* 512 */     long l = getYearMillis(paramInt);
/* 513 */     return (int)((paramLong - l) / 86400000L) + 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int getWeekyear(long paramLong) {
/* 520 */     int i = getYear(paramLong);
/* 521 */     int j = getWeekOfWeekyear(paramLong, i);
/* 522 */     if (j == 1)
/* 523 */       return getYear(paramLong + 604800000L); 
/* 524 */     if (j > 51) {
/* 525 */       return getYear(paramLong - 1209600000L);
/*     */     }
/* 527 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int getWeekOfWeekyear(long paramLong) {
/* 535 */     return getWeekOfWeekyear(paramLong, getYear(paramLong));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int getWeekOfWeekyear(long paramLong, int paramInt) {
/* 543 */     long l1 = getFirstWeekOfYearMillis(paramInt);
/* 544 */     if (paramLong < l1) {
/* 545 */       return getWeeksInYear(paramInt - 1);
/*     */     }
/* 547 */     long l2 = getFirstWeekOfYearMillis(paramInt + 1);
/* 548 */     if (paramLong >= l2) {
/* 549 */       return 1;
/*     */     }
/* 551 */     return (int)((paramLong - l1) / 604800000L) + 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int getDayOfWeek(long paramLong) {
/*     */     long l;
/* 561 */     if (paramLong >= 0L) {
/* 562 */       l = paramLong / 86400000L;
/*     */     } else {
/* 564 */       l = (paramLong - 86399999L) / 86400000L;
/*     */       
/* 566 */       if (l < -3L) {
/* 567 */         return 7 + (int)((l + 4L) % 7L);
/*     */       }
/*     */     } 
/*     */     
/* 571 */     return 1 + (int)((l + 3L) % 7L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int getMillisOfDay(long paramLong) {
/* 578 */     if (paramLong >= 0L) {
/* 579 */       return (int)(paramLong % 86400000L);
/*     */     }
/* 581 */     return 86399999 + (int)((paramLong + 1L) % 86400000L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int getDaysInMonthMax() {
/* 592 */     return 31;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int getDaysInMonthMax(long paramLong) {
/* 602 */     int i = getYear(paramLong);
/* 603 */     int j = getMonthOfYear(paramLong, i);
/* 604 */     return getDaysInYearMonth(i, j);
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
/*     */   int getDaysInMonthMaxForSet(long paramLong, int paramInt) {
/* 617 */     return getDaysInMonthMax(paramLong);
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
/*     */   long getDateMidnightMillis(int paramInt1, int paramInt2, int paramInt3) {
/* 630 */     FieldUtils.verifyValueBounds(DateTimeFieldType.year(), paramInt1, getMinYear() - 1, getMaxYear() + 1);
/* 631 */     FieldUtils.verifyValueBounds(DateTimeFieldType.monthOfYear(), paramInt2, 1, getMaxMonth(paramInt1));
/* 632 */     FieldUtils.verifyValueBounds(DateTimeFieldType.dayOfMonth(), paramInt3, 1, getDaysInYearMonth(paramInt1, paramInt2));
/* 633 */     long l = getYearMonthDayMillis(paramInt1, paramInt2, paramInt3);
/*     */     
/* 635 */     if (l < 0L && paramInt1 == getMaxYear() + 1)
/* 636 */       return Long.MAX_VALUE; 
/* 637 */     if (l > 0L && paramInt1 == getMinYear() - 1) {
/* 638 */       return Long.MIN_VALUE;
/*     */     }
/* 640 */     return l;
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
/*     */   boolean isLeapDay(long paramLong) {
/* 667 */     return false;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int getMaxMonth(int paramInt) {
/* 726 */     return getMaxMonth();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int getMaxMonth() {
/* 735 */     return 12;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private YearInfo getYearInfo(int paramInt) {
/* 782 */     YearInfo yearInfo = this.iYearInfoCache[paramInt & 0x3FF];
/* 783 */     if (yearInfo == null || yearInfo.iYear != paramInt) {
/* 784 */       yearInfo = new YearInfo(paramInt, calculateFirstDayOfYearMillis(paramInt));
/* 785 */       this.iYearInfoCache[paramInt & 0x3FF] = yearInfo;
/*     */     } 
/* 787 */     return yearInfo;
/*     */   } abstract int getMonthOfYear(long paramLong, int paramInt); abstract long getYearDifference(long paramLong1, long paramLong2); abstract boolean isLeapYear(int paramInt); abstract int getDaysInYearMonth(int paramInt1, int paramInt2); abstract int getDaysInMonthMax(int paramInt); abstract long getTotalMillisByYearMonth(int paramInt1, int paramInt2); abstract long calculateFirstDayOfYearMillis(int paramInt); abstract int getMinYear(); abstract int getMaxYear();
/*     */   abstract long getAverageMillisPerYear();
/*     */   abstract long getAverageMillisPerYearDividedByTwo();
/*     */   abstract long getAverageMillisPerMonth();
/*     */   abstract long getApproxMillisAtEpochDividedByTwo();
/*     */   abstract long setYear(long paramLong, int paramInt);
/*     */   private static class HalfdayField extends PreciseDateTimeField { HalfdayField() {
/* 795 */       super(DateTimeFieldType.halfdayOfDay(), BasicChronology.cHalfdaysField, BasicChronology.cDaysField);
/*     */     }
/*     */     private static final long serialVersionUID = 581601443656929254L;
/*     */     public String getAsText(int param1Int, Locale param1Locale) {
/* 799 */       return GJLocaleSymbols.forLocale(param1Locale).halfdayValueToText(param1Int);
/*     */     }
/*     */     
/*     */     public long set(long param1Long, String param1String, Locale param1Locale) {
/* 803 */       return set(param1Long, GJLocaleSymbols.forLocale(param1Locale).halfdayTextToValue(param1String));
/*     */     }
/*     */     
/*     */     public int getMaximumTextLength(Locale param1Locale) {
/* 807 */       return GJLocaleSymbols.forLocale(param1Locale).getHalfdayMaxTextLength();
/*     */     } }
/*     */ 
/*     */   
/*     */   private static class YearInfo {
/*     */     public final int iYear;
/*     */     public final long iFirstDayMillis;
/*     */     
/*     */     YearInfo(int param1Int, long param1Long) {
/* 816 */       this.iYear = param1Int;
/* 817 */       this.iFirstDayMillis = param1Long;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/chrono/BasicChronology.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */