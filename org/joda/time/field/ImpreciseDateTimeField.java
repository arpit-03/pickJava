/*     */ package org.joda.time.field;
/*     */ 
/*     */ import org.joda.time.DateTimeFieldType;
/*     */ import org.joda.time.DurationField;
/*     */ import org.joda.time.DurationFieldType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ImpreciseDateTimeField
/*     */   extends BaseDateTimeField
/*     */ {
/*     */   private static final long serialVersionUID = 7190739608550251860L;
/*     */   final long iUnitMillis;
/*     */   private final DurationField iDurationField;
/*     */   
/*     */   public ImpreciseDateTimeField(DateTimeFieldType paramDateTimeFieldType, long paramLong) {
/*  56 */     super(paramDateTimeFieldType);
/*  57 */     this.iUnitMillis = paramLong;
/*  58 */     this.iDurationField = new LinkedDurationField(paramDateTimeFieldType.getDurationType());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract int get(long paramLong);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract long set(long paramLong, int paramInt);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract long add(long paramLong, int paramInt);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract long add(long paramLong1, long paramLong2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDifference(long paramLong1, long paramLong2) {
/*  92 */     return FieldUtils.safeToInt(getDifferenceAsLong(paramLong1, paramLong2));
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getDifferenceAsLong(long paramLong1, long paramLong2) {
/* 119 */     if (paramLong1 < paramLong2) {
/* 120 */       return -getDifferenceAsLong(paramLong2, paramLong1);
/*     */     }
/*     */     
/* 123 */     long l = (paramLong1 - paramLong2) / this.iUnitMillis;
/* 124 */     if (add(paramLong2, l) < paramLong1)
/*     */       while (true)
/* 126 */       { l++;
/* 127 */         if (add(paramLong2, l) > paramLong1)
/* 128 */         { l--;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 134 */           return l; }  }   if (add(paramLong2, l) > paramLong1) do { l--; } while (add(paramLong2, l) > paramLong1);  return l;
/*     */   }
/*     */   
/*     */   public final DurationField getDurationField() {
/* 138 */     return this.iDurationField;
/*     */   }
/*     */   
/*     */   public abstract DurationField getRangeDurationField();
/*     */   
/*     */   public abstract long roundFloor(long paramLong);
/*     */   
/*     */   protected final long getDurationUnitMillis() {
/* 146 */     return this.iUnitMillis;
/*     */   }
/*     */   
/*     */   private final class LinkedDurationField extends BaseDurationField {
/*     */     private static final long serialVersionUID = -203813474600094134L;
/*     */     
/*     */     LinkedDurationField(DurationFieldType param1DurationFieldType) {
/* 153 */       super(param1DurationFieldType);
/*     */     }
/*     */     
/*     */     public boolean isPrecise() {
/* 157 */       return false;
/*     */     }
/*     */     
/*     */     public long getUnitMillis() {
/* 161 */       return ImpreciseDateTimeField.this.iUnitMillis;
/*     */     }
/*     */     
/*     */     public int getValue(long param1Long1, long param1Long2) {
/* 165 */       return ImpreciseDateTimeField.this.getDifference(param1Long2 + param1Long1, param1Long2);
/*     */     }
/*     */ 
/*     */     
/*     */     public long getValueAsLong(long param1Long1, long param1Long2) {
/* 170 */       return ImpreciseDateTimeField.this.getDifferenceAsLong(param1Long2 + param1Long1, param1Long2);
/*     */     }
/*     */ 
/*     */     
/*     */     public long getMillis(int param1Int, long param1Long) {
/* 175 */       return ImpreciseDateTimeField.this.add(param1Long, param1Int) - param1Long;
/*     */     }
/*     */     
/*     */     public long getMillis(long param1Long1, long param1Long2) {
/* 179 */       return ImpreciseDateTimeField.this.add(param1Long2, param1Long1) - param1Long2;
/*     */     }
/*     */     
/*     */     public long add(long param1Long, int param1Int) {
/* 183 */       return ImpreciseDateTimeField.this.add(param1Long, param1Int);
/*     */     }
/*     */     
/*     */     public long add(long param1Long1, long param1Long2) {
/* 187 */       return ImpreciseDateTimeField.this.add(param1Long1, param1Long2);
/*     */     }
/*     */     
/*     */     public int getDifference(long param1Long1, long param1Long2) {
/* 191 */       return ImpreciseDateTimeField.this.getDifference(param1Long1, param1Long2);
/*     */     }
/*     */ 
/*     */     
/*     */     public long getDifferenceAsLong(long param1Long1, long param1Long2) {
/* 196 */       return ImpreciseDateTimeField.this.getDifferenceAsLong(param1Long1, param1Long2);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/field/ImpreciseDateTimeField.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */