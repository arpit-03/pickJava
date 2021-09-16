/*     */ package org.joda.time.field;
/*     */ 
/*     */ import org.joda.time.DateTimeField;
/*     */ import org.joda.time.DateTimeFieldType;
/*     */ import org.joda.time.DurationField;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class DecoratedDateTimeField
/*     */   extends BaseDateTimeField
/*     */ {
/*     */   private static final long serialVersionUID = 203115783733757597L;
/*     */   private final DateTimeField iField;
/*     */   
/*     */   protected DecoratedDateTimeField(DateTimeField paramDateTimeField, DateTimeFieldType paramDateTimeFieldType) {
/*  55 */     super(paramDateTimeFieldType);
/*  56 */     if (paramDateTimeField == null) {
/*  57 */       throw new IllegalArgumentException("The field must not be null");
/*     */     }
/*  59 */     if (!paramDateTimeField.isSupported()) {
/*  60 */       throw new IllegalArgumentException("The field must be supported");
/*     */     }
/*  62 */     this.iField = paramDateTimeField;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final DateTimeField getWrappedField() {
/*  71 */     return this.iField;
/*     */   }
/*     */   
/*     */   public boolean isLenient() {
/*  75 */     return this.iField.isLenient();
/*     */   }
/*     */   
/*     */   public int get(long paramLong) {
/*  79 */     return this.iField.get(paramLong);
/*     */   }
/*     */   
/*     */   public long set(long paramLong, int paramInt) {
/*  83 */     return this.iField.set(paramLong, paramInt);
/*     */   }
/*     */   
/*     */   public DurationField getDurationField() {
/*  87 */     return this.iField.getDurationField();
/*     */   }
/*     */   
/*     */   public DurationField getRangeDurationField() {
/*  91 */     return this.iField.getRangeDurationField();
/*     */   }
/*     */   
/*     */   public int getMinimumValue() {
/*  95 */     return this.iField.getMinimumValue();
/*     */   }
/*     */   
/*     */   public int getMaximumValue() {
/*  99 */     return this.iField.getMaximumValue();
/*     */   }
/*     */   
/*     */   public long roundFloor(long paramLong) {
/* 103 */     return this.iField.roundFloor(paramLong);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/field/DecoratedDateTimeField.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */