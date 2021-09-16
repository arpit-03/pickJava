/*     */ package org.joda.time;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.Locale;
/*     */ import org.joda.convert.FromString;
/*     */ import org.joda.convert.ToString;
/*     */ import org.joda.time.base.BasePartial;
/*     */ import org.joda.time.chrono.ISOChronology;
/*     */ import org.joda.time.field.AbstractPartialFieldProperty;
/*     */ import org.joda.time.field.FieldUtils;
/*     */ import org.joda.time.format.DateTimeFormat;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class YearMonth
/*     */   extends BasePartial
/*     */   implements ReadablePartial, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 797544782896179L;
/*  75 */   private static final DateTimeFieldType[] FIELD_TYPES = new DateTimeFieldType[] { DateTimeFieldType.year(), DateTimeFieldType.monthOfYear() };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int YEAR = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int MONTH_OF_YEAR = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static YearMonth now() {
/*  95 */     return new YearMonth();
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
/*     */   public static YearMonth now(DateTimeZone paramDateTimeZone) {
/* 108 */     if (paramDateTimeZone == null) {
/* 109 */       throw new NullPointerException("Zone must not be null");
/*     */     }
/* 111 */     return new YearMonth(paramDateTimeZone);
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
/*     */   public static YearMonth now(Chronology paramChronology) {
/* 124 */     if (paramChronology == null) {
/* 125 */       throw new NullPointerException("Chronology must not be null");
/*     */     }
/* 127 */     return new YearMonth(paramChronology);
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
/*     */   @FromString
/*     */   public static YearMonth parse(String paramString) {
/* 141 */     return parse(paramString, ISODateTimeFormat.localDateParser());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static YearMonth parse(String paramString, DateTimeFormatter paramDateTimeFormatter) {
/* 152 */     LocalDate localDate = paramDateTimeFormatter.parseLocalDate(paramString);
/* 153 */     return new YearMonth(localDate.getYear(), localDate.getMonthOfYear());
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
/*     */   public static YearMonth fromCalendarFields(Calendar paramCalendar) {
/* 174 */     if (paramCalendar == null) {
/* 175 */       throw new IllegalArgumentException("The calendar must not be null");
/*     */     }
/* 177 */     return new YearMonth(paramCalendar.get(1), paramCalendar.get(2) + 1);
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
/*     */   public static YearMonth fromDateFields(Date paramDate) {
/* 195 */     if (paramDate == null) {
/* 196 */       throw new IllegalArgumentException("The date must not be null");
/*     */     }
/* 198 */     return new YearMonth(paramDate.getYear() + 1900, paramDate.getMonth() + 1);
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
/*     */   public YearMonth() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public YearMonth(DateTimeZone paramDateTimeZone) {
/* 228 */     super((Chronology)ISOChronology.getInstance(paramDateTimeZone));
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
/*     */   public YearMonth(Chronology paramChronology) {
/* 243 */     super(paramChronology);
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
/*     */   public YearMonth(long paramLong) {
/* 257 */     super(paramLong);
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
/*     */   public YearMonth(long paramLong, Chronology paramChronology) {
/* 272 */     super(paramLong, paramChronology);
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
/*     */   public YearMonth(Object paramObject) {
/* 289 */     super(paramObject, null, ISODateTimeFormat.localDateParser());
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
/*     */   public YearMonth(Object paramObject, Chronology paramChronology) {
/* 311 */     super(paramObject, DateTimeUtils.getChronology(paramChronology), ISODateTimeFormat.localDateParser());
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
/*     */   public YearMonth(int paramInt1, int paramInt2) {
/* 326 */     this(paramInt1, paramInt2, (Chronology)null);
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
/*     */   public YearMonth(int paramInt1, int paramInt2, Chronology paramChronology) {
/* 344 */     super(new int[] { paramInt1, paramInt2 }, paramChronology);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   YearMonth(YearMonth paramYearMonth, int[] paramArrayOfint) {
/* 354 */     super(paramYearMonth, paramArrayOfint);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   YearMonth(YearMonth paramYearMonth, Chronology paramChronology) {
/* 364 */     super(paramYearMonth, paramChronology);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Object readResolve() {
/* 372 */     if (!DateTimeZone.UTC.equals(getChronology().getZone())) {
/* 373 */       return new YearMonth(this, getChronology().withUTC());
/*     */     }
/* 375 */     return this;
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
/*     */   public int size() {
/* 387 */     return 2;
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
/*     */   protected DateTimeField getField(int paramInt, Chronology paramChronology) {
/* 400 */     switch (paramInt) {
/*     */       case 0:
/* 402 */         return paramChronology.year();
/*     */       case 1:
/* 404 */         return paramChronology.monthOfYear();
/*     */     } 
/* 406 */     throw new IndexOutOfBoundsException("Invalid index: " + paramInt);
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
/*     */   public DateTimeFieldType getFieldType(int paramInt) {
/* 418 */     return FIELD_TYPES[paramInt];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DateTimeFieldType[] getFieldTypes() {
/* 429 */     return (DateTimeFieldType[])FIELD_TYPES.clone();
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
/*     */   public YearMonth withChronologyRetainFields(Chronology paramChronology) {
/* 448 */     paramChronology = DateTimeUtils.getChronology(paramChronology);
/* 449 */     paramChronology = paramChronology.withUTC();
/* 450 */     if (paramChronology == getChronology()) {
/* 451 */       return this;
/*     */     }
/* 453 */     YearMonth yearMonth = new YearMonth(this, paramChronology);
/* 454 */     paramChronology.validate(yearMonth, getValues());
/* 455 */     return yearMonth;
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
/*     */   public YearMonth withField(DateTimeFieldType paramDateTimeFieldType, int paramInt) {
/* 478 */     int i = indexOfSupported(paramDateTimeFieldType);
/* 479 */     if (paramInt == getValue(i)) {
/* 480 */       return this;
/*     */     }
/* 482 */     int[] arrayOfInt = getValues();
/* 483 */     arrayOfInt = getField(i).set(this, i, arrayOfInt, paramInt);
/* 484 */     return new YearMonth(this, arrayOfInt);
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
/*     */   public YearMonth withFieldAdded(DurationFieldType paramDurationFieldType, int paramInt) {
/* 506 */     int i = indexOfSupported(paramDurationFieldType);
/* 507 */     if (paramInt == 0) {
/* 508 */       return this;
/*     */     }
/* 510 */     int[] arrayOfInt = getValues();
/* 511 */     arrayOfInt = getField(i).add(this, i, arrayOfInt, paramInt);
/* 512 */     return new YearMonth(this, arrayOfInt);
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
/*     */   public YearMonth withPeriodAdded(ReadablePeriod paramReadablePeriod, int paramInt) {
/* 532 */     if (paramReadablePeriod == null || paramInt == 0) {
/* 533 */       return this;
/*     */     }
/* 535 */     int[] arrayOfInt = getValues();
/* 536 */     for (byte b = 0; b < paramReadablePeriod.size(); b++) {
/* 537 */       DurationFieldType durationFieldType = paramReadablePeriod.getFieldType(b);
/* 538 */       int i = indexOf(durationFieldType);
/* 539 */       if (i >= 0) {
/* 540 */         arrayOfInt = getField(i).add(this, i, arrayOfInt, FieldUtils.safeMultiply(paramReadablePeriod.getValue(b), paramInt));
/*     */       }
/*     */     } 
/*     */     
/* 544 */     return new YearMonth(this, arrayOfInt);
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
/*     */   public YearMonth plus(ReadablePeriod paramReadablePeriod) {
/* 562 */     return withPeriodAdded(paramReadablePeriod, 1);
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
/*     */   public YearMonth plusYears(int paramInt) {
/* 582 */     return withFieldAdded(DurationFieldType.years(), paramInt);
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
/*     */   public YearMonth plusMonths(int paramInt) {
/* 601 */     return withFieldAdded(DurationFieldType.months(), paramInt);
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
/*     */   public YearMonth minus(ReadablePeriod paramReadablePeriod) {
/* 619 */     return withPeriodAdded(paramReadablePeriod, -1);
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
/*     */   public YearMonth minusYears(int paramInt) {
/* 639 */     return withFieldAdded(DurationFieldType.years(), FieldUtils.safeNegate(paramInt));
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
/*     */   public YearMonth minusMonths(int paramInt) {
/* 658 */     return withFieldAdded(DurationFieldType.months(), FieldUtils.safeNegate(paramInt));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LocalDate toLocalDate(int paramInt) {
/* 669 */     return new LocalDate(getYear(), getMonthOfYear(), paramInt, getChronology());
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
/* 683 */     return toInterval((DateTimeZone)null);
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
/*     */   public Interval toInterval(DateTimeZone paramDateTimeZone) {
/* 697 */     paramDateTimeZone = DateTimeUtils.getZone(paramDateTimeZone);
/* 698 */     DateTime dateTime1 = toLocalDate(1).toDateTimeAtStartOfDay(paramDateTimeZone);
/* 699 */     DateTime dateTime2 = plusMonths(1).toLocalDate(1).toDateTimeAtStartOfDay(paramDateTimeZone);
/* 700 */     return new Interval(dateTime1, dateTime2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getYear() {
/* 710 */     return getValue(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMonthOfYear() {
/* 719 */     return getValue(1);
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
/*     */   public YearMonth withYear(int paramInt) {
/* 735 */     int[] arrayOfInt = getValues();
/* 736 */     arrayOfInt = getChronology().year().set(this, 0, arrayOfInt, paramInt);
/* 737 */     return new YearMonth(this, arrayOfInt);
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
/*     */   public YearMonth withMonthOfYear(int paramInt) {
/* 752 */     int[] arrayOfInt = getValues();
/* 753 */     arrayOfInt = getChronology().monthOfYear().set(this, 1, arrayOfInt, paramInt);
/* 754 */     return new YearMonth(this, arrayOfInt);
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
/*     */   public Property property(DateTimeFieldType paramDateTimeFieldType) {
/* 767 */     return new Property(this, indexOfSupported(paramDateTimeFieldType));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Property year() {
/* 777 */     return new Property(this, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Property monthOfYear() {
/* 786 */     return new Property(this, 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ToString
/*     */   public String toString() {
/* 797 */     return ISODateTimeFormat.yearMonth().print(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString(String paramString) {
/* 807 */     if (paramString == null) {
/* 808 */       return toString();
/*     */     }
/* 810 */     return DateTimeFormat.forPattern(paramString).print(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString(String paramString, Locale paramLocale) throws IllegalArgumentException {
/* 821 */     if (paramString == null) {
/* 822 */       return toString();
/*     */     }
/* 824 */     return DateTimeFormat.forPattern(paramString).withLocale(paramLocale).print(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Property
/*     */     extends AbstractPartialFieldProperty
/*     */     implements Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 5727734012190224363L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final YearMonth iBase;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final int iFieldIndex;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Property(YearMonth param1YearMonth, int param1Int) {
/* 854 */       this.iBase = param1YearMonth;
/* 855 */       this.iFieldIndex = param1Int;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public DateTimeField getField() {
/* 864 */       return this.iBase.getField(this.iFieldIndex);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected ReadablePartial getReadablePartial() {
/* 873 */       return this.iBase;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public YearMonth getYearMonth() {
/* 882 */       return this.iBase;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int get() {
/* 891 */       return this.iBase.getValue(this.iFieldIndex);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public YearMonth addToCopy(int param1Int) {
/* 913 */       int[] arrayOfInt = this.iBase.getValues();
/* 914 */       arrayOfInt = getField().add(this.iBase, this.iFieldIndex, arrayOfInt, param1Int);
/* 915 */       return new YearMonth(this.iBase, arrayOfInt);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public YearMonth addWrapFieldToCopy(int param1Int) {
/* 937 */       int[] arrayOfInt = this.iBase.getValues();
/* 938 */       arrayOfInt = getField().addWrapField(this.iBase, this.iFieldIndex, arrayOfInt, param1Int);
/* 939 */       return new YearMonth(this.iBase, arrayOfInt);
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
/*     */     
/*     */     public YearMonth setCopy(int param1Int) {
/* 954 */       int[] arrayOfInt = this.iBase.getValues();
/* 955 */       arrayOfInt = getField().set(this.iBase, this.iFieldIndex, arrayOfInt, param1Int);
/* 956 */       return new YearMonth(this.iBase, arrayOfInt);
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
/*     */     
/*     */     public YearMonth setCopy(String param1String, Locale param1Locale) {
/* 971 */       int[] arrayOfInt = this.iBase.getValues();
/* 972 */       arrayOfInt = getField().set(this.iBase, this.iFieldIndex, arrayOfInt, param1String, param1Locale);
/* 973 */       return new YearMonth(this.iBase, arrayOfInt);
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
/*     */     public YearMonth setCopy(String param1String) {
/* 987 */       return setCopy(param1String, null);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/YearMonth.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */