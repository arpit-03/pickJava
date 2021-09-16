/*     */ package org.joda.time.convert;
/*     */ 
/*     */ import org.joda.time.Chronology;
/*     */ import org.joda.time.DateTimeUtils;
/*     */ import org.joda.time.DateTimeZone;
/*     */ import org.joda.time.ReadablePartial;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class ReadablePartialConverter
/*     */   extends AbstractConverter
/*     */   implements PartialConverter
/*     */ {
/*  35 */   static final ReadablePartialConverter INSTANCE = new ReadablePartialConverter();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*  53 */     return getChronology(paramObject, (Chronology)null).withZone(paramDateTimeZone);
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
/*  67 */     if (paramChronology == null) {
/*  68 */       paramChronology = ((ReadablePartial)paramObject).getChronology();
/*  69 */       paramChronology = DateTimeUtils.getChronology(paramChronology);
/*     */     } 
/*  71 */     return paramChronology;
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
/*     */   public int[] getPartialValues(ReadablePartial paramReadablePartial, Object paramObject, Chronology paramChronology) {
/*  87 */     ReadablePartial readablePartial = (ReadablePartial)paramObject;
/*  88 */     int i = paramReadablePartial.size();
/*  89 */     int[] arrayOfInt = new int[i];
/*  90 */     for (byte b = 0; b < i; b++) {
/*  91 */       arrayOfInt[b] = readablePartial.get(paramReadablePartial.getFieldType(b));
/*     */     }
/*  93 */     paramChronology.validate(paramReadablePartial, arrayOfInt);
/*  94 */     return arrayOfInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Class<?> getSupportedType() {
/* 104 */     return ReadablePartial.class;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/convert/ReadablePartialConverter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */