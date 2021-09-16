/*      */ package org.joda.time.format;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.concurrent.ConcurrentHashMap;
/*      */ import org.joda.time.Chronology;
/*      */ import org.joda.time.DateTimeField;
/*      */ import org.joda.time.DateTimeFieldType;
/*      */ import org.joda.time.DateTimeUtils;
/*      */ import org.joda.time.DateTimeZone;
/*      */ import org.joda.time.MutableDateTime;
/*      */ import org.joda.time.ReadablePartial;
/*      */ import org.joda.time.field.MillisDurationField;
/*      */ import org.joda.time.field.PreciseDateTimeField;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class DateTimeFormatterBuilder
/*      */ {
/*   83 */   private ArrayList<Object> iElementPairs = new ArrayList();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Object iFormatter;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DateTimeFormatter toFormatter() {
/*  103 */     Object object = getFormatter();
/*  104 */     InternalPrinter internalPrinter = null;
/*  105 */     if (isPrinter(object)) {
/*  106 */       internalPrinter = (InternalPrinter)object;
/*      */     }
/*  108 */     InternalParser internalParser = null;
/*  109 */     if (isParser(object)) {
/*  110 */       internalParser = (InternalParser)object;
/*      */     }
/*  112 */     if (internalPrinter != null || internalParser != null) {
/*  113 */       return new DateTimeFormatter(internalPrinter, internalParser);
/*      */     }
/*  115 */     throw new UnsupportedOperationException("Both printing and parsing not supported");
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
/*      */   public DateTimePrinter toPrinter() {
/*  131 */     Object object = getFormatter();
/*  132 */     if (isPrinter(object)) {
/*  133 */       InternalPrinter internalPrinter = (InternalPrinter)object;
/*  134 */       return InternalPrinterDateTimePrinter.of(internalPrinter);
/*      */     } 
/*  136 */     throw new UnsupportedOperationException("Printing is not supported");
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
/*      */   public DateTimeParser toParser() {
/*  152 */     Object object = getFormatter();
/*  153 */     if (isParser(object)) {
/*  154 */       InternalParser internalParser = (InternalParser)object;
/*  155 */       return InternalParserDateTimeParser.of(internalParser);
/*      */     } 
/*  157 */     throw new UnsupportedOperationException("Parsing is not supported");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canBuildFormatter() {
/*  168 */     return isFormatter(getFormatter());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canBuildPrinter() {
/*  178 */     return isPrinter(getFormatter());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canBuildParser() {
/*  188 */     return isParser(getFormatter());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clear() {
/*  197 */     this.iFormatter = null;
/*  198 */     this.iElementPairs.clear();
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
/*      */   public DateTimeFormatterBuilder append(DateTimeFormatter paramDateTimeFormatter) {
/*  217 */     if (paramDateTimeFormatter == null) {
/*  218 */       throw new IllegalArgumentException("No formatter supplied");
/*      */     }
/*  220 */     return append0(paramDateTimeFormatter.getPrinter0(), paramDateTimeFormatter.getParser0());
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
/*      */   public DateTimeFormatterBuilder append(DateTimePrinter paramDateTimePrinter) {
/*  238 */     checkPrinter(paramDateTimePrinter);
/*  239 */     return append0(DateTimePrinterInternalPrinter.of(paramDateTimePrinter), null);
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
/*      */   public DateTimeFormatterBuilder append(DateTimeParser paramDateTimeParser) {
/*  257 */     checkParser(paramDateTimeParser);
/*  258 */     return append0(null, DateTimeParserInternalParser.of(paramDateTimeParser));
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
/*      */   public DateTimeFormatterBuilder append(DateTimePrinter paramDateTimePrinter, DateTimeParser paramDateTimeParser) {
/*  276 */     checkPrinter(paramDateTimePrinter);
/*  277 */     checkParser(paramDateTimeParser);
/*  278 */     return append0(DateTimePrinterInternalPrinter.of(paramDateTimePrinter), DateTimeParserInternalParser.of(paramDateTimeParser));
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
/*      */   public DateTimeFormatterBuilder append(DateTimePrinter paramDateTimePrinter, DateTimeParser[] paramArrayOfDateTimeParser) {
/*  305 */     if (paramDateTimePrinter != null) {
/*  306 */       checkPrinter(paramDateTimePrinter);
/*      */     }
/*  308 */     if (paramArrayOfDateTimeParser == null) {
/*  309 */       throw new IllegalArgumentException("No parsers supplied");
/*      */     }
/*  311 */     int i = paramArrayOfDateTimeParser.length;
/*  312 */     if (i == 1) {
/*  313 */       if (paramArrayOfDateTimeParser[0] == null) {
/*  314 */         throw new IllegalArgumentException("No parser supplied");
/*      */       }
/*  316 */       return append0(DateTimePrinterInternalPrinter.of(paramDateTimePrinter), DateTimeParserInternalParser.of(paramArrayOfDateTimeParser[0]));
/*      */     } 
/*      */     
/*  319 */     InternalParser[] arrayOfInternalParser = new InternalParser[i];
/*      */     byte b;
/*  321 */     for (b = 0; b < i - 1; b++) {
/*  322 */       arrayOfInternalParser[b] = DateTimeParserInternalParser.of(paramArrayOfDateTimeParser[b]); if (DateTimeParserInternalParser.of(paramArrayOfDateTimeParser[b]) == null) {
/*  323 */         throw new IllegalArgumentException("Incomplete parser array");
/*      */       }
/*      */     } 
/*  326 */     arrayOfInternalParser[b] = DateTimeParserInternalParser.of(paramArrayOfDateTimeParser[b]);
/*      */     
/*  328 */     return append0(DateTimePrinterInternalPrinter.of(paramDateTimePrinter), new MatchingParser(arrayOfInternalParser));
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
/*      */   public DateTimeFormatterBuilder appendOptional(DateTimeParser paramDateTimeParser) {
/*  345 */     checkParser(paramDateTimeParser);
/*  346 */     InternalParser[] arrayOfInternalParser = { DateTimeParserInternalParser.of(paramDateTimeParser), null };
/*  347 */     return append0(null, new MatchingParser(arrayOfInternalParser));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkParser(DateTimeParser paramDateTimeParser) {
/*  357 */     if (paramDateTimeParser == null) {
/*  358 */       throw new IllegalArgumentException("No parser supplied");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkPrinter(DateTimePrinter paramDateTimePrinter) {
/*  368 */     if (paramDateTimePrinter == null) {
/*  369 */       throw new IllegalArgumentException("No printer supplied");
/*      */     }
/*      */   }
/*      */   
/*      */   private DateTimeFormatterBuilder append0(Object paramObject) {
/*  374 */     this.iFormatter = null;
/*      */     
/*  376 */     this.iElementPairs.add(paramObject);
/*  377 */     this.iElementPairs.add(paramObject);
/*  378 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   private DateTimeFormatterBuilder append0(InternalPrinter paramInternalPrinter, InternalParser paramInternalParser) {
/*  383 */     this.iFormatter = null;
/*  384 */     this.iElementPairs.add(paramInternalPrinter);
/*  385 */     this.iElementPairs.add(paramInternalParser);
/*  386 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DateTimeFormatterBuilder appendLiteral(char paramChar) {
/*  397 */     return append0(new CharacterLiteral(paramChar));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DateTimeFormatterBuilder appendLiteral(String paramString) {
/*  408 */     if (paramString == null) {
/*  409 */       throw new IllegalArgumentException("Literal must not be null");
/*      */     }
/*  411 */     switch (paramString.length()) {
/*      */       case 0:
/*  413 */         return this;
/*      */       case 1:
/*  415 */         return append0(new CharacterLiteral(paramString.charAt(0)));
/*      */     } 
/*  417 */     return append0(new StringLiteral(paramString));
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
/*      */   public DateTimeFormatterBuilder appendDecimal(DateTimeFieldType paramDateTimeFieldType, int paramInt1, int paramInt2) {
/*  434 */     if (paramDateTimeFieldType == null) {
/*  435 */       throw new IllegalArgumentException("Field type must not be null");
/*      */     }
/*  437 */     if (paramInt2 < paramInt1) {
/*  438 */       paramInt2 = paramInt1;
/*      */     }
/*  440 */     if (paramInt1 < 0 || paramInt2 <= 0) {
/*  441 */       throw new IllegalArgumentException();
/*      */     }
/*  443 */     if (paramInt1 <= 1) {
/*  444 */       return append0(new UnpaddedNumber(paramDateTimeFieldType, paramInt2, false));
/*      */     }
/*  446 */     return append0(new PaddedNumber(paramDateTimeFieldType, paramInt2, false, paramInt1));
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
/*      */   public DateTimeFormatterBuilder appendFixedDecimal(DateTimeFieldType paramDateTimeFieldType, int paramInt) {
/*  464 */     if (paramDateTimeFieldType == null) {
/*  465 */       throw new IllegalArgumentException("Field type must not be null");
/*      */     }
/*  467 */     if (paramInt <= 0) {
/*  468 */       throw new IllegalArgumentException("Illegal number of digits: " + paramInt);
/*      */     }
/*  470 */     return append0(new FixedNumber(paramDateTimeFieldType, paramInt, false));
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
/*      */   public DateTimeFormatterBuilder appendSignedDecimal(DateTimeFieldType paramDateTimeFieldType, int paramInt1, int paramInt2) {
/*  486 */     if (paramDateTimeFieldType == null) {
/*  487 */       throw new IllegalArgumentException("Field type must not be null");
/*      */     }
/*  489 */     if (paramInt2 < paramInt1) {
/*  490 */       paramInt2 = paramInt1;
/*      */     }
/*  492 */     if (paramInt1 < 0 || paramInt2 <= 0) {
/*  493 */       throw new IllegalArgumentException();
/*      */     }
/*  495 */     if (paramInt1 <= 1) {
/*  496 */       return append0(new UnpaddedNumber(paramDateTimeFieldType, paramInt2, true));
/*      */     }
/*  498 */     return append0(new PaddedNumber(paramDateTimeFieldType, paramInt2, true, paramInt1));
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
/*      */   public DateTimeFormatterBuilder appendFixedSignedDecimal(DateTimeFieldType paramDateTimeFieldType, int paramInt) {
/*  516 */     if (paramDateTimeFieldType == null) {
/*  517 */       throw new IllegalArgumentException("Field type must not be null");
/*      */     }
/*  519 */     if (paramInt <= 0) {
/*  520 */       throw new IllegalArgumentException("Illegal number of digits: " + paramInt);
/*      */     }
/*  522 */     return append0(new FixedNumber(paramDateTimeFieldType, paramInt, true));
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
/*      */   public DateTimeFormatterBuilder appendText(DateTimeFieldType paramDateTimeFieldType) {
/*  534 */     if (paramDateTimeFieldType == null) {
/*  535 */       throw new IllegalArgumentException("Field type must not be null");
/*      */     }
/*  537 */     return append0(new TextField(paramDateTimeFieldType, false));
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
/*      */   public DateTimeFormatterBuilder appendShortText(DateTimeFieldType paramDateTimeFieldType) {
/*  549 */     if (paramDateTimeFieldType == null) {
/*  550 */       throw new IllegalArgumentException("Field type must not be null");
/*      */     }
/*  552 */     return append0(new TextField(paramDateTimeFieldType, true));
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
/*      */   public DateTimeFormatterBuilder appendFraction(DateTimeFieldType paramDateTimeFieldType, int paramInt1, int paramInt2) {
/*  570 */     if (paramDateTimeFieldType == null) {
/*  571 */       throw new IllegalArgumentException("Field type must not be null");
/*      */     }
/*  573 */     if (paramInt2 < paramInt1) {
/*  574 */       paramInt2 = paramInt1;
/*      */     }
/*  576 */     if (paramInt1 < 0 || paramInt2 <= 0) {
/*  577 */       throw new IllegalArgumentException();
/*      */     }
/*  579 */     return append0(new Fraction(paramDateTimeFieldType, paramInt1, paramInt2));
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
/*      */   public DateTimeFormatterBuilder appendFractionOfSecond(int paramInt1, int paramInt2) {
/*  597 */     return appendFraction(DateTimeFieldType.secondOfDay(), paramInt1, paramInt2);
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
/*      */   public DateTimeFormatterBuilder appendFractionOfMinute(int paramInt1, int paramInt2) {
/*  614 */     return appendFraction(DateTimeFieldType.minuteOfDay(), paramInt1, paramInt2);
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
/*      */   public DateTimeFormatterBuilder appendFractionOfHour(int paramInt1, int paramInt2) {
/*  631 */     return appendFraction(DateTimeFieldType.hourOfDay(), paramInt1, paramInt2);
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
/*      */   public DateTimeFormatterBuilder appendFractionOfDay(int paramInt1, int paramInt2) {
/*  648 */     return appendFraction(DateTimeFieldType.dayOfYear(), paramInt1, paramInt2);
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
/*      */   public DateTimeFormatterBuilder appendMillisOfSecond(int paramInt) {
/*  665 */     return appendDecimal(DateTimeFieldType.millisOfSecond(), paramInt, 3);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DateTimeFormatterBuilder appendMillisOfDay(int paramInt) {
/*  675 */     return appendDecimal(DateTimeFieldType.millisOfDay(), paramInt, 8);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DateTimeFormatterBuilder appendSecondOfMinute(int paramInt) {
/*  685 */     return appendDecimal(DateTimeFieldType.secondOfMinute(), paramInt, 2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DateTimeFormatterBuilder appendSecondOfDay(int paramInt) {
/*  695 */     return appendDecimal(DateTimeFieldType.secondOfDay(), paramInt, 5);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DateTimeFormatterBuilder appendMinuteOfHour(int paramInt) {
/*  705 */     return appendDecimal(DateTimeFieldType.minuteOfHour(), paramInt, 2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DateTimeFormatterBuilder appendMinuteOfDay(int paramInt) {
/*  715 */     return appendDecimal(DateTimeFieldType.minuteOfDay(), paramInt, 4);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DateTimeFormatterBuilder appendHourOfDay(int paramInt) {
/*  725 */     return appendDecimal(DateTimeFieldType.hourOfDay(), paramInt, 2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DateTimeFormatterBuilder appendClockhourOfDay(int paramInt) {
/*  735 */     return appendDecimal(DateTimeFieldType.clockhourOfDay(), paramInt, 2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DateTimeFormatterBuilder appendHourOfHalfday(int paramInt) {
/*  745 */     return appendDecimal(DateTimeFieldType.hourOfHalfday(), paramInt, 2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DateTimeFormatterBuilder appendClockhourOfHalfday(int paramInt) {
/*  755 */     return appendDecimal(DateTimeFieldType.clockhourOfHalfday(), paramInt, 2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DateTimeFormatterBuilder appendDayOfWeek(int paramInt) {
/*  765 */     return appendDecimal(DateTimeFieldType.dayOfWeek(), paramInt, 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DateTimeFormatterBuilder appendDayOfMonth(int paramInt) {
/*  775 */     return appendDecimal(DateTimeFieldType.dayOfMonth(), paramInt, 2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DateTimeFormatterBuilder appendDayOfYear(int paramInt) {
/*  785 */     return appendDecimal(DateTimeFieldType.dayOfYear(), paramInt, 3);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DateTimeFormatterBuilder appendWeekOfWeekyear(int paramInt) {
/*  795 */     return appendDecimal(DateTimeFieldType.weekOfWeekyear(), paramInt, 2);
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
/*      */   public DateTimeFormatterBuilder appendWeekyear(int paramInt1, int paramInt2) {
/*  807 */     return appendSignedDecimal(DateTimeFieldType.weekyear(), paramInt1, paramInt2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DateTimeFormatterBuilder appendMonthOfYear(int paramInt) {
/*  817 */     return appendDecimal(DateTimeFieldType.monthOfYear(), paramInt, 2);
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
/*      */   public DateTimeFormatterBuilder appendYear(int paramInt1, int paramInt2) {
/*  829 */     return appendSignedDecimal(DateTimeFieldType.year(), paramInt1, paramInt2);
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
/*      */   public DateTimeFormatterBuilder appendTwoDigitYear(int paramInt) {
/*  851 */     return appendTwoDigitYear(paramInt, false);
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
/*      */   public DateTimeFormatterBuilder appendTwoDigitYear(int paramInt, boolean paramBoolean) {
/*  869 */     return append0(new TwoDigitYear(DateTimeFieldType.year(), paramInt, paramBoolean));
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
/*      */   public DateTimeFormatterBuilder appendTwoDigitWeekyear(int paramInt) {
/*  891 */     return appendTwoDigitWeekyear(paramInt, false);
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
/*      */   public DateTimeFormatterBuilder appendTwoDigitWeekyear(int paramInt, boolean paramBoolean) {
/*  909 */     return append0(new TwoDigitYear(DateTimeFieldType.weekyear(), paramInt, paramBoolean));
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
/*      */   public DateTimeFormatterBuilder appendYearOfEra(int paramInt1, int paramInt2) {
/*  921 */     return appendDecimal(DateTimeFieldType.yearOfEra(), paramInt1, paramInt2);
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
/*      */   public DateTimeFormatterBuilder appendYearOfCentury(int paramInt1, int paramInt2) {
/*  933 */     return appendDecimal(DateTimeFieldType.yearOfCentury(), paramInt1, paramInt2);
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
/*      */   public DateTimeFormatterBuilder appendCenturyOfEra(int paramInt1, int paramInt2) {
/*  945 */     return appendSignedDecimal(DateTimeFieldType.centuryOfEra(), paramInt1, paramInt2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DateTimeFormatterBuilder appendHalfdayOfDayText() {
/*  955 */     return appendText(DateTimeFieldType.halfdayOfDay());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DateTimeFormatterBuilder appendDayOfWeekText() {
/*  965 */     return appendText(DateTimeFieldType.dayOfWeek());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DateTimeFormatterBuilder appendDayOfWeekShortText() {
/*  976 */     return appendShortText(DateTimeFieldType.dayOfWeek());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DateTimeFormatterBuilder appendMonthOfYearText() {
/*  987 */     return appendText(DateTimeFieldType.monthOfYear());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DateTimeFormatterBuilder appendMonthOfYearShortText() {
/*  997 */     return appendShortText(DateTimeFieldType.monthOfYear());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DateTimeFormatterBuilder appendEraText() {
/* 1007 */     return appendText(DateTimeFieldType.era());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DateTimeFormatterBuilder appendTimeZoneName() {
/* 1018 */     return append0(new TimeZoneName(0, null), null);
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
/*      */   public DateTimeFormatterBuilder appendTimeZoneName(Map<String, DateTimeZone> paramMap) {
/* 1031 */     TimeZoneName timeZoneName = new TimeZoneName(0, paramMap);
/* 1032 */     return append0(timeZoneName, timeZoneName);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DateTimeFormatterBuilder appendTimeZoneShortName() {
/* 1043 */     return append0(new TimeZoneName(1, null), null);
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
/*      */   public DateTimeFormatterBuilder appendTimeZoneShortName(Map<String, DateTimeZone> paramMap) {
/* 1057 */     TimeZoneName timeZoneName = new TimeZoneName(1, paramMap);
/* 1058 */     return append0(timeZoneName, timeZoneName);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DateTimeFormatterBuilder appendTimeZoneId() {
/* 1068 */     return append0(TimeZoneId.INSTANCE, TimeZoneId.INSTANCE);
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
/*      */   public DateTimeFormatterBuilder appendTimeZoneOffset(String paramString, boolean paramBoolean, int paramInt1, int paramInt2) {
/* 1091 */     return append0(new TimeZoneOffset(paramString, paramString, paramBoolean, paramInt1, paramInt2));
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
/*      */   public DateTimeFormatterBuilder appendTimeZoneOffset(String paramString1, String paramString2, boolean paramBoolean, int paramInt1, int paramInt2) {
/* 1118 */     return append0(new TimeZoneOffset(paramString1, paramString2, paramBoolean, paramInt1, paramInt2));
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
/*      */   public DateTimeFormatterBuilder appendPattern(String paramString) {
/* 1132 */     DateTimeFormat.appendPatternTo(this, paramString);
/* 1133 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   private Object getFormatter() {
/* 1138 */     Object object = this.iFormatter;
/*      */     
/* 1140 */     if (object == null) {
/* 1141 */       if (this.iElementPairs.size() == 2) {
/* 1142 */         Object object1 = this.iElementPairs.get(0);
/* 1143 */         Object object2 = this.iElementPairs.get(1);
/*      */         
/* 1145 */         if (object1 != null) {
/* 1146 */           if (object1 == object2 || object2 == null) {
/* 1147 */             object = object1;
/*      */           }
/*      */         } else {
/* 1150 */           object = object2;
/*      */         } 
/*      */       } 
/*      */       
/* 1154 */       if (object == null) {
/* 1155 */         object = new Composite(this.iElementPairs);
/*      */       }
/*      */       
/* 1158 */       this.iFormatter = object;
/*      */     } 
/*      */     
/* 1161 */     return object;
/*      */   }
/*      */   
/*      */   private boolean isPrinter(Object paramObject) {
/* 1165 */     if (paramObject instanceof InternalPrinter) {
/* 1166 */       if (paramObject instanceof Composite) {
/* 1167 */         return ((Composite)paramObject).isPrinter();
/*      */       }
/* 1169 */       return true;
/*      */     } 
/* 1171 */     return false;
/*      */   }
/*      */   
/*      */   private boolean isParser(Object paramObject) {
/* 1175 */     if (paramObject instanceof InternalParser) {
/* 1176 */       if (paramObject instanceof Composite) {
/* 1177 */         return ((Composite)paramObject).isParser();
/*      */       }
/* 1179 */       return true;
/*      */     } 
/* 1181 */     return false;
/*      */   }
/*      */   
/*      */   private boolean isFormatter(Object paramObject) {
/* 1185 */     return (isPrinter(paramObject) || isParser(paramObject));
/*      */   }
/*      */   
/*      */   static void appendUnknownString(Appendable paramAppendable, int paramInt) throws IOException {
/* 1189 */     for (int i = paramInt; --i >= 0;) {
/* 1190 */       paramAppendable.append('�');
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static class CharacterLiteral
/*      */     implements InternalPrinter, InternalParser
/*      */   {
/*      */     private final char iValue;
/*      */ 
/*      */     
/*      */     CharacterLiteral(char param1Char) {
/* 1202 */       this.iValue = param1Char;
/*      */     }
/*      */     
/*      */     public int estimatePrintedLength() {
/* 1206 */       return 1;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void printTo(Appendable param1Appendable, long param1Long, Chronology param1Chronology, int param1Int, DateTimeZone param1DateTimeZone, Locale param1Locale) throws IOException {
/* 1212 */       param1Appendable.append(this.iValue);
/*      */     }
/*      */     
/*      */     public void printTo(Appendable param1Appendable, ReadablePartial param1ReadablePartial, Locale param1Locale) throws IOException {
/* 1216 */       param1Appendable.append(this.iValue);
/*      */     }
/*      */     
/*      */     public int estimateParsedLength() {
/* 1220 */       return 1;
/*      */     }
/*      */     
/*      */     public int parseInto(DateTimeParserBucket param1DateTimeParserBucket, CharSequence param1CharSequence, int param1Int) {
/* 1224 */       if (param1Int >= param1CharSequence.length()) {
/* 1225 */         return param1Int ^ 0xFFFFFFFF;
/*      */       }
/*      */       
/* 1228 */       char c1 = param1CharSequence.charAt(param1Int);
/* 1229 */       char c2 = this.iValue;
/*      */       
/* 1231 */       if (c1 != c2) {
/* 1232 */         c1 = Character.toUpperCase(c1);
/* 1233 */         c2 = Character.toUpperCase(c2);
/* 1234 */         if (c1 != c2) {
/* 1235 */           c1 = Character.toLowerCase(c1);
/* 1236 */           c2 = Character.toLowerCase(c2);
/* 1237 */           if (c1 != c2) {
/* 1238 */             return param1Int ^ 0xFFFFFFFF;
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/* 1243 */       return param1Int + 1;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static class StringLiteral
/*      */     implements InternalPrinter, InternalParser
/*      */   {
/*      */     private final String iValue;
/*      */ 
/*      */     
/*      */     StringLiteral(String param1String) {
/* 1255 */       this.iValue = param1String;
/*      */     }
/*      */     
/*      */     public int estimatePrintedLength() {
/* 1259 */       return this.iValue.length();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void printTo(Appendable param1Appendable, long param1Long, Chronology param1Chronology, int param1Int, DateTimeZone param1DateTimeZone, Locale param1Locale) throws IOException {
/* 1265 */       param1Appendable.append(this.iValue);
/*      */     }
/*      */     
/*      */     public void printTo(Appendable param1Appendable, ReadablePartial param1ReadablePartial, Locale param1Locale) throws IOException {
/* 1269 */       param1Appendable.append(this.iValue);
/*      */     }
/*      */     
/*      */     public int estimateParsedLength() {
/* 1273 */       return this.iValue.length();
/*      */     }
/*      */     
/*      */     public int parseInto(DateTimeParserBucket param1DateTimeParserBucket, CharSequence param1CharSequence, int param1Int) {
/* 1277 */       if (DateTimeFormatterBuilder.csStartsWithIgnoreCase(param1CharSequence, param1Int, this.iValue)) {
/* 1278 */         return param1Int + this.iValue.length();
/*      */       }
/* 1280 */       return param1Int ^ 0xFFFFFFFF;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static abstract class NumberFormatter
/*      */     implements InternalPrinter, InternalParser
/*      */   {
/*      */     protected final DateTimeFieldType iFieldType;
/*      */     
/*      */     protected final int iMaxParsedDigits;
/*      */     protected final boolean iSigned;
/*      */     
/*      */     NumberFormatter(DateTimeFieldType param1DateTimeFieldType, int param1Int, boolean param1Boolean) {
/* 1294 */       this.iFieldType = param1DateTimeFieldType;
/* 1295 */       this.iMaxParsedDigits = param1Int;
/* 1296 */       this.iSigned = param1Boolean;
/*      */     }
/*      */     
/*      */     public int estimateParsedLength() {
/* 1300 */       return this.iMaxParsedDigits;
/*      */     }
/*      */     
/*      */     public int parseInto(DateTimeParserBucket param1DateTimeParserBucket, CharSequence param1CharSequence, int param1Int) {
/* 1304 */       int j, i = Math.min(this.iMaxParsedDigits, param1CharSequence.length() - param1Int);
/*      */       
/* 1306 */       boolean bool1 = false;
/* 1307 */       boolean bool2 = false;
/* 1308 */       byte b = 0;
/* 1309 */       while (b < i) {
/* 1310 */         j = param1CharSequence.charAt(param1Int + b);
/* 1311 */         if (b == 0 && (j == 45 || j == 43) && this.iSigned) {
/* 1312 */           bool1 = (j == 45) ? true : false;
/* 1313 */           bool2 = (j == 43) ? true : false;
/*      */ 
/*      */           
/* 1316 */           if (b + 1 >= i || (j = param1CharSequence.charAt(param1Int + b + 1)) < '0' || j > 57) {
/*      */             break;
/*      */           }
/*      */           
/* 1320 */           b++;
/*      */ 
/*      */           
/* 1323 */           i = Math.min(i + 1, param1CharSequence.length() - param1Int);
/*      */           continue;
/*      */         } 
/* 1326 */         if (j < 48 || j > 57) {
/*      */           break;
/*      */         }
/* 1329 */         b++;
/*      */       } 
/*      */       
/* 1332 */       if (b == 0) {
/* 1333 */         return param1Int ^ 0xFFFFFFFF;
/*      */       }
/*      */ 
/*      */       
/* 1337 */       if (b >= 9) {
/*      */ 
/*      */         
/* 1340 */         if (bool2) {
/* 1341 */           j = Integer.parseInt(param1CharSequence.subSequence(param1Int + 1, param1Int += b).toString());
/*      */         } else {
/* 1343 */           j = Integer.parseInt(param1CharSequence.subSequence(param1Int, param1Int += b).toString());
/*      */         } 
/*      */       } else {
/*      */         
/* 1347 */         int k = param1Int;
/* 1348 */         if (bool1 || bool2) {
/* 1349 */           k++;
/*      */         }
/*      */         try {
/* 1352 */           j = param1CharSequence.charAt(k++) - 48;
/* 1353 */         } catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException) {
/* 1354 */           return param1Int ^ 0xFFFFFFFF;
/*      */         } 
/* 1356 */         param1Int += b;
/* 1357 */         while (k < param1Int) {
/* 1358 */           j = (j << 3) + (j << 1) + param1CharSequence.charAt(k++) - 48;
/*      */         }
/* 1360 */         if (bool1) {
/* 1361 */           j = -j;
/*      */         }
/*      */       } 
/*      */       
/* 1365 */       param1DateTimeParserBucket.saveField(this.iFieldType, j);
/* 1366 */       return param1Int;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static class UnpaddedNumber
/*      */     extends NumberFormatter
/*      */   {
/*      */     protected UnpaddedNumber(DateTimeFieldType param1DateTimeFieldType, int param1Int, boolean param1Boolean) {
/* 1376 */       super(param1DateTimeFieldType, param1Int, param1Boolean);
/*      */     }
/*      */     
/*      */     public int estimatePrintedLength() {
/* 1380 */       return this.iMaxParsedDigits;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void printTo(Appendable param1Appendable, long param1Long, Chronology param1Chronology, int param1Int, DateTimeZone param1DateTimeZone, Locale param1Locale) throws IOException {
/*      */       try {
/* 1387 */         DateTimeField dateTimeField = this.iFieldType.getField(param1Chronology);
/* 1388 */         FormatUtils.appendUnpaddedInteger(param1Appendable, dateTimeField.get(param1Long));
/* 1389 */       } catch (RuntimeException runtimeException) {
/* 1390 */         param1Appendable.append('�');
/*      */       } 
/*      */     }
/*      */     
/*      */     public void printTo(Appendable param1Appendable, ReadablePartial param1ReadablePartial, Locale param1Locale) throws IOException {
/* 1395 */       if (param1ReadablePartial.isSupported(this.iFieldType)) {
/*      */         try {
/* 1397 */           FormatUtils.appendUnpaddedInteger(param1Appendable, param1ReadablePartial.get(this.iFieldType));
/* 1398 */         } catch (RuntimeException runtimeException) {
/* 1399 */           param1Appendable.append('�');
/*      */         } 
/*      */       } else {
/* 1402 */         param1Appendable.append('�');
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static class PaddedNumber
/*      */     extends NumberFormatter
/*      */   {
/*      */     protected final int iMinPrintedDigits;
/*      */ 
/*      */     
/*      */     protected PaddedNumber(DateTimeFieldType param1DateTimeFieldType, int param1Int1, boolean param1Boolean, int param1Int2) {
/* 1415 */       super(param1DateTimeFieldType, param1Int1, param1Boolean);
/* 1416 */       this.iMinPrintedDigits = param1Int2;
/*      */     }
/*      */     
/*      */     public int estimatePrintedLength() {
/* 1420 */       return this.iMaxParsedDigits;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void printTo(Appendable param1Appendable, long param1Long, Chronology param1Chronology, int param1Int, DateTimeZone param1DateTimeZone, Locale param1Locale) throws IOException {
/*      */       try {
/* 1427 */         DateTimeField dateTimeField = this.iFieldType.getField(param1Chronology);
/* 1428 */         FormatUtils.appendPaddedInteger(param1Appendable, dateTimeField.get(param1Long), this.iMinPrintedDigits);
/* 1429 */       } catch (RuntimeException runtimeException) {
/* 1430 */         DateTimeFormatterBuilder.appendUnknownString(param1Appendable, this.iMinPrintedDigits);
/*      */       } 
/*      */     }
/*      */     
/*      */     public void printTo(Appendable param1Appendable, ReadablePartial param1ReadablePartial, Locale param1Locale) throws IOException {
/* 1435 */       if (param1ReadablePartial.isSupported(this.iFieldType)) {
/*      */         try {
/* 1437 */           FormatUtils.appendPaddedInteger(param1Appendable, param1ReadablePartial.get(this.iFieldType), this.iMinPrintedDigits);
/* 1438 */         } catch (RuntimeException runtimeException) {
/* 1439 */           DateTimeFormatterBuilder.appendUnknownString(param1Appendable, this.iMinPrintedDigits);
/*      */         } 
/*      */       } else {
/* 1442 */         DateTimeFormatterBuilder.appendUnknownString(param1Appendable, this.iMinPrintedDigits);
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   static class FixedNumber
/*      */     extends PaddedNumber
/*      */   {
/*      */     protected FixedNumber(DateTimeFieldType param1DateTimeFieldType, int param1Int, boolean param1Boolean) {
/* 1451 */       super(param1DateTimeFieldType, param1Int, param1Boolean, param1Int);
/*      */     }
/*      */ 
/*      */     
/*      */     public int parseInto(DateTimeParserBucket param1DateTimeParserBucket, CharSequence param1CharSequence, int param1Int) {
/* 1456 */       int i = super.parseInto(param1DateTimeParserBucket, param1CharSequence, param1Int);
/* 1457 */       if (i < 0) {
/* 1458 */         return i;
/*      */       }
/* 1460 */       int j = param1Int + this.iMaxParsedDigits;
/* 1461 */       if (i != j) {
/* 1462 */         if (this.iSigned) {
/* 1463 */           char c = param1CharSequence.charAt(param1Int);
/* 1464 */           if (c == '-' || c == '+') {
/* 1465 */             j++;
/*      */           }
/*      */         } 
/* 1468 */         if (i > j)
/*      */         {
/* 1470 */           return j + 1 ^ 0xFFFFFFFF; } 
/* 1471 */         if (i < j)
/*      */         {
/* 1473 */           return i ^ 0xFFFFFFFF;
/*      */         }
/*      */       } 
/* 1476 */       return i;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static class TwoDigitYear
/*      */     implements InternalPrinter, InternalParser
/*      */   {
/*      */     private final DateTimeFieldType iType;
/*      */     
/*      */     private final int iPivot;
/*      */     
/*      */     private final boolean iLenientParse;
/*      */ 
/*      */     
/*      */     TwoDigitYear(DateTimeFieldType param1DateTimeFieldType, int param1Int, boolean param1Boolean) {
/* 1492 */       this.iType = param1DateTimeFieldType;
/* 1493 */       this.iPivot = param1Int;
/* 1494 */       this.iLenientParse = param1Boolean;
/*      */     }
/*      */     
/*      */     public int estimateParsedLength() {
/* 1498 */       return this.iLenientParse ? 4 : 2;
/*      */     }
/*      */     
/*      */     public int parseInto(DateTimeParserBucket param1DateTimeParserBucket, CharSequence param1CharSequence, int param1Int) {
/* 1502 */       int n, i = param1CharSequence.length() - param1Int;
/*      */       
/* 1504 */       if (!this.iLenientParse) {
/* 1505 */         i = Math.min(2, i);
/* 1506 */         if (i < 2) {
/* 1507 */           return param1Int ^ 0xFFFFFFFF;
/*      */         }
/*      */       } else {
/* 1510 */         boolean bool1 = false;
/* 1511 */         boolean bool2 = false;
/* 1512 */         byte b = 0;
/* 1513 */         while (b < i) {
/* 1514 */           char c1 = param1CharSequence.charAt(param1Int + b);
/* 1515 */           if (b == 0 && (c1 == '-' || c1 == '+')) {
/* 1516 */             bool1 = true;
/* 1517 */             bool2 = (c1 == '-') ? true : false;
/* 1518 */             if (bool2) {
/* 1519 */               b++;
/*      */               continue;
/*      */             } 
/* 1522 */             param1Int++;
/* 1523 */             i--;
/*      */             
/*      */             continue;
/*      */           } 
/* 1527 */           if (c1 < '0' || c1 > '9') {
/*      */             break;
/*      */           }
/* 1530 */           b++;
/*      */         } 
/*      */         
/* 1533 */         if (b == 0) {
/* 1534 */           return param1Int ^ 0xFFFFFFFF;
/*      */         }
/*      */         
/* 1537 */         if (bool1 || b != 2) {
/*      */           int i1;
/* 1539 */           if (b >= 9) {
/*      */ 
/*      */             
/* 1542 */             i1 = Integer.parseInt(param1CharSequence.subSequence(param1Int, param1Int += b).toString());
/*      */           } else {
/* 1544 */             n = param1Int;
/* 1545 */             if (bool2) {
/* 1546 */               n++;
/*      */             }
/*      */             try {
/* 1549 */               i1 = param1CharSequence.charAt(n++) - 48;
/* 1550 */             } catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException) {
/* 1551 */               return param1Int ^ 0xFFFFFFFF;
/*      */             } 
/* 1553 */             param1Int += b;
/* 1554 */             while (n < param1Int) {
/* 1555 */               i1 = (i1 << 3) + (i1 << 1) + param1CharSequence.charAt(n++) - 48;
/*      */             }
/* 1557 */             if (bool2) {
/* 1558 */               i1 = -i1;
/*      */             }
/*      */           } 
/*      */           
/* 1562 */           param1DateTimeParserBucket.saveField(this.iType, i1);
/* 1563 */           return param1Int;
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 1568 */       char c = param1CharSequence.charAt(param1Int);
/* 1569 */       if (c < '0' || c > '9') {
/* 1570 */         return param1Int ^ 0xFFFFFFFF;
/*      */       }
/* 1572 */       int j = c - 48;
/* 1573 */       c = param1CharSequence.charAt(param1Int + 1);
/* 1574 */       if (c < '0' || c > '9') {
/* 1575 */         return param1Int ^ 0xFFFFFFFF;
/*      */       }
/* 1577 */       j = (j << 3) + (j << 1) + c - 48;
/*      */       
/* 1579 */       int k = this.iPivot;
/*      */       
/* 1581 */       if (param1DateTimeParserBucket.getPivotYear() != null) {
/* 1582 */         k = param1DateTimeParserBucket.getPivotYear().intValue();
/*      */       }
/*      */       
/* 1585 */       int m = k - 50;
/*      */ 
/*      */       
/* 1588 */       if (m >= 0) {
/* 1589 */         n = m % 100;
/*      */       } else {
/* 1591 */         n = 99 + (m + 1) % 100;
/*      */       } 
/*      */       
/* 1594 */       j += m + ((j < n) ? 100 : 0) - n;
/*      */       
/* 1596 */       param1DateTimeParserBucket.saveField(this.iType, j);
/* 1597 */       return param1Int + 2;
/*      */     }
/*      */     
/*      */     public int estimatePrintedLength() {
/* 1601 */       return 2;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void printTo(Appendable param1Appendable, long param1Long, Chronology param1Chronology, int param1Int, DateTimeZone param1DateTimeZone, Locale param1Locale) throws IOException {
/* 1607 */       int i = getTwoDigitYear(param1Long, param1Chronology);
/* 1608 */       if (i < 0) {
/* 1609 */         param1Appendable.append('�');
/* 1610 */         param1Appendable.append('�');
/*      */       } else {
/* 1612 */         FormatUtils.appendPaddedInteger(param1Appendable, i, 2);
/*      */       } 
/*      */     }
/*      */     
/*      */     private int getTwoDigitYear(long param1Long, Chronology param1Chronology) {
/*      */       try {
/* 1618 */         int i = this.iType.getField(param1Chronology).get(param1Long);
/* 1619 */         if (i < 0) {
/* 1620 */           i = -i;
/*      */         }
/* 1622 */         return i % 100;
/* 1623 */       } catch (RuntimeException runtimeException) {
/* 1624 */         return -1;
/*      */       } 
/*      */     }
/*      */     
/*      */     public void printTo(Appendable param1Appendable, ReadablePartial param1ReadablePartial, Locale param1Locale) throws IOException {
/* 1629 */       int i = getTwoDigitYear(param1ReadablePartial);
/* 1630 */       if (i < 0) {
/* 1631 */         param1Appendable.append('�');
/* 1632 */         param1Appendable.append('�');
/*      */       } else {
/* 1634 */         FormatUtils.appendPaddedInteger(param1Appendable, i, 2);
/*      */       } 
/*      */     }
/*      */     
/*      */     private int getTwoDigitYear(ReadablePartial param1ReadablePartial) {
/* 1639 */       if (param1ReadablePartial.isSupported(this.iType)) {
/*      */         try {
/* 1641 */           int i = param1ReadablePartial.get(this.iType);
/* 1642 */           if (i < 0) {
/* 1643 */             i = -i;
/*      */           }
/* 1645 */           return i % 100;
/* 1646 */         } catch (RuntimeException runtimeException) {}
/*      */       }
/* 1648 */       return -1;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static class TextField
/*      */     implements InternalPrinter, InternalParser
/*      */   {
/* 1656 */     private static Map<Locale, Map<DateTimeFieldType, Object[]>> cParseCache = new ConcurrentHashMap<Locale, Map<DateTimeFieldType, Object[]>>();
/*      */     
/*      */     private final DateTimeFieldType iFieldType;
/*      */     
/*      */     private final boolean iShort;
/*      */     
/*      */     TextField(DateTimeFieldType param1DateTimeFieldType, boolean param1Boolean) {
/* 1663 */       this.iFieldType = param1DateTimeFieldType;
/* 1664 */       this.iShort = param1Boolean;
/*      */     }
/*      */     
/*      */     public int estimatePrintedLength() {
/* 1668 */       return this.iShort ? 6 : 20;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void printTo(Appendable param1Appendable, long param1Long, Chronology param1Chronology, int param1Int, DateTimeZone param1DateTimeZone, Locale param1Locale) throws IOException {
/*      */       try {
/* 1675 */         param1Appendable.append(print(param1Long, param1Chronology, param1Locale));
/* 1676 */       } catch (RuntimeException runtimeException) {
/* 1677 */         param1Appendable.append('�');
/*      */       } 
/*      */     }
/*      */     
/*      */     public void printTo(Appendable param1Appendable, ReadablePartial param1ReadablePartial, Locale param1Locale) throws IOException {
/*      */       try {
/* 1683 */         param1Appendable.append(print(param1ReadablePartial, param1Locale));
/* 1684 */       } catch (RuntimeException runtimeException) {
/* 1685 */         param1Appendable.append('�');
/*      */       } 
/*      */     }
/*      */     
/*      */     private String print(long param1Long, Chronology param1Chronology, Locale param1Locale) {
/* 1690 */       DateTimeField dateTimeField = this.iFieldType.getField(param1Chronology);
/* 1691 */       if (this.iShort) {
/* 1692 */         return dateTimeField.getAsShortText(param1Long, param1Locale);
/*      */       }
/* 1694 */       return dateTimeField.getAsText(param1Long, param1Locale);
/*      */     }
/*      */ 
/*      */     
/*      */     private String print(ReadablePartial param1ReadablePartial, Locale param1Locale) {
/* 1699 */       if (param1ReadablePartial.isSupported(this.iFieldType)) {
/* 1700 */         DateTimeField dateTimeField = this.iFieldType.getField(param1ReadablePartial.getChronology());
/* 1701 */         if (this.iShort) {
/* 1702 */           return dateTimeField.getAsShortText(param1ReadablePartial, param1Locale);
/*      */         }
/* 1704 */         return dateTimeField.getAsText(param1ReadablePartial, param1Locale);
/*      */       } 
/*      */       
/* 1707 */       return "�";
/*      */     }
/*      */ 
/*      */     
/*      */     public int estimateParsedLength() {
/* 1712 */       return estimatePrintedLength();
/*      */     }
/*      */ 
/*      */     
/*      */     public int parseInto(DateTimeParserBucket param1DateTimeParserBucket, CharSequence param1CharSequence, int param1Int) {
/* 1717 */       Locale locale = param1DateTimeParserBucket.getLocale();
/*      */ 
/*      */       
/* 1720 */       Map map = null;
/* 1721 */       int i = 0;
/* 1722 */       Map<Object, Object> map1 = (Map)cParseCache.get(locale);
/* 1723 */       if (map1 == null) {
/* 1724 */         map1 = new ConcurrentHashMap<Object, Object>();
/* 1725 */         cParseCache.put(locale, map1);
/*      */       } 
/* 1727 */       Object[] arrayOfObject = (Object[])map1.get(this.iFieldType);
/* 1728 */       if (arrayOfObject == null) {
/* 1729 */         map = new ConcurrentHashMap<Object, Object>(32);
/* 1730 */         MutableDateTime mutableDateTime = new MutableDateTime(0L, DateTimeZone.UTC);
/* 1731 */         MutableDateTime.Property property = mutableDateTime.property(this.iFieldType);
/* 1732 */         int m = property.getMinimumValueOverall();
/* 1733 */         int n = property.getMaximumValueOverall();
/* 1734 */         if (n - m > 32) {
/* 1735 */           return param1Int ^ 0xFFFFFFFF;
/*      */         }
/* 1737 */         i = property.getMaximumTextLength(locale);
/* 1738 */         for (int i1 = m; i1 <= n; i1++) {
/* 1739 */           property.set(i1);
/* 1740 */           map.put(property.getAsShortText(locale), Boolean.TRUE);
/* 1741 */           map.put(property.getAsShortText(locale).toLowerCase(locale), Boolean.TRUE);
/* 1742 */           map.put(property.getAsShortText(locale).toUpperCase(locale), Boolean.TRUE);
/* 1743 */           map.put(property.getAsText(locale), Boolean.TRUE);
/* 1744 */           map.put(property.getAsText(locale).toLowerCase(locale), Boolean.TRUE);
/* 1745 */           map.put(property.getAsText(locale).toUpperCase(locale), Boolean.TRUE);
/*      */         } 
/* 1747 */         if ("en".equals(locale.getLanguage()) && this.iFieldType == DateTimeFieldType.era()) {
/*      */           
/* 1749 */           map.put("BCE", Boolean.TRUE);
/* 1750 */           map.put("bce", Boolean.TRUE);
/* 1751 */           map.put("CE", Boolean.TRUE);
/* 1752 */           map.put("ce", Boolean.TRUE);
/* 1753 */           i = 3;
/*      */         } 
/* 1755 */         arrayOfObject = new Object[] { map, Integer.valueOf(i) };
/* 1756 */         map1.put(this.iFieldType, arrayOfObject);
/*      */       } else {
/* 1758 */         map = (Map)arrayOfObject[0];
/* 1759 */         i = ((Integer)arrayOfObject[1]).intValue();
/*      */       } 
/*      */       
/* 1762 */       int j = Math.min(param1CharSequence.length(), param1Int + i);
/* 1763 */       for (int k = j; k > param1Int; k--) {
/* 1764 */         String str = param1CharSequence.subSequence(param1Int, k).toString();
/* 1765 */         if (map.containsKey(str)) {
/* 1766 */           param1DateTimeParserBucket.saveField(this.iFieldType, str, locale);
/* 1767 */           return k;
/*      */         } 
/*      */       } 
/* 1770 */       return param1Int ^ 0xFFFFFFFF;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static class Fraction
/*      */     implements InternalPrinter, InternalParser
/*      */   {
/*      */     private final DateTimeFieldType iFieldType;
/*      */     
/*      */     protected int iMinDigits;
/*      */     protected int iMaxDigits;
/*      */     
/*      */     protected Fraction(DateTimeFieldType param1DateTimeFieldType, int param1Int1, int param1Int2) {
/* 1784 */       this.iFieldType = param1DateTimeFieldType;
/*      */       
/* 1786 */       if (param1Int2 > 18) {
/* 1787 */         param1Int2 = 18;
/*      */       }
/* 1789 */       this.iMinDigits = param1Int1;
/* 1790 */       this.iMaxDigits = param1Int2;
/*      */     }
/*      */     
/*      */     public int estimatePrintedLength() {
/* 1794 */       return this.iMaxDigits;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void printTo(Appendable param1Appendable, long param1Long, Chronology param1Chronology, int param1Int, DateTimeZone param1DateTimeZone, Locale param1Locale) throws IOException {
/* 1800 */       printTo(param1Appendable, param1Long, param1Chronology);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void printTo(Appendable param1Appendable, ReadablePartial param1ReadablePartial, Locale param1Locale) throws IOException {
/* 1806 */       long l = param1ReadablePartial.getChronology().set(param1ReadablePartial, 0L);
/* 1807 */       printTo(param1Appendable, l, param1ReadablePartial.getChronology());
/*      */     }
/*      */     
/*      */     protected void printTo(Appendable param1Appendable, long param1Long, Chronology param1Chronology) throws IOException {
/*      */       long l1;
/*      */       String str;
/* 1813 */       DateTimeField dateTimeField = this.iFieldType.getField(param1Chronology);
/* 1814 */       int i = this.iMinDigits;
/*      */ 
/*      */       
/*      */       try {
/* 1818 */         l1 = dateTimeField.remainder(param1Long);
/* 1819 */       } catch (RuntimeException runtimeException) {
/* 1820 */         DateTimeFormatterBuilder.appendUnknownString(param1Appendable, i);
/*      */         
/*      */         return;
/*      */       } 
/* 1824 */       if (l1 == 0L) {
/* 1825 */         while (--i >= 0) {
/* 1826 */           param1Appendable.append('0');
/*      */         }
/*      */         
/*      */         return;
/*      */       } 
/*      */       
/* 1832 */       long[] arrayOfLong = getFractionData(l1, dateTimeField);
/* 1833 */       long l2 = arrayOfLong[0];
/* 1834 */       int j = (int)arrayOfLong[1];
/*      */       
/* 1836 */       if ((l2 & 0x7FFFFFFFL) == l2) {
/* 1837 */         str = Integer.toString((int)l2);
/*      */       } else {
/* 1839 */         str = Long.toString(l2);
/*      */       } 
/*      */       
/* 1842 */       int k = str.length();
/* 1843 */       int m = j;
/* 1844 */       while (k < m) {
/* 1845 */         param1Appendable.append('0');
/* 1846 */         i--;
/* 1847 */         m--;
/*      */       } 
/*      */       
/* 1850 */       if (i < m) {
/*      */         
/* 1852 */         while (i < m && 
/* 1853 */           k > 1 && str.charAt(k - 1) == '0') {
/*      */ 
/*      */           
/* 1856 */           m--;
/* 1857 */           k--;
/*      */         } 
/* 1859 */         if (k < str.length()) {
/* 1860 */           for (byte b = 0; b < k; b++) {
/* 1861 */             param1Appendable.append(str.charAt(b));
/*      */           }
/*      */           
/*      */           return;
/*      */         } 
/*      */       } 
/* 1867 */       param1Appendable.append(str);
/*      */     }
/*      */     
/*      */     private long[] getFractionData(long param1Long, DateTimeField param1DateTimeField) {
/* 1871 */       long l2, l1 = param1DateTimeField.getDurationField().getUnitMillis();
/*      */       
/* 1873 */       int i = this.iMaxDigits;
/*      */       while (true) {
/* 1875 */         switch (i) { default:
/* 1876 */             l2 = 1L; break;
/* 1877 */           case 1: l2 = 10L; break;
/* 1878 */           case 2: l2 = 100L; break;
/* 1879 */           case 3: l2 = 1000L; break;
/* 1880 */           case 4: l2 = 10000L; break;
/* 1881 */           case 5: l2 = 100000L; break;
/* 1882 */           case 6: l2 = 1000000L; break;
/* 1883 */           case 7: l2 = 10000000L; break;
/* 1884 */           case 8: l2 = 100000000L; break;
/* 1885 */           case 9: l2 = 1000000000L; break;
/* 1886 */           case 10: l2 = 10000000000L; break;
/* 1887 */           case 11: l2 = 100000000000L; break;
/* 1888 */           case 12: l2 = 1000000000000L; break;
/* 1889 */           case 13: l2 = 10000000000000L; break;
/* 1890 */           case 14: l2 = 100000000000000L; break;
/* 1891 */           case 15: l2 = 1000000000000000L; break;
/* 1892 */           case 16: l2 = 10000000000000000L; break;
/* 1893 */           case 17: l2 = 100000000000000000L; break;
/* 1894 */           case 18: l2 = 1000000000000000000L; break; }
/*      */         
/* 1896 */         if (l1 * l2 / l2 == l1) {
/*      */           break;
/*      */         }
/*      */         
/* 1900 */         i--;
/*      */       } 
/*      */       
/* 1903 */       return new long[] { param1Long * l2 / l1, i };
/*      */     }
/*      */     
/*      */     public int estimateParsedLength() {
/* 1907 */       return this.iMaxDigits;
/*      */     }
/*      */     
/*      */     public int parseInto(DateTimeParserBucket param1DateTimeParserBucket, CharSequence param1CharSequence, int param1Int) {
/* 1911 */       DateTimeField dateTimeField = this.iFieldType.getField(param1DateTimeParserBucket.getChronology());
/*      */       
/* 1913 */       int i = Math.min(this.iMaxDigits, param1CharSequence.length() - param1Int);
/*      */       
/* 1915 */       long l1 = 0L;
/* 1916 */       long l2 = dateTimeField.getDurationField().getUnitMillis() * 10L;
/* 1917 */       byte b = 0;
/* 1918 */       while (b < i) {
/* 1919 */         char c = param1CharSequence.charAt(param1Int + b);
/* 1920 */         if (c < '0' || c > '9') {
/*      */           break;
/*      */         }
/* 1923 */         b++;
/* 1924 */         long l = l2 / 10L;
/* 1925 */         l1 += (c - 48) * l;
/* 1926 */         l2 = l;
/*      */       } 
/*      */       
/* 1929 */       l1 /= 10L;
/*      */       
/* 1931 */       if (b == 0) {
/* 1932 */         return param1Int ^ 0xFFFFFFFF;
/*      */       }
/*      */       
/* 1935 */       if (l1 > 2147483647L) {
/* 1936 */         return param1Int ^ 0xFFFFFFFF;
/*      */       }
/*      */       
/* 1939 */       PreciseDateTimeField preciseDateTimeField = new PreciseDateTimeField(DateTimeFieldType.millisOfSecond(), MillisDurationField.INSTANCE, dateTimeField.getDurationField());
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1944 */       param1DateTimeParserBucket.saveField((DateTimeField)preciseDateTimeField, (int)l1);
/*      */       
/* 1946 */       return param1Int + b;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static class TimeZoneOffset
/*      */     implements InternalPrinter, InternalParser
/*      */   {
/*      */     private final String iZeroOffsetPrintText;
/*      */     
/*      */     private final String iZeroOffsetParseText;
/*      */     
/*      */     private final boolean iShowSeparators;
/*      */     
/*      */     private final int iMinFields;
/*      */     
/*      */     private final int iMaxFields;
/*      */     
/*      */     TimeZoneOffset(String param1String1, String param1String2, boolean param1Boolean, int param1Int1, int param1Int2) {
/* 1965 */       this.iZeroOffsetPrintText = param1String1;
/* 1966 */       this.iZeroOffsetParseText = param1String2;
/* 1967 */       this.iShowSeparators = param1Boolean;
/* 1968 */       if (param1Int1 <= 0 || param1Int2 < param1Int1) {
/* 1969 */         throw new IllegalArgumentException();
/*      */       }
/* 1971 */       if (param1Int1 > 4) {
/* 1972 */         param1Int1 = 4;
/* 1973 */         param1Int2 = 4;
/*      */       } 
/* 1975 */       this.iMinFields = param1Int1;
/* 1976 */       this.iMaxFields = param1Int2;
/*      */     }
/*      */     
/*      */     public int estimatePrintedLength() {
/* 1980 */       int i = 1 + this.iMinFields << 1;
/* 1981 */       if (this.iShowSeparators) {
/* 1982 */         i += this.iMinFields - 1;
/*      */       }
/* 1984 */       if (this.iZeroOffsetPrintText != null && this.iZeroOffsetPrintText.length() > i) {
/* 1985 */         i = this.iZeroOffsetPrintText.length();
/*      */       }
/* 1987 */       return i;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void printTo(Appendable param1Appendable, long param1Long, Chronology param1Chronology, int param1Int, DateTimeZone param1DateTimeZone, Locale param1Locale) throws IOException {
/* 1993 */       if (param1DateTimeZone == null) {
/*      */         return;
/*      */       }
/* 1996 */       if (param1Int == 0 && this.iZeroOffsetPrintText != null) {
/* 1997 */         param1Appendable.append(this.iZeroOffsetPrintText);
/*      */         return;
/*      */       } 
/* 2000 */       if (param1Int >= 0) {
/* 2001 */         param1Appendable.append('+');
/*      */       } else {
/* 2003 */         param1Appendable.append('-');
/* 2004 */         param1Int = -param1Int;
/*      */       } 
/*      */       
/* 2007 */       int i = param1Int / 3600000;
/* 2008 */       FormatUtils.appendPaddedInteger(param1Appendable, i, 2);
/* 2009 */       if (this.iMaxFields == 1) {
/*      */         return;
/*      */       }
/* 2012 */       param1Int -= i * 3600000;
/* 2013 */       if (param1Int == 0 && this.iMinFields <= 1) {
/*      */         return;
/*      */       }
/*      */       
/* 2017 */       int j = param1Int / 60000;
/* 2018 */       if (this.iShowSeparators) {
/* 2019 */         param1Appendable.append(':');
/*      */       }
/* 2021 */       FormatUtils.appendPaddedInteger(param1Appendable, j, 2);
/* 2022 */       if (this.iMaxFields == 2) {
/*      */         return;
/*      */       }
/* 2025 */       param1Int -= j * 60000;
/* 2026 */       if (param1Int == 0 && this.iMinFields <= 2) {
/*      */         return;
/*      */       }
/*      */       
/* 2030 */       int k = param1Int / 1000;
/* 2031 */       if (this.iShowSeparators) {
/* 2032 */         param1Appendable.append(':');
/*      */       }
/* 2034 */       FormatUtils.appendPaddedInteger(param1Appendable, k, 2);
/* 2035 */       if (this.iMaxFields == 3) {
/*      */         return;
/*      */       }
/* 2038 */       param1Int -= k * 1000;
/* 2039 */       if (param1Int == 0 && this.iMinFields <= 3) {
/*      */         return;
/*      */       }
/*      */       
/* 2043 */       if (this.iShowSeparators) {
/* 2044 */         param1Appendable.append('.');
/*      */       }
/* 2046 */       FormatUtils.appendPaddedInteger(param1Appendable, param1Int, 3);
/*      */     }
/*      */ 
/*      */     
/*      */     public void printTo(Appendable param1Appendable, ReadablePartial param1ReadablePartial, Locale param1Locale) throws IOException {}
/*      */ 
/*      */     
/*      */     public int estimateParsedLength() {
/* 2054 */       return estimatePrintedLength();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int parseInto(DateTimeParserBucket param1DateTimeParserBucket, CharSequence param1CharSequence, int param1Int) {
/*      */       // Byte code:
/*      */       //   0: aload_2
/*      */       //   1: invokeinterface length : ()I
/*      */       //   6: iload_3
/*      */       //   7: isub
/*      */       //   8: istore #4
/*      */       //   10: aload_0
/*      */       //   11: getfield iZeroOffsetParseText : Ljava/lang/String;
/*      */       //   14: ifnull -> 98
/*      */       //   17: aload_0
/*      */       //   18: getfield iZeroOffsetParseText : Ljava/lang/String;
/*      */       //   21: invokevirtual length : ()I
/*      */       //   24: ifne -> 68
/*      */       //   27: iload #4
/*      */       //   29: ifle -> 58
/*      */       //   32: aload_2
/*      */       //   33: iload_3
/*      */       //   34: invokeinterface charAt : (I)C
/*      */       //   39: istore #5
/*      */       //   41: iload #5
/*      */       //   43: bipush #45
/*      */       //   45: if_icmpeq -> 98
/*      */       //   48: iload #5
/*      */       //   50: bipush #43
/*      */       //   52: if_icmpne -> 58
/*      */       //   55: goto -> 98
/*      */       //   58: aload_1
/*      */       //   59: iconst_0
/*      */       //   60: invokestatic valueOf : (I)Ljava/lang/Integer;
/*      */       //   63: invokevirtual setOffset : (Ljava/lang/Integer;)V
/*      */       //   66: iload_3
/*      */       //   67: ireturn
/*      */       //   68: aload_2
/*      */       //   69: iload_3
/*      */       //   70: aload_0
/*      */       //   71: getfield iZeroOffsetParseText : Ljava/lang/String;
/*      */       //   74: invokestatic csStartsWithIgnoreCase : (Ljava/lang/CharSequence;ILjava/lang/String;)Z
/*      */       //   77: ifeq -> 98
/*      */       //   80: aload_1
/*      */       //   81: iconst_0
/*      */       //   82: invokestatic valueOf : (I)Ljava/lang/Integer;
/*      */       //   85: invokevirtual setOffset : (Ljava/lang/Integer;)V
/*      */       //   88: iload_3
/*      */       //   89: aload_0
/*      */       //   90: getfield iZeroOffsetParseText : Ljava/lang/String;
/*      */       //   93: invokevirtual length : ()I
/*      */       //   96: iadd
/*      */       //   97: ireturn
/*      */       //   98: iload #4
/*      */       //   100: iconst_1
/*      */       //   101: if_icmpgt -> 108
/*      */       //   104: iload_3
/*      */       //   105: iconst_m1
/*      */       //   106: ixor
/*      */       //   107: ireturn
/*      */       //   108: aload_2
/*      */       //   109: iload_3
/*      */       //   110: invokeinterface charAt : (I)C
/*      */       //   115: istore #6
/*      */       //   117: iload #6
/*      */       //   119: bipush #45
/*      */       //   121: if_icmpne -> 130
/*      */       //   124: iconst_1
/*      */       //   125: istore #5
/*      */       //   127: goto -> 147
/*      */       //   130: iload #6
/*      */       //   132: bipush #43
/*      */       //   134: if_icmpne -> 143
/*      */       //   137: iconst_0
/*      */       //   138: istore #5
/*      */       //   140: goto -> 147
/*      */       //   143: iload_3
/*      */       //   144: iconst_m1
/*      */       //   145: ixor
/*      */       //   146: ireturn
/*      */       //   147: iinc #4, -1
/*      */       //   150: iinc #3, 1
/*      */       //   153: aload_0
/*      */       //   154: aload_2
/*      */       //   155: iload_3
/*      */       //   156: iconst_2
/*      */       //   157: invokespecial digitCount : (Ljava/lang/CharSequence;II)I
/*      */       //   160: iconst_2
/*      */       //   161: if_icmpge -> 168
/*      */       //   164: iload_3
/*      */       //   165: iconst_m1
/*      */       //   166: ixor
/*      */       //   167: ireturn
/*      */       //   168: aload_2
/*      */       //   169: iload_3
/*      */       //   170: invokestatic parseTwoDigits : (Ljava/lang/CharSequence;I)I
/*      */       //   173: istore #8
/*      */       //   175: iload #8
/*      */       //   177: bipush #23
/*      */       //   179: if_icmple -> 186
/*      */       //   182: iload_3
/*      */       //   183: iconst_m1
/*      */       //   184: ixor
/*      */       //   185: ireturn
/*      */       //   186: iload #8
/*      */       //   188: ldc 3600000
/*      */       //   190: imul
/*      */       //   191: istore #7
/*      */       //   193: iinc #4, -2
/*      */       //   196: iinc #3, 2
/*      */       //   199: iload #4
/*      */       //   201: ifgt -> 207
/*      */       //   204: goto -> 569
/*      */       //   207: aload_2
/*      */       //   208: iload_3
/*      */       //   209: invokeinterface charAt : (I)C
/*      */       //   214: istore #6
/*      */       //   216: iload #6
/*      */       //   218: bipush #58
/*      */       //   220: if_icmpne -> 235
/*      */       //   223: iconst_1
/*      */       //   224: istore #9
/*      */       //   226: iinc #4, -1
/*      */       //   229: iinc #3, 1
/*      */       //   232: goto -> 252
/*      */       //   235: iload #6
/*      */       //   237: bipush #48
/*      */       //   239: if_icmplt -> 569
/*      */       //   242: iload #6
/*      */       //   244: bipush #57
/*      */       //   246: if_icmpgt -> 569
/*      */       //   249: iconst_0
/*      */       //   250: istore #9
/*      */       //   252: aload_0
/*      */       //   253: aload_2
/*      */       //   254: iload_3
/*      */       //   255: iconst_2
/*      */       //   256: invokespecial digitCount : (Ljava/lang/CharSequence;II)I
/*      */       //   259: istore #10
/*      */       //   261: iload #10
/*      */       //   263: ifne -> 274
/*      */       //   266: iload #9
/*      */       //   268: ifne -> 274
/*      */       //   271: goto -> 569
/*      */       //   274: iload #10
/*      */       //   276: iconst_2
/*      */       //   277: if_icmpge -> 284
/*      */       //   280: iload_3
/*      */       //   281: iconst_m1
/*      */       //   282: ixor
/*      */       //   283: ireturn
/*      */       //   284: aload_2
/*      */       //   285: iload_3
/*      */       //   286: invokestatic parseTwoDigits : (Ljava/lang/CharSequence;I)I
/*      */       //   289: istore #11
/*      */       //   291: iload #11
/*      */       //   293: bipush #59
/*      */       //   295: if_icmple -> 302
/*      */       //   298: iload_3
/*      */       //   299: iconst_m1
/*      */       //   300: ixor
/*      */       //   301: ireturn
/*      */       //   302: iload #7
/*      */       //   304: iload #11
/*      */       //   306: ldc 60000
/*      */       //   308: imul
/*      */       //   309: iadd
/*      */       //   310: istore #7
/*      */       //   312: iinc #4, -2
/*      */       //   315: iinc #3, 2
/*      */       //   318: iload #4
/*      */       //   320: ifgt -> 326
/*      */       //   323: goto -> 569
/*      */       //   326: iload #9
/*      */       //   328: ifeq -> 352
/*      */       //   331: aload_2
/*      */       //   332: iload_3
/*      */       //   333: invokeinterface charAt : (I)C
/*      */       //   338: bipush #58
/*      */       //   340: if_icmpeq -> 346
/*      */       //   343: goto -> 569
/*      */       //   346: iinc #4, -1
/*      */       //   349: iinc #3, 1
/*      */       //   352: aload_0
/*      */       //   353: aload_2
/*      */       //   354: iload_3
/*      */       //   355: iconst_2
/*      */       //   356: invokespecial digitCount : (Ljava/lang/CharSequence;II)I
/*      */       //   359: istore #10
/*      */       //   361: iload #10
/*      */       //   363: ifne -> 374
/*      */       //   366: iload #9
/*      */       //   368: ifne -> 374
/*      */       //   371: goto -> 569
/*      */       //   374: iload #10
/*      */       //   376: iconst_2
/*      */       //   377: if_icmpge -> 384
/*      */       //   380: iload_3
/*      */       //   381: iconst_m1
/*      */       //   382: ixor
/*      */       //   383: ireturn
/*      */       //   384: aload_2
/*      */       //   385: iload_3
/*      */       //   386: invokestatic parseTwoDigits : (Ljava/lang/CharSequence;I)I
/*      */       //   389: istore #12
/*      */       //   391: iload #12
/*      */       //   393: bipush #59
/*      */       //   395: if_icmple -> 402
/*      */       //   398: iload_3
/*      */       //   399: iconst_m1
/*      */       //   400: ixor
/*      */       //   401: ireturn
/*      */       //   402: iload #7
/*      */       //   404: iload #12
/*      */       //   406: sipush #1000
/*      */       //   409: imul
/*      */       //   410: iadd
/*      */       //   411: istore #7
/*      */       //   413: iinc #4, -2
/*      */       //   416: iinc #3, 2
/*      */       //   419: iload #4
/*      */       //   421: ifgt -> 427
/*      */       //   424: goto -> 569
/*      */       //   427: iload #9
/*      */       //   429: ifeq -> 465
/*      */       //   432: aload_2
/*      */       //   433: iload_3
/*      */       //   434: invokeinterface charAt : (I)C
/*      */       //   439: bipush #46
/*      */       //   441: if_icmpeq -> 459
/*      */       //   444: aload_2
/*      */       //   445: iload_3
/*      */       //   446: invokeinterface charAt : (I)C
/*      */       //   451: bipush #44
/*      */       //   453: if_icmpeq -> 459
/*      */       //   456: goto -> 569
/*      */       //   459: iinc #4, -1
/*      */       //   462: iinc #3, 1
/*      */       //   465: aload_0
/*      */       //   466: aload_2
/*      */       //   467: iload_3
/*      */       //   468: iconst_3
/*      */       //   469: invokespecial digitCount : (Ljava/lang/CharSequence;II)I
/*      */       //   472: istore #10
/*      */       //   474: iload #10
/*      */       //   476: ifne -> 487
/*      */       //   479: iload #9
/*      */       //   481: ifne -> 487
/*      */       //   484: goto -> 569
/*      */       //   487: iload #10
/*      */       //   489: iconst_1
/*      */       //   490: if_icmpge -> 497
/*      */       //   493: iload_3
/*      */       //   494: iconst_m1
/*      */       //   495: ixor
/*      */       //   496: ireturn
/*      */       //   497: iload #7
/*      */       //   499: aload_2
/*      */       //   500: iload_3
/*      */       //   501: iinc #3, 1
/*      */       //   504: invokeinterface charAt : (I)C
/*      */       //   509: bipush #48
/*      */       //   511: isub
/*      */       //   512: bipush #100
/*      */       //   514: imul
/*      */       //   515: iadd
/*      */       //   516: istore #7
/*      */       //   518: iload #10
/*      */       //   520: iconst_1
/*      */       //   521: if_icmple -> 569
/*      */       //   524: iload #7
/*      */       //   526: aload_2
/*      */       //   527: iload_3
/*      */       //   528: iinc #3, 1
/*      */       //   531: invokeinterface charAt : (I)C
/*      */       //   536: bipush #48
/*      */       //   538: isub
/*      */       //   539: bipush #10
/*      */       //   541: imul
/*      */       //   542: iadd
/*      */       //   543: istore #7
/*      */       //   545: iload #10
/*      */       //   547: iconst_2
/*      */       //   548: if_icmple -> 569
/*      */       //   551: iload #7
/*      */       //   553: aload_2
/*      */       //   554: iload_3
/*      */       //   555: iinc #3, 1
/*      */       //   558: invokeinterface charAt : (I)C
/*      */       //   563: bipush #48
/*      */       //   565: isub
/*      */       //   566: iadd
/*      */       //   567: istore #7
/*      */       //   569: aload_1
/*      */       //   570: iload #5
/*      */       //   572: ifeq -> 581
/*      */       //   575: iload #7
/*      */       //   577: ineg
/*      */       //   578: goto -> 583
/*      */       //   581: iload #7
/*      */       //   583: invokestatic valueOf : (I)Ljava/lang/Integer;
/*      */       //   586: invokevirtual setOffset : (Ljava/lang/Integer;)V
/*      */       //   589: iload_3
/*      */       //   590: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #2058	-> 0
/*      */       //   #2061	-> 10
/*      */       //   #2062	-> 17
/*      */       //   #2064	-> 27
/*      */       //   #2065	-> 32
/*      */       //   #2066	-> 41
/*      */       //   #2067	-> 55
/*      */       //   #2070	-> 58
/*      */       //   #2071	-> 66
/*      */       //   #2073	-> 68
/*      */       //   #2074	-> 80
/*      */       //   #2075	-> 88
/*      */       //   #2081	-> 98
/*      */       //   #2082	-> 104
/*      */       //   #2086	-> 108
/*      */       //   #2087	-> 117
/*      */       //   #2088	-> 124
/*      */       //   #2089	-> 130
/*      */       //   #2090	-> 137
/*      */       //   #2092	-> 143
/*      */       //   #2095	-> 147
/*      */       //   #2096	-> 150
/*      */       //   #2110	-> 153
/*      */       //   #2112	-> 164
/*      */       //   #2117	-> 168
/*      */       //   #2118	-> 175
/*      */       //   #2119	-> 182
/*      */       //   #2121	-> 186
/*      */       //   #2122	-> 193
/*      */       //   #2123	-> 196
/*      */       //   #2129	-> 199
/*      */       //   #2130	-> 204
/*      */       //   #2134	-> 207
/*      */       //   #2135	-> 216
/*      */       //   #2136	-> 223
/*      */       //   #2137	-> 226
/*      */       //   #2138	-> 229
/*      */       //   #2139	-> 235
/*      */       //   #2140	-> 249
/*      */       //   #2147	-> 252
/*      */       //   #2148	-> 261
/*      */       //   #2149	-> 271
/*      */       //   #2150	-> 274
/*      */       //   #2152	-> 280
/*      */       //   #2155	-> 284
/*      */       //   #2156	-> 291
/*      */       //   #2157	-> 298
/*      */       //   #2159	-> 302
/*      */       //   #2160	-> 312
/*      */       //   #2161	-> 315
/*      */       //   #2165	-> 318
/*      */       //   #2166	-> 323
/*      */       //   #2169	-> 326
/*      */       //   #2170	-> 331
/*      */       //   #2171	-> 343
/*      */       //   #2173	-> 346
/*      */       //   #2174	-> 349
/*      */       //   #2177	-> 352
/*      */       //   #2178	-> 361
/*      */       //   #2179	-> 371
/*      */       //   #2180	-> 374
/*      */       //   #2182	-> 380
/*      */       //   #2185	-> 384
/*      */       //   #2186	-> 391
/*      */       //   #2187	-> 398
/*      */       //   #2189	-> 402
/*      */       //   #2190	-> 413
/*      */       //   #2191	-> 416
/*      */       //   #2195	-> 419
/*      */       //   #2196	-> 424
/*      */       //   #2199	-> 427
/*      */       //   #2200	-> 432
/*      */       //   #2201	-> 456
/*      */       //   #2203	-> 459
/*      */       //   #2204	-> 462
/*      */       //   #2207	-> 465
/*      */       //   #2208	-> 474
/*      */       //   #2209	-> 484
/*      */       //   #2210	-> 487
/*      */       //   #2212	-> 493
/*      */       //   #2215	-> 497
/*      */       //   #2216	-> 518
/*      */       //   #2217	-> 524
/*      */       //   #2218	-> 545
/*      */       //   #2219	-> 551
/*      */       //   #2224	-> 569
/*      */       //   #2225	-> 589
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private int digitCount(CharSequence param1CharSequence, int param1Int1, int param1Int2) {
/* 2233 */       int i = Math.min(param1CharSequence.length() - param1Int1, param1Int2);
/* 2234 */       param1Int2 = 0;
/* 2235 */       for (; i > 0; i--) {
/* 2236 */         char c = param1CharSequence.charAt(param1Int1 + param1Int2);
/* 2237 */         if (c < '0' || c > '9') {
/*      */           break;
/*      */         }
/* 2240 */         param1Int2++;
/*      */       } 
/* 2242 */       return param1Int2;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static class TimeZoneName
/*      */     implements InternalPrinter, InternalParser
/*      */   {
/*      */     static final int LONG_NAME = 0;
/*      */     
/*      */     static final int SHORT_NAME = 1;
/*      */     
/*      */     private final Map<String, DateTimeZone> iParseLookup;
/*      */     private final int iType;
/*      */     
/*      */     TimeZoneName(int param1Int, Map<String, DateTimeZone> param1Map) {
/* 2258 */       this.iType = param1Int;
/* 2259 */       this.iParseLookup = param1Map;
/*      */     }
/*      */     
/*      */     public int estimatePrintedLength() {
/* 2263 */       return (this.iType == 1) ? 4 : 20;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void printTo(Appendable param1Appendable, long param1Long, Chronology param1Chronology, int param1Int, DateTimeZone param1DateTimeZone, Locale param1Locale) throws IOException {
/* 2269 */       param1Appendable.append(print(param1Long - param1Int, param1DateTimeZone, param1Locale));
/*      */     }
/*      */     
/*      */     private String print(long param1Long, DateTimeZone param1DateTimeZone, Locale param1Locale) {
/* 2273 */       if (param1DateTimeZone == null) {
/* 2274 */         return "";
/*      */       }
/* 2276 */       switch (this.iType) {
/*      */         case 0:
/* 2278 */           return param1DateTimeZone.getName(param1Long, param1Locale);
/*      */         case 1:
/* 2280 */           return param1DateTimeZone.getShortName(param1Long, param1Locale);
/*      */       } 
/* 2282 */       return "";
/*      */     }
/*      */ 
/*      */     
/*      */     public void printTo(Appendable param1Appendable, ReadablePartial param1ReadablePartial, Locale param1Locale) throws IOException {}
/*      */ 
/*      */     
/*      */     public int estimateParsedLength() {
/* 2290 */       return (this.iType == 1) ? 4 : 20;
/*      */     }
/*      */     
/*      */     public int parseInto(DateTimeParserBucket param1DateTimeParserBucket, CharSequence param1CharSequence, int param1Int) {
/* 2294 */       Map<String, DateTimeZone> map = this.iParseLookup;
/* 2295 */       map = (map != null) ? map : DateTimeUtils.getDefaultTimeZoneNames();
/* 2296 */       String str = null;
/* 2297 */       for (String str1 : map.keySet()) {
/* 2298 */         if (DateTimeFormatterBuilder.csStartsWith(param1CharSequence, param1Int, str1) && (
/* 2299 */           str == null || str1.length() > str.length())) {
/* 2300 */           str = str1;
/*      */         }
/*      */       } 
/*      */       
/* 2304 */       if (str != null) {
/* 2305 */         param1DateTimeParserBucket.setZone(map.get(str));
/* 2306 */         return param1Int + str.length();
/*      */       } 
/* 2308 */       return param1Int ^ 0xFFFFFFFF;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   enum TimeZoneId
/*      */     implements InternalPrinter, InternalParser
/*      */   {
/* 2316 */     INSTANCE;
/*      */     
/*      */     static final int MAX_LENGTH;
/* 2319 */     private static final List<String> ALL_IDS = new ArrayList<String>(DateTimeZone.getAvailableIDs()); static {
/* 2320 */       Collections.sort(ALL_IDS);
/*      */ 
/*      */ 
/*      */       
/* 2324 */       int i = 0;
/* 2325 */       for (String str : ALL_IDS) {
/* 2326 */         i = Math.max(i, str.length());
/*      */       }
/* 2328 */       MAX_LENGTH = i;
/*      */     }
/*      */     
/*      */     public int estimatePrintedLength() {
/* 2332 */       return MAX_LENGTH;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void printTo(Appendable param1Appendable, long param1Long, Chronology param1Chronology, int param1Int, DateTimeZone param1DateTimeZone, Locale param1Locale) throws IOException {
/* 2338 */       param1Appendable.append((param1DateTimeZone != null) ? param1DateTimeZone.getID() : "");
/*      */     }
/*      */ 
/*      */     
/*      */     public void printTo(Appendable param1Appendable, ReadablePartial param1ReadablePartial, Locale param1Locale) throws IOException {}
/*      */ 
/*      */     
/*      */     public int estimateParsedLength() {
/* 2346 */       return MAX_LENGTH;
/*      */     }
/*      */     
/*      */     public int parseInto(DateTimeParserBucket param1DateTimeParserBucket, CharSequence param1CharSequence, int param1Int) {
/* 2350 */       String str = null;
/* 2351 */       int i = prefixedStartPosition(param1CharSequence, param1Int);
/* 2352 */       for (int j = i; j < ALL_IDS.size(); ) {
/* 2353 */         String str1 = ALL_IDS.get(j);
/* 2354 */         if (DateTimeFormatterBuilder.csStartsWith(param1CharSequence, param1Int, str1)) {
/* 2355 */           if (str == null || str1.length() > str.length()) {
/* 2356 */             str = str1;
/*      */           }
/*      */           
/*      */           j++;
/*      */         } 
/*      */       } 
/* 2362 */       if (str != null) {
/* 2363 */         param1DateTimeParserBucket.setZone(DateTimeZone.forID(str));
/* 2364 */         return param1Int + str.length();
/*      */       } 
/* 2366 */       return param1Int ^ 0xFFFFFFFF;
/*      */     }
/*      */     
/*      */     private static int prefixedStartPosition(CharSequence param1CharSequence, int param1Int) {
/* 2370 */       int i = 0;
/* 2371 */       int j = ALL_IDS.size() - 1;
/*      */       
/* 2373 */       while (i <= j) {
/* 2374 */         int k = i + j >>> 1;
/* 2375 */         String str = ALL_IDS.get(k);
/* 2376 */         int m = DateTimeFormatterBuilder.csCompare(param1CharSequence, param1Int, str);
/* 2377 */         if (m > 0) {
/* 2378 */           j = k - 1; continue;
/* 2379 */         }  if (m < 0) {
/* 2380 */           i = k + 1; continue;
/*      */         } 
/* 2382 */         return k;
/*      */       } 
/*      */       
/* 2385 */       return i;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static class Composite
/*      */     implements InternalPrinter, InternalParser
/*      */   {
/*      */     private final InternalPrinter[] iPrinters;
/*      */     
/*      */     private final InternalParser[] iParsers;
/*      */     
/*      */     private final int iPrintedLengthEstimate;
/*      */     
/*      */     private final int iParsedLengthEstimate;
/*      */     
/*      */     Composite(List<Object> param1List) {
/* 2402 */       ArrayList<Object> arrayList1 = new ArrayList();
/* 2403 */       ArrayList<Object> arrayList2 = new ArrayList();
/*      */       
/* 2405 */       decompose(param1List, arrayList1, arrayList2);
/*      */       
/* 2407 */       if (arrayList1.contains(null) || arrayList1.isEmpty()) {
/* 2408 */         this.iPrinters = null;
/* 2409 */         this.iPrintedLengthEstimate = 0;
/*      */       } else {
/* 2411 */         int i = arrayList1.size();
/* 2412 */         this.iPrinters = new InternalPrinter[i];
/* 2413 */         int j = 0;
/* 2414 */         for (byte b = 0; b < i; b++) {
/* 2415 */           InternalPrinter internalPrinter = (InternalPrinter)arrayList1.get(b);
/* 2416 */           j += internalPrinter.estimatePrintedLength();
/* 2417 */           this.iPrinters[b] = internalPrinter;
/*      */         } 
/* 2419 */         this.iPrintedLengthEstimate = j;
/*      */       } 
/*      */       
/* 2422 */       if (arrayList2.contains(null) || arrayList2.isEmpty()) {
/* 2423 */         this.iParsers = null;
/* 2424 */         this.iParsedLengthEstimate = 0;
/*      */       } else {
/* 2426 */         int i = arrayList2.size();
/* 2427 */         this.iParsers = new InternalParser[i];
/* 2428 */         int j = 0;
/* 2429 */         for (byte b = 0; b < i; b++) {
/* 2430 */           InternalParser internalParser = (InternalParser)arrayList2.get(b);
/* 2431 */           j += internalParser.estimateParsedLength();
/* 2432 */           this.iParsers[b] = internalParser;
/*      */         } 
/* 2434 */         this.iParsedLengthEstimate = j;
/*      */       } 
/*      */     }
/*      */     
/*      */     public int estimatePrintedLength() {
/* 2439 */       return this.iPrintedLengthEstimate;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void printTo(Appendable param1Appendable, long param1Long, Chronology param1Chronology, int param1Int, DateTimeZone param1DateTimeZone, Locale param1Locale) throws IOException {
/* 2445 */       InternalPrinter[] arrayOfInternalPrinter = this.iPrinters;
/* 2446 */       if (arrayOfInternalPrinter == null) {
/* 2447 */         throw new UnsupportedOperationException();
/*      */       }
/*      */       
/* 2450 */       if (param1Locale == null)
/*      */       {
/* 2452 */         param1Locale = Locale.getDefault();
/*      */       }
/*      */       
/* 2455 */       int i = arrayOfInternalPrinter.length;
/* 2456 */       for (byte b = 0; b < i; b++) {
/* 2457 */         arrayOfInternalPrinter[b].printTo(param1Appendable, param1Long, param1Chronology, param1Int, param1DateTimeZone, param1Locale);
/*      */       }
/*      */     }
/*      */     
/*      */     public void printTo(Appendable param1Appendable, ReadablePartial param1ReadablePartial, Locale param1Locale) throws IOException {
/* 2462 */       InternalPrinter[] arrayOfInternalPrinter = this.iPrinters;
/* 2463 */       if (arrayOfInternalPrinter == null) {
/* 2464 */         throw new UnsupportedOperationException();
/*      */       }
/*      */       
/* 2467 */       if (param1Locale == null)
/*      */       {
/* 2469 */         param1Locale = Locale.getDefault();
/*      */       }
/*      */       
/* 2472 */       int i = arrayOfInternalPrinter.length;
/* 2473 */       for (byte b = 0; b < i; b++) {
/* 2474 */         arrayOfInternalPrinter[b].printTo(param1Appendable, param1ReadablePartial, param1Locale);
/*      */       }
/*      */     }
/*      */     
/*      */     public int estimateParsedLength() {
/* 2479 */       return this.iParsedLengthEstimate;
/*      */     }
/*      */     
/*      */     public int parseInto(DateTimeParserBucket param1DateTimeParserBucket, CharSequence param1CharSequence, int param1Int) {
/* 2483 */       InternalParser[] arrayOfInternalParser = this.iParsers;
/* 2484 */       if (arrayOfInternalParser == null) {
/* 2485 */         throw new UnsupportedOperationException();
/*      */       }
/*      */       
/* 2488 */       int i = arrayOfInternalParser.length;
/* 2489 */       for (byte b = 0; b < i && param1Int >= 0; b++) {
/* 2490 */         param1Int = arrayOfInternalParser[b].parseInto(param1DateTimeParserBucket, param1CharSequence, param1Int);
/*      */       }
/* 2492 */       return param1Int;
/*      */     }
/*      */     
/*      */     boolean isPrinter() {
/* 2496 */       return (this.iPrinters != null);
/*      */     }
/*      */     
/*      */     boolean isParser() {
/* 2500 */       return (this.iParsers != null);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void decompose(List<Object> param1List1, List<Object> param1List2, List<Object> param1List3) {
/* 2508 */       int i = param1List1.size();
/* 2509 */       for (byte b = 0; b < i; b += 2) {
/* 2510 */         Object object = param1List1.get(b);
/* 2511 */         if (object instanceof Composite) {
/* 2512 */           addArrayToList(param1List2, (Object[])((Composite)object).iPrinters);
/*      */         } else {
/* 2514 */           param1List2.add(object);
/*      */         } 
/*      */         
/* 2517 */         object = param1List1.get(b + 1);
/* 2518 */         if (object instanceof Composite) {
/* 2519 */           addArrayToList(param1List3, (Object[])((Composite)object).iParsers);
/*      */         } else {
/* 2521 */           param1List3.add(object);
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/*      */     private void addArrayToList(List<Object> param1List, Object[] param1ArrayOfObject) {
/* 2527 */       if (param1ArrayOfObject != null) {
/* 2528 */         for (byte b = 0; b < param1ArrayOfObject.length; b++) {
/* 2529 */           param1List.add(param1ArrayOfObject[b]);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static class MatchingParser
/*      */     implements InternalParser
/*      */   {
/*      */     private final InternalParser[] iParsers;
/*      */     
/*      */     private final int iParsedLengthEstimate;
/*      */     
/*      */     MatchingParser(InternalParser[] param1ArrayOfInternalParser) {
/* 2544 */       this.iParsers = param1ArrayOfInternalParser;
/* 2545 */       int i = 0;
/* 2546 */       for (int j = param1ArrayOfInternalParser.length; --j >= 0; ) {
/* 2547 */         InternalParser internalParser = param1ArrayOfInternalParser[j];
/* 2548 */         if (internalParser != null) {
/* 2549 */           int k = internalParser.estimateParsedLength();
/* 2550 */           if (k > i) {
/* 2551 */             i = k;
/*      */           }
/*      */         } 
/*      */       } 
/* 2555 */       this.iParsedLengthEstimate = i;
/*      */     }
/*      */     
/*      */     public int estimateParsedLength() {
/* 2559 */       return this.iParsedLengthEstimate;
/*      */     }
/*      */     
/*      */     public int parseInto(DateTimeParserBucket param1DateTimeParserBucket, CharSequence param1CharSequence, int param1Int) {
/* 2563 */       InternalParser[] arrayOfInternalParser = this.iParsers;
/* 2564 */       int i = arrayOfInternalParser.length;
/*      */       
/* 2566 */       Object object1 = param1DateTimeParserBucket.saveState();
/* 2567 */       boolean bool = false;
/*      */       
/* 2569 */       int j = param1Int;
/* 2570 */       Object object2 = null;
/*      */       
/* 2572 */       int k = param1Int;
/*      */       
/* 2574 */       for (byte b = 0; b < i; b++) {
/* 2575 */         InternalParser internalParser = arrayOfInternalParser[b];
/* 2576 */         if (internalParser == null) {
/*      */           
/* 2578 */           if (j <= param1Int) {
/* 2579 */             return param1Int;
/*      */           }
/* 2581 */           bool = true;
/*      */           break;
/*      */         } 
/* 2584 */         int m = internalParser.parseInto(param1DateTimeParserBucket, param1CharSequence, param1Int);
/* 2585 */         if (m >= param1Int) {
/* 2586 */           if (m > j) {
/* 2587 */             if (m >= param1CharSequence.length() || b + 1 >= i || arrayOfInternalParser[b + 1] == null)
/*      */             {
/*      */ 
/*      */ 
/*      */               
/* 2592 */               return m;
/*      */             }
/* 2594 */             j = m;
/* 2595 */             object2 = param1DateTimeParserBucket.saveState();
/*      */           }
/*      */         
/* 2598 */         } else if (m < 0) {
/* 2599 */           m ^= 0xFFFFFFFF;
/* 2600 */           if (m > k) {
/* 2601 */             k = m;
/*      */           }
/*      */         } 
/*      */         
/* 2605 */         param1DateTimeParserBucket.restoreState(object1);
/*      */       } 
/*      */       
/* 2608 */       if (j > param1Int || (j == param1Int && bool)) {
/*      */         
/* 2610 */         if (object2 != null) {
/* 2611 */           param1DateTimeParserBucket.restoreState(object2);
/*      */         }
/* 2613 */         return j;
/*      */       } 
/*      */       
/* 2616 */       return k ^ 0xFFFFFFFF;
/*      */     }
/*      */   }
/*      */   
/*      */   static int csCompare(CharSequence paramCharSequence, int paramInt, String paramString) {
/* 2621 */     int i = paramCharSequence.length() - paramInt;
/* 2622 */     int j = paramString.length();
/* 2623 */     int k = Math.min(i, j);
/* 2624 */     for (byte b = 0; b < k; b++) {
/* 2625 */       int m = paramString.charAt(b) - paramCharSequence.charAt(paramInt + b);
/* 2626 */       if (m != 0) {
/* 2627 */         return m;
/*      */       }
/*      */     } 
/* 2630 */     return j - i;
/*      */   }
/*      */   
/*      */   static boolean csStartsWith(CharSequence paramCharSequence, int paramInt, String paramString) {
/* 2634 */     int i = paramString.length();
/* 2635 */     if (paramCharSequence.length() - paramInt < i) {
/* 2636 */       return false;
/*      */     }
/* 2638 */     for (byte b = 0; b < i; b++) {
/* 2639 */       if (paramCharSequence.charAt(paramInt + b) != paramString.charAt(b)) {
/* 2640 */         return false;
/*      */       }
/*      */     } 
/* 2643 */     return true;
/*      */   }
/*      */   
/*      */   static boolean csStartsWithIgnoreCase(CharSequence paramCharSequence, int paramInt, String paramString) {
/* 2647 */     int i = paramString.length();
/* 2648 */     if (paramCharSequence.length() - paramInt < i) {
/* 2649 */       return false;
/*      */     }
/* 2651 */     for (byte b = 0; b < i; b++) {
/* 2652 */       char c1 = paramCharSequence.charAt(paramInt + b);
/* 2653 */       char c2 = paramString.charAt(b);
/* 2654 */       if (c1 != c2) {
/* 2655 */         char c3 = Character.toUpperCase(c1);
/* 2656 */         char c4 = Character.toUpperCase(c2);
/* 2657 */         if (c3 != c4 && Character.toLowerCase(c3) != Character.toLowerCase(c4)) {
/* 2658 */           return false;
/*      */         }
/*      */       } 
/*      */     } 
/* 2662 */     return true;
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/format/DateTimeFormatterBuilder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */