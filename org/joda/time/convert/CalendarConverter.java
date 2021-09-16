/*     */ package org.joda.time.convert;
/*     */ 
/*     */ import java.util.Calendar;
/*     */ import java.util.GregorianCalendar;
/*     */ import org.joda.time.Chronology;
/*     */ import org.joda.time.DateTimeZone;
/*     */ import org.joda.time.chrono.BuddhistChronology;
/*     */ import org.joda.time.chrono.GJChronology;
/*     */ import org.joda.time.chrono.GregorianChronology;
/*     */ import org.joda.time.chrono.ISOChronology;
/*     */ import org.joda.time.chrono.JulianChronology;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class CalendarConverter
/*     */   extends AbstractConverter
/*     */   implements InstantConverter, PartialConverter
/*     */ {
/*  43 */   static final CalendarConverter INSTANCE = new CalendarConverter();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Chronology getChronology(Object paramObject, Chronology paramChronology) {
/*  68 */     if (paramChronology != null) {
/*  69 */       return paramChronology;
/*     */     }
/*  71 */     Calendar calendar = (Calendar)paramObject;
/*  72 */     DateTimeZone dateTimeZone = null;
/*     */     try {
/*  74 */       dateTimeZone = DateTimeZone.forTimeZone(calendar.getTimeZone());
/*     */     }
/*  76 */     catch (IllegalArgumentException illegalArgumentException) {
/*  77 */       dateTimeZone = DateTimeZone.getDefault();
/*     */     } 
/*  79 */     return getChronology(calendar, dateTimeZone);
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
/*     */   public Chronology getChronology(Object paramObject, DateTimeZone paramDateTimeZone) {
/*  94 */     if (paramObject.getClass().getName().endsWith(".BuddhistCalendar"))
/*  95 */       return (Chronology)BuddhistChronology.getInstance(paramDateTimeZone); 
/*  96 */     if (paramObject instanceof GregorianCalendar) {
/*  97 */       GregorianCalendar gregorianCalendar = (GregorianCalendar)paramObject;
/*  98 */       long l = gregorianCalendar.getGregorianChange().getTime();
/*  99 */       if (l == Long.MIN_VALUE)
/* 100 */         return (Chronology)GregorianChronology.getInstance(paramDateTimeZone); 
/* 101 */       if (l == Long.MAX_VALUE) {
/* 102 */         return (Chronology)JulianChronology.getInstance(paramDateTimeZone);
/*     */       }
/* 104 */       return (Chronology)GJChronology.getInstance(paramDateTimeZone, l, 4);
/*     */     } 
/*     */     
/* 107 */     return (Chronology)ISOChronology.getInstance(paramDateTimeZone);
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
/*     */   public long getInstantMillis(Object paramObject, Chronology paramChronology) {
/* 121 */     Calendar calendar = (Calendar)paramObject;
/* 122 */     return calendar.getTime().getTime();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Class<?> getSupportedType() {
/* 132 */     return Calendar.class;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/convert/CalendarConverter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */