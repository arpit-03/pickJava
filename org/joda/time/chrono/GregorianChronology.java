/*     */ package org.joda.time.chrono;
/*     */ 
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import org.joda.time.Chronology;
/*     */ import org.joda.time.DateTimeZone;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class GregorianChronology
/*     */   extends BasicGJChronology
/*     */ {
/*     */   private static final long serialVersionUID = -861407383323710522L;
/*     */   private static final long MILLIS_PER_YEAR = 31556952000L;
/*     */   private static final long MILLIS_PER_MONTH = 2629746000L;
/*     */   private static final int DAYS_0000_TO_1970 = 719527;
/*     */   private static final int MIN_YEAR = -292275054;
/*     */   private static final int MAX_YEAR = 292278993;
/*     */   private static final GregorianChronology INSTANCE_UTC;
/*  67 */   private static final ConcurrentHashMap<DateTimeZone, GregorianChronology[]> cCache = (ConcurrentHashMap)new ConcurrentHashMap<DateTimeZone, GregorianChronology>();
/*     */   
/*     */   static {
/*  70 */     INSTANCE_UTC = getInstance(DateTimeZone.UTC);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static GregorianChronology getInstanceUTC() {
/*  80 */     return INSTANCE_UTC;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static GregorianChronology getInstance() {
/*  89 */     return getInstance(DateTimeZone.getDefault(), 4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static GregorianChronology getInstance(DateTimeZone paramDateTimeZone) {
/*  99 */     return getInstance(paramDateTimeZone, 4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static GregorianChronology getInstance(DateTimeZone paramDateTimeZone, int paramInt) {
/*     */     GregorianChronology gregorianChronology;
/* 110 */     if (paramDateTimeZone == null) {
/* 111 */       paramDateTimeZone = DateTimeZone.getDefault();
/*     */     }
/*     */     
/* 114 */     GregorianChronology[] arrayOfGregorianChronology = cCache.get(paramDateTimeZone);
/* 115 */     if (arrayOfGregorianChronology == null) {
/* 116 */       arrayOfGregorianChronology = new GregorianChronology[7];
/* 117 */       GregorianChronology[] arrayOfGregorianChronology1 = cCache.putIfAbsent(paramDateTimeZone, arrayOfGregorianChronology);
/* 118 */       if (arrayOfGregorianChronology1 != null) {
/* 119 */         arrayOfGregorianChronology = arrayOfGregorianChronology1;
/*     */       }
/*     */     } 
/*     */     try {
/* 123 */       gregorianChronology = arrayOfGregorianChronology[paramInt - 1];
/* 124 */     } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
/* 125 */       throw new IllegalArgumentException("Invalid min days in first week: " + paramInt);
/*     */     } 
/*     */     
/* 128 */     if (gregorianChronology == null) {
/* 129 */       synchronized (arrayOfGregorianChronology) {
/* 130 */         gregorianChronology = arrayOfGregorianChronology[paramInt - 1];
/* 131 */         if (gregorianChronology == null) {
/* 132 */           if (paramDateTimeZone == DateTimeZone.UTC) {
/* 133 */             gregorianChronology = new GregorianChronology(null, null, paramInt);
/*     */           } else {
/* 135 */             gregorianChronology = getInstance(DateTimeZone.UTC, paramInt);
/* 136 */             gregorianChronology = new GregorianChronology(ZonedChronology.getInstance(gregorianChronology, paramDateTimeZone), null, paramInt);
/*     */           } 
/*     */           
/* 139 */           arrayOfGregorianChronology[paramInt - 1] = gregorianChronology;
/*     */         } 
/*     */       } 
/*     */     }
/* 143 */     return gregorianChronology;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private GregorianChronology(Chronology paramChronology, Object paramObject, int paramInt) {
/* 153 */     super(paramChronology, paramObject, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Object readResolve() {
/* 160 */     Chronology chronology = getBase();
/* 161 */     int i = getMinimumDaysInFirstWeek();
/* 162 */     i = (i == 0) ? 4 : i;
/* 163 */     return (chronology == null) ? getInstance(DateTimeZone.UTC, i) : getInstance(chronology.getZone(), i);
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
/*     */   public Chronology withUTC() {
/* 176 */     return INSTANCE_UTC;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Chronology withZone(DateTimeZone paramDateTimeZone) {
/* 186 */     if (paramDateTimeZone == null) {
/* 187 */       paramDateTimeZone = DateTimeZone.getDefault();
/*     */     }
/* 189 */     if (paramDateTimeZone == getZone()) {
/* 190 */       return this;
/*     */     }
/* 192 */     return getInstance(paramDateTimeZone);
/*     */   }
/*     */   
/*     */   protected void assemble(AssembledChronology.Fields paramFields) {
/* 196 */     if (getBase() == null) {
/* 197 */       super.assemble(paramFields);
/*     */     }
/*     */   }
/*     */   
/*     */   boolean isLeapYear(int paramInt) {
/* 202 */     return ((paramInt & 0x3) == 0 && (paramInt % 100 != 0 || paramInt % 400 == 0));
/*     */   }
/*     */ 
/*     */   
/*     */   long calculateFirstDayOfYearMillis(int paramInt) {
/* 207 */     int i = paramInt / 100;
/* 208 */     if (paramInt < 0) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 214 */       i = (paramInt + 3 >> 2) - i + (i + 3 >> 2) - 1;
/*     */     } else {
/* 216 */       i = (paramInt >> 2) - i + (i >> 2);
/* 217 */       if (isLeapYear(paramInt)) {
/* 218 */         i--;
/*     */       }
/*     */     } 
/*     */     
/* 222 */     return (paramInt * 365L + (i - 719527)) * 86400000L;
/*     */   }
/*     */   
/*     */   int getMinYear() {
/* 226 */     return -292275054;
/*     */   }
/*     */   
/*     */   int getMaxYear() {
/* 230 */     return 292278993;
/*     */   }
/*     */   
/*     */   long getAverageMillisPerYear() {
/* 234 */     return 31556952000L;
/*     */   }
/*     */   
/*     */   long getAverageMillisPerYearDividedByTwo() {
/* 238 */     return 15778476000L;
/*     */   }
/*     */   
/*     */   long getAverageMillisPerMonth() {
/* 242 */     return 2629746000L;
/*     */   }
/*     */   
/*     */   long getApproxMillisAtEpochDividedByTwo() {
/* 246 */     return 31083597720000L;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/chrono/GregorianChronology.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */