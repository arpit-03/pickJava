/*      */ package org.joda.time;
/*      */ 
/*      */ import java.io.Serializable;
/*      */ import java.util.Calendar;
/*      */ import java.util.Date;
/*      */ import java.util.Locale;
/*      */ import org.joda.time.base.BasePartial;
/*      */ import org.joda.time.chrono.ISOChronology;
/*      */ import org.joda.time.field.AbstractPartialFieldProperty;
/*      */ import org.joda.time.field.FieldUtils;
/*      */ import org.joda.time.format.ISODateTimeFormat;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ @Deprecated
/*      */ public final class TimeOfDay
/*      */   extends BasePartial
/*      */   implements ReadablePartial, Serializable
/*      */ {
/*      */   private static final long serialVersionUID = 3633353405803318660L;
/*   75 */   private static final DateTimeFieldType[] FIELD_TYPES = new DateTimeFieldType[] { DateTimeFieldType.hourOfDay(), DateTimeFieldType.minuteOfHour(), DateTimeFieldType.secondOfMinute(), DateTimeFieldType.millisOfSecond() };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   83 */   public static final TimeOfDay MIDNIGHT = new TimeOfDay(0, 0, 0, 0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int HOUR_OF_DAY = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int MINUTE_OF_HOUR = 1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int SECOND_OF_MINUTE = 2;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int MILLIS_OF_SECOND = 3;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static TimeOfDay fromCalendarFields(Calendar paramCalendar) {
/*  115 */     if (paramCalendar == null) {
/*  116 */       throw new IllegalArgumentException("The calendar must not be null");
/*      */     }
/*  118 */     return new TimeOfDay(paramCalendar.get(11), paramCalendar.get(12), paramCalendar.get(13), paramCalendar.get(14));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static TimeOfDay fromDateFields(Date paramDate) {
/*  145 */     if (paramDate == null) {
/*  146 */       throw new IllegalArgumentException("The date must not be null");
/*      */     }
/*  148 */     return new TimeOfDay(paramDate.getHours(), paramDate.getMinutes(), paramDate.getSeconds(), ((int)(paramDate.getTime() % 1000L) + 1000) % 1000);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static TimeOfDay fromMillisOfDay(long paramLong) {
/*  168 */     return fromMillisOfDay(paramLong, (Chronology)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static TimeOfDay fromMillisOfDay(long paramLong, Chronology paramChronology) {
/*  183 */     paramChronology = DateTimeUtils.getChronology(paramChronology);
/*  184 */     paramChronology = paramChronology.withUTC();
/*  185 */     return new TimeOfDay(paramLong, paramChronology);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TimeOfDay() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TimeOfDay(DateTimeZone paramDateTimeZone) {
/*  214 */     super((Chronology)ISOChronology.getInstance(paramDateTimeZone));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TimeOfDay(Chronology paramChronology) {
/*  228 */     super(paramChronology);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TimeOfDay(long paramLong) {
/*  242 */     super(paramLong);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TimeOfDay(long paramLong, Chronology paramChronology) {
/*  257 */     super(paramLong, paramChronology);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TimeOfDay(Object paramObject) {
/*  277 */     super(paramObject, null, ISODateTimeFormat.timeParser());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TimeOfDay(Object paramObject, Chronology paramChronology) {
/*  302 */     super(paramObject, DateTimeUtils.getChronology(paramChronology), ISODateTimeFormat.timeParser());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TimeOfDay(int paramInt1, int paramInt2) {
/*  317 */     this(paramInt1, paramInt2, 0, 0, (Chronology)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TimeOfDay(int paramInt1, int paramInt2, Chronology paramChronology) {
/*  332 */     this(paramInt1, paramInt2, 0, 0, paramChronology);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TimeOfDay(int paramInt1, int paramInt2, int paramInt3) {
/*  348 */     this(paramInt1, paramInt2, paramInt3, 0, (Chronology)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TimeOfDay(int paramInt1, int paramInt2, int paramInt3, Chronology paramChronology) {
/*  364 */     this(paramInt1, paramInt2, paramInt3, 0, paramChronology);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TimeOfDay(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  381 */     this(paramInt1, paramInt2, paramInt3, paramInt4, (Chronology)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TimeOfDay(int paramInt1, int paramInt2, int paramInt3, int paramInt4, Chronology paramChronology) {
/*  399 */     super(new int[] { paramInt1, paramInt2, paramInt3, paramInt4 }, paramChronology);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   TimeOfDay(TimeOfDay paramTimeOfDay, int[] paramArrayOfint) {
/*  409 */     super(paramTimeOfDay, paramArrayOfint);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   TimeOfDay(TimeOfDay paramTimeOfDay, Chronology paramChronology) {
/*  419 */     super(paramTimeOfDay, paramChronology);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int size() {
/*  429 */     return 4;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected DateTimeField getField(int paramInt, Chronology paramChronology) {
/*  442 */     switch (paramInt) {
/*      */       case 0:
/*  444 */         return paramChronology.hourOfDay();
/*      */       case 1:
/*  446 */         return paramChronology.minuteOfHour();
/*      */       case 2:
/*  448 */         return paramChronology.secondOfMinute();
/*      */       case 3:
/*  450 */         return paramChronology.millisOfSecond();
/*      */     } 
/*  452 */     throw new IndexOutOfBoundsException("Invalid index: " + paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DateTimeFieldType getFieldType(int paramInt) {
/*  464 */     return FIELD_TYPES[paramInt];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DateTimeFieldType[] getFieldTypes() {
/*  475 */     return (DateTimeFieldType[])FIELD_TYPES.clone();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TimeOfDay withChronologyRetainFields(Chronology paramChronology) {
/*  494 */     paramChronology = DateTimeUtils.getChronology(paramChronology);
/*  495 */     paramChronology = paramChronology.withUTC();
/*  496 */     if (paramChronology == getChronology()) {
/*  497 */       return this;
/*      */     }
/*  499 */     TimeOfDay timeOfDay = new TimeOfDay(this, paramChronology);
/*  500 */     paramChronology.validate(timeOfDay, getValues());
/*  501 */     return timeOfDay;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TimeOfDay withField(DateTimeFieldType paramDateTimeFieldType, int paramInt) {
/*  524 */     int i = indexOfSupported(paramDateTimeFieldType);
/*  525 */     if (paramInt == getValue(i)) {
/*  526 */       return this;
/*      */     }
/*  528 */     int[] arrayOfInt = getValues();
/*  529 */     arrayOfInt = getField(i).set(this, i, arrayOfInt, paramInt);
/*  530 */     return new TimeOfDay(this, arrayOfInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TimeOfDay withFieldAdded(DurationFieldType paramDurationFieldType, int paramInt) {
/*  553 */     int i = indexOfSupported(paramDurationFieldType);
/*  554 */     if (paramInt == 0) {
/*  555 */       return this;
/*      */     }
/*  557 */     int[] arrayOfInt = getValues();
/*  558 */     arrayOfInt = getField(i).addWrapPartial(this, i, arrayOfInt, paramInt);
/*  559 */     return new TimeOfDay(this, arrayOfInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TimeOfDay withPeriodAdded(ReadablePeriod paramReadablePeriod, int paramInt) {
/*  580 */     if (paramReadablePeriod == null || paramInt == 0) {
/*  581 */       return this;
/*      */     }
/*  583 */     int[] arrayOfInt = getValues();
/*  584 */     for (byte b = 0; b < paramReadablePeriod.size(); b++) {
/*  585 */       DurationFieldType durationFieldType = paramReadablePeriod.getFieldType(b);
/*  586 */       int i = indexOf(durationFieldType);
/*  587 */       if (i >= 0) {
/*  588 */         arrayOfInt = getField(i).addWrapPartial(this, i, arrayOfInt, FieldUtils.safeMultiply(paramReadablePeriod.getValue(b), paramInt));
/*      */       }
/*      */     } 
/*      */     
/*  592 */     return new TimeOfDay(this, arrayOfInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TimeOfDay plus(ReadablePeriod paramReadablePeriod) {
/*  611 */     return withPeriodAdded(paramReadablePeriod, 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TimeOfDay plusHours(int paramInt) {
/*  632 */     return withFieldAdded(DurationFieldType.hours(), paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TimeOfDay plusMinutes(int paramInt) {
/*  652 */     return withFieldAdded(DurationFieldType.minutes(), paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TimeOfDay plusSeconds(int paramInt) {
/*  672 */     return withFieldAdded(DurationFieldType.seconds(), paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TimeOfDay plusMillis(int paramInt) {
/*  692 */     return withFieldAdded(DurationFieldType.millis(), paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TimeOfDay minus(ReadablePeriod paramReadablePeriod) {
/*  711 */     return withPeriodAdded(paramReadablePeriod, -1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TimeOfDay minusHours(int paramInt) {
/*  732 */     return withFieldAdded(DurationFieldType.hours(), FieldUtils.safeNegate(paramInt));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TimeOfDay minusMinutes(int paramInt) {
/*  752 */     return withFieldAdded(DurationFieldType.minutes(), FieldUtils.safeNegate(paramInt));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TimeOfDay minusSeconds(int paramInt) {
/*  772 */     return withFieldAdded(DurationFieldType.seconds(), FieldUtils.safeNegate(paramInt));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TimeOfDay minusMillis(int paramInt) {
/*  792 */     return withFieldAdded(DurationFieldType.millis(), FieldUtils.safeNegate(paramInt));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Property property(DateTimeFieldType paramDateTimeFieldType) {
/*  805 */     return new Property(this, indexOfSupported(paramDateTimeFieldType));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LocalTime toLocalTime() {
/*  816 */     return new LocalTime(getHourOfDay(), getMinuteOfHour(), getSecondOfMinute(), getMillisOfSecond(), getChronology());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DateTime toDateTimeToday() {
/*  829 */     return toDateTimeToday((DateTimeZone)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DateTime toDateTimeToday(DateTimeZone paramDateTimeZone) {
/*  844 */     Chronology chronology = getChronology().withZone(paramDateTimeZone);
/*  845 */     long l1 = DateTimeUtils.currentTimeMillis();
/*  846 */     long l2 = chronology.set(this, l1);
/*  847 */     return new DateTime(l2, chronology);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getHourOfDay() {
/*  857 */     return getValue(0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMinuteOfHour() {
/*  866 */     return getValue(1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getSecondOfMinute() {
/*  875 */     return getValue(2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMillisOfSecond() {
/*  884 */     return getValue(3);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TimeOfDay withHourOfDay(int paramInt) {
/*  901 */     int[] arrayOfInt = getValues();
/*  902 */     arrayOfInt = getChronology().hourOfDay().set(this, 0, arrayOfInt, paramInt);
/*  903 */     return new TimeOfDay(this, arrayOfInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TimeOfDay withMinuteOfHour(int paramInt) {
/*  919 */     int[] arrayOfInt = getValues();
/*  920 */     arrayOfInt = getChronology().minuteOfHour().set(this, 1, arrayOfInt, paramInt);
/*  921 */     return new TimeOfDay(this, arrayOfInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TimeOfDay withSecondOfMinute(int paramInt) {
/*  937 */     int[] arrayOfInt = getValues();
/*  938 */     arrayOfInt = getChronology().secondOfMinute().set(this, 2, arrayOfInt, paramInt);
/*  939 */     return new TimeOfDay(this, arrayOfInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TimeOfDay withMillisOfSecond(int paramInt) {
/*  955 */     int[] arrayOfInt = getValues();
/*  956 */     arrayOfInt = getChronology().millisOfSecond().set(this, 3, arrayOfInt, paramInt);
/*  957 */     return new TimeOfDay(this, arrayOfInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Property hourOfDay() {
/*  967 */     return new Property(this, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Property minuteOfHour() {
/*  976 */     return new Property(this, 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Property secondOfMinute() {
/*  985 */     return new Property(this, 2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Property millisOfSecond() {
/*  994 */     return new Property(this, 3);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1004 */     return ISODateTimeFormat.tTime().print(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static class Property
/*      */     extends AbstractPartialFieldProperty
/*      */     implements Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 5598459141741063833L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final TimeOfDay iTimeOfDay;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final int iFieldIndex;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Property(TimeOfDay param1TimeOfDay, int param1Int) {
/* 1036 */       this.iTimeOfDay = param1TimeOfDay;
/* 1037 */       this.iFieldIndex = param1Int;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public DateTimeField getField() {
/* 1046 */       return this.iTimeOfDay.getField(this.iFieldIndex);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected ReadablePartial getReadablePartial() {
/* 1055 */       return this.iTimeOfDay;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public TimeOfDay getTimeOfDay() {
/* 1064 */       return this.iTimeOfDay;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int get() {
/* 1073 */       return this.iTimeOfDay.getValue(this.iFieldIndex);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public TimeOfDay addToCopy(int param1Int) {
/* 1097 */       int[] arrayOfInt = this.iTimeOfDay.getValues();
/* 1098 */       arrayOfInt = getField().addWrapPartial(this.iTimeOfDay, this.iFieldIndex, arrayOfInt, param1Int);
/* 1099 */       return new TimeOfDay(this.iTimeOfDay, arrayOfInt);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public TimeOfDay addNoWrapToCopy(int param1Int) {
/* 1123 */       int[] arrayOfInt = this.iTimeOfDay.getValues();
/* 1124 */       arrayOfInt = getField().add(this.iTimeOfDay, this.iFieldIndex, arrayOfInt, param1Int);
/* 1125 */       return new TimeOfDay(this.iTimeOfDay, arrayOfInt);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public TimeOfDay addWrapFieldToCopy(int param1Int) {
/* 1147 */       int[] arrayOfInt = this.iTimeOfDay.getValues();
/* 1148 */       arrayOfInt = getField().addWrapField(this.iTimeOfDay, this.iFieldIndex, arrayOfInt, param1Int);
/* 1149 */       return new TimeOfDay(this.iTimeOfDay, arrayOfInt);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public TimeOfDay setCopy(int param1Int) {
/* 1164 */       int[] arrayOfInt = this.iTimeOfDay.getValues();
/* 1165 */       arrayOfInt = getField().set(this.iTimeOfDay, this.iFieldIndex, arrayOfInt, param1Int);
/* 1166 */       return new TimeOfDay(this.iTimeOfDay, arrayOfInt);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public TimeOfDay setCopy(String param1String, Locale param1Locale) {
/* 1181 */       int[] arrayOfInt = this.iTimeOfDay.getValues();
/* 1182 */       arrayOfInt = getField().set(this.iTimeOfDay, this.iFieldIndex, arrayOfInt, param1String, param1Locale);
/* 1183 */       return new TimeOfDay(this.iTimeOfDay, arrayOfInt);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public TimeOfDay setCopy(String param1String) {
/* 1197 */       return setCopy(param1String, (Locale)null);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public TimeOfDay withMaximumValue() {
/* 1211 */       return setCopy(getMaximumValue());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public TimeOfDay withMinimumValue() {
/* 1224 */       return setCopy(getMinimumValue());
/*      */     }
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/TimeOfDay.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */