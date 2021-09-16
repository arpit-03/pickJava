/*     */ package org.joda.time.convert;
/*     */ 
/*     */ import org.joda.time.Chronology;
/*     */ import org.joda.time.DateTimeUtils;
/*     */ import org.joda.time.DateTimeZone;
/*     */ import org.joda.time.ReadableInstant;
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
/*     */ class ReadableInstantConverter
/*     */   extends AbstractConverter
/*     */   implements InstantConverter, PartialConverter
/*     */ {
/*  36 */   static final ReadableInstantConverter INSTANCE = new ReadableInstantConverter();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*  58 */     Chronology chronology = ((ReadableInstant)paramObject).getChronology();
/*  59 */     if (chronology == null) {
/*  60 */       return (Chronology)ISOChronology.getInstance(paramDateTimeZone);
/*     */     }
/*  62 */     DateTimeZone dateTimeZone = chronology.getZone();
/*  63 */     if (dateTimeZone != paramDateTimeZone) {
/*  64 */       chronology = chronology.withZone(paramDateTimeZone);
/*  65 */       if (chronology == null) {
/*  66 */         return (Chronology)ISOChronology.getInstance(paramDateTimeZone);
/*     */       }
/*     */     } 
/*  69 */     return chronology;
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
/*     */   public Chronology getChronology(Object paramObject, Chronology paramChronology) {
/*  83 */     if (paramChronology == null) {
/*  84 */       paramChronology = ((ReadableInstant)paramObject).getChronology();
/*  85 */       paramChronology = DateTimeUtils.getChronology(paramChronology);
/*     */     } 
/*  87 */     return paramChronology;
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
/*     */   public long getInstantMillis(Object paramObject, Chronology paramChronology) {
/* 100 */     return ((ReadableInstant)paramObject).getMillis();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Class<?> getSupportedType() {
/* 110 */     return ReadableInstant.class;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/convert/ReadableInstantConverter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */