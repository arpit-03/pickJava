/*     */ package org.joda.time;
/*     */ 
/*     */ import java.lang.reflect.Method;
/*     */ import java.text.DateFormatSymbols;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.atomic.AtomicReference;
/*     */ import org.joda.time.chrono.ISOChronology;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DateTimeUtils
/*     */ {
/*  44 */   public static final MillisProvider SYSTEM_MILLIS_PROVIDER = new SystemMillisProvider();
/*     */ 
/*     */   
/*  47 */   private static volatile MillisProvider cMillisProvider = SYSTEM_MILLIS_PROVIDER;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  52 */   private static final AtomicReference<Map<String, DateTimeZone>> cZoneNames = new AtomicReference<Map<String, DateTimeZone>>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final long currentTimeMillis() {
/*  72 */     return cMillisProvider.getMillis();
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
/*     */   public static final void setCurrentMillisSystem() throws SecurityException {
/*  84 */     checkPermission();
/*  85 */     cMillisProvider = SYSTEM_MILLIS_PROVIDER;
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
/*     */   public static final void setCurrentMillisFixed(long paramLong) throws SecurityException {
/*  98 */     checkPermission();
/*  99 */     cMillisProvider = new FixedMillisProvider(paramLong);
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
/*     */   public static final void setCurrentMillisOffset(long paramLong) throws SecurityException {
/* 113 */     checkPermission();
/* 114 */     if (paramLong == 0L) {
/* 115 */       cMillisProvider = SYSTEM_MILLIS_PROVIDER;
/*     */     } else {
/* 117 */       cMillisProvider = new OffsetMillisProvider(paramLong);
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
/*     */   
/*     */   public static final void setCurrentMillisProvider(MillisProvider paramMillisProvider) throws SecurityException {
/* 132 */     if (paramMillisProvider == null) {
/* 133 */       throw new IllegalArgumentException("The MillisProvider must not be null");
/*     */     }
/* 135 */     checkPermission();
/* 136 */     cMillisProvider = paramMillisProvider;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void checkPermission() throws SecurityException {
/* 145 */     SecurityManager securityManager = System.getSecurityManager();
/* 146 */     if (securityManager != null) {
/* 147 */       securityManager.checkPermission(new JodaTimePermission("CurrentTime.setProvider"));
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
/*     */   
/*     */   public static final long getInstantMillis(ReadableInstant paramReadableInstant) {
/* 162 */     if (paramReadableInstant == null) {
/* 163 */       return currentTimeMillis();
/*     */     }
/* 165 */     return paramReadableInstant.getMillis();
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
/*     */   public static final Chronology getInstantChronology(ReadableInstant paramReadableInstant) {
/* 180 */     if (paramReadableInstant == null) {
/* 181 */       return (Chronology)ISOChronology.getInstance();
/*     */     }
/* 183 */     Chronology chronology = paramReadableInstant.getChronology();
/* 184 */     if (chronology == null) {
/* 185 */       return (Chronology)ISOChronology.getInstance();
/*     */     }
/* 187 */     return chronology;
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
/*     */   public static final Chronology getIntervalChronology(ReadableInstant paramReadableInstant1, ReadableInstant paramReadableInstant2) {
/*     */     ISOChronology iSOChronology;
/* 203 */     Chronology chronology = null;
/* 204 */     if (paramReadableInstant1 != null) {
/* 205 */       chronology = paramReadableInstant1.getChronology();
/* 206 */     } else if (paramReadableInstant2 != null) {
/* 207 */       chronology = paramReadableInstant2.getChronology();
/*     */     } 
/* 209 */     if (chronology == null) {
/* 210 */       iSOChronology = ISOChronology.getInstance();
/*     */     }
/* 212 */     return (Chronology)iSOChronology;
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
/*     */   public static final Chronology getIntervalChronology(ReadableInterval paramReadableInterval) {
/* 227 */     if (paramReadableInterval == null) {
/* 228 */       return (Chronology)ISOChronology.getInstance();
/*     */     }
/* 230 */     Chronology chronology = paramReadableInterval.getChronology();
/* 231 */     if (chronology == null) {
/* 232 */       return (Chronology)ISOChronology.getInstance();
/*     */     }
/* 234 */     return chronology;
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
/*     */   public static final ReadableInterval getReadableInterval(ReadableInterval paramReadableInterval) {
/* 250 */     if (paramReadableInterval == null) {
/* 251 */       long l = currentTimeMillis();
/* 252 */       paramReadableInterval = new Interval(l, l);
/*     */     } 
/* 254 */     return paramReadableInterval;
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
/*     */   public static final Chronology getChronology(Chronology paramChronology) {
/* 268 */     if (paramChronology == null) {
/* 269 */       return (Chronology)ISOChronology.getInstance();
/*     */     }
/* 271 */     return paramChronology;
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
/*     */   public static final DateTimeZone getZone(DateTimeZone paramDateTimeZone) {
/* 285 */     if (paramDateTimeZone == null) {
/* 286 */       return DateTimeZone.getDefault();
/*     */     }
/* 288 */     return paramDateTimeZone;
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
/*     */   public static final PeriodType getPeriodType(PeriodType paramPeriodType) {
/* 302 */     if (paramPeriodType == null) {
/* 303 */       return PeriodType.standard();
/*     */     }
/* 305 */     return paramPeriodType;
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
/*     */   public static final long getDurationMillis(ReadableDuration paramReadableDuration) {
/* 319 */     if (paramReadableDuration == null) {
/* 320 */       return 0L;
/*     */     }
/* 322 */     return paramReadableDuration.getMillis();
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
/*     */   public static final boolean isContiguous(ReadablePartial paramReadablePartial) {
/* 350 */     if (paramReadablePartial == null) {
/* 351 */       throw new IllegalArgumentException("Partial must not be null");
/*     */     }
/* 353 */     DurationFieldType durationFieldType = null;
/* 354 */     for (byte b = 0; b < paramReadablePartial.size(); b++) {
/* 355 */       DateTimeField dateTimeField = paramReadablePartial.getField(b);
/* 356 */       if (b > 0 && (
/* 357 */         dateTimeField.getRangeDurationField() == null || dateTimeField.getRangeDurationField().getType() != durationFieldType)) {
/* 358 */         return false;
/*     */       }
/*     */       
/* 361 */       durationFieldType = dateTimeField.getDurationField().getType();
/*     */     } 
/* 363 */     return true;
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
/*     */   public static final DateFormatSymbols getDateFormatSymbols(Locale paramLocale) {
/*     */     try {
/* 381 */       Method method = DateFormatSymbols.class.getMethod("getInstance", new Class[] { Locale.class });
/* 382 */       return (DateFormatSymbols)method.invoke(null, new Object[] { paramLocale });
/* 383 */     } catch (Exception exception) {
/* 384 */       return new DateFormatSymbols(paramLocale);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final Map<String, DateTimeZone> getDefaultTimeZoneNames() {
/* 413 */     Map<String, DateTimeZone> map = cZoneNames.get();
/* 414 */     if (map == null) {
/* 415 */       map = buildDefaultTimeZoneNames();
/* 416 */       if (!cZoneNames.compareAndSet(null, map)) {
/* 417 */         map = cZoneNames.get();
/*     */       }
/*     */     } 
/* 420 */     return map;
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
/*     */   public static final void setDefaultTimeZoneNames(Map<String, DateTimeZone> paramMap) {
/* 432 */     cZoneNames.set(Collections.unmodifiableMap(new HashMap<String, DateTimeZone>(paramMap)));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static Map<String, DateTimeZone> buildDefaultTimeZoneNames() {
/* 438 */     LinkedHashMap<Object, Object> linkedHashMap = new LinkedHashMap<Object, Object>();
/* 439 */     linkedHashMap.put("UT", DateTimeZone.UTC);
/* 440 */     linkedHashMap.put("UTC", DateTimeZone.UTC);
/* 441 */     linkedHashMap.put("GMT", DateTimeZone.UTC);
/* 442 */     put((Map)linkedHashMap, "EST", "America/New_York");
/* 443 */     put((Map)linkedHashMap, "EDT", "America/New_York");
/* 444 */     put((Map)linkedHashMap, "CST", "America/Chicago");
/* 445 */     put((Map)linkedHashMap, "CDT", "America/Chicago");
/* 446 */     put((Map)linkedHashMap, "MST", "America/Denver");
/* 447 */     put((Map)linkedHashMap, "MDT", "America/Denver");
/* 448 */     put((Map)linkedHashMap, "PST", "America/Los_Angeles");
/* 449 */     put((Map)linkedHashMap, "PDT", "America/Los_Angeles");
/* 450 */     return (Map)Collections.unmodifiableMap(linkedHashMap);
/*     */   }
/*     */   private static void put(Map<String, DateTimeZone> paramMap, String paramString1, String paramString2) {
/*     */     try {
/* 454 */       paramMap.put(paramString1, DateTimeZone.forID(paramString2));
/* 455 */     } catch (RuntimeException runtimeException) {}
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
/*     */   public static final double toJulianDay(long paramLong) {
/* 483 */     double d = paramLong / 8.64E7D;
/* 484 */     return d + 2440587.5D;
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
/*     */   public static final long toJulianDayNumber(long paramLong) {
/* 503 */     return (long)Math.floor(toJulianDay(paramLong) + 0.5D);
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
/*     */   public static final long fromJulianDay(double paramDouble) {
/* 516 */     double d = paramDouble - 2440587.5D;
/* 517 */     return (long)(d * 8.64E7D);
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
/*     */   public static interface MillisProvider
/*     */   {
/*     */     long getMillis();
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
/*     */   static class SystemMillisProvider
/*     */     implements MillisProvider
/*     */   {
/*     */     public long getMillis() {
/* 547 */       return System.currentTimeMillis();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class FixedMillisProvider
/*     */     implements MillisProvider
/*     */   {
/*     */     private final long iMillis;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     FixedMillisProvider(long param1Long) {
/* 563 */       this.iMillis = param1Long;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public long getMillis() {
/* 571 */       return this.iMillis;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class OffsetMillisProvider
/*     */     implements MillisProvider
/*     */   {
/*     */     private final long iMillis;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     OffsetMillisProvider(long param1Long) {
/* 587 */       this.iMillis = param1Long;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public long getMillis() {
/* 595 */       return System.currentTimeMillis() + this.iMillis;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/DateTimeUtils.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */