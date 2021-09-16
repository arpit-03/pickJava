/*      */ package org.joda.time;
/*      */ 
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.ObjectStreamException;
/*      */ import java.io.Serializable;
/*      */ import java.util.Collections;
/*      */ import java.util.HashMap;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.TimeZone;
/*      */ import java.util.concurrent.atomic.AtomicReference;
/*      */ import org.joda.convert.FromString;
/*      */ import org.joda.convert.ToString;
/*      */ import org.joda.time.chrono.BaseChronology;
/*      */ import org.joda.time.field.FieldUtils;
/*      */ import org.joda.time.format.DateTimeFormatter;
/*      */ import org.joda.time.format.DateTimeFormatterBuilder;
/*      */ import org.joda.time.format.FormatUtils;
/*      */ import org.joda.time.tz.DefaultNameProvider;
/*      */ import org.joda.time.tz.FixedDateTimeZone;
/*      */ import org.joda.time.tz.NameProvider;
/*      */ import org.joda.time.tz.Provider;
/*      */ import org.joda.time.tz.UTCProvider;
/*      */ import org.joda.time.tz.ZoneInfoProvider;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class DateTimeZone
/*      */   implements Serializable
/*      */ {
/*      */   private static final long serialVersionUID = 5546345482340108586L;
/*  108 */   public static final DateTimeZone UTC = UTCDateTimeZone.INSTANCE;
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int MAX_MILLIS = 86399999;
/*      */ 
/*      */ 
/*      */   
/*  116 */   private static final AtomicReference<Provider> cProvider = new AtomicReference<Provider>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  122 */   private static final AtomicReference<NameProvider> cNameProvider = new AtomicReference<NameProvider>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  128 */   private static final AtomicReference<DateTimeZone> cDefault = new AtomicReference<DateTimeZone>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final String iID;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static DateTimeZone getDefault() {
/*  145 */     DateTimeZone dateTimeZone = cDefault.get();
/*  146 */     if (dateTimeZone == null) {
/*      */       try {
/*      */         try {
/*  149 */           String str = System.getProperty("user.timezone");
/*  150 */           if (str != null) {
/*  151 */             dateTimeZone = forID(str);
/*      */           }
/*  153 */         } catch (RuntimeException runtimeException) {}
/*      */ 
/*      */         
/*  156 */         if (dateTimeZone == null) {
/*  157 */           dateTimeZone = forTimeZone(TimeZone.getDefault());
/*      */         }
/*  159 */       } catch (IllegalArgumentException illegalArgumentException) {}
/*      */ 
/*      */       
/*  162 */       if (dateTimeZone == null) {
/*  163 */         dateTimeZone = UTC;
/*      */       }
/*  165 */       if (!cDefault.compareAndSet(null, dateTimeZone)) {
/*  166 */         dateTimeZone = cDefault.get();
/*      */       }
/*      */     } 
/*  169 */     return dateTimeZone;
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
/*      */   public static void setDefault(DateTimeZone paramDateTimeZone) throws SecurityException {
/*  182 */     SecurityManager securityManager = System.getSecurityManager();
/*  183 */     if (securityManager != null) {
/*  184 */       securityManager.checkPermission(new JodaTimePermission("DateTimeZone.setDefault"));
/*      */     }
/*  186 */     if (paramDateTimeZone == null) {
/*  187 */       throw new IllegalArgumentException("The datetime zone must not be null");
/*      */     }
/*  189 */     cDefault.set(paramDateTimeZone);
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
/*      */   @FromString
/*      */   public static DateTimeZone forID(String paramString) {
/*  210 */     if (paramString == null) {
/*  211 */       return getDefault();
/*      */     }
/*  213 */     if (paramString.equals("UTC")) {
/*  214 */       return UTC;
/*      */     }
/*  216 */     DateTimeZone dateTimeZone = getProvider().getZone(paramString);
/*  217 */     if (dateTimeZone != null) {
/*  218 */       return dateTimeZone;
/*      */     }
/*  220 */     if (paramString.startsWith("+") || paramString.startsWith("-")) {
/*  221 */       int i = parseOffset(paramString);
/*  222 */       if (i == 0L) {
/*  223 */         return UTC;
/*      */       }
/*  225 */       paramString = printOffset(i);
/*  226 */       return fixedOffsetZone(paramString, i);
/*      */     } 
/*      */     
/*  229 */     throw new IllegalArgumentException("The datetime zone id '" + paramString + "' is not recognised");
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
/*      */   public static DateTimeZone forOffsetHours(int paramInt) throws IllegalArgumentException {
/*  243 */     return forOffsetHoursMinutes(paramInt, 0);
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
/*      */   public static DateTimeZone forOffsetHoursMinutes(int paramInt1, int paramInt2) throws IllegalArgumentException {
/*  278 */     if (paramInt1 == 0 && paramInt2 == 0) {
/*  279 */       return UTC;
/*      */     }
/*  281 */     if (paramInt1 < -23 || paramInt1 > 23) {
/*  282 */       throw new IllegalArgumentException("Hours out of range: " + paramInt1);
/*      */     }
/*  284 */     if (paramInt2 < -59 || paramInt2 > 59) {
/*  285 */       throw new IllegalArgumentException("Minutes out of range: " + paramInt2);
/*      */     }
/*  287 */     if (paramInt1 > 0 && paramInt2 < 0) {
/*  288 */       throw new IllegalArgumentException("Positive hours must not have negative minutes: " + paramInt2);
/*      */     }
/*  290 */     int i = 0;
/*      */     try {
/*  292 */       int j = paramInt1 * 60;
/*  293 */       if (j < 0) {
/*  294 */         paramInt2 = j - Math.abs(paramInt2);
/*      */       } else {
/*  296 */         paramInt2 = j + paramInt2;
/*      */       } 
/*  298 */       i = FieldUtils.safeMultiply(paramInt2, 60000);
/*  299 */     } catch (ArithmeticException arithmeticException) {
/*  300 */       throw new IllegalArgumentException("Offset is too large");
/*      */     } 
/*  302 */     return forOffsetMillis(i);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static DateTimeZone forOffsetMillis(int paramInt) {
/*  312 */     if (paramInt < -86399999 || paramInt > 86399999) {
/*  313 */       throw new IllegalArgumentException("Millis out of range: " + paramInt);
/*      */     }
/*  315 */     String str = printOffset(paramInt);
/*  316 */     return fixedOffsetZone(str, paramInt);
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
/*      */   public static DateTimeZone forTimeZone(TimeZone paramTimeZone) {
/*  335 */     if (paramTimeZone == null) {
/*  336 */       return getDefault();
/*      */     }
/*  338 */     String str1 = paramTimeZone.getID();
/*  339 */     if (str1 == null) {
/*  340 */       throw new IllegalArgumentException("The TimeZone id must not be null");
/*      */     }
/*  342 */     if (str1.equals("UTC")) {
/*  343 */       return UTC;
/*      */     }
/*      */ 
/*      */     
/*  347 */     DateTimeZone dateTimeZone = null;
/*  348 */     String str2 = getConvertedId(str1);
/*  349 */     Provider provider = getProvider();
/*  350 */     if (str2 != null) {
/*  351 */       dateTimeZone = provider.getZone(str2);
/*      */     }
/*  353 */     if (dateTimeZone == null) {
/*  354 */       dateTimeZone = provider.getZone(str1);
/*      */     }
/*  356 */     if (dateTimeZone != null) {
/*  357 */       return dateTimeZone;
/*      */     }
/*      */ 
/*      */     
/*  361 */     if (str2 == null) {
/*  362 */       str2 = str1;
/*  363 */       if (str2.startsWith("GMT+") || str2.startsWith("GMT-")) {
/*  364 */         str2 = str2.substring(3);
/*  365 */         int i = parseOffset(str2);
/*  366 */         if (i == 0L) {
/*  367 */           return UTC;
/*      */         }
/*  369 */         str2 = printOffset(i);
/*  370 */         return fixedOffsetZone(str2, i);
/*      */       } 
/*      */     } 
/*      */     
/*  374 */     throw new IllegalArgumentException("The datetime zone id '" + str1 + "' is not recognised");
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
/*      */   private static DateTimeZone fixedOffsetZone(String paramString, int paramInt) {
/*  386 */     if (paramInt == 0) {
/*  387 */       return UTC;
/*      */     }
/*  389 */     return (DateTimeZone)new FixedDateTimeZone(paramString, null, paramInt, paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Set<String> getAvailableIDs() {
/*  398 */     return getProvider().getAvailableIDs();
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
/*      */   public static Provider getProvider() {
/*  411 */     Provider provider = cProvider.get();
/*  412 */     if (provider == null) {
/*  413 */       provider = getDefaultProvider();
/*  414 */       if (!cProvider.compareAndSet(null, provider)) {
/*  415 */         provider = cProvider.get();
/*      */       }
/*      */     } 
/*  418 */     return provider;
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
/*      */   public static void setProvider(Provider paramProvider) throws SecurityException {
/*  432 */     SecurityManager securityManager = System.getSecurityManager();
/*  433 */     if (securityManager != null) {
/*  434 */       securityManager.checkPermission(new JodaTimePermission("DateTimeZone.setProvider"));
/*      */     }
/*  436 */     if (paramProvider == null) {
/*  437 */       paramProvider = getDefaultProvider();
/*      */     } else {
/*  439 */       validateProvider(paramProvider);
/*      */     } 
/*  441 */     cProvider.set(paramProvider);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Provider validateProvider(Provider paramProvider) {
/*  452 */     Set set = paramProvider.getAvailableIDs();
/*  453 */     if (set == null || set.size() == 0) {
/*  454 */       throw new IllegalArgumentException("The provider doesn't have any available ids");
/*      */     }
/*  456 */     if (!set.contains("UTC")) {
/*  457 */       throw new IllegalArgumentException("The provider doesn't support UTC");
/*      */     }
/*  459 */     if (!UTC.equals(paramProvider.getZone("UTC"))) {
/*  460 */       throw new IllegalArgumentException("Invalid UTC zone provided");
/*      */     }
/*  462 */     return paramProvider;
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
/*      */   private static Provider getDefaultProvider() {
/*      */     try {
/*  486 */       String str = System.getProperty("org.joda.time.DateTimeZone.Provider");
/*  487 */       if (str != null) {
/*      */         try {
/*  489 */           Provider provider = (Provider)Class.forName(str).newInstance();
/*  490 */           return validateProvider(provider);
/*  491 */         } catch (Exception exception) {
/*  492 */           throw new RuntimeException(exception);
/*      */         } 
/*      */       }
/*  495 */     } catch (SecurityException securityException) {}
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  500 */       String str = System.getProperty("org.joda.time.DateTimeZone.Folder");
/*  501 */       if (str != null) {
/*      */         try {
/*  503 */           ZoneInfoProvider zoneInfoProvider = new ZoneInfoProvider(new File(str));
/*  504 */           return validateProvider((Provider)zoneInfoProvider);
/*  505 */         } catch (Exception exception) {
/*  506 */           throw new RuntimeException(exception);
/*      */         } 
/*      */       }
/*  509 */     } catch (SecurityException securityException) {}
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  514 */       ZoneInfoProvider zoneInfoProvider = new ZoneInfoProvider("org/joda/time/tz/data");
/*  515 */       return validateProvider((Provider)zoneInfoProvider);
/*  516 */     } catch (Exception exception) {
/*  517 */       exception.printStackTrace();
/*      */ 
/*      */       
/*  520 */       return (Provider)new UTCProvider();
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
/*      */   public static NameProvider getNameProvider() {
/*  533 */     NameProvider nameProvider = cNameProvider.get();
/*  534 */     if (nameProvider == null) {
/*  535 */       nameProvider = getDefaultNameProvider();
/*  536 */       if (!cNameProvider.compareAndSet(null, nameProvider)) {
/*  537 */         nameProvider = cNameProvider.get();
/*      */       }
/*      */     } 
/*  540 */     return nameProvider;
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
/*      */   public static void setNameProvider(NameProvider paramNameProvider) throws SecurityException {
/*  554 */     SecurityManager securityManager = System.getSecurityManager();
/*  555 */     if (securityManager != null) {
/*  556 */       securityManager.checkPermission(new JodaTimePermission("DateTimeZone.setNameProvider"));
/*      */     }
/*  558 */     if (paramNameProvider == null) {
/*  559 */       paramNameProvider = getDefaultNameProvider();
/*      */     }
/*  561 */     cNameProvider.set(paramNameProvider);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static NameProvider getDefaultNameProvider() {
/*      */     DefaultNameProvider defaultNameProvider;
/*  573 */     NameProvider nameProvider = null;
/*      */     try {
/*  575 */       String str = System.getProperty("org.joda.time.DateTimeZone.NameProvider");
/*  576 */       if (str != null) {
/*      */         try {
/*  578 */           nameProvider = (NameProvider)Class.forName(str).newInstance();
/*  579 */         } catch (Exception exception) {
/*  580 */           throw new RuntimeException(exception);
/*      */         } 
/*      */       }
/*  583 */     } catch (SecurityException securityException) {}
/*      */ 
/*      */ 
/*      */     
/*  587 */     if (nameProvider == null) {
/*  588 */       defaultNameProvider = new DefaultNameProvider();
/*      */     }
/*      */     
/*  591 */     return (NameProvider)defaultNameProvider;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String getConvertedId(String paramString) {
/*  602 */     return LazyInit.CONVERSION_MAP.get(paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int parseOffset(String paramString) {
/*  612 */     return -((int)LazyInit.OFFSET_FORMATTER.parseMillis(paramString));
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
/*      */   private static String printOffset(int paramInt) {
/*  625 */     StringBuffer stringBuffer = new StringBuffer();
/*  626 */     if (paramInt >= 0) {
/*  627 */       stringBuffer.append('+');
/*      */     } else {
/*  629 */       stringBuffer.append('-');
/*  630 */       paramInt = -paramInt;
/*      */     } 
/*      */     
/*  633 */     int i = paramInt / 3600000;
/*  634 */     FormatUtils.appendPaddedInteger(stringBuffer, i, 2);
/*  635 */     paramInt -= i * 3600000;
/*      */     
/*  637 */     int j = paramInt / 60000;
/*  638 */     stringBuffer.append(':');
/*  639 */     FormatUtils.appendPaddedInteger(stringBuffer, j, 2);
/*  640 */     paramInt -= j * 60000;
/*  641 */     if (paramInt == 0) {
/*  642 */       return stringBuffer.toString();
/*      */     }
/*      */     
/*  645 */     int k = paramInt / 1000;
/*  646 */     stringBuffer.append(':');
/*  647 */     FormatUtils.appendPaddedInteger(stringBuffer, k, 2);
/*  648 */     paramInt -= k * 1000;
/*  649 */     if (paramInt == 0) {
/*  650 */       return stringBuffer.toString();
/*      */     }
/*      */     
/*  653 */     stringBuffer.append('.');
/*  654 */     FormatUtils.appendPaddedInteger(stringBuffer, paramInt, 3);
/*  655 */     return stringBuffer.toString();
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
/*      */   protected DateTimeZone(String paramString) {
/*  670 */     if (paramString == null) {
/*  671 */       throw new IllegalArgumentException("Id must not be null");
/*      */     }
/*  673 */     this.iID = paramString;
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
/*      */   @ToString
/*      */   public final String getID() {
/*  686 */     return this.iID;
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
/*      */   public abstract String getNameKey(long paramLong);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final String getShortName(long paramLong) {
/*  709 */     return getShortName(paramLong, null);
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
/*      */   public String getShortName(long paramLong, Locale paramLocale) {
/*      */     String str2;
/*  724 */     if (paramLocale == null) {
/*  725 */       paramLocale = Locale.getDefault();
/*      */     }
/*  727 */     String str1 = getNameKey(paramLong);
/*  728 */     if (str1 == null) {
/*  729 */       return this.iID;
/*      */     }
/*      */     
/*  732 */     NameProvider nameProvider = getNameProvider();
/*  733 */     if (nameProvider instanceof DefaultNameProvider) {
/*  734 */       str2 = ((DefaultNameProvider)nameProvider).getShortName(paramLocale, this.iID, str1, isStandardOffset(paramLong));
/*      */     } else {
/*  736 */       str2 = nameProvider.getShortName(paramLocale, this.iID, str1);
/*      */     } 
/*  738 */     if (str2 != null) {
/*  739 */       return str2;
/*      */     }
/*  741 */     return printOffset(getOffset(paramLong));
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
/*      */   public final String getName(long paramLong) {
/*  755 */     return getName(paramLong, null);
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
/*      */   public String getName(long paramLong, Locale paramLocale) {
/*      */     String str2;
/*  770 */     if (paramLocale == null) {
/*  771 */       paramLocale = Locale.getDefault();
/*      */     }
/*  773 */     String str1 = getNameKey(paramLong);
/*  774 */     if (str1 == null) {
/*  775 */       return this.iID;
/*      */     }
/*      */     
/*  778 */     NameProvider nameProvider = getNameProvider();
/*  779 */     if (nameProvider instanceof DefaultNameProvider) {
/*  780 */       str2 = ((DefaultNameProvider)nameProvider).getName(paramLocale, this.iID, str1, isStandardOffset(paramLong));
/*      */     } else {
/*  782 */       str2 = nameProvider.getName(paramLocale, this.iID, str1);
/*      */     } 
/*  784 */     if (str2 != null) {
/*  785 */       return str2;
/*      */     }
/*  787 */     return printOffset(getOffset(paramLong));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract int getOffset(long paramLong);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final int getOffset(ReadableInstant paramReadableInstant) {
/*  805 */     if (paramReadableInstant == null) {
/*  806 */       return getOffset(DateTimeUtils.currentTimeMillis());
/*      */     }
/*  808 */     return getOffset(paramReadableInstant.getMillis());
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
/*      */   public abstract int getStandardOffset(long paramLong);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isStandardOffset(long paramLong) {
/*  836 */     return (getOffset(paramLong) == getStandardOffset(paramLong));
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
/*      */ 
/*      */ 
/*      */   
/*      */   public int getOffsetFromLocal(long paramLong) {
/*  877 */     int i = getOffset(paramLong);
/*      */     
/*  879 */     long l = paramLong - i;
/*  880 */     int j = getOffset(l);
/*      */     
/*  882 */     if (i != j) {
/*      */ 
/*      */       
/*  885 */       if (i - j < 0) {
/*      */ 
/*      */ 
/*      */         
/*  889 */         long l1 = nextTransition(l);
/*  890 */         if (l1 == paramLong - i) {
/*  891 */           l1 = Long.MAX_VALUE;
/*      */         }
/*  893 */         long l2 = nextTransition(paramLong - j);
/*  894 */         if (l2 == paramLong - j) {
/*  895 */           l2 = Long.MAX_VALUE;
/*      */         }
/*  897 */         if (l1 != l2) {
/*  898 */           return i;
/*      */         }
/*      */       } 
/*  901 */     } else if (i >= 0) {
/*  902 */       long l1 = previousTransition(l);
/*  903 */       if (l1 < l) {
/*  904 */         int k = getOffset(l1);
/*  905 */         int m = k - i;
/*  906 */         if (l - l1 <= m) {
/*  907 */           return k;
/*      */         }
/*      */       } 
/*      */     } 
/*  911 */     return j;
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
/*      */   public long convertUTCToLocal(long paramLong) {
/*  925 */     int i = getOffset(paramLong);
/*  926 */     long l = paramLong + i;
/*      */     
/*  928 */     if ((paramLong ^ l) < 0L && (paramLong ^ i) >= 0L) {
/*  929 */       throw new ArithmeticException("Adding time zone offset caused overflow");
/*      */     }
/*  931 */     return l;
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
/*      */   public long convertLocalToUTC(long paramLong1, boolean paramBoolean, long paramLong2) {
/*  952 */     int i = getOffset(paramLong2);
/*  953 */     long l = paramLong1 - i;
/*  954 */     int j = getOffset(l);
/*  955 */     if (j == i) {
/*  956 */       return l;
/*      */     }
/*  958 */     return convertLocalToUTC(paramLong1, paramBoolean);
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
/*      */   public long convertLocalToUTC(long paramLong, boolean paramBoolean) {
/*  975 */     int i = getOffset(paramLong);
/*      */     
/*  977 */     int j = getOffset(paramLong - i);
/*      */     
/*  979 */     if (i != j)
/*      */     {
/*      */ 
/*      */       
/*  983 */       if (paramBoolean || i < 0) {
/*      */         
/*  985 */         long l1 = nextTransition(paramLong - i);
/*  986 */         if (l1 == paramLong - i) {
/*  987 */           l1 = Long.MAX_VALUE;
/*      */         }
/*  989 */         long l2 = nextTransition(paramLong - j);
/*  990 */         if (l2 == paramLong - j) {
/*  991 */           l2 = Long.MAX_VALUE;
/*      */         }
/*  993 */         if (l1 != l2) {
/*      */           
/*  995 */           if (paramBoolean)
/*      */           {
/*  997 */             throw new IllegalInstantException(paramLong, getID());
/*      */           }
/*      */ 
/*      */ 
/*      */           
/* 1002 */           j = i;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/* 1008 */     long l = paramLong - j;
/*      */     
/* 1010 */     if ((paramLong ^ l) < 0L && (paramLong ^ j) < 0L) {
/* 1011 */       throw new ArithmeticException("Subtracting time zone offset caused overflow");
/*      */     }
/* 1013 */     return l;
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
/*      */   public long getMillisKeepLocal(DateTimeZone paramDateTimeZone, long paramLong) {
/* 1027 */     if (paramDateTimeZone == null) {
/* 1028 */       paramDateTimeZone = getDefault();
/*      */     }
/* 1030 */     if (paramDateTimeZone == this) {
/* 1031 */       return paramLong;
/*      */     }
/* 1033 */     long l = convertUTCToLocal(paramLong);
/* 1034 */     return paramDateTimeZone.convertLocalToUTC(l, false, paramLong);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isLocalDateTimeGap(LocalDateTime paramLocalDateTime) {
/* 1144 */     if (isFixed()) {
/* 1145 */       return false;
/*      */     }
/*      */     try {
/* 1148 */       paramLocalDateTime.toDateTime(this);
/* 1149 */       return false;
/* 1150 */     } catch (IllegalInstantException illegalInstantException) {
/* 1151 */       return true;
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
/*      */   public long adjustOffset(long paramLong, boolean paramBoolean) {
/* 1166 */     long l1 = paramLong - 10800000L;
/* 1167 */     long l2 = paramLong + 10800000L;
/* 1168 */     long l3 = getOffset(l1);
/* 1169 */     long l4 = getOffset(l2);
/* 1170 */     if (l3 <= l4) {
/* 1171 */       return paramLong;
/*      */     }
/*      */ 
/*      */     
/* 1175 */     long l5 = l3 - l4;
/* 1176 */     long l6 = nextTransition(l1);
/* 1177 */     long l7 = l6 - l5;
/* 1178 */     long l8 = l6 + l5;
/* 1179 */     if (paramLong < l7 || paramLong >= l8) {
/* 1180 */       return paramLong;
/*      */     }
/*      */ 
/*      */     
/* 1184 */     long l9 = paramLong - l7;
/* 1185 */     if (l9 >= l5)
/*      */     {
/* 1187 */       return paramBoolean ? paramLong : (paramLong - l5);
/*      */     }
/*      */     
/* 1190 */     return paramBoolean ? (paramLong + l5) : paramLong;
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
/*      */   public abstract boolean isFixed();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract long nextTransition(long paramLong);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract long previousTransition(long paramLong);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TimeZone toTimeZone() {
/* 1232 */     return TimeZone.getTimeZone(this.iID);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract boolean equals(Object paramObject);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int hashCode() {
/* 1249 */     return 57 + getID().hashCode();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1257 */     return getID();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Object writeReplace() throws ObjectStreamException {
/* 1267 */     return new Stub(this.iID);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class Stub
/*      */     implements Serializable
/*      */   {
/*      */     private static final long serialVersionUID = -6471952376487863581L;
/*      */ 
/*      */     
/*      */     private transient String iID;
/*      */ 
/*      */ 
/*      */     
/*      */     Stub(String param1String) {
/* 1284 */       this.iID = param1String;
/*      */     }
/*      */     
/*      */     private void writeObject(ObjectOutputStream param1ObjectOutputStream) throws IOException {
/* 1288 */       param1ObjectOutputStream.writeUTF(this.iID);
/*      */     }
/*      */     
/*      */     private void readObject(ObjectInputStream param1ObjectInputStream) throws IOException {
/* 1292 */       this.iID = param1ObjectInputStream.readUTF();
/*      */     }
/*      */     
/*      */     private Object readResolve() throws ObjectStreamException {
/* 1296 */       return DateTimeZone.forID(this.iID);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final class LazyInit
/*      */   {
/* 1307 */     static final Map<String, String> CONVERSION_MAP = buildMap();
/*      */     
/* 1309 */     static final DateTimeFormatter OFFSET_FORMATTER = buildFormatter();
/*      */ 
/*      */ 
/*      */     
/*      */     private static DateTimeFormatter buildFormatter() {
/* 1314 */       BaseChronology baseChronology = new BaseChronology()
/*      */         {
/*      */           public DateTimeZone getZone() {
/* 1317 */             return null;
/*      */           } private static final long serialVersionUID = -3128740902654445468L;
/*      */           public Chronology withUTC() {
/* 1320 */             return (Chronology)this;
/*      */           }
/*      */           public Chronology withZone(DateTimeZone param2DateTimeZone) {
/* 1323 */             return (Chronology)this;
/*      */           }
/*      */           public String toString() {
/* 1326 */             return getClass().getName();
/*      */           }
/*      */         };
/* 1329 */       return (new DateTimeFormatterBuilder()).appendTimeZoneOffset(null, true, 2, 4).toFormatter().withChronology((Chronology)baseChronology);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private static Map<String, String> buildMap() {
/* 1337 */       HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
/* 1338 */       hashMap.put("GMT", "UTC");
/* 1339 */       hashMap.put("WET", "WET");
/* 1340 */       hashMap.put("CET", "CET");
/* 1341 */       hashMap.put("MET", "CET");
/* 1342 */       hashMap.put("ECT", "CET");
/* 1343 */       hashMap.put("EET", "EET");
/* 1344 */       hashMap.put("MIT", "Pacific/Apia");
/* 1345 */       hashMap.put("HST", "Pacific/Honolulu");
/* 1346 */       hashMap.put("AST", "America/Anchorage");
/* 1347 */       hashMap.put("PST", "America/Los_Angeles");
/* 1348 */       hashMap.put("MST", "America/Denver");
/* 1349 */       hashMap.put("PNT", "America/Phoenix");
/* 1350 */       hashMap.put("CST", "America/Chicago");
/* 1351 */       hashMap.put("EST", "America/New_York");
/* 1352 */       hashMap.put("IET", "America/Indiana/Indianapolis");
/* 1353 */       hashMap.put("PRT", "America/Puerto_Rico");
/* 1354 */       hashMap.put("CNT", "America/St_Johns");
/* 1355 */       hashMap.put("AGT", "America/Argentina/Buenos_Aires");
/* 1356 */       hashMap.put("BET", "America/Sao_Paulo");
/* 1357 */       hashMap.put("ART", "Africa/Cairo");
/* 1358 */       hashMap.put("CAT", "Africa/Harare");
/* 1359 */       hashMap.put("EAT", "Africa/Addis_Ababa");
/* 1360 */       hashMap.put("NET", "Asia/Yerevan");
/* 1361 */       hashMap.put("PLT", "Asia/Karachi");
/* 1362 */       hashMap.put("IST", "Asia/Kolkata");
/* 1363 */       hashMap.put("BST", "Asia/Dhaka");
/* 1364 */       hashMap.put("VST", "Asia/Ho_Chi_Minh");
/* 1365 */       hashMap.put("CTT", "Asia/Shanghai");
/* 1366 */       hashMap.put("JST", "Asia/Tokyo");
/* 1367 */       hashMap.put("ACT", "Australia/Darwin");
/* 1368 */       hashMap.put("AET", "Australia/Sydney");
/* 1369 */       hashMap.put("SST", "Pacific/Guadalcanal");
/* 1370 */       hashMap.put("NST", "Pacific/Auckland");
/* 1371 */       return (Map)Collections.unmodifiableMap(hashMap);
/*      */     }
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/DateTimeZone.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */