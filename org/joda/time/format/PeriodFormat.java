/*     */ package org.joda.time.format;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Writer;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Locale;
/*     */ import java.util.ResourceBundle;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ import org.joda.time.ReadWritablePeriod;
/*     */ import org.joda.time.ReadablePeriod;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PeriodFormat
/*     */ {
/*     */   private static final String BUNDLE_NAME = "org.joda.time.format.messages";
/*  53 */   private static final ConcurrentMap<Locale, PeriodFormatter> FORMATTERS = new ConcurrentHashMap<Locale, PeriodFormatter>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PeriodFormatter getDefault() {
/*  73 */     return wordBased(Locale.ENGLISH);
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
/*     */   public static PeriodFormatter wordBased() {
/*  85 */     return wordBased(Locale.getDefault());
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PeriodFormatter wordBased(Locale paramLocale) {
/* 203 */     PeriodFormatter periodFormatter = FORMATTERS.get(paramLocale);
/* 204 */     if (periodFormatter == null) {
/* 205 */       DynamicWordBased dynamicWordBased = new DynamicWordBased(buildWordBased(paramLocale));
/* 206 */       periodFormatter = new PeriodFormatter(dynamicWordBased, dynamicWordBased, paramLocale, null);
/* 207 */       PeriodFormatter periodFormatter1 = FORMATTERS.putIfAbsent(paramLocale, periodFormatter);
/* 208 */       if (periodFormatter1 != null) {
/* 209 */         periodFormatter = periodFormatter1;
/*     */       }
/*     */     } 
/* 212 */     return periodFormatter;
/*     */   }
/*     */ 
/*     */   
/*     */   private static PeriodFormatter buildWordBased(Locale paramLocale) {
/* 217 */     ResourceBundle resourceBundle = ResourceBundle.getBundle("org.joda.time.format.messages", paramLocale);
/* 218 */     if (containsKey(resourceBundle, "PeriodFormat.regex.separator")) {
/* 219 */       return buildRegExFormatter(resourceBundle, paramLocale);
/*     */     }
/* 221 */     return buildNonRegExFormatter(resourceBundle, paramLocale);
/*     */   }
/*     */ 
/*     */   
/*     */   private static PeriodFormatter buildRegExFormatter(ResourceBundle paramResourceBundle, Locale paramLocale) {
/* 226 */     String[] arrayOfString = retrieveVariants(paramResourceBundle);
/* 227 */     String str = paramResourceBundle.getString("PeriodFormat.regex.separator");
/*     */     
/* 229 */     PeriodFormatterBuilder periodFormatterBuilder = new PeriodFormatterBuilder();
/* 230 */     periodFormatterBuilder.appendYears();
/* 231 */     if (containsKey(paramResourceBundle, "PeriodFormat.years.regex")) {
/* 232 */       periodFormatterBuilder.appendSuffix(paramResourceBundle.getString("PeriodFormat.years.regex").split(str), paramResourceBundle.getString("PeriodFormat.years.list").split(str));
/*     */     }
/*     */     else {
/*     */       
/* 236 */       periodFormatterBuilder.appendSuffix(paramResourceBundle.getString("PeriodFormat.year"), paramResourceBundle.getString("PeriodFormat.years"));
/*     */     } 
/*     */     
/* 239 */     periodFormatterBuilder.appendSeparator(paramResourceBundle.getString("PeriodFormat.commaspace"), paramResourceBundle.getString("PeriodFormat.spaceandspace"), arrayOfString);
/* 240 */     periodFormatterBuilder.appendMonths();
/* 241 */     if (containsKey(paramResourceBundle, "PeriodFormat.months.regex")) {
/* 242 */       periodFormatterBuilder.appendSuffix(paramResourceBundle.getString("PeriodFormat.months.regex").split(str), paramResourceBundle.getString("PeriodFormat.months.list").split(str));
/*     */     }
/*     */     else {
/*     */       
/* 246 */       periodFormatterBuilder.appendSuffix(paramResourceBundle.getString("PeriodFormat.month"), paramResourceBundle.getString("PeriodFormat.months"));
/*     */     } 
/*     */     
/* 249 */     periodFormatterBuilder.appendSeparator(paramResourceBundle.getString("PeriodFormat.commaspace"), paramResourceBundle.getString("PeriodFormat.spaceandspace"), arrayOfString);
/* 250 */     periodFormatterBuilder.appendWeeks();
/* 251 */     if (containsKey(paramResourceBundle, "PeriodFormat.weeks.regex")) {
/* 252 */       periodFormatterBuilder.appendSuffix(paramResourceBundle.getString("PeriodFormat.weeks.regex").split(str), paramResourceBundle.getString("PeriodFormat.weeks.list").split(str));
/*     */     }
/*     */     else {
/*     */       
/* 256 */       periodFormatterBuilder.appendSuffix(paramResourceBundle.getString("PeriodFormat.week"), paramResourceBundle.getString("PeriodFormat.weeks"));
/*     */     } 
/*     */     
/* 259 */     periodFormatterBuilder.appendSeparator(paramResourceBundle.getString("PeriodFormat.commaspace"), paramResourceBundle.getString("PeriodFormat.spaceandspace"), arrayOfString);
/* 260 */     periodFormatterBuilder.appendDays();
/* 261 */     if (containsKey(paramResourceBundle, "PeriodFormat.days.regex")) {
/* 262 */       periodFormatterBuilder.appendSuffix(paramResourceBundle.getString("PeriodFormat.days.regex").split(str), paramResourceBundle.getString("PeriodFormat.days.list").split(str));
/*     */     }
/*     */     else {
/*     */       
/* 266 */       periodFormatterBuilder.appendSuffix(paramResourceBundle.getString("PeriodFormat.day"), paramResourceBundle.getString("PeriodFormat.days"));
/*     */     } 
/*     */     
/* 269 */     periodFormatterBuilder.appendSeparator(paramResourceBundle.getString("PeriodFormat.commaspace"), paramResourceBundle.getString("PeriodFormat.spaceandspace"), arrayOfString);
/* 270 */     periodFormatterBuilder.appendHours();
/* 271 */     if (containsKey(paramResourceBundle, "PeriodFormat.hours.regex")) {
/* 272 */       periodFormatterBuilder.appendSuffix(paramResourceBundle.getString("PeriodFormat.hours.regex").split(str), paramResourceBundle.getString("PeriodFormat.hours.list").split(str));
/*     */     }
/*     */     else {
/*     */       
/* 276 */       periodFormatterBuilder.appendSuffix(paramResourceBundle.getString("PeriodFormat.hour"), paramResourceBundle.getString("PeriodFormat.hours"));
/*     */     } 
/*     */     
/* 279 */     periodFormatterBuilder.appendSeparator(paramResourceBundle.getString("PeriodFormat.commaspace"), paramResourceBundle.getString("PeriodFormat.spaceandspace"), arrayOfString);
/* 280 */     periodFormatterBuilder.appendMinutes();
/* 281 */     if (containsKey(paramResourceBundle, "PeriodFormat.minutes.regex")) {
/* 282 */       periodFormatterBuilder.appendSuffix(paramResourceBundle.getString("PeriodFormat.minutes.regex").split(str), paramResourceBundle.getString("PeriodFormat.minutes.list").split(str));
/*     */     }
/*     */     else {
/*     */       
/* 286 */       periodFormatterBuilder.appendSuffix(paramResourceBundle.getString("PeriodFormat.minute"), paramResourceBundle.getString("PeriodFormat.minutes"));
/*     */     } 
/*     */     
/* 289 */     periodFormatterBuilder.appendSeparator(paramResourceBundle.getString("PeriodFormat.commaspace"), paramResourceBundle.getString("PeriodFormat.spaceandspace"), arrayOfString);
/* 290 */     periodFormatterBuilder.appendSeconds();
/* 291 */     if (containsKey(paramResourceBundle, "PeriodFormat.seconds.regex")) {
/* 292 */       periodFormatterBuilder.appendSuffix(paramResourceBundle.getString("PeriodFormat.seconds.regex").split(str), paramResourceBundle.getString("PeriodFormat.seconds.list").split(str));
/*     */     }
/*     */     else {
/*     */       
/* 296 */       periodFormatterBuilder.appendSuffix(paramResourceBundle.getString("PeriodFormat.second"), paramResourceBundle.getString("PeriodFormat.seconds"));
/*     */     } 
/*     */     
/* 299 */     periodFormatterBuilder.appendSeparator(paramResourceBundle.getString("PeriodFormat.commaspace"), paramResourceBundle.getString("PeriodFormat.spaceandspace"), arrayOfString);
/* 300 */     periodFormatterBuilder.appendMillis();
/* 301 */     if (containsKey(paramResourceBundle, "PeriodFormat.milliseconds.regex")) {
/* 302 */       periodFormatterBuilder.appendSuffix(paramResourceBundle.getString("PeriodFormat.milliseconds.regex").split(str), paramResourceBundle.getString("PeriodFormat.milliseconds.list").split(str));
/*     */     }
/*     */     else {
/*     */       
/* 306 */       periodFormatterBuilder.appendSuffix(paramResourceBundle.getString("PeriodFormat.millisecond"), paramResourceBundle.getString("PeriodFormat.milliseconds"));
/*     */     } 
/* 308 */     return periodFormatterBuilder.toFormatter().withLocale(paramLocale);
/*     */   }
/*     */   
/*     */   private static PeriodFormatter buildNonRegExFormatter(ResourceBundle paramResourceBundle, Locale paramLocale) {
/* 312 */     String[] arrayOfString = retrieveVariants(paramResourceBundle);
/* 313 */     return (new PeriodFormatterBuilder()).appendYears().appendSuffix(paramResourceBundle.getString("PeriodFormat.year"), paramResourceBundle.getString("PeriodFormat.years")).appendSeparator(paramResourceBundle.getString("PeriodFormat.commaspace"), paramResourceBundle.getString("PeriodFormat.spaceandspace"), arrayOfString).appendMonths().appendSuffix(paramResourceBundle.getString("PeriodFormat.month"), paramResourceBundle.getString("PeriodFormat.months")).appendSeparator(paramResourceBundle.getString("PeriodFormat.commaspace"), paramResourceBundle.getString("PeriodFormat.spaceandspace"), arrayOfString).appendWeeks().appendSuffix(paramResourceBundle.getString("PeriodFormat.week"), paramResourceBundle.getString("PeriodFormat.weeks")).appendSeparator(paramResourceBundle.getString("PeriodFormat.commaspace"), paramResourceBundle.getString("PeriodFormat.spaceandspace"), arrayOfString).appendDays().appendSuffix(paramResourceBundle.getString("PeriodFormat.day"), paramResourceBundle.getString("PeriodFormat.days")).appendSeparator(paramResourceBundle.getString("PeriodFormat.commaspace"), paramResourceBundle.getString("PeriodFormat.spaceandspace"), arrayOfString).appendHours().appendSuffix(paramResourceBundle.getString("PeriodFormat.hour"), paramResourceBundle.getString("PeriodFormat.hours")).appendSeparator(paramResourceBundle.getString("PeriodFormat.commaspace"), paramResourceBundle.getString("PeriodFormat.spaceandspace"), arrayOfString).appendMinutes().appendSuffix(paramResourceBundle.getString("PeriodFormat.minute"), paramResourceBundle.getString("PeriodFormat.minutes")).appendSeparator(paramResourceBundle.getString("PeriodFormat.commaspace"), paramResourceBundle.getString("PeriodFormat.spaceandspace"), arrayOfString).appendSeconds().appendSuffix(paramResourceBundle.getString("PeriodFormat.second"), paramResourceBundle.getString("PeriodFormat.seconds")).appendSeparator(paramResourceBundle.getString("PeriodFormat.commaspace"), paramResourceBundle.getString("PeriodFormat.spaceandspace"), arrayOfString).appendMillis().appendSuffix(paramResourceBundle.getString("PeriodFormat.millisecond"), paramResourceBundle.getString("PeriodFormat.milliseconds")).toFormatter().withLocale(paramLocale);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String[] retrieveVariants(ResourceBundle paramResourceBundle) {
/* 341 */     return new String[] { paramResourceBundle.getString("PeriodFormat.space"), paramResourceBundle.getString("PeriodFormat.comma"), paramResourceBundle.getString("PeriodFormat.commandand"), paramResourceBundle.getString("PeriodFormat.commaspaceand") };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean containsKey(ResourceBundle paramResourceBundle, String paramString) {
/* 347 */     for (Enumeration<String> enumeration = paramResourceBundle.getKeys(); enumeration.hasMoreElements();) {
/* 348 */       if (((String)enumeration.nextElement()).equals(paramString)) {
/* 349 */         return true;
/*     */       }
/*     */     } 
/* 352 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class DynamicWordBased
/*     */     implements PeriodPrinter, PeriodParser
/*     */   {
/*     */     private final PeriodFormatter iFormatter;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     DynamicWordBased(PeriodFormatter param1PeriodFormatter) {
/* 367 */       this.iFormatter = param1PeriodFormatter;
/*     */     }
/*     */     
/*     */     public int countFieldsToPrint(ReadablePeriod param1ReadablePeriod, int param1Int, Locale param1Locale) {
/* 371 */       return getPrinter(param1Locale).countFieldsToPrint(param1ReadablePeriod, param1Int, param1Locale);
/*     */     }
/*     */     
/*     */     public int calculatePrintedLength(ReadablePeriod param1ReadablePeriod, Locale param1Locale) {
/* 375 */       return getPrinter(param1Locale).calculatePrintedLength(param1ReadablePeriod, param1Locale);
/*     */     }
/*     */     
/*     */     public void printTo(StringBuffer param1StringBuffer, ReadablePeriod param1ReadablePeriod, Locale param1Locale) {
/* 379 */       getPrinter(param1Locale).printTo(param1StringBuffer, param1ReadablePeriod, param1Locale);
/*     */     }
/*     */     
/*     */     public void printTo(Writer param1Writer, ReadablePeriod param1ReadablePeriod, Locale param1Locale) throws IOException {
/* 383 */       getPrinter(param1Locale).printTo(param1Writer, param1ReadablePeriod, param1Locale);
/*     */     }
/*     */     
/*     */     private PeriodPrinter getPrinter(Locale param1Locale) {
/* 387 */       if (param1Locale != null && !param1Locale.equals(this.iFormatter.getLocale())) {
/* 388 */         return PeriodFormat.wordBased(param1Locale).getPrinter();
/*     */       }
/* 390 */       return this.iFormatter.getPrinter();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int parseInto(ReadWritablePeriod param1ReadWritablePeriod, String param1String, int param1Int, Locale param1Locale) {
/* 396 */       return getParser(param1Locale).parseInto(param1ReadWritablePeriod, param1String, param1Int, param1Locale);
/*     */     }
/*     */     
/*     */     private PeriodParser getParser(Locale param1Locale) {
/* 400 */       if (param1Locale != null && !param1Locale.equals(this.iFormatter.getLocale())) {
/* 401 */         return PeriodFormat.wordBased(param1Locale).getParser();
/*     */       }
/* 403 */       return this.iFormatter.getParser();
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/format/PeriodFormat.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */