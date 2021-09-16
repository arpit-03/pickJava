/*     */ package org.joda.time.chrono;
/*     */ 
/*     */ import java.util.Locale;
/*     */ import org.joda.time.DateTimeField;
/*     */ import org.joda.time.DateTimeFieldType;
/*     */ import org.joda.time.DurationField;
/*     */ import org.joda.time.DurationFieldType;
/*     */ import org.joda.time.IllegalFieldValueException;
/*     */ import org.joda.time.field.BaseDateTimeField;
/*     */ import org.joda.time.field.FieldUtils;
/*     */ import org.joda.time.field.UnsupportedDurationField;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class BasicSingleEraDateTimeField
/*     */   extends BaseDateTimeField
/*     */ {
/*     */   private static final int ERA_VALUE = 1;
/*     */   private final String iEraText;
/*     */   
/*     */   BasicSingleEraDateTimeField(String paramString) {
/*  51 */     super(DateTimeFieldType.era());
/*  52 */     this.iEraText = paramString;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isLenient() {
/*  57 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int get(long paramLong) {
/*  62 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public long set(long paramLong, int paramInt) {
/*  67 */     FieldUtils.verifyValueBounds((DateTimeField)this, paramInt, 1, 1);
/*  68 */     return paramLong;
/*     */   }
/*     */ 
/*     */   
/*     */   public long set(long paramLong, String paramString, Locale paramLocale) {
/*  73 */     if (!this.iEraText.equals(paramString) && !"1".equals(paramString)) {
/*  74 */       throw new IllegalFieldValueException(DateTimeFieldType.era(), paramString);
/*     */     }
/*  76 */     return paramLong;
/*     */   }
/*     */ 
/*     */   
/*     */   public long roundFloor(long paramLong) {
/*  81 */     return Long.MIN_VALUE;
/*     */   }
/*     */ 
/*     */   
/*     */   public long roundCeiling(long paramLong) {
/*  86 */     return Long.MAX_VALUE;
/*     */   }
/*     */ 
/*     */   
/*     */   public long roundHalfFloor(long paramLong) {
/*  91 */     return Long.MIN_VALUE;
/*     */   }
/*     */ 
/*     */   
/*     */   public long roundHalfCeiling(long paramLong) {
/*  96 */     return Long.MIN_VALUE;
/*     */   }
/*     */ 
/*     */   
/*     */   public long roundHalfEven(long paramLong) {
/* 101 */     return Long.MIN_VALUE;
/*     */   }
/*     */ 
/*     */   
/*     */   public DurationField getDurationField() {
/* 106 */     return (DurationField)UnsupportedDurationField.getInstance(DurationFieldType.eras());
/*     */   }
/*     */ 
/*     */   
/*     */   public DurationField getRangeDurationField() {
/* 111 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMinimumValue() {
/* 116 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaximumValue() {
/* 121 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getAsText(int paramInt, Locale paramLocale) {
/* 126 */     return this.iEraText;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaximumTextLength(Locale paramLocale) {
/* 131 */     return this.iEraText.length();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/chrono/BasicSingleEraDateTimeField.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */