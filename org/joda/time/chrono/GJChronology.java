/*      */ package org.joda.time.chrono;
/*      */ 
/*      */ import java.util.Locale;
/*      */ import java.util.concurrent.ConcurrentHashMap;
/*      */ import org.joda.time.Chronology;
/*      */ import org.joda.time.DateTimeField;
/*      */ import org.joda.time.DateTimeUtils;
/*      */ import org.joda.time.DateTimeZone;
/*      */ import org.joda.time.DurationField;
/*      */ import org.joda.time.IllegalFieldValueException;
/*      */ import org.joda.time.Instant;
/*      */ import org.joda.time.LocalDate;
/*      */ import org.joda.time.ReadableInstant;
/*      */ import org.joda.time.ReadablePartial;
/*      */ import org.joda.time.field.BaseDateTimeField;
/*      */ import org.joda.time.field.DecoratedDurationField;
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
/*      */ public final class GJChronology
/*      */   extends AssembledChronology
/*      */ {
/*      */   private static final long serialVersionUID = -2545574827706931671L;
/*      */   
/*      */   private static long convertByYear(long paramLong, Chronology paramChronology1, Chronology paramChronology2) {
/*   82 */     return paramChronology2.getDateTimeMillis(paramChronology1.year().get(paramLong), paramChronology1.monthOfYear().get(paramLong), paramChronology1.dayOfMonth().get(paramLong), paramChronology1.millisOfDay().get(paramLong));
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
/*      */   private static long convertByWeekyear(long paramLong, Chronology paramChronology1, Chronology paramChronology2) {
/*   94 */     long l = paramChronology2.weekyear().set(0L, paramChronology1.weekyear().get(paramLong));
/*   95 */     l = paramChronology2.weekOfWeekyear().set(l, paramChronology1.weekOfWeekyear().get(paramLong));
/*   96 */     l = paramChronology2.dayOfWeek().set(l, paramChronology1.dayOfWeek().get(paramLong));
/*   97 */     l = paramChronology2.millisOfDay().set(l, paramChronology1.millisOfDay().get(paramLong));
/*   98 */     return l;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  104 */   static final Instant DEFAULT_CUTOVER = new Instant(-12219292800000L);
/*      */ 
/*      */   
/*  107 */   private static final ConcurrentHashMap<GJCacheKey, GJChronology> cCache = new ConcurrentHashMap<GJCacheKey, GJChronology>();
/*      */ 
/*      */   
/*      */   private JulianChronology iJulianChronology;
/*      */   
/*      */   private GregorianChronology iGregorianChronology;
/*      */   
/*      */   private Instant iCutoverInstant;
/*      */   
/*      */   private long iCutoverMillis;
/*      */   
/*      */   private long iGapDuration;
/*      */ 
/*      */   
/*      */   public static GJChronology getInstanceUTC() {
/*  122 */     return getInstance(DateTimeZone.UTC, (ReadableInstant)DEFAULT_CUTOVER, 4);
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
/*      */   public static GJChronology getInstance() {
/*  138 */     return getInstance(DateTimeZone.getDefault(), (ReadableInstant)DEFAULT_CUTOVER, 4);
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
/*      */   public static GJChronology getInstance(DateTimeZone paramDateTimeZone) {
/*  154 */     return getInstance(paramDateTimeZone, (ReadableInstant)DEFAULT_CUTOVER, 4);
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
/*      */   public static GJChronology getInstance(DateTimeZone paramDateTimeZone, ReadableInstant paramReadableInstant) {
/*  172 */     return getInstance(paramDateTimeZone, paramReadableInstant, 4);
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
/*      */   public static GJChronology getInstance(DateTimeZone paramDateTimeZone, ReadableInstant paramReadableInstant, int paramInt) {
/*      */     Instant instant;
/*  188 */     paramDateTimeZone = DateTimeUtils.getZone(paramDateTimeZone);
/*      */     
/*  190 */     if (paramReadableInstant == null) {
/*  191 */       instant = DEFAULT_CUTOVER;
/*      */     } else {
/*  193 */       instant = paramReadableInstant.toInstant();
/*  194 */       LocalDate localDate = new LocalDate(instant.getMillis(), GregorianChronology.getInstance(paramDateTimeZone));
/*  195 */       if (localDate.getYear() <= 0) {
/*  196 */         throw new IllegalArgumentException("Cutover too early. Must be on or after 0001-01-01.");
/*      */       }
/*      */     } 
/*      */     
/*  200 */     GJCacheKey gJCacheKey = new GJCacheKey(paramDateTimeZone, instant, paramInt);
/*  201 */     GJChronology gJChronology = cCache.get(gJCacheKey);
/*  202 */     if (gJChronology == null) {
/*  203 */       if (paramDateTimeZone == DateTimeZone.UTC) {
/*  204 */         gJChronology = new GJChronology(JulianChronology.getInstance(paramDateTimeZone, paramInt), GregorianChronology.getInstance(paramDateTimeZone, paramInt), instant);
/*      */       
/*      */       }
/*      */       else {
/*      */         
/*  209 */         gJChronology = getInstance(DateTimeZone.UTC, (ReadableInstant)instant, paramInt);
/*  210 */         gJChronology = new GJChronology(ZonedChronology.getInstance(gJChronology, paramDateTimeZone), gJChronology.iJulianChronology, gJChronology.iGregorianChronology, gJChronology.iCutoverInstant);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  216 */       GJChronology gJChronology1 = cCache.putIfAbsent(gJCacheKey, gJChronology);
/*  217 */       if (gJChronology1 != null) {
/*  218 */         gJChronology = gJChronology1;
/*      */       }
/*      */     } 
/*  221 */     return gJChronology;
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
/*      */   public static GJChronology getInstance(DateTimeZone paramDateTimeZone, long paramLong, int paramInt) {
/*      */     Instant instant;
/*  238 */     if (paramLong == DEFAULT_CUTOVER.getMillis()) {
/*  239 */       instant = null;
/*      */     } else {
/*  241 */       instant = new Instant(paramLong);
/*      */     } 
/*  243 */     return getInstance(paramDateTimeZone, (ReadableInstant)instant, paramInt);
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
/*      */   private GJChronology(JulianChronology paramJulianChronology, GregorianChronology paramGregorianChronology, Instant paramInstant) {
/*  262 */     super((Chronology)null, new Object[] { paramJulianChronology, paramGregorianChronology, paramInstant });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private GJChronology(Chronology paramChronology, JulianChronology paramJulianChronology, GregorianChronology paramGregorianChronology, Instant paramInstant) {
/*  272 */     super(paramChronology, new Object[] { paramJulianChronology, paramGregorianChronology, paramInstant });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Object readResolve() {
/*  279 */     return getInstance(getZone(), (ReadableInstant)this.iCutoverInstant, getMinimumDaysInFirstWeek());
/*      */   }
/*      */   
/*      */   public DateTimeZone getZone() {
/*      */     Chronology chronology;
/*  284 */     if ((chronology = getBase()) != null) {
/*  285 */       return chronology.getZone();
/*      */     }
/*  287 */     return DateTimeZone.UTC;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Chronology withUTC() {
/*  298 */     return withZone(DateTimeZone.UTC);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Chronology withZone(DateTimeZone paramDateTimeZone) {
/*  308 */     if (paramDateTimeZone == null) {
/*  309 */       paramDateTimeZone = DateTimeZone.getDefault();
/*      */     }
/*  311 */     if (paramDateTimeZone == getZone()) {
/*  312 */       return this;
/*      */     }
/*  314 */     return getInstance(paramDateTimeZone, (ReadableInstant)this.iCutoverInstant, getMinimumDaysInFirstWeek());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long getDateTimeMillis(int paramInt1, int paramInt2, int paramInt3, int paramInt4) throws IllegalArgumentException {
/*      */     Chronology chronology;
/*  322 */     if ((chronology = getBase()) != null) {
/*  323 */       return chronology.getDateTimeMillis(paramInt1, paramInt2, paramInt3, paramInt4);
/*      */     }
/*      */ 
/*      */     
/*  327 */     long l = this.iGregorianChronology.getDateTimeMillis(paramInt1, paramInt2, paramInt3, paramInt4);
/*      */     
/*  329 */     if (l < this.iCutoverMillis) {
/*      */       
/*  331 */       l = this.iJulianChronology.getDateTimeMillis(paramInt1, paramInt2, paramInt3, paramInt4);
/*      */       
/*  333 */       if (l >= this.iCutoverMillis)
/*      */       {
/*  335 */         throw new IllegalArgumentException("Specified date does not exist");
/*      */       }
/*      */     } 
/*  338 */     return l;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long getDateTimeMillis(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7) throws IllegalArgumentException {
/*      */     long l;
/*      */     Chronology chronology;
/*  347 */     if ((chronology = getBase()) != null) {
/*  348 */       return chronology.getDateTimeMillis(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  356 */       l = this.iGregorianChronology.getDateTimeMillis(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7);
/*      */     
/*      */     }
/*  359 */     catch (IllegalFieldValueException illegalFieldValueException) {
/*  360 */       if (paramInt2 != 2 || paramInt3 != 29) {
/*  361 */         throw illegalFieldValueException;
/*      */       }
/*  363 */       l = this.iGregorianChronology.getDateTimeMillis(paramInt1, paramInt2, 28, paramInt4, paramInt5, paramInt6, paramInt7);
/*      */ 
/*      */       
/*  366 */       if (l >= this.iCutoverMillis) {
/*  367 */         throw illegalFieldValueException;
/*      */       }
/*      */     } 
/*  370 */     if (l < this.iCutoverMillis) {
/*      */       
/*  372 */       l = this.iJulianChronology.getDateTimeMillis(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7);
/*      */ 
/*      */       
/*  375 */       if (l >= this.iCutoverMillis)
/*      */       {
/*  377 */         throw new IllegalArgumentException("Specified date does not exist");
/*      */       }
/*      */     } 
/*  380 */     return l;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Instant getGregorianCutover() {
/*  388 */     return this.iCutoverInstant;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMinimumDaysInFirstWeek() {
/*  397 */     return this.iGregorianChronology.getMinimumDaysInFirstWeek();
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
/*      */   public boolean equals(Object paramObject) {
/*  409 */     if (this == paramObject) {
/*  410 */       return true;
/*      */     }
/*  412 */     if (paramObject instanceof GJChronology) {
/*  413 */       GJChronology gJChronology = (GJChronology)paramObject;
/*  414 */       return (this.iCutoverMillis == gJChronology.iCutoverMillis && getMinimumDaysInFirstWeek() == gJChronology.getMinimumDaysInFirstWeek() && getZone().equals(gJChronology.getZone()));
/*      */     } 
/*      */ 
/*      */     
/*  418 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int hashCode() {
/*  428 */     return "GJ".hashCode() * 11 + getZone().hashCode() + getMinimumDaysInFirstWeek() + this.iCutoverInstant.hashCode();
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
/*      */   public String toString() {
/*  440 */     StringBuffer stringBuffer = new StringBuffer(60);
/*  441 */     stringBuffer.append("GJChronology");
/*  442 */     stringBuffer.append('[');
/*  443 */     stringBuffer.append(getZone().getID());
/*      */     
/*  445 */     if (this.iCutoverMillis != DEFAULT_CUTOVER.getMillis()) {
/*  446 */       DateTimeFormatter dateTimeFormatter; stringBuffer.append(",cutover=");
/*      */       
/*  448 */       if (withUTC().dayOfYear().remainder(this.iCutoverMillis) == 0L) {
/*  449 */         dateTimeFormatter = ISODateTimeFormat.date();
/*      */       } else {
/*  451 */         dateTimeFormatter = ISODateTimeFormat.dateTime();
/*      */       } 
/*  453 */       dateTimeFormatter.withChronology(withUTC()).printTo(stringBuffer, this.iCutoverMillis);
/*      */     } 
/*      */     
/*  456 */     if (getMinimumDaysInFirstWeek() != 4) {
/*  457 */       stringBuffer.append(",mdfw=");
/*  458 */       stringBuffer.append(getMinimumDaysInFirstWeek());
/*      */     } 
/*  460 */     stringBuffer.append(']');
/*      */     
/*  462 */     return stringBuffer.toString();
/*      */   }
/*      */   
/*      */   protected void assemble(AssembledChronology.Fields paramFields) {
/*  466 */     Object[] arrayOfObject = (Object[])getParam();
/*      */     
/*  468 */     JulianChronology julianChronology = (JulianChronology)arrayOfObject[0];
/*  469 */     GregorianChronology gregorianChronology = (GregorianChronology)arrayOfObject[1];
/*  470 */     Instant instant = (Instant)arrayOfObject[2];
/*  471 */     this.iCutoverMillis = instant.getMillis();
/*      */     
/*  473 */     this.iJulianChronology = julianChronology;
/*  474 */     this.iGregorianChronology = gregorianChronology;
/*  475 */     this.iCutoverInstant = instant;
/*      */     
/*  477 */     if (getBase() != null) {
/*      */       return;
/*      */     }
/*      */     
/*  481 */     if (julianChronology.getMinimumDaysInFirstWeek() != gregorianChronology.getMinimumDaysInFirstWeek()) {
/*  482 */       throw new IllegalArgumentException();
/*      */     }
/*      */ 
/*      */     
/*  486 */     this.iGapDuration = this.iCutoverMillis - julianToGregorianByYear(this.iCutoverMillis);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  492 */     paramFields.copyFieldsFrom(gregorianChronology);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  498 */     if (gregorianChronology.millisOfDay().get(this.iCutoverMillis) == 0) {
/*      */ 
/*      */ 
/*      */       
/*  502 */       paramFields.millisOfSecond = (DateTimeField)new CutoverField(julianChronology.millisOfSecond(), paramFields.millisOfSecond, this.iCutoverMillis);
/*  503 */       paramFields.millisOfDay = (DateTimeField)new CutoverField(julianChronology.millisOfDay(), paramFields.millisOfDay, this.iCutoverMillis);
/*  504 */       paramFields.secondOfMinute = (DateTimeField)new CutoverField(julianChronology.secondOfMinute(), paramFields.secondOfMinute, this.iCutoverMillis);
/*  505 */       paramFields.secondOfDay = (DateTimeField)new CutoverField(julianChronology.secondOfDay(), paramFields.secondOfDay, this.iCutoverMillis);
/*  506 */       paramFields.minuteOfHour = (DateTimeField)new CutoverField(julianChronology.minuteOfHour(), paramFields.minuteOfHour, this.iCutoverMillis);
/*  507 */       paramFields.minuteOfDay = (DateTimeField)new CutoverField(julianChronology.minuteOfDay(), paramFields.minuteOfDay, this.iCutoverMillis);
/*  508 */       paramFields.hourOfDay = (DateTimeField)new CutoverField(julianChronology.hourOfDay(), paramFields.hourOfDay, this.iCutoverMillis);
/*  509 */       paramFields.hourOfHalfday = (DateTimeField)new CutoverField(julianChronology.hourOfHalfday(), paramFields.hourOfHalfday, this.iCutoverMillis);
/*  510 */       paramFields.clockhourOfDay = (DateTimeField)new CutoverField(julianChronology.clockhourOfDay(), paramFields.clockhourOfDay, this.iCutoverMillis);
/*  511 */       paramFields.clockhourOfHalfday = (DateTimeField)new CutoverField(julianChronology.clockhourOfHalfday(), paramFields.clockhourOfHalfday, this.iCutoverMillis);
/*      */       
/*  513 */       paramFields.halfdayOfDay = (DateTimeField)new CutoverField(julianChronology.halfdayOfDay(), paramFields.halfdayOfDay, this.iCutoverMillis);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  518 */     paramFields.era = (DateTimeField)new CutoverField(julianChronology.era(), paramFields.era, this.iCutoverMillis);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  525 */     paramFields.year = (DateTimeField)new ImpreciseCutoverField(julianChronology.year(), paramFields.year, this.iCutoverMillis);
/*      */     
/*  527 */     paramFields.years = paramFields.year.getDurationField();
/*  528 */     paramFields.yearOfEra = (DateTimeField)new ImpreciseCutoverField(julianChronology.yearOfEra(), paramFields.yearOfEra, paramFields.years, this.iCutoverMillis);
/*      */ 
/*      */     
/*  531 */     paramFields.centuryOfEra = (DateTimeField)new ImpreciseCutoverField(julianChronology.centuryOfEra(), paramFields.centuryOfEra, this.iCutoverMillis);
/*      */     
/*  533 */     paramFields.centuries = paramFields.centuryOfEra.getDurationField();
/*      */     
/*  535 */     paramFields.yearOfCentury = (DateTimeField)new ImpreciseCutoverField(julianChronology.yearOfCentury(), paramFields.yearOfCentury, paramFields.years, paramFields.centuries, this.iCutoverMillis);
/*      */ 
/*      */     
/*  538 */     paramFields.monthOfYear = (DateTimeField)new ImpreciseCutoverField(julianChronology.monthOfYear(), paramFields.monthOfYear, null, paramFields.years, this.iCutoverMillis);
/*      */     
/*  540 */     paramFields.months = paramFields.monthOfYear.getDurationField();
/*      */     
/*  542 */     paramFields.weekyear = (DateTimeField)new ImpreciseCutoverField(julianChronology.weekyear(), paramFields.weekyear, null, this.iCutoverMillis, true);
/*      */     
/*  544 */     paramFields.weekyears = paramFields.weekyear.getDurationField();
/*  545 */     paramFields.weekyearOfCentury = (DateTimeField)new ImpreciseCutoverField(julianChronology.weekyearOfCentury(), paramFields.weekyearOfCentury, paramFields.weekyears, paramFields.centuries, this.iCutoverMillis);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  555 */     long l = gregorianChronology.year().roundCeiling(this.iCutoverMillis);
/*  556 */     paramFields.dayOfYear = (DateTimeField)new CutoverField(julianChronology.dayOfYear(), paramFields.dayOfYear, paramFields.years, l, false);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  561 */     l = gregorianChronology.weekyear().roundCeiling(this.iCutoverMillis);
/*  562 */     paramFields.weekOfWeekyear = (DateTimeField)new CutoverField(julianChronology.weekOfWeekyear(), paramFields.weekOfWeekyear, paramFields.weekyears, l, true);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  569 */     CutoverField cutoverField = new CutoverField(julianChronology.dayOfMonth(), paramFields.dayOfMonth, this.iCutoverMillis);
/*      */     
/*  571 */     cutoverField.iRangeDurationField = paramFields.months;
/*  572 */     paramFields.dayOfMonth = (DateTimeField)cutoverField;
/*      */   }
/*      */ 
/*      */   
/*      */   long julianToGregorianByYear(long paramLong) {
/*  577 */     return convertByYear(paramLong, this.iJulianChronology, this.iGregorianChronology);
/*      */   }
/*      */   
/*      */   long gregorianToJulianByYear(long paramLong) {
/*  581 */     return convertByYear(paramLong, this.iGregorianChronology, this.iJulianChronology);
/*      */   }
/*      */   
/*      */   long julianToGregorianByWeekyear(long paramLong) {
/*  585 */     return convertByWeekyear(paramLong, this.iJulianChronology, this.iGregorianChronology);
/*      */   }
/*      */   
/*      */   long gregorianToJulianByWeekyear(long paramLong) {
/*  589 */     return convertByWeekyear(paramLong, this.iGregorianChronology, this.iJulianChronology);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private class CutoverField
/*      */     extends BaseDateTimeField
/*      */   {
/*      */     private static final long serialVersionUID = 3528501219481026402L;
/*      */ 
/*      */     
/*      */     final DateTimeField iJulianField;
/*      */ 
/*      */     
/*      */     final DateTimeField iGregorianField;
/*      */     
/*      */     final long iCutover;
/*      */     
/*      */     final boolean iConvertByWeekyear;
/*      */     
/*      */     protected DurationField iDurationField;
/*      */     
/*      */     protected DurationField iRangeDurationField;
/*      */ 
/*      */     
/*      */     CutoverField(DateTimeField param1DateTimeField1, DateTimeField param1DateTimeField2, long param1Long) {
/*  615 */       this(param1DateTimeField1, param1DateTimeField2, param1Long, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     CutoverField(DateTimeField param1DateTimeField1, DateTimeField param1DateTimeField2, long param1Long, boolean param1Boolean) {
/*  626 */       this(param1DateTimeField1, param1DateTimeField2, null, param1Long, param1Boolean);
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
/*      */     CutoverField(DateTimeField param1DateTimeField1, DateTimeField param1DateTimeField2, DurationField param1DurationField, long param1Long, boolean param1Boolean) {
/*  638 */       super(param1DateTimeField2.getType());
/*  639 */       this.iJulianField = param1DateTimeField1;
/*  640 */       this.iGregorianField = param1DateTimeField2;
/*  641 */       this.iCutover = param1Long;
/*  642 */       this.iConvertByWeekyear = param1Boolean;
/*      */ 
/*      */       
/*  645 */       this.iDurationField = param1DateTimeField2.getDurationField();
/*  646 */       if (param1DurationField == null) {
/*  647 */         param1DurationField = param1DateTimeField2.getRangeDurationField();
/*  648 */         if (param1DurationField == null) {
/*  649 */           param1DurationField = param1DateTimeField1.getRangeDurationField();
/*      */         }
/*      */       } 
/*  652 */       this.iRangeDurationField = param1DurationField;
/*      */     }
/*      */     
/*      */     public boolean isLenient() {
/*  656 */       return false;
/*      */     }
/*      */     
/*      */     public int get(long param1Long) {
/*  660 */       if (param1Long >= this.iCutover) {
/*  661 */         return this.iGregorianField.get(param1Long);
/*      */       }
/*  663 */       return this.iJulianField.get(param1Long);
/*      */     }
/*      */ 
/*      */     
/*      */     public String getAsText(long param1Long, Locale param1Locale) {
/*  668 */       if (param1Long >= this.iCutover) {
/*  669 */         return this.iGregorianField.getAsText(param1Long, param1Locale);
/*      */       }
/*  671 */       return this.iJulianField.getAsText(param1Long, param1Locale);
/*      */     }
/*      */ 
/*      */     
/*      */     public String getAsText(int param1Int, Locale param1Locale) {
/*  676 */       return this.iGregorianField.getAsText(param1Int, param1Locale);
/*      */     }
/*      */     
/*      */     public String getAsShortText(long param1Long, Locale param1Locale) {
/*  680 */       if (param1Long >= this.iCutover) {
/*  681 */         return this.iGregorianField.getAsShortText(param1Long, param1Locale);
/*      */       }
/*  683 */       return this.iJulianField.getAsShortText(param1Long, param1Locale);
/*      */     }
/*      */ 
/*      */     
/*      */     public String getAsShortText(int param1Int, Locale param1Locale) {
/*  688 */       return this.iGregorianField.getAsShortText(param1Int, param1Locale);
/*      */     }
/*      */     
/*      */     public long add(long param1Long, int param1Int) {
/*  692 */       return this.iGregorianField.add(param1Long, param1Int);
/*      */     }
/*      */     
/*      */     public long add(long param1Long1, long param1Long2) {
/*  696 */       return this.iGregorianField.add(param1Long1, param1Long2);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int[] add(ReadablePartial param1ReadablePartial, int param1Int1, int[] param1ArrayOfint, int param1Int2) {
/*  702 */       if (param1Int2 == 0) {
/*  703 */         return param1ArrayOfint;
/*      */       }
/*  705 */       if (DateTimeUtils.isContiguous(param1ReadablePartial)) {
/*  706 */         long l = 0L; byte b; int i;
/*  707 */         for (b = 0, i = param1ReadablePartial.size(); b < i; b++) {
/*  708 */           l = param1ReadablePartial.getFieldType(b).getField(GJChronology.this).set(l, param1ArrayOfint[b]);
/*      */         }
/*  710 */         l = add(l, param1Int2);
/*  711 */         return GJChronology.this.get(param1ReadablePartial, l);
/*      */       } 
/*  713 */       return super.add(param1ReadablePartial, param1Int1, param1ArrayOfint, param1Int2);
/*      */     }
/*      */ 
/*      */     
/*      */     public int getDifference(long param1Long1, long param1Long2) {
/*  718 */       return this.iGregorianField.getDifference(param1Long1, param1Long2);
/*      */     }
/*      */     
/*      */     public long getDifferenceAsLong(long param1Long1, long param1Long2) {
/*  722 */       return this.iGregorianField.getDifferenceAsLong(param1Long1, param1Long2);
/*      */     }
/*      */     
/*      */     public long set(long param1Long, int param1Int) {
/*  726 */       if (param1Long >= this.iCutover) {
/*  727 */         param1Long = this.iGregorianField.set(param1Long, param1Int);
/*  728 */         if (param1Long < this.iCutover) {
/*      */           
/*  730 */           if (param1Long + GJChronology.this.iGapDuration < this.iCutover) {
/*  731 */             param1Long = gregorianToJulian(param1Long);
/*      */           }
/*      */           
/*  734 */           if (get(param1Long) != param1Int) {
/*  735 */             throw new IllegalFieldValueException(this.iGregorianField.getType(), Integer.valueOf(param1Int), null, null);
/*      */           }
/*      */         } 
/*      */       } else {
/*      */         
/*  740 */         param1Long = this.iJulianField.set(param1Long, param1Int);
/*  741 */         if (param1Long >= this.iCutover) {
/*      */           
/*  743 */           if (param1Long - GJChronology.this.iGapDuration >= this.iCutover) {
/*  744 */             param1Long = julianToGregorian(param1Long);
/*      */           }
/*      */           
/*  747 */           if (get(param1Long) != param1Int) {
/*  748 */             throw new IllegalFieldValueException(this.iJulianField.getType(), Integer.valueOf(param1Int), null, null);
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/*  753 */       return param1Long;
/*      */     }
/*      */     
/*      */     public long set(long param1Long, String param1String, Locale param1Locale) {
/*  757 */       if (param1Long >= this.iCutover) {
/*  758 */         param1Long = this.iGregorianField.set(param1Long, param1String, param1Locale);
/*  759 */         if (param1Long < this.iCutover)
/*      */         {
/*  761 */           if (param1Long + GJChronology.this.iGapDuration < this.iCutover) {
/*  762 */             param1Long = gregorianToJulian(param1Long);
/*      */           }
/*      */         }
/*      */       } else {
/*      */         
/*  767 */         param1Long = this.iJulianField.set(param1Long, param1String, param1Locale);
/*  768 */         if (param1Long >= this.iCutover)
/*      */         {
/*  770 */           if (param1Long - GJChronology.this.iGapDuration >= this.iCutover) {
/*  771 */             param1Long = julianToGregorian(param1Long);
/*      */           }
/*      */         }
/*      */       } 
/*      */       
/*  776 */       return param1Long;
/*      */     }
/*      */     
/*      */     public DurationField getDurationField() {
/*  780 */       return this.iDurationField;
/*      */     }
/*      */     
/*      */     public DurationField getRangeDurationField() {
/*  784 */       return this.iRangeDurationField;
/*      */     }
/*      */     
/*      */     public boolean isLeap(long param1Long) {
/*  788 */       if (param1Long >= this.iCutover) {
/*  789 */         return this.iGregorianField.isLeap(param1Long);
/*      */       }
/*  791 */       return this.iJulianField.isLeap(param1Long);
/*      */     }
/*      */ 
/*      */     
/*      */     public int getLeapAmount(long param1Long) {
/*  796 */       if (param1Long >= this.iCutover) {
/*  797 */         return this.iGregorianField.getLeapAmount(param1Long);
/*      */       }
/*  799 */       return this.iJulianField.getLeapAmount(param1Long);
/*      */     }
/*      */ 
/*      */     
/*      */     public DurationField getLeapDurationField() {
/*  804 */       return this.iGregorianField.getLeapDurationField();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getMinimumValue() {
/*  811 */       return this.iJulianField.getMinimumValue();
/*      */     }
/*      */     
/*      */     public int getMinimumValue(ReadablePartial param1ReadablePartial) {
/*  815 */       return this.iJulianField.getMinimumValue(param1ReadablePartial);
/*      */     }
/*      */     
/*      */     public int getMinimumValue(ReadablePartial param1ReadablePartial, int[] param1ArrayOfint) {
/*  819 */       return this.iJulianField.getMinimumValue(param1ReadablePartial, param1ArrayOfint);
/*      */     }
/*      */     
/*      */     public int getMinimumValue(long param1Long) {
/*  823 */       if (param1Long < this.iCutover) {
/*  824 */         return this.iJulianField.getMinimumValue(param1Long);
/*      */       }
/*      */       
/*  827 */       int i = this.iGregorianField.getMinimumValue(param1Long);
/*      */ 
/*      */ 
/*      */       
/*  831 */       param1Long = this.iGregorianField.set(param1Long, i);
/*  832 */       if (param1Long < this.iCutover) {
/*  833 */         i = this.iGregorianField.get(this.iCutover);
/*      */       }
/*      */       
/*  836 */       return i;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int getMaximumValue() {
/*  842 */       return this.iGregorianField.getMaximumValue();
/*      */     }
/*      */     
/*      */     public int getMaximumValue(long param1Long) {
/*  846 */       if (param1Long >= this.iCutover) {
/*  847 */         return this.iGregorianField.getMaximumValue(param1Long);
/*      */       }
/*      */       
/*  850 */       int i = this.iJulianField.getMaximumValue(param1Long);
/*      */ 
/*      */ 
/*      */       
/*  854 */       param1Long = this.iJulianField.set(param1Long, i);
/*  855 */       if (param1Long >= this.iCutover) {
/*  856 */         i = this.iJulianField.get(this.iJulianField.add(this.iCutover, -1));
/*      */       }
/*      */       
/*  859 */       return i;
/*      */     }
/*      */     
/*      */     public int getMaximumValue(ReadablePartial param1ReadablePartial) {
/*  863 */       long l = GJChronology.getInstanceUTC().set(param1ReadablePartial, 0L);
/*  864 */       return getMaximumValue(l);
/*      */     }
/*      */     
/*      */     public int getMaximumValue(ReadablePartial param1ReadablePartial, int[] param1ArrayOfint) {
/*  868 */       GJChronology gJChronology = GJChronology.getInstanceUTC();
/*  869 */       long l = 0L; byte b; int i;
/*  870 */       for (b = 0, i = param1ReadablePartial.size(); b < i; b++) {
/*  871 */         DateTimeField dateTimeField = param1ReadablePartial.getFieldType(b).getField(gJChronology);
/*  872 */         if (param1ArrayOfint[b] <= dateTimeField.getMaximumValue(l)) {
/*  873 */           l = dateTimeField.set(l, param1ArrayOfint[b]);
/*      */         }
/*      */       } 
/*  876 */       return getMaximumValue(l);
/*      */     }
/*      */     
/*      */     public long roundFloor(long param1Long) {
/*  880 */       if (param1Long >= this.iCutover) {
/*  881 */         param1Long = this.iGregorianField.roundFloor(param1Long);
/*  882 */         if (param1Long < this.iCutover)
/*      */         {
/*  884 */           if (param1Long + GJChronology.this.iGapDuration < this.iCutover) {
/*  885 */             param1Long = gregorianToJulian(param1Long);
/*      */           }
/*      */         }
/*      */       } else {
/*  889 */         param1Long = this.iJulianField.roundFloor(param1Long);
/*      */       } 
/*  891 */       return param1Long;
/*      */     }
/*      */     
/*      */     public long roundCeiling(long param1Long) {
/*  895 */       if (param1Long >= this.iCutover) {
/*  896 */         param1Long = this.iGregorianField.roundCeiling(param1Long);
/*      */       } else {
/*  898 */         param1Long = this.iJulianField.roundCeiling(param1Long);
/*  899 */         if (param1Long >= this.iCutover)
/*      */         {
/*  901 */           if (param1Long - GJChronology.this.iGapDuration >= this.iCutover) {
/*  902 */             param1Long = julianToGregorian(param1Long);
/*      */           }
/*      */         }
/*      */       } 
/*  906 */       return param1Long;
/*      */     }
/*      */     
/*      */     public int getMaximumTextLength(Locale param1Locale) {
/*  910 */       return Math.max(this.iJulianField.getMaximumTextLength(param1Locale), this.iGregorianField.getMaximumTextLength(param1Locale));
/*      */     }
/*      */ 
/*      */     
/*      */     public int getMaximumShortTextLength(Locale param1Locale) {
/*  915 */       return Math.max(this.iJulianField.getMaximumShortTextLength(param1Locale), this.iGregorianField.getMaximumShortTextLength(param1Locale));
/*      */     }
/*      */ 
/*      */     
/*      */     protected long julianToGregorian(long param1Long) {
/*  920 */       if (this.iConvertByWeekyear) {
/*  921 */         return GJChronology.this.julianToGregorianByWeekyear(param1Long);
/*      */       }
/*  923 */       return GJChronology.this.julianToGregorianByYear(param1Long);
/*      */     }
/*      */ 
/*      */     
/*      */     protected long gregorianToJulian(long param1Long) {
/*  928 */       if (this.iConvertByWeekyear) {
/*  929 */         return GJChronology.this.gregorianToJulianByWeekyear(param1Long);
/*      */       }
/*  931 */       return GJChronology.this.gregorianToJulianByYear(param1Long);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class ImpreciseCutoverField
/*      */     extends CutoverField
/*      */   {
/*      */     private static final long serialVersionUID = 3410248757173576441L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     ImpreciseCutoverField(DateTimeField param1DateTimeField1, DateTimeField param1DateTimeField2, long param1Long) {
/*  951 */       this(param1DateTimeField1, param1DateTimeField2, (DurationField)null, param1Long, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     ImpreciseCutoverField(DateTimeField param1DateTimeField1, DateTimeField param1DateTimeField2, DurationField param1DurationField, long param1Long) {
/*  962 */       this(param1DateTimeField1, param1DateTimeField2, param1DurationField, param1Long, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     ImpreciseCutoverField(DateTimeField param1DateTimeField1, DateTimeField param1DateTimeField2, DurationField param1DurationField1, DurationField param1DurationField2, long param1Long) {
/*  973 */       this(param1DateTimeField1, param1DateTimeField2, param1DurationField1, param1Long, false);
/*  974 */       this.iRangeDurationField = param1DurationField2;
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
/*      */     ImpreciseCutoverField(DateTimeField param1DateTimeField1, DateTimeField param1DateTimeField2, DurationField param1DurationField, long param1Long, boolean param1Boolean) {
/*  986 */       super(param1DateTimeField1, param1DateTimeField2, param1Long, param1Boolean); GJChronology.LinkedDurationField linkedDurationField;
/*  987 */       if (param1DurationField == null) {
/*  988 */         linkedDurationField = new GJChronology.LinkedDurationField(this.iDurationField, this);
/*      */       }
/*  990 */       this.iDurationField = (DurationField)linkedDurationField;
/*      */     }
/*      */     
/*      */     public long add(long param1Long, int param1Int) {
/*  994 */       if (param1Long >= this.iCutover) {
/*  995 */         param1Long = this.iGregorianField.add(param1Long, param1Int);
/*  996 */         if (param1Long < this.iCutover)
/*      */         {
/*  998 */           if (param1Long + GJChronology.this.iGapDuration < this.iCutover) {
/*  999 */             if (this.iConvertByWeekyear) {
/* 1000 */               int i = GJChronology.this.iGregorianChronology.weekyear().get(param1Long);
/* 1001 */               if (i <= 0) {
/* 1002 */                 param1Long = GJChronology.this.iGregorianChronology.weekyear().add(param1Long, -1);
/*      */               }
/*      */             } else {
/* 1005 */               int i = GJChronology.this.iGregorianChronology.year().get(param1Long);
/* 1006 */               if (i <= 0) {
/* 1007 */                 param1Long = GJChronology.this.iGregorianChronology.year().add(param1Long, -1);
/*      */               }
/*      */             } 
/* 1010 */             param1Long = gregorianToJulian(param1Long);
/*      */           } 
/*      */         }
/*      */       } else {
/* 1014 */         param1Long = this.iJulianField.add(param1Long, param1Int);
/* 1015 */         if (param1Long >= this.iCutover)
/*      */         {
/* 1017 */           if (param1Long - GJChronology.this.iGapDuration >= this.iCutover)
/*      */           {
/* 1019 */             param1Long = julianToGregorian(param1Long);
/*      */           }
/*      */         }
/*      */       } 
/* 1023 */       return param1Long;
/*      */     }
/*      */     
/*      */     public long add(long param1Long1, long param1Long2) {
/* 1027 */       if (param1Long1 >= this.iCutover) {
/* 1028 */         param1Long1 = this.iGregorianField.add(param1Long1, param1Long2);
/* 1029 */         if (param1Long1 < this.iCutover)
/*      */         {
/* 1031 */           if (param1Long1 + GJChronology.this.iGapDuration < this.iCutover) {
/* 1032 */             if (this.iConvertByWeekyear) {
/* 1033 */               int i = GJChronology.this.iGregorianChronology.weekyear().get(param1Long1);
/* 1034 */               if (i <= 0) {
/* 1035 */                 param1Long1 = GJChronology.this.iGregorianChronology.weekyear().add(param1Long1, -1);
/*      */               }
/*      */             } else {
/* 1038 */               int i = GJChronology.this.iGregorianChronology.year().get(param1Long1);
/* 1039 */               if (i <= 0) {
/* 1040 */                 param1Long1 = GJChronology.this.iGregorianChronology.year().add(param1Long1, -1);
/*      */               }
/*      */             } 
/* 1043 */             param1Long1 = gregorianToJulian(param1Long1);
/*      */           } 
/*      */         }
/*      */       } else {
/* 1047 */         param1Long1 = this.iJulianField.add(param1Long1, param1Long2);
/* 1048 */         if (param1Long1 >= this.iCutover)
/*      */         {
/* 1050 */           if (param1Long1 - GJChronology.this.iGapDuration >= this.iCutover)
/*      */           {
/* 1052 */             param1Long1 = julianToGregorian(param1Long1);
/*      */           }
/*      */         }
/*      */       } 
/* 1056 */       return param1Long1;
/*      */     }
/*      */     
/*      */     public int getDifference(long param1Long1, long param1Long2) {
/* 1060 */       if (param1Long1 >= this.iCutover) {
/* 1061 */         if (param1Long2 >= this.iCutover) {
/* 1062 */           return this.iGregorianField.getDifference(param1Long1, param1Long2);
/*      */         }
/*      */ 
/*      */         
/* 1066 */         param1Long1 = gregorianToJulian(param1Long1);
/* 1067 */         return this.iJulianField.getDifference(param1Long1, param1Long2);
/*      */       } 
/* 1069 */       if (param1Long2 < this.iCutover) {
/* 1070 */         return this.iJulianField.getDifference(param1Long1, param1Long2);
/*      */       }
/*      */ 
/*      */       
/* 1074 */       param1Long1 = julianToGregorian(param1Long1);
/* 1075 */       return this.iGregorianField.getDifference(param1Long1, param1Long2);
/*      */     }
/*      */ 
/*      */     
/*      */     public long getDifferenceAsLong(long param1Long1, long param1Long2) {
/* 1080 */       if (param1Long1 >= this.iCutover) {
/* 1081 */         if (param1Long2 >= this.iCutover) {
/* 1082 */           return this.iGregorianField.getDifferenceAsLong(param1Long1, param1Long2);
/*      */         }
/*      */ 
/*      */         
/* 1086 */         param1Long1 = gregorianToJulian(param1Long1);
/* 1087 */         return this.iJulianField.getDifferenceAsLong(param1Long1, param1Long2);
/*      */       } 
/* 1089 */       if (param1Long2 < this.iCutover) {
/* 1090 */         return this.iJulianField.getDifferenceAsLong(param1Long1, param1Long2);
/*      */       }
/*      */ 
/*      */       
/* 1094 */       param1Long1 = julianToGregorian(param1Long1);
/* 1095 */       return this.iGregorianField.getDifferenceAsLong(param1Long1, param1Long2);
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
/*      */     public int getMinimumValue(long param1Long) {
/* 1110 */       if (param1Long >= this.iCutover) {
/* 1111 */         return this.iGregorianField.getMinimumValue(param1Long);
/*      */       }
/* 1113 */       return this.iJulianField.getMinimumValue(param1Long);
/*      */     }
/*      */ 
/*      */     
/*      */     public int getMaximumValue(long param1Long) {
/* 1118 */       if (param1Long >= this.iCutover) {
/* 1119 */         return this.iGregorianField.getMaximumValue(param1Long);
/*      */       }
/* 1121 */       return this.iJulianField.getMaximumValue(param1Long);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static class LinkedDurationField
/*      */     extends DecoratedDurationField
/*      */   {
/*      */     private static final long serialVersionUID = 4097975388007713084L;
/*      */     
/*      */     private final GJChronology.ImpreciseCutoverField iField;
/*      */ 
/*      */     
/*      */     LinkedDurationField(DurationField param1DurationField, GJChronology.ImpreciseCutoverField param1ImpreciseCutoverField) {
/* 1136 */       super(param1DurationField, param1DurationField.getType());
/* 1137 */       this.iField = param1ImpreciseCutoverField;
/*      */     }
/*      */     
/*      */     public long add(long param1Long, int param1Int) {
/* 1141 */       return this.iField.add(param1Long, param1Int);
/*      */     }
/*      */     
/*      */     public long add(long param1Long1, long param1Long2) {
/* 1145 */       return this.iField.add(param1Long1, param1Long2);
/*      */     }
/*      */     
/*      */     public int getDifference(long param1Long1, long param1Long2) {
/* 1149 */       return this.iField.getDifference(param1Long1, param1Long2);
/*      */     }
/*      */     
/*      */     public long getDifferenceAsLong(long param1Long1, long param1Long2) {
/* 1153 */       return this.iField.getDifferenceAsLong(param1Long1, param1Long2);
/*      */     }
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/chrono/GJChronology.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */