/*     */ package org.joda.time.chrono;
/*     */ 
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import org.joda.time.Chronology;
/*     */ import org.joda.time.DateTime;
/*     */ import org.joda.time.DateTimeField;
/*     */ import org.joda.time.DateTimeZone;
/*     */ import org.joda.time.ReadableDateTime;
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
/*     */ public final class EthiopicChronology
/*     */   extends BasicFixedMonthChronology
/*     */ {
/*     */   private static final long serialVersionUID = -5972804258688333942L;
/*     */   public static final int EE = 1;
/*  62 */   private static final DateTimeField ERA_FIELD = (DateTimeField)new BasicSingleEraDateTimeField("EE");
/*     */ 
/*     */   
/*     */   private static final int MIN_YEAR = -292269337;
/*     */ 
/*     */   
/*     */   private static final int MAX_YEAR = 292272984;
/*     */ 
/*     */   
/*  71 */   private static final ConcurrentHashMap<DateTimeZone, EthiopicChronology[]> cCache = (ConcurrentHashMap)new ConcurrentHashMap<DateTimeZone, EthiopicChronology>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  77 */   private static final EthiopicChronology INSTANCE_UTC = getInstance(DateTimeZone.UTC);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static EthiopicChronology getInstanceUTC() {
/*  88 */     return INSTANCE_UTC;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static EthiopicChronology getInstance() {
/*  97 */     return getInstance(DateTimeZone.getDefault(), 4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static EthiopicChronology getInstance(DateTimeZone paramDateTimeZone) {
/* 107 */     return getInstance(paramDateTimeZone, 4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static EthiopicChronology getInstance(DateTimeZone paramDateTimeZone, int paramInt) {
/*     */     EthiopicChronology ethiopicChronology;
/* 118 */     if (paramDateTimeZone == null) {
/* 119 */       paramDateTimeZone = DateTimeZone.getDefault();
/*     */     }
/*     */     
/* 122 */     EthiopicChronology[] arrayOfEthiopicChronology = cCache.get(paramDateTimeZone);
/* 123 */     if (arrayOfEthiopicChronology == null) {
/* 124 */       arrayOfEthiopicChronology = new EthiopicChronology[7];
/* 125 */       EthiopicChronology[] arrayOfEthiopicChronology1 = cCache.putIfAbsent(paramDateTimeZone, arrayOfEthiopicChronology);
/* 126 */       if (arrayOfEthiopicChronology1 != null) {
/* 127 */         arrayOfEthiopicChronology = arrayOfEthiopicChronology1;
/*     */       }
/*     */     } 
/*     */     try {
/* 131 */       ethiopicChronology = arrayOfEthiopicChronology[paramInt - 1];
/* 132 */     } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
/* 133 */       throw new IllegalArgumentException("Invalid min days in first week: " + paramInt);
/*     */     } 
/*     */ 
/*     */     
/* 137 */     if (ethiopicChronology == null) {
/* 138 */       synchronized (arrayOfEthiopicChronology) {
/* 139 */         ethiopicChronology = arrayOfEthiopicChronology[paramInt - 1];
/* 140 */         if (ethiopicChronology == null) {
/* 141 */           if (paramDateTimeZone == DateTimeZone.UTC) {
/*     */             
/* 143 */             ethiopicChronology = new EthiopicChronology(null, null, paramInt);
/*     */             
/* 145 */             DateTime dateTime = new DateTime(1, 1, 1, 0, 0, 0, 0, ethiopicChronology);
/* 146 */             ethiopicChronology = new EthiopicChronology(LimitChronology.getInstance(ethiopicChronology, (ReadableDateTime)dateTime, null), null, paramInt);
/*     */           }
/*     */           else {
/*     */             
/* 150 */             ethiopicChronology = getInstance(DateTimeZone.UTC, paramInt);
/* 151 */             ethiopicChronology = new EthiopicChronology(ZonedChronology.getInstance(ethiopicChronology, paramDateTimeZone), null, paramInt);
/*     */           } 
/*     */           
/* 154 */           arrayOfEthiopicChronology[paramInt - 1] = ethiopicChronology;
/*     */         } 
/*     */       } 
/*     */     }
/* 158 */     return ethiopicChronology;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   EthiopicChronology(Chronology paramChronology, Object paramObject, int paramInt) {
/* 167 */     super(paramChronology, paramObject, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Object readResolve() {
/* 174 */     Chronology chronology = getBase();
/* 175 */     return (chronology == null) ? getInstance(DateTimeZone.UTC, getMinimumDaysInFirstWeek()) : getInstance(chronology.getZone(), getMinimumDaysInFirstWeek());
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
/* 188 */     return INSTANCE_UTC;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Chronology withZone(DateTimeZone paramDateTimeZone) {
/* 198 */     if (paramDateTimeZone == null) {
/* 199 */       paramDateTimeZone = DateTimeZone.getDefault();
/*     */     }
/* 201 */     if (paramDateTimeZone == getZone()) {
/* 202 */       return this;
/*     */     }
/* 204 */     return getInstance(paramDateTimeZone);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isLeapDay(long paramLong) {
/* 210 */     return (dayOfMonth().get(paramLong) == 6 && monthOfYear().isLeap(paramLong));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   long calculateFirstDayOfYearMillis(int paramInt) {
/* 219 */     int j, i = paramInt - 1963;
/*     */     
/* 221 */     if (i <= 0) {
/*     */ 
/*     */       
/* 224 */       j = i + 3 >> 2;
/*     */     } else {
/* 226 */       j = i >> 2;
/*     */       
/* 228 */       if (!isLeapYear(paramInt)) {
/* 229 */         j++;
/*     */       }
/*     */     } 
/*     */     
/* 233 */     long l = (i * 365L + j) * 86400000L;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 238 */     return l + 21859200000L;
/*     */   }
/*     */ 
/*     */   
/*     */   int getMinYear() {
/* 243 */     return -292269337;
/*     */   }
/*     */ 
/*     */   
/*     */   int getMaxYear() {
/* 248 */     return 292272984;
/*     */   }
/*     */ 
/*     */   
/*     */   long getApproxMillisAtEpochDividedByTwo() {
/* 253 */     return 30962844000000L;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void assemble(AssembledChronology.Fields paramFields) {
/* 258 */     if (getBase() == null) {
/* 259 */       super.assemble(paramFields);
/*     */ 
/*     */       
/* 262 */       paramFields.year = (DateTimeField)new SkipDateTimeField(this, paramFields.year);
/* 263 */       paramFields.weekyear = (DateTimeField)new SkipDateTimeField(this, paramFields.weekyear);
/*     */       
/* 265 */       paramFields.era = ERA_FIELD;
/* 266 */       paramFields.monthOfYear = (DateTimeField)new BasicMonthOfYearDateTimeField(this, 13);
/* 267 */       paramFields.months = paramFields.monthOfYear.getDurationField();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/chrono/EthiopicChronology.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */