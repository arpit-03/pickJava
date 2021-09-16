/*     */ package org.joda.time.base;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Locale;
/*     */ import org.joda.time.Chronology;
/*     */ import org.joda.time.DateTimeField;
/*     */ import org.joda.time.DateTimeUtils;
/*     */ import org.joda.time.ReadablePartial;
/*     */ import org.joda.time.convert.ConverterManager;
/*     */ import org.joda.time.convert.PartialConverter;
/*     */ import org.joda.time.format.DateTimeFormat;
/*     */ import org.joda.time.format.DateTimeFormatter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class BasePartial
/*     */   extends AbstractPartial
/*     */   implements ReadablePartial, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 2353678632973660L;
/*     */   private final Chronology iChronology;
/*     */   private final int[] iValues;
/*     */   
/*     */   protected BasePartial() {
/*  65 */     this(DateTimeUtils.currentTimeMillis(), (Chronology)null);
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
/*     */   protected BasePartial(Chronology paramChronology) {
/*  79 */     this(DateTimeUtils.currentTimeMillis(), paramChronology);
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
/*     */   protected BasePartial(long paramLong) {
/*  93 */     this(paramLong, (Chronology)null);
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
/*     */   protected BasePartial(long paramLong, Chronology paramChronology) {
/* 109 */     paramChronology = DateTimeUtils.getChronology(paramChronology);
/* 110 */     this.iChronology = paramChronology.withUTC();
/* 111 */     this.iValues = paramChronology.get(this, paramLong);
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
/*     */   protected BasePartial(Object paramObject, Chronology paramChronology) {
/* 132 */     PartialConverter partialConverter = ConverterManager.getInstance().getPartialConverter(paramObject);
/* 133 */     paramChronology = partialConverter.getChronology(paramObject, paramChronology);
/* 134 */     paramChronology = DateTimeUtils.getChronology(paramChronology);
/* 135 */     this.iChronology = paramChronology.withUTC();
/* 136 */     this.iValues = partialConverter.getPartialValues(this, paramObject, paramChronology);
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
/*     */   protected BasePartial(Object paramObject, Chronology paramChronology, DateTimeFormatter paramDateTimeFormatter) {
/* 159 */     PartialConverter partialConverter = ConverterManager.getInstance().getPartialConverter(paramObject);
/* 160 */     paramChronology = partialConverter.getChronology(paramObject, paramChronology);
/* 161 */     paramChronology = DateTimeUtils.getChronology(paramChronology);
/* 162 */     this.iChronology = paramChronology.withUTC();
/* 163 */     this.iValues = partialConverter.getPartialValues(this, paramObject, paramChronology, paramDateTimeFormatter);
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
/*     */   protected BasePartial(int[] paramArrayOfint, Chronology paramChronology) {
/* 181 */     paramChronology = DateTimeUtils.getChronology(paramChronology);
/* 182 */     this.iChronology = paramChronology.withUTC();
/* 183 */     paramChronology.validate(this, paramArrayOfint);
/* 184 */     this.iValues = paramArrayOfint;
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
/*     */   protected BasePartial(BasePartial paramBasePartial, int[] paramArrayOfint) {
/* 197 */     this.iChronology = paramBasePartial.iChronology;
/* 198 */     this.iValues = paramArrayOfint;
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
/*     */   protected BasePartial(BasePartial paramBasePartial, Chronology paramChronology) {
/* 212 */     this.iChronology = paramChronology.withUTC();
/* 213 */     this.iValues = paramBasePartial.iValues;
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
/*     */   public int getValue(int paramInt) {
/* 225 */     return this.iValues[paramInt];
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
/*     */   public int[] getValues() {
/* 237 */     return (int[])this.iValues.clone();
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
/*     */   public Chronology getChronology() {
/* 249 */     return this.iChronology;
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
/*     */   protected void setValue(int paramInt1, int paramInt2) {
/* 265 */     DateTimeField dateTimeField = getField(paramInt1);
/* 266 */     int[] arrayOfInt = dateTimeField.set(this, paramInt1, this.iValues, paramInt2);
/* 267 */     System.arraycopy(arrayOfInt, 0, this.iValues, 0, this.iValues.length);
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
/*     */   protected void setValues(int[] paramArrayOfint) {
/* 280 */     getChronology().validate(this, paramArrayOfint);
/* 281 */     System.arraycopy(paramArrayOfint, 0, this.iValues, 0, this.iValues.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString(String paramString) {
/* 292 */     if (paramString == null) {
/* 293 */       return toString();
/*     */     }
/* 295 */     return DateTimeFormat.forPattern(paramString).print(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString(String paramString, Locale paramLocale) throws IllegalArgumentException {
/* 306 */     if (paramString == null) {
/* 307 */       return toString();
/*     */     }
/* 309 */     return DateTimeFormat.forPattern(paramString).withLocale(paramLocale).print(this);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/base/BasePartial.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */