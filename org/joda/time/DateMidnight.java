/*      */ package org.joda.time;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.util.Locale;
/*      */ import org.joda.convert.FromString;
/*      */ import org.joda.time.base.BaseDateTime;
/*      */ import org.joda.time.field.AbstractReadableInstantFieldProperty;
/*      */ import org.joda.time.format.DateTimeFormatter;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*      */ public final class DateMidnight
/*      */   extends BaseDateTime
/*      */   implements ReadableDateTime, Serializable
/*      */ {
/*      */   private static final long serialVersionUID = 156371964018738L;
/*      */   
/*      */   public static DateMidnight now() {
/*   95 */     return new DateMidnight();
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
/*      */   public static DateMidnight now(DateTimeZone paramDateTimeZone) {
/*  108 */     if (paramDateTimeZone == null) {
/*  109 */       throw new NullPointerException("Zone must not be null");
/*      */     }
/*  111 */     return new DateMidnight(paramDateTimeZone);
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
/*      */   public static DateMidnight now(Chronology paramChronology) {
/*  124 */     if (paramChronology == null) {
/*  125 */       throw new NullPointerException("Chronology must not be null");
/*      */     }
/*  127 */     return new DateMidnight(paramChronology);
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
/*      */   @FromString
/*      */   public static DateMidnight parse(String paramString) {
/*  141 */     return parse(paramString, ISODateTimeFormat.dateTimeParser().withOffsetParsed());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static DateMidnight parse(String paramString, DateTimeFormatter paramDateTimeFormatter) {
/*  152 */     return paramDateTimeFormatter.parseDateTime(paramString).toDateMidnight();
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
/*      */   public DateMidnight() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DateMidnight(DateTimeZone paramDateTimeZone) {
/*  178 */     super(paramDateTimeZone);
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
/*      */   public DateMidnight(Chronology paramChronology) {
/*  193 */     super(paramChronology);
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
/*      */   public DateMidnight(long paramLong) {
/*  205 */     super(paramLong);
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
/*      */   public DateMidnight(long paramLong, DateTimeZone paramDateTimeZone) {
/*  219 */     super(paramLong, paramDateTimeZone);
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
/*      */   public DateMidnight(long paramLong, Chronology paramChronology) {
/*  234 */     super(paramLong, paramChronology);
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
/*      */   public DateMidnight(Object paramObject) {
/*  256 */     super(paramObject, (Chronology)null);
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
/*      */   public DateMidnight(Object paramObject, DateTimeZone paramDateTimeZone) {
/*  281 */     super(paramObject, paramDateTimeZone);
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
/*      */   public DateMidnight(Object paramObject, Chronology paramChronology) {
/*  303 */     super(paramObject, DateTimeUtils.getChronology(paramChronology));
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
/*      */   public DateMidnight(int paramInt1, int paramInt2, int paramInt3) {
/*  317 */     super(paramInt1, paramInt2, paramInt3, 0, 0, 0, 0);
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
/*      */   public DateMidnight(int paramInt1, int paramInt2, int paramInt3, DateTimeZone paramDateTimeZone) {
/*  333 */     super(paramInt1, paramInt2, paramInt3, 0, 0, 0, 0, paramDateTimeZone);
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
/*      */   public DateMidnight(int paramInt1, int paramInt2, int paramInt3, Chronology paramChronology) {
/*  350 */     super(paramInt1, paramInt2, paramInt3, 0, 0, 0, 0, paramChronology);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected long checkInstant(long paramLong, Chronology paramChronology) {
/*  361 */     return paramChronology.dayOfMonth().roundFloor(paramLong);
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
/*      */   public DateMidnight withMillis(long paramLong) {
/*  376 */     Chronology chronology = getChronology();
/*  377 */     paramLong = checkInstant(paramLong, chronology);
/*  378 */     return (paramLong == getMillis()) ? this : new DateMidnight(paramLong, chronology);
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
/*      */   public DateMidnight withChronology(Chronology paramChronology) {
/*  403 */     return (paramChronology == getChronology()) ? this : new DateMidnight(getMillis(), paramChronology);
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
/*      */   public DateMidnight withZoneRetainFields(DateTimeZone paramDateTimeZone) {
/*  415 */     paramDateTimeZone = DateTimeUtils.getZone(paramDateTimeZone);
/*  416 */     DateTimeZone dateTimeZone = DateTimeUtils.getZone(getZone());
/*  417 */     if (paramDateTimeZone == dateTimeZone) {
/*  418 */       return this;
/*      */     }
/*      */     
/*  421 */     long l = dateTimeZone.getMillisKeepLocal(paramDateTimeZone, getMillis());
/*  422 */     return new DateMidnight(l, getChronology().withZone(paramDateTimeZone));
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
/*      */   public DateMidnight withFields(ReadablePartial paramReadablePartial) {
/*  439 */     if (paramReadablePartial == null) {
/*  440 */       return this;
/*      */     }
/*  442 */     return withMillis(getChronology().set(paramReadablePartial, getMillis()));
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
/*      */   public DateMidnight withField(DateTimeFieldType paramDateTimeFieldType, int paramInt) {
/*  465 */     if (paramDateTimeFieldType == null) {
/*  466 */       throw new IllegalArgumentException("Field must not be null");
/*      */     }
/*  468 */     long l = paramDateTimeFieldType.getField(getChronology()).set(getMillis(), paramInt);
/*  469 */     return withMillis(l);
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
/*      */   public DateMidnight withFieldAdded(DurationFieldType paramDurationFieldType, int paramInt) {
/*  491 */     if (paramDurationFieldType == null) {
/*  492 */       throw new IllegalArgumentException("Field must not be null");
/*      */     }
/*  494 */     if (paramInt == 0) {
/*  495 */       return this;
/*      */     }
/*  497 */     long l = paramDurationFieldType.getField(getChronology()).add(getMillis(), paramInt);
/*  498 */     return withMillis(l);
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
/*      */   public DateMidnight withDurationAdded(long paramLong, int paramInt) {
/*  513 */     if (paramLong == 0L || paramInt == 0) {
/*  514 */       return this;
/*      */     }
/*  516 */     long l = getChronology().add(getMillis(), paramLong, paramInt);
/*  517 */     return withMillis(l);
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
/*      */   public DateMidnight withDurationAdded(ReadableDuration paramReadableDuration, int paramInt) {
/*  531 */     if (paramReadableDuration == null || paramInt == 0) {
/*  532 */       return this;
/*      */     }
/*  534 */     return withDurationAdded(paramReadableDuration.getMillis(), paramInt);
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
/*      */   public DateMidnight withPeriodAdded(ReadablePeriod paramReadablePeriod, int paramInt) {
/*  553 */     if (paramReadablePeriod == null || paramInt == 0) {
/*  554 */       return this;
/*      */     }
/*  556 */     long l = getChronology().add(paramReadablePeriod, getMillis(), paramInt);
/*  557 */     return withMillis(l);
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
/*      */   public DateMidnight plus(long paramLong) {
/*  571 */     return withDurationAdded(paramLong, 1);
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
/*      */   public DateMidnight plus(ReadableDuration paramReadableDuration) {
/*  584 */     return withDurationAdded(paramReadableDuration, 1);
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
/*      */   public DateMidnight plus(ReadablePeriod paramReadablePeriod) {
/*  601 */     return withPeriodAdded(paramReadablePeriod, 1);
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
/*      */   public DateMidnight plusYears(int paramInt) {
/*  622 */     if (paramInt == 0) {
/*  623 */       return this;
/*      */     }
/*  625 */     long l = getChronology().years().add(getMillis(), paramInt);
/*  626 */     return withMillis(l);
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
/*      */   public DateMidnight plusMonths(int paramInt) {
/*  646 */     if (paramInt == 0) {
/*  647 */       return this;
/*      */     }
/*  649 */     long l = getChronology().months().add(getMillis(), paramInt);
/*  650 */     return withMillis(l);
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
/*      */   public DateMidnight plusWeeks(int paramInt) {
/*  670 */     if (paramInt == 0) {
/*  671 */       return this;
/*      */     }
/*  673 */     long l = getChronology().weeks().add(getMillis(), paramInt);
/*  674 */     return withMillis(l);
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
/*      */   public DateMidnight plusDays(int paramInt) {
/*  694 */     if (paramInt == 0) {
/*  695 */       return this;
/*      */     }
/*  697 */     long l = getChronology().days().add(getMillis(), paramInt);
/*  698 */     return withMillis(l);
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
/*      */   public DateMidnight minus(long paramLong) {
/*  712 */     return withDurationAdded(paramLong, -1);
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
/*      */   public DateMidnight minus(ReadableDuration paramReadableDuration) {
/*  725 */     return withDurationAdded(paramReadableDuration, -1);
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
/*      */   public DateMidnight minus(ReadablePeriod paramReadablePeriod) {
/*  742 */     return withPeriodAdded(paramReadablePeriod, -1);
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
/*      */   public DateMidnight minusYears(int paramInt) {
/*  763 */     if (paramInt == 0) {
/*  764 */       return this;
/*      */     }
/*  766 */     long l = getChronology().years().subtract(getMillis(), paramInt);
/*  767 */     return withMillis(l);
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
/*      */   public DateMidnight minusMonths(int paramInt) {
/*  787 */     if (paramInt == 0) {
/*  788 */       return this;
/*      */     }
/*  790 */     long l = getChronology().months().subtract(getMillis(), paramInt);
/*  791 */     return withMillis(l);
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
/*      */   public DateMidnight minusWeeks(int paramInt) {
/*  811 */     if (paramInt == 0) {
/*  812 */       return this;
/*      */     }
/*  814 */     long l = getChronology().weeks().subtract(getMillis(), paramInt);
/*  815 */     return withMillis(l);
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
/*      */   public DateMidnight minusDays(int paramInt) {
/*  835 */     if (paramInt == 0) {
/*  836 */       return this;
/*      */     }
/*  838 */     long l = getChronology().days().subtract(getMillis(), paramInt);
/*  839 */     return withMillis(l);
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
/*      */   public Property property(DateTimeFieldType paramDateTimeFieldType) {
/*  851 */     if (paramDateTimeFieldType == null) {
/*  852 */       throw new IllegalArgumentException("The DateTimeFieldType must not be null");
/*      */     }
/*  854 */     DateTimeField dateTimeField = paramDateTimeFieldType.getField(getChronology());
/*  855 */     if (!dateTimeField.isSupported()) {
/*  856 */       throw new IllegalArgumentException("Field '" + paramDateTimeFieldType + "' is not supported");
/*      */     }
/*  858 */     return new Property(this, dateTimeField);
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
/*      */   @Deprecated
/*      */   public YearMonthDay toYearMonthDay() {
/*  871 */     return new YearMonthDay(getMillis(), getChronology());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LocalDate toLocalDate() {
/*  882 */     return new LocalDate(getMillis(), getChronology());
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
/*      */   public Interval toInterval() {
/*  895 */     Chronology chronology = getChronology();
/*  896 */     long l1 = getMillis();
/*  897 */     long l2 = DurationFieldType.days().getField(chronology).add(l1, 1);
/*  898 */     return new Interval(l1, l2, chronology);
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
/*      */   public DateMidnight withEra(int paramInt) {
/*  915 */     return withMillis(getChronology().era().set(getMillis(), paramInt));
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
/*      */   public DateMidnight withCenturyOfEra(int paramInt) {
/*  931 */     return withMillis(getChronology().centuryOfEra().set(getMillis(), paramInt));
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
/*      */   public DateMidnight withYearOfEra(int paramInt) {
/*  947 */     return withMillis(getChronology().yearOfEra().set(getMillis(), paramInt));
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
/*      */   public DateMidnight withYearOfCentury(int paramInt) {
/*  963 */     return withMillis(getChronology().yearOfCentury().set(getMillis(), paramInt));
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
/*      */   public DateMidnight withYear(int paramInt) {
/*  979 */     return withMillis(getChronology().year().set(getMillis(), paramInt));
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
/*      */   public DateMidnight withWeekyear(int paramInt) {
/* 1001 */     return withMillis(getChronology().weekyear().set(getMillis(), paramInt));
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
/*      */   public DateMidnight withMonthOfYear(int paramInt) {
/* 1017 */     return withMillis(getChronology().monthOfYear().set(getMillis(), paramInt));
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
/*      */   public DateMidnight withWeekOfWeekyear(int paramInt) {
/* 1038 */     return withMillis(getChronology().weekOfWeekyear().set(getMillis(), paramInt));
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
/*      */   public DateMidnight withDayOfYear(int paramInt) {
/* 1054 */     return withMillis(getChronology().dayOfYear().set(getMillis(), paramInt));
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
/*      */   public DateMidnight withDayOfMonth(int paramInt) {
/* 1070 */     return withMillis(getChronology().dayOfMonth().set(getMillis(), paramInt));
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
/*      */   public DateMidnight withDayOfWeek(int paramInt) {
/* 1086 */     return withMillis(getChronology().dayOfWeek().set(getMillis(), paramInt));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Property era() {
/* 1097 */     return new Property(this, getChronology().era());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Property centuryOfEra() {
/* 1106 */     return new Property(this, getChronology().centuryOfEra());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Property yearOfCentury() {
/* 1115 */     return new Property(this, getChronology().yearOfCentury());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Property yearOfEra() {
/* 1124 */     return new Property(this, getChronology().yearOfEra());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Property year() {
/* 1133 */     return new Property(this, getChronology().year());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Property weekyear() {
/* 1142 */     return new Property(this, getChronology().weekyear());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Property monthOfYear() {
/* 1151 */     return new Property(this, getChronology().monthOfYear());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Property weekOfWeekyear() {
/* 1160 */     return new Property(this, getChronology().weekOfWeekyear());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Property dayOfYear() {
/* 1169 */     return new Property(this, getChronology().dayOfYear());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Property dayOfMonth() {
/* 1178 */     return new Property(this, getChronology().dayOfMonth());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Property dayOfWeek() {
/* 1187 */     return new Property(this, getChronology().dayOfWeek());
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
/*      */   public static final class Property
/*      */     extends AbstractReadableInstantFieldProperty
/*      */   {
/*      */     private static final long serialVersionUID = 257629620L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private DateMidnight iInstant;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private DateTimeField iField;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Property(DateMidnight param1DateMidnight, DateTimeField param1DateTimeField) {
/* 1237 */       this.iInstant = param1DateMidnight;
/* 1238 */       this.iField = param1DateTimeField;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void writeObject(ObjectOutputStream param1ObjectOutputStream) throws IOException {
/* 1245 */       param1ObjectOutputStream.writeObject(this.iInstant);
/* 1246 */       param1ObjectOutputStream.writeObject(this.iField.getType());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void readObject(ObjectInputStream param1ObjectInputStream) throws IOException, ClassNotFoundException {
/* 1253 */       this.iInstant = (DateMidnight)param1ObjectInputStream.readObject();
/* 1254 */       DateTimeFieldType dateTimeFieldType = (DateTimeFieldType)param1ObjectInputStream.readObject();
/* 1255 */       this.iField = dateTimeFieldType.getField(this.iInstant.getChronology());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public DateTimeField getField() {
/* 1265 */       return this.iField;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected long getMillis() {
/* 1274 */       return this.iInstant.getMillis();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected Chronology getChronology() {
/* 1284 */       return this.iInstant.getChronology();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public DateMidnight getDateMidnight() {
/* 1293 */       return this.iInstant;
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
/*      */     public DateMidnight addToCopy(int param1Int) {
/* 1310 */       return this.iInstant.withMillis(this.iField.add(this.iInstant.getMillis(), param1Int));
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
/*      */     public DateMidnight addToCopy(long param1Long) {
/* 1326 */       return this.iInstant.withMillis(this.iField.add(this.iInstant.getMillis(), param1Long));
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
/*      */     public DateMidnight addWrapFieldToCopy(int param1Int) {
/* 1344 */       return this.iInstant.withMillis(this.iField.addWrapField(this.iInstant.getMillis(), param1Int));
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
/*      */     public DateMidnight setCopy(int param1Int) {
/* 1361 */       return this.iInstant.withMillis(this.iField.set(this.iInstant.getMillis(), param1Int));
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
/*      */     public DateMidnight setCopy(String param1String, Locale param1Locale) {
/* 1378 */       return this.iInstant.withMillis(this.iField.set(this.iInstant.getMillis(), param1String, param1Locale));
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
/*      */     public DateMidnight setCopy(String param1String) {
/* 1394 */       return setCopy(param1String, (Locale)null);
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
/*      */     public DateMidnight withMaximumValue() {
/* 1414 */       return setCopy(getMaximumValue());
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
/*      */     public DateMidnight withMinimumValue() {
/* 1427 */       return setCopy(getMinimumValue());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public DateMidnight roundFloorCopy() {
/* 1437 */       return this.iInstant.withMillis(this.iField.roundFloor(this.iInstant.getMillis()));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public DateMidnight roundCeilingCopy() {
/* 1446 */       return this.iInstant.withMillis(this.iField.roundCeiling(this.iInstant.getMillis()));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public DateMidnight roundHalfFloorCopy() {
/* 1456 */       return this.iInstant.withMillis(this.iField.roundHalfFloor(this.iInstant.getMillis()));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public DateMidnight roundHalfCeilingCopy() {
/* 1466 */       return this.iInstant.withMillis(this.iField.roundHalfCeiling(this.iInstant.getMillis()));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public DateMidnight roundHalfEvenCopy() {
/* 1476 */       return this.iInstant.withMillis(this.iField.roundHalfEven(this.iInstant.getMillis()));
/*      */     }
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/DateMidnight.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */