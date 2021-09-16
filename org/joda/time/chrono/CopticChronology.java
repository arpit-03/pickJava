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
/*     */ public final class CopticChronology
/*     */   extends BasicFixedMonthChronology
/*     */ {
/*     */   private static final long serialVersionUID = -5972804258688333942L;
/*     */   public static final int AM = 1;
/*  62 */   private static final DateTimeField ERA_FIELD = (DateTimeField)new BasicSingleEraDateTimeField("AM");
/*     */ 
/*     */   
/*     */   private static final int MIN_YEAR = -292269337;
/*     */ 
/*     */   
/*     */   private static final int MAX_YEAR = 292272708;
/*     */ 
/*     */   
/*  71 */   private static final ConcurrentHashMap<DateTimeZone, CopticChronology[]> cCache = (ConcurrentHashMap)new ConcurrentHashMap<DateTimeZone, CopticChronology>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  77 */   private static final CopticChronology INSTANCE_UTC = getInstance(DateTimeZone.UTC);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CopticChronology getInstanceUTC() {
/*  88 */     return INSTANCE_UTC;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CopticChronology getInstance() {
/*  97 */     return getInstance(DateTimeZone.getDefault(), 4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CopticChronology getInstance(DateTimeZone paramDateTimeZone) {
/* 107 */     return getInstance(paramDateTimeZone, 4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CopticChronology getInstance(DateTimeZone paramDateTimeZone, int paramInt) {
/*     */     CopticChronology copticChronology;
/* 118 */     if (paramDateTimeZone == null) {
/* 119 */       paramDateTimeZone = DateTimeZone.getDefault();
/*     */     }
/*     */     
/* 122 */     CopticChronology[] arrayOfCopticChronology = cCache.get(paramDateTimeZone);
/* 123 */     if (arrayOfCopticChronology == null) {
/* 124 */       arrayOfCopticChronology = new CopticChronology[7];
/* 125 */       CopticChronology[] arrayOfCopticChronology1 = cCache.putIfAbsent(paramDateTimeZone, arrayOfCopticChronology);
/* 126 */       if (arrayOfCopticChronology1 != null) {
/* 127 */         arrayOfCopticChronology = arrayOfCopticChronology1;
/*     */       }
/*     */     } 
/*     */     try {
/* 131 */       copticChronology = arrayOfCopticChronology[paramInt - 1];
/* 132 */     } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
/* 133 */       throw new IllegalArgumentException("Invalid min days in first week: " + paramInt);
/*     */     } 
/*     */     
/* 136 */     if (copticChronology == null) {
/* 137 */       synchronized (arrayOfCopticChronology) {
/* 138 */         copticChronology = arrayOfCopticChronology[paramInt - 1];
/* 139 */         if (copticChronology == null) {
/* 140 */           if (paramDateTimeZone == DateTimeZone.UTC) {
/*     */             
/* 142 */             copticChronology = new CopticChronology(null, null, paramInt);
/*     */             
/* 144 */             DateTime dateTime = new DateTime(1, 1, 1, 0, 0, 0, 0, copticChronology);
/* 145 */             copticChronology = new CopticChronology(LimitChronology.getInstance(copticChronology, (ReadableDateTime)dateTime, null), null, paramInt);
/*     */           }
/*     */           else {
/*     */             
/* 149 */             copticChronology = getInstance(DateTimeZone.UTC, paramInt);
/* 150 */             copticChronology = new CopticChronology(ZonedChronology.getInstance(copticChronology, paramDateTimeZone), null, paramInt);
/*     */           } 
/*     */           
/* 153 */           arrayOfCopticChronology[paramInt - 1] = copticChronology;
/*     */         } 
/*     */       } 
/*     */     }
/* 157 */     return copticChronology;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   CopticChronology(Chronology paramChronology, Object paramObject, int paramInt) {
/* 166 */     super(paramChronology, paramObject, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Object readResolve() {
/* 173 */     Chronology chronology = getBase();
/* 174 */     int i = getMinimumDaysInFirstWeek();
/* 175 */     i = (i == 0) ? 4 : i;
/* 176 */     return (chronology == null) ? getInstance(DateTimeZone.UTC, i) : getInstance(chronology.getZone(), i);
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
/* 189 */     return INSTANCE_UTC;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Chronology withZone(DateTimeZone paramDateTimeZone) {
/* 199 */     if (paramDateTimeZone == null) {
/* 200 */       paramDateTimeZone = DateTimeZone.getDefault();
/*     */     }
/* 202 */     if (paramDateTimeZone == getZone()) {
/* 203 */       return this;
/*     */     }
/* 205 */     return getInstance(paramDateTimeZone);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isLeapDay(long paramLong) {
/* 211 */     return (dayOfMonth().get(paramLong) == 6 && monthOfYear().isLeap(paramLong));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   long calculateFirstDayOfYearMillis(int paramInt) {
/* 220 */     int j, i = paramInt - 1687;
/*     */     
/* 222 */     if (i <= 0) {
/*     */ 
/*     */       
/* 225 */       j = i + 3 >> 2;
/*     */     } else {
/* 227 */       j = i >> 2;
/*     */       
/* 229 */       if (!isLeapYear(paramInt)) {
/* 230 */         j++;
/*     */       }
/*     */     } 
/*     */     
/* 234 */     long l = (i * 365L + j) * 86400000L;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 239 */     return l + 21859200000L;
/*     */   }
/*     */ 
/*     */   
/*     */   int getMinYear() {
/* 244 */     return -292269337;
/*     */   }
/*     */ 
/*     */   
/*     */   int getMaxYear() {
/* 249 */     return 292272708;
/*     */   }
/*     */ 
/*     */   
/*     */   long getApproxMillisAtEpochDividedByTwo() {
/* 254 */     return 26607895200000L;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void assemble(AssembledChronology.Fields paramFields) {
/* 259 */     if (getBase() == null) {
/* 260 */       super.assemble(paramFields);
/*     */ 
/*     */       
/* 263 */       paramFields.year = (DateTimeField)new SkipDateTimeField(this, paramFields.year);
/* 264 */       paramFields.weekyear = (DateTimeField)new SkipDateTimeField(this, paramFields.weekyear);
/*     */       
/* 266 */       paramFields.era = ERA_FIELD;
/* 267 */       paramFields.monthOfYear = (DateTimeField)new BasicMonthOfYearDateTimeField(this, 13);
/* 268 */       paramFields.months = paramFields.monthOfYear.getDurationField();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/chrono/CopticChronology.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */