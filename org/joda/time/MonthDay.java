/*     */ package org.joda.time;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
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
/*     */ import org.joda.time.format.DateTimeFormatterBuilder;
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
/*     */ 
/*     */ 
/*     */ public final class MonthDay
/*     */   extends BasePartial
/*     */   implements ReadablePartial, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 2954560699050434609L;
/*  79 */   private static final DateTimeFieldType[] FIELD_TYPES = new DateTimeFieldType[] { DateTimeFieldType.monthOfYear(), DateTimeFieldType.dayOfMonth() };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  84 */   private static final DateTimeFormatter PARSER = (new DateTimeFormatterBuilder()).appendOptional(ISODateTimeFormat.localDateParser().getParser()).appendOptional(DateTimeFormat.forPattern("--MM-dd").getParser()).toFormatter();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int MONTH_OF_YEAR = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int DAY_OF_MONTH = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static MonthDay now() {
/* 103 */     return new MonthDay();
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
/*     */   public static MonthDay now(DateTimeZone paramDateTimeZone) {
/* 116 */     if (paramDateTimeZone == null) {
/* 117 */       throw new NullPointerException("Zone must not be null");
/*     */     }
/* 119 */     return new MonthDay(paramDateTimeZone);
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
/*     */   public static MonthDay now(Chronology paramChronology) {
/* 132 */     if (paramChronology == null) {
/* 133 */       throw new NullPointerException("Chronology must not be null");
/*     */     }
/* 135 */     return new MonthDay(paramChronology);
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
/*     */   public static MonthDay parse(String paramString) {
/* 149 */     return parse(paramString, PARSER);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static MonthDay parse(String paramString, DateTimeFormatter paramDateTimeFormatter) {
/* 160 */     LocalDate localDate = paramDateTimeFormatter.parseLocalDate(paramString);
/* 161 */     return new MonthDay(localDate.getMonthOfYear(), localDate.getDayOfMonth());
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
/*     */   public static MonthDay fromCalendarFields(Calendar paramCalendar) {
/* 182 */     if (paramCalendar == null) {
/* 183 */       throw new IllegalArgumentException("The calendar must not be null");
/*     */     }
/* 185 */     return new MonthDay(paramCalendar.get(2) + 1, paramCalendar.get(5));
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
/*     */   public static MonthDay fromDateFields(Date paramDate) {
/* 203 */     if (paramDate == null) {
/* 204 */       throw new IllegalArgumentException("The date must not be null");
/*     */     }
/* 206 */     return new MonthDay(paramDate.getMonth() + 1, paramDate.getDate());
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
/*     */   public MonthDay() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MonthDay(DateTimeZone paramDateTimeZone) {
/* 236 */     super((Chronology)ISOChronology.getInstance(paramDateTimeZone));
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
/*     */   public MonthDay(Chronology paramChronology) {
/* 251 */     super(paramChronology);
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
/*     */   public MonthDay(long paramLong) {
/* 265 */     super(paramLong);
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
/*     */   public MonthDay(long paramLong, Chronology paramChronology) {
/* 280 */     super(paramLong, paramChronology);
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
/*     */   public MonthDay(Object paramObject) {
/* 297 */     super(paramObject, null, ISODateTimeFormat.localDateParser());
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
/*     */   public MonthDay(Object paramObject, Chronology paramChronology) {
/* 319 */     super(paramObject, DateTimeUtils.getChronology(paramChronology), ISODateTimeFormat.localDateParser());
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
/*     */   public MonthDay(int paramInt1, int paramInt2) {
/* 334 */     this(paramInt1, paramInt2, (Chronology)null);
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
/*     */   public MonthDay(int paramInt1, int paramInt2, Chronology paramChronology) {
/* 352 */     super(new int[] { paramInt1, paramInt2 }, paramChronology);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   MonthDay(MonthDay paramMonthDay, int[] paramArrayOfint) {
/* 362 */     super(paramMonthDay, paramArrayOfint);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   MonthDay(MonthDay paramMonthDay, Chronology paramChronology) {
/* 372 */     super(paramMonthDay, paramChronology);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Object readResolve() {
/* 380 */     if (!DateTimeZone.UTC.equals(getChronology().getZone())) {
/* 381 */       return new MonthDay(this, getChronology().withUTC());
/*     */     }
/* 383 */     return this;
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
/* 395 */     return 2;
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
/* 408 */     switch (paramInt) {
/*     */       case 0:
/* 410 */         return paramChronology.monthOfYear();
/*     */       case 1:
/* 412 */         return paramChronology.dayOfMonth();
/*     */     } 
/* 414 */     throw new IndexOutOfBoundsException("Invalid index: " + paramInt);
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
/* 426 */     return FIELD_TYPES[paramInt];
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
/* 437 */     return (DateTimeFieldType[])FIELD_TYPES.clone();
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
/*     */   public MonthDay withChronologyRetainFields(Chronology paramChronology) {
/* 456 */     paramChronology = DateTimeUtils.getChronology(paramChronology);
/* 457 */     paramChronology = paramChronology.withUTC();
/* 458 */     if (paramChronology == getChronology()) {
/* 459 */       return this;
/*     */     }
/* 461 */     MonthDay monthDay = new MonthDay(this, paramChronology);
/* 462 */     paramChronology.validate(monthDay, getValues());
/* 463 */     return monthDay;
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
/*     */   public MonthDay withField(DateTimeFieldType paramDateTimeFieldType, int paramInt) {
/* 486 */     int i = indexOfSupported(paramDateTimeFieldType);
/* 487 */     if (paramInt == getValue(i)) {
/* 488 */       return this;
/*     */     }
/* 490 */     int[] arrayOfInt = getValues();
/* 491 */     arrayOfInt = getField(i).set(this, i, arrayOfInt, paramInt);
/* 492 */     return new MonthDay(this, arrayOfInt);
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
/*     */   public MonthDay withFieldAdded(DurationFieldType paramDurationFieldType, int paramInt) {
/* 514 */     int i = indexOfSupported(paramDurationFieldType);
/* 515 */     if (paramInt == 0) {
/* 516 */       return this;
/*     */     }
/* 518 */     int[] arrayOfInt = getValues();
/* 519 */     arrayOfInt = getField(i).add(this, i, arrayOfInt, paramInt);
/* 520 */     return new MonthDay(this, arrayOfInt);
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
/*     */   public MonthDay withPeriodAdded(ReadablePeriod paramReadablePeriod, int paramInt) {
/* 540 */     if (paramReadablePeriod == null || paramInt == 0) {
/* 541 */       return this;
/*     */     }
/* 543 */     int[] arrayOfInt = getValues();
/* 544 */     for (byte b = 0; b < paramReadablePeriod.size(); b++) {
/* 545 */       DurationFieldType durationFieldType = paramReadablePeriod.getFieldType(b);
/* 546 */       int i = indexOf(durationFieldType);
/* 547 */       if (i >= 0) {
/* 548 */         arrayOfInt = getField(i).add(this, i, arrayOfInt, FieldUtils.safeMultiply(paramReadablePeriod.getValue(b), paramInt));
/*     */       }
/*     */     } 
/*     */     
/* 552 */     return new MonthDay(this, arrayOfInt);
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
/*     */   public MonthDay plus(ReadablePeriod paramReadablePeriod) {
/* 570 */     return withPeriodAdded(paramReadablePeriod, 1);
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
/*     */   public MonthDay plusMonths(int paramInt) {
/* 592 */     return withFieldAdded(DurationFieldType.months(), paramInt);
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
/*     */   public MonthDay plusDays(int paramInt) {
/* 615 */     return withFieldAdded(DurationFieldType.days(), paramInt);
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
/*     */   public MonthDay minus(ReadablePeriod paramReadablePeriod) {
/* 633 */     return withPeriodAdded(paramReadablePeriod, -1);
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
/*     */   public MonthDay minusMonths(int paramInt) {
/* 655 */     return withFieldAdded(DurationFieldType.months(), FieldUtils.safeNegate(paramInt));
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
/*     */   public MonthDay minusDays(int paramInt) {
/* 675 */     return withFieldAdded(DurationFieldType.days(), FieldUtils.safeNegate(paramInt));
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
/* 686 */     return new LocalDate(paramInt, getMonthOfYear(), getDayOfMonth(), getChronology());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMonthOfYear() {
/* 696 */     return getValue(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDayOfMonth() {
/* 705 */     return getValue(1);
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
/*     */   public MonthDay withMonthOfYear(int paramInt) {
/* 721 */     int[] arrayOfInt = getValues();
/* 722 */     arrayOfInt = getChronology().monthOfYear().set(this, 0, arrayOfInt, paramInt);
/* 723 */     return new MonthDay(this, arrayOfInt);
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
/*     */   public MonthDay withDayOfMonth(int paramInt) {
/* 738 */     int[] arrayOfInt = getValues();
/* 739 */     arrayOfInt = getChronology().dayOfMonth().set(this, 1, arrayOfInt, paramInt);
/* 740 */     return new MonthDay(this, arrayOfInt);
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
/* 753 */     return new Property(this, indexOfSupported(paramDateTimeFieldType));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Property monthOfYear() {
/* 763 */     return new Property(this, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Property dayOfMonth() {
/* 772 */     return new Property(this, 1);
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
/* 783 */     ArrayList<DateTimeFieldType> arrayList = new ArrayList();
/* 784 */     arrayList.add(DateTimeFieldType.monthOfYear());
/* 785 */     arrayList.add(DateTimeFieldType.dayOfMonth());
/* 786 */     return ISODateTimeFormat.forFields(arrayList, true, true).print(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString(String paramString) {
/* 796 */     if (paramString == null) {
/* 797 */       return toString();
/*     */     }
/* 799 */     return DateTimeFormat.forPattern(paramString).print(this);
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
/* 810 */     if (paramString == null) {
/* 811 */       return toString();
/*     */     }
/* 813 */     return DateTimeFormat.forPattern(paramString).withLocale(paramLocale).print(this);
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
/*     */     private final MonthDay iBase;
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
/*     */     Property(MonthDay param1MonthDay, int param1Int) {
/* 843 */       this.iBase = param1MonthDay;
/* 844 */       this.iFieldIndex = param1Int;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public DateTimeField getField() {
/* 853 */       return this.iBase.getField(this.iFieldIndex);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected ReadablePartial getReadablePartial() {
/* 862 */       return this.iBase;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public MonthDay getMonthDay() {
/* 871 */       return this.iBase;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int get() {
/* 880 */       return this.iBase.getValue(this.iFieldIndex);
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
/*     */     public MonthDay addToCopy(int param1Int) {
/* 899 */       int[] arrayOfInt = this.iBase.getValues();
/* 900 */       arrayOfInt = getField().add(this.iBase, this.iFieldIndex, arrayOfInt, param1Int);
/* 901 */       return new MonthDay(this.iBase, arrayOfInt);
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
/*     */     public MonthDay addWrapFieldToCopy(int param1Int) {
/* 923 */       int[] arrayOfInt = this.iBase.getValues();
/* 924 */       arrayOfInt = getField().addWrapField(this.iBase, this.iFieldIndex, arrayOfInt, param1Int);
/* 925 */       return new MonthDay(this.iBase, arrayOfInt);
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
/*     */     public MonthDay setCopy(int param1Int) {
/* 940 */       int[] arrayOfInt = this.iBase.getValues();
/* 941 */       arrayOfInt = getField().set(this.iBase, this.iFieldIndex, arrayOfInt, param1Int);
/* 942 */       return new MonthDay(this.iBase, arrayOfInt);
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
/*     */     public MonthDay setCopy(String param1String, Locale param1Locale) {
/* 957 */       int[] arrayOfInt = this.iBase.getValues();
/* 958 */       arrayOfInt = getField().set(this.iBase, this.iFieldIndex, arrayOfInt, param1String, param1Locale);
/* 959 */       return new MonthDay(this.iBase, arrayOfInt);
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
/*     */     public MonthDay setCopy(String param1String) {
/* 973 */       return setCopy(param1String, null);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/MonthDay.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */