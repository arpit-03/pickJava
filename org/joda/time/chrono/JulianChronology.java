/*     */ package org.joda.time.chrono;
/*     */ 
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import org.joda.time.Chronology;
/*     */ import org.joda.time.DateTimeField;
/*     */ import org.joda.time.DateTimeFieldType;
/*     */ import org.joda.time.DateTimeZone;
/*     */ import org.joda.time.IllegalFieldValueException;
/*     */ import org.joda.time.field.SkipDateTimeField;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class JulianChronology
/*     */   extends BasicGJChronology
/*     */ {
/*     */   private static final long serialVersionUID = -8731039522547897247L;
/*     */   private static final long MILLIS_PER_YEAR = 31557600000L;
/*     */   private static final long MILLIS_PER_MONTH = 2629800000L;
/*     */   private static final int MIN_YEAR = -292269054;
/*     */   private static final int MAX_YEAR = 292272992;
/*     */   private static final JulianChronology INSTANCE_UTC;
/*  70 */   private static final ConcurrentHashMap<DateTimeZone, JulianChronology[]> cCache = (ConcurrentHashMap)new ConcurrentHashMap<DateTimeZone, JulianChronology>();
/*     */   
/*     */   static {
/*  73 */     INSTANCE_UTC = getInstance(DateTimeZone.UTC);
/*     */   }
/*     */   
/*     */   static int adjustYearForSet(int paramInt) {
/*  77 */     if (paramInt <= 0) {
/*  78 */       if (paramInt == 0) {
/*  79 */         throw new IllegalFieldValueException(DateTimeFieldType.year(), Integer.valueOf(paramInt), null, null);
/*     */       }
/*     */       
/*  82 */       paramInt++;
/*     */     } 
/*  84 */     return paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static JulianChronology getInstanceUTC() {
/*  94 */     return INSTANCE_UTC;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static JulianChronology getInstance() {
/* 103 */     return getInstance(DateTimeZone.getDefault(), 4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static JulianChronology getInstance(DateTimeZone paramDateTimeZone) {
/* 113 */     return getInstance(paramDateTimeZone, 4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static JulianChronology getInstance(DateTimeZone paramDateTimeZone, int paramInt) {
/*     */     JulianChronology julianChronology;
/* 124 */     if (paramDateTimeZone == null) {
/* 125 */       paramDateTimeZone = DateTimeZone.getDefault();
/*     */     }
/*     */     
/* 128 */     JulianChronology[] arrayOfJulianChronology = cCache.get(paramDateTimeZone);
/* 129 */     if (arrayOfJulianChronology == null) {
/* 130 */       arrayOfJulianChronology = new JulianChronology[7];
/* 131 */       JulianChronology[] arrayOfJulianChronology1 = cCache.putIfAbsent(paramDateTimeZone, arrayOfJulianChronology);
/* 132 */       if (arrayOfJulianChronology1 != null) {
/* 133 */         arrayOfJulianChronology = arrayOfJulianChronology1;
/*     */       }
/*     */     } 
/*     */     try {
/* 137 */       julianChronology = arrayOfJulianChronology[paramInt - 1];
/* 138 */     } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
/* 139 */       throw new IllegalArgumentException("Invalid min days in first week: " + paramInt);
/*     */     } 
/*     */     
/* 142 */     if (julianChronology == null) {
/* 143 */       synchronized (arrayOfJulianChronology) {
/* 144 */         julianChronology = arrayOfJulianChronology[paramInt - 1];
/* 145 */         if (julianChronology == null) {
/* 146 */           if (paramDateTimeZone == DateTimeZone.UTC) {
/* 147 */             julianChronology = new JulianChronology(null, null, paramInt);
/*     */           } else {
/* 149 */             julianChronology = getInstance(DateTimeZone.UTC, paramInt);
/* 150 */             julianChronology = new JulianChronology(ZonedChronology.getInstance(julianChronology, paramDateTimeZone), null, paramInt);
/*     */           } 
/*     */           
/* 153 */           arrayOfJulianChronology[paramInt - 1] = julianChronology;
/*     */         } 
/*     */       } 
/*     */     }
/* 157 */     return julianChronology;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   JulianChronology(Chronology paramChronology, Object paramObject, int paramInt) {
/* 167 */     super(paramChronology, paramObject, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Object readResolve() {
/* 174 */     Chronology chronology = getBase();
/* 175 */     int i = getMinimumDaysInFirstWeek();
/* 176 */     i = (i == 0) ? 4 : i;
/* 177 */     return (chronology == null) ? getInstance(DateTimeZone.UTC, i) : getInstance(chronology.getZone(), i);
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
/* 190 */     return INSTANCE_UTC;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Chronology withZone(DateTimeZone paramDateTimeZone) {
/* 200 */     if (paramDateTimeZone == null) {
/* 201 */       paramDateTimeZone = DateTimeZone.getDefault();
/*     */     }
/* 203 */     if (paramDateTimeZone == getZone()) {
/* 204 */       return this;
/*     */     }
/* 206 */     return getInstance(paramDateTimeZone);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   long getDateMidnightMillis(int paramInt1, int paramInt2, int paramInt3) throws IllegalArgumentException {
/* 212 */     return super.getDateMidnightMillis(adjustYearForSet(paramInt1), paramInt2, paramInt3);
/*     */   }
/*     */   
/*     */   boolean isLeapYear(int paramInt) {
/* 216 */     return ((paramInt & 0x3) == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   long calculateFirstDayOfYearMillis(int paramInt) {
/* 224 */     int j, i = paramInt - 1968;
/*     */     
/* 226 */     if (i <= 0) {
/*     */ 
/*     */       
/* 229 */       j = i + 3 >> 2;
/*     */     } else {
/* 231 */       j = i >> 2;
/*     */       
/* 233 */       if (!isLeapYear(paramInt)) {
/* 234 */         j++;
/*     */       }
/*     */     } 
/*     */     
/* 238 */     long l = (i * 365L + j) * 86400000L;
/*     */ 
/*     */ 
/*     */     
/* 242 */     return l - 62035200000L;
/*     */   }
/*     */   
/*     */   int getMinYear() {
/* 246 */     return -292269054;
/*     */   }
/*     */   
/*     */   int getMaxYear() {
/* 250 */     return 292272992;
/*     */   }
/*     */   
/*     */   long getAverageMillisPerYear() {
/* 254 */     return 31557600000L;
/*     */   }
/*     */   
/*     */   long getAverageMillisPerYearDividedByTwo() {
/* 258 */     return 15778800000L;
/*     */   }
/*     */   
/*     */   long getAverageMillisPerMonth() {
/* 262 */     return 2629800000L;
/*     */   }
/*     */   
/*     */   long getApproxMillisAtEpochDividedByTwo() {
/* 266 */     return 31083663600000L;
/*     */   }
/*     */   
/*     */   protected void assemble(AssembledChronology.Fields paramFields) {
/* 270 */     if (getBase() == null) {
/* 271 */       super.assemble(paramFields);
/*     */       
/* 273 */       paramFields.year = (DateTimeField)new SkipDateTimeField(this, paramFields.year);
/* 274 */       paramFields.weekyear = (DateTimeField)new SkipDateTimeField(this, paramFields.weekyear);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/chrono/JulianChronology.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */