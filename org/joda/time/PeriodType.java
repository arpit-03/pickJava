/*     */ package org.joda.time;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
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
/*     */ public class PeriodType
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 2274324892792009998L;
/*  56 */   private static final Map<PeriodType, Object> cTypes = new HashMap<PeriodType, Object>(32);
/*     */   
/*  58 */   static int YEAR_INDEX = 0;
/*  59 */   static int MONTH_INDEX = 1;
/*  60 */   static int WEEK_INDEX = 2;
/*  61 */   static int DAY_INDEX = 3;
/*  62 */   static int HOUR_INDEX = 4;
/*  63 */   static int MINUTE_INDEX = 5;
/*  64 */   static int SECOND_INDEX = 6;
/*  65 */   static int MILLI_INDEX = 7;
/*     */   
/*     */   private static PeriodType cStandard;
/*     */   
/*     */   private static PeriodType cYMDTime;
/*     */   
/*     */   private static PeriodType cYMD;
/*     */   
/*     */   private static PeriodType cYWDTime;
/*     */   
/*     */   private static PeriodType cYWD;
/*     */   
/*     */   private static PeriodType cYDTime;
/*     */   
/*     */   private static PeriodType cYD;
/*     */   
/*     */   private static PeriodType cDTime;
/*     */   
/*     */   private static PeriodType cTime;
/*     */   
/*     */   private static PeriodType cYears;
/*     */   
/*     */   private static PeriodType cMonths;
/*     */   
/*     */   private static PeriodType cWeeks;
/*     */   
/*     */   private static PeriodType cDays;
/*     */   
/*     */   private static PeriodType cHours;
/*     */   private static PeriodType cMinutes;
/*     */   private static PeriodType cSeconds;
/*     */   private static PeriodType cMillis;
/*     */   private final String iName;
/*     */   private final DurationFieldType[] iTypes;
/*     */   private final int[] iIndices;
/*     */   
/*     */   public static PeriodType standard() {
/* 102 */     PeriodType periodType = cStandard;
/* 103 */     if (periodType == null) {
/* 104 */       periodType = new PeriodType("Standard", new DurationFieldType[] { DurationFieldType.years(), DurationFieldType.months(), DurationFieldType.weeks(), DurationFieldType.days(), DurationFieldType.hours(), DurationFieldType.minutes(), DurationFieldType.seconds(), DurationFieldType.millis() }, new int[] { 0, 1, 2, 3, 4, 5, 6, 7 });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 114 */       cStandard = periodType;
/*     */     } 
/* 116 */     return periodType;
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
/*     */   public static PeriodType yearMonthDayTime() {
/* 134 */     PeriodType periodType = cYMDTime;
/* 135 */     if (periodType == null) {
/* 136 */       periodType = new PeriodType("YearMonthDayTime", new DurationFieldType[] { DurationFieldType.years(), DurationFieldType.months(), DurationFieldType.days(), DurationFieldType.hours(), DurationFieldType.minutes(), DurationFieldType.seconds(), DurationFieldType.millis() }, new int[] { 0, 1, -1, 2, 3, 4, 5, 6 });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 146 */       cYMDTime = periodType;
/*     */     } 
/* 148 */     return periodType;
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
/*     */   public static PeriodType yearMonthDay() {
/* 163 */     PeriodType periodType = cYMD;
/* 164 */     if (periodType == null) {
/* 165 */       periodType = new PeriodType("YearMonthDay", new DurationFieldType[] { DurationFieldType.years(), DurationFieldType.months(), DurationFieldType.days() }, new int[] { 0, 1, -1, 2, -1, -1, -1, -1 });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 173 */       cYMD = periodType;
/*     */     } 
/* 175 */     return periodType;
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
/*     */   public static PeriodType yearWeekDayTime() {
/* 193 */     PeriodType periodType = cYWDTime;
/* 194 */     if (periodType == null) {
/* 195 */       periodType = new PeriodType("YearWeekDayTime", new DurationFieldType[] { DurationFieldType.years(), DurationFieldType.weeks(), DurationFieldType.days(), DurationFieldType.hours(), DurationFieldType.minutes(), DurationFieldType.seconds(), DurationFieldType.millis() }, new int[] { 0, -1, 1, 2, 3, 4, 5, 6 });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 205 */       cYWDTime = periodType;
/*     */     } 
/* 207 */     return periodType;
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
/*     */   public static PeriodType yearWeekDay() {
/* 222 */     PeriodType periodType = cYWD;
/* 223 */     if (periodType == null) {
/* 224 */       periodType = new PeriodType("YearWeekDay", new DurationFieldType[] { DurationFieldType.years(), DurationFieldType.weeks(), DurationFieldType.days() }, new int[] { 0, -1, 1, 2, -1, -1, -1, -1 });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 232 */       cYWD = periodType;
/*     */     } 
/* 234 */     return periodType;
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
/*     */   public static PeriodType yearDayTime() {
/* 251 */     PeriodType periodType = cYDTime;
/* 252 */     if (periodType == null) {
/* 253 */       periodType = new PeriodType("YearDayTime", new DurationFieldType[] { DurationFieldType.years(), DurationFieldType.days(), DurationFieldType.hours(), DurationFieldType.minutes(), DurationFieldType.seconds(), DurationFieldType.millis() }, new int[] { 0, -1, -1, 1, 2, 3, 4, 5 });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 262 */       cYDTime = periodType;
/*     */     } 
/* 264 */     return periodType;
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
/*     */   public static PeriodType yearDay() {
/* 278 */     PeriodType periodType = cYD;
/* 279 */     if (periodType == null) {
/* 280 */       periodType = new PeriodType("YearDay", new DurationFieldType[] { DurationFieldType.years(), DurationFieldType.days() }, new int[] { 0, -1, -1, 1, -1, -1, -1, -1 });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 287 */       cYD = periodType;
/*     */     } 
/* 289 */     return periodType;
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
/*     */   public static PeriodType dayTime() {
/* 305 */     PeriodType periodType = cDTime;
/* 306 */     if (periodType == null) {
/* 307 */       periodType = new PeriodType("DayTime", new DurationFieldType[] { DurationFieldType.days(), DurationFieldType.hours(), DurationFieldType.minutes(), DurationFieldType.seconds(), DurationFieldType.millis() }, new int[] { -1, -1, -1, 0, 1, 2, 3, 4 });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 316 */       cDTime = periodType;
/*     */     } 
/* 318 */     return periodType;
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
/*     */   public static PeriodType time() {
/* 333 */     PeriodType periodType = cTime;
/* 334 */     if (periodType == null) {
/* 335 */       periodType = new PeriodType("Time", new DurationFieldType[] { DurationFieldType.hours(), DurationFieldType.minutes(), DurationFieldType.seconds(), DurationFieldType.millis() }, new int[] { -1, -1, -1, -1, 0, 1, 2, 3 });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 343 */       cTime = periodType;
/*     */     } 
/* 345 */     return periodType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PeriodType years() {
/* 354 */     PeriodType periodType = cYears;
/* 355 */     if (periodType == null) {
/* 356 */       periodType = new PeriodType("Years", new DurationFieldType[] { DurationFieldType.years() }, new int[] { 0, -1, -1, -1, -1, -1, -1, -1 });
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 361 */       cYears = periodType;
/*     */     } 
/* 363 */     return periodType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PeriodType months() {
/* 372 */     PeriodType periodType = cMonths;
/* 373 */     if (periodType == null) {
/* 374 */       periodType = new PeriodType("Months", new DurationFieldType[] { DurationFieldType.months() }, new int[] { -1, 0, -1, -1, -1, -1, -1, -1 });
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 379 */       cMonths = periodType;
/*     */     } 
/* 381 */     return periodType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PeriodType weeks() {
/* 390 */     PeriodType periodType = cWeeks;
/* 391 */     if (periodType == null) {
/* 392 */       periodType = new PeriodType("Weeks", new DurationFieldType[] { DurationFieldType.weeks() }, new int[] { -1, -1, 0, -1, -1, -1, -1, -1 });
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 397 */       cWeeks = periodType;
/*     */     } 
/* 399 */     return periodType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PeriodType days() {
/* 408 */     PeriodType periodType = cDays;
/* 409 */     if (periodType == null) {
/* 410 */       periodType = new PeriodType("Days", new DurationFieldType[] { DurationFieldType.days() }, new int[] { -1, -1, -1, 0, -1, -1, -1, -1 });
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 415 */       cDays = periodType;
/*     */     } 
/* 417 */     return periodType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PeriodType hours() {
/* 426 */     PeriodType periodType = cHours;
/* 427 */     if (periodType == null) {
/* 428 */       periodType = new PeriodType("Hours", new DurationFieldType[] { DurationFieldType.hours() }, new int[] { -1, -1, -1, -1, 0, -1, -1, -1 });
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 433 */       cHours = periodType;
/*     */     } 
/* 435 */     return periodType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PeriodType minutes() {
/* 444 */     PeriodType periodType = cMinutes;
/* 445 */     if (periodType == null) {
/* 446 */       periodType = new PeriodType("Minutes", new DurationFieldType[] { DurationFieldType.minutes() }, new int[] { -1, -1, -1, -1, -1, 0, -1, -1 });
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 451 */       cMinutes = periodType;
/*     */     } 
/* 453 */     return periodType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PeriodType seconds() {
/* 462 */     PeriodType periodType = cSeconds;
/* 463 */     if (periodType == null) {
/* 464 */       periodType = new PeriodType("Seconds", new DurationFieldType[] { DurationFieldType.seconds() }, new int[] { -1, -1, -1, -1, -1, -1, 0, -1 });
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 469 */       cSeconds = periodType;
/*     */     } 
/* 471 */     return periodType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PeriodType millis() {
/* 480 */     PeriodType periodType = cMillis;
/* 481 */     if (periodType == null) {
/* 482 */       periodType = new PeriodType("Millis", new DurationFieldType[] { DurationFieldType.millis() }, new int[] { -1, -1, -1, -1, -1, -1, -1, 0 });
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 487 */       cMillis = periodType;
/*     */     } 
/* 489 */     return periodType;
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
/*     */   public static synchronized PeriodType forFields(DurationFieldType[] paramArrayOfDurationFieldType) {
/* 502 */     if (paramArrayOfDurationFieldType == null || paramArrayOfDurationFieldType.length == 0) {
/* 503 */       throw new IllegalArgumentException("Types array must not be null or empty");
/*     */     }
/* 505 */     for (byte b = 0; b < paramArrayOfDurationFieldType.length; b++) {
/* 506 */       if (paramArrayOfDurationFieldType[b] == null) {
/* 507 */         throw new IllegalArgumentException("Types array must not contain null");
/*     */       }
/*     */     } 
/* 510 */     Map<PeriodType, Object> map = cTypes;
/* 511 */     if (map.isEmpty()) {
/* 512 */       map.put(standard(), standard());
/* 513 */       map.put(yearMonthDayTime(), yearMonthDayTime());
/* 514 */       map.put(yearMonthDay(), yearMonthDay());
/* 515 */       map.put(yearWeekDayTime(), yearWeekDayTime());
/* 516 */       map.put(yearWeekDay(), yearWeekDay());
/* 517 */       map.put(yearDayTime(), yearDayTime());
/* 518 */       map.put(yearDay(), yearDay());
/* 519 */       map.put(dayTime(), dayTime());
/* 520 */       map.put(time(), time());
/* 521 */       map.put(years(), years());
/* 522 */       map.put(months(), months());
/* 523 */       map.put(weeks(), weeks());
/* 524 */       map.put(days(), days());
/* 525 */       map.put(hours(), hours());
/* 526 */       map.put(minutes(), minutes());
/* 527 */       map.put(seconds(), seconds());
/* 528 */       map.put(millis(), millis());
/*     */     } 
/* 530 */     PeriodType periodType1 = new PeriodType(null, paramArrayOfDurationFieldType, null);
/* 531 */     Object object = map.get(periodType1);
/* 532 */     if (object instanceof PeriodType) {
/* 533 */       return (PeriodType)object;
/*     */     }
/* 535 */     if (object != null) {
/* 536 */       throw new IllegalArgumentException("PeriodType does not support fields: " + object);
/*     */     }
/* 538 */     PeriodType periodType2 = standard();
/* 539 */     ArrayList arrayList = new ArrayList(Arrays.asList((Object[])paramArrayOfDurationFieldType));
/* 540 */     if (!arrayList.remove(DurationFieldType.years())) {
/* 541 */       periodType2 = periodType2.withYearsRemoved();
/*     */     }
/* 543 */     if (!arrayList.remove(DurationFieldType.months())) {
/* 544 */       periodType2 = periodType2.withMonthsRemoved();
/*     */     }
/* 546 */     if (!arrayList.remove(DurationFieldType.weeks())) {
/* 547 */       periodType2 = periodType2.withWeeksRemoved();
/*     */     }
/* 549 */     if (!arrayList.remove(DurationFieldType.days())) {
/* 550 */       periodType2 = periodType2.withDaysRemoved();
/*     */     }
/* 552 */     if (!arrayList.remove(DurationFieldType.hours())) {
/* 553 */       periodType2 = periodType2.withHoursRemoved();
/*     */     }
/* 555 */     if (!arrayList.remove(DurationFieldType.minutes())) {
/* 556 */       periodType2 = periodType2.withMinutesRemoved();
/*     */     }
/* 558 */     if (!arrayList.remove(DurationFieldType.seconds())) {
/* 559 */       periodType2 = periodType2.withSecondsRemoved();
/*     */     }
/* 561 */     if (!arrayList.remove(DurationFieldType.millis())) {
/* 562 */       periodType2 = periodType2.withMillisRemoved();
/*     */     }
/* 564 */     if (arrayList.size() > 0) {
/* 565 */       map.put(periodType1, arrayList);
/* 566 */       throw new IllegalArgumentException("PeriodType does not support fields: " + arrayList);
/*     */     } 
/*     */     
/* 569 */     PeriodType periodType3 = new PeriodType(null, periodType2.iTypes, null);
/* 570 */     PeriodType periodType4 = (PeriodType)map.get(periodType3);
/* 571 */     if (periodType4 != null) {
/* 572 */       map.put(periodType3, periodType4);
/* 573 */       return periodType4;
/*     */     } 
/* 575 */     map.put(periodType3, periodType2);
/* 576 */     return periodType2;
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
/*     */   protected PeriodType(String paramString, DurationFieldType[] paramArrayOfDurationFieldType, int[] paramArrayOfint) {
/* 596 */     this.iName = paramString;
/* 597 */     this.iTypes = paramArrayOfDurationFieldType;
/* 598 */     this.iIndices = paramArrayOfint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 608 */     return this.iName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 617 */     return this.iTypes.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DurationFieldType getFieldType(int paramInt) {
/* 628 */     return this.iTypes[paramInt];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSupported(DurationFieldType paramDurationFieldType) {
/* 638 */     return (indexOf(paramDurationFieldType) >= 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int indexOf(DurationFieldType paramDurationFieldType) {
/*     */     byte b;
/*     */     int i;
/* 648 */     for (b = 0, i = size(); b < i; b++) {
/* 649 */       if (this.iTypes[b] == paramDurationFieldType) {
/* 650 */         return b;
/*     */       }
/*     */     } 
/* 653 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 662 */     return "PeriodType[" + getName() + "]";
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
/*     */   int getIndexedField(ReadablePeriod paramReadablePeriod, int paramInt) {
/* 674 */     int i = this.iIndices[paramInt];
/* 675 */     return (i == -1) ? 0 : paramReadablePeriod.getValue(i);
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
/*     */   boolean setIndexedField(ReadablePeriod paramReadablePeriod, int paramInt1, int[] paramArrayOfint, int paramInt2) {
/* 688 */     int i = this.iIndices[paramInt1];
/* 689 */     if (i == -1) {
/* 690 */       throw new UnsupportedOperationException("Field is not supported");
/*     */     }
/* 692 */     paramArrayOfint[i] = paramInt2;
/* 693 */     return true;
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
/*     */   boolean addIndexedField(ReadablePeriod paramReadablePeriod, int paramInt1, int[] paramArrayOfint, int paramInt2) {
/* 707 */     if (paramInt2 == 0) {
/* 708 */       return false;
/*     */     }
/* 710 */     int i = this.iIndices[paramInt1];
/* 711 */     if (i == -1) {
/* 712 */       throw new UnsupportedOperationException("Field is not supported");
/*     */     }
/* 714 */     paramArrayOfint[i] = FieldUtils.safeAdd(paramArrayOfint[i], paramInt2);
/* 715 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PeriodType withYearsRemoved() {
/* 725 */     return withFieldRemoved(0, "NoYears");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PeriodType withMonthsRemoved() {
/* 734 */     return withFieldRemoved(1, "NoMonths");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PeriodType withWeeksRemoved() {
/* 743 */     return withFieldRemoved(2, "NoWeeks");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PeriodType withDaysRemoved() {
/* 752 */     return withFieldRemoved(3, "NoDays");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PeriodType withHoursRemoved() {
/* 761 */     return withFieldRemoved(4, "NoHours");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PeriodType withMinutesRemoved() {
/* 770 */     return withFieldRemoved(5, "NoMinutes");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PeriodType withSecondsRemoved() {
/* 779 */     return withFieldRemoved(6, "NoSeconds");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PeriodType withMillisRemoved() {
/* 788 */     return withFieldRemoved(7, "NoMillis");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private PeriodType withFieldRemoved(int paramInt, String paramString) {
/* 799 */     int i = this.iIndices[paramInt];
/* 800 */     if (i == -1) {
/* 801 */       return this;
/*     */     }
/*     */     
/* 804 */     DurationFieldType[] arrayOfDurationFieldType = new DurationFieldType[size() - 1];
/* 805 */     for (byte b1 = 0; b1 < this.iTypes.length; b1++) {
/* 806 */       if (b1 < i) {
/* 807 */         arrayOfDurationFieldType[b1] = this.iTypes[b1];
/* 808 */       } else if (b1 > i) {
/* 809 */         arrayOfDurationFieldType[b1 - 1] = this.iTypes[b1];
/*     */       } 
/*     */     } 
/*     */     
/* 813 */     int[] arrayOfInt = new int[8];
/* 814 */     for (byte b2 = 0; b2 < arrayOfInt.length; b2++) {
/* 815 */       if (b2 < paramInt) {
/* 816 */         arrayOfInt[b2] = this.iIndices[b2];
/* 817 */       } else if (b2 > paramInt) {
/* 818 */         arrayOfInt[b2] = (this.iIndices[b2] == -1) ? -1 : (this.iIndices[b2] - 1);
/*     */       } else {
/* 820 */         arrayOfInt[b2] = -1;
/*     */       } 
/*     */     } 
/* 823 */     return new PeriodType(getName() + paramString, arrayOfDurationFieldType, arrayOfInt);
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
/* 835 */     if (this == paramObject) {
/* 836 */       return true;
/*     */     }
/* 838 */     if (!(paramObject instanceof PeriodType)) {
/* 839 */       return false;
/*     */     }
/* 841 */     PeriodType periodType = (PeriodType)paramObject;
/* 842 */     return Arrays.equals((Object[])this.iTypes, (Object[])periodType.iTypes);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 851 */     int i = 0;
/* 852 */     for (byte b = 0; b < this.iTypes.length; b++) {
/* 853 */       i += this.iTypes[b].hashCode();
/*     */     }
/* 855 */     return i;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/PeriodType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */