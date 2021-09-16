/*     */ package org.joda.time.field;
/*     */ 
/*     */ import org.joda.time.Chronology;
/*     */ import org.joda.time.DateTimeField;
/*     */ import org.joda.time.DateTimeFieldType;
/*     */ import org.joda.time.IllegalFieldValueException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class SkipDateTimeField
/*     */   extends DelegatedDateTimeField
/*     */ {
/*     */   private static final long serialVersionUID = -8869148464118507846L;
/*     */   private final Chronology iChronology;
/*     */   private final int iSkip;
/*     */   private transient int iMinValue;
/*     */   
/*     */   public SkipDateTimeField(Chronology paramChronology, DateTimeField paramDateTimeField) {
/*  54 */     this(paramChronology, paramDateTimeField, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SkipDateTimeField(Chronology paramChronology, DateTimeField paramDateTimeField, int paramInt) {
/*  65 */     super(paramDateTimeField);
/*  66 */     this.iChronology = paramChronology;
/*  67 */     int i = super.getMinimumValue();
/*  68 */     if (i < paramInt) {
/*  69 */       this.iMinValue = i - 1;
/*  70 */     } else if (i == paramInt) {
/*  71 */       this.iMinValue = paramInt + 1;
/*     */     } else {
/*  73 */       this.iMinValue = i;
/*     */     } 
/*  75 */     this.iSkip = paramInt;
/*     */   }
/*     */ 
/*     */   
/*     */   public int get(long paramLong) {
/*  80 */     int i = super.get(paramLong);
/*  81 */     if (i <= this.iSkip) {
/*  82 */       i--;
/*     */     }
/*  84 */     return i;
/*     */   }
/*     */   
/*     */   public long set(long paramLong, int paramInt) {
/*  88 */     FieldUtils.verifyValueBounds(this, paramInt, this.iMinValue, getMaximumValue());
/*  89 */     if (paramInt <= this.iSkip) {
/*  90 */       if (paramInt == this.iSkip) {
/*  91 */         throw new IllegalFieldValueException(DateTimeFieldType.year(), Integer.valueOf(paramInt), null, null);
/*     */       }
/*     */       
/*  94 */       paramInt++;
/*     */     } 
/*  96 */     return super.set(paramLong, paramInt);
/*     */   }
/*     */   
/*     */   public int getMinimumValue() {
/* 100 */     return this.iMinValue;
/*     */   }
/*     */   
/*     */   private Object readResolve() {
/* 104 */     return getType().getField(this.iChronology);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/field/SkipDateTimeField.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */