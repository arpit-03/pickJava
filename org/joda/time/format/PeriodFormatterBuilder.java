/*      */ package org.joda.time.format;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.Writer;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collections;
/*      */ import java.util.Comparator;
/*      */ import java.util.HashSet;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.Set;
/*      */ import java.util.TreeSet;
/*      */ import java.util.concurrent.ConcurrentHashMap;
/*      */ import java.util.concurrent.ConcurrentMap;
/*      */ import java.util.regex.Pattern;
/*      */ import org.joda.time.DurationFieldType;
/*      */ import org.joda.time.PeriodType;
/*      */ import org.joda.time.ReadWritablePeriod;
/*      */ import org.joda.time.ReadablePeriod;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class PeriodFormatterBuilder
/*      */ {
/*      */   private static final int PRINT_ZERO_RARELY_FIRST = 1;
/*      */   private static final int PRINT_ZERO_RARELY_LAST = 2;
/*      */   private static final int PRINT_ZERO_IF_SUPPORTED = 3;
/*      */   private static final int PRINT_ZERO_ALWAYS = 4;
/*      */   private static final int PRINT_ZERO_NEVER = 5;
/*      */   private static final int YEARS = 0;
/*      */   private static final int MONTHS = 1;
/*      */   private static final int WEEKS = 2;
/*      */   private static final int DAYS = 3;
/*      */   private static final int HOURS = 4;
/*      */   private static final int MINUTES = 5;
/*      */   private static final int SECONDS = 6;
/*      */   private static final int MILLIS = 7;
/*      */   private static final int SECONDS_MILLIS = 8;
/*      */   private static final int SECONDS_OPTIONAL_MILLIS = 9;
/*      */   private static final int MAX_FIELD = 9;
/*   91 */   private static final ConcurrentMap<String, Pattern> PATTERNS = new ConcurrentHashMap<String, Pattern>();
/*      */   
/*      */   private int iMinPrintedDigits;
/*      */   
/*      */   private int iPrintZeroSetting;
/*      */   
/*      */   private int iMaxParsedDigits;
/*      */   
/*      */   private boolean iRejectSignedValues;
/*      */   
/*      */   private PeriodFieldAffix iPrefix;
/*      */   
/*      */   private List<Object> iElementPairs;
/*      */   
/*      */   private boolean iNotPrinter;
/*      */   
/*      */   private boolean iNotParser;
/*      */   private FieldFormatter[] iFieldFormatters;
/*      */   
/*      */   public PeriodFormatterBuilder() {
/*  111 */     clear();
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
/*      */   public PeriodFormatter toFormatter() {
/*  132 */     PeriodFormatter periodFormatter = toFormatter(this.iElementPairs, this.iNotPrinter, this.iNotParser);
/*  133 */     for (FieldFormatter fieldFormatter : this.iFieldFormatters) {
/*  134 */       if (fieldFormatter != null) {
/*  135 */         fieldFormatter.finish(this.iFieldFormatters);
/*      */       }
/*      */     } 
/*  138 */     this.iFieldFormatters = (FieldFormatter[])this.iFieldFormatters.clone();
/*  139 */     return periodFormatter;
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
/*      */   public PeriodPrinter toPrinter() {
/*  155 */     if (this.iNotPrinter) {
/*  156 */       return null;
/*      */     }
/*  158 */     return toFormatter().getPrinter();
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
/*      */   public PeriodParser toParser() {
/*  174 */     if (this.iNotParser) {
/*  175 */       return null;
/*      */     }
/*  177 */     return toFormatter().getParser();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clear() {
/*  185 */     this.iMinPrintedDigits = 1;
/*  186 */     this.iPrintZeroSetting = 2;
/*  187 */     this.iMaxParsedDigits = 10;
/*  188 */     this.iRejectSignedValues = false;
/*  189 */     this.iPrefix = null;
/*  190 */     if (this.iElementPairs == null) {
/*  191 */       this.iElementPairs = new ArrayList();
/*      */     } else {
/*  193 */       this.iElementPairs.clear();
/*      */     } 
/*  195 */     this.iNotPrinter = false;
/*  196 */     this.iNotParser = false;
/*  197 */     this.iFieldFormatters = new FieldFormatter[10];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PeriodFormatterBuilder append(PeriodFormatter paramPeriodFormatter) {
/*  206 */     if (paramPeriodFormatter == null) {
/*  207 */       throw new IllegalArgumentException("No formatter supplied");
/*      */     }
/*  209 */     clearPrefix();
/*  210 */     append0(paramPeriodFormatter.getPrinter(), paramPeriodFormatter.getParser());
/*  211 */     return this;
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
/*      */   public PeriodFormatterBuilder append(PeriodPrinter paramPeriodPrinter, PeriodParser paramPeriodParser) {
/*  226 */     if (paramPeriodPrinter == null && paramPeriodParser == null) {
/*  227 */       throw new IllegalArgumentException("No printer or parser supplied");
/*      */     }
/*  229 */     clearPrefix();
/*  230 */     append0(paramPeriodPrinter, paramPeriodParser);
/*  231 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PeriodFormatterBuilder appendLiteral(String paramString) {
/*  242 */     if (paramString == null) {
/*  243 */       throw new IllegalArgumentException("Literal must not be null");
/*      */     }
/*  245 */     clearPrefix();
/*  246 */     Literal literal = new Literal(paramString);
/*  247 */     append0(literal, literal);
/*  248 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PeriodFormatterBuilder minimumPrintedDigits(int paramInt) {
/*  259 */     this.iMinPrintedDigits = paramInt;
/*  260 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PeriodFormatterBuilder maximumParsedDigits(int paramInt) {
/*  270 */     this.iMaxParsedDigits = paramInt;
/*  271 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PeriodFormatterBuilder rejectSignedValues(boolean paramBoolean) {
/*  280 */     this.iRejectSignedValues = paramBoolean;
/*  281 */     return this;
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
/*      */   public PeriodFormatterBuilder printZeroRarelyLast() {
/*  294 */     this.iPrintZeroSetting = 2;
/*  295 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PeriodFormatterBuilder printZeroRarelyFirst() {
/*  306 */     this.iPrintZeroSetting = 1;
/*  307 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PeriodFormatterBuilder printZeroIfSupported() {
/*  317 */     this.iPrintZeroSetting = 3;
/*  318 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PeriodFormatterBuilder printZeroAlways() {
/*  329 */     this.iPrintZeroSetting = 4;
/*  330 */     return this;
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
/*      */   public PeriodFormatterBuilder printZeroNever() {
/*  343 */     this.iPrintZeroSetting = 5;
/*  344 */     return this;
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
/*      */   public PeriodFormatterBuilder appendPrefix(String paramString) {
/*  357 */     if (paramString == null) {
/*  358 */       throw new IllegalArgumentException();
/*      */     }
/*  360 */     return appendPrefix(new SimpleAffix(paramString));
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
/*      */   public PeriodFormatterBuilder appendPrefix(String paramString1, String paramString2) {
/*  377 */     if (paramString1 == null || paramString2 == null) {
/*  378 */       throw new IllegalArgumentException();
/*      */     }
/*  380 */     return appendPrefix(new PluralAffix(paramString1, paramString2));
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
/*      */   public PeriodFormatterBuilder appendPrefix(String[] paramArrayOfString1, String[] paramArrayOfString2) {
/*  417 */     if (paramArrayOfString1 == null || paramArrayOfString2 == null || paramArrayOfString1.length < 1 || paramArrayOfString1.length != paramArrayOfString2.length)
/*      */     {
/*  419 */       throw new IllegalArgumentException();
/*      */     }
/*  421 */     return appendPrefix(new RegExAffix(paramArrayOfString1, paramArrayOfString2));
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
/*      */   private PeriodFormatterBuilder appendPrefix(PeriodFieldAffix paramPeriodFieldAffix) {
/*  433 */     if (paramPeriodFieldAffix == null) {
/*  434 */       throw new IllegalArgumentException();
/*      */     }
/*  436 */     if (this.iPrefix != null) {
/*  437 */       paramPeriodFieldAffix = new CompositeAffix(this.iPrefix, paramPeriodFieldAffix);
/*      */     }
/*  439 */     this.iPrefix = paramPeriodFieldAffix;
/*  440 */     return this;
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
/*      */   public PeriodFormatterBuilder appendYears() {
/*  453 */     appendField(0);
/*  454 */     return this;
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
/*      */   public PeriodFormatterBuilder appendMonths() {
/*  466 */     appendField(1);
/*  467 */     return this;
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
/*      */   public PeriodFormatterBuilder appendWeeks() {
/*  479 */     appendField(2);
/*  480 */     return this;
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
/*      */   public PeriodFormatterBuilder appendDays() {
/*  492 */     appendField(3);
/*  493 */     return this;
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
/*      */   public PeriodFormatterBuilder appendHours() {
/*  505 */     appendField(4);
/*  506 */     return this;
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
/*      */   public PeriodFormatterBuilder appendMinutes() {
/*  518 */     appendField(5);
/*  519 */     return this;
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
/*      */   public PeriodFormatterBuilder appendSeconds() {
/*  531 */     appendField(6);
/*  532 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PeriodFormatterBuilder appendSecondsWithMillis() {
/*  543 */     appendField(8);
/*  544 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PeriodFormatterBuilder appendSecondsWithOptionalMillis() {
/*  555 */     appendField(9);
/*  556 */     return this;
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
/*      */   public PeriodFormatterBuilder appendMillis() {
/*  568 */     appendField(7);
/*  569 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PeriodFormatterBuilder appendMillis3Digit() {
/*  580 */     appendField(7, 3);
/*  581 */     return this;
/*      */   }
/*      */   
/*      */   private void appendField(int paramInt) {
/*  585 */     appendField(paramInt, this.iMinPrintedDigits);
/*      */   }
/*      */   
/*      */   private void appendField(int paramInt1, int paramInt2) {
/*  589 */     FieldFormatter fieldFormatter = new FieldFormatter(paramInt2, this.iPrintZeroSetting, this.iMaxParsedDigits, this.iRejectSignedValues, paramInt1, this.iFieldFormatters, this.iPrefix, null);
/*      */     
/*  591 */     append0(fieldFormatter, fieldFormatter);
/*  592 */     this.iFieldFormatters[paramInt1] = fieldFormatter;
/*  593 */     this.iPrefix = null;
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
/*      */   public PeriodFormatterBuilder appendSuffix(String paramString) {
/*  607 */     if (paramString == null) {
/*  608 */       throw new IllegalArgumentException();
/*      */     }
/*  610 */     return appendSuffix(new SimpleAffix(paramString));
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
/*      */   public PeriodFormatterBuilder appendSuffix(String paramString1, String paramString2) {
/*  628 */     if (paramString1 == null || paramString2 == null) {
/*  629 */       throw new IllegalArgumentException();
/*      */     }
/*  631 */     return appendSuffix(new PluralAffix(paramString1, paramString2));
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
/*      */   public PeriodFormatterBuilder appendSuffix(String[] paramArrayOfString1, String[] paramArrayOfString2) {
/*  668 */     if (paramArrayOfString1 == null || paramArrayOfString2 == null || paramArrayOfString1.length < 1 || paramArrayOfString1.length != paramArrayOfString2.length)
/*      */     {
/*  670 */       throw new IllegalArgumentException();
/*      */     }
/*  672 */     return appendSuffix(new RegExAffix(paramArrayOfString1, paramArrayOfString2));
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
/*      */   private PeriodFormatterBuilder appendSuffix(PeriodFieldAffix paramPeriodFieldAffix) {
/*      */     FieldFormatter fieldFormatter1;
/*      */     Object object;
/*  687 */     if (this.iElementPairs.size() > 0) {
/*  688 */       fieldFormatter1 = (FieldFormatter)this.iElementPairs.get(this.iElementPairs.size() - 2);
/*  689 */       object = this.iElementPairs.get(this.iElementPairs.size() - 1);
/*      */     } else {
/*  691 */       fieldFormatter1 = null;
/*  692 */       object = null;
/*      */     } 
/*      */     
/*  695 */     if (fieldFormatter1 == null || object == null || fieldFormatter1 != object || !(fieldFormatter1 instanceof FieldFormatter))
/*      */     {
/*      */       
/*  698 */       throw new IllegalStateException("No field to apply suffix to");
/*      */     }
/*      */     
/*  701 */     clearPrefix();
/*  702 */     FieldFormatter fieldFormatter2 = new FieldFormatter(fieldFormatter1, paramPeriodFieldAffix);
/*  703 */     this.iElementPairs.set(this.iElementPairs.size() - 2, fieldFormatter2);
/*  704 */     this.iElementPairs.set(this.iElementPairs.size() - 1, fieldFormatter2);
/*  705 */     this.iFieldFormatters[fieldFormatter2.getFieldType()] = fieldFormatter2;
/*      */     
/*  707 */     return this;
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
/*      */   public PeriodFormatterBuilder appendSeparator(String paramString) {
/*  728 */     return appendSeparator(paramString, paramString, null, true, true);
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
/*      */   public PeriodFormatterBuilder appendSeparatorIfFieldsAfter(String paramString) {
/*  748 */     return appendSeparator(paramString, paramString, null, false, true);
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
/*      */   public PeriodFormatterBuilder appendSeparatorIfFieldsBefore(String paramString) {
/*  768 */     return appendSeparator(paramString, paramString, null, true, false);
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
/*      */   public PeriodFormatterBuilder appendSeparator(String paramString1, String paramString2) {
/*  793 */     return appendSeparator(paramString1, paramString2, null, true, true);
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
/*      */   public PeriodFormatterBuilder appendSeparator(String paramString1, String paramString2, String[] paramArrayOfString) {
/*  820 */     return appendSeparator(paramString1, paramString2, paramArrayOfString, true, true);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private PeriodFormatterBuilder appendSeparator(String paramString1, String paramString2, String[] paramArrayOfString, boolean paramBoolean1, boolean paramBoolean2) {
/*  826 */     if (paramString1 == null || paramString2 == null) {
/*  827 */       throw new IllegalArgumentException();
/*      */     }
/*      */     
/*  830 */     clearPrefix();
/*      */ 
/*      */     
/*  833 */     List<Object> list = this.iElementPairs;
/*  834 */     if (list.size() == 0) {
/*  835 */       if (paramBoolean2 && !paramBoolean1) {
/*  836 */         Separator separator = new Separator(paramString1, paramString2, paramArrayOfString, Literal.EMPTY, Literal.EMPTY, paramBoolean1, paramBoolean2);
/*      */ 
/*      */         
/*  839 */         append0(separator, separator);
/*      */       } 
/*  841 */       return this;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  846 */     Separator separator1 = null;
/*  847 */     for (int i = list.size(); --i >= 0; ) {
/*  848 */       if (list.get(i) instanceof Separator) {
/*  849 */         separator1 = (Separator)list.get(i);
/*  850 */         list = list.subList(i + 1, list.size());
/*      */         break;
/*      */       } 
/*  853 */       i--;
/*      */     } 
/*      */ 
/*      */     
/*  857 */     if (separator1 != null && list.size() == 0) {
/*  858 */       throw new IllegalStateException("Cannot have two adjacent separators");
/*      */     }
/*  860 */     Object[] arrayOfObject = createComposite(list);
/*  861 */     list.clear();
/*  862 */     Separator separator2 = new Separator(paramString1, paramString2, paramArrayOfString, (PeriodPrinter)arrayOfObject[0], (PeriodParser)arrayOfObject[1], paramBoolean1, paramBoolean2);
/*      */ 
/*      */ 
/*      */     
/*  866 */     list.add(separator2);
/*  867 */     list.add(separator2);
/*      */ 
/*      */     
/*  870 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   private void clearPrefix() throws IllegalStateException {
/*  875 */     if (this.iPrefix != null) {
/*  876 */       throw new IllegalStateException("Prefix not followed by field");
/*      */     }
/*  878 */     this.iPrefix = null;
/*      */   }
/*      */   
/*      */   private PeriodFormatterBuilder append0(PeriodPrinter paramPeriodPrinter, PeriodParser paramPeriodParser) {
/*  882 */     this.iElementPairs.add(paramPeriodPrinter);
/*  883 */     this.iElementPairs.add(paramPeriodParser);
/*  884 */     this.iNotPrinter |= (paramPeriodPrinter == null) ? 1 : 0;
/*  885 */     this.iNotParser |= (paramPeriodParser == null) ? 1 : 0;
/*  886 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   private static PeriodFormatter toFormatter(List<Object> paramList, boolean paramBoolean1, boolean paramBoolean2) {
/*  891 */     if (paramBoolean1 && paramBoolean2) {
/*  892 */       throw new IllegalStateException("Builder has created neither a printer nor a parser");
/*      */     }
/*  894 */     int i = paramList.size();
/*  895 */     if (i >= 2 && paramList.get(0) instanceof Separator) {
/*  896 */       Separator separator = (Separator)paramList.get(0);
/*  897 */       if (separator.iAfterParser == null && separator.iAfterPrinter == null) {
/*  898 */         PeriodFormatter periodFormatter = toFormatter(paramList.subList(2, i), paramBoolean1, paramBoolean2);
/*  899 */         separator = separator.finish(periodFormatter.getPrinter(), periodFormatter.getParser());
/*  900 */         return new PeriodFormatter(separator, separator);
/*      */       } 
/*      */     } 
/*  903 */     Object[] arrayOfObject = createComposite(paramList);
/*  904 */     if (paramBoolean1)
/*  905 */       return new PeriodFormatter(null, (PeriodParser)arrayOfObject[1]); 
/*  906 */     if (paramBoolean2) {
/*  907 */       return new PeriodFormatter((PeriodPrinter)arrayOfObject[0], null);
/*      */     }
/*  909 */     return new PeriodFormatter((PeriodPrinter)arrayOfObject[0], (PeriodParser)arrayOfObject[1]);
/*      */   }
/*      */ 
/*      */   
/*      */   private static Object[] createComposite(List<Object> paramList) {
/*  914 */     switch (paramList.size()) {
/*      */       case 0:
/*  916 */         return new Object[] { Literal.EMPTY, Literal.EMPTY };
/*      */       case 1:
/*  918 */         return new Object[] { paramList.get(0), paramList.get(1) };
/*      */     } 
/*  920 */     Composite composite = new Composite(paramList);
/*  921 */     return new Object[] { composite, composite };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static interface PeriodFieldAffix
/*      */   {
/*      */     int calculatePrintedLength(int param1Int);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void printTo(StringBuffer param1StringBuffer, int param1Int);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void printTo(Writer param1Writer, int param1Int) throws IOException;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     int parse(String param1String, int param1Int);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     int scan(String param1String, int param1Int);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     String[] getAffixes();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void finish(Set<PeriodFieldAffix> param1Set);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static abstract class IgnorableAffix
/*      */     implements PeriodFieldAffix
/*      */   {
/*      */     private volatile String[] iOtherAffixes;
/*      */ 
/*      */ 
/*      */     
/*      */     public void finish(Set<PeriodFormatterBuilder.PeriodFieldAffix> param1Set) {
/*  973 */       if (this.iOtherAffixes == null) {
/*      */         
/*  975 */         int i = Integer.MAX_VALUE;
/*  976 */         String str = null;
/*  977 */         for (String str1 : getAffixes()) {
/*  978 */           if (str1.length() < i) {
/*  979 */             i = str1.length();
/*  980 */             str = str1;
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  987 */         HashSet<String> hashSet = new HashSet();
/*  988 */         for (PeriodFormatterBuilder.PeriodFieldAffix periodFieldAffix : param1Set) {
/*  989 */           if (periodFieldAffix != null) {
/*  990 */             for (String str1 : periodFieldAffix.getAffixes()) {
/*  991 */               if (str1.length() > i || (str1.equalsIgnoreCase(str) && !str1.equals(str)))
/*      */               {
/*  993 */                 hashSet.add(str1);
/*      */               }
/*      */             } 
/*      */           }
/*      */         } 
/*  998 */         this.iOtherAffixes = hashSet.<String>toArray(new String[hashSet.size()]);
/*      */       } 
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
/*      */     protected boolean matchesOtherAffix(int param1Int1, String param1String, int param1Int2) {
/* 1013 */       if (this.iOtherAffixes != null)
/*      */       {
/*      */         
/* 1016 */         for (String str : this.iOtherAffixes) {
/* 1017 */           int i = str.length();
/* 1018 */           if ((param1Int1 < i && param1String.regionMatches(true, param1Int2, str, 0, i)) || (param1Int1 == i && param1String.regionMatches(false, param1Int2, str, 0, i)))
/*      */           {
/* 1020 */             return true;
/*      */           }
/*      */         } 
/*      */       }
/* 1024 */       return false;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static class SimpleAffix
/*      */     extends IgnorableAffix
/*      */   {
/*      */     private final String iText;
/*      */ 
/*      */     
/*      */     SimpleAffix(String param1String) {
/* 1036 */       this.iText = param1String;
/*      */     }
/*      */     
/*      */     public int calculatePrintedLength(int param1Int) {
/* 1040 */       return this.iText.length();
/*      */     }
/*      */     
/*      */     public void printTo(StringBuffer param1StringBuffer, int param1Int) {
/* 1044 */       param1StringBuffer.append(this.iText);
/*      */     }
/*      */     
/*      */     public void printTo(Writer param1Writer, int param1Int) throws IOException {
/* 1048 */       param1Writer.write(this.iText);
/*      */     }
/*      */     
/*      */     public int parse(String param1String, int param1Int) {
/* 1052 */       String str = this.iText;
/* 1053 */       int i = str.length();
/* 1054 */       if (param1String.regionMatches(true, param1Int, str, 0, i) && 
/* 1055 */         !matchesOtherAffix(i, param1String, param1Int)) {
/* 1056 */         return param1Int + i;
/*      */       }
/*      */       
/* 1059 */       return param1Int ^ 0xFFFFFFFF;
/*      */     }
/*      */     
/*      */     public int scan(String param1String, int param1Int) {
/* 1063 */       String str = this.iText;
/* 1064 */       int i = str.length();
/* 1065 */       int j = param1String.length();
/*      */       
/* 1067 */       for (int k = param1Int; k < j; k++)
/* 1068 */       { if (param1String.regionMatches(true, k, str, 0, i) && 
/* 1069 */           !matchesOtherAffix(i, param1String, k)) {
/* 1070 */           return k;
/*      */         }
/*      */ 
/*      */         
/* 1074 */         switch (param1String.charAt(k)) { case '+': case ',': case '-': case '.': case '0': case '1': case '2': case '3':
/*      */           case '4':
/*      */           case '5':
/*      */           case '6':
/*      */           case '7':
/*      */           case '8':
/*      */           case '9':
/*      */             break;
/*      */           default:
/* 1083 */             break; }  }  return param1Int ^ 0xFFFFFFFF;
/*      */     }
/*      */     
/*      */     public String[] getAffixes() {
/* 1087 */       return new String[] { this.iText };
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static class PluralAffix
/*      */     extends IgnorableAffix
/*      */   {
/*      */     private final String iSingularText;
/*      */     
/*      */     private final String iPluralText;
/*      */ 
/*      */     
/*      */     PluralAffix(String param1String1, String param1String2) {
/* 1101 */       this.iSingularText = param1String1;
/* 1102 */       this.iPluralText = param1String2;
/*      */     }
/*      */     
/*      */     public int calculatePrintedLength(int param1Int) {
/* 1106 */       return ((param1Int == 1) ? this.iSingularText : this.iPluralText).length();
/*      */     }
/*      */     
/*      */     public void printTo(StringBuffer param1StringBuffer, int param1Int) {
/* 1110 */       param1StringBuffer.append((param1Int == 1) ? this.iSingularText : this.iPluralText);
/*      */     }
/*      */     
/*      */     public void printTo(Writer param1Writer, int param1Int) throws IOException {
/* 1114 */       param1Writer.write((param1Int == 1) ? this.iSingularText : this.iPluralText);
/*      */     }
/*      */     
/*      */     public int parse(String param1String, int param1Int) {
/* 1118 */       String str1 = this.iPluralText;
/* 1119 */       String str2 = this.iSingularText;
/*      */       
/* 1121 */       if (str1.length() < str2.length()) {
/*      */         
/* 1123 */         String str = str1;
/* 1124 */         str1 = str2;
/* 1125 */         str2 = str;
/*      */       } 
/*      */       
/* 1128 */       if (param1String.regionMatches(true, param1Int, str1, 0, str1.length()) && 
/* 1129 */         !matchesOtherAffix(str1.length(), param1String, param1Int)) {
/* 1130 */         return param1Int + str1.length();
/*      */       }
/*      */       
/* 1133 */       if (param1String.regionMatches(true, param1Int, str2, 0, str2.length()) && 
/* 1134 */         !matchesOtherAffix(str2.length(), param1String, param1Int)) {
/* 1135 */         return param1Int + str2.length();
/*      */       }
/*      */ 
/*      */       
/* 1139 */       return param1Int ^ 0xFFFFFFFF;
/*      */     }
/*      */     
/*      */     public int scan(String param1String, int param1Int) {
/* 1143 */       String str1 = this.iPluralText;
/* 1144 */       String str2 = this.iSingularText;
/*      */       
/* 1146 */       if (str1.length() < str2.length()) {
/*      */         
/* 1148 */         String str = str1;
/* 1149 */         str1 = str2;
/* 1150 */         str2 = str;
/*      */       } 
/*      */       
/* 1153 */       int i = str1.length();
/* 1154 */       int j = str2.length();
/*      */       
/* 1156 */       int k = param1String.length();
/* 1157 */       for (int m = param1Int; m < k; m++) {
/* 1158 */         if (param1String.regionMatches(true, m, str1, 0, i) && 
/* 1159 */           !matchesOtherAffix(str1.length(), param1String, m)) {
/* 1160 */           return m;
/*      */         }
/*      */         
/* 1163 */         if (param1String.regionMatches(true, m, str2, 0, j) && 
/* 1164 */           !matchesOtherAffix(str2.length(), param1String, m)) {
/* 1165 */           return m;
/*      */         }
/*      */       } 
/*      */       
/* 1169 */       return param1Int ^ 0xFFFFFFFF;
/*      */     }
/*      */     
/*      */     public String[] getAffixes() {
/* 1173 */       return new String[] { this.iSingularText, this.iPluralText };
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class RegExAffix
/*      */     extends IgnorableAffix
/*      */   {
/* 1183 */     private static final Comparator<String> LENGTH_DESC_COMPARATOR = new Comparator<String>() {
/*      */         public int compare(String param2String1, String param2String2) {
/* 1185 */           return param2String2.length() - param2String1.length();
/*      */         }
/*      */       };
/*      */ 
/*      */     
/*      */     private final String[] iSuffixes;
/*      */     
/*      */     private final Pattern[] iPatterns;
/*      */     
/*      */     private final String[] iSuffixesSortedDescByLength;
/*      */     
/*      */     RegExAffix(String[] param1ArrayOfString1, String[] param1ArrayOfString2) {
/* 1197 */       this.iSuffixes = (String[])param1ArrayOfString2.clone();
/* 1198 */       this.iPatterns = new Pattern[param1ArrayOfString1.length];
/* 1199 */       for (byte b = 0; b < param1ArrayOfString1.length; b++) {
/* 1200 */         Pattern pattern = (Pattern)PeriodFormatterBuilder.PATTERNS.get(param1ArrayOfString1[b]);
/* 1201 */         if (pattern == null) {
/* 1202 */           pattern = Pattern.compile(param1ArrayOfString1[b]);
/* 1203 */           PeriodFormatterBuilder.PATTERNS.putIfAbsent(param1ArrayOfString1[b], pattern);
/*      */         } 
/* 1205 */         this.iPatterns[b] = pattern;
/*      */       } 
/* 1207 */       this.iSuffixesSortedDescByLength = (String[])this.iSuffixes.clone();
/* 1208 */       Arrays.sort(this.iSuffixesSortedDescByLength, LENGTH_DESC_COMPARATOR);
/*      */     }
/*      */     
/*      */     private int selectSuffixIndex(int param1Int) {
/* 1212 */       String str = String.valueOf(param1Int);
/* 1213 */       for (byte b = 0; b < this.iPatterns.length; b++) {
/* 1214 */         if (this.iPatterns[b].matcher(str).matches()) {
/* 1215 */           return b;
/*      */         }
/*      */       } 
/* 1218 */       return this.iPatterns.length - 1;
/*      */     }
/*      */     
/*      */     public int calculatePrintedLength(int param1Int) {
/* 1222 */       return this.iSuffixes[selectSuffixIndex(param1Int)].length();
/*      */     }
/*      */     
/*      */     public void printTo(StringBuffer param1StringBuffer, int param1Int) {
/* 1226 */       param1StringBuffer.append(this.iSuffixes[selectSuffixIndex(param1Int)]);
/*      */     }
/*      */     
/*      */     public void printTo(Writer param1Writer, int param1Int) throws IOException {
/* 1230 */       param1Writer.write(this.iSuffixes[selectSuffixIndex(param1Int)]);
/*      */     }
/*      */     
/*      */     public int parse(String param1String, int param1Int) {
/* 1234 */       for (String str : this.iSuffixesSortedDescByLength) {
/* 1235 */         if (param1String.regionMatches(true, param1Int, str, 0, str.length()) && 
/* 1236 */           !matchesOtherAffix(str.length(), param1String, param1Int)) {
/* 1237 */           return param1Int + str.length();
/*      */         }
/*      */       } 
/*      */       
/* 1241 */       return param1Int ^ 0xFFFFFFFF;
/*      */     }
/*      */     
/*      */     public int scan(String param1String, int param1Int) {
/* 1245 */       int i = param1String.length();
/* 1246 */       for (int j = param1Int; j < i; j++) {
/* 1247 */         for (String str : this.iSuffixesSortedDescByLength) {
/* 1248 */           if (param1String.regionMatches(true, j, str, 0, str.length()) && 
/* 1249 */             !matchesOtherAffix(str.length(), param1String, j)) {
/* 1250 */             return j;
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/* 1255 */       return param1Int ^ 0xFFFFFFFF;
/*      */     }
/*      */     
/*      */     public String[] getAffixes() {
/* 1259 */       return (String[])this.iSuffixes.clone();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static class CompositeAffix
/*      */     extends IgnorableAffix
/*      */   {
/*      */     private final PeriodFormatterBuilder.PeriodFieldAffix iLeft;
/*      */     
/*      */     private final PeriodFormatterBuilder.PeriodFieldAffix iRight;
/*      */     private final String[] iLeftRightCombinations;
/*      */     
/*      */     CompositeAffix(PeriodFormatterBuilder.PeriodFieldAffix param1PeriodFieldAffix1, PeriodFormatterBuilder.PeriodFieldAffix param1PeriodFieldAffix2) {
/* 1273 */       this.iLeft = param1PeriodFieldAffix1;
/* 1274 */       this.iRight = param1PeriodFieldAffix2;
/*      */ 
/*      */ 
/*      */       
/* 1278 */       HashSet<String> hashSet = new HashSet();
/* 1279 */       for (String str : this.iLeft.getAffixes()) {
/* 1280 */         for (String str1 : this.iRight.getAffixes()) {
/* 1281 */           hashSet.add(str + str1);
/*      */         }
/*      */       } 
/* 1284 */       this.iLeftRightCombinations = hashSet.<String>toArray(new String[hashSet.size()]);
/*      */     }
/*      */     
/*      */     public int calculatePrintedLength(int param1Int) {
/* 1288 */       return this.iLeft.calculatePrintedLength(param1Int) + this.iRight.calculatePrintedLength(param1Int);
/*      */     }
/*      */ 
/*      */     
/*      */     public void printTo(StringBuffer param1StringBuffer, int param1Int) {
/* 1293 */       this.iLeft.printTo(param1StringBuffer, param1Int);
/* 1294 */       this.iRight.printTo(param1StringBuffer, param1Int);
/*      */     }
/*      */     
/*      */     public void printTo(Writer param1Writer, int param1Int) throws IOException {
/* 1298 */       this.iLeft.printTo(param1Writer, param1Int);
/* 1299 */       this.iRight.printTo(param1Writer, param1Int);
/*      */     }
/*      */     
/*      */     public int parse(String param1String, int param1Int) {
/* 1303 */       int i = this.iLeft.parse(param1String, param1Int);
/* 1304 */       if (i >= 0) {
/* 1305 */         i = this.iRight.parse(param1String, i);
/* 1306 */         if (i >= 0 && matchesOtherAffix(parse(param1String, i) - i, param1String, param1Int)) {
/* 1307 */           return param1Int ^ 0xFFFFFFFF;
/*      */         }
/*      */       } 
/* 1310 */       return i;
/*      */     }
/*      */     
/*      */     public int scan(String param1String, int param1Int) {
/* 1314 */       int i = this.iLeft.scan(param1String, param1Int);
/* 1315 */       if (i >= 0) {
/* 1316 */         int j = this.iRight.scan(param1String, this.iLeft.parse(param1String, i));
/* 1317 */         if (j < 0 || !matchesOtherAffix(this.iRight.parse(param1String, j) - i, param1String, param1Int)) {
/* 1318 */           if (i > 0) {
/* 1319 */             return i;
/*      */           }
/* 1321 */           return j;
/*      */         } 
/*      */       } 
/*      */       
/* 1325 */       return param1Int ^ 0xFFFFFFFF;
/*      */     }
/*      */     
/*      */     public String[] getAffixes() {
/* 1329 */       return (String[])this.iLeftRightCombinations.clone();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static class FieldFormatter
/*      */     implements PeriodPrinter, PeriodParser
/*      */   {
/*      */     private final int iMinPrintedDigits;
/*      */ 
/*      */     
/*      */     private final int iPrintZeroSetting;
/*      */ 
/*      */     
/*      */     private final int iMaxParsedDigits;
/*      */ 
/*      */     
/*      */     private final boolean iRejectSignedValues;
/*      */     
/*      */     private final int iFieldType;
/*      */     
/*      */     private final FieldFormatter[] iFieldFormatters;
/*      */     
/*      */     private final PeriodFormatterBuilder.PeriodFieldAffix iPrefix;
/*      */     
/*      */     private final PeriodFormatterBuilder.PeriodFieldAffix iSuffix;
/*      */ 
/*      */     
/*      */     FieldFormatter(int param1Int1, int param1Int2, int param1Int3, boolean param1Boolean, int param1Int4, FieldFormatter[] param1ArrayOfFieldFormatter, PeriodFormatterBuilder.PeriodFieldAffix param1PeriodFieldAffix1, PeriodFormatterBuilder.PeriodFieldAffix param1PeriodFieldAffix2) {
/* 1359 */       this.iMinPrintedDigits = param1Int1;
/* 1360 */       this.iPrintZeroSetting = param1Int2;
/* 1361 */       this.iMaxParsedDigits = param1Int3;
/* 1362 */       this.iRejectSignedValues = param1Boolean;
/* 1363 */       this.iFieldType = param1Int4;
/* 1364 */       this.iFieldFormatters = param1ArrayOfFieldFormatter;
/* 1365 */       this.iPrefix = param1PeriodFieldAffix1;
/* 1366 */       this.iSuffix = param1PeriodFieldAffix2;
/*      */     }
/*      */     
/*      */     FieldFormatter(FieldFormatter param1FieldFormatter, PeriodFormatterBuilder.PeriodFieldAffix param1PeriodFieldAffix) {
/* 1370 */       this.iMinPrintedDigits = param1FieldFormatter.iMinPrintedDigits;
/* 1371 */       this.iPrintZeroSetting = param1FieldFormatter.iPrintZeroSetting;
/* 1372 */       this.iMaxParsedDigits = param1FieldFormatter.iMaxParsedDigits;
/* 1373 */       this.iRejectSignedValues = param1FieldFormatter.iRejectSignedValues;
/* 1374 */       this.iFieldType = param1FieldFormatter.iFieldType;
/* 1375 */       this.iFieldFormatters = param1FieldFormatter.iFieldFormatters;
/* 1376 */       this.iPrefix = param1FieldFormatter.iPrefix;
/* 1377 */       if (param1FieldFormatter.iSuffix != null) {
/* 1378 */         param1PeriodFieldAffix = new PeriodFormatterBuilder.CompositeAffix(param1FieldFormatter.iSuffix, param1PeriodFieldAffix);
/*      */       }
/* 1380 */       this.iSuffix = param1PeriodFieldAffix;
/*      */     }
/*      */ 
/*      */     
/*      */     public void finish(FieldFormatter[] param1ArrayOfFieldFormatter) {
/* 1385 */       HashSet<PeriodFormatterBuilder.PeriodFieldAffix> hashSet1 = new HashSet();
/* 1386 */       HashSet<PeriodFormatterBuilder.PeriodFieldAffix> hashSet2 = new HashSet();
/* 1387 */       for (FieldFormatter fieldFormatter : param1ArrayOfFieldFormatter) {
/* 1388 */         if (fieldFormatter != null && !equals(fieldFormatter)) {
/* 1389 */           hashSet1.add(fieldFormatter.iPrefix);
/* 1390 */           hashSet2.add(fieldFormatter.iSuffix);
/*      */         } 
/*      */       } 
/*      */       
/* 1394 */       if (this.iPrefix != null) {
/* 1395 */         this.iPrefix.finish(hashSet1);
/*      */       }
/*      */       
/* 1398 */       if (this.iSuffix != null) {
/* 1399 */         this.iSuffix.finish(hashSet2);
/*      */       }
/*      */     }
/*      */     
/*      */     public int countFieldsToPrint(ReadablePeriod param1ReadablePeriod, int param1Int, Locale param1Locale) {
/* 1404 */       if (param1Int <= 0) {
/* 1405 */         return 0;
/*      */       }
/* 1407 */       if (this.iPrintZeroSetting == 4 || getFieldValue(param1ReadablePeriod) != Long.MAX_VALUE) {
/* 1408 */         return 1;
/*      */       }
/* 1410 */       return 0;
/*      */     }
/*      */     
/*      */     public int calculatePrintedLength(ReadablePeriod param1ReadablePeriod, Locale param1Locale) {
/* 1414 */       long l = getFieldValue(param1ReadablePeriod);
/* 1415 */       if (l == Long.MAX_VALUE) {
/* 1416 */         return 0;
/*      */       }
/*      */       
/* 1419 */       int i = Math.max(FormatUtils.calculateDigitCount(l), this.iMinPrintedDigits);
/* 1420 */       if (this.iFieldType >= 8) {
/*      */ 
/*      */         
/* 1423 */         i = (l < 0L) ? Math.max(i, 5) : Math.max(i, 4);
/*      */         
/* 1425 */         i++;
/* 1426 */         if (this.iFieldType == 9 && Math.abs(l) % 1000L == 0L)
/*      */         {
/* 1428 */           i -= 4;
/*      */         }
/*      */         
/* 1431 */         l /= 1000L;
/*      */       } 
/* 1433 */       int j = (int)l;
/*      */       
/* 1435 */       if (this.iPrefix != null) {
/* 1436 */         i += this.iPrefix.calculatePrintedLength(j);
/*      */       }
/* 1438 */       if (this.iSuffix != null) {
/* 1439 */         i += this.iSuffix.calculatePrintedLength(j);
/*      */       }
/*      */       
/* 1442 */       return i;
/*      */     }
/*      */     
/*      */     public void printTo(StringBuffer param1StringBuffer, ReadablePeriod param1ReadablePeriod, Locale param1Locale) {
/* 1446 */       long l = getFieldValue(param1ReadablePeriod);
/* 1447 */       if (l == Long.MAX_VALUE) {
/*      */         return;
/*      */       }
/* 1450 */       int i = (int)l;
/* 1451 */       if (this.iFieldType >= 8) {
/* 1452 */         i = (int)(l / 1000L);
/*      */       }
/*      */       
/* 1455 */       if (this.iPrefix != null) {
/* 1456 */         this.iPrefix.printTo(param1StringBuffer, i);
/*      */       }
/* 1458 */       int j = param1StringBuffer.length();
/* 1459 */       int k = this.iMinPrintedDigits;
/* 1460 */       if (k <= 1) {
/* 1461 */         FormatUtils.appendUnpaddedInteger(param1StringBuffer, i);
/*      */       } else {
/* 1463 */         FormatUtils.appendPaddedInteger(param1StringBuffer, i, k);
/*      */       } 
/* 1465 */       if (this.iFieldType >= 8) {
/* 1466 */         int m = (int)(Math.abs(l) % 1000L);
/* 1467 */         if (this.iFieldType == 8 || m > 0) {
/* 1468 */           if (l < 0L && l > -1000L) {
/* 1469 */             param1StringBuffer.insert(j, '-');
/*      */           }
/* 1471 */           param1StringBuffer.append('.');
/* 1472 */           FormatUtils.appendPaddedInteger(param1StringBuffer, m, 3);
/*      */         } 
/*      */       } 
/* 1475 */       if (this.iSuffix != null) {
/* 1476 */         this.iSuffix.printTo(param1StringBuffer, i);
/*      */       }
/*      */     }
/*      */     
/*      */     public void printTo(Writer param1Writer, ReadablePeriod param1ReadablePeriod, Locale param1Locale) throws IOException {
/* 1481 */       long l = getFieldValue(param1ReadablePeriod);
/* 1482 */       if (l == Long.MAX_VALUE) {
/*      */         return;
/*      */       }
/* 1485 */       int i = (int)l;
/* 1486 */       if (this.iFieldType >= 8) {
/* 1487 */         i = (int)(l / 1000L);
/*      */       }
/*      */       
/* 1490 */       if (this.iPrefix != null) {
/* 1491 */         this.iPrefix.printTo(param1Writer, i);
/*      */       }
/* 1493 */       int j = this.iMinPrintedDigits;
/* 1494 */       if (j <= 1) {
/* 1495 */         FormatUtils.writeUnpaddedInteger(param1Writer, i);
/*      */       } else {
/* 1497 */         FormatUtils.writePaddedInteger(param1Writer, i, j);
/*      */       } 
/* 1499 */       if (this.iFieldType >= 8) {
/* 1500 */         int k = (int)(Math.abs(l) % 1000L);
/* 1501 */         if (this.iFieldType == 8 || k > 0) {
/* 1502 */           param1Writer.write(46);
/* 1503 */           FormatUtils.writePaddedInteger(param1Writer, k, 3);
/*      */         } 
/*      */       } 
/* 1506 */       if (this.iSuffix != null) {
/* 1507 */         this.iSuffix.printTo(param1Writer, i);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int parseInto(ReadWritablePeriod param1ReadWritablePeriod, String param1String, int param1Int, Locale param1Locale) {
/*      */       int j;
/* 1515 */       boolean bool1 = (this.iPrintZeroSetting == 4) ? true : false;
/*      */ 
/*      */       
/* 1518 */       if (param1Int >= param1String.length()) {
/* 1519 */         return bool1 ? (param1Int ^ 0xFFFFFFFF) : param1Int;
/*      */       }
/*      */       
/* 1522 */       if (this.iPrefix != null) {
/* 1523 */         param1Int = this.iPrefix.parse(param1String, param1Int);
/* 1524 */         if (param1Int >= 0) {
/*      */           
/* 1526 */           bool1 = true;
/*      */         } else {
/*      */           
/* 1529 */           if (!bool1)
/*      */           {
/*      */ 
/*      */             
/* 1533 */             return param1Int ^ 0xFFFFFFFF;
/*      */           }
/* 1535 */           return param1Int;
/*      */         } 
/*      */       } 
/*      */       
/* 1539 */       int i = -1;
/* 1540 */       if (this.iSuffix != null && !bool1) {
/*      */ 
/*      */         
/* 1543 */         i = this.iSuffix.scan(param1String, param1Int);
/* 1544 */         if (i >= 0) {
/*      */           
/* 1546 */           bool1 = true;
/*      */         } else {
/*      */           
/* 1549 */           if (!bool1)
/*      */           {
/*      */ 
/*      */             
/* 1553 */             return i ^ 0xFFFFFFFF;
/*      */           }
/* 1555 */           return i;
/*      */         } 
/*      */       } 
/*      */       
/* 1559 */       if (!bool1 && !isSupported(param1ReadWritablePeriod.getPeriodType(), this.iFieldType))
/*      */       {
/*      */         
/* 1562 */         return param1Int;
/*      */       }
/*      */ 
/*      */       
/* 1566 */       if (i > 0) {
/* 1567 */         j = Math.min(this.iMaxParsedDigits, i - param1Int);
/*      */       } else {
/* 1569 */         j = Math.min(this.iMaxParsedDigits, param1String.length() - param1Int);
/*      */       } 
/*      */ 
/*      */       
/* 1573 */       byte b = 0;
/* 1574 */       int k = -1;
/* 1575 */       boolean bool2 = false;
/* 1576 */       boolean bool3 = false;
/* 1577 */       while (b < j) {
/* 1578 */         char c = param1String.charAt(param1Int + b);
/*      */         
/* 1580 */         if (b == 0 && (c == '-' || c == '+') && !this.iRejectSignedValues) {
/* 1581 */           bool3 = (c == '-') ? true : false;
/*      */ 
/*      */           
/* 1584 */           if (b + 1 >= j || (c = param1String.charAt(param1Int + b + 1)) < '0' || c > '9') {
/*      */             break;
/*      */           }
/*      */ 
/*      */ 
/*      */           
/* 1590 */           if (bool3) {
/* 1591 */             b++;
/*      */           } else {
/*      */             
/* 1594 */             param1Int++;
/*      */           } 
/*      */           
/* 1597 */           j = Math.min(j + 1, param1String.length() - param1Int);
/*      */           
/*      */           continue;
/*      */         } 
/* 1601 */         if (c >= '0' && c <= '9') {
/* 1602 */           bool2 = true;
/*      */         }
/* 1604 */         else if ((c == '.' || c == ',') && (this.iFieldType == 8 || this.iFieldType == 9)) {
/*      */           
/* 1606 */           if (k >= 0) {
/*      */             break;
/*      */           }
/*      */           
/* 1610 */           k = param1Int + b + 1;
/*      */           
/* 1612 */           j = Math.min(j + 1, param1String.length() - param1Int);
/*      */         } else {
/*      */           break;
/*      */         } 
/*      */         
/* 1617 */         b++;
/*      */       } 
/*      */       
/* 1620 */       if (!bool2) {
/* 1621 */         return param1Int ^ 0xFFFFFFFF;
/*      */       }
/*      */       
/* 1624 */       if (i >= 0 && param1Int + b != i)
/*      */       {
/*      */ 
/*      */ 
/*      */         
/* 1629 */         return param1Int;
/*      */       }
/*      */       
/* 1632 */       if (this.iFieldType != 8 && this.iFieldType != 9) {
/*      */         
/* 1634 */         setFieldValue(param1ReadWritablePeriod, this.iFieldType, parseInt(param1String, param1Int, b));
/* 1635 */       } else if (k < 0) {
/* 1636 */         setFieldValue(param1ReadWritablePeriod, 6, parseInt(param1String, param1Int, b));
/* 1637 */         setFieldValue(param1ReadWritablePeriod, 7, 0);
/*      */       } else {
/* 1639 */         int i1, m = parseInt(param1String, param1Int, k - param1Int - 1);
/* 1640 */         setFieldValue(param1ReadWritablePeriod, 6, m);
/*      */         
/* 1642 */         int n = param1Int + b - k;
/*      */         
/* 1644 */         if (n <= 0) {
/* 1645 */           i1 = 0;
/*      */         } else {
/* 1647 */           if (n >= 3) {
/* 1648 */             i1 = parseInt(param1String, k, 3);
/*      */           } else {
/* 1650 */             i1 = parseInt(param1String, k, n);
/* 1651 */             if (n == 1) {
/* 1652 */               i1 *= 100;
/*      */             } else {
/* 1654 */               i1 *= 10;
/*      */             } 
/*      */           } 
/* 1657 */           if (bool3 || m < 0) {
/* 1658 */             i1 = -i1;
/*      */           }
/*      */         } 
/*      */         
/* 1662 */         setFieldValue(param1ReadWritablePeriod, 7, i1);
/*      */       } 
/*      */       
/* 1665 */       param1Int += b;
/*      */       
/* 1667 */       if (param1Int >= 0 && this.iSuffix != null) {
/* 1668 */         param1Int = this.iSuffix.parse(param1String, param1Int);
/*      */       }
/*      */       
/* 1671 */       return param1Int;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private int parseInt(String param1String, int param1Int1, int param1Int2) {
/*      */       boolean bool;
/* 1681 */       if (param1Int2 >= 10)
/*      */       {
/* 1683 */         return Integer.parseInt(param1String.substring(param1Int1, param1Int1 + param1Int2));
/*      */       }
/* 1685 */       if (param1Int2 <= 0) {
/* 1686 */         return 0;
/*      */       }
/* 1688 */       int i = param1String.charAt(param1Int1++);
/* 1689 */       param1Int2--;
/*      */       
/* 1691 */       if (i == 45) {
/* 1692 */         if (--param1Int2 < 0) {
/* 1693 */           return 0;
/*      */         }
/* 1695 */         bool = true;
/* 1696 */         i = param1String.charAt(param1Int1++);
/*      */       } else {
/* 1698 */         bool = false;
/*      */       } 
/* 1700 */       i -= '0';
/* 1701 */       while (param1Int2-- > 0) {
/* 1702 */         i = (i << 3) + (i << 1) + param1String.charAt(param1Int1++) - 48;
/*      */       }
/* 1704 */       return bool ? -i : i;
/*      */     }
/*      */     
/*      */     long getFieldValue(ReadablePeriod param1ReadablePeriod) {
/*      */       PeriodType periodType;
/*      */       long l;
/*      */       int i;
/*      */       int j;
/* 1712 */       if (this.iPrintZeroSetting == 4) {
/* 1713 */         periodType = null;
/*      */       } else {
/* 1715 */         periodType = param1ReadablePeriod.getPeriodType();
/*      */       } 
/* 1717 */       if (periodType != null && !isSupported(periodType, this.iFieldType)) {
/* 1718 */         return Long.MAX_VALUE;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1723 */       switch (this.iFieldType) {
/*      */         default:
/* 1725 */           return Long.MAX_VALUE;
/*      */         case 0:
/* 1727 */           l = param1ReadablePeriod.get(DurationFieldType.years());
/*      */           break;
/*      */         case 1:
/* 1730 */           l = param1ReadablePeriod.get(DurationFieldType.months());
/*      */           break;
/*      */         case 2:
/* 1733 */           l = param1ReadablePeriod.get(DurationFieldType.weeks());
/*      */           break;
/*      */         case 3:
/* 1736 */           l = param1ReadablePeriod.get(DurationFieldType.days());
/*      */           break;
/*      */         case 4:
/* 1739 */           l = param1ReadablePeriod.get(DurationFieldType.hours());
/*      */           break;
/*      */         case 5:
/* 1742 */           l = param1ReadablePeriod.get(DurationFieldType.minutes());
/*      */           break;
/*      */         case 6:
/* 1745 */           l = param1ReadablePeriod.get(DurationFieldType.seconds());
/*      */           break;
/*      */         case 7:
/* 1748 */           l = param1ReadablePeriod.get(DurationFieldType.millis());
/*      */           break;
/*      */         case 8:
/*      */         case 9:
/* 1752 */           i = param1ReadablePeriod.get(DurationFieldType.seconds());
/* 1753 */           j = param1ReadablePeriod.get(DurationFieldType.millis());
/* 1754 */           l = i * 1000L + j;
/*      */           break;
/*      */       } 
/*      */ 
/*      */       
/* 1759 */       if (l == 0L) {
/* 1760 */         switch (this.iPrintZeroSetting) {
/*      */           case 5:
/* 1762 */             return Long.MAX_VALUE;
/*      */           case 2:
/* 1764 */             if (isZero(param1ReadablePeriod) && this.iFieldFormatters[this.iFieldType] == this) {
/* 1765 */               for (i = this.iFieldType + 1; i <= 9; i++) {
/* 1766 */                 if (isSupported(periodType, i) && this.iFieldFormatters[i] != null)
/* 1767 */                   return Long.MAX_VALUE; 
/*      */               } 
/*      */               break;
/*      */             } 
/* 1771 */             return Long.MAX_VALUE;
/*      */ 
/*      */           
/*      */           case 1:
/* 1775 */             if (isZero(param1ReadablePeriod) && this.iFieldFormatters[this.iFieldType] == this) {
/* 1776 */               i = Math.min(this.iFieldType, 8);
/* 1777 */               i--;
/* 1778 */               for (; i >= 0 && i <= 9; i--) {
/* 1779 */                 if (isSupported(periodType, i) && this.iFieldFormatters[i] != null)
/* 1780 */                   return Long.MAX_VALUE; 
/*      */               } 
/*      */               break;
/*      */             } 
/* 1784 */             return Long.MAX_VALUE;
/*      */         } 
/*      */ 
/*      */ 
/*      */       
/*      */       }
/* 1790 */       return l;
/*      */     } boolean isZero(ReadablePeriod param1ReadablePeriod) {
/*      */       byte b;
/*      */       int i;
/* 1794 */       for (b = 0, i = param1ReadablePeriod.size(); b < i; b++) {
/* 1795 */         if (param1ReadablePeriod.getValue(b) != 0) {
/* 1796 */           return false;
/*      */         }
/*      */       } 
/* 1799 */       return true;
/*      */     }
/*      */     
/*      */     boolean isSupported(PeriodType param1PeriodType, int param1Int) {
/* 1803 */       switch (param1Int)
/*      */       { default:
/* 1805 */           return false;
/*      */         case 0:
/* 1807 */           return param1PeriodType.isSupported(DurationFieldType.years());
/*      */         case 1:
/* 1809 */           return param1PeriodType.isSupported(DurationFieldType.months());
/*      */         case 2:
/* 1811 */           return param1PeriodType.isSupported(DurationFieldType.weeks());
/*      */         case 3:
/* 1813 */           return param1PeriodType.isSupported(DurationFieldType.days());
/*      */         case 4:
/* 1815 */           return param1PeriodType.isSupported(DurationFieldType.hours());
/*      */         case 5:
/* 1817 */           return param1PeriodType.isSupported(DurationFieldType.minutes());
/*      */         case 6:
/* 1819 */           return param1PeriodType.isSupported(DurationFieldType.seconds());
/*      */         case 7:
/* 1821 */           return param1PeriodType.isSupported(DurationFieldType.millis());
/*      */         case 8:
/*      */         case 9:
/* 1824 */           break; }  return (param1PeriodType.isSupported(DurationFieldType.seconds()) || param1PeriodType.isSupported(DurationFieldType.millis()));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     void setFieldValue(ReadWritablePeriod param1ReadWritablePeriod, int param1Int1, int param1Int2) {
/* 1830 */       switch (param1Int1) {
/*      */         default:
/*      */           return;
/*      */         case 0:
/* 1834 */           param1ReadWritablePeriod.setYears(param1Int2);
/*      */         
/*      */         case 1:
/* 1837 */           param1ReadWritablePeriod.setMonths(param1Int2);
/*      */         
/*      */         case 2:
/* 1840 */           param1ReadWritablePeriod.setWeeks(param1Int2);
/*      */         
/*      */         case 3:
/* 1843 */           param1ReadWritablePeriod.setDays(param1Int2);
/*      */         
/*      */         case 4:
/* 1846 */           param1ReadWritablePeriod.setHours(param1Int2);
/*      */         
/*      */         case 5:
/* 1849 */           param1ReadWritablePeriod.setMinutes(param1Int2);
/*      */         
/*      */         case 6:
/* 1852 */           param1ReadWritablePeriod.setSeconds(param1Int2);
/*      */         case 7:
/*      */           break;
/* 1855 */       }  param1ReadWritablePeriod.setMillis(param1Int2);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     int getFieldType() {
/* 1861 */       return this.iFieldType;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class Literal
/*      */     implements PeriodPrinter, PeriodParser
/*      */   {
/* 1871 */     static final Literal EMPTY = new Literal("");
/*      */     private final String iText;
/*      */     
/*      */     Literal(String param1String) {
/* 1875 */       this.iText = param1String;
/*      */     }
/*      */     
/*      */     public int countFieldsToPrint(ReadablePeriod param1ReadablePeriod, int param1Int, Locale param1Locale) {
/* 1879 */       return 0;
/*      */     }
/*      */     
/*      */     public int calculatePrintedLength(ReadablePeriod param1ReadablePeriod, Locale param1Locale) {
/* 1883 */       return this.iText.length();
/*      */     }
/*      */     
/*      */     public void printTo(StringBuffer param1StringBuffer, ReadablePeriod param1ReadablePeriod, Locale param1Locale) {
/* 1887 */       param1StringBuffer.append(this.iText);
/*      */     }
/*      */     
/*      */     public void printTo(Writer param1Writer, ReadablePeriod param1ReadablePeriod, Locale param1Locale) throws IOException {
/* 1891 */       param1Writer.write(this.iText);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int parseInto(ReadWritablePeriod param1ReadWritablePeriod, String param1String, int param1Int, Locale param1Locale) {
/* 1897 */       if (param1String.regionMatches(true, param1Int, this.iText, 0, this.iText.length())) {
/* 1898 */         return param1Int + this.iText.length();
/*      */       }
/* 1900 */       return param1Int ^ 0xFFFFFFFF;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static class Separator
/*      */     implements PeriodPrinter, PeriodParser
/*      */   {
/*      */     private final String iText;
/*      */     
/*      */     private final String iFinalText;
/*      */     
/*      */     private final String[] iParsedForms;
/*      */     
/*      */     private final boolean iUseBefore;
/*      */     
/*      */     private final boolean iUseAfter;
/*      */     
/*      */     private final PeriodPrinter iBeforePrinter;
/*      */     
/*      */     private volatile PeriodPrinter iAfterPrinter;
/*      */     
/*      */     private final PeriodParser iBeforeParser;
/*      */     private volatile PeriodParser iAfterParser;
/*      */     
/*      */     Separator(String param1String1, String param1String2, String[] param1ArrayOfString, PeriodPrinter param1PeriodPrinter, PeriodParser param1PeriodParser, boolean param1Boolean1, boolean param1Boolean2) {
/* 1926 */       this.iText = param1String1;
/* 1927 */       this.iFinalText = param1String2;
/*      */       
/* 1929 */       if ((param1String2 == null || param1String1.equals(param1String2)) && (param1ArrayOfString == null || param1ArrayOfString.length == 0)) {
/*      */ 
/*      */         
/* 1932 */         this.iParsedForms = new String[] { param1String1 };
/*      */       } else {
/*      */         
/* 1935 */         TreeSet<String> treeSet = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
/* 1936 */         treeSet.add(param1String1);
/* 1937 */         treeSet.add(param1String2);
/* 1938 */         if (param1ArrayOfString != null) {
/* 1939 */           for (int i = param1ArrayOfString.length; --i >= 0;) {
/* 1940 */             treeSet.add(param1ArrayOfString[i]);
/*      */           }
/*      */         }
/* 1943 */         ArrayList<String> arrayList = new ArrayList<String>(treeSet);
/* 1944 */         Collections.reverse(arrayList);
/* 1945 */         this.iParsedForms = arrayList.<String>toArray(new String[arrayList.size()]);
/*      */       } 
/*      */       
/* 1948 */       this.iBeforePrinter = param1PeriodPrinter;
/* 1949 */       this.iBeforeParser = param1PeriodParser;
/* 1950 */       this.iUseBefore = param1Boolean1;
/* 1951 */       this.iUseAfter = param1Boolean2;
/*      */     }
/*      */     
/*      */     public int countFieldsToPrint(ReadablePeriod param1ReadablePeriod, int param1Int, Locale param1Locale) {
/* 1955 */       int i = this.iBeforePrinter.countFieldsToPrint(param1ReadablePeriod, param1Int, param1Locale);
/* 1956 */       if (i < param1Int) {
/* 1957 */         i += this.iAfterPrinter.countFieldsToPrint(param1ReadablePeriod, param1Int, param1Locale);
/*      */       }
/* 1959 */       return i;
/*      */     }
/*      */     
/*      */     public int calculatePrintedLength(ReadablePeriod param1ReadablePeriod, Locale param1Locale) {
/* 1963 */       PeriodPrinter periodPrinter1 = this.iBeforePrinter;
/* 1964 */       PeriodPrinter periodPrinter2 = this.iAfterPrinter;
/*      */       
/* 1966 */       int i = periodPrinter1.calculatePrintedLength(param1ReadablePeriod, param1Locale) + periodPrinter2.calculatePrintedLength(param1ReadablePeriod, param1Locale);
/*      */ 
/*      */       
/* 1969 */       if (this.iUseBefore) {
/* 1970 */         if (periodPrinter1.countFieldsToPrint(param1ReadablePeriod, 1, param1Locale) > 0) {
/* 1971 */           if (this.iUseAfter) {
/* 1972 */             int j = periodPrinter2.countFieldsToPrint(param1ReadablePeriod, 2, param1Locale);
/* 1973 */             if (j > 0) {
/* 1974 */               i += ((j > 1) ? this.iText : this.iFinalText).length();
/*      */             }
/*      */           } else {
/* 1977 */             i += this.iText.length();
/*      */           } 
/*      */         }
/* 1980 */       } else if (this.iUseAfter && periodPrinter2.countFieldsToPrint(param1ReadablePeriod, 1, param1Locale) > 0) {
/* 1981 */         i += this.iText.length();
/*      */       } 
/*      */       
/* 1984 */       return i;
/*      */     }
/*      */     
/*      */     public void printTo(StringBuffer param1StringBuffer, ReadablePeriod param1ReadablePeriod, Locale param1Locale) {
/* 1988 */       PeriodPrinter periodPrinter1 = this.iBeforePrinter;
/* 1989 */       PeriodPrinter periodPrinter2 = this.iAfterPrinter;
/*      */       
/* 1991 */       periodPrinter1.printTo(param1StringBuffer, param1ReadablePeriod, param1Locale);
/* 1992 */       if (this.iUseBefore) {
/* 1993 */         if (periodPrinter1.countFieldsToPrint(param1ReadablePeriod, 1, param1Locale) > 0) {
/* 1994 */           if (this.iUseAfter) {
/* 1995 */             int i = periodPrinter2.countFieldsToPrint(param1ReadablePeriod, 2, param1Locale);
/* 1996 */             if (i > 0) {
/* 1997 */               param1StringBuffer.append((i > 1) ? this.iText : this.iFinalText);
/*      */             }
/*      */           } else {
/* 2000 */             param1StringBuffer.append(this.iText);
/*      */           } 
/*      */         }
/* 2003 */       } else if (this.iUseAfter && periodPrinter2.countFieldsToPrint(param1ReadablePeriod, 1, param1Locale) > 0) {
/* 2004 */         param1StringBuffer.append(this.iText);
/*      */       } 
/* 2006 */       periodPrinter2.printTo(param1StringBuffer, param1ReadablePeriod, param1Locale);
/*      */     }
/*      */     
/*      */     public void printTo(Writer param1Writer, ReadablePeriod param1ReadablePeriod, Locale param1Locale) throws IOException {
/* 2010 */       PeriodPrinter periodPrinter1 = this.iBeforePrinter;
/* 2011 */       PeriodPrinter periodPrinter2 = this.iAfterPrinter;
/*      */       
/* 2013 */       periodPrinter1.printTo(param1Writer, param1ReadablePeriod, param1Locale);
/* 2014 */       if (this.iUseBefore) {
/* 2015 */         if (periodPrinter1.countFieldsToPrint(param1ReadablePeriod, 1, param1Locale) > 0) {
/* 2016 */           if (this.iUseAfter) {
/* 2017 */             int i = periodPrinter2.countFieldsToPrint(param1ReadablePeriod, 2, param1Locale);
/* 2018 */             if (i > 0) {
/* 2019 */               param1Writer.write((i > 1) ? this.iText : this.iFinalText);
/*      */             }
/*      */           } else {
/* 2022 */             param1Writer.write(this.iText);
/*      */           } 
/*      */         }
/* 2025 */       } else if (this.iUseAfter && periodPrinter2.countFieldsToPrint(param1ReadablePeriod, 1, param1Locale) > 0) {
/* 2026 */         param1Writer.write(this.iText);
/*      */       } 
/* 2028 */       periodPrinter2.printTo(param1Writer, param1ReadablePeriod, param1Locale);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int parseInto(ReadWritablePeriod param1ReadWritablePeriod, String param1String, int param1Int, Locale param1Locale) {
/* 2034 */       int i = param1Int;
/* 2035 */       param1Int = this.iBeforeParser.parseInto(param1ReadWritablePeriod, param1String, param1Int, param1Locale);
/*      */       
/* 2037 */       if (param1Int < 0) {
/* 2038 */         return param1Int;
/*      */       }
/*      */       
/* 2041 */       boolean bool = false;
/* 2042 */       byte b = -1;
/* 2043 */       if (param1Int > i) {
/*      */         
/* 2045 */         String[] arrayOfString = this.iParsedForms;
/* 2046 */         int j = arrayOfString.length;
/* 2047 */         for (byte b1 = 0; b1 < j; b1++) {
/* 2048 */           String str = arrayOfString[b1];
/* 2049 */           if (str == null || str.length() == 0 || param1String.regionMatches(true, param1Int, str, 0, str.length())) {
/*      */ 
/*      */             
/* 2052 */             b = (str == null) ? 0 : str.length();
/* 2053 */             param1Int += b;
/* 2054 */             bool = true;
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       } 
/* 2060 */       i = param1Int;
/* 2061 */       param1Int = this.iAfterParser.parseInto(param1ReadWritablePeriod, param1String, param1Int, param1Locale);
/*      */       
/* 2063 */       if (param1Int < 0) {
/* 2064 */         return param1Int;
/*      */       }
/*      */       
/* 2067 */       if (bool && param1Int == i && b > 0)
/*      */       {
/* 2069 */         return i ^ 0xFFFFFFFF;
/*      */       }
/*      */       
/* 2072 */       if (param1Int > i && !bool && !this.iUseBefore)
/*      */       {
/* 2074 */         return i ^ 0xFFFFFFFF;
/*      */       }
/*      */       
/* 2077 */       return param1Int;
/*      */     }
/*      */     
/*      */     Separator finish(PeriodPrinter param1PeriodPrinter, PeriodParser param1PeriodParser) {
/* 2081 */       this.iAfterPrinter = param1PeriodPrinter;
/* 2082 */       this.iAfterParser = param1PeriodParser;
/* 2083 */       return this;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static class Composite
/*      */     implements PeriodPrinter, PeriodParser
/*      */   {
/*      */     private final PeriodPrinter[] iPrinters;
/*      */     
/*      */     private final PeriodParser[] iParsers;
/*      */ 
/*      */     
/*      */     Composite(List<Object> param1List) {
/* 2098 */       ArrayList<Object> arrayList1 = new ArrayList();
/* 2099 */       ArrayList<Object> arrayList2 = new ArrayList();
/*      */       
/* 2101 */       decompose(param1List, arrayList1, arrayList2);
/*      */       
/* 2103 */       if (arrayList1.size() <= 0) {
/* 2104 */         this.iPrinters = null;
/*      */       } else {
/* 2106 */         this.iPrinters = arrayList1.<PeriodPrinter>toArray(new PeriodPrinter[arrayList1.size()]);
/*      */       } 
/*      */ 
/*      */       
/* 2110 */       if (arrayList2.size() <= 0) {
/* 2111 */         this.iParsers = null;
/*      */       } else {
/* 2113 */         this.iParsers = arrayList2.<PeriodParser>toArray(new PeriodParser[arrayList2.size()]);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int countFieldsToPrint(ReadablePeriod param1ReadablePeriod, int param1Int, Locale param1Locale) {
/* 2119 */       int i = 0;
/* 2120 */       PeriodPrinter[] arrayOfPeriodPrinter = this.iPrinters;
/* 2121 */       for (int j = arrayOfPeriodPrinter.length; i < param1Int && --j >= 0;) {
/* 2122 */         i += arrayOfPeriodPrinter[j].countFieldsToPrint(param1ReadablePeriod, 2147483647, param1Locale);
/*      */       }
/* 2124 */       return i;
/*      */     }
/*      */     
/*      */     public int calculatePrintedLength(ReadablePeriod param1ReadablePeriod, Locale param1Locale) {
/* 2128 */       int i = 0;
/* 2129 */       PeriodPrinter[] arrayOfPeriodPrinter = this.iPrinters;
/* 2130 */       for (int j = arrayOfPeriodPrinter.length; --j >= 0;) {
/* 2131 */         i += arrayOfPeriodPrinter[j].calculatePrintedLength(param1ReadablePeriod, param1Locale);
/*      */       }
/* 2133 */       return i;
/*      */     }
/*      */     
/*      */     public void printTo(StringBuffer param1StringBuffer, ReadablePeriod param1ReadablePeriod, Locale param1Locale) {
/* 2137 */       PeriodPrinter[] arrayOfPeriodPrinter = this.iPrinters;
/* 2138 */       int i = arrayOfPeriodPrinter.length;
/* 2139 */       for (byte b = 0; b < i; b++) {
/* 2140 */         arrayOfPeriodPrinter[b].printTo(param1StringBuffer, param1ReadablePeriod, param1Locale);
/*      */       }
/*      */     }
/*      */     
/*      */     public void printTo(Writer param1Writer, ReadablePeriod param1ReadablePeriod, Locale param1Locale) throws IOException {
/* 2145 */       PeriodPrinter[] arrayOfPeriodPrinter = this.iPrinters;
/* 2146 */       int i = arrayOfPeriodPrinter.length;
/* 2147 */       for (byte b = 0; b < i; b++) {
/* 2148 */         arrayOfPeriodPrinter[b].printTo(param1Writer, param1ReadablePeriod, param1Locale);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int parseInto(ReadWritablePeriod param1ReadWritablePeriod, String param1String, int param1Int, Locale param1Locale) {
/* 2155 */       PeriodParser[] arrayOfPeriodParser = this.iParsers;
/* 2156 */       if (arrayOfPeriodParser == null) {
/* 2157 */         throw new UnsupportedOperationException();
/*      */       }
/*      */       
/* 2160 */       int i = arrayOfPeriodParser.length;
/* 2161 */       for (byte b = 0; b < i && param1Int >= 0; b++) {
/* 2162 */         param1Int = arrayOfPeriodParser[b].parseInto(param1ReadWritablePeriod, param1String, param1Int, param1Locale);
/*      */       }
/* 2164 */       return param1Int;
/*      */     }
/*      */     
/*      */     private void decompose(List<Object> param1List1, List<Object> param1List2, List<Object> param1List3) {
/* 2168 */       int i = param1List1.size();
/* 2169 */       for (byte b = 0; b < i; b += 2) {
/* 2170 */         Object object = param1List1.get(b);
/* 2171 */         if (object instanceof PeriodPrinter) {
/* 2172 */           if (object instanceof Composite) {
/* 2173 */             addArrayToList(param1List2, (Object[])((Composite)object).iPrinters);
/*      */           } else {
/* 2175 */             param1List2.add(object);
/*      */           } 
/*      */         }
/*      */         
/* 2179 */         object = param1List1.get(b + 1);
/* 2180 */         if (object instanceof PeriodParser) {
/* 2181 */           if (object instanceof Composite) {
/* 2182 */             addArrayToList(param1List3, (Object[])((Composite)object).iParsers);
/*      */           } else {
/* 2184 */             param1List3.add(object);
/*      */           } 
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     private void addArrayToList(List<Object> param1List, Object[] param1ArrayOfObject) {
/* 2191 */       if (param1ArrayOfObject != null)
/* 2192 */         for (byte b = 0; b < param1ArrayOfObject.length; b++)
/* 2193 */           param1List.add(param1ArrayOfObject[b]);  
/*      */     }
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/format/PeriodFormatterBuilder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */