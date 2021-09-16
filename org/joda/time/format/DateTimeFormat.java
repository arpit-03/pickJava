/*     */ package org.joda.time.format;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.text.DateFormat;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Locale;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.atomic.AtomicReferenceArray;
/*     */ import org.joda.time.Chronology;
/*     */ import org.joda.time.DateTime;
/*     */ import org.joda.time.DateTimeZone;
/*     */ import org.joda.time.ReadablePartial;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DateTimeFormat
/*     */ {
/*     */   static final int FULL = 0;
/*     */   static final int LONG = 1;
/*     */   static final int MEDIUM = 2;
/*     */   static final int SHORT = 3;
/*     */   static final int NONE = 4;
/*     */   static final int DATE = 0;
/*     */   static final int TIME = 1;
/*     */   static final int DATETIME = 2;
/*     */   private static final int PATTERN_CACHE_SIZE = 500;
/* 154 */   private static final ConcurrentHashMap<String, DateTimeFormatter> cPatternCache = new ConcurrentHashMap<String, DateTimeFormatter>();
/*     */   
/* 156 */   private static final AtomicReferenceArray<DateTimeFormatter> cStyleCache = new AtomicReferenceArray<DateTimeFormatter>(25);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DateTimeFormatter forPattern(String paramString) {
/* 177 */     return createFormatterForPattern(paramString);
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
/*     */   
/*     */   public static DateTimeFormatter forStyle(String paramString) {
/* 201 */     return createFormatterForStyle(paramString);
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
/*     */   public static String patternForStyle(String paramString, Locale paramLocale) {
/* 219 */     DateTimeFormatter dateTimeFormatter = createFormatterForStyle(paramString);
/* 220 */     if (paramLocale == null) {
/* 221 */       paramLocale = Locale.getDefault();
/*     */     }
/*     */     
/* 224 */     return ((StyleFormatter)dateTimeFormatter.getPrinter0()).getPattern(paramLocale);
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
/*     */   public static DateTimeFormatter shortDate() {
/* 237 */     return createFormatterForStyleIndex(3, 4);
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
/*     */   public static DateTimeFormatter shortTime() {
/* 249 */     return createFormatterForStyleIndex(4, 3);
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
/*     */   public static DateTimeFormatter shortDateTime() {
/* 261 */     return createFormatterForStyleIndex(3, 3);
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
/*     */   public static DateTimeFormatter mediumDate() {
/* 274 */     return createFormatterForStyleIndex(2, 4);
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
/*     */   public static DateTimeFormatter mediumTime() {
/* 286 */     return createFormatterForStyleIndex(4, 2);
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
/*     */   public static DateTimeFormatter mediumDateTime() {
/* 298 */     return createFormatterForStyleIndex(2, 2);
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
/*     */   public static DateTimeFormatter longDate() {
/* 311 */     return createFormatterForStyleIndex(1, 4);
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
/*     */   public static DateTimeFormatter longTime() {
/* 323 */     return createFormatterForStyleIndex(4, 1);
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
/*     */   public static DateTimeFormatter longDateTime() {
/* 335 */     return createFormatterForStyleIndex(1, 1);
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
/*     */   public static DateTimeFormatter fullDate() {
/* 348 */     return createFormatterForStyleIndex(0, 4);
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
/*     */   public static DateTimeFormatter fullTime() {
/* 360 */     return createFormatterForStyleIndex(4, 0);
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
/*     */   public static DateTimeFormatter fullDateTime() {
/* 372 */     return createFormatterForStyleIndex(0, 0);
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
/*     */   static void appendPatternTo(DateTimeFormatterBuilder paramDateTimeFormatterBuilder, String paramString) {
/* 384 */     parsePatternTo(paramDateTimeFormatterBuilder, paramString);
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
/*     */   private static void parsePatternTo(DateTimeFormatterBuilder paramDateTimeFormatterBuilder, String paramString) {
/* 407 */     int i = paramString.length();
/* 408 */     int[] arrayOfInt = new int[1];
/*     */     
/* 410 */     for (int j = 0; j < i; j++) {
/* 411 */       int m; String str2; arrayOfInt[0] = j;
/* 412 */       String str1 = parseToken(paramString, arrayOfInt);
/* 413 */       j = arrayOfInt[0];
/*     */       
/* 415 */       int k = str1.length();
/* 416 */       if (k == 0) {
/*     */         break;
/*     */       }
/* 419 */       char c = str1.charAt(0);
/*     */       
/* 421 */       switch (c) {
/*     */         case 'G':
/* 423 */           paramDateTimeFormatterBuilder.appendEraText();
/*     */           break;
/*     */         case 'C':
/* 426 */           paramDateTimeFormatterBuilder.appendCenturyOfEra(k, k);
/*     */           break;
/*     */         case 'Y':
/*     */         case 'x':
/*     */         case 'y':
/* 431 */           if (k == 2) {
/* 432 */             boolean bool = true;
/*     */ 
/*     */             
/* 435 */             if (j + 1 < i) {
/* 436 */               arrayOfInt[0] = arrayOfInt[0] + 1;
/* 437 */               if (isNumericToken(parseToken(paramString, arrayOfInt)))
/*     */               {
/*     */ 
/*     */                 
/* 441 */                 bool = false;
/*     */               }
/* 443 */               arrayOfInt[0] = arrayOfInt[0] - 1;
/*     */             } 
/*     */ 
/*     */             
/* 447 */             switch (c) {
/*     */               case 'x':
/* 449 */                 paramDateTimeFormatterBuilder.appendTwoDigitWeekyear((new DateTime()).getWeekyear() - 30, bool);
/*     */                 break;
/*     */             } 
/*     */ 
/*     */ 
/*     */             
/* 455 */             paramDateTimeFormatterBuilder.appendTwoDigitYear((new DateTime()).getYear() - 30, bool);
/*     */             
/*     */             break;
/*     */           } 
/*     */           
/* 460 */           m = 9;
/*     */ 
/*     */           
/* 463 */           if (j + 1 < i) {
/* 464 */             arrayOfInt[0] = arrayOfInt[0] + 1;
/* 465 */             if (isNumericToken(parseToken(paramString, arrayOfInt)))
/*     */             {
/* 467 */               m = k;
/*     */             }
/* 469 */             arrayOfInt[0] = arrayOfInt[0] - 1;
/*     */           } 
/*     */           
/* 472 */           switch (c) {
/*     */             case 'x':
/* 474 */               paramDateTimeFormatterBuilder.appendWeekyear(k, m);
/*     */               break;
/*     */             case 'y':
/* 477 */               paramDateTimeFormatterBuilder.appendYear(k, m);
/*     */               break;
/*     */             case 'Y':
/* 480 */               paramDateTimeFormatterBuilder.appendYearOfEra(k, m);
/*     */               break;
/*     */           } 
/*     */           
/*     */           break;
/*     */         case 'M':
/* 486 */           if (k >= 3) {
/* 487 */             if (k >= 4) {
/* 488 */               paramDateTimeFormatterBuilder.appendMonthOfYearText(); break;
/*     */             } 
/* 490 */             paramDateTimeFormatterBuilder.appendMonthOfYearShortText();
/*     */             break;
/*     */           } 
/* 493 */           paramDateTimeFormatterBuilder.appendMonthOfYear(k);
/*     */           break;
/*     */         
/*     */         case 'd':
/* 497 */           paramDateTimeFormatterBuilder.appendDayOfMonth(k);
/*     */           break;
/*     */         case 'a':
/* 500 */           paramDateTimeFormatterBuilder.appendHalfdayOfDayText();
/*     */           break;
/*     */         case 'h':
/* 503 */           paramDateTimeFormatterBuilder.appendClockhourOfHalfday(k);
/*     */           break;
/*     */         case 'H':
/* 506 */           paramDateTimeFormatterBuilder.appendHourOfDay(k);
/*     */           break;
/*     */         case 'k':
/* 509 */           paramDateTimeFormatterBuilder.appendClockhourOfDay(k);
/*     */           break;
/*     */         case 'K':
/* 512 */           paramDateTimeFormatterBuilder.appendHourOfHalfday(k);
/*     */           break;
/*     */         case 'm':
/* 515 */           paramDateTimeFormatterBuilder.appendMinuteOfHour(k);
/*     */           break;
/*     */         case 's':
/* 518 */           paramDateTimeFormatterBuilder.appendSecondOfMinute(k);
/*     */           break;
/*     */         case 'S':
/* 521 */           paramDateTimeFormatterBuilder.appendFractionOfSecond(k, k);
/*     */           break;
/*     */         case 'e':
/* 524 */           paramDateTimeFormatterBuilder.appendDayOfWeek(k);
/*     */           break;
/*     */         case 'E':
/* 527 */           if (k >= 4) {
/* 528 */             paramDateTimeFormatterBuilder.appendDayOfWeekText(); break;
/*     */           } 
/* 530 */           paramDateTimeFormatterBuilder.appendDayOfWeekShortText();
/*     */           break;
/*     */         
/*     */         case 'D':
/* 534 */           paramDateTimeFormatterBuilder.appendDayOfYear(k);
/*     */           break;
/*     */         case 'w':
/* 537 */           paramDateTimeFormatterBuilder.appendWeekOfWeekyear(k);
/*     */           break;
/*     */         case 'z':
/* 540 */           if (k >= 4) {
/* 541 */             paramDateTimeFormatterBuilder.appendTimeZoneName(); break;
/*     */           } 
/* 543 */           paramDateTimeFormatterBuilder.appendTimeZoneShortName(null);
/*     */           break;
/*     */         
/*     */         case 'Z':
/* 547 */           if (k == 1) {
/* 548 */             paramDateTimeFormatterBuilder.appendTimeZoneOffset(null, "Z", false, 2, 2); break;
/* 549 */           }  if (k == 2) {
/* 550 */             paramDateTimeFormatterBuilder.appendTimeZoneOffset(null, "Z", true, 2, 2); break;
/*     */           } 
/* 552 */           paramDateTimeFormatterBuilder.appendTimeZoneId();
/*     */           break;
/*     */         
/*     */         case '\'':
/* 556 */           str2 = str1.substring(1);
/* 557 */           if (str2.length() == 1) {
/* 558 */             paramDateTimeFormatterBuilder.appendLiteral(str2.charAt(0));
/*     */             
/*     */             break;
/*     */           } 
/* 562 */           paramDateTimeFormatterBuilder.appendLiteral(new String(str2));
/*     */           break;
/*     */         
/*     */         default:
/* 566 */           throw new IllegalArgumentException("Illegal pattern component: " + str1);
/*     */       } 
/*     */     } 
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
/*     */   private static String parseToken(String paramString, int[] paramArrayOfint) {
/* 581 */     StringBuilder stringBuilder = new StringBuilder();
/*     */     
/* 583 */     int i = paramArrayOfint[0];
/* 584 */     int j = paramString.length();
/*     */     
/* 586 */     char c = paramString.charAt(i);
/* 587 */     if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z')) {
/*     */ 
/*     */       
/* 590 */       stringBuilder.append(c);
/*     */       
/* 592 */       while (i + 1 < j) {
/* 593 */         char c1 = paramString.charAt(i + 1);
/* 594 */         if (c1 == c) {
/* 595 */           stringBuilder.append(c);
/* 596 */           i++;
/*     */         }
/*     */       
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 603 */       stringBuilder.append('\'');
/*     */       
/* 605 */       boolean bool = false;
/*     */       
/* 607 */       for (; i < j; i++) {
/* 608 */         c = paramString.charAt(i);
/*     */         
/* 610 */         if (c == '\'')
/* 611 */         { if (i + 1 < j && paramString.charAt(i + 1) == '\'') {
/*     */             
/* 613 */             i++;
/* 614 */             stringBuilder.append(c);
/*     */           } else {
/* 616 */             bool = !bool ? true : false;
/*     */           }  }
/* 618 */         else { if (!bool && ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z'))) {
/*     */             
/* 620 */             i--;
/*     */             break;
/*     */           } 
/* 623 */           stringBuilder.append(c); }
/*     */       
/*     */       } 
/*     */     } 
/*     */     
/* 628 */     paramArrayOfint[0] = i;
/* 629 */     return stringBuilder.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isNumericToken(String paramString) {
/* 639 */     int i = paramString.length();
/* 640 */     if (i > 0) {
/* 641 */       char c = paramString.charAt(0);
/* 642 */       switch (c) {
/*     */         case 'C':
/*     */         case 'D':
/*     */         case 'F':
/*     */         case 'H':
/*     */         case 'K':
/*     */         case 'S':
/*     */         case 'W':
/*     */         case 'Y':
/*     */         case 'c':
/*     */         case 'd':
/*     */         case 'e':
/*     */         case 'h':
/*     */         case 'k':
/*     */         case 'm':
/*     */         case 's':
/*     */         case 'w':
/*     */         case 'x':
/*     */         case 'y':
/* 661 */           return true;
/*     */         case 'M':
/* 663 */           if (i <= 2) {
/* 664 */             return true;
/*     */           }
/*     */           break;
/*     */       } 
/*     */     } 
/* 669 */     return false;
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
/*     */   private static DateTimeFormatter createFormatterForPattern(String paramString) {
/* 681 */     if (paramString == null || paramString.length() == 0) {
/* 682 */       throw new IllegalArgumentException("Invalid pattern specification");
/*     */     }
/* 684 */     DateTimeFormatter dateTimeFormatter = cPatternCache.get(paramString);
/* 685 */     if (dateTimeFormatter == null) {
/* 686 */       DateTimeFormatterBuilder dateTimeFormatterBuilder = new DateTimeFormatterBuilder();
/* 687 */       parsePatternTo(dateTimeFormatterBuilder, paramString);
/* 688 */       dateTimeFormatter = dateTimeFormatterBuilder.toFormatter();
/* 689 */       if (cPatternCache.size() < 500) {
/*     */ 
/*     */         
/* 692 */         DateTimeFormatter dateTimeFormatter1 = cPatternCache.putIfAbsent(paramString, dateTimeFormatter);
/* 693 */         if (dateTimeFormatter1 != null) {
/* 694 */           dateTimeFormatter = dateTimeFormatter1;
/*     */         }
/*     */       } 
/*     */     } 
/* 698 */     return dateTimeFormatter;
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
/*     */   private static DateTimeFormatter createFormatterForStyle(String paramString) {
/* 711 */     if (paramString == null || paramString.length() != 2) {
/* 712 */       throw new IllegalArgumentException("Invalid style specification: " + paramString);
/*     */     }
/* 714 */     int i = selectStyle(paramString.charAt(0));
/* 715 */     int j = selectStyle(paramString.charAt(1));
/* 716 */     if (i == 4 && j == 4) {
/* 717 */       throw new IllegalArgumentException("Style '--' is invalid");
/*     */     }
/* 719 */     return createFormatterForStyleIndex(i, j);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static DateTimeFormatter createFormatterForStyleIndex(int paramInt1, int paramInt2) {
/* 730 */     int i = (paramInt1 << 2) + paramInt1 + paramInt2;
/*     */     
/* 732 */     if (i >= cStyleCache.length()) {
/* 733 */       return createDateTimeFormatter(paramInt1, paramInt2);
/*     */     }
/* 735 */     DateTimeFormatter dateTimeFormatter = cStyleCache.get(i);
/* 736 */     if (dateTimeFormatter == null) {
/* 737 */       dateTimeFormatter = createDateTimeFormatter(paramInt1, paramInt2);
/* 738 */       if (!cStyleCache.compareAndSet(i, null, dateTimeFormatter)) {
/* 739 */         dateTimeFormatter = cStyleCache.get(i);
/*     */       }
/*     */     } 
/* 742 */     return dateTimeFormatter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static DateTimeFormatter createDateTimeFormatter(int paramInt1, int paramInt2) {
/* 753 */     byte b = 2;
/* 754 */     if (paramInt1 == 4) {
/* 755 */       b = 1;
/* 756 */     } else if (paramInt2 == 4) {
/* 757 */       b = 0;
/*     */     } 
/* 759 */     StyleFormatter styleFormatter = new StyleFormatter(paramInt1, paramInt2, b);
/* 760 */     return new DateTimeFormatter(styleFormatter, styleFormatter);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int selectStyle(char paramChar) {
/* 770 */     switch (paramChar) {
/*     */       case 'S':
/* 772 */         return 3;
/*     */       case 'M':
/* 774 */         return 2;
/*     */       case 'L':
/* 776 */         return 1;
/*     */       case 'F':
/* 778 */         return 0;
/*     */       case '-':
/* 780 */         return 4;
/*     */     } 
/* 782 */     throw new IllegalArgumentException("Invalid style character: " + paramChar);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static class StyleFormatter
/*     */     implements InternalPrinter, InternalParser
/*     */   {
/* 790 */     private static final ConcurrentHashMap<DateTimeFormat.StyleFormatterCacheKey, DateTimeFormatter> cCache = new ConcurrentHashMap<DateTimeFormat.StyleFormatterCacheKey, DateTimeFormatter>();
/*     */     
/*     */     private final int iDateStyle;
/*     */     
/*     */     private final int iTimeStyle;
/*     */     private final int iType;
/*     */     
/*     */     StyleFormatter(int param1Int1, int param1Int2, int param1Int3) {
/* 798 */       this.iDateStyle = param1Int1;
/* 799 */       this.iTimeStyle = param1Int2;
/* 800 */       this.iType = param1Int3;
/*     */     }
/*     */     
/*     */     public int estimatePrintedLength() {
/* 804 */       return 40;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void printTo(Appendable param1Appendable, long param1Long, Chronology param1Chronology, int param1Int, DateTimeZone param1DateTimeZone, Locale param1Locale) throws IOException {
/* 810 */       InternalPrinter internalPrinter = getFormatter(param1Locale).getPrinter0();
/* 811 */       internalPrinter.printTo(param1Appendable, param1Long, param1Chronology, param1Int, param1DateTimeZone, param1Locale);
/*     */     }
/*     */     
/*     */     public void printTo(Appendable param1Appendable, ReadablePartial param1ReadablePartial, Locale param1Locale) throws IOException {
/* 815 */       InternalPrinter internalPrinter = getFormatter(param1Locale).getPrinter0();
/* 816 */       internalPrinter.printTo(param1Appendable, param1ReadablePartial, param1Locale);
/*     */     }
/*     */     
/*     */     public int estimateParsedLength() {
/* 820 */       return 40;
/*     */     }
/*     */     
/*     */     public int parseInto(DateTimeParserBucket param1DateTimeParserBucket, CharSequence param1CharSequence, int param1Int) {
/* 824 */       InternalParser internalParser = getFormatter(param1DateTimeParserBucket.getLocale()).getParser0();
/* 825 */       return internalParser.parseInto(param1DateTimeParserBucket, param1CharSequence, param1Int);
/*     */     }
/*     */     
/*     */     private DateTimeFormatter getFormatter(Locale param1Locale) {
/* 829 */       param1Locale = (param1Locale == null) ? Locale.getDefault() : param1Locale;
/* 830 */       DateTimeFormat.StyleFormatterCacheKey styleFormatterCacheKey = new DateTimeFormat.StyleFormatterCacheKey(this.iType, this.iDateStyle, this.iTimeStyle, param1Locale);
/* 831 */       DateTimeFormatter dateTimeFormatter = cCache.get(styleFormatterCacheKey);
/* 832 */       if (dateTimeFormatter == null) {
/* 833 */         dateTimeFormatter = DateTimeFormat.forPattern(getPattern(param1Locale));
/* 834 */         DateTimeFormatter dateTimeFormatter1 = cCache.putIfAbsent(styleFormatterCacheKey, dateTimeFormatter);
/* 835 */         if (dateTimeFormatter1 != null) {
/* 836 */           dateTimeFormatter = dateTimeFormatter1;
/*     */         }
/*     */       } 
/* 839 */       return dateTimeFormatter;
/*     */     }
/*     */     
/*     */     String getPattern(Locale param1Locale) {
/* 843 */       DateFormat dateFormat = null;
/* 844 */       switch (this.iType) {
/*     */         case 0:
/* 846 */           dateFormat = DateFormat.getDateInstance(this.iDateStyle, param1Locale);
/*     */           break;
/*     */         case 1:
/* 849 */           dateFormat = DateFormat.getTimeInstance(this.iTimeStyle, param1Locale);
/*     */           break;
/*     */         case 2:
/* 852 */           dateFormat = DateFormat.getDateTimeInstance(this.iDateStyle, this.iTimeStyle, param1Locale);
/*     */           break;
/*     */       } 
/* 855 */       if (!(dateFormat instanceof SimpleDateFormat)) {
/* 856 */         throw new IllegalArgumentException("No datetime pattern for locale: " + param1Locale);
/*     */       }
/* 858 */       return ((SimpleDateFormat)dateFormat).toPattern();
/*     */     }
/*     */   }
/*     */   
/*     */   static class StyleFormatterCacheKey
/*     */   {
/*     */     private final int combinedTypeAndStyle;
/*     */     private final Locale locale;
/*     */     
/*     */     public StyleFormatterCacheKey(int param1Int1, int param1Int2, int param1Int3, Locale param1Locale) {
/* 868 */       this.locale = param1Locale;
/*     */       
/* 870 */       this.combinedTypeAndStyle = param1Int1 + (param1Int2 << 4) + (param1Int3 << 8);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 876 */       int i = 1;
/* 877 */       i = 31 * i + this.combinedTypeAndStyle;
/* 878 */       i = 31 * i + ((this.locale == null) ? 0 : this.locale.hashCode());
/* 879 */       return i;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object param1Object) {
/* 884 */       if (this == param1Object) {
/* 885 */         return true;
/*     */       }
/* 887 */       if (param1Object == null) {
/* 888 */         return false;
/*     */       }
/* 890 */       if (!(param1Object instanceof StyleFormatterCacheKey)) {
/* 891 */         return false;
/*     */       }
/* 893 */       StyleFormatterCacheKey styleFormatterCacheKey = (StyleFormatterCacheKey)param1Object;
/* 894 */       if (this.combinedTypeAndStyle != styleFormatterCacheKey.combinedTypeAndStyle) {
/* 895 */         return false;
/*     */       }
/* 897 */       if (this.locale == null) {
/* 898 */         if (styleFormatterCacheKey.locale != null) {
/* 899 */           return false;
/*     */         }
/* 901 */       } else if (!this.locale.equals(styleFormatterCacheKey.locale)) {
/* 902 */         return false;
/*     */       } 
/* 904 */       return true;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/format/DateTimeFormat.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */