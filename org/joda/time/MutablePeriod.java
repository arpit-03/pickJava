/*      */ package org.joda.time;
/*      */ 
/*      */ import java.io.Serializable;
/*      */ import org.joda.convert.FromString;
/*      */ import org.joda.time.base.BasePeriod;
/*      */ import org.joda.time.field.FieldUtils;
/*      */ import org.joda.time.format.ISOPeriodFormat;
/*      */ import org.joda.time.format.PeriodFormatter;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class MutablePeriod
/*      */   extends BasePeriod
/*      */   implements ReadWritablePeriod, Cloneable, Serializable
/*      */ {
/*      */   private static final long serialVersionUID = 3436451121567212165L;
/*      */   
/*      */   @FromString
/*      */   public static MutablePeriod parse(String paramString) {
/*   74 */     return parse(paramString, ISOPeriodFormat.standard());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static MutablePeriod parse(String paramString, PeriodFormatter paramPeriodFormatter) {
/*   85 */     return paramPeriodFormatter.parsePeriod(paramString).toMutablePeriod();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MutablePeriod() {
/*   93 */     super(0L, null, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MutablePeriod(PeriodType paramPeriodType) {
/*  102 */     super(0L, paramPeriodType, null);
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
/*      */   public MutablePeriod(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  114 */     super(0, 0, 0, 0, paramInt1, paramInt2, paramInt3, paramInt4, PeriodType.standard());
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
/*      */   public MutablePeriod(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8) {
/*  131 */     super(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, PeriodType.standard());
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
/*      */   public MutablePeriod(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, PeriodType paramPeriodType) {
/*  150 */     super(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, paramPeriodType);
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
/*      */ 
/*      */ 
/*      */   
/*      */   public MutablePeriod(long paramLong) {
/*  180 */     super(paramLong);
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
/*      */   public MutablePeriod(long paramLong, PeriodType paramPeriodType) {
/*  200 */     super(paramLong, paramPeriodType, null);
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
/*      */   public MutablePeriod(long paramLong, Chronology paramChronology) {
/*  221 */     super(paramLong, null, paramChronology);
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
/*      */   public MutablePeriod(long paramLong, PeriodType paramPeriodType, Chronology paramChronology) {
/*  242 */     super(paramLong, paramPeriodType, paramChronology);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MutablePeriod(long paramLong1, long paramLong2) {
/*  253 */     super(paramLong1, paramLong2, null, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MutablePeriod(long paramLong1, long paramLong2, PeriodType paramPeriodType) {
/*  264 */     super(paramLong1, paramLong2, paramPeriodType, null);
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
/*      */   public MutablePeriod(long paramLong1, long paramLong2, Chronology paramChronology) {
/*  276 */     super(paramLong1, paramLong2, null, paramChronology);
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
/*      */   public MutablePeriod(long paramLong1, long paramLong2, PeriodType paramPeriodType, Chronology paramChronology) {
/*  288 */     super(paramLong1, paramLong2, paramPeriodType, paramChronology);
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
/*      */   public MutablePeriod(ReadableInstant paramReadableInstant1, ReadableInstant paramReadableInstant2) {
/*  302 */     super(paramReadableInstant1, paramReadableInstant2, null);
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
/*      */   public MutablePeriod(ReadableInstant paramReadableInstant1, ReadableInstant paramReadableInstant2, PeriodType paramPeriodType) {
/*  316 */     super(paramReadableInstant1, paramReadableInstant2, paramPeriodType);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MutablePeriod(ReadableInstant paramReadableInstant, ReadableDuration paramReadableDuration) {
/*  326 */     super(paramReadableInstant, paramReadableDuration, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MutablePeriod(ReadableInstant paramReadableInstant, ReadableDuration paramReadableDuration, PeriodType paramPeriodType) {
/*  337 */     super(paramReadableInstant, paramReadableDuration, paramPeriodType);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MutablePeriod(ReadableDuration paramReadableDuration, ReadableInstant paramReadableInstant) {
/*  347 */     super(paramReadableDuration, paramReadableInstant, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MutablePeriod(ReadableDuration paramReadableDuration, ReadableInstant paramReadableInstant, PeriodType paramPeriodType) {
/*  358 */     super(paramReadableDuration, paramReadableInstant, paramPeriodType);
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
/*      */   public MutablePeriod(Object paramObject) {
/*  374 */     super(paramObject, null, null);
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
/*      */   public MutablePeriod(Object paramObject, PeriodType paramPeriodType) {
/*  391 */     super(paramObject, paramPeriodType, null);
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
/*      */   public MutablePeriod(Object paramObject, Chronology paramChronology) {
/*  408 */     super(paramObject, null, paramChronology);
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
/*      */   public MutablePeriod(Object paramObject, PeriodType paramPeriodType, Chronology paramChronology) {
/*  426 */     super(paramObject, paramPeriodType, paramChronology);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clear() {
/*  434 */     setValues(new int[size()]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setValue(int paramInt1, int paramInt2) {
/*  445 */     super.setValue(paramInt1, paramInt2);
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
/*      */   public void set(DurationFieldType paramDurationFieldType, int paramInt) {
/*  458 */     setField(paramDurationFieldType, paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPeriod(ReadablePeriod paramReadablePeriod) {
/*  468 */     super.setPeriod(paramReadablePeriod);
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
/*      */   public void setPeriod(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8) {
/*  486 */     super.setPeriod(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPeriod(ReadableInterval paramReadableInterval) {
/*  497 */     if (paramReadableInterval == null) {
/*  498 */       setPeriod(0L);
/*      */     } else {
/*  500 */       Chronology chronology = DateTimeUtils.getChronology(paramReadableInterval.getChronology());
/*  501 */       setPeriod(paramReadableInterval.getStartMillis(), paramReadableInterval.getEndMillis(), chronology);
/*      */     } 
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
/*      */   public void setPeriod(ReadableInstant paramReadableInstant1, ReadableInstant paramReadableInstant2) {
/*  516 */     if (paramReadableInstant1 == paramReadableInstant2) {
/*  517 */       setPeriod(0L);
/*      */     } else {
/*  519 */       long l1 = DateTimeUtils.getInstantMillis(paramReadableInstant1);
/*  520 */       long l2 = DateTimeUtils.getInstantMillis(paramReadableInstant2);
/*  521 */       Chronology chronology = DateTimeUtils.getIntervalChronology(paramReadableInstant1, paramReadableInstant2);
/*  522 */       setPeriod(l1, l2, chronology);
/*      */     } 
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
/*      */   public void setPeriod(long paramLong1, long paramLong2) {
/*  535 */     setPeriod(paramLong1, paramLong2, (Chronology)null);
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
/*      */   public void setPeriod(long paramLong1, long paramLong2, Chronology paramChronology) {
/*  547 */     paramChronology = DateTimeUtils.getChronology(paramChronology);
/*  548 */     setValues(paramChronology.get(this, paramLong1, paramLong2));
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
/*      */   public void setPeriod(ReadableDuration paramReadableDuration) {
/*  563 */     setPeriod(paramReadableDuration, (Chronology)null);
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
/*      */   public void setPeriod(ReadableDuration paramReadableDuration, Chronology paramChronology) {
/*  579 */     long l = DateTimeUtils.getDurationMillis(paramReadableDuration);
/*  580 */     setPeriod(l, paramChronology);
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
/*      */   public void setPeriod(long paramLong) {
/*  595 */     setPeriod(paramLong, (Chronology)null);
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
/*      */   public void setPeriod(long paramLong, Chronology paramChronology) {
/*  610 */     paramChronology = DateTimeUtils.getChronology(paramChronology);
/*  611 */     setValues(paramChronology.get(this, paramLong));
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
/*      */   public void add(DurationFieldType paramDurationFieldType, int paramInt) {
/*  625 */     addField(paramDurationFieldType, paramInt);
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
/*      */   public void add(ReadablePeriod paramReadablePeriod) {
/*  637 */     addPeriod(paramReadablePeriod);
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
/*      */   public void add(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8) {
/*  657 */     setPeriod(FieldUtils.safeAdd(getYears(), paramInt1), FieldUtils.safeAdd(getMonths(), paramInt2), FieldUtils.safeAdd(getWeeks(), paramInt3), FieldUtils.safeAdd(getDays(), paramInt4), FieldUtils.safeAdd(getHours(), paramInt5), FieldUtils.safeAdd(getMinutes(), paramInt6), FieldUtils.safeAdd(getSeconds(), paramInt7), FieldUtils.safeAdd(getMillis(), paramInt8));
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
/*      */   public void add(ReadableInterval paramReadableInterval) {
/*  677 */     if (paramReadableInterval != null) {
/*  678 */       add(paramReadableInterval.toPeriod(getPeriodType()));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void add(ReadableDuration paramReadableDuration) {
/*  690 */     if (paramReadableDuration != null) {
/*  691 */       add(new Period(paramReadableDuration.getMillis(), getPeriodType()));
/*      */     }
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
/*      */   public void add(long paramLong) {
/*  707 */     add(new Period(paramLong, getPeriodType()));
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
/*      */   public void add(long paramLong, Chronology paramChronology) {
/*  723 */     add(new Period(paramLong, getPeriodType(), paramChronology));
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
/*      */   public void mergePeriod(ReadablePeriod paramReadablePeriod) {
/*  736 */     super.mergePeriod(paramReadablePeriod);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getYears() {
/*  746 */     return getPeriodType().getIndexedField(this, PeriodType.YEAR_INDEX);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMonths() {
/*  755 */     return getPeriodType().getIndexedField(this, PeriodType.MONTH_INDEX);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getWeeks() {
/*  764 */     return getPeriodType().getIndexedField(this, PeriodType.WEEK_INDEX);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getDays() {
/*  773 */     return getPeriodType().getIndexedField(this, PeriodType.DAY_INDEX);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getHours() {
/*  783 */     return getPeriodType().getIndexedField(this, PeriodType.HOUR_INDEX);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMinutes() {
/*  792 */     return getPeriodType().getIndexedField(this, PeriodType.MINUTE_INDEX);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getSeconds() {
/*  801 */     return getPeriodType().getIndexedField(this, PeriodType.SECOND_INDEX);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMillis() {
/*  810 */     return getPeriodType().getIndexedField(this, PeriodType.MILLI_INDEX);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setYears(int paramInt) {
/*  821 */     setField(DurationFieldType.years(), paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addYears(int paramInt) {
/*  832 */     addField(DurationFieldType.years(), paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMonths(int paramInt) {
/*  843 */     setField(DurationFieldType.months(), paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addMonths(int paramInt) {
/*  854 */     addField(DurationFieldType.months(), paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setWeeks(int paramInt) {
/*  865 */     setField(DurationFieldType.weeks(), paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addWeeks(int paramInt) {
/*  876 */     addField(DurationFieldType.weeks(), paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDays(int paramInt) {
/*  887 */     setField(DurationFieldType.days(), paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addDays(int paramInt) {
/*  898 */     addField(DurationFieldType.days(), paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setHours(int paramInt) {
/*  909 */     setField(DurationFieldType.hours(), paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addHours(int paramInt) {
/*  920 */     addField(DurationFieldType.hours(), paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMinutes(int paramInt) {
/*  931 */     setField(DurationFieldType.minutes(), paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addMinutes(int paramInt) {
/*  942 */     addField(DurationFieldType.minutes(), paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSeconds(int paramInt) {
/*  953 */     setField(DurationFieldType.seconds(), paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addSeconds(int paramInt) {
/*  964 */     addField(DurationFieldType.seconds(), paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMillis(int paramInt) {
/*  975 */     setField(DurationFieldType.millis(), paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addMillis(int paramInt) {
/*  986 */     addField(DurationFieldType.millis(), paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MutablePeriod copy() {
/*  997 */     return (MutablePeriod)clone();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object clone() {
/*      */     try {
/* 1007 */       return super.clone();
/* 1008 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/* 1009 */       throw new InternalError("Clone error");
/*      */     } 
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/MutablePeriod.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */