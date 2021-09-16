/*      */ package org.joda.time.format;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.Writer;
/*      */ import java.util.Locale;
/*      */ import org.joda.time.Chronology;
/*      */ import org.joda.time.DateTime;
/*      */ import org.joda.time.DateTimeUtils;
/*      */ import org.joda.time.DateTimeZone;
/*      */ import org.joda.time.LocalDate;
/*      */ import org.joda.time.LocalDateTime;
/*      */ import org.joda.time.LocalTime;
/*      */ import org.joda.time.MutableDateTime;
/*      */ import org.joda.time.ReadWritableInstant;
/*      */ import org.joda.time.ReadableInstant;
/*      */ import org.joda.time.ReadablePartial;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class DateTimeFormatter
/*      */ {
/*      */   private final InternalPrinter iPrinter;
/*      */   private final InternalParser iParser;
/*      */   private final Locale iLocale;
/*      */   private final boolean iOffsetParsed;
/*      */   private final Chronology iChrono;
/*      */   private final DateTimeZone iZone;
/*      */   private final Integer iPivotYear;
/*      */   private final int iDefaultYear;
/*      */   
/*      */   public DateTimeFormatter(DateTimePrinter paramDateTimePrinter, DateTimeParser paramDateTimeParser) {
/*  118 */     this(DateTimePrinterInternalPrinter.of(paramDateTimePrinter), DateTimeParserInternalParser.of(paramDateTimeParser));
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
/*      */   DateTimeFormatter(InternalPrinter paramInternalPrinter, InternalParser paramInternalParser) {
/*  131 */     this.iPrinter = paramInternalPrinter;
/*  132 */     this.iParser = paramInternalParser;
/*  133 */     this.iLocale = null;
/*  134 */     this.iOffsetParsed = false;
/*  135 */     this.iChrono = null;
/*  136 */     this.iZone = null;
/*  137 */     this.iPivotYear = null;
/*  138 */     this.iDefaultYear = 2000;
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
/*      */   private DateTimeFormatter(InternalPrinter paramInternalPrinter, InternalParser paramInternalParser, Locale paramLocale, boolean paramBoolean, Chronology paramChronology, DateTimeZone paramDateTimeZone, Integer paramInteger, int paramInt) {
/*  150 */     this.iPrinter = paramInternalPrinter;
/*  151 */     this.iParser = paramInternalParser;
/*  152 */     this.iLocale = paramLocale;
/*  153 */     this.iOffsetParsed = paramBoolean;
/*  154 */     this.iChrono = paramChronology;
/*  155 */     this.iZone = paramDateTimeZone;
/*  156 */     this.iPivotYear = paramInteger;
/*  157 */     this.iDefaultYear = paramInt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isPrinter() {
/*  167 */     return (this.iPrinter != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DateTimePrinter getPrinter() {
/*  176 */     return InternalPrinterDateTimePrinter.of(this.iPrinter);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   InternalPrinter getPrinter0() {
/*  185 */     return this.iPrinter;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isParser() {
/*  194 */     return (this.iParser != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DateTimeParser getParser() {
/*  203 */     return InternalParserDateTimeParser.of(this.iParser);
/*      */   }
/*      */   
/*      */   InternalParser getParser0() {
/*  207 */     return this.iParser;
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
/*      */   public DateTimeFormatter withLocale(Locale paramLocale) {
/*  223 */     if (paramLocale == getLocale() || (paramLocale != null && paramLocale.equals(getLocale()))) {
/*  224 */       return this;
/*      */     }
/*  226 */     return new DateTimeFormatter(this.iPrinter, this.iParser, paramLocale, this.iOffsetParsed, this.iChrono, this.iZone, this.iPivotYear, this.iDefaultYear);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Locale getLocale() {
/*  237 */     return this.iLocale;
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
/*      */   public DateTimeFormatter withOffsetParsed() {
/*  256 */     if (this.iOffsetParsed == true) {
/*  257 */       return this;
/*      */     }
/*  259 */     return new DateTimeFormatter(this.iPrinter, this.iParser, this.iLocale, true, this.iChrono, null, this.iPivotYear, this.iDefaultYear);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isOffsetParsed() {
/*  270 */     return this.iOffsetParsed;
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
/*      */   public DateTimeFormatter withChronology(Chronology paramChronology) {
/*  291 */     if (this.iChrono == paramChronology) {
/*  292 */       return this;
/*      */     }
/*  294 */     return new DateTimeFormatter(this.iPrinter, this.iParser, this.iLocale, this.iOffsetParsed, paramChronology, this.iZone, this.iPivotYear, this.iDefaultYear);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Chronology getChronology() {
/*  304 */     return this.iChrono;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public Chronology getChronolgy() {
/*  315 */     return this.iChrono;
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
/*      */   public DateTimeFormatter withZoneUTC() {
/*  335 */     return withZone(DateTimeZone.UTC);
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
/*      */   public DateTimeFormatter withZone(DateTimeZone paramDateTimeZone) {
/*  355 */     if (this.iZone == paramDateTimeZone) {
/*  356 */       return this;
/*      */     }
/*  358 */     return new DateTimeFormatter(this.iPrinter, this.iParser, this.iLocale, false, this.iChrono, paramDateTimeZone, this.iPivotYear, this.iDefaultYear);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DateTimeZone getZone() {
/*  368 */     return this.iZone;
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DateTimeFormatter withPivotYear(Integer paramInteger) {
/*  402 */     if (this.iPivotYear == paramInteger || (this.iPivotYear != null && this.iPivotYear.equals(paramInteger))) {
/*  403 */       return this;
/*      */     }
/*  405 */     return new DateTimeFormatter(this.iPrinter, this.iParser, this.iLocale, this.iOffsetParsed, this.iChrono, this.iZone, paramInteger, this.iDefaultYear);
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DateTimeFormatter withPivotYear(int paramInt) {
/*  439 */     return withPivotYear(Integer.valueOf(paramInt));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getPivotYear() {
/*  449 */     return this.iPivotYear;
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
/*      */   public DateTimeFormatter withDefaultYear(int paramInt) {
/*  472 */     return new DateTimeFormatter(this.iPrinter, this.iParser, this.iLocale, this.iOffsetParsed, this.iChrono, this.iZone, this.iPivotYear, paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getDefaultYear() {
/*  483 */     return this.iDefaultYear;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void printTo(StringBuffer paramStringBuffer, ReadableInstant paramReadableInstant) {
/*      */     try {
/*  495 */       printTo(paramStringBuffer, paramReadableInstant);
/*  496 */     } catch (IOException iOException) {}
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
/*      */   public void printTo(StringBuilder paramStringBuilder, ReadableInstant paramReadableInstant) {
/*      */     try {
/*  509 */       printTo(paramStringBuilder, paramReadableInstant);
/*  510 */     } catch (IOException iOException) {}
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
/*      */   public void printTo(Writer paramWriter, ReadableInstant paramReadableInstant) throws IOException {
/*  522 */     printTo(paramWriter, paramReadableInstant);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void printTo(Appendable paramAppendable, ReadableInstant paramReadableInstant) throws IOException {
/*  533 */     long l = DateTimeUtils.getInstantMillis(paramReadableInstant);
/*  534 */     Chronology chronology = DateTimeUtils.getInstantChronology(paramReadableInstant);
/*  535 */     printTo(paramAppendable, l, chronology);
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
/*      */   public void printTo(StringBuffer paramStringBuffer, long paramLong) {
/*      */     try {
/*  548 */       printTo(paramStringBuffer, paramLong);
/*  549 */     } catch (IOException iOException) {}
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
/*      */   public void printTo(StringBuilder paramStringBuilder, long paramLong) {
/*      */     try {
/*  563 */       printTo(paramStringBuilder, paramLong);
/*  564 */     } catch (IOException iOException) {}
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
/*      */   public void printTo(Writer paramWriter, long paramLong) throws IOException {
/*  577 */     printTo(paramWriter, paramLong);
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
/*      */   public void printTo(Appendable paramAppendable, long paramLong) throws IOException {
/*  589 */     printTo(paramAppendable, paramLong, null);
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
/*      */   public void printTo(StringBuffer paramStringBuffer, ReadablePartial paramReadablePartial) {
/*      */     try {
/*  604 */       printTo(paramStringBuffer, paramReadablePartial);
/*  605 */     } catch (IOException iOException) {}
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
/*      */   public void printTo(StringBuilder paramStringBuilder, ReadablePartial paramReadablePartial) {
/*      */     try {
/*  621 */       printTo(paramStringBuilder, paramReadablePartial);
/*  622 */     } catch (IOException iOException) {}
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
/*      */   public void printTo(Writer paramWriter, ReadablePartial paramReadablePartial) throws IOException {
/*  637 */     printTo(paramWriter, paramReadablePartial);
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
/*      */   public void printTo(Appendable paramAppendable, ReadablePartial paramReadablePartial) throws IOException {
/*  651 */     InternalPrinter internalPrinter = requirePrinter();
/*  652 */     if (paramReadablePartial == null) {
/*  653 */       throw new IllegalArgumentException("The partial must not be null");
/*      */     }
/*  655 */     internalPrinter.printTo(paramAppendable, paramReadablePartial, this.iLocale);
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
/*      */   public String print(ReadableInstant paramReadableInstant) {
/*  669 */     StringBuilder stringBuilder = new StringBuilder(requirePrinter().estimatePrintedLength());
/*      */     try {
/*  671 */       printTo(stringBuilder, paramReadableInstant);
/*  672 */     } catch (IOException iOException) {}
/*      */ 
/*      */     
/*  675 */     return stringBuilder.toString();
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
/*      */   public String print(long paramLong) {
/*  688 */     StringBuilder stringBuilder = new StringBuilder(requirePrinter().estimatePrintedLength());
/*      */     try {
/*  690 */       printTo(stringBuilder, paramLong);
/*  691 */     } catch (IOException iOException) {}
/*      */ 
/*      */     
/*  694 */     return stringBuilder.toString();
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
/*      */   public String print(ReadablePartial paramReadablePartial) {
/*  707 */     StringBuilder stringBuilder = new StringBuilder(requirePrinter().estimatePrintedLength());
/*      */     try {
/*  709 */       printTo(stringBuilder, paramReadablePartial);
/*  710 */     } catch (IOException iOException) {}
/*      */ 
/*      */     
/*  713 */     return stringBuilder.toString();
/*      */   }
/*      */   
/*      */   private void printTo(Appendable paramAppendable, long paramLong, Chronology paramChronology) throws IOException {
/*  717 */     InternalPrinter internalPrinter = requirePrinter();
/*  718 */     paramChronology = selectChronology(paramChronology);
/*      */ 
/*      */     
/*  721 */     DateTimeZone dateTimeZone = paramChronology.getZone();
/*  722 */     int i = dateTimeZone.getOffset(paramLong);
/*  723 */     long l = paramLong + i;
/*  724 */     if ((paramLong ^ l) < 0L && (paramLong ^ i) >= 0L) {
/*      */       
/*  726 */       dateTimeZone = DateTimeZone.UTC;
/*  727 */       i = 0;
/*  728 */       l = paramLong;
/*      */     } 
/*  730 */     internalPrinter.printTo(paramAppendable, l, paramChronology.withUTC(), i, dateTimeZone, this.iLocale);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private InternalPrinter requirePrinter() {
/*  739 */     InternalPrinter internalPrinter = this.iPrinter;
/*  740 */     if (internalPrinter == null) {
/*  741 */       throw new UnsupportedOperationException("Printing not supported");
/*      */     }
/*  743 */     return internalPrinter;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int parseInto(ReadWritableInstant paramReadWritableInstant, String paramString, int paramInt) {
/*  781 */     InternalParser internalParser = requireParser();
/*  782 */     if (paramReadWritableInstant == null) {
/*  783 */       throw new IllegalArgumentException("Instant must not be null");
/*      */     }
/*      */     
/*  786 */     long l1 = paramReadWritableInstant.getMillis();
/*  787 */     Chronology chronology = paramReadWritableInstant.getChronology();
/*  788 */     int i = DateTimeUtils.getChronology(chronology).year().get(l1);
/*  789 */     long l2 = l1 + chronology.getZone().getOffset(l1);
/*  790 */     chronology = selectChronology(chronology);
/*      */     
/*  792 */     DateTimeParserBucket dateTimeParserBucket = new DateTimeParserBucket(l2, chronology, this.iLocale, this.iPivotYear, i);
/*      */     
/*  794 */     int j = internalParser.parseInto(dateTimeParserBucket, paramString, paramInt);
/*  795 */     paramReadWritableInstant.setMillis(dateTimeParserBucket.computeMillis(false, paramString));
/*  796 */     if (this.iOffsetParsed && dateTimeParserBucket.getOffsetInteger() != null) {
/*  797 */       int k = dateTimeParserBucket.getOffsetInteger().intValue();
/*  798 */       DateTimeZone dateTimeZone = DateTimeZone.forOffsetMillis(k);
/*  799 */       chronology = chronology.withZone(dateTimeZone);
/*  800 */     } else if (dateTimeParserBucket.getZone() != null) {
/*  801 */       chronology = chronology.withZone(dateTimeParserBucket.getZone());
/*      */     } 
/*  803 */     paramReadWritableInstant.setChronology(chronology);
/*  804 */     if (this.iZone != null) {
/*  805 */       paramReadWritableInstant.setZone(this.iZone);
/*      */     }
/*  807 */     return j;
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
/*      */   public long parseMillis(String paramString) {
/*  823 */     InternalParser internalParser = requireParser();
/*  824 */     Chronology chronology = selectChronology(this.iChrono);
/*  825 */     DateTimeParserBucket dateTimeParserBucket = new DateTimeParserBucket(0L, chronology, this.iLocale, this.iPivotYear, this.iDefaultYear);
/*  826 */     return dateTimeParserBucket.doParseMillis(internalParser, paramString);
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
/*      */   public LocalDate parseLocalDate(String paramString) {
/*  844 */     return parseLocalDateTime(paramString).toLocalDate();
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
/*      */   public LocalTime parseLocalTime(String paramString) {
/*  862 */     return parseLocalDateTime(paramString).toLocalTime();
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
/*      */   public LocalDateTime parseLocalDateTime(String paramString) {
/*  880 */     InternalParser internalParser = requireParser();
/*      */     
/*  882 */     Chronology chronology = selectChronology(null).withUTC();
/*  883 */     DateTimeParserBucket dateTimeParserBucket = new DateTimeParserBucket(0L, chronology, this.iLocale, this.iPivotYear, this.iDefaultYear);
/*  884 */     int i = internalParser.parseInto(dateTimeParserBucket, paramString, 0);
/*  885 */     if (i >= 0) {
/*  886 */       if (i >= paramString.length()) {
/*  887 */         long l = dateTimeParserBucket.computeMillis(true, paramString);
/*  888 */         if (dateTimeParserBucket.getOffsetInteger() != null) {
/*  889 */           int j = dateTimeParserBucket.getOffsetInteger().intValue();
/*  890 */           DateTimeZone dateTimeZone = DateTimeZone.forOffsetMillis(j);
/*  891 */           chronology = chronology.withZone(dateTimeZone);
/*  892 */         } else if (dateTimeParserBucket.getZone() != null) {
/*  893 */           chronology = chronology.withZone(dateTimeParserBucket.getZone());
/*      */         } 
/*  895 */         return new LocalDateTime(l, chronology);
/*      */       } 
/*      */     } else {
/*  898 */       i ^= 0xFFFFFFFF;
/*      */     } 
/*  900 */     throw new IllegalArgumentException(FormatUtils.createErrorMessage(paramString, i));
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
/*      */   public DateTime parseDateTime(String paramString) {
/*  921 */     InternalParser internalParser = requireParser();
/*      */     
/*  923 */     Chronology chronology = selectChronology(null);
/*  924 */     DateTimeParserBucket dateTimeParserBucket = new DateTimeParserBucket(0L, chronology, this.iLocale, this.iPivotYear, this.iDefaultYear);
/*  925 */     int i = internalParser.parseInto(dateTimeParserBucket, paramString, 0);
/*  926 */     if (i >= 0) {
/*  927 */       if (i >= paramString.length()) {
/*  928 */         long l = dateTimeParserBucket.computeMillis(true, paramString);
/*  929 */         if (this.iOffsetParsed && dateTimeParserBucket.getOffsetInteger() != null) {
/*  930 */           int j = dateTimeParserBucket.getOffsetInteger().intValue();
/*  931 */           DateTimeZone dateTimeZone = DateTimeZone.forOffsetMillis(j);
/*  932 */           chronology = chronology.withZone(dateTimeZone);
/*  933 */         } else if (dateTimeParserBucket.getZone() != null) {
/*  934 */           chronology = chronology.withZone(dateTimeParserBucket.getZone());
/*      */         } 
/*  936 */         DateTime dateTime = new DateTime(l, chronology);
/*  937 */         if (this.iZone != null) {
/*  938 */           dateTime = dateTime.withZone(this.iZone);
/*      */         }
/*  940 */         return dateTime;
/*      */       } 
/*      */     } else {
/*  943 */       i ^= 0xFFFFFFFF;
/*      */     } 
/*  945 */     throw new IllegalArgumentException(FormatUtils.createErrorMessage(paramString, i));
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
/*      */   public MutableDateTime parseMutableDateTime(String paramString) {
/*  966 */     InternalParser internalParser = requireParser();
/*      */     
/*  968 */     Chronology chronology = selectChronology(null);
/*  969 */     DateTimeParserBucket dateTimeParserBucket = new DateTimeParserBucket(0L, chronology, this.iLocale, this.iPivotYear, this.iDefaultYear);
/*  970 */     int i = internalParser.parseInto(dateTimeParserBucket, paramString, 0);
/*  971 */     if (i >= 0) {
/*  972 */       if (i >= paramString.length()) {
/*  973 */         long l = dateTimeParserBucket.computeMillis(true, paramString);
/*  974 */         if (this.iOffsetParsed && dateTimeParserBucket.getOffsetInteger() != null) {
/*  975 */           int j = dateTimeParserBucket.getOffsetInteger().intValue();
/*  976 */           DateTimeZone dateTimeZone = DateTimeZone.forOffsetMillis(j);
/*  977 */           chronology = chronology.withZone(dateTimeZone);
/*  978 */         } else if (dateTimeParserBucket.getZone() != null) {
/*  979 */           chronology = chronology.withZone(dateTimeParserBucket.getZone());
/*      */         } 
/*  981 */         MutableDateTime mutableDateTime = new MutableDateTime(l, chronology);
/*  982 */         if (this.iZone != null) {
/*  983 */           mutableDateTime.setZone(this.iZone);
/*      */         }
/*  985 */         return mutableDateTime;
/*      */       } 
/*      */     } else {
/*  988 */       i ^= 0xFFFFFFFF;
/*      */     } 
/*  990 */     throw new IllegalArgumentException(FormatUtils.createErrorMessage(paramString, i));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private InternalParser requireParser() {
/*  999 */     InternalParser internalParser = this.iParser;
/* 1000 */     if (internalParser == null) {
/* 1001 */       throw new UnsupportedOperationException("Parsing not supported");
/*      */     }
/* 1003 */     return internalParser;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Chronology selectChronology(Chronology paramChronology) {
/* 1014 */     paramChronology = DateTimeUtils.getChronology(paramChronology);
/* 1015 */     if (this.iChrono != null) {
/* 1016 */       paramChronology = this.iChrono;
/*      */     }
/* 1018 */     if (this.iZone != null) {
/* 1019 */       paramChronology = paramChronology.withZone(this.iZone);
/*      */     }
/* 1021 */     return paramChronology;
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/format/DateTimeFormatter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */