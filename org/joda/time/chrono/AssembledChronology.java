/*     */ package org.joda.time.chrono;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import org.joda.time.Chronology;
/*     */ import org.joda.time.DateTimeField;
/*     */ import org.joda.time.DateTimeZone;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AssembledChronology
/*     */   extends BaseChronology
/*     */ {
/*     */   private static final long serialVersionUID = -6728465968995518215L;
/*     */   private final Chronology iBase;
/*     */   private final Object iParam;
/*     */   private transient DurationField iMillis;
/*     */   private transient DurationField iSeconds;
/*     */   private transient DurationField iMinutes;
/*     */   private transient DurationField iHours;
/*     */   private transient DurationField iHalfdays;
/*     */   private transient DurationField iDays;
/*     */   private transient DurationField iWeeks;
/*     */   private transient DurationField iWeekyears;
/*     */   private transient DurationField iMonths;
/*     */   private transient DurationField iYears;
/*     */   private transient DurationField iCenturies;
/*     */   private transient DurationField iEras;
/*     */   private transient DateTimeField iMillisOfSecond;
/*     */   private transient DateTimeField iMillisOfDay;
/*     */   private transient DateTimeField iSecondOfMinute;
/*     */   private transient DateTimeField iSecondOfDay;
/*     */   private transient DateTimeField iMinuteOfHour;
/*     */   private transient DateTimeField iMinuteOfDay;
/*     */   private transient DateTimeField iHourOfDay;
/*     */   private transient DateTimeField iClockhourOfDay;
/*     */   private transient DateTimeField iHourOfHalfday;
/*     */   private transient DateTimeField iClockhourOfHalfday;
/*     */   private transient DateTimeField iHalfdayOfDay;
/*     */   private transient DateTimeField iDayOfWeek;
/*     */   private transient DateTimeField iDayOfMonth;
/*     */   private transient DateTimeField iDayOfYear;
/*     */   private transient DateTimeField iWeekOfWeekyear;
/*     */   private transient DateTimeField iWeekyear;
/*     */   private transient DateTimeField iWeekyearOfCentury;
/*     */   private transient DateTimeField iMonthOfYear;
/*     */   private transient DateTimeField iYear;
/*     */   private transient DateTimeField iYearOfEra;
/*     */   private transient DateTimeField iYearOfCentury;
/*     */   private transient DateTimeField iCenturyOfEra;
/*     */   private transient DateTimeField iEra;
/*     */   private transient int iBaseFlags;
/*     */   
/*     */   protected AssembledChronology(Chronology paramChronology, Object paramObject) {
/* 100 */     this.iBase = paramChronology;
/* 101 */     this.iParam = paramObject;
/* 102 */     setFields();
/*     */   }
/*     */   
/*     */   public DateTimeZone getZone() {
/*     */     Chronology chronology;
/* 107 */     if ((chronology = this.iBase) != null) {
/* 108 */       return chronology.getZone();
/*     */     }
/* 110 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getDateTimeMillis(int paramInt1, int paramInt2, int paramInt3, int paramInt4) throws IllegalArgumentException {
/*     */     Chronology chronology;
/* 118 */     if ((chronology = this.iBase) != null && (this.iBaseFlags & 0x6) == 6)
/*     */     {
/* 120 */       return chronology.getDateTimeMillis(paramInt1, paramInt2, paramInt3, paramInt4);
/*     */     }
/* 122 */     return super.getDateTimeMillis(paramInt1, paramInt2, paramInt3, paramInt4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getDateTimeMillis(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7) throws IllegalArgumentException {
/*     */     Chronology chronology;
/* 131 */     if ((chronology = this.iBase) != null && (this.iBaseFlags & 0x5) == 5)
/*     */     {
/* 133 */       return chronology.getDateTimeMillis(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7);
/*     */     }
/*     */     
/* 136 */     return super.getDateTimeMillis(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getDateTimeMillis(long paramLong, int paramInt1, int paramInt2, int paramInt3, int paramInt4) throws IllegalArgumentException {
/*     */     Chronology chronology;
/* 146 */     if ((chronology = this.iBase) != null && (this.iBaseFlags & 0x1) == 1)
/*     */     {
/* 148 */       return chronology.getDateTimeMillis(paramLong, paramInt1, paramInt2, paramInt3, paramInt4);
/*     */     }
/*     */     
/* 151 */     return super.getDateTimeMillis(paramLong, paramInt1, paramInt2, paramInt3, paramInt4);
/*     */   }
/*     */ 
/*     */   
/*     */   public final DurationField millis() {
/* 156 */     return this.iMillis;
/*     */   }
/*     */   
/*     */   public final DateTimeField millisOfSecond() {
/* 160 */     return this.iMillisOfSecond;
/*     */   }
/*     */   
/*     */   public final DateTimeField millisOfDay() {
/* 164 */     return this.iMillisOfDay;
/*     */   }
/*     */   
/*     */   public final DurationField seconds() {
/* 168 */     return this.iSeconds;
/*     */   }
/*     */   
/*     */   public final DateTimeField secondOfMinute() {
/* 172 */     return this.iSecondOfMinute;
/*     */   }
/*     */   
/*     */   public final DateTimeField secondOfDay() {
/* 176 */     return this.iSecondOfDay;
/*     */   }
/*     */   
/*     */   public final DurationField minutes() {
/* 180 */     return this.iMinutes;
/*     */   }
/*     */   
/*     */   public final DateTimeField minuteOfHour() {
/* 184 */     return this.iMinuteOfHour;
/*     */   }
/*     */   
/*     */   public final DateTimeField minuteOfDay() {
/* 188 */     return this.iMinuteOfDay;
/*     */   }
/*     */   
/*     */   public final DurationField hours() {
/* 192 */     return this.iHours;
/*     */   }
/*     */   
/*     */   public final DateTimeField hourOfDay() {
/* 196 */     return this.iHourOfDay;
/*     */   }
/*     */   
/*     */   public final DateTimeField clockhourOfDay() {
/* 200 */     return this.iClockhourOfDay;
/*     */   }
/*     */   
/*     */   public final DurationField halfdays() {
/* 204 */     return this.iHalfdays;
/*     */   }
/*     */   
/*     */   public final DateTimeField hourOfHalfday() {
/* 208 */     return this.iHourOfHalfday;
/*     */   }
/*     */   
/*     */   public final DateTimeField clockhourOfHalfday() {
/* 212 */     return this.iClockhourOfHalfday;
/*     */   }
/*     */   
/*     */   public final DateTimeField halfdayOfDay() {
/* 216 */     return this.iHalfdayOfDay;
/*     */   }
/*     */   
/*     */   public final DurationField days() {
/* 220 */     return this.iDays;
/*     */   }
/*     */   
/*     */   public final DateTimeField dayOfWeek() {
/* 224 */     return this.iDayOfWeek;
/*     */   }
/*     */   
/*     */   public final DateTimeField dayOfMonth() {
/* 228 */     return this.iDayOfMonth;
/*     */   }
/*     */   
/*     */   public final DateTimeField dayOfYear() {
/* 232 */     return this.iDayOfYear;
/*     */   }
/*     */   
/*     */   public final DurationField weeks() {
/* 236 */     return this.iWeeks;
/*     */   }
/*     */   
/*     */   public final DateTimeField weekOfWeekyear() {
/* 240 */     return this.iWeekOfWeekyear;
/*     */   }
/*     */   
/*     */   public final DurationField weekyears() {
/* 244 */     return this.iWeekyears;
/*     */   }
/*     */   
/*     */   public final DateTimeField weekyear() {
/* 248 */     return this.iWeekyear;
/*     */   }
/*     */   
/*     */   public final DateTimeField weekyearOfCentury() {
/* 252 */     return this.iWeekyearOfCentury;
/*     */   }
/*     */   
/*     */   public final DurationField months() {
/* 256 */     return this.iMonths;
/*     */   }
/*     */   
/*     */   public final DateTimeField monthOfYear() {
/* 260 */     return this.iMonthOfYear;
/*     */   }
/*     */   
/*     */   public final DurationField years() {
/* 264 */     return this.iYears;
/*     */   }
/*     */   
/*     */   public final DateTimeField year() {
/* 268 */     return this.iYear;
/*     */   }
/*     */   
/*     */   public final DateTimeField yearOfEra() {
/* 272 */     return this.iYearOfEra;
/*     */   }
/*     */   
/*     */   public final DateTimeField yearOfCentury() {
/* 276 */     return this.iYearOfCentury;
/*     */   }
/*     */   
/*     */   public final DurationField centuries() {
/* 280 */     return this.iCenturies;
/*     */   }
/*     */   
/*     */   public final DateTimeField centuryOfEra() {
/* 284 */     return this.iCenturyOfEra;
/*     */   }
/*     */   
/*     */   public final DurationField eras() {
/* 288 */     return this.iEras;
/*     */   }
/*     */   
/*     */   public final DateTimeField era() {
/* 292 */     return this.iEra;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void assemble(Fields paramFields);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final Chronology getBase() {
/* 308 */     return this.iBase;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final Object getParam() {
/* 315 */     return this.iParam;
/*     */   }
/*     */   private void setFields() {
/*     */     int i;
/* 319 */     Fields fields = new Fields();
/* 320 */     if (this.iBase != null) {
/* 321 */       fields.copyFieldsFrom(this.iBase);
/*     */     }
/* 323 */     assemble(fields);
/*     */     
/*     */     DurationField durationField;
/*     */     
/* 327 */     this.iMillis = ((durationField = fields.millis) != null) ? durationField : super.millis();
/* 328 */     this.iSeconds = ((durationField = fields.seconds) != null) ? durationField : super.seconds();
/* 329 */     this.iMinutes = ((durationField = fields.minutes) != null) ? durationField : super.minutes();
/* 330 */     this.iHours = ((durationField = fields.hours) != null) ? durationField : super.hours();
/* 331 */     this.iHalfdays = ((durationField = fields.halfdays) != null) ? durationField : super.halfdays();
/* 332 */     this.iDays = ((durationField = fields.days) != null) ? durationField : super.days();
/* 333 */     this.iWeeks = ((durationField = fields.weeks) != null) ? durationField : super.weeks();
/* 334 */     this.iWeekyears = ((durationField = fields.weekyears) != null) ? durationField : super.weekyears();
/* 335 */     this.iMonths = ((durationField = fields.months) != null) ? durationField : super.months();
/* 336 */     this.iYears = ((durationField = fields.years) != null) ? durationField : super.years();
/* 337 */     this.iCenturies = ((durationField = fields.centuries) != null) ? durationField : super.centuries();
/* 338 */     this.iEras = ((durationField = fields.eras) != null) ? durationField : super.eras();
/*     */ 
/*     */     
/*     */     DateTimeField dateTimeField;
/*     */     
/* 343 */     this.iMillisOfSecond = ((dateTimeField = fields.millisOfSecond) != null) ? dateTimeField : super.millisOfSecond();
/* 344 */     this.iMillisOfDay = ((dateTimeField = fields.millisOfDay) != null) ? dateTimeField : super.millisOfDay();
/* 345 */     this.iSecondOfMinute = ((dateTimeField = fields.secondOfMinute) != null) ? dateTimeField : super.secondOfMinute();
/* 346 */     this.iSecondOfDay = ((dateTimeField = fields.secondOfDay) != null) ? dateTimeField : super.secondOfDay();
/* 347 */     this.iMinuteOfHour = ((dateTimeField = fields.minuteOfHour) != null) ? dateTimeField : super.minuteOfHour();
/* 348 */     this.iMinuteOfDay = ((dateTimeField = fields.minuteOfDay) != null) ? dateTimeField : super.minuteOfDay();
/* 349 */     this.iHourOfDay = ((dateTimeField = fields.hourOfDay) != null) ? dateTimeField : super.hourOfDay();
/* 350 */     this.iClockhourOfDay = ((dateTimeField = fields.clockhourOfDay) != null) ? dateTimeField : super.clockhourOfDay();
/* 351 */     this.iHourOfHalfday = ((dateTimeField = fields.hourOfHalfday) != null) ? dateTimeField : super.hourOfHalfday();
/* 352 */     this.iClockhourOfHalfday = ((dateTimeField = fields.clockhourOfHalfday) != null) ? dateTimeField : super.clockhourOfHalfday();
/* 353 */     this.iHalfdayOfDay = ((dateTimeField = fields.halfdayOfDay) != null) ? dateTimeField : super.halfdayOfDay();
/* 354 */     this.iDayOfWeek = ((dateTimeField = fields.dayOfWeek) != null) ? dateTimeField : super.dayOfWeek();
/* 355 */     this.iDayOfMonth = ((dateTimeField = fields.dayOfMonth) != null) ? dateTimeField : super.dayOfMonth();
/* 356 */     this.iDayOfYear = ((dateTimeField = fields.dayOfYear) != null) ? dateTimeField : super.dayOfYear();
/* 357 */     this.iWeekOfWeekyear = ((dateTimeField = fields.weekOfWeekyear) != null) ? dateTimeField : super.weekOfWeekyear();
/* 358 */     this.iWeekyear = ((dateTimeField = fields.weekyear) != null) ? dateTimeField : super.weekyear();
/* 359 */     this.iWeekyearOfCentury = ((dateTimeField = fields.weekyearOfCentury) != null) ? dateTimeField : super.weekyearOfCentury();
/* 360 */     this.iMonthOfYear = ((dateTimeField = fields.monthOfYear) != null) ? dateTimeField : super.monthOfYear();
/* 361 */     this.iYear = ((dateTimeField = fields.year) != null) ? dateTimeField : super.year();
/* 362 */     this.iYearOfEra = ((dateTimeField = fields.yearOfEra) != null) ? dateTimeField : super.yearOfEra();
/* 363 */     this.iYearOfCentury = ((dateTimeField = fields.yearOfCentury) != null) ? dateTimeField : super.yearOfCentury();
/* 364 */     this.iCenturyOfEra = ((dateTimeField = fields.centuryOfEra) != null) ? dateTimeField : super.centuryOfEra();
/* 365 */     this.iEra = ((dateTimeField = fields.era) != null) ? dateTimeField : super.era();
/*     */ 
/*     */ 
/*     */     
/* 369 */     if (this.iBase == null) {
/* 370 */       i = 0;
/*     */     } else {
/* 372 */       i = ((this.iHourOfDay == this.iBase.hourOfDay() && this.iMinuteOfHour == this.iBase.minuteOfHour() && this.iSecondOfMinute == this.iBase.secondOfMinute() && this.iMillisOfSecond == this.iBase.millisOfSecond()) ? 1 : 0) | ((this.iMillisOfDay == this.iBase.millisOfDay()) ? 2 : 0) | ((this.iYear == this.iBase.year() && this.iMonthOfYear == this.iBase.monthOfYear() && this.iDayOfMonth == this.iBase.dayOfMonth()) ? 4 : 0);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 385 */     this.iBaseFlags = i;
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 389 */     paramObjectInputStream.defaultReadObject();
/* 390 */     setFields();
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class Fields
/*     */   {
/*     */     public DurationField millis;
/*     */     
/*     */     public DurationField seconds;
/*     */     
/*     */     public DurationField minutes;
/*     */     
/*     */     public DurationField hours;
/*     */     
/*     */     public DurationField halfdays;
/*     */     
/*     */     public DurationField days;
/*     */     
/*     */     public DurationField weeks;
/*     */     
/*     */     public DurationField weekyears;
/*     */     
/*     */     public DurationField months;
/*     */     
/*     */     public DurationField years;
/*     */     
/*     */     public DurationField centuries;
/*     */     
/*     */     public DurationField eras;
/*     */     public DateTimeField millisOfSecond;
/*     */     public DateTimeField millisOfDay;
/*     */     public DateTimeField secondOfMinute;
/*     */     public DateTimeField secondOfDay;
/*     */     public DateTimeField minuteOfHour;
/*     */     public DateTimeField minuteOfDay;
/*     */     public DateTimeField hourOfDay;
/*     */     public DateTimeField clockhourOfDay;
/*     */     public DateTimeField hourOfHalfday;
/*     */     public DateTimeField clockhourOfHalfday;
/*     */     public DateTimeField halfdayOfDay;
/*     */     public DateTimeField dayOfWeek;
/*     */     public DateTimeField dayOfMonth;
/*     */     public DateTimeField dayOfYear;
/*     */     public DateTimeField weekOfWeekyear;
/*     */     public DateTimeField weekyear;
/*     */     public DateTimeField weekyearOfCentury;
/*     */     public DateTimeField monthOfYear;
/*     */     public DateTimeField year;
/*     */     public DateTimeField yearOfEra;
/*     */     public DateTimeField yearOfCentury;
/*     */     public DateTimeField centuryOfEra;
/*     */     public DateTimeField era;
/*     */     
/*     */     public void copyFieldsFrom(Chronology param1Chronology) {
/*     */       DurationField durationField;
/* 445 */       if (isSupported(durationField = param1Chronology.millis())) {
/* 446 */         this.millis = durationField;
/*     */       }
/* 448 */       if (isSupported(durationField = param1Chronology.seconds())) {
/* 449 */         this.seconds = durationField;
/*     */       }
/* 451 */       if (isSupported(durationField = param1Chronology.minutes())) {
/* 452 */         this.minutes = durationField;
/*     */       }
/* 454 */       if (isSupported(durationField = param1Chronology.hours())) {
/* 455 */         this.hours = durationField;
/*     */       }
/* 457 */       if (isSupported(durationField = param1Chronology.halfdays())) {
/* 458 */         this.halfdays = durationField;
/*     */       }
/* 460 */       if (isSupported(durationField = param1Chronology.days())) {
/* 461 */         this.days = durationField;
/*     */       }
/* 463 */       if (isSupported(durationField = param1Chronology.weeks())) {
/* 464 */         this.weeks = durationField;
/*     */       }
/* 466 */       if (isSupported(durationField = param1Chronology.weekyears())) {
/* 467 */         this.weekyears = durationField;
/*     */       }
/* 469 */       if (isSupported(durationField = param1Chronology.months())) {
/* 470 */         this.months = durationField;
/*     */       }
/* 472 */       if (isSupported(durationField = param1Chronology.years())) {
/* 473 */         this.years = durationField;
/*     */       }
/* 475 */       if (isSupported(durationField = param1Chronology.centuries())) {
/* 476 */         this.centuries = durationField;
/*     */       }
/* 478 */       if (isSupported(durationField = param1Chronology.eras())) {
/* 479 */         this.eras = durationField;
/*     */       }
/*     */ 
/*     */       
/*     */       DateTimeField dateTimeField;
/*     */       
/* 485 */       if (isSupported(dateTimeField = param1Chronology.millisOfSecond())) {
/* 486 */         this.millisOfSecond = dateTimeField;
/*     */       }
/* 488 */       if (isSupported(dateTimeField = param1Chronology.millisOfDay())) {
/* 489 */         this.millisOfDay = dateTimeField;
/*     */       }
/* 491 */       if (isSupported(dateTimeField = param1Chronology.secondOfMinute())) {
/* 492 */         this.secondOfMinute = dateTimeField;
/*     */       }
/* 494 */       if (isSupported(dateTimeField = param1Chronology.secondOfDay())) {
/* 495 */         this.secondOfDay = dateTimeField;
/*     */       }
/* 497 */       if (isSupported(dateTimeField = param1Chronology.minuteOfHour())) {
/* 498 */         this.minuteOfHour = dateTimeField;
/*     */       }
/* 500 */       if (isSupported(dateTimeField = param1Chronology.minuteOfDay())) {
/* 501 */         this.minuteOfDay = dateTimeField;
/*     */       }
/* 503 */       if (isSupported(dateTimeField = param1Chronology.hourOfDay())) {
/* 504 */         this.hourOfDay = dateTimeField;
/*     */       }
/* 506 */       if (isSupported(dateTimeField = param1Chronology.clockhourOfDay())) {
/* 507 */         this.clockhourOfDay = dateTimeField;
/*     */       }
/* 509 */       if (isSupported(dateTimeField = param1Chronology.hourOfHalfday())) {
/* 510 */         this.hourOfHalfday = dateTimeField;
/*     */       }
/* 512 */       if (isSupported(dateTimeField = param1Chronology.clockhourOfHalfday())) {
/* 513 */         this.clockhourOfHalfday = dateTimeField;
/*     */       }
/* 515 */       if (isSupported(dateTimeField = param1Chronology.halfdayOfDay())) {
/* 516 */         this.halfdayOfDay = dateTimeField;
/*     */       }
/* 518 */       if (isSupported(dateTimeField = param1Chronology.dayOfWeek())) {
/* 519 */         this.dayOfWeek = dateTimeField;
/*     */       }
/* 521 */       if (isSupported(dateTimeField = param1Chronology.dayOfMonth())) {
/* 522 */         this.dayOfMonth = dateTimeField;
/*     */       }
/* 524 */       if (isSupported(dateTimeField = param1Chronology.dayOfYear())) {
/* 525 */         this.dayOfYear = dateTimeField;
/*     */       }
/* 527 */       if (isSupported(dateTimeField = param1Chronology.weekOfWeekyear())) {
/* 528 */         this.weekOfWeekyear = dateTimeField;
/*     */       }
/* 530 */       if (isSupported(dateTimeField = param1Chronology.weekyear())) {
/* 531 */         this.weekyear = dateTimeField;
/*     */       }
/* 533 */       if (isSupported(dateTimeField = param1Chronology.weekyearOfCentury())) {
/* 534 */         this.weekyearOfCentury = dateTimeField;
/*     */       }
/* 536 */       if (isSupported(dateTimeField = param1Chronology.monthOfYear())) {
/* 537 */         this.monthOfYear = dateTimeField;
/*     */       }
/* 539 */       if (isSupported(dateTimeField = param1Chronology.year())) {
/* 540 */         this.year = dateTimeField;
/*     */       }
/* 542 */       if (isSupported(dateTimeField = param1Chronology.yearOfEra())) {
/* 543 */         this.yearOfEra = dateTimeField;
/*     */       }
/* 545 */       if (isSupported(dateTimeField = param1Chronology.yearOfCentury())) {
/* 546 */         this.yearOfCentury = dateTimeField;
/*     */       }
/* 548 */       if (isSupported(dateTimeField = param1Chronology.centuryOfEra())) {
/* 549 */         this.centuryOfEra = dateTimeField;
/*     */       }
/* 551 */       if (isSupported(dateTimeField = param1Chronology.era())) {
/* 552 */         this.era = dateTimeField;
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     private static boolean isSupported(DurationField param1DurationField) {
/* 558 */       return (param1DurationField == null) ? false : param1DurationField.isSupported();
/*     */     }
/*     */     
/*     */     private static boolean isSupported(DateTimeField param1DateTimeField) {
/* 562 */       return (param1DateTimeField == null) ? false : param1DateTimeField.isSupported();
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/chrono/AssembledChronology.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */